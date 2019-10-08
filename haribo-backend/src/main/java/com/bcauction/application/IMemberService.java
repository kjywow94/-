package com.bcauction.application;

import com.bcauction.domain.Member;
import com.bcauction.domain.Token;
import com.bcauction.domain.Wallet;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IMemberService {
    List<Member> 목록조회();
    Member 조회(long id);
    Member 조회(String 이메일);

    @Transactional
    Member 추가(Member 회원);

    @Transactional
    Member 수정(Member 회원);

    @Transactional
    void 삭제(long id);
    
    @Transactional
	Member findUserByWallet(String walletAddress);
	long storeToken(Token tokenInfo);
	Token selectToken(long id);
	Member 권한수정(long id, long auth);
}
