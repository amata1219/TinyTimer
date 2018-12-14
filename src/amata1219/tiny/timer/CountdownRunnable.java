package amata1219.tiny.timer;

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
			timer.updateBarTitle();

			if(timer.getPrepareTime() > 5)
				return;

			if(timer.getPrepareTime() == 0){
				timer.executeStartCommand();
				Util.sendTitle(timer, ChatColor.AQUA + "START!");
				return;
			}

			Util.sendTitle(timer, ChatColor.AQUA + String.valueOf(timer.getPrepareTime()));
			return;
		}

		timer.minusTime();

		if(timer.getTime() > 0)
			timer.updateBarProgress();

		timer.updateBarTitle();

		if(timer.getTime() > 5)
			return;

		if(timer.getTime() == 0){
			Util.sendTitle(timer, ChatColor.AQUA + "FINISH!");
			timer.end();
			return;
		}

		Util.sendTitle(timer, ChatColor.AQUA + String.valueOf(timer.getTime()));
	}

}
