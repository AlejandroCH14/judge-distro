package utpb.science.fair.models.judge;

import java.util.ArrayList;
import java.util.List;

import utpb.science.fair.models.Builder;
import utpb.science.fair.util.StringUtil;

public class JudgeBuilder implements Builder<Judge> {

	private final String _line;

	public JudgeBuilder(String line) {
		_line = line;
	}

	private List<String> getNameTokens() {
		final int index = _line.indexOf(":");
		final String str = _line.substring(0, index);
		return StringUtil.tokenize(str);
	}

	private List<String> getCategoriesTokens() {
		final int index = _line.indexOf(":");
		final String str = _line.substring(index + 1, _line.length());
		return StringUtil.tokenize(str, ',');
	}

	@Override
	public Judge build() {
		final List<String> nameTokens = getNameTokens();
		final List<String> categoriesTokens = getCategoriesTokens();

		final String firstName = nameTokens.get(0);
		final String lastName = nameTokens.get(1);
		final List<String> categories = new ArrayList<>(categoriesTokens);

		return new Judge(firstName, lastName, categories);
	}

}
