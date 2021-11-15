/* Names: Benjamin Lee and Christopher Shen
 * NetID: bjl170 and cs1448
*/

package songlib.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import songlib.model.Song;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



public class SongLibController {


	//main fxml scene 
	@FXML AnchorPane songlibPane;
	@FXML ListView<String> listView;
	@FXML Button detailsButton;
	@FXML Button addButton;
	@FXML Button editButton;
	@FXML Button deleteButton;
	@FXML Text songText;
	@FXML Text artistText;
	@FXML Text albumText;
	@FXML Text yearText;
	
	static ObservableList<String> obsList;
	static Stage primaryStage;
	static Map<String,Song> songMap = new TreeMap<String,Song>(String.CASE_INSENSITIVE_ORDER);
	private ArrayList<String> displayNames = new ArrayList<String>();
	
	static String selection = "";
	static int selectionNumber = 0;
	
	
	public void start(Stage primaryStage)  throws IOException {
		
		this.primaryStage = primaryStage;
		
		String text = new String(Files.readAllBytes(Paths.get("resources/SongLibData.txt")));
		
		text = text.trim();
		
		

		try {
			File file = new File("resources/SongLibData.txt");
			FileWriter fileWriter = new FileWriter(file);
			
			fileWriter.write(text);

			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		populateList();	
		
		listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        
		        String name = SongLibController.songMap.get(newValue).getName();
		        String artist = SongLibController.songMap.get(newValue).getArtist();
		        String album = SongLibController.songMap.get(newValue).getAlbum();
		        int year = SongLibController.songMap.get(newValue).getYear();
		        songText.setText(name);
		        artistText.setText(artist);
		        
		        if(album.equals("")){
		        	albumText.setText("N/A");
		        }
		        else {
		        	albumText.setText(album);
		        }
		        
		        if(year > 0) {
		        	yearText.setText(""+year);
		        }
		        else {
		        	yearText.setText("N/A");
		        }
		        
				SongLibController.selection = name + " | " + artist;
				SongLibController.selectionNumber = obsList.indexOf(selection);
		    }
		});
		select();
		
		
	}	
	
	public void populateList()  throws IOException {
		// create BufferedReader and read data from csv
		
		
		String row = "";
		String[] data;
		BufferedReader csvReader = new BufferedReader(new FileReader("resources/SongLibData.txt"));
		while ((row = csvReader.readLine()) != null) {
			
			if(row.equals("")) {
				continue;
			}
		    data = row.split(" \\| ");
		    Song song;
		    if(data.length == 2){
		    	song = new Song(data[0], data[1],"",0);
		    }
		    else{
		    	song = new Song(data[0], data[1], data[2], Integer.parseInt(data[3]));
		   
		    }
		    
		    
		    SongLibController.songMap.put(song.getName() + " | " + song.getArtist(),song);
		    
		    // do something with the data
		}
		csvReader.close();
		//}
		for(String s : SongLibController.songMap.keySet()){
			displayNames.add(s);
		}
		
		Collections.sort(displayNames, String.CASE_INSENSITIVE_ORDER);
	
	
	
	obsList = FXCollections.observableArrayList(displayNames);
	listView.setItems(obsList);
	}
	
	public void select() {
		
		
		if (!listView.getItems().isEmpty()) {
				
				if(selection.equals("")) {
					SongLibController.selectionNumber = 0;
					SongLibController.selection = "";
					listView.getSelectionModel().select(0);
				}
				else if(SongLibController.songMap.containsKey(SongLibController.selection)) { // song exists 
					
					SongLibController.selectionNumber = obsList.indexOf(selection);
					listView.getSelectionModel().select(SongLibController.selectionNumber);
				}
				else {// song doesn't exist
				
				
					while(SongLibController.selectionNumber >= songMap.size()) {
						SongLibController.selectionNumber--;
						
					}
					listView.getSelectionModel().select(SongLibController.selectionNumber);
				}
			    
		}
	}


	@FXML
	private void addButtonClicked(ActionEvent event) throws IOException {
		
		Parent pane = FXMLLoader.load(getClass().getResource("/songlib/view/add.fxml"));
		
		

	    primaryStage.getScene().setRoot(pane);

	}
	
	@FXML
	private void editButtonClicked(ActionEvent event) throws IOException {
	    
	    if (listView.getItems().isEmpty()) {
			
			showItem("Edit Song Error", "Cannot Edit: ", "Song List is Empty");
			return;
			
		}
		
	    Stage stage=(Stage) ((Button)(event.getSource())).getScene().getWindow();
	    
	    FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/songlib/view/edit.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		EditController editController = loader.getController();
		String songName = SongLibController.selection;
		Song currentSong = SongLibController.songMap.get(songName);
		editController.setTextFields(currentSong.getName(),currentSong.getArtist(),currentSong.getAlbum(),currentSong.getYear());
		
		
		Scene scene = new Scene(root);

	    stage.setScene(scene);
	    
		
	}
	
	@FXML
	private void removeButtonClicked(ActionEvent event) throws IOException {
	    
	        
	    if (listView.getItems().isEmpty()) {
	    	showItem("Remove Song Error", "Cannot Remove:", "Song List is Empty");
			return;
			
		}
	    
	    Stage stage=(Stage) ((Button)(event.getSource())).getScene().getWindow();
	    
	    FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/songlib/view/remove.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		RemoveController removeController = loader.getController();
		String songName = SongLibController.selection;
		Song currentSong = SongLibController.songMap.get(songName);
		removeController.setTextFields(currentSong.getName(),currentSong.getArtist(),currentSong.getAlbum(),currentSong.getYear());
		
		
		Scene scene = new Scene(root);

	    stage.setScene(scene);
	}
	

	public static void showItem(String errorTitle, String headerText, String contentText){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(primaryStage);
		alert.setTitle(errorTitle);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
		
	}
	
	public static boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
		}
	
}	
