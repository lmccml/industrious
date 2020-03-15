/**
 * 
 */
package book.Java系统性能优化源代码.asm;

import book.Java系统性能优化源代码.asm.om.asm.ASMBeanFactory;

/**
 *
 */
public class GetValueByAsm {
	private GetValueByAsm() {

	}

	private static final ASMBeanFactory asmBeanFactory;
	static {
		asmBeanFactory = new ASMBeanFactory();
		asmBeanFactory.setUsePropertyDescriptor(true);
	}

	public static Object value(Object bean, String attrName) {
		return asmBeanFactory.value(bean, attrName);
	}

}
