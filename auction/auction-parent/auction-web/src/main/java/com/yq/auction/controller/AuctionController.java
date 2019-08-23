package com.yq.auction.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yq.auction.pojo.Auction;
import com.yq.auction.pojo.AuctionCustom;
import com.yq.auction.pojo.AuctionRecord;
import com.yq.auction.pojo.AuctionUser;
import com.yq.auction.pojo.Results;
import com.yq.auction.service.AuctionService;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuctionController {
	
	@Reference
    private AuctionService auctionService;

	private static final int PAGE_SIZE = 5;
	
	@GetMapping("/toAddAuction")
	public String toAddAuction() {
				
		return "addAuction";
	}
	
	@RequestMapping("/publishAuctions")
	public String publishAuctions(Auction auction,MultipartFile pic) {
		
		//1、另存文件到服务器文件夹中
		try {
			if (pic.getSize() > 0) {
				
				String path = "d:/file";	//路径映射
				File targetFile = new File(path,pic.getOriginalFilename());
				pic.transferTo(targetFile);
				
				auction.setAuctionpic(pic.getOriginalFilename());
				auction.setAuctionpictype(pic.getContentType());
			}
			auctionService.addAuction(auction);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//postHandler会在此执行
		return "redirect:/queryAuctions";
	}

    @RequestMapping("/queryAuctions")
    public ModelAndView queryAuctions(@ModelAttribute("condition") Auction auction,HttpSession session,
    									@RequestParam(value = "pageNum",required = false,defaultValue = "1")int pageNum){

    	ModelAndView mv = new ModelAndView();
    	
    	Results results = auctionService.findAuctions(pageNum, PAGE_SIZE, auction);
    	
    	//从shiro框架中获取用户的对象
    	//AuctionUser loginUser = (AuctionUser) SecurityUtils.getSubject().getPrincipal();
    	
    	//pageInfo.getPages();	//${pageInfo.pages}
    	mv.addObject("pageInfo",results.getPageInfo());
    	mv.addObject("auctionList",results.getDatas());
    	
    	mv.setViewName("index");
    	
        return mv;
    }
    
    @RequestMapping("/toDetail/{auctionid}")
    public ModelAndView toDetail(@PathVariable int auctionid) {
    	
    	Auction auction = auctionService.findAuctionAndRecordListById(auctionid);
    	
    	ModelAndView mv = new ModelAndView();
    	
    	mv.addObject("auctionDetail", auction);
    	mv.setViewName("auctionDetail");
    	return mv;
    }
    
    @RequestMapping("/saveAuctionRecord")
    public String saveAuctionRecord(AuctionRecord record,HttpSession session,Model model) throws Exception {
    	
			AuctionUser user = (AuctionUser) session.getAttribute("user");
			record.setUserid(user.getUserid());
			
			//拍卖时间
			record.setAuctiontime(new Date());
			
			auctionService.addAuctionRecord(record);
    	
    	return "redirect:/toDetail/" + record.getAuctionid();
    	
    }

	@RequestMapping("/toAuctionResult")
    public ModelAndView toAuctionResult(HttpSession session) {
		
		List<AuctionCustom> endtimeList = auctionService.findAuctionEndtime();
		List<Auction> noendtimeList = auctionService.findAuctionNoendtime();
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("endtimeList",endtimeList);
		mv.addObject("noendtimeList",noendtimeList);
		mv.setViewName("auctionResult");
		
		return mv;
    }
    
}
