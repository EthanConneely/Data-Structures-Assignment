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

public class NGramBuilder
{
    private File filesDirectory;
    private File outputFile;
    private int ngramSize;
    private Map<String, Long> frequency;
    private Boolean whitespaceFiltering;
    private Boolean calculatePercentFrequency;
    private Boolean useSlidingNGramCalculation;

    public NGramBuilder(File filesDirectory, File outputFile, int ngramSize, Boolean whitespaceFiltering, Boolean calculatePercentFrequency, Boolean useSlidingNGramCalculation)
    {
        this.filesDirectory = filesDirectory;
        this.outputFile = outputFile;
        this.ngramSize = ngramSize;
        this.whitespaceFiltering = whitespaceFiltering;
        this.calculatePercentFrequency = calculatePercentFrequency;
        this.useSlidingNGramCalculation = useSlidingNGramCalculation;
        frequency = new HashMap<>();
    }

    // O(n * n * 1)
    public void build() throws InterruptedException, IOException
    {
        int totalNGrams = 0;

        for (File file : filesDirectory.listFiles())// O(n) number of files
        {
            if (!file.isFile())
            {
                continue;
            }

            System.out.println(ConsoleColour.YELLOW);
            System.out.println(file.getName());

            String content = Files.readString(file.toPath());
            content = content.toLowerCase();

            if (whitespaceFiltering)
            {
                content = content.replaceAll("[^a-z]", "");
            }

            char[] ngram = new char[ngramSize];

            int size = content.length() - (useSlidingNGramCalculation ? ngramSize - 1 : 0);

            for (int i = 0; i < size; i += useSlidingNGramCalculation ? 1 : ngramSize)// O(n) number of chars in each file
            {
                // Only render the progress bar every so often otherwise its way to slow
                if (i % Math.max(size / 100, 1) == 0)
                {
                    Progressbar.printProgress(i, size);
                }

                for (int j = 0; j < ngramSize; j++)// O(1) ngram size is always the same regardless of files contents
                {
                    if ((i + j) < content.length())
                    {
                        ngram[j] = content.charAt(i + j);
                    }
                    else
                    {
                        ngram[j] = '_';
                    }
                    // System.out.print(ngram[j]);
                }

                // System.out.println();

                String t = new String(ngram);
                frequency.put(t, frequency.getOrDefault(t, 0L) + 1L);
                totalNGrams++;
            }
            Progressbar.printProgress(size, size);
            System.out.println();
        }

        // Sort the hashmap
        List<Entry<String, Long>> list = new LinkedList<>(frequency.entrySet());

        // more than likely quicksort so O(n log (n))
        list.sort((a, b) -> -(int) (a.getValue() - b.getValue()));

        // Write the sorted map to csv file
        FileWriter myWriter = new FileWriter(outputFile);
        for (var pair : list)
        {
            String ngram = pair.getKey();
            long freq = pair.getValue();
            double percent = ((double) pair.getValue() / (double) totalNGrams) * 100;
            myWriter.write(ngram + ",");
            myWriter.write(freq + "");
            if (calculatePercentFrequency)
            {
                myWriter.write("," + percent);
            }
            myWriter.write('\n');
        }
        myWriter.close();

        System.out.println();
        System.out.println("Done! Outputing to " + outputFile.toString());
    }
}
