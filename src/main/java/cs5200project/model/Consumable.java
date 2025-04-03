package cs5200project.model;

import java.util.Objects;

public class Consumable extends Item {
    private int consumableId;
    private int healingAmount;
    private int manaRestoreAmount;
    private int duration;
    private String effectType;
    private boolean isStackable;
    private int cooldown;

    public Consumable(int itemId, String itemName, int itemLevel, int maxStackSize,
                     double price, int quantity, int consumableId, int healingAmount,
                     int manaRestoreAmount, int duration, String effectType,
                     boolean isStackable, int cooldown) {
        super(itemId, itemName, itemLevel, maxStackSize, price, quantity);
        this.consumableId = consumableId;
        this.healingAmount = healingAmount;
        this.manaRestoreAmount = manaRestoreAmount;
        this.duration = duration;
        this.effectType = effectType;
        this.isStackable = isStackable;
        this.cooldown = cooldown;
    }

    // Getters
    public int getConsumableId() {
        return consumableId;
    }

    public int getHealingAmount() {
        return healingAmount;
    }

    public int getManaRestoreAmount() {
        return manaRestoreAmount;
    }

    public int getDuration() {
        return duration;
    }

    public String getEffectType() {
        return effectType;
    }

    public boolean isStackable() {
        return isStackable;
    }

    public int getCooldown() {
        return cooldown;
    }

    // Setters
    public void setConsumableId(int consumableId) {
        this.consumableId = consumableId;
    }

    public void setHealingAmount(int healingAmount) {
        this.healingAmount = healingAmount;
    }

    public void setManaRestoreAmount(int manaRestoreAmount) {
        this.manaRestoreAmount = manaRestoreAmount;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setEffectType(String effectType) {
        this.effectType = effectType;
    }

    public void setStackable(boolean stackable) {
        isStackable = stackable;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), consumableId, healingAmount,
                          manaRestoreAmount, duration, effectType, isStackable, cooldown);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        Consumable other = (Consumable) obj;
        return consumableId == other.consumableId &&
               healingAmount == other.healingAmount &&
               manaRestoreAmount == other.manaRestoreAmount &&
               duration == other.duration &&
               Objects.equals(effectType, other.effectType) &&
               isStackable == other.isStackable &&
               cooldown == other.cooldown;
    }

    @Override
    public String toString() {
        return String.format(
            "Consumable(id=%d, name=\"%s\", level=%d, healing=%d, mana=%d, duration=%d, effect=\"%s\", stackable=%b, cooldown=%d)",
            consumableId, getItemName(), getItemLevel(), healingAmount, manaRestoreAmount,
            duration, effectType, isStackable, cooldown);
    }
} 