package utpb.science.fair.models.group;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import utpb.science.fair.models.category.Category;
import utpb.science.fair.models.category.CategoryProjectsListBuilder;
import utpb.science.fair.models.project.Project;
import utpb.science.fair.util.FileUtil;

@RunWith(Parameterized.class)
public class GroupListBuilderTest {
	
	@Parameters
	public static List<String> data() {
		return Arrays.asList(
				"src/test/resources/projects/given-projects.txt",
				"src/test/resources/projects/one-category.txt",
				"src/test/resources/projects/two-categories.txt",
				"src/test/resources/projects/three-categories.txt",
				"src/test/resources/projects/four-categories.txt",
				"src/test/resources/projects/five-categories.txt",
				"src/test/resources/projects/projects.txt");
	}
	
	private String _fileName;
	
	public GroupListBuilderTest(String fileName) {
		_fileName = fileName;
	}

	@Test
	public void testBuild() throws IOException {
		List<Project> projects = FileUtil.readProjectsFile(_fileName);

		List<Category> categories = new CategoryProjectsListBuilder(projects).build();

		List<List<Group>> scienceFairGroups = new LinkedList<>();

		List<Group> groups = new LinkedList<>();

		for (Category category : categories) {
			groups = new GroupListBuilder(category).build();
			scienceFairGroups.add(groups);
		}

		final int expectedProjectCount = projects.size();
		int actualProjectCount = 0;
		for (List<Group> gs : scienceFairGroups) {
			for (Group g : gs) {
				for (@SuppressWarnings("unused") Project p : g.getProjects()) {
					actualProjectCount++;
				}
			}
		}
		
		Assert.assertEquals(expectedProjectCount, actualProjectCount);
	}

}
