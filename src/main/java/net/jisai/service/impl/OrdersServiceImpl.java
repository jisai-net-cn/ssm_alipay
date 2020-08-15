package net.jisai.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.jisai.mapper.OrdersMapper;
import net.jisai.pojo.Orders;
import net.jisai.service.OrdersService;

@Service
public class OrdersServiceImpl implements OrdersService {
	@Resource
	private OrdersMapper ordersMapper;
	@Override
	public int createOrders(Orders orders) {
		// TODO Auto-generated method stub
		return ordersMapper.insertOrders(orders);
	}
	@Override
	public Orders showOrderByOrderNumber(String number) {
		// TODO Auto-generated method stub
		return ordersMapper.selectOrdersByOrderNumber(number);
	}
	@Override
	public int updateOrdersByOrderNumber(Orders orders) {
		// TODO Auto-generated method stub
		return ordersMapper.updateOrdersByOrderNumber(orders);
	}
}
