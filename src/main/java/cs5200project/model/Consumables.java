package cs5200project.model;

import java.util.Objects;

public class Consumables {

  // Enum for Consumables Type
  public enum ConsumablesType {
    GROCERIES, MEDICINE, THROWING, MATERIAL, AMMUNITION
  }

  // Fields
  private int itemID;
  private String consumablesName;
  private ConsumablesType consumablesType;
  private String consumablesDescription;
  private String source;

  // Constructor
  public Consumables(int itemID, String consumablesName, ConsumablesType consumablesType,
      String consumablesDescription, String source) {
    this.itemID = itemID;
    this.consumablesName = consumablesName;
    this.consumablesType = consumablesType;
    this.consumablesDescription = consumablesDescription;
    this.source = source;
  }

  // Getters and Setters
  public int getItemID() {
    return itemID;
  }

  public void setItemID(int itemID) {
    this.itemID = itemID;
  }

  public String getConsumablesName() {
    return consumablesName;
  }

  public void setConsumablesName(String consumablesName) {
    this.consumablesName = consumablesName;
  }

  public ConsumablesType getConsumablesType() {
    return consumablesType;
  }

  public void setConsumablesType(ConsumablesType consumablesType) {
    this.consumablesType = consumablesType;
  }

  public String getConsumablesDescription() {
    return consumablesDescription;
  }

  public void setConsumablesDescription(String consumablesDescription) {
    this.consumablesDescription = consumablesDescription;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  // equals and hashCode
  @Override
  public int hashCode() {
    return Objects.hash(itemID, consumablesName, consumablesType, consumablesDescription, source);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;

    Consumables other = (Consumables) obj;
    return itemID == other.itemID &&
        Objects.equals(consumablesName, other.consumablesName) &&
        consumablesType == other.consumablesType &&
        Objects.equals(consumablesDescription, other.consumablesDescription) &&
        Objects.equals(source, other.source);
  }

  @Override
  public String toString() {
    return String.format("Consumables(itemID=%d, name=\"%s\", type=%s, description=\"%s\", source=\"%s\")",
        itemID, consumablesName, consumablesType, consumablesDescription, source);
  }
}

