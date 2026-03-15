package com.myles.timer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TimerManager {

    private final JavaPlugin plugin; // Main-Instanz
    private long time = 0;
    private boolean running = false;
    private boolean countUp = false;
    private int taskId = -1;

    public TimerManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void startUp() {
        stop();
        time = 0;
        countUp = true;
        startTask();
    }

    public void startDown(long seconds) {
        stop();
        time = seconds;
        countUp = false;
        startTask();
    }

    private void startTask() {
        running = true;

        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(
                plugin,  // <- hier jetzt die Instanz
                () -> {

                    if (!countUp && time <= 0) {
                        stop();
                        Bukkit.broadcastMessage("§l00:00:00");
                        return;
                    }

                    updateActionBar();

                    if (countUp) time++;
                    else time--;

                },
                0L, 20L
        );
    }

    public void stop() {
        if (taskId != -1) {
            Bukkit.getScheduler().cancelTask(taskId);
            taskId = -1;
        }
        running = false;
    }

    public void resume() {
        if (!running) startTask();
    }

    public void reset() {
        stop();
        time = 0;
        clearActionBar();
    }

    private void updateActionBar() {
        String formatted = formatTime(time);

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendActionBar("§l" + formatted);
        }
    }

    private void clearActionBar() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendActionBar("");
        }
    }

    private String formatTime(long totalSeconds) {
        if (totalSeconds < 0) totalSeconds = 0;
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}