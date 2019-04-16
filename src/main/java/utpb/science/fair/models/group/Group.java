package utpb.science.fair.models.group;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import utpb.science.fair.models.judge.Judge;
import utpb.science.fair.models.project.Project;

public class Group {

	public static final int MAX_PROJECTS_PER_GROUP = 6;
	public static final int MIN_PROJECTS_PER_GROUP = 2;

	private String _categoryName;

	private List<Project> _projects;

	private List<Judge> _judges;

	private int _groupNumber;

	public Group(int number, String name, List<Project> projects) {
		_groupNumber = number;
		_categoryName = Objects.requireNonNull(name);
		_projects = Objects.requireNonNull(projects);
	}

	public String get_categoryName() {
		return _categoryName;
	}

	public void setCategoryName(String categoryName) {
		_categoryName = categoryName;
	}

	public List<Project> getProjects() {
		return _projects;
	}

	public void setProjects(List<Project> projects) {
		_projects = Objects.requireNonNull(projects);
	}

	public List<Judge> getJudges() {
		return _judges;
	}

	public void setJudges(List<Judge> judges) {
		_judges = Objects.requireNonNull(judges);
	}

	public int getGroupNumber() {
		return _groupNumber;
	}

	public void setGroupNumber(int groupNumber) {
		_groupNumber = groupNumber;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_categoryName, _groupNumber, _judges, _projects);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		return Objects.equals(_categoryName, other._categoryName) && _groupNumber == other._groupNumber
				&& Objects.equals(_judges, other._judges) && Objects.equals(_projects, other._projects);
	}

	@Override
	public String toString() {
		// TODO: uncomment once judges have been assigned.
//		List<String> judgesNames = new ArrayList<>(_judges.size());
		List<String> projectNumbers = new ArrayList<>(_projects.size());

//		for (Judge judge : _judges) {
//			judgesNames.add(judge.getFullName());
//		}

		for (Project project : _projects) {
			projectNumbers.add(String.valueOf(project.getNumber()));
		}

//		String judges = String.join(",", judgesNames);
		String projects = String.join(",", projectNumbers);

		// ready to print out to file
		// category name, group number, judges, project numbers
//		return String.format("Group: %s_%d%nJudges: %s%nProjects: %s%n%n", _categoryName, _groupNumber, judges,
//				projects);

		return String.format("%s_%d%nProjects: %s%n%n", _categoryName, _groupNumber, projects);
	}

}
