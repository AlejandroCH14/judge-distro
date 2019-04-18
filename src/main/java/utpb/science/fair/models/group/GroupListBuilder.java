package utpb.science.fair.models.group;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import utpb.science.fair.models.Builder;
import utpb.science.fair.models.category.Category;
import utpb.science.fair.models.project.Project;

public class GroupListBuilder implements Builder<List<Group>> {

	private final Category _category;

	public GroupListBuilder(Category category) {
		_category = Objects.requireNonNull(category);
		Objects.requireNonNull(category.getProjects());
	}

	@Override
	public List<Group> build() {
		final List<Project> categoryProjects = _category.getProjects();
		final int projectsPerGroup = Group.MAX_PROJECTS_PER_GROUP;
		final int totalProjects = categoryProjects.size();

		// int n = a / b + ((a % b == 0) ? 0 : 1) => this is easier to read
		int totalGroups = totalProjects / projectsPerGroup;
		int total = totalGroups * projectsPerGroup;
		totalGroups += ((totalProjects % projectsPerGroup == 0) ? 0 : 1);
		int begin = 0, end = 0;
		int groupNumber = 1;

		final List<Group> groups = new ArrayList<>(totalGroups + 1);
		List<Project> groupProjects = null;

		if (totalProjects <= 6) {
			groupProjects = categoryProjects.subList(0, totalProjects);
			groups.add(new Group(groupNumber, _category.getName(), groupProjects));
			_category.setGroups(groups);
			return groups;
		}

		// get the first sublist, begin = 0
		end = projectsPerGroup;
		int i = 1;

		while (i < totalGroups) {
			groupProjects = categoryProjects.subList(begin, end);
			groups.add(new Group(groupNumber++, _category.getName(), groupProjects));
			begin += projectsPerGroup;
			end += projectsPerGroup;
			i++;
		}

		// get the last sublist
		if (totalProjects % projectsPerGroup != 0) {
			groupProjects = categoryProjects.subList(total, totalProjects);
			groups.add(new Group(groupNumber++, _category.getName(), groupProjects));
		}

		_category.setGroups(groups);

		return groups;
	}

}
