/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.IChangeAuditBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * �����������ѯ �б����
 */
public class ChangeAuditFullListUI extends AbstractChangeAuditFullListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ChangeAuditFullListUI.class);
    
	private CustomerQueryPanel filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;
	
	private Map proLongNameMap=new HashMap();

    /**
     * output class constructor
     */
    public ChangeAuditFullListUI() throws Exception
    {
        super();
    }

	protected boolean isShowAttachmentAction() {
		
		return false;
	}
	
    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	super.actionAddNew_actionPerformed(e);
    	//ChangeAuditEditUI;
    	/*UIContext uiContext = new UIContext(this);
    	uiContext.put(UIContext.ID, null);
    	IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
		//IUIWindow dialog = uiFactory.create(ChangeAuditEditUI.class.getName(), uiContext);
		IUIWindow curDialog = uiFactory.create(ChangeAuditEditUI.class.getName(), getUIContext(), null, OprtState.ADDNEW);
		curDialog.show();*/
		
		//ChangeAuditEditUI ��onload ����
		//String cuId = editData.getCU()==null?SysContext.getSysContext().getCurrentCtrlUnit().getId().toString():editData.getCU().getId().toString();
    	
    	
    }

     /**
	 * output actionEdit_actionPerformed
	 */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	checkBeforeEdit();
        super.actionEdit_actionPerformed(e);
    }

	protected String getEditUIName() {
		return ChangeAuditEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ChangeAuditBillFactory.getRemoteInstance();
	}
	

	protected void execQuery() {
		super.execQuery();
		
		//costNouse
		FDCClientUtils.fmtFootNumber(this.tblMain, new String[]{"costNouse"});
		FDCClientHelper.initTable(tblMain);
	}
	

	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		return commonQueryDialog;
	}

	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new ChangeAuditFullFilterUI(this,
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

	public void onLoad() throws Exception {
		FDCClientUtils.checkCurrentCostCenterOrg(this);
		tblMain.checkParsed();		

		FDCClientHelper.addSqlMenu(this,this.menuEdit);
		this.proLongNameMap=FDCHelper.createTreeDataMap(CurProjectFactory.getRemoteInstance(),"name","\\");
		super.onLoad();
		
		//new update by renliang 2010-5-26
		this.actionAddNew.setEnabled(false);
		///this.actionEdit.setEnabled(false);
		this.actionEdit.setEnabled(true);
		
		this.actionRemove.setEnabled(false);
		this.actionLocate.setEnabled(false);
		this.actionCreateTo.setEnabled(false);
		this.actionTraceDown.setEnabled(false);
		this.actionTraceUp.setEnabled(false);
		this.actionCopyTo.setEnabled(false);
		this.actionAuditResult.setEnabled(false);
		this.actionMultiapprove.setEnabled(false);
		this.actionNextPerson.setEnabled(false);
		
		//new update by renliang 2010-5-26
		this.actionAddNew.setVisible(false);
		//this.actionEdit.setVisible(false);
		this.actionEdit.setVisible(true);
		
		this.actionRemove.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		
		//new add by renliang 2010-5-26
		this.actionAudit.setEnabled(true);
		this.actionAudit.setVisible(true);
		
		FDCClientHelper.initTable(tblMain);
		
		this.tblMain.getColumn("jobType.name").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("specialtyType.name").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("changeSubject").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("isNoUse").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("invalidCostReason.name").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("costNouse").getStyleAttributes().setHided(true);
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
		return UIFactoryName.NEWTAB;
	}
	
    protected boolean isIgnoreCUFilter() {
        return true;
    }
    
    protected OrgType getMainBizOrgType() {
		return OrgType.CostCenter;
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
     * �������������� by renlaing 2010-5-26
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    
    	//��鵥��״̬
		checkBillState(new String[]{getStateForAudit()}, "selectRightRowForAudit");
    	
    	// ��ȡ�û�ѡ��Ŀ�
		List idList = ContractClientUtils.getSelectedIdValues(
				getMainTable(), getKeyFieldName());
		
		if(idList!=null && idList.size()>0){
			IChangeAuditBill changeAuditBill = (IChangeAuditBill)getBizInterface();
			changeAuditBill.audit(idList);
			//��ʾ��ʾ��ˢ��ҳ��
			showOprtOKMsgAndRefresh();
		}else{
			MsgBox.showWarning(this,"����ѡ���У�");
			SysUtil.abort();
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
	 * ��鵥��״̬
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
	 
	 /**
	     * �޸�ǰ״̬�ļ��
	     * @throws renliang
	     * @date 2010-5-26
	     */
    private void checkBeforeEdit() throws Exception{
    	this.checkSelected();
    	
		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),getKeyFieldName());
		if (idList.size() > 1) {
			MsgBox.showWarning(this, "��ѡ���˶��м�¼��������ѡ��");
			abort();
			return;
		}

		String id = idList.get(0).toString();
		ChangeAuditBillInfo info = ChangeAuditBillFactory.getRemoteInstance().getChangeAuditBillInfo(new ObjectUuidPK(BOSUuid.read(id)));

		if (!info.getState().equals(FDCBillStateEnum.SAVED) && !info.getState().equals(FDCBillStateEnum.SUBMITTED)) {
			MsgBox.showWarning(this, "����ǰѡ��ĵ��ݵ�״̬���ʺ��޸Ĳ�����");
			abort();
		}
	}
}