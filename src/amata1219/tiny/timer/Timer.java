package amata1219.tiny.timer;

import org.bukkit.boss.BossBar;
import org.bukkit.scheduler.BukkitTask;

public class Timer {

	private BukkitTask task;

	private int time;
	private int prepare;

	private BossBar bar;

	private String start, end;

	private boolean pause;

	public Timer(int time, int prepare, String start, String end){
		this.time = time;
		this.prepare = prepare;
		this.start = start;
		this.end = end;

		task = new CountdownRunnable(this).runTaskTimer(TinyTimer.getPlugin(), 0, 20);
	}

	public void start(){

	}

	public void pause(){
		pause = !pause;
	}

	public boolean isPause(){
		return pause;
	}

	public void cancel(){

	}

	public void end(){

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

	public String getStartCommand(){
		return start;
	}

	public String getEndCommand(){
		return end;
	}

}
