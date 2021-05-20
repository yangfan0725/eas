/**
 * 
 */
package com.kingdee.eas.fdc.aimcost.app;

import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.DyCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.DynamicCostInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostProductSplitEntryFactory;
import com.kingdee.eas.fdc.aimcost.DynamicCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.aimcost.ProductDynamicCostMap;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.util.StringUtils;

/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		
 *		
 * @description ����Ʒ���Ͷ�̬Ŀ��ɱ�<����Ʒ��̬�ɱ���̯����>_����ʵ����
 * @author		Ӻ����
 * @version		EAS7.0		
 * @createDate	2011-6-29	 
 * @see						
 */
public class DynamicCostProductSplitEntryTransmission extends AbstractDataTransmission {
	// ��Դ�ļ�
	private static String resource = "com.kingdee.eas.fdc.aimcost.AimCostTransmissionResource";
	/** ����Ʒ���Ͷ�̬ģ��ɱ� */
	private DynamicCostProductSplitEntryInfo dynamicCostProductSplitEntryInfo = null;
	
	private DynamicCostInfo dynamicCostInfo = null;
	/** ������Ŀ */
	private CurProjectInfo curProject = null;
	/** ������Ŀ����Ӧ�ĳɱ�����<��֯> */
	private CostCenterOrgUnitInfo costCenterOrgUnit =null;
	/** �ɱ���Ŀ */
	private CostAccountInfo costAccount = null;
	/** ��Ʒ<����> */
	private ProductTypeInfo productTypeInfo = null;
	
