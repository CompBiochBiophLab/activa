package com.o2hlink.activa.news;

import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.o2hlink.activa.Activa;

public class Feed {
	
	public int id;
	
	public boolean initiated;
	
	public String url;

	public String title;
	
	public String link;
	
	public String description;
	
	public String author;
	
	public com.o2hlink.activ8.client.entity.Feed feedForServices;
	
	public Hashtable<Integer, New> newslist;

	public Feed() {
		newslist = new Hashtable<Integer, New>();
		this.initiated = false;
	}

	public Feed(String url, int id) {
		newslist = new Hashtable<Integer, New>();
		this.initiated = false;
		this.id = id;
		this.url = url;
	}
	
	public boolean getFeedFromRSS() {
		String json = Activa.myProtocolManager.getNewsFromFeed(this.url);
		if (json == null) return false;
		try {
			JSONObject jsonObject = new JSONObject(json);
			int code = jsonObject.getInt("responseStatus");
			if (code != 200) return false;
			JSONObject jsonData = jsonObject.getJSONObject("responseData");
			JSONObject feedJson = jsonData.getJSONObject("feed");
			this.title = feedJson.getString("title");
			this.link = feedJson.getString("link");
			this.author = feedJson.getString("author");
			this.description = feedJson.getString("description");
			JSONArray entries = feedJson.getJSONArray("entries");
			for (int i = 0; i < entries.length(); i++) {
				New entry = new New(i);
				JSONObject entryJson = entries.getJSONObject(i);
				entry.title = entryJson.getString("title");
				entry.author = entryJson.getString("author");
				entry.date = entryJson.getString("publishedDate");
				entry.content = entryJson.getString("content");
				entry.link = entryJson.getString("link");
				entry.snippet = entryJson.getString("contentSnippet");
				try {
					JSONArray mediagroups = entryJson.getJSONArray("mediaGroups");
					JSONObject photoJson = mediagroups.getJSONObject(0).getJSONArray("contents").getJSONObject(0);
					if (photoJson != null) {
						entry.photoURL = photoJson.getString("url");
						entry.photoHeight = photoJson.getInt("height");
						entry.photoHeight = photoJson.getInt("width");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				this.newslist.put(i, entry);
			}
			this.initiated = true;
			return true;
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
