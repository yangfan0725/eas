/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.util.REAutoCompletionDataFilter;
import com.kingdee.eas.fdc.basedata.util.REAutoCompletionDataSetter;
import com.kingdee.eas.fdc.contract.programming.PTECostCollection;
import com.kingdee.eas.fdc.contract.programming.PTECostInfo;
import com.kingdee.eas.fdc.contract.programming.PTEEnonomyInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * ��Լ���ģ�� �༭����
 */
public class PTEEditUI extends AbstractPTEEditUI implements REAutoCompletionDataSetter, REAutoCompletionDataFilter
{
    private static final Logger logger = CoreUIObject.getLogger(PTEEditUI.class);
	private final static String COST_ID = "id";// �ɱ�����ID
	private final static String COSTACCOUNT_NUMBER = "costAccountNumber";// �ɱ���Ŀ����
	private final static String COSTACCOUNT = "costAccount";// �ɱ���ĿF7
	private final static String ASSIGNSCALE = "assignScale";// ���������
	private final static String CONSTRACTSCALE = "contractScale";// ����Լ����
	private final static String COST_DES = "description";// ��ע

	private final static String ECONOMY_ID = "id";// ��������ID
	private final static String PAYMENTTYPE = "paymentType";// ��������F7
	private final static String SCALE = "scale";// �������
	private final static String CONDITION = "condition";// ��������

	private final static String ECONOMY_DES = "description";// ��ע

	private ProgrammingTemplateEntireCollection pteCollection;
    private ProgrammingTemplateEntireInfo  pteInfo;
	private ProgrammingTemplateEntireInfo oldPteInfo;
	protected KDWorkButton btnAddnewLine_cost;
	protected KDWorkButton btnRemoveLines_cost;
	protected KDWorkButton btnAddnewLine_economy;
	protected KDWorkButton btnRemoveLines_economy;

	private KDTextField kdtTestField = null;

	public PTEEditUI() throws Exception {
		super();
	}

    public void onLoad() throws Exception {
    	super.onLoad();
		setSmallButton();
    	if(this.oprtState.equals(OprtState.VIEW)){
    		isEditable(false);
    		ctrButtonEnable(false);
    	}else if(this.oprtState.equals(OprtState.EDIT) || this.oprtState.equals(OprtState.ADDNEW)){
    		isEditable(true);
    		ctrButtonEnable(true);
    	}
	}
    
	public void setFieldData(Object data) {
		if (data instanceof CostAccountInfo) {
			this.kdtTestField.setText(((CostAccountInfo) data).getLongNumber());
			this.kdtTestField.setUserObject(data);
		}
	}

