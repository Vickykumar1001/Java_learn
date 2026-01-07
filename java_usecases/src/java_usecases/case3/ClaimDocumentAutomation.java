package java_usecases.case3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ClaimDocumentAutomation {
	private static final String BASE_DIR = "claims_processing";
	private static final String INCOMING_DIR = BASE_DIR + "/incoming_claims";
	private static final String PROCESSING_DIR = BASE_DIR + "/processing_claims";
	private static final String PROCESSED_DIR = BASE_DIR + "/processed_claims";
	private static final String ERROR_DIR = BASE_DIR + "/error_claims";
	private static final String ARCHIVE_DIR = BASE_DIR + "/claims_archive";

	public static void main(String[] args) {
		try {
			initializeDirectories();

			while (true) {
				monitorIncomingClaims();
				TimeUnit.SECONDS.sleep(5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void initializeDirectories() throws IOException {
		createDirectory(INCOMING_DIR);
		createDirectory(PROCESSING_DIR);
		createDirectory(PROCESSED_DIR);
		createDirectory(ERROR_DIR);
		createDirectory(ARCHIVE_DIR);
	}

	private static void createDirectory(String dirPath) throws IOException {
		Path path = Paths.get(dirPath);
		if (Files.notExists(path)) {
			Files.createDirectories(path);
			log("Created directory: " + dirPath);
		}
	}

	private static void monitorIncomingClaims() throws IOException {
		File incomingDir = new File(INCOMING_DIR);
		File[] files = incomingDir.listFiles();

		if (files != null) {
			for (File file : files) {
				if (file.isFile()) {
					processFile(file);
				}
			}
		}
	}

	private static void processFile(File file) throws IOException {
		String fileName = file.getName();
		log("Detected file: " + fileName);

		if (isCorruptFile(fileName)) {
			moveFileToErrorDirectory(file);
		} else if (isValidClaimFile(fileName)) {
			moveFileToProcessingDirectory(file);
		} else {
			log("File does not meet valid claim or error file criteria: " + fileName);
		}
	}

	private static boolean isCorruptFile(String fileName) {
		return fileName.contains("unreadable") || fileName.endsWith(".corrupt");
	}

	private static boolean isValidClaimFile(String fileName) {
		return fileName.startsWith("claim_");
	}

	private static void moveFileToErrorDirectory(File file) throws IOException {
		String errorFilePath = ERROR_DIR + "/" + file.getName();
		Files.move(file.toPath(), Paths.get(errorFilePath), StandardCopyOption.REPLACE_EXISTING);
		log("Moved corrupt file to error_claims: " + file.getName());
		logError("Corrupt file detected: " + file.getName());
	}

	private static void moveFileToProcessingDirectory(File file) throws IOException {
		String processingFilePath = PROCESSING_DIR + "/" + file.getName();
		Files.move(file.toPath(), Paths.get(processingFilePath), StandardCopyOption.REPLACE_EXISTING);
		log("Moved file to processing_claims: " + file.getName());
		File processedFile = new File(processingFilePath);
		simulateProcessing(processedFile);
	}

	private static void simulateProcessing(File file) throws IOException {
		log("Processing file: " + file.getName());
		moveFileToProcessedDirectory(file);
	}

	private static void moveFileToProcessedDirectory(File file) throws IOException {
		String processedFilePath = PROCESSED_DIR + "/" + file.getName();
		Files.move(file.toPath(), Paths.get(processedFilePath), StandardCopyOption.REPLACE_EXISTING);
		log("Moved file to processed_claims: " + file.getName());
		File processedFile = new File(processedFilePath);
		archiveFile(processedFile);
	}

	private static void archiveFile(File file) throws IOException {
		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String archiveDir = ARCHIVE_DIR + "/" + timestamp;
		createDirectory(archiveDir);
		String archiveFilePath = archiveDir + "/" + file.getName();
		Files.move(file.toPath(), Paths.get(archiveFilePath), StandardCopyOption.REPLACE_EXISTING);
		log("Archived file: " + file.getName() + " to " + archiveDir);
	}

	private static void log(String message) {
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " - " + message);
	}

	private static void logError(String message) {
		System.err.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " - ERROR: " + message);
	}
}
