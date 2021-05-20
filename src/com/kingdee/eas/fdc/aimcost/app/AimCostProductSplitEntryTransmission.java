/**
 * 
 */
package com.kingdee.eas.fdc.aimcost.app;

import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.AimCostProductSplitEntryFactory;
import com.kingdee.eas.fdc.aimcost.AimCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.CostEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccount;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.ProductType;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
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
public class AimCostProductSplitEntryTransmission extends AbstractDataTransmission {

	
	/** ����Ʒ���Ͷ�̬ģ��ɱ� */
	private AimCostProductSplitEntryInfo aimCostProductSplitEntry = null;
	
	private CostEntryInfo costEntry = null;
	/** ������Ŀ */
	private CurProjectInfo curProject = null;
	private AimCostInfo aimCostInfo = null;
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
			factory = AimCostProductSplitEntryFactory.getLocalInstance(context);
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
		
		aimCostProductSplitEntry = new AimCostProductSplitEntryInfo();
		
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
			throw new TaskExternalException("������Ŀ���벻��Ϊ�գ�");
		}
		if (StringUtils.isEmpty(costAccountNumber)) {
			throw new TaskExternalException("�ɱ���Ŀ���벻��Ϊ�գ�");
		}
		if (!StringUtils.isEmpty(trendsCostMoney)) {
			// ���ڶ�̬�ɱ�����ֶ��ǷǱ�¼������ڸ��ֶηǿյ�ǰ�����ж��Ƿ�¼���Ϊ�Ϸ�����
			if (!trendsCostMoney.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
				throw new TaskExternalException("��̬�ɱ����¼�벻�Ϸ�,Ӧ��¼�������ͣ�");
			}
		}
