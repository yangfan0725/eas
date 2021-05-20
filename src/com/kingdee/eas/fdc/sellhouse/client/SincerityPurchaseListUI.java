/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeBizTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FunctionSetting;
import com.kingdee.eas.fdc.sellhouse.ISincerityPurchase;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.NumericExceptionSubItem;
/**
 * output class name
 */
public class SincerityPurchaseListUI extends AbstractSincerityPurchaseListUI {
	private static final Logger logger = CoreUIObject.getLogger(SincerityPurchaseListUI.class);
    protected boolean isSaleHouseOrg= FDCSysContext.getInstance().getOrgMap().containsKey(SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
	protected SellProjectInfo sellProject = null;
	Map orgMap = FDCSysContext.getInstance().getOrgMap();
	protected boolean isControl=SHEManageHelper.isControl(null, SysContext.getSysContext().getCurrentUserInfo());
	/**
	 * output class constructor
	 */
	public SincerityPurchaseListUI() throws Exception {
		super();
	}
	
	protected void initTree() throws Exception {
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}
	public void onLoad() throws Exception {
		initTree();
		super.onLoad();
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		initControl();
		//����������֯�ж�,��Ϊ��¥��֯
		FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
		String idStr =null;
		if(null!= orgUnit.getId()){
			idStr = orgUnit.getId().toString();
		}
		if(null==orgMap.get(idStr)){
		
			this.btnPrePur.setEnabled(false);
			this.actionPrePur.setEnabled(false);
			this.btnSignContract.setEnabled(false);
			this.actionSignContract.setEnabled(false);
			this.btnToPurchase.setEnabled(false);
		}
		this.btnChangeRecord.setIcon(EASResource.getIcon("imgTbtn_seeevaluateobject"));
		
		this.actionRevAmount.setVisible(false);
		this.btnToMT.setIcon(EASResource.getIcon("imgTbtn_input"));
	}
	protected void initControl(){
    	this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
    	this.treeMain.setSelectionRow(0);
    	   
    	if(!isSaleHouseOrg){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionQuitNumber.setEnabled(false);
			this.actionToPurchase.setEnabled(false);
			this.actionRevAmount.setEnabled(false);
			this.actionQuitAmount.setEnabled(false);
			this.actionBatchPurchase.setEnabled(false);
			
			this.actionPrePur.setEnabled(false);
			this.actionSignContract.setEnabled(false);
			this.actionToPurchase.setEnabled(false);
			this.actionChangeRecord.setEnabled(false);
			
		}
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.menuSincerPrint.setEnabled(true);
		this.menuSincerPrintPreview.setEnabled(true);
		this.btnSincerPrint.setEnabled(true);
		this.btnSincerPrintPreview.setEnabled(true);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.menuBar.remove(this.menuHelp);
		this.menuBar.add(this.menuHelp);
		
		
		//���ò���֮ǰ��ť
		this.btnBatchPurchase.setEnabled(false);
		this.btnSincerPrint.setEnabled(false);
		this.btnSincerPrintPreview.setEnabled(false);
		this.btnBatchPurchase.setVisible(false);
		this.btnSincerPrint.setVisible(false);
		this.btnSincerPrintPreview.setVisible(false);
		
		FDCHelper.formatTableDate(this.tblMain, "bizDate");
		FDCHelper.formatTableDate(this.tblMain, "validDate");
		FDCHelper.formatTableDate(this.tblMain, "pur.bizDate");
		FDCHelper.formatTableDate(this.tblMain, "pur.busAdscriptionDate");
		FDCHelper.formatTableDate(this.tblMain, "sign.bizDate");
		FDCHelper.formatTableDate(this.tblMain, "sign.busAdscriptionDate");
		
    }
	
//	public void checkIsValid(){
//		for(int i=0 ;i <this.tblMain.getRowCount() ; i++){
//			IRow row = this.tblMain.getRow(i);
//			Date validDate = FDCDateHelper.stringToDate(row.getCell("validDate").getValue().toString());
//			
//			if(SHEHelper.getCurrentTime().after(validDate)){
//				row.getStyleAttributes().setBackground(Color.red);
//			}
//		}
//	}
	
	protected void initListener() {
		super.initListener();

		/**
		 * �����ݵĴ������Ż����� by renliang at 2011-3-23
		 */
		this.tblMain.getDataRequestManager().addDataFillListener(
				new KDTDataFillListener() {
					public void afterDataFill(KDTDataRequestEvent e) {
						tblMain_afterDataFill(e);
					}
				});
	}

	private void tblMain_afterDataFill(KDTDataRequestEvent e) {

		// ȡ�õ�ǰҳ�ĵ�һ��
		int sr = e.getFirstRow();
		if (e.getFirstRow() > 0) {
			sr = sr - 1;
		}
		// ȡ�õ�ǰҳ�����һ��
		int lr = e.getLastRow();

		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for (int i = sr; i <= lr; i++) {
			IRow row = tblMain.getRow(i);
			if (row == null) {
				continue;
			}
			BizEnumValueInfo state = (BizEnumValueInfo) row.getCell("sincerityState").getValue();
			if(state==null) continue;
    		if(state.getValue().equals(TransactionStateEnum.BAYNUMBER_VALUE)||state.getValue().equals(TransactionStateEnum.WAITTINGFORDEAL_VALUE)){
			Date validDate = FDCDateHelper.stringToDate(row.getCell("validDate").getValue().toString());
			if(SHEHelper.getCurrentTime().after(validDate)){
				row.getStyleAttributes().setBackground(Color.PINK);
			}
    		}
		}
	}
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
		//this.execQuery();
	}
	
