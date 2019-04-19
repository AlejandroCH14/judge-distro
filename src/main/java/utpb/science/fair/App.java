package utpb.science.fair;

import java.io.IOException;
import java.util.List;

import utpb.science.fair.models.JudgeDistributor;
import utpb.science.fair.models.group.Group;
import utpb.science.fair.models.judge.Judge;
import utpb.science.fair.models.project.Project;
import utpb.science.fair.util.FileUtil;

public class App {

	public static final String PROJECTS_FILE = "src/test/resources/projects/given-projects.txt";

	public static final String JUDGES_FILE = "src/test/resources/given-judges.txt";

	public static void main(String[] args) throws IOException {

		List<Project> projects = FileUtil.readProjectsFile(PROJECTS_FILE);

		List<Judge> judges = FileUtil.readJudgesFiles(JUDGES_FILE);

		JudgeDistributor judgeDistributor = new JudgeDistributor(judges, projects);
		judgeDistributor.distribute();

		var scienceFair = judgeDistributor.getScienceFairGroups();
		
		for (List<Group> groups : scienceFair) {
			for (Group group : groups) {
				System.out.println(group);
			}
		}
	}

}
