package chess.time;


public final class TimeUtils {
	
	private TimeUtils(){}
	
	//86400 seconds in an day, 3600 seconds in a hour, 60 seconds in a minute,  
	public static int IntegerToSeconds(int number){
		return ((number % 86400 ) % 3600 ) % 60;
	}
	
	public static int IntegerToMinutes(int number){
		return ((number % 86400 ) % 3600 ) / 60;
	}		
	
}
