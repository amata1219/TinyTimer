package amata1219.tiny.timer;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

public class TimerPlusCommand implements TabExecutor {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 0){
			sender.sendMessage(ChatColor.GRAY + "(TimerPlusCommand: /timer+ start [時間(s)] [準備時間(s)] \"[開始時に実行されるコマンド]\" \"[終了時実行コマンド]\"");
			return true;
		}else if(args[0].equalsIgnoreCase("start")){
			if(args.length < 6){
				sender.sendMessage(ChatColor.RED + "引数が足りません。");
				sender.sendMessage(ChatColor.GRAY + "(TimerPlusCommand: /timer+ start [name] [時間(s)] [準備時間(s)] \"[開始時に実行されるコマンド]\" \"[終了時実行コマンド]\"");
				return true;
			}

			String name = args[1];

			int time = 0, prepare = 0;
			try{
				time = Integer.valueOf(args[2]);
				prepare = Integer.valueOf(args[3]);
			}catch(NumberFormatException e){
				sender.sendMessage(ChatColor.RED + "時間と準備時間は半角数字で入力して下さい。");
				sender.sendMessage(ChatColor.GRAY + "(TimerPlusCommand: /timer+ start [name] [時間(s)] [準備時間(s)] \"[開始時に実行されるコマンド]\" \"[終了時実行コマンド]\"");
				return true;
			}

			if(time <= 0){
				sender.sendMessage(ChatColor.RED + "時間は自然数で入力して下さい。");
				sender.sendMessage(ChatColor.GRAY + "(TimerCommand: /timer start [時間(s)] [準備時間(s)] \"[開始時に実行されるコマンド]\" \"[終了時実行コマンド]\"");
				return true;
			}

			if(prepare < 0){
				sender.sendMessage(ChatColor.RED + "準備時間は0又は自然数で入力して下さい。");
				sender.sendMessage(ChatColor.GRAY + "(TimerCommand: /timer start [時間(s)] [準備時間(s)] \"[開始時に実行されるコマンド]\" \"[終了時実行コマンド]\"");
				return true;
			}

			StringBuilder builder = new StringBuilder();
			for(int i = 4; i < args.length; i++)
				builder.append(args[i] + " ");

			String s = builder.toString();
			String[] commands = s.substring(1, s.length() - 2).split("\" \"");
			if(commands.length != 2){
				sender.sendMessage(ChatColor.RED + "コマンドの記述が不正です。");
				sender.sendMessage(ChatColor.GRAY + "(TimerPlusCommand: /timer+ start [name] [時間(s)] [準備時間(s)] \"[開始時に実行されるコマンド]\" \"[終了時実行コマンド]\"");
				return true;
			}

			if(commands[0].startsWith("/"))
				commands[0] = commands[0].substring(1);

			if(commands[1].startsWith("/"))
				commands[1] = commands[1].substring(1);

			TimerManager.registerTimer(name, new Timer(name, time, prepare, commands[0].equalsIgnoreCase("none") ? null : commands[0], commands[1].equalsIgnoreCase("none") ? null : commands[1]));

			sender.sendMessage(ChatColor.AQUA + "タイマー(" + name + ")を実行しました。");

			TimerManager.getTimer(name).info(sender);
			return true;
		}else if(args[0].equalsIgnoreCase("pause")){
			if(args.length == 1){
				sender.sendMessage(ChatColor.RED + "タイマーの登録名を指定して下さい。");
				sender.sendMessage(ChatColor.GRAY + "(TimerPlusCommand: /timer+ pause [name])");
				return true;
			}

			if(!TimerManager.isExistTimer(args[1])){
				sender.sendMessage(ChatColor.RED + "指定されたタイマー(" + args[1] + ")は存在しません。");
				return true;
			}

			Timer timer = TimerManager.getTimer(args[1]);
			timer.reversePause();

			sender.sendMessage(ChatColor.AQUA + (timer.isPause() ? "タイマー(" + args[1] + ")を一旦停止しました。" : "タイマー(" + args[1] + ")を再び実行しました。"));
			return true;
		}else if(args[0].equalsIgnoreCase("cancel")){
			if(args.length == 1){
				sender.sendMessage(ChatColor.RED + "タイマーの登録名を指定して下さい。");
				sender.sendMessage(ChatColor.GRAY + "(TimerPlusCommand: /timer+ cancel [name])");
				return true;
			}

			if(!TimerManager.isExistTimer(args[1])){
				sender.sendMessage(ChatColor.RED + "指定されたタイマー(" + args[1] + ")は存在しません。");
				return true;
			}

			TimerManager.getTimer(args[1]).cancel();

			sender.sendMessage(ChatColor.AQUA + "タイマー(" + args[1] + ")をキャンセルしました。");
			return true;
		}else if(args[0].equalsIgnoreCase("end")){
			if(args.length == 1){
				sender.sendMessage(ChatColor.RED + "タイマーの登録名を指定して下さい。");
				sender.sendMessage(ChatColor.GRAY + "(TimerPlusCommand: /timer+ end [name])");
				return true;
			}

			if(!TimerManager.isExistTimer(args[1])){
				sender.sendMessage(ChatColor.RED + "指定されたタイマー(" + args[1] + ")は存在しません。");
				return true;
			}

			TimerManager.getTimer(args[1]).end();

			sender.sendMessage(ChatColor.AQUA + "タイマー(" + args[1] + ")を終了しました。");
			return true;
		}else if(args[0].equalsIgnoreCase("info")){
			if(args.length == 1){
				sender.sendMessage(ChatColor.RED + "タイマーの登録名を指定して下さい。");
				sender.sendMessage(ChatColor.GRAY + "(TimerPlusCommand: /timer+ info [name])");
				return true;
			}

			if(!TimerManager.isExistTimer(args[1])){
				sender.sendMessage(ChatColor.RED + "指定されたタイマー(" + args[1] + ")は存在しません。");
				return true;
			}

			TimerManager.getTimer(args[1]).info(sender);
			return true;
		}
		return true;
	}

}
