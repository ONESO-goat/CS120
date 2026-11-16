
import database.Database;
import helpers.DataTypes;
import java.math.BigDecimal;
import models.*;
import services.*;





class Main{

    public record FakeBank(int id, long amount) {} // fake bank

    public static UserService userService = new UserService();
    public static AccountServices accountService = new AccountServices();
    public static IpoAndCompanyServices ICService = new IpoAndCompanyServices();

    public static void p(String d){
        System.out.println(d);
    }

    public static Database initializeDatabase(int howMany){
        int id;
       
        Database db = new Database();

        String userName;

        // create User
        String[] userNames = new String[howMany+1];
        for (id = 1; id <= howMany; id++){
            userName = String.format("user_test%d", id);
            User user = userService.createUser(id, userName, "12345678"); 
            Account account = new Account(id, id, new BigDecimal("1000"));
            user.setAccount(account);

            db.disputeSpecialWelcome(user); // lazy way to give users $5 for testing

            
            db.addOrUpdateObject(DataTypes.USER, id, user);
            db.addOrUpdateObject(DataTypes.ACCOUNT, id, account);

            userNames[id] = userName;
        }

        p("=".repeat(10)+" Users "+"=".repeat(10));
        for (String name : userNames){
            if (name == null){continue;}
            p("Created user: "+name);
        }

        // create companies

        String[] companyNames = new String[howMany+1];
        String companyName;
        String ipoName;
        BigDecimal startingPrice = new BigDecimal("10");
        long howManyShares = 1000;

        for (id = 1; id <= howMany; id++){
            companyName = String.format("company_test%d", id);
            Company company = ICService.createCompany(id, companyName);
            
            ipoName = String.format("%s_ipo", companyName);
            IPO companyIpo = ICService.releaseIPO(company, id, ipoName, startingPrice, howManyShares);
            
            db.addOrUpdateObject(DataTypes.COMPANY, id, company);
            db.addOrUpdateObject(DataTypes.IPO, id, companyIpo);

            companyNames[id] = companyName;
        }

        p("=".repeat(10)+" Companies "+"=".repeat(10));
        for (String name : companyNames){
            if (name == null){continue;}
            p("Created company: "+name);
        }

        return db;
    }



    public static void testTrade(Database db){
        final int id = 1;

        User user = db.getAllUsers().get(id);
        Company company = db.getAllCompanies().get(id);
        IPO ipo = company.getCompanyIpo();

        int howManySharesToBuy = 10;
        boolean success = ICService.buy(id, ipo, howManySharesToBuy, db);
        if (!success){
            return;
        }
        
        // TODO: create report here
        p(String.format(
            "User '%s' bought %d shares from Company '%s'.%n", 
            user.getUsername(), howManySharesToBuy, company.getName()
        )); 

        success = ICService.sell(id, ipo, 5, db);
        if (!success){
            return;
        }

        p(String.format(
            "User '%s' sold 5 shares from Company '%s'.%n", 
            user.getUsername(), company.getName()
        )); 

        Shares userShares = db.getUserShares(id, ipo);
        Account userAccount = user.getAccount();

        p(String.format(
            "User has %d left in their account. They hold %d shares from the company '%s'",
            userAccount.getBalance(), userShares.getAmountHeld(), company.getName()
        ));
        
    }






    private static void CS120Project(){
        Database db = initializeDatabase(3);
        
        

    }

    public static void main(String[] args){
        CS120Project();
    }


}