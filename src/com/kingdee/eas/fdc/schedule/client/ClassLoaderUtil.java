/*
 * @(#)ClassLoaderUtil.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.fdc.schedule.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Properties;

import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

public class ClassLoaderUtil {
	public static final String RE_KEY = "re";
	public static final String C_KEY = "c";
	private static final String BEGIN_IDENTIFIER = "begin.identifier";
	public static boolean isNeedRestart = false;

	/**
	 * Ŀǰ���ֽ�����/lib/industry/eas-gantt-client.jar�뷿�ز���lib/industry/fdc_schedule-client.jar�г�ͻ��
	 * ��ʹ����Ӧģ��ʱ����Ҫ����ͻ�İ����Ϻ�׺bak����֤��ͻ�İ����ᱻ���ؽ�����
	 * @param isRE
	 *            ��Ϊ���ز��˵����ã���Ϊtrue������Ϊfalse
	 */
	public static void modifyClassLoader(boolean isRE) {
		if (isNeedRestart) {
			MsgBox.showInfo("�����޸���ɣ��������ͻ����Ա�����ʹ�õ�ǰ����ģ�顣����������Բ�������ʹ�ã���ɾ���ͻ���Ŀ¼�µ�/classloader/pkmap.lst�ļ������¿�ʼ��ǰ�������ɡ�");
			SysUtil.abort();
		}
		try {
			Properties config = getCRDProperties();
			String[] beginIds = config.getProperty(BEGIN_IDENTIFIER).split(";");
			String c_jarName = config.getProperty(C_KEY);
			String re_jarName = config.getProperty(RE_KEY);
			
			File file = getClassLoaderConfig();
			System.out.println(file + "; exists:" + file.exists());
			boolean isConflict = isRE ? checkUseAll(file, beginIds, c_jarName) : checkUseAll(file, beginIds, re_jarName);

			if (isConflict) {
				if (MsgBox.showConfirm2("ϵͳ��⵽����ǰʹ�õĽ���ģ�飨��ͬʱʹ���˷��ز��뽨���Ľ���ģ�飩���ڳ�ͻ,����Ҫ�޸�ϵͳ�����Ա�����ʹ�õ�ǰ����ģ�顣 ���ȷ��ϵͳ���Զ��޸����á�") == MsgBox.CANCEL) {
					SysUtil.abort();
					return;
				}
				File bakFile = new File(file.getParent() + File.separator + file.getName() + ".bak");
				if (bakFile.exists())
					bakFile.delete();
				renameTo(file, bakFile);
				
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(bakFile)));
				String line = null;
				while ((line = br.readLine()) != null) {
					if (isRE) {
						/*
						 * ��ʹ�÷��ز����ȣ��������İ��Ӻ�׺bak��ͬʱ�����ز���bakȥ��
						 */
						if (isBegin(line, beginIds)) {
							line = line.replace(c_jarName, c_jarName + "bak");
							line = line.replace(re_jarName + "bak", re_jarName);
						}
					} else {
						/*
						 * ��ʹ�ý������ȣ������ز��İ��Ӻ�׺bak��ͬʱ��������bakȥ��
						 */
						if (isBegin(line, beginIds)) {
							// com/kingdee/eas/fdc/schedule/client= ���ܸ�����bak��������涼�򲻿����Ҳ�����������
							line = line.replace(re_jarName, re_jarName + "bak");
							line = line.replace(c_jarName + "bak", c_jarName);
						}
					}
					if (line.split("=").length > 1) {
						bw.write(line);
						bw.write("\r\n");
					}
				}
				bw.close();
				br.close();
				isNeedRestart = true;
				MsgBox.showInfo("�����޸���ɣ��������ͻ����Ա�����ʹ�õ�ǰ����ģ�顣����������Բ�������ʹ�ã���ɾ���ͻ���Ŀ¼�µ�/classloader/pkmap.lst�ļ������¿�ʼ��ǰ�������ɡ�");
				SysUtil.abort();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void renameTo(File src, File dest) throws IOException {
		if (!src.renameTo(dest)) {
			System.out.println(src.getName() + " rename to " + dest.getName() + " failure.");
			FileInputStream in = new FileInputStream(src);
			FileOutputStream out = new FileOutputStream(dest);
			try {
				byte[] buffer = new byte[1024 * 128];
				int length = 0;
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
			} finally {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			}
			System.out.println(src.getName() + " delete :" + src.delete());
		}
	}

	/**
	 * ����Ƿ��г�ͻ��
	 * @param clFile
	 * @param beginIds ����ĸ�������ͷ�ģ���net/sourceforge/ganttproject;org/
	 * @param jarNames ��ͻ���İ���
	 * @return true �г�ͻ��false �޳�ͻ
	 * @throws IOException
	 */
	private static boolean checkUseAll2(File clFile, String jarName) throws IOException {
		if (!clFile.exists()) {
			return false;
		}
		System.out.println(jarName);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(clFile)));
		String line = null;
		while ((line = br.readLine()) != null) {
			// "com/kingdee/eas/fdc/schedule/client=" ��ͷ�Ĳ����г�ͻ
			if (line.contains(jarName) && !line.contains(jarName + "bak") && !line.startsWith("com/kingdee/eas/fdc/schedule/client=")) {
				System.out.println("has conflict");
				return true;
			}
		}
		System.out.println(" no conflict ");
		return false;
	}

	/**
	 * ����Ƿ��г�ͻ��
	 * @param clFile
	 * @param beginIds ����ĸ�������ͷ�ģ���net/sourceforge/ganttproject;org/
	 * @param jarNames ��ͻ���İ���
	 * @return true �г�ͻ��false �޳�ͻ
	 * @throws IOException
	 */
	private static boolean checkUseAll(File clFile, String[] beginIds, String jarName) throws IOException {
		if (!clFile.exists()) {
			return false;
		}
		System.out.println(jarName);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(clFile)));
		String line = null;
		while ((line = br.readLine()) != null) {
			// ֻ���beginIds��ͷ����
			if (isBegin(line, beginIds) && line.contains(jarName) && !line.contains(jarName + "bak")) {
				System.out.println("has conflict");
				return true;
			}
		}
		System.out.println(" no conflict ");
		return false;
	}

	private static File getClassLoaderConfig() {
		String easClientRoot = System.getProperty("easclient.root");
		String easClient = easClientRoot.substring(0, easClientRoot.indexOf("/deploy/client"));
		return new File(easClient + "/classloader/pkmap.lst");
	}

	/**
	 * ���Ƿ��Ա�ʶ�ַ�����ͷ
	 * @param line ��������
	 * @param beginIds ��ʶ�ַ��� 
	 * @return ��line��beginIds�е��ַ�����ͷ������true
	 */
	private static boolean isBegin(String line, String[] beginIds) {
		for (String beginId : beginIds) {
			if (line.startsWith(beginId)) {
				return true;
			}
		}
		return false;
	}

	private static Properties getCRDProperties() throws IOException {
		Class clazz = ClassLoaderUtil.class;
		String path = clazz.getPackage().getName().replace('.', '/');
		Properties prop = new Properties();
		URL url = clazz.getClassLoader().getResource(path + "/crd.classloader.properties");
		System.out.println(url);
		prop.load(url.openStream());
		prop.list(System.out);
		return prop;
	}
	
	public static void main(String[] args) {
		modifyClassLoader(false);
	}
}
