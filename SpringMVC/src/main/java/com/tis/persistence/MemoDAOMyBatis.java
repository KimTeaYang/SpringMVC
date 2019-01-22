package com.tis.persistence;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import lombok.extern.log4j.Log4j;

//영속성 계층에 주는 어노테이션 => @Repository("빈이름")
@Repository(value="memoDAOMyBatis")
@Log4j
public class MemoDAOMyBatis implements MemoDAO {
	
	private final String NS = "com.tis.mapper.MemoMapper";
	
	//root-context.xml에 등록된 빈을 주입
	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate session;

	@Override
	public int getTotalCount() {
		log.info("session==="+session);
		return session.selectOne(NS+".memoCount");
	}

}
