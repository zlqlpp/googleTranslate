package com.bean;

public class Main_translator {
	private String id_translatorid;
	private String word           ;
	private String explanation    ;

	private String count          ;
	private String searchdate ;
	private String newsearchdate;
	public String getSearchdate() {
		return searchdate;
	}
	public void setSearchdate(String searchdate) {
		this.searchdate = searchdate;
	}
	public String getNewsearchdate() {
		return newsearchdate;
	}
	public void setNewsearchdate(String newsearchdate) {
		this.newsearchdate = newsearchdate;
	}
	public String getId_translatorid() {
		return id_translatorid;
	}
	public void setId_translatorid(String id_translatorid) {
		this.id_translatorid = id_translatorid;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
}
