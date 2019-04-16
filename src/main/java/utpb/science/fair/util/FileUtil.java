package utpb.science.fair.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import utpb.science.fair.models.judge.Judge;
import utpb.science.fair.models.judge.JudgeBuilder;
import utpb.science.fair.models.project.Project;
import utpb.science.fair.models.project.ProjectBuilder;

public final class FileUtil {

	private FileUtil() {
		throw new AssertionError("Utility classed should not be instantiated.");
	}

	/**
	 * Creates a {@link List} of {@link Judge}s by <em>tokenizing</em> every line in
	 * the file with {@link StringUtil#tokenize(String, char)} -- blank lines will
	 * be ignored.<br>
	 * Once the lines are <em>tokenized</em>, {@link JudgeBuilder} is used to build
	 * each <code>Judge</code> object.
	 * 
	 * @param fileName the file name in which to read the Projects from, must be
	 *                 non-null.
	 * @return a {@link List} of {@link Judge}s
	 * @throws IOException
	 */
	public static List<Judge> readJudgesFiles(String fileName) throws IOException {
		List<String> file = Files.readAllLines(Paths.get(fileName));
		List<Judge> judges = new LinkedList<>();
		Judge judge = null;

		for (String line : file) {
			if (line.isBlank()) {
				continue;
			}
			judge = new JudgeBuilder(line).build();
			judges.add(judge);
		}

		return judges;
	}

	/**
	 * Creates a {@link List} of {@link Project} by <em>tokenizing</em> every line
	 * in the file with {@link StringUtil#tokenize(String)} -- blank lines will be
	 * ignored.<br>
	 * Once the lines are <em>tokenized</em>, {@link ProjectBuilder} is used to
	 * build each <code>Project</code> object.
	 * 
	 * @param fileName the file name in which to read the Projects from, must be
	 *                 non-null.
	 * @return a {@link List} of {@link Project}s
	 * @throws IOException
	 */
	public static List<Project> readProjectsFile(String fileName) throws IOException {
		List<String> file = Files.readAllLines(Paths.get(fileName));
		List<Project> projects = new LinkedList<>();
		List<String> tokens = null;
		Project project = null;

		for (String line : file) {
			if (line.isBlank()) {
				continue;
			}
			tokens = StringUtil.tokenize(line);
			project = new ProjectBuilder(tokens).build();
			projects.add(project);
		}

		return projects;
	}
}
