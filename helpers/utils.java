
package helpers;

public class utils{
    private int count = 0;
    
    public utils() {
    }
    
    public void debug(
        final int tier,
        final String info,
        final String theError
    ){
        System.out.printf("[Tier %d] Error occurred during %s: \n\t %s", tier, info, theError );
        count++;
    }

    public void reset(){this.count = 0;}

    /*
        Avoid tedious print command
        P = Helpers::print
    */
    public void print(final Object thing){
        System.out.println(thing);
    }
}