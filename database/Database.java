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
    Map<Integer, Bank> externalBankDatabase = new HashMap<>();
    Map<Integer, User> userDatabase = new HashMap<>();
    Map<Integer, Company> companyDatabase = new HashMap<>();
    Map<Integer, IPO> IPODatabase = new HashMap<>();





    public Database(){}

    private Object getObject(
        final int id, 
        final DataTypes dataType
    ) throws IllegalArgumentException
    {
        Map<String, Map<Integer, ?>> datasets = new HashMap<>(Map.of(
            "ACCOUNT", accountDatabase,
            "USER", userDatabase,
            "COMPANY", companyDatabase,
            "BANK", externalBankDatabase,
            "IPO", IPODatabase
        ));

        Map<Integer, Object> d = datasets.get(dataType.toString());
        if (d == null){
            throw new IllegalArgumentException(dataType.toString() + " is not a valid datatype.");
        }

        Object returnedData = d.containsKey(id);
        if (returnedData == null){
            throw new IllegalArgumentException(dataType.toString() + " of id '"+id+"' does not exist." );
        }

        return returnedData;
    }
    
    public Object getData(int id, DataTypes dt){
        Object data = getObject(id, dt);
        return data;
    }

    public boolean updateUser(final int id, final User userData){
        try{
            if (userData == null || !userDatabase.containsKey(id)){
                throw new IllegalArgumentException("Invalid inputs when updating user data.");
            }

            
            userDatabase.put(id, userData);
            return true;
        }
        catch (IllegalArgumentException ex){
            System.err.println("An error occurred when updating user: " + ex.toString());
            return false;
        }
    }
}
