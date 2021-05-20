/*
 * @(#)FDCContants.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.fdc.basedata;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.kingdee.eas.fi.gl.GlUtils;

/**
 * 
 * ����:���ز�������
 * 
 * @author liupd date:2006-10-10
 *         <p>
 * @version EAS5.1.3
 */
public class FDCConstants {
	


	/** ��Ӧ�� F7 Query */
	public static final String SUPPLIER_F7_QUERY = "com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryWithDefaultStandard";

	/** Ŀ��ɱ���ָ��ID */
	public static final String AIM_COST_PERCENT_ID = "qHQt0wEMEADgAAaOoKgTuzW0boA=";

	/** �������ָ��id */
	public static final String BUILD_AREA_ID = "qHQt0wEMEADgAAaBwKgTuzW0boA=";

	/** ������� ָ��ID */
	public static final String SALE_AREA_ID = "qHQt0wEMEADgAAaHwKgTuzW0boA=";
	
	/** ��Ʊ�� */
	public static final String CHANGE_TYPE_DESIGN = "FQsluAENEADgAAr2wKgTu6wXT3w=";
	
	/** �ֳ�ǩ֤ */
	public static final String CHANGE_TYPE_SIGN = "FQsluAENEADgAAr5wKgTu6wXT3w=";
	
	/** �������� */
	public static final String CHANGE_TYPE_OTHER = "FQsluAENEADgAArwwKgTu6wXT3w=";
	
	//���Գ���
	/** Locale - L1 */
	public static final Locale L1 = new Locale("L1");
	/** Locale - L2 */
	public static final Locale L2 = new Locale("L2");
	/** Locale - L3 */
	public static final Locale L3 = new Locale("L3");
	
	//int ����
	//Ĭ�ϱұ𾫶�
	public static final int DEFAULT_CURRENCY_PREC = 2;
	
	public  static final int  DEFAULT_RATE_PRECISION = 5;
	
    public  static final int  ONE_SECOND = 1000;
    
    public static final int  ONE_MINUTE = 60*ONE_SECOND;
    
    public static final int  ONE_HOUR   = 60*ONE_MINUTE;
    
    public static final long ONE_DAY    = 24*ONE_HOUR;
    
    public static final long ONE_WEEK   = 7*ONE_DAY;
    
    final static public int MAX_YEAR = 2999;

	final public static Integer TRUE = new Integer(1);
	
	final public static Integer FALSE = new Integer(0);
	
	public static final int  MsgMaxLength=2000;
	
	/** �ۺϱ�λ��ID */
	public static final String GENERAL_LOCAL_CURRENCY  = "11111111-1111-1111-1111-111111111111DEB58FDC";
	/** �ۺϱ���ID */
	public static final String GENERAL_REPORT_CURRENCY = "22222222-2222-2222-2222-222222222222DEB58FDC";

	public static final String datafilter="datafilter";    

	//BigDecimal ����
    final static public BigDecimal FIRST_VERSION = new BigDecimal("0.9");
    
    final static public BigDecimal CURR_VERSION = new BigDecimal("0");
    
	public static final BigDecimal B0 = new BigDecimal("0");

	public static final BigDecimal B100 = new BigDecimal("100");
	
	public static final BigDecimal ZERO = new BigDecimal("0");
	
    public  static final Date MAX_DATE = new Date(1099,11,30);

    /** BigDecimal Value : 1 */
	public static final BigDecimal ONE = new BigDecimal("1");
	
	/** BigDecimal Value : -1 */
	public static final BigDecimal _ONE = new BigDecimal("-1");
	
	/** BigDecimal Value : 10 */
	public static final BigDecimal 	TEN = new BigDecimal("10");
	
	/** BigDecimal Value : -10 */
	public static final BigDecimal 	_TEN = new BigDecimal("-10");
	
	/** BigDecimal Value : 100 �� */
	public static final BigDecimal 	ONE_HUNDRED  = new BigDecimal("100");
	
	/** BigDecimal Value : -100 �� */
	public static final BigDecimal 	_ONE_HUNDRED  = new BigDecimal("-100");
	
	/** BigDecimal Value : 1000 ǧ */
	public static final BigDecimal 	 ONE_THOUSAND = new BigDecimal("1000");
	
	/** BigDecimal Value : -1000 ǧ */
	public static final BigDecimal 	 _ONE_THOUSAND = new BigDecimal("-1000");
	
	/** BigDecimal Value : 10000 �� */
	public static final BigDecimal 	TEN_THOUSAND = new BigDecimal("10000");
	
