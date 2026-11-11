package services;

import java.util.HashMap;
import java.util.Map;
import models.Account;
import models.ExternalAccount;

public class AccountServices<T> {
    
    /*
        returns map with keys being ID, and accounts being values.
        ex; {
            "1234": <account_object_1234>

        }    
    */
    public Map<String, Account> getAllAccounts(){

        return new HashMap<>();
    }

    /*
        id:
            Id for the account.
        
            returns the account if found, else null.
    */
    public Account getAccountById(final String id){
        return null;
    }

    public Account getAccountByUser(final String username){
        return null;
    }

    public Account createAccount(Map<String, T> data) {
        return null;
    }

    public ExternalAccount addExternalAccount(){
        return null;
    }




}
