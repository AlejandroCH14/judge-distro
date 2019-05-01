package utpb.science.fair;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import utpb.science.fair.models.JudgeDistributor;
import utpb.science.fair.models.group.Group;
import utpb.science.fair.models.judge.Judge;
import utpb.science.fair.models.project.Project;
import utpb.science.fair.util.FileUtil;

public class MainController {
	
	private Desktop desktop = Desktop.getDesktop();

	private App _app;

	private File _projectsFile;

	private File _judgesFile;

	private File _outFile;
	
	@FXML
	private TextField projectsTextField;

	@FXML
	private TextField judgesTextField;

	@FXML
	private TextField outFileTextField;
	
	@FXML
    private ProgressBar progressBar;

	@FXML
	void chooseJudgesFile(ActionEvent event) {
		final FileChooser fileChooser = new FileChooser();
		
		fileChooser.setTitle("Choose Judges file");

		_judgesFile = fileChooser.showOpenDialog(_app.getPrimaryStage());

		if (_judgesFile != null) {
			judgesTextField.setText(_judgesFile.getName());
		}
	}

	@FXML
	void chooseOutFile(ActionEvent event) {
		final FileChooser fileChooser = new FileChooser();

		_outFile = fileChooser.showSaveDialog(_app.getPrimaryStage());

		if (_outFile != null) {
			outFileTextField.setText(_outFile.getName());
		}
	}

	@FXML
	void chooseProjectsFile(ActionEvent event) {
		final FileChooser fileChooser = new FileChooser();
		
		fileChooser.setTitle("Choose Projects File");

		_projectsFile = fileChooser.showOpenDialog(_app.getPrimaryStage());

		if (_projectsFile != null) {
			projectsTextField.setText(_projectsFile.getName());
		}
	}

	@FXML
	void schedule(ActionEvent event) {
		if (isValid()) {
			Task<Void> task = new Task<>() {
			    @Override public Void call() {
			    	distrubute();
			        return null;
			    }
			};
			
			task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			    @Override
			    public void handle(WorkerStateEvent event) {
			    	task.getValue();
			    	openFile(_outFile);
			    }
			});
			
			progressBar.progressProperty().bind(task.progressProperty());
			
			progressBar.setVisible(true);
			
			new Thread(task).start();
			
			while(task.isRunning()) {
				
			}
			
			progressBar.setVisible(false);			
		}
	}

	public void setApp(App app) {
		_app = Objects.requireNonNull(app);
	}

	private boolean isValid() {
		return _projectsFile != null && _judgesFile != null && _outFile != null;
	}
	
	private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
                MainController.class.getName()).log(
                    Level.SEVERE, null, ex
                );
        }
    }

	private void distrubute() {
		List<Project> projects = null;
		List<Judge> judges = null;

		try {
			projects = FileUtil.readProjectsFile(_projectsFile.getAbsolutePath());
			
			judges = FileUtil.readJudgesFiles(_judgesFile.getAbsolutePath());
		} catch (IOException e) {
			System.out.println(e);
			return;
		}

		JudgeDistributor judgeDistributor = new JudgeDistributor(judges, projects);
		judgeDistributor.distribute();

		List<List<Group>> scienceFair = judgeDistributor.getScienceFairGroups();
		List<Group> incompleteGroups = new LinkedList<>();
		
		String newLine = System.getProperty("line.separator");

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(_outFile.getAbsolutePath()))) {
			writer.write(newLine);
			writer.write("=============================================");
			writer.write("Science Fair Schedule");
			writer.write("=============================================");
			writer.write(newLine);

			for (List<Group> groups : scienceFair) {
				for (Group group : groups) {
					writer.write(group.toString());
					if (group.getJudges().size() != 3) {
						incompleteGroups.add(group);
					}
				}
			}

			writer.write(newLine);
			writer.write("=============================================");
			writer.write("Available Judges");
			writer.write("=============================================");
			writer.write(newLine);

			List<Judge> availableJudges = judges.stream()
					.filter(j -> j.getProjectCount() < 6)
					.collect(Collectors.toList());
			
			if (availableJudges.isEmpty()) {
				writer.write("All judges have been exhausted");
			} else {
				for (Judge judge : availableJudges) {
					writer.write(String.format("%-40s Project Count = %d%n", judge.getFullName(), judge.getProjectCount()));
				}
			}

			writer.write(newLine);
			writer.write("=============================================");
			writer.write("Incomplete Groups");
			writer.write("=============================================");
			writer.write(newLine);

			if (incompleteGroups.isEmpty()) {
				writer.write("nothing to see here ... carry on human");
			} else {
				for (Group group : incompleteGroups) {
					writer.write(group.toString());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
