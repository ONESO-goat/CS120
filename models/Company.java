package models;

import java.time.LocalDate;

public class Company {
    private final int id;
    private final String name;
    private final LocalDate dateAdded = LocalDate.now();
    private final LocalDate dayOfIPORelease = null;

    private boolean companyHasIpo = false;

    public Company(
        int companyId,
        String companyName
    ){
        this.name = companyName;
        this.id = companyId;
    }

    public String getName(){ return this.name; }
    public int getId(){ return this.id; }
    public LocalDate getAddedDate(){ return this.dateAdded; }
    public LocalDate getDateOfIPORelease(){ return this.dayOfIPORelease; }

    public boolean hasIpo(){ return this.companyHasIpo; }

    public void setHasIpo(){ this.companyHasIpo = true; }   


}
