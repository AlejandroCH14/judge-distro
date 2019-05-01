package utpb.science.fair;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import utpb.science.fair.models.JudgeDistributor;
import utpb.science.fair.models.group.Group;
import utpb.science.fair.models.judge.Judge;
import utpb.science.fair.models.project.Project;
import utpb.science.fair.util.FileUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {

    @FXML
    private TextField projectsTextField;
    @FXML
    private TextField judgesTextField;
    @FXML
    private TextField outputFileTextField;
    @FXML
    private Button scheduleBtn;

    private String PROJECTS_FILE = "";

    private String JUDGES_FILE = "";


    public void schedule() throws IOException {

        PROJECTS_FILE = projectsTextField.getText();
        JUDGES_FILE = judgesTextField.getText();
        PrintWriter pw= new PrintWriter(new FileOutputStream(outputFileTextField.getText()));

        // FileWriter writer = new FileWriter(outputFileTextField.getText());

        List<Project> projects = FileUtil.readProjectsFile(PROJECTS_FILE);
        List<Judge> judges = FileUtil.readJudgesFiles(JUDGES_FILE);

        JudgeDistributor judgeDistributor = new JudgeDistributor(judges, projects);
        judgeDistributor.distribute();

        var scienceFair = judgeDistributor.getScienceFairGroups();
        var incompleteGroups = new LinkedList<Group>();

        pw.println();
        pw.write("===========================================");
        pw.write("Science Fair Schedule");
        pw.write("=============================================");
        pw.println();

        for (List<Group> groups : scienceFair) {
            for (Group group : groups) {
                pw.write(group.toString());
                if (group.getJudges().size() != 3) {
                    incompleteGroups.add(group);
                }
            }
        }

        pw.println();
        pw.write("===========================================");
        pw.write("Available Judges");
        pw.write("=============================================");
        pw.println();

        var availableJudges = judges.stream()
                .filter(j -> j.getProjectCount() < 6)
                .collect(Collectors.toList());
        for(Judge judge : availableJudges)
            pw.write( judge.toString());

        pw.println();
        pw.write("===========================================");
        pw.write("Incomplete Groups");
        pw.write("=============================================");
        pw.println();
        for(Group group : incompleteGroups)
            pw.write( group.toString());

        pw.close();

    }
}
