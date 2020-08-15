package net.jisai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("net.jisai.mapper")	//用于扫描MyBatis下的Mapper接口
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
