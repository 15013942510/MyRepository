package com.hua.menue;

/**
 * 存放  zTree插件需要的 一些关键信息
 * @author newuser
 *
 */
public class Menue {
	private Integer id; // 节点id
	private String name; // 节点名称
	private String url;// 节点url地址
	private Integer pId; // 父id
	private boolean open; // 是否可以打开
	private boolean checked; // 是否可以单选
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	

}