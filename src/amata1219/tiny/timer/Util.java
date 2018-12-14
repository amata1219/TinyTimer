package amata1219.tiny.timer;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class Util {

	private static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	static{
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	public static String toText(int seconds){
		return format.format(seconds * 1000);
	}

	public static void sendTitle(Timer timer, String message){
		timer.getBossBar().getPlayers().forEach(player -> player.sendTitle(message, "", 10, 20, 10));
	}

}
