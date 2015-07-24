package com.instrument.wifi_ctrl1;

import android.os.Bundle;
import android.os.Message;
import android.os.Handler;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
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

import java.util.Observable;



public class UdpProcessActivity extends Activity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {		
		
        super.onCreate(savedInstanceState); //after this, the activity is created.
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
        super.onStop();//after this, this activity is no longer visible, it is now stopped.
    }
	
	@Override
    protected void onDestroy() {
        super.onDestroy();//after this.this activity is about to destroyed.
    }
	
}