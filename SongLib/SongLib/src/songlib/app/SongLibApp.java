/* Names: Benjamin Lee and Christopher Shen
 * NetID: bjl170 and cs1448
*/

package songlib.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import songlib.view.SongLibController;


public class SongLibApp extends Application {

	//public static Stage stage;
	
	public void start(Stage primaryStage) 
		throws Exception {
			// TODO Auto-generated method stub
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/songlib/view/songlib.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			SongLibController songlibController = loader.getController();
			songlibController.start(primaryStage);
			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.setTitle("SongLib");
			primaryStage.setResizable(false);
			primaryStage.show();
		}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
