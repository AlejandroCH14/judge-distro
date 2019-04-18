package utpb.science.fair.models.judge;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Judge {

	/**
	 * A list of categories the each judge is willing to judge.
	 */
	private final List<String> _categories;
	private final String _firstName;
	private final String _lastName;
	/**
	 * The number of projects this judge will oversee.
	 */
	private int _projectCount;

	public Judge(String firstName, String lastName, List<String> categories) {
		_firstName = firstName;
		_lastName = lastName;
		_categories = Objects.requireNonNull(categories);
	}

	/**
	 * Returns the number of projects that this judge is currently judging.
	 * 
	 * @return the number of projects that this judge is currently judging.
	 */
	public int getProjectCount() {
		return _projectCount;
	}

	public void setProjectCount(int newCount) {
		_projectCount = newCount;
	}

	public void incrementProjectCount() {
		_projectCount++;
	}

	public void decrementProjectCount() {
		_projectCount--;
	}

	public List<String> getCategories() {
		return _categories;
	}

	public String getFullName() {
		return _firstName + " " + _lastName;
	}

	public String getFirstName() {
		return _firstName;
	}

	public String getLastName() {
		return _lastName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_categories, _firstName, _lastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Judge other = (Judge) obj;
		return Objects.equals(_categories, other._categories) && Objects.equals(_firstName, other._firstName)
				&& Objects.equals(_lastName, other._lastName);
	}

	@Override
	public String toString() {
		return String.format("Judge[firstName=%s, lastName=%s, categories=[%s]]", _firstName, _lastName,
				String.join(",", _categories));
	}

	/**
	 * Sorts or prioritizes the Judge with the least amount of Categories that the
	 * Judge can oversee.
	 */
	public static final Comparator<Judge> LEAST_CATEGORY_COUNT = new CategoryCountComparator();

	private static class CategoryCountComparator implements Comparator<Judge> {

		@Override
		public int compare(Judge o1, Judge o2) {
			return o1.getCategories().size() - o2.getCategories().size();
		}

	}

}
