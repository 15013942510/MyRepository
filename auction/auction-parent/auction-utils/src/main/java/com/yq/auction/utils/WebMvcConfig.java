package com.yq.auction.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration  // 把物理的路径  d:/file  映射到虚拟路径  /upload/
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
	private FileProperties fileProperties;
	
//	@Autowired
//	private CheckUserInterceptor checkUserInterceptor;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//前面表示映射的访问路径，后面表示文件存放路径
		registry.addResourceHandler(fileProperties.getStaticAccessPath()).addResourceLocations("file:" + fileProperties.getUploadFileFolder() + "/"); // file: 协议名
	}
	
	//该方法时注册拦截器对象
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		
//		//registry.addInterceptor(checkUserInterceptor);
//		
//		//定义排除的路径
//		List<String> paths = new ArrayList<>();
//		paths.add("/login");
//		paths.add("/doLogin");
//		paths.add("/toRegister");
//		paths.add("/register");
//		paths.add("/defaultKaptcha");
//		paths.add("/css/**");
//		paths.add("/images/**");
//		paths.add("/js/**");
//		
//		registry.addInterceptor(new CheckUserInterceptor())
//		.addPathPatterns("/**").excludePathPatterns(paths);
//	}

}
