package com.atlasmc.atlasassorted.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.atlasmc.atlasassorted.HiddenStringUtils;

public class SoulBoundMonitor implements Listener, CommandExecutor {
	JavaPlugin plugin;
	public final Map<String,List<ItemStack>> keepOnDeath = new HashMap<String, List<ItemStack>>();
	

	public SoulBoundMonitor(JavaPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}


	@EventHandler
	public void playerDeath(PlayerDeathEvent e){
		plugin.getLogger().info("Player Died");
		List<ItemStack> noRemove = new ArrayList<ItemStack>();
		List<ItemStack> drops = e.getDrops();
		for(ItemStack item: drops) {
			plugin.getLogger().info("Item: "+ item.getType().name());
			ItemMeta meta = item.getItemMeta();
			if(meta != null) {
				plugin.getLogger().info("Had Meta");
				List<String> lore = meta.getLore();
				if(lore != null) {
					plugin.getLogger().info("Had Lore");
					for(String loreLine: lore) {
						if(HiddenStringUtils.hasHiddenString(loreLine)) {
							
							String json = HiddenStringUtils.extractHiddenString(loreLine);
							plugin.getLogger().info("Had hidden Lore: " + json);
							if(json.equals("[Soulbound]")) {
								plugin.getLogger().info(item.getType().name() + " is Soulbound");
								noRemove.add(item);
							}
							break;
						}

					}
					
					
				}

			}
		}
		keepOnDeath.put(e.getEntity().getName(), noRemove);
		e.getDrops().removeAll(noRemove);
	}
	
	@EventHandler
	public void playerRespawn(PlayerRespawnEvent e){
		for(String name: keepOnDeath.keySet()) {
			if(e.getPlayer().getName().equals(name)) {
				for(ItemStack item: keepOnDeath.get(name)) {
					e.getPlayer().getInventory().addItem(item);
				}
				keepOnDeath.remove(name);
			}
		}
		
	}


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		
			if(cmd.getName().equalsIgnoreCase("soulbound")){
				if(sender instanceof Player) {
					Player player = (Player) sender;
					ItemStack item = player.getInventory().getItemInMainHand();
					if(item == null) {
						sender.sendMessage(ChatColor.DARK_RED+"You must have an item in your main hand to do this.");
						return true;
					}
					
					ItemMeta meta = item.getItemMeta();
					List<String> lore;
					if(meta.getLore() == null) {
						lore = new ArrayList<String>();
					} else {
						lore = meta.getLore();
					}
					lore.add(ChatColor.DARK_PURPLE+"Soulbound");
					lore.add(HiddenStringUtils.encodeString("[Soulbound]"));
					meta.setLore(lore);
					item.setItemMeta(meta);
					sender.sendMessage(ChatColor.GRAY+"Item is now Soulbound.");
					return true;
				} else {
					sender.sendMessage(ChatColor.DARK_RED+"Must be a player to do this.");
				}
			}
		return false;
	}
}