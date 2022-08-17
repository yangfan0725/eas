/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.BasicView;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLabel;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.LimitedLengthDocument;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.CostEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostEntryFactory;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.aimcost.MeasureCostCollection;
import com.kingdee.eas.fdc.aimcost.MeasureCostFactory;
import com.kingdee.eas.fdc.aimcost.MeasureCostInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexCollection;
import com.kingdee.eas.fdc.aimcost.PlanIndexFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.client.ContractTypePromptSelector;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.basedata.util.KDDetailedAreaDialog;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.programming.IProgramming;
import com.kingdee.eas.fdc.contract.programming.ProgrammingCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingException;
import com.kingdee.eas.fdc.contract.programming.ProgrammingFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingInfo;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.util.StringUtility;
import com.kingdee.eas.mm.control.client.TableCellComparator;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.StringUtils;
import com.kingdee.util.Uuid;

/**
 * output class name
 */
public class ProgrammingEditUI extends AbstractProgrammingEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProgrammingEditUI.class);
    private CreateProTableRow create = new CreateProTableRow(dataBinder);//��¼��������
	private DataChangeListener dataChangeListener = null;
	private EntryTreeSumField sumClass = new EntryTreeSumField();
    private final String LONGNUMBER = "longNumber";//������
    private final String HEADNUMBER = "headNumber";//����������
    private final String BALANCE = "balance";
	private final String AMOUNT = "amount";
	private final String CONTROLAMOUNT = "controlAmount";
    protected KDWorkButton btnAddnewLine;
    protected KDWorkButton btnInsertLines;
    protected KDWorkButton btnRemoveLines;
    protected KDWorkButton btnDetails;
    protected BigDecimal totalBuildArea;
    
	public ProgrammingEditUI() throws Exception {
		super();
	}
    public void onLoad() throws Exception {
    	this.kdtCompareEntry.checkParsed();
    	this.kdtVerCompareEntry.checkParsed();
    	this.kdtVerCompareEntry.setVisible(false);
		this.kdtCompareEntry.getColumn("programmingContract").getStyleAttributes().setLocked(true);
		this.kdtCompareEntry.getColumn("content").getStyleAttributes().setLocked(true);
		this.kdtCompareEntry.getColumn("programmingContract").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
		this.kdtCompareEntry.getColumn("content").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
		
    	txtAllAimCost.setEnabled(false);
		txtBuildArea.setEnabled(false);
		txtSaleArea.setEnabled(false);
    	super.onLoad();
    	txtVersion.setPrecision(1);
		initTable();
		setAttachmentRenderer();
    	setSmallButton();
    	setSmallBtnEnable();
    	initData();
    	setAimCostFilter();
    	setMouseClick();

		if (this.getUIContext().get("modify") != null) {
			// �޶�����¸���Լ����ID
			ProgrammingContractInfo programmingContractInfo = new ProgrammingContractInfo();
			for (int i = 0; i < kdtEntries.getRowCount(); i++) {
				String longNumber=kdtEntries.getCell(i, "longNumber").getValue().toString();
				ProgrammingContractInfo proConInfo = (ProgrammingContractInfo) kdtEntries.getRow(i).getUserObject();
				BOSUuid newid=BOSUuid.create(programmingContractInfo.getBOSType());
				kdtEntries.getCell(i, "id").setValue(newid);
				kdtEntries.getCell(i, "isCiting").setValue(Boolean.valueOf(proConInfo.isIsCiting()));
				kdtEntries.getCell(i, "isWTCiting").setValue(Boolean.valueOf(proConInfo.isIsWTCiting()));
				for(int j=0;j<kdtEntries.getRowCount();j++){
					ProgrammingContractInfo parent=((ProgrammingContractInfo)kdtEntries.getRow(j).getUserObject()).getParent();
					if(parent!=null&&parent.getLongNumber().equals(longNumber)){
						((ProgrammingContractInfo)kdtEntries.getRow(j).getUserObject()).setParent(proConInfo);
					}
				}
				
			}
			this.actionImport.setEnabled(false);
			
			this.txtDescription.setText(null);
			this.kdtCompareEntry.removeRows();
		}
		KDWorkButton compare=new KDWorkButton();
		this.actionCompare.putValue("SmallIcon", EASResource.getIcon("imgTbtn_input"));
		compare = (KDWorkButton)this.conCompare.add(this.actionCompare);
		compare.setText("��ȡ");
		compare.setSize(new Dimension(140, 19));
		
//		KDWorkButton addLine=new KDWorkButton();
//		this.actionComAddRow.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
//		addLine = (KDWorkButton)this.conCompare.add(this.actionComAddRow);
//		addLine.setText("������");
//		addLine.setSize(new Dimension(140, 19));
//		
//		KDWorkButton insertLine=new KDWorkButton();
//		this.actionComInsertRow.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
//		insertLine = (KDWorkButton)this.conCompare.add(this.actionComInsertRow);
//		insertLine.setText("������");
//		insertLine.setSize(new Dimension(140, 19));
//		
//		KDWorkButton removeLine=new KDWorkButton();
//		this.actionComRemoveRow.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
//		removeLine = (KDWorkButton)this.conCompare.add(this.actionComRemoveRow);
//		removeLine.setText("ɾ����");
//		removeLine.setSize(new Dimension(140, 19));
		
		if(this.txtVersion.getBigDecimalValue()!=null&&this.txtVersion.getBigDecimalValue().compareTo(new BigDecimal(1))==0){
			this.kDTabbedPane1.remove(this.conCompare);
			this.kDTabbedPane1.remove(this.kDScrollPane1);
		}
		
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setHgap(10);
		colorPanel.setLayout(flowLayout);
		drawALogo("����",new Color(248,171,166));
		drawALogo("����",new Color(163,207,98));
		
		KDWorkButton viewAmount=(KDWorkButton)this.conProgramming.add(this.actionViewAmount);
		viewAmount.setAction((IItemAction)ActionProxyFactory.getProxy(this.actionViewAmount, new Class[] { IItemAction.class }, getServiceContext()));
		this.actionViewAmount.putValue("SmallIcon", EASResource.getIcon("imgTbtn_view"));
		viewAmount.setText("��ʾ�滮���");
		viewAmount.setSize(new Dimension(140, 19));
		
		if (!STATUS_ADDNEW.equals(this.oprtState)&&!STATUS_EDIT.equals(this.oprtState)) {
			this.kdtEntries.getColumn("amount").getStyleAttributes().setHided(true);
			this.kdtEntries.getColumn("balance").getStyleAttributes().setHided(true);
			
			this.kdtCostAccount.checkParsed();
			this.kdtCostAccount.getColumn("aimCost").getStyleAttributes().setHided(true);
			this.kdtCostAccount.getColumn("assigned").getStyleAttributes().setHided(true);
			this.kdtCostAccount.getColumn("assigning").getStyleAttributes().setHided(true);
		}
		
		this.kdtCompareEntry.getColumn("reason").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.txtDescription.setRequired(true);
		
		boolean isPCInvite=false;
		HashMap hmParamIn = new HashMap();
		hmParamIn.put("FDC_ISPCINVITEDATE", null);
		try {
			HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
			if(hmAllParam.get("FDC_ISPCINVITEDATE")!=null){
				isPCInvite=Boolean.parseBoolean(hmAllParam.get("FDC_ISPCINVITEDATE").toString());
			}else{
				isPCInvite=false;
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		this.kdtEntries.getColumn("paperDate").getStyleAttributes().setHided(!isPCInvite);
		this.kdtEntries.getColumn("documentsAuditDate").getStyleAttributes().setHided(!isPCInvite);
		this.kdtEntries.getColumn("resultAuditDate").getStyleAttributes().setHided(!isPCInvite);
		this.kdtEntries.getColumn("contractAuditDate").getStyleAttributes().setHided(!isPCInvite);
		this.kdtEntries.getColumn("enterAuditDate").getStyleAttributes().setHided(!isPCInvite);
		
		
		this.kdtEntries.getColumn("balance").getStyleAttributes().setHided(true);
    }
    protected void drawALogo(String name, Color color) {
		KDLabel lable = new KDLabel(name);
		KDLabel colorLable = new KDLabel();
		Dimension d = new Dimension(40, 10);
		colorLable.setPreferredSize(d);
		colorLable.setOpaque(true);
		colorLable.setBackground(color);
		colorPanel.add(lable);
		colorPanel.add(colorLable);

	}
    protected void initListener() {
    	super.initListener();
    	dataChangeListener = new DataChangeListener() {
    		public void dataChanged(DataChangeEvent e) {
    			dataChangeMethod(e);
    		}
    	};
    	prmtAimCost.addDataChangeListener(dataChangeListener);
    }
    
    /**
     * Ŀ��ɱ������¼�
     * @param e
     */
	private void dataChangeMethod(DataChangeEvent e) {
		AimCostInfo oldInfo = (AimCostInfo) e.getOldValue();
		AimCostInfo newInfo = (AimCostInfo) e.getNewValue();
		if (oldInfo != null) {
			if (newInfo == null) { // ��Ŀ��ɱ�
				if (kdtEntries.getRowCount() > 0) {
					int i = FDCMsgBox.showConfirm2("�Ƿ����Ŀ��ɱ�,����󽫻��֮ǰ���滮�Ľ����Ϊ�㣡");
					if (FDCMsgBox.OK == i) {
						clearAimCost();
						txtAimCoustVersion.setText(null);
					}
					if (FDCMsgBox.CANCEL == i) {
						prmtAimCost.removeDataChangeListener(dataChangeListener);
						prmtAimCost.setValue(oldInfo);
						prmtAimCost.addDataChangeListener(dataChangeListener);
					}
				}
				try {
					setBuildAreaAndSellArea();
				} catch (BOSException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} else {
				if (kdtEntries.getRowCount() > 0) {
					if (oldInfo.getId().toString().equals(newInfo.getId().toString())) {
						return;
					} else {// ��Ŀ��ɱ�
						kdtEntries.getEditManager().editingStopped();
						int i = FDCMsgBox.showConfirm2("�Ƿ����Ŀ��ɱ������ĺ󽫻����֮ǰ���滮�Ľ�");
						if (FDCMsgBox.OK == i) {
							changeAimCost(newInfo);
							try {
								setBuildAreaAndSellArea();
							} catch (BOSException e1) {
								e1.printStackTrace();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
						if (FDCMsgBox.CANCEL == i) {
							prmtAimCost.removeDataChangeListener(dataChangeListener);
							prmtAimCost.setValue(oldInfo);
							prmtAimCost.addDataChangeListener(dataChangeListener);
						}
					}
				}
			}
		} else {
				int i = FDCMsgBox.showConfirm2("�Ƿ����Ŀ��ɱ������ĺ󽫻����֮ǰ���滮�Ľ�");
				if (FDCMsgBox.OK == i) {
					changeAimCost(newInfo);
					try {
						setBuildAreaAndSellArea();
					} catch (BOSException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				if (FDCMsgBox.CANCEL == i) {
					prmtAimCost.removeDataChangeListener(dataChangeListener);
					prmtAimCost.setValue(oldInfo);
					prmtAimCost.addDataChangeListener(dataChangeListener);
				}
		}
		
		if(e.getNewValue() != null){
			txtAimCoustVersion.setText(((AimCostInfo)e.getNewValue()).getVersionNumber());
			String id = ((AimCostInfo) e.getNewValue()).getId().toString();
			getCostAmount(id);
		}else{
			txtAimCoustVersion.setText(null);
			txtAllAimCost.setValue(null);
		}
	}

	/**
	 * ��ȡĿ��ɱ����ܽ��
	 * 
	 * @param id
	 */
	private void getCostAmount(String id) {
		StringBuffer sql = new StringBuffer("select sum(fcostamount) as amount from T_AIM_CostEntry where fheadid ='" + id + "'");
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(sql.toString());
		IRowSet rs;
		try {
			rs = sqlBuilder.executeQuery();

			if (rs.next()) {
				txtAllAimCost.setValue(rs.getBigDecimal("amount"));
			}
		} catch (BOSException e1) {
			logger.error(e1);
		} catch (SQLException e2) {
			logger.error(e2);
		}
	}

	/**
	 * ��Ҫ���ĵ�ֵ���£�
	 * 
	 * ����������Ŀ��ɱ��汾�����
	 * 
	 * �ɱ����� :Ŀ��ɱ����ѷ��䣬�����䣬����Լ���� ��0
	 * 
	 * �滮��Լ�����0,���ƽ����0,�滮�����0�����������0
	 * 
	 * ���������������0
	 */
	private void clearAimCost() {
		txtAimCoustVersion.setText(null);
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			ProgrammingContractInfo programmingContractInfo = (ProgrammingContractInfo) kdtEntries.getRow(i).getUserObject();
			ProgrammingContracCostCollection costEntries = programmingContractInfo.getCostEntries();
			ProgrammingContractEconomyCollection economyEntries = programmingContractInfo.getEconomyEntries();
			// �ɱ�����
			for (int j = 0; j < costEntries.size(); j++) {
				ProgrammingContracCostInfo pccInfo = costEntries.get(j);
				// Ŀ��ɱ����ѷ��䣬�����䣬����Լ���� ��0
				pccInfo.setGoalCost(FDCHelper.ZERO);
				pccInfo.setAssigned(FDCHelper.ZERO);
				pccInfo.setAssigning(FDCHelper.ZERO);
				pccInfo.setContractAssign(FDCHelper.ZERO);
			}
			// �滮��Լ�����0,���ƽ����0,�滮�����0�����������0,Ԥ�������0
		
			programmingContractInfo.setControlAmount(FDCHelper.ZERO);
			programmingContractInfo.setBalance(programmingContractInfo.getBalance().subtract(programmingContractInfo.getAmount()));
			programmingContractInfo.setControlBalance(FDCHelper.ZERO);
			programmingContractInfo.setAmount(FDCHelper.ZERO);
			// ��������
			for (int k = 0; k < economyEntries.size(); k++) {
				ProgrammingContractEconomyInfo pceInfo = economyEntries.get(k);
				// ��������0
				pceInfo.setAmount(FDCHelper.ZERO);
			}
			dataBinder.loadLineFields(kdtEntries, kdtEntries.getRow(i), programmingContractInfo);
			kdtEntries.getRow(i).getStyleAttributes().setFontColor(Color.BLACK);
		}
	}

	/**
	 * �ı�Ŀ��ɱ����Ǽ�Ŀ��ɱ�
	 * 
	 * ������
	 * 
	 * 1.���¸��ɱ�������"Ŀ��ɱ�"��"������"��ֵ
	 * 
	 * ��"Ŀ��ɱ�"�� ����ͨ���¹�����"Ŀ��ɱ�"�͸��ɱ�������"������Ŀ"��"�ɱ���Ŀ"��Ϊ�������˳��µ�
	 * 
	 * ��"������":��"Ŀ��ɱ�"-��"�ѷ���"
	 * 
	 * 2.���º󣬰ѳɱ����д��и�"������"ֵ����Ӧ�ĺ�Լ�ڿ�ܽ����¼���ú�ɫ��־�����������ú�ɫ���壩
	 * 
	 * 3.��¼���к�ɫ��־����ʱ���棬�ύ��ť��ʾ�����ɱ��桭��������,�����ɱ��治���ύ
	 * 
	 * ��Ҫ���ĵ�ֵ���£�
	 * 
	 * ����������Ŀ��ɱ��汾��
	 * 
	 * �ɱ����� :Ŀ��ɱ�����������
	 * 
	 * �����������ݣ�2011.04.13����
	 * 
	 * �� ԭ"����Լ����" = ԭ"Ŀ��ɱ�" ��̬���� "����Լ����" = ��"Ŀ��ɱ�"��
	 * 
	 */
    private void changeAimCost(AimCostInfo aimCost) {
		kdtEntries.getEditManager().editingStopped();
		Map isAssignAimCost=new HashMap();
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			ProgrammingContractInfo programmingContractInfo = (ProgrammingContractInfo) kdtEntries.getRow(i).getUserObject();
			programmingContractInfo.setLongNumber((String) kdtEntries.getCell(i, LONGNUMBER).getValue());
			programmingContractInfo.setName((String) kdtEntries.getCell(i, "name").getValue());
			programmingContractInfo.setAmount(FDCHelper.toBigDecimal(kdtEntries.getCell(i, "amount").getValue()));
			programmingContractInfo.setControlAmount(FDCHelper.toBigDecimal(kdtEntries.getCell(i, "controlAmount").getValue()));
			programmingContractInfo.setControlBalance(FDCHelper.toBigDecimal(kdtEntries.getCell(i, "controlBalance").getValue()));
			ProgrammingContracCostCollection costEntries = programmingContractInfo.getCostEntries();
			ProgrammingContractEconomyCollection economyEntries = programmingContractInfo.getEconomyEntries();

			BigDecimal newAmount = programmingContractInfo.getAmount();// �滮���
//			BigDecimal oldAmount = programmingContractInfo.getAmount();
//			BigDecimal oldBalance = programmingContractInfo.getBalance();// ԭ�滮���

			if (costEntries.size() == 0) {
				newAmount = programmingContractInfo.getAmount();// ԭ�滮���
			} else {
				int flagAllNoChange = 0;
				for (int j = 0; j < costEntries.size(); j++) {
					ProgrammingContracCostInfo pccInfo = costEntries.get(j);
					CostAccountInfo costAccount = pccInfo.getCostAccount();// �ɱ���Ŀ
//					// ��ȡԭ"�ѷ���"��ԭ"Ŀ��ɱ�","����Լ����"
//					BigDecimal oldAssigned = pccInfo.getAssigned();
//					BigDecimal oldGoalCost = pccInfo.getGoalCost();
//					BigDecimal oldContractAssign = pccInfo.getContractAssign();
//					// ��ȡ��"Ŀ��ɱ�"
//					BigDecimal newGoalCost = FDCHelper.ZERO;
					BigDecimal aimcostAccount=ProgrammingContractUtil.getGoalCostBy_costAcc_aimCost(costAccount, aimCost);
//					if(isAssignAimCost.get(aimCost.getId().toString())!=null){
//						Set isAssignCostAccount=(HashSet)isAssignAimCost.get(aimCost.getId().toString());
//						if(!isAssignCostAccount.contains(costAccount.getId().toString())){
//							newGoalCost=aimcostAccount;
//							isAssignCostAccount.add(costAccount.getId().toString());
//						}
//					}else{
//						newGoalCost=aimcostAccount;
//						Set isAssignCostAccount=new HashSet();
//						isAssignCostAccount.add(costAccount.getId().toString());
//						isAssignAimCost.put(aimCost.getId().toString(), isAssignCostAccount);
//					}
//					if (oldGoalCost.compareTo(oldContractAssign) == 0) {
//						// ��ԭ"Ŀ��ɱ�"=ԭ"����Լ����"������"����Լ����" = ��"Ŀ��ɱ�"
//						pccInfo.setContractAssign(newGoalCost);
//						// ��� ��"������" = ��"Ŀ��ɱ�"
//						pccInfo.setAssigning(newGoalCost);
//						newAmount = newAmount.add(newGoalCost);
//					} else {
//						flagAllNoChange++;
//						// ��ԭ"Ŀ��ɱ�"!=ԭ"����Լ����"������"����Լ����" ����
//						// ��� ��"������" = ��"Ŀ��ɱ�" - ԭ"�ѷ���"
//						BigDecimal newAssigning = newGoalCost.subtract(oldAssigned);
//						pccInfo.setAssigning(newAssigning);
//						newAmount = newAmount.add(pccInfo.getContractAssign());
//						if (costEntries.size() == flagAllNoChange) {
//							newAmount = oldAmount;
//						}
//					}
					pccInfo.setGoalCost(aimcostAccount);
				}
				// �滮��̬����
//				programmingContractInfo.setAmount(newAmount);
				// ���ƽ�� = �滮���
//				programmingContractInfo.setControlAmount(newAmount);
				// �滮��̬����
				
//				programmingContractInfo.setBalance(oldBalance.add(newAmount.subtract(oldAmount)));
				// ��������"������"��̬����
//				for (int k = 0; k < economyEntries.size(); k++) {
//					ProgrammingContractEconomyInfo pceInfo = economyEntries.get(k);
//					BigDecimal scale = pceInfo.getScale();
//					pceInfo.setAmount(FDCHelper.divide(newAmount.multiply(scale), FDCHelper.ONE_HUNDRED));
//				}
				dataBinder.loadLineFields(kdtEntries, kdtEntries.getRow(i), programmingContractInfo);
//				int level = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
//				if (level != 1) {
//					// ����
//					caclTotalAmount(i, kdtEntries.getColumnIndex("amount"), level);
//					caclTotalAmount(i, kdtEntries.getColumnIndex("balance"), level);
//				}
				setMyFontColor();
			}
		}
    }
	/**
	 * ���ں�ͬ���ù�ϵ����Ŀ��ɱ�f7�ؼ��û�
	 * 
	 * @return
	 */
	private boolean isCiting() {
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			Object value = kdtEntries.getCell(i, "isCiting").getValue();
			if (value instanceof Boolean) {
				Boolean b = (Boolean) value;
				if (b.booleanValue()) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * ���㽨�����������۵���
	 */
	private void calcSquare(int index) {
		BigDecimal totalBuildArea = txtBuildArea.getBigDecimalValue();
		BigDecimal sellArea = txtSaleArea.getBigDecimalValue();

		if (index == 0) {
			int size = this.kdtEntries.getRowCount();
			for (int i = 0; i < size; i++) {
				BigDecimal amount = FDCHelper.toBigDecimal(kdtEntries.getCell(i, "amount").getValue());
				if (amount.compareTo(FDCHelper.ZERO) != 0) {
					if (totalBuildArea != null && totalBuildArea.compareTo(FDCHelper.ZERO) != 0) {
						kdtEntries.getCell(i, "buildPerSquare").setValue(amount.divide(totalBuildArea, 4, BigDecimal.ROUND_HALF_UP));
					}
					if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
						kdtEntries.getCell(i, "soldPerSquare").setValue(amount.divide(sellArea, 4, BigDecimal.ROUND_HALF_UP));
					}
				} else {
					kdtEntries.getCell(i, "buildPerSquare").setValue(FDCHelper.ZERO);
					kdtEntries.getCell(i, "soldPerSquare").setValue(FDCHelper.ZERO);
				}
			}
		} else {
			BigDecimal amount = FDCHelper.toBigDecimal(kdtEntries.getCell(index, "amount").getValue());
			if (amount.compareTo(FDCHelper.ZERO) != 0) {
				if (totalBuildArea != null && totalBuildArea.compareTo(FDCHelper.ZERO) != 0) {
					kdtEntries.getCell(index, "buildPerSquare").setValue(amount.divide(totalBuildArea, 4, BigDecimal.ROUND_HALF_UP));
				}
				if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
					kdtEntries.getCell(index, "soldPerSquare").setValue(amount.divide(sellArea, 4, BigDecimal.ROUND_HALF_UP));
				}
			} else {
				kdtEntries.getCell(index, "buildPerSquare").setValue(FDCHelper.ZERO);
				kdtEntries.getCell(index, "soldPerSquare").setValue(FDCHelper.ZERO);
			}
		}
	}
    
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (STATUS_ADDNEW.equals(this.oprtState)) {
			this.actionCompare.setEnabled(true);
			this.actionComAddRow.setEnabled(true);
			this.actionComInsertRow.setEnabled(true);
			this.actionComRemoveRow.setEnabled(true);
			this.actionViewAmount.setEnabled(false);
			
			this.kdtEntries.checkParsed();
			this.kdtEntries.getColumn("amount").getStyleAttributes().setHided(false);
			
			this.kdtCostAccount.checkParsed();
			this.kdtCostAccount.getColumn("aimCost").getStyleAttributes().setHided(false);
			this.kdtCostAccount.getColumn("assigned").getStyleAttributes().setHided(false);
			this.kdtCostAccount.getColumn("assigning").getStyleAttributes().setHided(false);
		} else if (STATUS_EDIT.equals(this.oprtState)) {
			this.actionCompare.setEnabled(true);
			this.actionComAddRow.setEnabled(true);
			this.actionComInsertRow.setEnabled(true);
			this.actionComRemoveRow.setEnabled(true);
			changeActoinState(false);
			actionImport.setEnabled(true);
			this.actionViewAmount.setEnabled(false);
			
			this.kdtEntries.checkParsed();
			this.kdtEntries.getColumn("amount").getStyleAttributes().setHided(false);
			
			this.kdtCostAccount.checkParsed();
			this.kdtCostAccount.getColumn("aimCost").getStyleAttributes().setHided(false);
			this.kdtCostAccount.getColumn("assigned").getStyleAttributes().setHided(false);
			this.kdtCostAccount.getColumn("assigning").getStyleAttributes().setHided(false);
		} else if (STATUS_VIEW.equals(this.oprtState)) {
			this.actionCompare.setEnabled(false);
			this.actionComAddRow.setEnabled(false);
			this.actionComInsertRow.setEnabled(false);
			this.actionComRemoveRow.setEnabled(false);
			changeActoinState(false);
			this.actionViewAmount.setEnabled(true);
			
			this.kdtEntries.checkParsed();
			this.kdtEntries.getColumn("amount").getStyleAttributes().setHided(true);
			
			this.kdtCostAccount.checkParsed();
			this.kdtCostAccount.getColumn("aimCost").getStyleAttributes().setHided(true);
			this.kdtCostAccount.getColumn("assigned").getStyleAttributes().setHided(true);
			this.kdtCostAccount.getColumn("assigning").getStyleAttributes().setHided(true);
		} else if (STATUS_FINDVIEW.equals(this.oprtState)) {
			this.actionCompare.setEnabled(false);
			this.actionComAddRow.setEnabled(false);
			this.actionComInsertRow.setEnabled(false);
			this.actionComRemoveRow.setEnabled(false);
			this.actionViewAmount.setEnabled(true);
			
			this.kdtEntries.checkParsed();
			this.kdtEntries.getColumn("amount").getStyleAttributes().setHided(true);
			
			this.kdtCostAccount.checkParsed();
			this.kdtCostAccount.getColumn("aimCost").getStyleAttributes().setHided(true);
			this.kdtCostAccount.getColumn("assigned").getStyleAttributes().setHided(true);
			this.kdtCostAccount.getColumn("assigning").getStyleAttributes().setHided(true);
		}
	}
	/**
	 * ���ø�������ʾ��ʽ
	 */
	private void setAttachmentRenderer() {
		ObjectValueRender objectValueRender = new ObjectValueRender();
		objectValueRender.setFormat(new IDataFormat() {
			public String format(Object o) {
				if (o != null) {
					return "�鿴";
				}
				return null;
			}
		});
		this.kdtEntries.getColumn("attachment").setRenderer(objectValueRender);
	}
	//ǰһ�汾��ܺ�ԼID
	private List oldProgId = new ArrayList();
	private void initData() throws Exception {
		if (isBillModify()) {
			for(Iterator it = editData.getEntries().iterator() ; it.hasNext();){
				oldProgId.add(((ProgrammingContractInfo)it.next()).getId());
			}
			actionCopy_actionPerformed(null);
			int cou = 0;
			for(Iterator it = editData.getEntries().iterator(); it.hasNext();){
				ProgrammingContractInfo entry=(ProgrammingContractInfo)it.next();
				entry.setSrcId(oldProgId.get(cou).toString());
				cou++;
			}
			createTree();
		}
		this.btnExport.setEnabled(true);
		Object node = getUIContext().get("treeSelectedObj");
    	if(node != null)
    		curProject = (CurProjectInfo)node;
		
		if(editData.getProject() == null){
			if(curProject != null){
				editData.setProject(curProject);
				txtProjectName.setText(curProject.getName());
			}
		}
		
    }
	
	/**
	 * ���ý�������Ϳ������
	 * @param measureCostInfo
	 * @throws BOSException
	 * @throws SQLException
	 */
	private void setBuildAreaAndSellArea() throws BOSException, SQLException{
		if(curProject ==null){
			txtBuildArea.setValue(null);
			txtSaleArea.setValue(null);
			return;
		}
		BigDecimal buildArea = FDCHelper.getApportionValue(curProject.getId().toString(),ApportionTypeInfo.buildAreaType, ProjectStageEnum.AIMCOST);
		this.txtBuildArea.setText(buildArea.toString());
		BigDecimal sellArea = FDCHelper.getApportionValue(curProject.getId().toString(),ApportionTypeInfo.sellAreaType, ProjectStageEnum.AIMCOST);
		this.txtSaleArea.setText(sellArea.toString());
//		StringBuffer sql = new StringBuffer("select FID from T_AIM_MeasureCost where fprojectid ");
//		sql.append(" = '" + editData.getProject().getId().toString() + "' and fstate = '4AUDITTED' ");
//		sql.append(" order BY FMAINVERNO desc , FSUBVERNO desc");
//		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(sql.toString());
//		IRowSet rs = sqlBuilder.executeQuery();
//		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
////		if (rs.next()) {
//			String measureCostID = measureCostInfo.getId().toString();
//			if (measureCostID != null && measureCostID.length() > 1) {
//				StringBuffer sql_2 = new StringBuffer("select a.FTotalBuildArea , isnull(b.FSellArea ,0) as FSellArea from T_AIM_PlanIndex as a ");
//				sql_2.append(" left outer join T_AIM_PlanIndexEntry as b on a.FID = b.FParentID ");
//				sql_2.append(" where a.FHEADID = '" + measureCostID + "'");
//				sqlBuilder = new FDCSQLBuilder(sql_2.toString());
//				IRowSet rs_2 = sqlBuilder.executeQuery();
//				BigDecimal totalBuildArea = FDCHelper.ZERO;
//				BigDecimal sellArea = FDCHelper.ZERO;
//				while (rs_2.next()) {
//					totalBuildArea = rs_2.getBigDecimal("FTotalBuildArea");
//					sellArea = sellArea.add(rs_2.getBigDecimal("FSellArea"));
//				}
//				txtBuildArea.setValue(totalBuildArea);
//				txtSaleArea.setValue(sellArea);
//			}
//		}
	}
    
	//�޶�ʱ�ÿ��ֶ�ֵ
    protected void setFieldsNull(AbstractObjectValue newData) {
    	ProgrammingInfo info = (ProgrammingInfo) newData;
    	String number = getDateString();
    	info.setNumber(number);
    	txtNumber.setText(number);
    	inputVersion(info);
    	txtVersion.setText(info.getVersion().toString());
    	info.setState(FDCBillStateEnum.SAVED);
    }
	
    /**
     * ���÷�¼ʧȥ���㼰��¼��ť�Ƿ����
     */
	private void setKDTableLostFocus(){
		kdtEntries.getEditManager().stopEditing();
		kdtEntries.getSelectManager().remove();
		kdtEntries.getSelectManager().setActiveRowIndex(-1);
		btnAddnewLine.setEnabled(false);
		btnRemoveLines.setEnabled(false);
		btnDetails.setEnabled(false);
	}

	/**
	 * Ŀ��ɱ���������
	 */
	private void setAimCostFilter() {
		EntityViewInfo entityView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("orgOrProId",editData.getProject().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("isLastVersion",Boolean.TRUE));
		entityView.setFilter(filter);
		prmtAimCost.setEntityViewInfo(entityView);
		if (prmtAimCost.getValue() != null) {
			txtAimCoustVersion.setText(((AimCostInfo) prmtAimCost.getValue()).getVersionNumber());
		}
	}
    
	/**
	 * ����ʱ�Ա���������
	 */
	private void initTable() {
		kdtEntries.checkParsed();
		kdtEntries.getColumn("id").getStyleAttributes().setLocked(true);
		kdtEntries.getColumn("level").getStyleAttributes().setLocked(true);
		kdtEntries.getColumn("longName").getStyleAttributes().setLocked(true);
		kdtEntries.getColumn(HEADNUMBER).getStyleAttributes().setLocked(true);
		kdtEntries.getColumn("isCiting").getStyleAttributes().setLocked(true);
		kdtEntries.getColumn("isWTCiting").getStyleAttributes().setHided(true);
		// �滮������������ָ�ʽ����λС��
		cellToFormattedText(kdtEntries, "balance");
		cellToFormattedText(kdtEntries, "controlBalance");
		cellToFormattedText(kdtEntries , AMOUNT);
		cellToFormattedText(kdtEntries , CONTROLAMOUNT);
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(new KDTextField());
		KDTextField kdtf = (KDTextField) cellEditor.getComponent();
		kdtf.setMaxLength(1024);
		kdtEntries.getColumn("remark").setEditor(cellEditor);
//		kdtEntries.getColumn("headNumber").getStyleAttributes().setHided(false);
		
		kdtEntries.getColumn(AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn(CONTROLAMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn("balance").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn("controlBalance").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn("signUpAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn("changeAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn("settleAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		kdtEntries.getColumn("buildPerSquare").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn("soldPerSquare").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

//		KDTDefaultCellEditor inviteEditor = new KDTDefaultCellEditor(new KDComboBox());
//		KDComboBox kdCombox = (KDComboBox) inviteEditor.getComponent();
//		for(int i=0;i<InviteFormEnum.getEnumList().size();i++){
//			kdCombox.addItem(InviteFormEnum.getEnumList().get(i));
//		}
//		kdCombox.addItem(InviteFormEnum.PUBLICINVITE);
//		kdCombox.addItem(InviteFormEnum.INVITATORYINVITE);
//		kdCombox.addItem(InviteFormEnum.OTHER);
//		kdCombox.addItem(InviteFormEnum.TENDERDISCUSSION);
//		kdCombox.setSelectedIndex(-1);
//		kdtEntries.getColumn("inviteWay").setEditor(inviteEditor);

		createCostCentertF7();
		
		KDBizPromptBox f7Box = new KDBizPromptBox();
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		f7Box.setSelector(new ContractTypePromptSelector(this));
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$number$");
		f7Box.setCommitFormat("$number$");
		f7Editor = new KDTDefaultCellEditor(f7Box);
		this.kdtEntries.getColumn("contractType").setEditor(f7Editor);
		this.kdtEntries.getColumn("contractType").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		
		KDCheckBox hit = new KDCheckBox();
 		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(hit);
 		this.kdtEntries.getColumn("isInvite").setEditor(editor);
 		if(FDCBillStateEnum.AUDITTED.equals(this.editData.getState())&&this.oprtState.equals(OprtState.VIEW)
 				&&this.editData.isIsLatest()){
 			this.kdtEntries.setEditable(true);
 			this.actionEditInvite.setEnabled(true);
 			for(int i=0;i<this.kdtEntries.getColumnCount();i++){
 				if(this.kdtEntries.getColumnKey(i).equals("isInvite")){
 					this.kdtEntries.getColumn(i).getStyleAttributes().setLocked(false);
 				}else{
 					this.kdtEntries.getColumn(i).getStyleAttributes().setLocked(true);
 				}
 			}
 		}else{
 			this.kdtEntries.getColumn("isInvite").getStyleAttributes().setLocked(true);
 			this.actionEditInvite.setEnabled(false);
 		}
 		kdtEntries.getColumn("buildPrice").getStyleAttributes().setLocked(true);
 		kdtEntries.getColumn("buildPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
 		kdtEntries.getColumn("quantities").getStyleAttributes().setLocked(true);
 		kdtEntries.getColumn("quantities").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
 		kdtEntries.getColumn("unit").getStyleAttributes().setLocked(true);
 		kdtEntries.getColumn("price").getStyleAttributes().setLocked(true);
 		kdtEntries.getColumn("price").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
 		
 		String formatString = "yyyy-MM-dd";
		this.kdtEntries.getColumn("paperDate").getStyleAttributes().setNumberFormat(formatString);
		this.kdtEntries.getColumn("documentsAuditDate").getStyleAttributes().setNumberFormat(formatString);
		this.kdtEntries.getColumn("resultAuditDate").getStyleAttributes().setNumberFormat(formatString);
		this.kdtEntries.getColumn("contractAuditDate").getStyleAttributes().setNumberFormat(formatString);
		this.kdtEntries.getColumn("enterAuditDate").getStyleAttributes().setNumberFormat(formatString);
		
		KDDatePicker pk = new KDDatePicker();
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(pk);
		
 		this.kdtEntries.getColumn("paperDate").setEditor(dateEditor);
		this.kdtEntries.getColumn("documentsAuditDate").setEditor(dateEditor);
		this.kdtEntries.getColumn("resultAuditDate").setEditor(dateEditor);
		this.kdtEntries.getColumn("contractAuditDate").setEditor(dateEditor);
		this.kdtEntries.getColumn("enterAuditDate").setEditor(dateEditor);
	}
	public void actionEditInvite_actionPerformed(ActionEvent e) throws Exception {
		if (MsgBox.showConfirm2("�Ƿ�ȷ���޸ģ�") == MsgBox.CANCEL) {
			return;
		}
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("entries.isInvite"));
		ProgrammingFactory.getRemoteInstance().updatePartial(editData, sic);
	}
	private void cellToFormattedText(KDTable table, String column) {
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(new KDFormattedTextField());
		KDFormattedTextField kdftf = (KDFormattedTextField) cellEditor.getComponent();
		kdftf.setDataType(1);
		kdftf.setPrecision(2);
		kdftf.setSupportedEmpty(true);
		table.getColumn(column).setEditor(cellEditor);
	}

	public void loadFields() {
		super.loadFields();
		//��������ʱ������������
		List rows = kdtEntries.getBody().getRows();
		Collections.sort(rows, new TableCellComparator(kdtEntries.getColumnIndex("sortNumber"), KDTSortManager.SORT_ASCEND));
		kdtEntries.setRefresh(true);
		//��Ԫ�����ģʽ
		kdtEntries.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		kdtEntries.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		if (!OprtState.ADDNEW.equals(getOprtState())) {
			createTree();
			setNameDisplay();
			handleOldData();
			AimCostInfo aimCost = editData.getAimCost();
			if (aimCost != null && aimCost.getId() != null && aimCost.getId().toString().length() > 1)
				getCostAmount(editData.getAimCost().getId().toString());	
		}
		if (!OprtState.VIEW.equals(getOprtState())) {
			setCellEditorForTable();
		}
		setTableFontColor();
		cellAttachment();
		
		setTableRenderer();
		sumClass.caclTotalAmount(kdtEntries);
		setMyFontColor();
		
		for(int i=0;i<this.kdtEntries.getRowCount();i++){
			String compare=(String)this.kdtEntries.getRow(i).getCell("compare").getValue();
			if(compare!=null){
				if(compare.equals("red")){
					this.kdtEntries.getRow(i).getStyleAttributes().setBackground(new Color(248,171,166));
				}else{
					this.kdtEntries.getRow(i).getStyleAttributes().setBackground(new Color(163,207,98));
				}
			}
		}
		try {
			setBuildPrice();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
	private void setBuildPrice() throws BOSException{
		if(OprtState.VIEW.equals(getOprtState())){
			return;
		}
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		if(this.editData.getProject()==null){
			Object node = getUIContext().get("treeSelectedObj");
	    	filter.getFilterItems().add(new FilterItemInfo("project.id",((CurProjectInfo)node).getId().toString()));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("project.id",this.editData.getProject().getId().toString()));
		}
		
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("isLastVersion",Boolean.TRUE));
		view.setFilter(filter);
		MeasureCostCollection mcCol=MeasureCostFactory.getRemoteInstance().getMeasureCostCollection(view);
		if(mcCol.size()>0){
			view=new EntityViewInfo();
			filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("headID",mcCol.get(0).getId().toString()));
			view.setFilter(filter);
			SelectorItemCollection sic=new SelectorItemCollection();
			sic.add("totalBuildArea");
			view.setSelector(sic);
			PlanIndexCollection col=PlanIndexFactory.getRemoteInstance().getPlanIndexCollection(view);
			if(col.size()>0){
				this.totalBuildArea=col.get(0).getTotalBuildArea();
			}
		}
		for(int i=0;i<this.kdtEntries.getRowCount();i++){
			if(i+1 <kdtEntries.getRowCount()){
				Object oldLongNumber = kdtEntries.getCell(i, LONGNUMBER).getValue();
				if(oldLongNumber == null){
					continue;
				}
				Object nextHeadNumber = kdtEntries.getCell(i+1, HEADNUMBER).getValue();
				if(oldLongNumber.equals(nextHeadNumber)){
					continue;
				}
			}
			kdtEntries.getRow(i).getCell("buildPrice").setValue(FDCHelper.divide(kdtEntries.getRow(i).getCell("amount").getValue(), this.totalBuildArea, 2, BigDecimal.ROUND_HALF_UP));
			if((Boolean)kdtEntries.getRow(i).getCell("isInput").getValue()){
		 		kdtEntries.getRow(i).getCell("quantities").getStyleAttributes().setLocked(false);
		 		kdtEntries.getRow(i).getCell("unit").getStyleAttributes().setLocked(false);
		 		kdtEntries.getRow(i).getCell("quantities").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		 		kdtEntries.getRow(i).getCell("unit").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
			}else{
				kdtEntries.getRow(i).getCell("quantities").setValue(null);
				kdtEntries.getRow(i).getCell("unit").setValue(null);
				kdtEntries.getRow(i).getCell("price").setValue(null);
				kdtEntries.getRow(i).getCell("quantities").getStyleAttributes().setBackground(Color.WHITE);
		 		kdtEntries.getRow(i).getCell("unit").getStyleAttributes().setBackground(Color.WHITE);
			}
		}
	}
	
	/**
	 * ���ý���ֶ���ʾ��ʽ
	 */
	private void setTableRenderer() {
		ObjectValueRender objectValueRender = new ObjectValueRender();
		objectValueRender.setFormat(new IDataFormat() {
			public String format(Object o) {
				if (o != null) {
					if("0.00".equals(o.toString())){
						return "0";
					}
				}else{
					return "0";
				}
				return o.toString();
			}
		});
		kdtEntries.getColumn("controlAmount").setRenderer(objectValueRender);
		kdtEntries.getColumn("amount").setRenderer(objectValueRender);
		kdtEntries.getColumn("balance").setRenderer(objectValueRender);
		kdtEntries.getColumn("controlBalance").setRenderer(objectValueRender);
		kdtEntries.getColumn("signUpAmount").setRenderer(objectValueRender);
		kdtEntries.getColumn("changeAmount").setRenderer(objectValueRender);
		kdtEntries.getColumn("settleAmount").setRenderer(objectValueRender);
		kdtEntries.getColumn("buildPerSquare").setRenderer(objectValueRender);
		kdtEntries.getColumn("soldPerSquare").setRenderer(objectValueRender);	
		kdtEntries.getColumn("estimateAmount").setRenderer(objectValueRender);	
	}
	
	private void cellAttachment() {
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			Object idObj = kdtEntries.getCell(i, "id").getValue();
			if (idObj != null) {
				String id = idObj.toString();
				StringBuffer allAttachmentName = getAllAttachmentName(id);
				if (!FDCHelper.isEmpty(allAttachmentName)) {
					kdtEntries.getCell(i, "attachment").setValue("���ڸ���");
				}
			}
		}
	}

	/**
	 * ��ȡ��Լ������и��������ַ������������ֳ���","���
	 * @param boID
	 * @return
	 */
	private StringBuffer getAllAttachmentName(String boID) {
		FDCSQLBuilder fdcBuilder = new FDCSQLBuilder();
		fdcBuilder.appendSql(" select * from T_BAS_Attachment at");
		fdcBuilder.appendSql(" join T_BAS_BoAttchAsso boAt on at.FID=boAt.FAttachmentID");
		fdcBuilder.appendSql(" where boAt.FBoID = '" + boID + "'");
		System.out.println("sql:" + fdcBuilder.getSql().toString());
		StringBuffer sb = new StringBuffer();
		IRowSet rs = null;
		try {
			rs = fdcBuilder.executeQuery();
			while (rs.next()) {
				if (FDCHelper.isEmpty(rs.getString("FSimpleName"))) {
					sb.append(rs.getString("FName_l2") + ";");
				} else {
					if (rs.isLast()) {
						sb.append(rs.getString("FName_l2") + "." + rs.getString("FSimpleName"));
					} else {
						sb.append(rs.getString("FName_l2") + "." + rs.getString("FSimpleName") + ",");
					}
				}
			}
		} catch (BOSException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return sb;
	}
	
	public void onShow() throws Exception {
		setProTableToSumField();
		sumClass.appendProFootRow(null,null);
		
		super.onShow();
		//���ñ�ͷ����
		kdtEntries.addKDTMouseListener(new KDTSortManager(kdtEntries));
		kdtEntries.getSortMange().setSortAuto(false);
		
		setAuditBtnEnable();
		kdtEntries.getFootManager().getFootRow(0).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntries.getColumn("signUpAmount").getStyleAttributes().setHided(true);
		kdtEntries.getColumn("changeAmount").getStyleAttributes().setHided(true);
		kdtEntries.getColumn("settleAmount").getStyleAttributes().setHided(true);
		kdtEntries.getColumn("estimateAmount").getStyleAttributes().setHided(true);
	}
	
	/**
	 * ����������ť��ʾ
	 */
	private void setAuditBtnEnable(){
		if(editData.getState() == null){
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(false);
		}else if(FDCBillStateEnum.SUBMITTED.equals(editData.getState())){
			actionAudit.setEnabled(true);
			actionUnAudit.setEnabled(false);
			actionSave.setEnabled(false);
		}else if(FDCBillStateEnum.AUDITTED.equals(editData.getState())){
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(true);
		}
	}
	
	protected void updateButtonStatus() {
		super.updateButtonStatus();
		if (this.oprtState.equals(STATUS_EDIT)) {
			// boolean lastVersion = ((IProgramming) getBizInterface()).isLastVersion(
			// new ObjectUuidPK(editData.getId()));
			boolean isAudit = FDCBillStateEnum.AUDITTED.equals(editData.getState())
					|| FDCBillStateEnum.AUDITTING.equals(editData.getState());
			if (!isBillModify()) {
				actionAudit.setEnabled(!isAudit);
				actionUnAudit.setEnabled(isAudit);
				actionImport.setEnabled(!isAudit);
			}
		}
	}
	
	private void changeActoinState(boolean flag) {
		actionAudit.setEnabled(flag);
		actionUnAudit.setEnabled(flag);
		actionSubmit.setEnabled(flag);
		actionImport.setEnabled(flag);
	}
	
    /**
     * ����ǩ��������������롢ɾ������ϸ��Ϣ��ť
     */
    private void setSmallButton(){
    	btnImport.setIcon(EASResource.getIcon("imgTbtn_input"));
    	menuItemImport.setIcon(EASResource.getIcon("imgTbtn_input"));
    	btnExport.setIcon(EASResource.getIcon("imgTbtn_output"));
    	menuItemExport.setEnabled(true);
    	menuItemExport.setIcon(EASResource.getIcon("imgTbtn_output"));
    	btnRefresh.setEnabled(true);
    	menuItemRefresh.setEnabled(true);
    	btnRefresh.setIcon(EASResource.getIcon("imgTbtn_refresh"));
    	menuItemRefresh.setIcon(EASResource.getIcon("imgTbtn_refresh"));
    	btnAddnewLine = new KDWorkButton();
    	btnInsertLines = new KDWorkButton();
    	btnRemoveLines = new KDWorkButton();
    	btnDetails = new KDWorkButton();
    	
    	btnDetails.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    actionDetails_actionPerformed(e);
                }
                catch (Exception e1){
                	logger.error("detials" , e1);
                }
            }});
    	
    	btnAddnewLine.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                	actionAddnewLine_actionPerformed(e);
                }
                catch (Exception e1){
                    e1.printStackTrace();
                }
            }});
    	
    	btnInsertLines.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					actionInsertLines_actionPerformed(arg0);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
    		
    	});
    	
    	btnRemoveLines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRemoveLines_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
    	
