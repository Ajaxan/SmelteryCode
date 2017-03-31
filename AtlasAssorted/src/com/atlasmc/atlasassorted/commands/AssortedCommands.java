package com.atlasmc.atlasassorted.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;


public class AssortedCommands implements CommandExecutor, Listener {
	JavaPlugin plugin;

	public AssortedCommands(JavaPlugin plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("purgatory")){
			if(args.length == 2) {
				
				Player badguy = plugin.getServer().getPlayer(args[1]);
				
				if(badguy != null) {
					ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
					Location loc = new Location(plugin.getServer().getWorld("Purgatory"), 0.5, 200.0, 0.5, -14.0f, 0.6f);
					badguy.teleport(loc);
					badguy.getInventory().clear();
					badguy.getInventory().addItem(new ItemStack(Material.WOOD_PICKAXE));					
					String purgatoryCommand = "questadmin give " + badguy.getName() + " Purgatory: " + args[0]+"000 Cobblestone";
					Bukkit.dispatchCommand(console, purgatoryCommand);
					String broadcastCommand = "broadcast " + badguy.getName() + " has been banished to Purgatory to gather "+ args[0]+"000 Cobblestone!";
					Bukkit.dispatchCommand(console, broadcastCommand);
					return true;
				}
			}
		}
		return false;
	}
	
	@EventHandler
	public void playerBreakBlockPurgatory(BlockBreakEvent e){
		if(e.getBlock().getType().equals(Material.OBSIDIAN) && e.getPlayer().getWorld().equals(plugin.getServer().getWorld("Purgatory"))) {
			ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
			if(item == null) {
				e.getPlayer().sendMessage(ChatColor.GRAY+"I'd recommend buying a pick from Zaxyl next time.");
				e.getPlayer().getInventory().addItem(new ItemStack(Material.WOOD_PICKAXE));
				e.setCancelled(true);
			}
		}
		
	}
}
