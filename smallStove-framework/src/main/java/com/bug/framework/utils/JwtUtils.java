package com.bug.framework.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

/**
 * @Author: BugOS-ly
 * @Date: 2022/7/15 21:19
 * @Description: JWT工具类，用来生成access_token和refreshToken
 */
public class JwtUtils {
    //一般放在官方声明中
    // 密钥，后续会进行加密
    private static final String JWT_SECRET = "BugOS_ly";
    // access_token的过期时间
    private static final long EXPIRATION_TIME = 1000 * 60 * 120;
    // refresh_token的过期时间
    private static final long REFRESH_EXPIRATION_TIME = 1000 * 60 * 150;


    //一般放在自定义声明中
    // jwt主题
    public static final String SUBJECT = "sub";
    // 当前登录用户
    public static final String LOGIN_USER_ID = "loginUserId";
    // 用户权限
    public static final String AUTHORITIES = "authorities";


    /**
     * 根据Authentication(验证)对象生成JWT
     */
    public static String generateTokenByAuthentication(Authentication authentication) {
        //自定义属性
        Map<String, Object> claims = new HashMap<>();

        //jwt主题
        claims.put(SUBJECT, authentication.getName());
        //jwt中自定义声明：用户权限
        claims.put(AUTHORITIES, spliceAuthorities(authentication.getAuthorities()));

        return generateTokenByClaims(claims);
    }

    /**
     * 根据UserDetails对象创建claims集合然后生成对应JWT
     */
    public static String generateTokenByUserDetails(UserDetails userDetails) {
        //自定义声明（private claims）
        Map<String, Object> claims = new HashMap<>();

        //JWT主题
        claims.put(SUBJECT, userDetails.getUsername());
        //用户权限
        claims.put(AUTHORITIES, spliceAuthorities(userDetails.getAuthorities()));

        return generateTokenByClaims(claims);
    }

    /**
     * 根据Claims（声明）生成JWT，一般用来服务本类中生成jwt的其他方法（生成基础jwt，通过传参claims来自定义jwt）
     */
    public static String generateTokenByClaims(Map<String, Object> claims) {
        return Jwts.builder()
                //自定义属性
                .setClaims(claims)
                //JWT的签发日期
                .setIssuedAt(new Date())
                //过期时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                //签名
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }

    /**
     * 根据token生成refreshToken
     */
    public static String generateRefreshTokenByToken(String token) {
        return Jwts.builder()
                //自定义属性
                .setClaims(parseToken(token))
                //JWT的签发日期
                .setIssuedAt(new Date())
                //过期时间
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                //签名
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }

    /**
     * 根据refreshToken生成token
     */
    public static String generateTokenByRefreshToken(String refreshToken) {
        return Jwts.builder()
                //自定义属性
                .setClaims(parseToken(refreshToken))
                //JWT的签发日期
                .setIssuedAt(new Date())
                //过期时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                //签名
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }

    /**
     * 拼接用户权限，转为字符串
     */
    public static StringBuffer spliceAuthorities(Collection<? extends GrantedAuthority> authorities) {
        StringBuffer permissionString = new StringBuffer();

        // 拼接用户权限字符串
        for (GrantedAuthority authority : authorities) {
            permissionString.append(authority.getAuthority()).append(",");
        }

        return permissionString;
    }

    /**
     * 加密JWT密钥并返回加密后的密钥
     */
    private static SecretKey createSecret() {
        //加密（可解密的）
        byte[] decode = Base64.getDecoder().decode(JWT_SECRET);
        return new SecretKeySpec(decode, 0, decode.length, "AES");
    }

    /**
     * 解析JWT, 获得Claims（声明）
     */
    public static Claims parseToken(String token) throws ExpiredJwtException {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            //如果过期,在异常中调用, 返回claims, 否则无法解析过期的token
            claims = e.getClaims();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 从JWT中获得主题
     */
    public static String getSubject(String token) {
        try {
            return parseToken(token).getSubject();
        } catch (ExpiredJwtException e) {
            //如果过期, 需要在此处异常调用如下的方法, 否则拿不到用户名
            return e.getClaims().getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从JWT中获取签发时间
     */
    public static Date getIssuedAt(String token) {
        Date issuedAt;
        try {
            Claims claims = parseToken(token);
            issuedAt = claims.getIssuedAt();
        } catch (Exception e) {
            issuedAt = null;
        }
        return issuedAt;
    }

    /**
     * 从JWT中获取过期时间
     */
    public static Date getExpiration(String token) {
        Date expiration;
        try {
            Claims claims = parseToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            // 如果token中没有设置过期时间，就给一个默认的过期时间
            expiration = new Date(EXPIRATION_TIME);
        }
        return expiration;
    }

    /**
     * 判断JWT是否过期
     */
    public static Boolean isExpired(String token) {
        Date expiration = getExpiration(token);
        //判断过期时间是否在当前时间之前
        return expiration.before(new Date());
    }

    /**
     * 判断token是否可以刷新
     */
    public static Boolean isRefresh(String token) {
        Date expiration = getExpiration(token);

        long time = 1000 * 60 * 15;
        Date date = new Date(new Date().getTime() + time);

        return date.after(expiration);
    }


    /**
     * 判断Token是否合法（属于当前登录用户且token未过期）
     */
    public static Boolean isLegal(String token, String username) {
        String subject = getSubject(token);
        //如果用户名与token一致且token没有过期, 则认为合法
        return username.equals(subject) && !isExpired(token);
    }

}
