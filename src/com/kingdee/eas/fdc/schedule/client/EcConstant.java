package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.kingdee.eas.fi.gl.GlUtils;

public class EcConstant {

	//��֯����
	public static final String CORP_CU = "00000000-0000-0000-0000-000000000000CCE7AED4";
	public static final String SYS_CU = "11111111-1111-1111-1111-111111111111CCE7AED4";
	
	//��Դ�ļ�����
	public static final String EcResource = "com.kingdee.eas.ec.basedata.client.EcResource";
	
	//���ȳ���
//	public static final int SCALE_QTY = 2;
//	public static final int SCALE_PRICE = 2;
//	public static final int SCALE_AMOUNT = 2;
//	public static final int SCALE_Rate = 2;
	
	public static final int MAX_LENGTH_TXT = 80;
	
	//���ֳ���
	public static final BigDecimal ZERO = new BigDecimal("0.0");

	/** BigDecimal Value : 1 */
	public static final BigDecimal ONE = new BigDecimal("1.0");

	/** BigDecimal Value : -1 */
	public static final BigDecimal _ONE = new BigDecimal("-1");

	/** BigDecimal Value : 10 */
	public static final BigDecimal TEN = new BigDecimal("10");

	/** BigDecimal Value : -10 */
	public static final BigDecimal _TEN = new BigDecimal("-10");

	/** BigDecimal Value : 100 �� */
	public static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

	/** BigDecimal Value : -100 �� */
	public static final BigDecimal _ONE_HUNDRED = new BigDecimal("-100");

	/** BigDecimal Value : 1000 ǧ */
	public static final BigDecimal ONE_THOUSAND = new BigDecimal("1000");

	/** BigDecimal Value : -1000 ǧ */
	public static final BigDecimal _ONE_THOUSAND = new BigDecimal("-1000");

	/** BigDecimal Value : 10000 �� */
	public static final BigDecimal TEN_THOUSAND = new BigDecimal("10000");

	/** BigDecimal Value : -10000 �� */
	public static final BigDecimal _TEN_THOUSAND = new BigDecimal("-10000");

	/** BigDecimal Value : 100000000 �� */
	public static final BigDecimal ONE_HUNDRED_MILLION = new BigDecimal(
			"100000000");

	/** BigDecimal Value : -100000000 �� */
	public static final BigDecimal _ONE_HUNDRED_MILLION = new BigDecimal(
			"-100000000");

	// public static final BigDecimal MAX_VALUE = new
	// BigDecimal("999999999999999999.9999999999");
	// public static final BigDecimal MIN_VALUE = new
	// BigDecimal("-999999999999999999.9999999999");

	// �����ϸ����1�ף�����С��ϸ������1�ף���������һ�£�����Ҫ����ƾ֤���ֶα��벻�ܴ��ڴ�������13λ��
	public static final BigDecimal MAX_VALUE = GlUtils.maxBigDecimal.divide(
			ONE_HUNDRED, BigDecimal.ROUND_HALF_UP); // 1000000000000

	public static final BigDecimal MIN_VALUE = GlUtils.minBigDecimal.divide(
			ONE_HUNDRED, BigDecimal.ROUND_HALF_UP); // -1000000000000

	// ���ϼ�����100�ף�����С�ϼ�������100�ף�MAX_VALUE/MIN_VALUE * 100 ��15λ��
	public final static BigDecimal MAX_TOTAL_VALUE = MAX_VALUE
			.multiply(ONE_HUNDRED);

	public final static BigDecimal MIN_TOTAL_VALUE = MIN_VALUE
			.multiply(ONE_HUNDRED);
	
//	 ���ϼ�����1000�ף�����С�ϼ�������1000�ף�MAX_VALUE/MIN_VALUE * 1000 ��16λ��
	public final static BigDecimal MAX_TOTAL_VALUE2 = MAX_VALUE
			.multiply(ONE_THOUSAND);

	public final static BigDecimal MIN_TOTAL_VALUE2 = MIN_VALUE
			.multiply(ONE_THOUSAND);

	public final static Color KDTABLE_TOTAL_BG_COLOR = new Color(0xF6F6B6);

	public final static Color KDTABLE_SUBTOTAL_BG_COLOR = new Color(0xF5F5E6);