	/*
	 * ������¼
	 */
	public void actionChangeRecord_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();// ����Ƿ�ѡ��
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if(row == null){
			MsgBox.showWarning(this, "��ѡ��Ҫ�������Ϲ�����");
			SysUtil.abort();
		}
//		if(row.getCell("sincerityState").getValue().toString().equals(SincerityPurchaseStateEnum.ArrangeNum.toString())){
		if(row.getCell("sincerityState").getValue().toString().equals(TransactionStateEnum.BAYNUMBER.toString())){	
//		Map map = new UIContext(this);
			UIContext uiContext = new UIContext(this);
		uiContext.put("selectedID", row.getCell("id").getValue().toString());
//		uiContext.put("sinPurID", row.getCell("id").getValue().toString());
//			uiContext.put(UIContext.ID, getSelectedKeyValue());

			// �����ඨ��Ҫ���ݵ�EditUI����չ������
			prepareUIContext(uiContext, e);
			IUIWindow uiWindow = null ; 
			// UIFactoryName.MODEL Ϊ����ģʽ
			uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).
			create("com.kingdee.eas.fdc.sellhouse.client.SincerityChangeNameEditUI",uiContext, null,
							OprtState.ADDNEW);
			uiWindow.show();
		}
		
	}
	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SincerityPurchaseFactory.getRemoteInstance();
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		
		checkSelected();// ����Ƿ�ѡ��
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if(row == null){
			MsgBox.showWarning(this, "��ѡ��Ҫ�������Ϲ�����");
			SysUtil.abort();
		}
		if(null!=row.getCell("sincerityState").getValue()){
			if(row.getCell("sincerityState").getValue().toString().equals(TransactionStateEnum.BAYNUMBER.toString())){
				btnChangeRecord.setEnabled(true);
			}else{
				btnChangeRecord.setEnabled(false);
			}
		}
		
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return SincerityPurchaseChangeNameUI.class.getName();
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		sellProject=null;
		if (node.getUserObject() instanceof SellProjectInfo){
			//��Ŀ
			if(node.getUserObject() != null){
				sellProject=(SellProjectInfo) node.getUserObject();
			}			
		}
		if (node.getUserObject() instanceof OrgStructureInfo){
			this.actionAddNew.setEnabled(false);
		}else if(node.getUserObject() instanceof SellProjectInfo){
			if(SHEManageHelper.isSellProjectHasChild((SellProjectInfo) node.getUserObject())){
				this.actionAddNew.setEnabled(false);
			}else if(isSaleHouseOrg){
				this.actionAddNew.setEnabled(true);
			}
		}else{
			if(isSaleHouseOrg){
				this.actionAddNew.setEnabled(true);
			}
		}
		this.refresh(null);
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		try	{
			FilterInfo filter = new FilterInfo();
			if(node!=null){
				if (node.getUserObject() instanceof SellProjectInfo){
					//��Ŀ
					if(node.getUserObject() != null){
						filter.getFilterItems().add(new FilterItemInfo("sellProject.id", SHEManageHelper.getAllSellProjectCollection(null,sellProject),CompareType.INCLUDE));	
					}
					if(!isControl){
						filter.getFilterItems().add(new FilterItemInfo("saleMansEntry.user.id", NewCommerceHelper.getPermitSaleManIdSql(sellProject, SysContext.getSysContext().getCurrentUserInfo()),CompareType.INNER));
					}
				}else if (node.getUserObject() instanceof OrgStructureInfo){
					//��֯
					if(node.getUserObject() != null){
						filter.getFilterItems().add(new FilterItemInfo("sellProject.id", "'null'",CompareType.INNER));
					}
				}
			}
			
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
		}catch (Exception e)
		{
			handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		uiContext.put("sellProject", sellProject);
	}

	
	
	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
		//checkIsValid();
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		String id = this.getSelectedKeyValue();
		if (id == null) {
			return;
		}
		SincerityPurchaseInfo sin = SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseInfo(new ObjectUuidPK(BOSUuid.read(id)));
		if(sin.getBizState()!=null) {
			if (sin.getBizState().equals(TransactionStateEnum.QUITNUMBER)) {
				MsgBox.showInfo("�Ѿ��˺�,�����޸�!");
				return;
			}
			if (sin.getBizState().equals(TransactionStateEnum.TOPUR)) {
				MsgBox.showInfo("�Ѿ�ת�Ϲ�,�����޸�!");
				return;
			}
			if (sin.isIsRev()) {
				MsgBox.showInfo("�Ѿ��տ�,�����޸�!");
				return;
			}
		}
		super.actionEdit_actionPerformed(e);
	}

	public void actionQuitNumber_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionQuitNumber_actionPerformed(e);
		int index = this.tblMain.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			return;
		}
		IRow row = this.tblMain.getRow(index);
		BizEnumValueInfo state = (BizEnumValueInfo) row.getCell("sincerityState").getValue();
		if (state.getValue().equals(TransactionStateEnum.QUITNUMBER_VALUE)) {
			MsgBox.showInfo("�Ѿ��˺�!");
			return;
		}
		if (!state.getValue().equals(TransactionStateEnum.BAYNUMBER_VALUE)) {
			MsgBox.showInfo("�˵���״̬���ʺ������β���!");
			return;
		}
		if (MsgBox.showConfirm2New(this, "�Ƿ�ȷ���˺�?") == MsgBox.YES) {
			
			String	param="false";
			try {
				param = ParamControlFactory.getRemoteInstance().getParamValue(new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId()), "YF_WF");
			} catch (EASBizException e1) {
				e1.printStackTrace();
			} catch (BOSException e1) {
				e1.printStackTrace();
			}
			if("true".equals(param)){
				SelectorItemCollection sic=new SelectorItemCollection();
				sic.add("*");
				sic.add("sellProject.number");
				sic.add("room.number");
				sic.add("room.sellState");
				sic.add("customer.isMain");
				sic.add("customer.customer.number");
				sic.add("room.productType.name");
				sic.add("saleMansEntry.user.number");
				sic.add("sincerPriceEntrys.appAmount");
				SincerityPurchaseInfo info=SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseInfo(new ObjectUuidPK(BOSUuid.read((String) row.getCell(
				"id").getValue())),sic);
				
				JSONArray carr=new JSONArray();
				JSONObject cjo=new JSONObject();
				
				cjo.put("type", "0");
				
				String customerId="";
				for(int i=0;i<info.getCustomer().size();i++){
					if(i==0){
						customerId=info.getCustomer().get(i).getCustomer().getNumber();
					}else{
						customerId=customerId+"@"+info.getCustomer().get(i).getCustomer().getNumber();
					}
					if(info.getCustomer().get(i).isIsMain()){
						cjo.put("qdCustId",info.getCustomer().get(i).getCustomer().getNumber());
					}
				}
				cjo.put("id", info.getNumber());
				cjo.put("customerId", customerId);
				cjo.put("flag", "1");
				cjo.put("customerStatus", "");
				
				if(info.getRoom()!=null){
					cjo.put("roomId", info.getRoom().getNumber());
					cjo.put("roomStatus", "����");
					
				}
				
				cjo.put("projectId",info.getSellProject().getNumber().toString());
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				cjo.put("changeDate", df.format(new Date()));
				
				cjo.put("isChangQuid", "0");
				
				carr.add(cjo);
				
				String crs=SHEManageHelper.execPost(null, "sap_change_mcrm_channel", carr.toJSONString());
				JSONObject crso = JSONObject.parseObject(crs);
				if(!"SUCCESS".equals(crso.getString("status"))){
					MsgBox.showWarning(crso.getString("message"));
					return;
				}
				crs=SHEManageHelper.execPost(null, "sap_change_personal_channel", carr.toJSONString());
				crso = JSONObject.parseObject(crs);
				if(!"SUCCESS".equals(crso.getString("status"))){
					MsgBox.showWarning(crso.getString("message"));
					return;
				}
			}
			
			
			
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("bizState");
			SelectorItemCollection selsr = new SelectorItemCollection();
			selsr.add("room.*");
			selsr.add("transactionID");
			SincerityPurchaseInfo sin1 = ((ISincerityPurchase)this.getBizInterface()).getSincerityPurchaseInfo(
					new ObjectUuidPK(BOSUuid.read((String) row.getCell(
							"id").getValue())),selsr);
			RoomInfo room = sin1.getRoom();
			SelectorItemCollection selsrom = new SelectorItemCollection();
			// ������Ϊ�źŵõ���  ������δ���̹�
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.BAYNUMBER_VALUE));
			if(sin1.getRoom()!=null){
				if(!sin1.getRoom().isIsPush()&&!this.getBizInterface().exists(filter)){
					room.setSellState(RoomSellStateEnum.Init);
					selsr.add("room.sellState");
					RoomFactory.getRemoteInstance().updatePartial(room, selsrom);
				}else{
					room.setSellState(RoomSellStateEnum.OnShow);
					
					selsr.add("room.sellState");
					RoomFactory.getRemoteInstance().updatePartial(room, selsrom);
				}
			}
			
			sin1.setBizState(TransactionStateEnum.QUITNUMBER);
		
			((ISincerityPurchase)this.getBizInterface()).quitNum(sin1, sels);
			MsgBox.showInfo("�˺ųɹ�!");
			this.refresh(null);
			
			
			
			//�˺ź���Ҫ�ѷ���״̬��Ϊ���۵���һ��������ܶ�Ӧ��������Ϲ����������Ѿ����Ϲ���
			//��ôֻ�е��˺ź��������û����Ч״̬�ĳ����Ϲ��������Ϲ������ܱ�ɴ���״̬
			//�Ϲ�����״̬���Ϲ�����û���տ��ʱ�򷿼��Ǵ���״̬�����ǵ�����ȡ��Ԥ���𳬹���ͱ�׼�󷿼�ͻ���Ԥ��״̬
