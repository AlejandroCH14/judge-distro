package utpb.science.fair.models.project;

import java.util.List;

import utpb.science.fair.models.Builder;

/**
 * Creates an instance of type {@link Project} from a non-null and non-empty
 * {@link List} of {@link String} tokens.<br>
 * <br>
 * This builder make 2 key assumptions:
 * <ol>
 * <li>the first token can be converted to a non-negative integer with
 * {@link Integer#parseInt(String)} without throwing an
 * {@link NumberFormatException}</li>
 * <li>the remaining tokens can be used to contruct the category name of the
 * project</li>
 * </ol>
 *
 */
public class ProjectBuilder implements Builder<Project> {

	private final List<String> _tokens;

	/**
	 * The list of tokens in which an instance of a Project will be constructed.
	 * 
	 * @param tokens must be non-null and not empty.
	 */
	public ProjectBuilder(List<String> tokens) {
		if (tokens != null && tokens.isEmpty()) {
			throw new IllegalArgumentException("the list of tokens can not be empty");
		}
		if (tokens.size() == 1) {
			throw new IllegalArgumentException(
					"the must be at least 2 tokens in the list to create an instance of a Project");
		}
		_tokens = tokens;
	}

	@Override
	public Project build() {
		final int number = Integer.parseInt(_tokens.get(0));

		if (_tokens.size() == 2) {
			return new Project(number, _tokens.get(1));
		}

		String category = String.join(" ", _tokens.subList(1, _tokens.size()));

		return new Project(number, category);
	}

}
