package utpb.science.fair.models.project;

import java.util.List;
import java.util.Objects;

import utpb.science.fair.models.Builder;

public class ProjectBuilder implements Builder<Project> {

	private final List<String> _tokens;

	public ProjectBuilder(List<String> tokens) {
		_tokens = Objects.requireNonNull(tokens);
	}

	@Override
	public Project build() {
		final int number = Integer.parseInt(_tokens.get(0));

		if (_tokens.size() == 2) {
			return new Project(number, _tokens.get(1));
		}

		String category = String.join(" ", _tokens);

		return new Project(number, category);
	}

}
