using System;
using System.Collections.Generic;
using Fiddler;
using Newtonsoft.Json.Linq;
using System.Text.RegularExpressions;

/*
 * TV_EUI
 * 
 * */
namespace AdPlugin
{
    public class Common
    {
        // log标签
        static string tag = "[LeEco_Debug]" + DateTime.Now.ToLocalTime().ToString() + "-------";

        /**
         * 判断广告请求的返回值中是否有广告
         * Ad.Count!=0
         * */
        public static Boolean hasAd(String response)
        {
            JObject obj = JObject.Parse(response);
            JArray AdArray = null;
            //处理合并接口的情况,play
            if (obj.Property("body") != null)
            {
                if (JObject.Parse(obj["body"]["adInfo"]["adData"].ToString()).Count>0)
                {
                   AdArray = JArray.Parse(obj["body"]["adInfo"]["adData"]["vast"]["Ad"].ToString());
                }
                else
                {
                    FiddlerApplication.Log.LogString("!"+tag+"合并接口play返回的数据中没有adData,adInfo="+ obj["body"]["adInfo"].ToString());
                    return false;
                }
            }
            else
            {//show
                AdArray = JArray.Parse(obj["vast"]["Ad"].ToString());
               
            }
            //广告数组
            if (AdArray.Count == 0)
            {
                return false;
            }
            else
            {
                return true;
            }
        }

        /**
        * 获取返回值中广告的vast串
        * */
        public static JObject getAdArray(String response)
        {
            JObject obj = JObject.Parse(response);
            JObject vast = null;
            //处理合并接口的情况,play
            if (obj.Property("body") != null)
            {
                vast = JObject.Parse(obj["body"]["adInfo"]["adData"]["vast"].ToString());
            }
            else
            {//show
                vast = JObject.Parse(obj["vast"].ToString());

            }
            //广告vast串
            return vast;
        }