	/** BigDecimal Value : -10000 �� */
	public static final BigDecimal 	_TEN_THOUSAND = new BigDecimal("-10000");
	
	/** BigDecimal Value : 100000000 �� */
	public static final BigDecimal 	ONE_HUNDRED_MILLION  = new BigDecimal("100000000");
	
	/** BigDecimal Value : -100000000 �� */
	public static final BigDecimal 	_ONE_HUNDRED_MILLION  = new BigDecimal("-100000000");
	
	
	//�����ϸ����1�ף�����С��ϸ������1�ף���������һ�£�����Ҫ����ƾ֤���ֶα��벻�ܴ��ڴ�������13λ��
	public static final BigDecimal MAX_VALUE = GlUtils.maxBigDecimal;	//1000000000000
	
	public static final BigDecimal MIN_VALUE = GlUtils.minBigDecimal; //-1000000000000
	
	//���ϼ�����100�ף�����С�ϼ�������100�ף�MAX_VALUE/MIN_VALUE * 100 ��15λ��
	public final static BigDecimal MAX_TOTAL_VALUE = MAX_VALUE.multiply(ONE_HUNDRED);
	
	public final static BigDecimal MIN_TOTAL_VALUE  = MIN_VALUE.multiply(ONE_HUNDRED);

	public static final String TIME_FORMAT = "yyyy-mm-dd hh:mm:ss";
	
    public static final DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
    
    public static final DateFormat FORMAT_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final DateFormat FORMAT_MONTH = new SimpleDateFormat("yyyy-MM");


    public static final DateFormat FORMAT_QUARTER = new DateFormat(){
        public  StringBuffer format(Date date, StringBuffer toAppendTo,
            FieldPosition fieldPosition)
	    {
	        Calendar cal = new GregorianCalendar();
	        cal.setTime(date);
	        int year;
	        int quarter;
	        StringBuffer sb = new StringBuffer(8);
	        year=cal.get(Calendar.YEAR);
	        quarter=cal.get(Calendar.MONTH)/3+1;
	        
	        return  sb.append(year).append(" - ").append(quarter);
	    }
	
	    public Date parse(String source, ParsePosition pos) {
	        // TODO �Զ����ɷ������
	        return null;
	    }
    };
    public static final DateFormat FORMAT_YEAR = new SimpleDateFormat("yyyy");
	public  static final String UI_ISCOPY = "isCopy";
	public static final String EXT_ISDATATYPE = "isDataType";
	public static final String EXT_ISACTIONDATE = "isActionDate";
	public static final String EXT_ISFILTER = "isCommonQueryFilter";
	public  static final int INT_ZERO = 0;
	
	
	public static final int DEFAULT_ROUND = BigDecimal.ROUND_HALF_UP;
	public static final String FM_CUSTOMER_KEY = "fmcustomer";
	public static final int PRE_2 = 2;
	 
	//�������� Param Constant  ��������ο�FDC001_STARTMG		
	/*******1 ���������µĲ���****FDC101**/	
	//ָ������Ƿ���ʾ���ָ�ꡡby sxhong 2008-02-58 16:26:41
	public static final String FDC_PARAM_PROJECTINDEX = "FDC006_PROJECTINDEX";
	//�����Ƿ���ʾ�ɱ��ϼ���
	public static final String FDC_PARAM_TOTALCOST = "FDC007_TOTALCOST";	
	//�ɱ�����ͳ�ư����ǳɱ����Ŀ��by yx 2008-03-24
	public static final String FDC_PARAM_INCLUDENOCOSTACCOUNT = "FDC008_INCLUDENOCOSTACCOUNT";
	
	//ȫ��Ŀ��̬�ɱ����еġ���ʵ�ֳɱ����ֶ����ء�by yx 2008-11-03
	public static final String FDC_PARAM_HIDEREALIZEDCOST = "FDC009_HIDEREALIZEDCOST";
	//���γɱ�
	public static final String FDC_PARAM_COSTACCOUNTSMANDATE = "FDC010_CostAccountsMandate";
	
	//������������Ϣ��ӡʱ��������ͬ�⣬��ͬ��֮ǰ�����ݶ�����ӡ
	public static final String FDC_PARAM_WFISPASSISFALSENOTPRINT = "FDC011_WFisPassIsFalseNotPrint";
	
	/**
	 * ������������Ϣ��ӡʱ�������ظ��������ˣ�ֻ��ӡ���һ������ 
	 */
	public static final String FDC_PARAM_WFIsDuplicateNotPrint = "FDC012_WFIsDuplicateNotPrint"; //added by owen_wen 2010-10-12
	
