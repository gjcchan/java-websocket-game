package data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dependencies.ServerLog;


public class gameWorld {
		
	public map gameMap;
		
	public HashMap<String, player> players = new HashMap<String, player>();
	
	public gameWorld() {
		gameMap = new map(100,100);
	}
	public Boolean move(String playerKey, String coords) {
		player playee = players.get(playerKey);
		ServerLog.write("POSX: " + playee.posX + " POSY: " + playee.posY + " " + gameMap.getTile(playee.posX, playee.posY) );
		//move left
		if(coords.charAt(0) == 'L') {
			//check for oob
			if(playee.posX == 0) {
				return false;
			}
			//check if current room allows moving left
			if(!gameMap.dirContains('L', gameMap.getTile(playee.posX,playee.posY) )) {
				ServerLog.write("CANNOT FIND ROOM: " + gameMap.getTile(playee.posX,playee.posY) );
				return false;
			}
			//check if left tile is generated, if not, generate
			if(gameMap.getTile(playee.posX-1, playee.posY) == null) {
				gameMap.generateTile('R',playee.posX-1, playee.posY);
			}
			playee.posX -= 1;
			return true;
		}
		//move right
		if(coords.charAt(0) == 'R') {
			//check for OOB
			if(gameMap.withinBounds(playee.posX, 0)) {
				return false;
			}			
			//check if current room allows moving right
			if(!gameMap.dirContains('R', gameMap.getTile(playee.posX,playee.posY))) {
				ServerLog.write("CANNOT FIND ROOM: " + gameMap.getTile(playee.posX,playee.posY));
				return false;
			}			
			//check if left tile is generated, if not, generate
			if(gameMap.getTile(playee.posX+1,playee.posY) == null) {
				gameMap.generateTile('L', playee.posX+1, playee.posY);
			}
			playee.posX += 1;
			return true;
		}
		//move up
		if(coords.charAt(0) == 'U') {
			//check for upperbound
			if(playee.posY == 0) {
				return false;
			}			
			//check if current room allows moving up
			if(!gameMap.dirContains('U',gameMap.getTile(playee.posX,playee.posY))) {
				return false;
			}	
			//check if upper tile is generated, if not, generate
			if(gameMap.getTile(playee.posX,playee.posY-1) == null) {
				gameMap.generateTile('D', playee.posX, playee.posY-1);
			}		
			playee.posY -= 1;
			return true;
		}
		//move down
		if(coords.charAt(0) == 'D') {
			//check for lower
			if( gameMap.withinBounds(0, playee.posY)) {
				return false;
			}			
			//check if current room allows moving up
			if(!gameMap.dirContains('D', gameMap.getTile(playee.posX,playee.posY))) {
				return false;
			}	
			//check if upper tile is generated, if not, generate
			if(gameMap.getTile(playee.posX,playee.posY+1) == null) {
				gameMap.generateTile('U', playee.posX, playee.posY+1);
			}		
			playee.posY += 1;
			return true;
		}
		return false;
	}

}
