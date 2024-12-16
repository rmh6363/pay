package com.pay.membership.adapter.out.jwt;

import com.pay.membership.application.port.out.AuthMembershipPort;
import com.pay.membership.domain.Membership;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider implements AuthMembershipPort {
    private final String jwtSecret; // jwt token 생성을 위한 비밀키

    private long jwtTokenExpirationInMs; // jwt token만료기간

    private long refreshTokenExpirationInMs; // refresh token만료기간

    public JwtTokenProvider(@Value("${jwt.secret}") String jwtSecret) {
        this.jwtSecret = jwtSecret;
        this.jwtTokenExpirationInMs = 10 * 60 * 1000;//10분
        this.refreshTokenExpirationInMs = 24 * 60 * 60 * 1000;; //24시간
    }

    @Override
    public String generateJwtToken(Membership.MembershipId membershipId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtTokenExpirationInMs);

        return Jwts.builder()
                .setSubject(membershipId.getMembershipId())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    @Override
    public String generateRefreshJwtToken(Membership.MembershipId membershipId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshTokenExpirationInMs);

        return Jwts.builder()
                .setSubject(membershipId.getMembershipId())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    @Override
    public boolean validateJwtToken(String jwtToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtToken);
            return true;
        } catch (MalformedJwtException ex) {
            // Invalid JWT token: 유효하지 않은 JWT 토큰일 때 발생하는 예외
        } catch (ExpiredJwtException ex) {
            // Expired JWT token: 토큰의 유효기간이 만료된 경우 발생하는 예외
        } catch (UnsupportedJwtException ex) {
            // Unsupported JWT token: 지원하지 않는 JWT 토큰일 때 발생하는 예외
        } catch (IllegalArgumentException ex) {
            // JWT claims string is empty: JWT 토큰이 비어있을 때 발생하는 예외
        }
        return false;
    }

    @Override
    public Membership.MembershipId parseMembershipIdFromToken(String jwtToken) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtToken).getBody();
        return new Membership.MembershipId(claims.getSubject());
    }
}
