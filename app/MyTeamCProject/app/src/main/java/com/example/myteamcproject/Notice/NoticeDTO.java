package com.example.myteamcproject.Notice;

import java.io.Serializable;

public class NoticeDTO implements Serializable {

	int n_numb, n_readcount;
	String n_title, n_content, n_writer;
	String n_date, n_filename;
	String n_filepath, n_important;
	
	public int getN_numb() {
		return n_numb;
	}
	public void setN_numb(int n_numb) {
		this.n_numb = n_numb;
	}
	public String getN_title() {
		return n_title;
	}
	public void setN_title(String n_title) {
		this.n_title = n_title;
	}
	public String getN_content() {
		return n_content;
	}
	public void setN_content(String n_content) {
		this.n_content = n_content;
	}
	public String getN_writer() {
		return n_writer;
	}
	public void setN_writer(String n_writer) {
		this.n_writer = n_writer;
	}
	public String getN_date() {
		return n_date;
	}
	public void setN_date(String n_date) {
		this.n_date = n_date;
	}
	public int getN_readcount() {
		return n_readcount;
	}
	public void setN_readcount(int n_readcount) {
		this.n_readcount = n_readcount;
	}
	public String getN_filename() {
		return n_filename;
	}
	public void setN_filename(String n_filename) {
		this.n_filename = n_filename;
	}
	public String getN_filepath() {
		return n_filepath;
	}
	public void setN_filepath(String n_filepath) {
		this.n_filepath = n_filepath;
	}
	public String getN_important() {
		return n_important;
	}
	public void setN_important(String n_important) {
		this.n_important = n_important;
	}

}//NotgiceVO
