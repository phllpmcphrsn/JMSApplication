package com.pm.dbmodel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Song extends DBItem{
	
	private String title;
	private int track;
	private String album;
	
//	public static final String QUERY_SONGS_BY_ARTIST_START = 
//          "SELECT " + TABLE_SONGS + '.' + COLUMN_SONG_TITLE + " FROM " + TABLE_SONGS +
//          		" INNER JOIN " +TABLE_ALBUMS + " ON " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ID +
//                  " = " + TABLE_SONGS + "." + COLUMN_SONG_ALBUM +
//          		" INNER JOIN " +TABLE_ARTISTS + " ON " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTIST +
//                  " = " + TABLE_ARTISTS + "." + COLUMN_ARTIST_ID +
//                  " WHERE " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + " = \"";

	public static final String QUERY_SONGS_BY_ARTIST_START = 
          "SELECT * FROM " + MUSIC_VIEW +
                  " WHERE " + COLUMN_MUSIC_VIEW_ARTIST + " = \"";
    public static final String QUERY_SONGS_BY_TITLE_START = 
            "SELECT * FROM " + MUSIC_VIEW +
                    " WHERE " + COLUMN_SONG_TITLE + " = \"";
    public static final String QUERY_SONGS_BY_AND_SORT = 
    		" ORDER BY " + TABLE_SONGS + "." + COLUMN_SONG_ALBUM + " COLLATE NOCASE ";

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getTrack() {
		return track;
	}
	public void setTrack(int track) {
		this.track = track;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	
	public List<Song> querySongs(int sortOrder){
		
		StringBuilder sb = new StringBuilder(SELECT_ALL);
		sb.append(TABLE_SONGS);
		
		sort(sortOrder, sb);
		
		try(Statement stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery(SELECT_ALL + TABLE_SONGS);){
			
			List<Song> songs = new ArrayList<>();
			while(results.next()) {
				Song song = new Song();
				song.setId(results.getInt(INDEX_SONG_ID));
				song.setTitle(results.getString(INDEX_SONG_TITLE));
				songs.add(song);
			}
			
			return songs;
			
		}catch(SQLException e) {
			System.out.println("Query failed: " + e.getMessage());
			return null;
		}
	}
	
	public List<View> querySongsByTitle(String title, int sortOrder){
		
        StringBuilder sb = new StringBuilder(QUERY_SONGS_BY_TITLE_START);
        sb.append(title);
        sb.append("\"");

        sort(sortOrder, sb);

        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sb.toString())) {

            List<View> songs = new ArrayList<>();
            while(results.next()) {
            	Artist artist = new Artist();
            	artist.setName(results.getString(INDEX_MUSIC_VIEW_ARTIST));
            	Album album = new Album();
            	album.setName(results.getString(INDEX_MUSIC_VIEW_ALBUM));
            	Song song = new Song();
            	song.setTrack(results.getInt(INDEX_MUSIC_VIEW_TRACK));
            	song.setTitle(results.getString(INDEX_MUSIC_VIEW_TITLE));
            	            	
            	View view = new View(artist, song, album);
            	songs.add(view);
            }

            return songs;

        } catch(SQLException e) {
            System.out.println("Query failed: "+ e.getMessage());
            return null;
        }
	}
	
	public List<Song> querySongsByArtist (String artistName, int sortOrder){
		
		StringBuilder sb = new StringBuilder(QUERY_SONGS_BY_ARTIST_START);
		sb.append(artistName);
		sb.append("\"");
		
		sort(sortOrder, sb);
		
		try(Statement stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery(sb.toString());){
			
			List<Song> songs = new ArrayList<>();
			while(results.next()) {
				Song song = new Song();
				song.setId(results.getInt(INDEX_SONG_ID));
				song.setTitle(results.getString(INDEX_SONG_TITLE));
				songs.add(song);
			}
			
			return songs;
			
		}catch(SQLException e) {
			System.out.println("Query failed: " + e.getMessage());
			return null;
		}
	}
	
//	public List<Song> querySongsByAlbum (String albumName, int sortOrder){
		
//	}
	
	//TODO adjust for view queries since songs.album is not valid for the created view
	@Override
	protected void sort(int sortOrder, StringBuilder sb) {
		if(sortOrder != ORDER_BY_NONE) {
            sb.append(QUERY_SONGS_BY_AND_SORT);
            if(sortOrder == ORDER_BY_DESC) {
                sb.append("DESC");
            } else {
                sb.append("ASC");
            }
        }
		
	}
}
