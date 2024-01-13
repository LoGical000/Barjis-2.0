package Models;

//import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class Shell {

    public static final Map<String, Integer> shellMap = createShellMap();

    public static Map<String, Integer> createShellMap() {
        Map<String, Integer> shellMap = new LinkedHashMap<>();
        shellMap.put("BARA", 12);
        shellMap.put("BANJ", 25);
        shellMap.put("FOUR", 4);
        shellMap.put("THREE", 3);
        shellMap.put("DOAQ", 2);
        shellMap.put("DAST", 10);
        shellMap.put("SHAKKE", 6);
        //shellMap.put("KHAL", 1);
        return shellMap;
    }


    public static int throwing() {
        // Get a random index
        int randomIndex = getRandomIndexWithProbabilities();

        // Get a random key
        String[] keys = shellMap.keySet().toArray(new String[0]);
        String randomKey = keys[randomIndex];



        int result = shellMap.get(randomKey);

        // Print the random key and its corresponding value
        System.out.println(" Shell: " + randomKey);
        System.out.println(" Value: " + result);



        // Return the corresponding value
        return shellMap.get(randomKey);
    }

    static public Random random = new Random();
    static int getRandomIndexWithProbabilities() {
        int [] probabilities = {40, 369, 1385, 2765, 3111, 1860, 466};
        int sum = 10000;


        int rnd = random.nextInt(sum);
        int tempSum = 0;
        for (int i = 0; i < shellMap.size(); i++) {
            tempSum += probabilities[i];
            if (rnd < tempSum) {
                return i;
            }
        }




        return shellMap.size() - 1;
    }
}


