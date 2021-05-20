/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.*;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.basedata.assistant.CityCollection;
import com.kingdee.eas.basedata.assistant.CityFactory;
import com.kingdee.eas.basedata.assistant.CityInfo;
import com.kingdee.eas.basedata.assistant.CountryCollection;
import com.kingdee.eas.basedata.assistant.CountryFactory;
import com.kingdee.eas.basedata.assistant.CountryInfo;
import com.kingdee.eas.basedata.assistant.ProvinceCollection;
import com.kingdee.eas.basedata.assistant.ProvinceFactory;
import com.kingdee.eas.basedata.assistant.ProvinceInfo;
import com.kingdee.eas.basedata.assistant.RegionInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class PolicyManageListUI extends AbstractPolicyManageListUI {
	private static final Logger logger = CoreUIObject.getLogger(PolicyManageListUI.class);

	/**
	 * output class constructor
	 */
	public PolicyManageListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * 
	 * ���������ظ��෽����������½�������ҳƬ�д�
	 * 
	 * @author:Liqi
	 * @see com.kingdee.eas.framework.client.ListUI#getEditUIModal()
	 */
	protected String getEditUIModal() {
		String openModel = UIConfigUtility.getOpenModel();
		if (openModel != null) {
			return UIFactoryName.NEWWIN;
		} else {
			return UIFactoryName.NEWTAB;
		}
	}

	//
	public void onLoad() throws Exception {
		super.onLoad();
		initTree();
		expandTreeNodes();
		this.actionAuditResult.setVisible(false);
	}
	/**
	 * ����ֻ���ڼ�����֯��ά��
	 */
	 private void setAddCtrl() {
		 if(SysContext.getSysContext().getCurrentAdminUnit()!=null && SysContext.getSysContext().getCurrentAdminUnit().getId()!=null){
			 String ctrlOrg=SysContext.getSysContext().getCurrentAdminUnit().getId().toString();
		    	String cuid=OrgConstants.DEF_CU_ID;
		    	if(ctrlOrg.compareTo(cuid)!=0) {
		    		MsgBox.showError("�ڵ�ǰ��֯�²��ܽ��иò�����");
		    		SysUtil.abort();
		    	}
		 }else{
			 MsgBox.showError("�ڵ�ǰ��֯�²��ܽ��иò�����");
	    		SysUtil.abort();
		 }
	 }

	/**
	 * չ����
	 */
	private void expandTreeNodes() {
		TreeModel tree = treeMain.getModel();
		KDTreeNode root = (KDTreeNode) tree.getRoot();
		treeMain.setSelectionNode(root); // Ĭ��ѡ����ڵ�
		// ��������ӽڵ㣬��չ���˽ڵ�
		if (root.getChildCount() > 0) {
			treeMain.expandPath(new TreePath(root.getPath()));
		}
	}

	/**
	 * ��ʼ����
	 * 
	 * @throws BOSException
	 */
	private void initTree() throws BOSException {
		KDTreeNode root = new KDTreeNode("����"); // �������ĸ��ڵ�
		treeMain.setModel(new DefaultTreeModel(root));
		CountryCollection countrys = CountryFactory.getRemoteInstance().getCountryCollection(); // ��ȡ���еĹ��Ҽ���
		CountryInfo country = null;
		KDTreeNode countryNode = null;
		BOSUuid countryID = null;
		ProvinceCollection provinces = null; // ʡ�ļ���
		ProvinceInfo province = null;
		KDTreeNode provinceNode = null;
		BOSUuid provinceID = null;
		CityCollection citys = null; // �еļ���
		CityInfo city = null;
		KDTreeNode cityNode = null;
		for (int i = 0; i < countrys.size(); i++) {
			country = countrys.get(i);
			if (country != null) {
				countryNode = new KDTreeNode(country); // �������ҽڵ�
				root.add(countryNode);
				countryID = country.getId();
				provinces = getProvinces(countryID); // ��ȡ�����µ�����ʡ
				for (int j = 0; j < provinces.size(); j++) {
					province = provinces.get(j);
					if (province != null) {
						provinceNode = new KDTreeNode(province); // ����ʡ�ڵ�
						countryNode.add(provinceNode);
						provinceID = province.getId();
						citys = getCitys(provinceID); // ��ȡʡ�����е���
						for (int k = 0; k < citys.size(); k++) {
							city = citys.get(k);
							if (city != null) {
								cityNode = new KDTreeNode(city); // �����нڵ�
								provinceNode.add(cityNode);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * ��ȡ�����µ����е�ʡ��
	 * 
	 * @throws BOSException
	 */
	private ProvinceCollection getProvinces(BOSUuid countryID) throws BOSException {
		ProvinceCollection collection = null;
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("country.id", countryID, CompareType.EQUALS));
		evi.setFilter(filter);
		collection = ProvinceFactory.getRemoteInstance().getProvinceCollection(evi);
		return collection;
	}

	/**
	 * ��ȡʡ�µ����е���
	 * 
	 * @throws BOSException
	 */
	private CityCollection getCitys(BOSUuid provinceID) throws BOSException {
		CityCollection collection = null;
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("province.id", provinceID, CompareType.EQUALS));
		evi.setFilter(filter);
		collection = CityFactory.getRemoteInstance().getCityCollection(evi);
		return collection;
	}

	/**
	 * ��ѡ��Ĺ��һ�ʡ����edit����
	 */
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		// TODO Auto-generated method stub
		super.prepareUIContext(uiContext, e);
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.treeMain.getLastSelectedPathComponent();
		if (node.getUserObject() instanceof CountryInfo) { // ѡ�еĽڵ��ǹ�������
			uiContext.put("country", node.getUserObject());
			uiContext.put("nodeType", "country");
		} else if (node.getUserObject() instanceof ProvinceInfo) { // ѡ�еĽڵ���ʡ���͵�
			KDTreeNode parentNode = (KDTreeNode) node.getParent();
			uiContext.put("country", parentNode.getUserObject());
			uiContext.put("province", node.getUserObject());
			uiContext.put("nodeType", "province");
		} else if (node.getUserObject() instanceof CityInfo) { // ��ѡ�Ľڵ��������͵�
			KDTreeNode parentNode = (KDTreeNode) node.getParent();
			KDTreeNode parentParentNode = (KDTreeNode) parentNode.getParent();
			uiContext.put("country", parentParentNode.getUserObject());
			uiContext.put("province", parentNode.getUserObject());
			uiContext.put("city", node.getUserObject());
			uiContext.put("nodeType", "city");
		} else {
			uiContext.put("nodeType", "else");
		}
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.treeMain_valueChanged(e);
		tblMain.removeRows();
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		try {
			viewInfo = new EntityViewInfo();
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.treeMain.getLastSelectedPathComponent();
			FilterInfo filter = new FilterInfo();
			if (node != null && node.getUserObject() instanceof CityInfo) {
				CityInfo city = (CityInfo) node.getUserObject();
				BOSUuid cityID = city.getId();
				filter.getFilterItems().add(new FilterItemInfo("city.id", cityID, CompareType.EQUALS));
			}
			if (node != null && node.getUserObject() instanceof ProvinceInfo) {
				ProvinceInfo province = (ProvinceInfo) node.getUserObject();
				BOSUuid provinceID = province.getId();
				filter.getFilterItems().add(new FilterItemInfo("province.id", provinceID, CompareType.EQUALS));
			}
			if (node != null && node.getUserObject() instanceof CountryInfo) {
				CountryInfo country = (CountryInfo) node.getUserObject();
				BOSUuid countryID = country.getId();
				filter.getFilterItems().add(new FilterItemInfo("country.id", countryID, CompareType.EQUALS));
			}
			if (viewInfo.getFilter() != null) {
				try {
					viewInfo.getFilter().mergeFilter(filter, "and");
				} catch (BOSException e) {
				}
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			MsgBox.showError("���ִ���");
			logger.info("��ȡ��ѯ��ʧ�ܣ�");
			// SysUtil.abort(e);
			this.handleException(e);
		}
		IQueryExecutor exec = super.getQueryExecutor(queryPK, viewInfo);
		exec.option().isAutoTranslateEnum = false; // �˾����˼�ǣ�ö��ֵ�����룬ֱ����ʾ���ݿ��е�ֵ
		return exec;
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		super.tblMain_tableSelectChanged(e);
	}

	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * output actionHelp_actionPerformed
	 */
	public void actionHelp_actionPerformed(ActionEvent e) throws Exception {
		super.actionHelp_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e) throws Exception {
		super.actionCalculator_actionPerformed(e);
	}

	/**
	 * output actionExport_actionPerformed
	 */
	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		super.actionExport_actionPerformed(e);
	}

	/**
	 * output actionExportSelected_actionPerformed
	 */
	public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * output actionRegProduct_actionPerformed
	 */
	public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception {
		super.actionRegProduct_actionPerformed(e);
	}

	/**
	 * output actionPersonalSite_actionPerformed
	 */
	public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception {
		super.actionPersonalSite_actionPerformed(e);
	}

	/**
	 * output actionProcductVal_actionPerformed
	 */
	public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception {
		super.actionProcductVal_actionPerformed(e);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		setAddCtrl();
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * output actionView_actionPerformed
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		super.actionView_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		setAddCtrl();
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		setAddCtrl();
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output actionRefresh_actionPerformed
	 */
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.actionRefresh_actionPerformed(e);
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	/**
	 * output actionLocate_actionPerformed
	 */
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		super.actionLocate_actionPerformed(e);
	}

	/**
	 * output actionQuery_actionPerformed
	 */
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);
	}

	/**
	 * output actionImportData_actionPerformed
	 */
	public void actionImportData_actionPerformed(ActionEvent e) throws Exception {
		super.actionImportData_actionPerformed(e);
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		super.actionAttachment_actionPerformed(e);
	}

	/**
	 * output actionExportData_actionPerformed
	 */
	public void actionExportData_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportData_actionPerformed(e);
	}

	/**
	 * output actionToExcel_actionPerformed
	 */
	public void actionToExcel_actionPerformed(ActionEvent e) throws Exception {
		super.actionToExcel_actionPerformed(e);
	}

	/**
	 * output actionStartWorkFlow_actionPerformed
	 */
	public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception {
		super.actionStartWorkFlow_actionPerformed(e);
	}

	/**
	 * output actionPublishReport_actionPerformed
	 */
	public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception {
		super.actionPublishReport_actionPerformed(e);
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		setAddCtrl();
		super.actionCancel_actionPerformed(e);
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		setAddCtrl();
		super.actionCancelCancel_actionPerformed(e);
	}

	/**
	 * output actionQueryScheme_actionPerformed
	 */
	public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception {
		super.actionQueryScheme_actionPerformed(e);
	}

	/**
	 * output actionCreateTo_actionPerformed
	 */
	public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception {
		super.actionCreateTo_actionPerformed(e);
	}

	/**
	 * output actionCopyTo_actionPerformed
	 */
	public void actionCopyTo_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopyTo_actionPerformed(e);
	}

	/**
	 * output actionTraceUp_actionPerformed
	 */
	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception {
		super.actionTraceUp_actionPerformed(e);
	}

	/**
	 * output actionTraceDown_actionPerformed
	 */
	public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception {
		super.actionTraceDown_actionPerformed(e);
	}

	/**
	 * output actionVoucher_actionPerformed
	 */
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
		super.actionVoucher_actionPerformed(e);
	}

	/**
	 * output actionDelVoucher_actionPerformed
	 */
	public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception {
		super.actionDelVoucher_actionPerformed(e);
	}

	/**
	 * output actionAuditResult_actionPerformed
	 */
	public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception {
		super.actionAuditResult_actionPerformed(e);
	}

	/**
	 * output actionViewDoProccess_actionPerformed
	 */
	public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception {
		super.actionViewDoProccess_actionPerformed(e);
	}

	/**
	 * output actionMultiapprove_actionPerformed
	 */
	public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception {
		super.actionMultiapprove_actionPerformed(e);
	}

	/**
	 * output actionNextPerson_actionPerformed
	 */
	public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception {
		super.actionNextPerson_actionPerformed(e);
	}

	/**
	 * output actionWorkFlowG_actionPerformed
	 */
	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
		super.actionWorkFlowG_actionPerformed(e);
	}

	/**
	 * output actionSendSmsMessage_actionPerformed
	 */
	public void actionSendSmsMessage_actionPerformed(ActionEvent e) throws Exception {
		super.actionSendSmsMessage_actionPerformed(e);
	}

	/**
	 * output actionSignature_actionPerformed
	 */
	public void actionSignature_actionPerformed(ActionEvent e) throws Exception {
		super.actionSignature_actionPerformed(e);
	}

	/**
	 * output actionWorkflowList_actionPerformed
	 */
	public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception {
		super.actionWorkflowList_actionPerformed(e);
	}

	/**
	 * output actoinViewSignature_actionPerformed
	 */
	public void actoinViewSignature_actionPerformed(ActionEvent e) throws Exception {
		super.actoinViewSignature_actionPerformed(e);
	}

	/**
	 * output actionTDPrint_actionPerformed
	 */
	public void actionTDPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionTDPrint_actionPerformed(e);
	}

	/**
	 * output actionTDPrintPreview_actionPerformed
	 */
	public void actionTDPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		super.actionTDPrintPreview_actionPerformed(e);
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception {
		return com.kingdee.eas.fdc.market.PolicyManageFactory.getRemoteInstance();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.market.PolicyManageInfo objectValue = new com.kingdee.eas.fdc.market.PolicyManageInfo();

		return objectValue;
	}

}