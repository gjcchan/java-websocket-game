package network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import data.global;
import network.extensions.WebSocket;

public class connectedTCP implements Runnable {

	private Socket connection;
	private InputStream persistentInputStream;
	private DataOutputStream persistentOutputStream;
	private boolean terminated;
	private global worldData;
	
	public connectedTCP(Socket newConnection, global data)
	{
		connection = newConnection;
		worldData = data;
		//setup parameters
		System.out.println("CONNECTED TO: " + connection.getInetAddress());
		//connection.setSoTimeout(timeout);
		try {
			connection.setTcpNoDelay(true);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		
		terminated = true;
		try {
			persistentInputStream = connection.getInputStream();
			persistentOutputStream = new DataOutputStream(connection.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void run() {
		terminated = false;
		String msg;
		while(!terminated) {		
			try {	
				msg = WebSocket.getJSON(persistentInputStream);
				//check if socket is closed
				if(msg.equals(WebSocket._CLOSESTR)) {
					System.out.println(connection.getInetAddress() + " has disconnected");
					halt();
					connection.close();
					continue;
				}
				synchronized(worldData.clientInput) {				
					worldData.clientInput.add(connection.getInetAddress() + "==" + msg);
					worldData.clientInput.notify();
				}

			} catch (IOException e) {
				System.out.println(connection.getInetAddress() + " has error");
				halt();
			} catch (Exception e) {
				e.printStackTrace();
				halt();
			}	
		}		
	}
	public void halt() {
		terminated = true;
	}
	public void close() {
		try {
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String IP() {
		return connection.getInetAddress().toString().substring(1);
	}
	public void send(byte[] msg) throws IOException {
		persistentOutputStream.write(msg);
		persistentOutputStream.flush();
	}
}
