package utpb.science.fair.models.category;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import utpb.science.fair.models.judge.Judge;
import utpb.science.fair.models.project.Project;

public class Category implements Comparable<Category> {

	public static final String[] CATEGORIES = { "Behavioral/Social Science", "Chemistry", "Earth/Space Science",
			"Environmental Science", "Life Science", "Mathematics/Physics" };

	public static final String BEHAVIORAL_SOCIAL_SCIENCE = "Behavioral/Social Science";

	public static final String CHEMISTRY = "Chemistry";

	public static final String EARTH_SPACE_SCIENCE = "Earth/Space Science";

	public static final String ENVIRONMENTAL_SCIENCE = "Environmental Science";

	public static final String LIFE_SCIENCE = "Life Science";

	public static final String MATHEMATICS_PHYSICS = "Mathematics/Physics";

	private final String _name;

	private List<Project> _projects = new LinkedList<>();

	private List<Judge> _judges = new LinkedList<>();

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

	public List<Project> getProjects() {
		return _projects;
	}

	public boolean addProject(Project project) {
		return _projects.add(Objects.requireNonNull(project));
	}

	public boolean removeProject(Project project) {
		return _projects.remove(Objects.requireNonNull(project));
	}

	public List<Judge> getJudges() {
		return _judges;
	}

	public void setJudges(List<Judge> judges) {
		_judges = judges;
	}

	public boolean addJudge(Judge judge) {
		return _judges.add(judge);
	}

	public boolean removeJudge(Judge judge) {
		return _judges.remove(judge);
	}

	@Override
	public int hashCode() {
		return Objects.hash(_judges, _name, _projects);
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
		return Objects.equals(_judges, other._judges) && Objects.equals(_name, other._name)
				&& Objects.equals(_projects, other._projects);
	}

	@Override
	public int compareTo(Category o) {
		return _name.compareToIgnoreCase(o.getName());
	}

	@Override
	public String toString() {
		return "Category [_name=" + _name + "]";
	}

	/**
	 * Sorts or prioritizes the Croup with the most Projects.
	 */
	public static final Comparator<Category> GREATEST_PROJECT_COUNT = new ProjectsCountComparator();

	private static class ProjectsCountComparator implements Comparator<Category> {

		@Override
		public int compare(Category o1, Category o2) {
			return o2.getProjects().size() - o1.getProjects().size();
		}

	}

}
