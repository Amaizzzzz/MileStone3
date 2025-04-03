package cs5200project.model;

import java.util.Objects;

public class GearStatisticBonus {
	private Item item;
	private Statistic stats; // Need Statistic table
	private int bonusValue;

	public GearStatisticBonus(Item item, Statistic stats, int bonusValue) {
		this.item = item;
		this.stats = stats;
		this.bonusValue = bonusValue;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Statistic getStats() {
		return stats;
	}

	public void setStats(Statistic stats) {
		this.stats = stats;
	}

	public int getBonusValue() {
		return bonusValue;
	}

	public void setBonusValue(int bonusValue) {
		this.bonusValue = bonusValue;
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
		GearStatisticBonus other = (GearStatisticBonus) obj;
		return bonusValue == other.bonusValue
				&& Objects.equals(stats, other.stats)
				&& Objects.equals(item, other.item);
	}

	@Override
	public String toString() {
		return String.format(
				"GearStatisticBonus(itemID=%d, statID=%d, bonusValue=%d)",
				item.getItemId(), stats.getStatisticID(), bonusValue);
	}
}