package com.tis.sample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Emp {

	@Value("홍길동")
	private String name;
	@Value("인사부")
	private String dept;
	@Value("3000")
	private int salaray;
	
}