//    	setButtonStyle(btnAddnewLine,"������","imgTbtn_addline");
//    	setButtonStyle(btnRemoveLines,"ɾ����","imgTbtn_deleteline");
//    	setButtonStyle(btnInsertLines,"������","imgTbtn_insert");
//    	setButtonStyle(btnDetails,"��ϸ��Ϣ","imgTbtn_particular");
    	
    	setButtonStyle(btnInsertLines,"������ܺ�Լ(ͬ��)","imgTbtn_insert");
    	setButtonStyle(btnAddnewLine,"�����¼���ܺ�Լ","imgTbtn_addline");
    	setButtonStyle(btnRemoveLines,"ɾ����","imgTbtn_deleteline");
    	setButtonStyle(btnDetails,"��ϸ��Ϣ","imgTbtn_particular");
    }
    
    private void setButtionEnable(boolean isEnable){
    	btnAddnewLine.setEnabled(isEnable);
		btnInsertLines.setEnabled(isEnable);
		btnRemoveLines.setEnabled(isEnable);
		btnDetails.setEnabled(isEnable);
    }
    
    //���ð�ť��ʾЧ��
    private void setButtonStyle(KDWorkButton button , String text , String icon){
    	button.setText(text);
    	button.setToolTipText(text);
    	button.setVisible(true);
    	button.setIcon(EASResource.getIcon(icon));
    	conProgramming.addButton(button);
    }
    
    //����������ɾ���������¼��ť�Ƿ����
    private void setSmallBtnEnable(){
    	if(OprtState.VIEW.equals(getOprtState())){
    		setButtionEnable(false);
    		if(kdtEntries.getSelectManager().getActiveRowIndex() < 0 || kdtEntries.getRowCount() <= 0){
    			btnDetails.setEnabled(false);
    		}else{
    			btnDetails.setEnabled(true);
    		}
    	}else{
    		btnInsertLines.setEnabled(true);
    		if(kdtEntries.getSelectManager().getActiveRowIndex() < 0 || kdtEntries.getRowCount() <= 0){
    			btnAddnewLine.setEnabled(false);
    			btnRemoveLines.setEnabled(false);
    			btnDetails.setEnabled(false);
    		}else{
    			btnAddnewLine.setEnabled(true);
    			btnRemoveLines.setEnabled(true);
    			btnDetails.setEnabled(true);
    		}
    	}
    }
    
    
    Map bkMap = new HashMap();
    /**
     * 
     * �޸�Ϊ�����¼���ܺ�Լ
     * @param e
     * @throws Exception
     */
    public void actionAddnewLine_actionPerformed(ActionEvent e) throws Exception{
    	//У��Ŀ��ɱ��汾�ű���
    	checkAimCostNotNull();
    	
    	int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
    	int rowCount = kdtEntries.getRowCount();
    	
    	int row = -1;
    	if(rowIndex < 0){
    		//�±�Ϊ0����������
    		create.addLine(kdtEntries, 1);
    		if(rowCount == 0)
    			row = 0;
    		else
    			row = rowCount;
    	}else{
    		this.storeFields();
    		
    		Object o = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
    		Object head = kdtEntries.getCell(rowIndex, HEADNUMBER).getValue();
    		Object headlevel = kdtEntries.getCell(rowIndex, "level").getValue();
    		Object name = kdtEntries.getCell(rowIndex, "name").getValue();
    		Object longName = kdtEntries.getCell(rowIndex, "longName").getValue();
    		Boolean isInvite=(Boolean)kdtEntries.getRow(rowIndex).getCell("isInvite").getValue();
    		boolean isCiting = ((Boolean)kdtEntries.getCell(rowIndex, "isCiting").getValue()).booleanValue()||((Boolean)kdtEntries.getCell(rowIndex, "isWTCiting").getValue()).booleanValue();
    		for(int i = rowIndex +1 ; i < kdtEntries.getRowCount(); i++){//�¼���������Ҳ���������¼�
    			if(o.equals(kdtEntries.getCell(i, HEADNUMBER).getValue())){
    				isCiting = ((Boolean)kdtEntries.getCell(i, "isCiting").getValue()).booleanValue()||((Boolean)kdtEntries.getCell(i, "isWTCiting").getValue()).booleanValue();
    				if(isCiting){
    					break;
    				}
    			}
    		}
        	if(isCiting){
        		MsgBox.showInfo("��ܺ�Լ�������޷������¼���");
        		return;
        	}
        	
    		ProgrammingContractInfo headObject = (ProgrammingContractInfo)kdtEntries.getRow(rowIndex).getUserObject();
    		headObject.setContractType((ContractTypeInfo)kdtEntries.getCell(rowIndex, "contractType").getValue());
    		int newLevel = 0;
    		//����ʱ�ж������Ƿ�Ϸ�
    		if(o == null || o.toString().trim().length() == 0){
    			MsgBox.showInfo("��¼�� "+(rowIndex+1)+" �У���ܺ�Լ���벻��Ϊ�գ�");
    			return;
    		}
//    		else if((o.toString().trim()+".").length() >= 80){
//    			MsgBox.showInfo("��¼�� "+(rowIndex+1)+" �У���ܺ�Լ�������\n���޸ĺ��������Ӽ���ܺ�Լ��");
//    			return;
//    		}
    		else if(name == null || StringUtils.isEmpty(name.toString())){
    			MsgBox.showInfo("��¼�� "+(rowIndex+1)+" �У���ܺ�Լ���Ʋ���Ϊ�գ�");
    			return;
    		}else{
    			String ln = o.toString();
    			//
    			row = getInsertRowIndex(o , rowIndex , rowCount);
    			newLevel = new Integer(headlevel.toString()).intValue()+1 ;
    			//����Ѿ����¼�����clone�ϼ������ݣ�
    			//��� �������� = ��ǰѡ���� + 1�� �� ��Ҫcloneѡ���е����ݴ��������С��������κδ��� 
    			if(rowIndex +1 == row){
    				create.insertLine(kdtEntries , row , newLevel, headObject);//clone
    			}else{
    				create.insertSameLine(kdtEntries , row , newLevel, headObject);//��������
    			}
//    			if(kdtEntries.getCell(rowIndex+1, HEADNUMBER)!=null){
//    				Object checkHeadNumber = kdtEntries.getCell(rowIndex+1, HEADNUMBER).getValue();
//    				if( checkHeadNumber!=null && ln.equals(checkHeadNumber)){
//        				create.insertSameLine(kdtEntries , row , newLevel, headObject);
//        			} else {
//        				create.insertLine(kdtEntries , row , newLevel, headObject);
//        			}
//    			} else {
//    				create.insertLine(kdtEntries , row , newLevel, headObject);
//    			}
    			
    			kdtEntries.getCell(row, HEADNUMBER).setValue(o);
    			if(longName != null)
    				kdtEntries.getCell(row, "longName").setValue(longName.toString().trim()+".");
    		}
    		//���»��ܵ��ϼ�
    		caclTotalAmount(row, kdtEntries.getColumnIndex("amount"), newLevel);
			caclTotalAmount(row, kdtEntries.getColumnIndex("controlAmount"), newLevel);
			caclTotalAmount(row, kdtEntries.getColumnIndex("balance"), newLevel);
			caclTotalAmount(row, kdtEntries.getColumnIndex("controlBalance"), newLevel);
			caclTotalAmount(row, kdtEntries.getColumnIndex("buildPerSquare"), newLevel);
			caclTotalAmount(row, kdtEntries.getColumnIndex("soldPerSquare"), newLevel);
			
			kdtEntries.getCell(row, "isInvite").setValue(isInvite);
    	}
    	//���ñ����еı༭��ʽ�������ϼ����벻���޸�
    	KDTextField txtLongNumber = new KDTextField();
		LimitedTextDocument document = new LimitedTextDocument("");
		txtLongNumber.setMaxLength(80);
		txtLongNumber.setDocument(document);
		KDTDefaultCellEditor cellEditorNumber = new KDTDefaultCellEditor(txtLongNumber);
		kdtEntries.getCell(row, LONGNUMBER).setEditor(cellEditorNumber);
		
		formatName(row);
		
    	createTree();

    	setSmallBtnEnable();
		createCostCentertF7();
	}

	private void checkAimCostNotNull() {
		if(prmtAimCost.getValue() == null){
			this.prmtAimCost.requestFocus();
			FDCMsgBox.showWarning(this, "������дĿ��ɱ��汾�ţ�");
			this.abort();
		}
	}
	private void formatName(int rowIndex) {
		KDTextField txtName = new KDTextField();
		txtName.setMaxLength(80);
		txtName.setDocument(new LimitedLengthDocument() {
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				if (str.matches("^\\.+$")) {
					return;
				}
				super.insertString(offs, str, a);
			}

			public void remove(int offs, int len) throws BadLocationException {
				super.remove(offs, len);
			}
		});
		KDTDefaultCellEditor cellEditorName = new KDTDefaultCellEditor(txtName);
		kdtEntries.getCell(rowIndex, "name").setEditor(cellEditorName);
    }
    
    /**
     * ��������а�ť
     * 2011.11.30�޸�Ϊ����ͬ����Լ��� 
     * �߼�˵����
     * 1����û�к�Լ����� ����һ��
     * 2����ѡ����һ����¼ ��������ͬ����ͬ���
     * @param e
     * @throws Exception
     */
    public void actionInsertLines_actionPerformed(ActionEvent e) throws Exception{
    	checkAimCostNotNull();
    	int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
    	int rowCount = kdtEntries.getRowCount();
    	int row = -1;
    	
    	if(rowIndex < 0){
    		//�±�Ϊ0����������
    		create.addLine(kdtEntries, 1);
    		if(rowCount == 0)
    			row = 0;
    		else
    			row = rowCount;
    	}else{
    		this.storeFields();
    		
    		Object o = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
    		Object head = kdtEntries.getCell(rowIndex, HEADNUMBER).getValue();
    		Object headlevel = kdtEntries.getCell(rowIndex, "level").getValue();
    		Object name = kdtEntries.getCell(rowIndex, "name").getValue();
    		Object longName = kdtEntries.getCell(rowIndex, "longName").getValue();
    		boolean isCiting = ((Boolean)kdtEntries.getCell(rowIndex, "isCiting").getValue()).booleanValue();
    		
    		ProgrammingContractInfo headObject = (ProgrammingContractInfo)kdtEntries.getRow(rowIndex).getUserObject();
    		
    		int activelevel = new Integer(headlevel.toString()).intValue();
    		if(activelevel == 1 ){ //�����һ��������һ�е����
    			create.insertSameLine(kdtEntries , rowCount , 1 , null);
    			row = rowCount;
    		}else if(o == null || o.toString().trim().length() == 0){//����ʱ�ж������Ƿ�Ϸ�
    			MsgBox.showInfo("��¼�� "+(rowIndex+1)+" �У���ܺ�Լ���벻��Ϊ�գ�");
    			return;
    		}
//    		else if((o.toString().trim()+".").length() >= 80){
//    			MsgBox.showInfo("��¼�� "+(rowIndex+1)+" �У���ܺ�Լ�������\n���޸ĺ��������Ӽ���ܺ�Լ��");
//    			return;
//    		}
    		else if(name == null || StringUtils.isEmpty(name.toString())){
    			MsgBox.showInfo("��¼�� "+(rowIndex+1)+" �У���ܺ�Լ���Ʋ���Ϊ�գ�");
    			return;
    		} else{
//    			if(isCiting){
//            		MsgBox.showInfo("��ܺ�Լ�������޷�����ͬ����");
//            		return;
//            	}
    			String ln = o.toString();
    			if(ln.length() == (head.toString().length() + 1)){
    				MsgBox.showInfo("��¼�� "+(rowIndex+1)+" �У���ܺ�Լ���벻��Ϊ�գ�");
        			return;
    			}
    			if(rowIndex+1 <kdtEntries.getRowCount()){
    				Object oldLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
    				Object nextHeadNumber = kdtEntries.getCell(rowIndex+1, HEADNUMBER).getValue();
    				if(oldLongNumber.equals(nextHeadNumber)){
    					FDCMsgBox.showInfo("����ϸĿ¼��������ͬ����");
    					return;
    				}
    			}
    			row = getInsertRowIndexSameLevel(rowIndex , rowCount);
//    			create.insertSameLine(kdtEntries , row ,activelevel , headObject.getParent());
    			
    			ProgrammingContractInfo newDetailInfo = new ProgrammingContractInfo();
		        newDetailInfo.setId(BOSUuid.create("ECE079DB"));
		        newDetailInfo.setLevel(activelevel);
		        newDetailInfo.setBalance(FDCHelper.ZERO);
			    newDetailInfo.setControlBalance(FDCHelper.ZERO);
			    newDetailInfo.setAmount(FDCHelper.ZERO);
			    newDetailInfo.setControlAmount(FDCHelper.ZERO);
			    newDetailInfo.setSignUpAmount(FDCHelper.ZERO);
			    newDetailInfo.setChangeAmount(FDCHelper.ZERO);
			    newDetailInfo.setSettleAmount(FDCHelper.ZERO);
	        	newDetailInfo.setParent(headObject.getParent());
	        	create.cloneInsertHead(headObject,this.editData.getEntries(),newDetailInfo,this.editData.getAimCost());
    		        
	        	IRow addRow = kdtEntries.addRow(row);
	        	create.loadLineFields(kdtEntries, addRow, newDetailInfo);
	            
    			kdtEntries.getCell(row, HEADNUMBER).setValue(head);
    			if(longName != null)
    				kdtEntries.getCell(row, "longName").setValue(longName.toString().trim()+".");
    			
    			
    			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("amount"), activelevel);
    			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("controlAmount"), activelevel);
    			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("balance"), activelevel);
    			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("controlBalance"), activelevel);
    		}
    	}
    	//���ñ����еı༭��ʽ�������ϼ����벻���޸�
    	KDTextField txtLongNumber = new KDTextField();
		LimitedTextDocument document = new LimitedTextDocument("");
		txtLongNumber.setMaxLength(80);
		txtLongNumber.setDocument(document);
		KDTDefaultCellEditor cellEditorNumber = new KDTDefaultCellEditor(txtLongNumber);
		kdtEntries.getCell(row, LONGNUMBER).setEditor(cellEditorNumber);
		
		formatName(row);
		
    	createTree();

    	setSmallBtnEnable();
		createCostCentertF7();
		
		
	
