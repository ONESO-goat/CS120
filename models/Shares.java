package models;
import java.time.LocalDate;

public class Shares {
    final private int id;
    final private int userId;

    final private IPO ipo;

    private long amountHeld = 0;
    private LocalDate recentPurchaseDate = LocalDate.now();

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


    // METHODS
    public void addShares(long amount){
        this.amountHeld += amount;
    }


}
