package com.itzilly.employeewelcomer;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.util.List;
import java.util.Random;

public class PlayerQuitListener implements Listener {

    private EmployeeWelcomer main;

    public PlayerQuitListener(EmployeeWelcomer aMain) {
        main = aMain;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        System.out.println("Player Quit: " + player.getDisplayName());

        File file = new File(main.getDataFolder() + "/players/" + player.getUniqueId() + "/yml");
        if (file.exists()) {
            YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(file);

            if (playerConfig.getBoolean("enable-random-quit-messages")) {
                Random rand = new Random();
                List<String> randomQuitMessageList = playerConfig.getStringList("random-quit-messages");
                int upperand = randomQuitMessageList.size();
                String message = randomQuitMessageList.get(rand.nextInt(upperand));
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.sendMessage(message);
                }
                return;
            }

            if (playerConfig.getBoolean("enable-quit-message")) {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.sendMessage(playerConfig.getString("broadcast-quit-message"));
                }
            }

        }

    }

}
