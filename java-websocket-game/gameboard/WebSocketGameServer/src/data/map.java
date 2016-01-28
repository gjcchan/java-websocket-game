package data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class map {
	
	private Integer[][] map;
	private List<Object>[][] items;
	
	private boolean blockDiscovered = false;
	private int newBlockX = 0;
	private int newBlockY = 0;
	
	public static final int _L = 0;
	public static final int _R = 1;
	public static final int _U = 2;
	public static final int _D = 3;
	
	public static final int _LU = 4;
	public static final int _LR = 5;
	public static final int _LD = 6;
	
	public static final int _RU = 7;
	public static final int _RD = 8;
	
	public static final int _UD = 9;
	
	public static final int _LRU = 10;
	public static final int _LRD = 11;
	public static final int _LUD = 12;
	public static final int _RUD = 13;
	public static final int _LRUD = 14;
		
	private static final Integer[] opt = {_L, _R, _U, _D, _LU, _LR, _LD, _RU, _RD, _UD, _LRU,  _LRD, _LUD, _RUD, _LRUD};
	//private final Integer[] L = {_L, _LU, _LR, _LD, _LRU, _LRD, _LUD, _LRUD};
	private static final Set<Integer> L = new HashSet<Integer>(Arrays.asList(new Integer[]  {_L, _LU, _LR, _LD, _LRU, _LRD, _LUD, _LRUD}));
	//private final Integer[] R = {_R, _LR, _RU, _RD, _LRU, _LRD, _RUD, _LRUD};
	private static final Set<Integer> R = new HashSet<Integer>(Arrays.asList(new Integer[] {_R, _LR, _RU, _RD, _LRU, _LRD, _RUD, _LRUD}));
	//private final Integer[] U = {_U, _LU, _RU, _UD, _LRU, _LUD, _RUD, _LRUD};
	private static final Set<Integer> U = new HashSet<Integer>(Arrays.asList(new Integer[] {_U, _LU, _RU, _UD, _LRU, _LUD, _RUD, _LRUD}));
	//private final Integer[] D = {_D, _LD, _RD, _UD, _LRD, _LUD, _RUD, _LRUD};
	private static final Set<Integer> D = new HashSet<Integer>(Arrays.asList(new Integer[] {_D, _LD, _RD, _UD, _LRD, _LUD, _RUD, _LRUD}));
	
	public map() {
		map = new Integer[100][100];
		map[0][0] = _RD;
	}
	public map(int x, int y) {
		map = new Integer[x][y];
		map[0][0] = _RD;
	}
	public void expandMap() {
		
	}
	public Integer[][] getMap() {
		return map;
	}
	public Boolean setMap(int val, int x, int y) {
		if(val >= 0 && val < opt.length) {
			if((x >= 0 && x < map.length) &&
			   (y >= 0 && y < map[0].length)){
					map[x][y] = val;
					return true;
			}
		}
		return false;
	}
	public Integer getTile(int x, int y) {
		if((x >= 0 && x < map.length) &&
		   (y >= 0 && y < map[0].length)){
			return map[x][y];
		}
		return (Integer) null;
	}
	public Boolean withinBounds(int x, int y) {
		if((x >= 0 && x < map.length) &&
			   (y >= 0 && y < map[0].length)){
				return false;
			}
			return null;
	}
	public void resetNewBlock() {
		blockDiscovered = false;
	}
	public Boolean checkNewBlock(){
		return blockDiscovered;
	}
	public int getNewBlockX() {
		return newBlockX;
	}
	public int getNewBlockY() {
		return newBlockY;
	}
 	public void generateTile(int x, int y) {
		ArrayList<Integer> options = new ArrayList<Integer>(Arrays.asList(opt));
		generateTile(options, x, y);
	}
 	public Boolean dirContains(char dir, int val) {
 		switch(dir) {
 		case 'L':
 			return L.contains(val);
 		case 'R':
 			return R.contains(val);
 		case 'U':
 			return U.contains(val);
 		case 'D':
 			return D.contains(val);
 		}
 		return false;
 	}
 	
 	public Boolean generateTile(char dir, int x, int y) {
 		switch(dir) {
 		case 'L':
 			return generateTile(new ArrayList(L), x, y);
 		case 'R':
 			return generateTile(new ArrayList(R), x, y);
 		case 'U':
 			return generateTile(new ArrayList(U), x, y);
 		case 'D':
 			return generateTile(new ArrayList(D), x, y);
 		}
 		return false;
 	}
 	
	public Boolean generateTile(ArrayList<Integer> options,  int x, int y) {
		//bounds checking
		if(x > -1 && x < map.length && y > -1 && y < map[0].length) {
			
			//check max left-bound
			if(x == 0) {
				System.out.println("Removing L");
				options = removeOptions("_L", options);
			} 
			else {
				if(map[x-1][y] != null) {
					//check left
					if(!R.contains(map[x-1][y])) {
						System.out.println("Removing L");
						options = removeOptions("_L", options);
					} else {
						System.out.println("Enforcing L");
						options = enforceOptions("_L", options);						
					}					
				}

			}
			//check max right-bound
			if(x == map.length-1) {
				options = removeOptions("_R", options);
			}
			else {
				if(map[x+1][y] != null) {
					//check right
					if(!L.contains(map[x+1][y])) {
						System.out.println("Removing R");
						options = removeOptions("_R", options);				
					} 
					else {
						System.out.println("Enforcing R");
						options = enforceOptions("_R", options);							
					}
				}

			}
			
			//check max upper-bound		
			if(y == 0) {
				options = removeOptions("_U", options);
			} 
			else {
				if(map[x][y-1] != null) {
					//check up
					if(!D.contains(map[x][y-1])) {
						System.out.println("Removing U");
						options = removeOptions("_U", options);
					} 
					else {
						System.out.println("Enforcing U");
						options = enforceOptions("_U", options);						
					}
				}
			
			}
			
			//check max lower-bound
			if(y == map[0].length-1) {
				options = removeOptions("_D", options);
			} 
			else {
				if(map[x][y+1] != null) {
					//check down
					if(!U.contains(map[x][y+1])) {
						System.out.println("Removing D");
						options = removeOptions("_D", options);					
					} 
					else {
						System.out.println("Enforcing D");
						options = enforceOptions("_D", options);									
					}
				}

			}

			if(options.size() == 0) {
				return false;
			}
			
			System.out.println("FINAL OPTS: " + options.toString() );
			map[x][y] = options.get(randomizer.getRandomInt(0, options.size()));
			blockDiscovered = true;
			newBlockX = x;
			newBlockY = y;
			return true;
		}	
		return false;
	}
	public int findNullPaths(int x, int y, String entrance) 
	{
		int pathToNulls = 0;
		if(map[x][y] == null) {
			return 0;
		}
		switch (map[x][y]) {
			case _L:		
			case _R:
			case _U:
			case _D:
				return 0;
			case _LU:
				if(entrance == "_L") {
					pathToNulls += findNullPaths(x, y-1, "_D");
				}
			case _LR:
				
			case _LD:				
			case _RU:
			case _RD:				
			case _UD:				
			case _LRU:
			case _LRD:
			case _LUD:
			case _RUD:
			case _LRUD:
							
		}
		return 0;
	}
	//Removes all rooms that doesn't contain a specific direction open
	private ArrayList<Integer> enforceOptions(String restriction, ArrayList<Integer> options) {
		//if i dont use a temp cache, the checker iterates through options while removing items from it at the same time, causing concurrency issues
		ArrayList<Integer> tempDict = new ArrayList<Integer>(options);
		if(restriction.equals("_L")) {
			for (Integer x : tempDict) {
				if(!L.contains(x)) {
					options.remove(Integer.valueOf(x));
				}
			}
		} 
		else if(restriction.equals("_R")) {
			for (Integer x : tempDict) {
				if(!R.contains(x)) {
					options.remove(Integer.valueOf(x));
				}
			}
		} else if(restriction.equals("_U")) {
			for (Integer x : tempDict) {
				if(!U.contains(x)) {
					options.remove(Integer.valueOf(x));
				}
			}
		}
		else if(restriction.equals("_D")) {
			for (Integer x : tempDict) {
				if(!D.contains(x)) {
					options.remove(Integer.valueOf(x));
				}
			}		
		}	
		return options;	
	}
	
	private ArrayList<Integer> removeOptions(String restriction, ArrayList<Integer> options) {
		options.remove(Integer.valueOf(_LRUD));
		
		if(restriction.equals("_L")) {
			options.remove(Integer.valueOf(_L));
			options.remove(Integer.valueOf(_LU));
			options.remove(Integer.valueOf(_LR));
			options.remove(Integer.valueOf(_LD));
			options.remove(Integer.valueOf(_LRU));
			options.remove(Integer.valueOf(_LRD));
			options.remove(Integer.valueOf(_LUD));			
		} 
		else if(restriction.equals("_R")) {
			options.remove(Integer.valueOf(_R));
			options.remove(Integer.valueOf(_LR));
			options.remove(Integer.valueOf(_RU));
			options.remove(Integer.valueOf(_RD));
			options.remove(Integer.valueOf(_LRU));
			options.remove(Integer.valueOf(_LRD));
			options.remove(Integer.valueOf(_RUD));	
			
		} else if(restriction.equals("_U")) {
			options.remove(Integer.valueOf(_U));
			options.remove(Integer.valueOf(_LU));
			options.remove(Integer.valueOf(_RU));
			options.remove(Integer.valueOf(_UD));
			options.remove(Integer.valueOf(_LRU));
			options.remove(Integer.valueOf(_LUD));
			options.remove(Integer.valueOf(_RUD));				
		}
		else if(restriction.equals("_D")) {
			options.remove(Integer.valueOf(_D));
			options.remove(Integer.valueOf(_LD));
			options.remove(Integer.valueOf(_RD));
			options.remove(Integer.valueOf(_UD));
			options.remove(Integer.valueOf(_LRD));
			options.remove(Integer.valueOf(_LUD));
			options.remove(Integer.valueOf(_RUD));				
		}
		
		return options;
	}
	
}
