package utpb.science.fair;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import utpb.science.fair.models.JudgeDistributor;
import utpb.science.fair.models.group.Group;
import utpb.science.fair.models.judge.Judge;
import utpb.science.fair.models.project.Project;
import utpb.science.fair.util.FileUtil;

public class App {

	public static final String PROJECTS_FILE = "src/test/resources/projects/Alejandro'sTest(P).txt";

	public static final String JUDGES_FILE = "src/test/resources/projects/Alejandro'sTest(J).txt";

	public static void main(String[] args) throws IOException {

		List<Project> projects = FileUtil.readProjectsFile(PROJECTS_FILE);

		List<Judge> judges = FileUtil.readJudgesFiles(JUDGES_FILE);

		JudgeDistributor judgeDistributor = new JudgeDistributor(judges, projects);
		judgeDistributor.distribute();

		var scienceFair = judgeDistributor.getScienceFairGroups();

		var incompleteGroups = new LinkedList<Group>();

		for (List<Group> groups : scienceFair) {
			for (Group group : groups) {
				System.out.println(group);
				if (group.getJudges().size() != 3) {
					incompleteGroups.add(group);
				}
			}
		}

		System.out.println("====================================================================================");
		System.out.println("Available Judges");
		System.out.println("====================================================================================");
		var availableJudges = judges.stream()
				.filter(j -> j.getProjectCount() < 6)
				.collect(Collectors.toList());
		
		availableJudges.stream().forEach(System.out::println);

		System.out.println("====================================================================================");
		System.out.println("Incomplete Groups");
		System.out.println("====================================================================================");

		incompleteGroups.stream().forEach(System.out::println);
	}

}
