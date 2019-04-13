package utpb.science.fair.util;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilTest {

	@Test
	public void testTokenizeStringChar() {
		final String line = "\t\t\n\r\n			Chemistry, Behavioral/Social Science, Earth/Space Science, Mathematics/Physics, Environmental Science";

		final List<String> expected = Arrays.asList("Chemistry", "Behavioral/Social Science", "Earth/Space Science",
				"Mathematics/Physics", "Environmental Science");

		final List<String> actual = StringUtil.tokenize(line, ',');

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testTokenizeString() {
		final String line = "\t\t\n\r\n John                  Doe   ";

		final List<String> expected = Arrays.asList("John", "Doe");

		final List<String> actual = StringUtil.tokenize(line);

		Assert.assertEquals(expected, actual);
	}

}
