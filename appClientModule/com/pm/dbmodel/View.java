package com.pm.dbmodel;

public class View extends DBItem {
	private Artist artist;
	private Song song;
	private Album album;
	
	public View(Artist artist, Song song, Album album) {
		this.artist = artist;
		this.song = song;
		this.album = album;
	}
	
	public String toString() {
		return artist.getName() + "|" + album.getName() + "|" + song.getTrack() + "|" + song.getTitle();
	}

	@Override
	protected void sort(int sortOrder, StringBuilder sb) {
		// TODO Auto-generated method stub

	}

}
