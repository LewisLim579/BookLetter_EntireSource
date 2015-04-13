package com.lewisapp.bookletter;

import android.content.Context;
import android.graphics.Typeface;


public class FontManager {
	private static FontManager instance;
	public static FontManager getInstance() {
		if (instance == null) {
			instance = new FontManager();
		}
		return instance;
	}
	private FontManager() {
		
	}
	
	Typeface noto;
	public static final String FONT_NAME_NOTOSANS = "noto";

	public Typeface getTypeface(Context context, String fontName) {
		if (FONT_NAME_NOTOSANS.equals(fontName)) {
			if (noto == null) {
				noto = Typeface.createFromAsset(context.getAssets(), "NotoSansKR-Regular.otf");
			}
			return noto;
		}
	
		return null;
	}
}
