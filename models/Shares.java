package models;
import java.time.LocalDate;

public class Shares {
    final private int id;
    final private int userId;

    final private IPO ipo;

    private long amountHeld = 0;
    private LocalDate lastUpdate = LocalDate.now();

    final private LocalDate firstPurchasedDate = LocalDate.now();

    public Shares(
        int givenId,
        int theUserId,
        IPO theIpo,
        long amountHolding
    ){
        this.id = givenId;
        this.userId = theUserId;
        this.ipo = theIpo;
        this.amountHeld = amountHolding;
    }

    
    // SETS
    public int getUserId(){ return this.userId; }
    public int getId(){ return this.id; }
    public IPO getIpo(){ return this.ipo; }
    public long getAmountHeld(){ return this.amountHeld; }
    public LocalDate getLastUpdate(){ return this.lastUpdate; }
    public LocalDate getFirstBoughtDate(){ return this.firstPurchasedDate; }

    // METHODS
    public void addShares(long amount){
        this.amountHeld += amount;
        this.lastUpdate = LocalDate.now();
    }

    public void removeShares(long amount){
        this.amountHeld -= amount;
        this.lastUpdate = LocalDate.now();
    }
}