        /**
         * 将url请求参数转换成map 
         * 
         **/
        public static Dictionary<String, String> params2map(String url)
        {
            Dictionary<String, String> dic = new Dictionary<String, String>();
            string param = url.Substring(url.IndexOf("?") + 1);
            String[] array = param.Split('&');
            String key, value;
            for (int i = 0; i < array.Length; i++)
            {
                key = array[i].Split('=')[0];
                if (array[i].EndsWith("="))
                {
                    value = "";
                }
                else
                {
                    value = array[i].Split('=')[1];
                }
                dic.Add(key, value);
            }
            try
            {
                //解析tracking 请求中的data字段
                //参考wiki http://wiki.letv.cn/pages/viewpage.action?pageId=30834694

                if (url.Contains("/t?mid="))
                {
                    //去掉不需要校验的字段
                    dic.Remove("uip");
                    dic.Remove("dtype");
                    dic.Remove("uid");
                    dic.Remove("t");
                    dic.Remove("s");

                    // 将data中的数据也通过对应的key存入map中,参考wiki文档
                    String[] data = Regex.Split(dic["data"], "%2C", RegexOptions.IgnoreCase);
                    dic.Remove("data");
                    dic.Add("pid", data[0]);
                    dic.Add("area", data[1]);
                    dic.Add("ark", data[2]);
              //      dic.Add("uuid", data[3]);         //暂时不校验，uuid=cuid+时间戳，焦点图广告请求一次，之后曝光，时间戳不同
                    dic.Add("orderId", data[4]);
                    if (data[5] != null && !"".Equals(data[5]))
                    {//屏保，开机和关机广告都没有vid
                        dic.Add("vid", data[5]);
                    }
                    dic.Add("lc", data[8]);
                    dic.Add("ct", data[9]);
                    if (url.Contains("%2CEUI%2C"))  //EUI版有值，TV版为空
                    {
                        dic.Add("ch", data[10]);
                    }
            //        dic.Add("ord", data[12]);
                    dic.Add("isoffline", data[14]);
            //        dic.Add("v", data[15]);    tracking中错误，少了_2
                    //         if (data[16] != null && !"".Equals(data[16]))
                    //            dic.Add("dev", data[16]);  //暂时不校验
                    dic.Add("product", data[17]);
                    dic.Add("mac", data[18]);
                    if(data[20]!=null && !"".Equals(data[20]))
                        dic.Add("sub", data[20]);
                    dic.Add("nv", data[22]);
                }

                //去掉report中不需校验的字段
                if (url.Contains("/va/?act="))
                {
                    //去掉动态变化，不重要的数据
                    dic.Remove("aps");
                    dic.Remove("ct"); //个数
                    dic.Remove("dur");
                    dic.Remove("dur_total");
                    dic.Remove("ia"); //禁播广告，1会员，0正常播放
                    dic.Remove("ontime");   //时间轴位置
                    dic.Remove("pid");
                    dic.Remove("py");
                    dic.Remove("ry");
                    dic.Remove("r"); //随机数
                    dic.Remove("ut");
                    if (dic.ContainsKey("dev"))   //暂时删掉，不校验
                        dic.Remove("dev");
                    if (dic.ContainsKey("uuid"))   //暂时删掉，不校验
                        dic.Remove("uuid");
                    if (dic.ContainsKey("uid"))   // 用户id，只有登录时有
                        dic.Remove("uid");
                    if (dic.ContainsKey("ftype"))  // 广告素材类型
                        dic.Remove("ftype");
                    if (dic.ContainsKey("loc"))   // 第三方检测，event时为第三方素材
                        dic.Remove("nord");
                    if (dic.ContainsKey("nord"))   // letv，顺序
                        dic.Remove("loc");
                    dic.Remove("vlen");
                    dic.Remove("ctime");
                    dic.Remove("apprunid");
                    dic.Remove("wmac");
                }
            }
            catch
            {
                throw;
            }

            //foreach (KeyValuePair<string, string> it in dic)//遍历dic
            //{
            //    Console.WriteLine("Output Key : {0}, Value : {1} ", it.Key, it.Value);
            //}

            return dic;
        }


