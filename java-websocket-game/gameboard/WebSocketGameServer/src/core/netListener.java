package core;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import data.global;
import network.connectedTCP;
import network.extensions.WebSocket;

public class netListener implements Runnable {
	byte[] buffer = new byte[1024];
	public int port;
	boolean terminate;
	global worldData;
	InputStream persistentInputStream;
	DataOutputStream persistentOutputStream;	
	ServerSocket listener;
	
	public netListener(int listenPort, global data) {
		try {
			port = listenPort;
			worldData = data;
			listener =  new ServerSocket(listenPort);
			listener.setReuseAddress(true);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		terminate = false;
		while(!terminate) {
			try{
			Socket newConnection = listener.accept();
			newConnection.setTcpNoDelay(true);
			persistentInputStream = newConnection.getInputStream();			
			persistentOutputStream = new DataOutputStream(newConnection.getOutputStream());
			String message = "";
			while(persistentInputStream.read(buffer) != -1 ) {
				message += new String(buffer);
				if(message.contains("\r\n\r\n") == true)
					break;
			}
			persistentOutputStream.writeBytes(WebSocket.generateReturnHeader(message));
			connectedTCP placeHolder = new connectedTCP(newConnection, worldData);	
			new Thread(placeHolder).start();
			global.sessions.put(newConnection.getInetAddress().toString().substring(1), placeHolder);
			} catch(Exception ex) {
				halt();			
			}
		}
		
	}
	public void halt() {
		terminate = true;
	}
	
	
}
