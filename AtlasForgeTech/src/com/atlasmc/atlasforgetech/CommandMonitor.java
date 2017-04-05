package com.atlasmc.atlasforgetech;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandMonitor implements CommandExecutor {

	private JavaPlugin plugin;
	private ItemCreator itemCreator;
	List<ForgeRecipe> recipes;
	public CommandMonitor(JavaPlugin plugin,List<ForgeRecipe> recipes, ItemCreator itemCreator) {
		this.plugin = plugin;
		this.itemCreator = itemCreator;
		this.recipes = recipes;
	}
    	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("smeltery")) {
			if (args.length == 1) {
				sender.sendMessage(ChatColor.RED+"Check 1");
				if(sender instanceof Player) {
					sender.sendMessage(ChatColor.RED+"Check 2");
					Player player = (Player) sender;
					for(ForgeRecipe recipe: recipes) {
						player.sendMessage(ChatColor.RED+"Given ID: " + args[0]);
						player.sendMessage(ChatColor.RED+"Testing with ID: " + recipe.getTag());
						if(args[0].equals(recipe.getTag())) {
							player.sendMessage(ChatColor.RED+"Check 3");
							Material mat = recipe.getMaterial();
							String name = recipe.getName();
							ItemStack alloy = new ItemStack(mat);
							ItemMeta alloymeta = alloy.getItemMeta();
							List<String> lore = new ArrayList<String>();
							lore.add(HiddenStringUtils.encodeString(recipe.getTag()));
							if(recipe.getMaterial().equals(Material.DIAMOND)) {
								alloymeta.setDisplayName(ChatColor.GREEN+name + " Crystal");
								player.sendMessage(ChatColor.GREEN+"You have been given a " + name + " Crystal");
								player.sendMessage(ChatColor.RED+"Check 4 Crystal");
							} else {
								alloymeta.setDisplayName(ChatColor.GREEN+name + " Ingot");
								player.sendMessage(ChatColor.GREEN+"You have been given a " + name + " Ingot");
								player.sendMessage(ChatColor.RED+"Check 4 Ingot");
							}
							alloymeta.setLore(lore);
							alloy.setItemMeta(alloymeta);
							player.getInventory().addItem(alloy);
							return true;
						}
					}
				}
				if(sender instanceof Player) {
					Player player = (Player) sender;
					player.sendMessage(ChatColor.RED+"Invalid Tag ID");
					return true;
				} else {
					sender.sendMessage(ChatColor.RED+"This is not a console command!");
				}
			}	
		}
		return false;
	}
}