/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.ITextIconDisplayStyle;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.monitor.client.BasicWorkFlowMonitorPanel;
import com.kingdee.bos.workflow.monitor.client.ProcessRunningListUI;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class TenBillBaseListUI extends AbstractTenBillBaseListUI
{
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
	
	/*----------------------------------------------------����ΪFDCBill���ƹ����ģ����޸�------------------------------------------------*/
    private static final Logger logger = CoreUIObject.getLogger(TenBillBaseListUI.class);
    
    /**
     * output class constructor
     */
    public TenBillBaseListUI() throws Exception
    {
        super();
    }

	public static final String COL_PERIOD= "period";
	public static final String COL_PERIODNUMBER= "period.number";
	public static final String COL_DATE = "bookedDate";
	
	//���óɱ��½�
	protected boolean isIncorporation = false;
	//���ò���ɱ�һ�廯
	protected boolean isFinacial = false;
	
    /**
     * output actionImportData_actionPerformed
     */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception
    {
    	if(this.getClass().getName().equalsIgnoreCase("com.kingdee.eas.fdc.contract.client.contractBillListUI")) {
    		fdcDataImport();
    	}else{
    		super.actionImportData_actionPerformed(e);
    	}
    }
    
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionAttachment_actionPerformed(e);
    	boolean isEdit=false;
        AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
        String boID = this.getSelectedKeyValue();
        checkSelected();
        if (boID == null)
        {
            return;
        }
        if(getBillStatePropertyName()!=null){
        	int rowIdx=getMainTable().getSelectManager().getActiveRowIndex();
        	ICell cell = getMainTable().getCell(rowIdx, getBillStatePropertyName());
        	Object obj=cell.getValue();
        	if(obj!=null&&
        			(obj.toString().equals(FDCBillStateEnum.SAVED.toString())
        			||obj.toString().equals(FDCBillStateEnum.SUBMITTED.toString())
        			||obj.toString().equals(FDCBillStateEnum.AUDITTING.toString())
        			||obj.toString().equals(BillStatusEnum.SAVE.toString())
        			||obj.toString().equals(BillStatusEnum.SUBMIT.toString())
        			||obj.toString().equals(BillStatusEnum.AUDITING.toString()))){
        		isEdit=true;
        	}else{
        		isEdit=false;
        	}
			
        }
        acm.showAttachmentListUIByBoID(boID, this,isEdit); // boID �� �븽�������� ҵ������ ID
    }
    
    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();

		checkBillState(new String[]{FDCBillStateEnum.AUDITTED_VALUE}, "selectRightRowForCancel");
		
		checkBeforeCancel();
		
		int confirm = MsgBox.showConfirm2(this, "ȷ����ֹ? �ò������ɳ�����");
		if(confirm == MsgBox.OK) {
			cancel(ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName()));
			
			showOprtOKMsgAndRefresh();
		}
    }

    /**
     * ����
     * @param ids
     * @throws Exception
     */
    protected void cancel(List ids) throws Exception{}
    
    /**
     * ��Ч
     * @param ids
     * @throws Exception
     */
    protected void cancelCancel(List ids) throws Exception{}

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();

		checkBillState(new String[]{FDCBillStateEnum.CANCEL_VALUE}, "selectRightRowForCancelCancel");

		cancelCancel(ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName()));
	
		showOprtOKMsgAndRefresh();
    }
    
    /**
     * ����ǰ��ҵ����
     * @throws Exception
     */
    protected void checkBeforeCancel() throws Exception{
    	
    }
    
    /**
	 * 
	 * ��������鵥��״̬
	 * 
	 * @param states
	 *            ״̬
	 * @param res
	 *            ��ʾ��Ϣ��Դ����
	 * @throws BOSException
	 * @author:liupd ����ʱ�䣺2006-7-27
	 *               <p>
	 */
	protected void checkBillState(String[] states, String res) throws Exception {
		List idList = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());

		if(idList==null){
			MsgBox.showWarning(this, "��ѡ����");
			abort();
			return ;
		}
		
		Set idSet = ContractClientUtils.listToSet(idList);
		Set stateSet = FDCHelper.getSetByArray(states);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add(getBillStatePropertyName());
		CoreBaseCollection coll = getBizInterface().getCollection(view);

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBillBaseInfo element = (CoreBillBaseInfo) iter.next();
			
