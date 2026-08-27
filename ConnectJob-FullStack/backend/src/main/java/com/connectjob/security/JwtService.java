package com.connectjob.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
 @Value("${app.jwt.secret}") String secret;
 @Value("${app.jwt.expiration}") long expiration;
 private byte[] key(){return secret.getBytes(StandardCharsets.UTF_8);}
 public String generate(String email){
  return Jwts.builder().subject(email).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+expiration)).signWith(Keys.hmacShaKeyFor(key())).compact();
 }
 public String email(String token){
  return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(key())).build().parseSignedClaims(token).getPayload().getSubject();
 }
}
