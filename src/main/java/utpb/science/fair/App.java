package utpb.science.fair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import utpb.science.fair.models.judge.Judge;
import utpb.science.fair.models.judge.JudgeBuilder;
import utpb.science.fair.models.project.Project;
import utpb.science.fair.models.project.ProjectBuilder;
import utpb.science.fair.util.StringUtil;

public class App {

	public static void main(String[] args) throws IOException, URISyntaxException {
		App app = new App();

		List<Project> projects = app.readProjectsFile();

		System.out.println("(=============================================================================");

		List<Judge> judges = app.readJudgesFiles();
		judges.stream().forEach(System.out::println);

	}

	public List<Judge> readJudgesFiles() throws IOException, URISyntaxException {
		List<String> file = Files.readAllLines(Paths.get("src/test/resources/judges.txt"));
		List<Judge> judges = new LinkedList<>();
		Judge judge = null;

		for (String line : file) {
			judge = new JudgeBuilder(line).build();
			judges.add(judge);
		}

		return judges;
	}

	public List<Project> readProjectsFile() throws IOException {
		List<String> file = Files.readAllLines(Paths.get("src/test/resources/projects.txt"));
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
