/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDSpinner;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.CommitEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MeasurePhaseInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.market.AreaSetInfo;
import com.kingdee.eas.fdc.market.MeasurePlanTargetCollection;
import com.kingdee.eas.fdc.market.MeasurePlanTargetFactory;
import com.kingdee.eas.fdc.market.MeasurePlanTargetInfo;
import com.kingdee.eas.fdc.market.ValueBreakCollection;
import com.kingdee.eas.fdc.market.ValueBreakEntryCollection;
import com.kingdee.eas.fdc.market.ValueBreakEntryInfo;
import com.kingdee.eas.fdc.market.ValueBreakFactory;
import com.kingdee.eas.fdc.market.ValueBreakInfo;
import com.kingdee.eas.fdc.market.VersionTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ValueBreakEditUI extends AbstractValueBreakEditUI {
	private static final Logger logger = CoreUIObject.getLogger(ValueBreakEditUI.class);
	private KDTable table = null;
	private IRow firstHead = null;
	private IRow secondHead = null;
	private int cycle = 0;
	private int year = 1999;
	private int editYear = 1999;
	final int gapColumn = 4;
	String sellProjectID = null;
	Date now = null;
	private int countCycleNum = 0;//ͳ�Ʒֽ����ڵ������(С��1)
	private int oldVal = 1;
    final static String HJ_NAME = "�ϼ�";
    final static String XJ_NAME = "С��";

	/**
	 * output class constructor
	 */
	public ValueBreakEditUI() throws Exception {
		super();
		Calendar cal = Calendar.getInstance();
		now = SysUtil.getAppServerTime(null);
		editYear = cal.get(Calendar.YEAR);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initUiState();
	}
	
	protected void versionType_itemStateChanged(ItemEvent e) throws Exception {
		super.versionType_itemStateChanged(e);
		VersionTypeEnum type = (VersionTypeEnum)e.getItem();
		if(type == VersionTypeEnum.year){
			this.contEditYear.setVisible(true);
		}else{
			this.contEditYear.setVisible(false);
		}
	}

	public void initUiState() {
//		this.setPreferredSize(getMaximumSize());
		this.menuTable1.setVisible(false);
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		this.menuBiz.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		this.contTotalAmount.setEnabled(false);
		//this.btnSave.setEnabled(true);
		
		this.actionAddNew.setVisible(false);
		this.txtNumber.setRequired(true);

		this.txtYear.setEditor(new com.kingdee.bos.ctrl.swing.KDSpinner.DefaultNumberEditor(this.txtYear, ""));
		this.txtCycle.setEditor(new com.kingdee.bos.ctrl.swing.KDSpinner.DefaultNumberEditor(this.txtCycle, ""));
		this.txtEditYear.setEditor(new com.kingdee.bos.ctrl.swing.KDSpinner.DefaultNumberEditor(this.txtEditYear, ""));

		versionType.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		

		if (this.oprtState.equals(OprtState.ADDNEW)) {
			this.txtEditYear.setValue(Integer.valueOf(editYear));
			this.txtYear.setValue(Integer.valueOf(editYear));
			this.txtCycle.setValue(Integer.valueOf(0));
		}
		
		if(this.oprtState.equals(OprtState.ADDNEW) || this.oprtState.equals(OprtState.EDIT)){
			this.table.getStyleAttributes().setLocked(false);
			btnInsert.setEnabled(true);
			btnRemo.setEnabled(true);
		}
		else{
			btnInsert.setEnabled(false);
			btnRemo.setEnabled(false);
			this.table.getStyleAttributes().setLocked(true);
		}
		btnInsert.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnRemo.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
	}

	protected void afterSubmitAddNew() {
	}

	protected boolean isContinueAddNew() {
		return false;
	}

	private SellProjectInfo SellInfo = null;
	protected IObjectValue createNewData() {
		ValueBreakInfo valueBreakInfo = new ValueBreakInfo();
		SellInfo = (SellProjectInfo)getUIContext().get("sellProject");
		if(SellInfo!=null)
		{
			sellProjectID = SellInfo.getId().toString();
		}
		try 
		{
			if (sellProjectID != null) {
				SellInfo = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sellProjectID));
				valueBreakInfo.setSellProject(SellInfo);
			}
			valueBreakInfo.setBizDate(SysUtil.getAppServerTime(null));
		} catch (Exception e) {
			e.printStackTrace();
		}
		valueBreakInfo.setVersion(setNestVersion(valueBreakInfo.getVersion(),sellProjectID));
		valueBreakInfo.setId(null);
		valueBreakInfo.setIsNewVersoin(false);
		valueBreakInfo.setNumber(null);
		valueBreakInfo.setState(FDCBillStateEnum.SAVED);
		valueBreakInfo.setEditYear(String.valueOf(editYear));
		valueBreakInfo.setAuditor(null);
		valueBreakInfo.setAuditTime(null);
		valueBreakInfo.setCreateTime(null);
		valueBreakInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		valueBreakInfo.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		valueBreakInfo.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		return valueBreakInfo;
	}

	// ������¼��Ʒ����Ϊf7�ؼ�
	private KDBizPromptBox f7productType = null;

	public KDBizPromptBox getF7productType() {
		if (f7productType == null) {
			f7productType = new KDBizPromptBox();
			f7productType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
			view.setFilter(filter);
			f7productType.setEntityViewInfo(view);
			f7productType.setEditable(true);
			f7productType.setDisplayFormat("$name$");
			f7productType.setEditFormat("$number$");
			f7productType.setCommitFormat("$number$");
		}
		return f7productType;
	}

	public void loadFields() {
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
		int idx = idList.getCurrentIndex();
		if (idx >= 0) {
			billIndex = "(" + (idx + 1) + ")";
		} else {
			billIndex = "";
		}
		
		//���ڱ༭ʱ������ֵ
		cycle = editData.getCycle();
		year = editData.getYear();
		editYear = Integer.parseInt(editData.getEditYear());
		txtYear.setValue(Integer.valueOf(editData.getYear()));
		txtCycle.setValue(Integer.valueOf(editData.getCycle()));
		txtEditYear.setValue(Integer.valueOf(editYear));
		
		refreshUITitle();
		setAuditButtonStatus(this.getOprtState());
//		if(editData.getSellProject()!=null)
//    		sellProjectID = editData.getSellProject().getId().toString();
		try {
			initTable();
			loadEntry(false);
		} catch (EASBizException e) {
			logger.error(e.getMessage());
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		attachListeners();
	}

	/**
	 * @isFromEditCell �Ƿ��Ǳ༭��
	 * @author wangyihong
	 * 
	 * ���ط�¼����
	 * */
	public void loadEntry(boolean isFromEditCell) throws EASBizException, BOSException {
		table.removeRows();
		table.checkParsed();
		ValueBreakEntryCollection valueBreakEntryColl = editData.getEntrys();
		//CRMHelper.sortCollection(valueBreakEntryColl, new String[]{"sequence","productType.name","seq"}, true);
		CRMHelper.sortCollection(valueBreakEntryColl, "seq", true);
		
		//������ʼ�����
		if (OprtState.ADDNEW.equals(oprtState)) {
			if(valueBreakEntryColl.size()==0){
				// ���ò�Ʒ����
				for (int i = 0; i < 4; i++) {
					IRow row = table.addRow();
					if (i == 0) {
						row.getCell(0).setValue(PlanIndexTypeEnum.house);
					}
					if (i == 1) {
						row.getCell(0).setValue(PlanIndexTypeEnum.business);
					}
					if (i == 2) {
						row.getCell(0).setValue(PlanIndexTypeEnum.parking);
					}
					if (i == 3) {
						row.getCell(0).setValue(PlanIndexTypeEnum.publicBuild);
					}
				}
			}
		}

		IRow row = null;
		// ����ֵ������д��table��,��д0����
		for (int i = 0; i < valueBreakEntryColl.size(); i++) {
			ValueBreakEntryInfo valueBreakEntryInfo = valueBreakEntryColl.get(i);
			if (valueBreakEntryInfo.getYear() == 0) {
				row = table.addRow();
				row.getCell(0).setValue(valueBreakEntryInfo.getType());
				if (valueBreakEntryInfo.getProductType() != null) {
					String productTypeID = valueBreakEntryInfo.getProductType().getId().toString();
					// ��Ʒ����
					ProductTypeInfo productTypeInfo = ProductTypeFactory.getRemoteInstance().getProductTypeInfo(new ObjectUuidPK(productTypeID));
					// ��ȷֽ��¼
					valueBreakEntryInfo.setProductType(productTypeInfo);
					row.getCell(1).setValue(valueBreakEntryInfo.getProductType());
				}
				if (valueBreakEntryInfo.getNewAreaRange() != null) {
					row.getCell(2).setValue(valueBreakEntryInfo.getNewAreaRange());
				}
				row.getCell(3).setValue(valueBreakEntryInfo.getArea().setScale(2, 4));
				row.getCell(4).setValue(Integer.valueOf(valueBreakEntryInfo.getPloidy()));
				row.getCell(5).setValue(valueBreakEntryInfo.getPrice().setScale(2, 4));
				row.getCell(6).setValue(valueBreakEntryInfo.getAmount().setScale(2, 4));
			}
			else{
				for (int j = 0; j < cycle; j++) {
					if (valueBreakEntryInfo.getYear() == year + j) {
						if (valueBreakEntryInfo.getArea() != null && valueBreakEntryInfo.getArea().compareTo(FDCHelper.ZERO) == 0) {
							row.getCell(7 + j * gapColumn).setValue(null);
						} else {
							if (valueBreakEntryInfo.getPrice() == null) {
								row.getCell(7 + j * gapColumn).setValue(null);
							} else {
								row.getCell(7 + j * gapColumn).setValue(((BigDecimal) valueBreakEntryInfo.getArea()).setScale(2, BigDecimal.ROUND_HALF_UP));
							}
						}
						if (valueBreakEntryInfo.getPloidy() == 0) {
							row.getCell(8 + j * gapColumn).setValue(null);
						} else {
							row.getCell(8 + j * gapColumn).setValue(Integer.valueOf(valueBreakEntryInfo.getPloidy()));
						}
						if (valueBreakEntryInfo.getPrice() != null && valueBreakEntryInfo.getPrice().compareTo(FDCHelper.ZERO) == 0) {
							row.getCell(9 + j * gapColumn).setValue(null);
						} else {
							if (valueBreakEntryInfo.getPrice() == null) {
								row.getCell(9 + j * gapColumn).setValue(null);
							} else {
								row.getCell(9 + j * gapColumn).setValue(((BigDecimal) valueBreakEntryInfo.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
							}
						}
						if (valueBreakEntryInfo.getAmount() != null && valueBreakEntryInfo.getAmount().compareTo(FDCHelper.ZERO) == 0) {
							row.getCell(10 + j * gapColumn).setValue(null);
						} else {
							if (valueBreakEntryInfo.getPrice() == null) {
								row.getCell(10 + j * gapColumn).setValue(null);
							} else {
								row.getCell(10 + j * gapColumn).setValue(((BigDecimal) valueBreakEntryInfo.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP));
							}
						}
					}
				}
			}
		}
		//7����������Ϊ�ֽ�������ݣ���Ҫͳ��
		if(table.getColumnCount() > 7){
			updateTable(0,0,isFromEditCell,true);
		}
		addSumRow();
	}

	public ValueBreakCollection getNewVersionValueBreakColl(String sellProjectId) throws BOSException {
		ValueBreakCollection valueBreakColl = ValueBreakFactory.getRemoteInstance().getValueBreakCollection("select * from where sellProject='" + sellProjectId + "' and isNewVersoin='1' ");
		return valueBreakColl;
	}

	public void addSumRow() {
		// ����Ҫ�ϼƵ��мӵ�set����
		Set columnName = new HashSet();
		for (int i = 0; i < table.getColumnCount(); i++) {
			if (i == 0 || i == 1 || i == 2) {
				continue;
			}
			columnName.add(String.valueOf(i));
		}
		MarketHelpForSec.setTotalRow2(table,true);//����Ʒ���ɺϼ�
		MarketHelpForSec.getYearFootRow(table, columnName);// �ܺϼ�
		
	}

	// ��ȡ�������
	public static int getYear(Date dateTime) {
		Calendar c = Calendar.getInstance();
		c.setTime(dateTime);
		int year = c.get(c.YEAR);
		return year;
	}

	// ���ڱ仯
	protected void txtCycle_stateChanged(ChangeEvent e) throws Exception {
		int cycleValue = Integer.parseInt(this.txtCycle.getValue().toString());
		System.out.println("==>"+oldVal);
		if(cycleValue == 1 && oldVal ==0){
			
		}else{
			if(cycleValue <= 0){
				FDCMsgBox.showInfo("�ֽ�����Ϊ0���ᵼ��֮ǰ�������ݶ�ʧ��");
				oldVal = 0;
				this.txtCycle.setValue(Integer.valueOf(1));
			}
			else{
				storeEntry();
				cycle = ((Integer) txtCycle.getValue()).intValue();
				oldVal = cycle;
				kdtPanel.removeAll();
				initTable();
				table.repaint();
				table.refresh();
				loadEntry(false);
			}
		}
	}

	// ��ݸı�
	protected void txtYear_stateChanged(ChangeEvent e) throws Exception {
		storeEntry();
		year = ((Integer) txtYear.getValue()).intValue();
		kdtPanel.removeAll();
		initTable();
		table.repaint();
		table.refresh();
		loadEntry(false);
	}

	/**
	 * @author wangyihong
	 * 
	 * ������
	 * */
	public void initTable() {
		table = new KDTable(7 + cycle * gapColumn, 2, 0);
		KDTMergeManager mm = table.getHeadMergeManager();
		for (int i = 0; i < 3; i++) {
			mm.mergeBlock(0, i, 1, i, KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(0, i, 1, i, KDTMergeManager.SPECIFY_MERGE);
		}
		for (int i = 0; i < cycle + 1; i++) {
			mm.mergeBlock(0, 3 + i * gapColumn, 0, 6 + i * gapColumn, KDTMergeManager.SPECIFY_MERGE);
		}
		firstHead = table.getHeadRow(0);

		firstHead.getCell(0).setValue("��Ʒ����");
		firstHead.getCell(1).setValue("��Ʒ����");
		// wyh
		firstHead.getCell(2).setValue("�����");
		firstHead.getCell(3).setValue("��Ŀ�ܻ�ֵ");
		for (int i = 0; i < cycle; i++) {
			firstHead.getCell(7 + i * gapColumn).setValue(year + i + "���ֵ");
		}
		secondHead = table.getHeadRow(1);
		secondHead.getCell(0).setValue("");
		secondHead.getCell(1).setValue("");
		secondHead.getCell(2).setValue("");
		for (int i = 0; i < cycle + 1; i++) {
			secondHead.getCell(3 + i * gapColumn).setValue("�������");
			secondHead.getCell(4 + i * gapColumn).setValue("��������");
			secondHead.getCell(5 + i * gapColumn).setValue("���۾���");
			secondHead.getCell(6 + i * gapColumn).setValue("���۽��");
		}
		for (int i = 0; i < table.getColumnCount(); i++) {
			IColumn column = table.getColumn(i);
			if (i == 0) {
				// ��Ʒ����
				ICellEditor combEditor = CommerceHelper.getKDComboColorEditor();
				column.setEditor(combEditor);
				column.getStyleAttributes().setLocked(true);
				column.getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			} else if (i == 1) {
				// ��Ʒ���� F7
				ICellEditor f7Editor = new KDTDefaultCellEditor(getF7productType());
				column.setEditor(f7Editor);
			} else if (i == 2) {
				// �����
				KDBizPromptBox f7AreaRange = new KDBizPromptBox();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
				view.setFilter(filter);
				f7AreaRange.setQueryInfo("com.kingdee.eas.fdc.market.app.AreaSetQuery");
				f7AreaRange.setEntityViewInfo(view);
				f7AreaRange.setEditable(true);
				f7AreaRange.setDisplayFormat("$description$");
				f7AreaRange.setEditFormat("$number$");
				f7AreaRange.setCommitFormat("$number$");
				ICellEditor f7Area = new KDTDefaultCellEditor(f7AreaRange);
				column.setEditor(f7Area);
			} else if (i % 4 == 0 || i == 4) {
				// ���� ����
				ICellEditor integerEditor = getCellIntegerNumberEdit();
				column.setEditor(integerEditor);
				column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			} else {
				// ���,����,���
				ICellEditor bigDecimalEditor = CommerceHelper.getKDFormattedTextDecimalEditor();
				column.setEditor(bigDecimalEditor);
				column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				if (i > 7 && i % 4 == 2) {
					column.getStyleAttributes().setLocked(true);
					column.getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
				}
			}
			if (i == 3 || i == 5 || i == 6) {
				ICellEditor bigDecimalEditor = CommerceHelper.getKDFormattedTextDecimalEditor();
				column.setEditor(bigDecimalEditor);
				column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			}
			// �̶���
			if (i >= 3 && i <= 6) {
				column.getStyleAttributes().setLocked(true);
				column.getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			}
		}
//		kdtPanel.setPreferredSize(new Dimension(800, 300));
//		table.setBounds(new Rectangle(0, 0, kdtPanel.getWidth(), kdtPanel.getHeight() - 10));
		kdtPanel.add(table);
		table.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		table.addKDTEditListener(new KDTEditAdapter() {
			public void editStopped(KDTEditEvent e) {
				table_editStopped(e);
			}
		});
	}

	/**
	 * @columnIndex �к�
	 * @rowIndex    �к�
	 * @isFromEdit  �Ƿ��Ǳ༭����
	 * @isUpdateAll �Ƿ�ȫ��ȫ������
	 * 
	 * @author wangyihong
	 * 
	 * �ж��ǲ��ǶԵ�Ԫ����б༭������ǵ�Ԫ��༭����ô��Ҫÿ�����Ԫ�����ݱ仯������ͳ�ơ�
	 * ��λ��� = ��λ���� * ��λ���� ; ���ǳ�λ   ��� = ��� * ���ۡ�
	 * */
 	private void updateTable(int columnIndex, int rowIndex, boolean isFromEditCell, boolean isUpdateAll) {
 		if(isFromEditCell){
 			if (columnIndex % gapColumn == 3) {//��Ӧ�������仯�����¶�Ӧ�ĵ�����
 				//�ǳ�λ����༭
 				if (!PlanIndexTypeEnum.parking.equals((PlanIndexTypeEnum) table.getCell(rowIndex, 0).getValue())) {
 					getTotalAmount(rowIndex, columnIndex, true);
 					//���¸������ݣ���������߼�
 	 				sumTotal(0,rowIndex,false,isUpdateAll);
 				}
 				else{
 					sumTotal(0,rowIndex,true,isUpdateAll);
 				}
 			} 
 			if (columnIndex % gapColumn == 0) {//��Ӧ������������仯�����¶�Ӧ�ĵ�����
 				//��λ�����༭
 				if (PlanIndexTypeEnum.parking.equals((PlanIndexTypeEnum) table.getCell(rowIndex, 0).getValue())) {
 					getParkTotalAmount(rowIndex, columnIndex, true);
 					//���¸������ݣ���λ�����߼�
 	 				sumTotal(0,rowIndex,false,isUpdateAll);
 				}
 				else{
 					sumTotal(0,rowIndex,false,isUpdateAll);
 				}
 			} 
 			if (columnIndex % gapColumn == 1) {//��Ӧ��ľ��۷����仯�����¶�Ӧ����Ľ��
 				if (PlanIndexTypeEnum.parking.equals((PlanIndexTypeEnum) table.getCell(rowIndex, 0).getValue())) {
 					getParkTotalAmount(rowIndex, columnIndex, false);
 					//���¸������ݣ���λ�����߼�
 	 				sumTotal(0,rowIndex,false,isUpdateAll);
 				} else {
 					getTotalAmount(rowIndex, columnIndex, false);
 					//���¸������ݣ���������߼�
 	 				sumTotal(0,rowIndex,false,isUpdateAll);
 				}
 			}
 		}
 		else{
 			sumTotal(0,0,false,isUpdateAll);
 		}
	}
	
 	/**
 	 * @index    �к�
 	 * @rowIndex �к� 
 	 * @flag     �Ƿ��ǳ�λ
 	 * @isUpdateAll �Ƿ�ȫ��ȫ������
 	 * 
 	 * @author wangyihong
 	 * 
 	 * ���Ϊ�ϼ����������Ϊ�ϼ����������Ϊ�ϼƽ����� = �ϼƽ�� / �ϼ������������
 	 * */
	public void sumTotal(int index, int rowIndex, boolean flag, boolean isUpdateAll) {
		if(isUpdateAll){
			for(int i=0;i<table.getRowCount();i++){
				IRow row = table.getRow(i);
				BigDecimal area = BigDecimal.ZERO;//���������
				int flat = 0;//����������
				BigDecimal amount = BigDecimal.ZERO;//�����ܽ��
				//�ϼ��У���ͳ��
				if(row.getCell(1).getValue() instanceof String){
					continue;
				}
				//С���У���ͳ��
				if(row.getCell(1).getValue() instanceof String){
					continue;
				}
				for(int j=7;j<table.getColumnCount();j++){
					if(j%4 == 3){
						area = area.add(row.getCell(j).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(j).getValue().toString()));
					}
					if(j%4 == 0){
						flat = flat + (row.getCell(j).getValue()==null?0:Integer.parseInt(row.getCell(j).getValue().toString()));
					}
					if(j%4 == 2){
						amount = amount.add(row.getCell(j).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(j).getValue().toString()));
					}
				}
				row.getCell(3).setValue(area);
				row.getCell(4).setValue(Integer.valueOf(flat));
				row.getCell(6).setValue(amount);
				if (PlanIndexTypeEnum.parking.equals((PlanIndexTypeEnum)row.getCell(0).getValue())){
					if(flat != 0){
						row.getCell(5).setValue(amount.divide(new BigDecimal(Integer.valueOf(flat).toString()),2, BigDecimal.ROUND_HALF_UP));
					}
				}
				else{
					if(area.compareTo(BigDecimal.ZERO) != 0){
						row.getCell(5).setValue(amount.divide(area, 2, BigDecimal.ROUND_HALF_UP));
					}
				}
			}
		}else{
			IRow row = table.getRow(rowIndex);
			BigDecimal area = BigDecimal.ZERO;//���������
			int flat = 0;//����������
			BigDecimal amount = BigDecimal.ZERO;//�����ܽ��
			//�ϼ��У���ͳ��
			for(int j=7;j<table.getColumnCount();j++){
				if(j%4 == 3){
					area = area.add(row.getCell(j).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(j).getValue().toString()));
				}
				if(j%4 == 0){
					flat = flat + (row.getCell(j).getValue()==null?0:Integer.parseInt(row.getCell(j).getValue().toString()));
				}
				if(j%4 == 2){
					amount = amount.add(row.getCell(j).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(j).getValue().toString()));
				}
			}
			row.getCell(3).setValue(area);
			row.getCell(4).setValue(Integer.valueOf(flat));
			row.getCell(6).setValue(amount);
			if (flag){
				if(flat != 0){
					row.getCell(5).setValue(amount.divide(new BigDecimal(Integer.valueOf(flat).toString()),2, BigDecimal.ROUND_HALF_UP));
				}
			}
			else{
				if(area.compareTo(BigDecimal.ZERO) != 0){
					row.getCell(5).setValue(amount.divide(area, 2, BigDecimal.ROUND_HALF_UP));
				}
			}
		}
	}

	// ��¼�༭���¼�
	protected void table_editStopped(KDTEditEvent e) {
		int columnIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
		table = (KDTable) e.getSource();
		if (cycle != 0 && e.getColIndex() > 6) {
			updateTable(columnIndex, rowIndex, true, false);
		}
		Set columnName = new HashSet();
		for (int i = 3; i < table.getColumnCount(); i++) {
			columnName.add(String.valueOf(i));
		}
		resetRowRules();// ����Ʒ���ɺϼƣ�С�ƹ���
		MarketHelpForSec.getYearFootRow(table, columnName);// �ܺϼ�
		table.repaint();
		BigDecimal allValue = BigDecimal.ZERO;
		for(int i=0;i<table.getRowCount();i++){
			IRow row = table.getRow(i);
			/*if(row.getCell(1).getValue() instanceof String){
				continue;
			}*/
			if(row.getCell(2).getValue() instanceof AreaSetInfo){
				allValue = allValue.add(row.getCell(6).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(6).getValue().toString()));
				//continue;
			}
			//allValue = allValue.add(row.getCell(6).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(6).getValue().toString()));
		}
		this.txtTotalAmount.setValue(allValue);
	}

	public void resetRowRules() {
		HashMap XJ_MAP = new HashMap();// ���С���к�
		int num = 0;
		for (int i = 0; i < table.getRowCount() - 1; i++) {
			IRow row_xj = table.getRow(i);
			// ��ȡС���к�
			if (row_xj.getCell(0).getValue() == PlanIndexTypeEnum.house && row_xj.getCell(2).getValue() != null) {
				if (row_xj.getCell(2).getValue().equals("С��")) {
					row_xj.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
					XJ_MAP.put(String.valueOf(num), String.valueOf(i));
					num++;
				}
			}
		}

		// ��С��������С�Ƽ���
		for (int i = 0; i < XJ_MAP.size(); i++) {
			int rowNum = Integer.parseInt(XJ_MAP.get(String.valueOf(i)).toString());
			int old_rowNum = 0;
			if (i > 0) {
				old_rowNum = Integer.parseInt(XJ_MAP.get(String.valueOf(i - 1)).toString()) + 1;
			}
			IRow houseRow = table.getRow(rowNum);
			for (int j = 11; j < table.getColumnCount(); j++) {
				if (j % 4 == 1) {// ��ȥ��Ʒ���ɡ���Ʒ���͡�����Ρ����۾�����ľ���
					continue;
				}
				Number TotalArea = null;
				for (int z = old_rowNum; z < rowNum; z++) {
					TotalArea = FDCHelper.add(TotalArea, table.getRow(z).getCell(j).getValue());
				}
				houseRow.getCell(j).setValue(TotalArea);
				if (j % 4 == 2) {// С�Ƶ����۾���
					Number subTotalArea = (Number) houseRow.getCell(j - 3).getValue();
					Number subTotalAmount = (Number) houseRow.getCell(j).getValue();
					BigDecimal price = FDCHelper.divide(subTotalAmount, subTotalArea);
					houseRow.getCell(j - 1).setValue(price);
				}
			}
		}
		// �ϼƼ���
		HashMap HJ_MAP = new HashMap();
		num = 0;
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row_hj = table.getRow(i);
			// ��ȡС���к�
			if (row_hj.getCell(1).getValue() instanceof String) {
				row_hj.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
				HJ_MAP.put(String.valueOf(num), String.valueOf(i));
				num++;
			}
		}
		for (int i = 0; i < HJ_MAP.size(); i++) {
			int rowNum = Integer.parseInt(HJ_MAP.get(String.valueOf(i)).toString());
			int old_rowNum = 1;
			if (i > 0) {
				old_rowNum = Integer.parseInt(HJ_MAP.get(String.valueOf(i - 1)).toString()) + 1;
			}
			IRow houseRow = table.getRow(rowNum);
			if (PlanIndexTypeEnum.house.equals((PlanIndexTypeEnum) houseRow.getCell(0).getValue())) {
				for (int j = 11; j < table.getColumnCount(); j++) {
					if (j % 4 == 1) {// ��ȥ��Ʒ���ɡ���Ʒ���͡�����Ρ����۾�����ľ���
						continue;
					}
					Number TotalArea = null;
					for (int z = 0; z < XJ_MAP.size(); z++) {
						TotalArea = FDCHelper.add(TotalArea, table.getRow(Integer.parseInt(XJ_MAP.get(String.valueOf(z)).toString())).getCell(j).getValue());
					}
					houseRow.getCell(j).setValue(TotalArea);
					if (j % 4 == 2) {// С�Ƶ����۾���
						Number subTotalArea = (Number) houseRow.getCell(j - 3).getValue();
						Number subTotalAmount = (Number) houseRow.getCell(j).getValue();
						BigDecimal price = FDCHelper.divide(subTotalAmount, subTotalArea);
						houseRow.getCell(j - 1).setValue(price);
					}
				}
			} else {
				for (int j = 11; j < table.getColumnCount(); j++) {
					if (j % 4 == 1) {// ��ȥ��Ʒ���ɡ���Ʒ���͡�����Ρ����۾�����ľ���
						continue;
					}
					Number TotalArea = null;
					for (int z = old_rowNum; z < rowNum; z++) {
						TotalArea = FDCHelper.add(TotalArea, table.getRow(z).getCell(j).getValue());
					}
					houseRow.getCell(j).setValue(TotalArea);
					if (j % 4 == 2) {
						Number subTotalArea = (Number) houseRow.getCell(j - 3).getValue();
						Number subTotalAmount = (Number) houseRow.getCell(j).getValue();
						BigDecimal price = FDCHelper.divide(subTotalAmount, subTotalArea);
						houseRow.getCell(j - 1).setValue(price);
					}
				}
			}
		}
	}

	/**
	 * @rowIndex   �к�
	 * @columnIndex �к�
	 * @flag �����������Ϊtrue�����ۼ۸񴥷�false
	 * 
	 * @author wangyihong
	 * 
	 * ���*����=��� flag��ʶ �������Ϊtrue���۸񴥷�false
	 * */
	public void getTotalAmount(int rowIndex, int columnIndex, boolean flag) {
		if (flag) {
			BigDecimal area = (BigDecimal) table.getCell(rowIndex, columnIndex).getValue();
			BigDecimal price = (BigDecimal) table.getCell(rowIndex, columnIndex + 2).getValue();
			if (area != null && price != null) {
				table.getCell(rowIndex, columnIndex + 3).setValue(area.multiply(price).setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		} else {
			BigDecimal area = (BigDecimal) table.getCell(rowIndex, columnIndex - 2).getValue();
			BigDecimal price = (BigDecimal) table.getCell(rowIndex, columnIndex).getValue();
			if (area != null && price != null) {
				table.getCell(rowIndex, columnIndex + 1).setValue(area.multiply(price).setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		}
	}

	/**
	 * @rowIndex   �к�
	 * @columnIndex �к�
	 * @flag ��������Ϊtrue���۸񴥷�false
	 * 
	 * @author wangyihong
	 * 
	 * ����*����=��� flag��ʶ ��������Ϊtrue���۸񴥷�false
	 * */
	public void getParkTotalAmount(int rowIndex, int columnIndex, boolean flag) {
		if (flag) {
			Integer ploidy = (Integer) table.getCell(rowIndex, columnIndex).getValue();
			BigDecimal price = (BigDecimal) table.getCell(rowIndex, columnIndex + 1).getValue();
			if (ploidy != null && price != null) {
				BigDecimal bPloidy = BigDecimal.valueOf(ploidy.intValue());
				// ���ó�λ���۽��
				table.getCell(rowIndex, columnIndex + 2).setValue(bPloidy.multiply(price).setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		} else {
			Number ploidy = (Number) table.getCell(rowIndex, columnIndex - 1).getValue();
			BigDecimal price = (BigDecimal) table.getCell(rowIndex, columnIndex).getValue();
			if (ploidy != null && price != null) {
				BigDecimal bPloidy = BigDecimal.valueOf(ploidy.intValue());
				// ���÷ǳ�λ���۽��
				table.getCell(rowIndex, columnIndex + 1).setValue(bPloidy.multiply(price).setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		}

	}

	protected void prmtMeasureStage_dataChanged(DataChangeEvent e) throws Exception {
		if(prmtMeasureStage.getValue() instanceof MeasurePhaseInfo){
			MeasurePhaseInfo info = (MeasurePhaseInfo)prmtMeasureStage.getValue();
			comboMeasureType.setSelectedItem(info.getPhaseType());
		}
	}
	
	protected void prmtMeasureStage_willCommit(CommitEvent e) throws Exception {
		EntityViewInfo goodEVInfo1 = new EntityViewInfo();
		FilterInfo goodFilter1 = new FilterInfo();
		goodFilter1.getFilterItems().add(new FilterItemInfo("isEnabled", "1"));
		goodEVInfo1.setFilter(goodFilter1);
		prmtMeasureStage.setEntityViewInfo(goodEVInfo1);
	}

	protected void prmtMeasureStage_willShow(SelectorEvent e) throws Exception {
		EntityViewInfo goodEVInfo1 = new EntityViewInfo();
		FilterInfo goodFilter1 = new FilterInfo();
		goodFilter1.getFilterItems().add(new FilterItemInfo("isEnabled", "1"));
		goodEVInfo1.setFilter(goodFilter1);
		prmtMeasureStage.setEntityViewInfo(goodEVInfo1);
	}

	/**
	 * �õ�һ��0����1.0E17��Integer CellEditor
	 * 
	 * @author chetao Date 2011-08-18
	 * @return
	 */
	public KDTDefaultCellEditor getCellIntegerNumberEdit() {
		KDFormattedTextField kdc = new KDFormattedTextField();
		kdc.setDataType(KDFormattedTextField.INTEGER_TYPE);
		kdc.setPrecision(0);
		kdc.setMinimumValue(FDCHelper.ZERO);
		kdc.setMaximumValue(FDCHelper.MAX_VALUE);
		kdc.setHorizontalAlignment(KDFormattedTextField.RIGHT);
		kdc.setSupportedEmpty(true);
		kdc.setVisible(true);
		kdc.setEnabled(true);
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
		return editor;
	}

	protected void attachListeners() {
		addDataChangeListener(this.txtYear);
		addDataChangeListener(this.txtCycle);
	}

	protected void detachListeners() {
		removeDataChangeListener(this.txtYear);
		removeDataChangeListener(this.txtCycle);
	}

	protected void removeDataChangeListener(JComponent com) {
		EventListener[] listeners = null;

		if (com instanceof KDPromptBox) {
			listeners = com.getListeners(DataChangeListener.class);
			for (int i = 0; i < listeners.length; i++) {
				((KDPromptBox) com).removeDataChangeListener((DataChangeListener) listeners[i]);
			}
		} else if (com instanceof KDFormattedTextField) {
			listeners = com.getListeners(DataChangeListener.class);
			for (int i = 0; i < listeners.length; i++) {
				((KDFormattedTextField) com).removeDataChangeListener((DataChangeListener) listeners[i]);
			}
		} else if (com instanceof KDDatePicker) {
			listeners = com.getListeners(DataChangeListener.class);
			for (int i = 0; i < listeners.length; i++) {
				((KDDatePicker) com).removeDataChangeListener((DataChangeListener) listeners[i]);
			}
		} else if (com instanceof KDComboBox) {
			listeners = com.getListeners(ItemListener.class);
			for (int i = 0; i < listeners.length; i++) {
				((KDComboBox) com).removeItemListener((ItemListener) listeners[i]);
			}
		} else if (com instanceof KDSpinner) {
			listeners = com.getListeners(ChangeListener.class);
			for (int i = 0; i < listeners.length; i++) {
				((KDSpinner) com).removeChangeListener((ChangeListener) listeners[i]);
			}
		}
		if (listeners != null && listeners.length > 0) {
			listenersMap.put(com, listeners);
		}
	}

	protected void addDataChangeListener(JComponent com) {
		EventListener[] listeners = (EventListener[]) listenersMap.get(com);
		if (listeners != null && listeners.length > 0) {
			if (com instanceof KDPromptBox) {
				for (int i = 0; i < listeners.length; i++) {
					((KDPromptBox) com).addDataChangeListener((DataChangeListener) listeners[i]);
				}
			} else if (com instanceof KDFormattedTextField) {
				for (int i = 0; i < listeners.length; i++) {
					((KDFormattedTextField) com).addDataChangeListener((DataChangeListener) listeners[i]);
				}
			} else if (com instanceof KDDatePicker) {
				for (int i = 0; i < listeners.length; i++) {
					((KDDatePicker) com).addDataChangeListener((DataChangeListener) listeners[i]);
				}
			} else if (com instanceof KDComboBox) {
				for (int i = 0; i < listeners.length; i++) {
					((KDComboBox) com).addItemListener((ItemListener) listeners[i]);
				}
			} else if (com instanceof KDSpinner) {
				for (int i = 0; i < listeners.length; i++) {
					((KDSpinner) com).addChangeListener((ChangeListener) listeners[i]);
				}
			}
		}
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected void verifyInputForSave() throws Exception {
		if (getNumberCtrl().isEnabled()) {
			VerifyInputUtil.verifyNull(this, txtNumber, "���ݱ��");
		}
		VerifyInputUtil.verifyNull(this, versionType, "�汾����");
		SellProjectInfo sellProjectInfo = (SellProjectInfo) prmtSellProject.getValue();
		int last = txtVersion.getText().lastIndexOf(".");
		String version = txtVersion.getText().substring(0, last);
		if (VersionTypeEnum.check.equals(versionType.getSelectedItem())) {
			if (MonthPlanEditUI.checkVersionType(editData.getId(), sellProjectInfo.getId().toString(), version, new ValueBreakInfo())) {
				MsgBox.showWarning(this, "��ǰ�׶εĽ׶ο��˰��Ѿ����ڣ������������׶ο��˰棡");
				SysUtil.abort();
			}
		}
	}

	protected void verifyInputForSubmint() throws Exception {
		verifyInputForSave();
		this.txtTotalAmount.setRequired(true);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtTotalAmount);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtMeasureStage);
		SellProjectInfo sp = (SellProjectInfo) prmtSellProject.getValue();
		MeasurePhaseInfo mp=(MeasurePhaseInfo) this.prmtMeasureStage.getValue();
		MeasurePlanTargetInfo info=loadLastVersionMeasureData(sp.getId().toString(),mp.getId().toString());
		if(info==null){
			FDCMsgBox.showWarning(this,"�����°�Ŀ���ֵ���㣡");
			SysUtil.abort();
		}
		if(info.getTotalAmount().compareTo(this.txtTotalAmount.getBigDecimalValue())!=0){
			FDCMsgBox.showWarning(this,"�ܻ�ֵ�����°�Ŀ���ֵ�����ܻ�ֵ���ȣ�");
			SysUtil.abort();
		}
		Map map=new HashMap();
		for(int i=0;i<info.getEntry().size();i++){
			if(info.getEntry().get(i).getProductType()==null||info.getEntry().get(i).getNewAreaRange()==null) continue;
			map.put(info.getEntry().get(i).getProductType().getId().toString()+info.getEntry().get(i).getNewAreaRange().getId().toString(),
					"��Ʒ����:"+info.getEntry().get(i).getProductType().getName()+"�����:"+info.getEntry().get(i).getNewAreaRange().getName());
		}
		for(int i=0;i<this.table.getRowCount();i++){
			IRow row=this.table.getRow(i);
			if(row.getCell(1).getValue()==null||row.getCell(2).getValue()==null
					||row.getCell(1).getValue() instanceof String||row.getCell(2).getValue() instanceof String) continue;
			String key=((ProductTypeInfo) row.getCell(1).getValue()).getId().toString()+((AreaSetInfo) row.getCell(2).getValue()).getId().toString();
			String value="��Ʒ����:"+((ProductTypeInfo) row.getCell(1).getValue()).getName()+"�����:"+((AreaSetInfo) row.getCell(2).getValue()).getName();
			
			if(!map.containsKey(key)){
				FDCMsgBox.showWarning(this,"���°�Ŀ���ֵ�����в����� "+value+"��");
				SysUtil.abort();
			}else{
				map.remove(key);
			}
		}
		if(map.size()>0){
			Object[] key = map.keySet().toArray();
			for (int k = 0; k < key.length; k++) { 
				FDCMsgBox.showWarning(this,"��¼�в����� "+map.get(key[k]).toString()+"��");
				SysUtil.abort();
			}
		}
	}
	public MeasurePlanTargetInfo loadLastVersionMeasureData(String projectId ,String measurePhasesId){
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("*");
		sic.add("entry.*");
		sic.add("entry.productType.*");
		sic.add("entry.newAreaRange.*");
		MeasurePlanTargetInfo info = null;
		if(projectId!=null){
			EntityViewInfo evInfo = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("projectAgin",projectId));
			filter.getFilterItems().add(new FilterItemInfo("isNew",Boolean.TRUE));
			evInfo.setFilter(filter);
			evInfo.setSelector(sic);
			MeasurePlanTargetCollection coll = null;
			try {
				coll = MeasurePlanTargetFactory.getRemoteInstance().getMeasurePlanTargetCollection(evInfo);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			CRMHelper.sortCollection(coll, "versions", false);
			if(coll!=null && coll.size()>0){
				info = coll.get(0);
			}
			if(measurePhasesId!=null){
				evInfo = new EntityViewInfo();
				filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("projectAgin",projectId));
				filter.getFilterItems().add(new FilterItemInfo("measurePhases",measurePhasesId));
				filter.getFilterItems().add(new FilterItemInfo("isNew",Boolean.TRUE));
				evInfo.setFilter(filter);
				evInfo.setSelector(sic);
				coll = null;
				try {
					coll = MeasurePlanTargetFactory.getRemoteInstance().getMeasurePlanTargetCollection(evInfo);
				} catch (BOSException e) {
					e.printStackTrace();
				}
				CRMHelper.sortCollection(coll, "versions", false);
				if(coll!=null && coll.size()>0){
					info = coll.get(0);
				}
			}
		}
		return info;
	}

	public void storeFields() {	
		storeEntry();
		super.storeFields();
	}
	
	// ���¼ֵ
	public void storeEntry() {
		editData.getEntrys().clear();
		int columnCount = table.getColumnCount();
		//����ֽ��������С�ڡ�0�����򲻱���
		int cycleValue = Integer.parseInt(this.txtCycle.getValue().toString());
		if(cycleValue > 0){
			int k = 0;
			int seq = 0;
			
			ValueBreakEntryCollection vbec = new ValueBreakEntryCollection();
			for(int i=0;i<table.getRowCount();i++){
				//�ڱ����ʱ���ͬһ��Ʒ���ͽ��кϲ� FIXME ADD BY YLW 20121214
				IRow row = table.getRow(i);
				if (row.getCell(1).getValue() instanceof String)
					continue;
				if (row.getCell(2).getValue() != null) {
					if (row.getCell(2).getValue() instanceof String) {
						continue;
					}
				}
				Object productConstitutes = table.getRow(i).getCell(0).getValue();
        		if(productConstitutes.equals(PlanIndexTypeEnum.house)){
        			seq = 100;
        		}else if(productConstitutes.equals(PlanIndexTypeEnum.business)){
        			seq = 200;
        		}else if(productConstitutes.equals(PlanIndexTypeEnum.parking)){
        			seq = 300;
        		}else{
        			seq = 400;
        		}
				
				ValueBreakEntryInfo valueBreakEntryInfo = null;
				for (int j = 0; j <= cycleValue; j++) {
					if(3+j*gapColumn >= columnCount){
						continue;
					}
					valueBreakEntryInfo = new ValueBreakEntryInfo();
					k++;
					valueBreakEntryInfo.setType((PlanIndexTypeEnum) row.getCell(0).getValue());
					valueBreakEntryInfo.setProductType((ProductTypeInfo) row.getCell(1).getValue());
					if (row.getCell(2).getValue() instanceof AreaSetInfo) {
						valueBreakEntryInfo.setNewAreaRange((AreaSetInfo) row.getCell(2).getValue());
					}
					if (j == 0) {
						valueBreakEntryInfo.setYear(j);
					} else {
						valueBreakEntryInfo.setYear(year + j - 1);
					}
					valueBreakEntryInfo.setArea(row.getCell(3+j*gapColumn).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(3+j*gapColumn).getValue().toString()));
					valueBreakEntryInfo.setPloidy(row.getCell(4+j*gapColumn).getValue()==null?0:Integer.parseInt(row.getCell(4+j*gapColumn).getValue().toString()));
					valueBreakEntryInfo.setPrice(row.getCell(5+j*gapColumn).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(5+j*gapColumn).getValue().toString()));
					valueBreakEntryInfo.setAmount(row.getCell(6+j*gapColumn).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(6+j*gapColumn).getValue().toString()));
					valueBreakEntryInfo.setSequence(seq);
					valueBreakEntryInfo.setSeq(k);
					vbec.add(valueBreakEntryInfo);
					//editData.getEntrys().add(valueBreakEntryInfo);
				}
			}
			CRMHelper.sortCollection(vbec, new String[]{"sequence","productType.name","seq"}, true);
			
			for(int i=0;i<vbec.size();i++){
				ValueBreakEntryInfo vbei = vbec.get(i);
				vbei.setSeq(i);
				editData.getEntrys().add(vbei);
			}
			
			
			/*for (int i = 0; i < table.getRowCount(); i++) {
				
				IRow row = table.getRow(i);
				if (row.getCell(1).getValue() instanceof String)
					continue;
				if (row.getCell(2).getValue() != null) {
					if (row.getCell(2).getValue() instanceof String) {
						continue;
					}
				}
				
				Object productConstitutes = table.getRow(i).getCell(0).getValue();
        		if(productConstitutes.equals(PlanIndexTypeEnum.house)){
        			seq = 100;
        		}else if(productConstitutes.equals(PlanIndexTypeEnum.business)){
        			seq = 200;
        		}else if(productConstitutes.equals(PlanIndexTypeEnum.parking)){
        			seq = 300;
        		}else{
        			seq = 400;
        		}
				
				ValueBreakEntryInfo valueBreakEntryInfo = null;
				for (int j = 0; j <= cycleValue; j++) {
					if(3+j*gapColumn >= columnCount){
						continue;
					}
					valueBreakEntryInfo = new ValueBreakEntryInfo();
					k++;
					valueBreakEntryInfo.setType((PlanIndexTypeEnum) row.getCell(0).getValue());
					valueBreakEntryInfo.setProductType((ProductTypeInfo) row.getCell(1).getValue());
					if (row.getCell(2).getValue() instanceof AreaSetInfo) {
						valueBreakEntryInfo.setNewAreaRange((AreaSetInfo) row.getCell(2).getValue());
					}
					if (j == 0) {
						valueBreakEntryInfo.setYear(j);
					} else {
						valueBreakEntryInfo.setYear(year + j - 1);
					}
					valueBreakEntryInfo.setArea(row.getCell(3+j*gapColumn).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(3+j*gapColumn).getValue().toString()));
					valueBreakEntryInfo.setPloidy(row.getCell(4+j*gapColumn).getValue()==null?0:Integer.parseInt(row.getCell(4+j*gapColumn).getValue().toString()));
					valueBreakEntryInfo.setPrice(row.getCell(5+j*gapColumn).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(5+j*gapColumn).getValue().toString()));
					valueBreakEntryInfo.setAmount(row.getCell(6+j*gapColumn).getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell(6+j*gapColumn).getValue().toString()));
					//valueBreakEntryInfo.setSeq(seq+k);
					valueBreakEntryInfo.setSequence(seq);
					valueBreakEntryInfo.setSeq(k);
					editData.getEntrys().add(valueBreakEntryInfo);
				}
			}*/
		}
		editData.setCycle(cycle);
		editData.setYear(year);
		editData.setTotalAmount(this.txtTotalAmount.getBigDecimalValue());
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		this.setOprtState("STATUS_VIEW");
		super.actionSubmit_actionPerformed(e);
		reSetRow();
	}
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		reSetRow();
	}
	
	public void reSetRow() throws EASBizException, BOSException{
		kdtPanel.removeAll();
		initTable();
		table.repaint();
		table.refresh();
		loadEntry(false);
	}

	/**
	 * ������
	 * */
	protected void btnInsert_actionPerformed(ActionEvent e) throws Exception {
		this.insertLine("addNew");
	}

	protected void btnRemo_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = table.getSelectManager().getActiveRowIndex();
		int colIndex = table.getSelectManager().getActiveColumnIndex();
		IRow row = table.getRow(rowIndex);
		if(colIndex == 0){
			FDCMsgBox.showInfo("��ѡ�о����Ʒ���ͽ���ɾ����");
			return;
		}
		if(row.getCell(1).getValue() instanceof String){
			if(row.getCell(1).getValue().equals("�ϼ�")){
				return;
			}
		}
		if(FDCMsgBox.showConfirm2("�Ƿ�ȷ��ɾ���˲�Ʒ��") == 2){
			return;
		}
		Object productConstitutes = table.getRow(rowIndex).getCell(0).getValue();
		isCanRemoveRow();
		table.getScriptManager().removeAll();
		table.removeRow(rowIndex);
//		removeLine(this.table);
	}
	
	/**
	 * �жϸ����ܷ�ɾ��
	 * */
	public void isCanRemoveRow(){
		int rowIndex = table.getSelectManager().getActiveRowIndex();
		int num =0;
		if(rowIndex!=-1){
			PlanIndexTypeEnum planenum =(PlanIndexTypeEnum)table.getRow(rowIndex).getCell(0).getValue();
			num = sameNum(planenum);
			if(num<=2){
//				MsgBox.showInfo("����ɾ�����һ�У�");
				for(int j=1;j<table.getColumnCount();j++){
					if(j == 2 || j == 1){
						table.getRow(rowIndex).getCell(j).setValue(null);
					}
					else{
						table.getRow(rowIndex).getCell(j).setValue(FDCHelper.ZERO);
					}
				}
				SysUtil.abort();
			}
			if(table.getRow(rowIndex).getCell(1).getValue()!=null){
				String productType = table.getRow(rowIndex).getCell(1).getValue().toString();
				if(HJ_NAME.equals(productType)){
					MsgBox.showInfo("����ɾ���ϼ��У�");
					SysUtil.abort();
				}
			}
		}
		else{
			MsgBox.showInfo("��ѡ����ɾ����");
			SysUtil.abort();
		}
	}
	
	/**
	 * ��ȡ��Ʒ��������
	 * */
	public int sameNum(PlanIndexTypeEnum planenum){
		int  planIndexRowCount =0;
		for(int i = 0 ; i < table.getRowCount() ; i ++){
			IRow row = table.getRow(i);
			PlanIndexTypeEnum planEnum = (PlanIndexTypeEnum)row.getCell(0).getValue();
			if(planEnum!=null&&planEnum.equals(planenum)){
				planIndexRowCount = planIndexRowCount +1;
			}
		}
		return planIndexRowCount;
	}
	
	/**
	 * @value �������
	 * @author wangyihong
	 * 
	 * ������
	 * */
	public void insertLine(String value){
		if(table == null)
			return;
        IRow row = null;
        if(table.getSelectManager().size() > 0)
        {    
            int top = table.getSelectManager().get().getTop();
            //wyh סլ���ںϼ���������һ�м�¼,סլ�����б����ѡ��һ�в���������
            int bottom = table.getSelectManager().get().getBottom();
            int rowIndex = table.getSelectManager().getActiveRowIndex();
            Object productType = table.getRow(rowIndex).getCell(1).getValue();
            if(value.equals("addNew")){
                Object productConstitutes = table.getRow(rowIndex).getCell(0).getValue();
        		if(productConstitutes.equals(PlanIndexTypeEnum.house)){
                	if(table.getSelectManager().getActiveColumnIndex() > 0){
                		FDCMsgBox.showInfo("סլ�����б����ѡ��һ��!");
                		SysUtil.abort();
                	}
                }
            }
    		if(productType!=null){//��Ʒ���Ͳ�Ϊ��
    			productType = table.getRow(rowIndex).getCell(1).getValue().toString();
    			if(HJ_NAME.equals(productType)){//��Ʒ���͵ĺϼ���
        			row = table.addRow(top+1);
    				PlanIndexTypeEnum productConstitute = (PlanIndexTypeEnum)table.getRow(top-1).getCell(0).getValue();    
	                row.getCell(0).setValue(productConstitute);
    			}else{
    				if(table.getRow(rowIndex).getCell(0).getValue().equals(PlanIndexTypeEnum.house) && value.equals("addNew")){
        				row = table.addRow(bottom);
        			}else{
        				row = table.addRow(top+1);
        			}
    				PlanIndexTypeEnum productConstitute = (PlanIndexTypeEnum)table.getRow(top).getCell(0).getValue();    
	                row.getCell(0).setValue(productConstitute);
    			}
    		}
    		else if(productType==null){//��Ʒ����Ϊ��
    			//wyh �����Ʒ������סլ���ںϼ���������һ�м�¼
    			if(table.getRow(rowIndex).getCell(0).getValue().equals(PlanIndexTypeEnum.house) && value.equals("addNew")){
    				row = table.addRow(bottom);
    			}else{
    				row = table.addRow(top+1);
    			}
	            PlanIndexTypeEnum productConstitute = (PlanIndexTypeEnum)table.getRow(top).getCell(0).getValue();    
	            row.getCell(0).setValue(productConstitute);
    		}
        } else
        {
        	MsgBox.showInfo("��ѡ��ĳһ��Ʒ���������У�");
			SysUtil.abort();
        }
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("creator.*"));
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("lastUpdateUser.*"));
		sic.add(new SelectorItemInfo("lastUpdateTime"));
		sic.add(new SelectorItemInfo("auditor.*"));
		sic.add(new SelectorItemInfo("orgUnit.*"));
		sic.add(new SelectorItemInfo("auditTime"));
		sic.add(new SelectorItemInfo("measureType"));
		sic.add(new SelectorItemInfo("version"));
		sic.add(new SelectorItemInfo("versionName"));
		sic.add(new SelectorItemInfo("sellProject.*"));
		sic.add(new SelectorItemInfo("measureStage.*"));
		sic.add(new SelectorItemInfo("totalAmount.*"));
		sic.add(new SelectorItemInfo("remark"));
		sic.add(new SelectorItemInfo("year"));
		sic.add(new SelectorItemInfo("cycle"));
		sic.add(new SelectorItemInfo("editYear"));
		sic.add(new SelectorItemInfo("totalAmount"));
		sic.add(new SelectorItemInfo("entrys.*"));
		sic.add(new SelectorItemInfo("entrys.productType.*"));
		sic.add(new SelectorItemInfo("entrys.newAreaRange.*"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("versionType"));
		sic.add(new SelectorItemInfo("number"));
		sic.add("CU.*");
		return sic;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ValueBreakFactory.getRemoteInstance();
	}

	/**
	 * ������һ�汾
	 * */
	public String setNestVersion(String version,String sellProjectId){
//		if(version!=null){
//			int index = version.indexOf(".");
//			int v = Integer.parseInt(version.substring(index+1))+1;
//			version = version.substring(0, index+1)+v;
//		}else{
//			version = "V1.0";
//		}
		version = "1.0";
		try {
			ValueBreakCollection collection = ValueBreakFactory.getRemoteInstance().getValueBreakCollection("where state = '4AUDITTED' and sellProject.id = '"+sellProjectId+"'");
			version = "V" + String.valueOf(collection.size()+1) + ".0";
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return version;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		initUiState();
	}
}