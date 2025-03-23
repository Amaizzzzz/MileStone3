package cs5200project.model;

public class Currency {
    private int currencyID;
    private String currencyName;
    private Integer cap;
    private Integer weeklyCap;

    public Currency(int currencyID, String currencyName, Integer cap, Integer weeklyCap) {
        this.currencyID = currencyID;
        this.currencyName = currencyName;
        this.cap = cap;
        this.weeklyCap = weeklyCap;
    }

    public int getCurrencyID() { return currencyID; }
    public void setCurrencyID(int currencyID) { this.currencyID = currencyID; }

    public String getCurrencyName() { return currencyName; }
    public void setCurrencyName(String currencyName) { this.currencyName = currencyName; }

    public Integer getCap() { return cap; }
    public void setCap(Integer cap) { this.cap = cap; }

    public Integer getWeeklyCap() { return weeklyCap; }
    public void setWeeklyCap(Integer weeklyCap) { this.weeklyCap = weeklyCap; }
}
