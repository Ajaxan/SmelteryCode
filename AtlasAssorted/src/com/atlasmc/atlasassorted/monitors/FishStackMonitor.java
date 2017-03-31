package com.atlasmc.atlasassorted.monitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.atlasmc.atlasassorted.HiddenStringUtils;

public class FishStackMonitor implements Listener{
	JavaPlugin plugin;
	
	public FishStackMonitor(JavaPlugin plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler
	public void fishStack(InventoryClickEvent e){
		//plugin.getLogger().info("Invetory Clicked");
		if(e.getAction().equals(InventoryAction.PICKUP_HALF)) {
			//.getLogger().info("Right clicked Stack");
			if(e.getCurrentItem().getType().equals(Material.RAW_FISH)) {
				ItemStack item = e.getCurrentItem();
				short dur = 0;
				if(item.getDurability() == 0) {
					dur = 0;
				} else if (item.getDurability() == 1) {
					dur = 1;
				} else if (item.getDurability() == 2) {
					dur = 2;
				} else if (item.getDurability() == 3) {
					dur = 3;
				}
				if(item.hasItemMeta()) {
					if(item.getItemMeta().hasLore()) {
						//plugin.getLogger().info("Fish Had Meta and Lore");
						ItemMeta meta = item.getItemMeta();
						List<String> lore = meta.getLore();
						for(String line: lore) {
							if(line.contains("Epic")) {
								ItemStack fish = new ItemStack(Material.RAW_FISH,1,dur);
								ItemMeta fishMeta = fish.getItemMeta();
								fishMeta.setDisplayName("Large Fish");
								List<String> fishLore = new ArrayList<String>();
								fishLore.add(HiddenStringUtils.encodeString("[LargeFish]"));
								fishLore.add(ChatColor.DARK_PURPLE+"Epic");
								fishMeta.setLore(fishLore);
								fish.setItemMeta(fishMeta);
								e.setCurrentItem(fish);
							} 
							else if (line.contains("Legendary")) {
								ItemStack fish = new ItemStack(Material.RAW_FISH,1,dur);
								ItemMeta fishMeta = fish.getItemMeta();
								fishMeta.setDisplayName("Gigantic Fish");
								List<String> fishLore = new ArrayList<String>();
								fishLore.add(HiddenStringUtils.encodeString("[GiganticFish]"));
								fishLore.add(ChatColor.GOLD+"Legendary");
								fishMeta.setLore(fishLore);
								fish.setItemMeta(fishMeta);
								e.setCurrentItem(fish);
							}
							else {
								ItemStack fish = new ItemStack(Material.RAW_FISH,1,dur);
								ItemMeta fishMeta = fish.getItemMeta();
								List<String> fishLore = new ArrayList<String>();
								fishLore.add(HiddenStringUtils.encodeString("[CommonFish]"));
								fishMeta.setLore(fishLore);
								fish.setItemMeta(fishMeta);
								e.setCurrentItem(fish);
							}
						}
					}
				}
			}
		}
	}
}
