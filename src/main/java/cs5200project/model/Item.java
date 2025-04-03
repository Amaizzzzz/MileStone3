package cs5200project.model;

import java.util.Objects;

public class Item {
	private int itemId;
	private String itemName;
	private int itemLevel;
	private int maxStackSize;
	private double price;
	private int quantity;

	public Item(int itemId, String itemName, int itemLevel, int maxStackSize,
			double price, int quantity) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemLevel = itemLevel;
		this.maxStackSize = maxStackSize;
		this.price = price;
		this.quantity = quantity;
	}

	public int getItemId() {
		return itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public int getItemLevel() {
		return itemLevel;
	}

	public int getMaxStackSize() {
		return maxStackSize;
	}

	public double getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setItemLevel(int itemLevel) {
		this.itemLevel = itemLevel;
	}

	public void setMaxStackSize(int maxStackSize) {
		this.maxStackSize = maxStackSize;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return itemId == other.itemId && itemLevel == other.itemLevel
				&& Objects.equals(itemName, other.itemName)
				&& maxStackSize == other.maxStackSize
				&& Double.doubleToLongBits(price) == Double
						.doubleToLongBits(other.price)
				&& quantity == other.quantity;
	}

	@Override
	public String toString() {
		return String.format(
				"Item(id=%d, name=\"%s\", level=%d, stackSize=%d, price=%.2f, quantity=%d)",
				itemId, itemName, itemLevel, maxStackSize, price, quantity);
	}
}
