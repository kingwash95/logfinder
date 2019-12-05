import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

public class FileFinder {
    //возвращает лист строк файла, где были найдены схожие фразы
    public List searchString(String fileName, String phrase) throws IOException{
        Scanner fileScanner = new Scanner(new File(fileName));
        int lineID = 0;
        List lineNumbers = new ArrayList();
        Pattern pattern =  Pattern.compile(phrase, Pattern.CASE_INSENSITIVE);
        Matcher matcher = null;
        while(fileScanner.hasNextLine()){
            String line = fileScanner.nextLine();
            lineID++;
            matcher = pattern.matcher(line);
            if(matcher.find()){
                lineNumbers.add(lineID);
            }
        }
        return lineNumbers;
    }

    public  void search(final String pattern, final File folder, ArrayList<String> result) {
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

    public void searchFile(String fileName, String phrase, List<String> allFind) throws IOException{
        File file = new File(fileName);
        Scanner fileScanner = new Scanner(file);
        Pattern pattern =  Pattern.compile(phrase, Pattern.CASE_INSENSITIVE);
        Matcher matcher = null;
        while(fileScanner.hasNextLine()){
            String line = fileScanner.nextLine();
            matcher = pattern.matcher(line);
            if(matcher.find()){
                allFind.add(file.getAbsolutePath());
            }
        }
    }

    public void searchFile2(String fileName, String phrase, List<String> allFind) throws IOException {
        File file = new File(fileName);
        FileReader fileFlow = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileFlow);
        String line;
        while((line = reader.readLine()) != null ){
            if(line.contains(phrase)){
                allFind.add(file.getAbsolutePath());
                break;
            }
        }
        reader.close();

    }

    public static void main(String[] args) throws IOException {
       /* Scanner scan = new Scanner(System.in);
        System.out.println("Укажите путь для поиска файлов:\n");
        String fileDirectory = scan.nextLine();*/
       FileFinder files = new FileFinder();
       List arr = files.searchString("C:\\Users\\ROBIN\\Desktop\\p.log", "Мама мыла раму");
        for (Object s : arr) {
            System.out.println(s);
        }

        final File folder = new File("C:\\Users\\ROBIN\\Desktop");

        List<String> result = new ArrayList();

       // search(".*\\.log", folder, result);

        List<String> allFind = new ArrayList<String>();

        for (String s : result) {
            files.searchFile(s,"Мама мыла раму", allFind);
            //System.out.println(s);
        }

        for (Object s : allFind){
            System.out.println(s+"\n");
        }
    }
}
