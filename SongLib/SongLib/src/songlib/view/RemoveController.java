/* Names: Benjamin Lee and Christopher Shen
 * NetID: bjl170 and cs1448
*/

package songlib.view;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import songlib.model.Song;

public class RemoveController {
	
	@FXML Button removeSongButton;
	@FXML Button cancelRemoveButton;
	@FXML Text removeSongText;
	@FXML Text removeArtistText;
	@FXML Text removeAlbumText;
	@FXML Text removeYearText;
	
	
	public void setTextFields(String name, String artist, String album, int year) {
		removeSongText.setText(name);
		removeArtistText.setText(artist);
        
		
		if(!album.equals("")) {
			removeAlbumText.setText(album);
		}
        if(year > 0) {
        	removeYearText.setText(""+year);
        }
        
        
    }
	
	
	public void switchScene() {
		try {
	         FXMLLoader loader = new FXMLLoader(getClass().getResource(
	        		 "/songlib/view/songlib.fxml"));
	         AnchorPane root = (AnchorPane) loader.load();
	         
	         SongLibController songlibController = loader.getController();
	         songlibController.start(SongLibController.primaryStage);

	         //Scene newScene = new Scene(root);
	         SongLibController.primaryStage.getScene().setRoot(root);

	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	}
	@FXML
	private void cancelButtonClicked(ActionEvent event) throws IOException {
	
		switchScene();
  
	}
	public void removeSong(ActionEvent event) throws IOException{

			String songName = SongLibController.selection;
			String song = removeSongText.getText();
			String artist = removeArtistText.getText();
			String album = removeAlbumText.getText();
			int year;
			if(removeYearText.getText().equals("N/A") || removeYearText.getText().equals("")) {
				year = 0;
			}
			else {
				year = Integer.parseInt(removeYearText.getText());
			}
			
			List<String> fileContent = new ArrayList<>(Files.readAllLines(Paths.get("resources/SongLibData.txt"), StandardCharsets.UTF_8));;
			for (int i = 0; i < fileContent.size(); i++) {
				
			    if (fileContent.get(i).substring(0, Math.min(fileContent.get(i).length(),songName.length())).equals(songName)) {
			        fileContent.set(i, "");
			        break;
			        
			    }
			}

			Files.write(Paths.get("resources/SongLibData.txt"), fileContent, StandardCharsets.UTF_8);
			
			SongLibController.songMap.remove(songName);
			
			SongLibController.selection = song + " | " + artist;
			
			switchScene();
		}

}
