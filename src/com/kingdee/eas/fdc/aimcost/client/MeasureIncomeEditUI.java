/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.export.ExportManager;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBook;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBookVO;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMenuManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.MeasureCostFactory;
import com.kingdee.eas.fdc.aimcost.MeasureEntryInfo;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeEntryCollection;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeEntryInfo;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeFactory;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexCollection;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexFactory;
import com.kingdee.eas.fdc.aimcost.PlanIndexInfo;
import com.kingdee.eas.fdc.basedata.AcctAccreditHelper;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IncomeAccountFactory;
import com.kingdee.eas.fdc.basedata.IncomeAccountInfo;
import com.kingdee.eas.fdc.basedata.MeasureStageInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectTypeInfo;
import com.kingdee.eas.fdc.basedata.TimeTools;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class MeasureIncomeEditUI extends AbstractMeasureIncomeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MeasureIncomeEditUI.class);
    
    /**
     * output class constructor
     */
    public MeasureIncomeEditUI() throws Exception
    {
        super();
    }

    private List tables = null;

	private CurProjectInfo project = null;

	private Map measureIncomeMap = new HashMap();

	private TreeModel incomeAcctTree = null;

	public Map apportionMap = new HashMap();
	
	private boolean isLimitSellArea = true;
	
	/** �滮ָ��� */
	private PlanIndexIncomeTable planIndexTable = null;

	/** ������ܱ� */
	private MeasureIncomeCollectTable measureCollectTable = null;
	
	private boolean isFirstLoad=true;

	//����
	CoreUI AimMeasureCostEditUI=null;
	List lockIds = new ArrayList();
	List lockId2s = new ArrayList();
	boolean hasMutex = true;
	
	public boolean destroyWindow() {
		boolean destroyWindow = super.destroyWindow();
		if(destroyWindow){
			
			//�ͷ�
			if ("RELEASEALL".equals(getOprtState())&&hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, lockIds);
					if(lockId2s.size()>0)
					FDCClientUtils.releaseDataObjectLock(AimMeasureCostEditUI, lockId2s);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}
			
			//�ͷ���Դ
			if(true)return true;
			this.removeAll();
			incomeAcctTree=null;
			tables=null;
			project=null;
			measureCollectTable.clear();
			measureCollectTable=null;
			planIndexTable.clear();
			planIndexTable=null;
			apportionMap=null;
			measureIncomeMap=null;
			tHelper=null;
			this.plTables=null;
			FDCClientHelper.clearMenuKeyboardHelper();
		}
		return destroyWindow;
	}
	
	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		MeasureIncomeInfo income = (MeasureIncomeInfo) this.editData;
		Object objStage = comboMeasureStage.getSelectedItem();
		if(objStage instanceof MeasureStageInfo){
			income.setMeasureStage((MeasureStageInfo)objStage);
		}else{
			income.setMeasureStage(null);
		}
		income.setVersionName(this.txtVersionName.getText());
		income.setVersionNumber(this.txtVersionNumber.getText());
		Object objPrj = prmtProject.getValue();
		if(objPrj instanceof CurProjectInfo){
			income.setProject((CurProjectInfo)objPrj);
		}else{
			income.setProject(null);
		}
		
		Object objPrjType = prmtProjectType.getValue();
		if(objPrjType instanceof ProjectTypeInfo){
			income.setProjectType((ProjectTypeInfo)objPrjType);
		}else{
			income.setProjectType(null);
		}

