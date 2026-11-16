
import database.Database;
import helpers.DataTypes;
import java.math.BigDecimal;
import models.Account;
import models.User;
import services.userService;




class Main{

    public record FakeBank(int id, long amount) {} // fake bank

    public static Database createDatabaseForTesting(int howMany){
        int id;
        userService userService = new userService();
        Database db = new Database();

        String userName;

        String[] userNames = new String[howMany];
        for (id = 0; id < howMany; id++){
            userName = String.format("test%d", id);
            User user = userService.createUser(id, userName, "12345678"); 
            Account account = new Account(id, id, BigDecimal.ZERO);

            
            db.addOrUpdateObject(DataTypes.USER, id, user);
            db.addOrUpdateObject(DataTypes.ACCOUNT, id, account);

            userNames[id] = userName;
        }

        for (String name : userNames){
            System.out.println("Created user: "+name);
        }

        return db;
    }
    private static void CS120Project(){
        Database db = createDatabaseForTesting(3);

    }

    public static void main(String[] args){
        CS120Project();
    }


}