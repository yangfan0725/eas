package com.kingdee.eas.fdc.tenancy.client;

/**
 * ���޺�ͬclient��س���
 * @author zhicheng_jin
 * */
public interface TenancyBillConstant {
	
	public static final String DATE_FORMAT_STR = "yyyy-MM-dd";
	
	//------- ���޲���ʱ����Ľ��������key,��UIContext.put(key, value)�е�key ----------
	/** ��������ʱ���뷿�伯��RoomCollection */
	public static final String KEY_ROOMS = "rooms";
	
	/** �������������ʱ����ɵ����޺�ͬ��ID */
	public static final String KEY_OLD_TENANCY_BILL_ID = "oldTenancyBillID";
	
	/** �����������޺�ͬ����,TenancyContractTypeEnum */
	public static final String KEY_TENANCY_CONTRACT_TYPE = "tenancyContractType";
	
	/** ��������������Ŀ,SellProjectInfo */
	public static final String KEY_SELL_PROJECT = "sellProject";
	
	
	
	//------------- ���޺�ͬ����ķ���Table���� ------------
	/** ����״̬ */
	public static final String C_ROOM_TEN_ROOM_STATE = "tenRoomState";
//	/** ��Ŀ */
//	public static final String C_ROOM_SELL_PROJECT = "sellProject";
//	/** ¥�� */
//	public static final String C_ROOM_BUILDING = "building";
//	/** ��Ԫ */
//	public static final String C_ROOM_UNIT = "unit";
	/** ���� */
	public static final String C_ROOM_ROOM = "room";
	/** ¥�� */
	public static final String C_ROOM_FLOOR = "floor";
	/** ��׼��� */
	public static final String C_ROOM_STANDARD_RENT = "standardRent";
	/** ��׼������� */
	public static final String C_ROOM_STANDARD_RENT_TYPE = "standardRentType";
	/** ��׼���� */
	public static final String C_ROOM_STANDARD_RENT_PRICE = "standardRentPrice";
	/** �ɽ���� */
	public static final String C_ROOM_DEAL_RENT = "dealRent";
	/** �ɽ�������� */
	public static final String C_ROOM_DEAL_RENT_TYPE = "dealRentType";
	/** �ɽ����� */
	public static final String C_ROOM_DEAL_RENT_PRICE = "dealRentPrice";
	/** ���ڱ�� */
	public static final String C_ROOM_FLAG_AT_TERM = "flagAtTerm";
	/** ���� */
	public static final String C_ROOM_DES = "des";
	/** ʵ�⽨����� */
	public static final String C_ROOM_BUILDING_AREA = "buildingArea";
	/** װ����� */
	public static final String C_ROOM_FITMENT = "fitment";
	/** ���� */
	public static final String C_ROOM_ROOM_MODEL = "roomModel";
	/** ���� */
	public static final String C_ROOM_DIRECTION = "direction";
	/** ʵ�ʽ������� */
	public static final String C_ROOM_ACT_DELIVER_DATE = "actDeliverDate";
	/** ʵ���˷����� */
	public static final String C_ROOM_ACT_QUIT_DATE = "actQuitDate";
	/** ����� */
	public static final String C_ROOM_DAYPRICE = "dayPrice";
	
	//add by yangfan
	//------------- ΥԼ�𷽰�����Table���� ------------
	/**��������*/
	public static final String C_LIQUI_MONEYDEFINE="moneyDefine";
	
	/**ΥԼ����㷽��*/
	public static final String C_LIQUI_LIQUIDATED="liquidated";
	
	//------------- ���޺�ͬ�����������ԴTable����------------TODO ������
	/** ������Դ */
	public static final String C_ATTACH_ATTACH = "attach";
	/** ������Դ���� */
	public static final String C_ATTACH_ATTACH_RES_TYPE = "attachResType";
	/** ������Դ���� */
	public static final String C_ATTACH_ATTACH_RES_NAME = "attachResName";
	/** ������Դ˵�� */
	public static final String C_ATTACH_ATTACH_RES_DES = "attachResDes";
	/** ��׼��� */
	public static final String C_ATTACH_STANDARD_RENT = "standardRent";
	/** ��׼������� */
	public static final String C_ATTACH_STANDARD_RENT_TYPE = "standardRentType";
//	/** �ɽ���� */
//	public static final String C_ATTACH_DEAL_RENT = "dealRent";
//	/** �ɽ�������� */
//	public static final String C_ATTACH_DEAL_RENT_TYPE = "dealRentType";
	/** ���ڱ�� */
	public static final String C_ATTACH_IS_AT_TERM = "isAtTerm";
	/** ���� */
	public static final String C_ATTACH_DES = "des";
	/** ʵ�ʽ������� */
	public static final String C_ATTACH_ACT_DELIVER_DATE = "actDeliverDate";
	/** ʵ���ջ����� */
	public static final String C_ATTACH_ACT_QUIT_DATE = "actQuitDate";
	
	
	//------------- ���޺�ͬ����Ŀͻ�Table���� ------------
	/** �������(%) */
	public static final String C_CUS_PROPERTY_PERCENT = "propertyPercent";
	/** �ͻ� */
	public static final String C_CUS_CUSTOMER = "customer";
	/** �ʱ� */
	public static final String C_CUS_POSTALCODE = "postalcode";
	/** ��ϵ�绰 */
	public static final String C_CUS_PHONE = "phone";
	/** ֤������ */
	public static final String C_CUS_CERTIFICATE_NAME = "certificateName";
	/** ֤������ */
	public static final String C_CUS_CERTIFICATE_NUMBER = "certificateNumber";
	/** ͨ�ŵ�ַ */
	public static final String C_CUS_MAIL_ADDRESS = "mailAddress";
	/** �Ǽ����� */
	public static final String C_CUS_BOOK_DATE = "bookDate";
	/** ��ע */
	public static final String C_CUS_DES = "des";
	