//		try {  //�����Ŀ����Ҫ�й���Ȩ��֤
//			handleAimCostAccredit(income);
//		} catch (BOSException e) {
//			handUIException(e);
//		}
		income.getIncomeEntry().clear();
		//Map splitTypeMap=measureCollectTable.getSplitTypes();
		for (int i = 2; i < tables.size(); i++) {
			KDTable table = (KDTable) tables.get(i);
			ProductTypeInfo product = (ProductTypeInfo) table.getHeadRow(0)
					.getUserObject();
			for (int j = 0; j < table.getRowCount(); j++) {
				IRow row = table.getRow(j);
				if (row.getUserObject() instanceof MeasureIncomeEntryInfo) {
					if(isDetailAcctRow(row)&&!isDetailAcctHasInput(row)){
						boolean isEmpty=true;
						for(int k=3;k<table.getColumnCount();k++){
							if(!FDCHelper.isEmpty(row.getCell(k).getValue())){
								isEmpty=false;
								break;
							}
						}
						if(isEmpty){
							continue;
						}
					}
					MeasureIncomeEntryInfo entry = (MeasureIncomeEntryInfo) row
							.getUserObject();
					entry.setEntryName((String) row.getCell("acctName")
							.getValue());
					entry.setProduct(product);

					entry.setSellArea((BigDecimal) row.getCell("sellArea").getValue());
					entry.setPrice((BigDecimal) row.getCell("price").getValue());
					entry.setChangeReason((String)row.getCell("changeReason").getValue());

					entry.setAmount((BigDecimal)row.getCell("amount").getValue());					
					income.getIncomeEntry().add(entry);
				}
			}
		}
	}

	protected void initWorkButton() {
		super.initWorkButton();
		
		this.actionFirst.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionRemove.setVisible(false);
		actionAddRow.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_addline"));
		actionDeleteRow.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_deleteline"));
		this.btnImportApportion.setIcon(EASResource.getIcon("imgTbtn_input"));
		actionImportTemplate.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_input"));
		menuEdit.setEnabled(false);
		menuEdit.setVisible(false);
		actionExportAllToExcel.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_output"));
		actionExportAllToExcel.setVisible(true);
		actionExportAllToExcel.setEnabled(true);

		menuItemExportAll.setMnemonic('E');
		menuItemExportAll.setText(menuItemExportAll.getText()+"(E)");
		menuItemExportAll.setAccelerator(KeyStroke.getKeyStroke("ctrl shift E"));
		
		menuItemImportTemplate.setMnemonic('T');
		menuItemImportTemplate.setText(menuItemImportTemplate.getText()+"(T)");
		menuItemImportTemplate.setAccelerator(KeyStroke.getKeyStroke("ctrl shift T"));
		
		menuItemAddRow.setMnemonic('A');
		menuItemAddRow.setText(menuItemAddRow.getText()+"(A)");
		menuItemAddRow.setAccelerator(KeyStroke.getKeyStroke("alt A"));
		
		menuItemDeleteRow.setMnemonic('D');
		menuItemDeleteRow.setText(menuItemDeleteRow.getText()+"(D)");
		menuItemDeleteRow.setAccelerator(KeyStroke.getKeyStroke("alt D"));
		chkMenuItemSubmitAndAddNew.setEnabled(false);
		chkMenuItemSubmitAndAddNew.setVisible(false);
		//by Cassiel_peng 2009-8-19
		this.btnSave.setIcon(EASResource.getIcon("imgTbtn_save"));
        this.btnSubmit.setIcon(EASResource.getIcon("imgTbtn_submit"));
        this.menuItemSave.setIcon(EASResource.getIcon("imgTbtn_save"));
        this.menuItemSubmit.setIcon(EASResource.getIcon("imgTbtn_submit"));
	}

	protected IObjectValue createNewData() {
		MeasureIncomeInfo income = new MeasureIncomeInfo();
		String orgId=(String)getUIContext().get("orgId");
		String prjId=(String)getUIContext().get("projectId");
		Boolean isAimMeasure=(Boolean)getUIContext().get("isAimMeasure");
		income.setIsAimMeasure(isAimMeasure.booleanValue());
		if(getUIContext().get("MeasureEditData") instanceof MeasureIncomeInfo){
			MeasureIncomeInfo editData1=(MeasureIncomeInfo)getUIContext().get("MeasureEditData");
			income.putAll(editData1);
			getUIContext().remove("MeasureEditData");
			return income;
		}
		try {
			/*MeasureCostVersionHandler version = new MeasureCostVersionHandler(
					this.project.getId().toString(), true);*/
			MeasureIncomeVersionHandler version = new MeasureIncomeVersionHandler(
					orgId,prjId, isAimMeasure.booleanValue());
			income.setVersionNumber(MeasureIncomeVersionHandler
					.getNextVersion(version.getLastVersion()));
		} catch (BOSException e) {
			handUIException(e);
		} catch (SQLException e) {
			handUIException(e);
		}
		
		String projectId = (String) this.getUIContext().get("projectId");
		MeasureStageInfo lastStageInfo = null;
		if(projectId!=null){
			try {
				SelectorItemCollection selector=new SelectorItemCollection();
				selector.add("name");
				selector.add("number");
				selector.add("longNumber");
				selector.add("projectType.*");
				project = CurProjectFactory.getRemoteInstance().getCurProjectInfo(
						new ObjectUuidPK(BOSUuid.read(projectId)),selector);
				income.setProject(this.project);
				income.setProjectType(project.getProjectType());
				lastStageInfo = AimCostClientHelper.getLastMeasureStage(project,isAimMeasure.booleanValue());
			} catch (Exception e){
				handUIException(e);
			}
		}
		FullOrgUnitInfo org = new FullOrgUnitInfo();
		org.setId(BOSUuid.read(orgId));
		income.setOrgUnit(org);
		if(lastStageInfo==null){
			
		}
		income.setMeasureStage(lastStageInfo);
		return income;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return MeasureIncomeFactory.getRemoteInstance();
	}

	private void addPanel() throws Exception {
		if(isFirstLoad) {
			isFirstLoad=false;
		}else{
			return;
		}
		ChangeListener[] changeListeners = plTables.getChangeListeners();
		for(int i=0;i<changeListeners.length;i++){
			plTables.removeChangeListener(changeListeners[i]);
		}
		this.plTables.removeAll();
		Object obj=getUIContext().get(UIContext.ID);
		if(editData.getId()!=null){
			obj=editData.getId();
		}
		
//		String id=obj==null?null:obj.toString();
		tables = new ArrayList();
		KDTable table =null;// new KDTable();
		//��ӻ��ܱ��滮ָ���
		measureCollectTable=new MeasureIncomeCollectTable(this);
		table=measureCollectTable.getTable();
		this.tables.add(table);
		FDCTableHelper.setColumnMoveable(table, true);
		FDCTableHelper.addTableMenu(table);
		this.plTables.add(table, "������ܱ�");
		planIndexTable=new PlanIndexIncomeTable(getInitPlanIndexInfo(),this);
		table=planIndexTable.getTable();		
		this.tables.add(table);
		FDCTableHelper.setColumnMoveable(table, true);
		FDCTableHelper.addTableMenu(table);
//		((TablePreferencesHelper)tHelper).getActionSave(table);
		this.plTables.add(planIndexTable.getContentPanel(), "�滮ָ���");		
		
		PlanIndexInfo info = planIndexTable.getPlanIndexInfo();		
		
		for(int i=0;i<info.getEntrys().size();i++){
			PlanIndexEntryInfo entry=info.getEntrys().get(i);
			if(entry.getProduct()!=null){
				table=addProductTypeTable(entry.getProduct());				
//				if(table!=null) table.setUserObject(entry);		
			}
		}
	
		//�ӽ���ȡ��
		measureCollectTable.refresh();
		for(int i=0;i<changeListeners.length;i++){
			plTables.addChangeListener(changeListeners[i]);
		}
	}

	public KDTable addProductTypeTable(ProductTypeInfo product){
		boolean isadd=true;
		for(int i=2;i<tables.size();i++){
			KDTable table=(KDTable) tables.get(i);
			if(table.getHeadRow(0).getUserObject() instanceof ProductTypeInfo){
				if(((ProductTypeInfo)table.getHeadRow(0).getUserObject()).getId().equals(product.getId())){
					isadd=false;
					break;
				}
			}
		}
		if(!isadd) return null;
		
		KDTable table = new KDTable();
		this.tables.add(table);
		FDCTableHelper.setColumnMoveable(table, true);
		this.initTable(table,product.getId().toString());
		table.getHeadRow(0).getCell(0).setUserObject(planIndexTable.getPlanIndexEntryInfo(product.getId().toString()));
		table.getHeadRow(0).setUserObject(product);
		try {
			this.fillTable(table);
		} catch (Exception e) {
			handUIException(e);
		}
		this.plTables.add(table, product.getName());
		this.setUnionData(table);
		FDCTableHelper.addTableMenu(table);
		return table;
		
	}
	public void deleteProductTypeTable(ProductTypeInfo product){
		for(int i=3;i<tables.size();i++){
			KDTable table=(KDTable) tables.get(i);
			if(table.getHeadRow(0).getUserObject() instanceof ProductTypeInfo){
				if(((ProductTypeInfo)table.getHeadRow(0).getUserObject()).getId().equals(product.getId())){
					tables.remove(i);
					plTables.remove(i);
					
					disableTableMenus(table);
					break;
				}
			}
		}
		
	}
	public void addTableChangeEnvent(final KDTable table) {
		table
				.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
					public void editStopped(
							com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
						try {
							table_editStopped(table, e);
						} catch (Exception exc) {
							handUIException(exc);
						}
					}
				});
	}

	public void initTable(KDTable table,String productId) { // CostAccountTypeEnum type,
		table.getViewManager().setFreezeView(-1, 3);
		table.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		table.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		IColumn column = table.addColumn();
		column.setKey("id");
		column.getStyleAttributes().setHided(true);
		column = table.addColumn();
		column.setKey("acctNumber");
		column = table.addColumn();
		column.setKey("acctName");

		column = table.addColumn();
		column.setKey("sellArea");
		column = table.addColumn();

		column.setKey("price");

		column = table.addColumn();
		column.setKey("amount");

		column = table.addColumn();
		column.setKey("changeReason");

		IRow row = table.addHeadRow();
		row.getCell("acctNumber").setValue("��Ŀ����");
		row.getCell("acctName").setValue("��Ŀ����");

		row.getCell("sellArea").setValue("�������");
//		row.getCell("unit").setValue("��λ");
		row.getCell("price").setValue("Ԥ��ƽ������");

		row.getCell("amount").setValue("Ԥ�������ܶ�");

		row.getCell("changeReason").setValue("�仯ԭ��");

//		if(((MeasureIncomeInfo)editData).getSourceBillId()==null){
//			table.getColumn("changeReason").getStyleAttributes().setHided(true);
//		}

		FDCHelper.formatTableNumber(table, new String[]{
				"sellArea","amount"});
		FDCHelper.formatTableNumber(table, "price", "#,##0.00;-#,##0.00");
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		ICellEditor txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn("acctName").setEditor(txtEditor);
		
		textField = new KDTextField();
		textField.setMaxLength(1200);
		txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn("changeReason").setEditor(txtEditor);
		
		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setMaximumValue(FDCNumberHelper.MAX_VALUE);
		formattedTextField.setMinimumValue(FDCNumberHelper.MIN_VALUE);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(true);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		//table.getColumn("coefficient").setEditor(numberEditor);
		table.getColumn("price").setEditor(numberEditor);
		
		/*******/
		formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("sellArea").setEditor(numberEditor);

		formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("amount").setEditor(numberEditor);

		table.getColumn("acctNumber").getStyleAttributes().setLocked(true);
		Color lockColor =FDCTableHelper.cantEditColor;// new Color(0xF0AAD9);
		table.getColumn("acctNumber").getStyleAttributes().setBackground(
				lockColor);
		
		table.getColumn("sellArea").getStyleAttributes().setLocked(true);//��ʼ��ʱ���ɱ༭����������ʱ��ֵ��ɱ༭

		table.getColumn("amount").getStyleAttributes().setLocked(true);
		table.getColumn("amount").getStyleAttributes().setBackground(lockColor);

		this.addTableChangeEnvent(table);
	}

	public void fillTable(KDTable table) throws Exception {

		table.removeRows();
		table.setUserObject(null);
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) incomeAcctTree
				.getRoot();
		Enumeration childrens = root.depthFirstEnumeration();
		int maxLevel = 0;
		while (childrens.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) childrens
					.nextElement();
			if (node.getUserObject() != null && node.getLevel() > maxLevel) {
				maxLevel = node.getLevel();
			}
		}
		table.getTreeColumn().setDepth(maxLevel + 1);
		for (int i = 0; i < root.getChildCount(); i++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) root
					.getChildAt(i);
			fillNode(table, child);
		}
	}

	private void fillNode(KDTable table, DefaultMutableTreeNode node)
			throws BOSException, SQLException, EASBizException {
		IncomeAccountInfo incomeAcct = (IncomeAccountInfo) node.getUserObject();
		
		//Ӧ�����ж��Ƿ�Ϊ��
		if (incomeAcct == null) {
			MsgBox.showError("�����Ŀ�ļ���̫��!");
			return;
		}

		ProductTypeInfo product = (ProductTypeInfo) table.getHeadRow(0)
				.getUserObject();
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		String longNumber = incomeAcct.getLongNumber();
		longNumber = longNumber.replace('!', '.');
		row.getCell("acctNumber").setValue(longNumber);
		row.getCell("acctName").setValue(incomeAcct.getName());
		row.setUserObject(incomeAcct);
		if (node.isLeaf()&&node.getLevel()>1) {
			String key = incomeAcct.getId().toString();
			if (product != null) {
				key += product.getId().toString();
			}
			MeasureIncomeEntryCollection coll = (MeasureIncomeEntryCollection) measureIncomeMap
					.get(key);
			if (coll != null && coll.size() > 0) {
				if(coll.size()==1){
					MeasureIncomeEntryInfo info = coll.get(0);
					IRow entryRow = row;
					entryRow.setUserObject(info);
					loadRow(table,entryRow, product);
					setDetailAcctRow(entryRow);
					row.getCell("acctName").setValue(incomeAcct.getName());
				}else{
					row.getStyleAttributes().setLocked(true);
					row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
					row.setUserObject(incomeAcct);
					for (int i = 0; i < coll.size(); i++) {
						MeasureIncomeEntryInfo info = coll.get(i);
						IRow entryRow = table.addRow();
						entryRow.setTreeLevel(node.getLevel());
						entryRow.setUserObject(info);
						loadRow(table,entryRow, product);
					}
				}
			}else{
				//�տ�Ŀ�����
				MeasureIncomeEntryInfo info = new MeasureIncomeEntryInfo();
				info.setIncomeAccount(incomeAcct);
				row.setUserObject(info);
				//setTemplateMeasureCostF7Editor(table,  row);
				setDetailAcctRow(row);
			}
		} else {
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode(table, (DefaultMutableTreeNode) node
						.getChildAt(i));
			}
			row.getStyleAttributes().setLocked(true);
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
		}
	}

	protected IObjectValue getValue(IObjectPK pk) throws Exception {
		return super.getValue(pk);
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("*");
		sels.add("project.name");
		sels.add("project.longNumber");
		sels.add("project.number");
		sels.add("projectType.*");
		sels.add("orgUnit.id");
		sels.add("incomeEntry.*");
		sels.add("incomeEntry.incomeAccount.*");
		sels.add("incomeEntry.incomeAccount.product.*");
		sels.add("measureStage.id");
		sels.add("measureStage.name");
		sels.add("measureStage.number");
		return sels;
	}

	public void loadRow(KDTable table,IRow row, ProductTypeInfo product) {
		MeasureIncomeEntryInfo info = (MeasureIncomeEntryInfo) row.getUserObject();
		row.getCell("acctName").setValue(info.getEntryName());

		BigDecimal sellArea = info.getSellArea();
		row.getCell("sellArea").setValue(sellArea);
		if (sellArea == null){//��ֵ���ɱ༭
			row.getCell("sellArea").getStyleAttributes().setLocked(true);
		}else{
			row.getCell("sellArea").getStyleAttributes().setLocked(false);
		}
		
		BigDecimal price = info.getPrice();
		row.getCell("price").setValue(FDCHelper.toBigDecimal(price,4));

		row.getCell("amount").setValue(info.getAmount());

		row.getCell("changeReason").setValue(info.getChangeReason());

	}

	public void onLoad() throws Exception {
		/**
		 * ���ڿ�����⣬ʹ��addnew�������ޣ�ͨ��addnew1αװ�󣬴����˴��ں�
		 * Ȼ�����޸Ļ���
		 */
		if(this.getOprtState().equals("ADDNEW1")){
			this.setOprtState("ADDNEW");
		}
		this.getMenuManager(null);
		
		this.comboMeasureStage.setEnabled(false);
		this.txtVersionName.setEnabled(false);
		this.prmtProjectType.setEnabled(false);

		this.txtVersionNumber.setEnabled(false);
		this.actionImportApportion.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
//		this.actionSave.setVisible(false);
		FDCClientHelper.initComboMeasureStage(comboMeasureStage,false);
		super.onLoad();

		//R110609-0515:ϵͳ����_���ز�_�ɱ�����_����������FDC5001300_LIMITSELLAREA��Ч�����޸�
		isLimitSellArea = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_LIMITSELLAREA);
		
		txtVersionName.setMaxLength(80);
		initCtrlListener();

		Boolean isAimMeasure=(Boolean)getUIContext().get("isAimMeasure");
		if(isAimMeasure==null){
			isAimMeasure=Boolean.TRUE;
			getUIContext().put("isAimMeasure",Boolean.TRUE);
		}

		setUITitle("��Ŀ�������");
		((MeasureIncomeInfo)editData).setIsAimMeasure(isAimMeasure.booleanValue());
		
		actionImportTemplate.setEnabled(false);
		actionImportTemplate.setVisible(false);
		
		actionAddNew.setEnabled(false);
		actionAddNew.setVisible(false);
		
		actionAddRow.setEnabled(false);
		actionAddRow.setVisible(false);
		actionDeleteRow.setEnabled(false);
		actionDeleteRow.setVisible(false);
        
    	actionEdit.setVisible(false);
    	actionEdit.setEnabled(false);
    	editVersion();
    	
    	setShowMessagePolicy(SHOW_MESSAGE_BOX_FIRST);
    	actionCopy.setVisible(false);
    	if(editData!=null&&((MeasureIncomeInfo)editData).getMeasureStage()!=null){
    		comboMeasureStage.setSelectedItem(((MeasureIncomeInfo)editData).getMeasureStage());
    	}
    	storeFields();//���ܱ�����afterSetUnionData�˳���ʾ
	}

	private void editVersion() throws BOSException, SQLException {
		Boolean isEditVersion=(Boolean)getUIContext().get("isEditVersion");
    	if(isEditVersion!=null&&isEditVersion.booleanValue()){
    		//�޸İ汾��
    		String sourceId=((MeasureIncomeInfo)editData).getSourceBillId();
    		if(sourceId==null){
    			sourceId=editData.getId().toString();
    		}
    		FDCSQLBuilder builder=new FDCSQLBuilder();
    		builder.appendSql("select FVersionNumber from T_AIM_MeasureIncome where fsourceBillId=?");
    		builder.addParam(sourceId);
    		IRowSet rowSet=builder.executeQuery();
    		int lastSubVersion=1;
    		String verNo=((MeasureIncomeInfo)editData).getVersionNumber();
    		verNo=verNo.replaceAll("\\.", "!");
    		while(rowSet.next()){
    			//�ҳ����汾
    			verNo=rowSet.getString("FVersionNumber");
    			verNo=verNo.replaceAll("\\.", "!");
    			if(verNo!=null&&verNo.indexOf('!')>0){
    				int temp=Integer.parseInt(verNo.substring(verNo.indexOf('!')+1));
    				if(temp>=lastSubVersion){
    					lastSubVersion=temp+1;
    				}
    			}
    			
    		}
    		if(!FDCHelper.isEmpty(verNo)){
    			if(verNo.indexOf('!')>0){
    				int index=verNo.lastIndexOf("!");
    				verNo=verNo.substring(0, index+1)+lastSubVersion;
    			}
    		}
    		txtVersionNumber.setText(verNo.replaceAll("!", "\\."));
    	}
	}

	private void registerMeasureDefaultSplitTypeSetKey() {
		String actionName="MeasureDefaultSplitTypeSetUI";
		final UIContext uiContext = new UIContext(this);
		this.getActionMap().put(actionName, new javax.swing.AbstractAction(){
			public void actionPerformed(ActionEvent e) {
                try {
                	setCursorOfWair();
					IUIFactory fy = UIFactory.createUIFactory(UIFactoryName.MODEL);
					IUIWindow wnd = fy.create(MeasureDefaultSplitTypeSetUI.class.getName(),
							uiContext,null,"EDIT",WinStyle.SHOW_KINGDEELOGO);
					wnd.show();
                } catch (Exception e1) {
                    handUIException(e1);
                }finally{
                	setCursorOfDefault();
                }
            }
		});
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl shift alt F12"), actionName);
		
	}
	
	private void initCtrlListener(){
		prmtProjectType.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent e) {
				if(e.getNewValue()==null){
					prmtProject.setData(null);
				}
				if(e.getOldValue()==null?e.getNewValue()!=null:!e.getOldValue().equals(e.getNewValue())){
					prmtProject.setData(null);
				}
				
			}
		});
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("name");
		selector.add("number");
		selector.add("longNumber");
		selector.add("projectType.*");
		prmtProject.setSelectorCollection(selector);
		prmtProject.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent e) {
				if(prmtProjectType.getData()==null&&e.getNewValue()!=null){
					EventListener[] listeners = prmtProjectType.getListeners(DataChangeListener.class);
					for(int i=0;i<listeners.length;i++){
						prmtProjectType.removeDataChangeListener((DataChangeListener)listeners[i]);
					}
					prmtProjectType.setData(((CurProjectInfo)e.getNewValue()).getProjectType());
					for(int i=0;i<listeners.length;i++){
						prmtProjectType.addDataChangeListener((DataChangeListener)listeners[i]);
					}
				}
			}
		});
		prmtProject.addSelectorListener(new SelectorListener(){
			public void willShow(SelectorEvent e) {
				prmtProject.getQueryAgent().resetRuntimeEntityView();
				String projectTypeid = FDCHelper.getF7Id(prmtProjectType);
				EntityViewInfo view = prmtProject.getEntityViewInfo();
				if(view==null){
					view=new EntityViewInfo();
				}
				view.setFilter(new FilterInfo());
				if(projectTypeid!=null){
					view.getFilter().appendFilterItem("projectType.id", projectTypeid);
				}
				view.getFilter().appendFilterItem("CU.id", SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
				prmtProject.setEntityViewInfo(view);
			}
		});
		plTables.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				Object obj=plTables.getClientProperty("oldIndex");
				if(obj instanceof Integer){
					if(((Integer)obj).intValue()==1){
						//refreshAllMeasureTable();
					}
				}
				plTables.putClientProperty("oldIndex", new  Integer(plTables.getSelectedIndex()));
				if(plTables.getSelectedIndex()==0){
					measureCollectTable.refresh();
				}
			}
			
		});
	}
	public void loadFields() {

		super.loadFields();
		//ֻȡ��˾�ĳɱ���Ŀ
		String orgId = (String) this.getUIContext().get("orgId");
		MeasureIncomeInfo income = (MeasureIncomeInfo) this.editData;
		if(getUIContext().get("isAimMeasure")==null){
			getUIContext().put("isAimMeasure", Boolean.valueOf(income.isIsAimMeasure()));
		}
		if(orgId==null){
			orgId=((MeasureIncomeInfo)this.editData).getOrgUnit().getId().toString();
			this.getUIContext().put("orgId", orgId);
		}
		if(incomeAcctTree==null){
			try{
				FilterInfo acctFilter = new FilterInfo();
				acctFilter.getFilterItems().add(
						new FilterItemInfo("isEnabled", Boolean.TRUE));

			if(income.getProject()==null //ѡ��֯������
				//�����ݣ���������ǰ����ʵ�������֯�ĳɱ���Ŀ������� by sxhong
			   ||(income.getIncomeEntry().size()>0&&income.getIncomeEntry().get(0).getIncomeAccount().getCurProject()==null)
			){
				acctFilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",orgId));
				accreditSet=AcctAccreditHelper.handAcctAccreditFilter(null, orgId, acctFilter);
			}else{
				String prjId = income.getProject().getId().toString();
				acctFilter.getFilterItems().add(new FilterItemInfo("curProject.id",prjId));
			}

			incomeAcctTree = FDCClientHelper.createDataTree(IncomeAccountFactory
					.getRemoteInstance(), acctFilter);
			}catch(Exception e){
				this.handUIException(e);
			}
		}
		GlUtils.setSelectedItem(comboMeasureStage, income.getMeasureStage());
		this.prmtProject.setValue(income.getProject());
		this.prmtProjectType.setValue(income.getProjectType());
		
		//�жϰ汾���Ƿ�Ϊ�գ����Ϊ�գ�������Ϣ��ʾ
		if(!(income.getVersionNumber()== null ))
		{
			this.txtVersionNumber.setText(income.getVersionNumber().replaceAll("!", "\\."));
		}
		else
		{
			MsgBox.showWarning(this, "�汾�Ų���Ϊ��");
			SysUtil.abort();
		}
		this.txtVersionName.setText(income.getVersionName());
		measureIncomeMap.clear();
		MeasureIncomeEntryCollection incomeEntrys = income.getIncomeEntry();

		for (int i = 0; i < incomeEntrys.size(); i++) {
			MeasureIncomeEntryInfo info = incomeEntrys.get(i);
			IncomeAccountInfo incomeAccount = info.getIncomeAccount();
			String key = incomeAccount.getId().toString();
			if (info.getProduct() != null) {
				key += info.getProduct().getId().toString();
			}
			if (measureIncomeMap.containsKey(key)) {
				MeasureIncomeEntryCollection coll = (MeasureIncomeEntryCollection) measureIncomeMap
						.get(key);
				coll.add(info);
			} else {
				MeasureIncomeEntryCollection newColl = new MeasureIncomeEntryCollection();
				newColl.add(info);
				measureIncomeMap.put(key, newColl);
			}
		}
		try {
			addPanel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		TimeTools.getInstance().msValuePrintln("end loadFields");
		//����
		if(STATUS_EDIT.equals(getOprtState())&&income!=null&&income.getId()!=null){
			String billId =income.getId().toString();
			lockIds.add(billId);
			String measureIncomeId = income.getSrcMeasureCostId();
			if(measureIncomeId!=null){
				lockId2s.add(measureIncomeId);
				try{
					FDCClientUtils.requestDataObjectLock(this, lockIds, "edit");
					Map uiContext = new HashMap();
					uiContext.put(UIContext.ID, measureIncomeId);
					IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
					IUIWindow window = uiFactory.create(AimMeasureCostEditUI.class.getName(), uiContext, null, OprtState.VIEW);
					AimMeasureCostEditUI = (CoreUI)window.getUIObject();
					if(lockId2s.size()>0)
					FDCClientUtils.requestDataObjectLock(AimMeasureCostEditUI, lockId2s, "edit");
				}
				catch (Throwable e1) {
					this.handUIException(e1);
					hasMutex = FDCClientUtils.hasMutexed(e1);
				}
			}
		}
	}

	protected void table_editStopped(KDTable table, KDTEditEvent e)
			throws Exception {
		Object oldValue = e.getOldValue();
		Object newValue=e.getValue();
		/*if(oldValue==null&&newValue==null){
			return;
		}*/
		//��һ�μ�������ʱoldValue,newValue��Ϊ��,��������һ�䣬����
		if(oldValue!=null&&newValue!=null&&oldValue.equals(newValue)){
			return;
		}
		Object objTmp=table.getHeadRow(0).getCell(0).getUserObject();
		IObjectValue info=null;
		if(objTmp instanceof IObjectValue){
			info=(IObjectValue)objTmp;
		}
		int rowIndex = e.getRowIndex();
		int columnIndex = e.getColIndex();
		if(oldValue==null&&newValue==null){
//			if(!table.getColumnKey(columnIndex).equals("indexName")){
//				return;
//			}
			return;
		}
		
		IRow row = table.getRow(rowIndex);
		
		//���� �ϼ� = �������* Ԥ��ƽ������
		if (table.getColumnKey(columnIndex).equals("price")
				|| table.getColumnKey(columnIndex).equals("sellArea")) {
			BigDecimal price = (BigDecimal) row.getCell("price").getValue();
			if(price == null){//�������cell��ղ����ɱ༭
				row.getCell("sellArea").setValue(null);
				row.getCell("sellArea").getStyleAttributes().setLocked(true);
			}else{//�������cell��ֵ���Զ����滮ָ��Ŀ������
				BigDecimal sellArea = (BigDecimal) row.getCell("sellArea").getValue();
				if(sellArea == null){
					//��ʾ�滮ָ��Ŀ������
					Object obj=table.getHeadRow(0).getCell(0).getUserObject();
					if(obj instanceof PlanIndexEntryInfo){
						BigDecimal planIndex_sellArea = ((PlanIndexEntryInfo)obj).getSellArea();
						row.getCell("sellArea").setValue(planIndex_sellArea);
					}
				}
				row.getCell("sellArea").getStyleAttributes().setLocked(false);				
			}			
			refreshAmount(table,row);
		} 
		
		if(isDetailAcctRow(row)){
			if(newValue!=null){
				setDetailAcctHashInput(row);
			}else{
				boolean isEmpty=true;
				for(int i=3;i<table.getColumnCount();i++){
					if(!FDCHelper.isEmpty(row.getCell(i).getValue())){
						isEmpty=false;
						break;
					}
				}
				if(isEmpty){
					setDetailAcctHasNotInput(row);
				}
			}
		}
		setUnionData(table);
		
		setDataChange(true);
	}
	
	//Ԥ�������ܶ�
	private void refreshAmount(KDTable table,IRow row) {
		BigDecimal price = (BigDecimal) row.getCell("price").getValue();
		BigDecimal sellArea = (BigDecimal) row.getCell("sellArea").getValue();
		if (price == null) {
			price = FDCHelper.ZERO;			
		}
		if (sellArea == null) {
			sellArea = FDCHelper.ZERO;			
		}
		if (price.compareTo(FDCHelper.ZERO) == 0
				&& sellArea.compareTo(FDCHelper.ZERO) == 0) {
			row.getCell("amount").setValue(null);
		}else{
			BigDecimal amount = price.multiply(sellArea).setScale(2,BigDecimal.ROUND_HALF_UP);
			row.getCell("amount").setValue(amount);
		}	
		
	}

	public void actionAddRow_actionPerformed(ActionEvent arg0) throws Exception {
		if(this.plTables.getSelectedIndex()==0){
			return;
		}
		if(this.plTables.getSelectedIndex()==1){
			//planIndexTable.addRow(arg0); //�滮ָ���ֻ��
			return;
		}

		KDTable table = (KDTable) this.tables.get(this.plTables
				.getSelectedIndex());
		if (table.getRowCount() == 0) {
			return;
		}
		int index = table.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			table.getSelectManager().set(table.getRowCount() - 1, 0);
			index = table.getRowCount() - 1;
		}
		IRow selectRow = table.getRow(index);
		if (selectRow.getUserObject() instanceof IncomeAccountInfo) {
			IncomeAccountInfo acct = (IncomeAccountInfo) selectRow.getUserObject();
			if (acct.isIsLeaf()&&acct.getLevel()>1) {
				IRow row = table.addRow(index + 1);
				row.setTreeLevel(selectRow.getTreeLevel() + 1);
				MeasureIncomeEntryInfo info = new MeasureIncomeEntryInfo();
				info.setIncomeAccount(acct);
				row.setUserObject(info);
				table.setUserObject("addRow");
				//setTemplateMeasureCostF7Editor(table,  row);
				
				
			} else {
				this.setMessageText("����ϸ�л�һ����ϸ�в����������!");
				this.showMessage();
			}
		} else {
			MeasureIncomeEntryInfo infoUp = (MeasureIncomeEntryInfo) selectRow
					.getUserObject();
			if(isDetailAcctRow(selectRow)){
				{
					//Map splitTypeMap=measureCollectTable.getSplitTypes();
					MeasureIncomeEntryInfo entry = (MeasureIncomeEntryInfo) selectRow.getUserObject();
					ProductTypeInfo product = (ProductTypeInfo) table.getHeadRow(0)
					.getUserObject();
					entry.setEntryName((String) selectRow.getCell("acctName").getValue());
					entry.setProduct(product);

					entry.setSellArea((BigDecimal) selectRow.getCell("sellArea").getValue());
					entry.setPrice((BigDecimal) selectRow.getCell("price").getValue());

					entry.setChangeReason((String)selectRow.getCell("changeReason").getValue());

					entry.setAmount((BigDecimal) selectRow.getCell("amount").getValue());
				}
				//��ϸ��¼��
				IRow tempRow=table.addRow(index+1);
				tempRow.setUserObject(infoUp);
				tempRow.setTreeLevel(selectRow.getTreeLevel()+1);
				loadRow(table, tempRow, infoUp.getProduct());
				selectRow.setUserObject(infoUp.getIncomeAccount());
				selectRow.getCell(0).setUserObject(null);
				clearDetailAcctRow(table, selectRow);
				setDetailAcctRowNull(selectRow);
				selectRow.getStyleAttributes().setLocked(true);
				selectRow.getStyleAttributes().setBackground(new Color(0xF0EDD9));
				index++;
				IRow row = table.addRow(index + 1);
				row.setTreeLevel(selectRow.getTreeLevel()+1);
				MeasureIncomeEntryInfo info = new MeasureIncomeEntryInfo();
				info.setIncomeAccount(infoUp.getIncomeAccount());
				row.setUserObject(info);
				table.setUserObject("addRow");
				//setTemplateMeasureCostF7Editor(table,  row);
				this.setUnionData(table);
			}else{
				IRow row = table.addRow(index + 1);
				row.setTreeLevel(selectRow.getTreeLevel());
				MeasureIncomeEntryInfo info = new MeasureIncomeEntryInfo();
				info.setIncomeAccount(infoUp.getIncomeAccount());
				row.setUserObject(info);
				table.setUserObject("addRow");
				//setTemplateMeasureCostF7Editor(table,  row);
			}
		}
	}

	public void actionDeleteRow_actionPerformed(ActionEvent arg0)
			throws Exception {
		if(this.plTables.getSelectedIndex()==0){
			return;
		}
		if(this.plTables.getSelectedIndex()==1){
			//planIndexTable.deleteRow(arg0);
			return;
		}
		KDTable table = (KDTable) this.tables.get(this.plTables
				.getSelectedIndex());
		KDTSelectManager selectManager = table.getSelectManager();
		if (selectManager == null || selectManager.size() == 0) {
			return;
		}
		for (int i = 0; i < selectManager.size(); i++) {
			KDTSelectBlock selectBlock = selectManager.get(i);
			for (int j = selectBlock.getTop(); j <= selectBlock.getBottom(); j++) {
				IRow selectRow = table.getRow(j);
				if (selectRow == null) {
					continue;
				}
				if (selectRow.getUserObject() instanceof MeasureIncomeEntryInfo) {
					selectRow.getCell("price").setUserObject("delete");
				}
			}
		}
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = (IRow) table.getRow(i);
			if (row.getCell("price").getUserObject() != null) {
				if(isDetailAcctRow(row)){
					clearDetailAcctRow(table, row);
					row.getCell("price").setUserObject(null);
				}else{
					//�ж��ǲ������һ��
					int j=row.getRowIndex()-1;
					int k=row.getRowIndex()+1;
					if(j>0){
						IRow parentRow = table.getRow(j);
						if(parentRow.getUserObject() instanceof IncomeAccountInfo){
							if(k==table.getRowCount()||isDetailAcctRow(table.getRow(k))
									//����ϸ��
									||table.getRow(k).getUserObject() instanceof IncomeAccountInfo){
								clearDetailAcctRow(table, parentRow);
								parentRow.getStyleAttributes().setBackground(Color.WHITE);
								parentRow.getStyleAttributes().setLocked(false);
								MeasureIncomeEntryInfo info = new MeasureIncomeEntryInfo();
								info.setIncomeAccount((IncomeAccountInfo)parentRow.getUserObject());
								parentRow.setUserObject(info);
								//setTemplateMeasureCostF7Editor(table,  parentRow);
								setDetailAcctRow(parentRow);
								parentRow.getCell("acctNumber").getStyleAttributes().setLocked(true);
								parentRow.getCell("acctNumber").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
								parentRow.getCell("acctName").getStyleAttributes().setLocked(true);
								parentRow.getCell("acctName").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
								parentRow.getCell("amount").getStyleAttributes().setLocked(true);
								parentRow.getCell("amount").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
							}
						}
					}
					table.removeRow(row.getRowIndex());
					i--;
				}
				table.setUserObject("delteRow");
				setUnionData(table);
			}
		}

	}

	/**
	 * ���ø���Ŀ������
	 * 
	 * @param table
	 * @param amountColumns
	 */
	public void setUnionData(KDTable table) {
//		String[] cols = new String[] { 
//				//"price", 
//				"sumPrice","amount","buildPart","sellPart","adjustAmt"
//				};
		String[] cols = new String[] {"amount"};
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getUserObject() instanceof IncomeAccountInfo) {
				// ���û�����
				int level = row.getTreeLevel();
				List aimRowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() instanceof MeasureIncomeEntryInfo) {
						aimRowList.add(rowAfter);
					}

				}
				
				for (int j = 0; j < cols.length; j++) {
					BigDecimal sum = FDCHelper.ZERO;
					for (int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) aimRowList.get(rowIndex);
						Object value = rowAdd.getCell(cols[j]).getValue();
						if (value != null) {
							if (value instanceof BigDecimal) {
								sum = sum.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								sum = sum.add(new BigDecimal(((Integer) value)
										.toString()));
							}
						}
					}						
					