	/**
	 * ������Ŀ������ʱ���б��Ƿ��տ���˳������
	 */
	public static final String FDC_PARAM_PROJECTTREESORTBYSORTNO = "FDC013_PROJECTTREESORTBYSORTNO";
	
	
	/*******2 ��ͬ�����µĲ���***FDC201***/
	//�Ƿ�����Ԥ��
	public static final String FDC_PARAM_STARTMG = "FDC001_STARTMG";
	//�����˿���ѡ��ȫ������Ա
	public static final String FDC_PARAM_SELECTPERSON = "FDC002_SELECTPERSON";
	//��ͬ�ɽ��ж�ν���
	public static final String FDC_PARAM_MORESETTER = "FDC004_MORESETTER";
	/**
	 * ��ͬ�ɽ��ж�ν����£���ִͬ����������깤δ�����Ƿ񰴡��ۼ��깤������-�Ѹ����ʽ����
	 */
	public static final String FDC_PARAM_MORESETTER_ALLNOTPAID = "FDC005_MORESETTER_ALLNOTPAID";
	//���Ų������ٴι鵵ʱ���º�ͬ���
	public static final String FDC_PARAM_UPDATECONTRACTNO = "FDC007_UPDATECONTRACTNO";
	//��ͬ����ǰ���в��
	public static final String FDC_PARAM_SPLITBFAUDIT = "FDC015_SPLITBFAUDIT";
	//����ύʱ��鸶��Ѿ�������
	public static final String FDC_PARAM_CHECKPAYMENTALLSPLIT = "FDC016_CHECKPAYMENTALLSPLIT";
	//�����ֿ�Ŀ�Ľ���ܶ�Ӧ��Ŀ�Ѳ�ֳɱ���������
	public static final String FDC_PARAM_LIMITCOST = "FDC017_LIMITCOST";
	//��ͬ�������ͬ����Ĳ���Ƿ��Զ����ú�ͬ��ֵĳɱ���Ŀ����ֱ���
	public static final String FDC_PARAM_IMPORTCONSPLIT = "FDC018_IMPORTCONSPLIT";
	//���������������Ƿ�����ʹ�ð��������
	public static final String FDC_PARAM_SPLITBASEONPROP ="FDC019_SPLITBASEONPROP";
	//�����ͬ����ѡ���ύ״̬������ͬ
	public static final String FDC_PARAM_SELECTSUPPLY = "FDC080_SELECTSUPPLY";
	
	
	//�������뵥������������ɸ�����
	public static final String FDC_PARAM_OUTPAYAMOUNT = "FDC071_OUTPAYAMOUNT";
	//���뵥���ȿ������Զ�Ϊ100%
	public static final String FDC_PARAM_PAYPROGRESS = "FDC072_PAYPROGRESS";
	//�������뵥����ǰ����¼���ͬ����ƻ�
	public static final String FDC_PARAM_CONPAYPLAN = "FDC073_CONPAYPLAN";
	//������ά����ͬ
	public static final String FDC_PARAM_CREATORATTACHMENT = "FDC211_CREATORATTACHMENT";
	//��������ʾ���в��
	public static final String FDC_PARAM_SPLITAFTERAUDIT = "FDC212_SPLITAFTERAUDIT";
	//����Ŀ��ֵĹ�����ĿҲ�ɽ��к�ͬ���㼰���
	public static final String FDC_PARAM_CROSSPROJECTSPLIT = "FDC215_CROSSPROJECTSPLIT";
	//δ�����ͬ��ʵ���������ʵ�ֲ�ֵʱ�Ƿ��ϸ����  by jian_wen 2009.12.15
	public static final String FDC_PARAM_ISCONTROLPAYMENT="FDC225_ISCONTROLPAYMENT"; 
	
	// ������Ŀͬ�������������е���Ŀʱ�Ƿ���ʾ��֯����
	public static final String FDC_PARAM_ISSHOWORGTOPROJECTNUM = "FDC1001_ISShowOrgToProjectNum";
	
	/**
	 * ��ͬ����ʱ�Ƿ�Ҫ��������������
	 */
	public static final String FDC_PARAM_CONTRACTCHANGESETTLEMENTMUSTCOMPLETE = "FDC226_CONTRACTCHANGESETTLEMENTMUSTCOMPLETE";
	
