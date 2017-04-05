package com.atlasmc.atlasforgetech;

import java.util.HashMap;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class DurabilityListener implements Listener {

	public DurabilityListener(JavaPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		
	}
	
	@EventHandler
	public void durabilityloss(PlayerItemDamageEvent e){
		e.getPlayer().getServer().getLogger().info("event triggered");
		ItemStack item = e.getItem();
		ItemMeta meta = item.getItemMeta();
		if(meta != null) {
			List<String> lore = meta.getLore();
			if(lore != null && lore.size() > 0 && HiddenStringUtils.hasHiddenString(lore.get(0))) {
				String json = HiddenStringUtils.extractHiddenString(lore.get(0));
				e.getPlayer().getServer().getLogger().info(json);
				String[] listSplit = json.split("/");
				if(listSplit[0].equalsIgnoreCase("Durability")) {
					double data = 0;
					if (listSplit.length > 1) {
						e.getPlayer().getServer().getLogger().info(listSplit[0] + ": " + listSplit[1]);
						data = Double.parseDouble(listSplit[1]); 
						if(data >= 1) {
							if(!(Math.random()*data < 1.0)) {
								e.setCancelled(true);
							}
						} else {
							e.setDamage((int) (e.getDamage()*(1/data)));
						}
						
					}
				}
			}
		}
	}
}
