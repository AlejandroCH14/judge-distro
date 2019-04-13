package utpb.science.fair.models.judge;

import java.util.Arrays;
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

}