//			��鵥���Ƿ��ڹ�������
			FDCClientUtils.checkBillInWorkflow(this, element.getId().toString());
			
//			if (!element.getString(getBillStatePropertyName()).equals(states)) {
			if (!stateSet.contains(element.getString(getBillStatePropertyName()))) {
				MsgBox.showWarning(this, ContractClientUtils.getRes(res));
				abort();
			}

		}
	}
	
	  /**
     * 
     * ����������״̬�������ƣ������ṩȱʡʵ��
     * @return
     * @author:liupd
     * ����ʱ�䣺2006-8-26 <p>
     */
    protected String getBillStatePropertyName() {
    	return "state";
    }
    
    /**
	 * 
	 * ��������ȡ��ǰ���ݵ�Table���������ʵ�֣�
	 * @return
	 * @author:liupd
	 * ����ʱ�䣺2006-8-2 <p>
	 */
	protected KDTable getBillListTable() {
		return getMainTable();
	}
	
    protected SelectorItemCollection getStateSelectors() {
    	SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("state");
		
		return selectors;
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }

    /**
     * output actionCopyTo_actionPerformed
     */
    public void actionCopyTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyTo_actionPerformed(e);
    }

    /**
     * output actionTraceUp_actionPerformed
     */
    public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceUp_actionPerformed(e);
    }

    /**
     * output actionTraceDown_actionPerformed
     */
    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceDown_actionPerformed(e);
    }

    /**
     * output actionVoucher_actionPerformed
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionVoucher_actionPerformed(e);
    }

    /**
     * output actionDelVoucher_actionPerformed
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelVoucher_actionPerformed(e);
    }

    /**
     * output actionAuditResult_actionPerformed
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAuditResult_actionPerformed(e);
    }

    /**
     * output actionViewDoProccess_actionPerformed
     */
    public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception
    {
       	checkSelected();
    	String fieldName = getQueryFieldNameBindingWf();
        String id = (String)getSelectedFDCFieldValues(fieldName).get(0);
        IEnactmentService service = EnactmentServiceFactory.createRemoteEnactService();
        ProcessInstInfo processInstInfo = null;
        ProcessInstInfo[] procInsts = service.getProcessInstanceByHoldedObjectId(id);
        for (int i = 0, n = procInsts.length; i < n; i++) {
            if (procInsts[i].getState().startsWith("open")) {
            	processInstInfo = procInsts[i];
            }
        }
        if (processInstInfo == null) {
        	//���û������ʱ���̣��ж��Ƿ��п���չ�ֵ�����ͼ��չ��
        	procInsts = service.getAllProcessInstancesByBizobjId(id);
        	if(procInsts== null || procInsts.length <=0)
        		MsgBox.showInfo(this ,EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_WFHasNotInstance"));
        	else if(procInsts.length ==1){
	        	showWorkflowDiagram(procInsts[0]);
        	}else{
        		UIContext uiContext = new UIContext(this);
                 uiContext.put("procInsts",procInsts);
                 String className = ProcessRunningListUI.class.getName();
                 IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(className,uiContext);
                 uiWindow.show();
        	}
		} else {
			showWorkflowDiagram(processInstInfo);
        }
    }
    
	/**
	 * ��������ѡ����������ģʽ��
	 * @param fieldName
	 * @return
	 */
	public final ArrayList getSelectedFDCFieldValues(String fieldName) {
        ArrayList list = new ArrayList();
        int[] selectRows = KDTableUtil.getSelectedRows( getBillListTable());
        for (int i = 0; i < selectRows.length; i++) {
        	ICell cell =  getBillListTable().getRow(selectRows[i]).getCell(fieldName);
        	if (cell == null) {
        		MsgBox.showError(EASResource.getString(FrameWorkClientUtils.strResource + "Error_KeyField_Fail"));
        		SysUtil.abort();
        	}
        	if (cell.getValue()!=null)
        	{
        		String id = cell.getValue().toString();
            	if (!list.contains(id))
            		list.add(id);
        	}

        }
        return list;
    }
    
    private void showWorkflowDiagram(ProcessInstInfo processInstInfo)
	throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put("id", processInstInfo.getProcInstId());
		uiContext.put("processInstInfo", processInstInfo);
		String className = BasicWorkFlowMonitorPanel.class.getName();
		IUIWindow uiWindow = UIFactory.createUIFactory(getEditUIModal())
				.create(className, uiContext);
		uiWindow.show();
	}

    /**
     * output actionMultiapprove_actionPerformed
     */
    public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMultiapprove_actionPerformed(e);
    }

    /**
     * output actionNextPerson_actionPerformed
     */
    public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNextPerson_actionPerformed(e);
    }

    /**
     * output actionWorkFlowG_actionPerformed
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkFlowG_actionPerformed(e);
    }

	protected String getEditUIName() {
		/* TODO �Զ����ɷ������ */
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		/* TODO �Զ����ɷ������ */
		return null;
	}
	
	protected  void fetchInitData() throws Exception{		

		try {
			//���óɱ�����һ�廯
//			HashMap paramItem = FDCUtils.getDefaultFDCParam(null,SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
			//������֯������,�õ�ǰ��֯ȥ�ж�,��ǰ��֯Ϊ�ɱ�����,�ϼ�ʵ�������֯�˲�������ʱ,��ȡ�Ĳ����Ǵ����
			HashMap paramItem = FDCUtils.getDefaultFDCParam(null,SysContext.getSysContext().getCurrentFIUnit().getId().toString());
			if(paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION)!=null){
				isIncorporation = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION).toString()).booleanValue();
			}
			isFinacial = FDCUtils.IsFinacial(null, SysContext.getSysContext().getCurrentFIUnit().getId().toString());
		}catch (Exception e) {
			handUIException(e);
		}
	}
	
	public void onLoad() throws Exception {
		
		//��ȡ��ʼ����������
		fetchInitData();
		
		super.onLoad();
		if(isUseMainMenuAsTitle()){
			FDCClientHelper.setUIMainMenuAsTitle(this);
		}
		//��ʼ�����
		initTable();
		
		getMainTable().setColumnMoveable(true);
		
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		
		this.actionCreateTo.setVisible(false);
    	this.actionCopyTo.setVisible(false);
    	this.actionTraceUp.setVisible(false);
    	this.actionTraceDown.setVisible(false);    	
	}
	
	
	/**
	 * 
	 * ��������ʼ�����
	 * @author:liupd
	 * ����ʱ�䣺2006-8-3 <p>
	 */
	protected void initTable() {
		
		getMainTable().setColumnMoveable(true);
		
		//freezeMainTableColumn();
		if(getBillListTable()!=null){
			//freezeBillTableColumn();
			getBillListTable().getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
			if(getBillListTable().getColumn("createTime")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "createTime");
			}
			if(getBillListTable().getColumn("auditTime")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "auditTime");
			}
			if(getBillListTable().getColumn("createDate")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "createDate");
			}
			if(getBillListTable().getColumn("createTime")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "createTime");
			}
			if(getBillListTable().getColumn("signDate")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "signDate");
			}
			if(getBillListTable().getColumn("entrys.deductDate")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "entrys.deductDate");
			}
