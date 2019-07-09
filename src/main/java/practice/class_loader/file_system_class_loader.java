package practice.class_loader;

import java.io.*;
import java.lang.reflect.Method;

/*
java.lang.ClassLoader类的常用方法中，
一般来说，自己开发的类加载器只需要覆写 findClass(String name)方法即可。
java.lang.ClassLoader类的方法 loadClass()封装了前面提到的代理模式的实现。
该方法会首先调用 findLoadedClass()方法来检查该类是否已经被加载过；
如果没有加载过的话，会调用父类加载器的 loadClass()方法来尝试加载该类；如果父类加载器无法加载该类的话，
就调用 findClass()方法来查找该类。因此，为了保证类加载器都正确实现代理模式，
在开发自己的类加载器时，最好不要覆写 loadClass()方法，而是覆写 findClass()方法。
类 FileSystemClassLoader的 findClass()方法首先根据类的全名在硬盘上查找类的字节代码文件（.class 文件），
然后读取该文件内容，最后通过 defineClass()方法来把这些字节代码转换成 java.lang.Class类的实例。
 */
public class file_system_class_loader extends ClassLoader {

	private String rootDir;

	public file_system_class_loader(String rootDir) {
		this.rootDir = rootDir;
	}

	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] classData = getClassData(name);
		if (classData == null) {
			throw new ClassNotFoundException();
		}
		else {
			return defineClass(name, classData, 0, classData.length);
		}
	}

	private byte[] getClassData(String className) {
		String path = classNameToPath(className);
		try {
			InputStream ins = new FileInputStream(path);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int bufferSize = 4096;
			byte[] buffer = new byte[bufferSize];
			int bytesNumRead = 0;
			while ((bytesNumRead = ins.read(buffer)) != -1) {
				baos.write(buffer, 0, bytesNumRead);
			}
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String classNameToPath(String className) {
		return rootDir + File.separatorChar
				+ className.replace('.', File.separatorChar) + ".class";
	}

	public static void main(String[] args) {
		String classDataRootPath = "/Users/lmc/work_space/industrious/target/classes";
		file_system_class_loader fscl1 = new file_system_class_loader(classDataRootPath);
		String className = "practice.class_loader.test_class";
		try {
			Class<?> clazz = fscl1.loadClass(className);
			Object obj = clazz.newInstance();
			Method method = clazz.getMethod("preDestroy");
			method.invoke(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
