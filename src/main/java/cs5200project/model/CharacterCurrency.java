package cs5200project.model;

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

    public int getCharacterID() { return characterID; }
    public void setCharacterID(int characterID) { this.characterID = characterID; }

    public int getCurrencyID() { return currencyID; }
    public void setCurrencyID(int currencyID) { this.currencyID = currencyID; }

    public int getCurrentAmount() { return currentAmount; }
    public void setCurrentAmount(int currentAmount) { this.currentAmount = currentAmount; }

    public boolean isCurrent() { return isCurrent; }
    public void setCurrent(boolean current) { isCurrent = current; }
}
