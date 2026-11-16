package database;

import helpers.DataTypes;
import java.math.BigDecimal;
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

    // This will be different. This will hold user id as the key. Then, have the IPO id.
    // so: theUsersShares = shares.get(the_users_id); -> theUsersShares.get(the_ipo_id); 
    private final Map<Integer,  Map<Integer, Shares>> sharesDatabase = new HashMap<>();


    private BigDecimal funding = new BigDecimal(1000);


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

        // ? type just means "unknown". "Object" won't work here because of my unique classes (for some reason).
        //  Basic Type (T) doesn't work here either
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

    public Shares getUserShares(int userId, IPO ipo){
        if (userId <= 0 || ipo == null){
            lazyErr(400, "getUserShares", "invalid input");
            return null;
        }
        Map<Integer, Shares> theUsersShares = sharesDatabase.get(userId);
        if (theUsersShares == null){
            lazyErr(400, "getUserShares", "user doesn't have shares from "+ipo.title());
            return null;
        }

        int iposId = ipo.getId();

        Shares sharesFromIpo = theUsersShares.get(iposId);
        return sharesFromIpo;
    }

    public boolean updateShare(int userId, int ipoId, Shares share){
        
        Map<Integer, Shares> theUsersShares = this.sharesDatabase.get(userId);
        if (theUsersShares == null || !theUsersShares.containsKey(ipoId)){
            lazyErr(400, "updateShare", "user doesn't own shares for this ipo.");
        }

        this.sharesDatabase.get(userId).put(ipoId, share);
        return true;
    }

    public boolean addShare(int userId, int ipoId, Shares share){
        Map<Integer, Shares> shareMap = new HashMap<>(Map.of(ipoId, share));
        this.sharesDatabase.put(userId, shareMap);
        return true;
    }

    public void disputeSpecialWelcome(User user){
        final BigDecimal amount = new BigDecimal("5");
        if (funding.compareTo(amount) < 0){
            throw new IllegalArgumentException("No more funds in the database");
        }

        try{
            Bank tempBank = new Bank(
                0, 
                "the_best_market_of_all_time_from_julius", 
                1111, 
                amount
            );

            Account userAccount = user.getAccount();
            userAccount.setBank(tempBank);
            userAccount.deposit(amount);

            userAccount.setBank(null);

            
        } catch (Exception ex){
            lazyErr(500, "disputeSpecialWelcome", ex);
        }

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
    public Map<Integer, Map<Integer, Shares>> getAllShares(){ return sharesDatabase; }
    

    // methods
    private void lazyErr(int statusCode, String functionName, Object ex){
    
        String e = String.format(
            "%d: Error occurred during `%s`: %n\t\u2022 %s",
             statusCode, functionName, ex.toString()
        );
        throw new IllegalArgumentException(e);
        //System.err.println(e);
    }

}
