package amata1219.tiny.timer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class CountdownRunnable extends BukkitRunnable {

	private Timer timer;

	public CountdownRunnable(Timer timer){
		this.timer = timer;
	}

	@Override
	public void run() {
		if(timer.isPause())
			return;

		if(timer.isPreparing()){
			timer.minusPrepareTime();

			if(timer.getPrepareTime() > 5)
				return;

			if(timer.getPrepareTime() == 0){
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), timer.getStartCommand());
				return;
			}

			Util.sendTitle(ChatColor.GREEN + String.valueOf(timer.getPrepareTime()));
			return;
		}

		timer.minusTime();

		if(timer.getTime() > 5)
			return;

		Util.sendTitle(ChatColor.GREEN + String.valueOf(""));
	}

}
