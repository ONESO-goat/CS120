


class Main{
    private static boolean arrayStringsAreEqual(String[] arr1, String[] arr2){
   

        // 2 builders for each String array. This is to consider different array length, 
        // avoiding more complex code trying to compress everything into a singular loop.
        StringBuilder builderForString1 = new StringBuilder();
        StringBuilder builderForString2 = new StringBuilder();
 
        
        // looping array 1
        for (String word : arr1){
            
            builderForString1.append(word);
        }

        // looping array 2
        for (String word : arr2){
            
            builderForString2.append(word);
        }
       
        // if the builder for the first array equals the second builder, this results in "true", else "false"
        return builderForString1.toString().equalsIgnoreCase(builderForString2.toString());
    }
    public static void main(String[] args){
        // TEST CASES
        String[] word1 = {"a", "bc"};
        String[] word2 = {"a", "b", "c"};

        String[] word3  = {"abc", "d", "defg"}; 
        String[] word4 = {"abcddefg"};

        String[] word5 = {"a", "cb"}; 
        String[] word6 = {"ab", "c"};
        
        // INPUT/RESULTS
        boolean areSame1 = arrayStringsAreEqual(word1, word2);
        boolean areSame2 = arrayStringsAreEqual(word3, word4);
        boolean areSame3 = arrayStringsAreEqual(word5, word6);
        
        // OUTPUTS
        System.out.println("Test case 1: " + areSame1);
        System.out.println("Test case 2: " + areSame2);
        System.out.println("Test case 3: " + areSame3);
    }


}