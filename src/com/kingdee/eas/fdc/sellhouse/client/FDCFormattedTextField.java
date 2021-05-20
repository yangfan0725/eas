package com.kingdee.eas.fdc.sellhouse.client;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.text.NumberFormatter;

import com.kingdee.bos.ctrl.common.util.StringUtil;
import com.kingdee.bos.ctrl.swing.DecimalFormatEx;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.NumberFormatterEx;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.ctrl.swing.util.CtrlFormatUtilities;

public class FDCFormattedTextField extends KDFormattedTextField {

	public FDCFormattedTextField() {
	}

	public FDCFormattedTextField(AbstractFormatterFactory factory) {
		super(factory);
	}

	public FDCFormattedTextField(Object value) {
		super(value);
	}

	public FDCFormattedTextField(int dataType) {
		super(dataType);
	}

	//���ش����༭formatter���޸�formatter��ʵ��ΪFDCNumberFormatterEx
	protected NumberFormatter createEditFormatter(int newDataType) {
		DecimalFormatEx sysFormat = CtrlFormatUtilities.getEditNumberFormat();
		DecimalFormatEx format = null;
		if (sysFormat == null)
			format = new DecimalFormatEx();
		else
			format = new DecimalFormatEx(sysFormat.toPattern());

		FDCNumberFormatterEx formatter = new FDCNumberFormatterEx();

		// ��DecimalFormatEx�ܹ����Ʊ༭״̬, by ��־��, 20060810
		format.setFormattedTextField(this);
		// 0 ����
		formatter.setDecimalPrecision(getPrecision());
		format.setMaximumFractionDigits(getPrecision());

		// 1 ����ԭ��
		formatter.setRoundingMode(this.getRoundingMode());
		
		// 2 ǧ��λ��ʾ
		format.setGroupingUsed(false);

		// �Ƿ�ٷ��� ��Ŀǰ��֧�ֱ༭״̬ʱ���ðٷ�����ʾ��
		// 3�Ƿ�֧�ֿ�ֵ
		formatter.setSupportedEmpty(isSupportedEmpty());

		// 4 ��֤����
		if (getDataVerifierType() == INPUTING_VERIFIER) {
			formatter.setAllowsInvalid(false);
			formatter.setSupportedEmpty(false);
			format.setMinimumFractionDigits(getPrecision());
		} else {
			formatter.setAllowsInvalid(true);
		}

		// 5 �����쳣����

		// 6 �Ƿ�ȥ�㴦��
		formatter.setRemoveingZero(isRemoveingZeroInEdit());
		if (!isRemoveingZeroInEdit()) {
			format.setMinimumFractionDigits(getPrecision());
		}

		// 7 ���ֵ����
		formatter.setMaximum((Comparable) getMaximumValue());

		// 8 ��Сֵ����
		formatter.setMinimum((Comparable) getMinimumValue());

		// 9 ÿ����Ч����ʱ,�Ƿ�����ύ.
		formatter.setCommitsOnValidEdit(isCommitsOnValidEdit());

		// 10 �Ƿ�֧�ֿ�ֵ
		formatter.setSupportedEmpty(isSupportedEmpty());

		exConfigEditFormat(format);

		formatter.setFormat(format);
		return formatter;
	}

	//��չNumberFormatterEx�࣬��Ҫ��Ϊ����дstringToValue������ԭ�еķ���û�ж�ȡ���뷽ʽ
	public class FDCNumberFormatterEx extends NumberFormatterEx {
		/**
		 * �Ƿ����㴦��
		 */
		private boolean isRemoveingZero = false;

		/** ���� */
		private int precision = 0;

		/** �������� */
		private int roundingMode = BigDecimal.ROUND_HALF_UP;

		/** �Ƿ�֧�ֿ�ֵ */
		private boolean isSupportedEmpty = false;

		private KDFormattedTextField ftf = null;

		public FDCNumberFormatterEx() {
			super();
		}

		public FDCNumberFormatterEx(NumberFormat format) {
			super(format);
		}

		public Object stringToValue(String text) throws ParseException {
			ParseException pEx = null;
			try {
				String newValue = text;

				char decimalSeparator = ((DecimalFormatEx) getFormat())
						.getDecimalFormatSymbols().getDecimalSeparator();// С����
				if (isRemoveingZero() && text != null && text.length() > 0
						&& text.indexOf(String.valueOf(decimalSeparator)) != -1) {
					newValue = new String(this.removeTrailZero(new StringBuffer(text)));// ʹ�ù��ʻ��˵ķ���
				}

				// added by ��־�� 2006-01-17, �����жϵ�ʹ��������֤ʱ��ֱ�ӽص��ַ���������Ҫ����������
				if (ftf != null
						&& ftf instanceof KDFormattedTextField
						&& ftf.getDataVerifierType() == KDFormattedTextField.INPUTING_VERIFIER) {
					newValue = repairingStrByPrecision(newValue,
							getDecimalPrecision());
				}

				Object obj = stringToValue2(newValue);
				try {
					return new BigDecimal(obj.toString()).setScale(
							getDecimalPrecision(), this.getRoundingMode());
				} catch (Exception e) {
					throw new ParseException("can not new a BigDecimal", 0);
				}
			} catch (ParseException ex) {
				pEx = ex;
			}
			// ��֧��nullʱ�����ӳ��쳣����nullֵ��ֵ��
			if (isSupportedEmpty() && text != null && text.trim().length() == 0) {
				return null;
			} else {
				// �����ӳ��쳣��һ����ֹnull���������û������쳣��
				throw pEx;
			}
		}

