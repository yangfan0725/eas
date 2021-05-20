/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.QueryInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.commonquery.client.DefaultPromptBoxFactory;
import com.kingdee.eas.base.commonquery.client.IPromptBoxFactory;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.ChangeAuditUtil;
import com.kingdee.eas.fdc.contract.ChangeBillStateEnum;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IContractChangeBill;
import com.kingdee.eas.fdc.contract.programming.client.ProgrammingContractUtil;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.DatataskMode;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ContractChangeFullListUI extends AbstractContractChangeFullListUI {
	public static final String resourcePath = "com.kingdee.eas.fdc.contract.client.ContractFullResource";

	private CustomerQueryPanel filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;
	
	private Map proLongNameMap=new HashMap();
	/**
	 * output class constructor
	 */
	public ContractChangeFullListUI() throws Exception {
		super();
	}
	protected boolean isShowAttachmentAction() {
		
		return false;
	}
	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected String getEditUIName() {
		return ContractChangeBillEditUI.class.getName();
	}

	protected void execQuery() {
		super.execQuery();
		
		FDCClientUtils.fmtFootNumber(this.tblMain, new String[]{"amount", "deductAmount","balanceAmount","changeAudit.costNouse"});
		FDCClientHelper.initTable(tblMain);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ContractChangeBillFactory.getRemoteInstance();
	}

	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());

		IPromptBoxFactory factory = new DefaultPromptBoxFactory() {
			public KDPromptBox create(String queryName,
					EntityObjectInfo entity, String propertyName) {
				return super.create(queryName, entity, propertyName);
			}

			public KDPromptBox create(String queryName, QueryInfo mainQuery,
					String queryFieldName) {
				final KDBizPromptBox f7 = (KDBizPromptBox) super.create(
						queryName, mainQuery, queryFieldName);
				if (queryName.equalsIgnoreCase("com.kingdee.eas.fdc.contract.app.F7ContractBillQueryForContractChangeBill")) {
					f7.addSelectorListener(new SelectorListener() {
						public void willShow(SelectorEvent e) {
							f7.getQueryAgent().resetRuntimeEntityView();
							EntityViewInfo view = new EntityViewInfo();
							FilterInfo filter = new FilterInfo();
							FilterItemCollection filterItems = filter.getFilterItems();

							//ȡ��ǰ��֯�ɱ����ļ������¼��ɱ�����
							BOSUuid id = SysContext.getSysContext().getCurrentOrgUnit().getId();
							Set idSet = null;
							try {
								idSet = FDCClientUtils.genOrgUnitIdSet(id);
							} catch (Exception exe) {
								exe.printStackTrace();
							}
							if (idSet != null && idSet.size() > 0) {
								filterItems.add(new FilterItemInfo(
										"orgUnit.id", idSet,
										CompareType.INCLUDE));
							}
							filterItems.add(new FilterItemInfo("isAmtWithoutCost", Boolean.valueOf(true), CompareType.NOTEQUALS));
							view.setFilter(filter);
							f7.setEntityViewInfo(view);
						}
					});

				}
				return f7;
			}
		};
		commonQueryDialog.setPromptBoxFactory(factory);
		return commonQueryDialog;
	}

	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new ContractChangeFullFilterUI(this,
						this.actionOnLoad);
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}

	/**
	 * 
	 * ��ʼ��Ĭ�Ϲ�������
	 * 
	 * @return ��������ˣ������˳�ʼ�����������뷵��true;Ĭ�Ϸ���false;
	 */
	protected boolean initDefaultFilter() {
		return true;
	}

	protected String getKeyFieldName() {
		return super.getKeyFieldName();
	}

	public void onLoad() throws Exception {
		FDCClientUtils.checkCurrentCostCenterOrg(this);
		tblMain.checkParsed();

		
		FDCClientHelper.addSqlMenu(this,this.menuEdit);
		this.proLongNameMap=FDCHelper.createTreeDataMap(CurProjectFactory.getRemoteInstance(),"name","\\");
		super.onLoad();
		this.actionAddNew.setEnabled(false);
		//this.actionEdit.setEnabled(false);
		this.actionRemove.setEnabled(true);
		this.actionLocate.setEnabled(false);
		this.actionCreateTo.setEnabled(false);
		this.actionTraceDown.setEnabled(false);
		this.actionTraceUp.setEnabled(false);
		this.actionCopyTo.setEnabled(false);
		this.actionAuditResult.setEnabled(false);
		this.actionMultiapprove.setEnabled(false);
		this.actionNextPerson.setEnabled(false);
		
		this.actionAddNew.setVisible(false);
		//this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);
		
		//new add by renliang 2010-5-26
		this.actionAudit.setEnabled(true);
		this.actionDispatch.setEnabled(true);
		this.actionVias.setEnabled(true);
		//this.actionDisPatch.setEnabled(true);
		//this.actionVisa.setEnabled(true);
		this.actionSettlement.setEnabled(true);
		this.actionAudit.setVisible(true);
		///this.actionDisPatch.setVisible(true);
		///this.actionVisa.setVisible(true);
		this.actionDispatch.setVisible(true);
		this.actionVias.setVisible(true);
		this.actionSettlement.setVisible(true);
		this.actionEdit.setEnabled(true);
		this.actionEdit.setVisible(true);
		
		this.menuEdit.setEnabled(true);
		this.menuEdit.setVisible(true);
		
		this.menuBiz.setEnabled(true);
		this.menuBiz.setVisible(true);
		
		FDCClientHelper.initTable(tblMain);
		actionImportData.setVisible(false);
		
		this.actionVias.setVisible(false);
		this.actionSettlement.setVisible(false);
	}
	public void onGetRowSet(IRowSet rowSet) {
		try {
			rowSet.beforeFirst();
			while (rowSet.next()) {
				String displayName="";
				String id=rowSet.getString("curProject.id");
				String orgName=rowSet.getString("orgUnit.name");
				if(orgName!=null){
					displayName+=orgName;
				}
				String proName = (String) this.proLongNameMap.get(id);
				if(proName!=null){
					displayName+="\\"+proName;
				}
				rowSet.updateString("curProject.name",displayName);
			}
			rowSet.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		super.onGetRowSet(rowSet);
	}
	protected String getEditUIModal() {
		// TODO �Զ����ɷ������
		return UIFactoryName.NEWTAB;
	}
    protected boolean isIgnoreCUFilter() {
        return true;
    }
    
    protected OrgType getMainBizOrgType() {
		return OrgType.CostCenter;
	}
    
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	super.actionRemove_actionPerformed(e);
    }
    
	/**
     * �����Ƿ���ʾ�ϼ���
     * 2005-03-09 haiti_yang
     */
    protected boolean isFootVisible()
    {
        return true;
    }
    
    /**
     * ������������ by renliang 2010-5-26
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		// ��鵥��״̬
		checkBillState(new String[] { getStateForAudit() }, "selectRightRowForAudit");

		// ��ȡ�û�ѡ��Ŀ�
		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(), getKeyFieldName());

		IContractChangeBill contractChangeBill = (IContractChangeBill) getBizInterface();
		contractChangeBill.audit(idList);
		ProgrammingContractUtil.amountChange(idList);
		// ��ʾ��ʾ��ˢ��ҳ��
		showOprtOKMsgAndRefresh();
    }
    
    /**
     * �����·����� by renliang 2010-5-26
     */
    public void actionDispatch_actionPerformed(ActionEvent e) throws Exception {
    	checkBeforeDisPatch();
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(
				getMainTable(), getKeyFieldName());
		IContractChangeBill bill = (IContractChangeBill) getBizInterface();		
    	if(selectedIdValues!=null){
    		bill.disPatch(FDCHelper.CollectionToArrayPK(selectedIdValues));
    		showOprtOKMsgAndRefresh();
    	}
    }
    
    /**
     * ����ǩ֤���� by renliang 2010-5-26
     */
    public void actionVias_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
        
    	//��鵥��״̬
		checkVisaStata(new String[]{getStateForAudit()}, "selectRightRowForAudit");
    	
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(
				getMainTable(), getKeyFieldName());
		
		IContractChangeBill bill = (IContractChangeBill) getBizInterface();
		Set idSet = ContractClientUtils.listToSet(selectedIdValues);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("state");
		ContractChangeBillCollection coll = bill.getContractChangeBillCollection(view);
		
		//����ǩ֤
		List idList = new ArrayList();
		Iterator iter = coll.iterator(); 
		while(iter.hasNext()){
			ContractChangeBillInfo element = (ContractChangeBillInfo) iter.next();
			if(element.getState()!=null){
				String state = element.getState().toString();
				String announce = String.valueOf(FDCBillStateEnum.ANNOUNCE);
				if(state.equals(announce)){
					idList.add(element.getId().toString());
				}
			}
		}
		
		if(idList.size()==0){
			MsgBox.showWarning(this,ChangeAuditUtil.getRes("noVisa"));
			SysUtil.abort();
		}
		
		UIContext uiContext = new UIContext(this); 
		uiContext.put(UIContext.IDLIST,idList);
		IUIWindow uiWin = UIFactory.createUIFactory(UIFactoryName.MODEL).	create(
				ContractChangeVisaBatchUI.class.getName(),	uiContext, null , OprtState.EDIT);       
		uiWin.show();
		
		refreshList();
		///super.actionVisa_actionPerformed(e);
    	
    }
   

    /**
     * ������������Ŀ���
     * @throws Exception
     */
	private void checkBeforeVisa() throws Exception{
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(
				getMainTable(), getKeyFieldName());
		if(selectedIdValues.size()>1){
			MsgBox.showWarning(this,ChangeAuditUtil.getRes("mustSingle"));
			SysUtil.abort();
		}
	}
	
    /**
     * ����������㷽�� by renliang 2010-5-26
     */
    public void actionSettlement_actionPerformed(ActionEvent e)
    		throws Exception {
    	
    	//Ϊ�����������ˢ�¹���
    	checkSelected();
   
    	//������������Ŀ���
    	checkBeforeVisa();
		
		//��鵥��״̬
		checkVisaStata(new String[]{getStateForAudit()}, "selectRightRowForAudit");
   	
		//����Ϊ��ǩ֤
		int activeRowIndex = getMainTable().getSelectManager().getActiveRowIndex();
		ICell cell = getMainTable().getCell(activeRowIndex, "state");
		
		String cellValue = cell.getValue().toString();
		String state = String.valueOf(FDCBillStateEnum.VISA);
		
		if(cell.getValue()!=null&&!(cellValue.equals(state))){
			MsgBox.showWarning(this,"�������֮ǰ������ǩ֤");
			SysUtil.abort();
		}

		UIContext uiContext = new UIContext(this); 
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		IUIWindow uiWin = UIFactory.createUIFactory(UIFactoryName.MODEL).	create(
				ContractChangeSettUI.class.getName(),	uiContext, null , OprtState.EDIT);       
		uiWin.show();
		ContractChangeSettUI ui=(ContractChangeSettUI)uiWin.getUIObject();
		if(ui.isOk()){
			refreshList();
			super.actionSettlement_actionPerformed(e);
		}
    }
    
    
    /**
	 * 
	 * ��������ʾ�����ɹ�
	 * @author:renliang
	 * @date 2010-5-19
	 */
	protected void showOprtOKMsgAndRefresh() throws Exception {
		FDCClientUtils.showOprtOK(this);
		refreshList();
	}
	
	/**
	 * ���������е���״̬�ļ��
	 * @param states
	 * @param res
	 * @throws Exception
	 */
	protected void checkBillState(String[] states, String res) throws Exception {
	    	
			List idList = ContractClientUtils.getSelectedIdValues(getMainTable(), getKeyFieldName());
	
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
				
				//��鵥���Ƿ��ڹ�������
				FDCClientUtils.checkBillInWorkflow(this, element.getId().toString());

				if (!stateSet.contains(element.getString(getBillStatePropertyName()))) {
					MsgBox.showWarning(this, ContractClientUtils.getRes(res));
					abort();
				}
	
			}
	}
	
	/**
	 * ��鵥�ݵ�״̬Ϊǩ֤������ʹ��
	 * @param states
	 * @param res
	 * @throws Exception
	 */
	protected void checkVisaStata(String[] states, String res) throws Exception {
    	
		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(), getKeyFieldName());

		if(idList==null){
			MsgBox.showWarning(this, "��ѡ����");
			abort();
			return ;
		}
		
		List list = new ArrayList();
		
		Set idSet = ContractClientUtils.listToSet(idList);
		//Set stateSet = FDCHelper.getSetByArray(states);

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
			
			 //��鵥���Ƿ��ڹ�������
			 if(FDCUtils.isRunningWorkflow(element.getId().toString())){
				
				list.add(element.getId().toString());
			 }

		}
		
		if(list.size()>0){
			 MsgBox.showWarning("���м�¼�ڹ����������У�������ѡ��");
			 SysUtil.abort();
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
	 
	 protected String getStateForAudit() {
			return FDCBillStateEnum.SUBMITTED_VALUE;
	 }
	 
	 private void checkBeforeDisPatch() throws Exception{
			List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),
					getKeyFieldName());

			if(idList==null){
				MsgBox.showWarning(this, "��ѡ����");
				abort();
				return ;
			}
			
			Set idSet = ContractClientUtils.listToSet(idList);

			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("id", idSet, CompareType.INCLUDE));
			view.setFilter(filter);
			view.getSelector().add("id");
			view.getSelector().add("state");
			view.getSelector().add("changeAudit");
			view.getSelector().add("changeAudit.changeState");
			IContractChangeBill bill = (IContractChangeBill) getBizInterface();	
			ContractChangeBillCollection coll = bill.getContractChangeBillCollection(view);

			for (Iterator iter = coll.iterator(); iter.hasNext();) {
				ContractChangeBillInfo element = (ContractChangeBillInfo) iter.next();
				// ��鵥���Ƿ��ڹ�������
				FDCClientUtils
						.checkBillInWorkflow(this, element.getId().toString());
				if(element.getChangeAudit()!=null){
					if((element.getChangeAudit().getChangeState()!=null)
							&&(!((element.getChangeAudit().getChangeState().equals(ChangeBillStateEnum.Audit))
									||(element.getChangeAudit().getChangeState().equals(ChangeBillStateEnum.Announce))
									||(element.getChangeAudit().getChangeState().equals(ChangeBillStateEnum.Visa))))){
						MsgBox.showWarning(this, ChangeAuditUtil.getRes("reSelect"));
						SysUtil.abort();
					} else if (!element.getState().equals(FDCBillStateEnum.AUDITTED)) {
						MsgBox.showWarning(this, ChangeAuditUtil.getRes("reSelect"));
						abort();
					} else
						continue;
				}
				else{
					if (!element.getState().equals(FDCBillStateEnum.AUDITTED)) {
						MsgBox.showWarning(this, ChangeAuditUtil.getRes("reSelect"));
						abort();
					}
				}
			}
		}
	
	 /**
	  * �����޸Ĺ���
	  */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeEdit();
		super.actionEdit_actionPerformed(e);
		
	} 
    
    /**
     * �޸�ǰ״̬�ļ��
     * @throws renliang
     * @date 2010-5-26
     */
    private void checkBeforeEdit() throws Exception{
		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),
				getKeyFieldName());

		if(idList==null || idList.size()==0){
			MsgBox.showWarning(this, "����ѡ���У�");
			abort();
			return ;
		}
		
		if(idList.size()>1){
			MsgBox.showWarning(this, "��ѡ���˶��м�¼��������ѡ��");
			abort();
			return ;
		}
	
	   String id = idList.get(0).toString();
	   ContractChangeBillInfo info =ContractChangeBillFactory.getRemoteInstance().getContractChangeBillInfo(new ObjectUuidPK(BOSUuid.read(id)));
	   
	   if(!info.getState().equals(FDCBillStateEnum.SAVED) && !info.getState().equals(FDCBillStateEnum.SUBMITTED)){
		   MsgBox.showWarning(this, "����ǰѡ��ĵ��ݵ�״̬���ʺ��޸Ĳ�����");
			abort();
	   }		
	}        
 	
	public void actionImportData_actionPerformed(ActionEvent e) throws Exception {

		DatataskCaller task = new DatataskCaller();
        task.setParentComponent(this);
        ArrayList list = this.getImportParam();
        
        if (list != null)
        {
            task.invoke(list, DatataskMode.ImpMode,true,true);
        }
        actionRefresh_actionPerformed(e);
	}
	
	protected ArrayList getImportParam(){	
		Hashtable hs = new Hashtable();
		
		DatataskParameter param = new DatataskParameter();
		param.setContextParam(hs);//
        param.solutionName = getSolutionName();      
        param.alias = getDatataskAlias();
        
        ArrayList paramList = new ArrayList();
        paramList.add(param);
        return paramList;
    }
	
	protected String getSolutionName(){
    	return "eas.fdc.contract.ContractChangeFull";
    }
    
    protected String getDatataskAlias(){
    	return "���ָ�";
    } 
    public boolean isAutoIgnoreZero()
    {
    	return false;
    }
}