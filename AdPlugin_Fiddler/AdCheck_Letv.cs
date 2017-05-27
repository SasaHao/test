using System;
using System.Collections.Generic;
using Fiddler;
using Newtonsoft.Json.Linq;
using System.Text.RegularExpressions;

/*
 * Letv乐视视频
 * 
 * */
namespace AdPlugin
{
    public class AdCheck_Letv : Common
    {
        // log标签
        static string tag = "[LeEco_Debug]" + DateTime.Now.ToLocalTime().ToString() + "-------";

        /**
         * 
         * 校验tracking数据
         * 
         * 返回值flag：0未校验，1成功，2失败
         * 
         * */
        public static int verifyTracking(List<String> adRequest, List<String> adResponse, String tracking)
        {
            int flag = 2;
            string msg = "";

            try
            {// 将tracking中的参数转存到map
                Dictionary<String, String> t_paramDic = params2map_Letv(tracking);
                String oid = t_paramDic["oid"]; // 该曝光对应的订单项id
                String vid = t_paramDic.ContainsKey("vid") ? t_paramDic["vid"] : ""; // 该曝光对应的视频id,
                //遍历广告数据，校验rt=1
                for (int i = adResponse.Count - 1; i >= 0; i--)
                {
                    //show请求和play请求 两种广告接口
                    JObject obj = getAdArray(adResponse[i]);
                    JArray AdArray = JArray.Parse(obj["Ad"].ToString());
                    // 广告请求参数的Dictionary
                    Dictionary<String, String> s_paramDic = params2map_Letv(adRequest[i].ToString());
                    String ad_vid = s_paramDic.ContainsKey("vid") ? s_paramDic["vid"] : ""; //屏保，开机，关机广告没有vid
                    for (int n = 0; n < AdArray.Count; n++)
                    {
                        FiddlerApplication.Log.LogString("------------------------------tt1");
                        JObject ad = JObject.Parse(AdArray[n].ToString());
                        String ad_oid = ad["order_item_id"].ToString();

                        //根据oiid和vid共同确定ad信息；屏保和开关机三种广告没有vid
                        if ((!"".Equals(ad_vid) && oid.Equals(ad_oid) && vid.Equals(ad_vid))
                            || (!s_paramDic.ContainsKey("vid") && (oid.Equals(ad_oid))))
                        {
                            FiddlerApplication.Log.LogString("------------------------------tt2");
                            // 匹配成功后，获取tracking中对应的字段信息，存入到s_paramDic中
                            s_paramDic.Add("rt", "1");//默认rt=1
                            if(!s_paramDic.ContainsKey("cuid"))
                                s_paramDic.Add("cuid", s_paramDic["devid"].Replace("-",""));
                            s_paramDic.Add("mid", getMid(ad));
                            s_paramDic.Add("oid", ad_oid);  //订单项id
                            s_paramDic["im"]="1"; // 暂时写1，均认识为主广告位
                            FiddlerApplication.Log.LogString("------------------------------tt3");
                            //data字段
                            if(!s_paramDic.ContainsKey("pid"))
                                s_paramDic.Add("pid", getPid(ad)); //广告位id
                            FiddlerApplication.Log.LogString("------------------------------tt3-----------------1");
                            s_paramDic.Add("area", obj["area_id"].ToString());  //地域编码
                            FiddlerApplication.Log.LogString("------------------------------tt3-----------------2");
                     //       s_paramDic.Add("uuid", s_paramDic["vvid"]);
                           
                            s_paramDic.Add("orderId", ad["order_id"].ToString()); //订单id
                            s_paramDic.Add("lc", ad["lc"].ToString());
                            FiddlerApplication.Log.LogString("------------------------------tt4");
                            s_paramDic.Add("ord", getOrd(ad));
                            s_paramDic.Add("isoffline", ad["offline"].ToString());
                            s_paramDic.Add("product", "0");
                            FiddlerApplication.Log.LogString("------------------------------tt5");
                            s_paramDic.Add("sub", ad.Property("parent") == null ? "0" : ad["parent"].ToString()); //是否是替补
                            //浮层和暂停广告请求中的cis请求的，没有ct字段
                            if (s_paramDic.ContainsKey("ct"))
                            {
                                s_paramDic["ct"] = ad["cuepoint_type"].ToString();
                            }
                            else
                            {
                                s_paramDic.Add("ct", ad["cuepoint_type"].ToString());
                            }
                            FiddlerApplication.Log.LogString("------------------------------tt6");
                            // TV端tracking data字段到nv，后面的值均为空，暂时只校验data前29个字段
                            // 校验tracking中的参数是否正确
                            Boolean res = checkParams(t_paramDic, s_paramDic);
                            FiddlerApplication.Log.LogString("------------------------------tt7");
                            if (res)
                            {
                                msg = "广告监测发送成功，cuepoint_type=" + ad["cuepoint_type"].ToString() + ",order_item_id=" + s_paramDic["oid"];
                                flag = 1;

                            }
                            else
                            {
                                msg = "广告监测数据失败，cuepoint_type=" + ad["cuepoint_type"].ToString() + ",order_item_id=" + s_paramDic["oid"];
                                flag = 2;
                            }
                            break;
                        }
                    }
                    if (flag == 1)  //校验成功后跳出外层循环
                    {
                        break;
                    }
                }
            }
            catch (Exception e)
            {
                msg = "tracking校验程序发生异常，异常信息为：" + e.Message;
                flag = 2;
            }
            finally
            {
                if ("".Equals(msg))
                {
                    flag = 0;
                    msg = "该tracking未找到对应的广告信息！tracking=" + tracking;
                }
                FiddlerApplication.Log.LogString("!" + tag + msg);
            }
            return flag;
        }

