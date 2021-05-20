package com.kingdee.eas.fdc.invite.markesupplier.uitl;

import java.math.BigDecimal;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collection;

import javax.swing.ImageIcon;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.CoreBaseInfo;

public class CommonHelper {
	
	public static final BigDecimal BIGZERO = new BigDecimal("0.0");
    public static final int TRUE = 1;
    public static final int FALSE = 0;
    
    
    /**
	 * ��ȡ�ļ���Դ·�������ð�ť�Զ���ͼ�꣩ 
	 * java.net.URL url = ClassUtil.getResource("com/kingdee/eas/custom/images/search.gif", MaterialInfoBaseEditUI.class);
     * ImageIcon icon = new ImageIcon(url);
     * this.wbtnContract.setIcon(icon);
	 * @return boolean, ����true,Ascill�ַ�
	 */
    public static URL getResource(final String resourceName,
			final Class callingClass) {
		URL url = null;
		try {
			url = (URL) AccessController.doPrivileged(new PrivilegedAction() {
				public Object run() {
					return Thread.currentThread().getContextClassLoader()
							.getResource(resourceName);
				}
			});
			if (url == null) {
				url = (URL) AccessController
						.doPrivileged(new PrivilegedAction() {
							public Object run() {
								return CommonHelper.class.getClassLoader()
										.getResource(resourceName);
							}
						});

			}
			if (url == null) {
				url = (URL) AccessController
						.doPrivileged(new PrivilegedAction() {
							public Object run() {
								return callingClass.getClassLoader()
										.getResource(resourceName);
							}
						});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	/**
	 * �ж�һ���ַ���Ascill�ַ����������ַ����纺���գ������ַ���
	 * 
	 * @param char c, ��Ҫ�жϵ��ַ�
	 * @return boolean, ����true,Ascill�ַ�
	 */
    private static boolean isLetter(char c) {
		int k = 0x80;
		return c / k == 0 ? true : false;
	}
	/**
	 *�ж��Ƿ�Ϊ��
	 * */
	public static boolean isEmpty(Object param[])
    {
		return param == null || param.length == 0 || isEmpty(param[0]);
    }

    public static boolean isEmpty(String s)
    {
    	return s == null || s.trim().length() == 0;
    }

    public static boolean isEmpty(Object o)
    {
    	if(o instanceof String)
    		return o == null || o.toString().trim().length() == 0;
    	else
    		return o == null;
    }

    public static boolean isEmpty(Collection coll)
    {
    	return coll == null || coll.isEmpty();
    }

    public static boolean isEmpty(AbstractObjectCollection coll)
    {
    	return coll == null || coll.isEmpty();
    }

    public static boolean isZERO(BigDecimal o)
    {
    	return o == null || o.compareTo(FMConstants.ZERO) == 0;
    }

    public static boolean isEmpty(CoreBaseInfo info)
    {
    	return info == null || isEmpty(info.getId());
    }
    /**
	  * ������ת��ΪӢ�Ĵ�д��ʽ��
	  * @param x
	  * @return��Ӣ�Ĵ�д��ʽ
	  */
	 public static String parse(String x) {
	     int z = x.indexOf("."); // ȡС����λ��
	     String lstr = "", rstr = "";
	     if (z > -1) { // ���Ƿ���С��������У���ֱ�ȡ��ߺ��ұ�
	      lstr = x.substring(0, z);
	      rstr = x.substring(z + 1);
	      } else // �������ȫ��
	      lstr = x;

	     String lstrrev = reverse(lstr); // ����ߵ��ִ�ȡ��
	     String[] a = new String[5]; // ����5���ִ���������Ž�����������λһ����ִ�

	     switch (lstrrev.length() % 3) {
	      case 1 :
	       lstrrev += "00";
	       break;
	      case 2 :
	       lstrrev += "0";
	       break;
	      }
	     String lm = ""; // �������ת�������������
	     for (int i = 0; i < lstrrev.length() / 3; i++) {
	      a[i] = reverse(lstrrev.substring(3 * i, 3 * i + 3)); // ��ȡ��һ����λ
	      if (!a[i].equals("000")) { // �����������������1000000 = one million thousand only
	       if (i != 0){  
	         
	        lm = transThree(a[i]) + " " + parseMore(String.valueOf(i)) + " " + lm; // ��: thousand��million��billion
	       }
	       else if(Integer.parseInt(a[i]) <  100 && lstrrev.length() > 3){
	               //�жϸ�,ʮ,����λС��100 ��Ҫת�������ִ���1000ʱҪ��AND 
	           // ��:1001 Ӧ��Ϊ:ONE THOUSAND AND ONE ONLY Ҫ��AND ������ONE THOUSAND ONE ONLY
	             lm = "AND " + transThree(a[i]); 
	            }else{
	             lm = transThree(a[i]); // ��ֹi=0ʱ�� �ڶ�������ո�.
	            }
	       } else
	       lm += transThree(a[i]);
	      }

	     String xs = ""; // �������ת����С������
	     if (z > -1)
	      xs = "AND " + transTwo(rstr) + " CENT(S) "; // С�����ִ���ʱת��С��

	     return "SAY USD "+ lm.trim() + " " + xs ;//+ "ONLY";
	     }

	 private static String parseFirst(String s) {
	     String[] a =
	      new String[] { "", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE" };
	     return a[Integer.parseInt(s.substring(s.length() - 1))];
	     }

	 private static String parseTeen(String s) {
	     String[] a =
	      new String[] {
	       "TEN",
	       "ELEVEN",
	       "TWELEVE",
	       "THIRTEEN",
	       "FOURTEEN",
	       "FIFTEEN",
	       "SIXTEEN",
	       "SEVENTEEN",
	       "EIGHTEEN",
	       "NINETEEN" };
	     return a[Integer.parseInt(s) - 10];
	     }

	 private static String parseTen(String s) {
	     String[] a =
	      new String[] {
	       "TEN",
	       "TWENTY",
	       "THIRTY",
	       "FORTY",
	       "FIFTY",
	       "SIXTY",
	       "SEVENTY",
	       "EIGHTY",
	       "NINETY" };
	     return a[Integer.parseInt(s.substring(0, 1)) - 1];
	     }

	//  ��λ
	 private static String transTwo(String s) {
	     String value = "";
	     // �ж�λ��
	     if (s.length() > 2)
	      s = s.substring(0, 2);
	     else if (s.length() < 2)
	      s = "0" + s;

	     if (s.startsWith("0")) // 07 - seven �Ƿ�С�10
	      value = parseFirst(s);
	     else if (s.startsWith("1")) // 17 seventeen �Ƿ���10��20֮��
	      value = parseTeen(s);
	     else if (s.endsWith("0")) // �Ƿ���10��100֮����ܱ�10��������
	      value = parseTen(s);
	     else
	      value = parseTen(s) + " " + parseFirst(s);
	     return value;
	     }

	 private static String parseMore(String s) {
	     String[] a = new String[] { "", "THOUSAND", "MILLION", "BILLION" };
	     return a[Integer.parseInt(s)];
	     }

	//  ������λ����
	//  s.length = 3
	 private static String transThree(String s) {
	     String value = "";
	     if (s.startsWith("0")) // �Ƿ�С�100
	      value = transTwo(s.substring(1));
	     else if (s.substring(1).equals("00")) // �Ƿ�100����
	      value = parseFirst(s.substring(0, 1)) + " HUNDRED";
	     else
	      value = parseFirst(s.substring(0, 1)) + " HUNDRED AND " + transTwo(s.substring(1));
	     return value;
	     }

	 /**
	  * �����ֽ��з�ת
	  * @param s
	  * @return ��ת�������
	  */
	 private static String reverse(String s) {
	     char[] aChr = s.toCharArray();
	     StringBuffer tmp = new StringBuffer();
	     for (int i = aChr.length - 1; i >= 0; i--) {
	      tmp.append(aChr[i]);
	      }
	     return tmp.toString();
	 }
	 
	 /** 
		 * --------������ת�������Ľ��Ĵ�д��ʽ����ʾ��------------
		 *  ���� 123.45 --> Ҽ�۷�ʰ��Ԫ�������
		 *  TransRMB t2r = new TransRMB();
		 *  String s = t2r.cleanZero(t2r.splitNum(t2r.roundString("")));
		 */  

		
	    /** 
	     * �ж��û�����������Ƿ�Ϸ����û�ֻ���������������֣��������������ַ� 
	     * @param s String 
	     * @return ����û��������ݺϷ������� true�����򷵻� false 
	     */  
	    public boolean checkNum(String s) {  
	        // ����û�����������з������ַ�������Ϊ�Ƿ����ݣ����� false  
	        try {  
	            float f = Float.valueOf(s);  
	            // ��������С��������Ϊ�Ƿ����ݣ����� false  
	            if(f < 0) {  
	                System.out.println("�Ƿ����ݣ����飡");  
	                return false;  
	            }else {  
	                return true;  
	            }  
	        } catch (NumberFormatException e) {  
	            System.out.println("�Ƿ����ݣ����飡");  
	            return false;  
	        }     
	    }  
	      
	    /** 
	     * ���û����������С����Ϊ��ָ���������� numFormat() ���� 
	     * ������Ӧ�����Ľ���д��ʽ��ת�� 
	     * ע������������Ӧ���Ǿ��� roundString() ����������������������� 
	     * @param s String 
	     * @return ת���õ����Ľ���д��ʽ���ַ��� 
	     */  
	    public static String splitNum(String s) {  
	        // ���������ǿմ���������ؿմ�  
	        if("".equals(s)) {  
	            return "";  
	        }  
	        // ��С����Ϊ��ָ�����ַ���  
	        int index = s.indexOf(".");  
	        // ��ȡ��ת�����������������  
	        String intOnly = s.substring(0, index);  
	        String part1 = numFormat(1, intOnly);  
	        // ��ȡ��ת���������С������  
	        String smallOnly = s.substring(index + 1);  
	        String part2 = numFormat(2, smallOnly);  
	        // ��ת�����˵��������ֺ�С����������ƴ��һ���µ��ַ���  
	        String newS = part1 + part2;  
	        return newS;  
	    }  
	          
	    /** 
	     * �Դ��������������������� 
	     * ����ת���ɴ�д���
	     * @param s String ��������������Ǹ��� 
	     * @return ������������ֵ 
	     */  
	    public static String roundString(String s) {  
	        // ���������ǿմ���������ؿմ�  
	        if("".equals(s)) {  
	            return "";  
	        }  
	        // �������ת���� double ���ͣ���������������������  
	        double d = Double.parseDouble(s);  
	        // �˲���������С�������λ��  
	        d = (d * 100 + 0.5) / 100;  
	        // �� d ���и�ʽ��  
	        s = new java.text.DecimalFormat("##0.000").format(d);  
	        // ��С����Ϊ��ָ�����ַ���  
	        int index = s.indexOf(".");  
	        // ���������������  
	        String intOnly = s.substring(0, index);  
	        // �涨��ֵ����󳤶�ֻ�ܵ����ڵ�λ�����򷵻� "0"  
	        if(intOnly.length() > 13) {  
	            System.out.println("�������ݹ��󣡣������������13λ����");  
	            return "";  
	        }  
	        // �������С������  
	        String smallOnly = s.substring(index + 1);  
	        // ���С�����ִ�����λ��ֻ��ȡС�������λ  
	        if(smallOnly.length() > 2) {  
	            String roundSmall = smallOnly.substring(0, 2);  
	            // ���������ֺ��½�ȡ��С����������ƴ������ַ���  
	            s = intOnly + "." + roundSmall;  
	        }  
	        s = splitNum(s);
	        s = cleanZero(s);
	        return s;  
	    }  
	      
	    /** 
	     * �Ѵ������ת��Ϊ���Ľ���д��ʽ 
	     * @param flag int ��־λ��1 ��ʾת���������֣�0 ��ʾת��С������ 
	     * @param s String Ҫת�����ַ��� 
	     * @return ת���õĴ���λ�����Ľ���д��ʽ 
	     */  
	    public static String numFormat(int flag, String s) {  
	        int sLength = s.length();  
	        // ���Ҵ�д��ʽ  
	        String bigLetter[] = {"��", "Ҽ", "��", "��", "��", "��", "½", "��", "��", "��"};  
	        // ���ҵ�λ  
	        String unit[] = {"Ԫ", "ʰ", "��", "Ǫ", "��",   
	                // ʰ��λ��Ǫ��λ  
	                "ʰ", "��", "Ǫ",  
	                // ��λ������λ  
	                "��", "ʰ", "��", "Ǫ", "��"};  
	        String small[] = {"��", "��"};  
	        // �������ת��������ַ���  
	        String newS = "";  
	        // ��λ�滻Ϊ���Ĵ�д��ʽ  
	        for(int i = 0; i < sLength; i ++) {  
	            if(flag == 1) {  
	                // ת����������Ϊ���Ĵ�д��ʽ������λ��  
	                newS = newS + bigLetter[s.charAt(i) - 48] + unit[sLength - i - 1];  
	            } else if(flag == 2) {  
	                // ת��С�����֣�����λ��  
	                newS = newS + bigLetter[s.charAt(i) - 48] + small[sLength - i - 1];  
	            }  
	        }  
	        return newS;  
	    }  
	      
	    /** 
	     * ���Ѿ�ת���õ����Ľ���д��ʽ���ԸĽ������������ 
	     * �������������㣬������ַ�����ø��ӿɹ� 
	     * ע������������Ӧ���Ǿ��� splitNum() �������д�������� 
	     * ����Ӧ���Ѿ��������Ľ���д��ʽ��ʾ�� 
	     * @param s String �Ѿ�ת���õ��ַ��� 
	     * @return �Ľ�����ַ��� 
	     */  
	    public static String cleanZero(String s) {  
	        // ���������ǿմ���������ؿմ�  
	        if("".equals(s)) {  
	            return "";  
	        }  
	        // ����û���ʼ�����˺ܶ� 0 ȥ���ַ���ǰ������'��'��ʹ�俴��ȥ������ϰ��  
	        while(s.charAt(0) == '��') {  
	            // ���ַ����е� "��" ������Ӧ�ĵ�λȥ��  
	            s = s.substring(2);  
	            // ����û����������ʱ��ֻ������ 0����ֻ����һ�� "��"  
	            if(s.length() == 0) {  
	                return "��";  
	            }  
	        }  
	        // �ַ����д��ڶ��'��'��һ���ʱ��ֻ����һ��'��'����ʡ�Զ���ĵ�λ  
	        /* ���ڱ��˶��㷨���о�̫���ˣ�ֻ����4��������ʽȥת���ˣ���λ��Ϻ������... */  
	        String regex1[] = {"��Ǫ", "���", "��ʰ"};  
	        String regex2[] = {"����", "����", "��Ԫ"};  
	        String regex3[] = {"��", "��", "Ԫ"};  
	        String regex4[] = {"���", "���"};  
	        // ��һ��ת���� "��Ǫ", ���","��ʰ"���ַ����滻��һ��"��"  
	        for(int i = 0; i < 3; i ++) {  
	            s = s.replaceAll(regex1[i], "��");  
	        }  
	        // �ڶ���ת������ "����","����","��Ԫ"�����  
	        // "��","��","Ԫ"��Щ��λ��Щ����ǲ���ʡ�ģ���Ҫ��������  
	        for(int i = 0; i < 3; i ++) {  
	            // ����һ��ת�������п����кܶ�������һ��  
	            // Ҫ�Ѻܶ���ظ�������һ����  
	            s = s.replaceAll("������", "��");  
	            s = s.replaceAll("����", "��");  
	            s = s.replaceAll(regex2[i], regex3[i]);  
	        }  
	        // ������ת����"���","���"�ַ���ʡ��  
	        for(int i = 0; i < 2; i ++) {  
	            s = s.replaceAll(regex4[i], "");  
	        }  
	        // ��"��"��"��"֮��ȫ����"��"��ʱ�򣬺���"����"��λ��ֻ����һ��"��"  
	        s = s.replaceAll("����", "��");  
	        return s;  
	    } 
}
