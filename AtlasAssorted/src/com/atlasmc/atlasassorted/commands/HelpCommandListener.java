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
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;

public class HelpCommandListener implements CommandExecutor {
	JavaPlugin plugin;
	String ip = "localhost";

	public HelpCommandListener(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {


		if(cmd.getName().equalsIgnoreCase("help")){
			if(sender instanceof Player) {
				Player player = (Player) sender;

				if(args.length == 0) {
					player.sendMessage(ChatColor.YELLOW + "------------------ "+ ChatColor.GREEN+"Atlas Help "+ChatColor.YELLOW+"------------------");

					TextComponent general = generalMessage();
					player.spigot().sendMessage(general);
					
					//TextComponent commands = commandMessage();
					//player.spigot().sendMessage(commands);

					TextComponent nations = nationsMessage();
					player.spigot().sendMessage(nations);

					TextComponent towns = townsMessage();
					player.spigot().sendMessage(towns);

					TextComponent websiteHelp = websiteMessage(); //links to website
					player.spigot().sendMessage(websiteHelp);
					
					TextComponent discordHelp = discordMessage(); //links to website
					player.spigot().sendMessage(discordHelp);

					TextComponent rulesHelp = rulesMessage(); // links to rules
					player.spigot().sendMessage(rulesHelp);



					player.sendMessage(ChatColor.YELLOW + "----------------------------------------------");
				} else if (args.length == 1) {
					if(args[0].equalsIgnoreCase("general")) {
						player.sendMessage(ChatColor.GREEN + "------------------ "+ ChatColor.YELLOW+"General Help "+ChatColor.GREEN+"------------------");

						TextComponent questsHelp = questsMessage();
						player.spigot().sendMessage(questsHelp);
						TextComponent duelsHelp = duelsMessage();
						player.spigot().sendMessage(duelsHelp);
						TextComponent worldHelp = worldMessage(); 
						player.spigot().sendMessage(worldHelp);
						TextComponent spawningHelp = spawnMessage(); 
						player.spigot().sendMessage(spawningHelp);
						
						TextComponent travelHelp = travelMessage(); 
						player.spigot().sendMessage(travelHelp);
						TextComponent magicHelp = magicMessage(); 
						player.spigot().sendMessage(magicHelp);
						TextComponent smelteryHelp = smelteryMessage(); 
						player.spigot().sendMessage(smelteryHelp);
						TextComponent breweryHelp = breweryMessage(); 
						player.spigot().sendMessage(breweryHelp);
						


						player.sendMessage(ChatColor.GREEN + "----------------------------------------------");
					}
					else if (args[0].equalsIgnoreCase("quests")) {
						player.sendMessage(ChatColor.AQUA + "------------------ "+ ChatColor.YELLOW+"General Help "+ChatColor.AQUA+"------------------");

						player.sendMessage(ChatColor.AQUA+"Finding Quests: " + ChatColor.RED + "NPCs " + ChatColor.WHITE + "with music notes on them have quests to offer!");
						player.sendMessage(ChatColor.AQUA+"Accepting Quests: " + ChatColor.WHITE + "First you must type the " + ChatColor.AQUA + "number " + ChatColor.WHITE + "of the quest you wish to do.\nNext you must Type " + ChatColor.AQUA + "\"Yes\" " + ChatColor.WHITE + "to accept the quest.");
						player.sendMessage(ChatColor.AQUA+"Doing Location Quests: " + ChatColor.WHITE + "If an " + ChatColor.RED + "NPC " + ChatColor.WHITE + "tells you to travel somewhere. You must enter a certain " + ChatColor.AQUA + "area" + ChatColor.WHITE + " to proceed on the quest.");
						player.sendMessage(ChatColor.AQUA+"Doing Item Quests: " + ChatColor.WHITE + "If an " + ChatColor.RED + "NPC " + ChatColor.WHITE + "tells you to bring an " + ChatColor.AQUA + "item" + ChatColor.WHITE + " to someone, you must be holding the " + ChatColor.AQUA + "item " + ChatColor.WHITE + "to turn it in.");

						player.sendMessage(ChatColor.AQUA + "----------------------------------------------");
					}
					else if (args[0].equalsIgnoreCase("duels")) {
						player.sendMessage(ChatColor.RED + "------------------ "+ ChatColor.YELLOW+"General Help "+ChatColor.RED+"------------------");

						player.sendMessage(ChatColor.RED+"/duel " + ChatColor.AQUA + "<player>" + ChatColor.WHITE + "- Use this command to duel a " + ChatColor.AQUA + "player.");
						player.sendMessage(ChatColor.RED+"/duel accept" + ChatColor.AQUA + "<player>" + ChatColor.WHITE + "- Accept a duel from " + ChatColor.AQUA + "<player>.");
						player.sendMessage(ChatColor.RED+"/duel deny" + ChatColor.AQUA + "<player>" + ChatColor.WHITE + "- Decline a duel from " + ChatColor.AQUA + "<player>.");

						player.sendMessage(ChatColor.RED + "----------------------------------------------");
					} 
					else if (args[0].equalsIgnoreCase("world")) {
						player.sendMessage(ChatColor.GREEN + "------------------- "+ ChatColor.YELLOW+"World Help "+ChatColor.GREEN+"-------------------");
						TextComponent atheraHelp = atheraMessage(); //display Athera information
						player.spigot().sendMessage(atheraHelp);
						TextComponent oceanusHelp = oceanusMessage(); //displays Oceanus information
						player.spigot().sendMessage(oceanusHelp);
						TextComponent nauruHelp = nauruMessage(); // displays Nauru information
						player.spigot().sendMessage(nauruHelp);
						player.sendMessage(ChatColor.GREEN + "----------------------------------------------");

					}
					else if (args[0].equalsIgnoreCase("athera")) {
						player.sendMessage(ChatColor.RED + "----------------------------------------------");
						player.sendMessage(ChatColor.RED+"Getting to Athera: " + ChatColor.WHITE + "Take the Ship with White Sails in Nauru");						
						player.sendMessage(ChatColor.RED+"What is Athera: " + ChatColor.WHITE + "Athera is the main player island.");
						player.sendMessage(ChatColor.RED+"Settling on Athera: " + ChatColor.WHITE + "Check the Nation and Town commands.");
						player.sendMessage(ChatColor.RED + "----------------------------------------------");

					}
					else if (args[0].equalsIgnoreCase("nauru")) {
						player.sendMessage(ChatColor.GOLD + "----------------------------------------------");
						player.sendMessage(ChatColor.GOLD+"Getting to Nauru: " + ChatColor.WHITE + "You can return to Nauru through the ship in Telaria which can be found at 500x 400z");						
						player.sendMessage(ChatColor.GOLD+"What is Nauru: " + ChatColor.WHITE + "Nauru is the spawn town where players can trade resources and get quests to complete.");
						player.sendMessage(ChatColor.GOLD+"Settling on Nauru: " + ChatColor.WHITE + "Players can't settle towns in Nauru or on the Nauru island. Try Settling on Athera.");
						player.sendMessage(ChatColor.GOLD + "----------------------------------------------");

					}
					else if (args[0].equalsIgnoreCase("oceanus")) {
						player.sendMessage(ChatColor.AQUA + "----------------------------------------------");
						player.sendMessage(ChatColor.AQUA+"Getting to Jagged Cay: " + ChatColor.WHITE + "Take the Ship with Blue Sails in Nauru");						
						player.sendMessage(ChatColor.AQUA+"What is Jagged Cay: " + ChatColor.WHITE + "Oceanus is the resource island. Come here to mine!");
						player.sendMessage(ChatColor.AQUA+"Settling on Jagged Cay: " + ChatColor.WHITE + "Players can't settle towns on Oceanus. Try Settling on Athera.");
						player.sendMessage(ChatColor.AQUA + "----------------------------------------------");

					}
					else if (args[0].equalsIgnoreCase("spawning")) {
						player.sendMessage(ChatColor.YELLOW + "----------------- "+ ChatColor.GREEN+"Spawning Help "+ChatColor.YELLOW+"------------------");
						player.sendMessage(ChatColor.YELLOW+"Initial Spawn: " + ChatColor.WHITE + "Player initially spawn on Old Auru, the Tutorial Island.");						
						player.sendMessage(ChatColor.YELLOW+"Default Spawn: " + ChatColor.WHITE + "Whenever a player dies, they will spawn in Nauru, the Spawn Island");
						player.sendMessage(ChatColor.YELLOW+"Bed Spawning: " + ChatColor.WHITE + "Once a player uses a bed that will be their new spawn point.");
						player.sendMessage(ChatColor.YELLOW + "----------------------------------------------");
					}
				}
			}
			return true;
		} 
		return false;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////
	//TODO//						Main Help														//
	//////////////////////////////////////////////////////////////////////////////////////////////////
	public TextComponent generalMessage() {
		TextComponent general = new TextComponent("[General]");
		TextComponent generalInfo = new TextComponent(" <- Click for general information about ");
		TextComponent atlasMention = new TextComponent("Atlas");
		general.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/help general" ) );
		general.setColor(ChatColor.GREEN);
		generalInfo.setColor(ChatColor.WHITE);
		atlasMention.setColor(ChatColor.GREEN);
		general.addExtra(generalInfo);
		general.addExtra(atlasMention);
		general.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click [General] to view general help!").create() ) );
		return general;
	}
	public TextComponent nationsMessage() {
		TextComponent nations = new TextComponent("[Nations]");
		TextComponent nationsInfo = new TextComponent(" <- Click for information about running ");
		TextComponent nationsMention = new TextComponent("Nations");
		nations.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/nation help" ) );
		nations.setColor(ChatColor.GOLD);
		nationsInfo.setColor(ChatColor.WHITE);
		nationsMention.setColor(ChatColor.GOLD);
		nations.addExtra(nationsInfo);
		nations.addExtra(nationsMention);
		
		nations.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click [Nations] to view nation help!").create() ) );
		return nations;
	}
	public TextComponent townsMessage() {
		TextComponent general = new TextComponent("[Towns]");
		TextComponent generalInfo = new TextComponent(" <- Click for information about running ");
		TextComponent atlasMention = new TextComponent("Towns");
		general.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/town help" ) );
		general.setColor(ChatColor.AQUA);
		generalInfo.setColor(ChatColor.WHITE);
		atlasMention.setColor(ChatColor.AQUA);
		general.addExtra(generalInfo);
		general.addExtra(atlasMention);
		
		general.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click [Towns] to view town help!").create() ) );
		return general;
	}
	public TextComponent websiteMessage() {
		TextComponent general = new TextComponent("[Website]");
		TextComponent generalInfo = new TextComponent(" <- Click to sign up for the website: "+ChatColor.YELLOW+"MC-Atlas.com");
		general.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/website" ) );
		general.setColor(ChatColor.YELLOW);
		generalInfo.setColor(ChatColor.WHITE);
		general.addExtra(generalInfo);
		
		general.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click [Website] to sign up for the website.").create() ) );
		return general;
	}
	public TextComponent discordMessage() {
		TextComponent general = new TextComponent("[Discord]");
		TextComponent generalInfo = new TextComponent(" <- Click to join our discord!");
		general.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "https://discord.gg/FtSsDsf" ) );
		general.setColor(ChatColor.BLUE);
		generalInfo.setColor(ChatColor.WHITE);
		general.addExtra(generalInfo);
		
		general.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click [Rules] to go to the rules page.").create() ) );
		return general;
	}
	public TextComponent rulesMessage() {
		TextComponent general = new TextComponent("[Rules]");
		TextComponent generalInfo = new TextComponent(" <- Click to view the rules");
		general.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "http://forums.mc-atlas.com/showthread.php?tid=21" ) );
		general.setColor(ChatColor.RED);
		generalInfo.setColor(ChatColor.WHITE);
		general.addExtra(generalInfo);
		
		general.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click [Rules] to go to the rules page.").create() ) );
		return general;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////
	//TODO//						General Help														//
	//////////////////////////////////////////////////////////////////////////////////////////////////
	public TextComponent questsMessage() {
		TextComponent general = new TextComponent("[Quests]");
		TextComponent generalInfo = new TextComponent(" <- Click for information about doing ");
		TextComponent atlasMention = new TextComponent("Quests");
		general.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/help quests" ) );
		general.setColor(ChatColor.AQUA);
		generalInfo.setColor(ChatColor.WHITE);
		atlasMention.setColor(ChatColor.AQUA);
		general.addExtra(generalInfo);
		general.addExtra(atlasMention);
		
		general.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click [Quests] to view quest help!").create() ) );
		return general;
	}
	public TextComponent duelsMessage() {
		TextComponent general = new TextComponent("[Duels]");
		TextComponent generalInfo = new TextComponent(" <- Click for general information on ");
		TextComponent atlasMention = new TextComponent("Duels");
		general.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/help duels" ) );
		general.setColor(ChatColor.RED);
		generalInfo.setColor(ChatColor.WHITE);
		atlasMention.setColor(ChatColor.RED);
		general.addExtra(generalInfo);
		general.addExtra(atlasMention);
		
		general.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click [Duels] to view dueling help!").create() ) );
		return general;
	}
	public TextComponent worldMessage() {
		TextComponent general = new TextComponent("[Worlds]");
		TextComponent generalInfo = new TextComponent(" <- Click for more information on ");
		TextComponent atlasMention = new TextComponent("Worlds");
		general.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/help world" ) );
		general.setColor(ChatColor.GREEN);
		generalInfo.setColor(ChatColor.WHITE);
		atlasMention.setColor(ChatColor.GREEN);
		general.addExtra(generalInfo);
		general.addExtra(atlasMention);
		
		general.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click [Worlds] to go to the Wiki!").create() ) );
		return general;
	}
	public TextComponent travelMessage() {
		TextComponent general = new TextComponent("[Travel]");
		TextComponent generalInfo = new TextComponent(" <- Click to go to the website to learn about ");
		TextComponent atlasMention = new TextComponent("Travel");
		general.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "http://mc-atlas.wikia.com/wiki/Ship_Travel" ) );
		general.setColor(ChatColor.DARK_GREEN);
		generalInfo.setColor(ChatColor.WHITE);
		atlasMention.setColor(ChatColor.DARK_GREEN);
		general.addExtra(generalInfo);
		general.addExtra(atlasMention);
		
		general.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click [Travel] to go to the Wiki!").create() ) );
		return general;
	}
	public TextComponent magicMessage() {
		TextComponent general = new TextComponent("[Magic]");
		TextComponent generalInfo = new TextComponent(" <- Click to go to the website to learn about ");
		TextComponent atlasMention = new TextComponent("Magic");
		general.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "http://mc-atlas.wikia.com/wiki/Magic" ) );
		general.setColor(ChatColor.LIGHT_PURPLE);
		generalInfo.setColor(ChatColor.WHITE);
		atlasMention.setColor(ChatColor.LIGHT_PURPLE);
		general.addExtra(generalInfo);
		general.addExtra(atlasMention);
		
		general.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click [Magic] to go to the Wiki!").create() ) );
		return general;
	}
	public TextComponent smelteryMessage() {
		TextComponent general = new TextComponent("[Smelting]");
		TextComponent generalInfo = new TextComponent(" <- Click to go to the website to learn about ");
		TextComponent atlasMention = new TextComponent("Smelting");
		general.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "http://mc-atlas.wikia.com/wiki/Smelting" ) );
		general.setColor(ChatColor.BLUE);
		generalInfo.setColor(ChatColor.WHITE);
		atlasMention.setColor(ChatColor.BLUE);
		general.addExtra(generalInfo);
		general.addExtra(atlasMention);
		
		general.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click [Smelting] to go to the Wiki!").create() ) );
		return general;
	}
	public TextComponent breweryMessage() {
		TextComponent general = new TextComponent("[Brewing]");
		TextComponent generalInfo = new TextComponent(" <- Click to go to the website to learn about ");
		TextComponent atlasMention = new TextComponent("Brewing");
		general.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "http://mc-atlas.wikia.com/wiki/Brewery" ) );
		general.setColor(ChatColor.GOLD);
		generalInfo.setColor(ChatColor.WHITE);
		atlasMention.setColor(ChatColor.GOLD);
		general.addExtra(generalInfo);
		general.addExtra(atlasMention);
		
		general.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click [Brewing] to go to the Wiki!").create() ) );
		return general;
	}
	public TextComponent spawnMessage() {
		TextComponent nations = new TextComponent("[Spawning]");
		TextComponent nationsInfo = new TextComponent(" <- Click for information about ");
		TextComponent nationsMention = new TextComponent("Spawn Points");
		nations.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/help spawning" ) );
		nations.setColor(ChatColor.YELLOW);
		nationsInfo.setColor(ChatColor.WHITE);
		nationsMention.setColor(ChatColor.YELLOW);
		nations.addExtra(nationsInfo);
		nations.addExtra(nationsMention);
		
		nations.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click [Nations] to view nation help!").create() ) );
		return nations;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////
	//TODO//						World Help														//
	//////////////////////////////////////////////////////////////////////////////////////////////////
	public TextComponent atheraMessage() {
		TextComponent general = new TextComponent("[Athera]");
		TextComponent generalInfo = new TextComponent(" <- Click to learn about ");
		TextComponent atlasMention = new TextComponent("Athera");
		general.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/help athera" ) );
		general.setColor(ChatColor.GREEN);
		generalInfo.setColor(ChatColor.WHITE);
		atlasMention.setColor(ChatColor.GREEN);
		general.addExtra(generalInfo);
		general.addExtra(atlasMention);
		
		general.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click [Athera] to view info on Athera!").create() ) );
		return general;
	}
	public TextComponent oceanusMessage() {
		TextComponent general = new TextComponent("[Oceanus]");
		TextComponent generalInfo = new TextComponent(" <- Click to learn about ");
		TextComponent atlasMention = new TextComponent("Oceanus");
		general.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/help oceanus" ) );
		general.setColor(ChatColor.AQUA);
		generalInfo.setColor(ChatColor.WHITE);
		atlasMention.setColor(ChatColor.AQUA);
		general.addExtra(generalInfo);
		general.addExtra(atlasMention);
		
		general.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click [Oceanus] to view info on Oceanus!").create() ) );
		return general;
	}
	public TextComponent nauruMessage() {
		TextComponent general = new TextComponent("[Nauru]");
		TextComponent generalInfo = new TextComponent(" <- Click to learn about ");
		TextComponent atlasMention = new TextComponent("Nauru");
		general.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/help nauru" ) );
		general.setColor(ChatColor.GOLD);
		generalInfo.setColor(ChatColor.WHITE);
		atlasMention.setColor(ChatColor.GOLD);
		general.addExtra(generalInfo);
		general.addExtra(atlasMention);
		
		general.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click [Nauru] to view info on Nauru!").create() ) );
		return general;
	}
	
}