//			if(sin.getRoom()!=null)
//			{
//				RoomInfo room = sin.getRoom();
//				//���û����Ч״̬�ĳ����Ϲ���
//				if(!SincerityPurchaseFactory.getRemoteInstance().exists("where (sincerityState='"+SincerityPurchaseStateEnum.ARRANGENUM_VALUE+"' or sincerityState='"
//						+SincerityPurchaseStateEnum.TOPURCHASE_VALUE+"') and room.id='"+room.getId().toString()+"'"))
//				{
//					//Ҳû����Ч״̬���Ϲ���,�ѷ���״̬��Ϊ����
//					//by tim_gao �޸��Ϲ����ж����������Ӷ��Ϲ�����"�Ϲ�����"״̬�ж� 2010-10-12
//					
////					�޸�ԭ�����ж�Ϊ����ǰ����״̬Ϊ�����Ϲ���ʱ����û���������Ϲ���ȥ�ķ���״̬Ϊ���ۣ�ȥ��ԭ�������Ϲ����Ͳ��Է���״̬���иı� xiaoao_liu
//					if(RoomFactory.getRemoteInstance().exists("where sellState='"+RoomSellStateEnum.SINCERPURCHASE_VALUE+"' and id='"+room.getId().toString() +"'"))
//					{
//						room.setSellState(RoomSellStateEnum.OnShow);
//						//by tim_gao �˺�ʱ�����Ϊδ���̷��䣬Ӧ�˻�δ����״̬ 2010-9-24
//						if(sin.getRoom().getSellOrder()==null)
//						{	
//							sin.getRoom().setSellState(RoomSellStateEnum.Init);
//						}
//						SelectorItemCollection selector = new SelectorItemCollection();
//						selector.add("sellState");
//						RoomFactory.getRemoteInstance().updatePartial(room, selector);
//					}
//				}
//				
//			}
//			
//			
		}
	}
	
	public void actionSincerPrint_actionPerformed(ActionEvent e)
			throws Exception {
		this.checkSelected();
		String sincerPurId = this.getSelectedKeyValue();
		SinerityPrintDataProvider data = new SinerityPrintDataProvider(sincerPurId, new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.SincerityPrintQuery"));
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print("/bim/fdc/sellhouse/sincerityPrint", data, javax.swing.SwingUtilities.getWindowAncestor(this));
		super.actionSincerPrint_actionPerformed(e);
	}
	
	public void actionSincerPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		this.checkSelected();
		String sincerPurId = this.getSelectedKeyValue();
		SinerityPrintDataProvider data = new SinerityPrintDataProvider(sincerPurId, new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.SincerityPrintQuery"));
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview("/bim/fdc/sellhouse/sincerityPrint", data, javax.swing.SwingUtilities.getWindowAncestor(this));
		super.actionSincerPrintPreview_actionPerformed(e);
	}

	public boolean isNullorZero(Object obj ){
		if(obj==null){
			return false;
		}
		if(FDCHelper.ZERO.equals((BigDecimal)obj)){
			return false;
		}
		return true;
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String id = this.getSelectedKeyValue();
		if (id == null) {
			return;
		}
		//���տ��ж�
		boolean isRev =false;
		SelectorItemCollection selCol = new SelectorItemCollection();
		
		selCol.add("sincerPriceEntrys.*");
		selCol.add("sincerPriceEntrys.moneyDefine.*");
		selCol.add("*");
		SincerityPurchaseInfo sin = SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseInfo(new ObjectUuidPK(BOSUuid.read(id)),selCol);

		
		BigDecimal actAmount = FDCHelper.ZERO;
	
		for(Iterator it = sin.getSincerPriceEntrys().iterator(); it.hasNext();){
			SincerReceiveEntryInfo sinInfo = (SincerReceiveEntryInfo) it.next();
			isRev = isNullorZero(SHEManageHelper.getActRevAmount(null,sinInfo.getTanPayListEntryId()));
			if(!isRev){
				isRev = isNullorZero(sinInfo.getActRevAmount());
			}
			if(isRev){
				break;
			}
		}
		
		if (sin.getBizState().equals(TransactionStateEnum.BAYNUMBER)&& !isRev) {
			checkSelected();
			if (confirmRemove()) {
				Remove();
				refresh(e);
			}
		} else {
			MsgBox.showInfo( "ֻ��ɾ���źŲ�δ�տ��ԤԼ��!");
		}
	}

	public void actionRevAmount_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("room.*");
		sels.add("sellProject.*");
		sels.add("customer.*");
		sels.add("customer.customer.*");
		sels.add("sincerPriceEntrys.*");
		sels.add("sincerPriceEntrys.moneyDefine.*");
		SincerityPurchaseInfo info =((ISincerityPurchase)this.getBizInterface()).getSincerityPurchaseInfo(new ObjectUuidPK(id),sels);
		if(info.isIsValid()||TransactionStateEnum.QUITNUMBER.equals(info.getBizState())||
				TransactionStateEnum.SINNULLIFY.equals(info.getBizState())||TransactionStateEnum.WAITTINGFORDEAL.equals(info.getBizState())){
			
			FDCMsgBox.showWarning(this,"�õ���ҵ��״̬���ܽ����տ������");
			SysUtil.abort();
		}
		Object[] custObjs=new Object[info.getCustomer().size()];
		for(int i=0;i<info.getCustomer().size();i++){
			custObjs[i]=info.getCustomer().get(i).getCustomer();
		}
		
		Set transEntryIdSet = new HashSet();
		SincerReceiveEntryCollection signPayListColl = info.getSincerPriceEntrys();
		for (int i = 0; i < signPayListColl.size(); i++) {
			SincerReceiveEntryInfo signPayEntryInfo = signPayListColl.get(i);
			transEntryIdSet.add(signPayEntryInfo.getTanPayListEntryId().toString());
		}				
		CRMClientHelper.openTheGatherRevBillWindow(this, null, info.getSellProject(), info ,custObjs,transEntryIdSet);
	}
	
	private SincerityPurchaseInfo getSincerityPurchaseById(String id) throws BOSException, EASBizException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("sellProject.*");
		sels.add("room.*");
		sels.add("room.building.sellProject");
		sels.add("customer.*");
		sels.add("customer.sysCustomer.*");
		sels.add("sellOrder.*");
		sels.add("salesman.*");
		
		sels.add("sincerPriceEntrys.*");
		sels.add("sincerPriceEntrys.moneyDefine.*");
		
		SincerityPurchaseInfo sin = SincerityPurchaseFactory
				.getRemoteInstance().getSincerityPurchaseInfo(
						new ObjectUuidPK(BOSUuid.read(id)),sels);
		return sin;
	}
	
	public void actionBatchPurchase_actionPerformed(ActionEvent e) throws Exception
    {
		BatchPurchaseUI.showUI(this);
    }

	public void actionQuitAmount_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionQuitAmount_actionPerformed(e);
		String id = this.getSelectedKeyValue();
		if (id == null) {
			return;
		}  
		
		//TODO �˿�Ҫ����ѡ�еļ�¼  �տ��Ƿ����� ��������.�������
		SincerityPurchaseInfo sin = getSincerityPurchaseById(id);
		if(sin.isIsReceiveEnterAccount()){
	
		}else{
			if (!sin.isIsRev()) {
				if (sin.getRevDate() == null) {
					MsgBox.showError("û���տ�!");
					return;
				} else {
					MsgBox.showError("�Ѿ��˿�!");
					return;
				}
			}
			sin.setIsRev(false);
			sin.setRevDate(null);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("isRev");
			selector.add("revDate");
			SincerityPurchaseFactory.getRemoteInstance().updatePartial(sin, selector);
			MsgBox.showInfo("�˿�ɹ�!");
			this.refresh(null);
		}
		

	}
	
	protected String getLongNumberFieldName() {
		return "number";
	}

	/**
	 * תǩԼ
	 */
	public void actionSignContract_actionPerformed(ActionEvent e)throws Exception {
		checkSelected();
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	BizEnumValueInfo state = (BizEnumValueInfo) row.getCell("sincerityState").getValue();
    	if(!TransactionStateEnum.BAYNUMBER_VALUE.equals(state.getValue())){
    		FDCMsgBox.showWarning("����״̬���ʺ����˲�����");
    		return;
    	}
    	SelectorItemCollection sels = super.getSelectors();
		sels.add("bizState");
		SincerityPurchaseInfo info=SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseInfo(new ObjectUuidPK(id));
		SHEManageHelper.toTransactionBill(BOSUuid.read(id), info.getBizState(),this,SignManageEditUI.class.getName());
	}
	
	public void actionPrePur_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	BizEnumValueInfo state = (BizEnumValueInfo) row.getCell("sincerityState").getValue();
    	if(!TransactionStateEnum.BAYNUMBER_VALUE.equals(state.getValue())){
    		FDCMsgBox.showWarning("����״̬���ʺ����˲�����");
    		return;
    	}
    	SelectorItemCollection sels = super.getSelectors();
		sels.add("bizState");
		SincerityPurchaseInfo info=SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseInfo(new ObjectUuidPK(id));
		SHEManageHelper.toTransactionBill(BOSUuid.read(id), info.getBizState(),this,PrePurchaseManageEditUI.class.getName());
	}
	public void actionToPurchase_actionPerformed(ActionEvent e)throws Exception {
		checkSelected();
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	BizEnumValueInfo state = (BizEnumValueInfo) row.getCell("sincerityState").getValue();
    	if(!TransactionStateEnum.BAYNUMBER_VALUE.equals(state.getValue())){
    		FDCMsgBox.showWarning("����״̬���ʺ����˲�����");
    		return;
    	}
    	SelectorItemCollection sels = super.getSelectors();
		sels.add("bizState");
		SincerityPurchaseInfo info=SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseInfo(new ObjectUuidPK(id));
		SHEManageHelper.toTransactionBill(BOSUuid.read(id), info.getBizState(),this,PurchaseManageEditUI.class.getName());
	}
	
	/**
	 * �ж��Ƿ�����ֱ��ǩԼ 
	 * @param room
	 * @return
	 */
	public boolean getIsImmediacySign(String id){
		SaleOrgUnitInfo sale = SysContext.getSysContext().getCurrentSaleUnit();
		if (sale == null || !sale.isIsBizUnit()) {
			return false;
		}
		boolean debug = false;
		RoomDisplaySetting setting= new RoomDisplaySetting();
		HashMap functionSetMap = (HashMap) setting.getFunctionSetMap();
		FunctionSetting funcSet = (FunctionSetting)functionSetMap.get(id);
		if(funcSet == null)	{
			debug = true ;
		}else{
			if(funcSet.getIsActGathering().booleanValue()
				|| funcSet.getIsSignGathering().booleanValue()){
				debug = false ;
			}else{
				debug = true ;
			}
		}
		return debug;
	}
	
	 /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }
    public void actionToMT_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("*");
		sic.add("sellProject.number");
		sic.add("room.number");
		sic.add("customer.isMain");
		sic.add("customer.customer.*");
		sic.add("room.productType.name");
		sic.add("saleMansEntry.user.number");
		sic.add("sincerPriceEntrys.appAmount");
		JSONArray carr=new JSONArray();
		 
		String	paramValue="true";
		try {
			paramValue = ParamControlFactory.getRemoteInstance().getParamValue(new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId()), "YF_QD");
		} catch (EASBizException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (BOSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(int ii = 0; ii < id.size(); ii++){
			SincerityPurchaseInfo info=SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseInfo(new ObjectUuidPK(id.get(ii).toString()),sic);
			 if(!info.getBizState().equals(TransactionStateEnum.BAYNUMBER)&&!info.getBizState().equals(TransactionStateEnum.TOPUR)&&!info.getBizState().equals(TransactionStateEnum.TOSIGN)){
				 continue;
			 }

			JSONObject cjo=new JSONObject();
			cjo.put("id", info.getNumber().toString());
			cjo.put("type", "1");
			cjo.put("projectId",info.getSellProject().getNumber());
			String customerId="";
			SHECustomerInfo quc=null;
			Timestamp qudate=null;
			for(int i=0;i<info.getCustomer().size();i++){
				if(i==0){
					customerId=info.getCustomer().get(i).getCustomer().getNumber();
				}else{
					customerId=customerId+"@"+info.getCustomer().get(i).getCustomer().getNumber();
				}
				if("true".equals(paramValue)){
					if(info.getCustomer().get(i).isIsMain()){
						quc=info.getCustomer().get(i).getCustomer();
					}
				}else{
					if(info.getCustomer().get(i).getCustomer().getFirstDate()==null&&info.getCustomer().get(i).getCustomer().getReportDate()==null){
						MsgBox.showWarning("�ͻ��������ں��׷����ڶ�Ϊ�գ�");
						return;
					}
					if(qudate==null){
						if(info.getCustomer().get(i).getCustomer().getReportDate()!=null){
							qudate=info.getCustomer().get(i).getCustomer().getReportDate();
						}else{
							qudate=info.getCustomer().get(i).getCustomer().getFirstDate();
						}
						quc=info.getCustomer().get(i).getCustomer();
					}else{
						Timestamp comdate=null;
						if(info.getCustomer().get(i).getCustomer().getReportDate()!=null){
							comdate=info.getCustomer().get(i).getCustomer().getReportDate();
						}else{
							comdate=info.getCustomer().get(i).getCustomer().getFirstDate();
						}
						if(qudate.after(comdate)){
							qudate=comdate;
							quc=info.getCustomer().get(i).getCustomer();
						}
					}
				}
			}
			cjo.put("qdCustId",quc.getNumber());
			cjo.put("customerId", customerId);
			if(info.getRoom()!=null){
				cjo.put("roomId", info.getRoom().getNumber().toString());
				cjo.put("rcYT", info.getRoom().getProductType().getName());
			}
			cjo.put("roomStatus", "�ź�");
			
			cjo.put("userId", info.getSaleMansEntry().get(0).getUser().getNumber());
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			cjo.put("tradeDate", df.format(info.getBizDate()));
			if(info.getSincerPriceEntrys().size()>0){
				cjo.put("tradeAmount",info.getSincerPriceEntrys().get(0).getAppAmount());
			}else{
				cjo.put("tradeAmount", "");
			}
			cjo.put("payType", "");
			cjo.put("expectDate", "");
			cjo.put("price", "");
			cjo.put("area", "");
			cjo.put("contractNo", "");
			cjo.put("rengouId", "");
			cjo.put("remark", "");
			cjo.put("orderType", "");
			cjo.put("saleType", "");
			cjo.put("rcId", "");
			cjo.put("salesStatisticsDate", "");
			cjo.put("fyyt", "");
			
			carr.add(cjo);
  		}
		try {
			String rs=SHEManageHelper.execPost(null, "sap_transaction_mcrm_channel", carr.toJSONString());
			JSONObject rso = JSONObject.parseObject(rs);
			if(!"SUCCESS".equals(rso.getString("status"))){
				MsgBox.showWarning(rso.getString("message"));
				return;
			}
			rs=SHEManageHelper.execPost(null, "sap_transaction_organ_personal_channel", carr.toJSONString());
			rso = JSONObject.parseObject(rs);
			if(!"SUCCESS".equals(rso.getString("status"))){
				MsgBox.showWarning(rso.getString("message"));
			}else{
				MsgBox.showInfo("ͬ���ɹ���");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}