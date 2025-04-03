package cs5200project.model;

import java.util.Objects;

public class GearSlot {
	private int slotID;
	private String slotName;

	public GearSlot(int slotID, String slotName) {
		this.slotID = slotID;
		this.slotName = slotName;
	}

	public GearSlot(String slotName) {
		this.slotName = slotName;
	}

	public int getSlotID() {
		return slotID;
	}

	public void setSlotID(int slotID) {
		this.slotID = slotID;
	}

	public String getSlotName() {
		return slotName;
	}

	public void setSlotName(String slotName) {
		this.slotName = slotName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		GearSlot gearSlot = (GearSlot) o;
		return slotID == gearSlot.slotID
				&& Objects.equals(slotName, gearSlot.slotName);
	}

	@Override
	public int hashCode() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String toString() {
		return String.format("GearSlot(%d, \"%s\")", slotID, slotName);
	}
}
