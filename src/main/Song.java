package main;

import javafx.scene.image.Image;
import javafx.scene.media.Media;

public class Song {
	
	private Image coverImg;
	private String songTitle;
	private String songArtist;
	private Media music;
	
	public Song(String songTitle, String songArtist, String filename) {
		this.songTitle = songTitle;
		this.songArtist = songArtist;
		this.coverImg= new Image(getClass().getResource("/assets/cover/" + filename + ".jpg").toExternalForm());
		this.music = new Media(getClass().getResource("/assets/music/" + filename + ".mp3").toExternalForm());
	}
	

	public String getSongTitle() {
		return songTitle;
	}
	public void setSongTitle(String songTitle) {
		this.songTitle = songTitle;
	}
	public String getSongArtist() {
		return songArtist;
	}
	public void setSongArtist(String songArtist) {
		this.songArtist = songArtist;
	}
	public Image getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(Image coverImg) {
		this.coverImg = coverImg;
	}

	public Media getMusic() {
		return music;
	}
	public void setMusic(Media music) {
		this.music = music;
	}
	
	
	
}
