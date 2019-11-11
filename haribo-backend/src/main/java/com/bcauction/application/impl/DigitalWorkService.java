package com.bcauction.application.impl;

import com.bcauction.application.IDigitalWorkService;
import com.bcauction.application.IFabricService;
import com.bcauction.domain.DigitalWork;
import com.bcauction.domain.DigitalWorkWithImg;
import com.bcauction.domain.Ownership;
import com.bcauction.domain.WorksImage;
import com.bcauction.domain.exception.ApplicationException;
import com.bcauction.domain.exception.NotFoundException;
import com.bcauction.domain.repository.IDigitalWorkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.List;

@Service
public class DigitalWorkService implements IDigitalWorkService {

	public static final Logger logger = LoggerFactory.getLogger(DigitalWorkService.class);

	private IDigitalWorkRepository digitalWorkRepository;
	private IFabricService fabricService;

	@Autowired
	public DigitalWorkService(IFabricService fabricService, IDigitalWorkRepository digitalWorkRepository) {
		this.fabricService = fabricService;
		this.digitalWorkRepository = digitalWorkRepository;
	}

	@Override
	public List<DigitalWork> 목록조회() {
		return this.digitalWorkRepository.목록조회();
	}
	
	@Override
	public List<DigitalWork> 전체조회() {
		return this.digitalWorkRepository.전체조회();
	}

	@Override
	public List<DigitalWork> 사용자작품목록조회(final long id) {
		return this.digitalWorkRepository.사용자작품목록조회(id);
	}

	@Override
	public DigitalWorkWithImg 조회(final long id) {
		DigitalWorkWithImg dww = null;
		DigitalWork work = this.digitalWorkRepository.조회(id);
		try {
			File f = new File("worksImage/" + work.getId());
			FileInputStream fis;
			try {
				fis = new FileInputStream(f);
			} catch (FileNotFoundException e) {
				f = new File("worksImage/artwork1.jpg");
				fis = new FileInputStream(f);
			}
			byte byteArray[] = new byte[(int) f.length()];
			fis.read(byteArray);
			String encodeImg = "data:image/" + work.getId() + ";base64, "
					+ Base64.getEncoder().encodeToString(byteArray);
			dww = new DigitalWorkWithImg(encodeImg);
			fis.close();
			dww.setId(work.getId());
			dww.set공개여부(work.get공개여부());
			dww.set상태(work.get상태());
			dww.set설명(work.get설명());
			dww.set이름(work.get이름());
			dww.set회원id(work.get회원id());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dww;
	}

	/**
	 * 작품 등록 시 작품 정보를 저장하고 패브릭에 작품 소유권을 등록한다.
	 * 
	 * @param 작품
	 * @return DigitalWork
	 */
	@Override
	public DigitalWork 작품등록(final DigitalWork 작품) {
		// TODO
		long 작품id = this.digitalWorkRepository.추가(작품);
		this.fabricService.소유권등록(작품.get회원id(), 작품id);
		return 작품;
	}

	@Override
	public WorksImage uploadImage(final WorksImage image) {
		String imageData = image.getImgData();
		String imageName = image.getImgName();
		String imageExtension = image.getExtension();
		Decoder decoder = Base64.getDecoder();
		byte[] decodedByte = decoder.decode(imageData.split(",")[1]);
		try {
			FileOutputStream fos = new FileOutputStream("worksImage/" + imageName);
			fos.write(decodedByte);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;
	}

	/**
	 * 작품 삭제 시, 작품의 상태를 업데이트하고 패브릭에 작품 소유권 소멸 이력을 추가한다.
	 * 
	 * @param id 작품id
	 * @return DigitalWork
	 */
	@Override
	public DigitalWork 작품삭제(final long id) {
		// TODO
		DigitalWork 작품 = 조회(id);
		this.digitalWorkRepository.삭제(id);
		this.fabricService.소유권소멸(작품.get회원id(), 작품.getId());
		return 작품;
	}

	@Override
	public DigitalWork 작품정보수정(final DigitalWork 작품) {
		DigitalWork workStored = this.digitalWorkRepository.조회(작품.getId());
		if (workStored == null)
			throw new ApplicationException("해당 작품을 찾을 수 없습니다.");

		if (작품.get회원id() != 0 && 작품.get회원id() != workStored.get회원id())
			throw new ApplicationException("잘못된 접근입니다.");

		if (작품.get이름() == null || "".equals(작품.get이름()))
			작품.set이름(workStored.get이름());
		if (작품.get설명() == null || "".equals(작품.get설명()))
			작품.set설명(workStored.get설명());
		if (작품.get공개여부() == null || "".equals(작품.get공개여부()))
			작품.set공개여부(workStored.get공개여부());
		if (작품.get상태() == null || "".equals(작품.get상태()))
			작품.set상태(workStored.get상태());
		if (작품.get회원id() == 0)
			작품.set회원id(workStored.get회원id());

		int affected = this.digitalWorkRepository.수정(작품);
		if (affected == 0)
			throw new ApplicationException("작품정보수정 처리가 반영되지 않았습니다.");

		return 작품;
	}

	@Override
	public List<DigitalWorkWithImg> DigitalWorkslist(List<DigitalWork> 목록) {
		List<DigitalWorkWithImg> listWithImg = new ArrayList<>();

		for (DigitalWork work : 목록) {

			try {
				File f = new File("worksImage/" + work.getId());
				FileInputStream fis;
				try {
					fis = new FileInputStream(f);
				} catch (FileNotFoundException e) {
					f = new File("worksImage/artwork1.jpg");
					fis = new FileInputStream(f);
				}
				byte byteArray[] = new byte[(int) f.length()];
				fis.read(byteArray);
				String encodeImg = "data:image/" + work.getId() + ";base64, "
						+ Base64.getEncoder().encodeToString(byteArray);
				DigitalWorkWithImg dww = new DigitalWorkWithImg(encodeImg);
				fis.close();
				dww.setId(work.getId());
				dww.set공개여부(work.get공개여부());
				dww.set상태(work.get상태());
				dww.set설명(work.get설명());
				dww.set이름(work.get이름());
				dww.set회원id(work.get회원id());
				listWithImg.add(dww);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listWithImg;
	}

}
