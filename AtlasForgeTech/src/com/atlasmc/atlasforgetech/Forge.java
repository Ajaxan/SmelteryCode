package com.atlasmc.atlasforgetech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class Forge {
	public static List<Forge> forgeList = new ArrayList<Forge>();

	private ArrayList<ItemStack> ingredients;
	private Block block;
	public Furnace furnace;
	private int state = 1;

	public Forge(Block block, ItemStack ingredient) {
		ingredients = new ArrayList<ItemStack>();
		this.block = block;
		furnace = (Furnace) this.block.getState();
		furnace.setBurnTime((short) 1200);
		add(ingredient);
		forgeList.add(this);
	}

	// add an ingredient to the forge
	public void add(ItemStack ingredient) {
		ItemStack temp = new ItemStack(ingredient);
		ingredients.add(temp);
		block.getWorld().playEffect(block.getLocation(), Effect.EXTINGUISH, 0);
		if (state > 1) {
			state--;
		}
	}

	public boolean onUpdate() {
		// Check if fire still alive
		if (!block.getChunk().isLoaded() || ((block.getRelative(BlockFace.DOWN).getType() == Material.FIRE || block.getRelative(BlockFace.DOWN).getType() == Material.STATIONARY_LAVA
				|| block.getRelative(BlockFace.DOWN).getType() == Material.LAVA) && block.getRelative(BlockFace.UP).getType()== Material.COBBLE_WALL)) {
			// add a minute to cooking time
			state++;
			
		} else {
			return false;
		}
		return true;
	}

	public ItemStack getResult(int time, Player player, List<ForgeRecipe> recipes) {
		ItemStack result = new ItemStack(Material.LAVA_BUCKET);
		Boolean match = false;
		String tag = "";
		
		
		
		HashMap<String,Integer> counts = new HashMap<String,Integer>();
		for(ItemStack item: ingredients) {
			if(item.hasItemMeta()) {
				ItemMeta meta = item.getItemMeta();
				List<String> lore = meta.getLore();
				if (lore != null && lore.size() > 0 && HiddenStringUtils.hasHiddenString(lore.get(0))) {
					String json = HiddenStringUtils.extractHiddenString(lore.get(0));
					if(counts.get(json) == null) {
						counts.put(json, 1);
					} else {
						counts.put(json, counts.get(json) + 1);
					}
				} else {
					if(counts.get(item.getType().name()) == null) {
						counts.put(item.getType().name(), 1);
					} else {
						counts.put(item.getType().name(), counts.get(item.getType().name()) + 1);
					}
				}
			} else {
				if(counts.get(item.getType().name()) == null) {
					counts.put(item.getType().name(), 1);
				} else {
					counts.put(item.getType().name(), counts.get(item.getType().name()) + 1);
				}
			}

		}
		
//		for(String name: counts.keySet()) {
//			player.sendMessage(name+ ": " + counts.get(name));
//		}
		String materialMatch = null;
		boolean fullMatch = false;
		boolean partialMatch = false;
		for(ForgeRecipe recipe: recipes) {
			if(counts.keySet().equals(recipe.getIngredients().keySet())) { //counts.keySet().size() == recipe.getIngredients().keySet().size()
				materialMatch = recipe.getName();
				//player.sendMessage("Material matched! " + materialMatch);
				for(String matName: counts.keySet()) {
					if(recipe.getIngredients().get(matName) == counts.get(matName)) {
						match = true;
					} else {
						match = false;
						break;
					} 
				}
				
				if(match && recipe.getTime() <= time && time <= (recipe.getTime()*3/2)) {
					//player.sendMessage("Full Match");
					tag = recipe.getTag();
					fullMatch = true;
					break;
				} else if (counts.size() == 1) {
					
				} else if (match && ForgeRecipe.isHintsEnabled()) {
					tag = recipe.getTag()+"/CorrectMaterialsAndAmounts/"+recipe.getMaterial();
					//player.sendMessage(tag);
					partialMatch = true;
					break;
				} else if (materialMatch != null && recipe.getTime() <= time && time <= (recipe.getTime()*3/2) && ForgeRecipe.isHintsEnabled()) {
					tag = recipe.getTag()+"/CorrectTimeAndMaterials/"+recipe.getMaterial();
					//player.sendMessage(tag);
					partialMatch = true;
					break;
				} else if (materialMatch != null && ForgeRecipe.isHintsEnabled()) {
					tag= recipe.getTag()+"/CorrectMaterial/"+recipe.getMaterial();
					//player.sendMessage(tag);
					partialMatch = true;
					break;
				}
			}

		}
		if(!fullMatch && !partialMatch) {
			String materialName = "";
			int highcount = 0;
			for(String matName: counts.keySet()) {
				if(counts.get(matName) > highcount) {
					highcount = counts.get(matName);
					materialName = matName;
				}
			}
			Material material = Material.getMaterial(materialName);
			if(material != null) {
				for(Material mat: ForgeRecipe.possibleIngredients.keySet()) {
					if(mat.equals(material)) {
						
						tag = ForgeRecipe.possibleIngredients.get(mat);
						//player.sendMessage(tag);
					}
				}
			} else {
				tag = "NONE";
			}
			
		}
		
		List<String> lore = new ArrayList<String>();
		lore.add(HiddenStringUtils.encodeString(tag));
		ItemMeta meta = result.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "Molten Metal");
		meta.setLore(lore);
		result.setItemMeta(meta);
		return result;
	}
	
	// get forge by Block
	public static Forge get(Block block) {
		for (Forge forge : forgeList) {
			if (forge.block.equals(block)) {
				return forge;
			}
		}
		return null;
	}
	
	// get cauldron from block and add given ingredient
	public static boolean ingredientAdd(Block block, ItemStack ingredient) {
		// if not empty
			Forge forge = get(block);
			if (forge != null) {
				forge.add(ingredient);
				return true;
			} else {
				new Forge(block, ingredient);
				return true;
			}
		
	}

	// fills players bottle with cooked brew
	public static boolean fill(Player player, Block block, List<ForgeRecipe> recipes) {
		Forge forge = get(block);
		if (forge != null) {
			
			ItemStack moltenMetal = forge.getResult(forge.state, player, recipes);
			if (moltenMetal != null) {
				remove(block);
				if (player.getInventory().getItemInMainHand().getAmount() > 1) {
					player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
					player.getInventory().addItem(moltenMetal);
				} else {
					player.getInventory().setItemInMainHand(moltenMetal);
				}
				block.getWorld().playEffect(block.getLocation(), Effect.BREWING_STAND_BREW, 0);
				return true;
			}
		}
		return false;
	}
	
	public static List<ItemStack> list(Block block) {
		Forge forge = get(block);
		if (forge != null) {
			return forge.ingredients;
			
		}
		return null;
	}

	// reset to normal cauldron
	public static boolean remove(Block block) {
			Forge forge = get(block);
			if (forge != null) {
				forge.furnace.setBurnTime((short) 0);
				forgeList.remove(forge);
				return true;
			}
			return false;
	}
	
	
	// prints the current cooking time to the player
	public static int printTime(Player player, Block block) {
		
		Forge forge = get(block);
		if (forge != null) {
			return forge.state;
		}
		return -1;
	}

	public static boolean isForge(Block block) {
		Forge forge = get(block);
		if (forge != null) {
			return true;
		}
		return false;
	}
	
	public static List<Forge> getForgeList() {
		return forgeList;
	}
	
	

}
