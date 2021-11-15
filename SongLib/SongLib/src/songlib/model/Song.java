/* Names: Benjamin Lee and Christopher Shen
 * NetID: bjl170 and cs1448
*/

package songlib.model;

public class Song {
	
	private String name;
	private String artist;
	private String album = "";
	private int year = 0;
	
	public Song(String name, String artist) {
		this.name = name;
		this.setArtist(artist);
	}
	
	public Song(String name, String artist, String album, int year){
		this(name, artist);
		this.setAlbum(album);
		this.setYear(year);
	}
	public Song(String name, String artist, String album){
		this(name, artist);
		this.setAlbum(album);
	}
	public Song(String name, String artist, int year){
		this(name, artist);
		this.setYear(year);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	
}
