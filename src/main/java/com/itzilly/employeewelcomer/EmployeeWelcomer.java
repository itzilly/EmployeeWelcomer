package com.itzilly.employeewelcomer;

/*
*
* EmployeeWelcomer by itzilly
* Created for a very small private Server
* Intentionally created without attempting any good practices or logicalness
*
* */


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.beans.EventHandler;
import java.io.File;
import java.io.IOException;

public final class EmployeeWelcomer extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        File playerDir = new File(getDataFolder() + "/players");
        if (!playerDir.exists()) {
            playerDir.mkdir();
            System.out.println("Generated 'players' data folder");
        }

        System.out.println("Enabling EmployeeWelcomer by itzilly");
        getCommand("employeewelcomer").setExecutor(new EmployeeWelcomerCommand(this));
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Disabling EmployeeWelcomer");
    }
}
