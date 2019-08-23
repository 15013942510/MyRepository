package com.web.shopping.controller;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.web.shopping.activemq.JmsConfig;
import com.web.shopping.entity.PageResult;
import com.web.shopping.entity.Result;
import com.web.shopping.pojo.TbGoods;
import com.web.shopping.pojo.TbItem;
import com.web.shopping.pojogroup.Goods;
import com.web.shopping.service.GoodsService;

@RestController
public class GoodsController {

	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	/**
	 * 返回数据列表
	 * @return
	 */
	@RequestMapping(value = "/findAllGoods",method = RequestMethod.GET)
	public List<TbGoods> findAll(){
		
		return goodsService.findAll();
	}
	
	/**
	 * 分页
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/findPageGoods",method = RequestMethod.GET)
	public PageResult findPage(int pageNum,int pageSize) {
		
		return goodsService.findPage(pageNum,pageSize);
	}
	
	/**
	 * 增加
	 * @param goods
	 * @return
	 */
	@RequestMapping(value = "/addGoods",method = RequestMethod.POST)
	public Result add(@RequestBody Goods goods) {
		
		try {
			//获得商家信息：
			//String sellerId = SecurityConTextHolder.getContext().getAuthentication().getName();
			String sellerId = "yqtech";	//暂时设定
			
			goods.getGoods().setSellerId(sellerId);
			
			goodsService.add(goods);
			
			return new Result(true, "增加成功");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param goods
	 * @return
	 */
	@RequestMapping(value = "/updateGoods",method = RequestMethod.POST)
	public Result update(@RequestBody Goods goods) {
		
		//获得商家信息：
		//String sellerId = SecurityConTextHolder.getContext().getAuthentication().getName();
		String sellerId = "yqtech";	//暂时设定
		
		Goods goods2 = goodsService.findOne(goods.getGoods().getId());
		
		if (!sellerId.equals(goods2.getGoods().getSellerId()) || !sellerId.equals(goods.getGoods().getSellerId())) {
			
			return new Result(false, "非法操作");
		}
		
		try {
			goodsService.update(goods);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}
	
	/**
	 * 根据id获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/findOneGoods/{id}",method = RequestMethod.GET)
	public Goods findOne(@PathVariable Long id) {
		
		return goodsService.findOne(id);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteGoods",method = RequestMethod.GET)
	public Result delete(Long[] ids) {
		
		try {
			goodsService.delete(ids);
			return new Result(true, "删除成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
	/**
	 * 搜索 + 分页
	 */
	@RequestMapping(value = "/searchGoods",method = RequestMethod.POST)
	public PageResult search(@RequestBody TbGoods goods,int pageNum,int pageSize) {
		
		//String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
		String sellerId = "yqtech"; //暂时设定
		goods.setSellerId(sellerId);
		
		return goodsService.search(goods, pageNum, pageSize);
	}
	
	/**
	 * 商品审核
	 */
	@RequestMapping(value = "/updateStatus",method = RequestMethod.GET)
	public Result updateStatus(Long[] ids,String status) {
		
		System.out.println("ids" + ids);
		System.out.println("status" + status);
		
		try {
			goodsService.updateStatus(ids,status);
			
			if ("1".equals(status)) {	//若是审核通过
				
				//导入到索引库
				//得到需要导入的SKU列表
				List<TbItem> itemList = goodsService.findItemListByGoodsIdListAndStatus(ids,status);
				
				//导入到solr
				//itemSearchService.importList(itemList);
				final String jsonString = JSON.toJSONString(itemList);
				
				System.out.println("Goods controller:" + jsonString);
				
				Topic topicSolrDeastination = new ActiveMQTopic(JmsConfig.TOPIC_SOLR);
				
				jmsTemplate.send(topicSolrDeastination, new MessageCreator() {
					
					@Override
					public Message createMessage(Session session) throws JMSException {
						
						return session.createTextMessage(jsonString);
					}
				});
				
				//生成商品详细页
				for (final Long goodsId : ids) {
					
					Topic topicPageDestination = new ActiveMQTopic(JmsConfig.TOPIC_HTML);
					jmsTemplate.send(topicPageDestination, new MessageCreator() {
						
						@Override
						public Message createMessage(Session session) throws JMSException {
							
							return session.createTextMessage(goodsId + "");
						}
					});
				}
			}
			
			return new Result(true, "修改状态成功");
		} catch (JmsException e) {
			
			e.printStackTrace();
			return new Result(false, "修改状态失败");
		}
	}
	
}
