using System;
using Fiddler;
using System.Windows.Forms;
using System.Collections.Generic;

namespace AdPlugin
{
    /**
     * 目前支持版本：
     * 1 TV版
     * 2 EUI版
     * 3 乐视视频（iOS&Android）
     * 
     * */
    public class Plugin : Inspector2, IAutoTamper
    {
        private MenuItem AdVerify;  //工具栏-菜单
        private bool bCreatedColumn = false;  //session列表新增列
        //TV_开关选项
        private MenuItem miEnabled_TV;
        private bool bEnabled_TV = false;
        
        //EUI_开关选项
        private MenuItem miEnabled_EUI;
        private bool bEnabled_EUI = false;

        //Letv乐视视频_开关选项
        private MenuItem miEnabled_Letv;
        private bool bEnabled_Letv = false;

        //TV版广告请求&返回
        private static List<String> adRequest = new List<String>();
        private static List<String> adResponse = new List<String>();

        //EUI广告请求&返回
        private static List<String> adRequest_EUI = new List<String>();
        private static List<String> adResponse_EUI = new List<String>();

        //Letv乐视视频广告请求&返回
        private static List<String> adRequest_Letv = new List<String>();
        private static List<String> adResponse_Letv = new List<String>();

        // Fiddler log标签
        private static string tag = "[LeEco_Debug]" + DateTime.Now.ToLocalTime().ToString() + "-------";