        /**
         * 将url请求参数转换成map 
         * 
         * iOS和Android乐视视频专用方法
         * 
         **/
        public static Dictionary<String, String> params2map_Letv(String url)
        {
            Dictionary<String, String> dic = new Dictionary<String, String>();
            string param = url.Substring(url.IndexOf("?") + 1);
            String[] array = param.Split('&');
            String key, value;
            for (int i = 0; i < array.Length; i++)
            {
                key = array[i].Split('=')[0];
                if (array[i].EndsWith("="))
                {
                    value = "";
                }
                else
                {
                    value = array[i].Split('=')[1];
                }
                dic.Add(key, value);
            }
            try
            {
                //解析tracking 请求中的data字段
                //参考wiki http://wiki.letv.cn/pages/viewpage.action?pageId=30834694

                if (url.Contains("/t?mid="))
                {
                    //去掉不需要校验的字段
                    dic.Remove("uip");
                    dic.Remove("dtype");
                    dic.Remove("uid");
                    dic.Remove("t");
                    dic.Remove("s");

                    // 将data中的数据也通过对应的key存入map中,参考wiki文档
                    String[] data = Regex.Split(dic["data"], "%2C", RegexOptions.IgnoreCase);
                    dic.Remove("data");
                    dic.Add("pid", data[0]);
                    dic.Add("area", data[1]);
                    dic.Add("ark", data[2]);
                    //      dic.Add("uuid", data[3]);         //暂时不校验，uuid=cuid+时间戳，焦点图广告请求一次，之后曝光，时间戳不同
                    dic.Add("orderId", data[4]);
                    if (data[5] != null && !"".Equals(data[5]))
                    {//屏保，开机和关机广告都没有vid
                        dic.Add("vid", data[5]);
                    }
                    dic.Add("lc", data[8]);
                    dic.Add("ct", data[9]);
                    if (url.Contains("%2CEUI%2C"))  //EUI版有值，TV版为空
                    {
                        dic.Add("ch", data[10]);
                    }
                    dic.Add("ord", data[12]);
                    dic.Add("isoffline", data[14]);
                    dic.Add("v", data[15]);
                    if (data[16] != null && !"".Equals(data[16]))
                        dic.Add("dev", data[16]);
                    dic.Add("product", data[17]);
                    dic.Add("mac", data[18]);
                    if (data[20] != null && !"".Equals(data[20]))
                        dic.Add("sub", data[20]);
                    dic.Add("nv", data[22]);
                }

                //去掉report中不需校验的字段
                if (url.Contains("/va/?act="))
                {
                    //去掉动态变化，不重要的数据
                    dic.Remove("cid"); //频道id
                    dic.Remove("ct"); //个数
                    dic.Remove("dur");
                    dic.Remove("dur_total");
                    dic.Remove("ia"); //禁播广告，1会员，0正常播放
                    dic.Remove("lc");
             //       dic.Remove("pid");   
                    dic.Remove("pv");
                    dic.Remove("py");
                    dic.Remove("ty");
                    dic.Remove("r"); //随机数
                    dic.Remove("ut");
                    dic.Remove("uuid");
                    if (dic.ContainsKey("uid"))   // 用户id，只有登录时有
                        dic.Remove("uid");
                    if (dic.ContainsKey("ftype"))  // 广告素材类型
                        dic.Remove("ftype");
                    if (dic.ContainsKey("loc"))   // 第三方检测，event时为第三方素材
                        dic.Remove("nord");
                    if (dic.ContainsKey("nord"))   // letv，顺序
                        dic.Remove("loc");
                    dic.Remove("vlen");
                    dic.Remove("ctime");
                    dic.Remove("apprunid");
                    dic.Remove("aps");
                    dic.Remove("wmac");
                    dic.Remove("IDFA");
                    dic.Remove("nord");
                    dic.Remove("astatus");
                    dic.Remove("ry");
                    dic.Remove("mmsid");
                }
            }
            catch
            {
                throw;
            }
            return dic;
        }


        /**
         * 非线性广告的ord=0
         * 
         * 根据Vast-Ad节点中的cuepoint_type判断广告类型
         * */
        public static String getOrd(JObject ad)
        {
            String ord = ad["ord"].ToString();
            String ct = ad["cuepoint_type"].ToString();
            if ("7".Equals(ct) || "6".Equals(ct) || "0".Equals(ct))
            {//浮层，暂停，页面
                ord = "0";
            }
            return ord;
        }

        /**
         * 获取ad中的mid字段,如果有多个以逗号拼接
         * 
         * */
        public static String getMid(JObject ad)
        {
            String ad_mid = "";
            JArray impression = JArray.Parse(ad["InLine"]["Impression"].ToString());
            //遍历impression，获取type=1下的cdata的mid值
            foreach (JObject obj in impression)
            {
                String cdata = obj["cdata"].ToString();
                if (!"".Equals(cdata) && cdata.Contains("/t?mid="))
                {
                    int len = cdata.IndexOf('&') - cdata.IndexOf('=') - 1;
                    ad_mid += cdata.Substring(cdata.IndexOf('=') + 1, len)+",";
                }
            }
            return ad_mid;
        }

        /**
         * 获取Pid（广告位id）
         * 
         * */
        public static String getPid(JObject ad)
        {
            String adzone_id = "";
            JArray creative = JArray.Parse(ad["InLine"]["Creatives"]["Creative"].ToString());
            JObject obj = (JObject)creative[0];
            if (obj.Property("NonLinearAds") != null)
            {// 非线性广告
                obj = JObject.Parse(obj["NonLinearAds"]["NonLinear"][0].ToString());
            }
            else if (obj.Property("Linear") != null)
            {// 线性广告
                obj = JObject.Parse(obj["Linear"].ToString());
            }

            adzone_id = obj["adzone_id"].ToString();
            return adzone_id;
        }

