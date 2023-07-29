package main;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	
	int viewWidth = 311;
	
	BorderPane root = new BorderPane();
	
	VBox botPart = new VBox();
	
	Label songTitle = new Label("Title");
	Label songArtist = new Label("Artist");
	VBox titleAndArtist = new VBox();
	
	Slider songProg = new Slider(0, 1, 0);
	Label time_elapsed = new Label("-:--");
	Label time_left = new Label("-:--");
	HBox progBar = new HBox();
	
	Button prevSong = new Button("Prev");
	Button nextSong = new Button("Next");
	Button playpause = new Button("Play/Pause");
	HBox btnBar = new HBox();
	
	ImageView coverImg = new ImageView();
	Playlist songPlaylist;
	
	MediaPlayer musicPlayer;
	
	public Main(){
		
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
//		Media video = new Media(getClass().getResource("/videos/video.mp4").toExternalForm());
//		MediaPlayer vidPlayer = new MediaPlayer(video);
//		MediaView vidViewer = new MediaView(vidPlayer);
		
		songProg.setMinWidth(viewWidth - 60);
		songTitle.setFont(Font.font("", FontWeight.BOLD, 24));
		songArtist.setFont(Font.font("", FontWeight.MEDIUM, 13));
		progBar.getChildren().addAll(time_elapsed, songProg, time_left);
		progBar.setAlignment(Pos.CENTER);
		progBar.setSpacing(8);
		
		btnBar.getChildren().addAll(prevSong, playpause, nextSong);
		btnBar.setAlignment(Pos.CENTER);
		btnBar.setSpacing(20);
		
		titleAndArtist.getChildren().addAll(songTitle, songArtist);
		botPart.getChildren().addAll(titleAndArtist, progBar, btnBar);
		botPart.setMaxWidth(viewWidth);
		botPart.setSpacing(15);
		
//		root.setTop(vidViewer);
		root.setCenter(coverImg);
		root.setBottom(botPart);
		BorderPane.setAlignment(botPart, Pos.CENTER);
		BorderPane.setMargin(botPart, new Insets(0, 0, 35, 0));
		
		// Song and Logic
		Song song1 = new Song("Deep Indigo", "Yorushika", "deep_indigo");
		Song song2 = new Song("Inside You", "Milet", "inside_you");
		Song song3 = new Song("Spring Thief", "Yorushika", "spring_thief");
		
		songPlaylist = new Playlist(song1, song2, song3);
		refreshPlayer();
		
		playpause.setOnAction(e -> {
			if(musicPlayer.getStatus() == MediaPlayer.Status.PLAYING)
				musicPlayer.pause();
			else
				musicPlayer.play();
		});
		
		prevSong.setOnAction(e -> {
			musicPlayer.pause();
			songPlaylist.prevSong();
			refreshPlayer();
		});
		
		nextSong.setOnAction(e -> {
			musicPlayer.pause();
			songPlaylist.nextSong();
			refreshPlayer();
		});
		
		Scene scene = new Scene(root, 390, 650);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Music Player");
		primaryStage.show();
	}
	
	boolean isUpdate = false;
	
	public void refreshPlayer() {
		musicPlayer = new MediaPlayer(songPlaylist.getCurrentSong().getMusic());
		songTitle.setText(songPlaylist.getCurrentSong().getSongTitle());
		songArtist.setText(songPlaylist.getCurrentSong().getSongArtist());
		coverImg.setImage(songPlaylist.getCurrentSong().getCoverImg());
		coverImg.setFitWidth(viewWidth);
		coverImg.setPreserveRatio(true);
		
		musicPlayer.currentTimeProperty().addListener((obs, oldVal, newVal) -> {
			int currDuration = (int) obs.getValue().toSeconds();
			int currMins = currDuration / 60;
			int currSec =  currDuration % 60; 
			time_elapsed.setText(String.format("%d:%02d", currMins, currSec));
			
			int fullDuration = (int) musicPlayer.getMedia().getDuration().toSeconds();
			int minsLeft = (fullDuration - currDuration) / 60;
			int secsLeft = (fullDuration - currDuration) % 60;
			time_left.setText(String.format("%d:%02d", minsLeft, secsLeft));
			
			isUpdate = true;
			songProg.setValue((double)currDuration/fullDuration);
			isUpdate = false;
		});
		
		songProg.valueProperty().addListener((obs, oldVal, newVal) -> {
			if(!isUpdate)
				musicPlayer.seek(Duration.seconds((double)obs.getValue() * musicPlayer.getMedia().getDuration().toSeconds()));
		});
		
		musicPlayer.setOnEndOfMedia(() -> {
			songPlaylist.nextSong();
			refreshPlayer();
		});
		musicPlayer.play();
	}
}
