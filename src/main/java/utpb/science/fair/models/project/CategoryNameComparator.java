package utpb.science.fair.models.project;

import java.util.Comparator;

public class CategoryNameComparator implements Comparator<Project> {

	@Override
	public int compare(Project o1, Project o2) {
		return o1.getCategory().compareToIgnoreCase(o2.getCategory());
	}

}
