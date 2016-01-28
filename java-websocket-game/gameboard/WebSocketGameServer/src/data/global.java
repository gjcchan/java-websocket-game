package data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import objects.client;
import network.connectedTCP;

public class global {
	public static List<String> clientInput = new LinkedList<String>();
	public static List<String> clientOutput = new LinkedList<String>();
	public static HashMap<String, connectedTCP> sessions = new HashMap<String, connectedTCP>();
	public static HashMap<String, client> player = new HashMap<String, client>();
	public static HashMap<String, gameWorld> worlds = new HashMap<String, gameWorld>();
}
