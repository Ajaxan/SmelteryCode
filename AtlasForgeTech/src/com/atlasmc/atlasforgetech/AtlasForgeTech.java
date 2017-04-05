package com.atlasmc.atlasforgetech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

import com.atlasmc.atlasforgetech.Attributes.*;


/**
 * Welcome to AtlasForgeTech! This is where we add the actual armor and weapons that
 * come out of crafting with our new materials. It isn't simply enough to add the
 * materials to the game, we also have to make then turn into something in a crafting
 * table!
 * 
 * To do that, the process is unfortunately, not easy. We can't simply add recipes for
 * our new items. This is because all materials for a recipe, must be vanilla items.
 * This means, we can't use a custom iron ingot such as our "steel ingot" in a recipe
 * as it isn't a vanilla item.
 * 
 * Instead of making recipes though, we simply alter the outcome on the crafting table
 * using the CraftItemEvent(). 
 * @author Ajaxan | Josh
 *
 */
public class AtlasForgeTech extends JavaPlugin {
	
	List<ForgeRecipe> recipes = new ArrayList<ForgeRecipe>();
	Configuration config;
	HashMap<String,String> phrases;
	
    @Override
    public void onEnable(){
    	this.saveDefaultConfig();
    	Server server = getServer();
		ConsoleCommandSender console = server.getConsoleSender();
		console.sendMessage(ChatColor.GOLD + "["+this.getName()+"] ---------------------------------");
		console.sendMessage(ChatColor.GOLD + "["+this.getName()+"] Plugin has been enabled");
		console.sendMessage(ChatColor.GOLD + "["+this.getName()+"] ---------------------------------");
		console.sendMessage(ChatColor.GOLD + "["+this.getName()+"] Version: " + this.getDescription().getVersion());
		console.sendMessage(ChatColor.GOLD + "["+this.getName()+"] ---------------------------------");
		console.sendMessage(ChatColor.GOLD + "["+this.getName()+"] ---------------------------------" + server.getClass().getPackage().getName());
		if(!loadConfig()) {
			console.sendMessage(ChatColor.RED + "["+this.getName()+"] ** Config failed to load correctly **");
		}
		ItemCreator itemCreator = new ItemCreator(this, recipes);
		new RecipeListener(this, recipes);
		getCommand("smeltery").setExecutor(new CommandMonitor(this, recipes, itemCreator));
		new PlayerListener(this, phrases, ForgeRecipe.getPossibleIngredients(), ForgeRecipe.getSpecialIngredients(), recipes); // Requires Config to already be loaded
		new BlockListener(this,phrases);
		new DurabilityListener(this);
		new ForgeUpdater(this);
    }
 
    @Override
    public void onDisable() {
    	Server server = getServer();
		ConsoleCommandSender console = server.getConsoleSender();
    	console.sendMessage(ChatColor.GOLD + "["+this.getName()+"] ---------------------------------");
		console.sendMessage(ChatColor.GOLD + "["+this.getName()+"] Plugin has been disabled");
		console.sendMessage(ChatColor.GOLD + "["+this.getName()+"] ---------------------------------");
    }
    
    public boolean loadConfig() {
    	
    	Server server = getServer();
		ConsoleCommandSender console = server.getConsoleSender();
    	
    	config = this.getConfig();
    	ConfigurationSection configSection = config.getConfigurationSection("recipes");
		if (configSection != null) {
			//int count = 0;
			for (String recipeId : configSection.getKeys(false)) {
				//if(count >= 5) {
				//	break;
				//}
				String name = configSection.getString(recipeId + ".name");
				String tag = configSection.getString(recipeId + ".tag");
				String item = configSection.getString(recipeId + ".baseitem");
				int time = configSection.getInt(recipeId + ".smelttime");
				Material baseItem = Material.getMaterial(item);
				if(baseItem == null) {
					console.sendMessage(ChatColor.RED + "["+this.getName()+"] Loading the Recipe with id: '" + recipeId + "' failed!");
					continue;
				}
				double durability = configSection.getDouble(recipeId + ".durabilitymultiplier");
				int mResist = configSection.getInt(recipeId + ".magicresistance");
				ForgeRecipe recipe = new ForgeRecipe(name, tag, baseItem, durability, mResist, time);
				ForgeRecipe.setPossibleIngredients(parsePossibleIngredients());
				ForgeRecipe.setRecipeHintWords(parseRecipeHints());
				ForgeRecipe.setSpecialIngredients(parseSpecialIngredients());
				ForgeRecipe.setHintsEnabled(config.getBoolean("EnableHints"));
				phrases = parsePhrases();
				List<String> ingredients = configSection.getStringList(recipeId + ".ingredients");
				recipe.setIngredients(parseIngredientsList(ingredients));
				recipe.setEffects(parseEffectsList(configSection,recipeId));
				recipe.setSlots(parseSlotsList(configSection,recipeId));
				
				
				
				if (recipe.isValid()) {
					recipes.add(recipe);
					console.sendMessage(ChatColor.GOLD + "["+this.getName()+"] Recipe with ID: "+recipeId+" loaded successfuly!");
					console.sendMessage(ChatColor.GOLD + "["+this.getName()+"] Name: "+recipe.getName()+", Tag: "+recipe.getTag()+", Base Item: "+recipe.getMaterial().name()+", Durability Increase: "+recipe.getDurability() +", Smelt Time: "+recipe.getTime());
					
				} else {
					console.sendMessage(ChatColor.RED + "["+this.getName()+"] Loading the Recipe with id: '" + recipeId + "' failed!");
				}
				//count++;
			}
		}
		return true;
    }
    public HashMap<String,Integer> parseIngredientsList(List<String> ingredients) {
    	HashMap<String,Integer> map = new HashMap<String,Integer>();
    	if (ingredients != null) {
			for (String listString : ingredients) {
				String[] listSplit = listString.split("/");
				if (listSplit.length > 1) {
					
					int amount = Integer.parseInt(listSplit[1]);
					map.put(listSplit[0], amount);
				}
			}
		}
		return map;
    }
    
