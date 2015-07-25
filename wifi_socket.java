package com.wyf.app.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class WifiManageUtils
{
    private static WifiManager wifiManager;
    private static WifiInfo wifiInfo;
    private static List<ScanResult> wifiScanlist;
    private static List<WifiConfiguration> wifiConfigurationlist;
    private static DhcpInfo wifiDhcpInfo;

    public WifiManageUtils(Context context)
    {
    // 取得WifiManager对象
    wifiManager = (WifiManager) context
        .getSystemService(Context.WIFI_SERVICE);
    }

    public WifiInfo getWifiConnectInfo()
    {
    wifiInfo = wifiManager.getConnectionInfo();
    return wifiInfo;
    }

    public List<ScanResult> getScanResult()
    {
    wifiScanlist = wifiManager.getScanResults();
    return wifiScanlist;
    }

    public List<WifiConfiguration> getConfiguration()
    {
    wifiConfigurationlist = wifiManager.getConfiguredNetworks();
    return wifiConfigurationlist;
    }

    public DhcpInfo getDhcpInfo()
    {
    wifiDhcpInfo = wifiManager.getDhcpInfo();
    return wifiDhcpInfo;
    }

    /**
     * 开启热点作为服务端的配置
     * 
     * @param ssid
     * @param passwd
     * @param type
     * @return
     */
    public WifiConfiguration getCustomeWifiConfiguration(String ssid,
        String passwd, int type)
    {
    WifiConfiguration config = new WifiConfiguration();
    config.allowedAuthAlgorithms.clear();
    config.allowedGroupCiphers.clear();
    config.allowedKeyManagement.clear();
    config.allowedPairwiseCiphers.clear();
    config.allowedProtocols.clear();
    config.SSID = ssid;
    if (type == 1) // NOPASS
    {
        config.wepKeys[0] = "";
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        config.wepTxKeyIndex = 0;
    }
    if (type == 2) // WEP
    {
        config.hiddenSSID = true;
        config.wepKeys[0] = passwd;
        config.allowedAuthAlgorithms
            .set(WifiConfiguration.AuthAlgorithm.SHARED);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
        config.allowedGroupCiphers
            .set(WifiConfiguration.GroupCipher.WEP104);
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        config.wepTxKeyIndex = 0;
    }
    if (type == 3) // WPA
    {
        config.preSharedKey = passwd;
        config.hiddenSSID = true;
        config.allowedAuthAlgorithms
            .set(WifiConfiguration.AuthAlgorithm.OPEN);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        config.allowedPairwiseCiphers
            .set(WifiConfiguration.PairwiseCipher.TKIP);
        // config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        config.allowedPairwiseCiphers
            .set(WifiConfiguration.PairwiseCipher.CCMP);
        config.status = WifiConfiguration.Status.ENABLED;
    }
    if (type == 4) // WPA2psk test
    {
        config.preSharedKey = passwd;
        config.hiddenSSID = true;

        config.status = WifiConfiguration.Status.ENABLED;
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        config.allowedPairwiseCiphers
            .set(WifiConfiguration.PairwiseCipher.TKIP);
        config.allowedPairwiseCiphers
            .set(WifiConfiguration.PairwiseCipher.CCMP);
        config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);

    }
    return config;

    }

    /**
     * 客户端添加配置，作为连接认证配置
     * ssid、passwd 配置前后必须加上双引号“
     * @param ssid
     * @param passwd
     * @param type
     * @return
     */
    public WifiConfiguration getCustomeWifiClientConfiguration(String ssid,
        String passwd, int type)
    {
    WifiConfiguration config = new WifiConfiguration();
    config.allowedAuthAlgorithms.clear();
    config.allowedGroupCiphers.clear();
    config.allowedKeyManagement.clear();
    config.allowedPairwiseCiphers.clear();
    config.allowedProtocols.clear();
    //双引号必须
    config.SSID = "\"" + ssid + "\"";
    if (type == 1) // WIFICIPHER_NOPASS
    {
        config.wepKeys[0] = "";
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        config.wepTxKeyIndex = 0;
    }
    if (type == 2) // WIFICIPHER_WEP
    {
        config.hiddenSSID = true;
         config.wepKeys[0] = "\"" + passwd + "\"";
        config.allowedAuthAlgorithms
            .set(WifiConfiguration.AuthAlgorithm.SHARED);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
        config.allowedGroupCiphers
            .set(WifiConfiguration.GroupCipher.WEP104);
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        config.wepTxKeyIndex = 0;
    }
    if (type == 3) // WIFICIPHER_WPA
    {
        config.preSharedKey = "\"" + passwd + "\"";
        config.hiddenSSID = true;
        config.allowedAuthAlgorithms
            .set(WifiConfiguration.AuthAlgorithm.OPEN);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        config.allowedPairwiseCiphers
            .set(WifiConfiguration.PairwiseCipher.TKIP);
        // config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        config.allowedPairwiseCiphers
            .set(WifiConfiguration.PairwiseCipher.CCMP);
        config.status = WifiConfiguration.Status.ENABLED;
    }
    if (type == 4) // WPA2psk test
    {
        config.preSharedKey = "\"" + passwd + "\"";
        config.hiddenSSID = true;

        config.status = WifiConfiguration.Status.ENABLED;
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        config.allowedPairwiseCiphers
            .set(WifiConfiguration.PairwiseCipher.TKIP);
        config.allowedPairwiseCiphers
            .set(WifiConfiguration.PairwiseCipher.CCMP);
        config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);

    }
    return config;

    }

    public Boolean stratWifiAp(String ssid, String psd, int type)
    {
    Method method1 = null;
    try
    {
        method1 = wifiManager.getClass().getMethod("setWifiApEnabled",
            WifiConfiguration.class, boolean.class);
        WifiConfiguration netConfig = getCustomeWifiConfiguration(ssid,
            psd, type);

        method1.invoke(wifiManager, netConfig, true);
        return true;
    }
    catch (Exception e)
    {
        e.printStackTrace();
        return false;
    }
    }

    public void closeWifiAp()
    {
    if (isWifiApEnabled())
    {
        try
        {
        Method method = wifiManager.getClass().getMethod(
            "getWifiApConfiguration");
        method.setAccessible(true);

        WifiConfiguration config = (WifiConfiguration) method
            .invoke(wifiManager);

        Method method2 = wifiManager.getClass().getMethod(
            "setWifiApEnabled", WifiConfiguration.class,
            boolean.class);
        method2.invoke(wifiManager, config, false);
        }
        catch (NoSuchMethodException e)
        {
        e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
        e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
        e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
        e.printStackTrace();
        }
    }
    }

    public boolean isWifiApEnabled()
    {
    try
    {
        Method method = wifiManager.getClass().getMethod("isWifiApEnabled");
        method.setAccessible(true);
        return (Boolean) method.invoke(wifiManager);

    }
    catch (NoSuchMethodException e)
    {
        e.printStackTrace();
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }

    return false;
    }
}

开启热点服务端部分代码

    public void startWifiHot()
    {
    btnServer.setEnabled(false);
    if (wifiManager.isWifiEnabled())
    {
        wifiManager.setWifiEnabled(false);
    }

    Boolean b = wifimanageutils.stratWifiAp(mSSID, mPasswd,3);
    if (b)
    {
        serverThread = new WifiServerThread(context, testh);
        Toast.makeText(context, "server 端启动", 3000).show();
        serverThread.start();
    }
    else
    {
        btnServer.setEnabled(true);
        Toast.makeText(context, "server 端失败，请重试", 3000).show();
    }
    }

WifiServerThread服务端线程

public class WifiServerThread extends Thread
{
    public ServerSocket mserverSocket;
    public Socket socket;
    public Context context;
    public static final int SERVERPORT = 8191;
    public Boolean isrun = true;
    public Handler handler;

    public WifiServerThread(Context context, Handler handler)
    {
    this.context = context;
    this.handler = handler;
    }

    public void run()
    {
    try
    {
        mserverSocket = new ServerSocket(SERVERPORT);
        while (isrun)
        {
        socket = mserverSocket.accept();
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
            byte[] buffer = new byte[1024];
            int bytes;
            InputStream mmInStream = null;

            try
            {
                mmInStream = socket.getInputStream();
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
            System.out.println("server");

            try
            {
                InputStream in = socket.getInputStream();
                OutputStream os = socket.getOutputStream();

                byte[] data = new byte[1024];
                while (in.available() <= 0)
                ;// 同步

                int len = in.read(data);

                String[] str = new String(data, 0, len, "utf-8")
                    .split(";");

                String path = Environment
                    .getExternalStorageDirectory()
                    .getAbsolutePath()
                    + "/CHFS/000000000000" + "/";
                if (len != -1)
                {
                path += "socket_" + str[0];// str[0]是文件名加类型
                }
                handler.obtainMessage(10, (Object) str[0])
                    .sendToTarget();

                System.out.println(path);

                os.write("start".getBytes());

                os.flush();

                File file = new File(path);

                DataOutputStream out = new DataOutputStream(
                    new FileOutputStream(file));

                System.out.println("开始接收.....");
                int countSize = 0;

                while ((len = in.read(data)) != -1)
                {
                out.write(data, 0, len);
                countSize += len;
                }

                os.close();
                out.flush();
                out.close();
                in.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                socket.close();
                System.out.println("关闭....");
                }
                catch (Exception e)
                {
                e.printStackTrace();
                }
                handler.obtainMessage(10, (Object) "接受 完成")
                    .sendToTarget();
            }

            }
        }).start();
        }
        if (mserverSocket != null)
        {
        try
        {
            mserverSocket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        }

    }
    catch (Exception e)
    {
        e.printStackTrace();
    }

    }
}

开启客户端部分代码

btnClient.setOnClickListener(new OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
        WifiConfiguration netConfig = wifimanageutils
            .getCustomeWifiClientConfiguration(mSSID, mPasswd,3);

        int wcgID = wifiManager.addNetwork(netConfig);
        boolean b = wifiManager.enableNetwork(wcgID, true);
        
        Boolean iptoready = false;
        if (!b)
        {
            Toast.makeText(context, "wifi 连接配置不可用", 3000).show();
            return;
        }
        while (!iptoready)
        {
            try
            {
            // 为了避免程序一直while循环，让它睡个100毫秒在检测……
            Thread.currentThread();
            Thread.sleep(100);
            }
            catch (InterruptedException ie)
            {
            }

            DhcpInfo dhcp = new WifiManageUtils(context).getDhcpInfo();
            int ipInt = dhcp.gateway;
            if (ipInt != 0)
            {
            iptoready = true;
            }
        }
        wifiLock.acquire();
        clientThread = new WifiClientThread(context);
        clientThread.start();
        }
    });

WifiClientThread客户端线程

public class WifiClientThread extends Thread
{
    public Socket socket;
    public Context context;
    public Boolean isrun = true;
    public static final int SERVERPORT = 8191;

    public OutputStream os;
    public InputStream in;

    public WifiClientThread(Context context)
    {
    this.context = context;
    }

    public void run()
    {
    try
    {
        DhcpInfo dhcp = new WifiManageUtils(context).getDhcpInfo();
        int ipInt = dhcp.gateway;
        String serverip = String.valueOf(new StringBuilder()
            .append((ipInt & 0xff)).append('.').append((ipInt >> 8) & 0xff)
            .append('.').append((ipInt >> 16) & 0xff).append('.')
            .append(((ipInt >> 24) & 0xff)).toString()

        );
        socket = new Socket(serverip, SERVERPORT);

        new Thread(new Runnable()
        {

        @Override
        public void run()
        {
            if (socket == null)
            {
            return;
            }
            System.out.println("client connect");

            try
            {
            String path = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/CHFS/000000000000";
            if (android.os.Build.MODEL.contains("8812"))
            {
                path += "/camera/" + "camera_temp_name.jpg";
            }
            else
            {
//                path += "/camera/" + "camera_temp_name.mp4";
                 path+="/ARChon-v1.1-x86_64.zip";
            }
            DataInputStream read = new DataInputStream(
                new FileInputStream(new File(path)));
            System.out.println(read.available());
            String fileName = path.substring(path.lastIndexOf("/") + 1);// 获得文件名加类型

            System.out.println(fileName);

            os = socket.getOutputStream();
            in = socket.getInputStream();
            os.write((fileName + ";" + read.available())
                .getBytes("utf-8"));// 将文件名和文件大小传给接收端
            os.flush();
            byte[] data = new byte[1024];

            int len = in.read(data);

            String start = new String(data, 0, len);

            int sendCountLen = 0;

            if (start.equals("start"))
            {

                while ((len = read.read(data)) != -1)
                {
                os.write(data, 0, len);
                sendCountLen += len;
                }
                os.flush();
                os.close();
                read.close();
            }

            }
            catch (Exception e)
            {

            e.printStackTrace();
            }
            finally
            {

            try
            {
                socket.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            }

        }
        }).start();
    }
    catch (IOException e)
    // catch (Exception e)
    {
        e.printStackTrace();
    }
    }

}





















返回主页	
zhuawang's blog

淡泊以明志,宁静以致远

