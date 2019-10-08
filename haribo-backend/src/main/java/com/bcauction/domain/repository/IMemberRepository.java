package com.bcauction.domain.repository;

import java.util.List;

import com.bcauction.domain.Member;
import com.bcauction.domain.Token;

public interface IMemberRepository {
    List<Member> 목록조회();
    Member 조회(long id);
    Member 조회(String 이메일);
    long 추가(Member 회원);
    int 수정(Member 회원);
    int 삭제(long id);
	Long storeToken(Token tokenInfo);
	int updateToken(Token tokenInfo);
	Token selectToken(long user_id);
	int 권한수정(long id, long auth);
}
