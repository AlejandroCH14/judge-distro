package utpb.science.fair.models.project;

import java.util.Comparator;
import java.util.Objects;

public class Project implements Comparable<Project> {

	private final int _number;
	private final String _category;

	public Project(int number, String category) {
		_number = number;
		_category = category;
	}

	public int getNumber() {
		return _number;
	}

	public String getCategoryName() {
		return _category;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_category, _number);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		return Objects.equals(_category, other._category) && _number == other._number;
	}

	@Override
	public String toString() {
		return "Project [number=" + _number + ", category=" + _category + "]";
	}

	@Override
	public int compareTo(Project o) {
		return _number - o.getNumber();
	}

	/**
	 * Sorts Project in ascending order by category name.
	 */
	public static final Comparator<Project> CATEGORY_NAME_ASCENDING = new CategoryName();

	private static class CategoryName implements Comparator<Project> {

		@Override
		public int compare(Project o1, Project o2) {
			return o1.getCategoryName().compareToIgnoreCase(o2.getCategoryName());
		}

	}

}
