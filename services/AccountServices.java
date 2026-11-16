package services;

import database.Database;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import models.Account;
import models.User;

public class AccountServices {
    


    /*
        id:
            Id for the account.
        
            returns the account if found, else null.
    */
    public Account getAccountById(final int id, Database db){
        Map<Integer, Account> a = db.getAllAccounts();
        return a.get(id); // returns null if not found.
    }

    public Account getAccountByUser(final int id, Database db){
        Map<Integer, User> a = db.getAllUsers();
        User user = a.get(id);
        if (user == null){
            return null;
        }
        return user.getAccount();
        
    }

    public Account createAccount(final int id, final User user) {
        if (id < 1 || user == null){
            throw new IllegalArgumentException("Invalid data given.");
        }
        Account account = new Account(id, user.getId(), new BigDecimal(BigInteger.ZERO));
        return account;
    }




}
