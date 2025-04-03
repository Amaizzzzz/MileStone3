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

  private int weaponId;
  private int damage;
  private int attackSpeed;
  private String weaponType;
  private int requiredLevel;
  private String requiredJob;
  private WeaponDurability weaponDurability;
  private RankValue rankValue;

  // Constructor
  public Weapon(int itemId, String itemName, int itemLevel, int maxStackSize,
                double price, int quantity, int weaponId, int damage, int attackSpeed,
                String weaponType, int requiredLevel, String requiredJob,
                WeaponDurability weaponDurability, RankValue rankValue) {
    super(itemId, itemName, itemLevel, maxStackSize, price, quantity);
    this.weaponId = weaponId;
    this.damage = damage;
    this.attackSpeed = attackSpeed;
    this.weaponType = weaponType;
    this.requiredLevel = requiredLevel;
    this.requiredJob = requiredJob;
    this.weaponDurability = weaponDurability;
    this.rankValue = rankValue;
  }

  // Getters
  public int getWeaponId() {
    return weaponId;
  }

  public int getDamage() {
    return damage;
  }

  public int getAttackSpeed() {
    return attackSpeed;
  }

  public String getWeaponType() {
    return weaponType;
  }

  public int getRequiredLevel() {
    return requiredLevel;
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
  public void setWeaponId(int weaponId) {
    this.weaponId = weaponId;
  }

  public void setDamage(int damage) {
    this.damage = damage;
  }

  public void setAttackSpeed(int attackSpeed) {
    this.attackSpeed = attackSpeed;
  }

  public void setWeaponType(String weaponType) {
    this.weaponType = weaponType;
  }

  public void setRequiredLevel(int requiredLevel) {
    this.requiredLevel = requiredLevel;
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
    return Objects.hash(super.hashCode(), weaponId, damage, attackSpeed, 
                      weaponType, requiredLevel, requiredJob, weaponDurability, rankValue);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!super.equals(obj)) return false;
    if (getClass() != obj.getClass()) return false;
    Weapon other = (Weapon) obj;
    return weaponId == other.weaponId &&
           damage == other.damage &&
           attackSpeed == other.attackSpeed &&
           Objects.equals(weaponType, other.weaponType) &&
           requiredLevel == other.requiredLevel &&
           Objects.equals(requiredJob, other.requiredJob) &&
           weaponDurability == other.weaponDurability &&
           rankValue == other.rankValue;
  }

  @Override
  public String toString() {
    return String.format(
        "Weapon(id=%d, name=\"%s\", level=%d, damage=%d, speed=%d, type=\"%s\", reqLevel=%d, reqJob=\"%s\", durability=%s, rankValue=%s)",
        weaponId, getItemName(), getItemLevel(), damage, attackSpeed, 
        weaponType, requiredLevel, requiredJob, weaponDurability, rankValue);
  }
}