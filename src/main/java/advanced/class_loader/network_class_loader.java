package advanced.class_loader;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

public class network_class_loader extends ClassLoader {

	private String rootUrl;

	public network_class_loader(String rootUrl) {
		this.rootUrl = rootUrl;
	}

	@Override
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
			URL url = new URL(path);
			InputStream ins = url.openStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int bufferSize = 4096;
			byte[] buffer = new byte[bufferSize];
			int bytesNumRead = 0;
			while ((bytesNumRead = ins.read(buffer)) != -1) {
				baos.write(buffer, 0, bytesNumRead);
			}
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String classNameToPath(String className) {
		return rootUrl + "/"
				+ className.replace('.', '/') + ".class";
	}

	public static void main(String[] args) {
		String url = "http://localhost";
		network_class_loader ncl = new network_class_loader(url);
		//需要带上包名
		String basicClassName = "practice.advanced.class_loader.test_class";
		try {
			Class<?> clazz = ncl.loadClass(basicClassName);
			test_class test_class = (test_class) clazz.newInstance();
			System.out.println(test_class.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
