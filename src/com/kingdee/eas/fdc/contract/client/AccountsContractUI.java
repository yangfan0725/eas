/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.aimcost.FullDynamicCostMap;
import com.kingdee.eas.fdc.aimcost.HappenDataGetter;
import com.kingdee.eas.fdc.aimcost.HappenDataInfo;
import com.kingdee.eas.fdc.aimcost.ProjectCostRptFacadeFactory;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.AcctAccreditHelper;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.ChangeTypeCollection;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.ChangeTypeInfo;
import com.kingdee.eas.fdc.basedata.ContractSourceInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.basedata.ProjectHelper;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.RetValue;
import com.kingdee.eas.fdc.basedata.TimeTools;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractExecFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IConChangeSplitEntry;
import com.kingdee.eas.fdc.contract.IContractCostSplitEntry;
import com.kingdee.eas.fdc.contract.IPayRequestBill;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.app.ProgrammingController;
import com.kingdee.eas.fdc.contract.programming.client.ProgrammingContractEditUI;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctHelper;
import com.kingdee.eas.fdc.finance.IPaymentSplitEntry;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.IPaymentBill;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * ����:��Ŀ��ͬ��ϸ��
 * 
 * @author jackwang date:2007-5-18f
 *         <p>
 * @version EAS5.3
 */
public class AccountsContractUI extends AbstractAccountsContractUI {
	private static final Logger logger = CoreUIObject.getLogger(AccountsContractUI.class);

	private CostCenterOrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentCostUnit();
	//Ĭ����ʾȫ��
	private boolean isDisplayAll = true;
	//�Ƿ�ֻ��ʾ���ı���ͬ
	private boolean isDisplayConNoText = false;
	//�Ƿ�ֻ��ʾ��ͬ
	private boolean isDisplayContract = false;
	
	private boolean isAccount = false;
	//��̬�ɱ�
	private Map dyCostMap = new HashMap();
	//Ŀ��ɱ�
	private Map aimCostMap = new HashMap();
	//Ŀ��ɱ�
	private Map noHappenMap = new HashMap();
	
	//�������
	private BigDecimal sellArea = null;
	//�������
	private BigDecimal buildArea = null;
	
	private HappenDataGetter happenGetter;
	//�������
	private ChangeTypeCollection changeTypes;
	//��ͬ�������
	private Map lastAmt = new HashMap();
	//��ͬ��ʵ�ֲ�ֵ
	private Map totalSettMap = new HashMap();
	//�ۼ��깤������
	private Map totalComplete = new HashMap();
	
	private String fullOrgUnitId = null;

	/**
	 * output class constructor
	 */
	public AccountsContractUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		//DELETE BY zhiyuan_tang 2010-08-11
		//R100721-063:"�������ɱ�Ԥ��"�е�"Ŀǰ�ѷ���"��"��Ŀ��ͬ��ϸ��"�е�"Ŀǰ�ѷ���"���ݲ�һ��
		//ԭ����֮ǰ������������ݵı�����ͱ������ˣ�����ȡ������͵�ʱ��Ҫ�����úͽ��õĶ�ȡ����
//		filter.getFilterItems().add(
//				new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.getSorter().add(new SorterItemInfo("number"));
		view.getSelector().add("id");
		view.getSelector().add("name");
		this.changeTypes = ChangeTypeFactory.getRemoteInstance()
				.getChangeTypeCollection(view);
		initControl();
		super.onLoad();
		// this.actionAttachment.setVisible(true);
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		AimCostInfo aimCost = (AimCostInfo) ((Map) this.getUIContext().get("DATAOBJECTS")).get("billInfo");
		if (aimCost != null) {
			DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) this.treeMain.getModel().getRoot();
			DefaultKingdeeTreeNode node = findNode(root, aimCost.getOrgOrProId());
			if (node != null) {
				this.treeMain.setSelectionNode(node);
				this.pnlMain.setDividerLocation(0);
				this.treeMain.setVisible(false);
			}
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				int acctNameIndex=tblMain.getColumn("acctName").getColumnIndex()+1;
				tblMain.getViewManager().freeze(0, acctNameIndex);
				
				tblMain.getColumn("sellPart").getStyleAttributes().setHided(true);
				tblMain.getColumn("buildPart").getStyleAttributes().setHided(true);
				
				tblMain.getHead().getRow(0).setHeight(22);
				tblMain.getHead().getRow(1).setHeight(22);
		}});
		FDCClientHelper.setUIMainMenuAsTitle(this);
		
		ClientHelper.changeTableNumberFormat(this.tblMain,new String[]{"aimCost","hasHappen","noHappen","absolute","rate","amt","splitAmt","changeSplitAmt","settSplitAmt","paymentSplitAmt","lastPrice","hasPayAmt","allNotPaid","payPercent","sellPart","buildPart"});
		
		ObjectValueRender render_scale = new ObjectValueRender();
		render_scale.setFormat(new IDataFormat() {
			public String format(Object o) {
				if(o==null){
					return null;
				}else{
					String str = o.toString();
					return str + "%";
				}
				
			}
		});
		this.tblMain.getColumn("rate").setRenderer(render_scale);
		this.tblMain.getColumn("payPercent").setRenderer(render_scale);
		
//		
//		//����������������
//		txtBuildArea.setEditable(false);
//		txtBuildArea.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
//		txtBuildArea.setPrecision(2);
//		txtBuildArea.setHorizontalAlignment(JTextField.RIGHT);
//		txtBuildArea.setRemoveingZeroInDispaly(false);
//		txtSaleArea.setEditable(false);
//		txtSaleArea.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
//		txtSaleArea.setPrecision(2);
//		txtSaleArea.setRemoveingZeroInDispaly(false);
//		txtSaleArea.setHorizontalAlignment(JTextField.RIGHT);
	}

	protected void initTree() throws Exception {
		this.initTable();
		ProjectTreeBuilder treeBuilder = new ProjectTreeBuilder();
		treeBuilder.build(this, this.treeMain, this.actionOnLoad);
		this.treeMain.expandAllNodes(true, (TreeNode) ((TreeModel) this.treeMain.getModel()).getRoot());
	}

	/**
	 * ���ñ������
	 */
	private void initTable() {
		KDTable table = this.tblMain;
		table.checkParsed();
		// table.getStyleAttributes().setLocked(false);
		table.setRefresh(false);
		table.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		table.getViewManager().setFreezeView(-1, 2);
		
		table.setColumnMoveable(false);
		
		ClientHelper.changeTableNumberFormat(this.tblMain,new String[]{"aimCost","hasHappen","noHappen","absolute","rate","amt","splitAmt","changeSplitAmt","settSplitAmt","paymentSplitAmt","lastPrice","hasPayAmt","allNotPaid","payPercent","sellPart","buildPart"});
		
		ObjectValueRender render_scale = new ObjectValueRender();
		render_scale.setFormat(new IDataFormat() {
			public String format(Object o) {
				if(o==null){
					return null;
				}else{
					String str = o.toString();
					return str + "%";
				}
				
			}
		});
		this.tblMain.getColumn("rate").setRenderer(render_scale);
		this.tblMain.getColumn("payPercent").setRenderer(render_scale);
		
		tblMain.getColumn("sellPart").getStyleAttributes().setHided(true);
		tblMain.getColumn("buildPart").getStyleAttributes().setHided(true);
		
		tblMain.getHead().getRow(0).setHeight(22);
		tblMain.getHead().getRow(1).setHeight(22);
		
//		FDCTableHelper.setColumnMoveable(table, true);
		//tHelper.init() ;
	}
    protected void initUserConfig()
    {
//    	super.initUserConfig();
    }

	private void initControl() {
		this.actionImportData.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuBiz.setEnabled(false);
		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setVisible(false);
		this.actionRemove.setEnabled(false);
		this.actionView.setVisible(false);
		this.actionView.setEnabled(false);
		this.actionQuery.setVisible(false);
		this.actionQuery.setEnabled(false);
		this.actionQuery.setEnabled(false);
		this.menuBiz.setVisible(false);
		this.menuEdit.setVisible(false);
		this.menuItemFirstVersion.setVisible(false);
		this.menuItemNextVersion.setVisible(false);
		this.menuItemPreVersion.setVisible(false);
		this.menuItemLastVersion.setVisible(false);		
		this.menuItemSubmit.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.menuItemRecense.setVisible(false);
		actionDisplayAll.putValue(Action.SMALL_ICON,EASResource.getIcon("imgTbtn_assistantlistaccount"));
		actionDisplayContract.putValue(Action.SMALL_ICON,EASResource.getIcon("imgTbtn_assistantlistaccount"));
		actionDisplayConNoText.putValue(Action.SMALL_ICON,EASResource.getIcon("imgTbtn_assistantlistaccount"));
		
//		actionDisplayAll.setVisible(false);
//		actionDisplayContract.setVisible(false);
		actionDisplayConNoText.setVisible(false);
		
		this.btnDisplayContract.setText("ֻ��ʾ��Ŀ");
		
//		this.menuTool.add(actionDisplayAll);
//		this.menuTool.add(actionDisplayContract);
//		this.menuTool.add(actionDisplayConNoText);
	}

	private DefaultKingdeeTreeNode findNode(DefaultKingdeeTreeNode node, String id) {
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			if (projectInfo.getId().toString().equals(id)) {
				return node;
			}
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			FullOrgUnitInfo info = oui.getUnit();
			if (info.getId().toString().equals(id)) {
				return node;
			}
		}

		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode findNode = findNode((DefaultKingdeeTreeNode) node.getChildAt(i), id);
			if (findNode != null) {
				return findNode;
			}

		}
		return null;
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected void tblMain_doRequestRowSet(RequestRowSetEvent e) {
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		//super.tblMain_tableClicked(e);  
		
		if(e.getClickCount()==1){
			//****  do  nothing  ****
		}
		if (e.getClickCount() == 2) {

			int rowIndex = e.getRowIndex();

			if(getMainTable().getRow(rowIndex)==null)
				return;
			
			Object value = getMainTable().getRow(rowIndex).getUserObject();
			if (value == null)
				return;
			
			this.setCursorOfWair();
			if(value!=null&& value instanceof ContractBillInfo){
				ContractBillInfo contractInfo=(ContractBillInfo)value;
				ContractFullInfoUI.showDialogWindows(this, contractInfo.getId().toString());
			}
			if(value!=null&&value instanceof ContractWithoutTextInfo){
				ContractWithoutTextInfo contractInfo=(ContractWithoutTextInfo)value;
				UIContext uiContext = new UIContext(ui);
				uiContext.put(UIContext.ID, contractInfo.getId().toString());
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
						.create(ContractWithoutTextEditUI.class.getName(), uiContext, null,"VIEW");
				uiWindow.show();
			}
			if(value!=null&&value instanceof ProgrammingContractInfo){
				ProgrammingContractInfo contractInfo=(ProgrammingContractInfo)value;
				UIContext uiContext = new UIContext(ui);
				contractInfo=ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo("select costEntries.*,costEntries.costAccount.*,costEntries.costAccount.curProject.*,* from where id='"+contractInfo.getId().toString()+"'");
				uiContext.put("programmingContract", contractInfo);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
						.create(ProgrammingContractEditUI.class.getName(), uiContext, null,OprtState.VIEW);
				uiWindow.show();
			}
			if(value!=null&&value instanceof CostAccountInfo){
				CostAccountInfo accountInfo = (CostAccountInfo)value;
				DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
        		.getLastSelectedPathComponent();
				if(accountInfo.isIsLeaf()&&!node.isLeaf()){
					String selectObjId = getSelectObjId(); 
	            	Set leafPrjIds = FDCClientHelper.getProjectLeafsOfNode(node);
        			boolean selectObjIsPrj = node.getUserObject() instanceof CurProjectInfo;
					Map param = new HashMap();
					param.put("accountInfo", accountInfo);
					param.put("selectObjId", selectObjId);
					param.put("leafPrjIds", leafPrjIds);
					param.put("selectObjIsPrj", Boolean.valueOf(selectObjIsPrj));
					param.put("node", node);
					AccountsContractDetailUI.showDialogWindows(this, param);
				}
			}
			this.setCursorOfDefault();
		}
		
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		// super.tblMain_tableSelectChanged(e);
	}


	private CurProjectInfo getSelectProject() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo;
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			return null;
		}
		return null;
	}

	private String getSelectObjId() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo.getId().toString();
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui.getUnit() == null) {

			}
			FullOrgUnitInfo info = oui.getUnit();
			return info.getId().toString();
		}
		return null;
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		this.txtBuildArea.setValue(null);
		this.txtSaleArea.setValue(null);
		String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		fetchAndFill();
		initUserConfig();
	}	

	/**
	 * ����˫�����ı���ͬ��ʱ�򿪶�Ӧ�����ı���ͬ�༭����
	 * ����д�˷���   by Cassiel_peng
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		
		UIContext uiContext = new UIContext(this);
		
		//��Ϊֻ��ѡ���ж���Ϊ���ı���ͬʱ�÷����Żᴥ�����ʽ�����ֱ��ת��ΪContractWithoutTextInfo�������κ�����.
		String  selectKeyValue =((ContractWithoutTextInfo)this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getUserObject()).getId().toString();
		uiContext.put(UIContext.ID, selectKeyValue);
	    prepareUIContext(uiContext, e);

        IUIWindow uiWindow = null;
        if (SwingUtilities.getWindowAncestor(this) != null && SwingUtilities.getWindowAncestor(this) instanceof JDialog)
        {
            uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create("com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI", uiContext, null,
                    OprtState.VIEW);

        }
        else
        {
            // ����UI������ʾ
            uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create("com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI", uiContext, null,
                    OprtState.VIEW);
        }

        uiWindow.show();
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		fetchAndFill();
		initUserConfig();
	}

	private void fetchAndFill() throws EASBizException,BOSException, Exception {
		LongTimeDialog dialog = UITools.getDialog(this);
		if(dialog==null)
			return;
        dialog.setLongTimeTask(new ILongTimeTask() {
            public Object exec() throws Exception {
                return fetchAllData();
            }

            public void afterExec(Object result) throws Exception {
            	String selectObjId = getSelectObjId(); 
            	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
        		if (node.isLeaf()&&node.getUserObject() instanceof CurProjectInfo) {
        			if (selectObjId == null) {
        				return;
        			}
        			fillTable();
        			int acctNameIndex=tblMain.getColumn("acctName").getColumnIndex()+1;
        			tblMain.getViewManager().freeze(0, acctNameIndex);
        		} else {
        			fillTableNotLeaf();
        		}
            }
        });
        //������һ���߳�����ʾ�������������û������飬���Ǹ��̲߳���ȫ�����������������ʱ��˴�����������������쳣. by cassiel_peng 2009-1224
        try {
        	dialog.show();
		} catch (NullPointerException e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * �����е�ȡ���������ϵ�����������
	 * @return
	 * @throws Exception
	 */
	private HashMap fetchAllData() throws Exception {
		HashMap resultMap = new HashMap();
		String selectObjId = getSelectObjId(); 
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node.isLeaf() && node.getUserObject() instanceof CurProjectInfo) {
			if (selectObjId != null) {
				fetchLeafData(resultMap);
			}
		} else {
//			Set leafPrjIds = FDCClientHelper.getProjectLeafsOfNode(node);
//			boolean selectObjIsPrj = node.getUserObject() instanceof CurProjectInfo;
//			fetchDataNotLeaf(selectObjId,leafPrjIds,selectObjIsPrj);
		}
		
		return resultMap;
	}
	
	private void fetchDataNotLeaf(String objId,Set leafPrjIds,boolean selectObjIsPrj) throws EASBizException, BOSException{
		TimeTools.getInstance().msValuePrintln("start fetchData");
		ParamValue paramValue = new ParamValue();
		paramValue.put("selectObjID", objId);
		paramValue.put("leafPrjIDs", leafPrjIds);
		paramValue.put("selectObjIsPrj", Boolean.valueOf(selectObjIsPrj));
		if(this.isDisplayAll==true){
			paramValue.put("displayContract", Boolean.TRUE);
			paramValue.put("displayNoText", Boolean.TRUE);
		}else{
			paramValue.put("displayContract", Boolean.valueOf(this.isDisplayContract));
			paramValue.put("displayNoText", Boolean.valueOf(this.isDisplayConNoText));
		}
		
		retValueNotLeaf = ProjectCostRptFacadeFactory.getRemoteInstance().getCollectionContractAcctCost(paramValue);
		TimeTools.getInstance().msValuePrintln("end fetchData");
	}
	public void setTableColumnShow(boolean show){
		
		this.actionDisplayAll.setEnabled(show);
		this.actionDisplayConNoText.setEnabled(show);
		this.actionDisplayContract.setEnabled(show);
		
		show = !show;
		tblMain.getColumn("conNumber").getStyleAttributes().setHided(show);
		tblMain.getColumn("contract").getStyleAttributes().setHided(show);
		tblMain.getColumn("unit").getStyleAttributes().setHided(show);
		tblMain.getColumn("date").getStyleAttributes().setHided(show);
		tblMain.getColumn("isInvite").getStyleAttributes().setHided(show);
		tblMain.getColumn("amt").getStyleAttributes().setHided(show);
		tblMain.getColumn("lastPrice").getStyleAttributes().setHided(show);
		tblMain.getColumn("totalSettPrice").getStyleAttributes().setHided(show);
		tblMain.getColumn("isFinalSett").getStyleAttributes().setHided(show);
		tblMain.getColumn("hasPayAmt").getStyleAttributes().setHided(show);
		tblMain.getColumn("allNotPaid").getStyleAttributes().setHided(show);
		tblMain.getColumn("payPercent").getStyleAttributes().setHided(show);
		
		
	}
	public void fillTableNotLeaf() throws Exception{
		this.txtBuildArea.setValue(null);
		this.txtSaleArea.setValue(null);
		tblMain.removeRows();
		if(retValueNotLeaf==null)
			return;
		CostAccountCollection costAccounts = (CostAccountCollection)retValueNotLeaf.get("CostAccountCollection");
		RetValue costValues = (RetValue)retValueNotLeaf.get("costValues");
		RetValue splitValues = (RetValue)retValueNotLeaf.get("splitValues");
		Map costAcctContractDetailMap = (HashMap)retValueNotLeaf.get("costAcctContractDetailMap");
		if(costAccounts==null)
			return;
		DefaultKingdeeTreeNode selectNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		int nodeLevel = 0;
		for(int i=0;i<selectNode.getChildCount();i++){
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)selectNode.getChildAt(i);
			nodeLevel = node.getLevel() - selectNode.getLevel()>nodeLevel?node.getLevel() - selectNode.getLevel():nodeLevel;
		}
		tblMain.getTreeColumn().setDepth(retValueNotLeaf.getInt("maxLevel")+nodeLevel+2);
