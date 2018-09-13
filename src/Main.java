import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input path to file");
        String path = sc.nextLine();
        String text = Reader.read(path);

        System.out.println(text);
    }
}
