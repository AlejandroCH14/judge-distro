package utpb.science.fair.project;

import java.util.Comparator;

public class ProjectNumberComparator implements Comparator<Project> {

	@Override
	public int compare(Project o1, Project o2) {
		return o1.getNumber() - o2.getNumber();
	}

}
