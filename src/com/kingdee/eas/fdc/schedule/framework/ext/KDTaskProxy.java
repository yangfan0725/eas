package com.kingdee.eas.fdc.schedule.framework.ext;

import java.awt.Color;
import java.io.IOException;



public class KDTaskProxy {
	/*private static KDTaskGeneratorClassLoader classLoader = 
		new KDTaskGeneratorClassLoader();
	private static Class secureKDTaskClass;
	public static void main(String[] args) throws ClassFormatError, InstantiationException, IllegalAccessException {
		KDTask task=generateSecureKDTask();
		task.setColor(Color.red);
		
		System.out.println(task.getColor());
	}
	public static KDTask generateSecureKDTask() throws ClassFormatError, 
		InstantiationException, IllegalAccessException {
		if (null == secureKDTaskClass) {            
			ClassReader cr;
			try {
				cr = new ClassReader(KDTask.class.getName());
				ClassWriter cw = new ClassWriter(true);
				ClassAdapter classAdapter = new AddMethodHandlerClassAdapter(cw);
				cr.accept(classAdapter, false);
				byte[] data = cw.toByteArray();
				secureKDTaskClass = classLoader.defineClassFromClassFile(
						"com.kingdee.eas.fdc.schedule.framework.ext.KDTask$EnhancedByASM",data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return (KDTask) secureKDTaskClass.newInstance();
	}

	private static class KDTaskGeneratorClassLoader extends ClassLoader {
		public Class defineClassFromClassFile(String className,
			byte[] classFile) throws ClassFormatError {
			return defineClass("com.kingdee.eas.fdc.schedule.framework.ext.KDTask$EnhancedByASM", classFile, 0, classFile.length);
		}
	}

}

class AddMethodHandlerClassAdapter extends ClassAdapter{
	private String enhancedSuperName;
	public AddMethodHandlerClassAdapter(ClassVisitor cv) {
		//Responsechain ����һ�� ClassVisitor���������ǽ����� ClassWriter��
		//�����д���������
		super(cv);
	}
	
	//��д visitMethod�����ʵ� "setXXX" ����ʱ��
	//�����Զ��� MethodVisitor��ʵ�ʸ�д��������
	public MethodVisitor visitMethod(final int access, final String name,
		final String desc, final String signature, final String[] exceptions) {
		MethodVisitor mv = cv.visitMethod(access, name, desc, signature,exceptions);
		MethodVisitor wrappedMv = mv;
		if (mv != null) {
			//���� "operation" ����
			if (name.startsWith("set")) { 
				//ʹ���Զ��� MethodVisitor��ʵ�ʸ�д��������
				wrappedMv = new AddMethodHandleAdapter(mv); 
			}  else if (name.equals("<init>")) {
				wrappedMv = new ChangeToChildConstructorMethodAdapter(mv,
						enhancedSuperName);
			}

		}
		return wrappedMv;
	}
	
	public void visit(final int version, final int access, final String name,
			final String signature, final String superName,
			final String[] interfaces) {
		String enhancedName = name + "$EnhancedByASM";  //�ı�������
		enhancedSuperName = name; //�ı丸�࣬�����ǡ�KDTask��
		super.visit(version, access, enhancedName, signature,
		enhancedSuperName, interfaces);
	}

}

class AddMethodHandleAdapter extends MethodAdapter {
	public AddMethodHandleAdapter(MethodVisitor mv) {
		super(mv);
	}

	public void visitCode() {
		System.out.println("set sxhong test");
		visitMethodInsn(Opcodes.INVOKESTATIC, "SecurityChecker",
			"checkSecurity", "()V");
	}
}   

class ChangeToChildConstructorMethodAdapter extends MethodAdapter {
	private String superClassName;

	public ChangeToChildConstructorMethodAdapter(MethodVisitor mv,
		String superClassName) {
		super(mv);
		this.superClassName = superClassName;
	}

	public void visitMethodInsn(int opcode, String owner, String name,
		String desc) {
		//���ø���Ĺ��캯��ʱ
		if (opcode == Opcodes.INVOKESPECIAL && name.equals("<init>")) { 
			owner = superClassName;
		}
		super.visitMethodInsn(opcode, owner, name, desc);//��д����ΪsuperClassName
	}*/
}


//��̬ASM ԭ��http://www.ibm.com/developerworks/cn/java/j-lo-asm30/
