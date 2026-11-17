package models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Bank {

    private final int id;
    private final LocalDate dateAdded = LocalDate.now();

    private final String bankProvider;

    private final long bankNumber;

    private BigDecimal balance;

    public Bank(
        final int bankId,
        final String theBankProvider,
        final long theBankNumber,
        final BigDecimal bankBalance
    ){
        this.id = bankId;
        this.bankProvider = theBankProvider;
        this.bankNumber = theBankNumber;
        this.balance = bankBalance;
    }

    // GETS

    public int getId(){ return this.id; }
    public long getBankNumber(){ return this.bankNumber; }
    public BigDecimal getBalance(){ return this.balance; }
    public String getBankProvider(){ return this.bankProvider; }
    public LocalDate getAddedDate(){ return this.dateAdded; }



    // Methods
    public boolean withdraw(final BigDecimal removedAmount){
        if (removedAmount.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }

        BigDecimal newBalance = this.balance.subtract(removedAmount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0){
            return false;
        }

        this.balance = newBalance;

        return true;
    }

    public boolean deposit(final BigDecimal gainedAmount){
        if (gainedAmount.compareTo(BigDecimal.ZERO) <= 0) { // adding $0 or negative balance
            return false;
        }

        BigDecimal newBalance = this.balance.add(gainedAmount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0){ 
            return false;
        }

        this.balance = newBalance;

        return true;
    }

    public String accountDetails(){
        return String.format(
            "id: %d %nProvider: %s%nBank Number: %d%nCurrent balance: %.2f%nCreation date: %s", 
            this.id, this.bankProvider, this.bankNumber, this.balance, this.dateAdded.toString()
        );
       
    }


}
