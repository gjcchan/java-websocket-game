package network.extensions;
/* 
 * Base64 Encoder sourced from http://en.wikibooks.org/wiki/Algorithm_Implementation/Miscellaneous/Base64#Java
 * FALL BACK FUNCTION FOR >JAVA 1.5
 */
class Base64Encode {
	
    private final static String base64chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
 
    public static String encode(byte[] s) {
    	int remainder = s.length % 3;
    	String output = new String();
    	String padding = new String();
    	byte charVal1 = 0;
    	byte charVal2 = 0;
    	byte charVal3 = 0;
    	byte charVal4 = 0;

    	
    	if (remainder > 0) {
    	    for (; remainder < 3; remainder++) {
    		padding += "=";
    	    }
    	}
    	for(int c = 0; c < s.length; c+= 3) {
    		if(c > 0 && (c / 3 * 4) % 76 == 0) {
    			output += "\r\n";
    		}
    		// visualize using http://www.herongyang.com/encoding/Base64-Encoding-Algorithm.html
    		if(s.length-1 < c+1) {
        		charVal1 = (byte) ((s[c] >>> 2) & 0x3F);
        		charVal2 = (byte) ((s[c] & 0x3) << 4);
        		
        		output += base64chars.charAt(charVal1);
        		output += base64chars.charAt(charVal2);  			
    		}
    		else if(s.length-1 < c+2) {
        		charVal1 = (byte) ((s[c] >>> 2) & 0x3F);
        		charVal2 = (byte) (((s[c] & 0x3) << 4) | ((s[c+1] >>> 4) & 0xF));
        		charVal3 = (byte) ((s[c+1] & 0xF) << 2); 
        		
        		output += base64chars.charAt(charVal1);
        		output += base64chars.charAt(charVal2);
        		output += base64chars.charAt(charVal3);
    		} 
    		else {
        		charVal1 = (byte) ((s[c] >>> 2) & 0x3F);
        		charVal2 = (byte) (((s[c] & 0x3) << 4) | ((s[c+1] >>> 4) & 0xF));
        		charVal3 = (byte) (((s[c+1] & 0xF) << 2) | ((s[c+2] & 0xC0) >>> 6));
        		charVal4 = (byte) (s[c+2] & 0x3F); 
        		
        		output += base64chars.charAt(charVal1);
        		output += base64chars.charAt(charVal2);
        		output += base64chars.charAt(charVal3);
        		output += base64chars.charAt(charVal4);
    		}
    	}
    	return output.substring(0, output.length()) + padding;
    }
}
