package com.atlasmc.atlasforgetech;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;


public class ForgeRecipe {
	public static HashMap<Material,String> possibleIngredients;
	public static Set<String> specialIngredients;
	public static HashMap<String,String> recipeHintWords;
	public static boolean hintsEnabled;
	String name;
	String tag;
	Material baseItem;
	double durability;
	int mResistance;
	int time;
	HashMap<String,Integer> ingredients;
	HashMap<String,Double> effects;
	HashMap<String,Boolean> slots;
	
	
	public ForgeRecipe(String name, String tag, Material base, double durability, int mResistance, int time) {
		setName(name);
		setTag(tag);
		setMaterial(base);
		setDurability(durability);
		setmResistance(mResistance);
		setTime(time);
		ingredients = null;
		effects = null;
		slots = null;
	}
	
	public int getmResistance() {
		return mResistance;
	}

	public void setmResistance(int mResistance) {
		this.mResistance = mResistance;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public Material getMaterial() {
		return baseItem;
	}
	public void setMaterial(Material material) {
		this.baseItem = material;
	}
	
	public double getDurability() {
		return durability;
	}
	public void setDurability(double durability) {
		this.durability = durability;
	}
	
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	
	public HashMap<String,Integer> getIngredients() {
		return ingredients;
	}
	public void setIngredients(HashMap<String, Integer> ingredients) {
		this.ingredients = ingredients;
	}
	
	public HashMap<String, Double> getEffects() {
		return effects;
	}
	public void setEffects(HashMap<String, Double> effects) {
		this.effects = effects;
	}
	public HashMap<String, Boolean> getSlots() {
		return slots;
	}
	public void setSlots(HashMap<String, Boolean> slots) {
		this.slots = slots;
	}
	
	public boolean isValid() {
		if(name == null) return false;
		if(tag == null) return false;
		if(baseItem == null) return false;
		if(ingredients == null) return false;
		if(effects == null) return false;
		if(slots == null) return false;
		return true;
	}
	
	public static Set<Material> getPossibleIngredients() {
		return possibleIngredients.keySet();
	}
	public static void setPossibleIngredients(HashMap<Material,String> ingredients) {
		possibleIngredients = ingredients;
	}
	public static String getFailedRecipeIngredient(Material material) {
		return possibleIngredients.get(material);
	}
	
	public static Set<String> getSpecialIngredients() {
		return specialIngredients;
	}
	public static void setSpecialIngredients(Set<String> ingredients) {
		specialIngredients = ingredients;
	}

	public static HashMap<String, String> getRecipeHintWords() {
		return recipeHintWords;
	}

	public static void setRecipeHintWords(HashMap<String, String> recipeHintWords) {
		ForgeRecipe.recipeHintWords = recipeHintWords;
	}

	public static boolean isHintsEnabled() {
		return hintsEnabled;
	}

	public static void setHintsEnabled(boolean hintsEnabled) {
		ForgeRecipe.hintsEnabled = hintsEnabled;
	}
	
}
