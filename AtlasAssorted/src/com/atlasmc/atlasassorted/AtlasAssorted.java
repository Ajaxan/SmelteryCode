package com.atlasmc.atlasassorted;

import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.block.Biome;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import com.atlasmc.atlasassorted.commands.HelpCommandListener;
import com.atlasmc.atlasassorted.commands.PurgatoryCommandListener;
import com.atlasmc.atlasassorted.commands.SoulBoundMonitor;
import com.atlasmc.atlasassorted.monitors.ChestGlitchMonitor;
import com.atlasmc.atlasassorted.monitors.EnderpearlMonitor;
import com.atlasmc.atlasassorted.monitors.FishStackMonitor;
import com.atlasmc.atlasassorted.monitors.TutorialMontior;


public class AtlasAssorted extends JavaPlugin {
	
    @Override
    public void onEnable() {
    	
    	Server server = getServer();
		ConsoleCommandSender console = server.getConsoleSender();
		console.sendMessage(ChatColor.DARK_AQUA + "["+this.getName()+"] ---------------------------------");
		console.sendMessage(ChatColor.DARK_AQUA + "["+this.getName()+"] Plugin has been enabled");
		console.sendMessage(ChatColor.DARK_AQUA + "["+this.getName()+"] ---------------------------------");
		console.sendMessage(ChatColor.DARK_AQUA + "["+this.getName()+"] Version: " + this.getDescription().getVersion());
		console.sendMessage(ChatColor.DARK_AQUA + "["+this.getName()+"] ---------------------------------");
    	
    	new TutorialMontior(this);
    	new AnnouncementHandler(this);
    	new ChestGlitchMonitor(this);
    	new FishStackMonitor(this);
    	new EnderpearlMonitor(this);
    	getCommand("website").setExecutor(new WebsiteSignUpListener(this));
    	getCommand("help").setExecutor(new HelpCommandListener(this));
    	getCommand("soulbound").setExecutor(new SoulBoundMonitor(this));
    	getCommand("purgatory").setExecutor(new PurgatoryCommandListener(this));
    	
    		
    	
    }
    // Fired when plugin is disabled
    @Override
    public void onDisable() {
    	Server server = getServer();
		ConsoleCommandSender console = server.getConsoleSender();
    	console.sendMessage(ChatColor.DARK_AQUA + "["+this.getName()+"] ---------------------------------");
		console.sendMessage(ChatColor.DARK_AQUA + "["+this.getName()+"] Plugin has been disabled");
		console.sendMessage(ChatColor.DARK_AQUA + "["+this.getName()+"] ---------------------------------");
    }
	
   
	
}