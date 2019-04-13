package com.test.proxy9.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TimeHandler implements InvocationHandler {
	// 被代理的对象
	private Object target;
	public TimeHandler(Object target) {
		this.target = target;
	}
	/*
	 *  obj本例实际没用到；Method后面没有s，是一个方法，就是当前方法(non-Javadoc)
	 *  动态代理类的每个方法特别简单，每个方法里主要就是调用下面这个方法 
	 */
	@Override
	public void invoke(Object obj, Method method) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		long start = System.currentTimeMillis();
		method.invoke(target);
		long end = System.currentTimeMillis();
		System.out.println("run time: " + (end - start) + " 毫秒");
	}

}