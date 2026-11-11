package models;
import java.math.BigDecimal;
import java.time.LocalDate;




class IPO{
    private final int id;

    private BigDecimal pricePerShare;

    private final String ipoName;
    private final Company company;

    private final LocalDate dateReleased = LocalDate.now();

    private long amountOfShares;

    public IPO(
        final int ipoId, 
        final String theIpoName,
        final BigDecimal startingPrice,
        final Company theCompany,
        final long companyShares
    ) throws IllegalArgumentException
    {
        if ( companyShares < 1 
            || theCompany == null 
            || startingPrice.compareTo(BigDecimal.ZERO) < 0
        ){
            throw new IllegalArgumentException("Invalid Company details. Either missing or flawed");
        }

        this.id = ipoId;
        this.ipoName = theIpoName;
        this.pricePerShare = startingPrice;
        this.company = theCompany;
        this.amountOfShares = companyShares;
    }

    // GETS
    public Company getCompany(){ return this.company; }
    public int getId(){ return this.id; }
    public String title(){ return this.ipoName; }
    public BigDecimal getPricePerShare(){ return this.pricePerShare; }
    public long getAmountOfShares(){ return this.amountOfShares; }
    public LocalDate getReleaseDate(){ return this.dateReleased; }

    // SETS
    public void updatePricePerShare(BigDecimal newPrice){
        companyExists();

        if (newPrice.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("New price is invalid.");
        }

        this.pricePerShare = newPrice;
    }

    public void updateAmountOfShares(long newAmount){
        companyExists();

        if (newAmount < 0){
            throw new IllegalArgumentException("New amount is invalid.");
        }

        this.amountOfShares = newAmount;
    }

    private void companyExists(){
        if (this.company == null){
            throw new IllegalArgumentException("No company found for this IPO.");
        }
    }


}