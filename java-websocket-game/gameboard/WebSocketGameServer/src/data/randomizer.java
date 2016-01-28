package data;

public class randomizer {
	public static int getRandomInt(int min, int max) {
		return (int) Math.abs((Math.random() * max) + min);
	}
	public static double getRandomDouble(double min, double max) {
		return (Math.random() * max) + min;
	}
	
}
