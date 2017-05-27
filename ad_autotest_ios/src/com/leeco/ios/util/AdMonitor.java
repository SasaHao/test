package com.leeco.ios.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.leeco.protobuf.ProtobufUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 1.校验广告监测数据(ark.letv.com/t?)
 * 
 * 2.校验广告上报数据(apple.www.letv.com/va/?)
 * 
 * 使用fiddler抓包，保存到本地后解析
 * 
 * @author haoxiaosha
 *
 */
public class AdMonitor {

	private static String fiddlerLogFile = Constant.FIDDLER_LOG;
	private static String playResponseFile = Constant.PLAY_RESPONSE_FILE;

	// 存放fiddler log文件中的内容（包含show请求和play请求获取的广告）
	private static List<String> urlList = new ArrayList<String>();
	private static List<String> vastList = new ArrayList<String>();
	private static List<String> videoInfoList = new ArrayList<String>();
	private static List<String> trackingList = new ArrayList<String>();
	private static List<String> reportList = new ArrayList<String>();

	/**
	 * 读取fiddler抓包得到的tracking数据
	 */
	public static void readFilddlerLog() {
		clearList(); // 首先，清空list的缓存
		try {
			FileReader reader = new FileReader(fiddlerLogFile);
			BufferedReader br = new BufferedReader(reader);
			String str, lastShow = "", lastPlay = "";
			while ((str = br.readLine()) != null) { // 始读文件
				if ("".equals(str.trim())) {
					continue; // 遇到空行跳过，读取下
				}
				if (str.startsWith("ark.letv.com/s?") || str.startsWith("test.ark.letv.com/s?")) {
					lastShow = str.trim();
					urlList.add(lastShow);
				} else if (str.startsWith("show_response")) {
					String json = str.substring(str.indexOf("{"), str.lastIndexOf("}") + 1);
					JSONObject obj = JSONObject.fromObject(json);
					if (obj.getJSONObject("vast").getJSONArray("Ad").size() != 0) { // 包含广告
						vastList.add(json);
					} else { // 不含广告的话忽略不计，并删除url中的数据
						urlList.remove(lastShow);
					}
				} else if (str.startsWith("api.mob.app.letv.com/play?")
						|| str.startsWith("t.api.mob.app.letv.com/play?")) {
					lastPlay = str.trim();
					urlList.add(lastPlay);
				} else if (str.startsWith("play_response")) {
					JSONObject obj = null;
					if (!str.endsWith("-")) {
						String json = str.substring(str.indexOf("{"), str.lastIndexOf("}") + 1);
						obj = JSONObject.fromObject(json);
						vastList.add(obj.getJSONObject("body").toString());
					} else {// 返回值为"play_response-----"，结果存在文件中，使用protobuf解析
						String json = ProtobufUtil.getPlayResponse(playResponseFile);
						obj = JSONObject.fromObject(json);
						vastList.add(obj.toString());
					}

					JSONObject videoInfo = obj.containsKey("body")
							? obj.getJSONObject("body").getJSONObject("videoInfo") : obj.getJSONObject("videoInfo");
					if (!"".equals(videoInfo)) {
						videoInfoList.add(videoInfo.toString());
					}

				} else if (str.startsWith("ark.letv.com/t?")) { // 监测
					trackingList.add(str.trim());
				} else if (str.startsWith("apple.www.letv.com/va/?") || str.startsWith("dev.dc.letv.com/va/?")) { // 上报
					reportList.add(str.trim());
				}
			}
			br.close();
			reader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 清空fiddler抓取到的log
	 */
	public static void clearLog() {
		try {
			FileWriter fw = new FileWriter(new File(fiddlerLogFile));
			fw.write("");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 清空list中的缓存数据
	 */
	private static void clearList() {
		urlList.clear();
		vastList.clear();
		videoInfoList.clear();
		trackingList.clear();
		reportList.clear();
	}

	// 本地测试
	public static void main(String[] args) {

		verifyTracking(0);
		// startAppVerify();
		// verifyReport(0);
	}

	/**
	 * 开机广告校验
	 * 
	 * @return
	 */
	public static boolean startAppVerify() {
		// 读取fiddler log文件
		readFilddlerLog();
		if (trackingList.size() > 0 && vastList.size() > 0) {

			/**
			 * 开机启动测试方法：打开两次app，第一次请求到的ad缓存，第二次启动时曝光
			 * 
			 * 检验： vastList.get(0)的广告和trackingList.get(1)是否匹配
			 */
			// 将tracking中的参数转存到map
			Map<String, String> t_paramMap = params2map(trackingList.get(1));
			Map<String, String> paramMap = params2map(urlList.get(0));
			String mid = t_paramMap.get("mid"); // 监测id
			String rt = t_paramMap.get("rt"); // 监测类型

			// 遍历vastList中的Ad
			JSONObject obj = JSONObject.fromObject(vastList.get(0)).getJSONObject("vast");
			JSONArray AdArray = obj.getJSONArray("Ad");
			// for (int n = 0; n < AdArray.size(); n++) {
			JSONObject ad = AdArray.getJSONObject(0); // 有网时，开机广告只显示第一个
			String ad_mid = getAdMid(ad, rt); // 根据rt值，获取对应广告总的mid
			if (mid.equals(ad_mid)) { // ad匹配到对应的tracking
				// 匹配到某个广告后，获取广告的信息，存入到paramMap中
				paramMap.put("mid", ad_mid);
				paramMap.put("oid", ad.getString("order_item_id"));
				paramMap.put("im", "1"); // 暂时写1，均认识为主广告位
				paramMap.put("cuid", obj.getString("cuid"));
				paramMap.put("pid", getAdPid(ad));
				paramMap.put("area", obj.getString("area_id"));
				paramMap.put("ark", obj.getString("ark"));
				paramMap.put("uuid", paramMap.containsKey("vvid") ? paramMap.get("vvid") : "");
				paramMap.put("orderId", ad.getString("order_id"));
				paramMap.put("vid", "");
				paramMap.put("aid", "");
				paramMap.put("cid", "");
				paramMap.put("lc", ad.getString("lc"));
				paramMap.put("ct", ad.getString("cuepoint_type"));
				paramMap.put("sid", ""); // 移动端，该参数一直是空
				paramMap.put("ord", ad.getString("ord"));
				paramMap.put("isoffline", ad.getString("offline"));
				paramMap.put("device", ""); // 移动端，该参数一直是空
				paramMap.put("product", "0");
				// 校验tracking中的参数是否正确
				boolean flag = checkParams(t_paramMap, paramMap);
				if (flag) {
					System.out.println("开机启动广告，发出的[mid=" + mid + "]的tracking正确！");
				} else {
					System.out.println("开机启动广告，发出的[mid=" + mid + "]的tracking有误！");
				}
			}

			System.out.println("开机启动广告，抓包校验成功！");
			return true;

		} else {
			System.out.println("未抓取到任何show、play、tracking请求的log！");
			return false;
		}

	}

	/**
	 * 校验tracking log 文件中抓取到的tracking是否正确
	 * 
	 * @return
	 */
	public static boolean verifyTracking(int clickCount) {
		readFilddlerLog(); // 读取fiddler log文件内容
		int count1 = 0; // rt=1 的tracking个数
		int count2 = 0; // rt=2 的tracking个数
		int count3 = 0; // rt=3 的tracking个数
		int count4 = 0; // rt=4 的tracking个数
		if (trackingList.size() > 0 && vastList.size() > 0) {
			Map<String, JSONObject> adMap = getAllAd(vastList); // 所有的广告
			Set<String> keysAll = adMap.keySet();
			System.out.println("本次广告order_item_id的个数为:" + keysAll.size() + ",order_item_id=" + keysAll);
			// 遍历tracking去校验
			for (int i = 0; i < trackingList.size(); i++) {
				boolean t_flag = false; // 该条tracking校验成功后置为true
				String msg = ""; // 存放tracking对应的广告ct和order_item_id
				// 将tracking中的参数转存到map
				Map<String, String> t_paramMap = params2map(trackingList.get(i));
				String mid = t_paramMap.get("mid"); // 监测id
				String rt = t_paramMap.get("rt"); // 监测类型
				if ("4".equals(rt)) {// 统计 rt=4 的个数,不做字段校验
					count4++;
					continue;
				}
				for (int j = 0; j < vastList.size(); j++) {
					// 广告请求参数的map
					Map<String, String> paramMap = params2map(urlList.get(j));
					// 遍历vastList中的Ad
					JSONObject obj = JSONObject.fromObject(vastList.get(j));
					if (obj.containsKey("adInfo")) { // play请求的返回值
						obj = obj.getJSONObject("adInfo").getJSONObject("adData").getJSONObject("vast");
					} else {// show请求的返回值
						obj = obj.getJSONObject("vast");
					}

					JSONArray AdArray = obj.getJSONArray("Ad");
					for (int n = 0; n < AdArray.size(); n++) {
						JSONObject ad = AdArray.getJSONObject(n);
						String ad_mid = getAdMid(ad, rt); // 根据rt值，获取对应广告总的mid
						if (mid.equals(ad_mid)) { // ad匹配到对应的tracking
							adMap.remove(ad.getString("order_item_id")); // 从广告总数中减去发出tracking的
							// 匹配到某个广告后，获取广告的信息，存入到paramMap中
							paramMap.put("mid", ad_mid);
							paramMap.put("oid", ad.getString("order_item_id"));
							paramMap.put("im", "1"); // 暂时写1，均认识为主广告位
							paramMap.put("cuid", obj.getString("cuid"));
							paramMap.put("u", getAdU(ad)); // rt=2时，有该值
							paramMap.put("pid", getAdPid(ad));
							paramMap.put("area", obj.getString("area_id"));
							paramMap.put("ark", obj.getString("ark"));
							paramMap.put("uuid", paramMap.containsKey("vvid") ? paramMap.get("vvid") : "");
							paramMap.put("orderId", ad.getString("order_id"));
							if (videoInfoList.size() > j) {
								JSONObject video = JSONObject.fromObject(videoInfoList.get(j));
								paramMap.put("vid", video.isEmpty() ? "" : video.getString("vid"));
								paramMap.put("aid", video.isEmpty() ? "" : video.getString("pid"));
								paramMap.put("cid", video.isEmpty() ? "" : video.getString("cid"));
							} else {
								paramMap.put("vid", "");
								paramMap.put("aid", "");
								paramMap.put("cid", "");
							}
							paramMap.put("lc", ad.getString("lc"));
							paramMap.put("ct", ad.getString("cuepoint_type"));
							paramMap.put("sid", ""); // 移动端，该参数一直是空
							paramMap.put("ord", ad.getString("ord"));
							paramMap.put("isoffline", ad.getString("offline"));
							paramMap.put("device", ""); // 移动端，该参数一直是空
							paramMap.put("product", "0");
							// 2016-6-29新增
							paramMap.put("issub", isSubAd(ad));
							paramMap.put("new_version",
									paramMap.get("nv") == null ? paramMap.get("v") + "_letvVideo" : paramMap.get("nv"));

							// 校验tracking中的参数是否正确
							boolean flag = checkParams(t_paramMap, paramMap);
							msg = "ct=" + paramMap.get("ct") + ",order_item_id=" + paramMap.get("oid");
							if (flag) {
								t_flag = true; // tracking正确
								// 按照rt的值，统计tracking个数
								if ("1".equals(rt)) {
									count1++;
								} else if ("2".equals(rt)) {
									count2++;
								} else if ("3".equals(rt)) {
									count3++;
								}
								break;
							} else {
								continue;
							}
						}
					}
					if (t_flag) { // 匹配到正确的mid后跳出vastlist的循环
						break;
					}
				}
				if (t_flag) {
					System.out.println("发出的[mid=" + mid + "]的tracking正确, 对应的广告为[" + msg + "]");
				} else {
					System.out.println("发出的[mid=" + mid + "]的tracking错误, 对应的广告为[" + msg + "]");
				}
			}

			/**
			 * 校验标准： rt=1 的tracking个数 == 广告个数 rt=2 && rt=3 ==点击次数 rt4 ==
			 * vast-ad中Creatives/Creative/Linear/TrackingEvents/
			 * Tracking中的监测全都发送成功
			 */
			// 广告个数
			int adCount = getAdCount(vastList, true);

			System.out.println("本次用例所有广告个数：" + adCount);
			System.out.println("本次用例所有tracking的个数：" + trackingList.size());
			System.out.println("发送成功的tracking中，rt=1的个数：" + count1);
			System.out.println("发送成功的tracking中，rt=2的个数：" + count2);
			System.out.println("发送成功的tracking中，rt=3的个数：" + count3);
			System.out.println("发送成功的tracking中，rt=4的个数：" + count4);

			// 进度监测tracking个数
			int rt4Count = getRt4MidCount(vastList);
			System.out.println("广告info中进度监测tracking的个数：" + rt4Count);

			// 未发出tracking的广告
			Set<String> keys = adMap.keySet();
			if (keys.size() != 0) {
				System.out.println("未发出tracking的广告order_item_id=" + keys);
			} else {
				System.out.println("所有广告(order_item_id)均发出tracking！");
			}

			if (count1 != adCount) {
				System.out.println("tracking中rt=1的个数：" + count1 + "和广告个数：" + adCount + "不相等，校验失败！");
				return false;
			}

			if (count2 != clickCount) {
				System.out.println("tracking中rt=2的个数：" + count2 + "和点击次数：" + clickCount + "不相等，校验失败！");
				return false;
			}

			if (count3 != clickCount) {
				System.out.println("tracking中rt=2的个数：" + count3 + "和点击次数：" + clickCount + "不相等，校验失败！");
				return false;
			}

			if (count3 != clickCount) {
				System.out.println("tracking中rt=4的个数：" + count4 + "和广告信息中进度监测的mid个数：" + rt4Count + "不相等，校验失败！");
				return false;
			}

			// 以上检验都通过后，说明校验成功
			System.out.println("本次测试tracking信息验证成功！");
			return true;

		} else {
			System.out.println("未抓取到任何show、play、tracking请求的log！");
			return false;
		}
	}

	/**
	 * 校验report log 文件中抓取到的上报数据是否正确
	 * 
	 * （目前只有播放器内的广告有上报，因此，只写了play广告的校验）
	 * 
	 * @author haoxiaosha
	 * 
	 * @return
	 */
	public static boolean verifyReport(int clickCount) {
		readFilddlerLog(); // 读取fiddler log文件内容
		int count1 = 0; // 统计event上报id=1 的个数
		int count2 = 0; // 统计event上报id=2 的个数
		int count3 = 0; // 统计event上报id=3 的个数
		if (reportList.size() > 0 && vastList.size() > 0) {
			// 同一个视频的videoInfo相同，获取一次后复用
			Map<String, String> common_map = getAdCommonInfo(urlList.get(0), vastList.get(0));
			// 一次播放器请求后，必须有ab和ae上报，通过flag标记是否出现这两条上报
			boolean ab_flag = false, ae_flag = false;
			// *********循环校验 report 请求**********
			for (int i = 0; i < reportList.size(); i++) {
				// System.out.println("开始校验-------" + reportList.get(i));
				// 将report url 中的参数转存到map
				Map<String, String> r_paramMap = params2map(reportList.get(i));
				// 上报类型,分为ac ab ae event,ab和ae不做字段校验，只要包含即可
				String act = r_paramMap.get("act");
				if ("ab".equals(act)) { // 包含存在ab上报即可，不用校验字段
					System.out.println("ab上报存在！");
					ab_flag = true;
					continue;
				} else if ("ae".equals(act)) { // 包含存在ae上报即可，不用校验字段
					System.out.println("ae上报存在！");
					ae_flag = true;
					continue;
				}

				// 广告类型，1中贴 2前贴 6暂停 13标板 14浮层
				String atype = r_paramMap.get("atype");

				// 校验ac和event上报的详细字段
				// ① 任何上报类型，common_map中的数据都应该一致
				if (!checkMap(common_map, r_paramMap)) {
					System.out.println("atype=" + atype + "的广告，上报数据错误：" + reportList.get(i));
					continue;
				}

				if ("ac".equals(act)) {
					if ("14".equals(atype) && !r_paramMap.containsKey("oiid")) { // 无广告订单项id，不做校验
						System.out.println("本条上报数据中无oiid参数，atype=" + atype + ",不做校验！");
						continue;
					}
					for (int j = 0; j < vastList.size(); j++) {
						// 获取某个广告类型的所有广告信息ct_map
						Map<String, String> ct_map = getAdInfoByAtype(vastList.get(j), atype);
						if (ct_map.isEmpty()) {
							// System.out.println("本次请求返回无该类型广告：atype=" + atype
							// + ",该条上报有问题，请仔细查看！");
							continue;
						}
						// ② 比较特定数据
						if (r_paramMap.get("ct").equals(ct_map.get("ct"))
								&& r_paramMap.get("dur").equals(ct_map.get("dur"))
								&& r_paramMap.get("dur_total").equals(ct_map.get("dur_total"))
								&& r_paramMap.get("oiid").equals(ct_map.get("oiid"))) {

							System.out.println("atype=" + atype + "的广告，ac上报正确！");
							break;
						}
					}
				} else if ("event".equals(act)) {
					// 每个广告至少都有两个event上报，id=1 and id=3,如果点击，则id=2也有
					String oiid = r_paramMap.get("oiid");
					for (int j = 0; j < vastList.size(); j++) {
						// 获取某个广告类型的所有广告信息ct_map
						Map<String, String> ct_map = getAdInfoByAtype(vastList.get(j), atype);
						if (ct_map.isEmpty()) {
							// System.out.println("本次请求返回无该类型广告：atype=" + atype
							// + ",该条上报有问题，请仔细查看！");
							continue;
						}

						// ftype字段校验
						if ("1".equals(atype) || "2".equals(atype) || "13".equals(atype)) {
							if (!"video".equals(r_paramMap.get("ftype"))) {
								System.out.println("atype=" + atype + ",oiid=" + oiid + ",id=" + r_paramMap.get("id")
										+ ",【event】上报错误,ftype字段校验失败！期望值是[video],实际值是：" + r_paramMap.get("ftype"));
								continue;
							}
						} else {
							if (!"bitmap".equals(r_paramMap.get("ftype"))) {
								System.out.println("atype=" + atype + ",oiid=" + oiid + ",id=" + r_paramMap.get("id")
										+ ",【event】上报错误,ftype字段校验失败！期望值是[bitmap],实际值是：" + r_paramMap.get("ftype"));
								continue;
							}
						}
						// ② 比较特定数据
						if (r_paramMap.get("ct").equals(ct_map.get("ct"))
								&& r_paramMap.get("dur").equals(ct_map.get(oiid)) // oiid对应单个广告的dur
								&& r_paramMap.get("ord").equals(ct_map.get(oiid + "_ord")) // oiid_ord对应广告顺序
								&& r_paramMap.get("dur_total").equals(ct_map.get("dur_total"))
								&& ct_map.get("oiid").contains(r_paramMap.get("oiid"))) {
							if ("1".equals(r_paramMap.get("id"))) {
								count1++;
								System.out.println("atype=" + atype + ",oiid=" + oiid + "的广告，event【曝光】上报正确！");
							} else if ("2".equals(r_paramMap.get("id"))) {
								count2++;
								System.out.println("atype=" + atype + ",oiid=" + oiid + "的广告，event【点击】上报正确！");
							} else if ("3".equals(r_paramMap.get("id"))) {
								count3++;
								System.out.println("atype=" + atype + ",oiid=" + oiid + "的广告，event【完成】上报正确！");
							}
							break;
						}
					}

				}
			}

			// 由广告个数判断有几个event上报，（id=1和id=3的event上报=广告个数）&& （id=2 即点击的个数=传入参数）

			int adCount = getAdCount(vastList, false); // 广告个数
			System.out.println("本次用例中播放器内的广告个数：" + adCount);
			System.out.println("发送成功的report中, event上报id=1的个数：" + count1);
			System.out.println("发送成功的report中, event上报id=2的个数：" + count2);
			System.out.println("发送成功的report中, event上报id=3的个数：" + count3);

			if (!ab_flag) {
				System.out.println("report数据中【没有ab上报】，校验失败！");
				return false;
			}

			if (!ae_flag) {
				System.out.println("report数据中【没有ae上报】，校验失败！");
				return false;
			}

			if (count1 != adCount) {
				System.out.println("report数据中【event上报，id=1】的个数：" + count1 + "，和广告总数：" + adCount + "不相等，校验失败！");
				return false;
			}
			if (count2 != clickCount) {
				System.out.println("report数据中【event上报，id=2】的个数：" + count2 + "，和广告点击次数：" + clickCount + "不相等，校验失败！");
				return false;
			}
			if ((adCount - 2) != count3) { // iOS客户端，暂停和角标广告没有id=3的event上报
				System.out
						.println("report数据中【event上报，id=3】的个数：" + count3 + "，和广告总数(-2)：" + (adCount - 2) + "不相等，校验失败！");
				return false;
			}

			// 以上检验都通过后，说明校验成功
			System.out.println("report数据上报验证成功！");
			return true;

		} else {
			System.out.println("未抓取到任何show、play、report请求的log！");
			return false;
		}
	}

	/**
	 * 获取广告的基本信息,例如专辑pid，频道cid,版本pv...
	 * 
	 * @param playStr
	 *            play请求的url
	 * @param playBodyStr
	 *            play请求返回的body json串
	 * @return
	 */
	private static Map<String, String> getAdCommonInfo(String playStr, String playBodyStr) {
		Map<String, String> common_map = new HashMap<String, String>();
		Map<String, String> play_map = params2map(playStr);
		JSONObject videoInfo = JSONObject.fromObject(playBodyStr).getJSONObject("videoInfo");
		String mmsid = "";
		if (JSONObject.fromObject(playBodyStr).containsKey("videofile")) {
			mmsid = JSONObject.fromObject(playBodyStr).getJSONObject("videofile").getString("mmsid");
		} else {
			mmsid = videoInfo.getString("mmsid");
		}
		common_map.put("cid", videoInfo.getString("cid"));
		common_map.put("vid", videoInfo.getString("vid"));
		common_map.put("pid", videoInfo.getString("pid"));
		common_map.put("mmsid", mmsid);
		common_map.put("pv", play_map.get("v") + "_letvVideo");
		common_map.put("lc", play_map.get("devid")); // ios,lc 对应设备id
		common_map.put("uuid", play_map.get("vvid"));
		common_map.put("pcode", play_map.get("pcode"));
		common_map.put("astatus", "0");
		return common_map;
	}

	/**
	 * 获取某个广告类型的相信信息
	 * 
	 * @param playVastStr
	 * @param atype
	 * @return
	 */
	private static Map<String, String> getAdInfoByAtype(String playBodyStr, String atype) {
		Map<String, String> ct_map = new HashMap<String, String>();
		JSONObject obj = JSONObject.fromObject(playBodyStr);
		if (obj.containsKey("adInfo")) { // play请求的返回值
			obj = obj.getJSONObject("adInfo").getJSONObject("adData").getJSONObject("vast");
		} else {// show请求的返回值
			obj = obj.getJSONObject("vast");
		}
		JSONArray AdArray = obj.getJSONArray("Ad");
		String cuepoint_type = atype2cuepointType(atype);
		int count = 0;
		String dur = "";
		int dur_total = 0;
		String oiid = "";
		for (int n = 0; n < AdArray.size(); n++) {
			JSONObject ad = AdArray.getJSONObject(n);
			if (ad != null && cuepoint_type.equals(ad.getString("cuepoint_type"))) {
				count++;
				int duration = getAdDuration(ad);
				dur += duration + "_";
				dur_total += duration;
				String order_item_id = ad.getString("order_item_id");
				String ord = ad.getString("ord");
				ct_map.put(order_item_id, String.valueOf(duration)); // 广告订单项id-----时长
				ct_map.put(order_item_id + "_ord", ord); // 广告订单项id_ord-----顺序
				oiid += order_item_id + "_";
			}
		}
		if (count != 0) { // 返回该类型的广告
			ct_map.put("ct", String.valueOf(count));
			ct_map.put("dur", "".equals(dur) ? "" : dur.substring(0, dur.length() - 1));
			ct_map.put("dur_total", String.valueOf(dur_total));
			ct_map.put("oiid", "".equals(oiid) ? "" : oiid.substring(0, oiid.length() - 1));
		}

		return ct_map;
	}

	/**
	 * 上报广告类型的映射到方舟广告类型 （前贴 2 ，暂停 6，这两个类型不用转变，是一致的）
	 * 
	 * @param atype
	 * @return
	 */
	private static String atype2cuepointType(String atype) {
		String cuepoint_type = atype;
		if ("1".equals(atype)) {// 中贴
			cuepoint_type = "4";
		} else if ("13".equals(atype)) {// 标板
			cuepoint_type = "3";
		} else if ("14".equals(atype)) {// 浮层
			cuepoint_type = "7";
		}
		return cuepoint_type;
	}

	/**
	 * 统计所有广告个数
	 * 
	 * @param vastList
	 * @param flag
	 *            true统计播放页banner广告，false不统计
	 * @return
	 */
	private static int getAdCount(List<String> vastList, boolean flag) {
		int count = 0;
		for (int j = 0; j < vastList.size(); j++) {
			// 遍历vastList中的Ad
			JSONObject obj = JSONObject.fromObject(vastList.get(j));
			if (obj.containsKey("adInfo")) { // play请求的返回值
				obj = obj.getJSONObject("adInfo").getJSONObject("adData").getJSONObject("vast");
			} else {// show请求的返回值
				obj = obj.getJSONObject("vast");
			}
			JSONArray AdArray = obj.getJSONArray("Ad");
			for (int i = 0; i < AdArray.size(); i++) {
				JSONObject ad = AdArray.getJSONObject(i);
				if (!flag && "0".equals(ad.getString("cuepoint_type"))) {
					continue;
				} else {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * 获取vastList中的所有广告
	 * 
	 * @param vastList
	 * @return
	 */
	private static Map<String, JSONObject> getAllAd(List<String> vastList) {
		Map<String, JSONObject> adMap = new HashMap<String, JSONObject>();
		for (int j = 0; j < vastList.size(); j++) {
			// 遍历vastList中的Ad
			JSONObject obj = JSONObject.fromObject(vastList.get(j));
			if (obj.containsKey("adInfo")) { // play请求的返回值
				obj = obj.getJSONObject("adInfo").getJSONObject("adData").getJSONObject("vast");
			} else {// show请求的返回值
				obj = obj.getJSONObject("vast");
			}
			JSONArray AdArray = obj.getJSONArray("Ad");
			for (int i = 0; i < AdArray.size(); i++) {
				JSONObject ad = AdArray.getJSONObject(i);
				adMap.put(ad.getString("order_item_id"), ad);
			}
		}
		return adMap;
	}

	/**
	 * 获取前贴和中贴广告进度监测的tracking个数
	 * 
	 * @param vastList
	 * @return
	 */
	private static int getRt4MidCount(List<String> vastList) {
		int count = 0;
		for (int j = 0; j < vastList.size(); j++) {
			// 遍历vastList中的Ad
			JSONObject obj = JSONObject.fromObject(vastList.get(j));
			if (obj.containsKey("adInfo")) { // play请求的返回值
				obj = obj.getJSONObject("adInfo").getJSONObject("adData").getJSONObject("vast");
			} else {// show请求的返回值
				obj = obj.getJSONObject("vast");
			}
			JSONArray AdArray = obj.getJSONArray("Ad");
			for (int n = 0; n < AdArray.size(); n++) {
				JSONObject ad = AdArray.getJSONObject(n);
				// 前贴，中贴有进度监测，即 cuepoint_type = 2 or 4
				if ("2".equals(ad.getString("cuepoint_type")) || "4".equals(ad.getString("cuepoint_type"))) {
					JSONArray trackingArray = ad.getJSONObject("InLine").getJSONObject("Creatives")
							.getJSONArray("Creative").getJSONObject(0).getJSONObject("Linear")
							.getJSONObject("TrackingEvents").getJSONArray("Tracking");
					for (int i = 0; i < trackingArray.size(); i++) {
						JSONObject tracking = trackingArray.getJSONObject(i);
						if (tracking.getString("cdata").contains("http://ark.letv.com/t?mid=")) {
							count++;
						}
					}
				}
			}
		}
		return count;
	}

	/**
	 * 检验tracking请求中的参数是否正确
	 * 
	 * @param t_map
	 * @param ad_map
	 * @return
	 */
	private static boolean checkParams(Map<String, String> t_map, Map<String, String> ad_map) {
		boolean flag = true;
		if (t_map.get("ct").equals("0") || t_map.get("ct").equals("6") || t_map.get("ct").equals("7")) {
			t_map.remove("ord"); // 开机广告和暂停广告的ord 广告中的和tracking中的不一致
			// 暂停的时候，这三个字段是从play请求中拿的，暂时未处理
			t_map.remove("vid");
			t_map.remove("aid");
			t_map.remove("cid");
		}

		// 遍历t_map,进行校验
		for (String key : t_map.keySet()) {
			if ("rt".equals(key)) { // 不校验rt，但是统计用到该参数，因此不能删除
				continue;
			}

			if (!t_map.get(key).equals(ad_map.get(key))) {
				System.out.println("tracking-----key= " + key + " and value= " + t_map.get(key));
				System.out.println("adInfo-----key= " + key + " and value= " + ad_map.get(key));
				flag = false;
				break;
			}
		}
		return flag;
	}

	/**
	 * 比较两个map中的值是否相等,参数2大于参数1,相当于参数2包含参数1时返回true
	 * 
	 * @param map
	 * @param r_paramMap
	 * @return
	 */
	private static boolean checkMap(Map<String, String> map, Map<String, String> r_paramMap) {
		boolean flag = true;
		// 遍历common_map,进行校验
		for (String key : map.keySet()) {
			if (!r_paramMap.containsKey(key) || !map.get(key).equals(r_paramMap.get(key))) {
				flag = false;
				System.out.println("common_map----[key=" + key + ",value=" + map.get(key));
				System.out.println("r_paramMap----[key=" + key + ",value=" + r_paramMap.get(key));
				break;
			}
		}
		return flag;
	}

	/**
	 * 将url参数转换成map格式
	 * 
	 * @param url
	 * @return
	 */
	private static Map<String, String> params2map(String url) {
		Map<String, String> map = new HashMap<String, String>();
		String params = url.substring(url.indexOf("?") + 1);
		String[] array = params.split("&");
		String key, value;
		for (int i = 0; i < array.length; i++) {
			key = array[i].split("=")[0];
			if (array[i].endsWith("=")) {
				value = "";
			} else {
				value = array[i].split("=")[1];
			}
			map.put(key, value);
		}

		if (url.startsWith("ark.letv.com/t?")) {
			// 将data中的数据也通过对应的key存入map中,参考wiki文档
			String[] data = map.get("data").split("%2C");
			map.put("pid", data[0]);
			map.put("area", data[1]);
			map.put("ark", data[2]);
			map.put("uuid", data[3]);
			map.put("orderId", data[4]);
			map.put("vid", data[5]);
			map.put("aid", data[6]);
			map.put("cid", data[7]);
			map.put("lc", data[8]);
			map.put("ct", data[9]);
			// map.put("ch", data[10]); //广告信息里获取不到
			map.put("sid", data[11].trim());
			map.put("ord", data[12]);
			// map.put("playtime", data[13]); //暂时未校验时间戳
			map.put("isoffline", data[14].trim());
			map.put("v", data[15]); // 对应请求url中的v
			map.put("device", data[16]);
			map.put("product", data[17]);
			// mac 该字段用于tv端，其他端未传，暂时不写

			// 2016-6-29 新增字段，校验issub和new_version
			// issunb,0 或空表示为主订单项
			map.put("issub", data[20].isEmpty() ? "0" : data[20]);
			map.put("new_version", data[22]);

			/**
			 * 时间戳未校验，故先删除，以免遍历比较的时候出错
			 */
			map.remove("t");
			map.remove("s");
			map.remove("u"); // 暂时不校验
			map.remove("data"); // data中的参数已存入
		}
		return map;
	}

	/**
	 * 获取广告对应的mid(广告监测id)
	 * 
	 * @param ad
	 * @return
	 */
	private static String getAdMid(JSONObject ad, String rt) {
		String ad_mid = "";
		JSONArray impression = ad.getJSONObject("InLine").getJSONArray("Impression");
		String ct = ad.getString("cuepoint_type"); // 广告类型，区分线性和非线性
		JSONObject videoClicks;
		if (ct.equals("0") || ct.equals("6") || ct.equals("7")) {// 非线性广告
			videoClicks = ad.getJSONObject("InLine").getJSONObject("Creatives").getJSONArray("Creative")
					.getJSONObject(0).getJSONObject("NonLinearAds").getJSONArray("NonLinear").getJSONObject(0);
		} else {
			// 线性广告
			videoClicks = ad.getJSONObject("InLine").getJSONObject("Creatives").getJSONArray("Creative")
					.getJSONObject(0).getJSONObject("Linear").getJSONObject("VideoClicks");
		}
		if ("1".equals(rt)) {
			for (int i = 0; i < impression.size(); i++) {
				JSONObject imp = impression.getJSONObject(i);
				String cdata = imp.getString("cdata");
				if (cdata.contains("http://ark.letv.com/t?mid=")) {
					ad_mid = cdata.split("=")[1];
					break;
				}
			}
		} else if ("2".equals(rt)) {
			String str;
			if (ct.equals("0") || ct.equals("6") || ct.equals("7")) {// 非线性广告
				str = videoClicks.containsKey("NonLinearClickThrough") ? videoClicks.getString("NonLinearClickThrough")
						: "";
			} else {
				str = videoClicks.containsKey("ClickThrough") ? videoClicks.getString("ClickThrough") : "";
			}
			if (!"".equals(str) && str.contains("http://ark.letv.com/t?mid=")) {
				ad_mid = str.substring(str.indexOf("=") + 1, str.lastIndexOf("&"));
			}

		} else if ("3".equals(rt)) {
			JSONArray strArray;
			if (ct.equals("0") || ct.equals("6") || ct.equals("7")) {// 非线性广告
				strArray = videoClicks.containsKey("NonLinearClickTracking")
						? videoClicks.getJSONArray("NonLinearClickTracking") : null;
			} else {// 线性广告
				strArray = videoClicks.containsKey("ClickTracking") ? videoClicks.getJSONArray("ClickTracking") : null;
			}
			if (strArray == null) { // 不存在点击监测mid
				return ad_mid;
			} else {
				for (int j = 0; j < strArray.size(); j++) {
					JSONObject obj = strArray.getJSONObject(j);
					String cdata = obj.getString("cdata");
					if (!"".equals(cdata) && cdata.contains("http://ark.letv.com/t?mid=")) {
						ad_mid = cdata.split("=")[1];
						break;
					}
				}
			}

		}
		return ad_mid;
	}

	/**
	 * 获取rt=2时的，广告跳转地址
	 * 
	 * @param ad
	 * @return
	 */
	private static String getAdU(JSONObject ad) {
		String uStr = "";
		String ct = ad.getString("cuepoint_type"); // 广告类型，区分线性和非线性
		JSONObject videoClicks;
		String str;
		if (ct.equals("0") || ct.equals("6") || ct.equals("7")) {// 非线性广告
			videoClicks = ad.getJSONObject("InLine").getJSONObject("Creatives").getJSONArray("Creative")
					.getJSONObject(0).getJSONObject("NonLinearAds").getJSONArray("NonLinear").getJSONObject(0);
			str = videoClicks.containsKey("NonLinearClickThrough") ? videoClicks.getString("NonLinearClickThrough")
					: "";
		} else {
			// 线性广告
			videoClicks = ad.getJSONObject("InLine").getJSONObject("Creatives").getJSONArray("Creative")
					.getJSONObject(0).getJSONObject("VideoClicks");
			str = videoClicks.containsKey("ClickThrough") ? videoClicks.getString("ClickThrough") : "";
		}
		if (!"".equals(str) && str.contains("http://ark.letv.com/t?mid=")) {
			uStr = str.substring(str.lastIndexOf("=") + 1);
		}
		return uStr;
	}

	/**
	 * 获取广告对应的pid（广告位id）
	 * 
	 * @param ad
	 * @return
	 */
	private static String getAdPid(JSONObject ad) {
		String ad_pid = "";
		JSONObject creative = ad.getJSONObject("InLine").getJSONObject("Creatives").getJSONArray("Creative")
				.getJSONObject(0);

		if (creative.containsKey("NonLinearAds")) {// 非线性广告
			ad_pid = creative.getJSONObject("NonLinearAds").getJSONArray("NonLinear").getJSONObject(0)
					.getString("adzone_id");
		} else if (creative.containsKey("Linear")) {// 线性广告
			ad_pid = creative.getJSONObject("Linear").getString("adzone_id");
		}
		return ad_pid;
	}

	/**
	 * 获取广告时长，目前只考虑线性广告（播放器内的广告）
	 * 
	 * @param ad
	 * @return
	 */
	private static int getAdDuration(JSONObject ad) {
		int duration = 0;
		JSONObject creative = ad.getJSONObject("InLine").getJSONObject("Creatives").getJSONArray("Creative")
				.getJSONObject(0);
		if (creative.containsKey("Linear")) {// 线性广告
			duration = creative.getJSONObject("Linear").getInt("Duration");
		}
		return duration;
	}

	/**
	 * 判断是否替补广告
	 * 
	 * @param ad
	 * @return
	 */
	private static String isSubAd(JSONObject ad) {
		String flag;
		String cdata = ad.getJSONObject("InLine").getJSONObject("AdSystem").getString("cdata");
		if ("ark".equals(cdata)) {// 方舟订单
			flag = "0";
		} else { // exchange订单
			flag = "1";
		}
		return flag;
	}
}
