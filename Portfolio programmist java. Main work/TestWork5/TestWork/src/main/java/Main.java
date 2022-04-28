import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        StringBuilder input = new StringBuilder();
        StringBuilder output = new StringBuilder();

        List<String> lines = Files.readAllLines( new File("C:\\Users\\User\\russianmorphology\\TestWork\\src\\main\\java\\input.txt").toPath(), StandardCharsets.UTF_8 );
        lines.forEach(l -> input.append(l).append("\n") );
        System.out.println(input.toString());
        HashMap<String,String> outputMap = new HashMap<>();

        String[] arrays = input.toString().split("[0-9]");
        System.out.println(arrays[1] + arrays[2]);
        String[] firstWords = arrays[1].split("\\n");
        String[] secondWords = arrays[2].split("\\n");
        for (int i = 1; i < firstWords.length; i++) {
            String[] singleWord = firstWords[i].split("\\s");
            outputMap.put(firstWords[i],"?");
            for (int j = 0; j < singleWord.length; j++) {
                String stringForPattern = singleWord[j];
            Pattern patternOfword = Pattern.compile(stringForPattern);
            for(int k = 1; k < secondWords.length; k++) {
                Matcher matcherOfWords = patternOfword.matcher(secondWords[k]);

               while (matcherOfWords.find()){
                  outputMap.put(firstWords[i],secondWords[k]);
               }
            }
            }
        }
        for (Map.Entry keyToValue : outputMap.entrySet()) {
            output.append(keyToValue.getKey() + ": " + keyToValue.getValue()).append("\n");
        }
        FileWriter writer = new FileWriter("C:\\Users\\User\\russianmorphology\\TestWork\\src\\main\\java\\output.txt",false);
        writer.write(output.toString());
        writer.flush();
        writer.close();
    }
}