    博客园
    闪存
    首页
    新随笔
    联系
    管理
    订阅
    订阅

随笔- 637  文章- 0  评论- 119 
控制WIFI状态

1.控制WIFI
复制代码

public class MainActivity extends Activity {
    private Button startButton = null;
    private Button stopButton = null;
    private Button checkButton = null;
    private WifiManager wifiManager = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        startButton = (Button)findViewById(R.id.startWifi);
        stopButton = (Button)findViewById(R.id.stopWifi);
        checkButton = (Button)findViewById(R.id.checkWifi);
        startButton.setOnClickListener(new StartWifiListener());
        stopButton.setOnClickListener(new StopWifiListener());
        checkButton.setOnClickListener(new CheckWifiListener());
        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }*/
    }
    class StartWifiListener implements OnClickListener{

        @Override
        public void onClick(View v) {
            wifiManager = (WifiManager)MainActivity.this.getSystemService(Context.WIFI_SERVICE);
            wifiManager.setWifiEnabled(true);
            System.out.println("wifi state --->" + wifiManager.getWifiState());
            Toast.makeText(MainActivity.this, "当前Wifi网卡状态为" + wifiManager.getWifiState(), Toast.LENGTH_SHORT).show();
        }
    }
    class StopWifiListener implements OnClickListener{

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            wifiManager = (WifiManager)MainActivity.this.getSystemService(Context.WIFI_SERVICE);
            wifiManager.setWifiEnabled(false);
            System.out.println("wifi state --->" + wifiManager.getWifiState());
            Toast.makeText(MainActivity.this, "当前Wifi网卡状态为" + wifiManager.getWifiState(), Toast.LENGTH_SHORT).show();
        }
    
    }
    
    class CheckWifiListener implements OnClickListener{

        @Override
        public void onClick(View v) {
            wifiManager = (WifiManager)MainActivity.this.getSystemService(Context.WIFI_SERVICE);
            System.out.println("wifi state --->" + wifiManager.getWifiState());
            Toast.makeText(MainActivity.this, "当前Wifi网卡状态为" + wifiManager.getWifiState(), Toast.LENGTH_SHORT).show();
        }
        
    }

复制代码

 

2.加入权限
复制代码

<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.mars_1900_wifi"
    android:versionCode="1"
    android:versionName="1.0" >

    <uses-sdk
        android:minSdkVersion="8"
        android:targetSdkVersion="19" />

    <application
        android:allowBackup="true"
        android:icon="@drawable/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/AppTheme" >
        <activity
            android:name="com.example.mars_1900_wifi.MainActivity"
            android:label="@string/app_name" >
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
    <!-- 以下是使用wifi访问网络所需要的权限 -->
    <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE"></uses-permission>
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"></uses-permission>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
    
</manifest>

复制代码

 
分类: Android
绿色通道： 好文要顶 关注我 收藏该文与我联系
上校
关注 - 2
粉丝 - 93
+加关注
0
0
(请您对文章做出评价)
« 上一篇：Android之BroadcastReceiver 监听系统广播
» 下一篇：android-Service和Thread的区别
posted @ 2014-04-07 15:19 上校 阅读(234) 评论(0) 编辑 收藏
刷新评论刷新页面返回顶部
注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。
【推荐】50万行VC++源码: 大型组态工控、电力仿真CAD与GIS源码库
【热门】支持Visual Studio2015的葡萄城控件新品发布暨夏季促销
最新IT新闻:
· 神呐！刚刚获得世界法语拼字大赛冠军的人，竟然一句法语都不会说
· Windows PC地位不保，iOS 设备销量也超越了Windows PC
· 世纪佳缘执行副总裁张亚红：与百合网合并是传说
· 李开复：共享经济即将崛起，将出现更多个体创业者
· 张涛：成立丽人事业部，大众点评O2O将进入2.0时代
» 更多新闻...
最新知识库文章:
· RESTful架构详解
· 优秀程序员眼中的整洁代码
· 怎样看待比自己强的人
· 编程王道，唯“慢”不破
· 程序员要有持续产出
» 更多知识库文章...
历史上的今天:
2007-04-07 .NET 生成高质量缩略图的通用函数代码 (修改版)
2007-04-07 C#利用正则表达式实现字符串搜索
2007-04-07 js校验文本框中只能输入0~9的数字
2007-04-07 在线编辑器FreeTextBox的使用
2007-04-07 两个DropDownList的客户端切换
2007-04-07 上传图片过程中给父窗口赋值
2007-04-07 Page.ClientScript.RegisterStartupScript 与 Page.ClientScript.RegisterClientScriptBlock 之间的区别
2007-04-07 ASP.NET 安全认证（一）——如何运用 Form 表单认证
2007-04-07 ASP.NET 安全认证（二）——灵活运用deny与allow 及保护.htm等文件
2007-04-07 XP系统下数据库文件夹的权限设置
2007-04-07 数据绑定时的转换问题
2007-04-07 确认删除
2007-04-07 具有身份验证的web.config
2007-04-07 回车提交表单
2007-04-07 OleDbParameter参数的使用
2007-04-07 MD5加密
2007-04-07 DES加解密
2007-04-07 asp.net生成验证码
2007-04-07 把CS文件编译成dll文件
2007-04-07 .net 下URL重写
2007-04-07 IT行业打拼7年，现35岁了很迷茫(转自csdn 一个IT人的感言)
2007-04-07 在b/s开发中经常用到的javaScript技术整理
2007-04-07 ASP.NET 2.0中的页面输出缓存
2007-04-07 35岁前成功的12条黄金法则
2007-04-07 FLASH广告轮播器
2007-04-07 回发或回调参数无效。
2007-04-07 如何点击服务器Button按钮后变为不可点
2007-04-07 常用正则表达式
2007-04-07 Regularexpressionvalidator控件常用正则表达式
2007-04-07 输出js及时刷新页面
2007-04-07 access和SQL的区别
2007-04-07 ajax实现DropDownList 联动
2007-04-07 对“三层结构”的深入理解——怎样才算是一个符合“三层结构”的Web应用程序？
2007-04-07 .Net简单三层
2007-04-07 ASP.NET 2.0 中(theme)介绍
2007-04-07 ASP.NET生成静态页面实现方法
2007-04-07 Asp.net 2.0 中将网站首页生成静态页的一个比较好的方法
2007-04-07 生成静态文件的新闻系统核心代码
2007-04-07 文件下载
昵称：上校
园龄：8年3个月
粉丝：93
关注：2
+加关注
<	2015年7月	>
日	一	二	三	四	五	六
28	29	30	1	2	3	4
5	6	7	8	9	10	11
12	13	14	15	16	17	18
19	20	21	22	23	24	25
26	27	28	29	30	31	1
2	3	4	5	6	7	8
搜索
 
 
常用链接

    我的随笔
    我的评论
    我的参与
    最新评论
    我的标签
    更多链接

随笔分类

    Ajax(23)
    Android(27)
    ASP.NET(136)
    ASP.NET MVC(12)
    C#(110)
    CSS(2)
    Financial
    Flash(1)
    Java(127)
    JavaScript(41)
    Java并发(33)
    Java网络编程(4)
    Linux(8)
    MySql(10)
    Remoting
    Silverlight(2)
    WCF(4)
    WebService(6)
    WPF
    大数据(1)
    控件开发(1)
    其它(21)
    设计模式(35)
    数据库(89)
    网络安全(4)
    网络编程(2)

随笔档案

    2015年7月 (1)
    2015年6月 (5)
    2015年5月 (2)
    2015年3月 (2)
    2015年1月 (10)
    2014年12月 (12)
    2014年11月 (8)
    2014年10月 (11)
    2014年9月 (12)
    2014年8月 (10)
    2014年7月 (3)
    2014年6月 (13)
    2014年5月 (38)
    2014年4月 (27)
    2014年3月 (5)
    2014年2月 (4)
    2014年1月 (2)
    2013年12月 (2)
    2013年11月 (15)
    2013年10月 (15)
    2013年9月 (7)
    2013年8月 (3)
    2013年7月 (1)
    2013年6月 (2)
    2013年5月 (9)
    2013年4月 (1)
    2013年2月 (2)
    2013年1月 (6)
    2012年12月 (8)
    2012年11月 (1)
    2012年10月 (1)
    2012年9月 (3)
    2012年8月 (3)
    2012年7月 (7)
    2012年5月 (2)
    2012年4月 (2)
    2012年3月 (5)
    2012年2月 (6)
    2012年1月 (1)
    2011年12月 (5)
    2011年11月 (11)
    2011年10月 (8)
    2011年9月 (34)
    2011年8月 (21)
    2011年7月 (17)
    2011年6月 (16)
    2011年5月 (18)
    2011年4月 (24)
    2011年3月 (3)
    2010年10月 (2)
    2010年8月 (1)
    2010年7月 (1)
    2010年4月 (1)
    2010年3月 (1)
    2010年1月 (1)
    2009年7月 (1)
    2009年6月 (2)
    2009年5月 (1)
    2008年9月 (1)
    2008年8月 (16)
    2008年7月 (25)
    2008年6月 (6)
    2008年5月 (7)
    2008年4月 (14)
    2007年10月 (1)
    2007年9月 (11)
    2007年8月 (3)
    2007年7月 (18)
    2007年6月 (21)
    2007年5月 (17)
    2007年4月 (62)

相册

    123(1)

最新评论

    1. Re:java发送http的get、post请求
    发送请求后，收到的String里面的汉字是乱码，这个怎么处理
    --穷苦书生
    2. Re:js跨域调用WebService
    测过了，不行
    --张大哈哥哥
    3. Re:js跨域调用WebService
    通过web.config配置能实现跨域？？？
    --张大哈哥哥
    4. Re:js跨域调用WebService
    亲测，不成功！~
    --cocomilk
    5. Re:java发送http的get、post请求
    mark
    --fromk7

阅读排行榜

    1. java发送http的get、post请求(90283)
    2. jQuery.parseJSON(json)方法将字符串转换成js对象(37772)
    3. 八款Js框架介绍及比较(20448)
    4. Html.DropDownList()的用法(17679)
    5. Page.ClientScript.RegisterStartupScript() 方法与Page.ClientScript.RegisterClientScriptBlock() 方法 (13457)

评论排行榜

    1. JS日期和时间选择控件(15)
    2. ajaxPro.dll基础教程(11)
    3. FLASH广告轮播器(10)
    4. 八款Js框架介绍及比较(8)
    5. java发送http的get、post请求(6)

推荐排行榜

    1. java发送http的get、post请求(11)
    2. Html.DropDownList()的用法(3)
    3. JS日期和时间选择控件(2)
    4. 临时表vs.表变量以及它们对SQLServer性能的影响(2)
    5. 八款Js框架介绍及比较(2)

Copyright ©2015 上校




返回主页	
zhuawang's blog

淡泊以明志,宁静以致远

    博客园
    闪存
    首页
    新随笔
    联系
    管理
    订阅
    订阅

随笔- 637  文章- 0  评论- 119 
控制WIFI状态

1.控制WIFI
复制代码

public class MainActivity extends Activity {
    private Button startButton = null;
    private Button stopButton = null;
    private Button checkButton = null;
    private WifiManager wifiManager = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        startButton = (Button)findViewById(R.id.startWifi);
        stopButton = (Button)findViewById(R.id.stopWifi);
        checkButton = (Button)findViewById(R.id.checkWifi);
        startButton.setOnClickListener(new StartWifiListener());
        stopButton.setOnClickListener(new StopWifiListener());
        checkButton.setOnClickListener(new CheckWifiListener());
        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }*/
    }
    class StartWifiListener implements OnClickListener{

        @Override
        public void onClick(View v) {
            wifiManager = (WifiManager)MainActivity.this.getSystemService(Context.WIFI_SERVICE);
            wifiManager.setWifiEnabled(true);
            System.out.println("wifi state --->" + wifiManager.getWifiState());
            Toast.makeText(MainActivity.this, "当前Wifi网卡状态为" + wifiManager.getWifiState(), Toast.LENGTH_SHORT).show();
        }
    }
    class StopWifiListener implements OnClickListener{

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            wifiManager = (WifiManager)MainActivity.this.getSystemService(Context.WIFI_SERVICE);
            wifiManager.setWifiEnabled(false);
            System.out.println("wifi state --->" + wifiManager.getWifiState());
            Toast.makeText(MainActivity.this, "当前Wifi网卡状态为" + wifiManager.getWifiState(), Toast.LENGTH_SHORT).show();
        }
    
    }
    
    class CheckWifiListener implements OnClickListener{

        @Override
        public void onClick(View v) {
            wifiManager = (WifiManager)MainActivity.this.getSystemService(Context.WIFI_SERVICE);
            System.out.println("wifi state --->" + wifiManager.getWifiState());
            Toast.makeText(MainActivity.this, "当前Wifi网卡状态为" + wifiManager.getWifiState(), Toast.LENGTH_SHORT).show();
        }
        
    }

复制代码

 

2.加入权限
复制代码

<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.mars_1900_wifi"
    android:versionCode="1"
    android:versionName="1.0" >

    <uses-sdk
        android:minSdkVersion="8"
        android:targetSdkVersion="19" />

    <application
        android:allowBackup="true"
        android:icon="@drawable/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/AppTheme" >
        <activity
            android:name="com.example.mars_1900_wifi.MainActivity"
            android:label="@string/app_name" >
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
    <!-- 以下是使用wifi访问网络所需要的权限 -->
    <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE"></uses-permission>
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"></uses-permission>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
    
</manifest>

