/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.FDCBaseCustomerInfo;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerFactory;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerInfo;
import com.kingdee.eas.fdc.basecrm.client.CRMTreeHelper;
import com.kingdee.eas.fdc.basecrm.client.CusClientHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCCustomerEditUI;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basecrm.client.ImportFDCOrgCustomerUI;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basecrm.client.ShareCustomerUI;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceSrcEnum;
import com.kingdee.eas.fdc.sellhouse.MarketUnitControlCollection;
import com.kingdee.eas.fdc.sellhouse.MarketUnitControlFactory;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SharePropertyCollection;
import com.kingdee.eas.fdc.sellhouse.SharePropertyFactory;
import com.kingdee.eas.fdc.sellhouse.SharePropertyInfo;
import com.kingdee.eas.fdc.sellhouse.ShareSellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.ShareSellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.ShareSellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.formula.SellHouseHelper;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CustomerRptListUI extends AbstractCustomerRptListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(CustomerRptListUI.class);
	private Map map = FDCSysContext.getInstance().getOrgMap();
	boolean existNumberRule = CommerceHelper
			.isExistNumberRule(new SHECustomerInfo());
    private boolean isOrgData = false;//�Ƿ�����֯�ͻ�������
    private boolean isPublic = false;//�Ƿ��ǹ����ͻ�������ǹ��е���ֻ�и����˿��Խ���ת��
    private boolean isPrivateValue = true;//�ж϶�ȡ�������ǹ��л���˽��
    private boolean isShowAll=true;
    protected Map permit=new HashMap();
    protected boolean isControl=SHEManageHelper.isControl(null, SysContext.getSysContext().getCurrentUserInfo());
	/**
	 * output class constructor
	 */
	public CustomerRptListUI() throws Exception {
		super();
	}
	protected String[] getLocateNames() {
		String[] locateNames = new String[2];
		locateNames[0] = "name";
		locateNames[1] = "phone";
		return locateNames;
	}
	public void onLoad() throws Exception {
		this.actionQuery.setEnabled(false);
		super.onLoad();
		if (!map.containsKey(SysContext.getSysContext().getCurrentOrgUnit().getId().toString())) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.btnMerge.setEnabled(false);
			this.actionAddToSysCustomer.setEnabled(false);
			this.actionImport.setEnabled(false);
		}else {
			this.actionAddNew.setEnabled(true);
			this.actionEdit.setEnabled(true);
			this.actionRemove.setEnabled(true);
			this.actionImport.setEnabled(true);
		}
		
		btnStatus(true);

		this.btnCommerceChance.setIcon(EASResource
				.getIcon("imgTbtn_synchronization"));
		this.btnTrack.setIcon(EASResource.getIcon("imgTbtn_follow"));
		this.btnQuestionPaper.setIcon(EASResource
				.getIcon("imgTbtn_addcredence"));
		this.btnInsider.setIcon(EASResource.getIcon("imgTbtn_upenumnew"));
		this.btnChangeName.setIcon(EASResource.getIcon("imgTbtn_rename"));
		this.btnUpdate.setIcon(EASResource.getIcon("imgIcon_update"));
		this.btnShare.setIcon(EASResource.getIcon("imgTbtn_sealup"));
		this.btnDeliver.setIcon(EASResource.getIcon("imgTbtn_deliverto"));
		this.btnMerge.setIcon(EASResource.getIcon("imgTbtn_merge"));
		this.btnAddToSysCustomer.setIcon(EASResource.getIcon("imgTbtn_synchronization"));
		initTree();

		tblMain.getColumn("createTime").getStyleAttributes().setNumberFormat(
				"yyyy-MM-dd");
		tblMain.getColumn("lastUpdateTime").getStyleAttributes()
				.setNumberFormat("yyyy-MM-dd");
		
		tblMain.getColumn("trackDate").getStyleAttributes()
		.setNumberFormat("yyyy-MM-dd");

		this.btnImport.setVisible(true);
		this.btnAttachment.setVisible(true);
		this.MenuItemAttachment.setVisible(true);
		this.btnInsider.setVisible(false);
		this.btnMerge.setVisible(false);
		menuItemCommerceChance.setIcon(EASResource.getIcon("imgTbtn_synchronization"));
		menuItemTrack.setIcon(EASResource.getIcon("imgTbtn_follow"));
		menuItemQuestion.setIcon(EASResource.getIcon("imgTbtn_addcredence"));
		menuItemChangeName.setIcon(EASResource.getIcon("imgTbtn_rename"));
		menuItemUpdate.setIcon(EASResource.getIcon("imgIcon_update"));
		menuItemShare.setIcon(EASResource.getIcon("imgTbtn_sealup"));
		menuItemDeliver.setIcon(EASResource.getIcon("imgTbtn_deliverto"));
		menuItemMerge.setIcon(EASResource.getIcon("imgTbtn_merge"));
		menuItemImport.setIcon(EASResource.getIcon("imgTbtn_importexcel"));
		
		this.actionTrack.setEnabled(true);
		this.btnShowAll.setIcon(EASResource.getIcon("imgTbtn_monadismpostil"));
		
		this.actionQuery.setEnabled(true);
		
		this.actionUpdate.setVisible(false);
		
		this.btnShare.setVisible(false);
		this.btnCancel.setVisible(false);
		this.btnCancelCancel.setVisible(false);
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(CRMTreeHelper.getSellProjectTree(actionOnLoad,false));
//		this.tblMain.getDataRequestManager().setDataRequestMode(
//				KDTDataRequestManager.REAL_MODE);
		if(this.treeMain.getRowCount()>0){
			treeMain.setSelectionRow(1); 
		}else{
			treeMain.setSelectionRow(0); 
		}
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if(orgNode != null && orgNode.getUserObject() != null){
			if(orgNode.getUserObject() instanceof OrgStructureInfo) {
				this.actionAddNew.setEnabled(false);
				this.btnCusTransform.setEnabled(false);
				this.actionImport.setEnabled(false);
				if(!isMarketUnitControlUser()){
					this.tblMain.removeRows(false);
					isOrgData = false;
					return;
				}else{
					mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "SHECustomerQuery");
					isOrgData = true;
				}
			}else{
				if (map.containsKey(SysContext.getSysContext().getCurrentOrgUnit().getId().toString())) {
					this.actionAddNew.setEnabled(true);
					this.actionImport.setEnabled(true);
					this.btnCusTransform.setEnabled(true);
					this.actionEdit.setEnabled(true);
					this.actionRemove.setEnabled(true);
				}
				mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "SHECustomerQuery");
				isOrgData = false;
			}
		}
		this.refresh(null);
	}

	/**
	 * �����Զ�����˽���
	 * 
	 */
	protected boolean initDefaultFilter() {
		return true;// Ĭ�ϲ�����
	}

	/**
	 * ��ʼ�����˽���---
	 */
    private CommonQueryDialog queryDlg = null;
	protected CommonQueryDialog initCommonQueryDialog() { //���Ƿ���
		if (queryDlg != null)
			return queryDlg;
		try {
			queryDlg = super.initCommonQueryDialog();
			queryDlg.setOwner(this);
			queryDlg.setParentUIClassName(getClass().getName());
			queryDlg.setQueryObjectPK(mainQueryPK); //mainQueryPK �����Զ�����˵��ֶ�
			queryDlg.setHeight(400);
			queryDlg.setWidth(600);
			queryDlg.addUserPanel(getFilterUI());//������˽���
			queryDlg.setTitle("�ͻ�̨�˹��˲�ѯ");
			queryDlg.setShowSorter(true);
			queryDlg.setUiObject(this);
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		return queryDlg;
	}
	/**
	 * �õ����˽���
	 */
	CustomerRptFilterUI filterUI;
	public CustomerRptFilterUI getFilterUI() throws Exception {
		if (filterUI == null)
			filterUI = new CustomerRptFilterUI();
		return filterUI;
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,EntityViewInfo viewInfo) {
		try {
			DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			FilterInfo filter = new FilterInfo();
			if (orgNode != null) {
				if (orgNode.getUserObject() != null&& orgNode.getUserObject() instanceof OrgStructureInfo) {
					filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("id", "null"));
					this.actionAddNew.setEnabled(false);
					this.actionEdit.setEnabled(false);
					this.actionRemove.setEnabled(false);
				} else if (orgNode.getUserObject() != null&& orgNode.getUserObject() instanceof SellProjectInfo) {
					SellProjectInfo sellProject = (SellProjectInfo) orgNode.getUserObject();
//					Set idRow = new HashSet();
//					idRow = getShareCustomerIdSet(sellProject);
//					idRow = getCustomerId(sellProject);
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
//					if (idRow != null && idRow.size() > 0) {
//					filter.getFilterItems().add(
//							new FilterItemInfo("id", idRow,
//									CompareType.INNER));
//					}else{
//						filter.getFilterItems().add(
//								new FilterItemInfo("id", null,
//										CompareType.EQUALS));
//					}
					if(!isShowAll){
						filter.getFilterItems().add(new FilterItemInfo("id", "select fcustomerid from T_SHE_CommerceChance where fcommerceSrc !='transaction'",CompareType.NOTINNER));
					}
					if(this.btnCusTransform.getText().equals("�鿴˽�пͻ�")){
						filter.getFilterItems().add(new FilterItemInfo("isPublic", Boolean.TRUE));
						filter.getFilterItems().add(new FilterItemInfo("propertyConsultant.id",getPublicSql(null,sellProject),CompareType.INNER));
					}else{
						if(!isControl){
							Set saleMan=SHEManageHelper.getPermitSaleManSet(sellProject,permit);
							filter.getFilterItems().add(new FilterItemInfo("propertyConsultant.id", saleMan,CompareType.INNER));
						}
						filter.getFilterItems().add(new FilterItemInfo("isPublic", Boolean.FALSE));
					}
				}
			}else{
				filter.getFilterItems().add(new FilterItemInfo("id", "null"));
			}
			viewInfo = (EntityViewInfo) mainQuery.clone();
			
			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "AND");
			} else {
				viewInfo.setFilter(filter);
			}

		} catch (Exception e) {
			this.handleException(e);
		}
		
		this.tblMain.getColumn("customerType").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("code").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("simpleCode").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("identity.name").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("firstLinkman").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("email").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("bookedAddress").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("enterpriceProperty.name").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("industry.name").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("isInsider").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("corporate").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("corporateTel").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("description").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("isEnabled").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("isChooseRoom").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("createUnit.name").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("creator.name").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("lastUpdateUnit.name").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("lastUpdateUser.name").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("createWay").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("sellProject.name").getStyleAttributes().setHided(true);
		
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	private String getPublicSql(UserInfo currSaleMan,SellProjectInfo spInfo)throws BOSException, EASBizException {

		OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		if(currSaleMan==null) currSaleMan = SysContext.getSysContext().getCurrentUserInfo();
	
		StringBuffer bufferSql = new StringBuffer();
		bufferSql.append("select fcontrolerid fmemberid from T_SHE_MarketUnitControl where forgUnitId = '"+orgUnit.getId()+"' and fcontrolerid = '"+currSaleMan.getId()+"' ");
		bufferSql.append(" union ");
		bufferSql.append("select distinct(fmemberid) from (");
		bufferSql.append("select MuSp.fsellProjectId,MuMember.fmemberid ");//����Ϊ�ܿ���Աʱ��ȡ��ǰ���¼���֯���������õ��Ŷ��е����г�Ա
		bufferSql.append("from T_SHE_MarketingUnitMember MuMember ");
		bufferSql.append("left join T_SHE_MarketingUnit MuUnit on MuUnit.fid =MuMember.fheadId ");
		bufferSql.append("left join T_SHE_MarketingUnitSellProject MuSp on MuSp.fheadid = MuUnit.fid ");
		bufferSql.append("where MuUnit.FIsEnabled = 1 and MuUnit.forgUnitId = '"+orgUnit.getId()+"' ");
		bufferSql.append("and exists (select * from T_SHE_MarketUnitControl where forgUnitId = '"+orgUnit.getId()+"' and fcontrolerid = '"+currSaleMan.getId()+"') ");
		bufferSql.append(" union ");
		bufferSql.append("select * from (");
		bufferSql.append("select MuSp.fsellProjectId,MuMember.fmemberid  ");//�ǹܿ���Ա������£�1��Ϊ�����ˣ���ǰ��֯�µĸ���������Ŷ��е����г�Ա
		bufferSql.append("from T_SHE_MarketingUnitMember MuMember ");
		bufferSql.append("left join T_SHE_MarketingUnit MuUnit on MuUnit.fid =MuMember.fheadId ");
		bufferSql.append("left join T_SHE_MarketingUnitSellProject MuSp on MuSp.fheadid = MuUnit.fid ");
		bufferSql.append("where MuMember.FHeadID in ( ");
		bufferSql.append("select MuMember.fheadid from T_SHE_MarketingUnitMember MuMember ");
		bufferSql.append("left join T_SHE_MarketingUnit MuUnit on MuUnit.fid =MuMember.fheadId ");
		bufferSql.append("where MuMember.fmemberid = '"+currSaleMan.getId()+"' and MuUnit.forgUnitId = '"+orgUnit.getId()+"' ");
		bufferSql.append("and MuMember.faccessionDate <= getDate() and (MuMember.fdimissionDate is null or MuMember.fdimissionDate >= getDate())  )   ");
		bufferSql.append(") NotControler  where not exists ( ");
		bufferSql.append("select * from T_SHE_MarketUnitControl where forgUnitId = '"+orgUnit.getId()+"' and fcontrolerid = '"+currSaleMan.getId()+"')");
		bufferSql.append(") AllMuUserId where fsellProjectId = '"+spInfo.getId()+"'");
		return bufferSql.toString();
	}
	private Set getShareCustomerIdSet(SellProjectInfo sellProject) throws EASBizException, BOSException{
		Set id = new HashSet();
		
		Set saleMan=SHEManageHelper.getPermitSaleManSet(sellProject,permit);
		
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("customer.id");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("user.id", saleMan, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("customer.sellProject.id", sellProject.getId().toString()));
		view.setFilter(filter);
		view.setSelector(sel);
		
		SharePropertyCollection shareProColl = SharePropertyFactory.getRemoteInstance().getSharePropertyCollection(view);
		for(int i = 0;i<shareProColl.size();i++){
			SharePropertyInfo info=(SharePropertyInfo)shareProColl.get(i);
			if(info.getCustomer()!= null){
				id.add(info.getCustomer().getId().toString());
			}
		}
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("propertyConsultant.id", saleMan,CompareType.INCLUDE));
		sel=new SelectorItemCollection();
		sel.add("id");
		view.setFilter(filter);
		view.setSelector(sel);
		

		SHECustomerCollection customerColl = SHECustomerFactory.getRemoteInstance().getSHECustomerCollection(view);
		for(int i = 0;i<customerColl.size();i++){
			id.add(customerColl.get(i).getId().toString());
		}
		return id;
	}
	/**
	 * ��ѯ���������ļ�¼ ��ǰ��ҵ���ʡ���ѡ��Ŀ��������ǰ��ҵ���ʡ�������ǰ��Ŀ
	 * 
	 * @param sellProjectInfo
	 * @return
	 * @throws BOSException
	 * @throws EASBizException 
	 */
	private Set getCustomerId(SellProjectInfo sellProjectInfo)
			throws BOSException, EASBizException {
		Set rs = new HashSet();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
//		if(isPrivateValue){
		//	UserInfo propertyConsultant = SysContext.getSysContext().getCurrentUserInfo();
			Set propertyConsultantSet = this.getUserIdSet(sellProjectInfo);//��¼���ܿ�����id����
			
			
			//������ҵ�����ǵ�ǰ��ҵ���ʵ�
	//		if(!isDutyMember()){
	//			filter.getFilterItems().add(new FilterItemInfo("user.id", propertyConsultant.getId(), CompareType.EQUALS));
	//		}else{
				filter.getFilterItems().add(new FilterItemInfo("user.id", propertyConsultantSet, CompareType.INCLUDE));
	//		}
			view.setFilter(filter);
			SharePropertyCollection shareProColl = SharePropertyFactory.getRemoteInstance().getSharePropertyCollection(view);
			Set propertyCustomerSet = new HashSet();
			if(shareProColl != null && shareProColl.size() > 0){
				for(int i = 0;i<shareProColl.size();i++){
					SharePropertyInfo  info = (SharePropertyInfo)shareProColl.get(i);
					if(info.getCustomer() != null){
						propertyCustomerSet.add(info.getCustomer().getId());
					}
				}
			}
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", propertyCustomerSet, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectInfo.getId(), CompareType.EQUALS));
			view.setFilter(filter);
			SHECustomerCollection colls = SHECustomerFactory.getRemoteInstance().getSHECustomerCollection(view);
			if(colls != null && colls.size() > 0){
				for(int i = 0;i<colls.size();i++){
					rs.add(colls.get(i).getId());
				}
			}
			
			
			//������Ŀ��
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectInfo.getId(), CompareType.EQUALS));
			view.setFilter(filter);
			ShareSellProjectCollection projectColl = ShareSellProjectFactory.getRemoteInstance().getShareSellProjectCollection(view);
			Set projectCustomerSet = new HashSet();
			if(projectColl != null && projectColl.size() > 0){
				for(int i = 0;i<projectColl.size();i++){
					ShareSellProjectInfo info = (ShareSellProjectInfo)projectColl.get(i);
					if(info.getCustomer() != null){
					    projectCustomerSet.add(info.getCustomer().getId());
					}
				}
			}
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", projectCustomerSet, CompareType.INCLUDE));
	//		if(!isDutyMember()){
	//			filter.getFilterItems().add(new FilterItemInfo("propertyConsultant.id", propertyConsultant.getId(), CompareType.EQUALS));
	//		}else{
				filter.getFilterItems().add(new FilterItemInfo("propertyConsultant.id", propertyConsultantSet, CompareType.INCLUDE));
	//		}
			view.setFilter(filter);
			SHECustomerCollection coll = SHECustomerFactory.getRemoteInstance().getSHECustomerCollection(view);
			if(projectColl != null && coll.size() > 0){
				for(int i = 0;i<coll.size();i++){
					rs.add(coll.get(i).getId());
				}
			}
			
			//��ǰ��ҵ���ʣ���ǰ��Ŀ��
			view = new EntityViewInfo();
			filter = new FilterInfo();
	//		if(!isDutyMember()){
	//			filter.getFilterItems().add(new FilterItemInfo("propertyConsultant.id", propertyConsultant.getId(), CompareType.EQUALS));
	//		}else{
				filter.getFilterItems().add(new FilterItemInfo("propertyConsultant.id", propertyConsultantSet, CompareType.INCLUDE));
	//		}
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectInfo.getId(), CompareType.EQUALS));
			view.setFilter(filter);
			SHECustomerCollection customerColl = SHECustomerFactory.getRemoteInstance().getSHECustomerCollection(view);
			if(customerColl != null && customerColl.size() > 0){
				for(int i = 0;i<customerColl.size();i++){
					rs.add(customerColl.get(i).getId());
				}
			}
			return rs;
