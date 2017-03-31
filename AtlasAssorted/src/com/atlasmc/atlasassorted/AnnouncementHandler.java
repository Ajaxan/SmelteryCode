package com.atlasmc.atlasassorted;

import java.util.Collection;
import java.util.List;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AnnouncementHandler implements Runnable{
	JavaPlugin plugin;
	int count;
	
	public AnnouncementHandler(JavaPlugin plugin) {
		this.plugin = plugin;
		count = 1;
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 1200, 12000l);
	}
	
	
	@Override
	public void run() {
		if (count <= 1) {
			TextComponent topper = new TextComponent( "[Server Tip] " );
			topper.setColor( ChatColor.GREEN );
			TextComponent message = new TextComponent( "Your voice only travels so far. Try using a \"!\" before messages to shout to all players!" );
			message.setColor( ChatColor.YELLOW );
			topper.addExtra( message );
			topper.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Normal chat travels 200 blocks. Shouts can be heard everywhere.").color( ChatColor.GREEN ).create() ) );
			for(Player p : plugin.getServer().getOnlinePlayers()) {
				p.spigot().sendMessage(topper);
			}
			count++;
		}
		else if (count == 2) {
			TextComponent topper = new TextComponent( "[Server Tip] " );
			topper.setColor( ChatColor.GREEN );
			TextComponent message = new TextComponent( "Atlas has a resource island! Take the boat to Jagged Cay to get the most out of mining!" );
			message.setColor( ChatColor.YELLOW );
			topper.addExtra( message );
			topper.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("The boat to Jagged Cay can be found in Nauru!").color( ChatColor.GREEN ).create() ) );
			for(Player p : plugin.getServer().getOnlinePlayers()) {
				p.spigot().sendMessage(topper);
			}
			count++;
		}
		else if (count == 3) {
			TextComponent topper = new TextComponent( "[Server Tip] " );
			topper.setColor( ChatColor.GREEN );
			TextComponent message = new TextComponent( "Doors and Trap Doors lock automatically. Use them to protect your valuables!" );
			message.setColor( ChatColor.YELLOW );
			topper.addExtra( message );
			topper.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("This only works inside your town's territory!").color( ChatColor.GREEN ).create() ) );
			for(Player p : plugin.getServer().getOnlinePlayers()) {
				p.spigot().sendMessage(topper);
			}
			count++;
		}
		else if (count == 4) {
			TextComponent topper = new TextComponent( "[Server Tip] " );
			topper.setColor( ChatColor.GREEN );
			TextComponent message = new TextComponent( "Remember to trade in your ores for gems!" );
			message.setColor( ChatColor.YELLOW );
			topper.addExtra( message );
			topper.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Gems can be traded in all NPC towns and Nauru!").color( ChatColor.GREEN ).create() ) );
			for(Player p : plugin.getServer().getOnlinePlayers()) {
				p.spigot().sendMessage(topper);
			}
			count++;
		}
		else if (count == 5) {
			TextComponent topper = new TextComponent( "[Server Tip] " );
			topper.setColor( ChatColor.GREEN );
			TextComponent message = new TextComponent( "Want to set your home? Put down a bed and right click it! You will now spawn there after dying!" );
			message.setColor( ChatColor.YELLOW );
			topper.addExtra( message );
			topper.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Notice that the /home command no longer works.").color( ChatColor.GREEN ).create() ) );
			for(Player p : plugin.getServer().getOnlinePlayers()) {
				p.spigot().sendMessage(topper);
			}
			count++;
		}
		else if (count == 6) {
			TextComponent topper = new TextComponent( "[Server Tip] " );
			topper.setColor( ChatColor.GREEN );
			TextComponent message = new TextComponent( "Mention a staff member's name in chat to ping them!" );
			message.setColor( ChatColor.YELLOW );
			topper.addExtra( message );
			topper.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("For example, to ping Ajaxan: Just say \"Hey Ajaxan\"").color( ChatColor.GREEN ).create() ) );
			for(Player p : plugin.getServer().getOnlinePlayers()) {
				p.spigot().sendMessage(topper);
			}
			count++;
		}
		else if (count == 7) {
			TextComponent topper = new TextComponent( "[Server Tip] " );
			topper.setColor( ChatColor.GREEN );
			TextComponent message = new TextComponent( "All NPC port towns have ships to and from Telaria. They can be unlocked by visiting the NPC port towns" );
			message.setColor( ChatColor.YELLOW );
			topper.addExtra( message );
			topper.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Each leg of the journey will cost 80 gems.").color( ChatColor.GREEN ).create() ) );
			for(Player p : plugin.getServer().getOnlinePlayers()) {
				p.spigot().sendMessage(topper);
			}
			count++;
		}
		else if (count == 8) {
			TextComponent topper = new TextComponent( "[Server Tip] " );
			topper.setColor( ChatColor.GREEN );
			TextComponent message = new TextComponent( "Try out the new custom /help command!" );
			message.setColor( ChatColor.YELLOW );
			topper.addExtra( message );
			topper.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("If it's still the boring old help blame Denizens").color( ChatColor.GREEN ).create() ) );
			for(Player p : plugin.getServer().getOnlinePlayers()) {
				p.spigot().sendMessage(topper);
			}
			count++;
		}
		else if (count >= 9) {
			TextComponent topper = new TextComponent( "[Server Tip] " );
			topper.setColor( ChatColor.GREEN );
			TextComponent message = new TextComponent( "Turning in a quest item? Make sure you’re holding it in your main hand when you talk to the NPC!" );
			message.setColor( ChatColor.YELLOW );
			topper.addExtra( message );
			topper.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("NPCs will ignore you otherwise!").color( ChatColor.GREEN ).create() ) );
			for(Player p : plugin.getServer().getOnlinePlayers()) {
				p.spigot().sendMessage(topper);
			}
			count = 1;
		}
		
		
	}

}
