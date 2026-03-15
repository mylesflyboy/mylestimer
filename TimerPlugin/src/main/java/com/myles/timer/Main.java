package com.myles.timer;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private TimerManager timerManager;

    @Override
    public void onEnable() {
        timerManager = new TimerManager(this); // <- hier die Main-Instanz übergeben
        this.getCommand("timer").setExecutor(new TimerCommand(timerManager));
    }
}