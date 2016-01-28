package core;

import objects.client;
import JSON.JSONException;
import JSON.JSONObject;
import data.gameWorld;
import data.global;
import data.player;
import dependencies.ServerLog;

public class netParser implements Runnable {
	private global worldData;
	private boolean terminated;
	
	public netParser(global data) {
		worldData = data;
	}
	public synchronized void run() {
		terminated = false;
		String data;
		String ip;
		while(!terminated) {		
			try {
				synchronized(worldData.clientInput) {
					worldData.clientInput.wait();
					data = worldData.clientInput.get(0);
					worldData.clientInput.remove(0);
				}
				JSONObject obj = new JSONObject(data.substring(data.indexOf("==")+2));
				ip = data.substring(1, data.indexOf("=="));
				try{
					processInput( obj.getString("action"), obj.getString("key"), ip);

				
				} catch(JSONException e) {
					ServerLog.write("bad packet from: " +ip);
					continue;
					//consider kicking user
				}

			} catch (InterruptedException e) {
				halt();
			}
		}
	}
		
	public void halt() {
		terminated = true;
	}
	
	protected void processInput(String action, String key, String ip) {
		
		if(!worldData.player.containsKey(ip)) {
			if(action.equals("connect")) {
				worldData.player.put(ip, new client(key) );
				if(!worldData.worlds.containsValue(key)) {
					worldData.worlds.put(key, new gameWorld() );
				}
				worldData.worlds.get(key).players.put(ip, new player());
				sendOut(ip+"{ \"status\" : \"OK\"}");
			}
			return;
		}		
		if(action.equals("move")) {
			boolean result = worldData.worlds.get(worldData.player.get(ip).gameWorld).move(ip, key);
			if(result == true) {
				sendOut(ip+"{ \"status\" : \"OK\"}");
				//update map for all players in server	
				gameWorld temp = worldData.worlds.get(worldData.player.get(ip).gameWorld);
				if(temp.gameMap.checkNewBlock() == true) {
					temp.gameMap.resetNewBlock();
					sendOut("T" + worldData.player.get(ip).gameWorld 
							+"{ \"status\" : \"newblock\",  \"x\" : \"" 
							+ temp.gameMap.getNewBlockX()  + "\", "
							+ "\"y\" : \"" +  temp.gameMap.getNewBlockY() + "\" ,\"blocktype\": \""
							+ temp.gameMap.getTile(temp.gameMap.getNewBlockX(),temp.gameMap.getNewBlockY()) + "\"}");

				}
			} else {
				sendOut(ip+"{ \"status\" : \"NO\"}");
			}
		}
		
	}
	private void sendOut(String msg) {
		synchronized(worldData.clientOutput) {
			worldData.clientOutput.add(msg);
			worldData.clientOutput.notify();
		}
	}
	
}
