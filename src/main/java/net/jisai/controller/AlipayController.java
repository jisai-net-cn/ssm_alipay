package net.jisai.controller;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;

import net.jisai.config.AlipayConfig;
import net.jisai.pojo.Orders;
import net.jisai.pojo.Product;
import net.jisai.service.OrdersService;
import net.jisai.service.ProductService;

@Controller
public class AlipayController {
	@Resource
	private OrdersService ordersServiceImpl;
	@Resource
	private ProductService productServiceImpl;
	@RequestMapping("/alipay")
	@ResponseBody
	public String alipay(String orderNumber) throws AlipayApiException {
		Orders orders = ordersServiceImpl.showOrderByOrderNumber(orderNumber);
		System.out.println("alipay:order:"+orders);
		Product product = productServiceImpl.showProductById(orders.getProductId());
		System.out.println("alipay:product:"+product);
//		初始化AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl,AlipayConfig.app_id,AlipayConfig.merchant_private_key,"json",AlipayConfig.charset,AlipayConfig.alipay_public_key,AlipayConfig.sign_type);
//		设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AlipayConfig.return_url);
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
//		设置订单号
		String out_trade_no = orderNumber;
//		付款金额
		Double total_amount = orders.getOrderAmount();
//		订单名称
		String subject = "购买"+product.getName();
//		商品描述
		String body = "购买了"+orders.getProductNumber()+"个"+product.getName();
//		该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
		String timeout_express = "20m";
		
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
				+ "\"total_amount\":\""+ total_amount +"\"," 
				+ "\"subject\":\""+ subject +"\"," 
				+ "\"body\":\""+ body +"\"," 
				+ "\"timeout_express\":\""+ timeout_express +"\","
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		//请求
		String result = alipayClient.pageExecute(alipayRequest).getBody();
		return result;
	}
	/**
	 * 页面跳转同步通知,直接copy支付宝demo代码 同时修改数据库订单支付状态
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws AlipayApiException 
	 */
	@RequestMapping("/alipayReturn")
	public String alipayReturnNotice(HttpServletRequest request,Model model) throws UnsupportedEncodingException, AlipayApiException {
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		
//		boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

		//——请在这里编写您的程序（以下代码仅作参考）——
//		if(signVerified) {
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			model.addAttribute("ordernumber",out_trade_no);
			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
			model.addAttribute("alipaynumber",trade_no);
			//付款金额
			String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
			model.addAttribute("amount",total_amount);
			Orders orders = new Orders();
			orders.setOrderStatus(true);
			orders.setOrderNumber(out_trade_no);
//			获取当前时间并转换成sqldate格式
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			orders.setPayTime(timestamp);
			orders.setPaymentMethod("alipay");
			System.out.println("return:orders:"+orders);
			int inidex = ordersServiceImpl.updateOrdersByOrderNumber(orders);
			if (inidex>0) {
				System.out.println("更新成功");
				model.addAttribute("status","更新数据库成功");
			}else {
				model.addAttribute("status","更新数据库失败");
			}
			
			

//		}else {
//			out.println("验签失败");
//		}
		//——请在这里编写您的程序（以上代码仅作参考）——
		return "status";
	}
	/**
	 * 处理服务器异步通知，直接copy支付宝demo代码即可
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws AlipayApiException 
	 */
	@RequestMapping("alipayNotify")
	@ResponseBody
	public String alipayNotifyNotice(HttpServletRequest request,Model model) throws UnsupportedEncodingException, AlipayApiException {
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		
		boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

		//——请在这里编写您的程序（以下代码仅作参考）——
		
		/* 实际验证过程建议商户务必添加以下校验：
		1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		4、验证app_id是否为该商户本身。
		*/
		if(signVerified) {//验证成功
			//商户订单号
//			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		
			//支付宝交易号
//			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		
			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
			
			if(trade_status.equals("TRADE_FINISHED")){
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序
					
				//注意：
				//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
			}else if (trade_status.equals("TRADE_SUCCESS")){
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序
				
				//注意：
				//付款完成后，支付宝系统发送该交易状态通知
			}
			
			return "success";
			
		}else {//验证失败
			return "failed";
		
			//调试用，写文本函数记录程序运行情况是否正常
			//String sWord = AlipaySignature.getSignCheckContentV1(params);
			//AlipayConfig.logResult(sWord);
		}
	}
}
