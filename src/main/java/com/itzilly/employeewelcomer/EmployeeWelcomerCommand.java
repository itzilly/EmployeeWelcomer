package com.itzilly.employeewelcomer;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeWelcomerCommand implements CommandExecutor {

    private EmployeeWelcomer main;
    private String prefix = ChatColor.YELLOW + "[Employee Welcomer]: ";

    public EmployeeWelcomerCommand(EmployeeWelcomer aMain) {
        main = aMain;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            // This is a player
            Player player = (Player) sender;

            if (args.length ==0) {
                player.sendMessage(prefix + ChatColor.YELLOW + "Use '/ewelc help' to view command usage!");
            }
            else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("add")) {
                    player.sendMessage(prefix + ChatColor.YELLOW + "Use '/ewelc help' to view command usage!");
                } else if (args[0].equalsIgnoreCase("help")) {
                    sendHelpMessage(player);
                } else {
                    player.sendMessage(prefix + ChatColor.RED + "Invalid argument");
                    player.sendMessage(prefix + ChatColor.YELLOW + "Use '/ewelc help' to view command usage!");
                }
            }
            else if (args.length == 2) {
                MCUUID mcuuidPlayer = new MCUUID(args[1]);
                if (mcuuidPlayer.isValidPlayer) {
                    File playerFile = new File(main.getDataFolder() + "/players/", mcuuidPlayer.raw_uuid + ".yml");
                    if (!playerFile.exists()) {
                        try {
                            playerFile.createNewFile();
                        } catch (IOException e) {
                            System.out.println("Can't load file");
                            e.printStackTrace();
                            return false;
                        }
                    }

                    YamlConfiguration modifyFile = YamlConfiguration.loadConfiguration(playerFile);

                    modifyFile.set("enable-broadcast-join-message", true);
                    modifyFile.set("broadcast-join-message", "This is the message that will be sent to all players when the target player joins!");

                    modifyFile.set("enable-player-join-message", false);
                    modifyFile.set("player-message", "This is the message that the joining player will see!");

                    modifyFile.set("enable-random-join-messages", false);
                    List<String> randomJoinMessagesList = new ArrayList<>();
                    randomJoinMessagesList.add(" ");
                    randomJoinMessagesList.add(" ");
                    randomJoinMessagesList.add(" ");
                    modifyFile.set("random-join-messages", randomJoinMessagesList);

                    modifyFile.set("enable-quit-message", true);
                    modifyFile.set("broadcast-quit-message", "This is the message that will be broadcasted to the server when the target player leaves the server!");

                    modifyFile.set("enable-random-quit-messages", false);
                    List<String> randomQuitMessagesList = new ArrayList<>();
                    randomQuitMessagesList.add(" ");
                    randomQuitMessagesList.add(" ");
                    randomQuitMessagesList.add(" ");
                    modifyFile.set("random-quit-messages", randomQuitMessagesList);

                    try {
                        modifyFile.save(playerFile);
                        player.sendMessage(prefix + ChatColor.YELLOW + "Generated configuration file for %".replace("%", args[1]));
                    } catch (IOException e) {
                        System.out.println("Error saving file");
                        e.printStackTrace();
                        return false;
                    }

                } else {
                    player.sendMessage(prefix + ChatColor.RED + "Unknown Player: '" + args[1] + "'");
                }
            }
            else {
                player.sendMessage(prefix + ChatColor.YELLOW + "Use '/ewelc help' to view command usage!");
            }
        }
        else {
            // Console
        }

        return false;
    }

    private void sendHelpMessage(Player player) {
        List<String> helpMessage = new ArrayList<>();
        helpMessage.add("");
        helpMessage.add(ChatColor.BOLD + "" + ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH + "==============================");
        helpMessage.add("");
        helpMessage.add(ChatColor.AQUA + "" + ChatColor.BOLD + "Employee Welcome Message Help");
        helpMessage.add(ChatColor.GRAY + " Version " + main.getConfig().getString("version"));
        helpMessage.add("");
        helpMessage.add(ChatColor.YELLOW + "" + ChatColor.BOLD + "Usage:" + ChatColor.RESET + ChatColor.YELLOW + " '/ewelc <arguments>'");
        helpMessage.add(ChatColor.YELLOW + "" + ChatColor.BOLD + "Arguments: ");
        helpMessage.add(ChatColor.YELLOW + "  - 'add <playername>'");
        helpMessage.add(ChatColor.AQUA + "       Generate player config file");
        helpMessage.add(ChatColor.YELLOW + "  - 'help' ");
        helpMessage.add(ChatColor.AQUA + "       Displays this nifty help message!");
        helpMessage.add("");
        helpMessage.add(ChatColor.BOLD + "" + ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH + "==============================");

        for (String line : helpMessage) {
            player.sendMessage(line);
        }
    }
}
