package cs5200project.model;

import java.util.Objects;

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

    // Getters and Setters
    public int getCurrencyID() { 
        return currencyID; 
    }
    
    public void setCurrencyID(int currencyID) { 
        this.currencyID = currencyID; 
    }

    public String getCurrencyName() { 
        return currencyName; 
    }
    
    public void setCurrencyName(String currencyName) { 
        this.currencyName = currencyName; 
    }

    public Integer getCap() { 
        return cap; 
    }
    
    public void setCap(Integer cap) { 
        this.cap = cap; 
    }

    public Integer getWeeklyCap() { 
        return weeklyCap; 
    }
    
    public void setWeeklyCap(Integer weeklyCap) { 
        this.weeklyCap = weeklyCap; 
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(currencyID, currencyName, cap, weeklyCap);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
            
        Currency other = (Currency) obj;
        return currencyID == other.currencyID 
                && Objects.equals(currencyName, other.currencyName)
                && Objects.equals(cap, other.cap)
                && Objects.equals(weeklyCap, other.weeklyCap);
    }
    
    @Override
    public String toString() {
        return String.format(
                "Currency(id=%d, name=\"%s\", cap=%s, weeklyCap=%s)",
                currencyID, currencyName, 
                cap == null ? "null" : cap.toString(),
                weeklyCap == null ? "null" : weeklyCap.toString());
    }
}
