package main;

import java.util.ArrayList;

public class Playlist {
	
	ArrayList<Song> songPlaylist;
	Song currentSong;
	
	public Playlist(Song...songs) {
		this.songPlaylist = new ArrayList<Song>();
		
		for (Song song : songs) {
			songPlaylist.add(song);
		}
		
		if(this.songPlaylist.size() > 0)
			this.currentSong = songPlaylist.get(0);
		else
			this.currentSong = null;
	}

	public ArrayList<Song> getSongPlaylist() {
		return songPlaylist;
	}

	public void setSongPlaylist(ArrayList<Song> songPlaylist) {
		this.songPlaylist = songPlaylist;
	}

	public Song getCurrentSong() {
		return currentSong;
	}

	public void setCurrentSong(Song currentSong) {
		this.currentSong = currentSong;
	}
	
	public void nextSong() {
		if(this.currentSong.equals(this.songPlaylist.get(this.songPlaylist.size()-1)))
			this.currentSong = this.songPlaylist.get(0);
		else
			this.currentSong = this.songPlaylist.get(this.songPlaylist.indexOf(currentSong)+1);	
	}
	
	public void prevSong() {
		if(this.currentSong.equals(this.songPlaylist.get(0)))
			this.currentSong = this.songPlaylist.get(this.songPlaylist.size()-1);
		else
			this.currentSong = this.songPlaylist.get(this.songPlaylist.indexOf(currentSong)-1);	
	}
}