//		setTableColumnShow(true);
		
		for(Iterator it=costAccounts.iterator();it.hasNext();){
			CostAccountInfo costAccountInfo = (CostAccountInfo)it.next();
			IRow row = tblMain.addRow();
			row.setTreeLevel(costAccountInfo.getLevel() - 1);
			String longNumber = costAccountInfo.getLongNumber();
			row.getCell("acctNumber").setValue(longNumber.replace('!', '.'));
			row.getCell("acctName").setValue(costAccountInfo.getName());
			
			if(costAccountInfo.isIsLeaf()){
				row.setUserObject(costAccountInfo);
				RetValue costValue = (RetValue)costValues.get(longNumber);
				RetValue splitValue = (RetValue)splitValues.get(longNumber);
				//�������� ���۵���
				RetValue areaValue = (RetValue)retValueNotLeaf.get("areaValue");
//				if(costValue!=null&&costValue.getBigDecimal("dynCost")!=null&&costValue.getBigDecimal("dynCost").compareTo(FDCHelper.ZERO)!=0)
//					row.getCell("dyCost").setValue(costValue.getBigDecimal("dynCost"));
				if(costValue!=null&&costValue.getBigDecimal("aimCost")!=null&&costValue.getBigDecimal("aimCost").compareTo(FDCHelper.ZERO)!=0)
					row.getCell("aimCost").setValue(costValue.getBigDecimal("aimCost"));
				if(costValue!=null&&costValue.getBigDecimal("soFarHasAmt")!=null&&costValue.getBigDecimal("soFarHasAmt").compareTo(FDCHelper.ZERO)!=0)
					row.getCell("hasHappen").setValue(costValue.getBigDecimal("soFarHasAmt"));
//				if(costValue!=null&&costValue.getBigDecimal("soFarToAmt")!=null&&costValue.getBigDecimal("soFarToAmt").compareTo(FDCHelper.ZERO)!=0)
//					row.getCell("indentingHappen").setValue(costValue.getBigDecimal("soFarToAmt"));
//				if(costValue!=null&&costValue.getBigDecimal("diffAmt")!=null&&costValue.getBigDecimal("diffAmt").compareTo(FDCHelper.ZERO)!=0)
//					row.getCell("diff").setValue(costValue.getBigDecimal("diffAmt"));
				if(splitValue!=null&&splitValue.getBigDecimal("contractSplitAmt")!=null&&splitValue.getBigDecimal("contractSplitAmt").compareTo(FDCHelper.ZERO)!=0)
					row.getCell("splitAmt").setValue(splitValue.getBigDecimal("contractSplitAmt"));
				if(splitValue!=null&&splitValue.getBigDecimal("conChangeSplitAmt")!=null&&splitValue.getBigDecimal("conChangeSplitAmt").compareTo(FDCHelper.ZERO)!=0)
					row.getCell("changeSplitAmt").setValue(splitValue.getBigDecimal("conChangeSplitAmt"));
				if(splitValue!=null&&splitValue.getBigDecimal("settlementSplitAmt")!=null&&splitValue.getBigDecimal("settlementSplitAmt").compareTo(FDCHelper.ZERO)!=0)
					row.getCell("settSplitAmt").setValue(splitValue.getBigDecimal("settlementSplitAmt"));
//				if(splitValue!=null&&splitValue.getBigDecimal("hasPayCostAmt")!=null&&splitValue.getBigDecimal("hasPayCostAmt").compareTo(FDCHelper.ZERO)!=0)
//					row.getCell("hasPayCostAmt").setValue(splitValue.getBigDecimal("hasPayCostAmt"));
				if(splitValue!=null&&splitValue.getBigDecimal("paymentSplitAmt")!=null&&splitValue.getBigDecimal("paymentSplitAmt").compareTo(FDCHelper.ZERO)!=0)
					row.getCell("paymentSplitAmt").setValue(splitValue.getBigDecimal("paymentSplitAmt"));
				if(areaValue!=null&&areaValue.getBigDecimal(ApportionTypeInfo.sellAreaType)!=null&&areaValue.getBigDecimal(ApportionTypeInfo.sellAreaType).compareTo(FDCHelper.ZERO)!=0&&costValue!=null&&costValue.getBigDecimal("dynCost")!=null&&costValue.getBigDecimal("dynCost").compareTo(FDCHelper.ZERO)!=0){
					this.sellArea=areaValue.getBigDecimal(ApportionTypeInfo.sellAreaType);
					row.getCell("sellPart").setValue(costValue.getBigDecimal("dynCost").divide(areaValue.getBigDecimal(ApportionTypeInfo.sellAreaType),2,BigDecimal.ROUND_HALF_UP));
				}
				if(areaValue!=null&&areaValue.getBigDecimal(ApportionTypeInfo.buildAreaType)!=null&&areaValue.getBigDecimal(ApportionTypeInfo.buildAreaType).compareTo(FDCHelper.ZERO)!=0&&costValue!=null&&costValue.getBigDecimal("dynCost")!=null&&costValue.getBigDecimal("dynCost").compareTo(FDCHelper.ZERO)!=0){
					this.buildArea=areaValue.getBigDecimal(ApportionTypeInfo.buildAreaType);
					row.getCell("buildPart").setValue(costValue.getBigDecimal("dynCost").divide(areaValue.getBigDecimal(ApportionTypeInfo.buildAreaType),2,BigDecimal.ROUND_HALF_UP));
				}
				this.txtBuildArea.setValue(this.buildArea==null?null:FDCHelper.toBigDecimal(this.buildArea,2));
				this.txtSaleArea.setValue(this.sellArea==null?null:FDCHelper.toBigDecimal(this.sellArea,2));
				int baseLevel = costAccountInfo.getLevel()-1;
				for(int i=0;i<selectNode.getChildCount();i++){
					DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)selectNode.getChildAt(i);
					fillNotLeafContractDetail(costAcctContractDetailMap, selectNode, longNumber, baseLevel, node);
					
				}
				
				
			}else{
				row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
			}
		}
		gatherDatas();
	}

	private void fillNotLeafContractDetail(Map costAcctContractDetailMap,
			DefaultKingdeeTreeNode selectNode, String longNumber,
			int baseLevel, DefaultKingdeeTreeNode node) {
		IRow row;
		boolean isMoreSett = false;
		BigDecimal allNotPaid = FDCHelper.ZERO;
		try {
			isMoreSett = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_MORESETTER);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			handUIException(e);
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			handUIException(e);
		}
		if(node.isLeaf()&&node.getUserObject() instanceof CurProjectInfo){
			if(costAcctContractDetailMap.containsKey(longNumber)){
				RetValue contractDetailValues = (RetValue)costAcctContractDetailMap.get(longNumber);
				RetValue projectCostAccounts = (RetValue)contractDetailValues.get("projectCostAccounts");
				RetValue accountContracts = (RetValue)contractDetailValues.get("accountContracts");
				RetValue accountContractSplitValues = (RetValue)contractDetailValues.get("accountContractSplitValues");
				Map contractBillMap = (Map)contractDetailValues.get("contractBillMap");
				Map contractWithoutTextMap = (Map) contractDetailValues.get("contractWithoutTextMap");
				
				Map totalSettlePriceMap = (Map) contractDetailValues.get("totalSettlePriceMap");
				Map hasPayAmtMap = (Map)contractDetailValues.get("hasPayAmtMap");
				Map lastPriceMap = (Map)contractDetailValues.get("lastPriceMap");
				Map payableAmtMap = (Map) contractDetailValues.get("payableAmtMap");
				CurProjectInfo curProject = (CurProjectInfo)node.getUserObject();
				String curProjectId = curProject.getId().toString();
				List costAccountList = (List)projectCostAccounts.get(curProjectId);
				if(costAccountList!=null){
					IRow detailRow = tblMain.addRow();
					String name = node.getText() ;
					detailRow.setTreeLevel(node.getLevel()-selectNode.getLevel()+baseLevel);
					detailRow.getCell("acctNumber").setValue(name);
					detailRow.getStyleAttributes().setBackground(new Color(0xF0EDD9));
					for(Iterator itDetail=costAccountList.iterator();itDetail.hasNext();){
						CostAccountInfo costAccount = (CostAccountInfo)itDetail.next();
						if(costAccount!=null){										
							row = tblMain.addRow();
							row.setTreeLevel(node.getLevel()-selectNode.getLevel()+baseLevel+1);
							row.getCell("acctNumber").setValue(costAccount.getLongNumber().replace('!', '.'));
							row.getCell("acctName").setValue(costAccount.getName());
							row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
							List contractIds = (List)accountContracts.get(costAccount.getId().toString());
							if(contractIds!=null){
								for(Iterator contractIt = contractIds.iterator();contractIt.hasNext();){
									String contractId = (String)contractIt.next();
									FDCBillInfo contractBill = null;
									boolean isInvite = false;
									ContractSourceInfo contractSource = null; // �γɷ�ʽ
									boolean isTotalSett = false;
									if(contractBillMap!=null&&contractBillMap.containsKey(contractId)){
										contractBill = (ContractBillInfo)contractBillMap.get(contractId);
										if(((ContractBillInfo)contractBill).getContractSourceId()!=null)
										{
											contractSource = ((ContractBillInfo) contractBill).getContractSourceId();
											isInvite = ContractSourceInfo.INVITE_VALUE.equals(contractSource.getId().toString());
											isTotalSett = ((ContractBillInfo)contractBill).isHasSettled();
										}
										
										
									}else if(contractWithoutTextMap!=null&&contractWithoutTextMap.containsKey(contractId)){
										contractBill = (ContractWithoutTextInfo)contractWithoutTextMap.get(contractId);
									}
									if(contractBill!=null){
										row = tblMain.addRow();
										row.setUserObject(contractBill);
										row.getCell("conNumber").setValue(contractBill.getNumber());
										row.getCell("contract").setValue(contractBill.getName());
										row.setTreeLevel(node.getLevel()-selectNode.getLevel()+baseLevel+2);
										if(contractBill.containsKey("curProject")){
											CurProjectInfo conCur = (CurProjectInfo)contractBill.get("curProject");
//														row.getCell("contractCurProject").setValue(conCur.getName());
										}
										if(contractBill.containsKey("partB")){
											SupplierInfo supplier = (SupplierInfo)contractBill.get("partB");
											row.getCell("unit").setValue(supplier.getName());
										}
										if(contractBill.containsKey("signDate")){
											row.getCell("date").setValue(contractBill.get("signDate"));
										}
										
										if(contractBill instanceof ContractBillInfo){
											row.getCell("isInvite").setValue(Boolean.valueOf(isInvite));
											row.getCell("contractSource").setValue(contractSource.getName());
											//�Ƿ����ս���
											row.getCell("isFinalSett").setValue(Boolean.valueOf(isTotalSett));
											row.getCell("payableAmt").setValue(payableAmtMap.get(contractId));											
										}
										
																							
										//��ͬ���
										row.getCell("amt").setValue(contractBill.getAmount());
										//�Ѹ�����
										BigDecimal hasPayAmt = FDCHelper.toBigDecimal(hasPayAmtMap.get(contractId));
										if(hasPayAmt.compareTo(FDCHelper.ZERO)!=0)
											row.getCell("hasPayAmt").setValue(hasPayAmt);
									
										//�������lastPriceMap
										if(lastPriceMap!=null)
											row.getCell("lastPrice").setValue(lastPriceMap.get(contractId));
										
										RetValue accountContractSplitValue = (RetValue)accountContractSplitValues.get(costAccount.getId().toString()+contractId);
										
										BigDecimal totalSettlePrice = FDCHelper.ZERO;
										BigDecimal allNotPay = FDCHelper.ZERO;
										BigDecimal lastPrice =FDCHelper.ZERO;
										BigDecimal hasPayCostAmt = FDCHelper.ZERO;
										
										if(totalSettlePriceMap!=null){
											totalSettlePrice = FDCHelper.toBigDecimal(totalSettlePriceMap.get(contractId));
										}	
											
											
										
										if(accountContractSplitValue!=null){
											
											hasPayCostAmt = FDCHelper.toBigDecimal(accountContractSplitValue.getBigDecimal("HASPAYCOSTAMT"));
											//��ͬ��ֽ��
											row.getCell("splitAmt").setValue(accountContractSplitValue.getBigDecimal(CostSplitBillTypeEnum.CONTRACTSPLIT_VALUE));
											//�����ֽ��
											row.getCell("changeSplitAmt").setValue(accountContractSplitValue.getBigDecimal(CostSplitBillTypeEnum.CNTRCHANGESPLIT_VALUE));
											//�����ֽ�� 
											row.getCell("settSplitAmt").setValue(accountContractSplitValue.getBigDecimal(CostSplitBillTypeEnum.SETTLEMENTSPLIT_VALUE));
											//�����ֽ��
											row.getCell("hasPayCostAmt").setValue(accountContractSplitValue.getBigDecimal("HASPAYCOSTAMT"));
											//�����ֽ��
											row.getCell("paymentSplitAmt").setValue(accountContractSplitValue.getBigDecimal(CostSplitBillTypeEnum.PAYMENTSPLIT_VALUE));
										}
										//��ʵ�ֲ�ֵ
										if(totalSettlePrice.compareTo(FDCHelper.ZERO)!=0)
											row.getCell("totalSettPrice").setValue(totalSettlePrice);
										
										BigDecimal complete = FDCHelper.ZERO;
										if(totalComplete.get(contractId) != null){
											complete = (BigDecimal) totalComplete.get(contractId);
										}
										
										//�깤δ����
										
										/***
										 * �깤δ����
										 * �깤δ���� = ��ͬ��ʵ�ֲ�ֵ - ��ͬ�Ѹ���
										 * ���߼�
										 * ���û�����ò����Ƿ������ν��� ���깤δ����=�ۼ��깤������-�Ѹ��� 
										 * ������ò����Ƿ������ν��� ���깤δ����=��ʵ�ֲ�ֵ - �Ѹ���
										 * ���ս���� �깤δ����=���ս�����-�Ѹ��
										 */
										if(lastPriceMap != null){
											lastPrice = (BigDecimal) lastPriceMap.get(contractId);
										}
										
										
										if(isMoreSett){
											if(isTotalSett){
												allNotPay = lastPrice.subtract(hasPayAmt);
											}else{
												allNotPay = totalSettlePrice.subtract(hasPayAmt);
											}
										}else{
											if(isTotalSett){
												 allNotPay = lastPrice.subtract(hasPayAmt);
											}else{
												 allNotPay = complete.subtract(hasPayAmt);
											}
											  
										}
										
										
										
										
										if(allNotPay.compareTo(FDCHelper.ZERO)!=0)
											row.getCell("allNotPaid").setValue(allNotPay);
										/***
										 * ������ = �Ѹ���/��ʵ�ֲ�ֵ
										 */
										if(hasPayAmt.compareTo(FDCHelper.ZERO)!=0&&totalSettlePrice.compareTo(FDCHelper.ZERO)!=0){
											row.getCell("payPercent").setValue(hasPayAmt.divide(totalSettlePrice,2,BigDecimal.ROUND_HALF_UP));
										}
										
									}
								}
							}
							
						
						}
					}
				}
			}
		}else{
			for(int i=0;i<node.getChildCount();i++)
				fillNotLeafContractDetail(costAcctContractDetailMap,
						selectNode, longNumber, baseLevel, (DefaultKingdeeTreeNode)node.getChildAt(i));
		}
	}
	private RetValue retValueNotLeaf;
	//������ı���ͬ��Ϣ
	private Map conWithoutMap = new HashMap();
	//������ı���ͬ��ֽ��
	private Map conWithoutSplitAmt = new HashMap();
	
	
	
	private void initCbMap(String selectObjId) throws BOSException {
		// ��ȡ��ǰ������Ŀ�µĿ�Ŀmap
		HashSet accountUps = new HashSet();
		ICostAccount ica = CostAccountFactory.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", selectObjId));
		evi.getSelector().add(new SelectorItemInfo("id"));
		evi.getSelector().add(new SelectorItemInfo("curProject.id"));
		evi.setFilter(filter);
		CostAccountCollection cac = ica.getCostAccountCollection(evi);
		if (cac != null) {
			for (int i = 0; i < cac.size(); i++) {
				accountUps.add(cac.get(i).getId().toString());
			}
		}
		if(accountUps.size()==0){
			return;
		}
		Set hasCostAccount=new HashSet();
		//
		// IContractBill icb = ContractBillFactory.getRemoteInstance();
		IContractCostSplitEntry icse = ContractCostSplitEntryFactory.getRemoteInstance();
		evi = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("costAccount.id", accountUps, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("parent.state",FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
		evi.getSelector().add(new SelectorItemInfo("id"));
		evi.getSelector().add(new SelectorItemInfo("costAccount.id"));
		evi.getSelector().add(new SelectorItemInfo("parent.contractBill.id"));
		evi.setFilter(filter);
		ContractCostSplitEntryCollection ccsec = icse.getContractCostSplitEntryCollection(evi);
		if (ccsec != null) {
			for (int i = 0; i < ccsec.size(); i++) {
				if (ccsec.get(i).getParent()!=null&&ccsec.get(i).getParent().getContractBill()!=null&&!lnUps.contains(ccsec.get(i).getParent().getContractBill().getId().toString())) {
					lnUps.add(ccsec.get(i).getParent().getContractBill().getId().toString());
					hasCostAccount.add(ccsec.get(i).getCostAccount().getId().toString());
				}
			}
		}
		
		//��԰Ҫ���ڿ�Ŀ��ͬ��ϸ�������ӱ����ֽ���У���Ϊ֮ǰ�ڸñ����в�δ���ǵ������֣���ȡ��ʾ�ڽ����еĺ�ͬ��ʱ��ֻ�Խ��й�
		//��ͬ���Ϊ������������Ҫ�����ӱ����ֽ���еĻ����ڱ����ֲ���ĳ����Ŀ���Ǻ�ͬ��ֲ�û�в𵽸ÿ�Ŀ���������ʱ�ú�ͬ��
		//������ʾ�ڱ����У�Ӱ��������ʾ�� �������Խ��й���ͬ��ֻ����Ǳ�����Ϊ����������ɸѡ��ʾ�ڽ���ĺ�ͬ  by Cassiel_peng 2008-9-4

		IConChangeSplitEntry conChangeSplitInterface=ConChangeSplitEntryFactory.getRemoteInstance();
		evi = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("costAccount.id", accountUps, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("parent.state",FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
		evi.getSelector().add(new SelectorItemInfo("id"));
		evi.getSelector().add(new SelectorItemInfo("costAccount.id"));
		evi.getSelector().add(new SelectorItemInfo("parent.contractBill.id"));
		evi.setFilter(filter);
		ConChangeSplitEntryCollection conChangeSplitEntry=conChangeSplitInterface.getConChangeSplitEntryCollection(evi);
		if(conChangeSplitEntry!=null){
			for(int j=0;j<conChangeSplitEntry.size();j++){
				if(conChangeSplitEntry.get(j).getParent()!=null&&conChangeSplitEntry.get(j).getParent().getContractBill()!=null&&!lnUps.contains(conChangeSplitEntry.get(j).getParent().getContractBill().getId().toString())){
					lnUps.add(conChangeSplitEntry.get(j).getParent().getContractBill().getId().toString());
				}
			}
		}
		
		
		//��ȡͨ�����ı���ͬ�����ֻ�ȡ���ı���ͬid
		IPaymentSplitEntry ipse = PaymentSplitEntryFactory.getRemoteInstance();
		evi = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("costAccount.id", accountUps, CompareType.INCLUDE));
		evi.getSelector().add(new SelectorItemInfo("*"));
		evi.getSelector().add(new SelectorItemInfo("costAccount.id"));
		evi.getSelector().add(new SelectorItemInfo("parent.conWithoutText.*"));
		evi.getSelector().add(new SelectorItemInfo("parent.conWithoutText.person.*"));
		evi.getSelector().add(new SelectorItemInfo("parent.conWithoutText.receiveUnit.*"));
		if(accountUps.size() != 0){
			filter.getFilterItems().add(new FilterItemInfo("costAccount.id", accountUps, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("parent.state",FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
			evi.setFilter(filter);
		}
		PaymentSplitEntryCollection psec = ipse.getPaymentSplitEntryCollection(evi);
		//���ԭ������
		conWithoutMap.clear();
		conWithoutSplitAmt.clear();
		
		//������ı���ͬids
		if(psec != null && psec.size() != 0){
			for(int i = 0 ;i < psec.size();i++){
				if(psec.get(i).getParent().getConWithoutText() != null
						&& !conWithoutIds.contains(psec.get(i).getParent().getConWithoutText().getId().toString())){
					conWithoutIds.add(psec.get(i).getParent().getConWithoutText().getId().toString());
					hasCostAccount.add(psec.get(i).getCostAccount().getId().toString());
				}
			}
		}
		
		if(psec != null && psec.size() != 0){
			for(int i = 0; i < psec.size(); i++){
				PaymentSplitEntryInfo entry = psec.get(i);
				String acctId = "";
				if(entry.getCostAccount() != null){
					acctId = entry.getCostAccount().getId().toString();
				}
				//��ͬһ��Ŀ�µ����ı���ͬ���Ϸ����Ӧmap��
				if(!conWithoutMap.containsKey(acctId)){
					ContractWithoutTextCollection cwtc = new ContractWithoutTextCollection();
					if(entry.getParent().getConWithoutText() != null){
						cwtc.add(entry.getParent().getConWithoutText());
					}
					conWithoutMap.put(acctId,cwtc);
				}else{
					ContractWithoutTextCollection cwtc = (ContractWithoutTextCollection)
						conWithoutMap.get(acctId);
					
					if(entry.getParent().getConWithoutText() != null
							&& !cwtc.contains(entry.getParent().getConWithoutText())){
						cwtc.add(entry.getParent().getConWithoutText());
						conWithoutMap.put(acctId,cwtc);
					}
				}
				//��ͬһ��Ŀ�µ����ı���ͬ��ֽ������Ӧmap��
				if(!conWithoutSplitAmt.containsKey(acctId)){
					Map map = new HashMap();
					if(entry.getParent().getConWithoutText() != null){
						map.put(entry.getParent().getConWithoutText().getId().toString(),entry.getAmount());
					}
					conWithoutSplitAmt.put(acctId,map);					
				}else{
					Map map = (Map)conWithoutSplitAmt.get(acctId);
					if(entry.getParent().getConWithoutText() != null 
							&&!map.containsKey(entry.getParent().getConWithoutText().getId().toString())){
						map.put(entry.getParent().getConWithoutText().getId().toString(),entry.getAmount());
					}
				}
			}
		}
		if(FDCHelper.isEmpty(lnUps)){
			return;
		}
		String[] contractIds = new String[lnUps.size()];
		int i = 0;
		for(Iterator it = lnUps.iterator();it.hasNext() && i < lnUps.size();i++){
			contractIds[i] = String.valueOf(it.next());
		}
		if(contractIds != null && contractIds.length != 0){
			try {
//				this.lastAmt = ContractFacadeFactory.getRemoteInstance().getLastAmt(contractIds);
				this.lastAmt = FDCUtils.getLastAmt_Batch(null, contractIds);
			} catch (EASBizException e) {
				// TODO �Զ����� catch ��
				e.printStackTrace();
			} catch (BOSException e) {
				// TODO �Զ����� catch ��
				e.printStackTrace();
			}
		}
		evi = new EntityViewInfo();
		filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("costAccount.id", hasCostAccount, CompareType.NOTINCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("contract.programming.project.id", selectObjId));
		filter.getFilterItems().add(new FilterItemInfo("contract.programming.isLatest", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("contract.isCiting", Boolean.FALSE));
		evi.setFilter(filter);
		evi.getSelector().add(new SelectorItemInfo("costAccount.id"));
		evi.getSelector().add(new SelectorItemInfo("contract.longNumber"));
		evi.getSelector().add(new SelectorItemInfo("contract.name"));
		evi.getSelector().add(new SelectorItemInfo("contractAssign"));
		
		ProgrammingContracCostCollection pcCol=ProgrammingContracCostFactory.getRemoteInstance().getProgrammingContracCostCollection(evi);
		pcMap.clear();
		pcContractMap.clear();
		for(int k=0;k<pcCol.size();k++){
			if(pcMap.containsKey(pcCol.get(k).getCostAccount().getId().toString())){
				ProgrammingContractCollection pcContractCol=(ProgrammingContractCollection) pcMap.get(pcCol.get(k).getCostAccount().getId().toString());
				pcContractCol.add(pcCol.get(k).getContract());
			}else{
				ProgrammingContractCollection pcContractCol=new ProgrammingContractCollection();
				pcContractCol.add(pcCol.get(k).getContract());
				pcMap.put(pcCol.get(k).getCostAccount().getId().toString(), pcContractCol);
			}
			pcContractMap.put(pcCol.get(k).getCostAccount().getId().toString()+pcCol.get(k).getContract().getId().toString(), pcCol.get(k).getContractAssign());
		}
		
		// ContractBillCollection cbc = icb.getContractBillCollection(evi);
		// if (cbc.size() != 0) {
		// for (int i = 0; i < cbc.size(); i++) {
		// // cbMap.put(cbc.get(i).getId().toString(),cbc.get(i));
		// lnUps.add(cbc.get(i).getId().toString());
		// }
		// }
	}

	private void initRealMap() throws BOSException {
		String fullOrgUnitId  = null;
		IContractCostSplitEntry icse = ContractCostSplitEntryFactory.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		evi.getSelector().add(new SelectorItemInfo("*"));
		evi.getSelector().add(new SelectorItemInfo("costAccount.id"));
		evi.getSelector().add(new SelectorItemInfo("parent.contractBill.id"));
		evi.getSelector().add(new SelectorItemInfo("parent.contractBill.name"));
		evi.getSelector().add(new SelectorItemInfo("parent.contractBill.signDate"));
		evi.getSelector().add(new SelectorItemInfo("parent.contractBill.contractSourceId.id"));
		evi.getSelector().add(new SelectorItemInfo("parent.contractBill.contractSourceId.name"));
		evi.getSelector().add(new SelectorItemInfo("parent.contractBill.amount"));
		evi.getSelector().add(new SelectorItemInfo("parent.contractBill.number"));
		evi.getSelector().add(new SelectorItemInfo("parent.contractBill.partB.name"));
		evi.getSelector().add(new SelectorItemInfo("parent.contractBill.hasSettlted"));
		evi.getSelector().add(new SelectorItemInfo("parent.contractBill.*"));
		evi.getSorter().add(new SorterItemInfo("parent.contractBill.number"));
		if (lnUps.size() != 0) {// ��ǰ������Ŀ���к�ͬ
			filter.getFilterItems().add(new FilterItemInfo("parent.contractBill.id", lnUps, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("parent.state",FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
			evi.setFilter(filter);
			ContractCostSplitEntryCollection ccsec = icse.getContractCostSplitEntryCollection(evi);
			if (ccsec.size() != 0) {
				for (int i = 0; i < ccsec.size(); i++) {
					if (!realMap.containsKey(ccsec.get(i).getCostAccount().getId().toString())) {
						ContractBillCollection cbc = new ContractBillCollection();
						cbc.add(ccsec.get(i).getParent().getContractBill());
						realMap.put(ccsec.get(i).getCostAccount().getId().toString(), cbc);
					} else {
						ContractBillCollection cbc = (ContractBillCollection) realMap.get(ccsec.get(i).getCostAccount().getId().toString());
						if (!cbc.contains(ccsec.get(i).getParent().getContractBill())) {
							cbc.add(ccsec.get(i).getParent().getContractBill());
							realMap.put(ccsec.get(i).getCostAccount().getId().toString(), cbc);
						}
					}
					if (((!ccsec.get(i).isIsLeaf())
							&& ccsec.get(i).getSplitType() != null && ccsec
							.get(i).getSplitType().equals(
									CostSplitTypeEnum.PRODSPLIT))
							|| (ccsec.get(i).getSplitType() != null && !ccsec
									.get(i).getSplitType().equals(
											CostSplitTypeEnum.PRODSPLIT))
							|| (ccsec.get(i).getSplitType() == null)) {
						if (splitAmtMap.containsKey(ccsec.get(i).getCostAccount().getId().toString())) {
							Map map = (Map) splitAmtMap.get(ccsec.get(i).getCostAccount().getId().toString());
							if (map.containsKey(ccsec.get(i).getParent().getContractBill().getId().toString())) {
								BigDecimal t = (BigDecimal) map.get(ccsec.get(i).getParent().getContractBill().getId().toString());
								t = t.add(ccsec.get(i).getAmount());
								map.put(ccsec.get(i).getParent().getContractBill().getId().toString(), t);
							} else {
								map.put(ccsec.get(i).getParent().getContractBill().getId().toString(), ccsec.get(i).getAmount());
							}
							splitAmtMap.put(ccsec.get(i).getCostAccount().getId().toString(), map);
						} else {
							Map map = new HashMap();
							map.put(ccsec.get(i).getParent().getContractBill().getId().toString(), ccsec.get(i).getAmount());
							splitAmtMap.put(ccsec.get(i).getCostAccount().getId().toString(), map);
						}
					}
				}
			}
			//��԰Ҫ���ڿ�Ŀ��ͬ��ϸ�������ӱ����ֽ���У���Ϊ֮ǰ�ڸñ����в�δ���ǵ������֣���ȡ��ʾ�ڽ����еĺ�ͬ��ʱ��ֻ�Խ��й�
			//��ͬ���Ϊ������������Ҫ�����ӱ����ֽ���еĻ����ڱ����ֲ���ĳ����Ŀ���Ǻ�ͬ��ֲ�û�в𵽸ÿ�Ŀ���������ʱ�ú�ͬ��
			//������ʾ�ڱ����У�Ӱ��������ʾ�� �������Խ��й���ͬ��ֻ����Ǳ�����Ϊ����������ɸѡ��ʾ�ڽ���ĺ�ͬ  by Cassiel_peng 2008-9-4
			ConChangeSplitEntryCollection conChangeSplitEntryCollection=ConChangeSplitEntryFactory.getRemoteInstance().getConChangeSplitEntryCollection(evi);
			if (conChangeSplitEntryCollection.size() != 0) {
				for (int i = 0; i < conChangeSplitEntryCollection.size(); i++) {
					if (!realMap.containsKey(conChangeSplitEntryCollection.get(i).getCostAccount().getId().toString())) {
						ContractBillCollection cbc = new ContractBillCollection();
						cbc.add(conChangeSplitEntryCollection.get(i).getParent().getContractBill());
						realMap.put(conChangeSplitEntryCollection.get(i).getCostAccount().getId().toString(), cbc);
					} else {
						ContractBillCollection cbc = (ContractBillCollection) realMap.get(conChangeSplitEntryCollection.get(i).getCostAccount().getId().toString());
						if (!cbc.contains(conChangeSplitEntryCollection.get(i).getParent().getContractBill())) {
							cbc.add(conChangeSplitEntryCollection.get(i).getParent().getContractBill());
							realMap.put(conChangeSplitEntryCollection.get(i).getCostAccount().getId().toString(), cbc);
						}
					}
				}
			}
		}
		IPayRequestBill ipb = PayRequestBillFactory.getRemoteInstance();
		totalSettMap.clear();
		if(lnUps.size()!=0){
			try {
				totalSettMap = ContractFacadeFactory.getRemoteInstance().getTotalSettlePrice(lnUps);
				totalComplete = ContractExecFacadeFactory.getRemoteInstance()._getCompleteAmt(getSelectObjId(), lnUps);
						
			} catch (EASBizException e) {
				// TODO �Զ����� catch ��
				e.printStackTrace();
			} catch (BOSException e) {
				// TODO �Զ����� catch ��
				e.printStackTrace();
			}
			
		}
	}

	Map pbAmtMap = new HashMap();

	private void initPayAmtMap() throws BOSException {
		// ��ȡ��ǰ������Ŀ�ڵ��º�ͬ�����������Ѹ����ļ���
		// ��ȡÿ����ͬ�������Ѹ������ļ���
		Set allIds = new HashSet();
		//�Ѻ�ͬid�����ı���ͬid����set ȡ�����Ѹ�������
		for(Iterator it = lnUps.iterator();it.hasNext();){
			allIds.add(it.next());
		}
		for(Iterator it = conWithoutIds.iterator();it.hasNext();){
			allIds.add(it.next());
		}
		IPaymentBill ipb = PaymentBillFactory.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		evi.getSelector().add(new SelectorItemInfo("id"));
		evi.getSelector().add(new SelectorItemInfo("actPayAmt"));
		evi.getSelector().add(new SelectorItemInfo("actPayLocAmt"));
		evi.getSelector().add(new SelectorItemInfo("contractBillId"));
		evi.getSelector().add(new SelectorItemInfo("billStatus"));
//		filter.getFilterItems().add(new FilterItemInfo("contractBillId", lnUps, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("contractBillId", allIds, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("billStatus", BillStatusEnum.PAYED));
		evi.setFilter(filter);
		PaymentBillCollection pbc = ipb.getPaymentBillCollection(evi);
		if (pbc.size() != 0) {
			for (int i = 0; i < pbc.size(); i++) {
				pBlnUps.add(pbc.get(i).getId().toString());
			}
			for (int i = 0; i < pbc.size(); i++) {
				String key = pbc.get(i).getContractBillId();
				//�Ѹ�����Ӧȡ����
				if (pbAmtMap.containsKey(key)) {
					BigDecimal tmp = (BigDecimal) pbAmtMap.get(key);
//					tmp = tmp.add(pbc.get(i).getActPayAmt());
					tmp = FDCHelper.add(tmp,pbc.get(i).getActPayLocAmt());
					pbAmtMap.put(key, tmp);
				} else {
//					pbAmtMap.put(key, pbc.get(i).getActPayAmt());
					pbAmtMap.put(key, pbc.get(i).getActPayLocAmt());
				}
			}
		}
	}

	Map cbMap = new HashMap();
	
	/**
	 *���ע��  by Cassiel_peng
	 */
	/**
	 * ��ͬid����
	 */
	HashSet lnUps = new HashSet();
	
	/**
	 * ���ı���ͬid����
	 */
	private Set conWithoutIds = new HashSet();

	/**
	 * �����Ѹ�����id����
	 */
	HashSet pBlnUps = new HashSet();
	
	/**
	 * �����ѽ�����㵥��id����
	 */
	HashSet sBlnUps=new HashSet();
	/**
	 * ������Ŀ���ͬ�Ľ�����Map
	 */
	Map settSplitAmtMap=new HashMap();

	/**
	 * ��ͬ��ֽ��Map
	 */
	Map splitAmtMap = new HashMap();
	/**
	 * ��ʵ�ֳɱ�Map
	 */
	Map hasPayCostAmtMap=new HashMap();
	/**
	 * ������Ŀ���ͬ�ĸ�����Map
	 */
	Map paymentSplitAmtMap=new HashMap();
	/**
	 * ������Ŀ���ͬ�ı�����Map
	 */
	Map changeSplitAmtMap=new HashMap();
	
	Map paySplitAmtMap = new HashMap();

	ArrayList cbList = new ArrayList();

	Map realMap = new HashMap();
	
	Map pcMap=new HashMap();
	Map pcContractMap=new HashMap();
	public void fetchLeafData(HashMap resultMap) throws Exception {
		this.realMap.clear();
		this.splitAmtMap.clear();
		this.cbMap.clear();
		this.pbAmtMap.clear();
		this.lnUps.clear();
		this.pBlnUps.clear();
		
		String selectObjId = getSelectObjId();
		CurProjectInfo project = getSelectProject();
		if (project != null && project.isIsLeaf()) {
			//�����ǰ��Ŀ�Ŀ�Ŀ������,���˳�
			FilterInfo filter=new FilterInfo();
			filter.appendFilterItem("curProject.id",project.getId().toString());
			boolean isExist = CostAccountFactory.getRemoteInstance().exists(filter);
			if(!isExist){
				SysUtil.abort();
			}
			//��ʼ��Ŀ��ɱ� ��̬�ɱ�
			fetchData(selectObjId);
			initCbMap(selectObjId);// ��ʼ����ͬmap
			if (lnUps.size() != 0) {// ��ǰ������Ŀ���к�ͬ
				initRealMap();// ��ʼ����Ŀ��Ӧ��ͬmap
				initPayAmtMap();// ��ʼ���Ѹ���map
				// initPayBillEntryMap();//��ʼ���Ѹ��������Ŀ���ͬ�Ĳ�ֽ��
				fetchSettAndPaymentAndChangeSplitData(selectObjId);//��𡣿϶�����ˣ� by  Cassiel_peng
			}

		}
	}
	

	public void fillTable() throws Exception {
		this.txtBuildArea.setValue(null);
		this.txtSaleArea.setValue(null);
		tblMain.removeRows();
//		setTableColumnShow(true);
		// this.paySplitAmtMap.clear();
		tblMain.setUserObject(null);
		CurProjectInfo project = getSelectProject();
		if (project != null && project.isIsLeaf()) {
			//��ʼ��Ŀ��ɱ� ��̬�ɱ�
//			fetchData(selectObjId);
			String selectObjId = getSelectObjId();
			//�������  ������� 
			Map map = ProjectHelper.getIndexValue(null, selectObjId, new String[] { ApportionTypeInfo.buildAreaType, ApportionTypeInfo.sellAreaType }, ProjectStageEnum.DYNCOST, false);
			buildArea = (BigDecimal) map.get(selectObjId + " " + ApportionTypeInfo.buildAreaType);
			sellArea = (BigDecimal) map.get(selectObjId + " " + ApportionTypeInfo.sellAreaType);
			this.txtBuildArea.setValue(this.buildArea==null?null:FDCHelper.toBigDecimal(this.buildArea,2));
			this.txtSaleArea.setValue(this.sellArea==null?null:FDCHelper.toBigDecimal(this.sellArea,2));
			
			if (lnUps.size() != 0) {// ��ǰ������Ŀ���к�ͬ
				BOSObjectType bosType = project.getId().getType();
				FilterInfo acctFilter = new FilterInfo();
				if (new CurProjectInfo().getBOSType().equals(bosType)) {
					acctFilter.getFilterItems().add(new FilterItemInfo("curProject.id", project.getId().toString()));
				}
				acctFilter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
				AcctAccreditHelper.handAcctAccreditFilter(null, project.getId().toString(), acctFilter);
				TreeModel costAcctTree;
				costAcctTree = FDCClientHelper.createDataTree(CostAccountFactory.getRemoteInstance(), acctFilter);
				DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) costAcctTree.getRoot();
				Enumeration childrens = root.depthFirstEnumeration();
				int maxLevel = 0;
				while (childrens.hasMoreElements()) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) childrens.nextElement();
					if (node.getUserObject() != null && node.getLevel() > maxLevel) {
						maxLevel = node.getLevel();
					}
				}
				tblMain.getTreeColumn().setDepth(maxLevel + 1);
				for (int i = 0; i < root.getChildCount(); i++) {
					fillNode((DefaultMutableTreeNode) root.getChildAt(i));
				}
				gatherDatas();
			}

		}
	}

	private void fillNode(DefaultMutableTreeNode node) throws BOSException, SQLException, EASBizException {
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
			.getLastSelectedPathComponent();
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		if (costAcct == null) {
			return;
		}
		String acctId = costAcct.getId().toString();
		IRow row = tblMain.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		row.getCell("acctNumber").setValue(costAcct.getLongNumber().replace('!', '.'));
		row.getCell("acctName").setValue(costAcct.getName());
		row.getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		if (node.isLeaf()) {
			//Ŀ��ɱ�
			BigDecimal aimAmount = (BigDecimal) this.aimCostMap.get(acctId);
			//��̬�ɱ�
			row.getCell("aimCost").setValue(aimAmount);
			HappenDataInfo happenDataInfo = (HappenDataInfo) this.happenGetter.conSplitMap
			.get(acctId + 0);
			BigDecimal noSettConAmount = null;
			if (happenDataInfo != null) {
				noSettConAmount = happenDataInfo.getAmount();
			}
			BigDecimal noSettleChangeSumAmount = null;
			for (int i = 0; i < changeTypes.size(); i++) {
				ChangeTypeInfo change = changeTypes.get(i);
				happenDataInfo = (HappenDataInfo) this.happenGetter.changeSplitMap
						.get(acctId + change.getId().toString() + 0);
				BigDecimal changeAmount = null;
				if (happenDataInfo != null) {
					changeAmount = happenDataInfo.getAmount();
				}
				if (changeAmount != null) {
					if (noSettleChangeSumAmount == null) {
						noSettleChangeSumAmount = FDCHelper.ZERO;
					}
					noSettleChangeSumAmount = noSettleChangeSumAmount
							.add(changeAmount);
				}
			}
			BigDecimal noSettleTotal = null;
			if (noSettConAmount != null) {
				noSettleTotal = noSettConAmount;
			}
			if (noSettleChangeSumAmount != null) {
				if (noSettleTotal == null) {
					noSettleTotal = noSettleChangeSumAmount;
				} else {
					noSettleTotal = noSettleTotal.add(noSettleChangeSumAmount);
				}
			}
			happenDataInfo = (HappenDataInfo) this.happenGetter.conSplitMap
				.get(acctId + 1);
			BigDecimal settConAmount = null;
			if (happenDataInfo != null) {
				settConAmount = happenDataInfo.getAmount();
			}
			BigDecimal settleChangeSumAmount = null;
			for (int i = 0; i < changeTypes.size(); i++) {
				ChangeTypeInfo change = changeTypes.get(i);
				happenDataInfo = (HappenDataInfo) this.happenGetter.changeSplitMap
						.get(acctId + change.getId().toString() + 1);
				BigDecimal changeAmount = null;
				if (happenDataInfo != null) {
					changeAmount = happenDataInfo.getAmount();
				}
				if (changeAmount != null) {
					if (settleChangeSumAmount == null) {
						settleChangeSumAmount = FDCHelper.ZERO;
					}
					settleChangeSumAmount = settleChangeSumAmount
							.add(changeAmount);
				}
			}
			happenDataInfo = (HappenDataInfo) this.happenGetter.settleSplitMap.get(acctId);
			BigDecimal settleTotal = null;
			if (happenDataInfo != null) {
				settleTotal = happenDataInfo.getAmount();
			}
			BigDecimal settleAdjust = null;
			if (settleTotal != null) {
				settleAdjust = settleTotal;
			}
			if (settConAmount != null) {
				if (settleAdjust == null) {
					settleAdjust = FDCHelper.ZERO;
				}
				settleAdjust = settleAdjust.subtract(settConAmount);
			}
			if (settleChangeSumAmount != null) {
				if (settleAdjust == null) {
					settleAdjust = FDCHelper.ZERO;
				}
				settleAdjust = settleAdjust.subtract(settleChangeSumAmount);
			}
			happenDataInfo = (HappenDataInfo) this.happenGetter.noTextSplitMap
				.get(acctId);
			BigDecimal noTextAmount = null;
			if (happenDataInfo != null) {
				noTextAmount = happenDataInfo.getAmount();
			}
			BigDecimal hasHappenAmount = null;
			if (noSettleTotal != null) {
				hasHappenAmount = noSettleTotal;
			}
			if (settleTotal != null) {
				if (hasHappenAmount == null) {
					hasHappenAmount = FDCHelper.ZERO;
				}
				hasHappenAmount = hasHappenAmount.add(settleTotal);
			}
			if (noTextAmount != null) {
				if (hasHappenAmount == null) {
					hasHappenAmount = FDCHelper.ZERO;
				}
				hasHappenAmount = hasHappenAmount.add(noTextAmount);
			}
			//�ѷ���
			row.getCell("hasHappen").setValue(hasHappenAmount);
			
			row.getCell("noHappen").setValue(this.noHappenMap.get(acctId));
			
			BigDecimal adjustAmount = null;
			BigDecimal dynamicAmount = null;
			// ��̬�ɱ����ڵ�Ҫ�����
			if (proNode.isLeaf()) {
				adjustAmount = (BigDecimal) this.dyCostMap.get(acctId);
			} else {
				adjustAmount = getSumAdjustCol(acctId,costAcct);
			}
			if (aimAmount != null) {
				dynamicAmount = aimAmount;
			}
			if (adjustAmount != null) {
				if (dynamicAmount == null) {
					dynamicAmount = adjustAmount;
				} else {
					dynamicAmount = dynamicAmount.add(adjustAmount);
				}
			}
			BigDecimal intendingHappen = null;
			if (dynamicAmount != null) {
				intendingHappen = dynamicAmount;
			}
			if (hasHappenAmount != null) {
				if (intendingHappen == null) {
					intendingHappen = FDCHelper.ZERO;
				}
				intendingHappen = intendingHappen.subtract(hasHappenAmount);
			}
			//������
//			row.getCell("indentingHappen").setValue(intendingHappen);
//			row.getCell("dyCost").setValue(dynamicAmount);
			//���۵���  ��������
//			if(dynamicAmount!=null&&sellArea!=null&&sellArea.compareTo(FDCHelper.ZERO)!=0&&dynamicAmount.compareTo(FDCHelper.ZERO)!=0){
//				row.getCell("sellPart").setValue(FDCHelper.divide(dynamicAmount, sellArea,2,BigDecimal.ROUND_HALF_UP));
//			}
//			if(dynamicAmount!=null&&buildArea!=null&&buildArea.compareTo(FDCHelper.ZERO)!=0&&dynamicAmount.compareTo(FDCHelper.ZERO)!=0){
//				row.getCell("buildPart").setValue(FDCHelper.divide(dynamicAmount, buildArea,2,BigDecimal.ROUND_HALF_UP));
//			}
//			if(aimAmount != null && dynamicAmount != null){
//				row.getCell("diff").setValue(dynamicAmount.subtract(aimAmount));
//			}
			row.getCell("absolute").setValue(FDCHelper.subtract(aimAmount, hasHappenAmount));
       		 
        	if(aimAmount==null||aimAmount.compareTo(FDCHelper.ZERO)==0){
        		row.getCell("rate").setValue(null);
        	}else{
        		row.getCell("rate").setValue(FDCHelper.divide(hasHappenAmount, aimAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
        	}
        	 
			//�Ƿ����ò��ֽ���
//			if(isUsePartSettle()){
//				HappenDataInfo partSettInfo = this.happenGetter.getPartSettleInfo(acctId);
//				BigDecimal partSettleAmt=null;
//				if(partSettInfo!=null){
//					partSettleAmt=partSettInfo.getAmount();
//				}
//				//��ʵ�ֲ�ֵ�����ֽ���+�ѽ����ͬ+�Ǻ�ͬ�Գɱ�֮��
//				BigDecimal hasAllSettleAmt=FDCNumberHelper.add(partSettleAmt, settleTotal);
//				hasAllSettleAmt=FDCNumberHelper.add(hasAllSettleAmt, noTextAmount);
//				row.getCell("totalSettPrice").setValue(hasAllSettleAmt);
//			}
			ContractBillCollection cbc = (ContractBillCollection) this.realMap.get(acctId);
//			if (cbc != null) {
			//���ȫ����ʾ ���� ֻ��ʾ��ͬ��Ϣ
			if (cbc != null && (isDisplayAll || isDisplayContract)) {
				CRMHelper.sortCollection(cbc, "number", true);
				for (int i = 0; i < cbc.size(); i++) {
					ContractBillInfo info = cbc.get(i);
					IRow eRow = this.tblMain.addRow();
					eRow.getStyleAttributes().setHided(isAccount);
					eRow.setTreeLevel(node.getLevel());
					eRow.setUserObject(info);
					loadRow(eRow);// ��ͬ�������
					// ArrayList al = (ArrayList) splitAmtMap.get(acctId);
					// if (al != null && al.size() != 0) {
					// for (int j = 0; j < al.size(); j++) {
					// Map map = (Map) al.get(j);
					// if (map.get(info.getId().toString()) != null) {
					// eRow.getCell("splitAmt").setValue(map.get(info.getId().toString()));//��ֽ��
					// }
					// }
					// }��ĳһ���ɱ���Ŀ�µ����к�ͬ��ֵȡ����
					Map map = (Map) splitAmtMap.get(acctId);
					if (map != null) {
						eRow.getCell("splitAmt").setValue(map.get(info.getId().toString()));// ��ֽ��     by Cassiel_peng 
					}
					// al = (ArrayList) paySplitAmtMap.get(acctId);
					// if (al != null && al.size() != 0) {
					// for (int j = 0; j < al.size(); j++) {
					// Map map = (Map) al.get(j);
					// if (map.get(info.getId().toString()) != null) {
					// eRow.getCell("hasPayAmt").setValue(map.get(info.getId().toString()));
					// }
					// }
					// }
//					eRow.getCell("hasPayAmt").setValue(pbAmtMap.get(info.getId().toString()));// �Ѹ�����
					
					/**
					 * �����ֽ��&�����ֽ��&�����ֽ�� by Cassiel_peng
					 */
					Map settSplitMap=(Map)settSplitAmtMap.get(acctId);
					if(settSplitMap!=null){
						eRow.getCell("settSplitAmt").setValue(settSplitMap.get(info.getId().toString()));
					}
					Map hasPayCostMap=(Map)hasPayCostAmtMap.get(acctId);
					if(hasPayCostMap!=null){
						eRow.getCell("hasPayCostAmt").setValue(hasPayCostMap.get(info.getId().toString()));
					}
					Map paymentBillSplitMap=(Map)paymentSplitAmtMap.get(acctId);
					if(paymentBillSplitMap!=null){
						eRow.getCell("paymentSplitAmt").setValue(paymentBillSplitMap.get(info.getId().toString()));
					}
					Map changeSplitMap=(Map)changeSplitAmtMap.get(acctId);
					if(changeSplitMap!=null){
						eRow.getCell("changeSplitAmt").setValue(changeSplitMap.get(info.getId().toString()));
					}
					
					eRow.getCell("hasPayAmt").setValue(FDCHelper.ZERO);
					if(pbAmtMap.containsKey(info.getId().toString())){
						eRow.getCell("hasPayAmt").setValue(pbAmtMap.get(info.getId().toString()));// �Ѹ�����
					}
					
					eRow.getCell("allNotPaid").setValue(FDCHelper.subtract(eRow.getCell("lastPrice").getValue(),eRow.getCell("hasPayAmt").getValue()));// �Ѹ�����
					
					BigDecimal hasPayAmt=(BigDecimal) eRow.getCell("hasPayAmt").getValue();
					BigDecimal lastPrice=(BigDecimal) eRow.getCell("lastPrice").getValue();
					if(lastPrice==null||lastPrice.compareTo(FDCHelper.ZERO)==0){
						eRow.getCell("payPercent").setValue(null);
		        	}else{
		        		eRow.getCell("payPercent").setValue(hasPayAmt.divide(lastPrice, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
		        	}
					
//					//�깤δ���� = ��ͬ��ʵ�ֲ�ֵ - ��ͬ�Ѹ���
//					BigDecimal hasPayAmt = (BigDecimal)eRow.getCell("hasPayAmt").getValue();
////					BigDecimal totalSettAmt = (BigDecimal)eRow.getCell("totalSettPrice").getValue();
//					BigDecimal complete = FDCHelper.ZERO;
//					BigDecimal allPaidAmt = FDCHelper.ZERO;
//					if(totalComplete.get(info.getId().toString()) != null){
//						complete = (BigDecimal) totalComplete.get(info.getId().toString());
//					}
//					if(hasPayAmt == null){
//						hasPayAmt = FDCHelper.ZERO;
//					}
////					if(totalSettAmt == null){
////						totalSettAmt = FDCHelper.ZERO;
////					}
//					
//					
//					
//					if(hasPayAmt != null ){
//						if(info.isHasSettled()){
//							allPaidAmt = info.getSettleAmt().subtract(hasPayAmt);
//						}else{
//							if(isUsePartSettle()){
////							   allPaidAmt = totalSettAmt.subtract(hasPayAmt);
//							}else{
//							   allPaidAmt = complete.subtract(hasPayAmt);	
//							}
//						}
//						eRow.getCell("allNotPaid").setValue(allPaidAmt);
////						if(totalSettAmt != null && !(totalSettAmt.compareTo(FDCHelper.ZERO)==0)){
//////							eRow.getCell("payPercent").setValue(hasPayAmt.multiply(FDCHelper.ONE_HUNDRED).divide(totalSettAmt,2,BigDecimal.ROUND_HALF_UP)+"%");
////							eRow.getCell("payPercent").setValue(FDCHelper.divide(FDCHelper.multiply(hasPayAmt, FDCHelper.ONE_HUNDRED), totalSettAmt,2,BigDecimal.ROUND_HALF_UP)+"%");
////						}
//					}
//					
//					BigDecimal lastPrice = FDCHelper.ZERO;
//					if(lastPriceMap != null){
//						lastPrice = (BigDecimal) lastPriceMap.get(contractId);
//					}
//					
//					
//					if(isMoreSett){
//						if(isTotalSett){
//							allNotPay = lastPrice.subtract(hasPayAmt);
//						}else{
//							allNotPay = totalSettlePrice.subtract(hasPayAmt);
//						}
//					}else{
//						if(isTotalSett){
//							 allNotPay = lastPrice.subtract(hasPayAmt);
//						}else{
//							 allNotPay = complete.subtract(hasPayAmt);
//						}
//						  
//					}
//					
//					
//					
//					
//					if(allNotPay.compareTo(FDCHelper.ZERO)!=0)
//						row.getCell("allNotPaid").setValue(allNotPay);
//					
					/**
					 * ��ϸ��ͬ�е�Ŀǰ�ѷ�����ȡ����
					 * ����ͬδ���ս��㣬��ȡ���к�ͬ��ֽ�� + �����ֽ��
					 * ����ͬ�����ս��㣬��ȡ���н����ֽ��   by Cassiel_peng  2009-10-2
					 */
//					if(eRow.getCell("contract").getValue()!=null){
//						if(!info.isHasSettled()){
//							eRow.getCell("hasHappen").setValue(FDCHelper.add(eRow.getCell("splitAmt").getValue(), eRow.getCell("changeSplitAmt").getValue()));
//						}else{
//							eRow.getCell("hasHappen").setValue(eRow.getCell("settSplitAmt").getValue());
//						}
//					}
				}
			}
			ContractWithoutTextCollection cwtc = (ContractWithoutTextCollection)this.conWithoutMap.get(acctId);
			//���ȫ����ʾ ���� ֻ��ʾ���ı���ͬ��Ϣ
			if(cwtc!=null && cwtc.size() != 0 && (isDisplayAll || isDisplayConNoText)){
				CRMHelper.sortCollection(cwtc, "number", true);
				for(int i = 0; i < cwtc.size();i++){
					ContractWithoutTextInfo info = cwtc.get(i);
					IRow newRow = this.tblMain.addRow();
					newRow.getStyleAttributes().setHided(isAccount);
					newRow.setTreeLevel(node.getLevel());
					newRow.setUserObject(info);
					//������ı���ͬ
					fillConWithoutText(newRow);
					Map map = (Map)this.conWithoutSplitAmt.get(acctId);
					if(map!= null){
						newRow.getCell("splitAmt").setValue(map.get(info.getId().toString()));
					}
					//�Ѹ���
					newRow.getCell("hasPayAmt").setValue(FDCHelper.ZERO);
					if(pbAmtMap.containsKey(info.getId().toString())){
						newRow.getCell("hasPayAmt").setValue(pbAmtMap.get(info.getId().toString()));
					}
					newRow.getCell("allNotPaid").setValue(FDCHelper.subtract(newRow.getCell("amt").getValue(),newRow.getCell("hasPayAmt").getValue()));// �Ѹ�����
					
					BigDecimal hasPayAmt=(BigDecimal) newRow.getCell("hasPayAmt").getValue();
					BigDecimal amt=(BigDecimal) newRow.getCell("amt").getValue();
					if(amt==null||amt.compareTo(FDCHelper.ZERO)==0){
						newRow.getCell("payPercent").setValue(null);
		        	}else{
		        		newRow.getCell("payPercent").setValue(hasPayAmt.divide(amt, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
		        	}
					/**
					 * �����ֽ��&�����ֽ��&�����ֽ�� by Cassiel_peng
					 */
					Map settSplitMap=(Map)settSplitAmtMap.get(acctId);
					if(settSplitMap!=null){
						newRow.getCell("settSplitAmt").setValue(settSplitMap.get(info.getId().toString()));
					}
					Map hasPayCostMap=(Map)hasPayCostAmtMap.get(acctId);
					if(hasPayCostMap!=null){
						newRow.getCell("hasPayCostAmt").setValue(hasPayCostMap.get(info.getId().toString()));
					}
					Map paymentBillSplitMap=(Map)paymentSplitAmtMap.get(acctId);
					if(paymentBillSplitMap!=null){
						newRow.getCell("paymentSplitAmt").setValue(paymentBillSplitMap.get(info.getId().toString()));
					}
					Map changeSplitMap=(Map)changeSplitAmtMap.get(acctId);
					if(changeSplitMap!=null){
						newRow.getCell("changeSplitAmt").setValue(changeSplitMap.get(info.getId().toString()));
					}
//					if(newRow.getCell("contract").getValue()!=null){
//						newRow.getCell("hasHappen").setValue(newRow.getCell("splitAmt").getValue());
//					}
				}
			}
		} else {
		}
		ProgrammingContractCollection pcCol = (ProgrammingContractCollection)this.pcMap.get(acctId);
		if(pcCol!=null && pcCol.size() != 0){
			CRMHelper.sortCollection(pcCol, "longNumber", true);
			for(int i = 0; i < pcCol.size();i++){
				ProgrammingContractInfo info = pcCol.get(i);
				IRow newRow = this.tblMain.addRow();
				newRow.getStyleAttributes().setHided(isAccount);
				newRow.setTreeLevel(node.getLevel());
				newRow.setUserObject(info);
				newRow.getCell("contract").setValue(info.getName());
				newRow.getCell("conNumber").setValue(info.getLongNumber());
				newRow.getCell("type").setValue("��Լ�滮");
				newRow.getCell("amt").setValue(pcContractMap.get(acctId+info.getId().toString()));
			}
		}
		//
		for (int i = 0; i < node.getChildCount(); i++) {
			this.fillNode((DefaultMutableTreeNode) node.getChildAt(i));
		}
	}

	//�ڴ�ͻ���Ϊ��"�깤δ����"����Ƿǲ�����ݣ���Ӧ�����ϻ���.
	//���ڸĶ����е�˳����Ϊ�������������У�gatherDatas�����Ѳ����ڵ�ǰ�龰��ʹ��   
	//amountColumns ��Ҫ���ܵ�����  by Cassiel_peng
	private List getUnionColumn(){
		List amountColumns = new ArrayList();
		amountColumns.add("splitAmt");//��ͬ��ֽ��
		amountColumns.add("aimCost");//Ŀ��ɱ�
		amountColumns.add("hasHappen");//��̬�ɱ�
		amountColumns.add("noHappen");//��̬�ɱ�
		amountColumns.add("absolute");//����
		amountColumns.add("settSplitAmt");//������
//		amountColumns.add("hasPayCostAmt");//������
		amountColumns.add("paymentSplitAmt");//������
		amountColumns.add("changeSplitAmt");//������
//		amountColumns.add("sellPart");//���۵���
//		amountColumns.add("buildPart");//��������
		return amountColumns;
	}
	
	/*
	 * ��������
	 */
	private void gatherDatas() {
		KDTable table = this.tblMain;
		List amountColumns = getUnionColumn();
		List zeroLeverRowList = new ArrayList();
/*//		for (int j = 8; j < table.getColumnCount(); j++) {
		for (int j = 8; j < table.getColumnCount()-5; j++) {
			String key=table.getColumnKey(j);
			if(key!=null &&key.equals("hasPayAmt")){
				//�Ѹ������Ǹ���Ľ��,����Ҫ���п�Ŀ����
				continue;
			}
			amountColumns.add(key);
		}*/

		for (int i = 0; i < table.getRowCount(); i++) {

			IRow row = table.getRow(i);
			if (row.getTreeLevel() == 0) {
				zeroLeverRowList.add(row);
			}
			if (row.getUserObject() == null) {// ��Ҷ���
				// ���û�����
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
//					if (rowAfter.getCell(0).getValue() == null){//�����ĸ�Դ,ֻͳ����ϸ
//						rowList.add(rowAfter);
//					}
//					if (rowAfter.getUserObject() != null) {
						//֮ǰ����ϸ�У�ֱ��add����������ϸ�У��������ѷ����У���ϸ��Ŀ��ʾ���ݣ���ϸ��Ŀ�µĺ�ͬ����ʾ�����������������˴�����˵�����Ͳ�Ҫ����
						rowList.add(rowAfter);
//					}
				}
				for (int k = 0; k < amountColumns.size(); k++) {
					String colName = (String) amountColumns.get(k);
					BigDecimal amount = FDCHelper.ZERO;
					boolean hasData = false;
				/**
				 * ��������е������������¼����ܹ�ϵ����Ӧ���Ǹ������εĶ����ڸ��ԵĶ�Ӧ�ɱ����Զ�Ӧ������������¼��ĵ������� by cassiel_peng 2009-12-21
				 */
					if(colName.equals("sellPart")){
						BigDecimal dyCost = FDCHelper.toBigDecimal(row.getCell("hasHappen").getValue());
						amount = FDCNumberHelper.divide(dyCost, this.sellArea, 2, BigDecimal.ROUND_HALF_UP);
						if(dyCost!=null&&dyCost.compareTo(FDCHelper.ZERO)!=0){
							hasData=true;
						}
					} else if(colName.equals("buildPart")){
						BigDecimal dyCost = FDCHelper.toBigDecimal(row.getCell("hasHappen").getValue());
						amount = FDCNumberHelper.divide(dyCost, this.buildArea, 2, BigDecimal.ROUND_HALF_UP);
						if(dyCost!=null&&dyCost.compareTo(FDCHelper.ZERO)!=0){
							hasData=true;
						}
					} else{
						for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
							IRow rowAdd = (IRow) rowList.get(rowIndex);
							//��ǰ��ϸ�����ǲ���ʾ���ݵģ�����Ҫ����ʾ���ݣ��ᵼ���ڴ����ϵ������ظ����ܣ����ݷ���  by Cassiel_peng 2009-10-28
							DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
			        		.getLastSelectedPathComponent();
							if(node.isLeaf()&&node.getUserObject() instanceof CurProjectInfo&&
									rowAdd.getUserObject()!=null&&colName.equals("hasHappen")){
								continue;
							}
							if (rowAdd.getCell(colName).getStyleAttributes().getFontColor().equals(Color.RED)) {
							}
							Object value = rowAdd.getCell(colName).getValue();
							if (value != null) {
								if (value instanceof BigDecimal) {
									amount = amount.add((BigDecimal) value);
								} else if (value instanceof Integer) {
									amount = amount.add(new BigDecimal(((Integer) value).toString()));
								}
								hasData = true;
							}
						}	
					}
					/*if(amount!=null&&amount.compareTo(FDCHelper.ZERO) != 0){
						row.getCell(colName).setValue(amount);
					}
					else{
						row.getCell(colName).setValue(null);
					}*/
	
					if(hasData){
						row.getCell(colName).setValue(amount);
					}
				}
			} else {

			}
//			BigDecimal dyCost = (BigDecimal)row.getCell("dyCost").getValue();
//			BigDecimal aimCost = (BigDecimal)row.getCell("aimCost").getValue();
//			if(dyCost != null && aimCost != null){
//				row.getCell("diff").setValue(dyCost.subtract(aimCost));
//			}
			BigDecimal aimAmount = (BigDecimal)row.getCell("aimCost").getValue();
			BigDecimal hasHappenAmount = (BigDecimal)row.getCell("hasHappen").getValue();
			row.getCell("absolute").setValue(FDCHelper.subtract(aimAmount, hasHappenAmount));
      		 
        	if(aimAmount==null||aimAmount.compareTo(FDCHelper.ZERO)==0){
        		row.getCell("rate").setValue(null);
        	}else{
        		row.getCell("rate").setValue(FDCHelper.divide(hasHappenAmount, aimAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
        	}
		}
	}

	// /////////////////////////
	public void loadRow(IRow row) throws BOSException {
		ContractBillInfo info = (ContractBillInfo) row.getUserObject();
		row.getCell("type").setValue("��ͬ");
		String contractId = info.getId().toString();
		if(lastAmt.containsKey(contractId)){
			row.getCell("lastPrice").setValue(lastAmt.get(contractId));
		}
		//�Ƿ����ս���
		row.getCell("isFinalSett").setValue(new Boolean(info.isHasSettled()));
		
		// ����ǰ��Ӧ����� = ��ͬ������� * ��1-��ͬ�ʱ��������
		if (!info.isHasSettled()) {
//			row.getCell("payableAmt").setValue(
//					((BigDecimal) lastAmt.get(contractId)).multiply(FDCConstants.ONE.subtract(info.getGrtRate().divide(FDCConstants.ONE_HUNDRED, 2,
//							BigDecimal.ROUND_HALF_UP))));
//			row.getCell("payableAmt").setValue(
//					FDCHelper.multiply(lastAmt.get(contractId),
//							FDCConstants.ONE.subtract(FDCHelper.divide(info
//									.getGrtRate(), FDCConstants.ONE_HUNDRED, 2,
//									BigDecimal.ROUND_HALF_UP))));
					
					
			
		} else { // �����Ӧ����� = ��ͬ������� * ��1-���㵥�ʱ��������
			ContractSettlementBillCollection conStlBillInfoCol = ContractSettlementBillFactory.getRemoteInstance()
					.getContractSettlementBillCollection(
					"where ContractBill.ID = '" + contractId + "'" + " and isFinalSettle = 1");
			//by tim_gao �Ӹ�У��
//			if (conStlBillInfoCol.size() > 0){
//				if(FDCHelper.isEmpty(conStlBillInfoCol.get(0).getQualityGuaranteRate())||FDCHelper.isZero(conStlBillInfoCol.get(0).getQualityGuaranteRate())){
//					row.getCell("payableAmt").setValue(null);
//				}else{
//					row.getCell("payableAmt").setValue(
//							((BigDecimal) lastAmt.get(contractId)).multiply(FDCConstants.ONE.subtract(conStlBillInfoCol.get(0).getQualityGuaranteRate()
//									.divide(FDCConstants.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP))));
//				}
//			}
		
		}
		
		if(info.getName()!=null){
			row.getCell("contract").setValue(info.getName());
			row.getCell("conNumber").setValue(info.getNumber());
		}
		if(info.getPartB()!=null){
			row.getCell("unit").setValue(info.getPartB().getName());
		}
		row.getCell("date").setValue(info.getBookedDate());
		
		//by tim_gao �Ӹ�У��
//		if(FDCHelper.isEmpty(info.getContractSourceId())||"".equals(info.getContractSourceId())){
//			row.getCell("contractSource").setValue(null);
//		}else{
//			row.getCell("contractSource").setValue(info.getContractSourceId().getName());
//		}
		
//		if (info.getContractSourceId() != null && ContractSourceInfo.INVITE_VALUE.equals(info.getContractSourceId().getId())) {
//			row.getCell("isInvite").setValue(Boolean.valueOf(true));
//		} else {
//			row.getCell("isInvite").setValue(Boolean.valueOf(false));
//		}
		row.getCell("amt").setValue(info.getAmount());
		//�����ʵ�ֲ�ֵ
		BigDecimal totalSettPrice ;
		//Ĭ��ֵΪ0
//		row.getCell("totalSettPrice").setValue(FDCHelper.ZERO);
//		if(this.totalSettMap.containsKey(info.getId().toString().concat("_SettlePrice"))){//��ʵ�ֲ�ֵӦȡ���Ҷ���ԭ��,��ȡ��������(Keyֵ)Ӧ�޸�  by Cassiel_peng
//			totalSettPrice = (BigDecimal)totalSettMap.get(info.getId().toString().concat("_SettlePrice"));
//			row.getCell("totalSettPrice").setValue(totalSettPrice);
//		}
		// BigDecimal workload = info.getWorkload();
		// if (workload != null && workload.compareTo(FDCHelper.ZERO) == 0) {
		// workload = null;
		// }
		// row.getCell("workload").setValue(workload);
		// row.getCell("unit").setValue(info.getUnit());
		// BigDecimal price = info.getPrice();
		// if (price != null && price.compareTo(FDCHelper.ZERO) == 0) {
		// price = null;
		// }
		// row.getCell("price").setValue(price);
		// row.getCell("aimCost").setValue(info.getCostAmount());
		// if (workload != null && price != null) {
		// row.getCell("aimCost").getStyleAttributes().setLocked(true);
		// }
		// if (buildArea != null && buildArea.compareTo(FDCHelper.ZERO) != 0) {
		// BigDecimal buildPart = info.getCostAmount().divide(this.buildArea,
		// 2, BigDecimal.ROUND_HALF_UP).setScale(2,
		// BigDecimal.ROUND_HALF_UP);
		// row.getCell("buildPart").setValue(buildPart);
		// }
		// if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
		// BigDecimal sellPart = info.getCostAmount().divide(this.sellArea, 2,
		// BigDecimal.ROUND_HALF_UP).setScale(2,
		// BigDecimal.ROUND_HALF_UP);
		// row.getCell("sellPart").setValue(sellPart);
		// }
		// row.getCell("product").setValue(info.getProduct());
		// row.getCell("description").setValue(info.getDescription());
	}
	//������ı���ͬ������Ϣ��Ϣ
	public void fillConWithoutText(IRow row) {
		ContractWithoutTextInfo info = (ContractWithoutTextInfo)row.getUserObject();
		if(info.getName() != null){
			row.getCell("contract").setValue(info.getName());
			row.getCell("conNumber").setValue(info.getNumber());
		}
		row.getCell("type").setValue("���ı���ͬ");
		row.getCell("date").setValue(info.getBookedDate());
		row.getCell("amt").setValue(info.getAmount());
//		EntityViewInfo ev = new EntityViewInfo();
//		ev.getSelector().add("realSupplier.name");
//		FilterInfo fi = new FilterInfo();
//		fi.getFilterItems().add(new FilterItemInfo("contractId",info.getId().toString(),CompareType.EQUALS));
//		ev.setFilter(fi);
//		PayRequestBillCollection coll;
//		try {
//			coll = PayRequestBillFactory
//				.getRemoteInstance().getPayRequestBillCollection(ev);
//			if(coll.size() == 1 && coll.get(0).getRealSupplier() != null){
//				row.getCell("unit").setValue(coll.get(0).getRealSupplier().getName());						
//			}
//		} catch (BOSException e) {
//			// TODO �Զ����� catch ��
//			e.printStackTrace();
//		}
		if(info.getReceiveUnit()!=null)
			row.getCell("unit").setValue(info.getReceiveUnit().getName());
		if(info.getPerson()!=null)
			row.getCell("unit").setValue(info.getPerson().getName());
	}

	/**
	 * ��ȡquery�е����������ƣ����ع��༭/ɾ��ʱ��ȡ�����ã�Ĭ��ֵΪ"id"���̳����������
	 * 
	 */
	protected String getKeyFieldName() {
		return "acctNumber";
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return CurProjectFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return null;
	}

	protected boolean isCanOrderTable() {
		return false;
	}
	//ȫ����ʾ
	public void actionDisplayAll_actionPerformed(ActionEvent e) throws Exception {
		this.isAccount = false;
//		this.isDisplayAll = true;
//		this.isDisplayConNoText = false;
//		this.isDisplayContract = false;
		this.actionRefresh_actionPerformed(e);
	}
	//ֻ��ʾ���ı���ͬ
	public void actionDisplayConNoText_actionPerformed(ActionEvent e) throws Exception {
		this.isDisplayAll = false;
		this.isDisplayConNoText = true;
		this.isDisplayContract = false;
		this.actionRefresh_actionPerformed(e);
	}
	//ֻ��ʾ��ͬ
	public void actionDisplayContract_actionPerformed(ActionEvent e) throws Exception {
		this.isAccount = true;
//		this.isDisplayAll = false;
//		this.isDisplayConNoText = false;
//		this.isDisplayContract = true;
		this.actionRefresh_actionPerformed(e);
	}
	
	//���Ŀ��ɱ�����̬�ɱ����ѷ�����δ����������
	private void fetchData(String prjId) throws BOSException,EASBizException{
		final FullDynamicCostMap fullDynamicCostMap = FDCCostRptFacadeFactory.getRemoteInstance().getFullDynamicCost(prjId, null);
//		resultMap.put("aimCostMap", fullDynamicCostMap.getAimCostMap());
//		resultMap.put("dyCostMap", fullDynamicCostMap.getDynamicCostMapp());
//		resultMap.put("happenGetter", fullDynamicCostMap.getHappenDataGetter());
		this.aimCostMap=fullDynamicCostMap.getAimCostMap();
		this.dyCostMap=fullDynamicCostMap.getDynamicCostMapp();
		this.happenGetter=fullDynamicCostMap.getHappenDataGetter();
		this.noHappenMap=fullDynamicCostMap.getNoHappen();
	}
	
	private void fetchSettAndPaymentAndChangeSplitData(String prjId) throws BOSException,EASBizException,SQLException{
//		hasPayCostAmtMap= FDCBudgetAcctHelper.getPayedAmtWithCost(null, prjId);
		paymentSplitAmtMap= FDCBudgetAcctHelper.getPayedAmtWithPayment(null, prjId);
	    settSplitAmtMap= FDCBudgetAcctHelper.getPayedAmtWithSettlement(null, prjId);
	    changeSplitAmtMap=FDCBudgetAcctHelper.getAmtWithContractChange(null, prjId);
	}
	//��õ������
	private BigDecimal getSumAdjustCol(String acctId,CostAccountInfo acct) throws BOSException,
		SQLException {
		StringBuffer innerSql = new StringBuffer();
		innerSql.append("select fid from " + FDCHelper.getFullAcctSql()
		+ " where ");
		String fullNumber = "";
//		CostAccountInfo acct = (CostAccountInfo) this.acctMap.get(acctId);
		if (acct.getCurProject() != null) {
			fullNumber = acct.getCurProject().getFullOrgUnit().getLongNumber()
				+ "!" + acct.getCurProject().getLongNumber();
		} else {
			fullNumber = acct.getFullOrgUnit().getLongNumber();
		}
		String longNumber = acct.getLongNumber();
		innerSql.append(" (FLongNumber ='").append(longNumber).append("'")
				.append(" or FLongNumber like '")
				.append(longNumber).append("!%' ")
				.append(" or FLongNumber like '%!")
				.append(longNumber).append("!%') ");
		innerSql.append("and (FullNumber =	'").append(fullNumber).append("'")
				.append(" or FullNumber like '")
				.append(fullNumber).append("!%' ")
				.append(" or FullNumber like '%!")
				.append(fullNumber).append("!%') ");

		String sql = "select sum(FAdjustSumAmount) sumAmount from T_AIM_DynamicCost where FAccountId in ("
			+ innerSql.toString() + ")";
		IRowSet rs = SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
		BigDecimal adjustAmount = null;
		while (rs.next()) {
			adjustAmount = rs.getBigDecimal("sumAmount");
		}
		if (adjustAmount != null && adjustAmount.compareTo(FDCHelper.ZERO) == 0) {
			return null;
		}
		return adjustAmount;
	}
	private boolean hasInit=false;
	private boolean isUsePartSettle=false;
	/**
	 * �Ƿ����ò��ֽ���
	 * @return
	 *  by sxhong 2008-05-30 13:31:20
	 */
	private  boolean isUsePartSettle(){
		if(!hasInit){
			try{
				isUsePartSettle=FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_MORESETTER);
			}catch(Exception e){
				e.printStackTrace();
			}
			hasInit=true;
		}
		return isUsePartSettle;
	}
	
	/**
	 * ���ӿ��ٶ�λ�ֶΣ��ɱ���Ŀ���롢�ɱ���Ŀ���ơ���ͬ��š���ͬ���ơ��ҷ���λ��ǩ�����ڡ���ͬ��
	 * @author owen_wen 2010-09-07
	 */
	protected String[] getLocateNames() {
		return new String[] {"acctNumber", "acctName"};
	}
}