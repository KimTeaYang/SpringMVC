package com.tis.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class MemoVO implements Serializable{
	
	private int idx;
	private String name;
	private String msg;
	private String filename;
	private Date wdate;
	
}
