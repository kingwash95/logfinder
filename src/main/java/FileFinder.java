import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileFinder {

    public void search(final String pattern, final File folder, ArrayList<String> result) {
        for (final File f : folder.listFiles()) {

            if (f.isDirectory()) {
                search(pattern, f, result);
            }

            if (f.isFile()) {
                if (f.getName().matches(pattern)) {
                    result.add(f.getAbsolutePath());
                }
            }

        }
    }


    public void searchFile(String fileName, String phrase, List<String> allFind) throws IOException {
        File file = new File(fileName);
        FileReader fileFlow = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileFlow);
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains(phrase)) {
                allFind.add(file.getAbsolutePath());
                break;
            }
        }
        reader.close();

    }

}
