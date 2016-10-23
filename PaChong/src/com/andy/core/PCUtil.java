package com.andy.core;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;

public class PCUtil {
	
	public static Document getDoc(Connection conn){
		Document doc = null;
		int retry = 0;
		IOException e1 = null;
		while (retry++ < 3) {
			try {
				doc = conn.get();
				return doc;
			} catch (IOException e) {
				e1 = e;
				System.out.println("retry:"+retry);
			}
		}
		throw new RuntimeException(e1);
		
	}
	
	public static Response getResponse(Connection conn){
		Response response = null;
		int retry = 0;
		IOException e1 = null;
		while (retry++ < 3) {
			try {
				response = conn.execute();
				return response;
			} catch (IOException e) {
				e1 = e;
				System.out.println("retry:"+retry);
			}
		}
		throw new RuntimeException(e1);
	}

}
