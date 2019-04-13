package utpb.science.fair.models.project;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import utpb.science.fair.models.Builder;

public class ProjectBuilderTest {

	@Test
	public void testBuild() {
		List<String> tokens = Arrays.asList("401", "Chemistry");
		Builder<Project> projectBuilder = new ProjectBuilder(tokens);
		
		Project expected = new Project(401, "Chemistry");
		Project actual = projectBuilder.build();
		
		Assert.assertEquals(expected, actual);
	}

}
