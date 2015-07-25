package com.instrument.wifi_ctrl1;

import android.os.Bundle;
import android.os.Message;
import android.os.Handler;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;  
import android.view.View.OnClickListener; 

import android.util.Log;

import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.OutputStream;  

import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.Inet4Address;
import java.net.InetSocketAddress;  
import java.net.ServerSocket;
import java.net.Socket;  
import java.net.SocketTimeoutException; 
import java.net.SocketException;
import java.net.DatagramPacket;  
import java.net.DatagramSocket;  
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.OutputStream;  
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class TcpProcessActivity extends Activity {
	private static WifiManager wifiManager;
	private static WifiInfo wifiInfo;
	private static int ipaddress;
	EditText srcip_txt=null;
	EditText dstip_txt=null;
	EditText sendcontent = null;
	EditText receivecontent = null;
	Button btn_send; 
	Button btn_receive;
	public int srcPortNum;
	
	Socket socket = null;  
    String sendBuffer = "";  
    String send_str;
    
    private static final int MAX_DATA_PACKET_LENGTH = 40;  
    private byte[] buffer = new byte[MAX_DATA_PACKET_LENGTH];  
    private DatagramPacket dataPacket;  
    private DatagramSocket udpSocket; 
	InetAddress serverAddress = null;
	private DatagramSocket serversocket = null;
	public Context context;
	private int recflag = 0;
	private int listenflag = 1;
	private String defaultPort;

	//存储所有客户端Socket连接对象
     private static List<Socket> mClientList = new ArrayList<Socket>(); 
	
	//线程池
	private ExecutorService mExecutorService;  
	//ServerSocket对象
	private ServerSocket mServerSocket;
	
	public Handler myHandler = new Handler() {	
		@Override  
		public void handleMessage(Message msg) {  
			if (msg.what == 0x11) {  
				Bundle bundle = msg.getData();	
				receivecontent.append(bundle.getString("msg")); 
			}
			else if (msg.what == 0x01) {  
				Bundle bundle = msg.getData();	
				receivecontent.append(bundle.getString("msg")); 
			}
		}  
  
	};	
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {		
		
        super.onCreate(savedInstanceState); //after this, the activity is created.
		
        setContentView(R.layout.wifi_udp);
        
        Intent intent = getIntent();
		defaultPort = intent.getStringExtra("defaultport");

		context = this.getApplicationContext();

		srcip_txt = (EditText)findViewById(R.id.srcip_edit);
		dstip_txt = (EditText)findViewById(R.id.dstip_edit);
		receivecontent = (EditText)findViewById(R.id.receive_content);
		sendcontent = (EditText)findViewById(R.id.send_content);
		btn_send = (Button) findViewById(R.id.btn_send);
		btn_receive = (Button) findViewById(R.id.btn_clear);		
		final EditText srcPort=(EditText)findViewById(R.id.srcport_edit);
		wifiManager = (WifiManager)TcpProcessActivity.this.getSystemService(Context.WIFI_SERVICE);		
		recflag = 1;

		if (!wifiManager.isWifiEnabled())
		{
			Toast.makeText(this.getApplicationContext(), "Please turn on wifi in setting", Toast.LENGTH_SHORT).show();
		}
		
		wifiInfo = getWifiConnectInfo();

		ipaddress = wifiInfo.getIpAddress();		
		
		srcip_txt.setText(ipIntToString(ipaddress));	

		new  tcpServiceThread().start();
		
		btn_send.setOnClickListener(new Button.OnClickListener()  
        {  
            @Override  
            public void onClick(View v)  
        	{  
        		if (srcPort.getText() == null ||  srcPort.getText().length() == 0)
				{
					Toast.makeText(context, "Please input valid para......", Toast.LENGTH_SHORT).show();
					return;
				}
        		srcPortNum = Integer.parseInt(srcPort.getText().toString());

        		SendTcpText();
        	}
        });  

		btn_receive.setOnClickListener(new Button.OnClickListener()  
        {  
            @Override  
            public void onClick(View v)  
        	{  
				receivecontent.setText("");
        	}
		});
    }
	
	public void getWifiManager(Context context)
    {
	    wifiManager = (WifiManager) context
	        .getSystemService(Context.WIFI_SERVICE);
    }
	
    public WifiInfo getWifiConnectInfo()
    {
	    wifiInfo = wifiManager.getConnectionInfo();
	    return wifiInfo;
    }

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
	public void SendTcpText() 
    {
        EditText dstIP=(EditText)findViewById(R.id.dstip_edit);
        EditText sendcontent=(EditText)findViewById(R.id.send_content);
		EditText srcPort=(EditText)findViewById(R.id.srcport_edit);
        EditText dstPort=(EditText)findViewById(R.id.dstport_edit);
        String message = sendcontent.getText().toString();

		if (srcPort.getText() == null || dstPort.getText() == null
			|| null == dstIP.getText() || srcPort.getText().length() == 0
		    || dstPort.getText().length() == 0 || dstIP.getText().length() == 0)
		{
			Toast.makeText(context, "Please input valid para......", Toast.LENGTH_SHORT).show();
			return;
		}
        
        TcpClient tcpET = new TcpClient(srcPort.getText().toString(), dstPort.getText().toString(),
										dstIP.getText().toString(), message, context, myHandler);
        tcpET.start();
    }


	@Override
    protected void onStart() {
        super.onStart();//after this, the activity is about to become visible.
    }

	@Override
    protected void onResume() {
        super.onResume();//after this, this activity has become visible(it is now resumed).
    }

	@Override
    protected void onPause() {
        super.onPause();//after this,another activity  is taking over focus (the activity is about to be paused).
	}

	@Override
    protected void onStop() {
		recflag = 0;		
        super.onStop();//after this, this activity is no longer visible, it is now stopped.
    }
	
	@Override
    protected void onDestroy() {
        super.onDestroy();//after this.this activity is about to destroyed.
    }

	
	class tcpServiceThread extends Thread{
	public void run(){
			try
			{
				mServerSocket = new ServerSocket(Integer.parseInt(defaultPort));
				mExecutorService = Executors.newCachedThreadPool();
				Socket client = null;
				while (true)
				{
					client = mServerSocket.accept(); 
					mClientList.add(client);
					mExecutorService.execute(new ThreadServer(client));
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	

	//每个客户端单独开启一个线程
     private class ThreadServer implements Runnable
     {
         private Socket            mSocket;
         private BufferedReader    mBufferedReader;
         private PrintWriter        mPrintWriter;
         private String            mStrMSG;
 
         public ThreadServer(Socket socket) throws IOException
         {
             this.mSocket = socket;			 
			 mBufferedReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
             mStrMSG = "user:"+this.mSocket.getInetAddress()+" come total:" + mClientList.size();
             sendMessage();
         }
         public void run()
         {
  			 Message msg = new Message();
  			 msg.what = 0x01;
  			 Bundle bundle = new Bundle();  
  			 bundle.clear();
			 int flag =0;
		
           	 while(0 == flag){
	             try
	             {
	                 while ((mStrMSG = mBufferedReader.readLine()) != null)
	                 {
	                     if (mStrMSG.trim().equals("exit"))
	                     {
	                         //当一个客户端退出时
	                         mClientList.remove(mSocket);
	                         mBufferedReader.close();
	                         mPrintWriter.close();
			         bundle.clear();

							 
							 bundle.putString("msg", mStrMSG); 
							 msg.setData(bundle);
							 myHandler.sendMessage(msg);
	                         mStrMSG = "user:"+this.mSocket.getInetAddress()+" exit total:" + mClientList.size();
	                         mSocket.close();
	                         sendMessage();
							 flag = 1;
	                         break;
	                     }
	                     else
	                     {
	                         mStrMSG =  mStrMSG;
	                         //sendMessage();
                                 bundle.clear();

							 bundle.putString("msg", mStrMSG); 
							 msg.setData(bundle);
							 myHandler.sendMessage(msg);
	                     }
	                 }
	             }
	             catch (IOException e)
	             {
	                 e.printStackTrace();
	             }
		 	}
         }
     
     
	   //发送消息给所有客户端
	     private void sendMessage() throws IOException
	     {
	         System.out.println(mStrMSG);
	         for (Socket client : mClientList)
	         {
	             mPrintWriter = new PrintWriter(client.getOutputStream(), true);
	             mPrintWriter.println(mStrMSG);
	         }
	     }

	}
	
}





