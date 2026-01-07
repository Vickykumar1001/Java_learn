package java_usecases.case1;

import java.util.Scanner;

public class SWIFTCodeValidator {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter SWIFT/BIC code: ");
		String swiftCode = scanner.nextLine();
		validateSWIFTCode(swiftCode);
	}

	public static void validateSWIFTCode(String code) {
		if(code==null || code=="") {
			System.out.println("Please provide the code.");
		}
		if (code.length() != 8 && code.length() != 11) {
			System.out.println(code + " is INVALID: Invalid Length.");
			return;
		}

		if (!isAlphanumeric(code)) {
			System.out.println(code + " is INVALID: Contains non-alphanumeric characters.");
			return;
		}

		String institutionCode = code.substring(0, 4);
		String countryCode = code.substring(4, 6);
		String locationCode = code.substring(6, 8);
		String branchCode = code.length() == 11 ? code.substring(8) : null;

		if (!isAlphabetic(institutionCode)) {
			System.out.println(code + " is INVALID: Institution Code must be alphabetic.");
			return;
		}

		if (!isAlphabetic(countryCode)) {
			System.out.println(code + " is INVALID: Country Code must be alphabetic.");
			return;
		}

		if (!isAlphanumeric(locationCode)) {
			System.out.println(code + " is INVALID: Location Code contains invalid characters.");
			return;
		}

		if (branchCode != null && !isAlphanumeric(branchCode)) {
			System.out.println(code + " is INVALID: Branch Code contains invalid characters.");
			return;
		}

		System.out.println(code + " is VALID");
	}

	public static boolean isAlphanumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isLetterOrDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isAlphabetic(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isLetter(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}
