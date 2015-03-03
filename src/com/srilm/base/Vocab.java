package com.srilm.base;

import java.util.HashMap;
import java.util.Map;

public class Vocab {
	
	public static String	Vocab_Unknown = "<unk>";
	public static String	Vocab_SentStart = "<s>";
	public static String	Vocab_SentEnd = "</s>";
	public static String	Vocab_Pause = "-pau-";
	
	private static int mMaxWordID = -1;
	private static Map<String, Integer> mMapIndex = new HashMap<String, Integer>();
	private static String[] mArrayIndex = new String[Integer.MAX_VALUE];
	
	
	public Vocab() {
		byName(Vocab_Unknown);
		byName(Vocab_SentStart);
		byName(Vocab_SentEnd);
		byName(Vocab_Pause);
	}
	
	public static int byName(String word) {
		int i = -1;
		if(mMapIndex.containsKey(word)) {
			i = mMapIndex.get(word);
		} else {
			mMaxWordID++;
			mMapIndex.put(word, mMaxWordID);
			mArrayIndex[mMaxWordID] = word;
			i = mMaxWordID;
		}
		return i;
	}
	
	public static String byIndex(int i) {
		String s = null;
		if(i < mMaxWordID) {
			s = mArrayIndex[i];
		}
		return s;
	}

}