//			if(getBillListTable().getColumn("entrys.deductDate")!=null){
//				FDCHelper.formatTableDate(getBillListTable(), "entrys.deductDate");
//			}
			if(getBillListTable().getColumn("bookedDate")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "bookedDate");
			}
		}
		//getMainTable().getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		if(getMainTable().getColumn("amount")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "amount");
		}
		if(getMainTable().getColumn("originalAmount")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "originalAmount");
		}
//		FDCHelper.formatTableDateTime(getMainTable(), "signDate");
		
		//
		if(!isIncorporation){
//			if(getMainTable().getColumn(COL_DATE)!=null){
//				getMainTable().getColumn(COL_DATE).getStyleAttributes().setHided(true);
//			}
			if(getMainTable().getColumn(COL_PERIOD)!=null){
				getMainTable().getColumn(COL_PERIOD).getStyleAttributes().setHided(true);
			}
			if(getMainTable().getColumn(COL_PERIODNUMBER)!=null){
				getMainTable().getColumn(COL_PERIODNUMBER).getStyleAttributes().setHided(true);
			}			
//			if(getBillListTable().getColumn(COL_DATE)!=null){
//				getBillListTable().getColumn(COL_DATE).getStyleAttributes().setHided(true);
//			}
			if(getBillListTable().getColumn(COL_PERIOD)!=null){
				getBillListTable().getColumn(COL_PERIOD).getStyleAttributes().setHided(true);
			}
			if(getBillListTable().getColumn(COL_PERIODNUMBER)!=null){
				getBillListTable().getColumn(COL_PERIODNUMBER).getStyleAttributes().setHided(true);
			}
		}
		
		//Table�������֮������ԭ�ҵľ���
		getMainTable().getDataRequestManager().addDataFillListener(
				new KDTDataFillListener() {
					public void afterDataFill(KDTDataRequestEvent e) {
						//FDCClientHelper.tblMainAfterDataFill(e,getMainTable(),TenBillBaseListUI.this);//TODO
					}
				}
		);	
	}
	


	/**
	 * 
	 * ��������ʾ�����ɹ�
	 * @author:liupd
	 * ����ʱ�䣺2006-8-1 <p>
	 */
	protected void showOprtOKMsgAndRefresh() throws Exception {
		FDCClientUtils.showOprtOK(this);
		refreshList();
	}
	
	protected void initWorkButton() {
		// TODO Auto-generated method stub
		super.initWorkButton();
		btnCancel.setTextIconDisStyle(ITextIconDisplayStyle.BOTH_TEXT_ICON);
		btnCancelCancel.setTextIconDisStyle(ITextIconDisplayStyle.BOTH_TEXT_ICON);
	}
	
	protected void fdcDataImport() throws Exception{
/*        FDCDatataskCaller task = new FDCDatataskCaller();
        task.setParentComponent(this);
        final ArrayList dataImportParam = getFDCDataImportParam();
        if (dataImportParam != null)
        {
            task.invoke(dataImportParam, DatataskMode.ImpMode);
        }*/
	}
    protected ArrayList getFDCDataImportParam()
    {
      DatataskParameter param = new DatataskParameter();
      //        String solutionName = EASResource.getString(AccountClientUtils.ACCOUNT_RESOURCE, "accountTypeImportSolutionPath"); //�����ܷ���.���뷽��-��������.ȫ������.��ƿ�Ŀ�����Ŀ¼
//      String solutionName = "�����ܷ���.���뷽��-��������.ȫ������.��ƿ�Ŀ���";
      String solutionName = "eas.fdc.bill.contractBill";
      param.solutionName = solutionName;
//      param.alias = EASResource.getString(AccountClientUtils.ACCOUNT_RESOURCE, "accountType"); //��ƿ�Ŀ���
      param.alias="��ͬ";
      Hashtable dataTaskCtx = new Hashtable();
      
      param.setContextParam(dataTaskCtx);

      ArrayList paramList = new ArrayList();
      paramList.add(param);

      return paramList;
  
    }
    
    /** ���ѡ���е�ID,���û��ѡ���и�����ʾ,���жϲ���,ע��Table���Ƿ���id�� */
    protected String getSelectedId(){
    	IRow row = getSelectedRow();
		String id = (String) row.getCell("id").getValue();
		return id;
    }
    
    /** ���ѡ����,���û��ѡ���и�����ʾ,���жϲ���,ע��Table���Ƿ���id�� */
    protected IRow getSelectedRow(){
    	this.checkSelected();
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		return row;
    }
    
    /**
     * �ò˵�����ΪUI��ҳǩ��,���������
     * @return Ĭ�Ϸ���true
     */
    protected boolean isUseMainMenuAsTitle(){
    	return false;
    }
}