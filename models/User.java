package models;
import database.Database;
import helpers.DataTypes;
import java.time.LocalDate;


public class User{
    private final int id;
    private String username;
    private int nameChangesCount = 0;
    private String hashedPassword;
    private Account account;

    private final LocalDate addedAt = LocalDate.now();;

    public User( 
        final int userId,
        final String  givenUsername, 
        final String hashedPass
    ) 
    {
        this.validateName(username);
        this.id = userId;
        this.username = givenUsername;
        this.hashedPassword = hashedPass;
    }



    // GETS
    public int getId(){return this.id;}
    public String getUsername (){ return this.username; }
    public Account getAccount(){return this.account;}
    public LocalDate getCreationDate(){return this.addedAt;}




    private void validateName(final String name) throws IllegalArgumentException {
        if (name == null || name.isEmpty()){
            throw new IllegalArgumentException("Name is invalid");
        }

    }

    /*
        Validate against illegal names
    */
    public boolean changeName(final String newName, final Database db) throws IllegalArgumentException{
        if (nameChangesCount >= 5){
            throw new IllegalArgumentException("Cannot change name as max limit was reached.");
        }

        if (db == null){
            throw new IllegalArgumentException("Database not found");
        }
        final String oldName = this.username; 
        final String newName_ = newName.toLowerCase().strip();
        /*
            Keeping it simple for the time being.
        */
        if (
            newName_.contentEquals(this.username.toLowerCase()) 
            || 
            newName_.isEmpty() 
            || 
            newName_.length() > 100
        ){
            throw new IllegalArgumentException("Name '"+newName+"' is not valid.");
        }
        this.username = newName;
        
        boolean success = db.addOrUpdateObject(DataTypes.USER, this.id, this);
        if (!success){
            this.username = oldName;
            return false;
        }
        this.nameChangesCount++;
        return true;
    }

}