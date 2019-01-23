package com.tis.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tis.domain.MemoVO;
import com.tis.persistence.MemoDAO;

//비즈니스 계층. 로직 또는 트랜잭션 처리 등을 담당함.
@Service("memoServiceImpl")
public class MemoServiceImpl implements MemoService {
	
	@Resource(name="memoDAOMyBatis")
	private MemoDAO memoDAO;

	@Override
	public int getTotalCount() {
		return this.memoDAO.getTotalCount();
	}

	@Override
	public int createMemo(MemoVO memo) {
		return this.memoDAO.createMemo(memo);
	}

	@Override
	public List<MemoVO> listMemo(Map<String, Integer> map) {
		return this.memoDAO.listMemo(map);
	}

	@Override
	public int deleteMemo(int idx) {
		return this.memoDAO.deleteMemo(idx);
	}

	@Override
	public int updateMemo(MemoVO memo) {
		return this.memoDAO.updateMemo(memo);
	}

	@Override
	public MemoVO getMemo(int idx) {
		return this.memoDAO.getMemo(idx);
	}

}
