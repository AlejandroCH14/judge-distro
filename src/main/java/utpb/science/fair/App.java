package utpb.science.fair;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application {

	private Stage _primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));

		AnchorPane root = loader.load();
		
		MainController mainController = loader.getController();
		
		mainController.setApp(this);

		primaryStage.setTitle("Judge Distro");

		primaryStage.setScene(new Scene(root, 650, 450));

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public Stage getPrimaryStage() {
		return _primaryStage;
	}
}