复制代码

 
分类: Android
绿色通道： 好文要顶 关注我 收藏该文与我联系
上校
关注 - 2
粉丝 - 93
+加关注
0
0
(请您对文章做出评价)
« 上一篇：Android之BroadcastReceiver 监听系统广播
» 下一篇：android-Service和Thread的区别
posted @ 2014-04-07 15:19 上校 阅读(234) 评论(0) 编辑 收藏
刷新评论刷新页面返回顶部
注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。
【推荐】50万行VC++源码: 大型组态工控、电力仿真CAD与GIS源码库
【热门】支持Visual Studio2015的葡萄城控件新品发布暨夏季促销
最新IT新闻:
· 神呐！刚刚获得世界法语拼字大赛冠军的人，竟然一句法语都不会说
· Windows PC地位不保，iOS 设备销量也超越了Windows PC
· 世纪佳缘执行副总裁张亚红：与百合网合并是传说
· 李开复：共享经济即将崛起，将出现更多个体创业者
· 张涛：成立丽人事业部，大众点评O2O将进入2.0时代
» 更多新闻...
最新知识库文章:
· RESTful架构详解
· 优秀程序员眼中的整洁代码
· 怎样看待比自己强的人
· 编程王道，唯“慢”不破
· 程序员要有持续产出
» 更多知识库文章...
历史上的今天:
2007-04-07 .NET 生成高质量缩略图的通用函数代码 (修改版)
2007-04-07 C#利用正则表达式实现字符串搜索
2007-04-07 js校验文本框中只能输入0~9的数字
2007-04-07 在线编辑器FreeTextBox的使用
2007-04-07 两个DropDownList的客户端切换
2007-04-07 上传图片过程中给父窗口赋值
2007-04-07 Page.ClientScript.RegisterStartupScript 与 Page.ClientScript.RegisterClientScriptBlock 之间的区别
2007-04-07 ASP.NET 安全认证（一）——如何运用 Form 表单认证
2007-04-07 ASP.NET 安全认证（二）——灵活运用deny与allow 及保护.htm等文件
2007-04-07 XP系统下数据库文件夹的权限设置
2007-04-07 数据绑定时的转换问题
2007-04-07 确认删除
2007-04-07 具有身份验证的web.config
2007-04-07 回车提交表单
2007-04-07 OleDbParameter参数的使用
2007-04-07 MD5加密
2007-04-07 DES加解密
2007-04-07 asp.net生成验证码
2007-04-07 把CS文件编译成dll文件
2007-04-07 .net 下URL重写
2007-04-07 IT行业打拼7年，现35岁了很迷茫(转自csdn 一个IT人的感言)
2007-04-07 在b/s开发中经常用到的javaScript技术整理
2007-04-07 ASP.NET 2.0中的页面输出缓存
2007-04-07 35岁前成功的12条黄金法则
2007-04-07 FLASH广告轮播器
2007-04-07 回发或回调参数无效。
2007-04-07 如何点击服务器Button按钮后变为不可点
2007-04-07 常用正则表达式
2007-04-07 Regularexpressionvalidator控件常用正则表达式
2007-04-07 输出js及时刷新页面
2007-04-07 access和SQL的区别
2007-04-07 ajax实现DropDownList 联动
2007-04-07 对“三层结构”的深入理解——怎样才算是一个符合“三层结构”的Web应用程序？
2007-04-07 .Net简单三层
2007-04-07 ASP.NET 2.0 中(theme)介绍
2007-04-07 ASP.NET生成静态页面实现方法
2007-04-07 Asp.net 2.0 中将网站首页生成静态页的一个比较好的方法
2007-04-07 生成静态文件的新闻系统核心代码
2007-04-07 文件下载
昵称：上校
园龄：8年3个月
粉丝：93
关注：2
+加关注
<	2015年7月	>
日	一	二	三	四	五	六
28	29	30	1	2	3	4
5	6	7	8	9	10	11
12	13	14	15	16	17	18
19	20	21	22	23	24	25
26	27	28	29	30	31	1
2	3	4	5	6	7	8
搜索
 
 
常用链接

    我的随笔
    我的评论
    我的参与
    最新评论
    我的标签
    更多链接

随笔分类

    Ajax(23)
    Android(27)
    ASP.NET(136)
    ASP.NET MVC(12)
    C#(110)
    CSS(2)
    Financial
    Flash(1)
    Java(127)
    JavaScript(41)
    Java并发(33)
    Java网络编程(4)
    Linux(8)
    MySql(10)
    Remoting
    Silverlight(2)
    WCF(4)
    WebService(6)
    WPF
    大数据(1)
    控件开发(1)
    其它(21)
    设计模式(35)
    数据库(89)
    网络安全(4)
    网络编程(2)

随笔档案

    2015年7月 (1)
    2015年6月 (5)
    2015年5月 (2)
    2015年3月 (2)
    2015年1月 (10)
    2014年12月 (12)
    2014年11月 (8)
    2014年10月 (11)
    2014年9月 (12)
    2014年8月 (10)
    2014年7月 (3)
    2014年6月 (13)
    2014年5月 (38)
    2014年4月 (27)
    2014年3月 (5)
    2014年2月 (4)
    2014年1月 (2)
    2013年12月 (2)
    2013年11月 (15)
    2013年10月 (15)
    2013年9月 (7)
    2013年8月 (3)
    2013年7月 (1)
    2013年6月 (2)
    2013年5月 (9)
    2013年4月 (1)
    2013年2月 (2)
    2013年1月 (6)
    2012年12月 (8)
    2012年11月 (1)
    2012年10月 (1)
    2012年9月 (3)
    2012年8月 (3)
    2012年7月 (7)
    2012年5月 (2)
    2012年4月 (2)
    2012年3月 (5)
    2012年2月 (6)
    2012年1月 (1)
    2011年12月 (5)
    2011年11月 (11)
    2011年10月 (8)
    2011年9月 (34)
    2011年8月 (21)
    2011年7月 (17)
    2011年6月 (16)
    2011年5月 (18)
    2011年4月 (24)
    2011年3月 (3)
    2010年10月 (2)
    2010年8月 (1)
    2010年7月 (1)
    2010年4月 (1)
    2010年3月 (1)
    2010年1月 (1)
    2009年7月 (1)
    2009年6月 (2)
    2009年5月 (1)
    2008年9月 (1)
    2008年8月 (16)
    2008年7月 (25)
    2008年6月 (6)
    2008年5月 (7)
    2008年4月 (14)
    2007年10月 (1)
    2007年9月 (11)
    2007年8月 (3)
    2007年7月 (18)
    2007年6月 (21)
    2007年5月 (17)
    2007年4月 (62)

相册

    123(1)

最新评论

    1. Re:java发送http的get、post请求
    发送请求后，收到的String里面的汉字是乱码，这个怎么处理
    --穷苦书生
    2. Re:js跨域调用WebService
    测过了，不行
    --张大哈哥哥
    3. Re:js跨域调用WebService
    通过web.config配置能实现跨域？？？
    --张大哈哥哥
    4. Re:js跨域调用WebService
    亲测，不成功！~
    --cocomilk
    5. Re:java发送http的get、post请求
    mark
    --fromk7

阅读排行榜

    1. java发送http的get、post请求(90283)
    2. jQuery.parseJSON(json)方法将字符串转换成js对象(37772)
    3. 八款Js框架介绍及比较(20448)
    4. Html.DropDownList()的用法(17679)
    5. Page.ClientScript.RegisterStartupScript() 方法与Page.ClientScript.RegisterClientScriptBlock() 方法 (13457)

评论排行榜

    1. JS日期和时间选择控件(15)
    2. ajaxPro.dll基础教程(11)
    3. FLASH广告轮播器(10)
    4. 八款Js框架介绍及比较(8)
    5. java发送http的get、post请求(6)

推荐排行榜

    1. java发送http的get、post请求(11)
    2. Html.DropDownList()的用法(3)
    3. JS日期和时间选择控件(2)
    4. 临时表vs.表变量以及它们对SQLServer性能的影响(2)
    5. 八款Js框架介绍及比较(2)

Copyright ©2015 上校





1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
42
43
44
45
46
47
48
49
50
51
52
53
54
55
56
57
58
59
60
61
62
63
64
65
66
67
68
	
package com.wifi;
 
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
 
import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
public class MainActivity extends Activity {
    public static String IP;             //本机IP
    public static String MAC;            //本机MAC
     
    Button btn=null;
    EditText ip_txt=null;
    EditText mac_txt=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btn=(Button)findViewById(R.id.btn);
        ip_txt=(EditText)findViewById(R.id.ip_txt);
        mac_txt=(EditText)findViewById(R.id.mac_txt);
         
    }
    public void onclick(View v){
        switch (v.getId()) {
        case R.id.btn:
            IP = getLocalIpAddress();  //获取本机IP
            MAC = getLocalMacAddress();//获取本机MAC
            ip_txt.setText(IP);
            mac_txt.setText(MAC);
            break;
        }
    }
     
    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("WifiPreference IpAddress", ex.toString());
        }
        return null;
    }
     
    public String getLocalMacAddress() {
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }
}








收藏本站
Android开发
移动开发SDK
Android中文文档
Android design
Android开发手册
ios开发
CADF
登录 注册

    首页
    板块列表
    安客学院
    代码源码
    技术博客
    巴友巴单
    巴友Q群
    精华专题
    活动
    论坛
    开发者服务
    下载专区

版块
|
源码
|
教学视频
| 开发专区›综合讨论›android wifi基本操作代码
android wifi基本操作代码
medium avatar
陈小二
发表于 2014-2-27 09:15:52 浏览（28658）
转载： http://blog.csdn.net/miueugene/article/details/19986201
判断当前网络是否可用[java] view plaincopyprint?


    //判断当前网络是否可用  
            public boolean note_Intent(Context context) {   
                ConnectivityManager con = (ConnectivityManager) context   
                    .getSystemService(Context.CONNECTIVITY_SERVICE);   
                NetworkInfo networkinfo = con.getActiveNetworkInfo();   
                if (networkinfo == null || !networkinfo.isAvailable()) {   
                // 当前网络不可用     
                    Toast.makeText(context.getApplicationContext(), "当前网络不可用",   
                    Toast.LENGTH_SHORT).show();   
                    return false;   
                }   
                boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI)   
                    .isConnectedOrConnecting();   
                if (!wifi) { // 提示使用wifi     
                    Toast.makeText(context.getApplicationContext(), "建议您使用WIFI以减少流量！",   
                    Toast.LENGTH_SHORT).show();   
                }   
                return true;   
                
            }  
      
      
    跳转到系统wifi设置接界面：  
    Intent localIntent = new Intent();  
                                  localIntent.setComponent( new ComponentName(  
                                               "com.android.settings",  
                                               "com.android.settings.wifi.WifiPickerActivity" ));  
                                  startActivityForResult(localIntent, 1);  






先获得可用对象  ：

[java] view plaincopyprint?


    wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);  
                wifiInfo = wifiManager.getConnectionInfo();  



之后你就可以通过这两个对象进行各种操作：

[java] view plaincopyprint?


    StringBuffer sb = new StringBuffer();  
                sb.append("Wifi信息\n");  
                sb.append("MAC地址：" + wifiInfo.getMacAddress() + "\n");  
                sb.append("接入点的BSSID：" + wifiInfo.getBSSID() + "\n");  
                sb.append("IP地址（int）：" + wifiInfo.getIpAddress() + "\n");  
                sb.append("IP地址（Hex）："  
                        + Integer.toHexString(wifiInfo.getIpAddress()) + "\n");  
                getBSSID = wifiInfo.getBSSID();  
                sb.append("IP地址：" + ipIntToString(wifiInfo.getIpAddress()) + "\n");  
                sb.append("网络ID：" + wifiInfo.getNetworkId() + "\n");  
                sb.append("网络名字：" + wifiInfo.getSSID() + "\n");  
                sb.append("网络的信号：" + wifiInfo.getRssi() + "\n");  
                sb.append("网络连接的速度：" + wifiInfo.getLinkSpeed() + "\n");  
                sb.append("客户端状态的信息：" + wifiInfo.getSupplicantState() + "\n");  
                sb.append("获取一个wifi 接入点是否有效：" + wifiManager.getWifiState() + "\n");  
                sb.append("判断一个wifi 连接是否有效：" + wifiManager.isWifiEnabled() + "\n");  
                sb.append("WiFi网络是否连接成功：" + isWifiConnect() + "\n");  



下面是自己写的一个简单类


