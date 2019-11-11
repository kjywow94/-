package com.bcauction.application;

import com.bcauction.domain.DigitalWork;
import com.bcauction.domain.DigitalWorkWithImg;
import com.bcauction.domain.WorksImage;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IDigitalWorkService
{
	List<DigitalWork> 목록조회();
	List<DigitalWork> 사용자작품목록조회(long id);
	DigitalWorkWithImg 조회(long id);

	@Transactional
	DigitalWork 작품등록(DigitalWork 작품);

	@Transactional
	DigitalWork 작품정보수정(DigitalWork 작품);

	@Transactional
	DigitalWork 작품삭제(long id);
	WorksImage uploadImage(WorksImage image);
	List<DigitalWorkWithImg> DigitalWorkslist(List<DigitalWork> 목록);
	List<DigitalWork> 전체조회();
}
