package com.atlasmc.atlasforgetech;

public class Defaults {
	int health = 0;
	double damage = 0.0;
	double speed = 0.0;
	double knockback = 0;
	int armor = 0;
	int toughness = 0;
	double attackspeed = 0;
	int luck = 0;
	
	public Defaults(double attackspeed, double damage) {
		this.damage = damage;
		this.attackspeed = attackspeed;
	}
	public Defaults(int armor, int toughness) {
		this.armor = armor;
		this.toughness = toughness;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public double getDamage() {
		return damage;
	}
	public void setDamage(double damage) {
		this.damage = damage;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public double getKnockback() {
		return knockback;
	}
	public void setKnockback(double knockback) {
		this.knockback = knockback;
	}
	public int getArmor() {
		return armor;
	}
	public void setArmor(int armor) {
		this.armor = armor;
	}
	public int getToughness() {
		return toughness;
	}
	public void setToughness(int toughness) {
		this.toughness = toughness;
	}
	public double getAttackspeed() {
		return attackspeed;
	}
	public void setAttackspeed(double attackspeed) {
		this.attackspeed = attackspeed;
	}
	public int getLuck() {
		return luck;
	}
	public void setLuck(int luck) {
		this.luck = luck;
	}
}