//					if(sum != null && sum.compareTo(FDCHelper.ZERO)==0){
//						sum = null;
//					}
					row.getCell(cols[j]).setValue(sum);
				}
			}
		}
		addTotalRow(table);
	}

	public void actionImportApportion_actionPerformed(ActionEvent arg0)
			throws Exception {
		super.actionImportApportion_actionPerformed(arg0);
	}

	protected void beforeStoreFields(ActionEvent e) throws Exception {
		if (this.txtVersionName.getText() == null
				|| this.txtVersionName.getText().length() == 0) {
			MsgBox.showInfo("�汾���Ʋ���Ϊ��!");
			this.abort();
		}
		for (int i = 0; i < tables.size(); i++) {
			KDTable table = (KDTable) tables.get(i);
			for (int j = 0; j < table.getRowCount(); j++) {
				IRow row = table.getRow(j);
				if (row.getUserObject() instanceof MeasureIncomeEntryInfo) {
					if(isDetailAcctRow(row)&&!isDetailAcctHasInput(row)){
						//δ¼��Ĳ�������ж�
						continue;
					}
					int k = table.getColumnIndex("sellArea");
					if (row.getCell("amount").getValue() != null) {
						BigDecimal value = (BigDecimal) row.getCell("sellArea")
								.getValue();
						if (value != null
								&& value.compareTo(FDCHelper.MAX_VALUE) > 0) {
							this.setMessageText("��������������ֵ!");
							this.showMessage();
							this.plTables.setSelectedIndex(i);
							table.getSelectManager().select(0, 0);
							table.getSelectManager().select(row.getRowIndex(),
									k);
							this.abort();
						}
					}

					k = table.getColumnIndex("amount");
					if (row.getCell("amount").getValue() != null) {
						BigDecimal value = (BigDecimal) row.getCell("amount").getValue();
						if (value.compareTo(FDCHelper.MAX_VALUE) > 0) {
							this.setMessageText("Ԥ�������ܶ�����ֵ!");
							this.showMessage();
							this.plTables.setSelectedIndex(i);
							table.getSelectManager().select(0, 0);
							table.getSelectManager().select(row.getRowIndex(),k);
							this.abort();
						}
					} 
				}
			}
		}
	}
	
	private void checkSellArea(){
		if(!isLimitSellArea){
			return;
		}
		StringBuffer content = new StringBuffer();
		content.append("��������Ʒ�������֮�ͳ����滮ָ���������,�����Ĳ�Ʒ�У� ");
		boolean isBigger = false;
		for (int i = 2; i < tables.size(); i++) {
			KDTable table = (KDTable) tables.get(i);
			ProductTypeInfo product = (ProductTypeInfo) table.getHeadRow(0)
			.getUserObject();
			PlanIndexEntryInfo info = planIndexTable.getPlanIndexEntryInfo(product.getId().toString());
			BigDecimal sellArea = info.getSellArea();
			BigDecimal totalSellArea = FDCHelper.ZERO;
			for (int j = 0; j < table.getRowCount(); j++) {
				IRow row = table.getRow(j);
				if (row.getUserObject() instanceof MeasureIncomeEntryInfo) {
					if(isDetailAcctRow(row)&&!isDetailAcctHasInput(row)){
						boolean isEmpty=true;
						for(int k=3;k<table.getColumnCount();k++){
							if(!FDCHelper.isEmpty(row.getCell(k).getValue())){
								isEmpty=false;
								break;
							}
						}
						if(isEmpty){
							continue;
						}
					}
 
					totalSellArea=FDCHelper.add(totalSellArea,row.getCell("sellArea").getValue());
				}
			}
			if(FDCHelper.toBigDecimal(sellArea).compareTo(totalSellArea)==-1){
				content.append(product.getName());
				content.append("��");
				isBigger=true;
			}
		}
		String msg = content.toString();
		msg=msg.substring(0, msg.length()-1);
		if(isBigger){
			MsgBox.showWarning(msg);
			this.abort();
		}
	}
	
	/**
	 * ϵͳԭ��������ǽ�btnSave����"�ݴ�", btnSubmit����"����"����������"�ύ"���ֿͻ�Ҫ���ܹ��ڹ��������������ʱ����ṩ"�ύ"���ܡ�
	 * �ʽ�actionSubmit_actionPerformed()�����еĴ���Ǩ�Ƶ�actionSave_actionPerformed()��.actionSubmit_actionPerformed()����Ϊ
	 * �ύʱ���߼�  by Cassiel_peng   2009-08-18
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		this.setOprtState("EDIT");
		/*		if(editData.getId()==null){
					editData.setId(BOSUuid.create(editData.getBOSType()));
				}*/
				editData.put("PlanIndex",planIndexTable.getPlanIndexInfo());
		    	Boolean isEditVersion=(Boolean)getUIContext().get("isEditVersion");
		    	if(isEditVersion!=null&&isEditVersion.booleanValue()){
		    		handleVersion((MeasureIncomeInfo)editData);
		    	}
				//����Ƿ���Ա���
				if(editData.getId()!=null){
					FilterInfo filter=new FilterInfo();
					filter.appendFilterItem("id",editData.getId().toString());
					filter.appendFilterItem("state", FDCBillStateEnum.AUDITTED_VALUE);
					if(MeasureIncomeFactory.getRemoteInstance().exists(filter)){
						FDCMsgBox.showWarning(this, "�������������ܽ��д˲���");
						SysUtil.abort();
					}
				}
				confirmVersionOnly();
				checkSellArea();
