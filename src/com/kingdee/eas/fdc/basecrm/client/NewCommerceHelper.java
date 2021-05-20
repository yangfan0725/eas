package com.kingdee.eas.fdc.basecrm.client;

import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;


/**
 * �ṩӪ��Ȩ�޵Ŀ���
 * @author eric_wang
 *
 */
public class NewCommerceHelper {
	/**
	 * ��õ�ǰӪ����Ա�ܹ�������Ӫ����Ŀ��view (���������Ŀ��f7�ؼ�) ����һ����Ŀ�����¼�
	 * @return
	 * @throws EASBizException 
	 */
	public static EntityViewInfo getPermitProjectView() throws BOSException, EASBizException {	
		UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",getPermitProjectIdSql(currentUserInfo),CompareType.INNER));
		view.setFilter(filter);
		return view;
	}
	public static String getPermitProjectIdSql(UserInfo userInfo)  throws BOSException, EASBizException{
		return MarketingUnitFactory.getRemoteInstance().getPermitSellProjectIdSql(userInfo);
	}		
	public static EntityViewInfo getPermitSalemanView(SellProjectInfo spInfo,boolean isInclude) throws BOSException, EASBizException {
		return getPermitSalemanView(spInfo,null);
	}	
	/**
	 * ��õ�ǰӪ����Ա�ܹ�������Ӫ�����ʵ�view (������۹��ʵ�f7�ؼ�)
	 * @return
	 * @throws EASBizException 
	 */		
	public static EntityViewInfo getPermitSalemanView(SellProjectInfo spInfo,UserInfo userInfo) throws BOSException, EASBizException {
		UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();		
		if(userInfo!=null) currentUserInfo = userInfo;
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",getPermitSaleManIdSql(spInfo,currentUserInfo),CompareType.INNER));
		view.setFilter(filter);
		return view;
	}	
	
	/**
	 * ���ָ��Ӫ����Ա�ܹ�������Ӫ����Ա��id����  ����ʹ�� getPermitSaleManIdSql
	 * @param spInfo ָ������Ŀ��
	 * @param userInfo Ӫ������
	 * @return Ӫ����Ա��id����
	 */
	public static String getPermitSaleManIdSql(SellProjectInfo spInfo,UserInfo userInfo) throws BOSException, EASBizException
	{
		return MarketingUnitFactory.getRemoteInstance().getPermitSaleManIdSql(userInfo,spInfo);
	}
	public static Set getPermitSaleManIdSet(SellProjectInfo spInfo,UserInfo userInfo) throws BOSException, EASBizException
	{
		return MarketingUnitFactory.getRemoteInstance().getPermitSaleManIdSet(userInfo,spInfo);
	}
	/**
	 * ��õ�ǰӪ����Ա,��ǰ��Ŀ�ܹ������Ŀ͑�����������ֹ�ģ����������
	 * @param sellPorjct
	 * @param UserInfo
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public static EntityViewInfo  getPermitCustomerView(SellProjectInfo sellPorjct,UserInfo userInfo) throws EASBizException, BOSException{
		SaleOrgUnitInfo orgUnitInfo = SysContext.getSysContext().getCurrentSaleUnit();
		
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();		
		if(sellPorjct==null) {
			String permitSaleIdStr = getPermitSaleManIdSql(sellPorjct,userInfo);
			String permitProIdStr = getPermitProjectIdSql(userInfo);
			filter.getFilterItems().add(new FilterItemInfo("createUnit.id",orgUnitInfo.getId().toString()));
			
			filter.getFilterItems().add(new FilterItemInfo("propertyConsultant.id",permitSaleIdStr,CompareType.INNER));			
			//�����Ӫ�����ʵĿͻ�
			String saleSaleIdStr = "select FCustomerID from T_SHE_ShareProperty where FUserID in ("+permitSaleIdStr+")";
			filter.getFilterItems().add(new FilterItemInfo("id",saleSaleIdStr,CompareType.INNER));			
			//�������Ŀ�Ŀͻ�
			String sellProIdStr = "select FCustomerID from T_SHE_ShareSellProject where FSellProjectID in ("+permitProIdStr+") ";
			filter.getFilterItems().add(new FilterItemInfo("id",sellProIdStr,CompareType.INNER));
			
			filter.setMaskString("#0 and (#1 or #2 or #3)");
		}else{
			String permitSaleIdStr = getPermitSaleManIdSql(sellPorjct,userInfo);
			//permitSaleIdStr += " and FSellProjectID = '"+sellPorjct.getId()+"' ";
			
			filter.getFilterItems().add(new FilterItemInfo("createUnit.id",orgUnitInfo.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id",sellPorjct.getId().toString()));
			
			filter.getFilterItems().add(new FilterItemInfo("propertyConsultant.id",permitSaleIdStr,CompareType.INNER));			
			//�����Ӫ�����ʵĿͻ�
			String saleSaleIdStr = "select FCustomerID from T_SHE_ShareProperty where FUserID in ("+permitSaleIdStr+")";
			filter.getFilterItems().add(new FilterItemInfo("id",saleSaleIdStr,CompareType.INNER));			
			//�������Ŀ�Ŀͻ�
			String sellProIdStr = "select FCustomerID from T_SHE_ShareSellProject where FSellProjectID = '"+sellPorjct.getId()+"' ";
			filter.getFilterItems().add(new FilterItemInfo("id",sellProIdStr,CompareType.INNER));
			
			filter.setMaskString("#0 and ((#1 and (#2 or #3)) or #4)  ");			
		}
		
		viewInfo.setFilter(filter);
		return viewInfo;
	}
	/**
	 * ��õ�ǰӪ����Ա�ܹ������Ŀ͑���������ֹ�ģ���������ģ���ǰ������Ա���ܿ�������Ŀ
	 * @param sellPorjct
	 * @param UserInfo
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public static EntityViewInfo  getPermitCustomerView(UserInfo userInfo) throws EASBizException, BOSException{
		return getPermitCustomerView(null,userInfo);
	}
	/**��ǰ����Ȩ�޿���������״̬���վݻ�Ʊ��view*/
	public static EntityViewInfo getPermitCrmChequeView(ChequeTypeEnum chequeType,SellProjectInfo spInfo,String batchNumber)  {	
		//UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
		SaleOrgUnitInfo orgInfo = SysContext.getSysContext().getCurrentSaleUnit();

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("cheque.chequeType",chequeType.getValue()));
		filter.getFilterItems().add(new FilterItemInfo("status",new Integer(ChequeStatusEnum.PICKED_VALUE)));
		filter.getFilterItems().add(new FilterItemInfo("keepOrgUnit.id",orgInfo.getId()));
		if(batchNumber!=null && !"".equals(batchNumber)){
			filter.getFilterItems().add(new FilterItemInfo("cheque.chequeBathNumber",batchNumber,CompareType.EQUALS));
		}
		if(spInfo!=null) {
			filter.getFilterItems().add(new FilterItemInfo("pickSellPro.id",null));
			filter.getFilterItems().add(new FilterItemInfo("pickSellPro.id",spInfo.getId()));
			filter.getFilterItems().add(new FilterItemInfo("pickSellPro.longNumber",spInfo.getLongNumber()+"!%",CompareType.LIKE));
			if(batchNumber!=null && !"".equals(batchNumber)){
				filter.setMaskString("#0 and #1 and #2 and #3 and (#4 or #5 or #6)");
			}else{
				filter.setMaskString("#0 and #1 and #2 and (#3 or #4 or #5)");
			}
		}else{
			filter.getFilterItems().add(new FilterItemInfo("pickSellPro.id",null));
			try {
				UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
				filter.getFilterItems().add(new FilterItemInfo("pickSellPro.id",getPermitProjectIdSql(currentUserInfo),CompareType.INNER));
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			if(batchNumber!=null && !"".equals(batchNumber)){
				filter.setMaskString("#0 and #1 and #2 and #3 and (#4 or #5) ");
			}else{
				filter.setMaskString("#0 and #1 and #2 and (#3 or #4) ");
			}
			
		}
		view.setFilter(filter);
		return view;
	}
	
