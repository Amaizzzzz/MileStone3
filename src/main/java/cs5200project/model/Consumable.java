package cs5200project.model;

import java.util.Objects;

public class Consumable extends Item {

  public enum ConsumablesType {
    GROCERIES, MEDICINE, THROWING, MATERIAL, AMMUNITION
  }

  private ConsumablesType consumablesType;
  private String consumablesDescription;
  private String source;

  // Constructor
	public Consumable(int itemID, String itemName, int itemLevel,
			int maxStackSize, double price, int quantity,
			ConsumablesType consumablesType,
			String consumablesDescription, String source) {
		super(itemID, itemName, itemLevel, maxStackSize, price, quantity);
    this.consumablesType = consumablesType;
    this.consumablesDescription = consumablesDescription;
    this.source = source;
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
		Consumable other = (Consumable) obj;
		return Objects.equals(consumablesDescription,
				other.consumablesDescription)
				&& consumablesType == other.consumablesType
				&& Objects.equals(source, other.source);
	}

	@Override
	public String toString() {
		return "Consumables [consumablesType=" + consumablesType
				+ ", consumablesDescription=" + consumablesDescription
				+ ", source=" + source + "]";
	}

}

