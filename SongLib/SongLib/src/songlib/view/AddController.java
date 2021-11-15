/* Names: Benjamin Lee and Christopher Shen
 * NetID: bjl170 and cs1448
*/

package songlib.view;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import songlib.model.Song;


public class AddController {



	//fxml for add
	@FXML TextField addSongTextField;
	@FXML TextField addArtistTextField;
	@FXML TextField addAlbumTextField;
	@FXML TextField addYearTextField;
	@FXML Button addSongButton;
	@FXML Button cancelAddButton;
	
	public void setEditSongTextField(String s) {
        addSongTextField.setText(s);
        
    }
	
	
	
	public void addSong(ActionEvent event)throws IOException{

		
		int year = 0;

		String songName = addSongTextField.getText().trim();
		String artistName = addArtistTextField.getText().trim();
		String albumName = addAlbumTextField.getText().trim();
		String yearNum = addYearTextField.getText().trim();

		
		
		
		
		if(songName.isEmpty() || artistName.isEmpty()){
			
			
			SongLibController.showItem("Add Song Failed", "Add Error", "Song Name and Artist Name Are Required Fields");
				
		}
			
		else if (SongLibController.songMap.containsKey(songName + " | " + artistName)){
			SongLibController.showItem("Add Song Failed", "Add Error", "Identical Song in Library");
			
		}
		
		else if(!yearNum.equals("") && (!SongLibController.isNumeric(yearNum) || Integer.parseInt(yearNum) <= 0)) {
			SongLibController.showItem("Add Song Failed", "Add Error", "Year is Invalid");
		}
		
		else {
			if (!albumName.isEmpty()){
				albumName = addAlbumTextField.getText();
			}
			
			if (!yearNum.isEmpty()){
				year = Integer.parseInt(yearNum);
			}
				
			
			Song addSong = new Song(songName, artistName, albumName, year);
			
			
			
			
			FileWriter fw = new FileWriter("resources/SongLibData.txt", true);
			File file = new File("resources/SongLibData.txt");
		    BufferedWriter bw = new BufferedWriter(fw);
		    
		    if(!(file.length() == 0)){	
		    	bw.newLine();
			}
		    bw.write(addSong.getName() + " | " + addSong.getArtist()  + " | " +  addSong.getAlbum()  + " | " +  addSong.getYear());
		    bw.close();
		   
		   	SongLibController.selection = addSong.getName() + " | " + addSong.getArtist();
		    
		   
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
