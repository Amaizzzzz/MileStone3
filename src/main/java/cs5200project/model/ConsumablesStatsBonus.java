package cs5200project.model;

import java.util.Objects;

public class ConsumablesStatsBonus {

  // Fields
  private Item item;
  private Statistic stats;
  private float percentageBonus;
  private int bonusCap;

  // Constructor
  public ConsumablesStatsBonus(Item item, Statistic stats, float percentageBonus, int bonusCap) {
    this.item = item;
    this.stats = stats;
    this.percentageBonus = percentageBonus;
    this.bonusCap = bonusCap;
  }

  // Getters and Setters
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

  public float getPercentageBonus() {
    return percentageBonus;
  }

  public void setPercentageBonus(float percentageBonus) {
    this.percentageBonus = percentageBonus;
  }

  public int getBonusCap() {
    return bonusCap;
  }

  public void setBonusCap(int bonusCap) {
    this.bonusCap = bonusCap;
  }

  // equals and hashCode
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    ConsumablesStatsBonus other = (ConsumablesStatsBonus) obj;
    return Float.compare(other.percentageBonus, percentageBonus) == 0 &&
        bonusCap == other.bonusCap &&
        Objects.equals(item, other.item) &&
        Objects.equals(stats, other.stats);
  }

  @Override
  public int hashCode() {
    return Objects.hash(item, stats, percentageBonus, bonusCap);
  }

  @Override
  public String toString() {
    return String.format(
        "ConsumablesStatsBonus(itemID=%d, statID=%d, percentageBonus=%.2f, bonusCap=%d)",
        item.getItemId(), stats.getStatisticID(), percentageBonus, bonusCap);
  }
}