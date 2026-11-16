package services;

import database.Database;
import helpers.DataTypes;
import java.math.BigDecimal;
import models.*;


public class IpoAndCompanyServices {

    public Company createCompany(
        int id,
        String name
    ){
        Company company = new Company(id, name);
        if (company == null){
            throw new IllegalArgumentException("Company was not created");
        }
        return company;
    }
    
    public IPO releaseIPO(
        Company holder,
        int id,
        String ipoTitle,
        BigDecimal startingPrice,
        long amountOfShares
    ){
        IPO ipo = new IPO(id, ipoTitle, startingPrice, holder, amountOfShares);
        holder.setIpo(ipo);
        return ipo;
    }


    public boolean buy(
        int requestUserId,
        IPO ipo,
        long requestedAmount,
        Database db
    ){
        if (requestedAmount <= 0){
            throw new IllegalArgumentException("404: You can't buy no or negative currency mate.");
        }

        if (ipo.getAmountOfShares() < requestedAmount){
            throw new IllegalArgumentException("400: Invalid amount of shares.");
        }

        User user = db.getAllUsers().get(requestUserId);
        if (user == null){
            throw new IllegalArgumentException("404: User does not exist");
        }

        // price per share requested.
        BigDecimal cost = ipo.getPricePerShare().multiply(new BigDecimal(requestedAmount));

        Account usersAccount = user.getAccount();
        if (usersAccount.getBalance().compareTo(cost) < 0){
            throw new IllegalArgumentException("400: Can't afford this amount.");
        }
                
        Shares userShares = db.getUserShares(requestUserId, ipo);

        if (userShares == null){

            usersAccount.withdraw(cost);
            Shares userShare = new Shares(0, requestUserId, ipo, requestedAmount);
            db.addShare(requestUserId, ipo.getId(), userShare);
            db.addOrUpdateObject(DataTypes.ACCOUNT, usersAccount.getId(), usersAccount);
            return true;

        } else {

            userShares.addShares(requestedAmount);
            db.updateShare(requestUserId, ipo.getId(), userShares);
            return true;

        }   
    }

    public boolean sell(
        int requestUserId,
        IPO ipo,
        long requestedAmount,
        Database db
    ){
        if (requestedAmount <= 0){
            throw new IllegalArgumentException("404: You can't buy no or negative currency mate.");
        }

        if (ipo.getAmountOfShares() < requestedAmount){
            throw new IllegalArgumentException("400: Invalid amount of shares.");
        }

        final BigDecimal ipoPricePerShare = ipo.getPricePerShare();

        Shares userShares = db.getUserShares(requestUserId, ipo);

        if (userShares == null){
            throw new IllegalArgumentException("400: User does not have any shares for this ipo");
        } 

        // Account logic
        AccountServices accountService = new AccountServices();

        Account account = accountService.getAccountByUser(requestUserId, db);
        account.deposit(ipoPricePerShare.multiply(new BigDecimal(requestedAmount)));
        
        db.addOrUpdateObject(DataTypes.ACCOUNT, account.getId(), account);
        

        // Removing shares
        userShares.removeShares(requestedAmount);
        db.updateShare(requestUserId, ipo.getId(), userShares);
        return true;
        
    }
}
