package com.kingdee.eas.fdc.basecrm;



/**
 * ���ز�CRMϵͳ�������붨�� 
 * @author jeegan_wang
 *
 */

public class CRMConstants {

	//�����ϵĻ�������   ��ʱ������:�������õķ��ز���¥������
	public static final String FDCCRM_ROOM_Height = "FDCCRM_ROOM_Height";	//����߶�
	
	public static final String FDCCRM_ROOM_Width = "FDCCRM_ROOM_Width";		//������
	
	public static final String FDCCRM_ROOM_DispField = "FDCCRM_ROOM_DispField";		//������ʾ�ֶ�
	
	public static final String FDCCRM_ROOM_AttachDisType = "FDCCRM_ROOM_AttachDisType";		//�󶨷�����ʾ
	
	public static final String FDCCRM_ROOM_FontName = "FDCCRM_ROOM_FontName";		//������ʾ���������
	
	public static final String FDCCRM_ROOM_FontStyle = "FDCCRM_ROOM_FontStyle";		//������ʾ�������ʽ
	
	public static final String FDCCRM_ROOM_FontSize = "FDCCRM_ROOM_FontSize";		//������ʾ����Ĵ�С
	
	public static final String FDCCRM_ROOM_FontColor = "FDCCRM_ROOM_FontColor";		//������ʾ�������ɫ
	
	
	//�����ϵ����� ��¥����SHE
	public static final String FDCCRM_SHE_ROOM_InitColor = "FDCCRM_SHE_ROOM_InitColor";		//�������ɫ-δ����
	public static final String FDCCRM_SHE_ROOM_OnShowColor = "FDCCRM_SHE_ROOM_OnShowColor";		//�������ɫ-����
	public static final String FDCCRM_SHE_ROOM_KeepDownColor = "FDCCRM_SHE_ROOM_KeepDownColor";		//�������ɫ-����
	public static final String FDCCRM_SHE_ROOM_PrePurColor = "FDCCRM_SHE_ROOM_PrePurColor";		//�������ɫ-Ԥ��
	public static final String FDCCRM_SHE_ROOM_PurchseColor = "FDCCRM_SHE_ROOM_PurchseColor";		//�������ɫ-�Ϲ�
	public static final String FDCCRM_SHE_ROOM_SignColor = "FDCCRM_SHE_ROOM_SignColor";		//�������ɫ-ǩԼ
	public static final String FDCCRM_SHE_ROOM_NoSaleColor = "FDCCRM_SHE_ROOM_NoSaleColor";		//�������ɫ-û����¥����

	public static final String FDCCRM_SHE_IsSpcilOnAudit = "FDCCRM_SHE_IsSpcilOnAudit";		//����ҵ��������ʱ��Ϊ׼	
	

	//�����ϵ����� ���޲���TEN
	public static final String FDCCRM_Ten_ROOM_UnTenColor = "FDCCRM_Ten_ROOM_UnTenColor";		//�������ɫ-δ����
	public static final String FDCCRM_Ten_ROOM_WaitTenColor = "FDCCRM_Ten_ROOM_WaitTenColor";		//�������ɫ-����
	public static final String FDCCRM_Ten_ROOM_NewTenColor = "FDCCRM_Ten_ROOM_NewTenColor";		//�������ɫ-����
	public static final String FDCCRM_Ten_ROOM_ContiuTenColor = "FDCCRM_Ten_ROOM_ContiuTenColor";		//�������ɫ-����
	public static final String FDCCRM_Ten_ROOM_EnlargeTenColor = "FDCCRM_Ten_ROOM_EnlargeTenColor";		//�������ɫ-����
	public static final String FDCCRM_Ten_ROOM_KeepTenColor = "FDCCRM_Ten_ROOM_KeepTenColor";		//�������ɫ-����
	public static final String FDCCRM_Ten_ROOM_SincerTenColor = "FDCCRM_Ten_ROOM_SincerTenColor";		//�������ɫ-Ԥ��
	public static final String FDCCRM_Ten_ROOM_NoTenColor = "FDCCRM_Ten_ROOM_NoTenColor";		//�������ɫ-û����������
	
	
	//��¥��������������ϵ�
	//��������  <'Fun'+'��Ŀid' , ���õ�ֵ�ԷֺŸ������ַ���> ���磺 <"FunoC7k7sSdSu2SSLxMMs3itZAYry8=","true;false;true;false;false;true;true;true;false">
	//	�����ֵ�����ǣ�//����ǿ�Ƶ׼ۿ��� //����ǩԼ�տ���� //���õ�Ѻ�������� //�Ϲ�ҵ��ʵ���տ�Ϊ׼ //������������Ϲ�δ���̷��� //�������������������  //�����Ƿ���������	 //�Ϲ�/ǩԼ�����Ƿ�����༭	 //�˷��Ƿ�������
	//					isBasePrice;isSignGathering;isMortagage;isActGathering;isSincerSellOrder;isSincerPrice;isHouseMoney;isEditPurAndSignDate;isAdjustPrices;
	
