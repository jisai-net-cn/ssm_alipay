package net.jisai.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import net.jisai.pojo.Orders;
import net.jisai.pojo.User;
import net.jisai.service.OrdersService;
import net.jisai.service.ProductService;

@Controller
public class OrdersController {
	@Resource
	private OrdersService ordersServiceImpl;
	@Resource
	private ProductService productServiceImpl;
	
	/**
	 * 第一次生成订单（未支付）
	 */
	@RequestMapping("/createOrder")
	public String createOrder(Orders orders,Model model,HttpSession session) {
		System.out.println(orders);
//		获取session里的用户ID
		User user = (User)session.getAttribute("user");
		int userid = user.getId();
		orders.setUserId(userid);
//		依据当前时间生成订单号
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String datesString = sdf.format(date);
		String orderNumber = datesString+userid;	//订单号：当前时间+用户id
//		设置订单号
		orders.setOrderNumber(orderNumber);
//		获取当前时间并转换成sqldate格式
		Timestamp timestamp = new Timestamp(date.getTime());
		orders.setCreateTime(timestamp);
//		设置订单状态
		orders.setOrderStatus(false);
//		计算订单金额
		Double price = productServiceImpl.showPriceById(orders.getProductId());	//单价
		int productNumber = orders.getProductNumber();	//数量
		Double amout = price*productNumber;
		orders.setOrderAmount(amout);
		int index = ordersServiceImpl.createOrders(orders);
		System.out.println("index:"+index);
		System.out.println("orders:"+orders);
		if (index>0) {
			model.addAttribute("orders",orders);
			model.addAttribute("product",productServiceImpl.showProductById(orders.getProductId()));
			return "order";
		}else {
			model.addAttribute("msg","订单提交失败");
			return "index";
		}
	}
}
