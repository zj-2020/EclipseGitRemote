package com.heima.zj;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * jedis的测试类
 * @author work
 *
 */
public class JedisTest {

	//jedis入门
	@Test
	public void test1() {
		//1.获取连接
		Jedis jedis = new Jedis("localhost",6379);
		//2.操作
		jedis.set("username","zhangsan");
		//3.关闭连接
		jedis.close();
	}
	/**
	 * String类型
	 */
	@Test
	public void test2() {
		Jedis jedis = new Jedis();//空参默认为"localhost",6379
		jedis.set("username","lisi");
		String username = jedis.get("username");
		System.out.println(username);
		
		//可以使用setex()方法存储可以指定过期时间的 key value
		jedis.setex("activecode", 20, "hehe");
		System.err.println(jedis.get("activecode"));//20秒后这个激活码会消失
		jedis.close();
	}
	/**
	 * hash数据结构操作
	 */
	@Test
	public void test3() {
		Jedis jedis = new Jedis();
		jedis.hset("user", "name", "tianqi");
		jedis.hset("user", "age", "22");
		jedis.hset("user", "gender", "male");
		String name = jedis.hget("user", "name");
		System.out.println(name);
		
		Map<String, String> user = jedis.hgetAll("user");
		Set<String> keySet = user.keySet();
		for(String key : keySet) {
			String value = user.get(key);
			System.out.println(value);
		}
	}
	/**
	 * 列表数据结构操作
	 */
	@Test
	public void test4() {
		Jedis jedis = new Jedis();
		jedis.lpush("mylist", "a","b","c");
		jedis.rpush("mylist", "a","b","c");
		List<String> mylist = jedis.lrange("mylist", 0, -1);
		System.out.println(mylist);
		
		System.out.println(jedis.lpop("mylist"));
		System.out.println(jedis.rpop("mylist"));
	}
	/**
	 * set数据结构操作
	 */
	@Test
	public void test5() {
		Jedis jedis = new Jedis();
		jedis.sadd("myset", "java","c++","c");
	    Set<String> set = jedis.smembers("myset");
	    System.out.println(set);
	}
	/**
	 * sortset数据结构操作
	 */
	@Test
	public void test6() {
		Jedis jedis = new Jedis();
		jedis.zadd("mysortedset", 3,"java");
		jedis.zadd("mysortedset", 30,"c++");
		jedis.zadd("mysortedset", 25,"c");
		
	    Set<String> sortset = jedis.zrange("mysortedset",0,-1);
	    System.out.println(sortset);
	}
	/**
	 * jedis连接池
	 */
	@Test
	public void test7() {
		//创建一个配置对象
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(50);
		config.setMaxIdle(10);
		//创建连接池对象
		JedisPool jedisPool = new JedisPool(config,"localhost",6379);
		//获取连接
		Jedis jedis = jedisPool.getResource();
		System.out.println(jedis.set("hehe", "haha"));
		System.out.println(jedis.get("hehe"));
		jedis.close();//不关闭，而是归还
	}
	/**
	 * jedis工具类的使用
	 */
	@Test
	public void test8() {
		Jedis jedis = JedisPoolUtil.getJedis();
		System.out.println(jedis.set("hehe", "haha"));
		System.out.println(jedis.get("hehe"));
		jedis.close();
	}
}
