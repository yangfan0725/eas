package com.kingdee.eas.fdc.sellhouse.app;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.CreateWayEnum;
import com.kingdee.eas.fdc.basecrm.CustomerTypeEnum;
import com.kingdee.eas.fdc.basecrm.FDCMainCustomerFactory;
import com.kingdee.eas.fdc.basecrm.FDCMainCustomerInfo;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerCollection;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerFactory;
import com.kingdee.eas.fdc.sellhouse.CluesManageCollection;
import com.kingdee.eas.fdc.sellhouse.CluesManageFactory;
import com.kingdee.eas.fdc.sellhouse.CluesManageInfo;
import com.kingdee.eas.fdc.sellhouse.CluesStatusEnum;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SharePropertyCollection;
import com.kingdee.eas.fdc.sellhouse.SharePropertyFactory;
import com.kingdee.eas.fdc.sellhouse.SharePropertyInfo;
import com.kingdee.eas.fdc.sellhouse.ShareSellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.ShareSellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.ShareSellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.ShareTypeEnum;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.util.IPropertyContainer;
import com.kingdee.util.NumericExceptionSubItem;

public class CluesManageControllerBean extends
		AbstractCluesManageControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.CluesManageControllerBean");

    protected void addnewCheck(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		
	}
    
	/**
	 * �����ͻ�ת��
	 */
	protected void _deliverClues(Context ctx, IObjectValue model, Map map)
			throws BOSException, EASBizException {
		CluesManageInfo info = (CluesManageInfo) model;
		UserInfo userInfo = (UserInfo) map.get("propertyConsultant");
		UserInfo deliverCustomer = info.getPropertyConsultant();
		SharePropertyCollection sharePropertyColl = null;
		if (deliverCustomer != null && userInfo.getId().toString().equals(
				deliverCustomer.getId().toString())) {
			throw new EASBizException(new NumericExceptionSubItem("100",
					"ת������ҵ���ʲ��ܺ�ԭ��ҵ������ͬ!"));
		} else {
			info.setPropertyConsultant(userInfo);
			CluesManageFactory.getLocalInstance(ctx).submit(info);
			sharePropertyColl = info.getShareProperty();
			for (int j = 0; j < sharePropertyColl.size(); j++) {
				SharePropertyInfo sharePropertyInfo = sharePropertyColl.get(j);
				if (userInfo.getId().toString().equals(
						sharePropertyInfo.getUser().getId().toString())) {
					SharePropertyFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(sharePropertyInfo.getId()));
				}
			}
		}
	}
	
	/**
	 * �����ͻ�����
	 */
	protected void _shareClues(Context ctx, IObjectCollection objectColl,
			Map map) throws BOSException, EASBizException {
		CluesManageCollection cluesCustomerColl = (CluesManageCollection)objectColl;
		Map shareMap = (HashMap)map;
		for(int i=0;i<cluesCustomerColl.size();i++){
			CluesManageInfo info = cluesCustomerColl.get(i);
			SharePropertyCollection sharePropertyColl = info.getShareProperty();
			ShareSellProjectCollection shareProjectColl = info.getShareProject();
			List oldShareList = new ArrayList();
			
			if(shareMap.get("shareType").equals(ShareTypeEnum.USERSHARE)){
			for(int j=0;j<sharePropertyColl.size();j++){
				SharePropertyInfo sharePropertyInfo = sharePropertyColl.get(j);
				oldShareList.add(sharePropertyInfo);
			}
			sharePropertyColl.clear();
			List tblShareList = new ArrayList();
			Object[] propertyConsultant = (Object[]) shareMap.get("propertyConsultant");
			if( propertyConsultant != null && propertyConsultant.length > 0){
				for (int j = 0; j < propertyConsultant.length; j++) {
					UserInfo userInfo = (UserInfo)propertyConsultant[j];
					
					SharePropertyInfo sharePropertyInfo = new SharePropertyInfo();
					sharePropertyInfo.setOperationPerson((UserInfo) (SysContext.getSysContext()
							.getCurrentUserInfo()));
					sharePropertyInfo.setShareDate(new Date());
					sharePropertyInfo.setUser(userInfo);
					
					if(info.getPropertyConsultant() != null && userInfo.getId().toString().equals(info.getPropertyConsultant().getId().toString())){
//						MsgBox.showInfo("�������ҵ���ʲ��ܺ�ԭ��ҵ������ͬ!");
//						this.abort();
						throw new EASBizException(new NumericExceptionSubItem("100","�������ҵ���ʲ��ܺ�ԭ��ҵ������ͬ!" ));
						
					}else{
						tblShareList.add(sharePropertyInfo);
						Set set = differentSet(tblShareList,oldShareList,false);
						Iterator iterator = set.iterator();
						while(iterator.hasNext())
						{
							SharePropertyInfo propertyInfo = (SharePropertyInfo)iterator.next();
							info.getShareProperty().add(propertyInfo);
						}
						CluesManageFactory.getLocalInstance(ctx).submit(info);
					}
				}
			}
		}else{
			for(int j=0;j<shareProjectColl.size();j++){
				ShareSellProjectInfo shareProjectInfo = shareProjectColl.get(j);
				oldShareList.add(shareProjectInfo);
			}
			shareProjectColl.clear();
			List tblShareList = new ArrayList();
			Object[] project = (Object[]) shareMap.get("project");
			if( project != null && project.length > 0){
				for (int j = 0; j < project.length; j++) {
					SellProjectInfo sellProjectInfo = (SellProjectInfo)project[j];
					
					ShareSellProjectInfo shareProjectInfo = new ShareSellProjectInfo();
					shareProjectInfo.setShareDate(new Date());
					shareProjectInfo.setSellProject(sellProjectInfo);
					
					if(sellProjectInfo.getId().toString().equals(info.getSellProject().getId().toString())){
						throw new EASBizException(new NumericExceptionSubItem("100","�������Ŀ���ܺ�ԭ��Ŀ��ͬ!" ));
						
					}else{
						tblShareList.add(shareProjectInfo);
						Set set = differentSet(tblShareList,oldShareList,true);
						
						Iterator iterator = set.iterator();
						CluesManageInfo cloneCluesInfo = new CluesManageInfo();
						while(iterator.hasNext())
						{
							ShareSellProjectInfo projectInfo = (ShareSellProjectInfo)iterator.next();
							
							//��Ŀ���������ɸ������ͻ�����һ����������Ŀ�У�Ĭ�����κ���ҵ������Ȩ�ޣ�������Ӫ���ܿ���Ա���з���
							String[] exceptFields = new String[]{"number","shareSellProject","shareProperty"};
							CRMHelper.changeObjectValue((IPropertyContainer) info.clone(),
									cloneCluesInfo, false,exceptFields);
							cloneCluesInfo.setId(BOSUuid.create(cloneCluesInfo.getBOSType()));
							
							ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
							.getLocalInstance(ctx);
							OrgUnitInfo orgUnit = ContextUtil.getCurrentSaleUnit(ctx);
							boolean existCodingRule = iCodingRuleManager.isExist(
									new CluesManageInfo(), orgUnit.getId().toString());
							if (existCodingRule) {
								String retNumber = iCodingRuleManager.getNumber(cloneCluesInfo, orgUnit
										.getId().toString());
								cloneCluesInfo.setNumber(retNumber);
							} else {
								Timestamp time = new Timestamp(new Date().getTime());
								String milliSecond = String.valueOf(time).substring(20, 23);
								String timeStr = String.valueOf(time).replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "").substring(0,14)+milliSecond;
								String number = String.valueOf(timeStr)+"001";
								cloneCluesInfo.setNumber(number);
							}
							
							cloneCluesInfo.setSellProject(sellProjectInfo);
							cloneCluesInfo.setPropertyConsultant(null);//����ҵ����
							cloneCluesInfo.getShareProject().add(projectInfo);
							
							String phone = cloneCluesInfo.getPhone();
					    	SellProjectInfo sellProject = cloneCluesInfo.getSellProject();
					    	CluesManageCollection coll = CluesManageFactory.getLocalInstance(ctx).getCluesManageCollection("where phone = '"+phone+"' and sellProject.id = '"+sellProject.getId().toString()+"'");
							if(coll != null && coll.size() > 0){
								throw new EASBizException(new NumericExceptionSubItem("100",
								"�����ͻ��Ѵ���,�����ظ�!"));
							}
							
							CluesManageFactory.getLocalInstance(ctx).submit(cloneCluesInfo);
						//	info.getShareProject().add(projectInfo);
						}
						
//						String phone = cloneCLuesInfo.getPhone();
//				    	SellProjectInfo sellProject = cloneCLuesInfo.getSellProject();
//				    	CluesManageCollection coll = CluesManageFactory.getLocalInstance(ctx).getCluesManageCollection("where phone = '"+phone+"' and sellProject.id = '"+sellProject.getId().toString()+"'");
//						if(coll != null && coll.size() > 0){
//							throw new EASBizException(new NumericExceptionSubItem("100",
//							"�����ͻ��Ѵ���,�����ظ�!"));
//						}
//						CluesManageFactory.getLocalInstance(ctx).submit(cloneCLuesInfo);
						//CluesManageFactory.getLocalInstance(ctx).submit(info);
					}
				}
			}
		}
		}
	}
		
		 public Set differentSet(List tblShareList, List list,boolean isProject) throws EASBizException {
				Set newSet = new HashSet();
				
				if(!isProject){//������ҵ����
					if (list.size() == 0){
						for (int i = 0; i < tblShareList.size(); i++) {
							SharePropertyInfo sharePropertyInfo = (SharePropertyInfo) tblShareList.get(i);
							newSet.add(sharePropertyInfo);
						}
					}else{
						for (int i = 0; i < tblShareList.size(); i++) {
							SharePropertyInfo sharePropertyInfo = (SharePropertyInfo)tblShareList.get(i);
							UserInfo userInfo = sharePropertyInfo.getUser();
							String userID = userInfo.getId().toString();
							
							if (list.size() > 0) {
								for (int j = 0; j < list.size(); j++) {
									SharePropertyInfo sharePropertyInfo2 = (SharePropertyInfo) list.get(j);
									UserInfo userInfo2 = sharePropertyInfo2.getUser();
									String userID2 = userInfo2.getId().toString();
									// �������ͬ��ȥ����ֵͬ������ֵ�ӵ�set�У������ظ�ֵ
									if (userID.equals(userID2)) {
										throw new EASBizException(new NumericExceptionSubItem("100","�����ͻ��ѹ��������ҵ����,�����ظ�!"));
//										newSet.add(sharePropertyInfo2);
//										//newSet.add(sharePropertyInfo);
//										list.remove(sharePropertyInfo2);
//										break;
										
//										UserInfo operationPerson = sharePropertyInfo.getOperationPerson();
//										UserInfo operationPerson2 = sharePropertyInfo2.getOperationPerson();
//										// ͬһ����������۹��ʵĹ�������˲�����һ���������һ�������õļ�¼�Ͱ�ǰ�����������
//										// Ҳ����˵����м�����ͬʱ��ĳ�ͻ��������ͬһ���˵�ʱ�������Ŀ��Եģ�
//										// ��������˶Ըÿͻ�ӵ�е�Ȩ��
//										// ȡ���Ȩ�ޣ�����Ĺ����ŶӺ���֯��һ����
//										if(operationPerson == null || operationPerson2 == null){
//											return null;
//										}
//										if (operationPerson.getId().toString().equals(operationPerson2.getId().toString())) {
//											newSet.add(sharePropertyInfo);
//											list.remove(sharePropertyInfo2);
//											break;
//										} else {
//											newSet.add(sharePropertyInfo);
//										}
									} else {
										newSet.add(sharePropertyInfo);
									}
								}
							}else {
								newSet.add(sharePropertyInfo);
							}
						}
						if (list.size() > 0) {
							for (int m = 0; m < list.size(); m++) {
								SharePropertyInfo sharePropertyInfo = (SharePropertyInfo) list.get(m);
								newSet.add(sharePropertyInfo);
							}
						}
					}
				}else{//������Ŀ
					if (list.size() == 0){
						for (int i = 0; i < tblShareList.size(); i++) {
							ShareSellProjectInfo shareProjectInfo = (ShareSellProjectInfo) tblShareList.get(i);
							newSet.add(shareProjectInfo);
						}
					}else{
						for (int i = 0; i < tblShareList.size(); i++) {
							ShareSellProjectInfo shareProjectInfo = (ShareSellProjectInfo) tblShareList.get(i);
							SellProjectInfo projectInfo = shareProjectInfo.getSellProject();
							String projectID = projectInfo.getId().toString();
							
							if (list.size() > 0) {
								for (int j = 0; j < list.size(); j++) {
									ShareSellProjectInfo shareProjectInfo2 = (ShareSellProjectInfo) list.get(j);
									SellProjectInfo projectInfo2 = shareProjectInfo2.getSellProject();
									String projectID2 = projectInfo2.getId().toString();
									if (projectID.equals(projectID2)) {
										throw new EASBizException(new NumericExceptionSubItem("100","�����ͻ��ѹ��������Ŀ,�����ظ�!"));
//										UserInfo operationPerson = shareProjectInfo.getOperationPerson();
//										UserInfo operationPerson2 = shareProjectInfo2.getOperationPerson();
//										
//										if (operationPerson.getId().toString().equals(operationPerson2.getId().toString())) {
//											newSet.add(shareProjectInfo);
//											list.remove(shareProjectInfo2);
//											break;
//										} else {
//											newSet.add(shareProjectInfo);
//										}
									} else {
										newSet.add(shareProjectInfo);
									}
								}
							}else {
								newSet.add(shareProjectInfo);
							}
						}
						if (list.size() > 0) {
							for (int m = 0; m < list.size(); m++) {
								ShareSellProjectInfo shareProjectInfo = (ShareSellProjectInfo) list.get(m);
								newSet.add(shareProjectInfo);
							}
						}
					}
				
				
				}
				return newSet;
		    }
	
	/**
	 * ת����ʱ��д�����ͻ���״̬�������浽��ʵ�ͻ�
	 */

	protected IObjectValue _updateCluesStatus(Context ctx, IObjectValue model,String firstLinkMan)
			throws BOSException, EASBizException {

		CluesManageInfo cluesInfo = (CluesManageInfo)model;
		if(cluesInfo != null ){
//			if(status.equals(CluesStatusEnum.PREPURCHASE.toString())){
//				cluesInfo.setCluesStatus(CluesStatusEnum.PREPURCHASE);
//			}else if(status.equals(CluesStatusEnum.PURCHASE.toString())){
//				cluesInfo.setCluesStatus(CluesStatusEnum.PURCHASE);
//			}else if(status.equals(CluesStatusEnum.SIGN.toString())){
//				cluesInfo.setCluesStatus(CluesStatusEnum.SIGN);
//			}
			cluesInfo.setCluesStatus(CluesStatusEnum.CUSTOMER);
			CluesManageFactory.getLocalInstance(ctx).submit(cluesInfo);
			
			//�����ʵ�ͻ�
			//saveSHECustomer(ctx,cluesInfo);
			
			SHECustomerInfo sheCustomerInfo = saveSHECustomer(ctx,cluesInfo,firstLinkMan);
		    return sheCustomerInfo;
			//info.setCustomer(sheCustomerInfo);
		}
		return null;
	}

	/**
	 * @author youzhen_qin 
	 * @date 20110720
	 * �������ͻ������ʵ�ͻ�
	 * ������,�ʾ�ת�Ƶ��ÿͻ���
	 * @param cluesInfo
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private SHECustomerInfo saveSHECustomer(Context ctx,CluesManageInfo cluesInfo,String firstLinkMan)throws BOSException, EASBizException {
		if(cluesInfo != null){
			SHECustomerInfo sheCustomerInfo = new SHECustomerInfo();
			sheCustomerInfo.setName(cluesInfo.getName());
			sheCustomerInfo.setPhone(cluesInfo.getPhone());
			sheCustomerInfo.setSellProject(cluesInfo.getSellProject());
			sheCustomerInfo.setPropertyConsultant(cluesInfo.getPropertyConsultant());
			sheCustomerInfo.setDescription(cluesInfo.getDescription());
			sheCustomerInfo.setCreateTime(new Timestamp(new Date().getTime()));
			sheCustomerInfo.setCreator(ContextUtil.getCurrentUserInfo(ctx));
			sheCustomerInfo.setCreateUnit(ContextUtil.getCurrentOrgUnit(ctx).castToFullOrgUnitInfo());
			sheCustomerInfo.setLastUpdateUnit(ContextUtil.getCurrentOrgUnit(ctx).castToFullOrgUnitInfo());
			sheCustomerInfo.setLastUpdateTime(new Timestamp(new Date().getTime()));
			sheCustomerInfo.setLastUpdateUser(ContextUtil.getCurrentUserInfo(ctx));
			sheCustomerInfo.setCreateWay(CreateWayEnum.HAND);// ������ʽ:�ֹ�����
			sheCustomerInfo.setIsEnabled(true);
			sheCustomerInfo.setFirstConsultant(cluesInfo.getPropertyConsultant());
			if(firstLinkMan == null || firstLinkMan.equals("")){
				sheCustomerInfo.setCustomerType(CustomerTypeEnum.PERSONALCUSTOMER);
			}else{
				sheCustomerInfo.setFirstLinkman(firstLinkMan);
				sheCustomerInfo.setCustomerType(CustomerTypeEnum.ENTERPRICECUSTOMER);
			}
			
			sheCustomerInfo.setId(BOSUuid.create(sheCustomerInfo.getBOSType()));
			sheCustomerInfo.setClues(cluesInfo);
			
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
			.getLocalInstance(ctx);
			OrgUnitInfo orgUnit = ContextUtil.getCurrentSaleUnit(ctx);
			boolean existCodingRule = iCodingRuleManager.isExist(
					new FDCOrgCustomerInfo(), orgUnit.getId().toString());
			if (existCodingRule) {
				String retNumber = iCodingRuleManager.getNumber(sheCustomerInfo, orgUnit
						.getId().toString());
				sheCustomerInfo.setNumber(retNumber);
			} else {
				Timestamp time = new Timestamp(new Date().getTime());
				String milliSecond = String.valueOf(time).substring(20, 23);
				String timeStr = String.valueOf(time).replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "").substring(0,14)+milliSecond;
				String number = String.valueOf(timeStr)+"001";
				sheCustomerInfo.setNumber(number);
			}
			
			//�ͻ�����
			FDCMainCustomerInfo mainCus = sheCustomerInfo.getMainCustomer();
			if (mainCus == null) {
				mainCus = new FDCMainCustomerInfo();
				//mainCus.putAll((IObjectValue) sheCustomerInfo.clone());
				CRMHelper.changeObjectValue((IPropertyContainer) sheCustomerInfo.clone(),
						mainCus, true);
				mainCus.setId(BOSUuid.create(mainCus.getBOSType()));
				sheCustomerInfo.setMainCustomer(mainCus);
			}
			
    		FDCMainCustomerFactory.getLocalInstance(ctx).submit(mainCus);
    		SHECustomerFactory.getLocalInstance(ctx).addnew(sheCustomerInfo);
			
			//����ҵ����ת��
			SharePropertyCollection shareIds = SharePropertyFactory.getLocalInstance(ctx).getSharePropertyCollection(" select id  where property.id = '"+cluesInfo.getId()+"'");
			if(shareIds != null  && shareIds.size() > 0){
				try{
					String shareSql = "update T_SHE_ShareProperty set fcustomerid=? where fpropertyid=?";
					FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
					sqlBuilder.setPrepareStatementSql(shareSql);
					sqlBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
					for (int i = 0; i < shareIds.size(); i++) {
						DbUtil.execute(ctx, shareSql, new Object[] {sheCustomerInfo.getId().toString(),cluesInfo.getId().toString()});
					}
				}catch (BOSException ex) {
					logger.error(ex.getMessage() + "������ҵ����ʧ��!");
					throw new BOSException(ex.getMessage() + "������ҵ����ʧ��!");
				}
			}
//			String shareSql = "update T_SHE_ShareProperty set fcustomerid=? where fpropertyid=?";
//			Object[] shareParams = new Object[2];
//			if(shareIds != null  && shareIds.size() > 0){
//				for(int i=0; i < shareIds.size(); i++){
//					String id = shareIds.get(i).toString();
//					shareParams[1] = sheCustomerInfo.getId();
//					shareParams[2] = id;
//					DbUtil.execute(ctx, shareSql, shareParams);
//				}
//			}
			
			//��������Ŀת��
			ShareSellProjectCollection sellProjectIds = ShareSellProjectFactory.getLocalInstance(ctx).getShareSellProjectCollection(" select id  where shareProject.id = '"+cluesInfo.getId()+"'");
//			String sellProjectSql = "update T_SHE_ShareSellProject set fcustomerid=? where FShareProjectID=?";
//			Object[] sellProjectParams = new Object[2];
//			if(sellProjectIds != null && sellProjectIds.size() >0){
//				for(int i=0; i<sellProjectIds.size(); i++){
//					String id = sellProjectIds.get(i).toString();
//					sellProjectParams[1] = sheCustomerInfo.getId();
//					sellProjectParams[2] = id;
//					DbUtil.execute(ctx, sellProjectSql, sellProjectParams);
//				}
//			}
			if(sellProjectIds != null  && sellProjectIds.size() > 0){
				try{
					String sellProjectSql = "update T_SHE_ShareSellProject set fcustomerid=? where FShareProjectID=?";
					FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
					sqlBuilder.setPrepareStatementSql(sellProjectSql);
					sqlBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
					for (int i = 0; i < sellProjectIds.size(); i++) {
						DbUtil.execute(ctx, sellProjectSql, new Object[] {sheCustomerInfo.getId().toString(),cluesInfo.getId().toString()});
					}
				}catch (BOSException ex) {
					logger.error(ex.getMessage() + "������Ŀʧ��!");
					throw new BOSException(ex.getMessage() + "������Ŀʧ��!");
				}
			}
			
			//���ʾ�ת��
			QuestionPaperAnswerCollection questionIds = QuestionPaperAnswerFactory.getLocalInstance(ctx).getQuestionPaperAnswerCollection(" select id  where cluesManage = '"+cluesInfo.getId()+"'");
			if(questionIds != null  && questionIds.size() > 0){
			try {
				String questionSql = "update T_MAR_QuestionPaperAnswer set fshecustomerid=? where FCluesManageID=?";
				FDCSQLBuilder sqlBuilder1 = new FDCSQLBuilder(ctx);
				sqlBuilder1.setPrepareStatementSql(questionSql);
				sqlBuilder1.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
				
				for (int i = 0; i < questionIds.size(); i++) {
					DbUtil.execute(ctx, questionSql, new Object[] {sheCustomerInfo.getId().toString(),cluesInfo.getId().toString()});
				}
			} catch (BOSException ex) {
				logger.error(ex.getMessage() + "�����ʾ�ʧ��!");
				throw new BOSException(ex.getMessage() + "�����ʾ�ʧ��!");
			}
			}
			return sheCustomerInfo;
		}
		return null;
	}

	protected void _importClues(Context ctx,IObjectCollection res) throws BOSException, EASBizException {
		for (int i = 0; i < res.size(); i++) {
			CluesManageInfo info = (CluesManageInfo)res.getObject(i);
			this._addnew(ctx, info);
		}
	}

	
	/**
//	 * �����ͻ�����
//	 */
//	protected void _importClues(Context ctx,List list,int rowCount) throws BOSException,
//			EASBizException {
//		if (list != null && list.size() > 0) {
//			for (int j = 0; j < list.size(); j++) {
//				for (int k = 0; k < j; k++) {
//					CluesManageInfo info = (CluesManageInfo) list.get(j);
//					CluesManageInfo info2 = (CluesManageInfo) list.get(k);
//					if (info.getNumber().equals(info2.getNumber())) {
//						throw new EASBizException(new NumericExceptionSubItem("100","��������" + info.getNumber() + "�����ظ�" ));
//					} else if (info.getPhone().equals(info2.getPhone())) {
//						throw new EASBizException(new NumericExceptionSubItem("100","��ϵ�绰" + info.getPhone() + "�����ظ�" ));
//					}
//				}
//			}
//		}
//		// �����е���֤ͨ��,���������
//		if (list != null && list.size() > 0
//				&& list.size() == rowCount) {
//			for (int j = 0; j < list.size(); j++) {
//				CluesManageInfo info = (CluesManageInfo) list.get(j);
//				CluesManageFactory.getLocalInstance(ctx).save(info);
//			}
//		}else{
//			throw new EASBizException(new NumericExceptionSubItem("100","�����ͻ�����ʧ��!" ));
//		}
//	}
}