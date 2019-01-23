package com.tis.service;

import java.util.List;
import java.util.Map;

import com.tis.domain.MemoVO;

public interface MemoService {

	int getTotalCount();
	int createMemo(MemoVO memo);
	List<MemoVO> listMemo(Map<String,Integer> map);
	int deleteMemo(int idx);
	int updateMemo(MemoVO memo);
	MemoVO getMemo(int idx);
	
}
