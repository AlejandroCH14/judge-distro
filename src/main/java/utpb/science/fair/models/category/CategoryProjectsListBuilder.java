package utpb.science.fair.models.category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import utpb.science.fair.IntDeque;
import utpb.science.fair.models.Builder;
import utpb.science.fair.models.project.Project;

/**
 * Builds a List of Category objects where each Category object has a List of
 * Projects associated with said Category.
 * 
 */
public class CategoryProjectsListBuilder implements Builder<List<Category>> {

	/**
	 * A sorted array of all available Categories.
	 */
	private final String[] _categories = Category.CATEGORIES;
	private final List<Project> _projects;

	/**
	 * The List of Projects from which to build the List of Category objects. The
	 * given List of Projects is immediately sorted in ascending order by category to
	 * facilitate the build process.
	 * 
	 * @param projects the List of Projects (non-null)
	 */
	public CategoryProjectsListBuilder(List<Project> projects) {
		_projects = Objects.requireNonNull(projects);
		Collections.sort(_projects, Project.CATEGORY_NAME_ASCENDING);
	}

	/**
	 * Returns the first index of the Project object in the List that matches the
	 * given Predicate. If the Project does not exist in the List then
	 * <code>-1</code> is returned.
	 * 
	 * @param begin     the beginning index to start searching from in the List
	 *                  (inclusive)
	 * @param end       the ending index in the List (exclusive)
	 * @param projects  the List of Projects to search in.
	 * @param predicate the predicate
	 * @return
	 */
	private int indexOf(int begin, int end, List<Project> projects, Predicate<Project> predicate) {
		final int size = projects.size();

		if (begin < 0) {
			throw new IllegalArgumentException();
		}

		if (end > size) {
			throw new IllegalArgumentException();
		}

		if (begin > end) {
			throw new IllegalArgumentException();
		}

		Project project = null;

		for (int i = begin; i < size; i++) {
			project = projects.get(i);

			if (predicate.test(project)) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Creates an array of integers where each value in the array is the first index
	 * of the Category in the List of Projects that passed in via the contructor.
	 * Each index in the create integer array corresponds with the Categories array.
	 * If a Category is without a Project then a value of <code>-1</code> is
	 * assigned as the value.
	 * 
	 * @return an integer array.
	 */
	private int[] getStartingIndicies() {
		final int totalProjects = _projects.size();
		final int totalCategories = _categories.length;
		final int[] indices = new int[totalCategories];

		// find the first occurrence of each category in the sorted projects list
		indices[0] = indexOf(0, totalProjects, _projects, p -> p.getCategoryName().contentEquals(_categories[0]));

		int offset = 0;

		for (int i = 1; i < indices.length; i++) {
			final int index = i;
			offset = indices[i - 1];

			// to prevent an IllegalArgumentException in indexOf()
			if (offset == -1) {
				offset = 0;
			}

			// returns -1 if there are no projects in the specified category
			indices[i] = indexOf(offset, totalProjects, _projects,
					p -> p.getCategoryName().contentEquals(_categories[index]));
		}

		return indices;
	}

	/**
	 * Builds the List of Category objects based on the given List of Projects.
	 */
	@Override
	public List<Category> build() {
		final List<Category> categories = new ArrayList<>(_categories.length);
		final int[] arr = getStartingIndicies();
		final IntDeque indexDeque = new IntDeque(arr.length);
		final IntDeque nameDeque = new IntDeque(arr.length);
		int begin = 0, end = 0, value = 0;

		// get the starting indices from the projects list and indices from arr
		for (int i = 0; i < arr.length; i++) {
			value = arr[i];
			if (value != -1) {
				indexDeque.enqueue(value);
				nameDeque.enqueue(i);
			}
		}

		String categoryName = null;
		List<Project> categoryProjects = null;

		while (!indexDeque.isEmpty() && !nameDeque.isEmpty()) {
			categoryName = _categories[nameDeque.dequeue()];
			begin = indexDeque.dequeue();

			// check before we peek ... to avoid throwing NullPtrException
			end = indexDeque.isEmpty() ? _projects.size() : indexDeque.peekFirst();

			categoryProjects = _projects.subList(begin, end);
			categories.add(new Category(categoryName, categoryProjects));
		}

		return categories;
	}

}