	/**
	 * ��ʾ��ִͬ�������ļƻ���
	 */
	public static final String FDC_PARAM_CONTRACTEXEC = "FDC074_CONTRACTEXEC";
	/**
	 * ����������ƾ֤�Ĳ���
	 */
	public static final String FDC_PARAM_SETTLEMENTCOSTSPLIT = "FDC075_SETTLEMENTCOSTSPLIT";
	//��ͬ������Ŀ
	public static final String FDC_PARAM_CHARGETYPE = "FDC075_CHARGETYPE";
	//����ύʱ���Ƿ����ͬδ��ȫ���
	public static final String FDC_PARAM_CHECKALLSPLIT = "FDC078_CHECKALLSPLIT";
	//�������ƾ֤ʱ��鸶��Ƿ��Ѳ��
	public static final String FDC_PARAM_CHECKPAYMENTSPLIT = "FDC079_CHECKPAYMENTSPLIT";
	//�����ͷ���������ͬһ���û�
	public static final String FDC_PARAM_AUDITORMUSTBETHESAMEUSER = "FDC011_AUDITORMUSTBETHESAMEUSER";
	//��Ʊ�����������������ʩ����λ���Ƿ���� 
	public static final String FDC_PARAM_ISREQUIREDFORASKANDCONSTRCTION="FDC219_ISREQUIREDFORASKANDCONSTRCTION";
	//�Ƿ����ñ���·�  
	public static final String FDC_PARAM_ALLOWDISPATCH="FDC220_ALLOWDISPATCH";
	//���ָ��Ƿ��Զ���Ϊ����ǩ֤��  
	public static final String FDC_PARAM_AUTOCHANGETOPROJECTVISA="FDC221_AUTOCHANGETOPROJECTVISA";
	//ָ��Ƿ�������������������������
	public static final String FDC_PARAM_GENERATEAFTERAUDIT = "FDC222_GENERATEAFTERAUDIT";
	//�������뵥����ֱ�Ӹ����޽𣬶�����Ҫ�������
	public static final String FDC_PARAM_KEEPBEFORESETTLEMENT = "FDC600_KEEPBEFORESETTLEMENT";
	
	/*******3 ��������µĲ���****FDC301**/
	//���óɱ�����һ�廯--->���óɱ��½�
	public static final String FDC_PARAM_INCORPORATION= "FDC003_INCORPORATION";
	//���óɱ�����һ�廯
	public static final String FDC_PARAM_FINACIAL = "FDC010_FINACIAL";
	//���������Է�Ʊ���Ϊ׼�ƿ����ɱ�
	public static final String FDC_PARAM_SIMPLEINVOICE = "FDC023_SIMPLEINVOICE";
	//�Է�Ʊ���Ϊ׼�ƿ����ɱ�ʱ��ƾ֤��¼Ӧ���˿�ĳ�ֹ���
	public static final String FDC_PARAM_INVOICEOFFSET = "FDC024_INVOICEOFFSET";
	//���ü�ģʽ�ɱ�����һ�廯
	public static final String FDC_PARAM_SIMPLEFINACIAL = "FDC025_SIMPLEFINACIAL";
	//��Ʒ���㵥�ڼ���ù�����Ŀ�ɱ��ڼ仹�ǲ����ڼ�,�ǲ��óɱ��ڼ�,����ò����ڼ�
	public static final String FDC_PARAM_USECOSTORFINANCE = "FDC014_USECOSTORFINANCE";
	//�������뵥�տ����к��տ��˺�Ϊ��¼��
	public static final String FDC_PARAM_BANKREQURE = "FDC301_BANKREQURE";
	//�����������޸ĸ����˻�\�����Ŀ\��������\���
	public static final String FDC_PARAM_MODIFYACOUNT = "FDC302_MODIFY";
	//���ز����ǿ�Ʋ��������ϵͳ
	public static final String FDC_PARAM_NOTENTERCAS = "FDC303_NOTENTERCAS";
	//���Ӳ��������ݹ�����Ŀ��Ӧ�ĳɱ����Ĳ鿴����ƻ���
	public static final String FDC_PARAM_VIEWPLAN= "FDC304_VIEWPLAN";
	//����Ƶ����Ǹ������뵥�Ƶ���/������
	public static final String FDC_PARAM_PAYMENTCREATOR = "FDC090_PAYMENTCREATOR";
	/**�׹��ĵĸ�����ɿۿ*/
	public static final String FDC_PARAM_CREATEPARTADEDUCT= "FDC305_CREATEPARTADEDUCT";
	public static final String FDC_PARAM_PARTA_MAINCONTRACT="FDC330_PARTA_MAINCONTRACT";
	