[java] view plaincopyprint?


    package com.example.wifidemo;  
      
    import java.net.Inet4Address;  
      
    import android.app.Activity;  
    import android.app.AlertDialog;  
    import android.content.ComponentName;  
    import android.content.Context;  
    import android.content.DialogInterface;  
    import android.content.Intent;  
    import android.net.ConnectivityManager;  
    import android.net.NetworkInfo;  
    import android.net.wifi.WifiConfiguration;  
    import android.net.wifi.WifiInfo;  
    import android.net.wifi.WifiManager;  
    import android.net.wifi.WifiManager.WifiLock;  
    import android.os.AsyncTask;  
    import android.os.Bundle;  
    import android.view.View;  
    import android.view.View.OnClickListener;  
    import android.widget.Button;  
    import android.widget.CheckBox;  
    import android.widget.CompoundButton;  
    import android.widget.CompoundButton.OnCheckedChangeListener;  
    import android.widget.TextView;  
    import android.widget.Toast;  
      
    public class MainActivity extends Activity {  
        private WifiManager wifiManager;  
        private WifiInfo wifiInfo;  
        // 扫描出的网络连接列表  
        CheckBox checkbox;  
        TextView textview;  
        Button look;  
        WifiLock mWifiLock;// 锁住wifi表示不会断开连接  
        static String WifiBSSID = "38:22:d6:93:0e:a0";  
        String getBSSID = "";  
      
        @Override  
        protected void onCreate(Bundle savedInstanceState) {  
            super.onCreate(savedInstanceState);  
            setContentView(R.layout.activity_main);  
            checkbox = (CheckBox) findViewById(R.id.checkbox);  
            textview = (TextView) findViewById(R.id.textview);  
            look = (Button) findViewById(R.id.look);  
            new GetAsyncData().execute();  
            look.setOnClickListener(new OnClickListener() {  
                @Override  
                public void onClick(View v) {  
                    new GetAsyncData().execute();  
                    switch (checkState()) {  
                    case 0:  
                        break;  
                    case 1:  
                        Toast.makeText(getApplicationContext(),  
                                "wifi网卡不可用，先打开Wifi", Toast.LENGTH_SHORT).show();  
      
                        break;  
                    case 2:  
                        break;  
                    case 3:  
                        if (getBSSID.equals(WifiBSSID)) {  
                            Toast.makeText(getApplicationContext(), "wifi配置正确",  
                                    Toast.LENGTH_SHORT).show();  
                        } else {  
                            Toast.makeText(getApplicationContext(), "请连接到正确的网络",  
                                    Toast.LENGTH_SHORT).show();  
                            Intent localIntent = new Intent();  
                            localIntent  
                                    .setComponent(new ComponentName(  
                                            "com.android.settings",  
                                            "com.android.settings.wifi.WifiPickerActivity"));  
                            startActivityForResult(localIntent, 1);  
                        }  
                        break;  
                    case 4:  
                        Toast.makeText(getApplicationContext(), "未知网卡状态",  
                                Toast.LENGTH_SHORT).show();  
                        break;  
                    }  
                }  
            });  
            checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
      
                @Override  
                public void onCheckedChanged(CompoundButton buttonView,  
                        boolean isChecked) {  
                    if (isChecked) {  
                        wifiManager.setWifiEnabled(true);  
                        checkbox.setText("Wifi已开启");  
                    } else {  
                        wifiManager.setWifiEnabled(false);  
                        checkbox.setText("Wifi已关闭");  
                    }  
                }  
            });  
        }  
      
        @Override  
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
            if (requestCode == 1) {  
                new GetAsyncData().execute();  
                if (getBSSID.equals(WifiBSSID)) {  
                    Toast.makeText(getApplicationContext(), "wifi配置正确",  
                            Toast.LENGTH_SHORT).show();  
                } else {  
                    makeDialog();  
                }  
            }  
            super.onActivityResult(requestCode, resultCode, data);  
        }  
      
        private void makeDialog() {  
            new AlertDialog.Builder(MainActivity.this)  
                    .setTitle("提示")  
                    .setMessage("你还没有连接到正确的网络，是否要回到软件页面？")  
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {  
      
                        @Override  
                        public void onClick(DialogInterface dialog, int which) {  
                            Toast.makeText(getApplicationContext(), "没有连接到正确的网络",  
                                    Toast.LENGTH_SHORT).show();  
                        }  
                    })  
                    .setNegativeButton("继续配置",  
                            new DialogInterface.OnClickListener() {  
      
                                @Override  
                                public void onClick(DialogInterface dialog,  
                                        int which) {  
                                    Intent localIntent = new Intent();  
                                    localIntent  
                                            .setComponent(new ComponentName(  
                                                    "com.android.settings",  
                                                    "com.android.settings.wifi.WifiPickerActivity"));  
                                    startActivityForResult(localIntent, 1);  
                                }  
                            }).create().show();  
        }  
      
        // 将int类型的IP转换成字符串形式的IP  
        private String ipIntToString(int ip) {  
            try {  
                byte[] bytes = new byte[4];  
                bytes[0] = (byte) (0xff & ip);  
                bytes[1] = (byte) ((0xff00 & ip) >> 8);  
                bytes[2] = (byte) ((0xff0000 & ip) >> 16);  
                bytes[3] = (byte) ((0xff000000 & ip) >> 24);  
                return Inet4Address.getByAddress(bytes).getHostAddress();  
            } catch (Exception e) {  
                return "";  
            }  
        }  
      
        public int checkState() {  
            return wifiManager.getWifiState();  
        }  
      
        // 锁定wifiLock  
        public void acquireWifiLock() {  
            mWifiLock.acquire();  
        }  
      
        // 解锁wifiLock  
        public void releaseWifiLock() {  
            // 判断是否锁定  
            if (mWifiLock.isHeld()) {  
                mWifiLock.acquire();  
            }  
        }  
      
        // 添加一个网络并连接  
        public void addNetWork(WifiConfiguration configuration) {  
            int wcgId = wifiManager.addNetwork(configuration);  
            wifiManager.enableNetwork(wcgId, true);  
        }  
      
        // 断开指定ID的网络  
        public void disConnectionWifi(int netId) {  
            wifiManager.disableNetwork(netId);  
            wifiManager.disconnect();  
        }  
      
        public boolean isWifiConnect() {  
            ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);  
            NetworkInfo mWifi = connManager  
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);  
            return mWifi.isConnected();  
        }  
      
        class GetAsyncData extends AsyncTask<Void, Void, String> {  
      
            @Override  
            protected void onPostExecute(String result) {  
                if (wifiManager.isWifiEnabled()) {  
                    checkbox.setText("Wifi已开启");  
                    checkbox.setChecked(true);  
                } else {  
                    checkbox.setText("Wifi已关闭");  
                    checkbox.setChecked(false);  
                }  
                textview.setText(result);  
                super.onPostExecute(result);  
            }  
      
            @Override  
            protected String doInBackground(Void... params) {  
                wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);  
                wifiInfo = wifiManager.getConnectionInfo();  
                StringBuffer sb = new StringBuffer();  
                sb.append("Wifi信息\n");  
                sb.append("MAC地址：" + wifiInfo.getMacAddress() + "\n");  
                sb.append("接入点的BSSID：" + wifiInfo.getBSSID() + "\n");  
                sb.append("IP地址（int）：" + wifiInfo.getIpAddress() + "\n");  
                sb.append("IP地址（Hex）："  
                        + Integer.toHexString(wifiInfo.getIpAddress()) + "\n");  
                getBSSID = wifiInfo.getBSSID();  
                sb.append("IP地址：" + ipIntToString(wifiInfo.getIpAddress()) + "\n");  
                sb.append("网络ID：" + wifiInfo.getNetworkId() + "\n");  
                sb.append("网络名字：" + wifiInfo.getSSID() + "\n");  
                sb.append("网络的信号：" + wifiInfo.getRssi() + "\n");  
                sb.append("网络连接的速度：" + wifiInfo.getLinkSpeed() + "\n");  
                sb.append("客户端状态的信息：" + wifiInfo.getSupplicantState() + "\n");  
                sb.append("获取一个wifi 接入点是否有效：" + wifiManager.getWifiState() + "\n");  
                sb.append("判断一个wifi 连接是否有效：" + wifiManager.isWifiEnabled() + "\n");  
                sb.append("WiFi网络是否连接成功：" + isWifiConnect() + "\n");  
                return sb.toString();  
            }  
        }  
    }  




package test.soket;

//import com.test_button.R;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class test_socket extends Activity {
	public static TextView show;
	public static Button press;
	public static boolean flag;
    
    private static final int MAX_DATA_PACKET_LENGTH = 40;
    private byte[] buffer = new byte[MAX_DATA_PACKET_LENGTH];
    private DatagramPacket dataPacket;
    private DatagramSocket udpSocket;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //开辟控件空间
        show = (TextView)findViewById(R.id.editText1);
        press = (Button)findViewById(R.id.button1);
        flag = false;
        //soket_send thread = new soket_send();
        //thread.init();
        //thread.start();
        
        try
        {
        	udpSocket = new DatagramSocket(5554);
        }
        catch (SocketException e)
        {
        	// TODO Auto-generated catch block
        	e.printStackTrace();
        }
        dataPacket = new DatagramPacket(buffer, MAX_DATA_PACKET_LENGTH);
        String str = "hello,jdh";  //这是要传输的数据
		byte out [] = str.getBytes();  //把传输内容分解成字节
        dataPacket.setData(out);
        dataPacket.setLength(out.length);
        dataPacket.setPort(5554);
        try
        {
                
        	InetAddress broadcastAddr = InetAddress.getByName("192.168.0.248");
        	dataPacket.setAddress(broadcastAddr);
        	udpSocket.send(dataPacket);
        }
        catch (IOException e)
        {
        	// TODO Auto-generated catch block
        	e.printStackTrace();
        }
        
	       
        press.setOnClickListener(new Button.OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
        		flag = true;
        		/*
                String str = "hello,jdh";  //这是要传输的数据
        		byte out [] = str.getBytes();  //把传输内容分解成字节
                dataPacket.setData(out);
                dataPacket.setLength(out.length);
                */
                
                //获得输入框文本
        		CharSequence str =test_socket.show.getText();
                byte out[] = str.toString().getBytes();
                dataPacket.setData(out);
                dataPacket.setLength(out.length);
                try
                {
                        
                	InetAddress broadcastAddr = InetAddress.getByName("192.168.0.248");
                	dataPacket.setAddress(broadcastAddr);
                	udpSocket.send(dataPacket);
                }
                catch (IOException e)
                {
                	// TODO Auto-generated catch block
                	e.printStackTrace();
                }
        	}
        });
    }
}









server

 

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {

 public static void main(String[] args) throws IOException {
  
  DatagramSocket socket = null;
  try {
   socket = new DatagramSocket(4567);
  } catch (Exception e) {
   e.printStackTrace();
  }
  while(true){
   byte data [] = new byte[1024];
   DatagramPacket packet = new DatagramPacket(data,data.length);
   socket.receive(packet);
   String result = new String(packet.getData(),packet.getOffset(),packet.getLength());
   System.out.println("result--->" + result);
  }

 }
 
}

 

client

 

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class aaa extends Activity {
 private Button requestButton = null;
 DatagramSocket socket = null;
 InetAddress serverAddress = null;

 @Override
 public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.main);
  // 首先创建一个DatagramSocket对象
  try {
   socket = new DatagramSocket(4567);
   serverAddress = InetAddress.getByName("192.168.1.109");
  } catch (Exception e1) {
   e1.printStackTrace();
  }

  requestButton = (Button) findViewById(R.id.startRequest);// 作为客户端

  requestButton.setOnClickListener(new OnClickListener() {

   @Override
   public void onClick(View arg0) {
    try {
     System.out.println("request..");

     String str = "hello";
     byte data[] = str.getBytes();
     // 创建一个DatagramPacket对象，并指定要讲这个数据包发送到网络当中的哪个地址，以及端口号
     DatagramPacket packet = new DatagramPacket(data,
       data.length, serverAddress, 4567);
     // 调用socket对象的send方法，发送数据
     socket.send(packet);
    } catch (Exception e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
    }
   }

  });
 }

}


在android上，AndroidManifest.xml要加上这几句，否则不能访问网络：

<uses-permission android:name="android.permission.CHANGE_NETWORK_STATE"></uses-permission>
 <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"></uses-permission>
 <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
 <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
 <uses-permission android:name="android.permission.INTERNET"></uses-permission>


以上是基础，下面我们把它延伸一下，写一个双向通信。我们可以把这种双向通信的方式理解为两个服务器和两个客户端，即双方各带一个服务器和客户端。下面，还是以android+java+udp协议为例：

 


通讯甲(java)

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class MainFrame extends JFrame {

 private static final long serialVersionUID = -5280640205777786533L;
 DatagramSocket socket = null;
 InetAddress serverAddress = null;
 public MainFrame() {
  init();
 }

 public void init() {
  this.setVisible(true);
  this.setBounds(100, 100, 300, 100);
  this.setLayout(null);

  JPanel topPanel = getPanel();

  Container con = this.getContentPane();
  con.add(topPanel);

  this.validate();
  this.repaint();
  setDefaultCloseOperation(EXIT_ON_CLOSE);
  
  //发送
  try {
   socket = new DatagramSocket(6789);
   serverAddress = InetAddress.getByName("192.168.1.110");
  } catch (Exception e1) {
   e1.printStackTrace();
  }
  
  
  
  //监听
  new Thread(new Runnable(){

   @Override
   public void run() {
    System.out.println("listen..");
    DatagramSocket serversocket = null;
    try {
     serversocket = new DatagramSocket(4567);
    } catch (Exception e) {
     e.printStackTrace();
    }
     while(true){
      byte data[] = new byte[1024];
      DatagramPacket packet = new DatagramPacket(data, data.length);
      try {
       serversocket.receive(packet);
      } catch (IOException e) {
       e.printStackTrace();
      }
      String result = new String(packet.getData(), packet.getOffset(),
        packet.getLength());
      System.out.println("来自手机的消息--->" + result);
     }
     
   }
   
  }).start();//启动监听
 }

 public JPanel getPanel() {
  JPanel topPanel = new JPanel();
  topPanel.setBounds(1, 1, 200, 100);
  topPanel.setBackground(new Color(244, 244, 244));
  topPanel.setLayout(null);
  JButton sendButton = new JButton("开始发送");
  sendButton.setBounds(40, 15, 150, 20);
  topPanel.add(sendButton);
  sendButton.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {
    sendMessage();
   }
  });
  return topPanel;
 }
 public void sendMessage(){
  
  try {
   String str = "I am from computer...";
   byte data[] = str.getBytes();
   
   DatagramPacket packet = new DatagramPacket(data,
     data.length, serverAddress, 6789);
   socket.send(packet);
  } catch (Exception e) {
   e.printStackTrace();
  }
 }
 public static void main(String[] args) {
  new MainFrame();
 }

}

 

通讯乙(android)

import android.app.Activity;
import android.os.Bundle;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ServerActivity extends Activity {
 private Button requestButton = null;
 DatagramSocket socket = null;
 InetAddress serverAddress = null;

 @Override
 public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.main);
  
  
  
  
  
  new Thread(new Runnable(){

   @Override
   public void run() {
    System.out.println("listen..");
    DatagramSocket serversocket = null;
    try {
     serversocket = new DatagramSocket(6789);
    } catch (Exception e) {
     e.printStackTrace();
    }
     while(true){
      byte data[] = new byte[1024];
      DatagramPacket packet = new DatagramPacket(data, data.length);
      try {
       serversocket.receive(packet);
      } catch (IOException e) {
       e.printStackTrace();
      }
      String result = new String(packet.getData(), packet.getOffset(),
        packet.getLength());
      System.out.println("来自电脑的消息--->" + result);
     }
     
   }
   
  }).start();//启动监听
  
  
  //设置发送
  try {
   socket = new DatagramSocket(4567);
   serverAddress = InetAddress.getByName("192.168.1.109");
  } catch (Exception e1) {
   e1.printStackTrace();
  }
  requestButton = (Button) findViewById(R.id.startRequest);// 作为客户端

  requestButton.setOnClickListener(new OnClickListener() {

   @Override
   public void onClick(View arg0) {
    try {
     System.out.println("request..");
     String str = "hello";
     byte data[] = str.getBytes();
     
     DatagramPacket packet = new DatagramPacket(data,
       data.length, serverAddress, 4567);
     socket.send(packet);
    } catch (Exception e) {
     e.printStackTrace();
    }
   }

  });
  
 }
 

}






























复制代码

 1 package mao.example.quicksend;
 2 
 3 
 4 import android.app.Activity;
 5 import android.content.Context;
 6 import android.os.Bundle;
 7 import android.os.Handler;
 8 import android.util.Log;
 9 import android.view.Menu;