	public ArrayList filter(String text) {
		if (text == null || text.trim().length() == 0) {
			return null;
		}
		try {
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(new FilterInfo());
			view.getFilter().getFilterItems().add(new FilterItemInfo("longNumber", text + "%", CompareType.LIKE));
			view.getFilter().getFilterItems().add(
					new FilterItemInfo("fullOrgUnit", SysContext.getSysContext().getCurrentCostUnit().getId()));
			view.getSelector().add("longNumber");
			view.getSelector().add("name");
			view.getSelector().add("id");
			view.getSorter().add(new SorterItemInfo("longNumber"));
			CostAccountCollection coll = CostAccountFactory.getRemoteInstance().getCostAccountCollection(view);
			if (coll != null) {
				ArrayList ret = new ArrayList();
				for (int i = 0; i < coll.size(); ++i) {
					ret.add(coll.get(i));
				}
				return ret;
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return null;
	}

    public void onShow() throws Exception {
		super.onShow();
		initTable();
		this.btnAttachment.setIcon(EASResource.getIcon("imgTbtn_affixmanage"));
		this.txtConstract.setEditable(false);//�ϼ���Լ��ܲ��ɱ༭
		this.txtAttachment.setEditable(false);// �����ı�����ṩ������ɸ��ݲ���״̬�����ڸ������������Ǳ༭״̬���ǲ鿴
    	Map uiContext = this.getUIContext();
		pteCollection = (ProgrammingTemplateEntireCollection) uiContext.get("pteCollection");
     	pteInfo = (ProgrammingTemplateEntireInfo)uiContext.get("rowObject");
		oldPteInfo = pteInfo;
		// ����ʱ��������ɱ�����"���������"
		for (int i = 0; i < pteInfo.getPteCost().size(); i++) {
			PTECostInfo pteCostInfo = pteInfo.getPteCost().get(i);
			BigDecimal assignScale = FDCHelper.ONE_HUNDRED;
			if (pteCostInfo != null) {
				CostAccountInfo costAccount = pteCostInfo.getCostAccount();
				if (costAccount != null) {
					BigDecimal scale = getCostAccountScale(costAccount, false);
					assignScale = assignScale.subtract(scale);
				}
			}
			pteCostInfo.setAssignScale(assignScale);
		}

		preparePTEData();
		costAccountF7();
		// initAutoComplete();
		paymentTypeF7();
	}

	/**
	 * ��ʼ����Ԫ���
	 */
	private void initTable() {
		kdtCostEntries.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		// kdtCostEntries.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		kdtEconomyEntriese.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		// kdtCostEntries.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		/* �ɱ����� */
		// ����Լ����
		KDTDefaultCellEditor cellEditor0 = new KDTDefaultCellEditor(new KDFormattedTextField());
		KDFormattedTextField kdftf = (KDFormattedTextField) cellEditor0.getComponent();
		kdftf.setDataType(1);
		kdftf.setPrecision(0);
		kdftf.setSupportedEmpty(true);
		kdftf.setMaximumValue(FDCHelper.ONE_HUNDRED);
		kdftf.setMinimumValue(new BigDecimal("1"));
		this.kdtCostEntries.getColumn(CONSTRACTSCALE).setEditor(cellEditor0);
		// ��ע
		KDTDefaultCellEditor cellEditor1 = new KDTDefaultCellEditor(new KDTextField());
		KDTextField kdtf1 = (KDTextField) cellEditor1.getComponent();
		kdtf1.setMaxLength(80);
		this.kdtCostEntries.getColumn(COST_DES).setEditor(cellEditor1);// ��ע�ֶ�����80�ַ�
		/* �������� */
		// ��������
		KDTDefaultCellEditor cellEditor2 = new KDTDefaultCellEditor(new KDTextField());
		KDTextField kdtf2 = (KDTextField) cellEditor2.getComponent();
		kdtf2.setMaxLength(80);
		this.kdtEconomyEntriese.getColumn(CONDITION).setEditor(cellEditor2);// ���������ֶ�����80�ַ�
		// ��ע
		KDTDefaultCellEditor cellEditor3 = new KDTDefaultCellEditor(new KDTextField());
		KDTextField kdtf3 = (KDTextField) cellEditor3.getComponent();
		kdtf3.setMaxLength(80);
		this.kdtEconomyEntriese.getColumn(ECONOMY_DES).setEditor(cellEditor3);// ��ע�ֶ�����80�ַ�
	}

	/**
	 * ���湦��
	 */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		/*
		 * 1.�����пմ���
		 * 
		 * 2.����ͷ���ݴ�洢
		 * 
		 * 3.���ݷ�¼�����ɱ����ɡ���������¼���ݴ洢����̬�ı�"���������"
		 * 
		 * 1�� �洢����Լ�ɱ����ɷ�¼����
		 * 
		 * 2�� ��̬�ı�����������Լ�гɱ����ɵ�"���������"
		 * 
		 * 4.���ݷ�¼�������������������¼���ݴ洢��
		 */
		// 1.
    	verifyTxt();
		// 2.
		if (FDCHelper.isEmpty(txtConstract.getText())) {// ������
			pteInfo.setDisplayName(txtConstractName.getText());
		} else {
			pteInfo.setDisplayName(txtConstract.getText() + "." + txtConstractName.getText());// �ϼ���Լ������
		}
		pteInfo.setLongNumber(txtConstractNumber.getText());// ������
		pteInfo.setName(setNameIndent(pteInfo.getLevel()) + txtConstractName.getText());// ����
		pteInfo.setScope(txtScope.getText());// ��Ҫ��Χ
		pteInfo.setProblem(txtProblem.getText());// ��������
		pteInfo.setDescription(txtDescription.getText());// ��ע
		if (FDCHelper.isEmpty(txtAttachment.getText())) {
			pteInfo.setAttachment(null);// ����
		} else {
			pteInfo.setAttachment(txtAttachment.getText());// ����
		}

		// 3.
		// 1)����ɱ����ɷ�¼��Ϣ
		int cost_rowCount = kdtCostEntries.getRowCount();
		pteInfo.getPteCost().clear();// д�����֮ǰ�����ԭ�����ݣ�����¼��ɱ����ɷ�¼��Ϣ
    	for(int i=0;i<cost_rowCount;i++){
    		PTECostInfo pteCostInfo = new PTECostInfo();
			Object id = kdtCostEntries.getCell(i, kdtCostEntries.getColumnIndex(COST_ID)).getValue();
			CostAccountInfo costAccountInfo = (CostAccountInfo) kdtCostEntries.getCell(i, COSTACCOUNT).getValue();
			Object assignScale = kdtCostEntries.getCell(i, kdtCostEntries.getColumnIndex(ASSIGNSCALE)).getValue();
			Object contractScale = kdtCostEntries.getCell(i, kdtCostEntries.getColumnIndex(CONSTRACTSCALE)).getValue();
			String description = (String) kdtCostEntries.getCell(i, kdtCostEntries.getColumnIndex(COST_DES)).getValue();

			pteCostInfo.setId(BOSUuid.read(id.toString()));
			pteCostInfo.setCostAccount(costAccountInfo);
			if (!FDCHelper.isEmpty(assignScale)) {
				pteCostInfo.setAssignScale(new BigDecimal(assignScale.toString()));
    		}
			if (!FDCHelper.isEmpty(contractScale)) {
				pteCostInfo.setContractScale(new BigDecimal(contractScale.toString()));
    		}
    		pteCostInfo.setDescription(description);
    		pteInfo.getPteCost().add(pteCostInfo);
    	}
		// 2)��̬�ı�"���������"
		// ѭ�����к�Լȡ��"����Լ����"��ֵ��
		// ������Լ"���������" = 100 - ALL + ����"����ͬ����"
		int pteSize = pteCollection.size();
		for (int i = 0; i < pteSize; i++) {
			PTECostCollection pteCostCollection = pteCollection.get(i).getPteCost();// �ɱ����ɼ���
			for (int j = 0; j < pteCostCollection.size(); j++) {
				PTECostInfo pteCostInfo = pteCostCollection.get(j);// �ɱ�����
				BigDecimal hundred = FDCHelper.ONE_HUNDRED;
				BigDecimal oldContractScale = pteCostInfo.getContractScale();
				CostAccountInfo costAccountInfo = pteCostInfo.getCostAccount();// �ɱ���Ŀ
				if (costAccountInfo != null) {
					BigDecimal AllCostAccountScale = getCostAccountScale(costAccountInfo, true);
					if (oldContractScale != null) {
						BigDecimal newAssignScale = hundred.subtract(AllCostAccountScale).add(oldContractScale);
						pteCostInfo.setAssignScale(newAssignScale);
					}
				}
			}
		}

		// 1)���澭�������¼��Ϣ
    	pteInfo.getPteEnonomy().clear();
		pteInfo.getPteEnonomy().size();
    	int enonomy_rowCount = kdtEconomyEntriese.getRowCount();
    	for(int i=0;i<enonomy_rowCount;i++){
    		PTEEnonomyInfo pteEnonomyInfo = new PTEEnonomyInfo();
			Object id = kdtEconomyEntriese.getCell(i, ECONOMY_ID).getValue();
			PaymentTypeInfo currentInfo = (PaymentTypeInfo) kdtEconomyEntriese.getCell(i, PAYMENTTYPE).getValue();// ��������
			Object scaleObj = kdtEconomyEntriese.getCell(i, SCALE).getValue();// �������
			String condition = (String) kdtEconomyEntriese.getCell(i, CONDITION).getValue();// ��������
			String description = (String) kdtEconomyEntriese.getCell(i, ECONOMY_DES).getValue();// ��ע

			pteEnonomyInfo.setId(BOSUuid.read(id.toString()));
			pteEnonomyInfo.setPaymentType(currentInfo);
			pteEnonomyInfo.setScale(new BigDecimal(scaleObj.toString()));
			pteEnonomyInfo.setCondition(condition);
    		pteEnonomyInfo.setDescription(description);
    		pteInfo.getPteEnonomy().add(pteEnonomyInfo);
    	}

		if (directExit) {
			// ֱ�ӱ����˳���������ʾ
		} else {
			FDCMsgBox.showInfo("�����ܺ�Լ�ɹ���");
		}
		oldPteInfo = pteInfo;
		// destroyWindow();
    }

	/**
	 * �жϵ����Ƿ������޸�
	 * 
	 * @return
	 * 
	 *         true��ʾ���޸Ĺ�
	 * 
	 *         false��ʾδ���޸�
	 */
	private boolean verifyIsModify() {
		if (oprtState.equals(OprtState.EDIT) || oprtState.equals(OprtState.ADDNEW)) {
			// ������
			if (FDCHelper.isEmpty(txtConstractNumber.getText()) ^ FDCHelper.isEmpty(oldPteInfo.getLongNumber())) {
				return true;
			}
			if (!FDCHelper.isEmpty(txtConstractNumber.getText()) & !FDCHelper.isEmpty(oldPteInfo.getLongNumber())) {
				if (!txtConstractNumber.getText().equals(oldPteInfo.getLongNumber())) {
					return true;
				}
			}
			// ����
			if (FDCHelper.isEmpty(txtConstractName.getText()) ^ FDCHelper.isEmpty(oldPteInfo.getName())) {
				return true;
			}
			if (!FDCHelper.isEmpty(txtConstractName.getText()) & !FDCHelper.isEmpty(oldPteInfo.getName())) {
				if (!txtConstractName.getText().equals(oldPteInfo.getName().trim())) {
					return true;
				}
			}
			// ��Ҫ��Χ
			if (FDCHelper.isEmpty(txtScope.getText()) ^ FDCHelper.isEmpty(oldPteInfo.getScope())) {
				return true;
			}
			if (!FDCHelper.isEmpty(txtScope.getText()) & !FDCHelper.isEmpty(oldPteInfo.getScope())) {
				if (!txtScope.getText().equals(oldPteInfo.getScope())) {
					return true;
				}
			}
			// ��������
			if (FDCHelper.isEmpty(txtProblem.getText()) ^ FDCHelper.isEmpty(oldPteInfo.getProblem())) {
				return true;
			}
			if (!FDCHelper.isEmpty(txtProblem.getText()) & !FDCHelper.isEmpty(oldPteInfo.getProblem())) {
				if (!txtProblem.getText().equals(oldPteInfo.getProblem())) {
					return true;
				}
			}
			// ��ע
			if (FDCHelper.isEmpty(txtDescription.getText()) ^ FDCHelper.isEmpty(oldPteInfo.getDescription())) {
				return true;
			}
			if (!FDCHelper.isEmpty(txtDescription.getText()) & !FDCHelper.isEmpty(oldPteInfo.getDescription())) {
				if (!txtDescription.getText().equals(oldPteInfo.getDescription())) {
					return true;
				}
			}
			// ����
			// if (FDCHelper.isEmpty(txtAttachment.getText()) ^ FDCHelper.isEmpty(oldPteInfo.getAttachment())) {
			// return true;
			// }
			// if (!FDCHelper.isEmpty(txtAttachment.getText()) & !FDCHelper.isEmpty(oldPteInfo.getAttachment())) {
			// if (!txtAttachment.getText().equals(oldPteInfo.getAttachment())) {
			// return true;
			// }
			// }
			if (kdtCostEntries.getRowCount() == oldPteInfo.getPteCost().size()) {
				for (int i = 0; i < kdtCostEntries.getRowCount(); i++) {
					for (int j = 0; j < oldPteInfo.getPteCost().size(); j++) {
						if (oldPteInfo.getPteCost().get(j).getId() == kdtCostEntries.getCell(i, COST_ID).getValue()) {
							CostAccountInfo infoCA = oldPteInfo.getPteCost().get(j).getCostAccount();
							BigDecimal infoAssignScale = oldPteInfo.getPteCost().get(j).getAssignScale();
							BigDecimal infoContractScale = oldPteInfo.getPteCost().get(j).getContractScale();
							String infoDescription = oldPteInfo.getPteCost().get(j).getDescription();

							CostAccountInfo tableCA = (CostAccountInfo) kdtCostEntries.getCell(j, COSTACCOUNT).getValue();
							Object assignScale = kdtCostEntries.getCell(j, ASSIGNSCALE).getValue();
							Object contractScale = kdtCostEntries.getCell(j, CONSTRACTSCALE).getValue();
							Object description = kdtCostEntries.getCell(j, COST_DES).getValue();

							// �ɱ���ĿF7
							if (FDCHelper.isEmpty(infoCA) ^ FDCHelper.isEmpty(tableCA)) {
								return true;
							}
							if (!FDCHelper.isEmpty(infoCA) & !FDCHelper.isEmpty(tableCA)) {
								if (!infoCA.getName().equals(tableCA.getName())) {
									return true;
								}
							}
							// ���������
							if (FDCHelper.isEmpty(infoAssignScale) ^ FDCHelper.isEmpty(assignScale)) {
								return true;
							}
							if (!FDCHelper.isEmpty(infoAssignScale) & !FDCHelper.isEmpty(assignScale)) {
								BigDecimal temp = new BigDecimal(assignScale.toString());
								if (infoAssignScale.compareTo(temp) != 0) {
									return true;
								}
							}
							// ����Լ����
							if (FDCHelper.isEmpty(infoContractScale) ^ FDCHelper.isEmpty(contractScale)) {
								return true;
							}
							if (!FDCHelper.isEmpty(infoContractScale) & !FDCHelper.isEmpty(contractScale)) {
								BigDecimal temp = new BigDecimal(contractScale.toString());
								if (infoContractScale.compareTo(temp) != 0) {
									return true;
								}
							}
							// ��ע
							if (FDCHelper.isEmpty(infoDescription) ^ FDCHelper.isEmpty(description)) {
								return true;
							}
							if (!FDCHelper.isEmpty(infoDescription) & !FDCHelper.isEmpty(description)) {
								if (!infoDescription.equals(description.toString())) {
									return true;
								}
							}
						}
					}
				}
			} else {
				return true;
			}
			if (kdtEconomyEntriese.getRowCount() == oldPteInfo.getPteEnonomy().size()) {
				for (int i = 0; i < kdtEconomyEntriese.getRowCount(); i++) {
					for (int j = 0; j < oldPteInfo.getPteEnonomy().size(); j++) {
						if (oldPteInfo.getPteEnonomy().get(j).getId() == kdtEconomyEntriese.getCell(i, ECONOMY_ID).getValue()) {

							PaymentTypeInfo infoPT = oldPteInfo.getPteEnonomy().get(j).getPaymentType();
							BigDecimal infoScale = oldPteInfo.getPteEnonomy().get(j).getScale();
							String infoCondition = oldPteInfo.getPteEnonomy().get(j).getCondition();
							String infoDescription = oldPteInfo.getPteEnonomy().get(j).getDescription();

							PaymentTypeInfo tablePT = (PaymentTypeInfo) kdtEconomyEntriese.getCell(j, PAYMENTTYPE).getValue();
							Object scale = kdtEconomyEntriese.getCell(j, SCALE).getValue();
							Object condition = kdtEconomyEntriese.getCell(j, CONDITION).getValue();
							Object description = kdtEconomyEntriese.getCell(j, ECONOMY_DES).getValue();

							// ��������
							if (FDCHelper.isEmpty(infoPT) ^ FDCHelper.isEmpty(tablePT)) {
								return true;
							}
							if (!FDCHelper.isEmpty(infoPT) & !FDCHelper.isEmpty(tablePT)) {
								if (!infoPT.getName().equals(tablePT.getName())) {
									return true;
								}
							}
							// �������
							if (FDCHelper.isEmpty(infoScale) ^ FDCHelper.isEmpty(scale)) {
								return true;
							}
							if (!FDCHelper.isEmpty(infoScale) & !FDCHelper.isEmpty(scale)) {
								BigDecimal temp = new BigDecimal(scale.toString());
								if (infoScale.compareTo(temp) != 0) {
									return true;
								}
							}
							// ��������
							if (FDCHelper.isEmpty(infoCondition) ^ FDCHelper.isEmpty(condition)) {
								return true;
							}
							if (!FDCHelper.isEmpty(infoCondition) & !FDCHelper.isEmpty(condition)) {
								if (!infoCondition.equals(condition.toString())) {
									return true;
								}
							}
							// ��ע
							if (FDCHelper.isEmpty(infoDescription) ^ FDCHelper.isEmpty(description)) {
								return true;
							}
							if (!FDCHelper.isEmpty(infoDescription) & !FDCHelper.isEmpty(description)) {
								if (!infoDescription.equals(description.toString())) {
									return true;
								}
							}
						}
					}
				}
			}
		} else {
			return true;
		}
		if (oprtState.equals(OprtState.VIEW)) {
			return false;
		}
		return false;
	}

	/**
	 * �رմ���ǰ�¼�
	 */
	private boolean directExit = false;
	protected boolean checkBeforeWindowClosing() {
		this.kdtCostEntries.getEditManager().editingStopped();
		this.kdtEconomyEntriese.getEditManager().editingStopped();
		if (this.oprtState.equals(OprtState.ADDNEW) || this.oprtState.equals(OprtState.EDIT)) {
			if (verifyIsModify()) {
				int i = FDCMsgBox.showConfirm3("�������޸ģ��Ƿ񱣴沢�˳�?");
				if (i == FDCMsgBox.OK) {
					try {
						directExit = true;
						actionSubmit_actionPerformed(null);
					} catch (Exception e) {
						SysUtil.abort();
						e.printStackTrace();
					}
				} else if (i == FDCMsgBox.NO) {
					return super.checkBeforeWindowClosing();
				} else if (i == FDCMsgBox.CANCEL) {
					return false;
				}
			}
		}
		return super.checkBeforeWindowClosing();
	}

	/**
	 * ������ǰ��ӿո���ʾ����Ч��
	 * @param level
	 * @return
	 */
	private String setNameIndent(int level){
		StringBuffer blank = new StringBuffer("");
		for(int i = level ; i > 1 ; i--){
			blank.append("        ");
		}
		return blank.toString();
	}

    /**
     * ���ƿ�ܺ�Լ���ݱ༭�ؼ��Ƿ����
     */
    private void isEditable(boolean isEditable){
    	//�ļ��༭��
    	txtConstractNumber.setEditable(isEditable);
    	txtConstractName.setEditable(isEditable);
    	txtScope.setEditable(isEditable);
    	txtProblem.setEditable(isEditable);
    	txtDescription.setEditable(isEditable);
    	//��¼
    	kdtCostEntries.setEditable(isEditable);
    	kdtEconomyEntriese.setEditable(isEditable);
    }

	/**
	 * ��֤��¼��
	 */
    private void verifyTxt(){
		ProgrammingTemplateEntireInfo head = pteInfo.getHead();
		if (head != null) {
			String longNumber = head.getLongNumber();
			if (txtConstractNumber.getText().equals(longNumber + ".")) {
				FDCMsgBox.showInfo("��ܺ�Լ���벻��Ϊ�գ�");
				txtConstractNumber.requestFocus();
				SysUtil.abort();
			}
			for(int i=0;i<pteCollection.size();i++){
				ProgrammingTemplateEntireInfo programmingTemplateEntireInfo = pteCollection.get(i);
				if (programmingTemplateEntireInfo.getLongNumber().equals(txtConstractNumber.getText())
						&& !pteInfo.getId().toString().equals(programmingTemplateEntireInfo.getId().toString())) {
					FDCMsgBox.showInfo("��ܺ�Լ�����Ѵ��ڣ����������룡");
					txtConstractNumber.requestFocus();
					SysUtil.abort();
				}
			}
		} else {
			if (FDCHelper.isEmpty(txtConstractNumber.getText())) {
				FDCMsgBox.showInfo("��ܺ�Լ���벻��Ϊ�գ�");
				txtConstractNumber.requestFocus();
				SysUtil.abort();
			}
    	}
    	if(FDCHelper.isEmpty(txtConstractName.getText())){
    		FDCMsgBox.showInfo("��ܺ�Լ���Ʋ���Ϊ�գ�");
    		txtConstractName.requestFocus();
    		SysUtil.abort();
		} else {
			for (int i = 0; i < pteCollection.size(); i++) {
				ProgrammingTemplateEntireInfo rowObject = pteCollection.get(i);
				String name = rowObject.getName();
				if (!FDCHelper.isEmpty(name)) {
					if (txtConstractName.getText().equals(name.trim()) && !pteInfo.getId().toString().equals(rowObject.getId().toString())) {
						FDCMsgBox.showInfo("��ܺ�Լ�����Ѵ��ڣ����������룡");
						txtConstractName.requestFocus();
						SysUtil.abort();
					}
				}
			}
    	}

		/* �ɱ����ɷ�¼��Ϣ��֤ */
    	for(int i=0;i<kdtCostEntries.getRowCount();i++){
    		IRow row = kdtCostEntries.getRow(i);
			ICell costAccountCell_number = row.getCell(COSTACCOUNT_NUMBER);
			ICell costAccountCell = row.getCell(COSTACCOUNT);
			if (FDCHelper.isEmpty(costAccountCell_number.getValue())) {
				FDCMsgBox.showInfo("�ɱ���Ŀ���벻��Ϊ�գ�");
				SysUtil.abort();
			}
    		if(FDCHelper.isEmpty(costAccountCell.getValue())){
    			FDCMsgBox.showInfo("�ɱ���Ŀ���Ʋ���Ϊ�գ�");
    			SysUtil.abort();
    		}
			Object assignScale = kdtCostEntries.getCell(i, ASSIGNSCALE).getValue();
			Object contractScale = kdtCostEntries.getCell(i, CONSTRACTSCALE).getValue();
			if(FDCHelper.isEmpty(contractScale)){
				FDCMsgBox.showInfo("\"����Լ����\"����Ϊ�գ�");
				SysUtil.abort();
			}
			if (!FDCHelper.isEmpty(assignScale) && !FDCHelper.isEmpty(contractScale)) {
				if (new BigDecimal(assignScale.toString()).compareTo(new BigDecimal(contractScale.toString())) < 0) {
					FDCMsgBox.showInfo("\"����Լ����\"���ܴ���\"���������\"��");
					SysUtil.abort();
				}
			}
    	}

		/* ���������¼��Ϣ��֤ */
		BigDecimal scale = FDCHelper.ZERO;
    	for(int i=0;i<kdtEconomyEntriese.getRowCount();i++){
    		IRow row = kdtEconomyEntriese.getRow(i);
			ICell costAccountCell = row.getCell(PAYMENTTYPE);
			ICell scaleCell = row.getCell(SCALE);
			if (FDCHelper.isEmpty(costAccountCell.getValue())) {
				FDCMsgBox.showInfo("�������Ͳ���Ϊ�գ�");
    			SysUtil.abort();
			} else if (FDCHelper.isEmpty(scaleCell.getValue())) {
				FDCMsgBox.showInfo("�����������Ϊ�գ�");
				SysUtil.abort();
    		}
			Object scaleObj = kdtEconomyEntriese.getCell(i, SCALE).getValue();
			if (!FDCHelper.isEmpty(scaleObj)) {
				BigDecimal scaleTemp = new BigDecimal(scaleObj.toString());
				scale = scale.add(scaleTemp);
				if (scale.compareTo(FDCHelper.ONE_HUNDRED) > 0) {
					FDCMsgBox.showInfo("��������ۼƲ��ó���100%");
					SysUtil.abort();
				}
			}
    	}
    }

    /**
     * ���ƿ�ܺ�Լ���ݰ�ť�Ƿ����
     */
    private void ctrButtonEnable(boolean isEnable){
		btnAttachment.setEnabled(true);
		btnAddnewLine_cost.setEnabled(isEnable);
		btnRemoveLines_cost.setEnabled(false);
		btnAddnewLine_economy.setEnabled(isEnable);
		btnRemoveLines_economy.setEnabled(false);
   		bntSubmit.setEnabled(isEnable);
    }

	/**
	 * ��ʼ����ܺ�Լ���ݽ���������ʾ����
	 * 
	 * @throws Exception
	 */
    private void preparePTEData() throws Exception{
		if (pteInfo.getHead() != null) {
			String longName = pteInfo.getDisplayName();
			String headName = longName.substring(0, longName.lastIndexOf('.'));
			this.txtConstract.setText(headName);// �ϼ���ܺ�Լ������
		}
		this.txtConstractNumber.setText(pteInfo.getLongNumber());// ��ܺ�Լ������
		this.txtConstractName.setText(pteInfo.getName() == null ? null : pteInfo.getName().trim());// ��ܺ�Լ����
		this.txtScope.setText(pteInfo.getScope());// ��Ҫ��Χ
		this.txtProblem.setText(pteInfo.getProblem());// ��������
		this.txtDescription.setText(pteInfo.getDescription());// ��ע
		this.txtDescription.setToolTipText(pteInfo.getDescription());
		this.txtAttachment.setText(getAllAttachmentName(pteInfo.getId().toString()).toString());// ����
		this.txtAttachment.setToolTipText(getAllAttachmentName(pteInfo.getId().toString()).toString());
		addCostLine(kdtCostEntries);
		addEconomyLine(kdtEconomyEntriese);
		int level = pteInfo.getLevel();

		if (level > 1) {
			String longNumber = pteInfo.getLongNumber();
			if (!FDCHelper.isEmpty(longNumber)) {
				LimitedTextDocument document = new LimitedTextDocument(longNumber);
				String text = txtConstractNumber.getText();
				txtConstractNumber.setDocument(document);
				document.setIsAutoUpdate(true);
				document.setIsOnload(true);
				txtConstractNumber.setText(text);
				document.setIsAutoUpdate(false);
				document.setIsOnload(false);
			}
		}
		if (level == 1) {
			LimitedTextDocument document = new LimitedTextDocument("");
			String text = txtConstractNumber.getText();
			txtConstractNumber.setDocument(document);
			document.setIsAutoUpdate(true);
			document.setIsOnload(true);
			txtConstractNumber.setText(text);
			document.setIsAutoUpdate(false);
			document.setIsOnload(false);
		}
    }

    /**
     * ��ʼ����ܺ�Լ�ɱ����ɷ�¼����
     * @param table
     */
	private void addCostLine(KDTable table){
		IRow row;
		for (int i = 0; i < pteInfo.getPteCost().size(); i++) {
			PTECostInfo pteCostInfo = pteInfo.getPteCost().get(i);
			row = table.addRow();
			if(pteCostInfo.getId() == null){
				pteCostInfo.setId(BOSUuid.create(pteCostInfo.getBOSType()));
			}
			row.getCell(COST_ID).setValue(pteCostInfo.getId());
			if(pteCostInfo.getCostAccount() != null){
				row.getCell(COSTACCOUNT_NUMBER).setValue(pteCostInfo.getCostAccount().getLongNumber().replace('!', '.'));
				row.getCell(COSTACCOUNT).setValue(pteCostInfo.getCostAccount());
			}
			row.getCell(ASSIGNSCALE).setValue(pteCostInfo.getAssignScale());
			row.getCell(CONSTRACTSCALE).setValue(pteCostInfo.getContractScale());
			row.getCell(COST_DES).setValue(pteCostInfo.getDescription());
		}
	
		ObjectValueRender render = new ObjectValueRender();
		render.setFormat(new IDataFormat() {
			public String format(Object o) {
				String str = o.toString();
				if (!FDCHelper.isEmpty(str)) {
					return str + "%";
				}
				return str;
			}
		});
		// ���ƴ����������ʾ����
		kdtCostEntries.getColumn(ASSIGNSCALE).setRenderer(render);
		// �汾��Լ������ʾ����
		kdtCostEntries.getColumn(CONSTRACTSCALE).setRenderer(render);
	}
	
	/**
	 * ��ʼ����ܺ�Լ���������¼����
	 * @param table
	 */
	private void addEconomyLine(KDTable table) {
		IRow row;
		for (int i = 0; i < pteInfo.getPteEnonomy().size(); i++) {
			PTEEnonomyInfo pteEnonomyInfo = pteInfo.getPteEnonomy().get(i);
			row = table.addRow();
			if(pteEnonomyInfo.getId() == null){
				pteEnonomyInfo.setId(BOSUuid.create(pteEnonomyInfo.getBOSType()));
			}
			row.getCell(ECONOMY_ID).setValue(pteEnonomyInfo.getId());
			row.getCell(PAYMENTTYPE).setValue(pteEnonomyInfo.getPaymentType());
			row.getCell(SCALE).setValue(pteEnonomyInfo.getScale());
			row.getCell(CONDITION).setValue(pteEnonomyInfo.getCondition());
			row.getCell(ECONOMY_DES).setValue(pteEnonomyInfo.getDescription());
		}

		// ���Ƹ��������ʾ����
		ObjectValueRender render_scale = new ObjectValueRender();
		render_scale.setFormat(new IDataFormat() {
			public String format(Object o) {
				String str = o.toString();
				if (!FDCHelper.isEmpty(str)) {
					return str + "%";
				}
				return str;
			}
		});
		kdtEconomyEntriese.getColumn(SCALE).setRenderer(render_scale);
	}

	/**
     * �ɱ���ĿF7
     */
    private void costAccountF7(){
    	CostAccountPromptBox selector=new CostAccountPromptBox(this);
		KDBizPromptBox prmtCostAccount=new KDBizPromptBox(){
			protected String valueToString(Object o) {
				String str=null;
				if (o != null && o instanceof CostAccountInfo) {
					str=((CostAccountInfo)o).getLongNumber().replace('!', '.');
				}
				return str;
			}
		};
		prmtCostAccount.setSelector(selector);
		prmtCostAccount.setEnabledMultiSelection(false);
		prmtCostAccount.setDisplayFormat("$longNumber$");
		prmtCostAccount.setEditFormat("$longNumber$");
		prmtCostAccount.setCommitFormat("$longNumber$");
		
		KDTDefaultCellEditor caEditor = new KDTDefaultCellEditor(prmtCostAccount);
    	EntityViewInfo entityView = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",OrgConstants.DEF_CU_ID,CompareType.EQUALS));
    	entityView.setFilter(filter);
    	prmtCostAccount.setEntityViewInfo(entityView);
    	kdtCostEntries.getColumn("costAccount").setEditor(caEditor);
    	ObjectValueRender kdtCostEntries_costAccount_OVR = new ObjectValueRender();
    	kdtCostEntries_costAccount_OVR.setFormat(new BizDataFormat("$name$"));
		this.kdtCostEntries.getColumn(COSTACCOUNT).setRenderer(kdtCostEntries_costAccount_OVR);
    }
	protected void kdtCostEntries_tableClicked(KDTMouseEvent e)
			throws Exception {
		if (e.getRowIndex() > -1 && !oprtState.equals(OprtState.VIEW) && e.getType() != KDTStyleConstants.HEAD_ROW) {
			btnRemoveLines_cost.setEnabled(true);
		}

	}

	protected void kdtCostEntries_editStopped(KDTEditEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();

		// ����ɱ���Ŀ
		if (colIndex == kdtCostEntries.getColumnIndex(COSTACCOUNT)) {
			CostAccountInfo costAccountInfo = (CostAccountInfo) kdtCostEntries.getCell(rowIndex, COSTACCOUNT).getValue();
			isCostAccountDup(costAccountInfo, rowIndex);
			if (costAccountInfo != null) {
				BigDecimal assignScale = FDCHelper.ONE_HUNDRED;
				BigDecimal costAccountScale = getCostAccountScale(costAccountInfo, false);
				assignScale = assignScale.subtract(costAccountScale);
				kdtCostEntries.getCell(rowIndex, ASSIGNSCALE).setValue(assignScale);// ���㵱ǰ�д��������
			} else {
				kdtCostEntries.getCell(rowIndex, COSTACCOUNT_NUMBER).setValue(null);
				kdtCostEntries.getCell(rowIndex, COSTACCOUNT).setValue(null);
				kdtCostEntries.getCell(rowIndex, ASSIGNSCALE).setValue(null);
			}
			precessCostAccountLongNumber(costAccountInfo, rowIndex);
		}

		if (colIndex == kdtCostEntries.getColumnIndex(CONSTRACTSCALE)) {
			Object contractScaleObj = kdtCostEntries.getCell(rowIndex, colIndex).getValue();
			if (!FDCHelper.isEmpty(contractScaleObj)) {
				String contractScaleStr = contractScaleObj.toString();
				BigDecimal contractScaleBig = new BigDecimal(contractScaleStr);
				if (contractScaleBig.compareTo(FDCHelper.ONE_HUNDRED) <= 0 && contractScaleBig.compareTo(FDCHelper.ZERO) > 0) {
					// ���Ƹ��������ʾ����
					ObjectValueRender render_scale = new ObjectValueRender();
					render_scale.setFormat(new IDataFormat() {
						public String format(Object o) {
							String str = o.toString();
							if (!FDCHelper.isEmpty(str)) {
								return str + "%";
							}
							return str;
						}
					});
					kdtCostEntries.getColumn(CONSTRACTSCALE).setRenderer(render_scale);
				}
			}
		}

	}


	protected void kdtCostEntries_editStopping(KDTEditEvent e) throws Exception {
		int colIndex = e.getColIndex();
		if (colIndex == kdtCostEntries.getColumnIndex(CONSTRACTSCALE)) {
			e.getValue();
			e.getOldValue();
		}
	}

	/**
	 * �ɱ���Ŀ�����봦��
	 * 
	 * @param caInfo
	 * @param rowIndex
	 */
	private void precessCostAccountLongNumber(CostAccountInfo caInfo, int rowIndex) {
		if (caInfo != null) {// �ɱ���Ŀ�����봦��
    		String longNumber=null;
    		longNumber = caInfo.getLongNumber().replaceAll("!", ".");
			kdtCostEntries.getCell(rowIndex, COSTACCOUNT_NUMBER).setValue(longNumber);
		}
	}

	/**
	 * �ж���һ����Լ֮�ڳɱ���Ŀ�Ƿ��ظ�<br>
	 * 
	 * ��name�ж��Ƿ��ظ������ʣ�����number�жϡ�Added By Owen_wen 2011-07-22 <br>
	 * 
	 * @param rowIndex
	 */
	private void isCostAccountDup(CostAccountInfo currentInfo, int rowIndex) {
		ICell costAccountNameCell = kdtCostEntries.getCell(rowIndex, COSTACCOUNT);
		ICell costAccountNumberCell = kdtCostEntries.getCell(rowIndex, COSTACCOUNT_NUMBER);
		if (FDCHelper.isEmpty(currentInfo)) {
			return;
		}
		int rowCount = kdtCostEntries.getRowCount();
		if (!FDCHelper.isEmpty(currentInfo.getLongNumber())) {
			int flag = 0;
			for (int i = 0; i < rowCount; i++) {
				CostAccountInfo forInfo = (CostAccountInfo) kdtCostEntries.getCell(i, COSTACCOUNT).getValue();
				if (forInfo == null) {
					break;
				}
				if (!FDCHelper.isEmpty(forInfo.getLongNumber())) {
					if (currentInfo.getLongNumber().equals(forInfo.getLongNumber())) {
						flag++;
						if (flag >= 2) {
							FDCMsgBox.showInfo("����ܺ�Լ�ɱ��������Ѿ���\"" + currentInfo.getName() + "\"�ɱ���Ŀ�������ټ�����Ӵ˳ɱ���Ŀ�ˣ�");
							costAccountNameCell.setValue(null);
							costAccountNumberCell.setValue(null);
							SysUtil.abort();
						}
					}
				}
			}
		}
	}

	/**
	 * ��Լ���ģ��ɱ�����ĳ�ɱ���Ŀ��Ӧ������Լ��������ֵ֮�� <br>
	 * 
	 * ��name�жϲ����ʣ�����number�жϡ�Added By Owen_wen 2011-07-22 <br>
	 * 
	 * @return
	 * @param costAccountInfo
	 */
	private BigDecimal getCostAccountScale(CostAccountInfo costAccountInfo, boolean flag) {
		String currentLongNumber = costAccountInfo.getLongNumber() != null ? costAccountInfo.getLongNumber() : null;
		BigDecimal scale = FDCHelper.ZERO;
		for (int i = 0; i < pteCollection.size(); i++) { // ��ܺ�Լ����
			ProgrammingTemplateEntireInfo forPteInfo = pteCollection.get(i);// ��ܺ�Լ
			if (flag) {
				PTECostCollection forCostCollection = forPteInfo.getPteCost(); // ��ܺ�Լ�гɱ����ɼ���
				for (int k = 0; k < forCostCollection.size(); k++) {
					PTECostInfo pteCostInfo = forCostCollection.get(k); // �ɱ�����
					if (pteCostInfo.getCostAccount() != null) {
						String forLongNumber = pteCostInfo.getCostAccount() != null ? pteCostInfo.getCostAccount().getLongNumber() : null;
						if (currentLongNumber.equals(forLongNumber) && !FDCHelper.isEmpty(pteCostInfo.getContractScale())) {
							scale = scale.add(pteCostInfo.getContractScale());
						}
					}
				}
			} else {
				if (!forPteInfo.getId().toString().equals(pteInfo.getId().toString())) {
					PTECostCollection forCostCollection = forPteInfo.getPteCost(); // ��ܺ�Լ�гɱ����ɼ���
					for (int k = 0; k < forCostCollection.size(); k++) {
						PTECostInfo pteCostInfo = forCostCollection.get(k); // �ɱ�����
						if (pteCostInfo.getCostAccount() != null) {
							String forLongNumber = pteCostInfo.getCostAccount() != null ? pteCostInfo.getCostAccount().getLongNumber() : null;
							if (currentLongNumber.equals(forLongNumber) && !FDCHelper.isEmpty(pteCostInfo.getContractScale())) {
								scale = scale.add(pteCostInfo.getContractScale());
							}
						}
					}
				}
			}
		}
		return scale;
	}
	/**
	 * ��������F7
	 */
	private void paymentTypeF7() {
		KDBizPromptBox kdtEconomyEntriese_costAccount_PromptBox = new KDBizPromptBox();
		kdtEconomyEntriese_costAccount_PromptBox.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7PaymentTypeQuery");
		kdtEconomyEntriese_costAccount_PromptBox.setVisible(true);
		kdtEconomyEntriese_costAccount_PromptBox.setEditable(true);
		kdtEconomyEntriese_costAccount_PromptBox.setDisplayFormat("$number$");
		kdtEconomyEntriese_costAccount_PromptBox.setEditFormat("$number$");
		kdtEconomyEntriese_costAccount_PromptBox.setCommitFormat("$number$");
		KDTDefaultCellEditor kdtEconomyEntriese_costAccount_CellEditor = new KDTDefaultCellEditor(kdtEconomyEntriese_costAccount_PromptBox);
		this.kdtEconomyEntriese.getColumn(PAYMENTTYPE).setEditor(kdtEconomyEntriese_costAccount_CellEditor);
		ObjectValueRender kdtCostEntries_paymentType_OVR = new ObjectValueRender();
		kdtCostEntries_paymentType_OVR.setFormat(new BizDataFormat("$name$"));
		this.kdtEconomyEntriese.getColumn(PAYMENTTYPE).setRenderer(kdtCostEntries_paymentType_OVR);
	}

	protected void kdtEconomyEntriese_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getRowIndex() > -1 && !oprtState.equals(OprtState.VIEW) && e.getType() != KDTStyleConstants.HEAD_ROW) {
			btnRemoveLines_economy.setEnabled(true);
		}
		if (e.getColIndex() == kdtEconomyEntriese.getColumnIndex(PAYMENTTYPE)) {

		}
	}

	protected void kdtEconomyEntriese_editStopped(KDTEditEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		if (colIndex == kdtEconomyEntriese.getColumnIndex(SCALE)) {
			Object scaleObj = kdtEconomyEntriese.getCell(rowIndex, colIndex).getValue();

			ObjectValueRender render_scale = new ObjectValueRender();
			render_scale.setFormat(new IDataFormat() {
				public String format(Object o) {
					String str = o.toString();
					if (!FDCHelper.isEmpty(str)) {
						return str + "%";
					}
					return str;
				}
			});
			kdtEconomyEntriese.getColumn(SCALE).setRenderer(render_scale);


			if (!FDCHelper.isEmpty(scaleObj)) {
				String scaleStr = scaleObj.toString();
				if (scaleStr.matches("^[1-9]\\d+$") || scaleStr.matches("^[1-9]")) {
					BigDecimal scaleBig = new BigDecimal(scaleStr);
					if (scaleBig.compareTo(FDCHelper.ONE_HUNDRED) <= 0 && scaleBig.compareTo(FDCHelper.ZERO) > 0) {
						if (getPayScaleAll().compareTo(FDCHelper.ONE_HUNDRED) > 0) {
							FDCMsgBox.showInfo("��������ۼƲ��ó���100%");
							kdtEconomyEntriese.getCell(rowIndex, colIndex).setValue(null);
							return;
						}
					} else {
						FDCMsgBox.showInfo("��¼��1-100������");
						kdtEconomyEntriese.getCell(rowIndex, colIndex).setValue(null);
						return;
					}
				} else {
					FDCMsgBox.showInfo("��¼��1-100������");
					kdtEconomyEntriese.getCell(rowIndex, colIndex).setValue(null);
					return;
				}
			}
		}
	}

	/**
	 * ��������ܺ�
	 * 
	 * @return
	 */
	private BigDecimal getPayScaleAll() {
		int rowCount = kdtEconomyEntriese.getRowCount();
		BigDecimal scale = new BigDecimal(0);
		for (int i = 0; i < rowCount; i++) {
			Object scaleObj = kdtEconomyEntriese.getCell(i, SCALE).getValue();
			if (!FDCHelper.isEmpty(scaleObj)) {
				BigDecimal temp = new BigDecimal(scaleObj.toString());
				scale = scale.add(temp);
			}
		}
		return scale;
	}

	/**
	 * �жϸ��������Ƿ��ظ�
	 * 
	 * @param enonomy_rowCount
	 */
	private void isEnonomDup() {
		int rowIndex = kdtEconomyEntriese.getSelectManager().getActiveRowIndex();
		ICell economyCell = kdtEconomyEntriese.getCell(rowIndex, PAYMENTTYPE);
		PaymentTypeInfo currentInfo = (PaymentTypeInfo) economyCell.getValue();
		if (FDCHelper.isEmpty(currentInfo)) {
			return;
		}
		int columnCount = kdtEconomyEntriese.getRowCount();
		if (!FDCHelper.isEmpty(currentInfo.getName())) {
			int flag = 0;
			for (int i = 0; i < columnCount; i++) {
				PaymentTypeInfo forInfo = (PaymentTypeInfo) kdtEconomyEntriese.getCell(i, PAYMENTTYPE).getValue();
				if (forInfo == null) {
					break;
				}
				if (!FDCHelper.isEmpty(forInfo.getName())) {
					if (currentInfo.getName().equals(forInfo.getName())) {
						flag++;
						if (flag >= 2) {
							FDCMsgBox.showInfo("����ܺ�Լ�����������Ѿ���\"" + currentInfo.getName() + "\"�������ͣ������ټ�����Ӵ˸��������ˣ�");
							economyCell.setValue(null);
							SysUtil.abort();
						}
					}
				}
			}
		}
	}

	//������״̬�£�Ҫ���и������������ʱ�����ʱ������id
	private String attachMentTempID=null;
	/**
	 * ��������
	 */
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		boolean isEdit = false;//Ĭ��Ϊ�鿴״̬
		if (OprtState.EDIT.equals(getOprtState())
				|| OprtState.ADDNEW.equals(getOprtState())) {
			isEdit = true;
		}
		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		AttachmentUIContextInfo info=getAttacheInfo();
		if (info == null) {
        	info=new AttachmentUIContextInfo();
		}
		if (FDCHelper.isEmpty(info.getBoID())) {
			String boID = getSelectBOID();
			if(boID == null){
				if (!isEdit) {
					if (attachMentTempID == null) {
						boID = acm.getAttID().toString();
						attachMentTempID = boID;
					} else {
						boID = attachMentTempID;
					}
				}else{
					return;
				}
			}
			info.setBoID(boID);
			acm.showAttachmentListUIByBoID(boID, this , isEdit);
		}
		info.setEdit(isEdit);
		if (getSelectBOID() != null) {
			StringBuffer allAttachmentName = getAllAttachmentName(getSelectBOID());
			if (!FDCHelper.isEmpty(allAttachmentName.toString())) {
				pteInfo.setAttachment(allAttachmentName.toString());
				this.txtAttachment.setText(allAttachmentName.toString());
				this.txtAttachment.setToolTipText(allAttachmentName.toString());
			} else {
				pteInfo.setAttachment(null);
				this.txtAttachment.setText(null);
			}
		}
	}

	protected AttachmentUIContextInfo getAttacheInfo() {
		return null;
	}
	protected final String getSelectBOID() {
		if (pteInfo == null)
			return null;
		String boID = pteInfo.getId() != null? pteInfo.getId().toString() : null;
		return boID;
	}
	
	/**
	 * ��ȡ��Լ������и��������ַ������������ֳ���";"���
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
			while(rs.next()){
				if (FDCHelper.isEmpty(rs.getString("FSimpleName"))) {
					sb.append(rs.getString("FName_l2") + ",");
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

	/**
	 * ��ܺ�Լ�����Ƿ��Ѵ���
	 * 
	 * @param name
	 */
	private void isNameDup(String name, String pteId, String ptId) {
		FDCSQLBuilder fdcBuilder = new FDCSQLBuilder();
		fdcBuilder.appendSql(" select * from T_CON_ProgrammingTE where FName_l2 = '" + name + "' ");
		fdcBuilder.appendSql(" and FID <> '" + pteId + "' ");
		fdcBuilder.appendSql(" and FParentID = '" + ptId + "' ");
		try {
			IRowSet iRowSet = fdcBuilder.executeQuery();
			if (iRowSet.next()) {
				FDCMsgBox.showInfo("��ܺ�Լ�����Ѵ��ڣ�����������");
				txtConstractName.requestFocus();
				SysUtil.abort();
			}
		} catch (BOSException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * ��ܺ�Լ�����Ƿ��Ѵ���
	 * @param number
	 */
	private void isNumberDup(String longNumber, String pteId, String ptId) {
		FDCSQLBuilder fdcBuilder = new FDCSQLBuilder();
		fdcBuilder.appendSql(" select * from T_CON_ProgrammingTE where FLongNumber = '" + longNumber + "' ");
		fdcBuilder.appendSql(" and FID <> '" + pteId + "' ");
		fdcBuilder.appendSql(" and FParentID = '" + ptId + "' ");
		try {
			IRowSet iRowSet = fdcBuilder.executeQuery();
			if (iRowSet.next()) {
				FDCMsgBox.showInfo("��ܺ�Լ�����Ѵ��ڣ�����������");
				txtConstractNumber.requestFocus();
				SysUtil.abort();
			}
		} catch (BOSException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * �ڳɱ����ɺ;�������ҳǩ�����������ɾ����ť
	 */
	private void setSmallButton() {
		btnAddnewLine_cost = new KDWorkButton();
		btnRemoveLines_cost = new KDWorkButton();
		btnAddnewLine_economy = new KDWorkButton();
		btnRemoveLines_economy = new KDWorkButton();

		btnAddnewLine_cost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddnewLine_cost_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		btnRemoveLines_cost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRemoveLine_cost_actionPerformed(e);
					if (kdtCostEntries.getRowCount() == 0) {
						btnRemoveLines_cost.setEnabled(false);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnAddnewLine_economy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddnewLine_enocomy_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnRemoveLines_economy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRemoveLine_enocomy_actionPerformed(e);
					if (kdtEconomyEntriese.getRowCount() == 0) {
						btnRemoveLines_economy.setEnabled(false);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		setButtonStyle(kdcCost, btnAddnewLine_cost, "������", "imgTbtn_addline");
		setButtonStyle(kdcCost, btnRemoveLines_cost, "ɾ����", "imgTbtn_deleteline");
		setButtonStyle(kdcEconomy, btnAddnewLine_economy, "������", "imgTbtn_addline");
		setButtonStyle(kdcEconomy, btnRemoveLines_economy, "ɾ����", "imgTbtn_deleteline");

		if (OprtState.VIEW.equals(getOprtState())) {
			setButtionEnable(false);
		} else {
			setButtionEnable(true);
		}
	}

	/**
	 * ���ð�ť��ʽ
	 * 
	 * @param kdContainer
	 * @param button
	 * @param text
	 * @param icon
	 */
	private void setButtonStyle(KDContainer kdContainer, KDWorkButton button, String text, String icon) {
		button.setText(text);
		button.setToolTipText(text);
		button.setVisible(true);
		button.setIcon(EASResource.getIcon(icon));
		kdContainer.addButton(button);
	}

	/**
	 * ���ư�ť�Ƿ����
	 * 
	 * @param isEnable
	 */
	private void setButtionEnable(boolean isEnable) {
		btnAddnewLine_cost.setEnabled(isEnable);
		btnRemoveLines_cost.setEnabled(isEnable);
		btnAddnewLine_economy.setEnabled(isEnable);
		btnRemoveLines_economy.setEnabled(isEnable);
	}

	protected void actionAddnewLine_cost_actionPerformed(ActionEvent e) {
		IRow row = kdtCostEntries.addRow();
		row.getCell(COST_ID).setValue(BOSUuid.create("A1B5BF1E"));
	}

	protected void actionRemoveLine_cost_actionPerformed(ActionEvent e) throws Exception {
		if (kdtCostEntries.getSelectManager().getActiveRowIndex() < 0) {
			FDCMsgBox.showInfo("��ѡ����");
			SysUtil.abort();
		}
		if (FDCMsgBox.OK == FDCMsgBox.showConfirm2("�Ƿ�ȷ��ɾ�����ݣ�")) {
			int rowIndex = this.kdtCostEntries.getSelectManager().getActiveRowIndex();
			removeLine(kdtCostEntries, rowIndex);
		}
	}

	protected void actionAddnewLine_enocomy_actionPerformed(ActionEvent e) {
		IRow row = kdtEconomyEntriese.addRow();
		row.getCell(ECONOMY_ID).setValue(BOSUuid.create("C4E9B822"));
	}

	protected void actionRemoveLine_enocomy_actionPerformed(ActionEvent e) throws Exception {
		if (kdtEconomyEntriese.getSelectManager().getActiveRowIndex() < 0) {
			FDCMsgBox.showInfo("��ѡ����");
			SysUtil.abort();
		}
		if (FDCMsgBox.OK == FDCMsgBox.showConfirm2("�Ƿ�ȷ��ɾ�����ݣ�")) {
			int rowIndex = this.kdtEconomyEntriese.getSelectManager().getActiveRowIndex();
			removeLine(kdtEconomyEntriese, rowIndex);
		}
	}

	/**
	 * ��ʾ������
	 */
	protected void loadLineFields(KDTable table, IRow row, IObjectValue obj) {
		dataBinder.loadLineFields(table, row, obj);
	}

	/**
	 * �½������У�����һ���µķ�¼�е�Ĭ��ֵ
	 */
	protected IObjectValue createNewDetailData(KDTable table, Object obj) {
		if (table == null) {
			return null;
		}
		if (obj instanceof ProgrammingContracCostInfo) {
			PTECostInfo newDetailInfo = new PTECostInfo();
			newDetailInfo.setId(BOSUuid.create("A1B5BF1E"));
			return (IObjectValue) newDetailInfo;
		}
		if (obj instanceof ProgrammingContractEconomyInfo) {
			PTEEnonomyInfo newDetailInfo = new PTEEnonomyInfo();
			newDetailInfo.setId(BOSUuid.create("C4E9B822"));
			return (IObjectValue) newDetailInfo;
		}
		return null;
	}

	/**
	 * ��ָ�������ɾ��ָ������
	 * 
	 * @param table
	 * @param rowIndex
	 * @throws Exception
	 */
	protected void removeLine(KDTable table, int rowIndex) throws Exception {
		IObjectValue detailData = (IObjectValue) table.getRow(rowIndex).getUserObject();
		table.removeRow(rowIndex);
		IObjectCollection collection = (IObjectCollection) table.getUserObject();
		if (collection != null) {
			if (detailData != null) {
				collection.removeObject(rowIndex);
			}
		}
	}
}