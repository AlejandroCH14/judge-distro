package utpb.science.fair.models;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import utpb.science.fair.models.project.Project;

public class Category {

	public static final String BEHAVIORAL_SOCIAL_SCIENCE = "Behavioral/Social Science";

	public static final String CHEMISTRY = "Chemistry";

	public static final String EARTH_SPACE_SCIENCE = "Earth/Space Science";

	public static final String ENVIRONMENTAL_SCIENCE = "Environmental Science";

	public static final String LIFE_SCIENCE = "Life Science";

	public static final String MATHEMATICS_PHYSICS = "Mathematics/Physics";

	private final String _name;

	private List<Project> _projects = new LinkedList<>();

	public Category(String name) {
		_name = Objects.requireNonNull(name);
	}

	public Category(String name, List<Project> projects) {
		_name = Objects.requireNonNull(name);
		_projects = Objects.requireNonNull(projects);
	}

	public String getName() {
		return _name;
	}

	public boolean addProject(Project project) {
		return _projects.add(Objects.requireNonNull(project));
	}

	public boolean removeProject(Project project) {
		return _projects.remove(Objects.requireNonNull(project));
	}

	@Override
	public int hashCode() {
		return Objects.hash(_name, _projects);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		return Objects.equals(_name, other._name) && Objects.equals(_projects, other._projects);
	}

}
