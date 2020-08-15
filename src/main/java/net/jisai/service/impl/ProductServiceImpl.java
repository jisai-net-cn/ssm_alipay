package net.jisai.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.jisai.mapper.ProductMapper;
import net.jisai.pojo.Product;
import net.jisai.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	@Resource
	private ProductMapper productMapper;
	@Override
	public List<Product> showAll() {
		// TODO Auto-generated method stub
		return productMapper.selectAll();
	}
	@Override
	public Product showProductById(int id) {
		// TODO Auto-generated method stub
		return productMapper.selectProductById(id);
	}
	@Override
	public int updateProductByIdAndMount(int id, int num) {
		// TODO Auto-generated method stub
		return productMapper.updateProductByIdAndMount(id, num);
	}
	@Override
	public Double showPriceById(int id) {
		// TODO Auto-generated method stub
		return productMapper.selectPriceByid(id);
	}
}
