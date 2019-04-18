package utpb.science.fair;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeSet;

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

	private static PriorityQueue<Judge> _resources;

	private static PriorityQueue<Category> _tasks;

	public static void main(String[] args) throws IOException {

		App app = new App();

		List<Project> projects = FileUtil.readProjectsFile(PROJECTS_FILE);

		List<Judge> judges = FileUtil.readJudgesFiles(JUDGES_FILE);

		List<Category> categories = new CategoryProjectsListBuilder(projects).build();
		app.assignJudgesToCategories(categories, judges);

		// list of categories now has everything to distrubute judges
		
//		List<List<Group>> scienceFairGroups = app.createAllCategoryGroups(categories);
//
//
//		_resources = app.createResourcesQueue(judges);
//
//		_tasks = app.createTaskQueue(categories);
//
//		app.assignJudgesToGroups(scienceFairGroups, judges);

	}

	public void assignJudgesToGroups(List<List<Group>> scienceFair, List<Judge> judges) {
		// hp = highest priority
		Category hpCategory = _tasks.peek();
		Judge hpJudge = _resources.peek();
		
		var tree = new TreeSet<List<Group>>(scienceFair);

		for (List<Group> groups : scienceFair) {
			
		}
	}

	public PriorityQueue<Judge> createResourcesQueue(List<Judge> judges) {
		PriorityQueue<Judge> resourcesQ = new PriorityQueue<Judge>(Judge.LEAST_CATEGORY_COUNT);
		resourcesQ.addAll(judges);
		return resourcesQ;
	}

	public PriorityQueue<Category> createTaskQueue(List<Category> categories) {
		PriorityQueue<Category> taskQ = new PriorityQueue<Category>(Category.GREATEST_PROJECT_COUNT);
		taskQ.addAll(categories);
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

		return scienceFairGroups;
	}

}
