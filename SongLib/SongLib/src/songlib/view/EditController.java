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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class EditController {

	//fxml for edit
	@FXML TextField editSongTextField;
	@FXML TextField editArtistTextField;
	@FXML TextField editAlbumTextField;
	@FXML TextField editYearTextField;
	@FXML Button editSongButton;
	@FXML Button cancelEditButton;
	
	public void setTextFields(String name, String artist, String album, int year) {
        editSongTextField.setText(name);
        editArtistTextField.setText(artist);
        
        editAlbumTextField.setText(album);
        if(year > 0) {
        	editYearTextField.setText(""+year);
        }
        
        
    }
	
	
	
	
	
	public void editSong(ActionEvent event) throws IOException{
		String songName = editSongTextField.getText().trim();
		String artistName = editArtistTextField.getText().trim();
		String albumName = editAlbumTextField.getText().trim();
		String yearNum = editYearTextField.getText().trim();
		
		System.out.println("Edit button clicked");
		if (songName.isEmpty() || artistName.isEmpty()){
			SongLibController.showItem("Edit Song Failed", "Edit Error", "Song Name and Artist Name Are Required Fields");
		}
		else if (SongLibController.songMap.containsKey(songName + " | " + artistName) 
				&& !SongLibController.selection.equals(songName + " | " + artistName)){
			
			
			SongLibController.showItem("Edit Song Failed", "Edit Error", "Identical Song in Library");
			
		}
		else if(!yearNum.equals("") && (!SongLibController.isNumeric(editYearTextField.getText()) || Integer.parseInt(editYearTextField.getText()) <= 0)) {
			SongLibController.showItem("Edit Song Failed", "Edit Error", "Year is Invalid");
		}
		
		else {
			System.out.println("Editing");
			String song = SongLibController.selection;
			
			int year;
			if(yearNum.equals("")) {
				year = 0;
			}
			else {
				year = Integer.parseInt(yearNum);
			}

			List<String> fileContent = new ArrayList<>(Files.readAllLines(Paths.get("resources/SongLibData.txt"), StandardCharsets.UTF_8));;
			for (int i = 0; i < fileContent.size(); i++) {
				
				System.out.println("i: " + i + " fileContent.get(i): " + fileContent.get(i));
			    if (fileContent.get(i).substring(0, Math.min(fileContent.get(i).length(),song.length())).equals(song)) {
			        fileContent.set(i, songName + " | " + artistName + " | " + albumName + " | " + year);
			        break;
			        
			    }
			}
			System.out.println("SongName: " + songName);
			
			
			Files.write(Paths.get("resources/SongLibData.txt"), fileContent, StandardCharsets.UTF_8);
			
	
			SongLibController.songMap.remove(song);
			
			SongLibController.selection = songName + " | " + artistName;
			
			switchScene();
		}
		

	}
	
	public void switchScene() {
		try {
	         FXMLLoader loader = new FXMLLoader(getClass().getResource(
	        		 "/songlib/view/songlib.fxml"));
	         AnchorPane root = (AnchorPane) loader.load();
	         
	         SongLibController songlibController = loader.getController();
	         songlibController.start(SongLibController.primaryStage);

	         SongLibController.primaryStage.getScene().setRoot(root);

	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	}
	@FXML
	private void cancelButtonClicked(ActionEvent event) throws IOException {
	
		switchScene();
  
	}
	
	
	
}
