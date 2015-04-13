package com.lewisapp.bookletter;

import android.text.Html;


public class DataManager {
	private static DataManager instance;

	public static DataManager getInstance() {
		if (instance == null) {
			instance = new DataManager();
		}
		return instance;
	}

	
	public static boolean myMode=false;
	public String myfromHtmltoStringTitle(String htmlString){
		String tmpTitle = Html.fromHtml(htmlString).toString();
		String printTitle = tmpTitle;
		int indexOf = tmpTitle.indexOf("(");
		if (indexOf != -1)
			printTitle = tmpTitle.substring(0, indexOf);
		
		return printTitle;
	}
	
	public String myDatetoSimpleDate(String longDate){

		String printTitle= longDate.substring(0, 10);

		return printTitle;
	}

}
