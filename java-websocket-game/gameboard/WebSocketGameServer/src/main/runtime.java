package main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import core.netBroadcaster;
import core.netListener;
import core.netParser;
import data.global;
import network.connectedTCP;
import network.extensions.WebSocket;

public class runtime {
	final static int listenPort = 7888;
	static global worldData = new global();
	
	public static void main(String[] args) {	
	
		netParser cmdParse = new netParser(worldData);
		new Thread(cmdParse).start();
		
		netListener connections = new netListener(listenPort, worldData);
		new Thread(connections).start();
		netBroadcaster broadcaster = new netBroadcaster(worldData);
		new Thread(broadcaster).start();
		
		/*try {
			ServerSocket listener =  new ServerSocket(listenPort);
			listener.setReuseAddress(true);
			while(true) {
				Socket newConnection = listener.accept();
				newConnection.setTcpNoDelay(true);
				persistentInputStream = newConnection.getInputStream();			
				persistentOutputStream = new DataOutputStream(newConnection.getOutputStream());
				String message = "";
				while(persistentInputStream.read(buffer) != -1 ) {
					message += new String(buffer);
					if(message.contains("\r\n\r\n") == true)
						break;2
				}
				persistentOutputStream.writeBytes(WebSocket.generateReturnHeader(message));
				connectedTCP placeHolder = new connectedTCP(newConnection, global);	
				new Thread(placeHolder).start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} */

		
	}
}