	//���óɱ�����һ�廯--->���óɱ����½�
	public static final String FDC_PARAM_UNINCORPORATION = "FDC004_UNINCORPORATION";
	//�򵥲���ɱ�һ�廯����ۿΥԼ������
	public static final String FDC_PARAM_SIMPLEFINACIALEXTEND = "FDC026_SIMPLEFINACIALEXTEND";

	//�����ϸ�Ԥ�����
	public static final String FDC_PARAM_BUDGET_CTRLCOSTACCOUNT= "FDC314_CTRLCOSTACCOUNT";
	//�����ϸ�Ԥ�����
	public static final String FDC_PARAM_BUDGET_STRICTCTRL= "FDC310_STRICTCTRL";
	//��ͬ�ƻ����Ƶ��¸���
	public static final String FDC_PARAM_BUDGET_CONTRACTCTRPAY= "FDC311_CONTRACTCTRPAY";
	//�ɱ���Ŀ����ƻ����Ƶ��¸���
	public static final String FDC_PARAM_BUDGET_COSTACCTCTRPAY= "FDC312_COSTACCTCTRPAY";
	//ͨ��Ԥ��ϵͳ���Ƶ��¸���
	public static final String FDC_PARAM_BUDGET_BGSYSCTRPAY= "FDC313_BGSYSCTRPAY";
	//Ԥ��ƻ�����ʾ�ɱ���
	public static final String FDC_PARAM_BUDGET_DISPLAYCOST= "FDC309_DISPLAYCOST";
	//��ͬ�¶ȸ���ƻ��Ƿ��в��ź�ͬ����ƻ�ֱ������
	public static final String FDC_PARAM_DEPCONPAYPLAN="FDC310_DEPCONPAYPLAN";
	//��ʵ�ֲ�ֵΪ0ʱ�Ŀ���
	public static final String FDC_PARAM_REALIZEDZEROCTRL= "FDC315_REALIZEDZEROCTRL";
	/*
	 *by Cassiel_peng
	 */
	//����ƻ��������(��Ŀ�ƻ�ִ���еı��¼ƻ�ִ������ļ�����ú��ּ��㷽ʽ)
	public static final String FDC_PARAM_BUDGET_PLANDIF= "FDC316_PLANDIF";
	//��Ŀ�ƻ�ִ�б��Ƿ���ʾ�����ʵ�ʸ����ֽ���Ӧ������
	public static final String FDC_PARAM_BUDGET_ACTDIF="FDC316_ACTDIF";
	//���ز�ҵ��ϵͳ���������Ƿ����ñʼ����۹���
	public static final String FDC_PARAM_WRITEMARK="FDC213_WRITEMARK";
	//�Ƿ��������ύ״̬�ĵ���
	public static final String FDC_PARAM_SPLITSUBMIT="FDC214_SPLITSUBMITBILL";
	//����״̬�ĵ��ݿ����ϴ�����
	public static final String FDC_PARAM_UPLOADAUDITEDBILL="FDC216_UPLOADAUDITEDBILL";
	//��ͬʵ�ʸ���������ݸ������                  �˲���������  by cassiel_peng  2009-12-05
	public static final String FDC_PARAM_BASEONPAYMENTBILL="FDC217_BASEONPAYMENTBILL";
	//���ñ�����㹤��������
	public static final String FDC_PARAM_CHANGESETTAUDIT="FDC218_CHANGESETTAUDIT";
	//����ƾ֤ģʽ
	public static final String FDC_PARAM_ADJUSTVOURCHER= "FDC019_ADJUSTVOURCHER";
	//������ȷ�������븶�����̷���
	public static final String FDC_PARAM_SEPARATEFROMPAYMENT = "FDC317_SEPARATEFROMPAYMENT";
	//��������������Ӻ�ͬ������
	public static final String FDC_PARAM_ADDCONTENTAUDITED="FDC223_ADDCONTENTAUDITED";
	//���㵥�ϱ��޽�����Ƿ���6λС��
	public static final String FDC_PARAM_FDC224_KEEP6FORGUARANTERATE="FDC224_KEEP6FORGUARANTERATE";
	//��Ŀ�¶ȼƻ��������ʾ��ͬ��������������
	public static final String FDC_PARAM_FDC322_DISECOITEMPROCESS="FDC322_DISECOITEMPROCESS";
	/**
	 * ��ͬ����ʱ�������Ƿ�ȡ��ͬǩ��ʱ�Ļ���
	 */
	public static final String FDC_PARAM_FDC323_SELECTEXECHANGERATE = "FDC323_SELECTEXECHANGERATE"; // added by Owen_wen 2010-10-09
	/**
	 * ��ͬ�Ƿ��ϴ����Ŀ��Ƹ������뵥������
	 * added by Owen_wen 2010-11-19
	 */
	public static final String FDC_PARAM_FDC324_NEWPAYREQWITHCONTRACTATT = "FDC324_NEWPAYREQWITHCONTRACTATT";
	
