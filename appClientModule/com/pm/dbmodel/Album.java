package com.pm.dbmodel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Album extends DBItem{
	
	private String name;
	public static final String TABLE_ALBUMS = "albums";
	public static final String COLUMN_ALBUM_ID = "_id";
	public static final String COLUMN_ALBUM_NAME = "name";
	public static final String COLUMN_ALBUM_ARTIST = "artist";
	public static final int INDEX_ALBUM_ID = 1;
	public static final int INDEX_ALBUM_NAME = 2;
	public static final int INDEX_ALBUM_ARTIST = 3;
	
    public static final String QUERY_ALBUMS_BY_ARTIST_START = 
            "SELECT " + TABLE_ALBUMS + '.' + COLUMN_ALBUM_NAME + " FROM " + TABLE_ALBUMS +
                    " INNER JOIN " +TABLE_ARTISTS + " ON " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTIST +
                    " = " + TABLE_ARTISTS + "." + COLUMN_ARTIST_ID +
                    " WHERE " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + " = \"";
    public static final String QUERY_ALBUMS_BY_NAME_START = 
            "SELECT " + COLUMN_ALBUM_NAME + " FROM " + TABLE_ALBUMS +
                    " WHERE " + COLUMN_ALBUM_NAME + " = \"";
    public static final String QUERY_ALBUMS_BY_AND_SORT =
    		" ORDER BY " + TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + " COLLATE NOCASE ";
    
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Album> queryAlbums(int sortOrder){
		
		StringBuilder sb = new StringBuilder(SELECT_ALL);
		sb.append(TABLE_ALBUMS);
		
		sort(sortOrder, sb);
		
		try(Statement stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery(SELECT_ALL + TABLE_ALBUMS);){
			
			List<Album> albums = new ArrayList<>();
			while(results.next()) {
				Album album = new Album();
				album.setId(results.getInt(INDEX_ALBUM_ID));
				album.setName(results.getString(INDEX_ALBUM_NAME));
				albums.add(album);
			}
			
			return albums;
			
		}catch(SQLException e) {
			System.out.println("Query failed: " + e.getMessage());
			return null;
		}
	}
	
	/****************************************************************************************
	 * Get all of the albums by an artist. Joins the album and artist tables.
	 * @param artistName
	 * @param sortOrder
	 * @return
	 ****************************************************************************************/
    public List<Album> queryAlbumsByArtist(String artistName, int sortOrder) {

        StringBuilder sb = new StringBuilder(QUERY_ALBUMS_BY_ARTIST_START);
        sb.append(artistName);
        sb.append("\"");

        sort(sortOrder, sb);

        System.out.println("SQL statement = " + sb.toString());

        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sb.toString())) {

            List<Album> albums = new ArrayList<>();
            while(results.next()) {
            	Album album = new Album();
				album.setName(results.getString(1));
				albums.add(album);
            }

            return albums;

        } catch(SQLException e) {
            System.out.println("Query failed: "+ e.getMessage());
            return null;
        }
    }
    
    /***********************************************************************************
     * Get albums by the name of the album.
     * @param albumName
     * @param sortOrder
     * @return
     ***********************************************************************************/
    public List<Album> queryAlbumsByName(String albumName, int sortOrder) {

        StringBuilder sb = new StringBuilder(QUERY_ALBUMS_BY_NAME_START);
        sb.append(albumName);
        sb.append("\"");

        sort(sortOrder, sb);

        System.out.println("SQL statement = " + sb.toString());

        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sb.toString())) {

            List<Album> albums = new ArrayList<>();
            while(results.next()) {
            	Album album = new Album();
				album.setId(results.getInt(INDEX_ALBUM_ID));
				album.setName(results.getString(INDEX_ALBUM_NAME));
				albums.add(album);
            }

            return albums;

        } catch(SQLException e) {
            System.out.println("Query failed: "+ e.getMessage());
            return null;
        }
    }
    
	public int queryNumberOfAlbums(String artistName) {
		int num = 0;
		
		StringBuilder sb = new StringBuilder("SELECT count(" + TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + ") FROM ");
		sb.append(TABLE_ALBUMS);
		sb.append(" INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTIST + "=" + TABLE_ARTISTS + "." + COLUMN_ARTIST_ID);
		sb.append(" WHERE " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + " = '" + artistName + "'");
		
		System.out.println("Here's your query: " + sb.toString());
		
		try (Statement stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery(sb.toString())){;

			if(results.next())
				num = results.getInt(1);
			return num;
		}catch (SQLException e) {
			System.out.println("Something went wrong: " + e.getMessage());
			e.printStackTrace();
			return num;
		}
	}
	
	@Override
	protected void sort(int sortOrder, StringBuilder sb) {
		if(sortOrder != ORDER_BY_NONE) {
            sb.append(QUERY_ALBUMS_BY_AND_SORT);
            if(sortOrder == ORDER_BY_DESC) {
                sb.append("DESC");
            } else {
                sb.append("ASC");
            }
        }
	}
}
