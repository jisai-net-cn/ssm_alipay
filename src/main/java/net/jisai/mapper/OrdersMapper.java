package net.jisai.mapper;

import net.jisai.pojo.Orders;

public interface OrdersMapper {
	/**
	 * 提交订单
	 * @param orders
	 * @return
	 */
	int insertOrders(Orders orders);
	
	/**
	 * 根据订单号，查询订单信息
	 * @param id
	 * @return
	 */
	Orders selectOrdersByOrderNumber(String number);
	/**
	 * 根据订单号更新订单支付信息
	 * @param orders
	 * @return
	 */
	int updateOrdersByOrderNumber(Orders orders);
}
