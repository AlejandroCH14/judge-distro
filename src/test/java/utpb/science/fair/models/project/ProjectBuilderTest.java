package utpb.science.fair.models.project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import utpb.science.fair.models.Builder;
import utpb.science.fair.util.StringUtil;

public class ProjectBuilderTest {

	@Test
	public void testBuild() {
		List<String> tokens = Arrays.asList("401", "Chemistry");
		Builder<Project> projectBuilder = new ProjectBuilder(tokens);
		
		Project expected = new Project(401, "Chemistry");
		Project actual = projectBuilder.build();
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testBuildAll() throws IOException {
		List<String> file = Files.readAllLines(Paths.get("src/test/resources/projects.txt"));
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
