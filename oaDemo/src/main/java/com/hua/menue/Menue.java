package com.hua.menue;

/**
 * ���  zTree�����Ҫ�� һЩ�ؼ���Ϣ
 * @author newuser
 *
 */
public class Menue {
	private Integer id; // �ڵ�id
	private String name; // �ڵ�����
	private String url;// �ڵ�url��ַ
	private Integer pId; // ��id
	private boolean open; // �Ƿ���Դ�
	private boolean checked; // �Ƿ���Ե�ѡ
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