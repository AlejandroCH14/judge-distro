package utpb.science.fair;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

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

		App app = new App();

		List<Project> projects = FileUtil.readProjectsFile(PROJECTS_FILE);

		List<Judge> judges = FileUtil.readJudgesFiles(JUDGES_FILE);

		List<Category> categories = new CategoryProjectsListBuilder(projects).build();

		List<List<Group>> scienceFairGroups = app.createAllCategoryGroups(categories);

		app.assignJudgesToCategories(categories, judges);

		System.out.println();

		PriorityQueue<Judge> resources = app.createResourcesQueue(judges);

		System.out.println();

		PriorityQueue<Category> tasks = app.createTaskQueue(categories);

	}

	public PriorityQueue<Judge> createResourcesQueue(List<Judge> judges) {
		PriorityQueue<Judge> resourcesQ = new PriorityQueue<Judge>(Judge.LEAST_CATEGORY_COUNT);
		resourcesQ.addAll(judges);

		Judge judge = null;

		System.out.println("================================================================");
		System.out.println("Resources");
		System.out.println("================================================================");
		while (!resourcesQ.isEmpty()) {
			judge = resourcesQ.poll();
			System.out.format("%s = %d%n", judge.getFullName(), judge.getCategories().size());
		}

		return resourcesQ;
	}

	public PriorityQueue<Category> createTaskQueue(List<Category> categories) {
		PriorityQueue<Category> taskQ = new PriorityQueue<Category>(Category.GREATEST_PROJECT_COUNT);
		taskQ.addAll(categories);

		Category category = null;

		System.out.println("================================================================");
		System.out.println("Tasks");
		System.out.println("================================================================");
		while (!taskQ.isEmpty()) {
			category = taskQ.poll();
			System.out.format("%s = %d%n", category.getName(), category.getProjects().size());
		}

		return taskQ;
	}

	public void assignJudgesToCategories(List<Category> categories, List<Judge> judges) {
		Collections.sort(categories);
		final String[] CATEGORIES = Category.CATEGORIES; // already sorted
		int index = -1;

		for (Judge judge : judges) {
			for (String category : judge.getCategories()) {
				// the list of categories for each judge are already sorted, thus
				index = Arrays.binarySearch(CATEGORIES, category);
				if (index < 0) {
					continue;
				}
				categories.get(index).addJudge(judge);
			}
		}

		// now print the judges that each category has
		for (Category category : categories) {
			System.out.println("================================================================");
			System.out.println(category.getName());
			System.out.println("================================================================");
			for (Judge judge : category.getJudges()) {
				System.out.println(judge.getFullName());
			}
			System.out.println("================================================================\n");
		}
	}

	public List<List<Group>> createAllCategoryGroups(List<Category> categories) {
		// a science fair will have 1...* many groups
		List<List<Group>> scienceFairGroups = new LinkedList<>();
		List<Group> groups = new LinkedList<>();

		// each category will have 1...* many to groups
		for (Category category : categories) {
			groups = new GroupListBuilder(category).build();
			scienceFairGroups.add(groups);
		}

		// make sure we have account for all projects
		int groupsCount = 0;
		int projectsCount = 0;
		for (List<Group> gs : scienceFairGroups) {
			for (Group g : gs) {
				groupsCount++;
				System.out.println(g);
				for (Project p : g.getProjects()) {
					projectsCount++;
				}
			}
		}

		System.out.println("\nTotal Groups: " + groupsCount);
		System.out.println("Total Projects: " + projectsCount);

		return scienceFairGroups;
	}

	public void printProjects() throws IOException {
		System.out.println("=============================================================================");
		System.out.println("PROJECTS");
		System.out.println("=============================================================================");

		List<Project> projects = FileUtil.readProjectsFile(PROJECTS_FILE);
		projects.stream().forEach(System.out::println);
		System.out.println("Total Projects: " + projects.size());
	}

	public void printJudges() throws IOException {
		System.out.println("\n=============================================================================");
		System.out.println("JUDGES");
		System.out.println("=============================================================================");

		List<Judge> judges = FileUtil.readJudgesFiles(JUDGES_FILE);
		judges.stream().forEach(System.out::println);
		System.out.println("Total Judges: " + judges.size());
	}

}
