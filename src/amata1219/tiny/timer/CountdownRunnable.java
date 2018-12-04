package amata1219.tiny.timer;

import org.bukkit.scheduler.BukkitRunnable;

public class CountdownRunnable extends BukkitRunnable {

	private int seconds;

	@Override
	public void run() {
		seconds--;
	}

}
