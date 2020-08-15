package net.jisai.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.jisai.pojo.Product;
import net.jisai.service.ProductService;

@Controller
public class ProductController {
	@Resource
	private ProductService productServiceImpl;
	@RequestMapping("/main")
	public String showAll(Model model) {
		List<Product> all = productServiceImpl.showAll();
		for (Product product : all) {
			System.out.println(product);
		}
		model.addAttribute("all",all);
		return "index";
	}
	@RequestMapping("/buy")
	public String buy(Model model,int id) {
		Product product = productServiceImpl.showProductById(id);
		System.out.println(product);
		model.addAttribute("product",product);
		return "buy";
	}
	/**
	 * 接收参数减少库存，并转发到createOrder controller
	 * @param id
	 * @param name
	 * @param price
	 * @param num
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/order")
	public String updateProductByIdAndMount(int id,String name,Double price,int num,Model model,RedirectAttributes redirectAttributes) {
		int index = productServiceImpl.updateProductByIdAndMount(id, num);
		if (index>0) {
			System.out.println("库存减少成功");
			redirectAttributes.addAttribute("productId",id);	//产品ID
			redirectAttributes.addAttribute("productNumber",num);	//产品数量
			return "redirect:/createOrder";
		}else {
			System.out.println("库存出错");
			model.addAttribute("msg","库存出错");
			return "index";
		}
	}
}
