package net.jisai.service;

import java.util.List;

import net.jisai.pojo.Product;

public interface ProductService {
	/**
	 * 显示全部商品
	 * @return
	 */
	List<Product> showAll();
	
	/**
	 * 根据商品id显示商品
	 * @param id
	 * @return
	 */
	Product showProductById(int id);
	
	/**
	 * 根据商品ID减商品库存
	 * @param id 商品id
	 * @param mount 数量
	 * @return
	 */
	int updateProductByIdAndMount(int id,int num);
	
	/**
	 *根据ID显示价格 
	 * @param id
	 * @return
	 */
	Double showPriceById(int id);
}
