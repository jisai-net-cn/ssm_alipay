package net.jisai.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.jisai.pojo.Product;

public interface ProductMapper {
	/**
	 * 查询全部商品
	 * @return
	 */
	
	List<Product> selectAll();
	/**
	 * 根据ID查询商品
	 * @param id
	 * @return
	 */
	Product selectProductById(int id);
	/**
	 * 根据商品id和数量，减商品库存
	 * @param id
	 * @param mount
	 * @return
	 */
	int updateProductByIdAndMount(@Param("id")int id,@Param("num") int num);
	
	/**
	 * 根据商品ID查价格
	 * @param id
	 * @return 价格
	 */
	Double selectPriceByid(int id);
}