//		}
//		else{
//			//���пͻ�
//			view = new EntityViewInfo();
//			filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectInfo.getId(), CompareType.EQUALS));
//			view.setFilter(filter);
//			SHECustomerCollection customerColl = SHECustomerFactory.getRemoteInstance().getSHECustomerCollection(view);
//			if(customerColl != null && customerColl.size() > 0){
//				for(int i = 0;i<customerColl.size();i++){
//					rs.add(customerColl.get(i).getId());
//				}
//			}
//			return rs;
//		}
	}

	private Map linkedCus;

	/**
	 * ����
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		if (linkedCus != null) {
			linkedCus.clear();
		}
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null) {
			Object object = node.getUserObject();
			if (object != null && object instanceof SellProjectInfo) {
				SellProjectInfo sellProjectInfo = (SellProjectInfo) object;
				getUIContext().put("sellProject", sellProjectInfo);				
				linkedCus = CusClientHelper.addNewCusBegin(this, sellProjectInfo.getId().toString());
			}
			
			if(linkedCus != null){
				Boolean isAbort = (Boolean) linkedCus.get("isAbort");
				if(isAbort != null  &&  isAbort.booleanValue()){
					this.abort();
				}
			}
			
		} else {
			MsgBox.showInfo("��ѡ��ڵ�!");
			SysUtil.abort();
		}

		super.actionAddNew_actionPerformed(e);
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		if (linkedCus != null) {
			uiContext.putAll(linkedCus);

			if (linkedCus.get("mainCus") == null) {
				uiContext.remove("mainCus");
				this.getUIContext().remove("mainCus");
			}
		}
	}

	/**
	 * �޸�
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
//		SHECustomerInfo info = getSelectInfo();
//		boolean result = info.isIsEnabled();
//		if (result) {
//			isEditOrDelete(OPERATION_EDIT, "�޸�!");
//		}
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * ɾ��
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
//		SHECustomerInfo info = getSelectInfo();
//		boolean result = info.isIsEnabled();
//		if (result) {
//			isEditOrDelete(OPERATION_EDIT, "ɾ��!");
//		}
		super.actionRemove_actionPerformed(e);
	}

//	private void isEditOrDelete(String operation, String msg) {
//		if (operation.equals(OPERATION_EDIT)) {
//			FDCMsgBox.showWarning(this, "�Ѿ����ã�����" + msg);
//			this.abort();
//		} else if (operation.equals(OPERATION_DELETE)) {
//			FDCMsgBox.showWarning(this, "�Ѿ����ã�����" + msg);
//			this.abort();
//		}
//	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		
		List selsIds = getSelectedIdValues();
		for (int i = 0; i < selsIds.size(); i++) {
			String selId = (String) selsIds.get(i);

			SHECustomerInfo info = new SHECustomerInfo();
			info.setId(BOSUuid.read(selId));
			info.setIsEnabled(true);

			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("isEnabled");
			SHECustomerFactory.getRemoteInstance().updatePartial(info, selector);
		}
		
		this.refresh(e);
		MsgBox.showInfo("��سɹ�");
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List selsIds = getSelectedIdValues();
		for (int i = 0; i < selsIds.size(); i++) {
			String selId = (String) selsIds.get(i);

			SHECustomerInfo info = new SHECustomerInfo();
			info.setId(BOSUuid.read(selId));
			info.setIsEnabled(false);

			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("isEnabled");
			SHECustomerFactory.getRemoteInstance().updatePartial(info, selector);
		}
		
		this.refresh(e);
		MsgBox.showInfo("���ϳɹ�");
	}
	
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		super.tblMain_tableClicked(e);
//		if(isOrderForClickTableHead() && e.getType() == 0 && e.getButton() == 1 && e.getClickCount() == 1)
//	    {
//			 super.tblMain_tableClicked(e);
//	    }
//		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
//			IRow row = this.tblMain.getRow(e.getRowIndex());
//			Object idObj = row.getCell("id").getValue();
//			if (idObj == null) {
//				return;
//			}
//			String idStr = "";
//			if (idObj instanceof String) {
//				idStr = (String) idObj;
//			} else if (idObj instanceof BOSUuid) {
//				idStr = ((BOSUuid) idObj).toString();
//			}
//			if (!idStr.equals("")) {
//				try {
//					UIContext uiContext = new UIContext(this);
//					uiContext.put(UIContext.ID, idStr);
//					IUIWindow uiWindow = null;
//					if(isOrgData){
//						uiContext.put("isOrgData", "isOrgData");
//						uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(FDCCustomerEditUI.class.getName(), uiContext,null, "VIEW");
//					}else{
//						uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(CustomerRptEditUI.class.getName(), uiContext,null, "VIEW");
//					}
//					uiWindow.show();
//					this.execQuery();
//				} catch (UIException e1) {
//					logger.error(e1.getMessage() + "�򿪱༭����ʧ�ܣ�");
//				}
//
//			}
//		}
	}

	// ����ѡ�е���
	private FDCBaseCustomerInfo getSelectInfo() throws EASBizException,
			BOSException {
		List idList = this.getSelectedIdValues();
		if(idList==null){
			FDCMsgBox.showWarning(this,"����ѡ���м�¼!");
		}
		if(idList.size()>0 && idList.get(0) != null){
			Object obj = idList.get(0);
			ObjectUuidPK  pk = new ObjectUuidPK(obj.toString());
			Object objValue = DynamicObjectFactory.getRemoteInstance().getValue(pk.getObjectType(),pk);
			FDCBaseCustomerInfo info = (FDCBaseCustomerInfo)objValue;
			SHECustomerInfo cinfo = (SHECustomerInfo)objValue;
			if(cinfo.isIsPublic()){
				isPublic = true;
			}
	        return info;
		}else{
			return null;
		}
	}

	/**
	 * �̻�
	 */
	public void actionCommerceChance_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		CommerceChanceCollection coll = getCommerceChance();
		if (coll != null && coll.size() > 0) {// ������ڸմ�
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, coll.get(0).getId());
			uiContext.put(CommerceChangeNewEditUI.KEY_DESTORY_WINDOW, Boolean.TRUE);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(CommerceChangeNewEditUI.class.getName(), uiContext,
							null, OprtState.VIEW);
			uiWindow.show();
		} else {// ���������������
			showEditUI(CommerceChangeNewEditUI.class.getName());
		}
	}
	
	/**
	 * �ж���ѡ�ͻ��Ƿ�����̻�
	 * @return
	 * @throws Exception
	 */
	private CommerceChanceCollection getCommerceChance() throws Exception{
		
		List idList = this.getSelectedIdValues();
		if(idList==null){
			FDCMsgBox.showWarning(this,"����ѡ���м�¼!");
		}
		String id = idList.get(0).toString();
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("*");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("customer.id", id, CompareType.EQUALS));
		view.setSelector(sic);
		view.setFilter(filter);

		CommerceChanceCollection coll = CommerceChanceFactory
				.getRemoteInstance().getCommerceChanceCollection(view);
		
		return coll;
	}

	/**
	 * ����
	 * 
	 * @throws Exception
	 * */
	public void actionTrack_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		UIContext uiContext = new UIContext(this);
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("sellProject.*");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", getSelectedKeyValue()));
		view.setSelector(sic);
		view.setFilter(filter);

		SHECustomerInfo info = SHECustomerFactory.getRemoteInstance()
				.getSHECustomerCollection(view).get(0);
		
		CommerceChanceCollection coll = getCommerceChance();
		CommerceChanceInfo commerceChance  = new CommerceChanceInfo();
		if (coll != null && coll.size() > 0) {
			commerceChance = (CommerceChanceInfo)coll.get(0);
		}else{
			FDCMsgBox.showWarning(this,"������д�ͻ��̻���");
			return;
		}
		
		uiContext.put("sheCustomer", info);
		uiContext.put("sellProject", info.getSellProject());
		uiContext.put("SelectCommerceChance", commerceChance);//�̻�
		IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
