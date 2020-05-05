import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.exit;

public class CW2Q2 {

	// list to store names to be sorted
	private static List<String> names = new ArrayList<>();


	// method to find length of string
	// ALTERNATIVE to length()
	int string_length(String str) {
		char[] charArray = str.toCharArray();
		int length = 0;
		for (char c : charArray) {
			length++;
		}
		return length;
	}


	// method to add modified names to array
	private int setNames() {
		try {
			File file = new File("./names.txt");
			Scanner sc = new Scanner(file);
			// reads the file as a CSV
			sc.useDelimiter(",");
			int nameCount = 0;
			// loop to add names from file to list
			while (sc.hasNext()) {

				// changes string to character array to allow string manipulation
				String name = sc.next();
				int length = string_length(name);
				char[] nameArray = name.toCharArray();

				// loop to remove speech marks from names
				String new_name = "";
				for (int c = 0; c < length; c++) {
					if (nameArray[c] != '\"') {

						// ALTERNATIVE to String Builder
						new_name = new_name + nameArray[c];

					}
				}
				// adds the modified name to list
				names.add(new_name);
				nameCount++;
			}
			sc.close();
			return nameCount;
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			exit(1);
			return -1;
		}
	}


	// method to swap two names in the list
	void swap(int i, int j) {
		String temp = names.get(i);
		names.set(i, names.get(j));
		names.set(j, temp);
	}

	// ALTERNATIVE to Math.min call
	private int mathMin(String str1, String str2) {
		if (string_length(str1) < string_length(str2)) {
			return string_length(str1);
		} else {
			return string_length(str2);
		}
	}

	// method to partition names into individual sub-lists
	int partition(int low, int high) {
		int i = low - 1;
		String pivot = names.get(high);

		// loop parses through each name in list
		for (int n = low; n < high; n++) {

			String check = names.get(n);

			int length = mathMin(check, pivot);

			int j = 0;
			// loop to compare corresponding characters in each name
			while (j < length) {
				if (check.charAt(j) == pivot.charAt(j)) {
					// this condition performs a swap if the characters are the same until the end of one, e.g. Adrian and Adria
					if (length == string_length(check) && j == length - 1) {
						i++;
						swap(i, n);
						break;
					}

					j++;
				} else if (check.charAt(j) < pivot.charAt(j)) {
					i++;
					swap(i, n);
					break;
				} else {
					break;
				}
			}
		}

		swap(i + 1, high);

		return i + 1;
	}


	// method to call partitions and recursively call itself until list is sorted
	private void sort_names(int low, int high) {
		if (low < high) {
			int index = partition(low, high);

			sort_names(low, index - 1);
			sort_names(index + 1, high);
		}
	}


	// method to print the list
	private void printList() {
		for (String num : names) {
			System.out.print(num + " ");
		}
		System.out.println();
	}


	public static void main(String[] args) {
		// object instantiation
		CW2Q2 ob = new CW2Q2();

		// retrieves names from file and adds them to list
		int nameCount = ob.setNames();

		// sort array of names
		ob.sort_names(0, nameCount - 1);

		// print sorted list of names
		ob.printList();
	}
}