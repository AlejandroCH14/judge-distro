package utpb.science.fair.models.category;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import utpb.science.fair.models.project.Project;
import utpb.science.fair.models.project.ProjectCategoryNameComparator;
import utpb.science.fair.util.FileUtil;

@RunWith(Parameterized.class)
public class CategoryProjectsListBuilderTest {

	// if you require additional tests then inject the file name here
	@Parameters
	public static List<String> data() {
		return Arrays.asList(
				"src/test/resources/projects/one-category.txt",
				"src/test/resources/projects/two-categories.txt",
				"src/test/resources/projects/three-categories.txt",
				"src/test/resources/projects/four-categories.txt",
				"src/test/resources/projects/five-categories.txt",
				"src/test/resources/projects/projects.txt");
	}

	// JUnit will iterate over the provide "data" list and inject it here
	private String _fileName;

	public CategoryProjectsListBuilderTest(String fileName) {
		_fileName = fileName;
	}

	/**
	 * Tests the {@link utpb.science.fair.models.category.CategoryProjectsListBuilder}
	 * 
	 * @throws IOException
	 */
	@Test
	public void testBuild() throws IOException {
		List<Project> projects = FileUtil.readProjectsFile(_fileName);
		List<Category> categories = new CategoryProjectsListBuilder(projects).build();
		
		Set<Project> distinctProjects = new TreeSet<>(new ProjectCategoryNameComparator());
		distinctProjects.addAll(projects);
		
		List<String> expectedCategoryNames = new ArrayList<>(distinctProjects.size());
		
		for (Project project : distinctProjects) {
			expectedCategoryNames.add(project.getCategoryName());
		}
		
		int expectedTotalCount = projects.size();
		int actualTotalCount = 0;
		int index = 0;

		for (Category category : categories) {
			int i = index;
			
			Assert.assertTrue(
					categories.stream()
						.filter(c -> c.getName().contentEquals(expectedCategoryNames.get(i)))
						.findFirst()
						.isPresent()
			);
			
			actualTotalCount += category.getProjects().size();
			index++;
		}

		Assert.assertTrue(actualTotalCount == expectedTotalCount);
	}
	
}
