package amata1219.tiny.timer;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TimerManager implements Listener {

	private static TimerManager manager;

	private HashMap<String, Timer> timers = new HashMap<>();

	private TimerManager(){

	}

	public static void load(){
		manager = new TimerManager();

		Bukkit.getPluginManager().registerEvents(manager, TinyTimer.getPlugin());
	}

	public static void unload(){
		HandlerList.unregisterAll(manager);
	}

	public static TimerManager getManager(){
		return manager;
	}

	public static void registerTimer(String name, Timer timer){
		manager.timers.put(name, timer);
	}

	public static void unregisterTimer(String name){
		manager.timers.remove(name);
	}

	public static boolean isExistTimer(String name){
		return manager.timers.containsKey(name);
	}

	public static Timer getTimer(String name){
		return manager.timers.get(name);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		manager.timers.values().forEach(timer -> timer.addPlayer(e.getPlayer()));
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		manager.timers.values().forEach(timer -> timer.removePlayer(e.getPlayer()));
	}

}
