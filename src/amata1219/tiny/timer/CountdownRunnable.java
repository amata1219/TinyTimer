package amata1219.tiny.timer;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
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

			if(timer.getPrepareTime() > TinyTimer.getPlugin().getConfig().getInt("CountdownThreshold"))
				return;

			if(timer.getPrepareTime() == 0){
				timer.executeStartCommand();
				Util.sendTitle(timer, TinyTimer.getPlugin().getConfig().getString("StartMessage"));

				Sound sound = Sound.valueOf(TinyTimer.getPlugin().getConfig().getString("StartSound"));
				Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), sound, 1, 1));
				return;
			}

			Util.sendTitle(timer, TinyTimer.getPlugin().getConfig().getString("CountdownMessageColor") + String.valueOf(timer.getPrepareTime()));

			Sound sound = Sound.valueOf(TinyTimer.getPlugin().getConfig().getString("CountdownSound"));
			Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), sound, 1, 1));
			return;
		}

		timer.minusTime();

		if(timer.getTime() > 0)
			timer.updateBarProgress();

		timer.updateBarTitle();

		if(timer.getTime() > TinyTimer.getPlugin().getConfig().getInt("CountdownThreshold"))
			return;

		if(timer.getTime() == 0){
			Util.sendTitle(timer, TinyTimer.getPlugin().getConfig().getString("EndMessage"));

			Sound sound = Sound.valueOf(TinyTimer.getPlugin().getConfig().getString("EndSound"));
			Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), sound, 1, 1));

			timer.end();
			return;
		}

		Util.sendTitle(timer, TinyTimer.getPlugin().getConfig().getString("CountdownMessageColor") + String.valueOf(timer.getTime()));

		Sound sound = Sound.valueOf(TinyTimer.getPlugin().getConfig().getString("CountdownSound"));
		Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), sound, 1, 1));
	}

}
