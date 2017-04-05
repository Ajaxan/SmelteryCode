package com.atlasmc.atlasforgetech;

import java.util.HashMap;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockListener implements Listener {
	HashMap<String,String> phrases;

	public BlockListener(JavaPlugin plugin, HashMap<String,String> phrases) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.phrases = phrases;
	}
	
	
	@EventHandler
	public void blockBreak(BlockBreakEvent e){
		Block block = e.getBlock();
		if(block.getType() == Material.BURNING_FURNACE) {
			if(Forge.remove(block)) {
				e.getPlayer().sendMessage(ChatColor.GRAY+phrases.get("BreakForge"));
			}
		}
		
		
	}
}