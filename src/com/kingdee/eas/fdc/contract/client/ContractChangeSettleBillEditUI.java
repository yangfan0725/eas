/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditUtil;
import com.kingdee.eas.fdc.contract.ChangeBillStateEnum;
import com.kingdee.eas.fdc.contract.ConChangeSettleEntryInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeEntryCollection;
import com.kingdee.eas.fdc.contract.ContractChangeSettleBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeSettleBillInfo;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeTypeEnum;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IChangeAuditBill;
import com.kingdee.eas.fdc.contract.ResponsibleStyleEnum;
import com.kingdee.eas.fdc.contract.app.OaUtil;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.AdvMsgBox;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * output class name
 */
public class ContractChangeSettleBillEditUI extends AbstractContractChangeSettleBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractChangeSettleBillEditUI.class);
    private ContractChangeBillInfo contractChangeInfo = null;
    protected KDWorkButton btnAddnewLine;
    protected KDWorkButton btnInsertLines;
    protected KDWorkButton btnRemoveLines;
    /**
     * output class constructor
     */
    public ContractChangeSettleBillEditUI() throws Exception
    {
        super();
    }

    protected void disablePrintFunc() {
	}
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    protected KDTable getDetailTable() {
    	return this.kdtEntrys;
    }

	protected void attachListeners() {
		// TODO Auto-generated method stub
		
	}

	protected void detachListeners() {
		// TODO Auto-generated method stub
		
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initTable();
		initBtn();
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		
		this.txtReasonDescription.setRequired(false);
		
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
	}
	
	 //���ð�ť��ʾЧ��
    private void setButtonStyle(KDWorkButton button , String text , String icon){
    	button.setText(text);
    	button.setToolTipText(text);
    	button.setVisible(true);
    	button.setIcon(EASResource.getIcon(icon));
    	kDContainer1.addButton(button);
    }
	private void initBtn() {
		btnAddnewLine = new KDWorkButton();
    	btnInsertLines = new KDWorkButton();
    	btnRemoveLines = new KDWorkButton();
    	btnAddnewLine.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                	actionAddLine_actionPerformed(e);
                }
                catch (Exception e1){
                    e1.printStackTrace();
                }
            }});
    	
    	btnInsertLines.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					actionInsertLine_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
    		
    	});
    	
    	btnRemoveLines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRemoveLine_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
    	setButtonStyle(btnAddnewLine,"������","imgTbtn_addline");
    	setButtonStyle(btnInsertLines,"������","imgTbtn_insert");
    	setButtonStyle(btnRemoveLines,"ɾ����","imgTbtn_deleteline");
	}
	
	private void initTable() {
		this.kdtEntrys.checkParsed();
		this.kdtEntrys.getColumn("amount").setEditor(CommerceHelper.getKDFormattedTextDecimalEditor());
		
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(true);
		ICellEditor formatTextEditor = new KDTDefaultCellEditor(formattedTextField);
		this.kdtEntrys.getColumn("proNumber").setEditor(formatTextEditor);
		this.kdtEntrys.getColumn("totalAmount").setEditor(CommerceHelper.getKDFormattedTextDecimalEditor());
	}

	protected ICoreBase getBizInterface() throws Exception {
		
		return ContractChangeSettleBillFactory.getRemoteInstance();
	}

	protected KDTextField getNumberCtrl() {
		
		return this.txtNumber;
	}
	
	protected IObjectValue createNewData() {
		ContractChangeSettleBillInfo info = new ContractChangeSettleBillInfo();
		info.setId(BOSUuid.create(info.getBOSType()));
		//Ϊ�˱������ȡ���ֵ ά����
		info.setBookedDate(new java.util.Date());
		if(this.getUIContext().get("contractChangeID") !=null ){
			String contractChangeID = this.getUIContext().get("contractChangeID").toString();
			SelectorItemCollection sel = new SelectorItemCollection();
			sel.add("*");
			sel.add("curProject.*");
			sel.add("contractBill.*");
			sel.add("mainSupp.*");
			sel.add("entrys.*");
			sel.add("orgUnit.*");
			try {
				contractChangeInfo = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillInfo(new ObjectUuidPK(contractChangeID), sel);
			} catch (EASBizException e) {
				this.handleException(e);
			} catch (BOSException e) {
				this.handleException(e);
			}
			
		} else {
			return info;
		}
		
		
		if(contractChangeInfo != null){
			info.setName(contractChangeInfo.getName()+"ȷ��");
			info.setConChangeBill(contractChangeInfo);
			info.setContractBill(contractChangeInfo.getContractBill());
			info.setCurProject(contractChangeInfo.getCurProject());
			info.setSupplier(contractChangeInfo.getMainSupp());
			info.setResponsibleStyle(ResponsibleStyleEnum.AllContain);
			info.setCreateTime(new Timestamp(new java.util.Date().getTime()));
			info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
			//���ԭ����ϳ��ֶ�
			ContractChangeEntryCollection  coll = contractChangeInfo.getEntrys();
			StringBuffer sb = new StringBuffer();
			for(int i = 0 ; i < coll.size() ; i ++){
				sb.append(",");
				sb.append(coll.get(i).getChangeContent());
			}
			info.setChangeReson(sb.toString().replaceFirst(",", ""));
			info.setOriginalAmount(contractChangeInfo.getContractBill().getOriginalAmount());
			info.setAmount(contractChangeInfo.getContractBill().getAmount());
			info.setOrgUnit(contractChangeInfo.getOrgUnit());
			info.setAllowAmount(new BigDecimal("0"));
			info.setIsFinish(true);
			//���ͬ�������
			String cotractId = contractChangeInfo.getContractBill().getId().toString();
			try {
				info.setLastAmount(FDCUtils.getContractLastAmt(null, cotractId));
			} catch (EASBizException e) {
				handleException(e);
			} catch (BOSException e) {
				handleException(e);
			}
			info.setNumber(contractChangeInfo.getNumber());
		}
		
		return info;
	}
	protected IObjectValue createNewDetailData(KDTable table) {
		return new ConChangeSettleEntryInfo();
	}
	
	protected final  static String  KD_AMOUNT = "amount"; 
	protected final  static String  KD_PRONUMBER = "proNumber";
	protected final  static String  KD_TOTALAMOUNT = "totalAmount";
	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		String key = kdtEntrys.getColumnKey(e.getColIndex());
		if(KD_PRONUMBER.equals(key) || KD_AMOUNT.equals(key)){
			IRow row = kdtEntrys.getRow(e.getRowIndex());
			BigDecimal amount = (BigDecimal)row.getCell(KD_AMOUNT).getValue()==null?new BigDecimal("0"):(BigDecimal)row.getCell(KD_AMOUNT).getValue() ;
			BigDecimal proNumber = (BigDecimal)row.getCell(KD_PRONUMBER).getValue() != null?(BigDecimal)row.getCell(KD_PRONUMBER).getValue():new BigDecimal("0");
			row.getCell(KD_TOTALAMOUNT).setValue(amount.multiply(proNumber).setScale(2,BigDecimal.ROUND_HALF_UP ));
			//���º�ͬ���½���Լ�����󶨽��
			BigDecimal totalAmount = FDCHelper.ZERO;
			BigDecimal cellAmount = null;
			for(int i = 0 ; i < kdtEntrys.getRowCount() ;i ++){
				cellAmount = (BigDecimal)kdtEntrys.getRow(i).getCell(KD_TOTALAMOUNT).getValue();
				totalAmount = totalAmount.add(cellAmount !=null?cellAmount:FDCHelper.ZERO);
			}
			this.txtAllowAmount.setValue(totalAmount.setScale(2,BigDecimal.ROUND_HALF_UP));
			BigDecimal originalAmount = (BigDecimal)this.txtOriginalAmount.getNumberValue();
//			this.txtLastAmount.setValue(totalAmount.add(originalAmount).setScale(2,BigDecimal.ROUND_HALF_UP));
		}
	}
