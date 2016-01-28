package dependencies;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.GregorianCalendar;


public class ServerLog {
	
	public static final String _LOGNAME = "server_log.txt";
		
	public static void write(String entry)
	{
		BufferedWriter writer = null;	
		System.out.println("[" + GregorianCalendar.getInstance().getTime() + "] " + entry);			
		try {
			writer = new BufferedWriter(new FileWriter(_LOGNAME, true));		
			writer.write("[" + GregorianCalendar.getInstance().getTime() + "] " + entry);
			writer.newLine();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

}
