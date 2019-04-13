package utpb.science.fair.models.judge;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import utpb.science.fair.models.Builder;

public class JudgeBuilderTest {

	@Test
	public void testBuild() {
		String line = "Bill Jones:			Chemistry, Behavioral/Social Science, Earth/Space Science, Mathematics/Physics, Environmental Science";

		Builder<Judge> judgeBuilder = new JudgeBuilder(line);

		List<String> categories = Arrays.asList("Chemistry", "Behavioral/Social Science", "Earth/Space Science",
				"Mathematics/Physics", "Environmental Science");

		Judge expected = new Judge("Bill", "Jones", categories);
		Judge actual = judgeBuilder.build();

		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testBuildAll() throws IOException {
		List<String> file = Files.readAllLines(Paths.get("src/test/resources/judges.txt"));
		List<Judge> judges = new LinkedList<>();
		Judge judge = null;

		for (String line : file) {
			judge = new JudgeBuilder(line).build();
			judges.add(judge);
		}
		
		Assert.assertEquals(file.size(), judges.size());
	}

}