//		if (StringUtils.isEmpty(choice)) {
//			throw new TaskExternalException("ѡ����Ϊ�գ�"); // ���ڸ�Ϊ�Ǳ�¼��
//		}
		if (StringUtils.isEmpty(fProductTypeLongnumber)) {
			throw new TaskExternalException("��Ʒ���벻��Ϊ�գ�");
		}
		if (StringUtils.isEmpty(fProductTypeTrends)) {
			throw new TaskExternalException("��Ʒ_��̬����Ϊ�գ�");
		}
		if (!fProductTypeTrends.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
			throw new TaskExternalException("��Ʒ_��̬¼�벻�Ϸ�,Ӧ��¼�������ͣ�");
		}
		
		/**
		 * �Ի�ȡ����Excelģ���е����ݽ������ݶԱ�,�ж��Ƿ������ݿ��д�����Щ����
		 */
		try {
			// 2. ������Ŀ����
			FilterInfo filter1 = new FilterInfo();
			filter1.getFilterItems().add(new FilterItemInfo("longnumber", curProjectLongnumber));
			EntityViewInfo view1 = new EntityViewInfo();
			view1.setFilter(filter1);
			CurProjectCollection curProjectColl = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(view1);
			if (!(curProjectColl.size() > 0)){
				throw new TaskExternalException("�ù�����Ŀ������ϵͳ�в����ڣ�");
			} else {
				curProject = curProjectColl.get(0);
			}
			// 1. �ɱ���Ŀ����
			FilterInfo filter4 = new FilterInfo();
			filter4.getFilterItems().add(new FilterItemInfo("longnumber", costAccountNumber, CompareType.EQUALS));
			filter4.getFilterItems().add(new FilterItemInfo("curproject", curProject.getId().toString(), CompareType.EQUALS));
			EntityViewInfo view4 = new EntityViewInfo();
			view4.setFilter(filter4);
			CostAccountCollection costAccountColl = CostAccountFactory.getLocalInstance(context).getCostAccountCollection(view4);
			if (!(costAccountColl.size() > 0)) {
				throw new TaskExternalException("�óɱ���Ŀ������ϵͳ�в����ڣ�");
			} else {
				costAccount = costAccountColl.get(0);
			}
			
//			<TODO ��ȷ�ϱ�д>
//			FilterInfo filter7 = new FilterInfo();
//			filter7.getFilterItems().add(new FilterItemInfo("orgOrProId", curProject.getId().toString()));
//			filter7.getFilterItems().add(new FilterItemInfo("versionNumber", ""));
//			filter7.getFilterItems().add(new FilterItemInfo("versionName", ""));
//			filter7.setMaskString("#0 and #1 and #2");
//			EntityViewInfo view7 = new EntityViewInfo();
//			view7.setFilter(filter7);
//			SorterItemInfo sort7 = new SorterItemInfo("aimcost.versionNumber");
//			sort7.setSortType(SortType.DESCEND); // desc �������
//			view7.getSorter().add(sort7);
			
//			// 1. ��֯����
//			costCenterOrgUnit = curProject.getCostCenter();
//			// �����Excelģ���еõ�����֯�������Ϊ��ʱ,��ô���Ӹù�����Ŀ��Ӧ�ĳɱ�������֯��ȡ�ı��븳����֯����
//			// �����Excelģ���еõ�����֯�������Ϊ��,��ô������֯������ӹ�����Ŀ��Ӧ�ĳɱ�������֯����ȡ���ı���Ա��Ƿ���ͬ
//			if (!StringUtils.isEmpty(costCenterOrgUnitNumber)) {
//				if (!costCenterOrgUnitNumber.equals(costCenterOrgUnit.getLongNumber())) {
//					throw new TaskExternalException("����֯�����ڸù�����Ŀ����Ӧ�ĳɱ�������֯�в����ڣ�");
//				}
//			}
//			// 3. ������Ŀ����
//			// �����Excelģ���еõ��Ĺ�����Ŀ����Ϊ��ʱ,��ô���Ӹù�����Ŀ�л�ȡ���Ƹ�ֵ
//			// �����Excelģ���еõ��Ĺ�����Ŀ���Ʋ�Ϊ��ʱ,��ô���ù�����Ŀ������ӹ�����Ŀ�л�ȡ�������ƶԱ��Ƿ���ͬ
//			if (!StringUtils.isEmpty(curProjectName)) {
//				if (!curProjectName.equals(curProject.getName())) {
//					throw new TaskExternalException("�˹�����Ŀ������ù�����Ŀ��������Ӧ�����Ʋ���ͬ��");
//				}
//			}
			// 6. ��̬�ɱ���� trendsCostMoney <TODO ��ʱ����ȷ����ֵ�Ĵ�Ŷ���,��ʱ��������>
			// 7. ѡ�� choice <TODO ��ʱ����ȷ����ֵ�Ĵ�Ŷ���,��ʱ��������>
			if (choice.equals("��")) { 
				// ���¼���Ϊ"��"��������Ϊfalse
				
			} else {
				// ���¼���Ϊ"��"��������Ϊtrue������¼��Ϊ��ʱĬ��Ϊture��¼�������ҲĬ��Ϊtrue
				
			}
			// 8. ��Ʒ���� fProductTypeLongnumber <TODO ���в�Ʒ���󣬵������ĵ�Ҫ��Ϊ��Ʒ�����룬�����ڲ�Ʒ�����в����ڳ������ֶΣ��д�ȷ����������ʱ�Ա��봦��>
			FilterInfo filter8 = new FilterInfo();
			filter8.getFilterItems().add(new FilterItemInfo("number", fProductTypeLongnumber));
			EntityViewInfo view8 = new EntityViewInfo();
			view8.setFilter(filter8);
			ProductTypeCollection productTypeColl = ProductTypeFactory.getLocalInstance(context).getProductTypeCollection(view8);
			if (!(productTypeColl.size() > 0)) {
				throw new TaskExternalException("�ò�Ʒ������ϵͳ�в����ڣ����ܵ��룡");
			} else {
				productTypeInfo = productTypeColl.get(0);
			}
			
//			EntityViewInfo view = new EntityViewInfo();
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("costAccount.id", costAccount.getId().toString(), CompareType.EQUALS));
////			filter.getFilterItems().add(new FilterItemInfo("product.id", productTypeInfo.getId().toString(), CompareType.EQUALS));
//			view.setFilter(filter);
//			CostEntryCollection costEntrys = null;
//			costEntrys = CostEntryFactory.getLocalInstance(context).getCostEntryCollection(view);
//			if(costEntrys.size() > 0) {
//				costEntry = costEntrys.get(0);
//			} else {
//				throw new TaskExternalException("�ò��Ŀ��ɱ���¼�����ڣ����ܵ��룡");
//			}
			
			EntityViewInfo aimView = new EntityViewInfo();
			FilterInfo aimFilter = new FilterInfo();
			aimFilter.getFilterItems().add(new FilterItemInfo("orgOrProId", curProject.getId().toString(), CompareType.EQUALS));
			aimFilter.getFilterItems().add(new FilterItemInfo("state", "4AUDITTED", CompareType.EQUALS));
			aimView.setFilter(aimFilter);
			
			SorterItemCollection sorters = aimView.getSorter();
			SorterItemInfo sort = new SorterItemInfo("versionNumber");
			sort.setSortType(SortType.DESCEND);
			sorters.add(sort);
			aimView.setSorter(sorters);
			
			AimCostCollection aimCosts = null;
			aimCosts = AimCostFactory.getLocalInstance(context).getAimCostCollection(aimView);
			if(aimCosts.size() > 0) {
				aimCostInfo = aimCosts.get(0);
			} else {
				throw new TaskExternalException("�ò��Ŀ��ɱ������ڣ����ܵ��룡");
			}

			//�Ȳ�ѯĿ��ɱ�ͷ
			CostEntryCollection costEntrys = aimCostInfo.getCostEntry();
			CostEntryInfo tempCostEntry = new CostEntryInfo();
			if(costEntrys.size()>0)
			{
				ProductTypeInfo typeTemp = null;
				CostAccountInfo accountTemp = null;
				for(int i=0;i<costEntrys.size();i++)
				{
					tempCostEntry = costEntrys.get(i);
					typeTemp = tempCostEntry.getProduct();
					accountTemp = tempCostEntry.getCostAccount();
					if(typeTemp!=null&&accountTemp!=null)
					{
						if(tempCostEntry.getProduct().getId().toString().equals(productTypeInfo.getId().toString())&&tempCostEntry.getCostAccount().getId().toString().equals(costAccount.getId().toString()))
						{
							costEntry = costEntrys.get(i);
							break;
						}
					}
				}
			}
			if(costEntry==null)
			{
				
				throw new TaskExternalException("�ò��Ŀ��ɱ���¼�����ڣ����ܵ��룡");
				
			}
			
			
			
			/**
			 * �����ֲ���ֵ��ŵ�����Ʒ���Ͷ�̬Ŀ��ɱ�<aimCostProductSplitEntry>������
			 */
			aimCostProductSplitEntry.setProduct(productTypeInfo);
			
			SelectorItemCollection appSelector=new SelectorItemCollection();
			appSelector.add("id");
			appSelector.add("targetType.id");
			ApportionTypeInfo apport = new ApportionTypeInfo();
			apport.setId(BOSUuid.read(ApportionTypeInfo.appointType));
			apport = ApportionTypeFactory.getLocalInstance(context).getApportionTypeInfo(new ObjectUuidPK(apport.getId()),appSelector);
			aimCostProductSplitEntry.setApportionType(apport);
			aimCostProductSplitEntry.setSplitAmount(FDCNumberHelper.toBigDecimal(trendsCostMoney));
			aimCostProductSplitEntry.setDirectAmount(FDCNumberHelper.toBigDecimal(trendsCostMoney));
			aimCostProductSplitEntry.setCostEntryId(costEntry.getId().toString());
			aimCostProductSplitEntry.setDescription("isChoose");
			
			
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		
		return aimCostProductSplitEntry;
	}

}
