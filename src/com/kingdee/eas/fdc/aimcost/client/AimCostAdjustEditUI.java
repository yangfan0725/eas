/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryCollection;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryInfo;
import com.kingdee.eas.fdc.aimcost.AimCostAdjustEntryInfo;
import com.kingdee.eas.fdc.aimcost.AimCostAdjustFactory;
import com.kingdee.eas.fdc.aimcost.AimCostAdjustInfo;
import com.kingdee.eas.fdc.aimcost.AimCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.DyCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.DyProductTypeGetter;
import com.kingdee.eas.fdc.aimcost.DynamicCostInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostMap;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.aimcost.HappenDataGetter;
import com.kingdee.eas.fdc.aimcost.HappenDataInfo;
import com.kingdee.eas.fdc.basedata.AdjustTypeCollection;
import com.kingdee.eas.fdc.basedata.AdjustTypeFactory;
import com.kingdee.eas.fdc.basedata.AdjustTypeInfo;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataCollection;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataFactory;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexVerTypeEnum;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class AimCostAdjustEditUI extends AbstractAimCostAdjustEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AimCostAdjustEditUI.class);
    
    private AimCostSplitDataGetter aimGetter;

	private DyCostSplitDataGetter dyGetter;

	private HappenDataGetter happenGetter;
	
    private DyProductTypeGetter productTypeGetter;
    
    //��������
    private static final Map dataMap=new HashMap();
    //����Ʒ��Ϣ����Ŀ
    private static CurProjectInfo prj = null;
    
    
    private static String SELLPRICE="sellPrice";
    
    private static String ADJUSTSELLPRICE="adjustSellPrice";
    
    private static String DIFSELLPRICE="difSellPrice";
    
    private static String  BUILDPRICE="buildPrice";
    
    private static String  ADJUSTBUILDPRICE="adjustBuildPrice";
    
    private static String DIFBUILDPRICE = "difBuildPrice";
    
    /**
     * output class constructor
     */
    public AimCostAdjustEditUI() throws Exception
    {
        super();
        jbInit();
    }
    private void jbInit() {
		titleMain = getUITitle();
	}
    
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		tblProduct.removeRows();
		tblDynCost.removeRows();
	}
    
    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	if(FDCBillStateEnum.AUDITTED.equals(editData.getState())){
    		MsgBox.showWarning(this, ContractClientUtils.getRes("noRemove"));
			SysUtil.abort();
    	}
        super.actionRemove_actionPerformed(e);
    }

	protected void attachListeners() {
	}

	protected void detachListeners() {
	}

	protected ICoreBase getBizInterface() throws Exception {
		return AimCostAdjustFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return this.kdtEntrys;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	private String prjId = null;
	private ProjectIndexDataCollection projectIndexDatas=null; 
	public void onLoad() throws Exception {
		super.onLoad();
		
		/**
		 * �󹤳��ҵ���������������Ƶ�����Ҫ�Ų�
		 * ������Ŀ��ѡ���ϼ���Ŀ��ѡ���޸��¼���Ŀ�ĵ��ݣ�����༭����������Ĵ�����
		 * by hpw 2011.2.11
		 */
		if(editData!=null&&editData.getCurProject() != null) {
			prjId = editData.getCurProject().getId().toString();
			SelectorItemCollection selects = new SelectorItemCollection();
			selects.add("*");
			selects.add("parent.id");
			selects.add("fullOrgUnit.name");
			selects.add("fullOrgUnit.code");
			selects.add("costCenter");
			selects.add("CU.name");
			selects.add("CU.number");
			selects.add("CU.code");
			curProject = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(prjId),selects);
		}
		
		if(curProject!=null){
			prjId = curProject.getId().toString();
		}
		fetchData(prjId);

		initPrmtF7();
		initTable();
		projectIndexDatas = getProjectIndexDatas(prjId);
		setDataMap(projectIndexDatas);
		
		kdtEntrysDataChanged();
		
	}

	private AdjustRecordEntryCollection getAdjustRecordCollection() {
		return null;
	}
	private void fetchData(String prjId)  throws Exception {
		if(prjId==null)
			return;
		final DynamicCostMap dynamicCostMap = FDCCostRptFacadeFactory.getRemoteInstance().getDynamicCost(prjId, curPeriod,true);
		if(dynamicCostMap==null){
			return;
		}
		this.productTypeGetter=dynamicCostMap.getDyProductTypeGetter();
		this.dyGetter=dynamicCostMap.getDyCostSplitDataGetter();
		this.aimGetter=dynamicCostMap.getAimCostSplitDataGetter();
		this.happenGetter=dynamicCostMap.getHappenDataGetter();
		
	}
	private void initTable() {
		FDCHelper.formatTableNumber(getDetailTable(), new String[] {
				"workLoad", "price", "costAmount" }, FDCHelper.strDataFormat);
		getDetailTable().getColumn("adjustDate").getStyleAttributes().setHided(true);
		getDetailTable().getColumn("adjuster").getStyleAttributes().setHided(true);
		
		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		getDetailTable().getColumn("workLoad").setEditor(numberEditor);
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		ICellEditor txtEditor = new KDTDefaultCellEditor(textField);
		getDetailTable().getColumn("unit").setEditor(txtEditor);

		formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(8);
		formattedTextField.setSupportedEmpty(true);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		getDetailTable().getColumn("price").setEditor(numberEditor);
		FDCHelper.formatTableNumber(getDetailTable(), "price", FDCHelper.strDataFormat);
		formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setRequired(true);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		getDetailTable().getColumn("costAmount").setEditor(numberEditor);
		
		textField = new KDTextField();
		textField.setMaxLength(300);//�������ɱ�Ԥ�������¼�ֶ���300��ֻ����¼80;����ͷſ�¼
		txtEditor = new KDTDefaultCellEditor(textField);
		getDetailTable().getColumn("description").setEditor(txtEditor);
		getDetailTable().setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		
		getDetailTable().getColumn("adjuster").getStyleAttributes().setBackground(
				FDCColorConstants.cantEditColor);
		getDetailTable().getColumn("adjustDate").getStyleAttributes().setBackground(
				FDCColorConstants.cantEditColor);
		getDetailTable().getColumn("costAccount").getStyleAttributes().setBackground(
				FDCColorConstants.requiredColor);
		getDetailTable().getColumn("costAmount").getStyleAttributes().setBackground(
				FDCColorConstants.requiredColor);
		
		getDetailTable().getColumn("adjuster").getStyleAttributes().setLocked(true);
		getDetailTable().getColumn("adjustDate").getStyleAttributes().setLocked(true);
		
		getDetailTable().getColumn("costAccount").setWidth(150);
		
		this.setProductColumn(this.tblProduct);
	}
	
	
	private void initPrmtF7() throws BOSException {
		//�ɱ� ��Ŀ
		KDBizPromptBox f7CostAccount = new KDBizPromptBox(){
			protected String valueToString(Object o) {
				String s=super.valueToString(o);
				if(!FDCHelper.isEmpty(s)&&o instanceof IObjectValue){
					return s.replaceAll("!", "\\.");
				}
				return s;
			}
		};
		f7CostAccount
				.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7CostAccountQuery");
		f7CostAccount.setVisible(true);
		f7CostAccount.setEditable(true);
		f7CostAccount.setDisplayFormat("$longNumber$");
		f7CostAccount.setEditFormat("$longNumber$");
		f7CostAccount.setCommitFormat("$longNumber$");
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		CurProjectInfo curProject = (CurProjectInfo)this.prmtProject.getValue();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", curProject.getId().toString()));
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("longNumber"));
		sic.add(new SelectorItemInfo("isEnabled"));
		sic.add(new SelectorItemInfo("isLeaf"));
		sic.add(new SelectorItemInfo("curProject.name"));
		f7CostAccount.setEntityViewInfo(view);
		f7CostAccount.setSelectorCollection(sic);

		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(f7CostAccount);
		this.kdtEntrys.getColumn("costAccount").setEditor(cellEditor);
		ObjectValueRender render = new ObjectValueRender();
		render.setFormat(new BizDataFormat("$name$"));
		this.kdtEntrys.getColumn("costAccount").setRenderer(render);
		
		//��������
		KDComboBox comboAdjustType = new KDComboBox();
		EntityViewInfo adjustTypeView = new EntityViewInfo();
		FilterInfo adjustTypeFilter = new FilterInfo();
		adjustTypeFilter.getFilterItems().add(
				new FilterItemInfo("isEnabled", new Integer(1)));
		adjustTypeView.setFilter(adjustTypeFilter);
		AdjustTypeCollection adjustTypes = AdjustTypeFactory
				.getRemoteInstance()
				.getAdjustTypeCollection(adjustTypeView);
		comboAdjustType.addItem(new AdjustTypeInfo());
		for (int i = 0; i < adjustTypes.size(); i++) {
			comboAdjustType.addItem(adjustTypes.get(i));
		}
		comboAdjustType.setSelectedIndex(0);
		ICellEditor f7AdjustTypeEditor = new KDTDefaultCellEditor(
				comboAdjustType);
		getDetailTable().getColumn("adjustType").setEditor(f7AdjustTypeEditor);
		
		//����ԭ��
		KDBizPromptBox f7AdjustReason = new KDBizPromptBox();
		f7AdjustReason
				.setQueryInfo("com.kingdee.eas.fdc.basedata.app.AdjustReasonQuery");
		EntityViewInfo adjustView = new EntityViewInfo();
		FilterInfo adjustFilter = new FilterInfo();
		adjustFilter.getFilterItems().add(
				new FilterItemInfo("isEnabled", new Integer(1)));
		adjustView.setFilter(adjustFilter);
		f7AdjustReason.setEntityViewInfo(adjustView);
		f7AdjustReason.setEditable(true);
		f7AdjustReason.setDisplayFormat("$name$");
		f7AdjustReason.setEditFormat("$number$");
		f7AdjustReason.setCommitFormat("$number$");
		ICellEditor f7AdjustReasonEditor = new KDTDefaultCellEditor(
				f7AdjustReason);
		getDetailTable().getColumn("adjustReason").setEditor(f7AdjustReasonEditor);
		
		//��Ʒ����
		KDBizPromptBox f7Product = new KDBizPromptBox();
		f7Product
				.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
		EntityViewInfo productView = new EntityViewInfo();
		FilterInfo productFilter = new FilterInfo();
		productFilter.getFilterItems().add(
				new FilterItemInfo("isEnabled", new Integer(1)));
		//TODO ֻ��ѡ���Ӧ����Ŀ��Ʒ?
		productFilter.getFilterItems().add(
				new FilterItemInfo("id", FDCHelper
						.getSetByArray(this.productTypeGetter
								.getProductTypeIds()), CompareType.INCLUDE));
		productView.setFilter(productFilter);
		f7Product.setEntityViewInfo(productView);
		f7Product.setEditable(true);
		f7Product.setDisplayFormat("$name$");
		f7Product.setEditFormat("$number$");
		f7Product.setCommitFormat("$number$");
		ICellEditor f7Editor = new KDTDefaultCellEditor(f7Product);
        getDetailTable().getColumn("productType").setEditor(f7Editor);
		
	}

	/**
	 * �������������Ը�ֵ 
	 */
	protected IObjectValue createNewData() {
		AimCostAdjustInfo objectValue = new AimCostAdjustInfo();
		try {
			objectValue.setCreateTime(FDCDateHelper.getServerTimeStamp());
		} catch (BOSException e) {
			this.handUIException(e);
		}
		if(curProject != null) {		
			objectValue.setCurProject(curProject);
			objectValue.setCU(curProject.getCU());			
			FullOrgUnitInfo costOrg = FDCClientUtils.getCostOrgByProj(curProject.getId().toString());
			objectValue.setOrgUnit(costOrg);
		}

		return objectValue;
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		AimCostAdjustEntryInfo entryInfo = new AimCostAdjustEntryInfo();
		try {
			//ȡ����������
			entryInfo.setAdjustDate(FDCDateHelper.getServerTimeStamp());
		} catch (BOSException e) {
			this.handUIException(e);
		}
		//ȡ��ǰ�û�
		entryInfo.setAdjuster(SysContext.getSysContext().getCurrentUserInfo());
		return entryInfo;
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		
		this.menuBiz.setVisible(false);
		this.menuBiz.setEnabled(false);
		this.menuView.setVisible(false);
		this.menuView.setEnabled(false);
		this.menuItemCreateFrom.setVisible(false);
		this.menuItemCreateFrom.setEnabled(false);
		this.menuItemCreateTo.setVisible(false);
		this.menuItemCreateTo.setEnabled(false);
		
		this.actionTraceDown.setVisible(false);
		this.actionTraceDown.setEnabled(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceUp.setEnabled(false);
		this.actionCreateFrom.setVisible(false);
		this.actionCreateFrom.setEnabled(false);
		this.actionCopy.setVisible(false);
		this.actionCopy.setEnabled(false);
		this.actionCopyFrom.setVisible(false);
		this.actionCopyFrom.setEnabled(false);
		this.actionPre.setVisible(false);
		this.actionPre.setEnabled(false);
		this.actionNext.setVisible(false);
		this.actionNext.setEnabled(false);
		this.actionFirst.setVisible(false);
		this.actionFirst.setEnabled(false);
		this.actionLast.setVisible(false);
		this.actionLast.setEnabled(false);
	}
	
	 protected void kdtEntrys_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		int top0 = kdtEntrys.getSelectManager().get().getTop();
		tblProduct.getSelectManager().select(top0, 0);
		tblDynCost.getSelectManager().select(top0, 1);
	}
	 
	protected void kdtEntrys_editStopped(
			com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e)
			throws Exception {
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		Object newValue = e.getValue();
		Object oldValue = e.getOldValue();
		IRow row = getDetailTable().getRow(rowIndex);
		//�������
		if ( getDetailTable().getColumnKey(colIndex).equals("workLoad")
				||  getDetailTable().getColumnKey(colIndex).equals("price")) {
			BigDecimal workLoad = (BigDecimal) row.getCell("workLoad")
					.getValue();
			BigDecimal price = (BigDecimal) row.getCell("price").getValue();
			if (workLoad == null) {
				workLoad = FDCHelper.ZERO;
			}
			if (price == null) {
				price = FDCHelper.ZERO;
			}
			if (workLoad.compareTo(FDCHelper.ZERO) == 0
					&& price.compareTo(FDCHelper.ZERO) == 0) {
				row.getCell("costAmount").getStyleAttributes().setLocked(false);
				row.getCell("workLoad").setValue(null);
				row.getCell("price").setValue(null);
			} else {
				BigDecimal aimCost = workLoad.multiply(price);
				row.getCell("costAmount").setValue(
						aimCost.setScale(2, BigDecimal.ROUND_HALF_UP));
				row.getCell("costAmount").getStyleAttributes().setLocked(true);
			}
		}
		//�ɱ���Ŀ
		if(getDetailTable().getColumnKey(colIndex).equals("costAccount")){
			if(newValue instanceof CostAccountInfo){
				CostAccountInfo info = (CostAccountInfo)newValue;
				if(!info.isIsLeaf()){
					row.getCell("costAccount").setValue(null);
					MsgBox.showWarning(this, "��ѡ����ϸ��Ŀ��");
					SysUtil.abort();
				}else{
					setDisply();
				}
			}
		}
		
		
		//TODO
		tblProductDataChanged(row);
		tblDynCostDataChanged(row);
	}
	private void kdtEntrysDataChanged(){
		if(kdtEntrys.getRowCount()==0){
			return;
		}
		tblProduct.checkParsed();
		tblDynCost.checkParsed();
		FDCHelper.formatTableNumber(this.tblDynCost, new String[] { "aimcost",
				"dynCost", "hasHappen", "intendAmt", "allAdjustAmt",
				"adjustAmt", "adjustDynCost", "adjustIntendAmt" },
				FDCHelper.strDataFormat);
		for(int i=0;i<kdtEntrys.getRowCount();i++){
			IRow row = kdtEntrys.getRow(i);
			tblProductDataChanged(row);
			tblDynCostDataChanged(row);
		}
	}
	
	private void tblProductDataChanged(IRow row){
		AimCostAdjustEntryInfo entry = (AimCostAdjustEntryInfo)row.getUserObject();
		BigDecimal adjustAmount = FDCHelper.toBigDecimal(row.getCell("costAmount").getValue());

		int rowIndex = row.getRowIndex();
		IRow tblRow = tblProduct.getRow(rowIndex);
		if(tblRow==null){
			tblRow = tblProduct.addRow();
		}else{
			for(int i=0;i<tblProduct.getColumnCount();i++){
				tblProduct.getCell(rowIndex, i).setValue(null);
			}
		}
		String key = prjId;

		BigDecimal sellArea = FDCHelper.ZERO;
		BigDecimal buildArea = FDCHelper.ZERO;
		
		String productId = null;
		String acctId = null;
		Object obj = row.getCell("productType").getValue();
		if (obj != null && obj instanceof ProductTypeInfo) {
			ProductTypeInfo type = (ProductTypeInfo) obj;
			productId=type.getId().toString();
		}	
		obj = row.getCell("costAccount").getValue();
		if(obj!=null&&obj instanceof CostAccountInfo){
			CostAccountInfo acct = (CostAccountInfo)obj;
			tblRow.getCell("costAccount").setValue(acct.getName());
			acctId = acct.getId().toString();
		}
		
		
		BigDecimal dynCost = FDCHelper.ZERO;
		if(acctId!=null){
			
			DynamicCostInfo dynamicCostInfo = this.dyGetter
			.getDynamicInfo(acctId);
			dynCost = this.aimGetter.getAimCost(acctId);
			if (productId!=null) {
				// ��Ŀ��Ʒ
				key = productId;
				sellArea = getKeyValue(key, 0);
				buildArea = getKeyValue(key, 1);
				tblRow.getCell("sellArea").setValue(sellArea);
				tblRow.getCell("buildArea").setValue(buildArea);
				
				if(dynamicCostInfo!=null){
					//��Ʒ��̬�ɱ�
					AdjustRecordEntryCollection entrys = dynamicCostInfo.getAdjustEntrys();
					Map productDynamic;
					try {
						productDynamic = this.dyGetter.getDyProductMap(acctId, entrys, true);
						Set keySet = productDynamic.keySet();
						for (Iterator iter = keySet.iterator(); iter.hasNext();) {
							String prodId = (String) iter.next();
							if(!productId.equals(prodId)){
								continue;
							}
							BigDecimal value = (BigDecimal)productDynamic.get(prodId);
							BigDecimal adjustValue = FDCHelper.add(value, adjustAmount);
							
							BigDecimal sellPrice = FDCHelper.divide(value, sellArea, 2, BigDecimal.ROUND_HALF_UP);
							BigDecimal adjustSellPrice = FDCHelper.divide(adjustValue, sellArea, 2, BigDecimal.ROUND_HALF_UP);
							BigDecimal difSellPrice = FDCHelper.subtract(adjustSellPrice, sellPrice);
							tblRow.getCell(SELLPRICE+productId).setValue(sellPrice);
							tblRow.getCell(ADJUSTSELLPRICE+productId).setValue(adjustSellPrice);
							tblRow.getCell(DIFSELLPRICE+productId).setValue(difSellPrice);
							
							BigDecimal buildPrice = FDCHelper.divide(value, buildArea, 2, BigDecimal.ROUND_HALF_UP);
							BigDecimal adjustBuildPrice = FDCHelper.divide(adjustValue, buildArea, 2, BigDecimal.ROUND_HALF_UP);
							BigDecimal difBuildPrice = FDCHelper.subtract(adjustBuildPrice, buildPrice);
							tblRow.getCell(BUILDPRICE+productId).setValue(buildPrice);
							tblRow.getCell(ADJUSTBUILDPRICE+productId).setValue(adjustBuildPrice);
							tblRow.getCell(DIFBUILDPRICE+productId).setValue(difBuildPrice);
						}
					} catch (BOSException e) {
						handUIException(e);
					}
				}else{
					//��Ŀ֮ǰ�޵�����ֱ�Ӹ�ָ���Ĳ�Ʒ
					BigDecimal value = dynCost;
					BigDecimal adjustValue = FDCHelper.add(value, adjustAmount);
					
					BigDecimal sellPrice = FDCHelper.divide(value, sellArea, 2, BigDecimal.ROUND_HALF_UP);
					BigDecimal adjustSellPrice = FDCHelper.divide(adjustValue, sellArea, 2, BigDecimal.ROUND_HALF_UP);
					BigDecimal difSellPrice = FDCHelper.subtract(adjustSellPrice, sellPrice);
					tblRow.getCell(SELLPRICE+productId).setValue(sellPrice);
					tblRow.getCell(ADJUSTSELLPRICE+productId).setValue(adjustSellPrice);
					tblRow.getCell(DIFSELLPRICE+productId).setValue(difSellPrice);
					
					BigDecimal buildPrice = FDCHelper.divide(value, buildArea, 2, BigDecimal.ROUND_HALF_UP);
					BigDecimal adjustBuildPrice = FDCHelper.divide(adjustValue, buildArea, 2, BigDecimal.ROUND_HALF_UP);
					BigDecimal difBuildPrice = FDCHelper.subtract(adjustBuildPrice, buildPrice);
					tblRow.getCell(BUILDPRICE+productId).setValue(buildPrice);
					tblRow.getCell(ADJUSTBUILDPRICE+productId).setValue(adjustBuildPrice);
					tblRow.getCell(DIFBUILDPRICE+productId).setValue(difBuildPrice);
					
				}
			}
			
			//��Ŀ
			key = prjId;
			sellArea = getKeyValue(key, 0);
			buildArea = getKeyValue(key, 1);
			
			if (dynamicCostInfo != null) {
				AdjustRecordEntryCollection entrys = dynamicCostInfo.getAdjustEntrys();
				if(entrys!=null&&entrys.size()>0){
					
					for (int i = 0; i < entrys.size(); i++) {
						AdjustRecordEntryInfo info = entrys.get(i);
						//TODO �ų����ڵ�
						if(entry!=null&&entry.getId()!=null){
							if(entry.getId().toString().equals(info.getAdjustEntryId())){
								continue;
							}
						}
						
						BigDecimal costAmount = info.getCostAmount();
						if (costAmount != null) {
							dynCost = FDCHelper.add(dynCost, costAmount);
						}
					}
				}
			}
			
			BigDecimal adjustValue = FDCHelper.add(dynCost, adjustAmount);
			BigDecimal sellPrice = FDCHelper.divide(dynCost, sellArea, 2, BigDecimal.ROUND_HALF_UP);
			BigDecimal adjustSellPrice = FDCHelper.divide(adjustValue, sellArea, 2, BigDecimal.ROUND_HALF_UP);
			BigDecimal difSellPrice = FDCHelper.subtract(adjustSellPrice, sellPrice);
			
			BigDecimal buildPrice = FDCHelper.divide(dynCost, buildArea, 2, BigDecimal.ROUND_HALF_UP);
			BigDecimal adjustBuildPrice = FDCHelper.divide(adjustValue, buildArea, 2, BigDecimal.ROUND_HALF_UP);
			BigDecimal difBuildPrice = FDCHelper.subtract(adjustBuildPrice, buildPrice);
			
			if(productId==null){
				tblRow.getCell("sellArea").setValue(sellArea);
				tblRow.getCell("buildArea").setValue(buildArea);
			}
			tblRow.getCell(SELLPRICE).setValue(sellPrice);
			tblRow.getCell(ADJUSTSELLPRICE).setValue(adjustSellPrice);
			tblRow.getCell(DIFSELLPRICE).setValue(difSellPrice);
			
			tblRow.getCell(BUILDPRICE).setValue(buildPrice);
			tblRow.getCell(ADJUSTBUILDPRICE).setValue(adjustBuildPrice);
			tblRow.getCell(DIFBUILDPRICE).setValue(difBuildPrice);
			
		}	
		tblRow.getStyleAttributes().setLocked(true);
	}
	
	private void tblDynCostDataChanged(IRow row){
		AimCostAdjustEntryInfo entry = (AimCostAdjustEntryInfo)row.getUserObject();
		BigDecimal adjustAmount = FDCHelper.toBigDecimal(row.getCell("costAmount").getValue());

		int rowIndex = row.getRowIndex();
		IRow tblRow = tblDynCost.getRow(rowIndex);
		if(tblRow==null){
			tblRow = tblDynCost.addRow();
		}else{
			for(int i=0;i<tblDynCost.getColumnCount();i++){
				tblDynCost.getCell(rowIndex, i).setValue(null);
			}
		}
		Object obj = row.getCell("costAccount").getValue();
		
		if(obj!=null&&obj instanceof CostAccountInfo){
			CostAccountInfo acct = (CostAccountInfo)obj;
			tblRow.getCell("costAccount").setValue(acct.getName());
			String acctId = acct.getId().toString();
			
			//Ŀ��ɱ�
			BigDecimal aimcost = this.aimGetter.getAimCost(acctId);
			tblRow.getCell("aimcost").setValue(aimcost);
			
			//��̬�ɱ�
			BigDecimal dynCost = aimcost;
			DynamicCostInfo dynamicCostInfo = this.dyGetter
					.getDynamicInfo(acctId);
			if (dynamicCostInfo != null) {
				AdjustRecordEntryCollection entrys = dynamicCostInfo.getAdjustEntrys();
				for (int i = 0; i < entrys.size(); i++) {
					AdjustRecordEntryInfo info = entrys.get(i);
					//TODO �ų����ڵ�
					if(entry!=null&&entry.getId()!=null){
						if(entry.getId().toString().equals(info.getAdjustEntryId())){
							continue;
						}
					}
					
					BigDecimal costAmount = info.getCostAmount();
					if (costAmount != null) {
						dynCost = FDCHelper.add(dynCost, costAmount);
					}
				}
			}
			tblRow.getCell("dynCost").setValue(dynCost);
			
			//�ѷ���
			HappenDataInfo happenInfo = this.happenGetter.getHappenInfo(acctId);
			BigDecimal hasHappen = FDCHelper.ZERO;
			if (happenInfo != null) {
				hasHappen = happenInfo.getAmount();
			}
			tblRow.getCell("hasHappen").setValue(hasHappen);
			
			//������
			tblRow.getCell("intendAmt").setValue(FDCHelper.subtract(dynCost, hasHappen));
			
			//��ǰ�ۼƵ���
			tblRow.getCell("allAdjustAmt").setValue(FDCHelper.subtract(dynCost, aimcost));
			
			//���ε���
			tblRow.getCell("adjustAmt").setValue(adjustAmount);
			
			//������Ʒ
			obj = row.getCell("productType").getValue();
			if(obj!=null&&obj instanceof ProductTypeInfo){
				ProductTypeInfo type = (ProductTypeInfo)obj;
				tblRow.getCell("productType").setValue(type.getName());
				
			}
			
			//������Ķ�̬�ɱ�
			BigDecimal adjustDynCost = FDCHelper.add(tblRow.getCell("dynCost").getValue(), adjustAmount);
			tblRow.getCell("adjustDynCost").setValue(adjustDynCost);
			
			//�����������
			tblRow.getCell("adjustIntendAmt").setValue(FDCHelper.subtract(adjustDynCost, tblRow.getCell("hasHappen").getValue()));
		}
		
		
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		int count = getDetailTable().getRowCount();
		if(count==0){
			MsgBox.showWarning(this,"������¼��һ����¼!");
			SysUtil.abort();
		}
		for(int i=0;i<count;i++){
			IRow row = getDetailTable().getRow(i);
			Object costAccount = row.getCell("costAccount").getValue();
			if(costAccount==null){
				MsgBox.showWarning(this,"�� "+(i+1)+" �гɱ���ĿΪ��!");
				SysUtil.abort();
			}
			Object costAmount = row.getCell("costAmount").getValue();
			if(FDCHelper.ZERO.compareTo(FDCHelper.toBigDecimal(costAmount))==0){
				MsgBox.showWarning(this,"�� "+(i+1)+" �гɱ�����Ϊ���δ¼����!");
				SysUtil.abort();
			}
		}
	}
	public SelectorItemCollection getSelectors()
    {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("state"));
		return sic;
    }
	public void loadFields() {
		super.loadFields();
	}
	public void onShow() throws Exception {
		super.onShow();
		setDisply();
		if(STATUS_VIEW.equals(this.getOprtState())){
			this.actionAddLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
		}
	}
	private void setDisply(){
		for(int i=0;i<getDetailTable().getRowCount();i++){
			IRow row = getDetailTable().getRow(i);
			Object value = row.getCell("costAccount").getValue();
			CostAccountInfo info = null;
			if(value instanceof CostAccountInfo){
				info = (CostAccountInfo)value;
				info.setLongNumber(info.getLongNumber().replaceAll("!", "\\."));
			}
			row.getCell("costAccount").setValue(info);
		}
	}
	public void afterActionPerformed(ActionEvent e) {
		super.afterActionPerformed(e);
		setDisply();
	}
	/**
	 * ��ָ������������У����������һ�У�
	 * 
	 * @param table
	 */
	protected void addLine(KDTable table) {
		if(!kdtEntrys.isFocusOwner()){
			return;
		}
		super.addLine(table);
		addProductLine(tblProduct);
		addDynCostLine(tblDynCost);
	}
	private void addProductLine(KDTable table){
		if (table == null) {
			return;
		}
		IRow row = table.addRow();
		row.getStyleAttributes().setLocked(true);
	}
	private void addDynCostLine(KDTable table){
		if (table == null) {
			return;
		}
		IRow row = table.addRow();
		row.getStyleAttributes().setLocked(true);
	}
	/**
	 * ��ָ������в����У��ڵ�ǰѡ����ǰ���룬�����ǰδѡ���κ��еĻ��������������һ�У�
	 * 
	 * @param table
	 */
	protected void insertLine(KDTable table) {
		if(!kdtEntrys.isFocusOwner()){
			return;
		}
		super.insertLine(table);
		insertProductLine(tblProduct);
		insertDynCostLine(tblDynCost);
		
	}
	private void insertProductLine(KDTable table) {
		if (table == null) {
			return;
		}
		IRow row = null;
		if (table.getSelectManager().size() > 0) {
			int top = table.getSelectManager().get().getTop();

			if (isTableColumnSelected(table)) {
				row = table.addRow();
			} else {
				row = table.addRow(top);
			}
		} else {
			row = table.addRow();
		}
		row.getStyleAttributes().setLocked(true);
	}
	private void insertDynCostLine(KDTable table) {
		if (table == null) {
			return;
		}
		IRow row = null;
		if (table.getSelectManager().size() > 0) {
			int top = table.getSelectManager().get().getTop();

			if (isTableColumnSelected(table)) {
				row = table.addRow();
			} else {
				row = table.addRow(top);
			}
		} else {
			row = table.addRow();
		}
		row.getStyleAttributes().setLocked(true);
	}
	/**
	 * ��ָ�������ɾ����ǰѡ���� ���Ӹ���ɾ������ 2007-03-12
	 * 
	 * @param table
	 */
	protected void removeLine(KDTable table) {
		if(!kdtEntrys.isFocusOwner()){
			return;
		}
		int selectSize = table.getSelectManager().size();
		if(selectSize>1){
			FDCMsgBox.showWarning("ѡ���˶���");
			this.abort();
		}
		int top = table.getSelectManager().get().getTop();
		super.removeLine(table);
		removeProductLine(tblProduct,top);
		removeDynCostLine(tblDynCost,top);
		
	}
	private void removeProductLine(KDTable table,int top) {
		if (table == null) {
			return;
		}
		table.removeRow(top);
		// ��������м�¼��λ����һ��
		if (table.getRow(0) != null){
			table.getSelectManager().select(0, 0);
		}
	}
	private void removeDynCostLine(KDTable table, int top) {
		if (table == null) {
			return;
		}
		table.removeRow(top);
		// ��������м�¼��λ����һ��
		if (table.getRow(0) != null){
			table.getSelectManager().select(0, 0);
		}
	}
	private void setProductColumn(KDTable table){
		tblProduct.checkParsed();
		int dynIndex = table.getColumnIndex("difBuildPrice");
		for (int i = table.getColumnCount(); i > dynIndex; i--) {
			table.removeColumn(i);
		}
		if(productTypeGetter==null||productTypeGetter.getSortedProductMap().size()==0){
			return;
		}
		
		Map prodcutMap = this.productTypeGetter.getSortedProductMap();
		Set keySet = prodcutMap.keySet();
		String name = "��Ʒ-";
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String number = (String) iter.next();
			ProductTypeInfo product = (ProductTypeInfo) prodcutMap.get(number);
			String productId = product.getId().toString();
			String productName = name + product.getName();
			IColumn col = table.addColumn(table.getColumnCount());
			int colIndx =col.getColumnIndex();
			col.setKey(SELLPRICE+productId);
			table.getHeadRow(0).getCell(colIndx).setValue(productName);
			table.getHeadRow(1).getCell(colIndx).setValue(table.getHeadRow(1).getCell(3).getValue());
			table.getHeadRow(2).getCell(colIndx).setValue(table.getHeadRow(2).getCell(3).getValue());
			
			col = table.addColumn(table.getColumnCount());
			colIndx =col.getColumnIndex();
			col.setKey(ADJUSTSELLPRICE+productId);
			table.getHeadRow(0).getCell(colIndx).setValue(productName);
			table.getHeadRow(1).getCell(colIndx).setValue(table.getHeadRow(1).getCell(4).getValue());
			table.getHeadRow(2).getCell(colIndx).setValue(table.getHeadRow(2).getCell(4).getValue());
		
			col = table.addColumn(table.getColumnCount());
			colIndx =col.getColumnIndex();
			col.setKey(DIFSELLPRICE+productId);
			table.getHeadRow(0).getCell(colIndx).setValue(productName);
			table.getHeadRow(1).getCell(colIndx).setValue(table.getHeadRow(1).getCell(5).getValue());
			table.getHeadRow(2).getCell(colIndx).setValue(table.getHeadRow(2).getCell(5).getValue());
			
			col = table.addColumn(table.getColumnCount());
			colIndx =col.getColumnIndex();
			col.setKey(BUILDPRICE+productId);
			table.getHeadRow(0).getCell(colIndx).setValue(productName);
			table.getHeadRow(1).getCell(colIndx).setValue(table.getHeadRow(1).getCell(6).getValue());
			table.getHeadRow(2).getCell(colIndx).setValue(table.getHeadRow(2).getCell(6).getValue());
			
			col = table.addColumn(table.getColumnCount());
			colIndx =col.getColumnIndex();
			col.setKey(ADJUSTBUILDPRICE+productId);
			table.getHeadRow(0).getCell(colIndx).setValue(productName);
			table.getHeadRow(1).getCell(colIndx).setValue(table.getHeadRow(1).getCell(7).getValue());
			table.getHeadRow(2).getCell(colIndx).setValue(table.getHeadRow(2).getCell(7).getValue());
			
			col = table.addColumn(table.getColumnCount());
			colIndx =col.getColumnIndex();
			col.setKey(DIFBUILDPRICE+productId);
			table.getHeadRow(0).getCell(colIndx).setValue(productName);
			table.getHeadRow(1).getCell(colIndx).setValue(table.getHeadRow(1).getCell(8).getValue());
			table.getHeadRow(2).getCell(colIndx).setValue(table.getHeadRow(2).getCell(8).getValue());
			table.getHeadMergeManager().mergeBlock(0, 9, 2, table.getColumnCount()-1,KDTMergeManager.FREE_MERGE);
		}
		formatTableNumber(table, 1, table.getColumnCount()-1,FDCHelper.strDataFormat);
		
		
	}
	
	public static void formatTableNumber(KDTable table, int from,int to,String strDataFormat) {
		for(int i=from;i<=to;i++){
			if(i>=table.getColumnCount()){
				break;
			}
			table.getColumn(i).getStyleAttributes().setNumberFormat(strDataFormat);
			table.getColumn(i).getStyleAttributes().setHorizontalAlign(
			HorizontalAlignment.RIGHT); // �Ҷ���
		}
	}
	
	/**
	 * ָ��ֵ��key=��Ŀ����Ʒ��ID+�׶�+[��̬�ɱ��汾]+ָ��
	 * ��̬�ɱ����°汾��key=��ƷID[]+�汾��
	 * @param projectIndexDatas
	 */
	private void setDataMap(ProjectIndexDataCollection projectIndexDatas){
		dataMap.clear();
		for(int i=0;i<projectIndexDatas.size();i++){
			ProjectIndexDataInfo projectIndexDataInfo = projectIndexDatas.get(i);
			String key=projectIndexDataInfo.getProjOrOrgID().toString();
			if(projectIndexDataInfo.getProductType()!=null){
				key=projectIndexDataInfo.getProductType().getId().toString();
			}
			
			//����ָ��:������Ŀ�׶�Ϊ�յ�����
			if(projectIndexDataInfo.getProjectStage()==null){
				continue ;	
			}
			
			key+=projectIndexDataInfo.getProjectStage().getValue();
			for(Iterator iter=projectIndexDataInfo.getEntries().iterator();iter.hasNext();){
				String myKey = key ;
				ProjectIndexDataEntryInfo entry=(ProjectIndexDataEntryInfo)iter.next();
				if(projectIndexDataInfo.getProjectStage()==ProjectStageEnum.DYNCOST){
					myKey+=projectIndexDataInfo.getVerName();
				}
				myKey+=entry.getApportionType().getId().toString();
				if(FDCHelper.isFDCDebug()){
					System.out.println(projectIndexDataInfo.getProjectStage().getValue()+"\t"+projectIndexDataInfo.getVerName()
						+"\t"+projectIndexDataInfo.getProductType()+"\t "+entry.getApportionType()
						+"\t"+entry.getIndexValue()+"\t "+myKey);
				}
				dataMap.put(myKey, entry.getIndexValue());
			}
		}
	}
	
	private CurProjectInfo getCurProject(String prjId) throws EASBizException, BOSException{
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("*"));
		selector.add(new SelectorItemInfo("curProjProductEntries.*"));
		selector.add(new SelectorItemInfo("curProjProductEntries.productType.*"));
		CurProjectInfo prj=CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(prjId),selector);
		return prj;
	}
	
	private ProjectIndexDataCollection getProjectIndexDatas(String prjId) throws EASBizException, BOSException{
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("projOrOrgID", prjId);
		filter.appendFilterItem("isLatestVer", Boolean.TRUE);
		filter.appendFilterItem("isLatestSubVer", Boolean.TRUE);
		filter.setMaskString("#0 and (#1 or #2)");
		view.setFilter(filter);
		
		view.getSelector().add("*");
		view.getSelector().add("productType.*");
		view.getSelector().add("entries.*");
		view.getSelector().add("entries.apportionType.*");
		ProjectIndexDataCollection collection=ProjectIndexDataFactory.getRemoteInstance().getProjectIndexDataCollection(view);
		return collection;
	}
	private boolean isZero(Object value){
		return FDCHelper.isZero(value);
	}
	private BigDecimal getKeyValue(String key,int type){
		BigDecimal value = FDCHelper.ZERO;
		String tkey = null;
		if(type==0){
			//��̬-��������
			tkey=ProjectStageEnum.DYNCOST_VALUE+ProjectIndexVerTypeEnum.COMPLETEAREA_VALUE+ApportionTypeInfo.sellAreaType;
			if(isZero(dataMap.get(key+tkey))){
				//��̬-Ԥ�۲���
				tkey=ProjectStageEnum.DYNCOST_VALUE+ProjectIndexVerTypeEnum.PRESELLAREA_VALUE+ApportionTypeInfo.sellAreaType;
				if(isZero(dataMap.get(key+tkey))){
					//Ŀ��ָ��
					tkey=ProjectStageEnum.AIMCOST_VALUE+ApportionTypeInfo.sellAreaType;
					if(isZero(dataMap.get(key+tkey))){
						//����ָ��
						tkey=ProjectStageEnum.RESEARCH_VALUE+ApportionTypeInfo.sellAreaType;
					}
				}
			}
		}else{
			//��̬-��������
			tkey=ProjectStageEnum.DYNCOST_VALUE+ProjectIndexVerTypeEnum.COMPLETEAREA_VALUE+ApportionTypeInfo.buildAreaType;
			if(isZero(dataMap.get(key+tkey))){
				//��̬-Ԥ�۲���
				tkey=ProjectStageEnum.DYNCOST_VALUE+ProjectIndexVerTypeEnum.PRESELLAREA_VALUE+ApportionTypeInfo.buildAreaType;
				if(isZero(dataMap.get(key+tkey))){
					//Ŀ��ָ��
					tkey=ProjectStageEnum.AIMCOST_VALUE+ApportionTypeInfo.buildAreaType;
					if(isZero(dataMap.get(key+tkey))){
						//����ָ��
						tkey=ProjectStageEnum.RESEARCH_VALUE+ApportionTypeInfo.buildAreaType;
					}
				}
			}
		}
		value = FDCHelper.toBigDecimal(dataMap.get(key+tkey));
		if(isZero(value)){
			value = null;
		}
		return value;
	}	
}