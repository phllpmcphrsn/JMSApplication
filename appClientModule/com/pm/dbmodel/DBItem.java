package com.pm.dbmodel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public abstract class DBItem {

	static Connection conn;
	protected int id;
	public static final String DB_NAME = "music.db";
	public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\phllp\\sqlite3\\" + DB_NAME;
	
	public static final int ORDER_BY_NONE = 1;
	public static final int ORDER_BY_ASC = 2;
	public static final int ORDER_BY_DESC = 3;
	
	public static final String SELECT_ALL = "SELECT * FROM ";
	
	public static final String TABLE_ALBUMS = "albums";
	public static final String COLUMN_ALBUM_ID = "_id";
	public static final String COLUMN_ALBUM_NAME = "name";
	public static final String COLUMN_ALBUM_ARTIST = "artist";
	public static final int INDEX_ALBUM_ID = 1;
	public static final int INDEX_ALBUM_NAME = 2;
	public static final int INDEX_ALBUM_ARTIST = 3;
	
	public static final String TABLE_ARTISTS = "artists";
	public static final String COLUMN_ARTIST_ID = "_id";
	public static final String COLUMN_ARTIST_NAME = "name";
	public static final int INDEX_ARTIST_ID = 1;
	public static final int INDEX_ARTIST_NAME = 2;
	
	public static final String TABLE_SONGS = "songs";
	public static final String COLUMN_SONG_ID = "_id";
	public static final String COLUMN_SONG_TITLE = "title";
	public static final String COLUMN_SONG_ALBUM = "album";
	public static final String COLUMN_SONG_TRACK = "track";
	public static final int INDEX_SONG_ID = 1;
	public static final int INDEX_SONG_TRACK = 2;
	public static final int INDEX_SONG_TITLE = 3;
	public static final int INDEX_SONG_ALBUM = 4;
	
	//May need to make a view object with view_creation() and view_query()
	public static final String MUSIC_VIEW = "music_view";
	public static final String CREATE_MUSIC_VIEW = "CREATE VIEW IF NOT EXISTS " +
            MUSIC_VIEW + " AS SELECT " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + ", " +
            TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + " AS " + COLUMN_SONG_ALBUM + ", " +
            TABLE_SONGS + "." + COLUMN_SONG_TRACK + ", " + TABLE_SONGS + "." + COLUMN_SONG_TITLE +
            " FROM " + TABLE_SONGS +
            " INNER JOIN " + TABLE_ALBUMS + " ON " + TABLE_SONGS +
            "." + COLUMN_SONG_ALBUM + " = " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ID +
            " INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTIST +
            " = " + TABLE_ARTISTS + "." + COLUMN_ARTIST_ID +
            " ORDER BY " +
            TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + ", " +
            TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + ", " +
            TABLE_SONGS + "." + COLUMN_SONG_TRACK;
	
	public static final String COLUMN_MUSIC_VIEW_ARTIST = "name";   
	public static final String COLUMN_MUSIC_VIEW_ALBUM = "album";   
	public static final String COLUMN_MUSIC_VIEW_TRACK = "track";	//song's track number
	public static final String COLUMN_MUSIC_VIEW_TITLE = "title";   //song title
	public static final int INDEX_MUSIC_VIEW_ARTIST = 1;   
	public static final int INDEX_MUSIC_VIEW_ALBUM = 2;   
	public static final int INDEX_MUSIC_VIEW_TRACK = 3;	
	public static final int INDEX_MUSIC_VIEW_TITLE = 4;   
	
	public boolean open() {
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING);
			return true;
		} catch(SQLException e) {
			System.out.println("Couldn't connect to database: " + e.getMessage());
			return false;
		}		
	}
	
	public void close() {
		try {
			if(conn != null)
				conn.close();
		} catch(SQLException e) {
			System.out.println("Couldn't close connection. " + e.getMessage());
		}
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	protected boolean createView() {
		try(Statement statement = conn.createStatement()) {
			statement.execute(CREATE_MUSIC_VIEW);
			return true;

		} catch(SQLException e) {
			System.out.println("Create View failed: " + e.getMessage());
			return false;
		}
	}
	
	protected abstract void sort(int sortOrder, StringBuilder sb);
	
	
}