	/**
	 * ��ͬ�������������󷽿ɱ�����������ã�������ְ����������֡������֡���������֡�
	 * added by Owen_wen 2010-11-29
	 */
	public static final String FDC_PARAM_FDC5014_NEXTSPLITISBASEONPREAUDITED = "FDC5014_NEXTSPLITISBASEONPREAUDITED";
	//�ۼƸ������ͬ�������Լ�������ϸ����
	public static final String FDC_PARAM_ALLPAIDMORETHANCONPRICE="FDC444_ALLPAIDMORETHANCONPRICE";
	//���ø���ͼ�ĵ�������ģʽ
	public static final String FDC_PARAM_ADJUSTBYGRANT="FDCSCH005_ADJUSTBYGRANT";
	//�������Ƿ񰴵�ǰ�û�������֯���й���
	public static final String FDC_PARAM_FILTERRESPPERSON="FDCSCH006_FILTERRESPPERSON";
	//���񹤳�����Ƿ�ֻ�ܰ��������
	public static final String FDC_PARAM_FILLBYRESPPERSON="FDCSCH007_FILLBYRESPPERSON";
	//�ƻ����š����β��ſ�ѡȫ������֯
	public static final String FDC_PARAM_CHOOSEALLORG="FDCSCH008_CHOOSEALLORG";
	//��ͬ�ύ����ʱ�Ƿ������ƻ�������й��� 2010-08-09
	public static final String FDC_PARAM_RELATEDTASK="FDC802_RELATEDTASK";
	
	
	
	
	//��ͬ�깤������ȡ����ϵͳ�����������
	public static final String FDC_PARAM_PROJECTFILLBILL = "FDC068_FROMPROJECTFILL";
	/**
	 *����ģʽ����ɱ�һ�廯֧�ַ�Ʊ���� 
	 */
	public static final String FDC_PARAM_INVOICEMRG = "FDC318_INVOICEMGR";

	// �������뵥�����ı���ͬ��Ʊ�š���Ʊ����¼
	public static final String FDC_PARAM_INVOICEREQUIRED = "FDC319_INVOICEREQUIRED";
	// �������뵥,���㵥��������ʱ�������ڼ�ֵ
	public static final String FDC_PARAM_RESETPERIOD = "FDC320_RESETPERIOD";
	// �Ƿ����������뵥�ۼƷ�Ʊ�����ں�ͬ�������
	public static final String FDC_PARAM_OVERRUNCONPRICE = "FDC321_OVERRUNCONPRICE";
	
	
	/*******4 �Զ������������ƵĲ���****FDC401******/
	//�������ƾ֤�м�ת����Ŀ
	public static final String FDC_PARAM_ACCOUNTVIEW = "FDC005_ACCOUNTVIEW";
	//����ƾ֤��ƾ֤����
	public static final String FDC_PARAM_VOUCHERTYPE = "FDC012_VOUCHERTYPE";
	//��Ŀ�º�ͬ�ܽ��ܳ���Ŀ��ɱ����:��Ŀ��ͬǩԼ�ܽ�����Ŀ
	public static final String FDC_PARAM_OUTCOST = "FDC013_OUTCOST";
	public static final String FDC_PARAM_CONTROLTYPE = "FDC013_CONTROLTYPE";
	public static final String FDC_PARAM_CONSETTTYPE = "FDC591_CONSETTTYPE";
	public static final String FDC_PARAM_COSTCENTERCONSTRATE = "FDC592_COSTCENTERCONSTRATE";
	
