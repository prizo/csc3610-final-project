package helperclasses;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SceneSwitcher {
	
	private Stage stage;
	private Parent root;
	
	public void switchScene(Button btn, String document) {
		
		// Get reference to the button's stage
		stage = (Stage) btn.getScene().getWindow();
		
		// Load up other FXML document
		try {
			root = FXMLLoader.load(getClass().getResource(document));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Create a new scene with root and set the stage
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}

}
