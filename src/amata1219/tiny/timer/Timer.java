package amata1219.tiny.timer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class Timer {

	private String name;

	private BukkitTask task;

	private final int t, p;

	private int time;
	private double progressPerSecond;
	private int prepare;

	private BossBar bar;

	private String start, end;

	private boolean pause;

	public Timer(int time, int prepare, String start, String end){
		this.time = time;
		this.t = time;
		this.progressPerSecond = 1.0 / time;
		this.prepare = prepare;
		this.p = prepare;
		this.start = start;
		this.end = end;

		this.bar = Bukkit.createBossBar(ChatColor.WHITE + "TinyTimer - Loading…", BarColor.WHITE, BarStyle.SOLID);
		bar.setProgress(1.0);
		Bukkit.getOnlinePlayers().forEach(player -> addPlayer(player));

		TimerManager.registerTimer(this.name = "TinyTimer(デフォルト)", this);

		task = new CountdownRunnable(this).runTaskTimer(TinyTimer.getPlugin(), 20, 20);
	}

	public Timer(String name, int time, int prepare, String start, String end){
		this.time = time;
		this.t = time;
		this.progressPerSecond = 1.0 / time;
		this.prepare = prepare;
		this.p = prepare;
		this.start = start;
		this.end = end;

		this.bar = Bukkit.createBossBar(ChatColor.WHITE + "TinyTimer - Loading…", BarColor.WHITE, BarStyle.SOLID);
		bar.setProgress(1.0);
		Bukkit.getOnlinePlayers().forEach(player -> addPlayer(player));

		TimerManager.registerTimer(this.name = name, this);

		task = new CountdownRunnable(this).runTaskTimer(TinyTimer.getPlugin(), 20, 20);
	}

	public void reversePause(){
		pause = !pause;
	}

	public boolean isPause(){
		return pause;
	}

	public void cancel(){
		task.cancel();
		bar.removeAll();

		TimerManager.unregisterTimer(name);
	}

	public void end(){
		executeEndCommand();
		cancel();
	}

	public int getTime(){
		return time;
	}

	public void minusTime(){
		time -= 1;
	}

	public int getPrepareTime(){
		return prepare;
	}

	public void minusPrepareTime(){
		prepare -= 1;
	}

	public boolean isPreparing(){
		return prepare > 0;
	}

	public BossBar getBossBar(){
		return bar;
	}

	public void updateBarProgress(){
		if(time == 1)
			bar.setProgress(0.0);
		else
			bar.setProgress(bar.getProgress() - progressPerSecond);
	}

	public void updateBarTitle(){
		bar.setTitle(ChatColor.WHITE + (isPreparing() ? Util.toText(prepare) + " (準備中)" : Util.toText(time)));
	}

	public void addPlayer(Player player){
		bar.addPlayer(player);
	}

	public void removePlayer(Player player){
		bar.removePlayer(player);
	}

	public String getStartCommand(){
		return start;
	}

	public void executeStartCommand(){
		if(start != null)
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), start);
	}

	public String getEndCommand(){
		return end;
	}

	public void executeEndCommand(){
		if(end != null)
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), end);
	}

	public void info(CommandSender sender){
		sender.sendMessage(ChatColor.AQUA + "タイマーの情報");
		sender.sendMessage("");
		sender.sendMessage(ChatColor.AQUA + "登録名: " + ChatColor.WHITE + name);
		sender.sendMessage(ChatColor.AQUA + "状態: " + ChatColor.WHITE + (isPreparing() ? "準備中" : (isPause() ? "停止中" : "実行中")));
		sender.sendMessage("");
		sender.sendMessage(ChatColor.AQUA + "時間(s): " + ChatColor.WHITE + t + ChatColor.GRAY + "(" + Util.toText(t) + ")");
		sender.sendMessage(ChatColor.GRAY + "残り時間(s): " + time + ChatColor.GRAY + "(" + Util.toText(time) + ")");
		sender.sendMessage(ChatColor.GRAY + "経過時間(s): " + (t - time) + ChatColor.GRAY + "(" + Util.toText(t - time) + ")");
		sender.sendMessage("");
		sender.sendMessage(ChatColor.AQUA + "準備時間(s): " + ChatColor.WHITE + p + ChatColor.GRAY + "(" + Util.toText(p) + ")");
		sender.sendMessage(ChatColor.GRAY + "残り準備時間(s): " + prepare + ChatColor.GRAY + "(" + Util.toText(prepare) + ")");
		sender.sendMessage(ChatColor.GRAY + "経過準備時間(s): " + (p - prepare) + ChatColor.GRAY + "(" + Util.toText(p -prepare) + ")");
		sender.sendMessage("");
		sender.sendMessage(ChatColor.AQUA + "開始時に実行されるコマンド: " + ChatColor.WHITE + (start != null ? start : "無し"));
		sender.sendMessage(ChatColor.AQUA + "終了時に実行されるコマンド: " + ChatColor.WHITE + (end != null ? end : "無し"));
	}

}