//				super.actionSubmit_actionPerformed(e);
		    	super.actionSave_actionPerformed(e);
//				planIndexTable.save(editData.getId().toString());
				this.storeFields();
				this.initOldData(this.editData);
//				actionImportTemplate.setEnabled(false);
				
				getUIContext().put("isEditVersion",null);
				
				setDataChange(false);
			}
	
	/**
	 * �ڱ�����ύ��ʱ����Ҫ�ٴ�ȷ�ϰ汾��Ψһ��  by Cassiel_peng 2009-8-19
	 * @throws BOSException 
	 */
	public void confirmVersionOnly() throws BOSException{
		
		Boolean isAimMeasure = (Boolean)getUIContext().get("isAimMeasure");
		String  versionNum = this.txtVersionNumber.getText();
		if(isAimMeasure.booleanValue())
		{
			MeasureIncomeInfo info=(MeasureIncomeInfo)this.editData;
			String selectOrgId = getUIContext().get("orgId").toString();
			FDCSQLBuilder builder=new FDCSQLBuilder();
			builder.appendSql("select REPLACE(FVersionNumber, '!', '.') as FVersionNumber from T_AIM_MeasureIncome where FOrgUnitID = ? ");
			builder.addParam(selectOrgId);
			builder.appendSql(" and FIsAimMeasure = ? ");
			builder.addParam(isAimMeasure) ;
			builder.appendSql(" and FVersionNumber = ? ");
			builder.addParam(versionNum) ;
			
			IRowSet row = builder.executeQuery();
			if(row.size() != 0)
			{
				FDCMsgBox.showWarning(this, "�ð汾���Ѿ�����");
				SysUtil.abort();
			}
		}
		else
		{
			String selectOrgId = getUIContext().get("orgId").toString();
			
			FDCSQLBuilder builder=new FDCSQLBuilder();
			builder.appendSql("select REPLACE(FVersionNumber, '!', '.') as FVersionNumber from T_AIM_MeasureIncome where FOrgUnitID = ? ");
			builder.addParam(selectOrgId);
			builder.appendSql(" and FIsAimMeasure = ? ");
			builder.addParam(isAimMeasure) ;
			builder.appendSql(" and FVersionNumber = ? ");
			builder.addParam(versionNum) ;
			
			IRowSet row = builder.executeQuery() ;
			if(row.size() !=0)
			{
				FDCMsgBox.showWarning(this, "�ð汾���Ѿ�����");
				SysUtil.abort();
			}
		}

	}
	
	protected boolean isModifySave() {
		return isModify();
	}
	
	
	/**
	 * ԭ��ϵͳ����������д������"����"�����߼�������Ҫ�ṩ"�ύ"���ܹ��ѽ��÷���ԭ���߼�Ǩ����actionSave_actionPerformed()������
	 * by Cassiel_peng   2009-08-18
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		this.setOprtState("EDIT");
/*		if(editData.getId()==null){
			editData.setId(BOSUuid.create(editData.getBOSType()));
		}*/
		editData.put("PlanIndex",planIndexTable.getPlanIndexInfo());
    	Boolean isEditVersion=(Boolean)getUIContext().get("isEditVersion");
    	if(isEditVersion!=null&&isEditVersion.booleanValue()){
    		handleVersion((MeasureIncomeInfo)editData);
    	}
		//����Ƿ���Ա���
		if(editData.getId()!=null){
			FilterInfo filter=new FilterInfo();
			filter.appendFilterItem("id",editData.getId().toString());
			filter.appendFilterItem("state", FDCBillStateEnum.AUDITTED_VALUE);
			if(MeasureIncomeFactory.getRemoteInstance().exists(filter)){
				FDCMsgBox.showWarning(this, "�������������ܽ��д˲���");
				SysUtil.abort();
			}
		}
		confirmVersionOnly();
		checkSellArea();
		super.actionSubmit_actionPerformed(e);
		//�ύ��֮��Ҫ��"����ģ��"�ҵ� ��ֻ�����������ݵ�ʱ������ʹ��"����ģ��"   by Cassiel_peng   2009-8-19
