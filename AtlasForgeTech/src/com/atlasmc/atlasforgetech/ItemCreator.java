package com.atlasmc.atlasforgetech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.atlasmc.atlasforgetech.Attributes.Attribute;
import com.atlasmc.atlasforgetech.Attributes.AttributeType;
import com.atlasmc.atlasforgetech.Attributes.Slot;

public class ItemCreator {
	
	HashMap<Material,Defaults> defaultsList;
	private JavaPlugin plugin;
	List<ForgeRecipe> recipes;
	
	public ItemCreator(JavaPlugin plugin, List<ForgeRecipe> recipes) {
		this.plugin = plugin;
		this.recipes = recipes;
		setDefaultData();
	}

	
	public void setDefaultData() {
		defaultsList = new HashMap<Material,Defaults>();
		defaultsList.put(Material.IRON_HELMET, new Defaults(2,0));
		defaultsList.put(Material.IRON_CHESTPLATE, new Defaults(6,0));
		defaultsList.put(Material.IRON_LEGGINGS, new Defaults(5,0));
		defaultsList.put(Material.IRON_BOOTS, new Defaults(2,0));
		defaultsList.put(Material.IRON_SWORD, new Defaults(1.6, 6.0));
		defaultsList.put(Material.IRON_AXE, new Defaults(0.9, 9.0));
		defaultsList.put(Material.IRON_PICKAXE, new Defaults(1.2, 4.0));
		defaultsList.put(Material.IRON_SPADE, new Defaults(1.0, 4.5));
		defaultsList.put(Material.IRON_HOE, new Defaults(3.0, 1.0));
		defaultsList.put(Material.DIAMOND_HELMET, new Defaults(3,2));
		defaultsList.put(Material.DIAMOND_CHESTPLATE, new Defaults(8,2));
		defaultsList.put(Material.DIAMOND_LEGGINGS, new Defaults(6,2));
		defaultsList.put(Material.DIAMOND_BOOTS, new Defaults(3,2));
		defaultsList.put(Material.DIAMOND_SWORD, new Defaults(1.6, 7.0));
		defaultsList.put(Material.DIAMOND_AXE, new Defaults(1.0, 9.0));
		defaultsList.put(Material.DIAMOND_PICKAXE, new Defaults(1.2, 5.0));
		defaultsList.put(Material.DIAMOND_SPADE, new Defaults(1.0, 5.5));
		defaultsList.put(Material.DIAMOND_HOE, new Defaults(4.0, 1.0));
		defaultsList.put(Material.GOLD_HELMET, new Defaults(2,0));
		defaultsList.put(Material.GOLD_CHESTPLATE, new Defaults(5,0));
		defaultsList.put(Material.GOLD_LEGGINGS, new Defaults(3,0));
		defaultsList.put(Material.GOLD_BOOTS, new Defaults(1,0));
		defaultsList.put(Material.GOLD_SWORD, new Defaults(1.6, 4.0));
		defaultsList.put(Material.GOLD_AXE, new Defaults(1.0, 7.0));
		defaultsList.put(Material.GOLD_PICKAXE, new Defaults(1.2, 2.0));
		defaultsList.put(Material.GOLD_SPADE, new Defaults(1.0, 2.5));
		defaultsList.put(Material.GOLD_HOE, new Defaults(1.0, 1.0));
	}
	
	public ItemStack makeShield(ForgeRecipe recipe) {
		Material mat = Material.SHIELD;
		ItemStack item = new ItemStack(mat);
		item.setItemMeta(setMeta(item, recipe, "Shield", "off hand"));
		return setAttributes(item, recipe.getEffects(), "offhand");
	}

	public ItemStack makeHoe(ForgeRecipe recipe) {
		Material mat = Material.IRON_INGOT;
		switch(recipe.getMaterial()) {
		case IRON_INGOT:
			mat = Material.IRON_HOE;
			break;
		case GOLD_INGOT:
			mat = Material.GOLD_HOE;
			break;
		case DIAMOND:
			mat = Material.DIAMOND_HOE;
			break;
		default:
			break;
		}
		ItemStack item = new ItemStack(mat);
		item.setItemMeta(setMeta(item, recipe, "Hoe", "main hand"));
		return setAttributes(item, recipe.getEffects(), "mainhand");
	}

