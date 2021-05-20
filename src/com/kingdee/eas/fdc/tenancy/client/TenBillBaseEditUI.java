/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDOptionPane;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.calc.CalculatorDialog;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.AmusementUI;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.tenancy.ITenBillBase;
import com.kingdee.eas.fdc.tenancy.TenBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public abstract class TenBillBaseEditUI extends AbstractTenBillBaseEditUI {
	private final static String CANTAUDIT = "cantAudit";

	private static final String CANTEDIT = "cantEdit";

	private static final String CANTREMOVE = "cantRemove";

	private final static String CANTUNAUDIT = "cantUnAudit";

	private final static String CANTAUDITEDITSTATE = "cantAuditEditState";

	private final static String CANTUNAUDITEDITSTATE = "cantUnAuditEditState";

	private final static int PRECSION = 2;

	//�Ƿ񱣴����
	public boolean isSaveAction = false;

	private static final Logger logger = CoreUIObject.getLogger(TenBillBaseEditUI.class);

	//������֯
	protected CompanyOrgUnitInfo company = null;
	
	//��ǰ��֯
	protected FullOrgUnitInfo orgUnitInfo = null;
	
	//��λ��
	protected CurrencyInfo baseCurrency = null;
	//��ͬ����
	protected ContractBillInfo contractBill = null;
	
	//��ǰ������Ŀ
	protected CurProjectInfo curProject = null;

    // ������⣺�����⣬�������޸ģ��鿴��ƾ֤����
    protected String titleMain, titleNew, titleModify, titleView, billIndex = "";

	//���óɱ��ɱ��½�
	protected boolean isIncorporation = false;
	
	//�Ǽ�����
	protected Date bookedDate = null;	
	//��ǰ�ڼ�
	protected PeriodInfo curPeriod = null ;
	//�ɱ����ǲ���
	protected boolean isCost = true;
	//�Ƿ��Ѿ�����
	protected boolean isFreeze = false;
	//��¼���ڼ�
	protected PeriodInfo canBookedPeriod = null ;
	
	/**
	 * output class constructor
	 */
	public TenBillBaseEditUI() throws Exception {
		super();
		jbInit() ;
	}

	private void jbInit() {
	    //titleMain = getUITitle();
	    titleNew = getStr("titleNew");
	    titleModify = getStr("titleModify");
	    titleView = getStr("titleView");
	}

    public String getStr(String name) {
        return getStr(FDCConstants.VoucherResource, name);
    }

    public String getStr(String resFile,String name) {
        return EASResource.getString(resFile, name);
    }

    protected void refreshUITitle() {
    	if(titleMain==null){
    		return;
    	}

        String state = getOprtState();
        if (OprtState.ADDNEW.equals(state)) {
            billIndex = "";
            this.setUITitle(titleMain + " - " + titleNew);
        } else {
            if (OprtState.EDIT.equals(state)) {
                this.setUITitle(titleMain + " - " + titleModify + billIndex);
            } else {
                boolean isFromMsgCenter = false;
                Map uiContext = getUIContext();
                if (uiContext != null) {
                    isFromMsgCenter = Boolean.TRUE.equals(uiContext.get("isFromWorkflow"));
                }
                if (!isFromMsgCenter) {
                    this.setUITitle(titleMain + " - " + titleView + billIndex);
                }
            }
        }
    }
    
    //�ؼ����ݱ仯ͳһ�¼�,ʹ�øù���ͳһ�ؼ��¼�,������loadFieldsʱ����ͣ�����ؼ��¼�
	public class ControlDateChangeListener implements DataChangeListener {
		private String shortCut = null; 
		public ControlDateChangeListener(String shortCut){
			this.shortCut = shortCut;
		}
        public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
            try {
            	controlDate_dataChanged(e,this);
            } catch (Exception exc) {
                handUIException(exc);
            } finally {
            }
        }
		public String getShortCut() {
			return shortCut;
		}
    };
    
    protected void controlDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e,ControlDateChangeListener listener) throws Exception
    {
    	//ҵ��EditUI���أ�ʵ�ֿؼ����ݱ仯�¼�
    }
    

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.SUBMITTED, CANTAUDIT);
		String id = getSelectBOID();
		if (id != null) {
			getTenBillInterface().audit(BOSUuid.read(id));
		}
		FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		setSaveActionStatus();
	}
	
	private ITenBillBase getTenBillInterface() throws Exception {
		return (ITenBillBase)getBizInterface();
	}

	/**
	 * ͬ�����ݿ����ݵ�����,��������/����������ʾ������,��������
	 * @throws Exception
	 */
	protected void syncDataFromDB() throws Exception {
		//�ɴ��ݹ�����ID��ȡֵ����
        if(getUIContext().get(UIContext.ID) == null)
        {
            String s = EASResource.getString(FrameWorkClientUtils.strResource + "Msg_IDIsNull");
            MsgBox.showError(s);
            SysUtil.abort();
        }
        IObjectPK pk = new ObjectUuidPK(BOSUuid.read(getUIContext().get(UIContext.ID).toString()));
        setDataObject(getValue(pk));
        loadFields();
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeEditOrRemove(CANTEDIT);
		super.actionEdit_actionPerformed(e);
		setSaveActionStatus();
		setNumberTextEnabled();
		
		//���ò���ɱ�һ�廯
		enablePeriodComponent(this,isIncorporation);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeEditOrRemove(CANTREMOVE);
		checkRef(getSelectBOID());
		super.actionRemove_actionPerformed(e);
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if(getTenBillBaseInfo().getState() == null)
			getTenBillBaseInfo().setState(FDCBillStateEnum.SAVED);
		setSaveAction(true);
		super.actionSave_actionPerformed(e);

		handleOldData();


	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		setSaveAction(false);

		if(!checkCanSubmit()){
			MsgBox.showWarning(this,"����״̬�Ѿ�������л�������ˣ��������ύ");
			SysUtil.abort();
		}

		super.actionSubmit_actionPerformed(e);

		if(!getOprtState().equals(OprtState.ADDNEW)){
			actionSave.setEnabled(false);
		}else{
			actionSave.setEnabled(true);
			handleCodingRule();
		}
		handleOldData();
	}

	protected boolean checkCanSubmit() throws Exception {
		
//		if(isIncorporation && ((FDCBillInfo)editData).getPeriod()==null){
//			MsgBox.showWarning(this,"���óɱ��½��ڼ䲻��Ϊ�գ����ڻ�������ά���ڼ������ѡ��ҵ������");
//			SysUtil.abort();
//		}
		
		if(editData.getId()==null){
			return true;
		}

		return ((ITenBillBase)getBillInterface()).checkCanSubmit(editData.getId().toString());
	}

	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
        AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
        String boID = getSelectBOID();
        if(boID == null)
        {
            return;
        }
        boolean isEdit = false;
        if(STATUS_ADDNEW.equals(getOprtState())||STATUS_EDIT.equals(getOprtState()))
        {
            isEdit = true;
        }
        acm.showAttachmentListUIByBoID(boID,this,isEdit); // boID �� �븽�������� ҵ������ ID
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.AUDITTED, CANTUNAUDIT);
		checkRef(getSelectBOID());
		String id = getSelectBOID();
		if (id != null) {
			getTenBillInterface().unAudit(BOSUuid.read(id));
		}
		FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		setSaveActionStatus();
	}


	public void checkBeforeAuditOrUnAudit(FDCBillStateEnum state, String warning) {

		//��鵥���Ƿ��ڹ�������
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());

		boolean b = getTenBillBaseInfo() != null
				&& getTenBillBaseInfo().getState() != null
				&& getTenBillBaseInfo().getState().equals(state);
		if (!b) {
			MsgBox.showWarning(this, FDCClientUtils.getRes(warning));
			SysUtil.abort();
		}

		 if(getOprtState().equals(STATUS_EDIT)) {
			 String warn = null;
			 if(state.equals(FDCBillStateEnum.AUDITTED)) {
				 warn = CANTUNAUDITEDITSTATE;
			 }
			 else {
				 warn = CANTAUDITEDITSTATE;
			 }
			 MsgBox.showWarning(this, FDCClientUtils.getRes(warn));
			 SysUtil.abort();
		 }
	}

	protected void checkBeforeEditOrRemove(String warning) {
		FDCBillStateEnum state = getTenBillBaseInfo().getState();
		if (state != null
				&& (state == FDCBillStateEnum.AUDITTING || state == FDCBillStateEnum.AUDITTED || state == FDCBillStateEnum.CANCEL )) {
			MsgBox.showWarning(this, ContractClientUtils.getRes(warning));
			SysUtil.abort();
		}
	}

	/**
	 *
	 * ���������ݵ�ǰ���ݵ�״̬����ִ�б��滹���ύ����
	 *
	 * @author:liupd
	 * @see com.kingdee.eas.framework.client.EditUI#checkBeforeWindowClosing()
	 */
	public boolean checkBeforeWindowClosing() {
		if (hasWorkThreadRunning()) {
			return false;
		}

		if(getTableForPrintSetting()!=null){
			this.savePrintSetting(this.getTableForPrintSetting());
		}

		boolean b = true;

		if (!b) {
			return b;
		} else {

			// У��storeFields()�Ƿ��׳��쳣
			// Exception err = verifyStoreFields();

			// storeFields()�׳��쳣����editdata�иı䣬ѯ���Ƿ񱣴��˳�
			if (isModify()) {
				// editdata�иı�
				int result = MsgBox.showConfirm3(this, EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Confirm_Save_Exit"));

				if (result == KDOptionPane.YES_OPTION) {

					try {
						if (getTenBillBaseInfo().getState() == null
								|| getTenBillBaseInfo().getState() == FDCBillStateEnum.SAVED) {
							actionSave.setDaemonRun(false);
							// actionSubmit.actionPerformed(null);
							// by jakcy 2005-1-6
							// �����ָ�����ΪbeforeAction()��ʹ��ActionEvent��
							ActionEvent event = new ActionEvent(btnSave,
									ActionEvent.ACTION_PERFORMED, btnSave
											.getActionCommand());
							actionSave.actionPerformed(event);
							return !actionSave.isInvokeFailed();
						} else {
							actionSubmit.setDaemonRun(false);
							// actionSubmit.actionPerformed(null);
							// by jakcy 2005-1-6
							// �����ָ�����ΪbeforeAction()��ʹ��ActionEvent��
							ActionEvent event = new ActionEvent(btnSubmit,
									ActionEvent.ACTION_PERFORMED, btnSubmit
											.getActionCommand());
							actionSubmit.actionPerformed(event);
							return !actionSubmit.isInvokeFailed();
							// actionSubmit_actionPerformed(event);
						}
						// return true;
					} catch (Exception exc) {
						// handUIException(exc);
						return false;
					}

				} else if (result == KDOptionPane.NO_OPTION) {
					// stopTempSave();
					return true;
				} else {
					return false;
				}
			} else {
				// stopTempSave();
				return true;
			}

		}

	}

	protected IObjectValue createNewData() {
		/* TODO �Զ����ɷ������ */
		return null;
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		/* TODO �Զ����ɷ������ */
		return null;
	}

	private void disablePrintFunc() {
		actionPrint.setEnabled(false);
		actionPrint.setVisible(false);
		actionPrintPreview.setEnabled(false);
		actionPrintPreview.setVisible(false);
	}

	/* ������ӿ�,����ĳɳ��󷽷��������ʵ��
	 * @see com.kingdee.eas.framework.client.CoreBillEditUI#getBizInterface()
	 */
	protected abstract ICoreBase getBizInterface() throws Exception;
	
	/*
	 *  ������ӿ�,����ĳɳ��󷽷��������ʵ��,���û�з�¼table,����null
	 * (non-Javadoc)
	 * @see com.kingdee.eas.framework.client.CoreBillEditUI#getDetailTable()
	 */
	protected abstract KDTable getDetailTable();

	private TenBillBaseInfo getTenBillBaseInfo() {
		return (TenBillBaseInfo) editData;
	}

	//�����������֮��,����Ϳ����޸ĵȲ���.���Խ��˷����ſ�,�Ա����
	protected void handleActionStatusByCurOrg() {
		if(true){
			//ȡ�������岻��������Ĺ涨 by sxhong
			return;
		}
		// ���������ɱ����ģ���������ɾ����
		if (!SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit()) {
			actionAddNew.setEnabled(false);
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
			actionAddNew.setVisible(false);
			actionEdit.setVisible(false);
			actionRemove.setVisible(false);
			actionAttachment.setEnabled(false);
			actionAddLine.setEnabled(false);
			actionInsertLine.setEnabled(false);
			actionRemoveLine.setEnabled(false);
			actionCopy.setEnabled(false);
			actionCopy.setVisible(false);
		}
	}

	/**
	 *
	 * �������������������ݣ�δ���޸ģ�ֱ�ӹر�ʱ�����ֱ�����ʾ
	 * @author:liupd
	 * ����ʱ�䣺2006-10-13 <p>
	 */
	protected void handleOldData() {
		this.storeFields();
		this.initOldData(this.editData);
	}

	public boolean isModify() {
		if (STATUS_VIEW.equals(getOprtState())) {
			return false;
		}
		return super.isModify();
	}

	
	//������
	protected Map listenersMap = new HashMap();
    protected abstract void attachListeners();
    protected abstract void detachListeners();
    
    protected void addDataChangeListener(JComponent com) {
    	
    	EventListener[] listeners = (EventListener[] )listenersMap.get(com);
    	
    	if(listeners!=null && listeners.length>0){
	    	if(com instanceof KDPromptBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDPromptBox)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDFormattedTextField){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDFormattedTextField)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDDatePicker){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDDatePicker)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	} else if(com instanceof KDComboBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDComboBox)com).addItemListener((ItemListener)listeners[i]);
	    		}
	    	}
    	}
		
    }
        
    protected void removeDataChangeListener(JComponent com) {
		EventListener[] listeners = null;	
  	
		if(com instanceof KDPromptBox){
			listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDPromptBox)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDFormattedTextField){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDFormattedTextField)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDDatePicker){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDDatePicker)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	} 
    	else if(com instanceof KDComboBox){
    		listeners = com.getListeners(ItemListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDComboBox)com).removeItemListener((ItemListener)listeners[i]);
    		}
    	} 
		
		if(listeners!=null && listeners.length>0){
			listenersMap.put(com,listeners );
		}
    }
    
	public void loadFields() {
		//ע��������
		detachListeners();
		
		super.loadFields();

		setSaveActionStatus();

        int idx = idList.getCurrentIndex();
        if (idx >= 0) {
            billIndex = "(" + (idx + 1) + ")";
        } else {
        	billIndex = "";
        }
		refreshUITitle();
		
		//�����ϼ�����
		attachListeners();
	}

	protected void setSaveActionStatus() {
		if (getTenBillBaseInfo().getState() == FDCBillStateEnum.SUBMITTED) {
			actionSave.setEnabled(false);
		}
	}

	//�����������
	protected  void fetchInitParam() throws Exception{

		if(company==null){
			return ;
		}
		//���óɱ�����һ�廯
		HashMap paramItem = FDCUtils.getDefaultFDCParam(null,company.getId().toString());
		if(paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION)!=null){
			isIncorporation = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION).toString()).booleanValue();
		}				
	}

	//�����������
	protected  void fetchInitData() throws Exception{
		//��ͬId
		String contractBillId = (String) getUIContext().get("contractBillId");
		if(contractBillId==null){
			Object object  = getUIContext().get("ID");
			if(object instanceof String){
				contractBillId = (String)object;
			}else if(object!=null){
				contractBillId = object.toString();
			}
		}
		//������ĿId
		BOSUuid projectId = ((BOSUuid) getUIContext().get("projectId"));
		
		Map param = new HashMap();
		param.put("contractBillId",contractBillId);
		if(projectId!=null){
			param.put("projectId",projectId.toString());
		}
		Map initData = ((ITenBillBase)getBizInterface()).fetchInitData(param);

		//��λ��
		baseCurrency = (CurrencyInfo)initData.get(FDCConstants.FDC_INIT_CURRENCY);
		//��λ��
		company = (CompanyOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_COMPANY);
		if(company==null){
			company =  SysContext.getSysContext().getCurrentFIUnit();
		}
		//
		orgUnitInfo = (FullOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_ORGUNIT);
		if(orgUnitInfo==null){
			orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
		}
		
		//��ͬ����
		contractBill = (ContractBillInfo)initData.get(FDCConstants.FDC_INIT_CONTRACT);
		
		//��ǰ����
		bookedDate = (Date)initData.get(FDCConstants.FDC_INIT_DATE);
		if(bookedDate==null){
//			bookedDate = new Date();
			bookedDate = new Date(FDCDateHelper.getServerTimeStamp().getTime());
		}		
		//��ǰ�ڼ�
		curPeriod = (PeriodInfo) initData.get(FDCConstants.FDC_INIT_PERIOD);
		//�Ƿ��Ѿ�����
		if(initData.get(FDCConstants.FDC_INIT_ISFREEZE)!=null){
			isFreeze = ((Boolean)initData.get(FDCConstants.FDC_INIT_ISFREEZE)).booleanValue();
		}
		//��¼���ڼ�
		if(isFreeze){
			canBookedPeriod = (PeriodInfo) initData.get(FDCConstants.FDC_INIT_BOOKEDPERIOD);
		}else{
			canBookedPeriod = curPeriod;
		}		

		//������Ŀ
		curProject = (CurProjectInfo) initData.get(FDCConstants.FDC_INIT_PROJECT);
	}

	public void onLoad() throws Exception {

		//��ȡ��ʼ����������
		fetchInitData();
		
		//��ȡ����
		fetchInitParam();
		
		super.onLoad();
		if(isUseMainMenuAsTitle()){
			FDCClientHelper.setUIMainMenuAsTitle(this);
		}
		handleCodingRule();

		FDCClientHelper.addSqlMenu(this, this.menuEdit);

		btnSubmit.setToolTipText(FDCClientUtils.getRes("submit"));

		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.actionRemove.setVisible(false);
		
		disablePrintFunc();
		updateButtonStatus();

		// �������н��ؼ�KDFormattedTextField��BasicNumberTextField�������Сֵ�����ȣ��Ҷ���
		FDCHelper.setComponentPrecision(this.getComponents(), TenBillBaseEditUI.PRECSION);

		//
		setNumberTextEnabled();

		//���ò���ɱ�һ�廯
		enablePeriodComponent(this,isIncorporation);

//		handleOldData();
		AmusementUI.addHideMenuItem(this, this.menuBiz);
		
		setAuditButtonStatus(this.getOprtState());
		
		this.actionCopy.setVisible(false);
    	this.actionAttachment.setVisible(false);
    	this.actionTraceUp.setVisible(false);
    	this.actionTraceDown.setVisible(false);
    	this.actionCopyFrom.setVisible(false);
    	
    	this.actionCreateFrom.setVisible(false);
    	this.actionAddLine.setVisible(false);
    	this.actionInsertLine.setVisible(false);
    	this.actionRemoveLine.setVisible(false);
    	
	}

	//��ȡ�ڼ�
	public void fetchPeriod(DataChangeEvent e,KDDatePicker pkbookedDate,KDBizPromptBox cbPeriod,String projectId, boolean isCost) throws Exception {
		
		if(!getOprtState().equals(OprtState.ADDNEW)&& !getOprtState().equals(OprtState.EDIT)){
			return ;
		}
		
    	if(bookedDate==null){
    		return ;
    	}
    	
		Date bookedDate  = (Date)pkbookedDate.getValue();
		PeriodInfo period = null;
		if(isIncorporation){    	
	        if (canBookedPeriod!=null && bookedDate.before(canBookedPeriod.getBeginDate())) {
	        	MsgBox.showConfirm2(this,"��ѡ���ڲ����ڹ��̵�ǰ�ڼ�֮ǰ");
	        	pkbookedDate.setValue(e.getOldValue());
	        	this.abort();
	        }
	        
	        if(isCost){
	        	period = FDCUtils.fetchCostPeriod(projectId,bookedDate);
	        }else{
	        	period = FDCUtils.fetchFinacialPeriod(projectId,bookedDate);
	        }
	        
	        if (period!=null && bookedDate.before(period.getBeginDate())) {
	        	MsgBox.showConfirm2(this,"��ѡ���ڲ����ڹ��̵�ǰ�ڼ�֮ǰ");
	        	
	        	if(((Date)e.getOldValue()).before(period.getBeginDate())){
	        		pkbookedDate.setValue(period.getBeginDate());
	        	}else{
	        		pkbookedDate.setValue(e.getOldValue());
	        	}
	        	return ;
	        	//this.abort();
	        }
    	}else{
    		period = PeriodUtils.getPeriodInfo(null ,bookedDate ,new ObjectUuidPK(company.getId().toString()));
    		if(period==null){
	        	MsgBox.showConfirm2(this,"û�ж�Ӧ���ڼ䡣�����ڻ�������ά���ڼ�");
	        	pkbookedDate.setValue(e.getOldValue());
	        	this.abort();
    		}
    	}
    	
    	cbPeriod.setValue(period);
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionAddNew_actionPerformed(e);

		handleCodingRule();
	}

	protected void enablePeriodComponent(Container aa,boolean isIncorporation){
		Component[] cc = aa.getComponents();

		Component c;
		for (int i = 0, n = cc.length; i < n; i++) {
			c = cc[i];

			if (c instanceof Container) {
				enablePeriodComponent((Container)c,isIncorporation);
			}

			if (c instanceof KDDatePicker) {
				//ҵ�����ڿͻ����������޸�
//				KDDatePicker obj = (KDDatePicker)c;
//				if("pkbookedDate".equals(obj.getName())){
//					obj.setEnabled(isIncorporation);
//				}
			}else if (c instanceof KDBizPromptBox) {
				KDBizPromptBox obj = (KDBizPromptBox)c;
				if("cbPeriod".equals(obj.getName())){
					obj.setEnabled(false);
				}
			}
		}
	}

	/**
	 * ����������
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 */
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {

		/*
		 * 2008-09-27��������ȡֵȡ���ˣ�Ӧ��ȡFDCBillInfo�й�����ID
		 */
		String currentOrgId = "";//FDCClientHelper.getCurrentOrgId();
		if(((TenBillBaseInfo)editData).getOrgUnit() != null){
			currentOrgId = ((TenBillBaseInfo)editData).getOrgUnit().getId().toString();
		}else{
			currentOrgId = SysContext.getSysContext().getCurrentSaleUnit().getId().toString();
		}
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
		.getRemoteInstance();
		/*
		 * 2008-09-27�����������״̬��ֱ�ӷ���
		 * Ȼ��ֱ��жϳɱ����ĺ͵�ǰ��֯�Ƿ���ڱ������
		 */
		if(!STATUS_ADDNEW.equals(this.oprtState)){
			return;
		}
		boolean isExist = true;
		if(currentOrgId.length()==0||!iCodingRuleManager.isExist(editData, currentOrgId))
		{
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if(!iCodingRuleManager.isExist(editData, currentOrgId)) {
				//EditUI�ṩ�˷�������û�е��ã���onload����ã��Ը��ǳ�����loadfields����ĵ��ã��õ���û�д���Ϻ�ѡ��
				isExist = false;
			}
		}
				
		if( isExist ){
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(editData, currentOrgId);
			if(isAddView) {
				getNumberByCodingRule(editData, currentOrgId);
			}
			else {
				String number = null;

				if (iCodingRuleManager.isUseIntermitNumber(editData,
						currentOrgId)) { // �˴���orgId�벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
					if (iCodingRuleManager.isUserSelect(editData, currentOrgId)) {
						// �����˶Ϻ�֧�ֹ���,ͬʱ�������û�ѡ��ϺŹ���
						// KDBizPromptBox pb = new KDBizPromptBox();
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								editData, currentOrgId, null, null);
						// pb.setSelector(intermilNOF7);
						// Ҫ�ж��Ƿ���ڶϺ�,���򵯳�,���򲻵�//////////////////////////////////////////
						Object object = null;
						if (iCodingRuleManager
								.isDHExist(editData, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					}
				}
				getNumberCtrl().setText(number);
			}

			setNumberTextEnabled();
		}
	}

	/**
	 * getNumberByCodingRuleֻ�ṩ�˻�ȡ����Ĺ��ܣ�û���ṩ���õ��ؼ��Ĺ��ܣ�ʵ�ִ˷��������ݱ�������"�Ƿ�������ʾ"�������ñ��뵽�ؼ�
	 */
	protected void prepareNumber(IObjectValue caller, String number) {
		super.prepareNumber(caller, number);

		getNumberCtrl().setText(number);

	}

	protected void setNumberTextEnabled() {

		if(getNumberCtrl() != null) {
			SaleOrgUnitInfo currentSaleUnit = SysContext.getSysContext().getCurrentSaleUnit();

			if(currentSaleUnit != null) {
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(getTenBillBaseInfo(), currentSaleUnit.getId().toString());

				getNumberCtrl().setEnabled(isAllowModify);
				getNumberCtrl().setEditable(isAllowModify);
			}
		}
	}

	/**
	 *
	 * ���������ر���ؼ����������ʵ�֣�
	 * @return
	 * @author:liupd
	 * ����ʱ�䣺2006-10-13 <p>
	 */
	abstract protected KDTextField getNumberCtrl();

	/**
	 * �������ش˷������������һЩ�����ظ������ֵ
	 */
	protected void setFieldsNull(AbstractObjectValue newData) {
		TenBillBaseInfo info = (TenBillBaseInfo) newData;
		info.setNumber(null);
		info.setName(null);
		info.setAuditor(null);
		info.setAuditTime(null);
		info.setCreateTime(null);
		info.setCreator(null);
		info.setLastUpdateTime(null);
		info.setLastUpdateUser(null);
		info.setState(FDCBillStateEnum.SAVED);
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
	}

	protected void updateButtonStatus() {

		super.updateButtonStatus();
		actionUnAudit.setEnabled(true);
		handleActionStatusByCurOrg();

	}

	protected void verifyInput(ActionEvent e) throws Exception {
		//���治�����κ�У��
		if(isSaveAction()) {
			verifyInputForSave();
			return;
		}

		verifyInputForSubmint();
		
		super.verifyInput(e);

	}

	/**
	 * �����ʱ����ҪУ�������,
	 *  һ���Ǳ���¼���,�����¼�������쳣���ֶ�,�繤����Ŀ,�����¼��,�򵥾��޷�����ʱ����ʾ
	 *
	 */
	protected void verifyInputForSave() throws Exception{
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}

	}
	
	protected void verifyInputForSubmint() throws Exception {
		FDCClientVerifyHelper.verifyRequire(this);
	}

	protected void initWorkButton() {

		super.initWorkButton();

		btnAudit.setIcon(FDCClientHelper.ICON_AUDIT);
		btnUnAudit.setIcon(FDCClientHelper.ICON_UNAUDIT);
		menuItemAudit.setIcon(FDCClientHelper.ICON_AUDIT);
		menuItemUnAudit.setIcon(FDCClientHelper.ICON_UNAUDIT);

		btnCalculator.setIcon(FDCClientHelper.ICON_CALCULATOR);
		menuItemCalculator.setIcon(FDCClientHelper.ICON_CALCULATOR);

		menuItemAudit.setAccelerator(KeyStroke.getKeyStroke("ctrl U"));
		actionAudit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl U"));
		menuItemAudit.setText(menuItemAudit.getText().replaceAll("\\(A\\)", "")+"(A)");
		menuItemAudit.setMnemonic('A');

		actionAudit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift U"));
		menuItemUnAudit.setText(menuItemUnAudit.getText().replaceAll("\\(U\\)", "")+"(U)");
		menuItemUnAudit.setAccelerator(KeyStroke.getKeyStroke("ctrl shift U"));
		menuItemUnAudit.setMnemonic('U');
	}

	public boolean isSaveAction() {
		return isSaveAction;
	}

	public void setSaveAction(boolean isSaveAction) {
		this.isSaveAction = isSaveAction;
	}

	public void actionCalculator_actionPerformed(ActionEvent e) throws Exception {
		//super.actionCalculator_actionPerformed(e);
/*		//���ִ���ʽ�����������Ŀ¼��ͻ,��ȥ��������ù��� sxhong
		if(getCalcValue() == null) return;

		boolean hasSet = setValueForFocusComp(getComponents(), getCalcValue());

		if(!hasSet && getDetailTable() != null) {
			int c = getDetailTable().getSelectManager().getActiveColumnIndex();
	    	int r = getDetailTable().getSelectManager().getActiveRowIndex();

	    	ICell cell = getDetailTable().getCell(r, c);
	    	if(cell!=null&&!cell.getStyleAttributes().isLocked()){
				cell.setValue(getCalcValue());
			}
		}
*/

    	CalculatorDialog calc = new CalculatorDialog(this);
        calc.showDialog(2, true);
	}

	/**
	 * Ϊ��ǰ����ؼ�����value
	 * @param components
	 * @param value
	 * @return
	 */
	private boolean setValueForFocusComp(Component[] components, BigDecimal value) {
		boolean found = false;
		if(found) return found;
		for (int i = 0; i < components.length; i++) {
			Component comp = components[i];
			if(comp instanceof KDLabelContainer) {
				JComponent boundEditor = ((KDLabelContainer)components[i]).getBoundEditor();
				if(boundEditor == null) continue;

				if(boundEditor instanceof KDFormattedTextField) {
					if(boundEditor.isFocusOwner()) {	//��֪��Ϊɶ���ǽ���
						((KDFormattedTextField)boundEditor).setValue(value);
						found = true;
					}
				}
			}
			else if(comp instanceof KDTabbedPane || comp instanceof KDPanel) {
				setValueForFocusComp(((JComponent)comp).getComponents(), value);
			}
		}

		return found;
	}

	/**
     *
     * ����������Ƿ��й�������
     * @author:liupd
     * ����ʱ�䣺2006-8-26 <p>
     */
    protected void checkRef(String id) throws Exception {

    }

    public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
    	// TODO Auto-generated method stub
    	super.actionCopy_actionPerformed(e);
    	handleCodingRule();
    }

    public void setOprtState(String oprtType) {
    	// TODO Auto-generated method stub
    	super.setOprtState(oprtType);

//    	FDCClientHelper.setCtrlEnabledByState(this.getComponents(), oprtType);

    	setAuditButtonStatus(oprtType);

    	refreshUITitle();
    }
    
    protected void setAuditButtonStatus(String oprtType){
    	if(STATUS_VIEW.equals(oprtType)) {
    		actionAudit.setVisible(true);
    		actionUnAudit.setVisible(true);
    		actionAudit.setEnabled(true);
    		actionUnAudit.setEnabled(true);
    		
    		TenBillBaseInfo bill = (TenBillBaseInfo)editData;
    		if(editData!=null){
    			if(FDCBillStateEnum.AUDITTED.equals(bill.getState())){
    	    		actionUnAudit.setVisible(true);
    	    		actionUnAudit.setEnabled(true);   
    	    		actionAudit.setVisible(false);
    	    		actionAudit.setEnabled(false);
    			}else{
    	    		actionUnAudit.setVisible(false);
    	    		actionUnAudit.setEnabled(false);   
    	    		actionAudit.setVisible(true);
    	    		actionAudit.setEnabled(true);
    			}
    		}
    	}else {
    		actionAudit.setVisible(false);
    		actionUnAudit.setVisible(false);
    		actionAudit.setEnabled(false);
    		actionUnAudit.setEnabled(false);
    	}
    }
    /**
     * �ò˵�����ΪUI��ҳǩ��,���������
     * @return Ĭ�Ϸ���true
     */
    protected boolean isUseMainMenuAsTitle(){
    	return false;
    }
}