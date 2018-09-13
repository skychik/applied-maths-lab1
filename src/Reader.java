import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class Reader {
    public static  String read(String path) {
        String text = "";
        try {
            byte[] symbols = Files.readAllBytes(Paths.get(path));
            text = new String(symbols);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