10 import android.view.MenuItem;
11 import android.widget.Button;
12 import android.widget.TextView;
13 import android.widget.Toast;
14 import android.view.View;
15 
16 public class MainActivity extends Activity {
17     
18     @Override
19     protected void onCreate(Bundle savedInstanceState) {
20         super.onCreate(savedInstanceState);
21         setContentView(R.layout.activity_main);
22         
23         InitUI();
24         
25         //获得Button对象
26         Button btnConnect = (Button) findViewById(R.id.buttonConnect);
27         btnConnect.setOnClickListener(new Button.OnClickListener() {
28             public void onClick(View v)
29             {
30                 //这里处理事件
31                 
32                 //ConnectServer();
33                 //UDPClient udpET = new UDPClient("192.168.0.14","123");
34                 //udpET.start();
35                 //DisplayToast("点击了\"连接\"按钮");
36             }
37         });
38         
39         //发送Button
40         btnConnect = (Button) findViewById(R.id.buttonSend);
41         btnConnect.setOnClickListener(new Button.OnClickListener() {
42             public void onClick(View v)
43             {
44                 //这里处理事件
45                 SendText();
46             }
47         });
48     }
49 
50     @Override
51     public boolean onCreateOptionsMenu(Menu menu) {
52         // Inflate the menu; this adds items to the action bar if it is present.
53         getMenuInflater().inflate(R.menu.main, menu);
54         return true;
55     }
56 
57     @Override
58     public boolean onOptionsItemSelected(MenuItem item) {
59         // Handle action bar item clicks here. The action bar will
60         // automatically handle clicks on the Home/Up button, so long
61         // as you specify a parent activity in AndroidManifest.xml.
62         int id = item.getItemId();
63         if (id == R.id.action_settings) {
64             return true;
65         }
66         return super.onOptionsItemSelected(item);
67     }
68     
69     /* 显示Toast  */
70     public void DisplayToast(String str)
71     {
72         Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
73     }
74     
75     public void InitUI()
76     {
77         TextView text=(TextView)findViewById(R.id.editTextIP);
78         text.setText("192.168.0.14");
79         
80         text = (TextView)findViewById(R.id.editTextFilePath);
81         text.setText("Udp Client Send Test");
82     }
83     
84     // connect server
85     public void SendText() 
86     {
87         TextView editIP=(TextView)findViewById(R.id.editTextIP);
88         TextView editText=(TextView)findViewById(R.id.editTextFilePath);
89         String message = editText.getText().toString() + "\r\n"; 
90         
91         UDPClient udpET = new UDPClient(editIP.getText().toString(), message);
92         udpET.start();
93     }
94 }

复制代码

UDPClient.java
复制代码

 1 package mao.example.quicksend;
 2 
 3 import java.io.IOException;
 4 import java.net.DatagramPacket;
 5 import java.net.DatagramSocket;
 6 import java.net.InetAddress;
 7 import java.net.SocketException;
 8 import java.lang.String;
 9 
10 public class UDPClient extends Thread{
11 
12     public String m_serverIP;
13     public String m_text;
14     
15     public  UDPClient(String strServerIP, String strText)
16     {
17         m_serverIP = strServerIP;
18         m_text = strText;
19     }
20     
21     @Override
22     public void run() {
23         // TODO Auto-generated method stub
24         int TIMEOUT = 3000;
25         int servPort = 8800;
26         byte[] bytesToSend = m_text.getBytes();//"test_client".getBytes();
27 
28         try {
29             InetAddress serverAddress = InetAddress.getByName(m_serverIP);
30             DatagramSocket socket = new DatagramSocket();
31             socket.setSoTimeout(TIMEOUT);
32             
33             DatagramPacket sendPacket = new DatagramPacket(bytesToSend,bytesToSend.length,serverAddress,servPort);
34             socket.send(sendPacket);
35                 
36             socket.close();
37         } catch (SocketException e){
38             e.printStackTrace();
39         }catch(IOException e){
40             e.printStackTrace();
41         }
42     }
43 }

复制代码

复制代码

 1 package mao.example.quicksend;
 2 
 3 
 4 import android.app.Activity;
 5 import android.content.Context;
 6 import android.os.Bundle;
 7 import android.os.Handler;
 8 import android.util.Log;
 9 import android.view.Menu;
10 import android.view.MenuItem;
11 import android.widget.Button;
12 import android.widget.TextView;
13 import android.widget.Toast;
14 import android.view.View;
15 
16 public class MainActivity extends Activity {
17     
18     @Override
19     protected void onCreate(Bundle savedInstanceState) {
20         super.onCreate(savedInstanceState);
21         setContentView(R.layout.activity_main);
22         
23         InitUI();
24         
25         //获得Button对象
26         Button btnConnect = (Button) findViewById(R.id.buttonConnect);
27         btnConnect.setOnClickListener(new Button.OnClickListener() {
28             public void onClick(View v)
29             {
30                 //这里处理事件
31                 
32                 //ConnectServer();
33                 //UDPClient udpET = new UDPClient("192.168.0.14","123");
34                 //udpET.start();
35                 //DisplayToast("点击了\"连接\"按钮");
36             }
37         });
38         
39         //发送Button
40         btnConnect = (Button) findViewById(R.id.buttonSend);
41         btnConnect.setOnClickListener(new Button.OnClickListener() {
42             public void onClick(View v)
43             {
44                 //这里处理事件
45                 SendText();
46             }
47         });
48     }
49 
50     @Override
51     public boolean onCreateOptionsMenu(Menu menu) {
52         // Inflate the menu; this adds items to the action bar if it is present.
53         getMenuInflater().inflate(R.menu.main, menu);
54         return true;
55     }
56 
57     @Override
58     public boolean onOptionsItemSelected(MenuItem item) {
59         // Handle action bar item clicks here. The action bar will
60         // automatically handle clicks on the Home/Up button, so long
61         // as you specify a parent activity in AndroidManifest.xml.
62         int id = item.getItemId();
63         if (id == R.id.action_settings) {
64             return true;
65         }
66         return super.onOptionsItemSelected(item);
67     }
68     
69     /* 显示Toast  */
70     public void DisplayToast(String str)
71     {
72         Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
73     }
74     
75     public void InitUI()
76     {
77         TextView text=(TextView)findViewById(R.id.editTextIP);
78         text.setText("192.168.0.14");
79         
80         text = (TextView)findViewById(R.id.editTextFilePath);
81         text.setText("Udp Client Send Test");
82     }
83     
84     // connect server
85     public void SendText() 
86     {
87         TextView editIP=(TextView)findViewById(R.id.editTextIP);
88         TextView editText=(TextView)findViewById(R.id.editTextFilePath);
89         String message = editText.getText().toString() + "\r\n"; 
90         
91         UDPClient udpET = new UDPClient(editIP.getText().toString(), message);
92         udpET.start();
93     }
94 }



    　　mEditText.getText().length()== 0
    　　mEditText.getText().toString() == null
    　　mEditText.getText().toString().equals("")

复制代码
edittext.getText().toString.equals("");




android:id="@+id/editSms"
   android:layout_width="fill_parent"
   android:layout_height="wrap_content"
   android:hint="@string/saveSms"
   android:inputType="textMultiLine"
   android:gravity="left|top"
   android:minLines="6" />
注释：
android:inputType="textMultiLine"//可以显示多行
android:gravity="left|top"//输入时光标左上角
android:minLines="6" //最小显示6行



android:layout_gravity="center_vertical"//设置控件显示的位置：默认top，这里居中显示，还有bottom 
android:hint="请输入数字！"//设置显示在空间上的提示信息 
android:numeric="integer"//设置只能输入整数，如果是小数则是：decimal 
android:singleLine="true"//设置单行输入，一旦设置为true，则文字不会自动换行。
android:gray="top" //多行中指针在第一行第一位置
et.setSelection(et.length());//调整光标到最后一行
Android:autoText //自动拼写帮助
Android:capitalize //首字母大写
Android:digits //设置只接受某些数字
Android：singleLine //是否单行或者多行，回车是离开文本框还是文本框增加新行
Android：numeric //只接受数字
Android：password //密码
Android：phoneNumber // 输入电话号码
Android：editable //是否可编辑
Android:autoLink=”all” //设置文本超链接样式当点击网址时，跳向该网址
android:password="true"//设置只能输入密码 
android:textColor = "#ff8c00"//字体颜色 
android:textStyle="bold"//字体，bold, italic, bolditalic 
android:textSize="20dip"//大小 
android:capitalize = "characters"//以大写字母写 
android:textAlign="center"//EditText没有这个属性，但TextView有 
android:textColorHighlight="#cccccc"//被选中文字的底色，默认为蓝色 
android:textColorHint="#ffff00"//设置提示信息文字的颜色，默认为灰色 
android:textScaleX="1.5"//控制字与字之间的间距 
android:typeface="monospace"//字型，normal, sans, serif, monospace 
android:background="@null"//空间背景，这里没有，指透明 
android:layout_weight="1"//权重 在控制控   件显示的大小时蛮有用的。 
android:textAppearance="?android:attr/textAppearanceLargeInverse"//文字外观，这里引用的是系统自带的一个外观，？表示系统是否有这种外观，否则使用默认的外观。不知道这样理解对不对？ 

属性名称描述
android:autoLink设置是否当文本为URL链接/email/电话号码/map时，文本显示为可点击的链接。可选值(none/web/email/phone/map/all)
android:autoText如果设置，将自动执行输入值的拼写纠正。此处无效果，在显示输入法并输入的时候起作用。
android:bufferType指定getText()方式取得的文本类别。选项editable 类似于StringBuilder可追加字符，
也就是说getText后可调用append方法设置文本内容。spannable 则可在给定的字符区域使用样式，参见这里1、这里2。
android:capitalize设置英文字母大写类型。此处无效果，需要弹出输入法才能看得到，参见EditView此属性说明。
android:cursorVisible设定光标为显示/隐藏，默认显示。
android:digits设置允许输入哪些字符。如“1234567890.+-*/% ()”
android:drawableBottom在text的下方输出一个drawable，如图片。如果指定一个颜色的话会把text的背景设为该颜色，并且同时和background使用时覆盖后者。
android:drawableLeft在text的左边输出一个drawable，如图片。
android:drawablePadding设置text与drawable(图片)的间隔，与drawableLeft、drawableRight、drawableTop、drawableBottom一起使用，可设置为负数，单独使用没有效果。

android:drawableRight在text的右边输出一个drawable，如图片。
android:drawableTop在text的正上方输出一个drawable，如图片。
android:editable设置是否可编辑。这里无效果，参见EditView。
android:editorExtras设置文本的额外的输入数据。在EditView再讨论。
android:ellipsize设置当文字过长时,该控件该如何显示。有如下值设置：”start”—?省略号显示在开头;”end”——省略号显示在结尾;”middle”—-省略号显示在中间;”marquee” ——以跑马灯的方式显示(动画横向移动)
android:freezesText设置保存文本的内容以及光标的位置。参见：这里。
android:gravity设置文本位置，如设置成“center”，文本将居中显示。
android:hintText为空时显示的文字提示信息，可通过textColorHint设置提示信息的颜色。此属性在EditView中使用，但是这里也可以用。
android:imeOptions附加功能，设置右下角IME动作与编辑框相关的动作，如actionDone右下角将显示一个“完成”，而不设置默认是一个回车符号。这个在EditView中再详细说明，此处无用。
android:imeActionId设置IME动作ID。在EditView再做说明，可以先看这篇帖子：这里。
android:imeActionLabel设置IME动作标签。在EditView再做说明。
android:includeFontPadding设置文本是否包含顶部和底部额外空白，默认为true。
android:inputMethod为文本指定输入法，需要完全限定名(完整的包名)。例如：com.google.android.inputmethod.pinyin，但是这里报错找不到。
android:inputType设置文本的类型，用于帮助输入法显示合适的键盘类型。在EditView中再详细说明，这里无效果。
android:linksClickable设置链接是否点击连接，即使设置了autoLink。
android:marqueeRepeatLimit在ellipsize指定marquee的情况下，设置重复滚动的次数，当设置为marquee_forever时表示无限次。
android:ems设置TextView的宽度为N个字符的宽度。这里测试为一个汉字字符宽度，如图：
android:maxEms设置TextView的宽度为最长为N个字符的宽度。与ems同时使用时覆盖ems选项。
android:minEms设置TextView的宽度为最短为N个字符的宽度。与ems同时使用时覆盖ems选项。
android:maxLength限制显示的文本长度，超出部分不显示。
android:lines设置文本的行数，设置两行就显示两行，即使第二行没有数据。
android:maxLines设置文本的最大显示行数，与width或者layout_width结合使用，超出部分自动换行，超出行数将不显示。
android:minLines设置文本的最小行数，与lines类似。
android:lineSpacingExtra设置行间距。
android:lineSpacingMultiplier设置行间距的倍数。如”1.2”
android:numeric如果被设置，该TextView有一个数字输入法。此处无用，设置后唯一效果是TextView有点击效果，此属性在

