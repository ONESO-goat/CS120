package models;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Account{


    private final int id;

    private final LocalDate createdAtDate = LocalDate.now();

    private Bank externalBank = null;

    private BigDecimal balance = BigDecimal.ZERO;

    private final int ownerId;

    public Account(
        final int accountId,
        final BigDecimal accountBalance,
        final int accountOwnerId
    ) 
    {  
        this.id = accountId;
        this.ownerId = accountOwnerId;
        this.balance = accountBalance;
    }


    // GETS
    public int getId(){return this.id;}
    public BigDecimal getAmount(){return this.balance;}
    public LocalDate getAddedDate(){return this.createdAtDate;}
    public int getOwnerId(){return this.ownerId;}

    // SETS
    public void setBank(Bank newBank){ this.externalBank = newBank; }

    // Methods
    public boolean withdraw(final BigDecimal removedAmount){
        if (this.externalBank == null || removedAmount.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }

        boolean successful = this.externalBank.deposit(removedAmount);
         if (!successful){
            return false;
        }
        
        this.balance = this.balance.subtract(removedAmount);

        return true;
    }

    public boolean deposit(final BigDecimal gainedAmount){
        if (this.externalBank == null || gainedAmount.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }

        boolean successful = externalBank.withdraw(gainedAmount);
        if (!successful){
            return false;
        }
        this.balance = this.balance.add(gainedAmount);
        
        return true;
    }

    public String accountDetails(){
        return String.format(
            "id: %d %nOwner id: %d %nCurrent balance: %.2f%nCreation date: %s", 
            this.id, this.ownerId, this.balance, this.createdAtDate.toString()
        );
       
    }

    
}