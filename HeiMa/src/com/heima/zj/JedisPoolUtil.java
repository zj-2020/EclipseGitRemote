package com.heima.zj;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtil {

	/**
	 * 获取连接的方法123
	 */
	private static JedisPool jedisPool;
	
	static {
	    //读取配置文件
		InputStream in = JedisPoolUtil.class.getClassLoader().getResourceAsStream("jedis.properties");
		Properties pro = new Properties();
		try {
			pro.load(in);
		}catch(IOException e) {
			e.printStackTrace();
		}
		//获取数据到JedisPoolConfig
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(Integer.parseInt(pro.getProperty("maxTotal")));
		config.setMaxIdle(Integer.parseInt(pro.getProperty("maxIdle")));
		
		//初始化JedisPool
		jedisPool = new JedisPool(config,pro.getProperty("host"),Integer.parseInt(pro.getProperty("port")));
	}
	
	public static Jedis getJedis() {
		return jedisPool.getResource();
	}
}