//    	int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
//    	Object o = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
//    	Object name = kdtEntries.getCell(rowIndex, "name").getValue();
//    	if(o == null || o.toString().trim().length() == 0){
//			MsgBox.showInfo("��¼�� "+(rowIndex+1)+" �У���ܺ�Լ���벻��Ϊ�գ�");
//			return;
//		}else if(name == null || StringUtils.isEmpty(name.toString())){
//			MsgBox.showInfo("��¼�� "+(rowIndex+1)+" �У���ܺ�Լ���Ʋ���Ϊ�գ�");
//			return;
//		}
//    	Object headNumber = kdtEntries.getCell(rowIndex, HEADNUMBER).getValue();
//		Object level = kdtEntries.getCell(rowIndex, "level").getValue();
//		ProgrammingContractInfo headObject = (ProgrammingContractInfo)kdtEntries.getRow(rowIndex).getUserObject();
//    	create.insertLine(kdtEntries , rowIndex , new Integer(level.toString()).intValue() , headObject.getParent());
//		kdtEntries.getCell(rowIndex, HEADNUMBER).setValue(headNumber);
//		if(headObject.getParent() != null && headObject.getParent().getDisplayName() != null){
//			kdtEntries.getCell(rowIndex, "longName").setValue(headObject.getParent().getDisplayName().toString().trim()+".");
//		}
//		KDTextField txtLongNumber = new KDTextField();
//		LimitedTextDocument document = new LimitedTextDocument("");
//		txtLongNumber.setDocument(document);
//		KDTDefaultCellEditor cellEditorNumber = new KDTDefaultCellEditor(txtLongNumber);
//		kdtEntries.getCell(rowIndex, LONGNUMBER).setEditor(cellEditorNumber);
//		formatName(rowIndex);
//		createTree();
    }

	/**
     * ���ɾ���а�ť
     * @param e
     * @throws Exception
     */
    public void actionRemoveLines_actionPerformed(ActionEvent e) throws Exception{
    	int rowIndex = this.kdtEntries.getSelectManager().getActiveRowIndex();
    	Object longNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
    	Object h = kdtEntries.getCell(rowIndex, HEADNUMBER).getValue();
    	
    	boolean isCiting = ((Boolean)kdtEntries.getCell(rowIndex, "isCiting").getValue()).booleanValue()||((Boolean)kdtEntries.getCell(rowIndex, "isWTCiting").getValue()).booleanValue();
    	if(isCiting){
    		MsgBox.showInfo("���ڱ����õĿ�ܺ�Լ��"+longNumber.toString()+"��,�޷�ɾ����");
    		return;
    	}
    	
    	ArrayList list = new ArrayList();
    	if(longNumber != null){
			list.add(new Integer(rowIndex));
			getSublevel(longNumber.toString() , rowIndex , list);
    	}
    	
    	int oldLevel = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
		boolean isHasSomeLevel = false;// Ĭ��û��ͬ���ڵ�
		int someIndex = -1;// ��ͬ���ڵ�ʱ��Ӧ�±�
		int SomeLevel = 0;// ��ͬ���ڵ�ʱ��Ӧ����
		if (h != null) {
			for (int i = kdtEntries.getRowCount() - 1; i >= 0; --i) {
				if (i == rowIndex) {
					continue;
				}
				Object l_2 = kdtEntries.getCell(i, HEADNUMBER).getValue();
				if (l_2 != null) {
					if (h.toString().equals(l_2.toString())) {
						isHasSomeLevel = true;
						someIndex = i;
						SomeLevel = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
						break;
					}
				}
			}
		}
    	
		if(list.size() > 1){
			if(MsgBox.OK == MsgBox.showConfirm2New(null,"����ǰɾ���ĸ��ڵ㡰"+longNumber.toString()+"���»��������Ŀ�ܺ�Լ��ȷ��Ҫһ��ɾ����")){
				create.removeLine(kdtEntries, list);
				if (!isHasSomeLevel && oldLevel == 2) {
					if (rowIndex > 0) {
						kdtEntries.getCell(rowIndex - 1, "amount").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "controlAmount").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "balance").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "controlBalance").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "buildPerSquare").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "soldPerSquare").setValue(FDCHelper.ZERO);
					}
				} else if (isHasSomeLevel && oldLevel == 2) {
					if (h != null) {
						for (int i = kdtEntries.getRowCount() - 1; i >= 0; --i) {
							Object l_2 = kdtEntries.getCell(i, HEADNUMBER).getValue();
							if (l_2 != null) {
								if (h.toString().equals(l_2.toString())) {
									someIndex = i;
									SomeLevel = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
									break;
								}
							}
						}
					}
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("amount"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("controlAmount"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("balance"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("controlBalance"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("buildPerSquare"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("soldPerSquare"), SomeLevel);
				}
			} else {
				return;
			}
		}else{
			if(MsgBox.OK == MsgBox.showConfirm2New(null,"�Ƿ�ȷ��ɾ�����ݣ�")){
		    	create.removeLine(kdtEntries,rowIndex);
		    	if (!isHasSomeLevel && oldLevel == 2) {
					if (rowIndex > 0) {
						kdtEntries.getCell(rowIndex - 1, "amount").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "controlAmount").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "balance").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "controlBalance").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "buildPerSquare").setValue(FDCHelper.ZERO);
						kdtEntries.getCell(rowIndex - 1, "soldPerSquare").setValue(FDCHelper.ZERO);
					}
				} else if (isHasSomeLevel && oldLevel == 2) {
					if (h != null) {
						for (int i = kdtEntries.getRowCount() - 1; i >= 0; --i) {
							Object l_2 = kdtEntries.getCell(i, HEADNUMBER).getValue();
							if (l_2 != null) {
								if (h.toString().equals(l_2.toString())) {
									someIndex = i;
									SomeLevel = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
									break;
								}
							}
						}
					}
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("amount"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("controlAmount"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("balance"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("controlBalance"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("buildPerSquare"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("soldPerSquare"), SomeLevel);
				}
			} else {
				return;
			}
    	}
		
		if (h != null) {
			for (int i = kdtEntries.getRowCount() - 1; i >= 0; --i) {
				Object l_2 = kdtEntries.getCell(i, HEADNUMBER).getValue();
				if (l_2 != null) {
					if (h.toString().equals(l_2.toString())) {
						someIndex = i;
						SomeLevel = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
						break;
					}
				}
			}
		}
		
		//ɾ��ʱ��Ҫ���¶Խ����л���
		int loop = getLoop(rowIndex, h);
		if (loop > 0) {
			int level = new Integer(kdtEntries.getCell(loop, "level").getValue().toString()).intValue();
			ProgrammingContractInfo proConInfo = (ProgrammingContractInfo) kdtEntries.getRow(loop).getUserObject();
			ProgrammingContracCostCollection costEntries = proConInfo.getCostEntries();
			if (costEntries.size() == 0) {
				if (!isHasSomeLevel) {
					kdtEntries.getCell(loop, "amount").setValue(FDCHelper.ZERO);
					kdtEntries.getCell(loop, "controlAmount").setValue(FDCHelper.ZERO);
					kdtEntries.getCell(loop, "balance").setValue(FDCHelper.ZERO);
					kdtEntries.getCell(loop, "controlBalance").setValue(FDCHelper.ZERO);
					kdtEntries.getCell(loop, "buildPerSquare").setValue(FDCHelper.ZERO);
					kdtEntries.getCell(loop, "soldPerSquare").setValue(FDCHelper.ZERO);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("amount"), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("controlAmount"), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("balance"), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("controlBalance"), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("buildPerSquare"), level);
					caclTotalAmount(loop, kdtEntries.getColumnIndex("soldPerSquare"), level);
				} else {
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("amount"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("controlAmount"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("balance"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("controlBalance"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("buildPerSquare"), SomeLevel);
					caclTotalAmount(someIndex, kdtEntries.getColumnIndex("soldPerSquare"), SomeLevel);
				}
			} else {
				caclTotalAmount(loop, kdtEntries.getColumnIndex("amount"), level);
				caclTotalAmount(loop, kdtEntries.getColumnIndex("controlAmount"), level);
				caclTotalAmount(loop, kdtEntries.getColumnIndex("balance"), level);
				caclTotalAmount(loop, kdtEntries.getColumnIndex("controlBalance"), level);
				caclTotalAmount(loop, kdtEntries.getColumnIndex("buildPerSquare"), level);
				caclTotalAmount(loop, kdtEntries.getColumnIndex("soldPerSquare"), level);
			}
		}
		sumClass.caclTotalAmount(kdtEntries);
		sumClass.appendProFootRow(null,null);
		setSmallBtnEnable();
		dataBinder.storeFields();
		createTree();
    }
	private int getLoop(int rowIndex, Object h) {
		int loop = 0;
		boolean isHasSame = false;
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			if (i == rowIndex)
				break;
			Object l_2 = kdtEntries.getCell(i, HEADNUMBER).getValue();
			if (l_2 != null) {
				if (h.toString().equals(l_2.toString())) {
					loop = i;
					isHasSame = true;
					break;
				}
			}
		}
		
		if(!isHasSame){
			if(rowIndex == kdtEntries.getRowCount()){
				loop = rowIndex - 1 ;
			}else{
				loop = rowIndex - 1;
			}
		}
		return loop;
	}
    
	/**
	 * �����ϸ��Ϣ��������¼����
	 * 
	 * @param e
	 * @throws Exception
	 */
    public void actionDetails_actionPerformed(ActionEvent e) throws Exception{
    	checkAimCostNotNull();
		int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
		this.kdtEntries.getEditManager().editingStopped();
		Object oldLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
		int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
		if (rowIndex == -1) {
			FDCMsgBox.showInfo("��ѡ����");
			return;
		}
		if(oldLongNumber == null){
			FDCMsgBox.showInfo("���벻��Ϊ�գ�");
			return;
		}
		//У���Ƿ����¼����������û����ϸ��
		if(rowIndex+1 <kdtEntries.getRowCount()){
			Object nextHeadNumber = kdtEntries.getCell(rowIndex+1, HEADNUMBER).getValue();
			if(oldLongNumber.equals(nextHeadNumber)){
				FDCMsgBox.showInfo("����ϸĿ¼û����ϸ��Ϣ��");
				return;
			}
		}
		Object headNumber = kdtEntries.getCell(rowIndex, HEADNUMBER).getValue();
		Object name = kdtEntries.getCell(rowIndex, "name").getValue();
		if(headNumber!=null && oldLongNumber !=null){
			if(headNumber.toString().length()+1 == oldLongNumber.toString().length()){
				FDCMsgBox.showInfo("��������д���룡");
				return;
			}
		}
		if(name == null){
			FDCMsgBox.showInfo("���Ʋ���Ϊ�գ�");
			return;
		}
		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = null;
		ProgrammingContractInfo rowObject = (ProgrammingContractInfo) kdtEntries.getRow(rowIndex).getUserObject();
		setContractToEditData(rowIndex, rowObject);
		ProgrammingContractCollection pcCollection = getPCCollection();
		CurProjectInfo project = (CurProjectInfo) this.getUIContext().get("treeSelectedObj");
		AimCostInfo aimCostInfo = (AimCostInfo) prmtAimCost.getData();
		uiContext.put("programmingContract", rowObject);// �滮��Լ
		uiContext.put("pcCollection", pcCollection);// �滮��Լ����
		uiContext.put("project", project);// ������Ŀ
		uiContext.put("aimCostInfo", aimCostInfo);// Ŀ��ɱ�

		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProgrammingContractEditUI.class.getName(), uiContext, null,
				oprtState);
		uiWindow.show();
		// �����ݵ���¼��
		dataBinder.loadLineFields(kdtEntries, kdtEntries.getRow(rowIndex), rowObject);
		// ���³�����
		setEntriesNameCol(rowIndex, level);
		// ���³�����
		Object newLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
		if (oldLongNumber != null && newLongNumber != null) {
			if (!oldLongNumber.toString().trim().equals(newLongNumber.toString().trim())) {
				setEntriesNumberCol(rowIndex, level);
			}
		}
		calcSquare(rowIndex);
		// ���¹滮���,�滮���,���ƽ��������Ļ���
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("amount"), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("controlAmount"), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("balance"), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("controlBalance"), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("buildPerSquare"), level);
		caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("soldPerSquare"), level);
		// �����ж����Ƿ���д������ݣ�����������ú���ɫ���Ե��������ú���ɫ
		setMyFontColor();
	}

	/**
	 * �����ж����Ƿ���д������ݣ�����������ú���ɫ���Ե��������ú���ɫ
	 * @param rowIndex
	 */
	private void setMyFontColor() {
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			boolean flag = false;// falseĬ�ϱ�ʾû�д�������
			ProgrammingContractInfo programmingContractInfo = (ProgrammingContractInfo) kdtEntries.getRow(i).getUserObject();
			ProgrammingContracCostCollection pccCollection = programmingContractInfo.getCostEntries();
			for (int j = 0; j < pccCollection.size(); j++) {
				ProgrammingContracCostInfo pccInfo = pccCollection.get(j);
				BigDecimal assigning = pccInfo.getAssigning();// ������
				BigDecimal contractAssign = pccInfo.getContractAssign();// ����Լ����
				// ��"����Լ����">"������"����ʾ�д�������
				if (contractAssign.compareTo(assigning) > 0&&!programmingContractInfo.isIsCiting()&&!programmingContractInfo.isIsWTCiting()) {
					flag = true;
				}
			}
			if (flag) {
				kdtEntries.getRow(i).getStyleAttributes().setFontColor(Color.RED);
			} else {
				kdtEntries.getRow(i).getStyleAttributes().setFontColor(Color.BLACK);
				Object blanceValue = kdtEntries.getCell(i, BALANCE).getValue();
				if (blanceValue != null) {
					BigDecimal blance = FDCHelper.toBigDecimal(blanceValue);
					if (blance.compareTo(FDCHelper.ZERO) > 0) {
						kdtEntries.getCell(i, BALANCE).getStyleAttributes().setFontColor(Color.blue);
					} else {
						kdtEntries.getCell(i, BALANCE).getStyleAttributes().setFontColor(Color.red);
					}
				}
			}

			Object blanceValue = kdtEntries.getCell(i, BALANCE).getValue();
			if (blanceValue != null) {
				BigDecimal blance = FDCHelper.toBigDecimal(blanceValue);
				if (blance.compareTo(FDCHelper.ZERO) == 0) {
					kdtEntries.getCell(i, BALANCE).getStyleAttributes().setFontColor(Color.black);
				}
			}
		}
	}
    
	/**
	 * ��ȡ��ѡ�е������Ӽ��ڵ㡢���ж��Ƿ��б����õĿ�ܺ�Լ
	 * @param longNumber
	 * @param rowIndex
	 * @param list
	 */
    private void getSublevel(String longNumber , int rowIndex , ArrayList list){
    	int rowCount = kdtEntries.getRowCount();
    	for(int i = rowIndex+1 ; i < rowCount ; i++){
			Object headNumber = kdtEntries.getCell(i, HEADNUMBER).getValue();
			Object l = kdtEntries.getCell(i, LONGNUMBER).getValue();
			if(headNumber != null && headNumber.toString() != null){
				if(headNumber.toString().startsWith(longNumber)){
					boolean isCiting = ((Boolean)kdtEntries.getCell(i, "isCiting").getValue()).booleanValue()||((Boolean)kdtEntries.getCell(i, "isWTCiting").getValue()).booleanValue();
			    	if(isCiting){
			    		MsgBox.showInfo("���ڱ����õĿ�ܺ�Լ��"+l.toString()+"��,�޷�ɾ����");
			    		SysUtil.abort();
			    	}
					list.add(new Integer(i));
				}else{
					break;
				}
			}
    	}
    }
    
    /**
     * ����ʱ��������
     */
    private void createTree(){
		int maxLevel = 0;
		int[] levelArray = new int[kdtEntries.getRowCount()];
		
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			IRow row = kdtEntries.getRow(i);
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			levelArray[i] = level;
			row.setTreeLevel(level - 1);
		}
		
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			maxLevel = Math.max(levelArray[i], maxLevel);
		}
		kdtEntries.getTreeColumn().setDepth(maxLevel);
		
		kdtEntries.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.repaint();
    }
    
    
    /**
     * ��ȡҪ�����е�λ�ã��±꣩����ͬ����ʱ��ʹ��
     * @param o
     * @param rowIndex
     * @param rowCount
     * @return
     */
    protected int getInsertRowIndexSameLevel(int rowIndex , int rowCount){
    	int row = 0;
    	if(rowIndex + 1 == rowCount){
    		return rowIndex+1;
    	}
		for(int i = rowIndex ; i < rowCount ; ++i){//Ѱ�ұ��������һ��
			int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
			int level_2 = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
			if (level_2 < level) {
				return i;
			}
		}
		return row == 0 ? rowCount : row;
    
    }
    /**
     * ��ȡҪ�����е�λ�ã��±꣩
     * @param o
     * @param rowIndex
     * @param rowCount
     * @return
     */
    protected int getInsertRowIndex(Object o , int rowIndex , int rowCount){
    	int row = 0;
    	String longNumber = o.toString();
    	if(rowIndex + 1 == rowCount){
    		return rowIndex+1;
    	}
    	
		for(int i = rowIndex+1 ; i < rowCount ; i++){
			int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
			int level_2 = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
			if (level_2 == level || level_2 < level) {
				return i;
			}
			Object n = kdtEntries.getCell(i, LONGNUMBER).getValue();
			if(n != null && n.toString() != null){
				if(!n.toString().startsWith(longNumber)){
					row = i;
					break;
				}
			}else{
				return i+1;
			}
			
			if(rowIndex + 2 == rowCount){
				return rowCount;
			}
		}
		return row == 0 ? rowCount : row;
    }
    
    protected void kdtEntries_editStarting(KDTEditEvent e) throws Exception {
    	if(e.getColIndex() == kdtEntries.getColumnIndex(LONGNUMBER)){
    		Object longNumber = kdtEntries.getCell(e.getRowIndex(), LONGNUMBER).getValue();
			if(longNumber != null && longNumber.toString().trim().length() > 80){
				MsgBox.showInfo("��¼�� "+(e.getRowIndex()+1)+" �У���ܺ�Լ���볬��\n���޸��ϼ�������ٽ��б༭��");
				kdtEntries.getEditManager().cancelEditing();
				e.setCancel(true);
			}
    	}
    }
    
    protected void kdtEntries_activeCellChanged(KDTActiveCellEvent e) throws Exception {
    	setSmallBtnEnable();
    }
    
    protected void kdtEntries_editStarted(KDTEditEvent e) throws Exception {
    	//����༭���Ǳ������������ȡ�����еı༭���������ϼ����벻���Ա�ɾ�����޸�
    	if(e.getColIndex() == kdtEntries.getColumnIndex(LONGNUMBER)){
    		int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
    		ICellEditor editor = kdtEntries.getCell(rowIndex, LONGNUMBER).getEditor();
    		if(editor != null){
    			if(editor instanceof KDTDefaultCellEditor){
		    		int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
		    		KDTDefaultCellEditor de = (KDTDefaultCellEditor)editor;
    	    		KDTextField txtLongNumber = (KDTextField) de.getComponent();
    	    		LimitedTextDocument doc = (LimitedTextDocument)txtLongNumber.getDocument();
    	    		txtLongNumber.setMaxLength(80);
    	    		String txt = "";
    	    		Object longNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
    	    		Object subNumber = kdtEntries.getCell(rowIndex, HEADNUMBER).getValue();
    	    		if(longNumber == null || longNumber.toString().trim().length() == 0){
    	    			if(subNumber != null && subNumber.toString().trim().length() > 0){
    	    				txt = subNumber.toString().trim()+".";
    	    				kdtEntries.getCell(rowIndex, LONGNUMBER).setValue(txt);
    	    			}
    	    		}
    	    		else{
    	    			txt = longNumber.toString().trim();
    	    		}
    	    		if(level > 1){
    	    			doc.setLimitedText(subNumber.toString().trim()+".");
    	    			doc.setIsOnload(true);
    	    			doc.setIsAutoUpdate(true);
    	    			txtLongNumber.setText(txt);
    	    			doc.setIsOnload(false);
    	    			doc.setIsAutoUpdate(false);
    	    		}else{
    					doc.setIsAutoUpdate(true);
    					doc.setIsOnload(true);
    					txtLongNumber.setText(txt);
    					doc.setIsAutoUpdate(false);
    					doc.setIsOnload(false);
    	    		}
    			}
    		}
    	}
    }
    
    protected void kdtEntries_editStopped(KDTEditEvent e) throws Exception {
    	//���ñ���
    	Object oldValue = e.getOldValue();
		Object newValue = e.getValue();
		if(oldValue == null && newValue == null){
			return;
		}
		this.dataBinder.storeFields();
		int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
		int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
		int colIndex = e.getColIndex();
		
    	if(colIndex == kdtEntries.getColumnIndex(LONGNUMBER)){
    		if(oldValue != null && newValue != null){
    			if(oldValue.equals(newValue)){
    				return;
    			}
    		}
    		setEntriesNumberCol(rowIndex, level);
    	}
    	
    	if(colIndex == kdtEntries.getColumnIndex("name")){
    		setEntriesNameCol(rowIndex, level);
    	}


    	
		// �жϹ滮����Ƿ�����ѷ���������ƽ��
    	if (colIndex == kdtEntries.getColumnIndex("amount")) {
			Object newAmountObj = e.getValue();// �¹滮���
			Object oldAmountObj = e.getOldValue();// ԭ�滮���
			Object oldControlAmountObj = kdtEntries.getCell(rowIndex, "controlAmount").getValue();
			Object oldBalanceObj = kdtEntries.getCell(rowIndex, "balance").getValue();
			BigDecimal oldAmount = oldAmountObj == null ? FDCHelper.ZERO : FDCHelper.toBigDecimal(oldAmountObj);
			BigDecimal newAmount = oldAmountObj == null ? FDCHelper.ZERO : FDCHelper.toBigDecimal(newAmountObj);
			BigDecimal oldBalance = oldBalanceObj == null ? FDCHelper.ZERO : FDCHelper.toBigDecimal(oldBalanceObj);

			BigDecimal oldControlAmount = oldBalanceObj == null ? FDCHelper.ZERO : FDCHelper.toBigDecimal(oldControlAmountObj);
			// �ѷ������
			BigDecimal occurredAmount = oldAmount.subtract(oldBalance);
			if (newAmount.compareTo(oldControlAmount) < 0) {
				StringBuffer msg = new StringBuffer();
				msg.append("�� ").append(rowIndex + 1).append("�� \n");
				msg.append("�滮��� ����С�� ���ƽ��");
				FDCMsgBox.showInfo(msg.toString());
				kdtEntries.getCell(rowIndex, "amount").setValue(oldAmountObj);
				return;
			}
			Boolean isCiting = (Boolean) kdtEntries.getCell(rowIndex, "isCiting").getValue()||(Boolean) kdtEntries.getCell(rowIndex, "isWTCiting").getValue();
			if (Boolean.FALSE == isCiting) {
				return;
			}
			if (newAmount.compareTo(oldControlAmount) < 0) {
				StringBuffer msg = new StringBuffer();
				msg.append("�� ").append(rowIndex + 1).append("�� \n");
				msg.append("�滮��� ����С�� ���ƽ��");
				FDCMsgBox.showInfo(msg.toString());
				kdtEntries.getCell(rowIndex, "amount").setValue(oldAmountObj);
				return;
			} else if (newAmount.compareTo(occurredAmount) < 0) {
				StringBuffer msg = new StringBuffer();
				msg.append("�� ").append(rowIndex + 1).append("�� \n");
				msg.append("�滮��� ����С�� �������");
				FDCMsgBox.showInfo(msg.toString());
				kdtEntries.getCell(rowIndex, "amount").setValue(oldAmountObj);
				return;
			}
			
			calcSquare(e.getRowIndex());
			setMyFontColor();
    	}
		// �жϹ滮����Ƿ�����ѷ���������ƽ��2
    	if (colIndex == kdtEntries.getColumnIndex("controlAmount")) {
			Object newControlAmountObj = e.getValue();// �¿��ƽ��
			Object oldControlAmountObj = e.getOldValue();// ԭ���ƽ��
			Object oldAmountObj = kdtEntries.getCell(rowIndex, "amount").getValue();
			BigDecimal oldControlAmount= oldControlAmountObj == null ? FDCHelper.ZERO : FDCHelper.toBigDecimal(oldControlAmountObj);
			BigDecimal newControlAmount = newControlAmountObj == null ? FDCHelper.ZERO : FDCHelper.toBigDecimal(newControlAmountObj);
			BigDecimal oldAmount = oldAmountObj == null ? FDCHelper.ZERO : FDCHelper.toBigDecimal(oldAmountObj);
			if (oldAmount.compareTo(oldControlAmount) < 0) {
				StringBuffer msg = new StringBuffer();
				msg.append("�� ").append(rowIndex + 1).append("�� \n");
				msg.append("�滮��� ����С�� ���ƽ��");
				FDCMsgBox.showInfo(msg.toString());
				kdtEntries.getCell(rowIndex, "controlAmount").setValue(oldControlAmount);
				return;
			}
			Boolean isCiting = (Boolean) kdtEntries.getCell(rowIndex, "isCiting").getValue()||(Boolean) kdtEntries.getCell(rowIndex, "isWTCiting").getValue();
			if (Boolean.FALSE == isCiting) {
				return;
			}
			if (oldAmount.compareTo(newControlAmount) < 0) {
				StringBuffer msg = new StringBuffer();
				msg.append("�� ").append(rowIndex + 1).append("�� \n");
				msg.append("�滮��� ����С�� ���ƽ��");
				FDCMsgBox.showInfo(msg.toString());
				kdtEntries.getCell(rowIndex, "controlAmount").setValue(oldControlAmount);
				return;
			} 
			
			setMyFontColor();
    	}
    	if (colIndex == kdtEntries.getColumnIndex("amount") || 
    			colIndex == kdtEntries.getColumnIndex("controlAmount")){
    		if(oldValue != null && newValue != null){
				if ((new BigDecimal(oldValue.toString())).compareTo(new BigDecimal(newValue.toString())) == 0) {
    				return;
    			}
    		}
			// ���¹滮���Ϳ������
			if (colIndex == kdtEntries.getColumnIndex("amount")) {
				if (oldValue != null && newValue != null) {
					Object balanceObj = kdtEntries.getCell(rowIndex, "balance").getValue();
					if (balanceObj != null) {
						BigDecimal balance = FDCHelper.toBigDecimal(balanceObj);
						kdtEntries.getCell(rowIndex, "balance").setValue(
								balance.add((FDCHelper.toBigDecimal(newValue).subtract(FDCHelper.toBigDecimal(oldValue)))));
					}
				}
				if (level != 1) {
					caclTotalAmount(e.getRowIndex(), kdtEntries.getColumnIndex("balance"), level);
				}
			}

			if (colIndex == kdtEntries.getColumnIndex("controlAmount")) {
				if (oldValue != null && newValue != null) {
					Object controlBalanceObj = kdtEntries.getCell(rowIndex, "controlBalance").getValue();
					if (controlBalanceObj != null) {
						BigDecimal controlBalance = FDCHelper.toBigDecimal(controlBalanceObj);
						kdtEntries.getCell(rowIndex, "controlBalance").setValue(
								controlBalance.add((FDCHelper.toBigDecimal(newValue).subtract(FDCHelper.toBigDecimal(oldValue)))));
					}
				}
				if (level != 1) {
					caclTotalAmount(e.getRowIndex(), kdtEntries.getColumnIndex("controlBalance"), level);
				}
			}
			caclTotalAmount(e.getRowIndex(), e.getColIndex(), level);
    	}
		// ���ù滮�����ɫ
		if (colIndex == kdtEntries.getColumnIndex(BALANCE)) {
			Object blanceValue = kdtEntries.getCell(rowIndex, BALANCE).getValue();
			if (oldValue != null && newValue != null) {
				BigDecimal newV = new BigDecimal(newValue.toString());
				BigDecimal oldV = new BigDecimal(oldValue.toString());
				if (newV.compareTo(oldV) == 0) {
					return;
				}
			}
			if (blanceValue != null) {
				BigDecimal blance = new BigDecimal(blanceValue.toString());
				if (blance.compareTo(FDCHelper.ZERO) > 0) {
					kdtEntries.getCell(rowIndex, BALANCE).getStyleAttributes().setFontColor(Color.blue);
				} else {
					kdtEntries.getCell(rowIndex, BALANCE).getStyleAttributes().setFontColor(Color.red);
				}
			}
		}
		if (colIndex == kdtEntries.getColumnIndex("contractType")) {
			if(rowIndex+1 <kdtEntries.getRowCount()){
				Object oldLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
				if(oldLongNumber == null){
					FDCMsgBox.showInfo("����д���룡");
					kdtEntries.getRow(rowIndex).getCell("contractType").setValue(null);
					return;
				}
				Object nextHeadNumber = kdtEntries.getCell(rowIndex+1, HEADNUMBER).getValue();
				if(oldLongNumber.equals(nextHeadNumber)){
					FDCMsgBox.showInfo("����ϸĿ¼û�к�ͬ���ͣ�");
					kdtEntries.getRow(rowIndex).getCell("contractType").setValue(null);
					return;
				}
			}
		}
		
		if (colIndex == kdtEntries.getColumnIndex("isInvite")){
			if(rowIndex+1 <kdtEntries.getRowCount()){
				Object oldLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
				if(oldLongNumber == null){
					FDCMsgBox.showInfo("����д���룡");
					kdtEntries.getRow(rowIndex).getCell("contractType").setValue(null);
					return;
				}
				Object nextHeadNumber = kdtEntries.getCell(rowIndex+1, HEADNUMBER).getValue();
				if(oldLongNumber.equals(nextHeadNumber)){
					FDCMsgBox.showInfo("�������ϸĿ¼��");
					kdtEntries.getRow(rowIndex).getCell("isInvite").setValue(e.getOldValue());
					return;
				}else{
					Boolean isInvite=(Boolean) kdtEntries.getRow(rowIndex).getCell("isInvite").getValue();
					setIsInvite(kdtEntries.getRow(rowIndex),isInvite);
				}
			}
		}
		
		if (colIndex == kdtEntries.getColumnIndex("isInput")) {
			if(rowIndex+1 <kdtEntries.getRowCount()){
				Object oldLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
				if(oldLongNumber == null){
					FDCMsgBox.showInfo("����д���룡");
					kdtEntries.getRow(rowIndex).getCell("isInput").setValue(false);
					return;
				}
				Object nextHeadNumber = kdtEntries.getCell(rowIndex+1, HEADNUMBER).getValue();
				if(oldLongNumber.equals(nextHeadNumber)){
					FDCMsgBox.showInfo("����ϸĿ¼����¼���ۺϵ��ۣ�");
					kdtEntries.getRow(rowIndex).getCell("isInput").setValue(false);
					kdtEntries.getRow(rowIndex).getCell("quantities").setValue(null);
					kdtEntries.getRow(rowIndex).getCell("unit").setValue(null);
					kdtEntries.getRow(rowIndex).getCell("price").setValue(null);
					kdtEntries.getRow(rowIndex).getCell("quantities").getStyleAttributes().setBackground(Color.WHITE);
			 		kdtEntries.getRow(rowIndex).getCell("unit").getStyleAttributes().setBackground(Color.WHITE);
					return;
				}
			}
			if((Boolean)kdtEntries.getRow(rowIndex).getCell("isInput").getValue()){
		 		kdtEntries.getRow(rowIndex).getCell("quantities").getStyleAttributes().setLocked(false);
		 		kdtEntries.getRow(rowIndex).getCell("unit").getStyleAttributes().setLocked(false);
		 		kdtEntries.getRow(rowIndex).getCell("quantities").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		 		kdtEntries.getRow(rowIndex).getCell("unit").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
			}else{
				kdtEntries.getRow(rowIndex).getCell("quantities").setValue(null);
				kdtEntries.getRow(rowIndex).getCell("unit").setValue(null);
				kdtEntries.getRow(rowIndex).getCell("price").setValue(null);
				kdtEntries.getRow(rowIndex).getCell("quantities").getStyleAttributes().setBackground(Color.WHITE);
		 		kdtEntries.getRow(rowIndex).getCell("unit").getStyleAttributes().setBackground(Color.WHITE);
			}
		}
		if (colIndex == kdtEntries.getColumnIndex("quantities")) {
			kdtEntries.getRow(rowIndex).getCell("price").setValue(FDCHelper.divide(kdtEntries.getRow(rowIndex).getCell("amount").getValue(), kdtEntries.getRow(rowIndex).getCell("quantities").getValue(), 2, BigDecimal.ROUND_HALF_UP));
		}
		if (colIndex == kdtEntries.getColumnIndex("paperDate")) {
			if(rowIndex+1 <kdtEntries.getRowCount()){
				Object oldLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
				if(oldLongNumber == null){
					FDCMsgBox.showInfo("����д���룡");
					return;
				}
				Object nextHeadNumber = kdtEntries.getCell(rowIndex+1, HEADNUMBER).getValue();
				if(oldLongNumber.equals(nextHeadNumber)){
					FDCMsgBox.showInfo("����ϸĿ¼����¼��ͼֽ����Ʒȷ�ϣ�");
					kdtEntries.getRow(rowIndex).getCell("paperDate").setValue(null);
					return;
				}
			}
		}
		if (colIndex == kdtEntries.getColumnIndex("documentsAuditDate")) {
			if(rowIndex+1 <kdtEntries.getRowCount()){
				Object oldLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
				if(oldLongNumber == null){
					FDCMsgBox.showInfo("����д���룡");
					return;
				}
				Object nextHeadNumber = kdtEntries.getCell(rowIndex+1, HEADNUMBER).getValue();
				if(oldLongNumber.equals(nextHeadNumber)){
					FDCMsgBox.showInfo("����ϸĿ¼����¼���б��ļ��������ڣ�");
					kdtEntries.getRow(rowIndex).getCell("documentsAuditDate").setValue(null);
					return;
				}
			}
		}
		if (colIndex == kdtEntries.getColumnIndex("resultAuditDate")) {
			if(rowIndex+1 <kdtEntries.getRowCount()){
				Object oldLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
				if(oldLongNumber == null){
					FDCMsgBox.showInfo("����д���룡");
					return;
				}
				Object nextHeadNumber = kdtEntries.getCell(rowIndex+1, HEADNUMBER).getValue();
				if(oldLongNumber.equals(nextHeadNumber)){
					FDCMsgBox.showInfo("����ϸĿ¼����¼���б��������ڣ�");
					kdtEntries.getRow(rowIndex).getCell("resultAuditDate").setValue(null);
					return;
				}
			}
		}
		if (colIndex == kdtEntries.getColumnIndex("contractAuditDate")) {
			if(rowIndex+1 <kdtEntries.getRowCount()){
				Object oldLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
				if(oldLongNumber == null){
					FDCMsgBox.showInfo("����д���룡");
					return;
				}
				Object nextHeadNumber = kdtEntries.getCell(rowIndex+1, HEADNUMBER).getValue();
				if(oldLongNumber.equals(nextHeadNumber)){
					FDCMsgBox.showInfo("����ϸĿ¼����¼���ͬ�������ڣ�");
					kdtEntries.getRow(rowIndex).getCell("contractAuditDate").setValue(null);
					return;
				}
			}
		}
		if (colIndex == kdtEntries.getColumnIndex("enterAuditDate")) {
			if(rowIndex+1 <kdtEntries.getRowCount()){
				Object oldLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
				if(oldLongNumber == null){
					FDCMsgBox.showInfo("����д���룡");
					return;
				}
				Object nextHeadNumber = kdtEntries.getCell(rowIndex+1, HEADNUMBER).getValue();
				if(oldLongNumber.equals(nextHeadNumber)){
					FDCMsgBox.showInfo("����ϸĿ¼����¼��Ԥ�ƽ������ڣ�");
					kdtEntries.getRow(rowIndex).getCell("enterAuditDate").setValue(null);
					return;
				}
			}
		}
    }
    
    private void setIsInvite(IRow row,Boolean isInvite){
    	ProgrammingContractInfo pc=(ProgrammingContractInfo) row.getUserObject();
    	int level = Integer.parseInt(row.getCell("level").getValue().toString());
    	if(level==1){
    		pc.setIsInvite(isInvite);
    		return;
    	}
    	if(!isInvite.booleanValue()&&level!=1){
    		for(int j=0;j<kdtEntries.getRowCount();j++){
    			if(j==row.getRowIndex()) continue;
				ProgrammingContractInfo parent=((ProgrammingContractInfo)kdtEntries.getRow(j).getUserObject()).getParent();
				Boolean pIsInvite=(Boolean) kdtEntries.getRow(j).getCell("isInvite").getValue();
				if(parent!=null&&pc.getParent()!=null&&parent.getId().toString().equals(pc.getParent().getId().toString())){
					if(!pIsInvite.equals(isInvite)){
						return;
					}
				}
			}
    	}
    	for(int j=0;j<row.getRowIndex();j++){
    		ProgrammingContractInfo parent=((ProgrammingContractInfo)kdtEntries.getRow(j).getUserObject());
    		if(pc.getParent()!=null&&pc.getParent().getId().toString().equals(parent.getId().toString())){
    			Boolean pIsInvite=(Boolean) kdtEntries.getRow(j).getCell("isInvite").getValue();
    			if(pIsInvite.equals(isInvite)){
    				return;
    			}else{
    				parent.setIsInvite(isInvite);
    				kdtEntries.getRow(j).getCell("isInvite").setValue(isInvite);
    				setIsInvite(kdtEntries.getRow(j),isInvite);
    			}
    		}
		}
    	
    }

	/**
	 * ������ֶ����޸�ʱ�Զ����ϻ���
	 * 
	 * @param index
	 * @param colIndex
	 * @param level
	 */
    private void caclTotalAmount(int index , int colIndex , int level) {
		if(level == 1){
			return;
		}
		int loop = index;
		int loopLevel = level;
		while(loop >= 0){
			int parentIndex = 0;
			BigDecimal dbSum = FDCHelper.ZERO;
			int curLevel = new Integer(kdtEntries.getCell(loop, "level").getValue().toString()).intValue();
			if(curLevel > loopLevel){
				loop--;
				continue;
			}
			String parentNumber = "";
			if(curLevel == 1){
				parentNumber = kdtEntries.getCell(loop, LONGNUMBER).getValue().toString();
			}else{
				parentNumber = kdtEntries.getCell(loop, HEADNUMBER).getValue().toString();
			}
			dbSum = FDCHelper.ZERO;
			for (int i = 0; i < kdtEntries.getRowCount(); i++) {
				Object l = kdtEntries.getCell(i, LONGNUMBER).getValue();
				Object h = kdtEntries.getCell(i, HEADNUMBER).getValue();
				int cacl_Level = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
				String number = "";
				//�ж�Ҫ���ܵ��±�ֵ
				if (l != null) {
					if (parentNumber.equals(l.toString())) {
						parentIndex = i;
					}
					number = l.toString();
				}else if(h != null){
					if (parentNumber.equals(h.toString())) {
						for (int j = 0; j < kdtEntries.getRowCount(); j++) {
							if(j == i)
								continue;
							int j_Level = new Integer(kdtEntries.getCell(j, "level").getValue().toString()).intValue();
							if(cacl_Level == j_Level){
								Object l_2 = kdtEntries.getCell(i, LONGNUMBER).getValue();
								if(l_2 != null){
									if(h.toString().equals(l_2.toString())){
										parentIndex = j;
									}
								}
							}
						}
					}
					number = h.toString();
				}
				//�������ֵ
				if (loopLevel == cacl_Level && number.startsWith(parentNumber)) {
					ICell cell = kdtEntries.getRow(i).getCell(colIndex);
					String cellValue = kdtEntries.getCellDisplayText(cell);
					if (cellValue != null)
						cellValue = cellValue.toString().replaceAll(",", "");

					if (!StringUtility.isNumber(cellValue)) {
						Object cellObj = cell.getValue();
						if (cellObj != null)
							cellValue = cellObj.toString();
						if (!StringUtility.isNumber(cellValue))
							continue;
					}
					BigDecimal bigdem = new BigDecimal(String.valueOf(cellValue).trim());
					dbSum = dbSum.add(bigdem);
				}
			}
			String strSum = null;
			if(!(parentIndex == kdtEntries.getRowCount() - 1)){
				strSum = dbSum.toString();
				kdtEntries.getCell(parentIndex, colIndex).setValue(strSum);
				calcSquare(parentIndex);
			}
			loop--;
			loopLevel--;
		}
	}
    
	private String attachMentTempID = null;
    protected void kdtEntries_tableClicked(KDTMouseEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		if (e.getClickCount() < 2 || e.getType() == KDTStyleConstants.HEAD_ROW || e.getColIndex() == 0) {
			return;
		}
		int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = null;
		ProgrammingContractInfo rowObject = (ProgrammingContractInfo) kdtEntries.getRow(rowIndex).getUserObject();
		setContractToEditData(rowIndex, rowObject);
		ProgrammingContractCollection pcCollection = getPCCollection();
		CurProjectInfo project = (CurProjectInfo) this.getUIContext().get("treeSelectedObj");
		AimCostInfo aimCostInfo = (AimCostInfo) prmtAimCost.getData();
		uiContext.put("programmingContract", rowObject);// �滮��Լ
		uiContext.put("pcCollection", pcCollection);// �滮��Լ����
		uiContext.put("project", project);// ������Ŀ
		uiContext.put("aimCostInfo", aimCostInfo);// Ŀ��ɱ�

		// ˫���༭����
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2
				&& e.getColIndex() == kdtEntries.getColumnIndex("attachment")) {
			boolean isEdit = false;// Ĭ��Ϊ�鿴״̬
			// if (oprtState.equals(OprtState.ADDNEW) || oprtState.equals(OprtState.EDIT)) {
			// isEdit = true;
			// }
			AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
			AttachmentUIContextInfo info = getAttacheInfo();
			if (info == null) {
				info = new AttachmentUIContextInfo();
			}
			if (FDCHelper.isEmpty(info.getBoID())) {
				String boID = rowObject.getId().toString();
				if (boID == null) {
					if (!isEdit) {
						if (attachMentTempID == null) {
							boID = acm.getAttID().toString();
							attachMentTempID = boID;
						} else {
							boID = attachMentTempID;
						}
					} else {
						return;
					}
				}
				info.setBoID(boID);
				acm.showAttachmentListUIByBoID(boID, this, isEdit);
				Object idObj = kdtEntries.getCell(rowIndex, "id").getValue();
				StringBuffer allAttachmentName = getAllAttachmentName(idObj.toString());
				if (!FDCHelper.isEmpty(allAttachmentName.toString())) {
					kdtEntries.getCell(rowIndex, "attachment").setValue("���ڸ���");
				} else {
					kdtEntries.getCell(rowIndex, "attachment").setValue(null);
				}
			}
			SysUtil.abort();
		}


		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			checkAimCostNotNull();
			Object oldLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
			if(oldLongNumber == null){
				FDCMsgBox.showInfo("����д���룡");
				return;
			}
			//У���Ƿ����¼����������û����ϸ��
			if(rowIndex+1 <kdtEntries.getRowCount()){
				Object nextHeadNumber = kdtEntries.getCell(rowIndex+1, HEADNUMBER).getValue();
				if(oldLongNumber.equals(nextHeadNumber)){
					FDCMsgBox.showInfo("����ϸĿ¼û����ϸ��Ϣ��");
					return;
				}
			}
			uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProgrammingContractEditUI.class.getName(), uiContext, null,
					oprtState);
			uiWindow.show();
			// �����ݵ���¼��
			dataBinder.loadLineFields(kdtEntries, kdtEntries.getRow(rowIndex), rowObject);
			// ���³�����
			setEntriesNameCol(rowIndex, level);
			// ���³�����
			Object newLongNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
			if (oldLongNumber != null && newLongNumber != null) {
				if (!oldLongNumber.toString().trim().equals(newLongNumber.toString().trim())) {
					setEntriesNumberCol(rowIndex, level);
				}
			}
			// ���¹滮���,�滮���,���ƽ��������Ļ���
			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("amount"), level);
			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("controlAmount"), level);
			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("balance"), level);
			caclTotalAmount(rowIndex, kdtEntries.getColumnIndex("controlBalance"), level);
			calcSquare(rowIndex);
			// �����ж�����ɫ
			setMyFontColor();
			
		
			if(OprtState.VIEW.equals(getOprtState())){
				return;
			}
			kdtEntries.getRow(rowIndex).getCell("buildPrice").setValue(FDCHelper.divide(kdtEntries.getRow(rowIndex).getCell("amount").getValue(), this.totalBuildArea, 2, BigDecimal.ROUND_HALF_UP));
			kdtEntries.getRow(rowIndex).getCell("price").setValue(FDCHelper.divide(kdtEntries.getRow(rowIndex).getCell("amount").getValue(), kdtEntries.getRow(rowIndex).getCell("quantities").getValue(), 2, BigDecimal.ROUND_HALF_UP));
			
		}
    }

	/**
	 * ��ȡ�п�����к�Լ
	 * 
	 * @return
	 */
	private ProgrammingContractCollection getPCCollection() {
		ProgrammingContractCollection pcCollection = new ProgrammingContractCollection();
		ProgrammingContractInfo pcInfo = null;
		int columnCount = kdtEntries.getRowCount();
		for (int i = 0; i < columnCount; i++) {
			pcInfo = (ProgrammingContractInfo) kdtEntries.getRow(i).getUserObject();
			setContractToEditData(i, pcInfo);
			pcCollection.add(pcInfo);
		}
		return pcCollection;
	}
	
	/**
	 * �����и��ĸ������ݡ������¼��ڵ����
	 * @param rowIndex
	 * @param level
	 */
    private void setEntriesNumberCol(int rowIndex, int level) {
		Object longNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
		if(longNumber != null && longNumber.toString().trim().length() > 0){
			String lnumber = longNumber.toString();
			if(level == 1){
				kdtEntries.getCell(rowIndex, "number").setValue(lnumber);
			}else{
				String number = lnumber.substring(lnumber.lastIndexOf(".")+1 , lnumber.length());
				kdtEntries.getCell(rowIndex, "number").setValue(number);
			}
			for(int i = rowIndex+1 ; i < kdtEntries.getRowCount() ; i++){
				Object headNumber = kdtEntries.getCell(i, HEADNUMBER).getValue();
				Object longNumber_2 = kdtEntries.getCell(i, LONGNUMBER).getValue();
				int level_2 = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
				if (level_2 == level || level_2 < level) {
					break;
				}
				
				String[] editString = lnumber.split("\\.");
				if(longNumber_2 != null && longNumber_2.toString().trim().length() > 0){
					String hNumber_2 = headNumber.toString();
					String lNumber_2 = longNumber_2.toString();
					String[] newL = lNumber_2.split("\\.");
					String[] newH = hNumber_2.split("\\.");
					for(int j = 0 ; j < editString.length ; j++){
						newL[j] = editString[j];newH[j] = editString[j];
					}
					StringBuffer str = new StringBuffer();
					for(int j = 0 ; j < newL.length ; j++){
						str.append(newL[j]).append(".");
					}
					if(newL.length < level_2)
						str.append(".");
					StringBuffer str2 = new StringBuffer();
					for(int j = 0 ; j < newH.length ; j++){
						str2.append(newH[j]).append(".");
					}
					setkdtEntriesNumber(i , str.substring(0, str.length() - 1) , str2.substring(0, str2.length() - 1));
				}
			}
		}
	}

    /**
     * ��������ʱ���á������ӽڵ㳤����
     * @param rowIndex
     * @param level
     */
	private void setEntriesNameCol(int rowIndex, int level) {
		Object name =  kdtEntries.getCell(rowIndex , "name").getValue();
		if(name != null && name.toString().trim().length() > 0){
			String nameStr = name.toString().trim();
			String blank = setNameIndent(level);
			kdtEntries.getCell(rowIndex ,"name").setValue(blank+nameStr);
			if(level == 1){
				kdtEntries.getCell(rowIndex ,"longName").setValue(nameStr);
			}else{
				Object lo = kdtEntries.getCell(rowIndex ,"longName").getValue();
				String displayName = lo == null ? "" : lo.toString();
				String ln = displayName.substring(0 , displayName.lastIndexOf("."))+".";
				kdtEntries.getCell(rowIndex ,"longName").setValue(ln + nameStr);
			}
			
			Object lo = kdtEntries.getCell(rowIndex ,"longName").getValue();
			String displayName = lo == null ? "" : lo.toString();
			if(level == 1){
				displayName = displayName+".";
			}
			String [] l = displayName.split("\\.");
			for(int i = rowIndex+1 ; i < kdtEntries.getRowCount() ; i++){
				int level_2 = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
				if (level_2 == level || level_2 < level) {
					break;
				}
				Object l2 = kdtEntries.getCell(i ,"longName").getValue();
				String l3[] = l2.toString().split("\\.");
				for(int j = 0 ; j < l.length ; j++){
					if(l3[j] != null && l3[j].length() > 0){
						l3[j] = l[j];
					}
				}
				StringBuffer str = new StringBuffer();
				for(int j = 0 ; j < l3.length ; j++){
					str.append(l3[j]).append(".");
				}
				Object n2 = kdtEntries.getCell(i ,"name").getValue();
				if(n2 == null){
					str.append(".");
				}
				kdtEntries.getCell(i ,"longName").setValue(str.substring(0, str.length() - 1));
				displayName = null;
			}
		}
	}
	
	/**
	 * �޸��¼����롢�����¼��ر�����
	 * @param i
	 * @param lnumber
	 * @param hNumber
	 */
	private void setkdtEntriesNumber(int i, String lnumber, String hNumber) {
		kdtEntries.getCell(i, HEADNUMBER).setValue(hNumber);
		kdtEntries.getCell(i, LONGNUMBER).setValue(lnumber);	
		
		ICellEditor editor = kdtEntries.getCell(i, LONGNUMBER).getEditor();
		if(editor != null){
			if(editor instanceof KDTDefaultCellEditor){
	    		KDTDefaultCellEditor de = (KDTDefaultCellEditor)editor;
    			KDTextField txtLongNumber = (KDTextField) de.getComponent();
    			LimitedTextDocument doc = (LimitedTextDocument)txtLongNumber.getDocument();
    			doc.setIsAutoUpdate(true);
    			doc.setIsOnload(true);
	    		txtLongNumber.setText(lnumber);
	    		doc.setIsAutoUpdate(false);
	    		doc.setIsOnload(false);
			}
		}
	}
	
	/**
	 * Ϊ��¼���ñ༭��
	 */
	private void setCellEditorForTable(){
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			KDTextField txtLongNumber = new KDTextField();
			String txt = "";
			Object longNumber = kdtEntries.getCell(i, LONGNUMBER).getValue();
			Object subNumber = kdtEntries.getCell(i, HEADNUMBER).getValue();
			int level = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
			if(longNumber == null || longNumber.toString().trim().length() == 0){
    			if(subNumber != null && subNumber.toString().trim().length() > 0){
    				txt = subNumber.toString().trim()+".";
    			}
			}
			else{
				txt = longNumber.toString().trim();
			}
			if(level > 1){
				LimitedTextDocument document = new LimitedTextDocument(subNumber.toString().trim()+"." , true);
				txtLongNumber.setMaxLength(80);
				txtLongNumber.setDocument(document);
				txtLongNumber.setText(txt);
				document.setIsOnload(false);
			}else{
				LimitedTextDocument document = new LimitedTextDocument("");
				txtLongNumber.setDocument(document);
				document.setIsAutoUpdate(true);
				document.setIsOnload(true);
				txtLongNumber.setText(txt);
				document.setIsAutoUpdate(false);
				document.setIsOnload(false);
			}
			
			KDTDefaultCellEditor cellEditorNumber = new KDTDefaultCellEditor(txtLongNumber);
			kdtEntries.getCell(i , LONGNUMBER).setEditor(cellEditorNumber);
			String name = (String) kdtEntries.getCell(i, "name").getValue();
			formatName(i);
			kdtEntries.getCell(i, "name").setValue(name);
		}
    }
	
	private void setTableFontColor(){
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			Object blanceValue = kdtEntries.getCell(i, BALANCE).getValue();
			boolean isCiting = ((Boolean)kdtEntries.getCell(i, "isCiting").getValue()).booleanValue();
			if(blanceValue != null){
				BigDecimal blance = (BigDecimal)blanceValue;
				if(blance.compareTo(FDCHelper.ZERO) > 0){
					kdtEntries.getCell(i, BALANCE).getStyleAttributes().setFontColor(Color.blue);
				}else{
					kdtEntries.getCell(i, BALANCE).getStyleAttributes().setFontColor(Color.red);
				}
			}
//			if(((Boolean)kdtEntries.getCell(i, "isWTCiting").getValue()).booleanValue()){
//				kdtEntries.getRow(i).getStyleAttributes().setBackground(new java.awt.Color(128,255,128));
//			}
//			if(isCiting){
//				kdtEntries.getRow(i).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
//			}
		}
	}

	/**
	 * ������ǰ��ӿո���ʾ����Ч��
	 * 
	 * @param level
	 * @return
	 */
	private String setNameIndent(int level) {
		StringBuffer blank = new StringBuffer("");
		for (int i = level; i > 1; i--) {
			blank.append("        ");
		}
		return blank.toString();
	}

	/**
	 * ���ú�Լ����ͷ��Ϣ
	 * 
	 * @param rowIndex
	 * @param rowObject
	 */
	private void setContractToEditData(int rowIndex, ProgrammingContractInfo rowObject) {
		int level = new Integer(kdtEntries.getCell(rowIndex, "level").getValue().toString()).intValue();
		rowObject.setLevel(level);
		if (level > 1) {
			Object longNumber = kdtEntries.getCell(rowIndex, LONGNUMBER).getValue();
			if (FDCHelper.isEmpty(longNumber)) {
				String subNumber = (String) kdtEntries.getCell(rowIndex, HEADNUMBER).getValue();
				if (!FDCHelper.isEmpty(subNumber)) {
					rowObject.setLongNumber(subNumber.toString().trim() + ".");// ������
				}
			} else {
				rowObject.setLongNumber((String) kdtEntries.getCell(rowIndex, "longNumber").getValue());// ������
			}
		} else {
			rowObject.setLongNumber((String) kdtEntries.getCell(rowIndex, "longNumber").getValue());// ������
		}
		rowObject.setDisplayName((String) kdtEntries.getCell(rowIndex, "longName").getValue());// ������
		rowObject.setNumber((String) kdtEntries.getCell(rowIndex, "number").getValue());// ����
		rowObject.setName((String) kdtEntries.getCell(rowIndex, "name").getValue());// ����

		rowObject.setWorkContent((String) kdtEntries.getCell(rowIndex, "workContent").getValue());// ��������
		rowObject.setSupMaterial((String) kdtEntries.getCell(rowIndex, "supMaterial").getValue());// �׹�����ָ����
		rowObject.setIsInvite((Boolean) kdtEntries.getCell(rowIndex, "isInvite").getValue());
//		rowObject.setInviteWay((InviteFormEnum) kdtEntries.getCell(rowIndex, "inviteWay").getValue());// �б귽ʽ
		if (!FDCHelper.isEmpty(kdtEntries.getCell(rowIndex, "inviteOrg").getValue())) {
			Object value = kdtEntries.getCell(rowIndex, "inviteOrg").getValue();
			rowObject.setInviteOrg((CostCenterOrgUnitInfo) value);// �б���֯
		}
		
		Object amount = kdtEntries.getCell(rowIndex, AMOUNT).getValue();
		if (!FDCHelper.isEmpty(amount)) {
			rowObject.setAmount(new BigDecimal(amount.toString()));// �滮���
		}
		Object controlAmount = kdtEntries.getCell(rowIndex, CONTROLAMOUNT).getValue();
		if (!FDCHelper.isEmpty(controlAmount)) {
			rowObject.setControlAmount(new BigDecimal(controlAmount.toString())); // ���ƽ��
		}
		Object signUpAmount = kdtEntries.getCell(rowIndex, "signUpAmount").getValue();
		if (!FDCHelper.isEmpty(signUpAmount)) {
			rowObject.setSignUpAmount(new BigDecimal(signUpAmount.toString())); // ǩԼ���
		}
		Object changeAmount = kdtEntries.getCell(rowIndex, "changeAmount").getValue();
		if (!FDCHelper.isEmpty(changeAmount)) {
			rowObject.setChangeAmount(new BigDecimal(changeAmount.toString())); // ������
		}
		Object settleAmount = kdtEntries.getCell(rowIndex, "settleAmount").getValue();
		if (!FDCHelper.isEmpty(settleAmount)) {
			rowObject.setSettleAmount(new BigDecimal(settleAmount.toString())); // ������
		}
		Object balance = kdtEntries.getCell(rowIndex, "balance").getValue();
		if (!FDCHelper.isEmpty(balance)) {
			rowObject.setBalance(new BigDecimal(balance.toString())); // �滮���
		}
		Object controlBalance = kdtEntries.getCell(rowIndex, "controlBalance").getValue();
		if (!FDCHelper.isEmpty(controlBalance)) {
			rowObject.setControlBalance(new BigDecimal(controlBalance.toString())); // �������
		}
		Object citeVersionObj = kdtEntries.getCell(rowIndex, "citeVersion").getValue();
		if (!FDCHelper.isEmpty(citeVersionObj)) {
			rowObject.setCiteVersion(new Integer(kdtEntries.getCell(rowIndex, "citeVersion").getValue().toString()).intValue());// ���ð汾
		}

		rowObject.setDescription((String) kdtEntries.getCell(rowIndex, "remark").getValue());// ��ע
	}

	public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}
    
	public void inputVersion(ProgrammingInfo info) {
		if (info == null) {
			return;
		}
		if (info.getVersion() == null) {
			info.setVersion(new BigDecimal("1.0"));
		} else {
			BigDecimal version = info.getVersion();
			info.setVersion(version.add(new BigDecimal("1.0")));
		}
	}

	/**
	 * ����ǰ�Ѽ�����"����Լ����"���� "������"���
	 */
	private void verifyRedData() {
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			ProgrammingContractInfo programmingContractInfo = (ProgrammingContractInfo) kdtEntries.getRow(i).getUserObject();
			ProgrammingContracCostCollection costEntries = programmingContractInfo.getCostEntries();
			for (int j = 0; j < costEntries.size(); j++) {
				ProgrammingContracCostInfo pccInfo = costEntries.get(j);
				BigDecimal contractAssign = pccInfo.getContractAssign();
				BigDecimal assigning = pccInfo.getAssigning();
				if (contractAssign.compareTo(assigning) > 0) {
					Color fontColor = kdtEntries.getRow(i).getStyleAttributes().getFontColor();
					Color red = new Color(255, 0, 0);
					if (fontColor.equals(red)) {
						FDCMsgBox.showInfo("�Բ��𣬳ɱ������д���\"����Լ����\"���� \"������\"�����ܱ�����ύ��");
						SysUtil.abort();
					}
				}
			}
		}
	}
	

	/**
	 * �ж��ѹ滮����Ƿ������Ŀ��ɱ����
	 * 
	 * @return ���ڷ���true
	 */
	private boolean verifyAmountVSAimCost() {
		kdtEntries.getEditManager().editingStopped();
		Object aimCostAccountTotalObj = txtAllAimCost.getNumberValue();
		BigDecimal aimCostAccountTotal = FDCHelper.ZERO;// ��Ŀ��ɱ�
		BigDecimal amountTotal = FDCHelper.ZERO;// �ܹ滮���
		if (!FDCHelper.isEmpty(aimCostAccountTotalObj)) {
			aimCostAccountTotal = FDCHelper.toBigDecimal(aimCostAccountTotalObj);
		}
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			int level = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
			if (level == 1) {
				Object amountSingleObj = kdtEntries.getCell(i, "amount").getValue();
				BigDecimal amountSingle = FDCHelper.ZERO;
				if (amountSingleObj != null) {
					amountSingle = FDCHelper.toBigDecimal(amountSingleObj);
				}
				amountTotal = amountTotal.add(amountSingle);
			}
		}
		// �ѹ滮��������Ŀ��ɱ���� return true
		if (amountTotal.compareTo(aimCostAccountTotal) > 0) {
			FDCMsgBox.showWarning(this,"�ܹ滮���ܴ�����Ŀ��ɱ���");
			this.abort();
		} else if(amountTotal.compareTo(aimCostAccountTotal)<0){
			FDCMsgBox.showWarning(this,"�ܹ滮����С����Ŀ��ɱ���");
			this.abort();
		} 
		return false;
	}

	private void verifyControlParam() {
		if (prmtAimCost.getData() != null) {
			ObjectUuidPK pk = new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId());
			String param = ContextHelperFactory.getRemoteInstance().getStringParam("FDC229_ISSTRICTCONTROL", pk);
			if (!com.kingdee.util.StringUtils.isEmpty(param)) {
				int i = Integer.parseInt(param);
				switch (i) {
				case 0:
					// ������
					break;
				case 1:
					// ��ʾ����
					if (verifyAmountVSAimCost()) {
						int isSubmit = FDCMsgBox.showConfirm2("�ܹ滮��������Ŀ��ɱ����Ƿ�Ҫ�ύ?");
						if (FDCMsgBox.OK == isSubmit) {
							break;
						} else {
							SysUtil.abort();
						}
					}
				case 2:
					// �ϸ����
					if (verifyAmountVSAimCost()) {
						FDCMsgBox.showWarning(this, "�ܹ滮��������Ŀ��ɱ����������ύ");
						SysUtil.abort();
					}
				}
			}
		}
	}

	/**
	 * ��Լ�滮����
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		veryfyForSave();
		super.actionSave_actionPerformed(e);
		sumClass.appendProFootRow(null, null);
	}

	/**
	 * ����У��
	 * 
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void veryfyForSave() throws EASBizException, BOSException {
		String name_txt = txtName.getText();
		String errrMsg = "��Լ��ܰ汾����";
		if (name_txt == null || name_txt.trim().equals("")) {
			txtName.requestFocus(true);
			throw new EASBizException(new NumericExceptionSubItem("1", errrMsg + "����Ϊ�գ�"));
			
		}
		if (StringUtils.isEmpty(txtProjectName.getText())) {
			throw new EASBizException(new NumericExceptionSubItem("1", "������Ŀ����Ϊ��"));
		}
		if (StringUtils.isEmpty(txtVersion.getText())) {
			throw new EASBizException(new NumericExceptionSubItem("1", "�汾�Ų���Ϊ��"));
		}
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Name, name_txt, CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		if (editData.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, editData.getId(), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
			filter.setMaskString("#0 and #1");
		}
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));
		if(ProgrammingFactory.getRemoteInstance().exists(filter)) {
			txtName.requestFocus(true);
			throw new EASBizException(new NumericExceptionSubItem("1", errrMsg + name_txt + "�Ѿ����ڣ������ظ���"));
		}
		// ����ʱȥ����¼���Ƶ�ǰ��ո�
		int rowCount = kdtEntries.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			IRow row = kdtEntries.getRow(i);
			Object name = row.getCell("name").getValue();
			
			Object number = kdtEntries.getCell(i, "longNumber").getValue();
    		Object head = kdtEntries.getCell(i, HEADNUMBER).getValue();
    		if(number == null || number.toString().trim() == null){
	    		throw new ProgrammingException(ProgrammingException.NUMBER_NULL , new Object[]{new Integer(i+1)});
        	}
    		
    		Object level = kdtEntries.getCell(i, "level").getValue();
    		int level_int = new Integer(level.toString()).intValue();
			if(level_int != 1){
	    		String ln = number.toString();
	    		if(ln.length() == (head.toString().length() + 1)){
	    			throw new ProgrammingException(ProgrammingException.NUMBER_NULL , new Object[]{new Integer(i+1)});
				}
			}
			if (name != null && name.toString().trim().length() > 0) {
				row.getCell("name").setValue(name.toString().trim());
			}
			row.getCell("sortNumber").setValue(new Integer(i));
		}

		if (txtNumber.getText() == null || "".equals(txtNumber.getText())) {
			txtNumber.setText(getDateString());
		}
	}
	
    /**
     * output actionSubmit_actionPerformed
     */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		
