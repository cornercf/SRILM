package com.srilm.base;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NgramLM {
	
	/**
	 * 
	 */
	private static int mMaxNgramOrder = 100;
	
	private static Vocab vocab = new Vocab();
	
	/**
	 * @param lmFile
	 * @param limitVocab
	 * @return
	 */
	public boolean read(String lmFile, boolean limitVocab) {
		
		
		/*maximal n-gram order in this model file*/
		int maxOrder = 0; 
		
		/* the number of n-grams for each order */
		long numNgrams[] = new long[mMaxNgramOrder + 1];
		
		/* Number of n-grams actually read */
		long numRead[] = new long[mMaxNgramOrder + 1];
		
		/* Numer of n-gram skipped due to OOVs */
		long numOOVs = 0;
		
		/* 
		 * section of file being read:
		 * -1  - pre-header,
		 * 0   - header,
		 * 1   - unigrams,
		 * 2   - bigrams,
		 * ... 
		 */
		int state = -1;
		
		/* at most one warning about <unk> */
		boolean warnedAboutUnk = false;
		
		/*initial*/
		for(int i = 0; i <= mMaxNgramOrder; i++) {
			numNgrams[i] = 0;
			numRead[i] = 0;
		}
		
		//clear();
		
		/*
		 * The ARPA format implicitly assumes a zero-gram backoff weight of 0.
		 * This has to be properly represented in the BOW trie so various
		 * recursive operations work correctly.
		 */
		int nullContext[] = new int[1];
		nullContext[0] = ConstVar.Vocab_None;
		insertBOW(nullContext, ConstVar.LogP_Zero);
		
		BufferedReader breader = new BufferedReader(new InputStreamReader(new FileInputStream(lmFile), "UTF-8"));
		String line;
		while((line = breader.readLine()) != null) {
			boolean blackslash = line.startsWith("\\");
			switch(state) {
			
			case -1: /*looking for start of header*/
				if(blackslash && line.equals("\\data\\")) {
					state = 0;
					continue;
				}
				continue;
				
			case 0: /*ngram header*/
				int thisOrder;
				long nNgrams;
				
				if(blackslash && line.substring(line.indexOf("\\"), line.indexOf("-grams")).matches("\\d")) {
					/*
					 * start reading n-grams
					 */
					state = Integer.valueOf(line.substring(line.indexOf("\\"), line.indexOf("-grams")));
					if(state < 1 || state > mMaxNgramOrder) {
						System.out.println("Invalid ngram order.");
						return false;
					}
					
					continue;
					
				} else if(line.matches("ngram \\d+=\\d+")) {
					/*
					 * scanned a line of the form
					 * ngram <N>=<howmany>
					 * now perform various sanity checks
					 */
					thisOrder = Integer.valueOf(line.substring(line.indexOf(" ") +1, line.indexOf("=")));
					long num = Integer.valueOf(line.substring(line.indexOf("=") +1, line.length()));
					if(thisOrder <= 0 || thisOrder > mMaxNgramOrder) {
						System.out.println("ngram order " + thisOrder + " out of range.");
						return false;
					}
					
					if(num < 0) {
						System.out.println("ngram number " + num + " out of range");
						return false;
					}
					
					if(thisOrder > maxOrder) {
						maxOrder = thisOrder;
					}
					numNgrams[thisOrder] = num;
					continue;
				} else {
					System.out.println("unexpected input");
					return false;
				}
				
				default: /*reading n-grams, where n == state*/
					if(blackslash) {
						if(numOOVs > 0) {
							numOOVs = 0;
						}
					}
					
					if(blackslash && line.matches("\\\\d+-grams")) {
						state = Integer.valueOf(line.substring(line.indexOf("\\") +1, line.indexOf("-")));
						if(state < 1 || state > maxOrder) {
							System.out.println("invalid ngram order " + state);
							return false;
						}
						/*
						 * starting reading more n-grams
						 */
						continue;
					} else if (blackslash && line.matches("\\end\\")) {
						/*
						 * check that the total number of ngrams read matches 
						 * that found in the header
						 */
						for(int i = 0; i <= maxOrder && i <= order; i++) {
							if()
						}
					}
			}
			
			
				
		}
		
		return false;
	}

}
