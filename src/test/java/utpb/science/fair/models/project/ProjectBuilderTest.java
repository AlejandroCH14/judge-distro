package utpb.science.fair.models.project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import utpb.science.fair.models.Builder;
import utpb.science.fair.util.StringUtil;

@RunWith(Parameterized.class)
public class ProjectBuilderTest {
	
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
	
	public ProjectBuilderTest(String fileName) {
		_fileName = fileName;
	}

	@Test
	public void testBuild() {
		List<String> tokens = Arrays.asList("401", "Environmental Science");
		Builder<Project> projectBuilder = new ProjectBuilder(tokens);
		
		Project expected = new Project(401, "Environmental Science");
		Project actual = projectBuilder.build();
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testBuildAll() throws IOException {
		List<String> file = Files.readAllLines(Paths.get(_fileName));
		List<Project> projects = new LinkedList<>();
		List<String> tokens = null;
		Project project = null;

		for (String line : file) {
			tokens = StringUtil.tokenize(line);
			project = new ProjectBuilder(tokens).build();
			projects.add(project);
		}

		Assert.assertEquals(file.size(), projects.size());
	}

}
