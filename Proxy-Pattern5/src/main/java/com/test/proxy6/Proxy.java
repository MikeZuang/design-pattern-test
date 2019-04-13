package com.test.proxy6;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

@SuppressWarnings("resource")
public class Proxy {
	// 这个方法用来产生新的代理类，这个代理类的名称并不重要；并加载代理类返回代理类的对象
	public static Object newProxyInstance(Class<?> infce) throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// JDK1.6 Compiler API、CGlib、ASM(CGlib用到了ASM)
		// CGlib和ASM可以不使用java编译器而直接把源码生成字节码文件，因为java的字节码文件的格式也是公开的
		String src = "package com.test.proxy6;\n" +
				"\n" +
				"public class TankProxy implements " + infce.getName() + "{\n" +
				"\tMoveable tank;\n" +
				"\t\n" +
				"\tpublic TankProxy(" + infce.getName() + " moveable) {\n" +
				"\t\tthis.tank = moveable;\n" +
				"\t}\n" +
				"\t\n" +
				"\t@Override\n" +
				"\tpublic void move() {\n" +
				"\t\tlong start = System.currentTimeMillis();\n" +
				"\t\ttank.move();\n" +
				"\t\tlong end = System.currentTimeMillis();\n" +
				"\t\tSystem.out.println(\"run time: \" + (end - start) + \" 毫秒\");\n" +
				"\t}\n" +
				"}";
		//String fileName = System.getProperty("user.dir") + "/src/com/test/proxy6/TankProxy.java";
		File myFile = new File("d:/src/com/test/proxy6/");
		if (!myFile.exists()) { myFile.mkdirs();}
		String fileName = "d:/src/com/test/proxy6/TankProxy.java";
		File file = new File(fileName);
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(src);
		fileWriter.flush();
		fileWriter.close();
		// 编译源码
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		System.out.println(compiler);
		// 编译的过程有错误可以由diagnosticListener来监听和收集，locale和国际化相关
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		Iterable<? extends JavaFileObject> javaFileObjects = fileManager.getJavaFileObjects(fileName);
		CompilationTask task = compiler.getTask(null, fileManager, null, null, null, javaFileObjects);
		task.call();
		fileManager.close();
		// 加载字节码文件到内存并实例化，这种方法还可以从网上下载字节码文件并加载到内存
		//URL[] urls = new URL[] {new URL("file:/" + System.getProperty("user.dir") + "/src")};
		URL[] urls = new URL[] {new URL("file:/d:/src/")};
		URLClassLoader classLoader = new URLClassLoader(urls);
		Class<?> clazz = classLoader.loadClass("com.test.proxy6.TankProxy");
		System.out.println(clazz);
		// 实例化
		// clazz.newInstance(); 这行代码会调用这个类的无参构造方法，但我们没有无参的构造方法
		Constructor<?> constructor = clazz.getConstructor(Moveable.class);
		// 传入的参数就是被代理对象，代理对象会把它组合为成员变量
		Moveable tankProxy = (Moveable)constructor.newInstance(new Tank());
		return tankProxy;
	}
}