	/**
	 * @description		
	 * @author			Ӻ����		
	 * @createDate		2011-6-29
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.AbstractDataTransmission#getController(com.kingdee.bos.Context)					
	 */
	protected ICoreBase getController(Context context) throws TaskExternalException {
		ICoreBase factory = null;
		try {
			factory = DynamicCostProductSplitEntryFactory.getLocalInstance(context);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	/**
	 * @description		
	 * @author			Ӻ����		
	 * @createDate		2011-6-29
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.IDataTransmission#transmit(java.util.Hashtable, com.kingdee.bos.Context)					
	 */
	public CoreBaseInfo transmit(Hashtable hashtable, Context context) throws TaskExternalException {
		
		dynamicCostProductSplitEntryInfo = new DynamicCostProductSplitEntryInfo();
		
		/**
		 * ��ȡExcelģ���е�����
		 */
		// 1. ��֯����
		String costCenterOrgUnitNumber = ((String) ((DataToken) hashtable.get("CostCenterOrgUnit_number")).data).trim();
		// 2. ������Ŀ����
		String curProjectLongnumber = ((String) ((DataToken) hashtable.get("CurProject_longnumber")).data).trim();
		// 3. ������Ŀ����
		String curProjectName = ((String) ((DataToken) hashtable.get("CurProject_name")).data).trim();
		// 4. �ɱ���Ŀ����
		String costAccountNumber = ((String) ((DataToken) hashtable.get("CostAccount_number")).data).trim();
		costAccountNumber = costAccountNumber.replace('!', '.');
		// 5. �ɱ���Ŀ����
		String costAccountName = ((String) ((DataToken) hashtable.get("CostAccount_name")).data).trim();
		// 6. ��̬�ɱ����
		String trendsCostMoney = ((String) ((DataToken) hashtable.get("TrendsCostMoney")).data).trim();
		// 7. ѡ��
		String choice = ((String) ((DataToken) hashtable.get("Choice")).data).trim();
		// 8. ��Ʒ����
		String fProductTypeLongnumber = ((String) ((DataToken) hashtable.get("FProductType_longnumber")).data).trim();
		// 9. ��Ʒ_��̬
		String fProductTypeTrends = ((String) ((DataToken) hashtable.get("FProductType_trends")).data).trim();
		
		/**
		 * �Ի�ȡ����Excelģ���е����ݽ��зǿ�У��
		 */
		if (StringUtils.isEmpty(curProjectLongnumber)) {
			throw new TaskExternalException(getResource(context, "CurProjectNumberNotNull"));
		}
		if (StringUtils.isEmpty(costAccountNumber)) {
			throw new TaskExternalException(getResource(context, "CostAccountNumberNotNull"));
		}
		if (!StringUtils.isEmpty(trendsCostMoney)) {
			// ���ڶ�̬�ɱ�����ֶ��ǷǱ�¼������ڸ��ֶηǿյ�ǰ�����ж��Ƿ�¼���Ϊ�Ϸ�����
			if (!trendsCostMoney.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
				throw new TaskExternalException(getResource(context, "Import_DynamicCostAmount"));
			}
		}
		if (StringUtils.isEmpty(fProductTypeLongnumber)) {
			throw new TaskExternalException(getResource(context, "Import_ProductNumberIsNull"));
		}
		if (StringUtils.isEmpty(fProductTypeTrends)) {
			throw new TaskExternalException(getResource(context, "Import_ProductDynamicIsNull"));
		}
		if (!fProductTypeTrends.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
			throw new TaskExternalException(getResource(context, "Import_ProductDynamicIsError"));
		}
		
		/**
		 * �Ի�ȡ����Excelģ���е����ݽ������ݶԱ�,�ж��Ƿ������ݿ��д�����Щ����
		 */
		try {
			// 2. ������Ŀ����
			FilterInfo filter1 = new FilterInfo();
			filter1.getFilterItems().add(new FilterItemInfo("longnumber", curProjectLongnumber.replace('.', '!')));
			EntityViewInfo view1 = new EntityViewInfo();
			view1.setFilter(filter1);
			CurProjectCollection curProjectColl = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(view1);
			if (!(curProjectColl.size() > 0)){
				throw new TaskExternalException(getResource(context, "CurProjectNumberNotFound"));
			} else {
				curProject = curProjectColl.get(0);
			}
			// 1. �ɱ���Ŀ����
			FilterInfo filter4 = new FilterInfo();
			filter4.getFilterItems().add(new FilterItemInfo("codingnumber", costAccountNumber, CompareType.EQUALS));
			filter4.getFilterItems().add(new FilterItemInfo("curproject", curProject.getId().toString(), CompareType.EQUALS));
			EntityViewInfo view4 = new EntityViewInfo();
			view4.setFilter(filter4);
			CostAccountCollection costAccountColl = CostAccountFactory.getLocalInstance(context).getCostAccountCollection(view4);
			if (!(costAccountColl.size() > 0)) {
				throw new TaskExternalException(getResource(context, "Import_CostAccount_NotExist"));
			} else {
				costAccount = costAccountColl.get(0);
			}
			// 6. ��̬�ɱ���� trendsCostMoney <TODO ��ʱ����ȷ����ֵ�Ĵ�Ŷ���,��ʱ��������>
			// 7. ѡ�� choice <TODO ��ʱ����ȷ����ֵ�Ĵ�Ŷ���,��ʱ��������>
//			if (choice.equals("��")) { 
//				// ���¼���Ϊ"��"��������Ϊfalse
//			} else {
//				// ���¼���Ϊ"��"��������Ϊtrue������¼��Ϊ��ʱĬ��Ϊture��¼�������ҲĬ��Ϊtrue
//				
//			}
			// 8. ��Ʒ���� fProductTypeLongnumber <TODO ���в�Ʒ���󣬵������ĵ�Ҫ��Ϊ��Ʒ�����룬�����ڲ�Ʒ�����в����ڳ������ֶΣ��д�ȷ����������ʱ�Ա��봦��>
			FilterInfo filter8 = new FilterInfo();
			filter8.getFilterItems().add(new FilterItemInfo("number", fProductTypeLongnumber));
			EntityViewInfo view8 = new EntityViewInfo();
			view8.setFilter(filter8);
			ProductTypeCollection productTypeColl = ProductTypeFactory.getLocalInstance(context).getProductTypeCollection(view8);
			if (!(productTypeColl.size() > 0)) {
				throw new TaskExternalException(getResource(context, "Import_Product_NotExist"));
			} else {
				productTypeInfo = productTypeColl.get(0);
			}
			//��ѯ�õ���ͷ
			
			final ProductDynamicCostMap productDynamicCostMap = FDCCostRptFacadeFactory.getLocalInstance(context).getProductDynamicCost(curProject.getId().toString(), null);
			DyCostSplitDataGetter dyGetter= productDynamicCostMap.getDyCostSplitDataGetter();
			if(dyGetter!=null)
			{
				dynamicCostInfo = dyGetter.getDynamicInfo(costAccount.getId().toString());
			}
			
			if(dynamicCostInfo == null)
			{
				throw new TaskExternalException(getResource(context, "Import_Dynamic_NotExist"));
			}
			/**
			 * �����ֲ���ֵ��ŵ�����Ʒ���Ͷ�̬Ŀ��ɱ�<aimCostProductSplitEntry>������
			 */
			dynamicCostProductSplitEntryInfo.setProduct(productTypeInfo);
			
			SelectorItemCollection appSelector=new SelectorItemCollection();
			appSelector.add("id");
			appSelector.add("targetType.id");
			ApportionTypeInfo apport = new ApportionTypeInfo();
			apport.setId(BOSUuid.read(ApportionTypeInfo.appointType));
			apport = ApportionTypeFactory.getLocalInstance(context).getApportionTypeInfo(new ObjectUuidPK(apport.getId()),appSelector);
			dynamicCostProductSplitEntryInfo.setApportionType(apport);
			dynamicCostProductSplitEntryInfo.setSplitAmount(FDCNumberHelper.toBigDecimal(trendsCostMoney));
			dynamicCostProductSplitEntryInfo.setAimCostAmount(FDCNumberHelper.toBigDecimal(trendsCostMoney));
			dynamicCostProductSplitEntryInfo.setAppointAmount(FDCNumberHelper.toBigDecimal(trendsCostMoney));
			dynamicCostProductSplitEntryInfo.setIntendingDirectAmount(FDCNumberHelper.toBigDecimal(trendsCostMoney));
			dynamicCostProductSplitEntryInfo.setHanppenDirectAmount(FDCNumberHelper.toBigDecimal(trendsCostMoney));
			dynamicCostProductSplitEntryInfo.setDynamicCostId(dynamicCostInfo.getId().toString());
			dynamicCostProductSplitEntryInfo.setIsLatestVer(true);
			dynamicCostProductSplitEntryInfo.setDescription("isChoose");
			
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		
		return dynamicCostProductSplitEntryInfo;
	}

	/**
	 * �õ���Դ�ļ�
	 */
	public static String getResource(Context context, String key) {
		return ResourceBase.getString(resource, key, context.getLocale());
	}
}