//	private Map getEntryContracts() {
//		Map contractMap = new HashMap();
//		ContractBillInfo contract = (ContractBillInfo) this.prmtContractBill.getValue();
//		ProgrammingContractInfo programmingContract = contract.getProgrammingContract();
//		if (programmingContract == null || programmingContract.getId() == null) {
//			String conId = contract.getId().toString();
//			String oql = "select id, programmingContract where id = '".concat(conId).concat("'");
//			try {
//				ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(oql);
//				programmingContract = contractBillInfo.getProgrammingContract();
//				if (programmingContract == null || programmingContract.getId() == null) {
//					return contractMap;
//				}
//			} catch (Exception e) {
//				return contractMap;
//			}
//		}
//		BigDecimal amount = this.txtAllowAmount.getBigDecimalValue().subtract(this.txtOrigDeductAmount.getBigDecimalValue());
//		// �����ͬ��ͬ�Ĳ������ۼ�
//		BigDecimal amountValue = (BigDecimal) contractMap.get(contract.getId().toString());
//		if (amountValue != null) {
//			amount = amount.add(amountValue);
//		}
//		contractMap.put(contract.getId().toString(), amount);
//		return contractMap;
//	}
	private Object getCtrlParam() throws EASBizException, BOSException {
		String orgPk = this.editData.getOrgUnit().getId().toString();
		IObjectPK orgpk = new ObjectUuidPK(orgPk);
		return ParamControlFactory.getRemoteInstance().getParamValue(orgpk, "FDC228_ISSTRICTCONTROL");
	}
	/**
	 * ���ڲ����� + ǩԼ��� + �ۼƱ�� > ��ܺ�Լ �� �滮��� �Ĳ������Ʋ���
	 * 
	 * @throws Exception
	 */
	private boolean isCheckCtrlAmountPass() throws Exception {
		BOSUuid id = this.editData.getId();
		if (id == null) {
			// δ����ֱ���ύ
			throw new EASBizException(new NumericExceptionSubItem("1", "���ȱ������ύ"));
		}
		Object param = getCtrlParam();
		String paramValue = param == null ? "" : param.toString();
		if ("2".equals(paramValue)) {
			// �����ƻ��޲���
			return true;
		}
		
		BigDecimal amount = this.txtAllowAmount.getBigDecimalValue().subtract(this.txtOrigDeductAmount.getBigDecimalValue());
		ContractBillInfo contract = (ContractBillInfo) this.prmtContractBill.getValue();
		ProgrammingContractInfo pc = contract.getProgrammingContract();
		boolean isExist=ContractEstimateChangeBillFactory.getRemoteInstance().sub(pc, ContractEstimateChangeTypeEnum.CHANGE, amount, false,this.editData.getOrgUnit().getId().toString());
		if(!isExist){
			BigDecimal balance=BigDecimal.ZERO;
			if(pc==null){
				return true;
			}else{
				pc=ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(pc.getId()));
			}
			if(pc!=null&&pc.getBalance()!=null){
				balance=pc.getBalance();
			}
			if ("0".equals(paramValue)) {
				if(amount.compareTo(balance)>0){
					FDCMsgBox.showWarning("������Լ�滮����ֹ������");
					return false;
				}
				return true;
			} else if ("1".equals(paramValue)) {
				if(amount.compareTo(balance)>0){
					if(FDCMsgBox.showConfirm2(this,"������Լ�滮����ȷ���Ƿ��ύ��") == FDCMsgBox.CANCEL){
						return false;
					}
				}
				return true;
			}
		}
		return true;
	}
	private String getConAmountSql(Set contractIdSet) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT CB.FID CONID, CB.FNUMBER CONNUMBER, PC.FID PROID                       \n");
		sql.append(" ,ISNULL(SUM(PC.FBalance), 0) AMOUNT FROM T_CON_ProgrammingContract PC        \n");
		sql.append("	INNER JOIN T_CON_ContractBill CB ON CB.fProgrammingContract = PC.FID                 \n");
		sql.append("	WHERE CB.FID IN ").append(FDCUtils.buildBillIds(contractIdSet)).append("  \n");
		sql.append(" GROUP BY CB.FID, CB.FNUMBER, PC.FID");
		return sql.toString();
	}

	protected Object checkAmount(IObjectPK pk, Map contractMap) throws BOSException, EASBizException {
		StringBuffer result = new StringBuffer();
		FDCSQLBuilder fdcSB = new FDCSQLBuilder(getConAmountSql(contractMap.keySet()));
		IRowSet rs = fdcSB.executeQuery();

		Map pcMappingMap = new HashMap();
		Map conDetailMap = new HashMap(); // key: ��ͬ id -- value: ��ͬ number

		try {
			while (rs.next()) {
				String proId = rs.getString("PROID");
				String conId = rs.getString("CONID");
				BigDecimal proAmount = rs.getBigDecimal("AMOUNT"); // ��ܺ�Լ�滮���
				String conNumber = rs.getString("CONNUMBER");
				conDetailMap.put(conId, conNumber);

				// ͬһ��ܺ�Լ�º�ͬ��������л���
				ProConMapping mapping = (ProConMapping) pcMappingMap.get(proId);
				if (mapping == null) {
					Set conSet = new HashSet();
					conSet.add(conId);
					mapping = new ProConMapping(proId, conSet, proAmount);
					pcMappingMap.put(proId, mapping);
				} else {
					mapping.conSet.add(conId);
				}

				// �����������ͬ������
				BigDecimal conBudgetAmount = (BigDecimal) contractMap.get(conId);
				if (conBudgetAmount != null) {
					mapping.conBudgetAmountTotal = conBudgetAmount.add(mapping.conBudgetAmountTotal);
				}
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}
		for (Iterator it = pcMappingMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			ProConMapping mapping = (ProConMapping) entry.getValue();
			if (!mapping.isCheckAmountPass()) {
				result.append("��ͬ ");
				for (Iterator it2 = mapping.conSet.iterator(); it2.hasNext();) {
					String conId = (String) it2.next();
					String conNumber = (String) conDetailMap.get(conId);
					result.append(" [").append(conNumber).append("] ");
				}
				result.append("\n���ȷ�Ͻ����ڿ�ܺ�Լ�Ĺ滮��");
			}
		}

		return result.toString();
	}
	class ProConMapping {
		String proId;
		Set conSet;
		BigDecimal proBalanceAmount; // ��ܺ�Լ�滮���
		BigDecimal conBudgetAmountTotal; // ����º�ͬ�������ۼ�

		ProConMapping(String _proId, Set _conSet, BigDecimal _proBalanceAmount) {
			proId = _proId;
			conSet = _conSet;
			proBalanceAmount = _proBalanceAmount == null ? FDCHelper.ZERO : _proBalanceAmount;
			conBudgetAmountTotal = FDCHelper.ZERO;
		}

		boolean isCheckAmountPass() {
			return proBalanceAmount.compareTo(conBudgetAmountTotal) >= 0;
		}

		void meger(ProConMapping p) {
			conSet.addAll(p.conSet);
		}

		public int hashCode() {
			return proId == null ? "".hashCode() : proId.hashCode();
		}

		public boolean equals(Object obj) {
			if (obj == null || !(obj instanceof ProConMapping)) {
				return false;
			}
			return proId.equals(((ProConMapping) obj).proId);
		}
	}
	public boolean isBillInWorkflow(String id) throws BOSException{
		ProcessInstInfo instInfo = null;
		ProcessInstInfo procInsts[] = null;

		IEnactmentService service2 = EnactmentServiceFactory.createRemoteEnactService();
		procInsts = service2.getProcessInstanceByHoldedObjectId(id);
		int i = 0;
		for(int n = procInsts.length; i < n; i++){
			if("open.running".equals(procInsts[i].getState()) || "open.not_running.suspended".equals(procInsts[i].getState())){
				instInfo = procInsts[i];
			}
		}
		if(instInfo != null){
			return true;
		}else{
			return false;
		}
    }
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if (!isCheckCtrlAmountPass()) {
			return;
		}
		this.storeFields();
		this.verifyInputForSubmint();
		UserInfo u=SysContext.getSysContext().getCurrentUserInfo();
		CurProjectInfo project=CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(this.editData.getCurProject().getId()));
		if(project.isIsOA()&&u.getPerson()!=null&&!isBillInWorkflow(this.editData.getId().toString())){
			if(this.editData.getSourceFunction()==null){
				this.editData.setOaPosition(null);
				Map map=ContractBillFactory.getRemoteInstance().getOAPosition(u.getNumber());
				if(map.size()>1){
					UIContext uiContext = new UIContext(this);
					uiContext.put("map", map);
					uiContext.put("editData", this.editData);
			        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			        IUIWindow uiWindow = uiFactory.create(OaPositionUI.class.getName(), uiContext,null,OprtState.VIEW);
			        uiWindow.show();
			        if(this.editData.getOaPosition()==null){
			        	return;
			        }
				}else if(map.size()==1){
					Iterator<Entry<String, String>> entries = map.entrySet().iterator();
					while(entries.hasNext()){
						Entry<String, String> entry = entries.next();
					    String key = entry.getKey();
					    String value = entry.getValue();
					    this.editData.setOaPosition(key+":"+value);
					}
				}else{
					FDCMsgBox.showWarning(this,"��ȡOA���ʧ�ܣ�");
		    		return;
				}
			}else{
				this.editData.setOaOpinion(null);
				UIContext uiContext = new UIContext(this);
				uiContext.put("editData", this.editData);
		        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		        IUIWindow uiWindow = uiFactory.create(OaOpinionUI.class.getName(), uiContext,null,OprtState.VIEW);
		        uiWindow.show();
		        if(this.editData.getOaOpinion()==null){
		        	return;
		        }
			}
		}
		
		super.actionSubmit_actionPerformed(e);
		if (editData.getState() == FDCBillStateEnum.AUDITTING) {
			btnSave.setEnabled(false);
			btnSubmit.setEnabled(false);
			btnEdit.setEnabled(false);
			btnRemove.setEnabled(false);
		}
		this.setOprtState("VIEW");