	/*******5 Ŀ��ɱ��µĲ���***FDC501***/	
	//�������ɱ�Ԥ������ͨ���������ɱ�Ԥ�ⵥ���д����������ɱ�Ԥ�⹦��ֻ�����ѯ
	public static final String FDC_PARAM_COSTMEASURE = "FDC011_COSTMEASURE";	
	//�ɱ���ĿԤ����ƹ��ܲ���
	public static final String FDC_PARAM_ACCTBUDGET = "FDC5001001_ACCTBUDGET";
	//Ŀ��ɱ���ѯ�Ƿ����¼��
	public static final String FDC_PARAM_AIMCOSTINPUT = "FDC5001002_AIMCOSTINPUT";
	//�������ϵ��
	public static final String FDC_PARAM_MEASUREADJUST = "FDC5001002_MEASUREADJUST";
	public static final String FDC_PARAM_MEASUREQUALITY = "FDC5001003_MEASUREQUALITY";
	//ʹ���Զ���ָ��
	public static final String FDC_PARAM_USECOSTOMINDEX = "FDC5001004_USECOSTOMINDEX";
	//Ŀ��ɱ��������¼�Ƿ�����ֱ��¼��
	public static final String FDC_PARAM_AIMCOSTADJUSTINPUT = "FDC5001005_AIMCOSTADJUSTINPUT";
	//����ɾ���������ɱ��ĵ�����¼
	public static final String FDC_PARAM_AIMCOSTADJUSTDELETE = "FDC5001009_AIMCOSTADJUSTDELETE";
	//Ŀ��ɱ�����ʱ������Ʒ��̯�Ƿ񰴼��Ź涨��ָ����з�̯
	public static final String FDC_PARAM_MEASUREINDEX = "FDC5001010_MEASUREINDEX";
	//--�����������Ŀ�ܺ�Ϊ���Ľ�����
	public static final String FDC_PARAM_NOSETTLEMENTSPLITACCTCTRL = "FDC5001100_NOSETTLEMENTSPLITACCT";
	//�����������Ŀ�ܺ�Ϊ���ı�����
	public static final String FDC_PARAM_NOCHANGESPLITACCTCTRL = "FDC5001200_NOCHANGESPLITACCT";
	//��������������Ʒ�������֮�Ͳ��ܳ����滮ָ���������
	public static final String FDC_PARAM_LIMITSELLAREA ="FDC5001300_LIMITSELLAREA";
	//��ͬ�Ƿ�ɽ������۲��
	public static final String FDC_PARAM_MEASURESPLIT = "FDC010_MEASURESPLIT";
	//������Ŀ��ɱ��޶�����֮���Ӱ�춯̬�ɱ�
	public static final String FDC_PARAM_AIMCOSTAUDIT = "FDC5001006_AIMCOSTAUDIT";
	// ���гɱ����㼰Ŀ��ɱ�����ʱ���滮ָ����ϣ����е����㳡�����ڡ��̻��õغϼơ���
	public static final String FDC_PARAM_PLANINDEXLOGIC = "FDC5001007_PLANINDXLOGIC";
	// ���гɱ����㡢Ŀ��ɱ���������Ŀ�����������㣬Ŀ��ɱ����㵼����Ŀ�������ָ�궼ʹ�ò����̯�Ĳ�Ʒ�������֮�ͣ��������ܽ������
	public static final String FDC_PARAM_BUILDPARTLOGIC = "FDC5001008_BUILDPARTLOGIC";	
	
    public static final String FDC_PARAM_MEASURECOSTISCONTAINDEVPROJECT ="FDC588_MEASURECOSTCONTAINDEVPROJECT";
	
    public static final String FDC_PARAM_SETTLEMENTOVERCONTRACTAMOUNT = "FDC589_SETTLEMENTOVERCONTRACTAMOUNT";
   
    public static final String FDC_PARAM_ADVANCEPAYMENTNUMBER = "FDC590_ADVANCEPAYMENTNUMBER";
    
    public static final String FDC_PARAM_CANMODIFYCONTRACTNUMBER ="FDC601_CANMODIFYNUMBER";
    
    public static final String FDC_PARAM_INCLUDECHANGEAUDIT ="FDC800_INCLUDECHANGEAUDIT";
    
    public static final String FDC_PARAM_ISOPENPAYMENTEDITUI = "FDC801_ISOPENPAYMENTEDITUI";
    
    //�ɱ���������������Ƿ�����
    public static final String FDC_PARAM_ISINCOMEJOINCOST = "FDC5013_ISINCOMEJOINCOST";
    
	/*******5 �����µ�***FDC501***/
	//������Ŀ�ƻ�������Ȩ
	public static final String FDC_PARAM_PLANMANDATE = "FDC088_PlanMandate";
	public static final String FDC_PARAM_SCHUDLEEXECUSEVIRTUALMODE = "FDCSCH009_EXECUSEVIRTUALMODE";
	
