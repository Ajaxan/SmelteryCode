package com.atlasmc.atlasforgetech;

import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;

public class ForgeUpdater implements Runnable {

	@SuppressWarnings("unused")
	private JavaPlugin plugin;
	@SuppressWarnings("unused")
	private int taskNumber;


	public ForgeUpdater(JavaPlugin plugin) {
		this.plugin = plugin;
		taskNumber = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 20l, 1180l);
	}

	List<Forge> forgeList = Forge.getForgeList();
	public void run() {
		for (Forge forge : forgeList) {
			if(forge.onUpdate()){
				forge.furnace.setBurnTime((short) 1200);
			}
		}
	}
}
