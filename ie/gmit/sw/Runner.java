package ie.gmit.sw;

import java.io.File;
import java.util.Scanner;

public class Runner {

	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		// Defaults if nothing is specified
		File filesDirectory = new File("./Files/");
		File outputFile = new File("./Output.csv");
		int ngramSize = 4;
		Boolean alphanumFiltering = false;

		while (true) {
			// You should put the following code into a menu or Menu class
			System.out.println(ConsoleColour.WHITE);
			System.out.println("************************************************************");
			System.out.println("*      GMIT - Dept. Computer Science & Applied Physics     *");
			System.out.println("*                                                          *");
			System.out.println("*                  N-Gram Frequency Builder                *");
			System.out.println("*                                                          *");
			System.out.println("************************************************************");
			System.out.println("(1) Specify Text File Directory");
			System.out.println("(2) Specify n-Gram Size");
			System.out.println("(3) Specify Output File");
			System.out.println("(4) Build n-Grams ");
			System.out.println("(5) Quit");
			System.out.println("(6) Enable Alphanumeric Filtering");

			// Output a menu of options and solicit text from the user
			System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
			System.out.print("Select Option: ");
			System.out.print(ConsoleColour.WHITE);

			int option = scanner.nextInt();

			System.out.println(ConsoleColour.WHITE);

			switch (option) {
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
					NGramBuilder builder = new NGramBuilder(filesDirectory, outputFile, ngramSize);
					builder.build();
					break;

				case 5:
					scanner.close();
					System.exit(0);
					break;

				case 6:
					alphanumFiltering = !alphanumFiltering;
					System.out.println("Toggling alphanumeric filtering: " + alphanumFiltering);
					Thread.sleep(1000);
					break;
			}
		}
	}
}
