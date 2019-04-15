package utpb.science.fair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import utpb.science.fair.models.judge.Judge;
import utpb.science.fair.models.project.Project;
import utpb.science.fair.util.FileUtil;

public class App {

	public static final String PROJECTS_FILE = "src/test/resources/projects/projects.txt";

	public static final String JUDGES_FILE = "src/test/resources/judges.txt";

	public static void main(String[] args) throws IOException, URISyntaxException {
		
		System.out.println("=============================================================================");
		System.out.println("PROJECTS");
		System.out.println("=============================================================================");
		
		List<Project> projects = FileUtil.readProjectsFile(PROJECTS_FILE);
		projects.stream().forEach(System.out::println);
		System.out.println("Total Projects: " + projects.size());

		System.out.println("\n=============================================================================");
		System.out.println("JUDGES");
		System.out.println("=============================================================================");

		List<Judge> judges = FileUtil.readJudgesFiles(JUDGES_FILE);
		judges.stream().forEach(System.out::println);
		System.out.println("Total Judges: "+ judges.size());

	}

}
