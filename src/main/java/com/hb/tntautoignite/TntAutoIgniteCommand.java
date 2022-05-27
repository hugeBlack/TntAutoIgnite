package com.hb.tntautoignite;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class TntAutoIgniteCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if(args[0].equals("reload")){
            TntAutoIgnite.loadConfig();
            if(sender instanceof Player) sender.sendMessage("§aConfig Reload Completed");
            if(sender instanceof ConsoleCommandSender) Bukkit.getLogger().log(Level.WARNING,"Config Reloaded.");
            return true;
        }else{
            return false;
        }
    }
}