	//Ԥ����������  <'Pre'+'��Ŀid' , ���õ�ֵ�ԷֺŸ������ַ���> ���磺 <"FunoC7k7sSdSu2SSLxMMs3itZAYry8=","1000;false;2000;true">
	//	�����ֵ�����ǣ�  //Ԥ����ͽ�� //��Ϳɷ��޸� //Ԥ����׼��� //��׼�ɷ��޸�
	//					preLevelAmount;isLevelModify;preStandAmount;isStandModify
	
	//����������� 
	public static final String FDCCRM_SHE_Cas_QuitMoneyType = "FDCCRM_SHE_Cas_QuitMoneyType";	//��Ӧ��ֵ=�����id+";" + (true/false)�Ƿ������˷����ÿ����޸�
	public static final String FDCCRM_SHE_Cas_ChangeFeeType = "FDCCRM_SHE_Cas_ChangeFeeType";	//��Ӧ��ֵ=�����id+";" + (true/false)�Ƿ����������ÿ����޸�
	public static final String FDCCRM_SHE_Cas_ChangeTrsfType = "FDCCRM_SHE_Cas_ChangeTrsfType";	//��Ӧ��ֵ=�����id+";" + (true/false)�Ƿ�������ת������޸�
	public static final String FDCCRM_SHE_Cas_ChangeBalcObj = "FDCCRM_SHE_Cas_ChangeBalcObj";	//��Ӧ��ֵ=ChangeBalanceObjectEnum��ֵ+";" + (true/false)�Ƿ���������������޸�
	public static final String FDCCRM_SHE_Cas_SincerMoneyType = "FDCCRM_SHE_Cas_SincerMoneyType";	//��Ӧ��ֵ=�����id+";" + (true/false)�Ƿ��������������޸�
	
	
	//���޹�������������ϵ�
	
	//CRMϵͳ��������
	public static final String FDCCRM_PARAM_PROJECTLEVEL = "FDCCRM_010_PROJECTLEVEL";
	public static final String FDCCRM_PARAM_LEVELLENGTH = "FDCCRM_020_LEVELLENGTH";
	
	public static final String FDCSHE_PARAM_ISSHARECUS = "FDCSHE_010_ISSHARECUS";
	public static final String FDCSHE_PARAM_REPEATCUSSHOWRULE_DIR = "FDCSHE_020_REPEATCUSSHOWRULE_DIR";
	public static final String FDCSHE_PARAM_REPEATCUSSHOWRULE_START = "FDCSHE_030_REPEATCUSSHOWRULE_START";
	public static final String FDCSHE_PARAM_REPEATCUSSHOWRULE_LEN = "FDCSHE_040_REPEATCUSSHOWRULE_LEN";
	public static final String FDCSHE_PARAM_CUSREPEATRULE = "FDCSHE_050_CUSREPEATRULE";
	public static final String FDCSHE_PARAM_ISUSECHEQUE = "FDCSHE_060_ISUSECHEQUE";
	public static final String FDCSHE_PARAM_ROOMCOLOR_INIT = "FDCSHE_070_ROOMCOLOR_INIT";
	public static final String FDCSHE_PARAM_ROOMCOLOR_ONSHOW = "FDCSHE_071_ROOMCOLOR_ONSHOW";
	public static final String FDCSHE_PARAM_ROOMCOLOR_KEEPDOWN = "FDCSHE_072_ROOMCOLOR_KEEPDOWN";
	public static final String FDCSHE_PARAM_ROOMCOLOR_CONTROL = "FDCSHE_072_ROOMCOLOR_CONTROL";
	public static final String FDCSHE_PARAM_ROOMCOLOR_SINPURCHASE = "FDCSHE_073_ROOMCOLOR_SINPURCHASE";
	public static final String FDCSHE_PARAM_ROOMCOLOR_PREPURCHASE = "FDCSHE_074_ROOMCOLOR_PREPURCHASE";
	public static final String FDCSHE_PARAM_ROOMCOLOR_PURCHASE = "FDCSHE_075_ROOMCOLOR_PURCHASE";
	public static final String FDCSHE_PARAM_ROOMCOLOR_SIGN = "FDCSHE_076_ROOMCOLOR_SIGN";
	public static final String FDCSHE_PARAM_TOLAMOUNT_BIT = "FDCSHE_080_TOLAMOUNT_BIT";
	public static final String FDCSHE_PARAM_TOL_TOINTEGER_TYPE = "FDCSHE_090_TOL_TOINTEGER_TYPE";
	public static final String FDCSHE_PARAM_PRICE_BIT = "FDCSHE_100_PRICE_BIT";
	public static final String FDCSHE_PARAM_PRICE_TOINTEGER_TYPE = "FDCSHE_110_PRICE_TOINTEGER_TYPE";
	//����Ʊ����ڲ���
	public static final int CHEQUEREBACK_TYEP_CHEQUE = 0;
	public static final int CHEQUEREBACK_TYEP_REVBILLEntry = 1;
	public static final int CHEQUEREBACK_TYEP_REVBILL = 2;
	
