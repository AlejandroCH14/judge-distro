package utpb.science.fair.seed;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ThreadLocalRandom;

public final class SeedWork {

	public static final String[] CATEGORIES = { "Behavioral/Social Science", "Chemistry", "Earth/Space Science",
			"Environmental Science", "Life Science", "Mathematics/Physics" };

	/**
	 * Creates a <code>projects.txt</code> file with 300 projects, numbered 400-700, to use as
	 * dummy data. The file has whitespaces before and after the project number and
	 * project name.
	 */
	public void createProjectsFile() {
		final String fileName = "projects.txt";
		final Path filePath = Paths.get(fileName);
		final int min = 0;
		final int max = CATEGORIES.length;
		final int[] categoryIndices = new int[301];
		int randomIndex = 0;

		for (int i = 0; i <= 300; i++) {
			randomIndex = ThreadLocalRandom.current().nextInt(min, max);
			categoryIndices[i] = randomIndex;
		}

		try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.TRUNCATE_EXISTING)) {
			String line = null;
			int j = 0;
			for (int i = 400; i <= 700; i++) {
				line = String.format(" %d  %s %n", i, CATEGORIES[categoryIndices[j++]]);
				writer.write(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