	//------------ �������б�������б��Table����----
	/** �������� */
	public static final String C_INC_INCREASE_DATE = "increaseDate";
	/** ������ʽ */
	public static final String C_INC_INCREASE_TYPE = "increaseType";
	/** ֵ */
	public static final String C_INC_VALUE = "value";
	/** �������� */    
	public static final String C_INC_INCREASESTYLE = "increaseStyle";

	/** ���⿪ʼ���� */
	public static final String C_FREE_START_DATE = "freeStartDate";
	/** ����������� */
	public static final String C_FREE_END_DATE = "freeEndDate";
	/**�������� */
	public static final String C_FREE_TENANCY_TYPE="freeTenancyType";
	/** ��ע */
	public static final String C_FREE_DES = "description";
	//---------------------
	
	//------- ��������б������ (�����ж�̬����)---
	/** ���� */
	public static final String C_RENT_ROOM = "room";
	/** Ѻ�� */
	public static final String C_RENT_DEPOSIT = "deposit";
	/** ���� */
	public static final String C_RENT_FIRST_RENT = "firstRent";
	/** ��𶨼۷�ʽ */
	public static final String C_RENT_TENANCY_MODEL = "tenancyModel";
	/** ������� */
	public static final String C_RENT_RENT_TYPE = "rentType";
	
	/** ��𶨼۵���ǰ׺ */
	public static final String PREFIX_C_RENT_RENT_PRICE = "rentPrice-";
	/** ��𶨼�ǰ׺ */
	public static final String PREFIX_C_RENT_RENT = "rent-";
	/** �����ǰ׺ */
	public static final String  PREFIX_C_RENT_RENT_PREDAY = "rentPreDay-";
	
	/** �����ͷ��õ���ǰ׺ */
	public static final String PREFIX_C_RENT_PERIODICITY_PRICE = "periodictityPrice-";
	/** �����ͷ���ǰ׺ */
	public static final String PREFIX_C_RENT_PERIODICITY = "periodictity-";
	
	//--------------
	
	//------------- ���޺�ͬ����ĸ�����ϸTable����(����������̬����) ---------
	/** �������� */
	public static final String C_PAYS_MONEY_DEFINE = "moneyDefine";
	/** �������� */
	public static final String C_PAYS_LEASE_SEQ = "leaseSeq";
	/** ��ʼ���� */
	public static final String C_PAYS_START_DATE = "startDate";
	/** �������� */
	public static final String C_PAYS_END_DATE = "endDate";
	/** Ӧ������ */
	public static final String C_PAYS_APP_PAY_DATE = "actPayDate";
	/** �������� */
	public static final String C_PAYS_FREE_DAYS = "freeDays";
	
	/** ������ǰ׺ */
	public static final String PREFIX_C_PAYS_ROOM = "room-";
	/** ������Դ��ǰ׺ */
	public static final String PREFIX_C_PAYS_ATTACH_RES = "attach-";
	/** Ӧ������׺ */
	public static final String POSTFIX_C_PAYS_APP_AMOUNT = "-appPay";
	/** ʵ������׺ */
	public static final String POSTFIX_C_PAYS_ACT_AMOUNT = "-actPay";
	/** ʵ�����ں�׺ */
	public static final String POSTFIX_C_PAYS_ACT_PAY_DATE = "-actPayDate";
	
	/** Ӧ���ϼ� */
	public static final String C_PAYS_TOTAL_APP_PAY = "totalAppPay";
	/** ʵ���ϼ� */
	public static final String C_PAYS_TOTAL_ACT_PAY = "totalActPay";
	
}
