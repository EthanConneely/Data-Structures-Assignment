package ie.gmit.sw;

import java.io.File;
import java.util.Scanner;

public class Runner
{

	public static void main(String[] args) throws Exception
	{
		Scanner scanner = new Scanner(System.in);

		// Defaults if nothing is specified
		File filesDirectory = new File("./Files/");
		File outputFile = new File("./Output.csv");
		int ngramSize = 4;
		Boolean alphanumFiltering = true;
		Boolean calculatePercentFrequency = false;

		// Infinite loop that closes when you (5) Quit
		while (true)
		{
			System.out.println(ConsoleColour.WHITE);
			System.out.println("************************************************************");
			System.out.println("*                                                          *");
			System.out.println("*                  N-Gram Frequency Builder                *");
			System.out.println("*                                                          *");
			System.out.println("************************************************************");
			System.out.println("(1) Specify Text File Directory (Current: \"" + filesDirectory + "\")");
			System.out.println("(2) Specify n-Gram Size (Current: " + ngramSize + ")");
			System.out.println("(3) Specify Output File (Current: \"" + outputFile + "\")");
			System.out.println("(4) Build n-Grams");
			System.out.println("(5) Quit");
			System.out.println("(6) Toggle Alphanumeric Filtering (Current: " + alphanumFiltering + ")");
			System.out.println("(7) Toggle Calculate Percent Frequency (Current: " + calculatePercentFrequency + ")");

			// Output a menu of options and solicit text from the user
			System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
			System.out.print("Select Option: ");
			System.out.print(ConsoleColour.WHITE);

			int option = scanner.nextInt();

			System.out.println(ConsoleColour.WHITE);

			switch (option)
			{
			case 1:
				System.out.print("Specify text files Directory: ");
				filesDirectory = new File(scanner.next());
				break;

			case 2:
				System.out.print("Specify n-gram Size: ");
				ngramSize = scanner.nextInt();
				break;

			case 3:
				System.out.print("Specify output File: ");
				outputFile = new File(scanner.next());
				break;

			case 4:
				NGramBuilder builder = new NGramBuilder(filesDirectory, outputFile, ngramSize, alphanumFiltering, calculatePercentFrequency);
				builder.build();
				break;

			case 5:
				scanner.close();
				System.exit(0);
				break;

			case 6:
				alphanumFiltering = !alphanumFiltering;
				break;

			case 7:
				calculatePercentFrequency = !calculatePercentFrequency;
				break;
			}
		}
	}
}
