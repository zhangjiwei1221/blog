package cn.butterfly.back.util;

import cn.butterfly.back.constant.BaseConstants;
import cn.butterfly.back.entity.SysUser;
import cn.butterfly.back.exception.ApiException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import java.util.function.Function;

/**
 * jwt 工具类
 *
 * @author zjw
 * @date 2022-07-03
 */
public class JwtUtils {

	/**
	 * 过期时间 30天
	 */
	private static final long EXPIRE_TIME = (long) 30 * 24 * 60 * 60;

	/**
	 * 生成 token
	 *
	 * @param sysUser 用户
	 * @return token
	 */
	public static String create(SysUser sysUser) {
		Algorithm algorithm = Algorithm.HMAC256(sysUser.getPassword());
		return JWT.create()
			.withClaim(BaseConstants.USERNAME_STR, sysUser.getUsername())
			.withExpiresAt(new Date(System.currentTimeMillis() + (EXPIRE_TIME * 1000)))
			.sign(algorithm);
	}

	/**
	 * 根据 token 获取用户名
	 *
	 * @param token token
	 * @param getUserFun 获取用户函数
	 * @return 用户名
	 */
	public static String getUsernameWithVerify(String token, Function<String, SysUser> getUserFun) {
		try {
			SysUser sysUser = getUserFun.apply(token);
			Algorithm algorithm = Algorithm.HMAC256(sysUser.getPassword());
			JWTVerifier verifier = JWT.require(algorithm)
					.withClaim(BaseConstants.USERNAME_STR, sysUser.getUsername())
					.build();
			DecodedJWT jwt = verifier.verify(token);
			return jwt.getClaim(BaseConstants.USERNAME_STR).asString();
		} catch (Exception e) {
			throw new ApiException("获取用户信息失败");
		}
	}

	/**
	 * 根据 token 获取用户名
	 *
	 * @param token token
	 * @return 用户名
	 */
	public static String getUsername(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim(BaseConstants.USERNAME_STR).asString();
		} catch (Exception e) {
			throw new ApiException("获取用户信息失败");
		}
	}

	private JwtUtils() {}

}