	//��Դ����
	public static final String FinanceResource = "com.kingdee.eas.fdc.finance.client.FinanceResource";
	public static final String VoucherResource = "com.kingdee.eas.fi.gl.client.VoucherEditResource";
	
	
	//Ԥ�����ʵ��
	//�������뵥
	static public final String PayRequestBill = "com.kingdee.eas.fdc.contract.app.PayRequestBill";
	
	//Ԥ����ƺ�ͬ������Ŀ
	//��ͬ
	public static final String ContractBill = "com.kingdee.eas.fdc.contract.app.ContractBill";
	//��ͬ
	public static final String ContractWithoutText = "com.kingdee.eas.fdc.contract.app.ContractWithoutText";
	
	//��ʼ�����ݱ�־
	public static final String FDC_INIT_ORGUNIT = "orgUnitId";
	public static final String FDC_INIT_CURRENCY = "baseCurrencyInfo";
	public static final String FDC_INIT_COMPANY= "company";
	public static final String FDC_INIT_CONTRACT = "contractBillInfo";
	
	public static final String FDC_INIT_PROJECT = "curProject";
	public static final String FDC_INIT_PERIOD = "curPeriod";
	public static final String FDC_INIT_DATE = "bookedDate";	
	
	public static final String FDC_INIT_ISFREEZE = "isFreeze";
	public static final String FDC_INIT_BOOKEDPERIOD = "canBookedPeriod";
	
	/*
	 *���¼�����������������ƾ֤��¼������ 
	 */
	public static final String FDC_INIT_PROJECTPRICEINCONTRACT = "projectPriceInContract";
	public static final String FDC_INIT_ADVANCE = "advance";
	public static final String FDC_INIT_COMPENSATION = "compensation";
	public static final String FDC_INIT_GUERDON = "guerdon";
	public static final String FDC_INIT_INVOICE = "invoice";
	/**���һ�廯����,�����ν���*/
	public static final boolean INVALID_GUERDON = true;
	
	/*******6 ��Ͷ�������***FDC601***/
	/**
	 * �ɹ���Ͷ��
	 */
	/**�б��ļ��ϳɡ���Ӧ���ʸ�Ԥ��ȷ������ר�ҡ�ȷ������ģ��һ������**/
	public static final String INVITE_SUPPLIER_EXPERT_TEMPLATE = "m0+mbCEzSQm6Deqwm/ltVKiB8+c=";
	public static final String FDC_PARAM_INVITE_SUPPLIER_EXPERT_TEMPLATE = "FDC601_INVITE_TOGETHERAUDIT";
	//�ɹ����뵥���ϱ�����Դ�ڼ׹������嵥
	public static final String FDC_PARAM_INVITE_PURCHASEBOMMUSTPARTA="FDC602_INVITE_PURCHASEBOMMUSTPARTA";
	//��Ӧ�̱�����Դ�ڷ��ز��Ĺ�Ӧ�̹������ɵı�׼��Ӧ��
	public static final String FDC_PARAM_INVITE_SUPPLIER_FROM_FDCSUPPLIER = "FDC603_INVITE_SUPPLIERFROMFDC";
	/****��Ӧ�̲��� ***********/
	public static final String FDC_PARAM_SUPPLIER_LIMIT="FDC_SUPPLIER_LIMIT";
	
	/*******7 Ͷ�ʲ�������Ĳ��� **** FDC701****/
	//�ɱ����ò����в�����ܱ�ĵ����Ƿ��¼��
	public static final String MEASURE_COSTMEASURE = "DjH0Xv3XTi6aP4tP3klLCqiB8+c=";
	public static final String FDC_PARAM_MEASURE_COSTMEASURE = "FDC701_MEASURE_COSTMEASURE";

	
	/**���ȹ����µ�FDCSCH��ͷ��һ��ŵ�ScheduleParamHelper�У���Ϊ��ϵ������Ķ��������Էŵ�basedata��**/
	/**
	 * �Ƿ���ں�ͬ�������
	 */
	public static final String FDCSCH_PARAM_ISFILLBILLCONTROLSTRICT = "FDCSCH003";
	public static final String FDCSCH_PARAM_BASEONTASK = "FDCSCH04_BASEONTASK";
	/**
	 * ��ͬǩԼ������֮�����ĺ�Լ�滮���ʱ�Ƿ��ϸ����
	 * */
	public static final String FDC_PARAM_CONTRACT_PROGRAM_AMOUNT = "ContractProgramAmountControlMode";
	//��ʷ��ͬ������Լ�滮
	public static final String FDC_PARAM_OLDCONTRACT_PROGRAM = "FDC900_CON_OLDCONTRACTRTOPROGRAMMING";
	
}