	//------------------ �ͻ������������Ԥ������ ---------------------
//	/** �ͻ���Դ */ �ÿͻ����������е�
//	public static final String CUSTOMER_ORIGIN_GROUP_ID = "r7uyGaH8QjmY6mtFiwBbIoCJEuM=";
	/** �Ӵ���ʽ */
	public static final String RECEPTION_TYPE_GROUP_ID = "AWrIRp54RU2mKNK/dZGfPICJEuM=";
	/** ��ס���� */
	public static final String HABITATION_AREA_GROUP_ID = "t2QAma06THCMM1fhcgFQJ4CJEuM=";
	/** �������� */
	public static final String WORK_AREA_GROUP_ID = "4+yJb7XlRjyWtiT9wAcDMYCJEuM=";
	/** ҵ��Χ */
	public static final String BIZ_SCOPE_GROUP_ID = "tGKMX23PQjW3uiwz/rXiOICJEuM=";
	/** ��֪;�� */
	public static final String KNOW_TYPE_GROUP_ID = "aKnRDPwgRLKedBSNSdp5VoCJEuM=";
	//--------------------------------------------------------------------
	
	//------------------------ �ͻ������������Ԥ������ -------------------------
	/** ���� */
	public static final String PERSON_GROUP_ID = "Y+Wj8rPtRrix14gOw9isKEhRlQw=";
	/** ��ҵ */
	public static final String ENTERPRICE_GROUP_ID = "5MhohX5hT4Sx/Iw/1Uhow0hRlQw=";
	/** ��ҵ֤������ */
	public static final String EN_CERTIFICATE_TYPE_GROUP_ID = "QjTHOJE/SdeQXpOArsMIDUhRlQw=";
	/** ��ҵ���� */
	public static final String ENTERPRICE_PROPERTY_GROUP_ID = "VEcIKw21SKCbY32n5imloEhRlQw=";
	/** ��ҵ��ģ */
	public static final String ENTERPRICE_SIZE_GROUP_ID = "kjFpMWS3SZiB5HCbX8OouUhRlQw=";
	/** ����ģʽ */
	public static final String COOPERATE_MODEL_GROUP_ID = "+gURTIYCScOj2MJw0w6W4UhRlQw=";
	/** ����֤������ */
	public static final String PE_CERTIFICATE_TYPE_GROUP_ID = "dKH98sDNTvepPa6zBbdTv0hRlQw=";
	/** �ͻ���� */
	public static final String CUSTOMER_STATUS_GROUP_ID = "n++o6YvfRXKE8SBbbYD88khRlQw=";
	/** ����׶� */
	public static final String AGE_STEP_GROUP_ID = "52JgBl0gQRu2b2dTejXk2khRlQw=";
	/** �����̶� */
	public static final String EDUCATION_GROUP_ID = "qClQZBozSPiy+GdHQH5tkEhRlQw=";
	/** ������ */
	public static final String INCOME_GROUP_ID = "dLJDogdGSj6d9X/kM3ma30hRlQw=";
	/** ��Ȥ���� */
	public static final String INTEREST_GROUP_ID = "DN6MxqEvSkuXySzMOJMwp0hRlQw=";
	/** ��ͥ�ṹ */
	public static final String FAMILY_GROUP_ID = "rCf2+xliTFSC6n2uvpBV5UhRlQw=";
	/** ְҵ */
	public static final String WORK_GROUP_ID = "0oWLxLdFTeqHjD+F1Eh/80hRlQw=";
	/** �ͻ���Դ */
	public static final String CUSTOMER_ORIGIN_GROUP_ID = "aP1LxRYPSGabLszhEs2T8UhRlQw=";
	/** ��ס��� */
	public static final String HABITATION_STATUS_GROUP_ID = "xTdtP3JzTBSwYf8/Gib280hRlQw=";
	//--------------------------------------------------------------------
	