        /**
         * 
         * 校验report数据(ac,event)
         * 
         * 返回值flag：0未校验，1成功，2失败
         * 
         * */
        public static int verifyReport(List<String> adRequest, List<String> adResponse, String report)
        {
            int flag = 2;
            string msg = "";

            try
            {// 将report中的参数转存到map
                Dictionary<String, String> r_paramDic = params2map_Letv(report);
                //err字段！=0表示报错
                if (!"0".Equals(r_paramDic["err"]))
                {
                    msg = "上报数据错误，err=" + r_paramDic["err"];
                    FiddlerApplication.Log.LogString("!" + tag + msg);
                    flag = 2;
                    return flag;
                }
                FiddlerApplication.Log.LogString("------------------------------1");

                String act = r_paramDic["act"]; // 上报类型
                String vid = r_paramDic.ContainsKey("vid") ? r_paramDic["vid"] : ""; // 视频id
                String atype = r_paramDic["atype"]; // 广告类型，1中贴 2前贴 6暂停 13标板 14浮层
                String oiid = "";  //订单项id
                //如果请求不返回广告，则播放默认广告，此时没有oiid（测试中遇到过暂停广告播放默认，20170317）
                if (!r_paramDic.ContainsKey("oiid"))
                {
                    FiddlerApplication.Log.LogString("/"+tag+ "上报数据中没有oiid字段，请查看是否播放的是默认广告，上报请求："+ report);
                }else
                {
                    //订单项id,如果ac中有多支广告，则取第一个
                    oiid = r_paramDic["oiid"].Contains("_") ? r_paramDic["oiid"].Split('_')[0] : r_paramDic["oiid"];
                }
                //遍历广告数据
                for (int i = adResponse.Count - 1; i >= 0; i--)
                {

                   FiddlerApplication.Log.LogString("------------------------------2");
                    //show请求和play请求 两种广告接口
                    JObject obj = getAdArray(adResponse[i]);
                    JArray AdArray = JArray.Parse(obj["Ad"].ToString());
                    // 广告请求参数的Dictionary
                    Dictionary<String, String> s_paramDic = params2map_Letv(adRequest[i].ToString());
                    String ad_vid = s_paramDic.ContainsKey("vid") ? s_paramDic["vid"] : "";   //视频id，屏保，开机，关机广告没有vid
                    for (int n = 0; n < AdArray.Count; n++)
                    {
                        FiddlerApplication.Log.LogString("------------------------------3");

                        //校验公共字段
                        JObject ad = JObject.Parse(AdArray[n].ToString());
                        String ad_oid = ad["order_item_id"].ToString();
                        //根据oiid和vid共同确定ad信息；屏保和开关机三种广告没有vid
                        if ((!"".Equals(vid) && oiid.Equals(ad_oid) && vid.Equals(ad_vid))
                            || (!s_paramDic.ContainsKey("vid") && (oiid.Equals(ad_oid))))
                        {
                            FiddlerApplication.Log.LogString("------------------------------4");

                            // 匹配成功后，获取report中对应的字段信息，存入到s_paramDic中
                            s_paramDic.Add("oiid", ad_oid);
                            s_paramDic.Add("atype", toAtype(ad["cuepoint_type"].ToString()));
                            //        s_paramDic.Add("ch", "01001001000");//乐视视频没有渠道号
                            FiddlerApplication.Log.LogString("------------------------------5");
                      //      s_paramDic.Add("cid", "2");//默认值
                            s_paramDic.Add("err", "0");//默认值,成功，err!=0时，直接跳出校验
                            s_paramDic.Add("p1", "0");//默认值
                            s_paramDic.Add("p2", "00");//乐视视频
                            s_paramDic.Add("p3", "002");
                            s_paramDic.Add("sys", "1");
                            s_paramDic.Add("ty", "0");
                            s_paramDic.Add("ver", "2.0");
                            FiddlerApplication.Log.LogString("------------------------------6");
                            s_paramDic.Add("zone", "CN");
                            //          s_paramDic.Add("lc", s_paramDic["cuid"]);   //合并接口中没有cuid
                   //         s_paramDic.Add("pv", s_paramDic["nv"]);
                 //           s_paramDic.Add("uuid", s_paramDic["vvid"]);   //合并接口中没有vvid，不用校验---20170330
                            FiddlerApplication.Log.LogString("------------------------------7");
                  //          s_paramDic.Add("astatus", s_paramDic["offline"]);
                            s_paramDic.Add("androidID", s_paramDic["devid"]);
                            s_paramDic.Add("ord", ad["ord"].ToString());
                            FiddlerApplication.Log.LogString("-----------------------------8");

                            // 校验ac/event上报中的参数是否正确
                            Boolean res = checkParams(r_paramDic, s_paramDic);
                            FiddlerApplication.Log.LogString("------------------------------9");

                            if (res)
                            {
                                //event上报
                                if ("event".Equals(act))
                                {
                                    if ("1".Equals(r_paramDic["id"]))
                                    {
                                        msg = "广告event曝光上报发送成功，cuepoint_type=" + ad["cuepoint_type"].ToString() + ",order_item_id=" + oiid;
                                    }
                                    else if ("3".Equals(r_paramDic["id"]))
                                    {
                                        msg = "广告event完成上报发送成功，cuepoint_type=" + ad["cuepoint_type"].ToString() + ",order_item_id=" + oiid;
                                    }
                                }
                                else
                                {//ac上报
                                    msg = "广告ac上报发送成功，cuepoint_type=" + ad["cuepoint_type"].ToString() + ",order_item_id=" + oiid;
                                }
                                flag = 1;
                            }
                            else
                            {
                                flag = 2;
                                msg = "广告上报失败，上报请求为：" + report;
                            }
                            break;
                        }
                    }
                    if (flag == 1)  //校验成功后跳出外层循环
                    {
                        break;
                    }
                }
            }
            catch (Exception e)
            {
                flag = 2;
                msg = "report校验程序发生异常，异常信息为：" + e.Message;
            }
            finally
            {
                if ("".Equals(msg))
                {
                    flag = 0;
                    msg = "该report未找到对应的广告信息！report=" + report;
                }
                FiddlerApplication.Log.LogString("!" + tag + msg);
            }
            return flag;
        }
    }
}
