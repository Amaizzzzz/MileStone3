package cs5200project.model;

import java.util.Objects;

public class Weapon {

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

  private int itemID;
  private String weaponName;
  private WeaponType weaponType;
  private int gearSlotID;
  private int jobID;
  private int requiredLevel;
  private int damage;
  private WeaponDurability weaponDurability;
  private RankValue rankValue;

  // Constructor
  public Weapon(int itemID, String weaponName, WeaponType weaponType, int gearSlotID, int jobID,
      int requiredLevel, int damage, WeaponDurability weaponDurability, RankValue rankValue) {
    this.itemID = itemID;
    this.weaponName = weaponName;
    this.weaponType = weaponType;
    this.gearSlotID = gearSlotID;
    this.jobID = jobID;
    this.requiredLevel = requiredLevel;
    this.damage = damage;
    this.weaponDurability = weaponDurability;
    this.rankValue = rankValue;
  }

  // Getters and Setters
  public int getItemID() {
    return itemID;
  }

  public void setItemID(int itemID) {
    this.itemID = itemID;
  }

  public String getWeaponName() {
    return weaponName;
  }

  public void setWeaponName(String weaponName){
    this.weaponName = weaponName;
  }

  public WeaponType getWeaponType() {
    return weaponType;
  }

  public void setWeaponType(WeaponType weaponType) {
    this.weaponType = weaponType;
  }

  public int getGearSlotID() {
    return gearSlotID;
  }

  public void setGearSlotID(int gearSlotID) {
    this.gearSlotID = gearSlotID;
  }

  public int getJobID() {
    return jobID;
  }

  public void setJobID(int jobID) {
    this.jobID = jobID;
  }

  public int getRequiredLevel() {
    return requiredLevel;
  }

  public void setRequiredLevel(int requiredLevel) {
    this.requiredLevel = requiredLevel;
  }

  public int getDamage() {
    return damage;
  }

  public void setDamage(int damage) {
    this.damage = damage;
  }

  public WeaponDurability getWeaponDurability() {
    return weaponDurability;
  }

  public void setWeaponDurability(WeaponDurability weaponDurability) {
    this.weaponDurability = weaponDurability;
  }

  public RankValue getRankValue() {
    return rankValue;
  }

  public void setRankValue(RankValue rankValue) {
    this.rankValue = rankValue;
  }


  @Override
  public int hashCode() {
    return Objects.hash(itemID, weaponName, weaponType, gearSlotID, jobID,
        requiredLevel, damage, weaponDurability, rankValue);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;

    Weapon other = (Weapon) obj;
    return itemID == other.itemID
        && Objects.equals(weaponName, other.weaponName)
        && weaponType == other.weaponType
        && gearSlotID == other.gearSlotID
        && jobID == other.jobID
        && requiredLevel == other.requiredLevel
        && damage == other.damage
        && weaponDurability == other.weaponDurability
        && rankValue == other.rankValue;
  }

  @Override
  public String toString() {
    return String.format(
        "Weapon(itemID=%d, weaponName=\"%s %s\", weaponType=%s, gearSlotID=%d, jobID=%d, requiredLevel=%d, damage=%d, weaponDurability=%s, rankValue=%s)",
        itemID, weaponName, weaponType, gearSlotID, jobID,
        requiredLevel, damage, weaponDurability, rankValue);
  }
}