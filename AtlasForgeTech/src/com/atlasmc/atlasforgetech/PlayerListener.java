package com.atlasmc.atlasforgetech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Cauldron;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerListener implements Listener {
	
	private JavaPlugin plugin;
	Set<Material> possibleIngredients;
	Set<String> specialIngredients;
	List<ForgeRecipe> recipes;
	boolean use1_9;
	HashMap<String,String> phrases;

	public PlayerListener(JavaPlugin plugin, HashMap<String,String> phrases, Set<Material> ingredientsList, Set<String> specialIngredients, List<ForgeRecipe> recipes) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		possibleIngredients = ingredientsList;
		this.specialIngredients = specialIngredients;
		this.recipes = recipes;
		this.phrases = phrases;
		
		String v = Bukkit.getBukkitVersion();
		use1_9 = !v.matches("(^|.*[^\\.\\d])1\\.[0-8]([^\\d].*|$)");
	}
	

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Block clickedBlock = event.getClickedBlock();

		if (clickedBlock != null) {
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				Player player = event.getPlayer();
				if (!player.isSneaking()) {
					Material type = clickedBlock.getType();

					if (type == Material.CAULDRON) {
						ItemStack item = event.getItem();
						if (item.getType() == Material.LAVA_BUCKET) {
							ItemMeta meta = item.getItemMeta();
							List<String> lore = meta.getLore();
						    if (lore != null && lore.size() > 0 && HiddenStringUtils.hasHiddenString(lore.get(0))) {   
						    	if(getFillLevel(clickedBlock) != 0) {
						    		String json = HiddenStringUtils.extractHiddenString(lore.get(0));
						    		String[] listSplit = json.split("/");
									
						    		for(ForgeRecipe recipe : recipes) {
						    			String tag = recipe.getTag();
						    			if (listSplit.length > 1) {
						    				if(tag.equals(listSplit[0])) {
						    					String specialName = getSpecialNameColor(listSplit[1]);
						    					ItemStack result;
						    					if(recipe.getMaterial().equals(Material.DIAMOND)) {
						    						result = getSpecialMaterial(listSplit[2], specialName+" Crytal");
												} else {
													result = getSpecialMaterial(listSplit[2], specialName+" Ingot");
												}
						    					if(result != null) {
									    			player.getInventory().addItem(result);
									    			player.sendMessage(ChatColor.GRAY + phrases.get("SuccessfulRecipe"));
									    		} else {
									    			player.sendMessage(ChatColor.GRAY + phrases.get("FailedRecipe"));
									    		}
									    		subtractFillLevel(clickedBlock);
									    		player.getInventory().setItemInMainHand(new ItemStack(Material.BUCKET));
									    		return;
						    				}
										} else {
											if(tag.equals(json)) {
												Material mat = recipe.getMaterial();
												String name = recipe.getName();
												ItemStack metal = new ItemStack(mat);
												ItemMeta metalmeta = metal.getItemMeta();
												if(recipe.getMaterial().equals(Material.DIAMOND)) {
													metalmeta.setDisplayName(ChatColor.GREEN+name + " Crystal");
												} else {
													metalmeta.setDisplayName(ChatColor.GREEN+name + " Ingot");
												}
												
												metalmeta.setLore(lore);
												metal.setItemMeta(metalmeta);
												player.getInventory().addItem(metal);
												subtractFillLevel(clickedBlock);
												player.getInventory().setItemInMainHand(new ItemStack(Material.BUCKET)); 
												player.sendMessage(ChatColor.GRAY + phrases.get("SuccessfulRecipe"));
												event.setCancelled(true);
												return;
											}
										}
						    			
						    			
						    		}
						    		
						    		ItemStack result = getSpecialMaterial(json,null);
						    		if(result != null) {
						    			player.getInventory().addItem(result);
						    			player.sendMessage(ChatColor.GRAY + phrases.get("SuccessfulRecipe"));
						    		} else {
						    			player.sendMessage(ChatColor.GRAY + phrases.get("FailedRecipe"));
						    		}
						    		subtractFillLevel(clickedBlock);
						    		player.getInventory().setItemInMainHand(new ItemStack(Material.BUCKET));
						    		event.setCancelled(true);
						    		return;
						    		
						    	} else {
						    		player.sendMessage(ChatColor.GRAY+phrases.get("FillCauldron"));
						    	}
						    	
						    }
						          
							
						}
						
					}
					
					
					
					// Interacting with a Cauldron
					else if (type == Material.FURNACE || type == Material.BURNING_FURNACE) {
						Material materialInHand = event.getMaterial();
						ItemStack item = event.getItem();

						if (item == null) {
							if(Forge.isForge(clickedBlock)) {
								player.sendMessage(ChatColor.GRAY + phrases.get("ClickSmeltingForge"));
								event.setCancelled(true);
								return;
							} 

						} else if (item.getType() == Material.WATCH) {
							int time = Forge.printTime(player, clickedBlock);
							if(time > 1) {
								player.sendMessage(ChatColor.GRAY + phrases.get("ForgeSmeltingTime")+ time +" minutes.");
								event.setCancelled(true);
							} else if (time == 1 || time == 0){
								player.sendMessage(ChatColor.GRAY + phrases.get("ForgeJustBegunSmelting"));
								event.setCancelled(true);
							}		
							return;
							// fill a glass bottle with potion
						} else if (item.getType() == Material.BUCKET) {
							if(Forge.fill(player, clickedBlock, recipes)) {
								
								player.sendMessage(ChatColor.GRAY + phrases.get("RetrieveMoltenMetal"));
								event.setCancelled(true);
							}
							
							return;

							// reset forge
						} else if (item.getType() == Material.WATER_BUCKET) {
							if(Forge.remove(clickedBlock)) {
								player.sendMessage(ChatColor.GRAY + phrases.get("ForgeMaterialsFlushed"));
								event.setCancelled(true);
							}
							return;
						} else if (item.getType() == Material.BEDROCK) {
							List<ItemStack> ingredients = Forge.list(clickedBlock);
							if(ingredients != null) {
								for(ItemStack ingredient: ingredients) {
									player.sendMessage(ingredient.getType().name());
								}
							}
							event.setCancelled(true);
						}

						// Check if there is fire below the forge
						Block down = clickedBlock.getRelative(BlockFace.DOWN);
						Block up = clickedBlock.getRelative(BlockFace.UP);
						if ((down.getType() == Material.FIRE || down.getType() == Material.STATIONARY_LAVA || down.getType() == Material.LAVA) && up.getType()== Material.COBBLE_WALL) {
							event.setCancelled(true);
							
							if(item==null) {
								player.sendMessage(ChatColor.GRAY+"This is a forge. Right Click it with items to add them and begin smelting!");
							}
							
							if(!use1_9) {
								if (event.getHand() != EquipmentSlot.HAND) {
								return;	
								}
							}
							
							// add ingredient to forge that meet the previous conditions
							if (possibleIngredients.contains(materialInHand)) {
									if (Forge.ingredientAdd(clickedBlock, item)) {
										boolean isBucket =  item.getType().equals(Material.LAVA_BUCKET)
												|| item.getType().equals(Material.MILK_BUCKET);
										if (item.getAmount() > 1) {
											item.setAmount(item.getAmount() - 1);
										} else {
											if (isBucket) {
												player.getInventory().setItemInMainHand(new ItemStack(Material.BUCKET));
											} else {
												player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
											}
										}
									}
							} else if (item.hasItemMeta()) {
								ItemMeta meta = item.getItemMeta();
								List<String> lore = meta.getLore();
							    if (lore != null && lore.size() > 0 && HiddenStringUtils.hasHiddenString(lore.get(0))) {
							    	String json = HiddenStringUtils.extractHiddenString(lore.get(0));
							    	for(String tag: specialIngredients) {
							    		if(tag.equals(json)) {
							    			Forge.ingredientAdd(clickedBlock, item);
							    			if (item.getAmount() > 1) {
												item.setAmount(item.getAmount() - 1);
											} else {
												player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
											}
							    			break;
							    		}
							    	}
							    }
							    		
								
							}
						}
						return;
					}
					if(!use1_9) {
						if (event.getHand() != EquipmentSlot.HAND) {
							return;
						}
					}
				}
				ItemStack item = event.getItem();
				if(item != null) {
					if (item.getType() == Material.LAVA_BUCKET) {
						ItemMeta meta = item.getItemMeta();
						List<String> lore = meta.getLore();
				    	if (lore != null && lore.size() > 0 && HiddenStringUtils.hasHiddenString(lore.get(0))) {
				    		event.setCancelled(true);
				    	}
					}
				}
				
			}
		}
	}
	private ItemStack getSpecialMaterial(String json, String specialName) {
		plugin.getLogger().info("Special Material Found: " + json);
		if(Material.getMaterial(json) != null) {
			if(Material.getMaterial(json).equals(Material.IRON_INGOT)) {
				plugin.getLogger().info("Specal Iron Found: " + specialName);

				ItemStack item = new ItemStack(Material.IRON_NUGGET, 1);
				if(specialName != null) {
					ItemMeta meta = item.getItemMeta();
					meta.setDisplayName(ChatColor.GRAY+specialName);
					item.setItemMeta(meta);
					return new ItemStack(item);
				}
			} else if (Material.getMaterial(json).equals(Material.GOLD_INGOT)) {
				plugin.getLogger().info("Specal Gold Found: " + specialName);

				ItemStack item = new ItemStack(Material.GOLD_NUGGET, 1);
				if(specialName != null) {
					ItemMeta meta = item.getItemMeta();
					meta.setDisplayName(ChatColor.GRAY+specialName);
					item.setItemMeta(meta);
					return new ItemStack(item);
				} 
			} else if (Material.getMaterial(json).equals(Material.DIAMOND)) {
				plugin.getLogger().info("Specal Diamond Found: " + specialName);

				ItemStack item = new ItemStack(Material.PRISMARINE_CRYSTALS, 1);
				if(specialName != null) {
					ItemMeta meta = item.getItemMeta();
					meta.setDisplayName(ChatColor.GRAY+specialName);
					item.setItemMeta(meta);
					return new ItemStack(item);
				} 

			}
			return new ItemStack(Material.getMaterial(json), 1);
		}
		List<String> lore = new ArrayList<String>();
		lore.add(HiddenStringUtils.encodeString(json));
		switch (json) {
        case "TIN":
			ItemStack tin = new ItemStack(Material.IRON_INGOT);
			ItemMeta tinmeta = tin.getItemMeta();
			if(specialName != null) {
				tinmeta.setDisplayName(ChatColor.GRAY+specialName);
			} else {
				tinmeta.setDisplayName(ChatColor.WHITE+"Tin Ingot");
			}
			tinmeta.setLore(lore);
			tin.setItemMeta(tinmeta);
			return tin;
        case "COPPER": 
        	ItemStack copper = new ItemStack(Material.GOLD_INGOT);
			ItemMeta coppermeta = copper.getItemMeta();
			if(specialName != null) {
				coppermeta.setDisplayName(ChatColor.GRAY+specialName);
			} else {
				coppermeta.setDisplayName(ChatColor.GRAY+"Copper Ingot");
			}
			coppermeta.setLore(lore);
			copper.setItemMeta(coppermeta);
			return copper;
        case "TINCOPPER":
        	int random = (int) Math.ceil(Math.random() * 2);
        	if(random < 2) {
        		ItemStack tincopper1 = new ItemStack(Material.GOLD_INGOT);
				ItemMeta tincopper1meta = tincopper1.getItemMeta();
				if(specialName != null) {
					tincopper1meta.setDisplayName(ChatColor.GRAY+specialName);
				} else {
					tincopper1meta.setDisplayName(ChatColor.GRAY+"Copper Ingot");
				}
				List<String> tincopperlore = new ArrayList<String>();
				tincopperlore.add(HiddenStringUtils.encodeString("COPPER"));
				tincopper1meta.setLore(tincopperlore);
				tincopper1.setItemMeta(tincopper1meta);
				return tincopper1;
        	} else {
        		ItemStack tincopper1 = new ItemStack(Material.IRON_INGOT);
				ItemMeta tincopper1meta = tincopper1.getItemMeta();
				if(specialName != null) {
					tincopper1meta.setDisplayName(ChatColor.GRAY+specialName);
				} else {
					tincopper1meta.setDisplayName(ChatColor.GRAY+"Tin Ingot");
				}
				List<String> tincopperlore = new ArrayList<String>();
				tincopperlore.add(HiddenStringUtils.encodeString("TIN"));
				tincopper1meta.setLore(tincopperlore);
				tincopper1.setItemMeta(tincopper1meta);
				return tincopper1;
        	}
        case "SCRAP_METAL":
        	ItemStack scrapmetal = new ItemStack(Material.IRON_NUGGET);
			ItemMeta scrapmetalmeta = scrapmetal.getItemMeta();
			if(specialName != null) {
				scrapmetalmeta.setDisplayName(ChatColor.GRAY+specialName);
			} else {
				scrapmetalmeta.setDisplayName(ChatColor.GRAY+"Scrap Metal");
			}
			scrapmetalmeta.setLore(lore);
			scrapmetal.setItemMeta(scrapmetalmeta);
			return scrapmetal;
        case "SCRAP_GOLD":
        	ItemStack scrapgold = new ItemStack(Material.GOLD_NUGGET);
			ItemMeta scrapgoldmeta = scrapgold.getItemMeta();
			if(specialName != null) {
				scrapgoldmeta.setDisplayName(ChatColor.GRAY+specialName);
			} else {
				scrapgoldmeta.setDisplayName(ChatColor.GRAY+"Scrap Gold");
			}
			scrapgoldmeta.setLore(lore);
			scrapgold.setItemMeta(scrapgoldmeta);
			return scrapgold;
        case "CRYSTAL":
        	ItemStack crystal = new ItemStack(Material.PRISMARINE_CRYSTALS);
			ItemMeta crystalmeta = crystal.getItemMeta();
			if(specialName != null) {
				crystalmeta.setDisplayName(ChatColor.GRAY+specialName);
			} else {
				crystalmeta.setDisplayName(ChatColor.GRAY+"Crystal");
			}
			crystalmeta.setLore(lore);
			crystal.setItemMeta(crystalmeta);
			return crystal;
        case "NONE":
        	return null;
        case "EXPLODE":
        	return null;
        default:
        	
		}
		return null;
	}

	public String getSpecialNameColor(String specialName) {
		plugin.getLogger().info(specialName + "Special");
		String name = null;
		if("CorrectMaterialsAndAmounts".equalsIgnoreCase(specialName)) {
			name = ChatColor.YELLOW+ForgeRecipe.getRecipeHintWords().get("CorrectMaterialsAndAmounts");
		} else if ("CorrectTimeAndMaterials".equalsIgnoreCase(specialName)) {
			name = ChatColor.GOLD+ForgeRecipe.getRecipeHintWords().get("CorrectTimeAndMaterials");
		} else if ("CorrectMaterial".equalsIgnoreCase(specialName)) {
			name = ChatColor.RED+ForgeRecipe.getRecipeHintWords().get("CorrectMaterial");
		}
		return name;
	}
	// 0 = empty, 1 = something in, 2 = full
	public static byte getFillLevel(Block block) {
		if (block.getType() == Material.CAULDRON) {
			MaterialData data = block.getState().getData();
			if (data instanceof Cauldron) {
				Cauldron cauldron = (Cauldron) data;
				if (cauldron.isEmpty()) {
					return 0;
				} else if (cauldron.isFull()) {
					return 2;
				} else {
					return 1;
				}
			}
		}
		return 0;
	}
	// 0 = empty, 1 = something in, 2 = full
	@SuppressWarnings("deprecation")
	public static byte subtractFillLevel(Block block) {
		if (block.getType() == Material.CAULDRON) {
			MaterialData data = block.getState().getData();
			if (data instanceof Cauldron) {
				Cauldron cauldron = (Cauldron) data;
                BlockState d = block.getState();
                d.getData().setData((byte) (cauldron.getData()-1));
                d.update();
			}
		}
		return 0;
	}
}
