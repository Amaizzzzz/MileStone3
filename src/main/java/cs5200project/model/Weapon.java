package cs5200project.model;

import java.util.Objects;

public class Weapon extends Item {

  // Instants fields
  public enum WeaponType {
    SWORD, HAMMER, AXE, SPEAR, STICK, BOW, CROSSBOW, WAND, GUN, SHIELD, KNIFE, DART, GLOVES
  }

  public enum WeaponDurability {
    NEW, SLIGHT, USED, OLD
  }

  public enum RankValue {
    WHITE, GREEN, BLUE, PURPLE, ORANGE, RED
  }

  private int damage;
  private int attackSpeed;
  private String weaponType;
  private String requiredJob;
  private WeaponDurability weaponDurability;
  private RankValue rankValue;

  // Constructor
	public Weapon(int itemID, String itemName, int itemLevel, int maxStackSize,
			double price, int quantity, int requiredLevel, int damage,
			int attackSpeed, String weaponType, Job requiredJob,
			WeaponDurability weaponDurability, RankValue rankValue) {
		super(itemID, itemName, itemLevel, maxStackSize, price, quantity);
    this.damage = damage;
    this.attackSpeed = attackSpeed;
    this.weaponType = weaponType;
    this.requiredJob = requiredJob.getJobName();
    this.weaponDurability = weaponDurability;
    this.rankValue = rankValue;
  }

  public Weapon(int itemID, String itemName, int itemLevel, int maxStackSize,
			double price, int quantity, int requiredLevel, int damage,
			int attackSpeed, String weaponType, String requiredJob,
			WeaponDurability weaponDurability, RankValue rankValue) {
		super(itemID, itemName, itemLevel, maxStackSize, price, quantity);
    this.damage = damage;
    this.attackSpeed = attackSpeed;
    this.weaponType = weaponType;
    this.requiredJob = requiredJob;
    this.weaponDurability = weaponDurability;
    this.rankValue = rankValue;
  }

  // Getters

  public int getDamage() {
    return damage;
  }

  public int getAttackSpeed() {
    return attackSpeed;
  }

  public String getWeaponType() {
    return weaponType;
  }

  public String getRequiredJob() {
    return requiredJob;
  }

  public WeaponDurability getWeaponDurability() {
    return weaponDurability;
  }

  public RankValue getRankValue() {
    return rankValue;
  }

  // Setters

  public void setDamage(int damage) {
    this.damage = damage;
  }

  public void setAttackSpeed(int attackSpeed) {
    this.attackSpeed = attackSpeed;
  }

  public void setWeaponType(String weaponType) {
    this.weaponType = weaponType;
  }

  public void setRequiredJob(String requiredJob) {
    this.requiredJob = requiredJob;
  }

  public void setWeaponDurability(WeaponDurability weaponDurability) {
    this.weaponDurability = weaponDurability;
  }

  public void setRankValue(RankValue rankValue) {
    this.rankValue = rankValue;
  }

	@Override
	public int hashCode() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Weapon other = (Weapon) obj;
		return attackSpeed == other.attackSpeed && damage == other.damage
				&& rankValue == other.rankValue
				&& Objects.equals(requiredJob, other.requiredJob)
				&& weaponDurability == other.weaponDurability
				&& Objects.equals(weaponType, other.weaponType);
	}

	@Override
	public String toString() {
		return "Weapon [damage=" + damage + ", attackSpeed=" + attackSpeed
				+ ", weaponType=" + weaponType + ", requiredJob=" + requiredJob
				+ ", weaponDurability=" + weaponDurability + ", rankValue="
				+ rankValue + "]";
	}

}