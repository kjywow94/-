package com.bcauction.infrastructure.repository.factory;

import com.bcauction.domain.Token;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TokenFactory {

    public static Token 생성(ResultSet rs) throws SQLException {
        if (rs == null) return null;
        Token token = new Token();
        token.setId(rs.getLong("id"));
		token.setUser_id(rs.getLong("user_id"));
        token.setToken(rs.getString("token"));
        return token;
    }
}