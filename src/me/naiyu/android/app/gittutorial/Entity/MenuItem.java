package me.naiyu.android.app.gittutorial.Entity;

import java.io.Serializable;

public class MenuItem implements Serializable {

	private static final long serialVersionUID = -3509501250054034080L;
	
	private String name;
	
	private String url;
	
	private int tag;
	
	private boolean isCheck;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

}
