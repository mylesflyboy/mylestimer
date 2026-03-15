package com.myles.timer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TimerCommand implements CommandExecutor, TabCompleter {

    private final TimerManager timerManager;

    public TimerCommand(TimerManager timerManager) {
        this.timerManager = timerManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage("§c/timer <up|down|stop|resume|reset|reload>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "up" -> {
                timerManager.startUp();
                sender.sendMessage("§aCount-Up gestartet!");
            }
            case "down" -> {
                if (args.length != 2) {
                    sender.sendMessage("§c/timer down <seconds>");
                    return true;
                }
                try {
                    long sec = Long.parseLong(args[1]);
                    timerManager.startDown(sec);
                    sender.sendMessage("§aCount-Down gestartet!");
                } catch (NumberFormatException e) {
                    sender.sendMessage("§cUngültige Zahl!");
                }
            }
            case "stop" -> {
                timerManager.stop();
                sender.sendMessage("§eTimer pausiert!");
            }
            case "resume" -> {
                timerManager.resume();
                sender.sendMessage("§aTimer fortgesetzt!");
            }
            case "reset" -> {
                timerManager.reset();
                sender.sendMessage("§cTimer zurückgesetzt!");
            }
            case "reload" -> {
                timerManager.stop();
                sender.sendMessage("§bTimer neu geladen!");
            }
            default -> sender.sendMessage("§cUnbekannter Befehl!");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            List<String> commands = List.of("up", "down", "stop", "resume", "reset", "reload");
            for (String cmd : commands) {
                if (cmd.startsWith(args[0].toLowerCase())) completions.add(cmd);
            }
        }
        return completions;
    }
}