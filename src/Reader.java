import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

class Reader {
    static String read(String path) throws IOException {
        String text;
        byte[] symbols = Files.readAllBytes(Paths.get(path));
        text = new String(symbols);
        return text;
    }
}
