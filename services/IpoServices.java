package services;

import database.Database;
import java.math.BigDecimal;
import models.Company;
import models.IPO;
import models.Shares;

public class IpoServices {

    
    public IPO releaseIPO(
        Company holder,
        int id,
        String ipoTitle,
        BigDecimal startingPrice,
        long amountOfShares
    ){
        IPO ipo = new IPO(id, ipoTitle, startingPrice, holder, amountOfShares);
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

        Shares userShares = db.getUserShares(requestUserId, ipo);

        if (userShares == null){
            Shares userShare = new Shares(0, requestUserId, ipo, requestedAmount);
            db.addShare(requestUserId, ipo.getId(), userShare);
            return true;
        } else {
            userShares.addShares(requestedAmount);
            db.updateShare(requestUserId, ipo.getId(), userShares);
            return true;
        }
        return false;
    }
}
