package utpb.science.fair;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import utpb.science.fair.models.category.Category;
import utpb.science.fair.models.category.CategoryProjectsListBuilder;
import utpb.science.fair.models.group.Group;
import utpb.science.fair.models.group.GroupListBuilder;
import utpb.science.fair.models.judge.Judge;
import utpb.science.fair.models.project.Project;
import utpb.science.fair.util.FileUtil;

public class App {

	public static final String PROJECTS_FILE = "src/test/resources/projects/given-projects.txt";

	public static final String JUDGES_FILE = "src/test/resources/given-judges.txt";

	public static void main(String[] args) throws IOException {

		List<Project> projects = FileUtil.readProjectsFile(PROJECTS_FILE);

		List<Category> categories = new CategoryProjectsListBuilder(projects).build();

		List<List<Group>> scienceFairGroups = new LinkedList<>();

		List<Group> groups = new LinkedList<>();

		for (Category category : categories) {
			groups = new GroupListBuilder(category).build();
			scienceFairGroups.add(groups);
		}

		int n = 0;
		for (List<Group> gs : scienceFairGroups) {
			for (Group g : gs) {
				System.out.println(g);
				for (Project p : g.getProjects()) {
					n++;
				}
			}
		}
		
		System.out.println("\nTotal Projects: " + n);
		System.out.println("\nTotal Projects: " + projects.size());
	}

	public void foo() throws IOException {
		System.out.println("=============================================================================");
		System.out.println("PROJECTS");
		System.out.println("=============================================================================");

		List<Project> projects = FileUtil.readProjectsFile(PROJECTS_FILE);
		projects.stream().forEach(System.out::println);
		System.out.println("Total Projects: " + projects.size());
	}

	public void bar() throws IOException {
		System.out.println("\n=============================================================================");
		System.out.println("JUDGES");
		System.out.println("=============================================================================");

		List<Judge> judges = FileUtil.readJudgesFiles(JUDGES_FILE);
		judges.stream().forEach(System.out::println);
		System.out.println("Total Judges: " + judges.size());
	}

}
