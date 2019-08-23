package com.web.shopping.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.web.shopping.entity.Result;


@RestController
public class uploadFileController {

	@RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
	public Result uploadFile(MultipartFile file) {
		
		try {
			String path = "D:/file";
			String url = "";
			
			if (file != null && file.getSize() > 0) {
				
				file.transferTo(new File(path, file.getOriginalFilename()));
				url = "http://localhost:8887/upload/" + file.getOriginalFilename();
			}
			return new Result(true, url);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Result(false, "上传失败");
		}
	}
}
