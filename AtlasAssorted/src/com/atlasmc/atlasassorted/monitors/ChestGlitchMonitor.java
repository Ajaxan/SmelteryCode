package com.atlasmc.atlasassorted.monitors;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ChestGlitchMonitor implements Listener, Runnable{
	JavaPlugin plugin;
	HashMap<String,Long> breakers;
	HashMap<String,Long> openers;
	
	public ChestGlitchMonitor(JavaPlugin plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 100, 100l);
		breakers = new HashMap<String,Long>();
		openers = new HashMap<String,Long>();
	}
	
	
	@EventHandler
	public void playerChestGlitch(PlayerInteractEvent e){
		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if(e.getClickedBlock().getType().equals(Material.CHEST)) {
				if(breakers.get(e.getPlayer().getName()) != null) {
					if(breakers.get(e.getPlayer().getName()) >= System.currentTimeMillis()) {
						e.setCancelled(true);
					}
				}
			} else if (e.getClickedBlock().getType().equals(Material.DARK_OAK_DOOR) ||e.getClickedBlock().getType().equals(Material.ACACIA_DOOR)
					|| e.getClickedBlock().getType().equals(Material.SPRUCE_DOOR) || e.getClickedBlock().getType().equals(Material.BIRCH_DOOR)
					|| e.getClickedBlock().getType().equals(Material.WOODEN_DOOR) || e.getClickedBlock().getType().equals(Material.JUNGLE_DOOR)) {
				if(openers.get(e.getPlayer().getName()) != null) {
					if(openers.get(e.getPlayer().getName()) >= System.currentTimeMillis()) {
						e.setCancelled(true);
					}
				} else {
					openers.put(e.getPlayer().getName(), System.currentTimeMillis()+500);
				}
			} 
		}
		
	}
	@EventHandler
	public void playerChestGlitch(BlockBreakEvent e){
		breakers.put(e.getPlayer().getName(), System.currentTimeMillis()+500);
	}
	
	@EventHandler
	public void blockVillagerTrades(PlayerInteractEntityEvent e){
		if(e.getRightClicked().getType().equals(EntityType.VILLAGER)) {
			e.setCancelled(true);
		}
	}


	@Override
	public void run() {
		Set<String> breakerlist = new HashSet<String>();
		for(String name: breakers.keySet()) {
			if(breakers.get(name) < System.currentTimeMillis()) {
				breakerlist.add(name);
			}
		}
		for(String name: breakerlist) {
			breakers.remove(name);
		}
	}

	
}
