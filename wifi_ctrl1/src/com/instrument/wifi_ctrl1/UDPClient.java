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

import java.io.IOException;

public class UDPClient extends Thread{
	
	public String m_serverIP;
	public String m_text;
	private int m_srcPort;
	private int m_dstPort;
	private Context context;
	private Handler myHandler;
	
	public	UDPClient(String strSrcPort, String strDstport, String strServerIP, 
						String strText, Context context, Handler myHandler)
	{
		this.context = context;
		this.myHandler = myHandler;
		
		if (strSrcPort == null || strDstport== null || strServerIP == null ||strText == null
			||strSrcPort.isEmpty()|| strDstport.isEmpty() || strServerIP == "" || strText == "")
		{
			Toast.makeText(context, "Please input valid udp para...", Toast.LENGTH_SHORT).show();
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
		int TIMEOUT = 100;
		DatagramSocket udpSocket = null;
		
		if (m_serverIP == null || m_text == null || m_serverIP.isEmpty()|| m_text.isEmpty())
		{
			System.out.println("Invalid para in udp");
			return;
		}
		
		byte[] bytesToSend = m_text.getBytes();
		try {
			InetAddress serverAddress = InetAddress.getByName(m_serverIP);			

			if(udpSocket==null){
				udpSocket = new DatagramSocket(null);
				udpSocket.setReuseAddress(true);
				udpSocket.bind(new InetSocketAddress(m_srcPort));
			}
			
			udpSocket.setSoTimeout(TIMEOUT);
			
			DatagramPacket sendPacket = new DatagramPacket(bytesToSend,bytesToSend.length,serverAddress,m_dstPort);
			udpSocket.send(sendPacket);
				
			udpSocket.close();
		} catch (SocketException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}

		listen();
	}		

	private void listen()
	{			
		DatagramSocket serversocket = null;
		byte data[] = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data, data.length);
		Message msg = new Message();
		msg.what = 0x01;
		Bundle bundle = new Bundle();  
		bundle.clear();

		try{
			if(serversocket==null){
				serversocket = new DatagramSocket(null);
				serversocket.setReuseAddress(true);
				serversocket.bind(new InetSocketAddress(m_srcPort));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			serversocket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		serversocket.close();
		
		String result = new String(packet.getData(), packet.getOffset(), packet.getLength());
		bundle.putString("msg", result); 
		msg.setData(bundle);
		myHandler.sendMessage(msg);	

		return;
	}
} 
