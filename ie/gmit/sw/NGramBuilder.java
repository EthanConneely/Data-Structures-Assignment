package ie.gmit.sw;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class NGramBuilder {

    private File filesDirectory;
    private File outputFile;
    private int ngramSize;
    private Map<String, Long> frequency;
    private Boolean alphanumFiltering;
    private Boolean calculatePercentFrequency;

    public NGramBuilder(File filesDirectory, File outputFile, int ngramSize, Boolean alphanumFiltering,
            Boolean calculatePercentFrequency) {
        this.filesDirectory = filesDirectory;
        this.outputFile = outputFile;
        this.ngramSize = ngramSize;
        this.alphanumFiltering = alphanumFiltering;
        this.calculatePercentFrequency = calculatePercentFrequency;
        frequency = new HashMap<>();
    }

    public void build() throws InterruptedException, IOException {
        int totalNGrams = 0;

        for (File file : filesDirectory.listFiles()) {

            if (!file.isFile()) {
                continue;
            }

            System.out.println(file.getName());
            Progressbar.display();
            System.out.println();
            System.out.println();

            String content = Files.readString(file.toPath());

            if (alphanumFiltering) {
                content = content.toLowerCase().replaceAll("[^a-zA-Z]", "");
            }

            char[] ngram = new char[ngramSize];
            for (int i = 0; i < content.length() - ngramSize; i++) {
                for (int j = 0; j < ngramSize; j++) {
                    ngram[(i + j) % ngramSize] = content.charAt(i + j);
                }
                String t = new String(ngram);
                frequency.put(t, frequency.getOrDefault(t, 0L) + 1L);
                totalNGrams++;
            }
        }

        // Sort the hashmap
        List<Entry<String, Long>> list = new LinkedList<>(frequency.entrySet());
        list.sort((a, b) -> -(int) (a.getValue() - b.getValue()));

        // Write the sorted map to csv file
        FileWriter myWriter = new FileWriter(outputFile);

        for (var pair : list) {
            String ngram = pair.getKey();
            long freq = pair.getValue();
            double percent = ((double) pair.getValue() / (double) totalNGrams) * 100;
            myWriter.write(ngram + ",");
            myWriter.write(freq + "");
            if (calculatePercentFrequency) {
                myWriter.write("," + percent);
            }
            myWriter.write('\n');
        }
        myWriter.close();
    }
}