        void IAutoTamper.AutoTamperResponseBefore(Session oSession)
        {
            //判断校验开关 
            if (!bEnabled_TV && !bEnabled_EUI && !bEnabled_Letv)
            {//两个开关都关闭，跳出
                return;
            }

            int res = 0; //校验结果标志,0未校验，1成功，2失败

            // 广告请求和返回
            // 开机广告第一个参数是p=1、2、3
            if ((oSession.HostnameIs("ark.cp21.ott.cibntv.net") || oSession.HostnameIs("ark.letv.com")
                || oSession.HostnameIs("test.ark.letv.com") || oSession.HostnameIs("10.154.156.146")
                || oSession.HostnameIs("t.api.mob.app.letv.com"))
                && (oSession.uriContains("/s?ark=") || oSession.uriContains("/s?p=")
                    || oSession.uriContains("/play?")))
            {
                //存储广告数据vast
                String response = oSession.GetResponseBodyAsString().Trim();
                if (!response.StartsWith("<?xml "))  //过滤垃圾数据
                {
                    if (!Common.hasAd(response))//如果请求返回的广告字段Ad为空，则不记录
                    {
                        return;
                    }
                    //打印广告信息
                    FiddlerApplication.Log.LogString(tag + oSession.url);
                    FiddlerApplication.Log.LogString(tag + response);
                    if (bEnabled_TV)//TV版
                    {
                        FiddlerApplication.Log.LogString("/" + tag + "记录TV广告");
                        adRequest.Add(oSession.url.Trim());
                        adResponse.Add(response);
                    }
                    if (bEnabled_EUI)//EUI
                    {
                        FiddlerApplication.Log.LogString("/" + tag + "记录EUI广告");
                        adRequest_EUI.Add(oSession.url.Trim());
                        adResponse_EUI.Add(response);
                    }
                    if (bEnabled_Letv)//Letv
                    {
                        FiddlerApplication.Log.LogString("/" + tag + "记录Letv广告");
                        adRequest_Letv.Add(oSession.url.Trim());
                        adResponse_Letv.Add(response);
                    }
                }
            }

            // 数据监测(tracking)
            if ((oSession.HostnameIs("ark.cp21.ott.cibntv.net") || oSession.HostnameIs("ark.letv.com") 
                || oSession.HostnameIs("test.ark.letv.com") || oSession.HostnameIs("10.154.156.146"))
                   && oSession.uriContains("/t?mid=") && oSession.uriContains("rt=1"))
            {
                //打印log信息
                FiddlerApplication.Log.LogString(tag + oSession.url);
                FiddlerApplication.Log.LogString(tag + oSession.GetResponseBodyAsString().Trim());
                if (oSession.responseCode != 200)
                {//监测请求发送不成功
                    res = 2;
                    FiddlerApplication.Log.LogString("!" + tag + "tracking发送失败，oSession.responseCode=" + oSession.responseCode);
                }
                else
                {
                    if (!"OK".Equals(oSession.GetResponseBodyAsString().Trim()))
                    {
                        res = 2;
                        FiddlerApplication.Log.LogString("!" + tag + "tracking发送失败，返回值=" + oSession.GetResponseBodyAsString());
                    }
                    else
                    {
                        if (bEnabled_TV) //TV版本
                        {
                            if (adRequest.Count < 1)
                            {
                                res = 0;
                                FiddlerApplication.Log.LogString("_" + tag + "未抓取到TV广告信息，无法校验tracking数据!");
                            }
                            else
                            {
                                FiddlerApplication.Log.LogString("/" + tag + "开始校验TV版tracking");
                                res = AdCheck_TV.verifyTracking(adRequest, adResponse, oSession.url);
                            }
                        }
                        if (bEnabled_EUI) //EUI
                        {
                            if (adRequest_EUI.Count < 1)
                            {
                                res = 0;
                                FiddlerApplication.Log.LogString("_" + tag + "未抓取到EUI广告信息，无法校验tracking数据!");
                            }
                            else
                            {
                                FiddlerApplication.Log.LogString("/" + tag + "开始校验EUI版tracking");
                                res = AdCheck_EUI.verifyTracking(adRequest_EUI, adResponse_EUI, oSession.url);
                            }
                        }
                        if (bEnabled_Letv) //Letv
                        {
                            if (adRequest_Letv.Count < 1)
                            {
                                res = 0;
                                FiddlerApplication.Log.LogString("_" + tag + "未抓取到Letv广告信息，无法校验tracking数据!");
                            }
                            else
                            {
                                FiddlerApplication.Log.LogString("/" + tag + "开始校验Letv版tracking");
                                res = AdCheck_Letv.verifyTracking(adRequest_Letv, adResponse_Letv, oSession.url);
                            }
                        }
                    }
                }
                //显示校验结果
                if (res == 1)
                {
                    oSession["X-Privacy"] = "PASS";
                    oSession["ui-color"] = "green";
                }
                else if (res == 2)
                {
                    oSession["X-Privacy"] = "FAILED";
                    oSession["ui-color"] = "red";
                }
                else
                {  //未校验
                    oSession["X-Privacy"] = "UNCHECKED";
                    oSession["ui-color"] = "purple";
                }

            }

            // 数据上报(report)-----------只校验ac,event
            // 暂时过滤掉live广告
            if ((oSession.HostnameIs("dc.cp21.ott.cibntv.net") || oSession.HostnameIs("apple.www.letv.com")
                || (oSession.HostnameIs("develop.bigdata.letv.com")))
                && !oSession.uriContains("ch=tvlive")
                && (oSession.uriContains("/va/?act=ac") || oSession.uriContains("/va/?act=event")))
            {
                //打印log信息
                FiddlerApplication.Log.LogString(tag + oSession.url);
                FiddlerApplication.Log.LogString(tag + oSession.GetResponseBodyAsString());
                if (oSession.responseCode != 200)
                {//监测请求发送不成功
                    res = 2;
                    FiddlerApplication.Log.LogString("!" + tag + "report发送失败，oSession.responseCode=" + oSession.responseCode);
                }
                else
                {
                    if (bEnabled_TV && oSession.uriContains("ch=01001001000")) //TV版本
                    {
                        if (adRequest.Count < 1)
                        {
                            res = 0;
                            FiddlerApplication.Log.LogString("_" + tag + "未抓取到TV版广告信息，无法校验report数据!");
                        }
                        else
                        {
                            //校验report，根据ch来区分TV版和EUI
                            res = AdCheck_TV.verifyReport(adRequest, adResponse, oSession.url);
                        }
                    }
                    if (bEnabled_EUI && oSession.uriContains("ch=EUI")) //EUI
                    {
                        if (adRequest_EUI.Count < 1)
                        {
                            res = 0;
                            FiddlerApplication.Log.LogString("_" + tag + "未抓取到EUI广告信息，无法校验report数据!");
                        }
                        else
                        {
                            //校验report，根据ch来区分TV版和EUI
                            res = AdCheck_EUI.verifyReport(adRequest_EUI, adResponse_EUI, oSession.url);
                        }
                    }
                    if (bEnabled_Letv) //Letv
                    {
                        if (adRequest_Letv.Count < 1)
                        {
                            res = 0;
                            FiddlerApplication.Log.LogString("_" + tag + "未抓取到Letv广告信息，无法校验report数据!");
                        }
                        else
                        {
                            //校验report
                            res = AdCheck_Letv.verifyReport(adRequest_Letv, adResponse_Letv, oSession.url);
                        }
                    }

                }
                //显示校验结果
                if (res == 1)
                {//成功
                    oSession["X-Privacy"] = "PASS";
                    oSession["ui-color"] = "green";
                }
                else if (res == 2)
                {//失败
                    oSession["X-Privacy"] = "FAILED";
                    oSession["ui-color"] = "red";
                }
                else
                {  //未校验
                    oSession["X-Privacy"] = "UNCHECKED";
                    oSession["ui-color"] = "purple";
                }

            }

        }

        void IAutoTamper.AutoTamperRequestAfter(Session oSession)
        {

        }