	public ItemStack makePickaxe(ForgeRecipe recipe) {
		Material mat = Material.IRON_INGOT;
		switch(recipe.getMaterial()) {
		case IRON_INGOT:
			mat = Material.IRON_PICKAXE;
			break;
		case GOLD_INGOT:
			mat = Material.GOLD_PICKAXE;
			break;
		case DIAMOND:
			mat = Material.DIAMOND_PICKAXE;
			break;
		default:
			break;
		}
		ItemStack item = new ItemStack(mat);
		item.setItemMeta(setMeta(item, recipe, "Pickaxe", "main hand"));
		return setAttributes(item, recipe.getEffects(), "mainhand");
	}

	public ItemStack makeSpade(ForgeRecipe recipe) {
		Material mat = Material.IRON_INGOT;
		switch(recipe.getMaterial()) {
		case IRON_INGOT:
			mat = Material.IRON_SPADE;
			break;
		case GOLD_INGOT:
			mat = Material.GOLD_SPADE;
			break;
		case DIAMOND:
			mat = Material.DIAMOND_SPADE;
			break;
		default:
			break;
		}
		
		ItemStack item = new ItemStack(mat);
		item.setItemMeta(setMeta(item, recipe, "Shovel", "main hand"));
		return setAttributes(item, recipe.getEffects(), "mainhand");
	}

	public ItemStack makeAxe(ForgeRecipe recipe) {
		Material mat = Material.IRON_INGOT;
		switch(recipe.getMaterial()) {
		case IRON_INGOT:
			mat = Material.IRON_AXE;
			break;
		case GOLD_INGOT:
			mat = Material.GOLD_AXE;
			break;
		case DIAMOND:
			mat = Material.DIAMOND_AXE;
			break;
		default:
			break;
		}
		
		ItemStack item = new ItemStack(mat);
		item.setItemMeta(setMeta(item, recipe, "Axe", "main hand"));
		return setAttributes(item, recipe.getEffects(), "mainhand");
	}

	public ItemStack makeSword(ForgeRecipe recipe) {
		Material mat = Material.IRON_INGOT;
		switch(recipe.getMaterial()) {
		case IRON_INGOT:
			mat = Material.IRON_SWORD;
			break;
		case GOLD_INGOT:
			mat = Material.GOLD_SWORD;
			break;
		case DIAMOND:
			mat = Material.DIAMOND_SWORD;
			break;
		default:
			break;
		}
		
		ItemStack item = new ItemStack(mat);
		item.setItemMeta(setMeta(item, recipe, "Sword", "main hand"));
		return setAttributes(item, recipe.getEffects(), "mainhand");
	}

	public ItemStack makeBoots(ForgeRecipe recipe) {
		Material mat = Material.IRON_INGOT;
		switch(recipe.getMaterial()) {
		case IRON_INGOT:
			mat = Material.IRON_BOOTS;
			break;
		case GOLD_INGOT:
			mat = Material.GOLD_BOOTS;
			break;
		case DIAMOND:
			mat = Material.DIAMOND_BOOTS;
			break;
		default:
			break;
		}
		
		ItemStack item = new ItemStack(mat);
		item.setItemMeta(setMeta(item, recipe, "Boots", "feet"));
		return setAttributes(item, recipe.getEffects(), "feet");
	}

	public ItemStack makeLeggings(ForgeRecipe recipe) {
		Material mat = Material.IRON_INGOT;
		switch(recipe.getMaterial()) {
		case IRON_INGOT:
			mat = Material.IRON_LEGGINGS;
			break;
		case GOLD_INGOT:
			mat = Material.GOLD_LEGGINGS;
			break;
		case DIAMOND:
			mat = Material.DIAMOND_LEGGINGS;
			break;
		default:
			break;
		}
		
		ItemStack item = new ItemStack(mat);
		item.setItemMeta(setMeta(item, recipe, "Leggings", "legs"));
		return setAttributes(item, recipe.getEffects(), "legs");
	}

	public ItemStack makeHelmet(ForgeRecipe recipe) {
		plugin.getLogger().info("Making Helmet!");
		Material mat = Material.IRON_INGOT;
		switch(recipe.getMaterial()) {
		case IRON_INGOT:
			mat = Material.IRON_HELMET;
			break;
		case GOLD_INGOT:
			mat = Material.GOLD_HELMET;
			break;
		case DIAMOND:
			mat = Material.DIAMOND_HELMET;
			break;
		default:
			break;
		}
		plugin.getLogger().info("Helmet Type Chosen");

		ItemStack item = new ItemStack(mat);
		item.setItemMeta(setMeta(item, recipe, "Helmet", "head"));
		
		plugin.getLogger().info("Meta Set");

		return setAttributes(item, recipe.getEffects(), "head");
	}

