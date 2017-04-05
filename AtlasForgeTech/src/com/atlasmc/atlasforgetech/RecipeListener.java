package com.atlasmc.atlasforgetech;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.atlasmc.atlasforgetech.Attributes.Attribute;
import com.atlasmc.atlasforgetech.Attributes.AttributeType;
import com.atlasmc.atlasforgetech.Attributes.Slot;



public class RecipeListener implements Listener {

	private JavaPlugin plugin;
	List<ForgeRecipe> recipes;
	HashMap<String,Integer> counts;
	HashMap<Material,Defaults> defaultsList;

	public RecipeListener(JavaPlugin plugin, List<ForgeRecipe> recipes) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
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
		defaultsList.put(Material.SHIELD, new Defaults(0, 0));
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
	
	@EventHandler
	public void CheckRecipe(PrepareItemCraftEvent e){
		
		Server server = plugin.getServer();
		ConsoleCommandSender console = server.getConsoleSender();
    	
		
		if(e.getInventory() instanceof CraftingInventory){
			CraftingInventory inv = (CraftingInventory) e.getInventory();
			
			for(int i = 0; i < inv.getMatrix().length; i++) {

				ItemStack item = inv.getMatrix()[i];
				if(item.getType() != Material.AIR) {
					ItemMeta meta = item.getItemMeta();
					if(meta != null) {
						List<String> lore = meta.getLore();

						if(lore != null && lore.size() > 0 && HiddenStringUtils.hasHiddenString(lore.get(0))) {
							String json = HiddenStringUtils.extractHiddenString(lore.get(0));

							if(json.equals("TIN")) {
								e.getInventory().setResult(new ItemStack(Material.AIR));
							} else if(json.equals("COPPER")) {
								e.getInventory().setResult(new ItemStack(Material.AIR));
							} else if(json.equals("CRYSTAL")) {
								e.getInventory().setResult(new ItemStack(Material.AIR));
							}
						}
					}

				}
			}
			if(inv.getSize() != 4){


				counts = new HashMap<String,Integer>();
				for(ForgeRecipe recipe: recipes) {
					counts.put(recipe.getTag(), 0);
				}

				for(int i = 0; i < inv.getMatrix().length; i++) {
					
					getHiddenString(inv, i);
					
					if(e.getRecipe().getResult().getType() == Material.IRON_CHESTPLATE || 
							e.getRecipe().getResult().getType() == Material.GOLD_CHESTPLATE || 
							e.getRecipe().getResult().getType() == Material.DIAMOND_CHESTPLATE) {
						//console.sendMessage(ChatColor.LIGHT_PURPLE + "["+plugin.getName()+"] Chestplate recognized!");

						for(String tag: counts.keySet()) {
							if(counts.get(tag)==8) {
								for(ForgeRecipe recipe: recipes) {
									if(recipe.getTag().equals(tag) && recipe.getSlots().get("chest")) {
										e.getInventory().setResult(makeChestplate(recipe));
										return;
									}
								}
							}
						}


					} else if(e.getRecipe().getResult().getType() == Material.IRON_HELMET || 
							e.getRecipe().getResult().getType() == Material.GOLD_HELMET || 
							e.getRecipe().getResult().getType() == Material.DIAMOND_HELMET) {
						//console.sendMessage(ChatColor.LIGHT_PURPLE + "["+plugin.getName()+"] Helmet recognized!");
						
						for(String tag: counts.keySet()) {
							if(counts.get(tag)==5) {
								for(ForgeRecipe recipe: recipes) {
									if(recipe.getTag().equals(tag) && recipe.getSlots().get("head")) {
										e.getInventory().setResult(makeHelmet(recipe));
										return;
									}
								}
							}
						}

					} else if(e.getRecipe().getResult().getType() == Material.IRON_LEGGINGS || 
							e.getRecipe().getResult().getType() == Material.GOLD_LEGGINGS || 
							e.getRecipe().getResult().getType() == Material.DIAMOND_LEGGINGS) {
						
						for(String tag: counts.keySet()) {
							if(counts.get(tag)==7) {
								for(ForgeRecipe recipe: recipes) {
									if(recipe.getTag().equals(tag) && recipe.getSlots().get("legs")) {
										e.getInventory().setResult(makeLeggings(recipe));
										return;
									}
								}
							}
						}

					} else if(e.getRecipe().getResult().getType() == Material.IRON_BOOTS || 
							e.getRecipe().getResult().getType() == Material.GOLD_BOOTS || 
							e.getRecipe().getResult().getType() == Material.DIAMOND_BOOTS) {
						
						for(String tag: counts.keySet()) {
							if(counts.get(tag)==4) {
								for(ForgeRecipe recipe: recipes) {
									if(recipe.getTag().equals(tag) && recipe.getSlots().get("feet")) {
										e.getInventory().setResult(makeBoots(recipe));
										return;
									}
								}
							}
						}

					} else if(e.getRecipe().getResult().getType() == Material.IRON_SWORD || 
							e.getRecipe().getResult().getType() == Material.GOLD_SWORD || 
							e.getRecipe().getResult().getType() == Material.DIAMOND_SWORD) {
						
						for(String tag: counts.keySet()) {
							if(counts.get(tag)==2) {
								for(ForgeRecipe recipe: recipes) {
									if(recipe.getTag().equals(tag) && recipe.getSlots().get("mainhand")) {
										e.getInventory().setResult(makeSword(recipe));
										return;
									}
								}
							}
						}

					} else if(e.getRecipe().getResult().getType() == Material.IRON_AXE || 
							e.getRecipe().getResult().getType() == Material.GOLD_AXE || 
							e.getRecipe().getResult().getType() == Material.DIAMOND_AXE) {
						
						for(String tag: counts.keySet()) {
							if(counts.get(tag)==3) {
								for(ForgeRecipe recipe: recipes) {
									if(recipe.getTag().equals(tag) && recipe.getSlots().get("mainhand")) {
										e.getInventory().setResult(makeAxe(recipe));
										return;
									}
								}
							}
						}

					} else if(e.getRecipe().getResult().getType() == Material.IRON_SPADE || 
							e.getRecipe().getResult().getType() == Material.GOLD_SPADE || 
							e.getRecipe().getResult().getType() == Material.DIAMOND_SPADE) {
						
						for(String tag: counts.keySet()) {
							if(counts.get(tag)==1) {
								for(ForgeRecipe recipe: recipes) {
									if(recipe.getTag().equals(tag) && recipe.getSlots().get("mainhand")) {
										e.getInventory().setResult(makeSpade(recipe));
										return;
									}
								}
							}
						}

					} else if(e.getRecipe().getResult().getType() == Material.IRON_PICKAXE || 
							e.getRecipe().getResult().getType() == Material.GOLD_PICKAXE || 
							e.getRecipe().getResult().getType() == Material.DIAMOND_PICKAXE) {
						
						for(String tag: counts.keySet()) {
							if(counts.get(tag)==3) {
								for(ForgeRecipe recipe: recipes) {
									if(recipe.getTag().equals(tag) && recipe.getSlots().get("mainhand")) {
										e.getInventory().setResult(makePickaxe(recipe));
										return;
									}
								}
							}
						}

					} else if(e.getRecipe().getResult().getType() == Material.IRON_HOE || 
							e.getRecipe().getResult().getType() == Material.GOLD_HOE || 
							e.getRecipe().getResult().getType() == Material.DIAMOND_HOE) {
						
						for(String tag: counts.keySet()) {
							if(counts.get(tag)==2) {
								for(ForgeRecipe recipe: recipes) {
									if(recipe.getTag().equals(tag) && recipe.getSlots().get("mainhand")) {
										e.getInventory().setResult(makeHoe(recipe));
										return;
									}
								}
							}
						}

					} else if(e.getRecipe().getResult().getType() == Material.SHIELD) {
						
						for(String tag: counts.keySet()) {
							if(counts.get(tag)==1) {
								for(ForgeRecipe recipe: recipes) {
									if(recipe.getTag().equals(tag) && recipe.getSlots().get("offhand")) {
										e.getInventory().setResult(makeShield(recipe));
										return;
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	
	private ItemStack makeShield(ForgeRecipe recipe) {
		Material mat = Material.SHIELD;
		ItemStack item = new ItemStack(mat);
		item.setItemMeta(setMeta(item, recipe, "Shield", "off hand"));
		return setAttributes(item, recipe.getEffects(), "offhand");
	}

	private ItemStack makeHoe(ForgeRecipe recipe) {
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

	private ItemStack makePickaxe(ForgeRecipe recipe) {
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

	private ItemStack makeSpade(ForgeRecipe recipe) {
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

	private ItemStack makeAxe(ForgeRecipe recipe) {
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

	private ItemStack makeSword(ForgeRecipe recipe) {
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

	private ItemStack makeBoots(ForgeRecipe recipe) {
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

	private ItemStack makeLeggings(ForgeRecipe recipe) {
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

	private ItemStack makeHelmet(ForgeRecipe recipe) {
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
		
		ItemStack item = new ItemStack(mat);
		item.setItemMeta(setMeta(item, recipe, "Helmet", "head"));
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
	
	public ItemMeta setMeta(ItemStack item, ForgeRecipe recipe, String name, String slot) {
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
	
	public void getHiddenString(CraftingInventory inv, int i) {
		
		ItemStack ingot = inv.getMatrix()[i];
		if(ingot.getType() != Material.AIR) {
			ItemMeta meta = ingot.getItemMeta();
			List<String> lore = meta.getLore();

			if(lore != null && lore.size() > 0 && HiddenStringUtils.hasHiddenString(lore.get(0))) {
				String json = HiddenStringUtils.extractHiddenString(lore.get(0));

				for(String tag: counts.keySet()) {
					String officialTag = tag;
					if(officialTag.equalsIgnoreCase(json)) {
						counts.put(tag, counts.get(tag)+1);
					}
				}
			}
		}
	}
	
	public ItemStack setAttributes(ItemStack item, HashMap<String, Double> hashMap, String slot) {
		
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
