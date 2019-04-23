package utpb.science.fair.models.judge;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Judge {

	/**
	 * The maximum amount of projects that a single judge can oversee.
	 */
	public static final int MAX_PROJECTS = 6;

	/**
	 * A list of categories the each judge is willing to judge.
	 */
	private List<String> _categories;
	private String _firstName;
	private String _lastName;
	/**
	 * The number of projects this judge will oversee.
	 */
	private int _projectCount = 0;

	public Judge() {
	}

	public Judge(String firstName, String lastName, List<String> categories) {
		_firstName = Objects.requireNonNull(firstName);
		_lastName = Objects.requireNonNull(lastName);
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

	/**
	 * Will add the given value to the current project count.
	 * 
	 * @param value positive or negative
	 */
	public void addToProjectCount(int value) {
		_projectCount += value;
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
		return Objects.hash(_categories, _firstName, _lastName, _projectCount);
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
		return _projectCount == other._projectCount && Objects.equals(_categories, other._categories)
				&& Objects.equals(_firstName, other._firstName) && Objects.equals(_lastName, other._lastName);
	}

	@Override
	public String toString() {
		return String.format("Judge[firstName=%s, lastName=%s, projectCount=%d, categoryCount=%d]%n", _firstName,
				_lastName, _projectCount, _categories.size());
	}

	/**
	 * Sorts or prioritizes the Judge with the least amount of Categories that the
	 * Judge can oversee.
	 */
	public static final Comparator<Judge> SMALLEST_CATEGORY_COUNT = new CategoryCountComparator();

	private static class CategoryCountComparator implements Comparator<Judge> {

		@Override
		public int compare(Judge o1, Judge o2) {
			return o1.getCategories().size() - o2.getCategories().size();
		}

	}

}
