package cs5200project.model;

public class Gear extends Item {
    private int requiredLevel;

	public Gear(int itemID, String itemName, int itemLevel, int maxStackSize,
			double price, int quantity, int requiredLevel) {
		super(itemID, itemName, itemLevel, maxStackSize, price, quantity);
        this.requiredLevel = requiredLevel;
	}

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(int requiredLevel) {
        this.requiredLevel = requiredLevel;
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
		Gear other = (Gear) obj;
		return requiredLevel == other.requiredLevel;
	}

	@Override
    public String toString() {
		return String.format("Gear(requiredLevel=%d)", requiredLevel);
    }
}