        void IAutoTamper.AutoTamperRequestBefore(Session oSession)
        {

        }

        void IAutoTamper.AutoTamperResponseAfter(Session oSession)
        {

        }

        void IAutoTamper.OnBeforeReturningError(Session oSession)
        {

        }

        void IFiddlerExtension.OnBeforeUnload()
        {

        }

        //加载菜单
        void IFiddlerExtension.OnLoad()
        {
            //菜单
            InitializeMenu();
            FiddlerApplication.UI.mnuMain.MenuItems.Add(AdVerify);
            //session列表中的列
            if (bEnabled_TV || bEnabled_EUI || bEnabled_EUI)
            {
                EnsureColoumn();
            }
        }

        //初始化菜单
        private void InitializeMenu()
        {
            this.miEnabled_TV = new MenuItem("&TV版");
            this.miEnabled_EUI = new MenuItem("&TV_EUI");
            this.miEnabled_Letv = new MenuItem("&乐视视频");
            this.AdVerify = new MenuItem("乐视-AdVerify");

            this.miEnabled_TV.Index = 0;
            this.miEnabled_EUI.Index = 1;
            this.miEnabled_Letv.Index = 2;
            this.AdVerify.MenuItems.AddRange(new MenuItem[] { this.miEnabled_TV, this.miEnabled_EUI, this.miEnabled_Letv });
    //        this.AdVerify.MenuItems.AddRange(new MenuItem[] { this.miEnabled_EUI });

            this.miEnabled_TV.Click += new System.EventHandler(this.miEnabled_TV_Click);
            this.miEnabled_EUI.Click += new System.EventHandler(this.miEnabled_EUI_Click);
            this.miEnabled_Letv.Click += new System.EventHandler(this.miEnabled_Letv_Click);
        }

        //TV版-选项
        public void miEnabled_TV_Click(object sender, EventArgs e)
        {
            miEnabled_TV.Checked = !miEnabled_TV.Checked;
            bEnabled_TV = miEnabled_TV.Checked;
            if (bEnabled_TV)
            {
                if (bEnabled_EUI)
                {
                    miEnabled_EUI_Click(sender, e);
                }
                if (bEnabled_Letv)
                {
                    miEnabled_Letv_Click(sender, e);
                }
                EnsureColoumn();
                FiddlerApplication.Log.LogString(tag + "开启TV版广告校验功能！");
            }
            else
            {
                //清空广告list
                adRequest.Clear();
                adResponse.Clear();
                FiddlerApplication.Log.LogString(tag + "关闭TV版广告校验功能,已清空广告缓存数据！");
            }
        }

        //EUI-选项
        public void miEnabled_EUI_Click(object sender, EventArgs e)
        {
            miEnabled_EUI.Checked = !miEnabled_EUI.Checked;
            bEnabled_EUI = miEnabled_EUI.Checked;
            if (bEnabled_EUI)
            {
                if (bEnabled_TV)
                {
                    miEnabled_TV_Click(sender, e);
                }
                if (bEnabled_Letv)
                {
                    miEnabled_Letv_Click(sender, e);
                }
                EnsureColoumn();
                FiddlerApplication.Log.LogString(tag + "开启TV_EUI广告校验功能！");
            }
            else
            {
                //清空广告list
                adRequest_EUI.Clear();
                adResponse_EUI.Clear();
                FiddlerApplication.Log.LogString(tag + "关闭TV_EUI广告校验功能,已清空广告缓存数据！");
            }
        }

        //Letv乐视视频-选项
        public void miEnabled_Letv_Click(object sender, EventArgs e)
        {
            miEnabled_Letv.Checked = !miEnabled_Letv.Checked;
            bEnabled_Letv = miEnabled_Letv.Checked;
            if (bEnabled_Letv)
            {
                if (bEnabled_TV)
                {
                    miEnabled_TV_Click(sender, e);
                }
                if (bEnabled_EUI)
                {
                    miEnabled_EUI_Click(sender, e);
                }
                EnsureColoumn();
                FiddlerApplication.Log.LogString(tag + "开启Letv乐视视频广告校验功能！");
            }
            else
            {
                //清空广告list
                adRequest_Letv.Clear();
                adResponse_Letv.Clear();
                FiddlerApplication.Log.LogString(tag + "关闭Letv乐视视频广告校验功能,已清空广告缓存数据！");
            }
        }

        //session列表中的列
        private void EnsureColoumn()
        {
            if (bCreatedColumn)
                return;
            FiddlerApplication.UI.lvSessions.AddBoundColumn("Tracking & Report", 1, 100, "X-Privacy");
            bCreatedColumn = true;
        }

        public override void AddToTab(TabPage o)
        {
            throw new NotImplementedException();
        }

        public override int GetOrder()
        {
            throw new NotImplementedException();
        }
    }
}
