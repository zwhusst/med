package com.souyibao.search;

public class SelectOption {
	private String id = null;
	private String value = null;
	private boolean enabled = false;
	private short level = 0;
	private boolean checked = false;
	
	public SelectOption(){}
	public SelectOption (String id, String value) {
		this.id = id;
		this.value = value;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	public short getLevel() {
		return level;
	}
	public void setLevel(short level) {
		this.level = level;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}	
}
