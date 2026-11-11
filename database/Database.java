package database;

import exceptions.AccountNotFoundException;
import exceptions.UserNotFoundException;
import java.util.HashMap;
import java.util.Map;
import models.*;
import helpers.DataTypes;

public class Database {
    /*
        Databases for each schema. 
        Each hashmap holds IDs as keys, then the object for values. This is for quick calls
    */
    Map<Integer, Account> accountDatabase = new HashMap<>();
    Map<Integer, User> userDatabase = new HashMap<>();
    Map<Integer, Company> companyDatabase = new HashMap<>();
    Map<Integer, IPO> IPODatabase = new HashMap<>();





    public Database(){}
    private void idExists(
        final int id, 
        final DataType dataType
    ) : AccountNotFoundException
    {
        Map<String, Map> datasets = new HashMap<>(
            ("")
        );
        if (!accountDatabase.containsKey(id)){
            throw new AccountNotFoundException(dataType.toString() + " of id '"+id+"' does not exist." );
        }
    }
    public Account getAccount(final int id){
        idExists(id, DataType.Account);
        return accountDatabase.get(id);
    }

    public User getUser(final String id){
    
        idExists(id, DataType.User);
        return userDatabase.get(id);
    }

    public boolean updateUser(final String id, final User userData){
        try{
            if (userData == null || id.isEmpty()){
                throw new IllegalArgumentException("Invalid inputs when updating user data.");
            }

            if (!userDatabase.containsKey(id)){
                // TODO: Further validation needed, like making sure format is legal.
                userDatabase.put(id, userData);
                return true;
            }
            userDatabase.put(id, userData);
            return true;
        }
        catch (Exception ex){
            System.err.println("An error occurred when updating user: " + ex.toString());
            return false;
        }
    }
}