EdtiView将详细说明。
android:password以小点”.”显示文本
android:phoneNumber设置为电话号码的输入方式。
android:privateImeOptions设置输入法选项，此处无用，在EditText将进一步讨论。
android:scrollHorizontally设置文本超出TextView的宽度的情况下，是否出现横拉条。
android:selectAllOnFocus如果文本是可选择的，让他获取焦点而不是将光标移动为文本的开始位置或者末尾位置。TextView中设置后无效果。
android:shadowColor指定文本阴影的颜色，需要与shadowRadius一起使用。效果：
android:shadowDx设置阴影横向坐标开始位置。
android:shadowDy设置阴影纵向坐标开始位置。
android:shadowRadius设置阴影的半径。设置为0.1就变成字体的颜色了，一般设置为3.0的效果比较好。
android:singleLine设置单行显示。如果和layout_width一起使用，当文本不能全部显示时，后面用“…”来表示。如android:text="test_ singleLine " android:singleLine="true" android:layout_width="20dp"将只显示“t…”。如果不设置singleLine或者设置为false，文本将自动换行
android:text设置显示文本.
android:shadowDx设置阴影横向坐标开始位置。
android:shadowDy设置阴影纵向坐标开始位置。
android:shadowRadius设置阴影的半径。设置为0.1就变成字体的颜色了，一般设置为3.0的效果比较好。
android:singleLine设置单行显示。如果和layout_width一起使用，当文本不能全部显示时，后面用“…”来表示。如
android:text="test_ singleLine " android:singleLine="true" android:layout_width="20dp"将只显示“t…”。如果不设置singleLine或者设置为false，文本将自动换行
android:text设置显示文本.
android:textSize设置文字大小，推荐度量单位”sp”，如”15sp”
android:textStyle设置字形[bold(粗体) 0, italic(斜体) 1, bolditalic(又粗又斜) 2] 可以设置一个或多个，用“|”隔开
android:typeface设置文本字体，必须是以下常量值之一：normal 0, sans 1, serif 2, monospace(等宽字体) 3]
android:height设置文本区域的高度，支持度量单位：px(像素)/dp/sp/in/mm(毫米)
android:maxHeight设置文本区域的最大高度
android:minHeight设置文本区域的最小高度
android:width设置文本区域的宽度，支持度量单位：px(像素)/dp/sp/in/mm(毫米)，与layout_width的区别看这里。
android:maxWidth设置文本区域的最大宽度
android:minWidth设置文本区域的最小宽度
android:textAppearance设置文字外观。如“?android:attr/textAppearanceLargeInverse”这里引用的是系统自带的一个外观，?表示系统是否有这种外观，否则使用默认的外观。可设置的值如下：
textAppearanceButton/textAppearanceInverse/textAppearanceLarge/textAppearanceLargeInverse/textAppearanceMedium/textAp
pearanceMediumInverse/textAppearanceSmall/textAppearanceSmallInverse
android:textAppearance设置文字外观。如“?android:attr/textAppearanceLargeInverse”这里引用的是系统自带的一个外观，?表示系统是否有这种外观，否则使用默认的外观。可设置的值如下：
textAppearanceButton/textAppearanceInverse/textAppearanceLarge/





public class SocketActivity extends Activity{
    private Button startButton=null;
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.main);
        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(new StartSocketListener());
    }
    class StartSocketListener implements OnClickListener{
        public void onClick(){
           new ServerThread().start();
        }
    }
    class ServerThread extends Thread{
        public void run(){
           ServerSocket serverSocket=null;
           try{
              serverSocket = new ServerSocket(3456);
              Socket socket = serverSocket.accept();
              InputStream inputStream = socket.getInputStream();
              byte buffer [] = new byte[1024];
              int temp = 0;
              while((temp = inputStream.read(buffer)) !=-1){
                    System.out.println(new String(buffer,0,temp));
              }
           }catch(IOException e){
               e.printStackTrace();
           }finally{
               try{
                   serverSocket.close();
               }catch(Exception e){
                   e.printStackTrace();
               }
           }
        }
    }
}


public class TCPClient{
     public static void main(String[] args){
          try{
              Socket socket = new Socket("192.168.0.100",3456);
              InputStream inputStream = new FileInputStream("f://file/words.txt");
              OutputStream outputStream = socket.getOutputStream();
              byte buffer[] = new byte[4*1024];
              int temp=0;
              while((temp=inputStream.read(buffer))!=-1){
                  outputStream.write(buffer,0,temp);
              }
          }catch(Exception e){
             e.printStackTrace();
          }
     }
}

UDP协议下的SocketActivity中的run()方法
    public void run(){
       try{
          DatagramSocket socket=new DatagramSocket(4567);
          byte data[]=new byte[1024];
          DatagramPacket packet = new DatagramPacket(data,data.length);
          socket.receive(packet);
          String result= new String(packet.getData(),packet.getOffset(),packet.getLength());
          System.out.println("result----->"+result); 
       }
    }
public class UDPClient{
    public static void main(String[] args){
        try{
           DatagramSocket socket = new DatagramSocket(3456);
           InetAddress serverAddress = InetAddress.getByName("192.168.1.100");
           String str= "hello";
           byte data[]=str.getBytes();
           DatagramPacket packet = new DatagramPacket(data,data.length,serverAddress,3456);
           socket.send(packet);
        }catch(Exception e){
           e.printStackTrace();
        }       
    }
}
public class SocketActivity extends Activity{
    private Button startButton=null;
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.main);
        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(new StartSocketListener());
    }
    class StartSocketListener implements OnClickListener{
        public void onClick(){
           new ServerThread().start();
        }
    }
    class ServerThread extends Thread{
        public void run(){
           ServerSocket serverSocket=null;
           try{
              serverSocket = new ServerSocket(3456);
              Socket socket = serverSocket.accept();
              InputStream inputStream = socket.getInputStream();
              byte buffer [] = new byte[1024];
              int temp = 0;
              while((temp = inputStream.read(buffer)) !=-1){
                    System.out.println(new String(buffer,0,temp));
              }
           }catch(IOException e){
               e.printStackTrace();
           }finally{
               try{
                   serverSocket.close();
               }catch(Exception e){
                   e.printStackTrace();
               }
           }
        }
    }
}


public class TCPClient{
     public static void main(String[] args){
          try{
              Socket socket = new Socket("192.168.0.100",3456);
              InputStream inputStream = new FileInputStream("f://file/words.txt");
              OutputStream outputStream = socket.getOutputStream();
              byte buffer[] = new byte[4*1024];
              int temp=0;
              while((temp=inputStream.read(buffer))!=-1){
                  outputStream.write(buffer,0,temp);
              }
          }catch(Exception e){
             e.printStackTrace();
          }
     }
}

UDP协议下的SocketActivity中的run()方法
    public void run(){
       try{
          DatagramSocket socket=new DatagramSocket(4567);
          byte data[]=new byte[1024];
          DatagramPacket packet = new DatagramPacket(data,data.length);
          socket.receive(packet);
          String result= new String(packet.getData(),packet.getOffset(),packet.getLength());
          System.out.println("result----->"+result); 
       }
    }
public class UDPClient{
    public static void main(String[] args){
        try{
           DatagramSocket socket = new DatagramSocket(3456);
           InetAddress serverAddress = InetAddress.getByName("192.168.1.100");
           String str= "hello";
           byte data[]=str.getBytes();
           DatagramPacket packet = new DatagramPacket(data,data.length,serverAddress,3456);
           socket.send(packet);
        }catch(Exception e){
           e.printStackTrace();
        }       
    }
}




Android客户端通过socket与服务器进行通信可以分为以下几步：

       应用程序与服务器通信可以采用两种模式：TCP可靠通信和UDP不可靠通信。

       （1）通过IP地址和端口实例化Socket，请求连接服务器：
Java代码

    socket = new Socket(HOST, PORT);   //host:为服务器的IP地址  port：为服务器的端口号  

       （2）获取Socket流以进行读写，并把流包装进BufferWriter或者PrintWriter：
Java代码

    PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);    

        这里涉及了三个类：socket.getOutputStream得到socket的输出字节流，OutputStreamWriter是字节流向字符流转换的桥梁，BufferWriter是字符流，然后再包装进PrintWriter。

       （3）对Socket进行读写
Java代码

    if (socket.isConnected()) {  
        if (!socket.isOutputShutdown()) {  
            out.println(msg);  
        }  
    }  

       （4）关闭打开的流
Java代码

    out.close();  

        在写代码的过程中一定要注意对socket  输入流 、输出流的关闭。

        下面是一个简单的例子：

        main.xml：
XML/HTML代码

    <?xml version="1.0" encoding="utf-8"?>  
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"    
        android:orientation="vertical"   
        android:layout_width="fill_parent"    
        android:layout_height="fill_parent">    
        <TextView   
            android:id="@+id/TextView"   
            android:singleLine="false"    
            android:layout_width="fill_parent"    
            android:layout_height="wrap_content" />    
        <EditText android:hint="content"   
            android:id="@+id/EditText01"    
            android:layout_width="fill_parent"    
            android:layout_height="wrap_content">    
        </EditText>    
        <Button   
            android:text="send"   
            android:id="@+id/Button02"    
            android:layout_width="fill_parent"    
            android:layout_height="wrap_content">    
        </Button>    
    </LinearLayout>   

       下面是android客户端的源代码：