		/**
		 * ���վ��ȸ��ַ���������Чλ�Ĳ���. ���ַ������аٷ������򲻽��д������磺Դ2.123 ������5 �������2.12300
		 * 
		 * @param str
		 *            Դ�ַ�����
		 * @param precision
		 *            ����ֵ��
		 * @return ������Чλ���ַ�����
		 */
		private String repairingStrByPrecision(String str, int precision) {
			char decimalSeparator = ((DecimalFormatEx) getFormat())
					.getDecimalFormatSymbols().getDecimalSeparator();// С����
			char zero = ((DecimalFormatEx) getFormat())
					.getDecimalFormatSymbols().getZeroDigit();// ��
			if (str != null && str.length() > 0
					&& str.indexOf(String.valueOf(decimalSeparator)) != -1
					&& !StringUtil.isPercentStr(str)) {
				StringBuffer sb = new StringBuffer(20);
				// ����ֻ���� 0 - 10 ֮�䡣
				precision = precision < CtrlCommonConstant.PRECISION_MIN ? CtrlCommonConstant.PRECISION_MIN
						: precision;
				// ע�͵�����Ĳ�������Ϊ��BigDecimal�������KDFormattedTextFieldû�ж��侫�������ƣ�
				// �˴�Ҳ��Ӧ�����ƣ���������value��text��һ�µ����
				// precision = precision > CtrlCommonConstant.PRECISION_MAX
				// ? CtrlCommonConstant.PRECISION_MAX
				// : precision;

				int dotIndex = str
						.lastIndexOf(String.valueOf(decimalSeparator));
				// С����ǰһ����,������ 234.056 �� 234
				String preStr = str.substring(0, dotIndex);

				// С�����һ����,������ 234.056 �� 056
				String nextStr = str.substring(dotIndex + 1);

				int nextStrLen = nextStr.length();

				if (nextStrLen == precision) {
					// 1 �ַ�������Чλ �պõ��ھ��ȷ�Χ
					return str;
				} else if (nextStrLen > precision) {
					// �ַ�������Чλ ���� ���ȷ�Χ��Ӧ�ý�ȡ�������ĸ
					nextStr = nextStr.substring(0, precision);
					if (nextStr.length() > 0)
						str = sb.append(preStr).append(decimalSeparator)
								.append(nextStr).toString();
					else
						str = sb.append(preStr).toString();
				} else {
					// �ַ�������Чλ С�� ���ȷ�Χ��Ӧ�ò�����
					int interval = precision - nextStrLen;
					sb.append(str);
					for (int i = 0; i < interval; i++) {
						sb.append(zero);
					}
					str = sb.toString();
				}
			}
			return str;
		}

		private Object convertValueToValueClass2(Object value, Class valueClass) {
			if (valueClass != null && (value instanceof Number)) {
				if (valueClass == Integer.class) {
					return new Integer(((Number) value).intValue());
				} else if (valueClass == Long.class) {
					return new Long(((Number) value).longValue());
				} else if (valueClass == Float.class) {
					return new Float(((Number) value).floatValue());
				} else if (valueClass == Double.class) {
					return new Double(((Number) value).doubleValue());
				} else if (valueClass == Byte.class) {
					return new Byte(((Number) value).byteValue());
				} else if (valueClass == Short.class) {
					return new Short(((Number) value).shortValue());
				}
			}
			return value;
		}

		/**
		 * �Ƴ���β�������
		 * 
		 * @param sb
		 * @return
		 */
		private StringBuffer removeTrailZero(StringBuffer sb) {
			int dotIndex = sb.indexOf(".");
			if (dotIndex != -1) {// ����С����ʱ
				int index = findNotZeroDigitLastIndex(sb, dotIndex);
				if (index != -1) // С������ҵ����������ַ�
				{
					sb = sb.delete(index + 1, sb.length());
				} else
				// С������Ҳ������������ַ�
				{
					sb = sb.delete(dotIndex, sb.length());
				}
			}
			return sb;
		}

		/**
		 * ����С��������һ����Ϊ�������λ��
		 * 
		 * @param sb
		 * @param dotIndex
		 * @return -1 �����Ҳ�������������ַ�
		 */
		private int findNotZeroDigitLastIndex(StringBuffer sb, int dotIndex) {
			int lastIndex = -1;
			for (int i = dotIndex + 1; i < sb.length(); i++) {
				if (isNotZeroDigits(sb.charAt(i))) {
					lastIndex = i;
				}
			}
			return lastIndex;
		}

		/**
		 * �����ַ��Ƿ�Ϊ����������ַ�
		 * 
		 * @param c
		 * @return
		 */
		private boolean isNotZeroDigits(char c) {
			if (c == '1' || c == '2' || c == '3' || c == '4' || c == '5'
					|| c == '6' || c == '7' || c == '8' || c == '9') {
				return true;
			}
			return false;
		}

		/**
		 * �Ƿ����㴦��
		 */
		public boolean isRemoveingZero() {
			return isRemoveingZero;
		}

		/**
		 * �����Ƿ����㴦��
		 */
		public void setRemoveingZero(boolean newValue) {
			isRemoveingZero = newValue;
		}

		/**
		 * ����
		 */
		public void setDecimalPrecision(int newValue) {
			precision = newValue;
		}

		/** ���� */
		public int getDecimalPrecision() {
			return precision;
		}

		/** ���뷽ʽ */
		public void setRoundingMode(int newValue) {
			roundingMode = newValue;
		}

		/** ���뷽ʽ */
		public int getRoundingMode() {
			return roundingMode;
		}

		/***/
		public void setSupportedEmpty(boolean newValue) {
			isSupportedEmpty = newValue;
		}

		/***/
		public boolean isSupportedEmpty() {
			return isSupportedEmpty;
		}
	}

}
