package utpb.science.fair.models;

import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

import utpb.science.fair.models.category.Category;
import utpb.science.fair.models.group.Group;
import utpb.science.fair.models.judge.Judge;

public class JudgeDistributor {

	private final PriorityQueue<Judge> _resources;
	private final PriorityQueue<Category> _tasks;

	private final List<List<Group>> _scienceFairGroups;
	private final List<Category> _categories;
	private final List<Judge> _judges;

	public JudgeDistributor(List<List<Group>> scienceFairGroups, List<Category> categories, List<Judge> judges) {
		_scienceFairGroups = Objects.requireNonNull(scienceFairGroups);
		_categories = Objects.requireNonNull(categories);
		_judges = Objects.requireNonNull(judges);
		_resources = createResourcesQueue(judges);
		_tasks = createTaskQueue(categories);
	}

	private void distribute() {

	}

	private PriorityQueue<Judge> createResourcesQueue(List<Judge> judges) {
		PriorityQueue<Judge> q = new PriorityQueue<Judge>(Judge.SMALLEST_CATEGORY_COUNT);
		q.addAll(judges);
		return q;
	}

	public PriorityQueue<Category> createTaskQueue(List<Category> categories) {
		PriorityQueue<Category> q = new PriorityQueue<Category>(Category.GREATEST_PROJECT_COUNT);
		q.addAll(categories);
		return q;
	}

}
