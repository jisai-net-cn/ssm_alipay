package net.jisai.service;

import net.jisai.pojo.Orders;

public interface OrdersService {
	/**
	 * 生成未支付订单
	 * @param orders
	 * @return
	 */
	int createOrders(Orders orders);
	/**
	 * 根据订单号查询订单信息
	 * @param number 订单号
	 * @return 订单信息
	 */
	Orders showOrderByOrderNumber(String number);
	/**
	 * 根据订单号更新订单支付信息
	 * @param orders
	 * @return
	 */
	int updateOrdersByOrderNumber(Orders orders);
}
