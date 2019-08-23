package com.yq.auction.utils;

/*
 * 集中处理软件运行中产生的各种异常
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Component
public class CustomExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse reso, Object obj,
			Exception exception) {
		AuctionPriceException priceException = null;
		
		if (exception instanceof AuctionPriceException) {
			
			priceException = (AuctionPriceException) exception;
			
		}else {
			priceException = new AuctionPriceException("未知异常！");
		}
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("errorMsg", exception.getMessage());
		mv.setViewName("error");
		
		return mv;
	}

}
