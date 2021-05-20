package com.kingdee.eas.fdc.sellhouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarketBaseDataList {
	
	public final  static String ALLSCOPE = ScopeTypeEnum.ALLSCOPE_VALUE;
	public final  static String ENACTMENTSCOPE = ScopeTypeEnum.ENACTMENTSCOPE_VALUE;
	public final  static String CUCONTROL = BaseDataPropertyEnum.CUCONTROL_VALUE;
	public final  static String PROJECTCONTROL = BaseDataPropertyEnum.PROJECTCONTROL_VALUE;
	
	public final static String CUSTOMERARCH = ArchivesTypeEnum.CUSTOMERARCH_VALUE;
	public final static String ROOMARCH = ArchivesTypeEnum.ROOMARCH_VALUE;
	public final static String COMMERRACH = ArchivesTypeEnum.COMMERARCH_VALUE;
	
	public static List nameList = new ArrayList();
	public static List tableList = new ArrayList();
	public static List defScopeTypeList = new ArrayList();
	public static List defBaseDataProList = new ArrayList();
	public static List archivesList = new ArrayList();
	public static Map editUINameMap = new HashMap();
	public static Map entiyFactoryMap = new HashMap();
	private MarketBaseDataList(){		
	}
	
	static{
		add("��������", "T_SHE_BuildingProperty", ALLSCOPE, CUCONTROL,ROOMARCH,"BuildingPropertyFactory","com.kingdee.eas.fdc.sellhouse.client.BuildingPropertyEditUI");
		add("�������", "T_SHE_RoomModelType", ALLSCOPE, CUCONTROL,ROOMARCH,"RoomModelTypeFactory","com.kingdee.eas.fdc.sellhouse.client.RoomModelTypeEditUI");
		add("��Ʒ����", "T_FDC_ProductType", ALLSCOPE, CUCONTROL,ROOMARCH,"ProductTypeFactory","com.kingdee.eas.fdc.basedata.client.ProductTypeEditUI");
		add("���侰��", "T_SHE_SightRequirement", ALLSCOPE, CUCONTROL,ROOMARCH,"SightRequirementFactory","com.kingdee.eas.fdc.sellhouse.client.SightRequirementEditUI");
		add("���䳯��", "T_SHE_HopedDirection", ALLSCOPE, CUCONTROL,ROOMARCH,"HopedDirectionFactory","com.kingdee.eas.fdc.sellhouse.client.HopedDirectionEditUI");
		
		add("�����ṹ", "T_SHE_BuildingStructure", ALLSCOPE, CUCONTROL,ROOMARCH,"BuildingStructureFactory","com.kingdee.eas.fdc.sellhouse.client.BuildingStructureEditUI");
		add("��Ʒ����", "T_SHE_ProductDetial", ALLSCOPE, PROJECTCONTROL,ROOMARCH,"ProductDetialFactory","com.kingdee.eas.fdc.sellhouse.client.ProductDetailEditUI");
		add("������;", "T_SHE_RoomUsage", ALLSCOPE, PROJECTCONTROL,ROOMARCH,"RoomUsageFactory","com.kingdee.eas.fdc.sellhouse.client.RoomUsageEditUI");
		
		add("����", "T_SHE_Noise", ALLSCOPE, CUCONTROL,ROOMARCH,"NoiseFactory","com.kingdee.eas.fdc.sellhouse.client.NoiseEditUI");
		add("��Ұ", "T_SHE_EyeSignht", ALLSCOPE, CUCONTROL,ROOMARCH,"EyeSignhtFactory","com.kingdee.eas.fdc.sellhouse.client.EyeSignhtEditUI");
		add("������׼", "T_SHE_PossessionStandard", ALLSCOPE, CUCONTROL,ROOMARCH,"PossessionStandardFactory","com.kingdee.eas.fdc.sellhouse.client.PossessionStandardEditUI");
		add("װ�ޱ�׼", "T_SHE_DecorationStandare", ALLSCOPE, CUCONTROL,ROOMARCH,"DecorationStandardFactory","com.kingdee.eas.fdc.sellhouse.client.DecorationStandardEditUI");
		
		add("�ͻ���Դ", "T_SHE_CustomerOrigin", ALLSCOPE, CUCONTROL,CUSTOMERARCH,"CustomerOriginFactory","com.kingdee.eas.fdc.sellhouse.client.CustomerOriginEditUI");
		add("�ͻ��ּ�", "T_SHE_CustomerGrade", ALLSCOPE, CUCONTROL,CUSTOMERARCH,"CustomerGradeFactory","com.kingdee.eas.fdc.sellhouse.client.CustomerGradeEditUI");
		
		add("��ͥ����", "T_SHE_FamillyEarning", ALLSCOPE, CUCONTROL,CUSTOMERARCH,"FamillyEarningFactory","com.kingdee.eas.fdc.sellhouse.client.FamillyEarningEditUI");
		add("��ס����", "T_SHE_HabitationArea", ALLSCOPE, CUCONTROL,CUSTOMERARCH,"HabitationAreaFactory","com.kingdee.eas.fdc.sellhouse.client.HabitationAreaEditUI");
		add("�Ӵ���ʽ", "T_SHE_ReceptionType", ALLSCOPE, CUCONTROL,CUSTOMERARCH,"ReceptionTypeFactory","com.kingdee.eas.fdc.sellhouse.client.ReceptionTypeEditUI");
		add("���򵥼�", "T_SHE_HopedUnitPrice", ALLSCOPE, CUCONTROL,COMMERRACH,"HopedUnitPriceFactory","com.kingdee.eas.fdc.sellhouse.client.HopedUnitPriceEditUI");
		add("�����ܼ�", "T_SHE_HopedTotalPrices", ALLSCOPE, CUCONTROL,COMMERRACH,"HopedTotalPricesFactory","com.kingdee.eas.fdc.sellhouse.client.HopedTotalPricesEditUI");
		
		add("����¥��", "T_SHE_HopedFloor", ALLSCOPE, CUCONTROL,COMMERRACH,"HopedFloorFactory","com.kingdee.eas.fdc.sellhouse.client.HopedFloorEditUI");
		add("�׸�����", "T_SHE_FirstPayProportion", ALLSCOPE, CUCONTROL,COMMERRACH,"FirstPayProportionFactory","com.kingdee.eas.fdc.sellhouse.client.FirstPayProportionEditUI");
		add("�̻�ԭ��", "T_SHE_BuyHouseReason", ALLSCOPE, CUCONTROL,COMMERRACH,"BuyHouseReasonFactory","com.kingdee.eas.fdc.sellhouse.client.BuyHouseReasonEditUI");
		add("����ǿ��", "T_SHE_HopePitch", ALLSCOPE, CUCONTROL,COMMERRACH,"HopePitchFactory","com.kingdee.eas.fdc.sellhouse.client.HopePitchEditUI");
		add("�������", "T_SHE_AreaRequirement", ALLSCOPE, CUCONTROL,COMMERRACH,"AreaRequirementFactory","com.kingdee.eas.fdc.sellhouse.client.AreaRequirementEditUI");
		add("�¼�����", "T_SHE_EventType", ALLSCOPE, CUCONTROL,COMMERRACH,"EventTypeFactory","com.kingdee.eas.fdc.sellhouse.client.EventTypeEditUI");
	}

//	private static void add(String name, String tableName, String defScopeType, String defBaseDataPro,String archivesType,String entiyFactory) {
//		add(name, tableName, defScopeType, defBaseDataPro, archivesType, entiyFactory,null);
//	}
	
	private static void add(String name, String tableName, String defScopeType, String defBaseDataPro,String archivesType,String entiyFactory,String editUIName) {
		nameList.add(name);
		tableList.add(tableName);
		defScopeTypeList.add(defScopeType);
		defBaseDataProList.add(defBaseDataPro);
		archivesList.add(archivesType);
		entiyFactoryMap.put(name, entiyFactory);
		editUINameMap.put(name, editUIName);
	}
}