	public ItemStack makeChestplate(ForgeRecipe recipe) {
		Material mat = Material.IRON_INGOT;
		switch(recipe.getMaterial()) {
		case IRON_INGOT:
			mat = Material.IRON_CHESTPLATE;
			break;
		case GOLD_INGOT:
			mat = Material.GOLD_CHESTPLATE;
			break;
		case DIAMOND:
			mat = Material.DIAMOND_CHESTPLATE;
			break;
		default:
			break;
		}
		
		ItemStack item = new ItemStack(mat);
		item.setItemMeta(setMeta(item, recipe, "Chestplate", "body"));
		return setAttributes(item, recipe.getEffects(), "chest");
	}
	
	private ItemMeta setMeta(ItemStack item, ForgeRecipe recipe, String name, String slot) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GRAY+recipe.getName()+" " + name);
		List<String> lore = new ArrayList<String>();
		lore.add(0,ChatColor.GRAY+"When on " + slot + ":");
		int loreLine = 1;
		if(recipe.getEffects().get("attackspeed") != 0 || defaultsList.get(item.getType()).getAttackspeed() != 0) {
			double attackspeed =  defaultsList.get(item.getType()).getAttackspeed() + recipe.getEffects().get("attackspeed");
			lore.add(loreLine,ChatColor.GRAY + " " + attackspeed + " Attack Speed");	
			loreLine++;
		}
		if(recipe.getEffects().get("attackdamage") != 0 || defaultsList.get(item.getType()).getDamage() != 0) {
			double attackdamage =  defaultsList.get(item.getType()).getDamage() + recipe.getEffects().get("attackdamage");
			lore.add(loreLine,ChatColor.GRAY + " " + attackdamage + " Attack Damage");	
			loreLine++;
		}
		if(recipe.getEffects().get("armortoughness") != 0 || defaultsList.get(item.getType()).getToughness() != 0) {
			int toughness =  (int) (defaultsList.get(item.getType()).getToughness() + recipe.getEffects().get("armortoughness"));
			lore.add(loreLine,ChatColor.BLUE + " +" + toughness + " Armor Toughness");	
			loreLine++;
		}
		if(recipe.getEffects().get("armor") > 0 || defaultsList.get(item.getType()).getArmor() != 0) {
			double armor =  defaultsList.get(item.getType()).getArmor() + recipe.getEffects().get("armor");
			lore.add(loreLine,ChatColor.BLUE + " +" + armor + " Armor");	
			loreLine++;
		}
		else if(recipe.getEffects().get("armor") < 0 || defaultsList.get(item.getType()).getArmor() != 0) {
			double armor =  defaultsList.get(item.getType()).getArmor() + recipe.getEffects().get("armor");
			lore.add(loreLine,ChatColor.RED + " " + armor + " Armor");	
			loreLine++;
		}
		if(recipe.getEffects().get("maxhealth") < 0) {
			lore.add(loreLine,ChatColor.RED+" "+recipe.getEffects().get("maxhealth")+ " Health");	
			loreLine++;
		}
		else if(recipe.getEffects().get("maxhealth") > 0) {
			lore.add(loreLine,ChatColor.BLUE+" +"+recipe.getEffects().get("maxhealth")+ " Health");	
			loreLine++;
		}
		plugin.getLogger().info("Adding Speed:");
		if(recipe.getEffects().get("movementspeed") > 0) {
			plugin.getLogger().info("Added " + recipe.getEffects().get("movementspeed") + " Speed.");
			double speed =  (defaultsList.get(item.getType()).getSpeed() + recipe.getEffects().get("movementspeed"))*1000;
			lore.add(loreLine,ChatColor.BLUE + " +" + speed + "% Speed");	
			loreLine++;
		}
		else if(recipe.getEffects().get("movementspeed") < 0) {
			plugin.getLogger().info("Subtracted " + recipe.getEffects().get("movementspeed") + " Speed.");
			double speed =  (defaultsList.get(item.getType()).getSpeed() + recipe.getEffects().get("movementspeed"))*1000;
			lore.add(loreLine,ChatColor.RED + " " + speed + "% Speed");	
			loreLine++;
		}
		if(recipe.getEffects().get("knockbackresistance") > 0) {
			double kResist =  (defaultsList.get(item.getType()).getKnockback() + recipe.getEffects().get("knockbackresistance"))*100;
			lore.add(loreLine,ChatColor.BLUE + " +" + kResist + "% Knockback Resist");	
			loreLine++;
		}
		else if(recipe.getEffects().get("knockbackresistance") < 0) {
			double kResist =  (defaultsList.get(item.getType()).getKnockback() + recipe.getEffects().get("knockbackresistance"))*100;
			lore.add(loreLine,ChatColor.RED + " " + kResist + "% Knockback Resist");	
			loreLine++;
		}
		if(recipe.getDurability() > 1.0) {
			lore.add(loreLine,ChatColor.BLUE+" +Durability"+HiddenStringUtils.encodeString("Durability/" + recipe.getDurability()));
			loreLine++;
		} 
		else if(recipe.getDurability() < 1.0) {
			lore.add(loreLine,ChatColor.RED+" -Durability"+HiddenStringUtils.encodeString("Durability/" + recipe.getDurability()));
			loreLine++;
		} 
		if(recipe.getmResistance() > 0) {
			lore.add(loreLine,ChatColor.BLUE+" +Magic Resist"+HiddenStringUtils.encodeString("MResist/" + recipe.getmResistance()));	
			loreLine++;
		}
		else if(recipe.getmResistance() < 0) {
			lore.add(loreLine,ChatColor.RED+" -Magic Resist"+HiddenStringUtils.encodeString("MResist/" + recipe.getmResistance()));	
			loreLine++;
		}
		if(recipe.getEffects().get("luck") > 0) {
			lore.add(loreLine,ChatColor.BLUE + " +Luck");	
			loreLine++;
		}
		else if(recipe.getEffects().get("luck") < 0) {
			lore.add(loreLine,ChatColor.RED + " -Luck");	
			loreLine++;
		}
		
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setLore(lore);
		return meta;
	}
	
	
	private ItemStack setAttributes(ItemStack item, HashMap<String, Double> hashMap, String slot) {
		
		Defaults defaults = defaultsList.get(item.getType());
		int health = (int) (hashMap.get("maxhealth") + defaults.getHealth());
		double damage = hashMap.get("attackdamage") + defaults.getDamage();
		double speed = hashMap.get("movementspeed") + defaults.getSpeed();
		double knockback = hashMap.get("knockbackresistance") + defaults.getKnockback();
		int armor = (int) (hashMap.get("armor") + defaults.getArmor());
		int toughness = (int) (hashMap.get("armortoughness") + defaults.getToughness());
		double attackspeed = hashMap.get("attackspeed");
		int luck = (int) (hashMap.get("luck") + defaults.getLuck());
	
		Slot slotType = null;
		switch(slot) {
		case "chest":
			slotType = Slot.CHEST;
			break;
		case "head":
			slotType = Slot.HEAD;
			break;
		case "legs":
			slotType = Slot.LEGS;
			break;
		case "feet":
			slotType = Slot.FEET;
			break;
		case "mainhand":
			slotType = Slot.MAIN_HAND;
			break;
		case "offhand":
			slotType = Slot.OFF_HAND;
			break;
		}
		
		
		Attributes attributes = new Attributes(item);
		
		attributes.add(Attribute.newBuilder().name("Health").type(AttributeType.GENERIC_MAX_HEALTH).amount(health).slot(slotType).build());
		attributes.add(Attribute.newBuilder().name("Attack").type(AttributeType.GENERIC_ATTACK_DAMAGE).amount(damage).slot(slotType).build());
		attributes.add(Attribute.newBuilder().name("Speed").type(AttributeType.GENERIC_MOVEMENT_SPEED).amount(speed).slot(slotType).build());
		attributes.add(Attribute.newBuilder().name("Knockback").type(AttributeType.GENERIC_KNOCKBACK_RESISTANCE).amount(knockback).slot(slotType).build());
		attributes.add(Attribute.newBuilder().name("Armor").type(AttributeType.GENERIC_ARMOR).amount(armor).slot(slotType).build());
		attributes.add(Attribute.newBuilder().name("Toughness").type(AttributeType.GENERIC_ARMOR_TOUGHNESS).amount(toughness).slot(slotType).build());
		attributes.add(Attribute.newBuilder().name("AttackSpeed").type(AttributeType.GENERIC_ATTACK_SPEED).amount(attackspeed).slot(slotType).build());
		attributes.add(Attribute.newBuilder().name("Luck").type(AttributeType.GENERIC_LUCK).amount(luck).slot(slotType).build());
		
		return attributes.getStack();
		
	}
}
