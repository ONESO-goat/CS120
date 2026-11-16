package models;
import database.Database;
import helpers.DataTypes;
import java.time.LocalDate;


public class User{
    private final int id;
    private String username;
    private int nameChangesCount = 0;
    private String password;
     // for this project, I am going to use plain string. In production, this should never be the case.
     
    private Account account;

    private final LocalDate addedAt = LocalDate.now();;

    public User( 
        final int userId,
        final String  givenUsername, 
        final String givenPassword
    ) 
    {
        this.validateName(username);
        this.id = userId;
        this.username = givenUsername;
        this.password = givenPassword;
    }



    // GETS
    public int getId(){return this.id;}
    public String getUsername (){ return this.username; }
    public Account getAccount(){return this.account;}
    public LocalDate getCreationDate(){return this.addedAt;}

    // SETS
    public void setNewPassword(String newPassword){
        newPassword = newPassword.strip();

        if (newPassword.length() < 8 || newPassword.length() > 120){
            throw new IllegalArgumentException("400: Password is falls outside the valid length.");
        }

        if (newPassword.isBlank() || newPassword.isEmpty()){
            throw new IllegalArgumentException("400: Invalid password");
        }
        
        this.password = newPassword;
    }



    // Methods

    private void validateName(final String name) throws IllegalArgumentException {
        if (name == null || name.isEmpty()){
            throw new IllegalArgumentException("400: Name is invalid");
        }

    }

    /*
        Validate against illegal names
    */
    public boolean changeName(final String newName, final Database db) throws IllegalArgumentException{
        if (nameChangesCount >= 5){
            throw new IllegalArgumentException("400: Cannot change name as max limit was reached.");
        }

        if (db == null){
            throw new IllegalArgumentException("500: Database not found");
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
            throw new IllegalArgumentException("400: Name '"+newName+"' is not valid.");
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