	// ��ʽ����������
	public final static String KDTABLE_NUMBER_FTM = "%-.2n";

	// public final static String kdTablePercentFtm = "%{0.00%}f";
	public final static String KDTABLE_PERCENT_FTM = "%r{0.00}p";

	public final static String KDTABLE_DATE_FMT = "yyyy-MM-dd";
	public final static String KDTABLE_DATETIME_FMT = "yyyy-MM-dd HH:mm:ss";

	public final static String ACTUAL_DIGIT_FMT = "#,##0.###########";

	public static final String strDataFormat = "#,##0.00;-#,##0.00";

	/**
	 * KDFormattedTextFieldʹ�õ������Сֵ��Ŀǰ���ݿⶨ��Ľ���ֶ�Ϊ18λ���ؼ�����16λ��ʾ
	 * �������ݾ��ȵ���ƹ淶����ֶ�ͳһʹ�� (19,4)
	 */
	public static final BigDecimal MAX_DECIMAL = new BigDecimal(
			"999999999999999.9999");

	public static final BigDecimal MIN_DECIMAL = new BigDecimal(
			"-999999999999999.9999");

	public static final DateFormat FORMAT_DAY = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static final DateFormat FORMAT_TIME = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static final DateFormat FORMAT_MONTH = new SimpleDateFormat(
			"yyyy-MM");
	
	
	//��ʼ�����ݱ�־
	public static final String EC_INIT_ORGUNIT = "orgUnitId";
	public static final String EC_INIT_CURRENCY = "baseCurrencyInfo";
	public static final String EC_INIT_COMPANY= "company";
	public static final String EC_INIT_CURRENTPERIOD= "currentPeriod";
	
	//�������ó���
	/**�������� EC1 **/
	//����ڼ�����
	public static final String EC101_PERIODTYPE = "EC101_PeriodType";
	public static final String EC102_PERIODTYPE = "EC102_PeriodType";
	
	//����
	public static final String EC102_AMOUT = "EC102_AMOUT";
	public static final String EC103_QTY = "EC103_QTY";
	public static final String EC104_PRICE = "EC104_PRICE";
	
	/**���� EC20**/
	//�Ƿ�����ʩ��������
	public static final String EC201_TASTBILL = "EC201_TASTBILL";
	//���̹鼯��Ҫ�Ӳ������ƣ����Ƿ������������߰�������������ĸ�����
	public static final String EC202_FILL = "EC202_FILL";
	//������ȼƻ���������Դ
	public static final String EC203_COUNT = "EC203_COUNT";
	
	/**��ͬ EC30**/
	//��ͬ���ս��㵥����ʱ�жϱ䶯����Ϊ"�ݶ����"���嵥��������
	public static final String EC301_LSTSETTLE = "EC301_LSTSETTLE";
	//��ͬ���㸶���Ƿ��߷�Ʊ����
	public static final String EC301_INVOICE_NEED="EC301_INVOICE_NEED";
	/**��Ӧ�� EC40**/
	//��Ϊ�ϸ�Ӧ�̺ϸ���͵ȼ�ֵ
	public static final String EC401_SUPPLIER_LIMIT="EC401_SUPPLIER_LIMIT";
	public static final String EC402_SUPPLIER_ESTIMATERESULT="EC402_SUPPLIER_ESTIMATERESULT";
	
	/**�б� EC50**/
	
	/**�ɱ� EC60**/
	//�Ƿ����óɱ�ϵͳ
	public static final String EC0601_COSTSTART = "EC0601_COSTSTART";
	//��ʼ�������Ƿ��ϸ����
	public static final String EC0602_STRICTINIT = "EC0602_STRICTINIT";	
	//�Ƿ�ʹ��ָ�����Դ
	public static final String EC0603_ISUSECOSTINDEX = "EC0603_ISUSECOSTINDEX";
	//��ĩ���۳ɱ�������Դ
	public static final String EC0604_COSTPRICESOURCE = "EC0604_COSTPRICESOURCE";
	//�ɱ���Ŀ�Ƿ����¼�븺��
	public static final String EC0605_COSTACCOUNTCANNEGATIVE = "EC0605_COSTACCOUNTCANNEGATIVE";
	
	//������ʾ�������
	public static final int MAX_ERRTIP=100;
}
