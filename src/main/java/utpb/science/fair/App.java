package utpb.science.fair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import utpb.science.fair.project.CategoryNameComparator;
import utpb.science.fair.project.Project;
import utpb.science.fair.project.ProjectNumberComparator;
import utpb.science.fair.util.StringUtil;

public class App {

	public static void main(String[] args) throws IOException {
		App app = new App();

		List<Project> projects = app.readProjectsFile();

	}

	public List<Project> readProjectsFile() throws IOException {
		List<String> file = Files.readAllLines(Paths.get("projects.txt"));
		List<String> tokens = null;
		List<Project> projects = new LinkedList<>();
		Project project = null;

		for (String line : file) {
			tokens = StringUtil.tokenize(line);
			project = new Project.Builder(tokens).build();
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