Java代码

    package com.android.SocketDemo;  
      
    import java.io.BufferedReader;  
    import java.io.BufferedWriter;  
    import java.io.IOException;  
    import java.io.InputStreamReader;  
    import java.io.OutputStreamWriter;  
    import java.io.PrintWriter;  
    import java.net.Socket;  
      
    import android.app.Activity;  
    import android.app.AlertDialog;  
    import android.content.DialogInterface;  
    import android.os.Bundle;  
    import android.os.Handler;  
    import android.os.Message;  
    import android.view.View;  
    import android.widget.Button;  
    import android.widget.EditText;  
    import android.widget.TextView;  
      
    public class SocketDemo extends Activity implements Runnable {  
        private TextView tv_msg = null;  
        private EditText ed_msg = null;  
        private Button btn_send = null;  
    //    private Button btn_login = null;  
        private static final String HOST = "192.168.1.223";  
        private static final int PORT = 9999;  
        private Socket socket = null;  
        private BufferedReader in = null;  
        private PrintWriter out = null;  
        private String content = "";  
      
        /** Called when the activity is first created. */  
        @Override  
        public void onCreate(Bundle savedInstanceState) {  
            super.onCreate(savedInstanceState);  
            setContentView(R.layout.main);  
      
            tv_msg = (TextView) findViewById(R.id.TextView);  
            ed_msg = (EditText) findViewById(R.id.EditText01);  
    //        btn_login = (Button) findViewById(R.id.Button01);  
            btn_send = (Button) findViewById(R.id.Button02);  
      
            try {  
                socket = new Socket(HOST, PORT);  
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(  
                        socket.getOutputStream())), true);  
            } catch (IOException ex) {  
                ex.printStackTrace();  
                ShowDialog("login exception" + ex.getMessage());  
            }  
            btn_send.setOnClickListener(new Button.OnClickListener() {  
      
                @Override  
                public void onClick(View v) {  
                    // TODO Auto-generated method stub  
                    String msg = ed_msg.getText().toString();  
                    if (socket.isConnected()) {  
                        if (!socket.isOutputShutdown()) {  
                            out.println(msg);  
                        }  
                    }  
                }  
            });  
            new Thread(SocketDemo.this).start();  
        }  
      
        public void ShowDialog(String msg) {  
            new AlertDialog.Builder(this).setTitle("notification").setMessage(msg)  
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {  
      
                        @Override  
                        public void onClick(DialogInterface dialog, int which) {  
                            // TODO Auto-generated method stub  
      
                        }  
                    }).show();  
        }  
      
        public void run() {  
            try {  
                while (true) {  
                    if (socket.isConnected()) {  
                        if (!socket.isInputShutdown()) {  
                            if ((content = in.readLine()) != null) {  
                                content += "\n";  
                                mHandler.sendMessage(mHandler.obtainMessage());  
                            } else {  
      
                            }  
                        }  
                    }  
                }  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
      
        public Handler mHandler = new Handler() {  
            public void handleMessage(Message msg) {  
                super.handleMessage(msg);  
                tv_msg.setText(tv_msg.getText().toString() + content);  
            }  
        };  
    }  

       下面是服务器端得java代码：
Java代码

    import java.io.BufferedReader;  
    import java.io.BufferedWriter;  
    import java.io.IOException;  
    import java.io.InputStreamReader;  
    import java.io.OutputStreamWriter;  
    import java.io.PrintWriter;  
    import java.net.ServerSocket;  
    import java.net.Socket;  
    import java.util.ArrayList;  
    import java.util.List;  
    import java.util.concurrent.ExecutorService;  
    import java.util.concurrent.Executors;  
      
      
    public class Main {  
        private static final int PORT = 9999;  
        private List<Socket> mList = new ArrayList<Socket>();  
        private ServerSocket server = null;  
        private ExecutorService mExecutorService = null; //thread pool  
          
        public static void main(String[] args) {  
            new Main();  
        }  
        public Main() {  
            try {  
                server = new ServerSocket(PORT);  
                mExecutorService = Executors.newCachedThreadPool();  //create a thread pool  
                System.out.print("server start ...");  
                Socket client = null;  
                while(true) {  
                    client = server.accept();  
                    mList.add(client);  
                    mExecutorService.execute(new Service(client)); //start a new thread to handle the connection  
                }  
            }catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        class Service implements Runnable {  
                private Socket socket;  
                private BufferedReader in = null;  
                private String msg = "";  
                  
                public Service(Socket socket) {  
                    this.socket = socket;  
                    try {  
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
                        msg = "user" +this.socket.getInetAddress() + "come toal:"  
                            +mList.size();  
                        this.sendmsg();  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                    }  
                      
                }  
      
                @Override  
                public void run() {  
                    // TODO Auto-generated method stub  
                    try {  
                        while(true) {  
                            if((msg = in.readLine())!= null) {  
                                if(msg.equals("exit")) {  
                                    System.out.println("ssssssss");  
                                    mList.remove(socket);  
                                    in.close();  
                                    msg = "user:" + socket.getInetAddress()  
                                        + "exit total:" + mList.size();  
                                    socket.close();  
                                    this.sendmsg();  
                                    break;  
                                } else {  
                                    msg = socket.getInetAddress() + ":" + msg;  
                                    this.sendmsg();  
                                }  
                            }  
                        }  
                    } catch (Exception e) {  
                        e.printStackTrace();  
                    }  
                }  
                
               public void sendmsg() {  
                   System.out.println(msg);  
                   int num =mList.size();  
                   for (int index = 0; index < num; index ++) {  
                       Socket mSocket = mList.get(index);  
                       PrintWriter pout = null;  
                       try {  
                           pout = new PrintWriter(new BufferedWriter(  
                                   new OutputStreamWriter(mSocket.getOutputStream())),true);  
                           pout.println(msg);  
                       }catch (IOException e) {  
                           e.printStackTrace();  
                       }  
                   }  
               }  
            }      
    }  

       注意在AndroidManifest.xml中加入对网络的访问权限：
XML/HTML代码

    <uses-permission android:name="android.permission.INTERNET"></uses-permission> 






【Activity】

　　一个Activity是一个应用程序组件，提供一个屏幕，用户可以用来交互为了完成某项任务，例如拨号、拍照、发送email、看地图。每一个activity被给予一个窗口，在上面可以绘制用户接口。窗口通常充满屏幕，但也可以小于屏幕而浮于其它窗口之上。

　　一个应用程序通常由多个activities组成，他们通常是松耦合关系。通常，一个应用程序中的activity被指定为"main"activity，当第一次启动应用程序的时候呈现给用户的那个activity。每一个activity然后可以启动另一个activity为了完成不同的动作。每一次一个activity启动，前一个activity就停止了，但是系统保留activity在一个栈上（“back stack”）。当一个新activity启动，它被推送到栈顶，取得用户焦点。Back Stack符合简单“后进先出”原则，所以，当用户完成当前activity然后点击back按钮，它被弹出栈（并且被摧毁），然后之前的activity恢复。

　　当一个activity因新的activity启动而停止，它被通知这种状态转变通过activity的生命周期回调函数。有许多回调函数一个activity可能会收到，源于它自己的状态变化－无论系统创建它、停止它、恢复它、摧毁它－并且每个回调提供你完成适合这个状态的指定工作的机会。例如，当停止的时候，你的activity应该释放任何大的对象，例如网络数据库连接。当activity恢复，你可以重新获得必要的资源和恢复被中断的动作。这些状态转换都是activity的生命周期的部分。

【Creating an Activity】

　　创建一个activity，你必须创建一个Activity的子类（或者一个Activity的子类的子类）。在你的子类中，你需要实现系统回调的回调方法，当activity在它的生命周期的多种状态中转换的时候，例如当activity被创建、停止、恢复或摧毁。两个最重要的回调方法是：
onCreate()
    你必须实现这个方法。系统调用它当创建你的activity的时候。在你的实现中，你应该初始化你的activity的基本的组件。更重要的是，这里就是你必须调用setContentView（）来定义activity用户接口而已的地方。
onPause()
    系统调用这个方法当用户离开你的activity（虽然不总是意味着activity被摧毁）。这通常是你应该提交任何变化，那此将会超越user session而存在的（因为用户可能不再回来）。

　　有若干其它生命周期回调函数你应该使用为了提供一个流畅的用户体验，并表操作异常中断会引起你的activity被中断甚至被摧毁。

1、Implementing a user interface

　　一个activity的用户接口被一个层次化的视图提供－－继承于View类的对象。每个View控制activity窗口中的一个特定矩形区域并且能响应用户交互。例如，一个view可能是个button，初始化动作当用户触摸它的时候。

　　Android提供大量预定义的view，你可以使用来设计和组件你的布局。“Widgets”是一种给屏幕提供可视化(并且交互)元素的view，例如按钮、文件域、复选框或者仅仅是图像。“Layouts”是继承于ViewGroup的View，提供特殊的布局模型为它的子view，例如线程布局、格子布局或相关性布局。你可以子类化View和ViewGroup类（或者存在的子类）来创建自己的widget和而已并且应用它们到你的activity布局中。

　　最普通的方法是定义一个布局使用view加上XML布局文件保存在你的程序资源里。这样，你可以单独维护你的用户接口设计，而与定义activity行为的代码无关。你可以设置布局作为UI使用setContentView()，传递资源布局的资源ID。可是，你也可以创建新Views在你的activity代码，并且创建一个view层次通过插入新Views到ViewGroup，然后使用那个布局通过传递到根ViewGroup给setContentView()。

【Declaring the activity in the manifest】

　　你必须声明你的activity在manifest文件为了它可以被系统访问。要声明你的activity，打开你的manifest文件，添加一个<activity>元素作为<application>元素的子元素。例如：

【Using intent filters】

　　一个<activity>元素也能指定多种intent filters－－使用<inetent-filter>元素－－为了声明其它应用程序可以激活它。

　　当你创建一个新应用程序使用Android SDK工具，存根activity自动为你创建，包含一个intent filter，声明了activity响应"main"动作，并且应该被 放置 在"launcher"分类。Intent filter看起来像这个样子。

　　<action>元素指定这是一个"main"入口点对这个应用程序。<category>元素指定，这个activity应该被列入系统应用程序列表中（为了允许用户启动这个activity）。

　　如果你希望应用程序自包含，并且不希望别的应用程序激活它的activities，那么你不需要任何其它intent filters。只有一个activity应该有“main"动作和”launcher“分类，就像前面这个例子。你不希望被其它应用程序访问原Activities应该没有intent filters而且你能启动他们通过自己显示的intent。

　　可是，如果你希望你的activity响应影含的intents，从其它应用程序（和你自己的），那么你必须定义额外的intent filters为这个activity。每一种你希望响应的类型的intent，你必须包含<intent-filter>，包含<action>元素，可选的，一个<category>元素并且/或一个<data>元素。这些元素指定你的activity能响应的intent的类型。

sense Broadband Multimedia Technology Co., Ltd
【Starting an Activity】

　　你可以开启另一个activity通过startActivity()，传递一个Intent描述了你希望启动的Activity。Intent指定要么准备的activity你希望启动或描述你希望完成的动作（操作系统选择合适的activity为你，可能来自定不同的应用程序）。一个intent可以传输小量数据被启动的activity使用。

　　完全工作在你的应用程序之内，你将经常需要简单的启动一个未知的activity。你可以这么通过创建一个intent显示的定义你希望启动的activity，使用类名。例如，下面显示一个activity怎么启动另一个activity命名为SignInActivity：

　　

　　可是，你的应用程序或许希望执行一些动作，例如发送一份邮件、文件消息或者状态更新，使用你的activity的数据。在这种情况下，你的应用程序或许没有它自己的activity来完成这个动作，因此你可以促使设备上其它应用程序提供的activity来完成你的动作。这才是intent真正有价值的地方－－你可以创建一个intent描述一个你希望执行的动作，然后系统启动一个合适的activity从其它应用程序。如果有多种activities可以处理这个intent，那么 用户可以选择哪一个来执行。例如，如果你希望允许用户发送邮件，你可以创建下面的Intent:

　　

　　EXTRA_EMAIL额外的添加给intent一个字符串数组指定email地址，当一个邮件应用程序响应这个intent的时候，它读取这些字符串数组并且放置他们到相应字段。在这种情况下，email应用程序的activity启动并且当用户执行完，你的activity恢复。

【Starting an activity for a result】

　　有时，你或许希望接收一个结果从你启动的activity。在这种情况下，开启这个activity通过startActivityForResult()(而不是startActivity())。然后从随后的activity接收结果，实现onActiviryResult()回调函数。当随后的activity完成，它返回一个结果给你的onActivityResult()函数通过一个intent。

　　例如，或许你希望用户选择他们中的一个联系人，所以你的activity可以对这个联系人做些事情。下面是你怎么建立这样一个Intent和操作结果：

　　这个例子展现了基本的逻辑你应该使用的在你的onActivityResult()函数中，为了操作一个activity的结果。第一个条件检测是否请求成功－－如果是，那么 resultCode将会是RESULT_OK－－并且是否这个请求是否是这个响应是响知道－－在这种情况下，requestCode匹配第二个参数用startActivityForResult()的参数。在那里，代码操作activity结果通过查询返回在intent中的数据（data参数）。

　　将发生的是，一个ContentResolver实现查询content provider，返回一个Cursor允许读查询的数据。

【Shut Down an Activity】

　　你可以关闭一个activity通过调用自身的finish()方法。你也可以关闭一个独立的activity你之前启动的通过finiActivity()。


A2F4, No.399 Songling Road

LaoShan District, Qingdao







*/




完整源码
1 服务器
复制代码

 1 public class tcpservice
 2 {    
 3     // ////////////////////////////////////////////////////////////////////////////////////    
 4 //服务器端口
 5     private static final int SERVERPORT = 8888; 
 6     //存储所有客户端Socket连接对象
 7     private static List<Socket> mClientList = new ArrayList<Socket>(); 
 8     //线程池
 9     private ExecutorService mExecutorService;  
10     //ServerSocket对象
11     private ServerSocket mServerSocket;  
12     //开启服务器
13     public static void main(String[] args)
14     {
15         new tcpservice();
16     }
17     //
18     public tcpservice()
19     {
20         try
21         {
22             //设置服务器端口
23             mServerSocket = new ServerSocket(SERVERPORT);
24             //创建一个线程池
25             mExecutorService = Executors.newCachedThreadPool();
26             System.out.println("start...");
27             //用来临时保存客户端连接的Socket对象
28             Socket client = null;
29             while (true)
30             {
31                 //接收客户连接并添加到list中
32                 client = mServerSocket.accept(); 
33                 mClientList.add(client);
34                 //开启一个客户端线程
35                 mExecutorService.execute(new ThreadServer(client));
36             }
37         }
38         catch (IOException e)
39         {
40             e.printStackTrace();
41         }
42     }   
43     //每个客户端单独开启一个线程
44     static class ThreadServer implements Runnable
45     {
46         private Socket            mSocket;
47         private BufferedReader    mBufferedReader;
48         private PrintWriter        mPrintWriter;
49         private String            mStrMSG;
50 
51         public ThreadServer(Socket socket) throws IOException
52         {
53             this.mSocket = socket;
54             mBufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
55             mStrMSG = "user:"+this.mSocket.getInetAddress()+" come total:" + mClientList.size();
56             sendMessage();
57         }
58         public void run()
59         {
60             try
61             {
62                 while ((mStrMSG = mBufferedReader.readLine()) != null)
63                 {
64                     if (mStrMSG.trim().equals("exit"))
65                     {
66                         //当一个客户端退出时
67                         mClientList.remove(mSocket);
68                         mBufferedReader.close();
69                         mPrintWriter.close();
70                         mStrMSG = "user:"+this.mSocket.getInetAddress()+" exit total:" + mClientList.size();
71                         mSocket.close();
72                         sendMessage();
73                         break;
74                     }
75                     else
76                     {
77                         mStrMSG = mSocket.getInetAddress() + ":" + mStrMSG;
78                         sendMessage();
79                     }
80                 }
81             }
82             catch (IOException e)
83             {
84                 e.printStackTrace();
85             }
86         }
87         //发送消息给所有客户端
88         private void sendMessage() throws IOException
89         {
90             System.out.println(mStrMSG);
91             for (Socket client : mClientList)
92             {
93                 mPrintWriter = new PrintWriter(client.getOutputStream(), true);
94                 mPrintWriter.println(mStrMSG);
95             }
96         }
97     }
98 }

复制代码
 
2 Android客户端java
复制代码

  1 public class tcpclient extends Activity
  2 {
  3     // 声明对象
  4     private Button mInButton, mSendButton;
  5     private EditText mEditText01, mEditText02;
  6     private static final String SERVERIP = "10.120.220.6";
  7     private static final int SERVERPORT = 8888;
  8     private Thread mThread = null;
  9     private Socket mSocket = null;
 10     private BufferedReader mBufferedReader = null;
 11     private PrintWriter mPrintWriter = null;
 12     private  String mStrMSG = "";
 13     private static String TAG = camera.class.getSimpleName();
 14     // ////////////////////////////////////////////////////////////////////////////////////
 15     @Override
 16     protected void onCreate(Bundle savedInstanceState)
 17     {
 18         // TODO Auto-generated method stub
 19         super.onCreate(savedInstanceState);
 20         setContentView(R.layout.myinternet_tcpclient);
 21 
 22         mInButton = (Button) findViewById(R.id.myinternet_tcpclient_BtnIn);
 23         mSendButton = (Button) findViewById(R.id.myinternet_tcpclient_BtnSend);
 24         mEditText01 = (EditText) findViewById(R.id.myinternet_tcpclient_EditText01);
 25         mEditText02 = (EditText) findViewById(R.id.myinternet_tcpclient_EditText02);
 26         // ////////////////////////////////////////////////////////////////////////////////////
 27 // 登陆
 28         mInButton.setOnClickListener(new OnClickListener()
 29         {
 30             public void onClick(View v)
 31             {
 32                 try
 33                 {
 34                     // ①Socket实例化，连接服务器
 35                     mSocket = new Socket(SERVERIP, SERVERPORT);
 36                     // ②获取Socket输入输出流进行读写操作
 37                     mBufferedReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
 38                     mPrintWriter = new PrintWriter(mSocket.getOutputStream(), true);
 39                 } catch (Exception e)
 40                 {
 41                     // TODO: handle exception
 42                     Log.e(TAG, e.toString());
 43                 }
 44             }
 45         });
 46         // ////////////////////////////////////////////////////////////////////////////////////
 47 // 发送消息
 48         mSendButton.setOnClickListener(new OnClickListener()
 49         {
 50             public void onClick(View v)
 51             {
 52                 try
 53                 {
 54                     // 取得编辑框中我们输入的内容
 55                     String str = mEditText02.getText().toString() + "\n";
 56                     // 发送给服务器
 57                     mPrintWriter.print(str);
 58                     mPrintWriter.flush();
 59                 } catch (Exception e)
 60                 {
 61                     // TODO: handle exception
 62                     Log.e(TAG, e.toString());
 63                 }
 64             }
 65         });
 66         mThread = new Thread(mRunnable);
 67         mThread.start();
 68     }
 69     // ////////////////////////////////////////////////////////////////////////////////////
 70 // 线程:监听服务器发来的消息
 71     private Runnable mRunnable = new Runnable()
 72     {
 73         public void run()
 74         {
 75             while (true)
 76             {
 77                 try
 78                 {
 79                     if ((mStrMSG = mBufferedReader.readLine()) != null)
 80                     {                        
 81                         mStrMSG += "\n";// 消息换行
 82                         mHandler.sendMessage(mHandler.obtainMessage());// 发送消息
 83                     }                    
 84                 } catch (Exception e)
 85                 {
 86                     Log.e(TAG, e.toString());
 87                 }
 88             }
 89         }
 90     };
 91     // ////////////////////////////////////////////////////////////////////////////////////
 92     Handler mHandler = new Handler()//更新界面的显示（不能直接在线程中更新视图，因为Android线程是安全的）
 93     {
 94         public void handleMessage(Message msg)
 95         {
 96             super.handleMessage(msg);
 97             // 刷新
 98             try
 99             {                
100                 mEditText01.append(mStrMSG);// 将聊天记录添加进来
101             } catch (Exception e)
102             {
103                 Log.e(TAG, e.toString());
104             }
105         }
106     };
107     // ////////////////////////////////////////////////////////////////////////////////////
108 }

复制代码





复制代码

 1 <?xml version="1.0" encoding="utf-8"?>
 2 <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
 3     android:layout_width="fill_parent"
 4     android:layout_height="fill_parent"
 5     android:orientation="vertical" >
 6 
 7     <EditText
 8 android:id="@+id/myinternet_tcpclient_EditText01"
 9         android:layout_width="fill_parent"
10         android:layout_height="200px"
11         android:text="聊天记录：\n" >
12     </EditText>
13 
14     <EditText
15 android:id="@+id/myinternet_tcpclient_EditText02"
16         android:layout_width="fill_parent"
17         android:layout_height="wrap_content"
18         android:text="输入要发送的内容" >
19     </EditText>
20 
21     <LinearLayout
22 android:layout_width="fill_parent"
23         android:layout_height="wrap_content"
24         android:orientation="horizontal"
25         android:gravity="center" >
26 
27         <Button
28 android:id="@+id/myinternet_tcpclient_BtnIn"
29             android:layout_width="200px"
30             android:layout_height="wrap_content"            
31             android:text="登陆" />
32 
33         <Button
34 android:id="@+id/myinternet_tcpclient_BtnSend"
35             android:layout_width="200px"
36             android:layout_height="wrap_content"
37             android:text="发送" />
38     </LinearLayout>
39 
40 </LinearLayout>

复制代码

 
3 PC客户端
复制代码

 1 public class tcpclient2
 2 {
 3     private static final int PORT = 8888;
 4     private static ExecutorService exec = Executors.newCachedThreadPool();
 5 
 6     public static void main(String[] args) throws Exception
 7     {
 8         new tcpclient2();
 9     }
10 
11     public tcpclient2()
12     {
13         try
14         {
15             Socket socket = new Socket("10.120.220.6", PORT);
16             exec.execute(new Sender(socket));
17 
18             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
19             String msg;
20             while ((msg = br.readLine()) != null)
21             {
22                 System.out.println(msg);
23             }
24         } catch (Exception e)
25         {
26 
27         }
28     }
29 
30     // 客户端线程获取控制台输入消息
31     static class Sender implements Runnable
32     {
33         private Socket socket;
34 
35         public Sender(Socket socket)
36         {
37             this.socket = socket;
38         }
39 
40         public void run()
41         {
42             try
43             {
44                 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
45                 PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
46                 String msg;
47 
48                 while (true)
49                 {
50                     msg = br.readLine();
51                     pw.println(msg);
52 
53                     if (msg.trim().equals("exit"))
54                     {
55                         pw.close();
56                         br.close();
57                         exec.shutdownNow();
58                         break;
59                     }
60                 }
61             } catch (Exception e)
62             {
63                 e.printStackTrace();
64             }
65         }
66     }
67     // ////////////////////////////////////////////////////////////////////////////////////
68 }

Devin Zhang
博客园   首页   博问   闪存   新随笔   联系   订阅订阅  管理
随笔-122  评论-214  文章-0  trackbacks-0
一个Android Socket的例子

1.开篇简介

　　Socket本质上就是Java封装了传输层上的TCP协议（注：UDP用的是DatagramSocket类）。要实现Socket的传输，需要构建客户端和服务器端。另外，传输的数据可以是字符串和字节。字符串传输主要用于简单的应用，比较复杂的应用(比如Java和C++进行通信)，往往需要构建自己的应用层规则(类似于应用层协议)，并用字节来传输。

2.基于字符串传输的Socket案例

　　1）服务器端代码(基于控制台的应用程序，模拟)
复制代码

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {
    private static final int PORT = 9999;
    private List<Socket> mList = new ArrayList<Socket>();
    private ServerSocket server = null;
    private ExecutorService mExecutorService = null; //thread pool
    
    public static void main(String[] args) {
        new Main();
    }
    public Main() {
        try {
            server = new ServerSocket(PORT);
            mExecutorService = Executors.newCachedThreadPool();  //create a thread pool
            System.out.println("服务器已启动...");
            Socket client = null;
            while(true) {
                client = server.accept();
                //把客户端放入客户端集合中
                mList.add(client);
                mExecutorService.execute(new Service(client)); //start a new thread to handle the connection
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    class Service implements Runnable {
            private Socket socket;
            private BufferedReader in = null;
            private String msg = "";
            
            public Service(Socket socket) {
                this.socket = socket;
                try {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    //客户端只要一连到服务器，便向客户端发送下面的信息。
                    msg = "服务器地址：" +this.socket.getInetAddress() + "come toal:"
                        +mList.size()+"（服务器发送）";
                    this.sendmsg();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
            }

            @Override
            public void run() {
                try {
                    while(true) {
                        if((msg = in.readLine())!= null) {
                            //当客户端发送的信息为：exit时，关闭连接
                            if(msg.equals("exit")) {
                                System.out.println("ssssssss");
                                mList.remove(socket);
                                in.close();
                                msg = "user:" + socket.getInetAddress()
                                    + "exit total:" + mList.size();
                                socket.close();
                                this.sendmsg();
                                break;
                                //接收客户端发过来的信息msg，然后发送给客户端。
                            } else {
                                msg = socket.getInetAddress() + ":" + msg+"（服务器发送）";
                                this.sendmsg();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
          /**
           * 循环遍历客户端集合，给每个客户端都发送信息。
           */
           public void sendmsg() {
               System.out.println(msg);
               int num =mList.size();
               for (int index = 0; index < num; index ++) {
                   Socket mSocket = mList.get(index);
                   PrintWriter pout = null;
                   try {
                       pout = new PrintWriter(new BufferedWriter(
                               new OutputStreamWriter(mSocket.getOutputStream())),true);
                       pout.println(msg);
                   }catch (IOException e) {
                       e.printStackTrace();
                   }
               }
           }
        }    
}

复制代码

　　2）Android客户端代码
复制代码

package com.amaker.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SocketDemo extends Activity implements Runnable {
    private TextView tv_msg = null;
    private EditText ed_msg = null;
    private Button btn_send = null;
    // private Button btn_login = null;
    private static final String HOST = "10.0.2.2";
    private static final int PORT = 9999;
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String content = "";
    //接收线程发送过来信息，并用TextView显示
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tv_msg.setText(content);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tv_msg = (TextView) findViewById(R.id.TextView);
        ed_msg = (EditText) findViewById(R.id.EditText01);
        btn_send = (Button) findViewById(R.id.Button02);

        try {
            socket = new Socket(HOST, PORT);
            in = new BufferedReader(new InputStreamReader(socket
                    .getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream())), true);
        } catch (IOException ex) {
            ex.printStackTrace();
            ShowDialog("login exception" + ex.getMessage());
        }
        btn_send.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String msg = ed_msg.getText().toString();
                if (socket.isConnected()) {
                    if (!socket.isOutputShutdown()) {
                        out.println(msg);
                    }
                }
            }
        });
        //启动线程，接收服务器发送过来的数据
        new Thread(SocketDemo.this).start();
    }
    /**
     * 如果连接出现异常，弹出AlertDialog！
     */
    public void ShowDialog(String msg) {
        new AlertDialog.Builder(this).setTitle("notification").setMessage(msg)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }
    /**
     * 读取服务器发来的信息，并通过Handler发给UI线程
     */
    public void run() {
        try {
            while (true) {
                if (!socket.isClosed()) {
                    if (socket.isConnected()) {
                        if (!socket.isInputShutdown()) {
                            if ((content = in.readLine()) != null) {
                                content += "\n";
                                mHandler.sendMessage(mHandler.obtainMessage());
                            } else {

                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

复制代码

 　　解析：除了isClose方法，Socket类还有一个isConnected方法来判断Socket对象是否连接成功。  看到这个名字，也许读者会产生误解。  其实isConnected方法所判断的并不是Socket对象的当前连接状态，  而是Socket对象是否曾经连接成功过，如果成功连接过，即使现在isClose返回true， isConnected仍然返回true。因此，要判断当前的Socket对象是否处于连接状态， 必须同时使用isClose和isConnected方法， 即只有当isClose返回false，isConnected返回true的时候Socket对象才处于连接状态。 虽然在大多数的时候可以直接使用Socket类或输入输出流的close方法关闭网络连接，但有时我们只希望关闭OutputStream或InputStream，而在关闭输入输出流的同时，并不关闭网络连接。这就需要用到Socket类的另外两个方法：shutdownInput和shutdownOutput，这两个方法只关闭相应的输入、输出流，而它们并没有同时关闭网络连接的功能。和isClosed、isConnected方法一样，Socket类也提供了两个方法来判断Socket对象的输入、输出流是否被关闭，这两个方法是isInputShutdown()和isOutputShutdown()。 shutdownInput和shutdownOutput并不影响Socket对象的状态。

 

2.基于字节的传输

  基于字节传输的时候，只需要把相应的字符串和整数等类型转换为对应的网络字节进行传输即可。具体关于如何把其转换为网络字节，请参《网路搜集：java整型数与网络字节序的 byte[] 数组转换关系》。

 

PS：欢迎有志之士加入Android之家群：272022717. 这里是技术交流、技术支持、思想汇聚、项目交流之地。
分类: Android之网络编程
绿色通道： 好文要顶 关注我 收藏该文与我联系
Devin Zhang
关注 - 0
粉丝 - 739
+加关注
6
0
(请您对文章做出评价)
« 上一篇：android 相对布局属性
» 下一篇：IOS开发资料汇总
posted on 2012-10-04 21:12 Devin Zhang 阅读(23272) 评论(4) 编辑 收藏

评论:
#1楼 2012-10-05 13:23 | 孤~影  
楼主写得很好！
支持(0)反对(0)
  
#2楼 2013-07-26 11:22 | 晓之不难  
学习了 ，谢谢分享！
支持(0)反对(0)
  
#3楼 2014-09-11 16:48 | LDragon  
“但有时我们只希望关闭OutputStream或InputStream，而在关闭输入输出流的同时，并不关闭网络连接。” —— 请教博主这样的情况能举个例子么？只关闭 socket的 OutputStream或 InputStream 而不关闭网络连接，是指的在每次通过 OutputStream 或 InputStream 获取到socket的流以后write或read操作之后么？
支持(0)反对(0)
  
#4楼 2015-02-13 16:48 | 吾是锋子  
http://blog.csdn.net/wuchuanpingstone/article/details/6617276

先看到你的，后看到他的
支持(0)反对(0)
  
刷新评论刷新页面返回顶部
注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。
【推荐】50万行VC++源码: 大型组态工控、电力仿真CAD与GIS源码库
【热门】支持Visual Studio2015的葡萄城控件新品发布暨夏季促销
最新IT新闻:
· 世界首个仿生眼植入手术完成 让患者重见光明
· 苹果新专利暗示iPhone 7设计更接近iPhone 4
· 格力手机为何销量只有几万部？质量难以过关
· 三星回应东莞普光停产事件：与我们无关
· 谨慎选择合伙人的理由
» 更多新闻...
最新知识库文章:
· 代码审查的价值——为何做、何时做、如何做？
· 所有程序员都应该遵守的11条规则
· RESTful架构详解
· 优秀程序员眼中的整洁代码
· 怎样看待比自己强的人
» 更多知识库文章...
昵称：Devin Zhang
园龄：4年6个月
粉丝：739
关注：0
+加关注
<	2012年10月	>
日	一	二	三	四	五	六
30	1	2	3	4	5	6
7	8	9	10	11	12	13
14	15	16	17	18	19	20
21	22	23	24	25	26	27
28	29	30	31	1	2	3
4	5	6	7	8	9	10

搜索
 
随笔分类(119)

    Android之Activity(8)
    Android之Adapter(1)
    Android之ContentProvider(1)
    Android之Handler(4)
    Android之JSON(2)
    Android之Service(4)
    Android之SharedPreferences(2)
    Android之SQLite(2)
    Android之SurfaceView(5)
    Android之Widget(1)
    Android之XML(1)
    Android之菜单(1)
    Android之单元测试(1)
    Android之调试开发工具(4)
    Android之多点触控(2)
    Android之环境配置(4)
    Android之基础知识(2)
    Android之界面布局(6)
    Android之图形图像(1)
    Android之网络编程(10)
    Android之文件操作(1)
    Android之优化技术(3)
    Android之游戏开发(4)
    iOS开发(2)
    Java基础知识(17)
    Java之安全通信(2)
    Java之集合类(4)
    Java之数据结构(3)
    Java之线程池和对象池(3)
    设计模式(17)
    杂谈(1)

随笔档案(122)

    2015年6月 (2)
    2014年7月 (2)
    2013年8月 (1)
    2012年10月 (1)
    2012年9月 (2)
    2012年8月 (2)
    2012年7月 (3)
    2012年6月 (1)
    2012年5月 (1)
    2012年4月 (9)
    2012年3月 (5)
    2012年2月 (24)
    2012年1月 (46)
    2011年12月 (23)

积分与排名

    积分 - 267758
    排名 - 417

阅读排行榜

    1. android AsyncTask介绍(153980)
    2. Android之Handler用法总结(126729)
    3. Java synchronized详解(120947)
    4. Android之NDK开发(110671)
    5. Android之Adapter用法总结(76321)

评论排行榜

    1. Java synchronized详解(21)
    2. Android之Adapter用法总结(21)
    3. android AsyncTask介绍(17)
    4. Android之ContentProvider总结(12)
    5. Listview中显示不同的视图布局(10)

Powered by: 博客园	模板提供：沪江博客 Copyright ©2015 Devin Zhang

