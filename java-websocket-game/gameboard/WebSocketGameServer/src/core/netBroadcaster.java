package core;

import java.io.IOException;

import objects.client;
import network.connectedTCP;
import network.extensions.WebSocket;
import data.global;
import data.player;

public class netBroadcaster implements Runnable {
	private global worldData;
	private boolean terminated;
	public final static int sleepTime = 1000; //ms
	
	public netBroadcaster(global data) {
		worldData = data;
	}
	
	public void run() {
		long start;
		String msg;
		terminated = false;
		while(!terminated) {
			try {
				synchronized(worldData.clientOutput) {
					worldData.clientOutput.wait();
					
					msg = worldData.clientOutput.get(0);
					worldData.clientOutput.remove(0);
					
					if(msg.charAt(0) == 'A') {
						broadcast( msg.substring(1).getBytes() );
					} else if(msg.charAt(0) == 'T') {
						broadcastToRoom( msg.substring(1, msg.indexOf('{')),
								msg.substring(msg.indexOf('{')).getBytes()
								);
					}
					else {
						ipSend( msg.substring(0, msg.indexOf('{')), 
							  msg.substring(msg.indexOf('{')).getBytes() );
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	public void halt() {
		terminated = true;
	}
	
	public void broadcast(byte[] data) {
		try {
			for(connectedTCP x : worldData.sessions.values()) {
				try {
					x.send(WebSocket.createPacket(data));
				} catch (IOException e) {
					worldData.player.remove(x.IP());
					x.halt();
					x.close();
					worldData.sessions.remove(x);			
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void broadcastToRoom(String key, byte[] msg) {
		for(String x : worldData.worlds.get(key).players.keySet()) {
			ipSend(x, msg);
		}
	}
	public void ipSend(String ip, byte[] msg) {
		try {
			worldData.sessions.get(ip).send(WebSocket.createPacket(msg));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