//		IUIWindow curDialog = uiFactory.create(CluesManageTrakcEditUI.class.getName(), uiContext, null,
//				OprtState.ADDNEW);
		IUIWindow curDialog = uiFactory.create(CommerceChanceTrackEditUI.class.getName(), uiContext, null,
				OprtState.ADDNEW);
		curDialog.show();
		this.refreshList();
	}

	/**
	 * ��д�ʾ�
	 */
	public void actionQuestionPaper_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		CommerceChanceCollection coll = getCommerceChance();
		if (coll.size()==0) {
			FDCMsgBox.showWarning(this,"������д�ͻ��̻���");
			return;
		}
		showEditUI(QuestionPaperAnswerEditUI.class.getName());
	}

	/**
	 * ���
	 * 
	 * @throws Exception
	 * */
	public void actionInsider_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();

		super.actionInsider_actionPerformed(e);
	}

	/**
	 * �ͻ�����
	 */
	public void actionChangeName_actionPerformed(ActionEvent e)
			throws Exception {
		this.checkSelected();
		List idList = this.getSelectedIdValues();
		FDCBaseCustomerInfo info = this.getSelectInfo();
		if(info instanceof SHECustomerInfo){
			SHECustomerChangeNameEditUI.showUI(this, idList);
		} 
		this.refresh(e);
	}

	/**
	 * �ͻ�����
	 */
	public void actionUpdate_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		List cusIds = getSelectedIdValues();
		if (confirmUpdate()) {
			FDCBaseCustomerInfo info = this.getSelectInfo();
			if(info instanceof SHECustomerInfo){
				SHECustomerFactory.getRemoteInstance().updateData(cusIds);
			} else if(info instanceof FDCOrgCustomerInfo) {
				FDCOrgCustomerFactory.getRemoteInstance().updateCustomerInfo(cusIds);
			}
			
			MsgBox.showInfo(this, "�ͻ����³ɹ���");
			this.refreshList();
		}
	}

	private boolean confirmUpdate() {
		if (MsgBox.isYes(MsgBox.showConfirm2(this, "�Ƿ�ȷ�ϸ���?"))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ����
	 */
	public void actionShare_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List idList = this.getSelectedIdValues();
		FDCBaseCustomerInfo info = this.getSelectInfo();
		SellProjectInfo sellProject = info.getSellProject();
		if(info instanceof SHECustomerInfo){
			if(info != null){
				CustomerRptShareEditUI.showUI(this, idList,sellProject);
			}
		} else if(info instanceof FDCOrgCustomerInfo) {
			List cusIds = getSelectedIdValues();
			if(cusIds == null  ||  cusIds.isEmpty()){
				MsgBox.showInfo(this, "��ѡ���¼!");
				this.abort();			
			}
			
			int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
			for (int i = 0; i < selectRows.length; i++) {
				IRow row = tblMain.getRow(selectRows[i]);
				if(row.getCell("isEnabled") == null  ||  !((Boolean)row.getCell("isEnabled").getValue()).booleanValue()){
					MsgBox.showInfo(this, "���ϵĿͻ���������");
					return;
				}
			}
			UIContext uiContext = new UIContext(this);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ShareCustomerUI.class.getName(), uiContext, null, "ADDNEW");
			uiWindow.show();
			
			Map result = uiWindow.getUIObject().getUIContext();
			FullOrgUnitInfo org = (FullOrgUnitInfo) result.get("shareOrg");
			
			if(org == null){
				return ;
			}
			
			FDCOrgCustomerFactory.getRemoteInstance().shareCustomer(cusIds, org.getId().toString());
			MsgBox.showInfo(this, "�ͻ�����ɹ���");
		}
		
		this.actionRefresh_actionPerformed(e);
	}

	/**
	 * ת��
	 * 
	 * @throws Exception
	 * */
	public void actionDeliver_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		List idList = this.getSelectedIdValues();
		FDCBaseCustomerInfo info = this.getSelectInfo();
		SellProjectInfo sellProject = info.getSellProject();
		if(info instanceof SHECustomerInfo){
			if(info != null){
				CustomerRptDeliverEditUI.showUI(this, idList,sellProject);
			}
		} 
		this.actionRefresh_actionPerformed(e);
	}

	/**
	 * �ͻ��ϲ�
	 */
	public void actionMerge_actionPerformed(ActionEvent e) throws Exception {
		List cusIds = getSelectedIdValues();
		if (cusIds == null || cusIds.size() < 2) {
			MsgBox.showInfo(this, "��ѡ�������¼!");
			return;
		}

		FDCBaseCustomerInfo tempInfo = this.getSelectInfo();
		if(tempInfo instanceof SHECustomerInfo){
			Set sellProjectSet = new HashSet();
			Set customerTypeSet = new HashSet();
			for(int i =0;i<cusIds.size();i++){ 
				SHECustomerInfo info = SHECustomerFactory.getRemoteInstance().getSHECustomerInfo(new ObjectUuidPK(cusIds.get(i).toString()));
				if(!info.isIsEnabled()){
					MsgBox.showInfo(this, "�����Ͽͻ������Ժϲ���");
					return;
				}else{
					customerTypeSet.add(info.getCustomerType());
					sellProjectSet.add(info.getSellProject());
				}
			}
			if(sellProjectSet != null && sellProjectSet.size() > 1){
				MsgBox.showInfo(this, "�ͻ��ϲ�����ͬһ��Ŀ�ͻ��ϲ���");
			    return;
			}
			if(customerTypeSet != null && customerTypeSet.size() > 1){
				MsgBox.showInfo(this, "�ͻ��ϲ�����ͬһ���Ϳͻ��ϲ���");
			    return;
			}
			
			EntityViewInfo view = CusClientHelper.getEntityView();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("id", FDCHelper.list2Set(cusIds),
							CompareType.INCLUDE));
			view.setFilter(filter);
			SHECustomerCollection cuses = SHECustomerFactory.getRemoteInstance()
					.getSHECustomerCollection(view);
			IObjectValue cus = CusClientHelper.selectCustomer(this, cuses);
			if (cus == null) {
				return;
			}
			SHECustomerFactory.getRemoteInstance().mergeCustomer(cusIds,
					cus.get("id").toString());
		}
		MsgBox.showInfo(this, "�ͻ��ϲ��ɹ���");
		this.refreshList();
	}

	/**
	 * ����
	 */
	public void actionImport_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put("forSHE", Boolean.TRUE);//�����ж��Ƿ��ǿͻ�̨��
		 SellProjectInfo sellProjectInfo = null;
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
        if (node != null) {
	     Object object = node.getUserObject();
	    if (object != null && object instanceof SellProjectInfo) {
		      sellProjectInfo = (SellProjectInfo) object;
	     }
        }
	    uiContext.put("sellProject", sellProjectInfo);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(ImportFDCOrgCustomerUI.class.getName(), uiContext,
						null, "ADDNEW");
		uiWindow.show();
		this.refreshList();
	}

	/**
	 * �򿪸�������������,�������ܵ���
	 * 
	 * @param name
	 * @throws Exception
	 */
	public void showEditUI(String name) throws Exception {
		UIContext uiContext = new UIContext(this);
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("sellProject.*");
		sic.add("clues.*");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", getSelectedKeyValue()));
		view.setSelector(sic);
		view.setFilter(filter);

		SHECustomerInfo info = SHECustomerFactory.getRemoteInstance()
				.getSHECustomerCollection(view).get(0);
		uiContext.put("sheCustomer", info);
		uiContext.put("sellProject", info.getSellProject());
		uiContext.put(CommerceChangeNewEditUI.KEY_DESTORY_WINDOW, Boolean.TRUE);
		IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow curDialog = uiFactory.create(name, uiContext, null,
				OprtState.ADDNEW);
		curDialog.show();
	}

	/**
	 * ����ÿһ�ʼ�¼�Ƿ����,�Ӷ����ð�ť�Ƿ����
	 */
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		// ------------------
		FDCBaseCustomerInfo info = getSelectInfo();
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		boolean isDuty = SellHouseHelper.getPersonIsDuty(info.getSellProject().getId().toString(), user.getId().toString());
		if(isPublic){
//			if(isDuty){
//				btnDeliver.setEnabled(true);
//			}else{
//				btnDeliver.setEnabled(false);
//			}
			//�����˶��ܽ��и���
			btnDeliver.setEnabled(true);
		}
		// if (!saleOrg.isIsBizUnit()) {
		if(info instanceof SHECustomerInfo){
		if (!map.containsKey(SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString())) {
			btnEdit.setEnabled(false);
			btnRemove.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
			this.actionCancel.setEnabled(false);
			btnStatus(false);
		} else {
			if(info != null){
				if (info.isIsEnabled()) {
					this.actionCancelCancel.setEnabled(false);
					this.actionCancel.setEnabled(true);

				} else {
					this.actionCancelCancel.setEnabled(true);
					this.actionCancel.setEnabled(false);

				}
			}
//			btnStatus(true);
//			btnEdit.setEnabled(true);
//			btnRemove.setEnabled(true);
			/** wyh
			CommerceChanceCollection coll = getCommerceChance();
			if(coll != null && coll.size() > 0){
				this.actionTrack.setEnabled(true);
			}else{
				this.actionTrack.setEnabled(false);
			}
			*/
		}
		}else if(info instanceof FDCOrgCustomerInfo){
			btnStatus(false);
			this.actionShare.setEnabled(true);
			this.actionEdit.setEnabled(true);
			this.actionRemove.setEnabled(true);
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionImport.setEnabled(false);
		}
	}

	public void btnStatus(boolean isEnabled) {
		this.actionCommerceChance.setEnabled(isEnabled);
		this.btnQuestionPaper.setEnabled(isEnabled);
		// this.actionInsider.setEnabled(isEnabled);
		this.actionChangeName.setEnabled(isEnabled);
		this.actionUpdate.setEnabled(isEnabled);
		this.actionShare.setEnabled(isEnabled);
		this.actionDeliver.setEnabled(isEnabled);
		this.actionMerge.setEnabled(isEnabled);
		// this.actionImport.setEnabled(isEnabled);
	}

	protected DataBaseInfo getBaseDataInfo() {
		return new SHECustomerInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SHECustomerFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return CustomerRptEditUI.class.getName();
	}
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	public boolean isIgnoreRowCount() {
		return false;
	}
	protected void refresh(ActionEvent e) throws Exception{
		this.tblMain.removeRows();
	}
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
	
	/**
	 * �жϵ�ǰ�û��Ƿ���Ӫ���ܿ���Ա
	 * @return
	 * @throws BOSException
	 */
	private boolean isMarketUnitControlUser() throws BOSException{
		UserInfo currentUser = SysContext.getSysContext().getCurrentUserInfo();
		FullOrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentSaleUnit().castToFullOrgUnitInfo();
		MarketUnitControlCollection coll = MarketUnitControlFactory.getRemoteInstance().getMarketUnitControlCollection("where controler.id='"+currentUser.getId().toString()+"' and orgUnit.id = '"+orgUnit.getId().toString()+"'");
		if(coll != null && coll.size() > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * ������������ҵ���ʼ���
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private Set getUserIdSet(SellProjectInfo sellProject)throws BOSException, EASBizException{
		Set idSet = new HashSet();
		EntityViewInfo view= NewCommerceHelper.getPermitSalemanView(sellProject,null);
		UserCollection userColl = UserFactory.getRemoteInstance().getUserCollection(view);
		if(userColl != null && userColl.size() > 0){
			for(int i=0;i<userColl.size();i++){
				idSet.add(userColl.get(i).getId());
			}
		}
		//����Լ�
		UserInfo currentInfo = SysContext.getSysContext().getCurrentUserInfo();
		idSet.add(currentInfo.getId());
		return idSet;
	}

	
/**
 * �鿴�����ͻ���˽�пͻ���ת����ť����
 * @author xiaoMin wang
 * 	
 */
	public void actionCusTansform_actionPerformed(ActionEvent e) throws Exception {
//		EntityViewInfo evInfo=new EntityViewInfo();
//		FilterInfo filter=new FilterInfo();
		if(this.btnCusTransform.getText().equals("�鿴�����ͻ�")){
			//filter.getFilterItems().add(new FilterItemInfo("isPublic",new Boolean(true)));
			//evInfo.setFilter(filter);
			//mainQuery=evInfo;
			btnCusTransform.setText("�鿴˽�пͻ�");
			btnAddNew.setEnabled(false);
			btnRemove.setEnabled(false);
//			btnEdit.setEnabled(false);
			//btnCancel.setEnabled(false);
			//btnCancelCancel.setEnabled(false);
			btnChangeName.setEnabled(false);
			btnCommerceChance.setEnabled(false);
//			btnTrack.setEnabled(false);
			btnQuestionPaper.setEnabled(false);
			btnUpdate.setEnabled(false);
			btnShare.setEnabled(false);
			btnDeliver.setEnabled(true);
			btnMerge.setEnabled(false);
			btnImport.setEnabled(false);
			isPrivateValue = false;
			execQuery();
//			PublicCustomerFacadeFactory.getRemoteInstance().changeToPublicCustomer();

		}else if(this.btnCusTransform.getText().equals("�鿴˽�пͻ�")){
//		    filter.getFilterItems().add(new FilterItemInfo("isPublic",new Boolean(false)));//???????Ŀǰ���ݶ���null
//		    filter.getFilterItems().add(new FilterItemInfo("isPublic",null));
//		    evInfo.setFilter(filter);
//		    mainQuery=evInfo;
			btnCusTransform.setText("�鿴�����ͻ�");
			btnAddNew.setEnabled(true);
			btnRemove.setEnabled(true);
			btnEdit.setEnabled(true);
//			btnCancel.setEnabled(true);
//			btnCancelCancel.setEnabled(true);
			btnChangeName.setEnabled(true);
			btnCommerceChance.setEnabled(true);
			btnTrack.setEnabled(true);
			btnQuestionPaper.setEnabled(true);
			btnUpdate.setEnabled(true);
			btnShare.setEnabled(true);
			btnDeliver.setEnabled(true);
			btnMerge.setEnabled(true);
			btnImport.setEnabled(true);
			isPrivateValue = true;
	 		execQuery();
		}
		this.refresh(null);
	}

	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if(orgNode!=null){
   	  	if(orgNode.getUserObject()instanceof OrgStructureInfo){
   	  		MsgBox.showInfo("��ѡ�������Ŀ��");
   	  		return;
   	  		}
		}
    	 super.actionQuery_actionPerformed(e);
	 
	}	
	
	public void actionAddToSysCustomer_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
				AddToSysCustomerUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}
	protected void btnShowAll_actionPerformed(ActionEvent e) throws Exception {
		if(isShowAll){
			this.btnShowAll.setText("��ʾ���пͻ�");
			isShowAll=false;
		}else{
			this.btnShowAll.setText("��ʾ���̻��ͻ�");
			isShowAll=true;
		}
		this.refresh(null);
	}
}