	public static EntityViewInfo getPermitCrmChequeBatchNumberView(ChequeTypeEnum chequeType,SellProjectInfo spInfo,String batchNumber)  {	
		//UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
		SaleOrgUnitInfo orgInfo = SysContext.getSysContext().getCurrentSaleUnit();

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("chequeType",chequeType.getValue()));
		filter.getFilterItems().add(new FilterItemInfo("chequeDetailEntry.status",new Integer(ChequeStatusEnum.PICKED_VALUE)));
		filter.getFilterItems().add(new FilterItemInfo("keepOrgUnit.id",orgInfo.getId()));
		if(batchNumber!=null && !"".equals(batchNumber)){
			filter.getFilterItems().add(new FilterItemInfo("chequeBathNumber",batchNumber,CompareType.EQUALS));
		}
		if(spInfo!=null) {
			filter.getFilterItems().add(new FilterItemInfo("pickSellPro.id",null));
			filter.getFilterItems().add(new FilterItemInfo("pickSellPro.id",spInfo.getId()));
			filter.getFilterItems().add(new FilterItemInfo("pickSellPro.longNumber",spInfo.getLongNumber()+"!%",CompareType.LIKE));
			if(batchNumber!=null && !"".equals(batchNumber)){
				filter.setMaskString("#0 and #1 and #2 and #3 and (#4 or #5 or #6)");
			}else{
				filter.setMaskString("#0 and #1 and #2 and (#3 or #4 or #5)");
			}
		}else{
			filter.getFilterItems().add(new FilterItemInfo("pickSellPro.id",null));
			try {
				UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
				filter.getFilterItems().add(new FilterItemInfo("pickSellPro.id",getPermitProjectIdSql(currentUserInfo),CompareType.INNER));
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			if(batchNumber!=null && !"".equals(batchNumber)){
				filter.setMaskString("#0 and #1 and #2 and #3 and (#4 or #5) ");
			}else{
				filter.setMaskString("#0 and #1 and #2 and (#3 or #4) ");
			}
			
		}
		view.setFilter(filter);
		return view;
	}
}
