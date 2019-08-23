package com.web.shopping.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@ConfigurationProperties(prefix="file")
public class FileProperties {
	
	private String staticAccessPath;	//映射虚拟路径
	
	private String uploadFileFolder;	//文件存放的物理路径

	public String getStaticAccessPath() {
		return staticAccessPath;
	}

	public void setStaticAccessPath(String staticAccessPath) {
		this.staticAccessPath = staticAccessPath;
	}

	public String getUploadFileFolder() {
		return uploadFileFolder;
	}

	public void setUploadFileFolder(String uploadFileFolder) {
		this.uploadFileFolder = uploadFileFolder;
	}
}