//		actionImportTemplate.setEnabled(false);
		this.storeFields();
		this.initOldData(this.editData);
//		actionImportTemplate.setEnabled(false);
		
		getUIContext().put("isEditVersion",null);
		
		setDataChange(false);
	}

	/**
	 * @see com.kingdee.eas.framework.client.EditUI
	 * @see com.kingdee.eas.framework.client.CoreBillEditUI
	 * ���ڸ�UIֱ�Ӽ̳еĿ�ܵ�EditUI,û�м̳�CoreBillEditUI������FDCBillEditUI(��������������д������������)
	 * ����еı�����ύ�ɹ���ʱ����ʾ��Ϣ��׼ȷ,���Լ�����д    by Cassiel_peng  2009-8-19
	 */
	protected void showSaveSuccess() {
		setMessageText(getClassAlise()
				+ " "
				+ EASResource.getString(FrameWorkClientUtils.strResource
						+ "Msg_Save_OK"));
		setNextMessageText(getClassAlise()
				+ " "
				+ EASResource.getString(FrameWorkClientUtils.strResource
						+ "Msg_Edit"));
		setShowMessagePolicy(SHOW_MESSAGE_DEFAULT);
		setIsShowTextOnly(false);
		showMessage();
	}
	protected void showSubmitSuccess() {
		setMessageText(getClassAlise()
				+ " "
				+ EASResource.getString(FrameWorkClientUtils.strResource
						+ "Msg_Submit_OK"));
		if (this.chkMenuItemSubmitAndAddNew.isSelected()) {
			setNextMessageText(getClassAlise()
					+ " "
					+ EASResource.getString(FrameWorkClientUtils.strResource
							+ "Msg_AddNew"));
		} else if (!this.chkMenuItemSubmitAndPrint.isSelected()
				&& this.chkMenuItemSubmitAndAddNew.isSelected()) {
			setNextMessageText(getClassAlise()
					+ " "
					+ EASResource.getString(FrameWorkClientUtils.strResource
							+ "Msg_Edit"));
		}
		setIsShowTextOnly(false);
		setShowMessagePolicy(SHOW_MESSAGE_DEFAULT);
		showMessage();
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = (KDTable) this.tables.get(this.plTables
				.getSelectedIndex());
		table.getPrintManager().print();
	}

	/**
	 * refresh measure table 2-X
	 */
	private void refreshAllMeasureTable(){
		for(int i=2;i<tables.size();i++){
			KDTable table=(KDTable)tables.get(i);
			//refreshMeasureTable(table);
		}

	}
	
	private void refreshMeasureTable(KDTable table){
		if(table.getHeadRow(0).getUserObject() instanceof ProductTypeInfo){
			ProductTypeInfo product=(ProductTypeInfo)table.getHeadRow(0).getUserObject();
			table.getHeadRow(0).getCell(0).setUserObject(
					planIndexTable.getPlanIndexEntryInfo(product.getId().toString()));

		}else{
			PlanIndexInfo planIndexInfo = planIndexTable.getPlanIndexInfo();

			table.getHeadRow(0).getCell(0).setUserObject(planIndexInfo);
		}
		
		for (int j = 0; j < table.getRowCount(); j++) {
			IRow row = table.getRow(j);
			if (row.getUserObject() instanceof MeasureIncomeEntryInfo) {
				try {
					//table_editStopped(table, new KDTEditEvent(table,null,null,j,table.getColumnIndex("indexName"),false,1));
//					table_editStopped(table, new KDTEditEvent(table,null,null,j,table.getColumnIndex("sumPrice"),false,1));
				} catch (Exception e) {
					handUIException(e);
				}
			}
		}
		
	}
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		KDTable table = (KDTable) this.tables.get(this.plTables
				.getSelectedIndex());
		table.getPrintManager().printPreview();
	}
	
	/**
	 * get new data from six or main table to measureIncomeMap �������Ŀû����six or main��
	 */
	void refreshMeasureIncomeMap(){
		storeFields();
		MeasureIncomeInfo income = (MeasureIncomeInfo) this.editData;

		measureIncomeMap.clear();
		MeasureIncomeEntryCollection incomeEntrys = income.getIncomeEntry();
		for (int i = 0; i < incomeEntrys.size(); i++) {
			MeasureIncomeEntryInfo info = incomeEntrys.get(i);
			IncomeAccountInfo incomeAccount = info.getIncomeAccount();
			String key = incomeAccount.getId().toString();
			if (info.getProduct() != null) {
				key += info.getProduct().getId().toString();
			}
			if (measureIncomeMap.containsKey(key)) {
				MeasureIncomeEntryCollection coll = (MeasureIncomeEntryCollection) measureIncomeMap
						.get(key);
				coll.add(info);
			} else {
				MeasureIncomeEntryCollection newColl = new MeasureIncomeEntryCollection();
				newColl.add(info);
				measureIncomeMap.put(key, newColl);
			}
		}
	}
	TreeModel getIncomeAcctTree(){
		return incomeAcctTree;
	}
	Map getMeasureIncomeMap() {
		return measureIncomeMap;
	}
	PlanIndexIncomeTable getPlanIndexTable() {
		return planIndexTable;
	}
	
	KDTabbedPane getplTables(){
		return plTables;
	}
	
	List getTables(){
		return tables;
	}
	
