package amata1219.tiny.timer;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class TinyTimer extends JavaPlugin {

	private static TinyTimer plugin;

	private HashMap<String, TabExecutor> commands = new HashMap<>();

	@Override
	public void onEnable(){
		plugin = this;

		TimerManager.load();

		saveDefaultConfig();

		commands.put("timer", new TimerCommand());
		commands.put("timer+", new TimerPlusCommand());
	}

	@Override
	public void onDisable(){
		TimerManager.unload();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		return commands.get(command.getName()).onCommand(sender, command, label, args);
	}

	public static TinyTimer getPlugin(){
		return plugin;
	}

}
