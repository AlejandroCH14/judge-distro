package utpb.science.fair.models;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

import utpb.science.fair.models.category.Category;
import utpb.science.fair.models.category.CategoryProjectsListBuilder;
import utpb.science.fair.models.group.Group;
import utpb.science.fair.models.group.GroupListBuilder;
import utpb.science.fair.models.judge.Judge;
import utpb.science.fair.models.project.Project;

public class JudgeDistributor {

	private final PriorityQueue<Judge> _resources;
	private final PriorityQueue<Category> _tasks;

	private final List<List<Group>> _scienceFairGroups;
	private final List<Category> _categories;
	private final List<Judge> _judges;
	private final List<Project> _projects;
	
	public JudgeDistributor(List<Judge> judges, List<Project> projects) {
		_judges = Objects.requireNonNull(judges);
		_projects = Objects.requireNonNull(projects);
		_categories = new CategoryProjectsListBuilder(projects).build();
		
		_resources = createResourcesQueue(judges);
		_tasks = createTaskQueue(_categories);
		
		assignJudgesToCategories(_categories, judges);
		_scienceFairGroups = createScienceFairGroups(_categories);
	}

	public void distribute() {
		final PriorityQueue<Judge> resources = _resources;
		final PriorityQueue<Category> tasks = _tasks;

		// (1) find category with highest priority -> category contains a list of Judges
		// & list of Groups
		while (!tasks.isEmpty() && !resources.isEmpty()) {
			Category hpCategory = tasks.peek();
			assignJudgesToGroup(hpCategory);
		}
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
		final PriorityQueue<Judge> resources = _resources;
		final PriorityQueue<Category> tasks = _tasks;

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

	public void assignJudgesToCategories(final List<Category> categories, final List<Judge> judges) {
		// sort we can use binary search
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

	private List<List<Group>> createScienceFairGroups(final List<Category> categories) {
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

	private PriorityQueue<Judge> createResourcesQueue(final List<Judge> judges) {
		PriorityQueue<Judge> q = new PriorityQueue<Judge>(Judge.SMALLEST_CATEGORY_COUNT);
		q.addAll(judges);
		return q;
	}

	public PriorityQueue<Category> createTaskQueue(final List<Category> categories) {
		PriorityQueue<Category> q = new PriorityQueue<Category>(Category.GREATEST_PROJECT_COUNT);
		q.addAll(categories);
		return q;
	}

	public List<List<Group>> getScienceFairGroups() {
		return _scienceFairGroups;
	}

	public List<Category> getCategories() {
		return _categories;
	}

	public List<Judge> getJudges() {
		return _judges;
	}
	
	public List<Project> getProjects() {
		return _projects;
	}

}