//	KDTDefaultCellEditor getIndexEditor(CostAccountTypeEnum type,String productId){
//		Object []items=null;
//		if(type==CostAccountTypeEnum.SIX){
//			items=getSixItems();
//		}else if(type==CostAccountTypeEnum.MAIN){
//			items=getMainItems();
//		}else{
//			return null;
//		}
//		KDComboBox box=new KDComboBox(items);
//		return 	new KDTDefaultCellEditor(box);
//	}
	
	private Object[] MAINITEMS=null;
	private Object[] getMainItems(){
		MAINITEMS=null;
		if(MAINITEMS==null){
			CustomPlanIndexEntryCollection customPlanIndexs = planIndexTable.getCustomPlanIndexs("productId");
			if(customPlanIndexs.size()==0){
				MAINITEMS=Item.PRODUCTITEMS;
			}else{
				List list=new ArrayList();
				Set appSet=new HashSet();
				for(int i=0;i<customPlanIndexs.size();i++){
					String appId = customPlanIndexs.get(i).getApportType().getId().toString();
					if(!appSet.contains(appId)){
						appSet.add(appId);
						list.add(Item.getCustomItem(customPlanIndexs.get(i).getApportType()));
					}
				}
				MAINITEMS=new Item[list.size()+Item.PRODUCTITEMS.length];
				System.arraycopy(Item.PRODUCTITEMS, 0, MAINITEMS, 0, Item.PRODUCTITEMS.length);
				int i=Item.PRODUCTITEMS.length;
				for(Iterator iter=list.iterator();iter.hasNext();){
					MAINITEMS[i++]=iter.next();
				}
			}
		}
		return MAINITEMS;
	}
	
	public static class Item{
		String key=null;
		String name=null;
		String productId=null;
		BigDecimal sellArea=null;
		boolean isCustom=false;
		Item(String key,String name) {
			this.key=key;
			this.name=name;
		}
		private Item(ApportionTypeInfo info) {
			this.key=info.getId().toString();
			this.name=info.getName();
			isCustom=true;
		}
		private static Map hashMap=null;
		public static Item getCustomItem(ApportionTypeInfo info){
			if(hashMap==null){
				hashMap=new HashMap();
			}
			Item item=(Item)hashMap.get(info.getId());
			if(item==null){
				item=new Item(info);
			}
			hashMap.put(info.getId(), item);
			return item;
			
		}
		public boolean isCustomIndex(){
			return isCustom;
		}
		public String toString() {
			return name;
		}
		
		public int hashCode() {
			return super.hashCode();
		}
		
		/**
		 * ���๫̯ʹ��ָ��
		 */
		public static Item [] SIXITEMS=new Item[]{
			new Item("empty",	""),	
			new Item("totalContainArea",	"��ռ�����"),	
			new Item("buildArea",	"�����õ����"),	
			new Item("totalBuildArea",	"�ܽ������"),	
			new Item("buildContainArea",	"������ռ�����"),
//			new Item("buildDensity",	"�����ܶ�	"),
//			new Item("greenAreaRate",	"�̵���"),	
			new Item("cubageRateArea",	"���ݻ������"),	
//			new Item("cubageRate",	"�ݻ���"),	
			new Item("totalRoadArea",	"��·�õغϼ�"),	
			new Item("totalGreenArea",	"�̻��õغϼ�	"),
			new Item("pitchRoad",	"����·�泵�е�"),
			new Item("concreteRoad",	"��·�泵�е���ͣ������"),	
			new Item("hardRoad",	"Ӳ����װ���е�"),
			new Item("hardSquare",	"Ӳ����װ�㳡	"),
			new Item("hardManRoad",	"Ӳ����װ���е�"),
			new Item("importPubGreenArea",	"��Ҫ�����̵�	"),
			new Item("houseGreenArea",	"����լ���̻�	"),
			new Item("privateGarden",	"�ײ�˽�һ�԰	"),
			new Item("warterViewArea",	"ˮ�����"),
			new Item("viewArea",	"�������")//,
//			new Item("doors",	"����")
			
		};
		
		/**
		 * ��Ʒʹ��ָ��
		 */
		public static Item [] PRODUCTITEMS=new Item[]{
				new Item("empty",	""),
				new Item("containArea",	"ռ�����"),	
				new Item("buildArea",	"�������"),	
				new Item("sellArea",	"�������"),	
				new Item("cubageRate",	"�ݻ���"),	
				new Item("productRate",	"��Ʒ����"),	
				new Item("unitArea",	"ƽ��ÿ�����"),	
				new Item("units",	"��Ԫ��"),	
				new Item("doors",	"����"),
		};
		
		/**
		 * ���ܱ��Ʒ��̯ʹ��ָ��
		 */
		public static Item [] SPLITITEMS = new Item[] { 
				new Item("man", "ָ����̯"), 
				new Item("buildArea", "�������"), 
				new Item("sellArea", "�������"),
				new Item("containArea", "ռ�����"),
				new Item("cubageRate", "�ݻ���"), 
				new Item("productRate", "��Ʒ����"), 
				new Item("unitArea", "ƽ��ÿ�����"), 
				new Item("units", "��Ԫ��"),
				new Item("doors", "����	"),

		};

	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyRequire(this);
		if(comboMeasureStage.getSelectedItem()==null){
			FDCMsgBox.showWarning(this,"����׶β���Ϊ��");
			this.abort();
		}
		MeasureIncomeInfo info = (MeasureIncomeInfo)editData;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",info.getSrcMeasureCostId()));
		
		if(!MeasureCostFactory.getRemoteInstance().exists(filter)){
			FDCMsgBox.showWarning(this,"��Ŀ��������Ӧ��Ŀ��ɱ������ѱ�ɾ�����ܱ��棡");
			this.abort();
		}
		super.verifyInput(e);
		
	}
	
	public void actionImportTemplate_actionPerformed(ActionEvent e)
			throws Exception {
	}
	
	public void actionExitCurrent_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}
	
	protected void comboMeasureStage_itemStateChanged(ItemEvent e) throws Exception {
		super.comboMeasureStage_itemStateChanged(e);
		MeasureStageInfo stageInfo = (MeasureStageInfo)comboMeasureStage.getSelectedItem();
		if(stageInfo==null || project==null){
			return;
		}
		if(STATUS_ADDNEW.equals(getOprtState()) || STATUS_EDIT.equals(getOprtState())){
			String msg=AimCostClientHelper.getRes("addNew");
			Boolean isAimMeasure=(Boolean)getUIContext().get("isAimMeasure");
			MeasureStageInfo lastStageInfo = AimCostClientHelper.getLastMeasureStage(project,isAimMeasure.booleanValue());
			if (lastStageInfo != null && FDCHelper.subtract(lastStageInfo.getNumber(), stageInfo.getNumber()).compareTo(FDCHelper.ZERO) == 1) {
				comboMeasureStage.setSelectedItem(null);
				StringBuffer sb = new StringBuffer();
				sb.append("�Ѵ��� [ ").append(lastStageInfo.getNumber()).append("-").append(lastStageInfo.getName()).append(" ] ���հ汾��Ŀ��ɱ�����,����").append(msg).append(" [ ").append(stageInfo.getNumber())
						.append(" ] Ŀ��ɱ����㡣");
				FDCMsgBox.showWarning(sb.toString());
				SysUtil.abort();
			}
			/**
			 * Ŀ��ɱ���������ʱ������Ŀ��ɱ����հ汾��Ӧ�Ĳ���׶μ��汾�ţ�����ͬһ����׶ε��µİ汾��Ŀ��ɱ����㣬���������޸Ĳ���׶�Ϊ��һ�׶Σ�ͬʱ�����Ӧ�İ汾��
			 */
			MeasureIncomeInfo info = ((MeasureIncomeInfo)editData);
			MeasureIncomeVersionHandler version = new MeasureIncomeVersionHandler(
					info.getOrgUnit().getId().toString(),info.getProject().getId().toString(), isAimMeasure.booleanValue());
			
			String versionNumber = version.getLastVersion();
			if(versionNumber.indexOf('!')==-1){
				versionNumber = versionNumber.replace('.', '!');
			}
			txtVersionNumber.setText(MeasureIncomeVersionHandler.getNextVersion(versionNumber).replaceAll("!", "\\."));
		}
		
	}
	
    public void actionExportAllToExcel_actionPerformed(ActionEvent e) throws Exception
    {
    	
        ExportManager exportM = new ExportManager();
        String path = null;
        File tempFile = File.createTempFile("eastemp",".xls");
        path = tempFile.getCanonicalPath();



        KDTables2KDSBookVO[] tablesVO = new KDTables2KDSBookVO[tables.size()];
        for(int i =0;i<tables.size();i++)
        {
            tablesVO[i] = new KDTables2KDSBookVO((KDTable)tables.get(i));
            String title = plTables.getTitleAt(i);
            title=title.replaceAll("[{\\\\}{\\*}{\\?}{\\[}{\\]}{\\/}]", "|");
			tablesVO[i].setTableName(title);
        }
        KDSBook book = null;
        book = KDTables2KDSBook.getInstance().exportKDTablesToKDSBook(tablesVO,true,true);

        exportM.exportToExcel(book, path);
        
		// ������excel��
		try
		{
			KDTMenuManager.openFileInExcel(path);
			tempFile.deleteOnExit();
		}
		catch (IOException e2)
		{
			// excel��ʧ�ܣ����浽ָ��λ��
			KDFileChooser fileChooser = new KDFileChooser();
			int result = fileChooser.showSaveDialog(this);
			if (result == KDFileChooser.APPROVE_OPTION)
			{
				// File dest = this.getFileChooser().getSelectedFile();
				File dest = fileChooser.getSelectedFile();
				try
				{
					// ��������ʱ�ļ���ָ��Ŀ��
					File src = new File(path);
					if (dest.exists())
						dest.delete();
					src.renameTo(dest);
				}
				catch (Exception e3)
				{
					handUIException(e3);
				}
			}
		}

    }
    
    public void onShow() throws Exception {
        
    	this.setQueryPreference(false);
    	super.onShow();
		FDCUIWeightWorker.getInstance().addWork(new IFDCWork(){
			public void run(){
				//refreshAllMeasureTable();
				setDataChange(false);
			}
		});
        if(!getOprtState().equals(OprtState.ADDNEW) && !getOprtState().equals(OprtState.EDIT)){
        	for(int i=0;i<tables.size();i++){
        		((KDTable)tables.get(i)).getStyleAttributes().setLocked(true);
        		if(i>1){
        			ICellEditor editor =((KDTable)tables.get(i)).getColumn("sellArea").getEditor();
        			if(editor!=null&&editor.getComponent()!=null){
        				editor.getComponent().setEnabled(false);
        			}
        			editor =((KDTable)tables.get(i)).getColumn("price").getEditor();
        			if(editor!=null&&editor.getComponent()!=null){
        				editor.getComponent().setEnabled(false);
        			}
        			
        		}
        	}
//        	actionAddRow.setVisible(false);
        	actionAddRow.setEnabled(false);
//        	actionDeleteRow.setVisible(false);
        	actionDeleteRow.setEnabled(false);
        	
        }
        //������Ŀ����ѡ�񣬱���������ʱ�����������������������
        this.prmtProject.setEnabled(false);
        /**
         * by Cassiel_peng 2009-8-18
         */
        /*this.btnSave.setIcon(EASResource.getIcon("imgTbtn_save"));
        this.btnSubmit.setIcon(EASResource.getIcon("imgTbtn_submit"));*/
//        this.btnSave.setVisible(true);
//        this.btnSave.setEnabled(true);
//        this.menuItemSave.setVisible(true);
//        this.menuItemSave.setEnabled(true);
//        this.menuItemSubmit.setVisible(true);
//        this.menuItemSubmit.setEnabled(true);
    }
    
    /**
     * �޶�����
     */
    private void handleVersion(MeasureIncomeInfo info){
    	//����Դ�汾��������޶��İ汾�����޶���Դ�汾��֮ͬ������ΪԴ�汾
    	if(info.getSourceBillId()==null){
    		info.setSourceBillId(info.getId().toString());
    	}
    	info.setId(null);
    	info.setIsLastVersion(false);
    	info.setState(FDCBillStateEnum.SAVED);
    	for(Iterator iter=info.getIncomeEntry().iterator();iter.hasNext();){
    		((MeasureIncomeEntryInfo)iter.next()).setId(null);
    	}
    	
    	PlanIndexInfo planIndex=(PlanIndexInfo)editData.get("PlanIndex");
    	if(planIndex==null) return;
    	planIndex.setId(null);
    	for(Iterator iter=planIndex.getEntrys().iterator();iter.hasNext();){
    		((PlanIndexEntryInfo)iter.next()).setId(null);
    	}
    	for(Iterator iter=planIndex.getCustomEntrys().iterator();iter.hasNext();){
    		((CustomPlanIndexEntryInfo)iter.next()).setId(null);
    	}
    }
    
	private boolean hasChanged=false;
    void setDataChange(boolean hasChange){
    	hasChanged=hasChange;
    }
    
    public boolean isModify() {
        //�鿴״̬���ж��Ƿ��޸�
        if (OprtState.VIEW.equals(getOprtState())) {
            return false;
        }
    	return hasChanged;
    }
    
    private FDCBillInfo getFDCBillInfo() {
		return (FDCBillInfo) editData;
	}
    

    /**
     * �õ���ʼ�Ĺ滮ָ���
     * @throws BOSException 
     */
    private PlanIndexInfo getInitPlanIndexInfo() throws BOSException{
    	if(editData==null){
    		return null;
    	}
    	if(editData.get("PlanIndex")==null&&editData.getId()!=null){
    		EntityViewInfo view=new EntityViewInfo();
    		FilterInfo filter=new FilterInfo();
    		view.setFilter(filter);
    		//filter.appendFilterItem("headID", editData.getId().toString());
    		filter.appendFilterItem("headID", editData.get("srcMeasureCostId").toString());
    		
    		SelectorItemCollection selector=view.getSelector();
    		selector.add("*");
    		selector.add("entrys.*");
    		selector.add("entrys.product.*");
    		selector.add("customEntrys.*");
    		selector.add("customEntrys.productType.id");
    		selector.add("customEntrys.productType.number");
    		selector.add("customEntrys.productType.name");
    		selector.add("customEntrys.apportType.id");
    		selector.add("customEntrys.apportType.number");
    		selector.add("customEntrys.apportType.name");
    		selector.add("customEntrys.value");
    		view.getSorter().add(new SorterItemInfo("entrys.type"));
    		view.getSorter().add(new SorterItemInfo("entrys.index"));
    		PlanIndexCollection planIndexCollection = PlanIndexFactory.getRemoteInstance().getPlanIndexCollection(view);
    		if(planIndexCollection.size()==1){
    			editData.put("PlanIndex",planIndexCollection.get(0));
    		}
    		
    	}
		
    	return (PlanIndexInfo)editData.get("PlanIndex");
    }
    
    public boolean isAimMeasure(){
		Boolean isAimMeasure=(Boolean)getUIContext().get("isAimMeasure");
		if(isAimMeasure!=null){
			return isAimMeasure.booleanValue();
		}else{
			return false;
		}
    }
    
    private void addTotalRow(KDTable table){
		try {
			//AimCostClientHelper.setTotalCostRow(table, new String[]{"sumPrice","buildPart","sellPart","adjustAmt","amount"});
			AimCostClientHelper.setTotalCostRow(table, new String[]{"amount"});
		} catch (Exception e) {
			handUIException(e);
		}
    }
    private boolean isDetailAcctRow(IRow row){
    	if(row!=null&&row.getCell(0).getUserObject()!=null&&row.getCell(0).getUserObject().equals("DetailInput")){
    		return true;
    	}
    	return false;
    }
    public void setDetailAcctRowNull(IRow row){
    	if(row!=null&&row.getCell(0).getUserObject()!=null){
    		row.getCell(0).setUserObject(null);
    	}
    }
    private void setDetailAcctRow(IRow row){
    	if(row!=null&&row.getCell(0)!=null){
    		row.getCell(0).setUserObject("DetailInput");
    	}
    	if(row != null && row.getCell("acctName") != null)
    	{
    		row.getCell("acctName").getStyleAttributes().setLocked(true);
    		row.getCell("acctName").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	}
    }
    private void setDetailAcctHashInput(IRow row){
    	if(row!=null&&row.getCell(1)!=null){
    		row.getCell(1).setUserObject("HasInput");
    	}
    }
    private boolean isDetailAcctHasInput(IRow row){
    	if(row!=null&&row.getCell(1).getUserObject()!=null&&row.getCell(1).getUserObject().equals("HasInput")){
    		return true;
    	}
    	return false;
    }
    
    public void setDetailAcctHasNotInput(IRow row){
    	if(row!=null&&row.getCell(1)!=null){
    		row.getCell(1).setUserObject(null);
    	}
    }
    public void clearDetailAcctRow(KDTable table,IRow row){
		for(int i=3;i<table.getColumnCount();i++){
			row.getCell(i).setValue(null);
		}
    }
    
	private void setTemplateMeasureCostF7Editor(final KDTable table) {
		KDBizPromptBox myPrmtBox=new  KDBizPromptBox(){
			protected Object stringToValue(String t) {
				Object obj = super.stringToValue(t);
				if(obj!=null){
					return FDCHelper.toBigDecimal(t,4);			//R090514-137����԰��
				}
				return obj;
			}
		};
		
		myPrmtBox.addSelectorListener(new SelectorListener(){
			public void willShow(SelectorEvent e) {
				IRow row=KDTableUtil.getSelectedRow(table);
				int colIdx=table.getSelectManager().getActiveColumnIndex();
				boolean isPrice=true;
				if(table.getColumnIndex("price")==colIdx){
					isPrice=true;
				}else{
					isPrice=false;
				}
				Object obj = row.getUserObject();
				final CostAccountInfo acct;
				if(obj instanceof MeasureEntryInfo){
					acct=((MeasureEntryInfo)obj).getCostAccount();
				}else{
					return;
				}

				
				String acctLongNumber=acct.getLongNumber();
				String projectTypeId=null;
				String productId=null;
				Object product = table.getHeadRow(0).getUserObject();
				if(product instanceof ProductTypeInfo){
					productId=((ProductTypeInfo)product).getId().toString();
				}
				if(acct.getType()==CostAccountTypeEnum.SIX){
					Object v = prmtProjectType.getValue();
					if(v instanceof ProjectTypeInfo){
						projectTypeId=((ProjectTypeInfo)v).getId().toString();
						
					}else{
						MsgBox.showWarning(getplTables(), "���๫̯������������Ŀϵ��");
						e.setCanceled(true);
						return;
					}
				}
				TemplateMeasureCostPromptBox selector=getTemplateMeasureCostPromptBox();
				selector.setAcctLongNumber(acctLongNumber);
				selector.setProjectTypeID(projectTypeId);
				selector.setProductId(productId);
				selector.setIsPrice(isPrice);
				if(!isPrice){
					//ϵ��Ҫ������ָ��
					if(row.getCell("indexName").getValue() instanceof Item){
						String key = ((Item)row.getCell("indexName").getValue()).key;
						if(key==null||key.equals("empty")){
							MsgBox.showWarning(getplTables(), "����ѡ��ָ��");
							e.setCanceled(true);
							return;
						}
						selector.setIndex(key);
						
					}else{
						MsgBox.showWarning(getplTables(), "����ѡ��ָ��");
						e.setCanceled(true);
						return;
					}
				}else{
					selector.setIndex(null);
				}
				((KDBizPromptBox) e.getSource()).setSelector(selector);
			}
		});
		ICellEditor f7Editor = new KDTDefaultCellEditor(myPrmtBox);
		table.getColumn("price").setEditor(f7Editor);
		table.getColumn("coefficient").setEditor(f7Editor);
	}
	
	private TemplateMeasureCostPromptBox selector=null;
	private TemplateMeasureCostPromptBox getTemplateMeasureCostPromptBox(){
		if(selector==null){
			String orgId = (String) getUIContext().get("orgId");
			Boolean objBoolean=(Boolean)getUIContext().get("isAimMeasure");
			boolean isAimMeasure=true;
			if(objBoolean!=null){
				isAimMeasure=objBoolean.booleanValue();
			}
			selector=new TemplateMeasureCostPromptBox(this,isAimMeasure,orgId);
		}
		return selector;
	}
	
	
	/**
	 * ��˽������params(HashMap),�ɲ�ȡ���·�ʽȡֵ
	 * <pre>if (params==null) {
			try {
				params = FDCUtils.getDefaultFDCParam(null, null);
			} catch (Exception e) {
				handUIException(e);
			}
		}
        Object theValue = params.get(FDCConstants.FDC_PARAM_MEASUREQUALITY);
        if(theValue != null){
        	return Boolean.valueOf(theValue.toString()).booleanValue();
		}else{
			return false;
		}
	 */
	private HashMap params = null;
	private HashMap getParams() {
		if(params==null){
	        HashMap hmParamIn = new HashMap();
	        hmParamIn.put(FDCConstants.FDC_PARAM_MEASUREADJUST, null);
	        hmParamIn.put(FDCConstants.FDC_PARAM_MEASUREQUALITY, null);
	        hmParamIn.put(FDCConstants.FDC_PARAM_USECOSTOMINDEX, null);
	        hmParamIn.put(FDCConstants.FDC_PARAM_PLANINDEXLOGIC, null);
	        hmParamIn.put(FDCConstants.FDC_PARAM_BUILDPARTLOGIC, null);
	        try{
	        	HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
	        	params=hmAllParam;
	        }catch(Exception e){
	        	handUIException(e);
	        }
		}
		if(params==null){
			params=new HashMap();
		}
        return params;
	}
	/**
	 * ϵͳ�������Ƿ����õ���ϵ��
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private boolean isUseAdjustCoefficient() {
		Object theValue = getParams().get(FDCConstants.FDC_PARAM_MEASUREADJUST);
        if(theValue != null){
        	return Boolean.valueOf(theValue.toString()).booleanValue();
		}else{
			return false;
		}
	}
	
	/**
	 * ����Ʒ��������
	 * @return
	 */
	private boolean isUseQuality() {
		Object theValue = getParams().get(FDCConstants.FDC_PARAM_MEASUREQUALITY);
        if(theValue != null){
        	return Boolean.valueOf(theValue.toString()).booleanValue();
		}else{
			return false;
		}
	}
	/**
	 * �����Զ���ָ��
	 * @return
	 */
	protected boolean isUseCustomIndex() {
		Object theValue = getParams().get(FDCConstants.FDC_PARAM_USECOSTOMINDEX);
        if(theValue != null){
        	return Boolean.valueOf(theValue.toString()).booleanValue();
		}else{
			return false;
		}
	}
	/**
	 * ���гɱ����㼰Ŀ��ɱ�����ʱ���滮ָ����ϣ����е����㳡�����ڡ��̻��õغϼơ���
	 * @return
	 */
	protected boolean isPlanIndexLogic() {
		Object theValue = getParams().get(FDCConstants.FDC_PARAM_PLANINDEXLOGIC);
        if(theValue != null){
        	return Boolean.valueOf(theValue.toString()).booleanValue();
		}else{
			return false;
		}
	}
	/**
	 * ���гɱ����㡢Ŀ��ɱ���������Ŀ�����������㣬Ŀ��ɱ����㵼����Ŀ�������ָ�궼ʹ�ò����̯�Ĳ�Ʒ�������֮�ͣ��������ܽ������
	 * @return
	 */
	protected boolean isBuildPartLogic() {
		Object theValue = getParams().get(FDCConstants.FDC_PARAM_BUILDPARTLOGIC);
        if(theValue != null){
        	return Boolean.valueOf(theValue.toString()).booleanValue();
		}else{
			return false;
		}
	}
	
	private Object[] SIXITEMS=null;
	private Object[] getSixItems(){
		SIXITEMS=null;
		if(SIXITEMS==null){
			CustomPlanIndexEntryCollection customPlanIndexs = planIndexTable.getCustomPlanIndexs(null);
			if(customPlanIndexs.size()==0){
				SIXITEMS=Item.SIXITEMS;
			}else{
				List list=new ArrayList();
				Set appSet=new HashSet();
				for(int i=0;i<customPlanIndexs.size();i++){
					String appId = customPlanIndexs.get(i).getApportType().getId().toString();
					if(!appSet.contains(appId)){
						appSet.add(appId);
						list.add(Item.getCustomItem(customPlanIndexs.get(i).getApportType()));
					}
				}
				SIXITEMS=new Item[list.size()+Item.SIXITEMS.length];
				System.arraycopy(Item.SIXITEMS, 0, SIXITEMS, 0, Item.SIXITEMS.length);
				int i=Item.SIXITEMS.length;
				for(Iterator iter=list.iterator();iter.hasNext();){
					SIXITEMS[i++]=iter.next();
				}
			}
			
		}
		return SIXITEMS;
	}
	
	private BigDecimal getCustomIndexValue(IObjectValue info,Item item){
		if(item.isCustomIndex()){
			CustomPlanIndexEntryInfo customEntry=null;
			if(info instanceof PlanIndexInfo){
				customEntry = planIndexTable.getCustomPlanIndexEntryInfo(item.key, null);
			}
			if(info instanceof PlanIndexEntryInfo){
				PlanIndexEntryInfo entry=(PlanIndexEntryInfo)info;
				if(entry.getProduct()==null){
					return FDCHelper.ZERO;
				}
				String productId=entry.getProduct().getId().toString();
				customEntry = planIndexTable.getCustomPlanIndexEntryInfo(item.key, productId);
			}
			
			if(customEntry!=null){
				return customEntry.getValue();
			}
		}else{
			return info.getBigDecimal(item.key);
		}
		return null;
	}
	

	/**
	 * ��Ȩ��Ŀ�����ڱ���ʱ���ж�
	 */
	private Set accreditSet=null;
	
    /**
     * ��ԭ��������з�¼���ݵ��߼��ĳ������Ȩ��Ŀ������δ����Ȩû����ʾ�Ŀ�Ŀ���ݾͲ����ڱ���������
     *  by sxhong 2009-01-05 14:04:41
     * @param aimCost
     * @throws BOSException
     */
    private void handleAimCostAccredit(MeasureIncomeInfo measureIncome) throws BOSException{
    	if(!AcctAccreditHelper.hasUsed(null)||accreditSet==null||accreditSet.size()==0){
    		//��ʹ�ÿ�Ŀ��Ȩ������°���ԭ���ķ�ʽ�����������
    		measureIncome.getIncomeEntry().clear();
    		return;
    	}
    	for(int i=measureIncome.getIncomeEntry().size()-1;i>=0;i--){
    		MeasureIncomeEntryInfo entry=measureIncome.getIncomeEntry().get(i);
    		if(entry==null||entry.getIncomeAccount()==null){
    			continue;
    		}
    		//�ⲿ�����´ӽ���ȡֵ��������ɾ����
    		if(accreditSet.contains(entry.getIncomeAccount().getId().toString())){
    			measureIncome.getIncomeEntry().remove(entry);
    		}else{
    			entry.setId(null);
    		}
    	}
    }
    /**
	 * ���ݲ���������״̬���ƿؼ�״̬
	 */
    public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		MeasureIncomeInfo info = (MeasureIncomeInfo) editData;
		btnSave.setVisible(true);
		if (STATUS_ADDNEW.equals(this.oprtState)) {
			actionSubmit.setEnabled(true);
			actionSave.setEnabled(true);
		} else if (STATUS_EDIT.equals(this.oprtState)) {
			actionSave.setEnabled(true);
			if(info != null&&info.getState() == FDCBillStateEnum.SUBMITTED){
				actionSubmit.setEnabled(true);
				actionSave.setEnabled(false);
				btnSave.setEnabled(false);
			}else if(info!=null&&info.getState()==FDCBillStateEnum.SAVED){
				actionSubmit.setEnabled(true);
			}else{
				actionSubmit.setEnabled(false);
			}
		} else if (STATUS_VIEW.equals(this.oprtState)) {
			actionSave.setEnabled(false);
			actionSubmit.setEnabled(false);
		}
		
	}
}