package com.itzilly.employeewelcomer;

/*
*
* EmployeeWelcomer by itzilly
* Created for a very small private Server
*
* */


import org.bukkit.plugin.java.JavaPlugin;

public final class EmployeeWelcomer extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Enabling EmployeeWelcomer by itzilly");

        getConfig().options().copyDefaults();
        saveDefaultConfig();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Disabling EmployeeWelcomer");
    }
}