//		this.actionAudit.setEnabled(true);
//		this.btnAudit.setVisible(true);
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sel = super.getSelectors();
		sel.add("state");
		sel.add("conChangeBill.*");
		sel.add("bookedDate");
		
		sel.add("sourceFunction");
		sel.add("oaPosition");
		sel.add("oaOpinion");
		return sel;
	}
	
	
	/**
	 * ���ñ�����㹤�������� by Cassiel_peng 2009-8-19
	 */
	private static boolean isConChangeAuditInWF() {
		String companyID = SysContext.getSysContext().getCurrentCostUnit().getId().toString();
		boolean retValue=false;
		try {
			 retValue=FDCUtils.getDefaultFDCParamByKey(null,companyID, FDCConstants.FDC_PARAM_CHANGESETTAUDIT);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return retValue;
	}
	
	/**
	 * ���ú�ͬ������۰������ by Cassiel_peng 2009-8-19
	 */
	private static boolean isIncludeChangeAudit() {
		String companyID = SysContext.getSysContext().getCurrentCostUnit().getId().toString();
		boolean retValue=false;
		try {
			 retValue=FDCUtils.getDefaultFDCParamByKey(null,companyID, FDCConstants.FDC_PARAM_INCLUDECHANGEAUDIT);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return retValue;
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		
//		��鵥���Ƿ��ڹ�������
		FDCClientUtils.checkBillInWorkflow(this, this.editData.getId().toString());
	
//		ContractChangeBillInfo conChangeBill = editData.getConChangeBill();
//		
//		FDCClientVerifyHelper.verifyRequire(this);
//		SelectorItemCollection sel = new SelectorItemCollection();
//		conChangeBill.setState(FDCBillStateEnum.ANNOUNCE);
//    	
//    	conChangeBill.setOriBalanceAmount(FDCHelper.ZERO);
//    	conChangeBill.setBalanceAmount(FDCHelper.ZERO);
//    	conChangeBill.setHasSettled(false);
//    	conChangeBill.setSettleTime(null);
//    	sel.clear();
//    	sel.add("balanceAmount");
//    	sel.add("oriBalanceAmount");
//    	sel.add("state");
//    	sel.add("hasSettled");
//    	sel.add("settleTime");
//    	ContractChangeBillFactory.getRemoteInstance().updatePartial(conChangeBill, sel);
//    	//���µ����ϵĺ�ͬ���������
//    	String cotractId = editData.getContractBill().getId().toString();
//    	try {
//			editData.setLastAmount(FDCUtils.getContractLastAmt(null, cotractId));
//			sel = new SelectorItemCollection();
//        	sel.add("lastAmount");
//        	ContractChangeSettleBillFactory.getRemoteInstance().updatePartial(editData, sel);
//		} catch (EASBizException e1) {
//			handleException(e1);
//		} catch (BOSException e1) {
//			handleException(e1);
//		}
//		ChangeAuditUtil.synContractProgAmt(editData.getAllowAmount(), conChangeBill, false);
		super.actionUnAudit_actionPerformed(e);
		ContractChangeSettleBillListUI parentUi = (ContractChangeSettleBillListUI)this.getUIContext().get("parent");
    	if(parentUi!=null){
    		parentUi.doRefresh();
    	}
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		
//		��鵥���Ƿ��ڹ�������
		FDCClientUtils.checkBillInWorkflow(this, this.editData.getId().toString());
	
//		ContractChangeBillInfo conChangeBill = editData.getConChangeBill();
//		BigDecimal balanceAmt = conChangeBill.getBalanceAmount();
//    	if(!isConChangeAuditInWF()){
//    		BigDecimal  allowAmount = editData.getAllowAmount();
//    		conChangeBill.setOriBalanceAmount(allowAmount);
//        	BigDecimal exRate = conChangeBill.getExRate();
//        	conChangeBill.setBalanceAmount(allowAmount.multiply(exRate).setScale(2, BigDecimal.ROUND_HALF_UP));
//    		conChangeBill.setHasSettled(true);
//    		conChangeBill.setSettleTime(DateTimeUtils.truncateDate(new java.util.Date()));
//    		FDCClientVerifyHelper.verifyRequire(this);
//        	//�������ʱ���Զ���ֶԹ�������������۵�У���뵯�����治��ͬһ������
//        	ConChangeSplitFactory.getRemoteInstance().changeSettle(conChangeBill);
//        	SelectorItemCollection sel = new SelectorItemCollection();
//        	sel.add("state");
//        	conChangeBill.setState(FDCBillStateEnum.VISA);
//        	ContractChangeBillFactory.getRemoteInstance().updatePartial(conChangeBill, sel);
//    		AbstractSplitInvokeStrategy strategy = SplitInvokeStrategyFactory.createSplitStrategyByParam(editData.getId().toString(), this);
//    		strategy.invokeSplit();
//    	}else{
//    		FDCClientVerifyHelper.verifyRequire(this);
//    		SelectorItemCollection sel = new SelectorItemCollection();
//    		conChangeBill.setState(FDCBillStateEnum.VISA);
//        	BigDecimal  allowAmount = editData.getAllowAmount();
//        	conChangeBill.setOriBalanceAmount(allowAmount);
//        	BigDecimal exRate = conChangeBill.getExRate();
//        	conChangeBill.setBalanceAmount(allowAmount.multiply(exRate).setScale(2, BigDecimal.ROUND_HALF_UP));
//        	conChangeBill.setHasSettled(true);
//        	conChangeBill.setSettleTime(DateTimeUtils.truncateDate(new java.util.Date()));
//        	sel.clear();
//        	sel.add("balanceAmount");
//        	sel.add("oriBalanceAmount");
//        	sel.add("state");
//        	sel.add("hasSettled");
//        	sel.add("settleTime");
//        	ContractChangeBillFactory.getRemoteInstance().updatePartial(conChangeBill, sel);
//    		
//    		
//    	}
//    	//���µ����ϵĺ�ͬ���������
//    	String cotractId = editData.getContractBill().getId().toString();
//    	try {
//			editData.setLastAmount(FDCUtils.getContractLastAmt(null, cotractId));
//			SelectorItemCollection sel = new SelectorItemCollection();
//        	sel.add("lastAmount");
//        	ContractChangeSettleBillFactory.getRemoteInstance().updatePartial(editData, sel);
//		} catch (EASBizException e1) {
//			handleException(e1);
//		} catch (BOSException e1) {
//			handleException(e1);
//		}
//    	ChangeAuditUtil.synContractProgAmt(balanceAmt, conChangeBill, true);
    	super.actionAudit_actionPerformed(e);
    	ContractChangeSettleBillListUI parentUi = (ContractChangeSettleBillListUI)this.getUIContext().get("parent");
    	if(parentUi!=null){
    		parentUi.doRefresh();
    	}
	}
	
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
        ArrayList idList = new ArrayList();
        if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null){
			MsgBox.showWarning(this,FDCClientUtils.getRes("cantPrint"));
			return;
        }
        ChangeConfirmProvider data = new ChangeConfirmProvider(editData.getString("id"),getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
	}
	
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
        ArrayList idList = new ArrayList();
        if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null){
			MsgBox.showWarning(this,FDCClientUtils.getRes("cantPrint"));
			return;
        }
        ChangeConfirmProvider data = new ChangeConfirmProvider(editData.getString("id"),getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
	}
	
	
	/**
     * �״��Ӧ��Ŀ¼
     */
	protected String getTDFileName() {
    	return "/bim/fdc/contract/changeConfirmbill";
	}
	
	/**
	*  �״��Ӧ��Query
	*/
	protected IMetaDataPK getTDQueryPK() {
		//ChangeAuditForPrintQuery
    	return new MetaDataPK("com.kingdee.eas.fdc.contract.app.ChangeConfirmPrintQuery");
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		FDCClientUtils.checkBillInWorkflow(this, this.editData.getId().toString());
		super.actionRemove_actionPerformed(e);
	}

	public void actionRemoveLine_actionPerformed(ActionEvent arg0)
			throws Exception {
		super.actionRemoveLine_actionPerformed(arg0);
		BigDecimal cellAmount = FDCHelper.ZERO;
		BigDecimal totalAmount = FDCHelper.ZERO;
		for(int i = 0 ; i < kdtEntrys.getRowCount() ;i ++){
			cellAmount = (BigDecimal)kdtEntrys.getRow(i).getCell(KD_TOTALAMOUNT).getValue();
			totalAmount = totalAmount.add(cellAmount !=null?cellAmount:FDCHelper.ZERO);
		}
		this.txtAllowAmount.setValue(totalAmount.setScale(2,BigDecimal.ROUND_HALF_UP));
		BigDecimal originalAmount = (BigDecimal)this.txtOriginalAmount.getNumberValue();
//		this.txtLastAmount.setValue(totalAmount.add(originalAmount).setScale(2,BigDecimal.ROUND_HALF_UP));
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if(cbIsFee.isSelected()){
			for(int i = 0 ; i < kdtEntrys.getRowCount(); i++){
				IRow row = kdtEntrys.getRow(i);
				if(row.getCell("changeContent").getValue()==null){
					FDCMsgBox.showWarning(this,"��"+(i+1)+"�еı䶯��Ŀ���ݲ���Ϊ�գ�");
					this.abort();
				}
				if(row.getCell(KD_AMOUNT).getValue()==null){
					FDCMsgBox.showWarning(this,"��"+(i+1)+"�еĵ��۲���Ϊ�գ�");
					this.abort();
				}
				if(row.getCell(KD_PRONUMBER).getValue()==null){
					FDCMsgBox.showWarning(this,"��"+(i+1)+"�еĹ���������Ϊ�գ�");
					this.abort();
				}
			}
		}
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		
//		��鵥���Ƿ��ڹ�������
		FDCClientUtils.checkBillInWorkflow(this,this.editData.getId().toString());
		super.actionEdit_actionPerformed(e);
	}
	protected void btnViewContractChange_actionPerformed(ActionEvent e) throws Exception {
		if(this.editData.getConChangeBill()!=null){
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", this.editData.getConChangeBill().getId());
	        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
	        IUIWindow uiWindow = uiFactory.create(ContractChangeBillEditUI.class.getName(), uiContext,null,OprtState.VIEW);
	        uiWindow.show();
		}else{
			FDCMsgBox.showWarning(this,"�޹������ָ���");
		}
	}
	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
    	String id = this.editData.getId().toString();
    	ContractChangeSettleBillInfo info=ContractChangeSettleBillFactory.getRemoteInstance().getContractChangeSettleBillInfo(new ObjectUuidPK(id));
    	if(info.getSourceFunction()!=null){
    		FDCSQLBuilder builder=new FDCSQLBuilder();
			builder.appendSql("select fviewurl from t_oa");
			IRowSet rs=builder.executeQuery();
			String url=null;
			while(rs.next()){
				url=rs.getString("fviewurl");
			}
			if(url!=null){
				String mtLoginNum = OaUtil.encrypt(SysContext.getSysContext().getCurrentUserInfo().getNumber());
				String s2 = "&MtFdLoinName=";
				StringBuffer stringBuffer = new StringBuffer();
	            String oaid = URLEncoder.encode(info.getSourceFunction());
	            String link = String.valueOf(stringBuffer.append(url).append(oaid).append(s2).append(mtLoginNum));
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+link);  
			}
    	}else{
    		super.actionWorkFlowG_actionPerformed(e);
    	}
	}
	 public void actionAuditResult_actionPerformed(ActionEvent e)
		throws Exception {
		 String id = this.editData.getId().toString();
	    	ContractChangeSettleBillInfo info=ContractChangeSettleBillFactory.getRemoteInstance().getContractChangeSettleBillInfo(new ObjectUuidPK(id));
	    	if(info.getSourceFunction()!=null){
	    		FDCSQLBuilder builder=new FDCSQLBuilder();
				builder.appendSql("select fviewurl from t_oa");
				IRowSet rs=builder.executeQuery();
				String url=null;
				while(rs.next()){
					url=rs.getString("fviewurl");
				}
				if(url!=null){
					String mtLoginNum = OaUtil.encrypt(SysContext.getSysContext().getCurrentUserInfo().getNumber());
					String s2 = "&MtFdLoinName=";
					StringBuffer stringBuffer = new StringBuffer();
		            String oaid = URLEncoder.encode(info.getSourceFunction());
		            String link = String.valueOf(stringBuffer.append(url).append(oaid).append(s2).append(mtLoginNum));
					Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+link);  
				}
	    	}else{
	    		super.actionAuditResult_actionPerformed(e);
	    	}
	 }
}