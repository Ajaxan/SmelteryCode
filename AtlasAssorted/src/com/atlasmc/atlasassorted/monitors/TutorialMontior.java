package com.atlasmc.atlasassorted.monitors;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TutorialMontior implements Runnable{
	JavaPlugin plugin;
	
	public TutorialMontior(JavaPlugin plugin) {
		this.plugin = plugin;
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 20, 100l);
	}
	
	
	@Override
	public void run() {
		List<Player> players1 = plugin.getServer().getWorld("OldAuru").getPlayers();
		for(Player p: players1) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,300,0));
		}
		
	}

}