    public HashMap<String, Double> parseEffectsList(ConfigurationSection configSection, String recipeId) {
    	HashMap<String, Double> map = new HashMap<String, Double>();
    	ConfigurationSection configSection2 = configSection.getConfigurationSection(recipeId+".effects");
		if (configSection2 != null) {
			for (String effect : configSection2.getKeys(false)) {
				if(effect.equalsIgnoreCase("maxhealth") || effect.equalsIgnoreCase("attackdamage") || effect.equalsIgnoreCase("movementspeed") || 
						effect.equalsIgnoreCase("knockbackresistance") || effect.equalsIgnoreCase("armor") || effect.equalsIgnoreCase("armortoughness") || 
						effect.equalsIgnoreCase("attackspeed") || effect.equalsIgnoreCase("luck")) {
					double add = configSection2.getDouble(effect);
					map.put(effect, add);
				} else {
					Server server = getServer();
					ConsoleCommandSender console = server.getConsoleSender();
					console.sendMessage(ChatColor.RED + "["+this.getName()+"] You Can't Spell Effects: " + effect);
					return null;
				}
			}
		}
    	return map;
    }
    public HashMap<String,Boolean> parseSlotsList(ConfigurationSection configSection, String recipeId) {
    	HashMap<String,Boolean> map = new HashMap<String,Boolean>();
    	ConfigurationSection configSection2 = configSection.getConfigurationSection(recipeId+".slots");
		if (configSection2 != null) {
			for (String slot : configSection2.getKeys(false)) {
				if(slot.equalsIgnoreCase("head") || slot.equalsIgnoreCase("chest") || slot.equalsIgnoreCase("legs") || 
						slot.equalsIgnoreCase("feet") || slot.equalsIgnoreCase("mainhand") || slot.equalsIgnoreCase("offhand")) {
					boolean activated = configSection2.getBoolean(slot);
					map.put(slot, activated);
				} else {
					Server server = getServer();
					ConsoleCommandSender console = server.getConsoleSender();
			    	console.sendMessage(ChatColor.RED + "["+this.getName()+"] You Can't Spell Slots: " + slot);
					return null;
				}
			}
		}
    	return map;
    }
    
    public HashMap<Material,String> parsePossibleIngredients() {
    	HashMap<Material,String> map = new HashMap<Material,String>();
    	ConfigurationSection configSection = config.getConfigurationSection("materials");
		if (configSection != null) {
			for (String pIngredient : configSection.getKeys(false)) {
				Material mat = Material.getMaterial(pIngredient);
				if(mat == null) {
					continue;
				}
				String result = configSection.getString(pIngredient);
				map.put(mat, result);
			}
		}
    	return map;
    }
    
    public Set<String> parseSpecialIngredients() {
    	Set<String> set = new HashSet<String>();
    	set.add("TIN");
    	set.add("COPPER}");
    	set.add("SCRAP_METAL");
    	set.add("SCRAP_GOLD");
    	set.add("CRYSTAL");
    	return set;
    }
    
    public HashMap<String,String> parsePhrases() {
    	HashMap<String,String> map = new HashMap<String,String>();
    	ConfigurationSection configSection = config.getConfigurationSection("phrases");
		if (configSection != null) {
			for (String phraseTitle : configSection.getKeys(false)) {
				String phrase = configSection.getString(phraseTitle);
				map.put(phraseTitle, phrase);
			}
		}
    	return map;
    }
    
    public HashMap<String,String> parseRecipeHints() {
    	HashMap<String,String> map = new HashMap<String,String>();
    	ConfigurationSection configSection = config.getConfigurationSection("HintWords");
		if (configSection != null) {
			for (String phraseTitle : configSection.getKeys(false)) {
				String phrase = configSection.getString(phraseTitle);
				map.put(phraseTitle, phrase);
			}
		}
    	return map;
    }
}
