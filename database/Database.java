package database;

import helpers.DataTypes;
import java.util.HashMap;
import java.util.Map;
import models.*;

public class Database{
    /*
        Databases for each schema. 
        Each hashmap holds IDs as keys, then the object for values. This is for quick calls
    */
    private final Map<Integer, Account> accountDatabase = new HashMap<>();
    private final Map<Integer, Bank> externalBankDatabase = new HashMap<>();
    private final Map<Integer, User> userDatabase = new HashMap<>();
    private final Map<Integer, Company> companyDatabase = new HashMap<>();
    private final Map<Integer, IPO> IPODatabase = new HashMap<>();


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

        final String dataTypeString = dataType.toString();

        // ? type just means "unknown". Object won't work here
        Map<Integer, ?> data = datasets.get(dataTypeString);
        if (data == null){
            throw new IllegalArgumentException(dataTypeString + " is not a valid datatype.");
        }

        Object returnedData = data.get(id);
        if (returnedData == null){
            throw new IllegalArgumentException(dataTypeString + " of id '"+id+"' does not exist." );
        }

        return returnedData;
    }

    public boolean addOrUpdateObject(
        final DataTypes dt,
        final int objectId,
        final Object data
    ){  
        
        if (data == null){
            return false;
        }

        String dataTypeString = dt.toString();
            
        Map<String, Map<Integer, ?>> datasets = new HashMap<>(Map.of(
            "ACCOUNT", accountDatabase,
            "USER", userDatabase,
            "COMPANY", companyDatabase,
            "BANK", externalBankDatabase,
            "IPO", IPODatabase
        ));

        Map<Integer, ?> dm = datasets.get(dataTypeString);
        if (dm == null){
            throw new IllegalArgumentException(dataTypeString + " is not a valid datatype.");
        }

        Map<Integer, Object> dataMap = (Map<Integer, Object>) dm; 
        dataMap.put(objectId, data);
        return true; 
    }


    public Object getData(int id, DataTypes dt){
        Object data = getObject(id, dt);
        return data;
    }

    // public boolean updateUser(final int id, final User userData){
    //     try{
    //         if (userData == null || !userDatabase.containsKey(id)){
    //             throw new IllegalArgumentException("Invalid inputs when updating user data.");
    //         }

    //         userDatabase.put(id, userData);
    //         return true;
    //     }
    //     catch (IllegalArgumentException ex){
    //         System.err.println("An error occurred when updating user: " + ex.toString());
    //         return false;
    //     }
    // }


    /*
        returns map with keys, being ID, and Objects, being values.
        ex; {
            "1234": <account_object_1234>
        }  
        {
            "5942": <user_object_5942>
        }     
    */
    public Map<Integer, Account> getAllAccounts(){ return accountDatabase; }
    public Map<Integer, User> getAllUsers(){ return userDatabase; }
    public Map<Integer, Bank> getAllBanks(){ return externalBankDatabase; }
    public Map<Integer, Company> getAllCompanies(){ return companyDatabase; }

}
