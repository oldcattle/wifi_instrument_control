package com.instrument.wifi_ctrl1;

import android.os.Bundle;
import android.os.Message;
import android.content.Context;
import android.widget.Toast;
import android.os.Handler;

import java.net.Inet4Address;
import java.net.InetSocketAddress;  
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

import android.util.Log;


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



import java.io.IOException;

public class TcpClient extends Thread{
	
	public String m_serverIP;
	public String m_text;
	private int m_srcPort;
	private int m_dstPort;
	private Context context;
	private Handler myHandler;
	private static String TAG = "TcpClient";
	private BufferedReader mBufferedReader = null;
	private Socket mSocket = null;
	
	private PrintWriter mPrintWriter = null;
	private Thread mThread = null;
	private  String mStrMSG = "";
	
	public	TcpClient(String strSrcPort, String strDstport, String strServerIP, 
						String strText, Context context, Handler myHandler)
	{
		this.context = context;
		this.myHandler = myHandler;
		
		if (strSrcPort == null || strDstport== null || strServerIP == null ||strText == null
			||strSrcPort.isEmpty()|| strDstport.isEmpty() || strServerIP == "" || strText == "")
		{
			Toast.makeText(context, "Please input valid tcp para...", Toast.LENGTH_SHORT).show();
			return;
		}
		
		m_serverIP = strServerIP;
		m_text = strText;			
		m_srcPort = Integer.parseInt(strSrcPort);
		m_dstPort = Integer.parseInt(strDstport);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
		if (m_serverIP == null || m_text == null || m_serverIP.isEmpty()|| m_text.isEmpty())
		{
			System.out.println("Invalid para in tcp");
			return;
		}
		
		try
        {
            mSocket = new Socket(m_serverIP, m_dstPort);
            mBufferedReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            mPrintWriter = new PrintWriter(mSocket.getOutputStream(), true);
        } catch (Exception e)
        {
            Log.e(TAG, e.toString());
        }

		try
        {
            mPrintWriter.print(m_text);
            mPrintWriter.flush();
        } catch (Exception e)
        {
            Log.e(TAG, e.toString());
        }

		mThread = new Thread(mRunnable);
        mThread.start();

		
	}		

	private Runnable mRunnable = new Runnable()
    {
        public void run()
        {
        	Message msg = new Message();
			msg.what = 0x01;
			Bundle bundle = new Bundle();  
			bundle.clear();
			int flag = 0;

			while(flag == 0)
			{			
	            try
	            {
	                if ((mStrMSG = mBufferedReader.readLine()) != null)
	                {                        
	                	bundle.putString("msg", mStrMSG); 
	        			msg.setData(bundle);
	        			myHandler.sendMessage(msg);
						flag = 1;

						try
				        {
							mSocket.close();
							mBufferedReader.close();
							mPrintWriter.close();
						} catch (Exception e)
				        {
				            Log.e(TAG, e.toString());
				        }
	                }                    
	            } catch (Exception e)
	            {
	                Log.e(TAG, e.toString());
	            }
			}
        }
    };
	
} 
