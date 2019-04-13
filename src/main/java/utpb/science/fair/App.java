package utpb.science.fair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import utpb.science.fair.models.judge.Judge;
import utpb.science.fair.models.judge.JudgeBuilder;
import utpb.science.fair.models.project.CategoryNameComparator;
import utpb.science.fair.models.project.Project;
import utpb.science.fair.models.project.ProjectBuilder;
import utpb.science.fair.models.project.ProjectNumberComparator;
import utpb.science.fair.util.StringUtil;

public class App {

	public static void main(String[] args) throws IOException {
//		App app = new App();

		// List<Project> projects = app.readProjectsFile();
		// app.testCategoryNameComparator(projects);

//		List<Judge> judges = app.readJudgesFiles();
//		judges.stream().forEach(System.out::println);

	}

	public List<Judge> readJudgesFiles() throws IOException {
		List<String> file = Files.readAllLines(Paths.get("judges.txt"));
		List<Judge> judges = new LinkedList<>();
		Judge judge = null;

		for (String line : file) {
			judge = new JudgeBuilder(line).build();
			judges.add(judge);
		}

		return judges;
	}

	public List<Project> readProjectsFile() throws IOException {
		List<String> file = Files.readAllLines(Paths.get("projects.txt"));
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

	public void testCategoryNameComparator(List<Project> projects) {
		Collections.sort(projects, new CategoryNameComparator());
		projects.stream().forEach(System.out::println);
	}

	public void testProjectNumberComparator(List<Project> projects) {
		Collections.sort(projects, new ProjectNumberComparator());
		projects.stream().forEach(System.out::println);
	}

}
