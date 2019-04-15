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
		throw new AssertionError();
	}

	public static List<Judge> readJudgesFiles(String fileName) throws IOException {
		List<String> file = Files.readAllLines(Paths.get(fileName));
		List<Judge> judges = new LinkedList<>();
		Judge judge = null;

		for (String line : file) {
			judge = new JudgeBuilder(line).build();
			judges.add(judge);
		}

		return judges;
	}

	public static List<Project> readProjectsFile(String fileName) throws IOException {
		List<String> file = Files.readAllLines(Paths.get(fileName));
		List<Project> projects = new LinkedList<>();
		List<String> tokens = null;
		Project project = null;

		for (String line : file) {
			tokens = StringUtil.tokenize(line);
			project = new ProjectBuilder(tokens).build();
			projects.add(project);
		}

		return projects;
	}
}