//		verifyControlParam();
		verifyRedData();
		verifyDataBySave();
		verifyAllInProgramming();
		if (StringUtils.isEmpty(txtProjectName.getText())) {
			throw new EASBizException(new NumericExceptionSubItem("1", "������Ŀ����Ϊ��"));
		}
		if (StringUtils.isEmpty(txtVersion.getText())) {
			throw new EASBizException(new NumericExceptionSubItem("1", "�汾�Ų���Ϊ��"));
		}
		//����ʱȥ����¼���Ƶ�ǰ��ո�
		int rowCount = kdtEntries.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			IRow row = kdtEntries.getRow(i);
			Object name = row.getCell("name").getValue();
			if (name != null && name.toString().trim().length() > 0) {
				row.getCell("name").setValue(name.toString().trim());
			}
			
			row.getCell("sortNumber").setValue(new Integer(i));
		}

		if (txtNumber.getText() == null || "".equals(txtNumber.getText())) {
			txtNumber.setText(getDateString());
		}
		
		String curVersion = txtVersion.getText();
		String versionGroup = txtVersionGroup.getText();
		
		if(this.txtVersion.getBigDecimalValue()!=null&&this.txtVersion.getBigDecimalValue().compareTo(new BigDecimal(1))>0){
			if(this.txtDescription.getText()==null||"".equals(this.txtDescription.getText().trim())){
				FDCMsgBox.showWarning(this,"����˵������Ϊ�գ�");
				SysUtil.abort();
			}
			setCompareTable(this.kdtVerCompareEntry);
			if(this.kdtCompareEntry.getRowCount()!=this.kdtVerCompareEntry.getRowCount()){
				FDCMsgBox.showWarning(this,"���Ƚ��е���ԭ����ȡ������");
				SysUtil.abort();
			}
			for(int i=0;i<this.kdtCompareEntry.getRowCount();i++){
				String srcPc=this.kdtCompareEntry.getRow(i).getCell("programmingContract").getValue().toString();
				String srcContent=this.kdtCompareEntry.getRow(i).getCell("content").getValue().toString();
				
				String pc=this.kdtVerCompareEntry.getRow(i).getCell("programmingContract").getValue().toString();
				String content=this.kdtVerCompareEntry.getRow(i).getCell("content").getValue().toString();
				if(!pc.equals(srcPc)||!content.equals(srcContent)){
					FDCMsgBox.showWarning(this,"���Ƚ��е���ԭ����ȡ������");
					SysUtil.abort();
				}
			}
			ProgrammingCollection col=ProgrammingFactory.getRemoteInstance().getProgrammingCollection("select aimCost.measureStage.id from where versionGroup = '" + this.editData.getVersionGroup()+"' and version="+(this.txtVersion.getBigDecimalValue().subtract(new BigDecimal(1))));
			if(col.size()>0){
				if(((AimCostInfo)this.prmtAimCost.getValue()).getMeasureStage().getId().toString().equals(col.get(0).getAimCost().getMeasureStage().getId().toString())){
					for(int i=0;i<this.kdtCompareEntry.getRowCount();i++){
						String reson=(String)this.kdtCompareEntry.getRow(i).getCell("reason").getValue();
						if (reson == null||"".equals(reson.trim())) {
							FDCMsgBox.showWarning(this,"��"+(i+1)+"�е���ԭ����Ϊ�գ�");
							this.kdtCompareEntry.getEditManager().editCellAt(i, this.kdtCompareEntry.getColumnIndex("reason"));
							SysUtil.abort();
						} 
					}
				}else{
					for(int i=0;i<this.kdtCompareEntry.getRowCount();i++){
						String content=(String)this.kdtCompareEntry.getRow(i).getCell("content").getValue();
						String reson=(String)this.kdtCompareEntry.getRow(i).getCell("reason").getValue();
						if (content != null) {
							if(content.indexOf("�滮�������")>=0){
								BigDecimal amount=new BigDecimal(content.substring(content.indexOf("�滮�������")+6, content.indexOf("Ԫ")));
								if(amount.compareTo(new BigDecimal(1000000))>=0&&(reson == null||"".equals(reson.trim()))){
									FDCMsgBox.showWarning(this,"��"+(i+1)+"�е���ԭ����Ϊ�գ�");
									this.kdtCompareEntry.getEditManager().editCellAt(i, this.kdtCompareEntry.getColumnIndex("reason"));
									SysUtil.abort();
								}
							}else{
								BigDecimal amount=new BigDecimal(content.substring(content.indexOf("�滮������")+6, content.indexOf("Ԫ")));
								if(amount.compareTo(new BigDecimal(1000000))>=0&&(reson == null||"".equals(reson.trim()))){
									FDCMsgBox.showWarning(this,"��"+(i+1)+"�е���ԭ����Ϊ�գ�");
									this.kdtCompareEntry.getEditManager().editCellAt(i, this.kdtCompareEntry.getColumnIndex("reason"));
									SysUtil.abort();
								}
							}
						} 
					}
				}
			}
		}
		
		super.actionSubmit_actionPerformed(e);
		if (isBillModify()) {
			((IProgramming) getBizInterface()).billModify(versionGroup, curVersion); // ά�����°汾�ֶ�
		}
		
		setAuditBtnEnable();
		sumClass.appendProFootRow(null, null);
		
		if (isBillModify()) {
			ProgrammingListUI parentUI = (ProgrammingListUI) getUIContext().get("parent");
			parentUI.refreshList();
			this.getUIContext().remove(ProgrammingListUI.IS_MODIFY);
		}
	}

	/***
	 * ��֤1���Ƿ���������Ŀ��ɱ��Ŀ�Ŀ���Ѿ���ӵ��˺�Լ�����
	 * 2���Ƿ����Ժ�Լ������Ŀ��ɱ�����Ŀ��ɱ��С�
	 */
	private void verifyAllInProgramming() {
		if(this.prmtAimCost.getValue() == null)return;
		
		AimCostInfo aimInfo = (AimCostInfo)this.prmtAimCost.getValue();
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("costAccount.id");
		sel.add("costAccount.name");
		sel.add("costAccount.longNumber");
		sel.add("costAmount");
		sel.add("costAccount.number");
		sel.add("costAccount.isProgramming");
		view.setSelector(sel);
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("head.id", aimInfo.getId().toString());
		view.setFilter(filter);
		CostEntryCollection costColl = null;
		try {
			costColl = CostEntryFactory.getRemoteInstance().getCostEntryCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		//Ŀ��ɱ� �ɱ���ĿSET
		Set costAccountSet = new  HashSet();
		for(int i = 0 ; i < costColl.size() ; i++){
			CostEntryInfo info = costColl.get(i);
			String costEntryNumber = info.getCostAccount().getLongNumber();
			if(!costAccountSet.contains(costEntryNumber)&&info.getCostAmount()!=null&&info.getCostAmount().compareTo(FDCHelper.ZERO)!=0){
				costAccountSet.add(costEntryNumber);
			}
		}
		
		//Ŀ��ɱ����� - Ŀ��ɱ����MAP
		Map costAccountAmount = new HashMap();
		for(Iterator it = costAccountSet.iterator(); it.hasNext();){
			String number = it.next().toString();
			BigDecimal amount =FDCHelper.ZERO;
			for(int i = 0 ; i < costColl.size() ; i++){
				CostEntryInfo info = costColl.get(i);
				if(number.equals(info.getCostAccount().getLongNumber())){
					if(info.getCostAmount()!=null){
						amount = amount.add(info.getCostAmount());
					}
				}
			}
			costAccountAmount.put(number, amount);
		}
		
		
		//��Լ��ܰ����ĳɱ���Ŀ
		Set prmCostSet = new HashSet();
		ProgrammingContractCollection  pmcoll = this.editData.getEntries();
		for(int i = 0 ; i < pmcoll.size();i++){
			ProgrammingContractInfo pmInfo = pmcoll.get(i);
//			if(pmInfo.getParent()==null)continue;
			ProgrammingContracCostCollection  pccColl = pmInfo.getCostEntries();
			for(int j = 0 ; j < pccColl.size() ; j++){
				ProgrammingContracCostInfo pccInfo = pccColl.get(j);
				if(pccInfo.getGoalCost()!=null&&pccInfo.getGoalCost().compareTo(FDCHelper.ZERO)!=0){
					prmCostSet.add(pccInfo.getCostAccount().getLongNumber());
				}
			}
		}
		
		//Ŀ��ɱ��ﲻ������Լ�滮��ĳɱ���Ŀ
		for(int i = 0 ; i < costColl.size() ; i++){
			CostEntryInfo info = costColl.get(i);
			String costEntryNumber = info.getCostAccount().getLongNumber();
			if(!info.getCostAccount().isIsProgramming()){
				continue;
			}
			if(!(info.getCostAmount()!=null&&info.getCostAmount().compareTo(FDCHelper.ZERO)!=0)){
				continue;
			}
			if(!prmCostSet.contains(costEntryNumber)){//����ڿ�ܺ�Լ���Ҳ�������ֹ
				FDCMsgBox.showWarning(this, "�ɱ���Ŀ��\""+info.getCostAccount().getLongNumber().replaceAll("!", ".")+"\"û�а����ں�Լ����ڣ����飡");
				SysUtil.abort();
			}else{//����Ƚ���ֵ��ȷ�
				BigDecimal  allAssign = getAllContractAssign(info.getCostAccount());
				//Ŀ��ɱ���ֵ
				BigDecimal amount = (BigDecimal)costAccountAmount.get(costEntryNumber);
				if(allAssign.compareTo(amount)!=0){
					FDCMsgBox.showWarning(this, "�ɱ���Ŀ\""+info.getCostAccount().getLongNumber().replaceAll("!", ".")+"\"��Ŀ��ɱ�������ͬ��ܽ�������飡");
					SysUtil.abort();
				}
			}
		}
		
		//��Լ�滮�ﲻ����Ŀ��ɱ���Ŀ�Ŀ
		for( Iterator  it = prmCostSet.iterator() ;it.hasNext() ; ){
			String number = it.next().toString();
			if(!costAccountSet.contains(number)){//����ڿ�ܺ�Լ���Ҳ�������ֹ
				FDCMsgBox.showWarning(this, "�ɱ���Ŀ��\""+number.replaceAll("!", ".")+"\"δ������Ŀ��ɱ��ڣ����Ժ�Լ��ܲ��ܰ��������飡");
				SysUtil.abort();
			}
		}
		
		
	}
	// �Ƿ��޶�
	private boolean isBillModify() {
		Boolean isSet = (Boolean) getUIContext().get(ProgrammingListUI.IS_MODIFY);
		return isSet != null && isSet.booleanValue();
	}
	
	// ���ƽ��ܴ��ڹ滮���
	// private void checkAmount(int rowIndex) throws EASBizException {
	// if (rowIndex < 0) {
	// return;
	// }
	//		
	// IRow row = kdtEntries.getRow(rowIndex);
	// Object cellValue = row.getCell("amount").getValue();
	// BigDecimal amount = checkNumber(cellValue, rowIndex, "amount", 0); // �滮���
	//		
	//
	// cellValue = row.getCell("controlAmount").getValue();
	// BigDecimal ctrlAmount = checkNumber(cellValue, rowIndex, "controlAmount", 0); // ���ƽ��
	//		
	// if (ctrlAmount.compareTo(amount) > 0) {
	// StringBuffer msg = new StringBuffer();
	// msg.append("�� ").append(rowIndex + 1).append("�� ���ƽ��ܴ��ڹ滮���");
	// throw new EASBizException(new NumericExceptionSubItem("1", msg.toString()));
	// }
	// }
    
	/**
	 * �ύǰУ�����ݣ����ơ���¼���뼰����
	 * @throws Exception
	 */
    public void verifyDataBySave() throws Exception {
    	veryfyForSave();
    	int rowCount = kdtEntries.getRowCount();
		//Ŀ��ɱ���Ϊ��20111130
		if(this.prmtAimCost.getValue() ==null){
			this.prmtAimCost.requestFocus(true);
			FDCMsgBox.showInfo(this,"Ŀ��ɱ��汾���Ʋ���Ϊ�գ�");
			SysUtil.abort();
		}
		
    	for(int i = 0 ; i < rowCount ; i++){
			// checkAmount(i);
    		
    		Object number = kdtEntries.getCell(i, "longNumber").getValue();
    		Object head = kdtEntries.getCell(i, HEADNUMBER).getValue();
    		if(number == null || number.toString().trim() == null){
	    		throw new ProgrammingException(ProgrammingException.NUMBER_NULL , new Object[]{new Integer(i+1)});
        	}
    		
    		Object level = kdtEntries.getCell(i, "level").getValue();
    		int level_int = new Integer(level.toString()).intValue();
			if(level_int != 1){
	    		String ln = number.toString();
	    		if(ln.length() == (head.toString().length() + 1)){
	    			throw new ProgrammingException(ProgrammingException.NUMBER_NULL , new Object[]{new Integer(i+1)});
				}
			}
			
			String longNumber = number.toString().trim();
			if(longNumber.length() > 80){
				throw new EASBizException(new NumericExceptionSubItem("1", "��¼��"+(i+1)+"�У����볬�������������룡"));
			}
    		
    		Object proName = kdtEntries.getCell(i, "name").getValue();
    		if(proName == null || proName.toString().trim() == null){
    			throw new ProgrammingException(ProgrammingException.NAME_NULL , new Object[]{new Integer(i+1)});
        	}
    		
    		if(proName != null && proName.toString().trim().length() > 80){
    			throw new EASBizException(new NumericExceptionSubItem("1", "��¼��"+(i+1)+"�У���ܺ�Լ���Ƴ�����"));
        	}
    		
    		Object longName = kdtEntries.getCell(i, "longName").getValue();
//    		if(longName != null && !StringUtils.isEmpty(longName.toString())){
//    			if(longName.toString().length() > 255){
//    				throw new EASBizException(new NumericExceptionSubItem("1", "��¼��"+(i+1)+"�У���ܺ�Լ�����Ƴ���\n���޸Ŀ�ܺ�Լ�������ݣ�"));
//    			}
//    		}
    		
    		String lnumber = number.toString();
    		String name = proName.toString().trim();
    		
    		for(int j = 0 ; j < rowCount ; j++){
    			if(j == i)
    				continue;
    			
    			Object number_2 = kdtEntries.getCell(j, "longNumber").getValue();
        		Object proName_2 = kdtEntries.getCell(j, "name").getValue();
        		
        		if(number_2 != null && number_2.toString().trim().length() > 0){
        			if(lnumber.equals(number_2.toString().trim())){
        				throw new ProgrammingException(ProgrammingException.NUMBER_REPEAT , new Object[]{new Integer(i+1) , new Integer(j+1) , "����"});
        			}
        		}
        		
        		if(proName_2 != null && proName_2.toString().trim().length() > 0){
        			if(name.equals(proName_2.toString().trim())){
        				throw new ProgrammingException(ProgrammingException.NUMBER_REPEAT , new Object[]{new Integer(i+1) , new Integer(j+1) , "����"});
        			}
        		}
    		}
    	}
    	for(int i=0;i<this.kdtEntries.getRowCount();i++){
    		Object oldLongNumber = kdtEntries.getCell(i, LONGNUMBER).getValue();
    		if(i+1 <kdtEntries.getRowCount()){
    			Object nextHeadNumber = kdtEntries.getCell(i+1, HEADNUMBER).getValue();
    			if(!oldLongNumber.equals(nextHeadNumber)){
    				if (kdtEntries.getCell(i, "contractType").getValue()== null){
    					throw new EASBizException(new NumericExceptionSubItem("1", "��¼��"+(i+1)+"�У���ͬ���Ͳ���Ϊ�գ�"));
    				}
    				if((Boolean)kdtEntries.getRow(i).getCell("isInput").getValue()){
    					if (kdtEntries.getCell(i, "quantities").getValue()== null){
        					throw new EASBizException(new NumericExceptionSubItem("1", "��¼��"+(i+1)+"�У�ʵ�幤��������Ϊ�գ�"));
        				}
        				if (kdtEntries.getCell(i, "unit").getValue()== null){
        					throw new EASBizException(new NumericExceptionSubItem("1", "��¼��"+(i+1)+"�У���λ����Ϊ�գ�"));
        				}
    				}
    			}
    		}else{
    			if (kdtEntries.getCell(i, "contractType").getValue()== null){
					throw new EASBizException(new NumericExceptionSubItem("1", "��¼��"+(i+1)+"�У���ͬ���Ͳ���Ϊ�գ�"));
				}
    			if((Boolean)kdtEntries.getRow(i).getCell("isInput").getValue()){
					if (kdtEntries.getCell(i, "quantities").getValue()== null){
    					throw new EASBizException(new NumericExceptionSubItem("1", "��¼��"+(i+1)+"�У�ʵ�幤��������Ϊ�գ�"));
    				}
    				if (kdtEntries.getCell(i, "unit").getValue()== null){
    					throw new EASBizException(new NumericExceptionSubItem("1", "��¼��"+(i+1)+"�У���λ����Ϊ�գ�"));
    				}
				}
    		}
    	}
    }
    
    /**
     * ��ȡ��ǰʱ���ַ������������ñ���
     */
    private String getDateString(){
    	Calendar cal = Calendar.getInstance();
    	Timestamp ts   =  new Timestamp(cal.getTimeInMillis());
    	Date bizDate = new Date(ts.getTime());
    	return bizDate.toString();
    }
    
    /**
     * ������������ʾ����Ч������ǰ��ӿո�
     */
    private void setNameDisplay(){
		int rowCount = kdtEntries.getRowCount();
		for(int i = 0 ; i < rowCount ; i++){
			IRow row = kdtEntries.getRow(i);
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			Object name =  row.getCell("name").getValue();
			if(name != null && name.toString().trim().length() > 0){
				String blank = setNameIndent(level);
				row.getCell("name").setValue(blank+name.toString());
			}
		}
	}
    
    //���ǵ�����ķ���
    protected void setAuditButtonStatus(String oprtType) {
    }
    
    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	checkHighVersion();
        super.actionEdit_actionPerformed(e);
        setSmallBtnEnable();
        setCellEditorForTable();
//		if (isCiting()) {
//			prmtAimCost.setEnabled(false);
//		} else {
//			prmtAimCost.setEnabled(true);
//		}
        handleOldData();
        
		setBuildPrice();
    }
    
    private void checkHighVersion() throws Exception {
		BigDecimal version = editData.getVersion().add(FDCHelper.ONE);
		String versionGroup = editData.getVersionGroup();
		
		String oql = "where version = '".concat(version.toString()).concat("' and versionGroup = '")
			.concat(versionGroup).concat("'");
		if (getBizInterface().exists(oql)) {
			throw new EASBizException(new NumericExceptionSubItem("1", "���ڸ��߰汾���ܽ��д˲���"));
		}
	}

    /**
     * output actionAudit_actionPerformed
     */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if (FDCBillStateEnum.SAVED.equals(editData.getState())) {
			FDCMsgBox.showInfo("����״̬���ݲ�����������");
			SysUtil.abort();
		}
		if (FDCBillStateEnum.SUBMITTED.equals(editData.getState())) {
			if (editData.getId() != null) {
				ProgrammingFactory.getRemoteInstance().audit(editData.getId());
				actionAudit.setEnabled(false);
				actionUnAudit.setEnabled(true);
				actionEdit.setEnabled(false);
				editData.setState(FDCBillStateEnum.AUDITTED);
				MsgBox.showInfo("�����ɹ���");
			}
		}
		handleOldData();
	}

    /**
     * output actionUnAudit_actionPerformed
     */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		if (FDCBillStateEnum.AUDITTED.equals(editData.getState())) {
			if (editData.getId() != null) {
				boolean isLastVersion = ProgrammingFactory.getRemoteInstance().isLastVersion(new ObjectUuidPK(editData.getId().toString()));
				if (!isLastVersion) {
					throw new EASBizException(new NumericExceptionSubItem("1", "�����°汾���ܷ�����"));
				}
				checkHighVersion();
				
				ProgrammingFactory.getRemoteInstance().unAudit(editData.getId());
				actionAudit.setEnabled(true);
				actionUnAudit.setEnabled(false);
				if(this.getOprtState().equals(STATUS_VIEW))
					actionEdit.setEnabled(true);
				editData.setState(FDCBillStateEnum.SUBMITTED);
				MsgBox.showInfo("�������ɹ���");
			}
		}
		handleOldData();
	}
    
    /**
     * ȫ���������������¼��ʱ����¼��Ҫʧȥ����
     */
    private void setMouseClick() {
		this.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
			}
			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseExited(MouseEvent arg0) {
			}
			public void mousePressed(MouseEvent arg0) {
			}
			public void mouseReleased(MouseEvent arg0) {
				if(!arg0.getComponent().equals(kdtEntries)){
					setKDTableLostFocus();
				}
			}
    	});
    	
    	Component[] com = this.getComponents();
    	for(int i = 0 ; i < com.length ; i++){
    		if(!com[i].equals(kdtEntries)){
    			com[i].addMouseListener(new MouseListener(){
    				public void mouseClicked(MouseEvent arg0) {
    				}
    				public void mouseEntered(MouseEvent arg0) {
    				}
    				public void mouseExited(MouseEvent arg0) {
    				}
    				public void mousePressed(MouseEvent arg0) {
    				}
    				public void mouseReleased(MouseEvent arg0) {
    					if(!arg0.getComponent().equals(kdtEntries)){
    						setKDTableLostFocus();
    					}
    				}
    	    	});
    		}
    	}
    	
    	this.txtName.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
			}
			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseExited(MouseEvent arg0) {
			}
			public void mousePressed(MouseEvent arg0) {
			}
			public void mouseReleased(MouseEvent arg0) {
				if(!arg0.getComponent().equals(kdtEntries)){
					setKDTableLostFocus();
				}
			}
    	});
    	
    	this.txtProjectName.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
			}
			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseExited(MouseEvent arg0) {
			}
			public void mousePressed(MouseEvent arg0) {
			}
			public void mouseReleased(MouseEvent arg0) {
				if(!arg0.getComponent().equals(kdtEntries)){
					setKDTableLostFocus();
				}
			}
    	});
    	
    	this.txtVersion.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
			}
			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseExited(MouseEvent arg0) {
			}
			public void mousePressed(MouseEvent arg0) {
			}
			public void mouseReleased(MouseEvent arg0) {
				if(!arg0.getComponent().equals(kdtEntries)){
					setKDTableLostFocus();
				}
			}
    	});
    	
    	this.txtAimCoustVersion.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
			}
			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseExited(MouseEvent arg0) {
			}
			public void mousePressed(MouseEvent arg0) {
			}
			public void mouseReleased(MouseEvent arg0) {
				if(!arg0.getComponent().equals(kdtEntries)){
					setKDTableLostFocus();
				}
			}
    	});
	}

	protected void attachListeners() {
		// TODO Auto-generated method stub
		
	}

	protected void detachListeners() {
		// TODO Auto-generated method stub
		
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ProgrammingFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return kdtEntries;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	
	protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.contract.programming.client.ProgrammingEditUI.class.getName();
    }
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("buildArea"));
		sic.add(new SelectorItemInfo("soldArea"));
		sic.add(new SelectorItemInfo("aimCost.*"));
		sic.add(new SelectorItemInfo("aimCost.measureStage.id"));
		
		sic.add(new SelectorItemInfo("project.name"));
		sic.add(new SelectorItemInfo("entries.longNumber"));
		sic.add(new SelectorItemInfo("entries.name"));
		sic.add(new SelectorItemInfo("entries.*"));
		sic.add(new SelectorItemInfo("entries.parent.*"));
		sic.add(new SelectorItemInfo("entries.economyEntries.*"));
		sic.add(new SelectorItemInfo("entries.economyEntries.paymentType.*"));
		sic.add(new SelectorItemInfo("entries.costEntries.*"));
		sic.add(new SelectorItemInfo("entries.costEntries.costAccount.*"));
		sic.add(new SelectorItemInfo("entries.costEntries.costAccount.curProject.*"));
		sic.add(new SelectorItemInfo("entries.amount"));
		sic.add(new SelectorItemInfo("entries.controlAmount"));
		//ȡԤ����� 20120420
		sic.add(new SelectorItemInfo("entries.estimateAmount"));
		sic.add(new SelectorItemInfo("entries.balance"));
		sic.add(new SelectorItemInfo("entries.controlBalance"));
		sic.add(new SelectorItemInfo("entries.signUpAmount"));
		sic.add(new SelectorItemInfo("entries.changeAmount"));
		sic.add(new SelectorItemInfo("entries.settleAmount"));
		sic.add(new SelectorItemInfo("entries.citeVersion"));
		sic.add(new SelectorItemInfo("entries.isCiting"));
		sic.add(new SelectorItemInfo("entries.attachment"));
		sic.add(new SelectorItemInfo("entries.description"));
		sic.add(new SelectorItemInfo("entries.id"));
		sic.add(new SelectorItemInfo("entries.number"));
		sic.add(new SelectorItemInfo("entries.level"));
		sic.add(new SelectorItemInfo("entries.parent.longNumber"));
		sic.add(new SelectorItemInfo("entries.sortNumber"));
		sic.add(new SelectorItemInfo("entries.displayName"));
		sic.add(new SelectorItemInfo("entries.workcontent"));
		sic.add(new SelectorItemInfo("entries.supMaterial"));
		sic.add(new SelectorItemInfo("entries.inviteWay"));
		sic.add(new SelectorItemInfo("entries.inviteOrg.*"));
		sic.add(new SelectorItemInfo("entries.buildPerSquare"));
		sic.add(new SelectorItemInfo("entries.soldPerSquare"));
		sic.add(new SelectorItemInfo("version"));
		sic.add(new SelectorItemInfo("versionGroup"));
	    sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
		sic.add(new SelectorItemInfo("CU.id"));
		sic.add(new SelectorItemInfo("orgUnit.id"));
		
		sic.add(new SelectorItemInfo("entries.contractType.*"));
		sic.add(new SelectorItemInfo("entries.programming.*"));
		
		sic.add(new SelectorItemInfo("isLatest"));
		sic.add(new SelectorItemInfo("entries.isInvite"));
		
		sic.add(new SelectorItemInfo("compareEntry.*"));
		sic.add(new SelectorItemInfo("description"));
		return sic;
	}      

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.contract.programming.ProgrammingInfo objectValue = new com.kingdee.eas.fdc.contract.programming.ProgrammingInfo();
		objectValue.setId(BOSUuid.create(objectValue.getBOSType()));
        objectValue.setVersion(new BigDecimal("1.0"));
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        objectValue.setVersionGroup(Uuid.create().toString());
        
        return objectValue;
    }
    
    protected void setTableToSumField() {
		HashMap sumFields = getSumFields();
		if (sumFields == null) return;
		//ȡ�����е�KDTable���ϼ�����Ϣ
		Iterator sumFieldsIterator = sumFields.keySet().iterator();
		if (sumFieldsIterator.hasNext()) {
			KDTable table = (KDTable) sumFieldsIterator.next();
			String[] sumColNames = (String[])sumFields.get(table);
			super.setTableToSumField(table, sumColNames);			
		}
	}
    
    protected HashMap getSumFields() {
		HashMap sumFields = new HashMap();
		// Ŀ��ɱ�ҳǩ�ϼ���
		sumFields.put(this.kdtCostAccount, new String[] { "aimCost", "assigned", "assigning"});
		return sumFields;
	}
    
    private void setProTableToSumField() {
		HashMap sumFields = getProSumFields();
		if (sumFields == null) return;
		//ȡ�����е�KDTable���ϼ�����Ϣ
		Iterator sumFieldsIterator = sumFields.keySet().iterator();
		if (sumFieldsIterator.hasNext()) {
			KDTable table = (KDTable) sumFieldsIterator.next();
			String[] sumColNames = (String[])sumFields.get(table);
			sumClass.setProTableToSumField(table, sumColNames);			
		}
	}
    
    private HashMap getProSumFields(){
    	HashMap sumFields = new HashMap();
    	sumFields.put(this.kdtEntries, new String[] {"amount", "controlAmount", "signUpAmount", "changeAmount", "settleAmount", "balance",
    			"controlBalance", "buildPerSquare", "soldPerSquare" });
    	return sumFields;
    }
    
    protected void handleOldData() {
		if(!(getOprtState()==STATUS_VIEW)){
			FDCUIWeightWorker.getInstance().addWork(new IFDCWork(){
				public void run() {
					storeFields();
					initOldData(editData);
				}
			});
		}
	}
    
    protected void kDTabbedPane1_stateChanged(ChangeEvent e) throws Exception {
    	if(this.txtVersion.getBigDecimalValue()!=null){
    		if(this.txtVersion.getBigDecimalValue().compareTo(new BigDecimal(1))>0&&kDTabbedPane1.getSelectedIndex() == 3){
        		loadCostAccountToCostEntry();
    		}else if(this.txtVersion.getBigDecimalValue().compareTo(new BigDecimal(1))==0&&kDTabbedPane1.getSelectedIndex() == 1){
    			loadCostAccountToCostEntry();
    		}
    	}
    }
    
    /**
     * �л����ɱ�ҳǩʱ��������
     */
    private void loadCostAccountToCostEntry(){
    	loadCostAccountTabData();
		setCostNumberFormat();
		createCostTree();
		
		List rows = this.kdtCostAccount.getBody().getRows();
	    Collections.sort(rows, new TableCellComparator(kdtCostAccount.getColumnIndex("costNumber"), KDTSortManager.SORT_ASCEND));
	    kdtCostAccount.setRefresh(false);
    }
    
    /**
	 * ���óɱ�ҳǩ�ĳɱ���Ŀ��������ʾ��ʽ
	 */
	private void setCostNumberFormat(){
		ObjectValueRender render = new ObjectValueRender();
		render.setFormat(new IDataFormat() {
			public String format(Object o) {
				if (o instanceof String) {
					return o.toString().replace('!', '.');
				} else
					return null;
			}
		});
		kdtCostAccount.getColumn("costNumber").setRenderer(render);
	}
	
	/**
	 * ���óɱ�ҳǩ������
	 */
	private void createCostTree() {
		int maxLevel = 0;
		int[] levelArray = new int[kdtCostAccount.getRowCount()];
		for (int i = 0; i < kdtCostAccount.getRowCount(); i++) {
			IRow row = kdtCostAccount.getRow(i);
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			levelArray[i] = level;
			row.setTreeLevel(level - 1);
		}
		for (int i = 0; i < kdtCostAccount.getRowCount(); i++) {
			maxLevel = Math.max(levelArray[i], maxLevel);
		}
		kdtCostAccount.getTreeColumn().setDepth(maxLevel);
		kdtCostAccount.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}
    
    /**
	 * ���سɱ���Ŀҳǩ������ʾ
	 */
	private void loadCostAccountTabData(){
		kdtCostAccount.removeRows();
		kdtCostAccount.getStyleAttributes().setLocked(true);
		costRowMap=new HashMap();
		costCostRowMap=new HashMap();
		int rowCount = kdtEntries.getRowCount();
		for(int i = 0 ; i < rowCount ; i++){
			ProgrammingContractInfo rowObject = (ProgrammingContractInfo)kdtEntries.getRow(i).getUserObject();
			Object name = kdtEntries.getCell(i, "name").getValue();//��ܺ�Լģ������
			String proName = "";
			String oldName = "";
			if(name != null && name.toString().trim().length() > 0){
				proName = name.toString().trim();
				oldName = name.toString().trim();
			}
			createCostEntriesRow(i, rowObject, proName, oldName);
		}
	}
	
	/**
	 * �����ɱ�ҳǩ��¼��
	 * @param i
	 * @param rowObject
	 * @param proName
	 * @param oldName
	 */
	private void createCostEntriesRow(int i, ProgrammingContractInfo rowObject, String proName, String oldName) {
		if (rowObject != null) {
			ProgrammingContracCostCollection proCol = rowObject.getCostEntries();
			if (proCol.size() > 0) {
				addRowForCost(proName, oldName, proCol);
			}
		}
	}
	
	/**
	 * ����е��ɱ���¼
	 * @param proName
	 * @param oldName
	 * @param proCol
	 */
	private Map costRowMap=null;
	private Map costCostRowMap=null;
	private void addRowForCost(String proName, String oldName, ProgrammingContracCostCollection proCol) {
		for (int i = 0; i < proCol.size(); i++) {
			boolean isHas = false;
			ProgrammingContracCostInfo info = proCol.get(i);
			BigDecimal contractAssign = info.getContractAssign();// ����Լ����
			BigDecimal goalCost = info.getGoalCost();// Ŀ��ɱ�
			if (contractAssign != null && goalCost != null) {
				if(goalCost.compareTo(FDCHelper.ZERO) > 0){
					BigDecimal percent = contractAssign.divide(goalCost, 4, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED);
					if (percent.intValue() <= 0) {
						proName = oldName;
					}else if(percent.intValue() < 100){
						proName = oldName + percent.intValue() + "%";
					}
				}
			}
//			SelectorItemCollection sic = new SelectorItemCollection();
//			sic.add("*");
//			sic.add("curProject.name");
			CostAccountInfo costInfo = info.getCostAccount();
//			try {
//				costInfo = CostAccountFactory.getRemoteInstance().getCostAccountInfo(new ObjectUuidPK(info.getCostAccount().getId()) , sic);
//			} catch (EASBizException e) {
//				logger.error(e);
//			} catch (BOSException e) {
//				logger.error(e);
//			}
//			if(!curProject.getId().equals(costInfo.getCurProject().getId())){
//				proName = costInfo.getCurProject().getName()+"."+proName;
//			}
			if(costRowMap.containsKey(costInfo.getId().toString())){
				IRow row_k=(IRow) costRowMap.get(costInfo.getId().toString());
				
				Object ass = row_k.getCell("assigned").getValue();
				BigDecimal assigned = FDCHelper.ZERO;
				if(ass != null && !StringUtils.isEmpty(ass.toString())){
					assigned = new BigDecimal(ass.toString());
					assigned = assigned.add(contractAssign);
					row_k.getCell("assigned").setValue(assigned);
				}
				Object aim = row_k.getCell("aimCost").getValue();
				BigDecimal aimCost = FDCHelper.ZERO;
				if(aim != null && !StringUtils.isEmpty(aim.toString())){
					aimCost = new BigDecimal(aim.toString());
				}
				row_k.getCell("assigning").setValue(aimCost.subtract(assigned));
			}else{
				List list = new ArrayList();
				getParentCostAccountInfo(costInfo , list);
				addCostAccountParent(list);
				
				IRow row = kdtCostAccount.addRow();
				row.getCell("costNumber").setValue(costInfo.getLongNumber());
				row.getCell("costName").setValue(setNameIndent(costInfo.getLevel())+costInfo.getName());
				row.getCell("aimCost").setValue(goalCost);
				row.getCell("assigned").setValue(contractAssign);
				row.getCell("assigning").setValue(goalCost.subtract(contractAssign));
				row.getCell("level").setValue(costInfo.getLevel() + "");
				row.getCell("proName").setValue(proName);
				
				costRowMap.put(costInfo.getId().toString(), row);
			}
//			String name = null;
//			for (int j = 0; j < kdtCostAccount.getRowCount(); j++) {
//				name = null;
//				IRow row_k = kdtCostAccount.getRow(j);
//				String number = row_k.getCell("costNumber").getValue().toString();
//				Object name_1 = row_k.getCell("proName").getValue();
//				if(name_1 != null)
//					name = name_1.toString();
//				if (number.equals(costInfo.getLongNumber())) {
//					isHas = true;
//					if(name == null){
//						row_k.getCell("proName").setValue(proName);
//					}else{
//						row_k.getCell("proName").setValue(name + "," + proName);
//					}
//					Object ass = row_k.getCell("assigned").getValue();
//					BigDecimal assigned = FDCHelper.ZERO;
//					if(ass != null && !StringUtils.isEmpty(ass.toString())){
//						assigned = new BigDecimal(ass.toString());
//						assigned = assigned.add(contractAssign);
//						row_k.getCell("assigned").setValue(assigned);
//					}
//					Object aim = row_k.getCell("aimCost").getValue();
//					BigDecimal aimCost = FDCHelper.ZERO;
//					if(aim != null && !StringUtils.isEmpty(aim.toString())){
//						aimCost = new BigDecimal(aim.toString());
//					}
//					row_k.getCell("assigning").setValue(aimCost.subtract(assigned));
//				}
//			}
//			if (!isHas) {
//				
//			}
		}
	}
	
	/**
	 * �ݹ��ȡ�ɱ���Ŀ�������ϼ��ɱ���Ŀ
	 * @param info
	 * @param list
	 * @return
	 */
	private CostAccountInfo getParentCostAccountInfo(CostAccountInfo info , List list){
		if(info.getParent() != null){
			CostAccountInfo costInfo = null;
			if(costCostRowMap.containsKey(info.getParent().getId())){
				costInfo=(CostAccountInfo) costCostRowMap.get(info.getParent().getId());
			}else{
				try {
					costInfo = CostAccountFactory.getRemoteInstance().getCostAccountInfo(new ObjectUuidPK(info.getParent().getId()));
				} catch (EASBizException e) {
					logger.error(e);
				} catch (BOSException e) {
					logger.error(e);
				}
				list.add(costInfo);
				costCostRowMap.put(info.getParent().getId(), costInfo);
			}
			return getParentCostAccountInfo(costInfo , list);
		}
		return null;
	}
	
	/**
	 * ��������ϼ��ĳɱ���Ŀ���������ϼ��ɱ���Ŀ
	 * @param list
	 */
	private void addCostAccountParent(List list) {
		for(int i = 0 ; i < list.size() ; i++){
			CostAccountInfo costAccountInfo = (CostAccountInfo)list.get(i);
//			boolean isHas = false;
//			for (int j = 0; j < kdtCostAccount.getRowCount(); j++) {
//				IRow row = kdtCostAccount.getRow(j);
//				String number = row.getCell("costNumber").getValue().toString();
//				if (number.equals(costAccountInfo.getLongNumber())) {
//					isHas = true;
//				}
//			}
//			if(!isHas){
//				IRow row = kdtCostAccount.addRow();
//				row.getCell("costNumber").setValue(costAccountInfo.getLongNumber());
//				row.getCell("costName").setValue(setNameIndent(costAccountInfo.getLevel())+costAccountInfo.getName());
//				row.getCell("level").setValue(costAccountInfo.getLevel() + "");
//			}
			if(!costRowMap.containsKey(costAccountInfo.getId().toString())){
				IRow row = kdtCostAccount.addRow();
				row.getCell("costNumber").setValue(costAccountInfo.getLongNumber());
				row.getCell("costName").setValue(setNameIndent(costAccountInfo.getLevel())+costAccountInfo.getName());
				row.getCell("level").setValue(costAccountInfo.getLevel() + "");
				costRowMap.put(costAccountInfo.getId().toString(), row);
			}
		}
	}
	
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId() != null){
			if(ProgrammingFactory.getRemoteInstance().exists(new ObjectUuidPK(editData.getId().toString()))){
				editData = ProgrammingFactory.getRemoteInstance().getProgrammingInfo(new ObjectUuidPK(editData.getId().toString()) , getSelectors());
				if(editData == null )return;
				setDataObject(editData);
				loadFields();
			}
		}
    	this.dataBinder.storeFields();
		setKDTableLostFocus();
    	createTree();
    	loadCostAccountToCostEntry();
    	// ���ˢ�����ݺϼƲ�������
		setProTableToSumField();
		sumClass.appendProFootRow(null,null);
	}
	
	/**
	 * ��ģ�嵼�빦��
	 */
	public void actionImport_actionPerformed(ActionEvent e) throws Exception {
		ProgrammingContractCollection entries = editData.getEntries();
		for (int i = 0, size = entries.size(); i < size; i++) {
			ProgrammingContractInfo entry = entries.get(i);
			if (entry.isIsCiting()||entry.isIsWTCiting()) {
				throw new EASBizException(new NumericExceptionSubItem("1", 
				"��Լ����д��ڱ����õĿ�ܺ�Լ\n������˲���"));
			}
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put("editData", editData);
		uiContext.put("project", curProject);
		uiContext.put("aimCost", prmtAimCost.getData());
		uiContext.put("dataChangeListener", dataChangeListener);
		IUIWindow ui = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProgrammingImportUI.class.getName(), uiContext, null,	OprtState.VIEW , WinStyle.SHOW_ONLYLEFTSTATUSBAR);
		ui.show();
		
		// ��ĩ�������ϻ���
		// �滮��� = �¼��滮������
		Set leafSet = findLeafSet();
		cleanNotLeafAmout(leafSet);
		totalAmout(leafSet);
		// �����滮��� = �滮��� = ���ƽ��
		for (int i = 0, size = entries.size(); i < size; i++) {
			ProgrammingContractInfo entry = entries.get(i);
			entry.setBalance(entry.getAmount());
		}
		
		setDataObject(editData);
		loadFields();
		if (this.oprtState.equals(STATUS_ADDNEW)) {
			setNameDisplay();
		}
	}
	
	private Set findLeafSet() {
		Set leafNumberSet = new HashSet();
		ProgrammingContractCollection entries = editData.getEntries();
		for (int i = 0, size = entries.size(); i < size; i++) {
			ProgrammingContractInfo info = entries.get(i);
			String longNumber = info.getLongNumber();
			boolean isLeaf = true;
			for (int j = 0; j < size; j++) {
				if (j == i) {
					continue;
				}
				ProgrammingContractInfo infoJ = entries.get(j);
				String longNumberJ = infoJ.getLongNumber();
				if (longNumberJ.startsWith(longNumber)) {
					isLeaf = false;
					break;
				}
			}
			if (isLeaf && longNumber.indexOf('.') != -1) {
				leafNumberSet.add(longNumber);
			}
		}
		return leafNumberSet;
	}
	
	/**
	 * һ�����ڵ��µ����нڵ�
	 * @param numberList
	 * @return
	 */
	private Set findLeafList(List numberList) {
		Set leafNumberList = new HashSet();
		for (int i = 0, size = numberList.size(); i <size; i++) {
			String number = numberList.get(i).toString();
			boolean isLeaf = true;
			for (int j = 0; j < size; j++) {
				if (j == i) {
					continue;
				}
				String numberJ = numberList.get(j).toString();
				if (numberJ.startsWith(number)) {
					isLeaf = false;
					break;
				}
			}
			if (isLeaf) {
				leafNumberList.add(number);
			}
		}
		return leafNumberList;
	}
	
	/**
	 * ��Ҷ�ڵ�ݹ������𼶻���
	 * @param leafSet
	 */
	private void totalAmout(Set leafSet) {
		if (leafSet.isEmpty()) {
			return;
		}
		
		Set leafParentSet = new HashSet(); // ��ǰҶ�ڵ�ĸ��ڵ�
		for (Iterator it = leafSet.iterator(); it.hasNext();) {
			BigDecimal leafAmount = FDCHelper.ZERO; // ��ǰҶ�ڵ���
			String leafLongNumber = it.next().toString();
			ProgrammingContractCollection entries = editData.getEntries();
			for (int j = 0, sizeJ = entries.size(); j < sizeJ; j++) {
				ProgrammingContractInfo entry = entries.get(j);
				if (leafLongNumber.equals(entry.getLongNumber())) {
					leafAmount = entry.getAmount();
					break;
				}
			}
			ProgrammingContractInfo parentEntry = getParentEntry(leafLongNumber);
			if (parentEntry != null) {
				parentEntry.setAmount(leafAmount.add(parentEntry.getAmount()));
				leafParentSet.add(parentEntry.getLongNumber());
			}
		}
		
		List nodeList = new ArrayList();
		nodeList.addAll(leafParentSet);
		totalAmout(findLeafList(nodeList));
	}
	
	/**
	 * ���з�Ҷ�ڵ��������㣬ֱ�Ӵ�Ҷ�ڵ���ܡ�
	 * @param leafSet
	 */
	private void cleanNotLeafAmout(Set leafSet) {
		ProgrammingContractCollection entries = editData.getEntries();
		for (int i = 0, size = entries.size(); i < size; i++) {
			ProgrammingContractInfo entry = entries.get(i);
			String longNumber = entry.getLongNumber();
			if (!leafSet.contains(longNumber)) {
				// �������ӽڵ�ĸ��ڵ㲻ɾ��
				boolean hasChild = false;
				for (int j = 0; j < size; j++) {
					if (i == j) {
						continue;
					}
					if (entries.get(j).getLongNumber().startsWith(longNumber)) {
						hasChild = true;
						break;
					}
				}
				if (hasChild) {
					entry.setAmount(FDCHelper.ZERO);
				}
			}
		}
	}
	
	private ProgrammingContractInfo getParentEntry(String subLongNumber) {
		ProgrammingContractCollection entries = editData.getEntries();
		for (int i = 0, size = entries.size(); i < size; i++) {
			ProgrammingContractInfo entry = entries.get(i);
			if (entry.getLongNumber().equals(subLongNumber)) {
				return entry.getParent();
			}
		}
		return null;
	}
	
	/**
	 * ���Ϊģ�幦��
	 */
	public void actionExportPro_actionPerformed(ActionEvent e) throws Exception {
		verifyDataBySave();
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			IRow row = kdtEntries.getRow(i);
			row.getCell("sortNumber").setValue(new Integer(i));
		}
		this.dataBinder.storeFields();
		verifyRedData();
		UIContext uiContext = new UIContext(this);
		uiContext.put("Programming", editData);
		IUIWindow ui = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProgrammingExportUI.class.getName(), uiContext, null,	OprtState.EDIT);
		ui.show();
	}

	private void createCostCentertF7() {
		KDBizPromptBox f7 = new KDBizPromptBox();
		f7.setVisible(true);
		f7.setEnabled(true);
		f7.setDisplayFormat("$name$");
		f7.setCommitFormat("$name$");
		f7.setEditFormat("$name$");
		f7.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterOrgUnitQuery");
		EntityViewInfo view = f7.getEntityViewInfo();
		if (view == null) {
			view = new EntityViewInfo();
		}
		if (view.getFilter() == null) {
			view.setFilter(new FilterInfo());
		}
		Set idSet = null;
		try {
			idSet = FDCUtils.getAuthorizedOrgs(null);
		} catch (Exception e) {
			logger.error(e);
		}
		view.getFilter().getFilterItems().add(new FilterItemInfo("COSTCENTERORGUNIT.id", idSet, CompareType.INCLUDE));
		f7.setEntityViewInfo(view);

		KDTDefaultCellEditor kdtEntriese_ORG_CellEditor = new KDTDefaultCellEditor(f7);
		kdtEntries.getColumn("inviteOrg").setEditor(kdtEntriese_ORG_CellEditor);
		ObjectValueRender kdtEntries_ORG_OVR = new ObjectValueRender();
		kdtEntries_ORG_OVR.setFormat(new BizDataFormat("$name$"));
		kdtEntries.getColumn("inviteOrg").setRenderer(kdtEntries_ORG_OVR);
	}
	
	/**
	 * ����������ͬ�ɱ���Ŀ��"����Լ����"���֮��
	 */
	private BigDecimal getAllContractAssign(CostAccountInfo caInfo) {
		ProgrammingContractCollection pcCollection  = this.editData.getEntries();
		BigDecimal allContractAssign = FDCHelper.ZERO;
		for (int i = 0; i < pcCollection.size(); i++) {
			ProgrammingContractInfo programmingContractInfo = pcCollection.get(i);
				ProgrammingContracCostCollection costEntries = programmingContractInfo.getCostEntries();
				for (int j = 0; j < costEntries.size(); j++) {
					ProgrammingContracCostInfo pccInfo = costEntries.get(j);
					CostAccountInfo costAccountInfo = pccInfo.getCostAccount();
					if (costAccountInfo != null) {
						if(costAccountInfo.getLongNumber()!=null){
							if (costAccountInfo.getLongNumber().equals(caInfo.getLongNumber())) {
								BigDecimal contractAssign = pccInfo.getContractAssign();
								if (contractAssign == null) {
									contractAssign = FDCHelper.ZERO;
								}
								allContractAssign = allContractAssign.add(contractAssign);
							}
						}
					}
				}
		}
		return allContractAssign;
	}
	public void actionComAddRow_actionPerformed(ActionEvent e) throws Exception {
	}
	public void actionComInsertRow_actionPerformed(ActionEvent e)throws Exception {
	}
	protected void setCompareTable(KDTable table) throws BOSException{
		Map reasonMap=new HashMap();
		if(table.equals(this.kdtCompareEntry)){
			for(int i=0;i<this.kdtCompareEntry.getRowCount();i++){
				if(this.kdtCompareEntry.getRow(i).getCell("programmingContract").getValue()==null) continue;
				String pc=this.kdtCompareEntry.getRow(i).getCell("programmingContract").getValue().toString();
				String reason=(String)this.kdtCompareEntry.getRow(i).getCell("reason").getValue();
				reasonMap.put(pc, reason);
			}
		}
		table.removeRows();
		Map entry=new HashMap();
		Map entryCost=new HashMap();
		Map colorEntry=new HashMap();
		for(int i=0;i<this.kdtEntries.getRowCount();i++){
			String longNumber = this.kdtEntries.getRow(i).getCell("longNumber").getValue().toString();
			boolean isLeaf = true;
			for (int j = i+1; j < this.kdtEntries.getRowCount(); j++) {
				String longNumberJ = this.kdtEntries.getRow(j).getCell("longNumber").getValue().toString();
				if (longNumberJ.startsWith(longNumber)) {
					isLeaf = false;
					break;
				}
			}
			if (isLeaf && longNumber.indexOf('.') != -1) {
				entry.put(longNumber, (ProgrammingContractInfo)this.kdtEntries.getRow(i).getUserObject());
				colorEntry.put(longNumber, this.kdtEntries.getRow(i));
				for(int k=0;k<((ProgrammingContractInfo) this.kdtEntries.getRow(i).getUserObject()).getCostEntries().size();k++){
					ProgrammingContracCostInfo pcc=((ProgrammingContractInfo) this.kdtEntries.getRow(i).getUserObject()).getCostEntries().get(k);
					entryCost.put(longNumber+pcc.getCostAccount().getLongNumber().replaceAll("!", "."),pcc);
				}
			}
		}
		ProgrammingContractCollection col=ProgrammingContractFactory.getRemoteInstance().getProgrammingContractCollection("select *,costEntries.*,costEntries.costAccount.* from where programming.versionGroup = '" + this.editData.getVersionGroup()+"' and programming.version="+(this.txtVersion.getBigDecimalValue().subtract(new BigDecimal(1)))+" order by longNumber,costEntries.costAccount.longNumber");
		for(int i=0;i<col.size();i++){
			String longNumber = col.get(i).getLongNumber();
			String name=col.get(i).getName();
			BigDecimal srcAmount=col.get(i).getAmount();
			boolean isLeaf = true;
			for (int j = i+1; j < col.size(); j++) {
				String longNumberJ = col.get(j).getLongNumber();
				if (longNumberJ.startsWith(longNumber)) {
					isLeaf = false;
					break;
				}
			}
			if (isLeaf && longNumber.indexOf('.') != -1) {
				IRow row=null;
				String content=null;
				if(entry.get(longNumber)!=null){
					ProgrammingContractInfo nowPC=(ProgrammingContractInfo) entry.get(longNumber);
					BigDecimal nowAmount=nowPC.getAmount();
					if(nowAmount.compareTo(srcAmount)!=0){
						row=table.addRow();
						row.getCell("programmingContract").setValue(longNumber+"     "+name);
						if(nowAmount.compareTo(srcAmount)>0){
							content="�滮�������"+nowAmount.subtract(srcAmount)+"Ԫ"+"(";
							((IRow)colorEntry.get(longNumber)).getCell("compare").setValue("red");
							((IRow)colorEntry.get(longNumber)).getStyleAttributes().setBackground(new Color(248,171,166));
						}else{
							content="�滮������"+srcAmount.subtract(nowAmount)+"Ԫ"+"(";
							((IRow)colorEntry.get(longNumber)).getCell("compare").setValue("green");
							((IRow)colorEntry.get(longNumber)).getStyleAttributes().setBackground(new Color(163,207,98));
						}
						for(int k=0;k<col.get(i).getCostEntries().size();k++){
							String costLongNumber=col.get(i).getCostEntries().get(k).getCostAccount().getLongNumber().replaceAll("!", ".");
							String costName=col.get(i).getCostEntries().get(k).getCostAccount().getName();
							BigDecimal srcCostAmount=col.get(i).getCostEntries().get(k).getContractAssign();
							if(entryCost.get(longNumber+costLongNumber)!=null){
								ProgrammingContracCostInfo srcPCC=(ProgrammingContracCostInfo) entryCost.get(longNumber+costLongNumber);
								BigDecimal nowCostAmount=srcPCC.getContractAssign();
								
								if(nowCostAmount.compareTo(srcCostAmount)!=0){
									if(nowCostAmount.compareTo(srcCostAmount)>0){
										content=content+costLongNumber+"     "+costName+"����"+nowCostAmount.subtract(srcCostAmount)+";";
									}else{
										content=content+costLongNumber+"     "+costName+"����"+srcCostAmount.subtract(nowCostAmount)+";";
									}
								}
								entryCost.remove(longNumber+costLongNumber);
							}else{
								content=content+costLongNumber+"     "+costName+"����"+srcCostAmount+";";
							}
						}
						Object[] key = entryCost.keySet().toArray(); 
			    		for (int k = 0; k < key.length; k++) {
			    			String nowCostNumber=(String)key[k];
			    			ProgrammingContracCostInfo nowPCC=(ProgrammingContracCostInfo) entryCost.get(key[k]);
			    			if(nowCostNumber.startsWith(longNumber)&&nowPCC.getContractAssign()!=null&&nowPCC.getContractAssign().compareTo(FDCHelper.ZERO)!=0){
			    				content=content+nowCostNumber.replaceFirst(longNumber, "")+"     "+nowPCC.getCostAccount().getName()+"����"+nowPCC.getContractAssign()+";";
			    			}
			    		}
						row.getCell("content").setValue(content+")");
						row.getCell("reason").setValue(reasonMap.get(longNumber+"     "+name));
					}
					entry.remove(longNumber);
				}else if(srcAmount.compareTo(FDCHelper.ZERO)!=0){
					row=table.addRow();
					row.getCell("programmingContract").setValue(longNumber+"     "+name);
					content="�滮������"+srcAmount+"Ԫ"+"(";
					for(int k=0;k<col.get(i).getCostEntries().size();k++){
						String costLongNumber=col.get(i).getCostEntries().get(k).getCostAccount().getLongNumber().replaceAll("!", ".");
						String costName=col.get(i).getCostEntries().get(k).getCostAccount().getName();
						BigDecimal srcCostAmount=col.get(i).getCostEntries().get(k).getContractAssign();
						if(srcCostAmount!=null&&srcCostAmount.compareTo(FDCHelper.ZERO)!=0){
							content=content+costLongNumber+"     "+costName+"����"+srcCostAmount+";";
						}
					}
					row.getCell("content").setValue(content+")");
					row.getCell("reason").setValue(reasonMap.get(longNumber+"     "+name));
				}
			}
		}
		Object[] key = entry.keySet().toArray(); 
		for (int k = 0; k < key.length; k++) {
			String nowNumber=(String)key[k];
			ProgrammingContractInfo nowPC=(ProgrammingContractInfo) entry.get(key[k]);
			String nowName=nowPC.getName();
			IRow row=table.addRow();
			row.getCell("programmingContract").setValue(nowNumber+"     "+nowName.trim());
			String content="�滮�������"+nowPC.getAmount()+"Ԫ"+"(";
			((IRow)colorEntry.get(nowNumber)).getCell("compare").setValue("red");
			((IRow)colorEntry.get(nowNumber)).getStyleAttributes().setBackground(new Color(248,171,166));
			for(int kk=0;kk<nowPC.getCostEntries().size();kk++){
				String costLongNumber=nowPC.getCostEntries().get(kk).getCostAccount().getLongNumber().replaceAll("!", ".");
				String costName=nowPC.getCostEntries().get(kk).getCostAccount().getName();
				BigDecimal nowCostAmount=nowPC.getCostEntries().get(kk).getContractAssign();
				if(nowCostAmount!=null&&nowCostAmount.compareTo(FDCHelper.ZERO)!=0){
					content=content+costLongNumber+"     "+costName+"����"+nowCostAmount+";";
				}
			}
			row.getCell("content").setValue(content+")");
			row.getCell("reason").setValue(reasonMap.get(nowNumber+"     "+nowName.trim()));
		}
	}
	public void actionCompare_actionPerformed(ActionEvent e) throws Exception {
		setCompareTable(this.kdtCompareEntry);
	}
	public void actionComRemoveRow_actionPerformed(ActionEvent e) throws Exception {
	}
	public void actionViewAmount_actionPerformed(ActionEvent e)throws Exception {
		this.kdtEntries.getColumn("amount").getStyleAttributes().setHided(!this.kdtEntries.getColumn("amount").getStyleAttributes().isHided());
//		this.kdtEntries.getColumn("balance").getStyleAttributes().setHided(!this.kdtEntries.getColumn("balance").getStyleAttributes().isHided());
		
		this.kdtCostAccount.getColumn("aimCost").getStyleAttributes().setHided(!this.kdtCostAccount.getColumn("aimCost").getStyleAttributes().isHided());
		this.kdtCostAccount.getColumn("assigned").getStyleAttributes().setHided(!this.kdtCostAccount.getColumn("assigned").getStyleAttributes().isHided());
		this.kdtCostAccount.getColumn("assigning").getStyleAttributes().setHided(!this.kdtCostAccount.getColumn("assigning").getStyleAttributes().isHided());
	}
	
}