        /**
         * 将方舟的广告类型转化成上报格式中的atype
         * 
         * */
        public static String toAtype(String ct)
        {
            String atype = ct;
            if ("4".Equals(ct))//前贴
            {
                atype = "1";
            }
            else if ("3".Equals(ct))//标板
            {
                atype = "13";
            }
            else if ("7".Equals(ct)) //浮层
            {
                atype = "14";
            }
            return atype;
        }

        /**
         * 
         * 校验tracking和report字段是否正确
         *
         * */
        public static Boolean checkParams(Dictionary<String, String> paramDic, Dictionary<String, String> s_paramDic)
        {
            Boolean flag = true;
            String msg = "";
            // 遍历paramDic,进行校验
            foreach (KeyValuePair<string, string> kvp in paramDic)
            {
                //      FiddlerApplication.Log.LogString("------------------------------c1------------进入校验"+ kvp.Key);
                // tracking，一个广告可能有多个mid，则判断包含关系
                if ("mid".Equals(kvp.Key))
                {
                    if (!s_paramDic[kvp.Key].Contains(kvp.Value))
                    {
                        flag = false;
                        FiddlerApplication.Log.LogString(tag + "曝光的mid未包含在广告信息的mid中");
                        msg += "字段key=" + kvp.Key + ",paramDic中值=" + kvp.Value + ",adInfo中值=" + s_paramDic[kvp.Key] + ";";
                    }
                    continue;
                }
                if ("act".Equals(kvp.Key) || "id".Equals(kvp.Key))
                {
                    continue;  //去掉act类型，event上报中id字段
                }
                if ("uuid".Equals(kvp.Key))
                {
                    //EUI广告监测数据中， uuid字段是两个拼接的，不能用equals比较
                    if (kvp.Value.Contains(s_paramDic[kvp.Key]))
                    {}
                    else
                    {
                        msg += "字段uuid=" + kvp.Key + ",paramDic中值=" + kvp.Value + ",adInfo中值=" + s_paramDic[kvp.Key] + ";";
                        flag = false;
                    }
                    continue;
                }
                if ("oiid".Equals(kvp.Key))
                { //ac中的oiid可能有多个，ad中只存了一个，故校验包含关系
                    if (!kvp.Value.Contains(s_paramDic[kvp.Key]))
                    {
                        flag = false;
                        FiddlerApplication.Log.LogString(tag + "上报中的oiid和广告信息的oiid不对应");
                        msg += "字段key=" + kvp.Key + ",paramDic中值=" + kvp.Value + ",adInfo中值=" + s_paramDic[kvp.Key] + ";";
                    }
                    continue;
                }
                if (!s_paramDic.ContainsKey(kvp.Key))
                {
                    msg += "广告信息的dic中缺少key：" + kvp.Key + ";";
                    flag = false;
                }
                else if (!kvp.Value.ToLower().Trim().Equals(s_paramDic[kvp.Key].ToLower()))  //忽略大小写比较
                {
                    FiddlerApplication.Log.LogString(tag + "paramDic-----key= " + kvp.Key + " and value=" + kvp.Value);
                    FiddlerApplication.Log.LogString(tag + "adInfo-----key= " + kvp.Key + " and value=" + s_paramDic[kvp.Key]);
                    msg += "字段" + kvp.Key + ",paramDic中值=" + kvp.Value + ",adInfo中值=" + s_paramDic[kvp.Key] + ";";
                    flag = false;
                }
            }
     //     FiddlerApplication.Log.LogString("------------------------------c2-----------校验结束");
            if (!"".Equals(msg))
            {
                //打印出所有字段不等的信息
                FiddlerApplication.Log.LogString("!" + tag + msg);
            }
            return flag;

        }
    }
}