	/**
	 * ����Ϊ�̻��������ϵ�Ԥ������
	 */
	/**
	 * 01	�̻����̻�����ԭ��
	 */
	public static final String COMMERCECHANCE_REASON_ID="WTkBZnw4R6ykPnTi1iqB37dELa4=";
	
	/**
	 * 02	����ǿ��
	 */
	//public static final String HOPE_INTENSITY_ID="/egtTNg2TzqA834F8giiSrdELa4=";
	/**
	 * 03	�����ܼ۷�Χ
	 */
	public static final String HOPE_TOTALAMOUNT_ID="Ksya73s9TxyhwmDJmErrhrdELa4=";
	/**
	 * 04	���򣭵�������
	 */
	public static final String HOPE_PRICE_ID="bb9+9OvfQD6xkYBMuJOUrLdELa4=";
	/**
	 * 05	����¥������
	 */
	public static final String HOPE_FLOOR_ID="ZHxBhnrrRYK5YoLNrPVpg7dELa4=";
	/**
	 * 06	�����׸�����
	 */
	//public static final String HOPE_FIRSTPAY_RATE_ID="gUWIwYh+QT2fedXTMVuF5rdELa4=";
	/**
	 * 07	���������Χ
	 */
	public static final String HOPE_AREA_SCOPE_ID="wFKzZaRpRQCl3VvhuKPfwrdELa4=";
	/**
	 * 08	���򹺷�����
	 */
	public static final String HOPE_BUY_AREA_ID="x9MDCs3JQ0uce+MqH7sIs7dELa4=";
	/**
	 * 09	�̻�����ҵĿ��
	 */
	public static final String HOPE_PURPOSE_ID="nlP5mkPWSmuIfCf6i9yEBLdELa4=";
	/**
	 * 10	��ҵ����
	 */
	//public static final String HOPE_BUY_NUMBER="h/FLyJa0Sc6t+dpFAveVs7dELa4=";
	/**
	 * 11	�̻����̻��׶Σ�����׶Σ�
	 */
	public static final String HOPE_STAGE_ID="iFsdzMD5R76pTkICDmcBq7dELa4=";
	/**
	 * 12	�̻����̻�����
	 */
	public static final String COMMERCECHANCE_LEVEL_ID="91bqhU4lTgKoJZDW9lCj87dELa4=";
	
	/**
	 * 13  ���򣭾���
	 */
	public static final String  HOPE_LANDSCAPE_ID="aDya2NbITr+ymXXU7/kCW7yNrUg=";
	
	/**
	 * �̻��׶�-�ź�
	 */
	public static final String COMMERCECHANCE_STAGE_PAIHAO="O1kNpTflRGmd+TnT9sq7LJy8SHQ=";
	/**
	 * �̻��׶�-Ԥ��
	 */
	public static final String COMMERCECHANCE_STAGE_BOOKING="UCR/jByXQkKgtBkyxqFGaZy8SHQ=";
	/**
	 * �̻��׶�-�Ϲ�
	 */
	public static final String COMMERCECHANCE_STAGE_PURCHASE="iizo9mVGR32CH8Qg04Qfcpy8SHQ=";
	/**
	 * �̻��׶�-ǩԼ
	 */
	public static final String COMMERCECHANCE_STAGE_SIGN="QkGAAtjBSeCbUrDmMFvvOZy8SHQ=";
	/**
	 * �̻��׶�-�˷�
	 */
	public static final String COMMERCECHANCE_STAGE_QUITROOM="qMXIwmEFSJayy6BGFG5hx5y8SHQ=";
	/**
	 * �̻��׶�-����
	 */
	public static final String COMMERCECHANCE_STAGE_CHANGENAME="ty07iCgaR7S4/OAl7VDqx5y8SHQ=";
	
	/**
	 * �̻���ֹ��ԭ��
	 */
	public static final String COMMERCECHANCE_STOP_REASON="Bb5bDAIuRUixAVLwgwkyDrdELa4=";
	
		
	
}
