package com.pm.dbmodel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Artist extends DBItem{
	
	private String name;
    private static final String QUERY_ARTISTS_BY_AND_SORT =
    		" ORDER BY " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + " COLLATE NOCASE ";

    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Artist> queryArtists(int sortOrder){		
		StringBuilder sb = new StringBuilder(SELECT_ALL);
		sb.append(TABLE_ARTISTS);
		
		sort(sortOrder, sb);
		
		try(Statement stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery(SELECT_ALL + TABLE_ARTISTS);){
			
			List<Artist> artists = new ArrayList<>();
			while(results.next()) {
				Artist artist = new Artist();
				artist.setId(results.getInt(INDEX_ARTIST_ID));
				artist.setName(results.getString(INDEX_ARTIST_NAME));
				artists.add(artist);
			}
			
			return artists;
			
		}catch(SQLException e) {
			System.out.println("Query failed: " + e.getMessage());
			return null;
		}
	}
	
	@Override
	protected void sort(int sortOrder, StringBuilder sb) {
		if(sortOrder != ORDER_BY_NONE) {
            sb.append(QUERY_ARTISTS_BY_AND_SORT);
            if(sortOrder == ORDER_BY_DESC) {
                sb.append("DESC");
            } else {
                sb.append("ASC");
            }
        }		
	}
}
