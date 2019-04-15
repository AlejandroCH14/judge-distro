package utpb.science.fair.models.project;

import java.util.Comparator;

public class ProjectCategoryNameComparator implements Comparator<Project> {

	@Override
	public int compare(Project o1, Project o2) {
		return o1.getCategoryName().compareToIgnoreCase(o2.getCategoryName());
	}

}
