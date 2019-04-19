package utpb.science.fair;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
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

	private static PriorityQueue<Judge> _resources;

	private static PriorityQueue<Category> _tasks;

	public static void main(String[] args) throws IOException {

		App app = new App();

		List<Project> projects = FileUtil.readProjectsFile(PROJECTS_FILE);

		List<Judge> judges = FileUtil.readJudgesFiles(JUDGES_FILE);

		List<Category> categories = new CategoryProjectsListBuilder(projects).build();
		// list of categories will have everything to distrubute judges
		app.assignJudgesToCategories(categories, judges);

		List<List<Group>> scienceFairGroups = app.createAllCategoryGroups(categories);

		_resources = app.createResourcesQueue(judges);
		_tasks = app.createTaskQueue(categories);

		app.assignJudgesToGroups(scienceFairGroups, categories, judges);

	}

	public void assignJudgesToGroups(List<List<Group>> scienceFair, List<Category> categories, List<Judge> judges) {
		// (1) find category with highest priority -> category contains a list of Judges & list of Groups
		while (!_tasks.isEmpty() && !_resources.isEmpty()) {
			Category hpCategory = _tasks.peek();
			assignJudgesToGroup(hpCategory);
		}
		
		for (var category : categories) {
			System.out.println(category.getGroups());
		}
		
		System.out.println("\n==============================================================================");
		System.out.println("leftover judges");
		System.out.println("==============================================================================");
		
		judges.stream()
			.filter(j -> j.getProjectCount() < 6)
			.forEach(System.out::println);
	}

	public void assignJudgesToGroup(Category category) {
		// (2) get all groups in that category -> put into priority queue where group
		// (3) find group with the smallest project count -> dequeue
		PriorityQueue<Group> groupsQ = new PriorityQueue<Group>(Group.LARGEST_PROJECT_COUNT);
		groupsQ.addAll(category.getGroups());

		// (4) find judge with the smallest category count -> dequeue
		PriorityQueue<Judge> judgesQ = new PriorityQueue<Judge>(Judge.SMALLEST_CATEGORY_COUNT);
		judgesQ.addAll(category.getJudges());

		Deque<Judge> queue = new ArrayDeque<>();
		List<Judge> groupJudges = null;
		Judge judge = null;
		int projectCount = 0, maxProjectsPerJudge = Judge.MAX_PROJECTS, maxJudgesPerGroup = Group.JUDGES_PER_GROUP;
		var resources = _resources;
		var tasks = _tasks;

		while (!judgesQ.isEmpty() && !groupsQ.isEmpty()) {
			judge = judgesQ.poll();

			if (!queue.isEmpty() && queue.peekFirst().getCategories().size() < judge.getCategories().size()) {
				judge = queue.poll();
			}

			for (Group group : groupsQ) {
				// do we even need to do work?
				groupJudges = group.getJudges();
				if (groupJudges.size() == maxJudgesPerGroup) {
					groupsQ.remove(group);
					continue;
				}

				// guess so ...
				projectCount = group.getProjects().size();

				// check if adding the judge to the current group will exceed the judge's limit
				if (judge.getProjectCount() + projectCount > maxProjectsPerJudge) {
					break;
				}

				// (5) assign judge to group and increment the judge's number of overseen
				// projects -> if (judge.projectCount >= 6) dequeue judge from available
				// resources
				if (groupJudges.contains(judge)) {
					judgesQ.remove(judge);
					queue.remove(judge);
					break;
				}
				group.addJudge(judge);
				judge.addToProjectCount(projectCount);
			}

			if (judge.getProjectCount() == maxProjectsPerJudge) {
				resources.remove(judge);
			} else if (judge.getProjectCount() < maxProjectsPerJudge && !groupJudges.contains(judge)) {
				queue.add(judge);
			}
		}

		tasks.remove(category);
	}

	public PriorityQueue<Judge> createResourcesQueue(List<Judge> judges) {
		PriorityQueue<Judge> resourcesQ = new PriorityQueue<Judge>(Judge.SMALLEST_CATEGORY_COUNT);
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
