package cs5200project.model;

import java.util.Objects;

public class CharacterCurrency {
    private int characterID;
    private int currencyID;
    private int currentAmount;
    private boolean isCurrent;

    public CharacterCurrency(int characterID, int currencyID, int currentAmount, boolean isCurrent) {
        this.characterID = characterID;
        this.currencyID = currencyID;
        this.currentAmount = currentAmount;
        this.isCurrent = isCurrent;
    }

    // Getters and Setters
    public int getCharacterID() { 
        return characterID; 
    }
    
    public void setCharacterID(int characterID) { 
        this.characterID = characterID; 
    }

    public int getCurrencyID() { 
        return currencyID; 
    }
    
    public void setCurrencyID(int currencyID) { 
        this.currencyID = currencyID; 
    }

    public int getCurrentAmount() { 
        return currentAmount; 
    }
    
    public void setCurrentAmount(int currentAmount) { 
        this.currentAmount = currentAmount; 
    }

    public boolean isCurrent() { 
        return isCurrent; 
    }
    
    public void setCurrent(boolean current) { 
        isCurrent = current; 
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(characterID, currencyID, currentAmount, isCurrent);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
            
        CharacterCurrency other = (CharacterCurrency) obj;
        return characterID == other.characterID 
                && currencyID == other.currencyID
                && currentAmount == other.currentAmount
                && isCurrent == other.isCurrent;
    }
    
    @Override
    public String toString() {
        return String.format(
                "CharacterCurrency(characterID=%d, currencyID=%d, currentAmount=%d, isCurrent=%s)",
                characterID, currencyID, currentAmount, isCurrent);
    }
}
