package com.itzilly.employeewelcomer;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.util.List;
import java.util.Random;

public class PlayerJoinListener implements Listener {

    private EmployeeWelcomer main;

    public PlayerJoinListener(EmployeeWelcomer aMain) {
        main = aMain;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        System.out.println("Player joined: " + player.getDisplayName());
        File file = new File(main.getDataFolder() + "/players/" + player.getUniqueId().toString().replace("-", "") + ".yml");
        System.out.println(file.getAbsolutePath());
        if (file.exists()) {
            System.out.println("File exists!");
            YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(file);

            if (playerConfig.getBoolean("enable-player-join-message")) {

                if (playerConfig.getBoolean("enable-random-join-messages")) {
                    Random rand = new Random();
                    List<String> randomJoinMessagesList = playerConfig.getStringList("random-join-messages");
                    int upperbound = randomJoinMessagesList.size();
                    String randomMessage = randomJoinMessagesList.get(rand.nextInt(upperbound));
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        if (!(onlinePlayer.getUniqueId() == player.getUniqueId())) {
                            onlinePlayer.sendMessage(randomMessage);
                        }

                    }
                }
            }

            if (playerConfig.getBoolean("enable-broadcast-join-message")) {
                e.setJoinMessage("");

                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.sendMessage(playerConfig.getString("broadcast-join-message"));
                }
                return;



            }
        } else {
            return;
        }
    }
}