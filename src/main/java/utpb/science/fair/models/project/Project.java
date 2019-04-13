package utpb.science.fair.models.project;

import java.util.Objects;

public class Project {

	private final int _number;
	private final String _category;

	public Project(int number, String category) {
		_number = number;
		_category = category;
	}

	public int getNumber() {
		return _number;
	}

	public String getCategory() {
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

}
