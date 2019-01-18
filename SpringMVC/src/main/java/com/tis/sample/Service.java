package com.tis.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Setter;

@Component
@Data
public class Service {

	@Setter(onMethod=@_({@Autowired}))
	private Emp emp;
	
}
