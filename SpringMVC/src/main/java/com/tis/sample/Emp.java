package com.tis.sample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Emp {

	@Value("ȫ�浿")
	private String name;
	@Value("�λ��")
	private String dept;
	@Value("3000")
	private int salaray;
	
}