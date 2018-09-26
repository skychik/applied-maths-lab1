import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String text;
        while (true) {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.println("* Input path to file:");
                String path = sc.nextLine();
                System.out.println("*** Reading the text");
                text = Reader.read(path);
                break;
            }
            catch (IOException e) {
                System.out.println("!!! No such file or wrong path. Try again");
                e.printStackTrace();
            }
        }

        //System.out.println("*** Text: \n" + text);
        System.out.println("*** Analysing");
        EntropyCounter ec = new EntropyCounter(text);
        System.out.println("* Simple entropy: \n" + ec.getSimpleEntropy());
        System.out.println("* Pair entropy: \n" + ec.getPairEntropy());
        Map<Character, Double> simpleEntropies = ec.getSimpleEntropies();
        for (char c = 'a'; c <= 'z'; c++) {
            System.out.println(Character.toUpperCase(c) + ": " + simpleEntropies.get(c));
        }
        System.out.println("* Spaces: " + simpleEntropies.get(' '));
        System.out.println("* Puncts: " + simpleEntropies.get('.'));
    }
}
