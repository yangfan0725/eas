/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.invite.InviteBaseFileFactory;
import com.kingdee.eas.fdc.invite.InviteBaseFileInfo;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class InviteBaseFileListUI extends AbstractInviteBaseFileListUI
{
    private static final Logger logger = CoreUIObject.getLogger(InviteBaseFileListUI.class);
    public InviteBaseFileListUI() throws Exception
    {
        super();
    }
    protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	checkBeforeEdit();
    	super.actionEdit_actionPerformed(e);
    }
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	checkBeforeRemove();
    	super.actionRemove_actionPerformed(e);
    }
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
    	DefaultKingdeeTreeNode obj = (DefaultKingdeeTreeNode)kdTree.getLastSelectedPathComponent();
    	
    	boolean b = false;
    	if(obj!=null && obj.getUserObject() instanceof InviteTypeInfo){
    		InviteTypeInfo typeInfo = (InviteTypeInfo)obj.getUserObject();
    		if(typeInfo.isIsLeaf()){
    			b=true;
    		}
    		
    	}
    	if(!b){
    		FDCMsgBox.showWarning(this,"��ѡ����ϸ�ɹ����ͣ�");
			this.abort();
    	}
    	super.actionAddNew_actionPerformed(e);
    }
    
    protected String getEditUIName() {
    	return InviteBaseFileEditUI.class.getName();
    }
    public ICoreBase getBillInterface() throws Exception {
    	return InviteBaseFileFactory.getRemoteInstance();
    }
    protected ICoreBase getBizInterface() throws Exception {
    	return getBillInterface();
    }
        
    public void onLoad() throws Exception {
    	super.onLoad();
    	tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	buildInviteTypeTree();	
    	
	    TreeSelectionListener conTypeTreeSelectionListener = null;
		TreeSelectionListener[] listeners2 = kdTree
				.getTreeSelectionListeners();
		if (listeners2.length > 0) {
			conTypeTreeSelectionListener = listeners2[0];
			kdTree.removeTreeSelectionListener(conTypeTreeSelectionListener);
		}

		/*
		 * ����ѡ�и����
		 */
		kdTree.setSelectionRow(0);
		kdTree.addTreeSelectionListener(conTypeTreeSelectionListener);
	
		this.btnAudit.setEnabled(true);
		this.actionTraceDown.setVisible(false);
		this.actionTraceDown.setEnabled(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceUp.setEnabled(false);
		this.actionCreateTo.setVisible(false);
		
		btnAudit.setIcon(FDCClientHelper.ICON_AUDIT);
		btnUnAudit.setIcon(FDCClientHelper.ICON_UNAUDIT);
		menuItemAudit.setIcon(FDCClientHelper.ICON_AUDIT);
		menuItemUnAudit.setIcon(FDCClientHelper.ICON_UNAUDIT);
		menuItemAudit.setAccelerator(KeyStroke.getKeyStroke("ctrl U"));
		actionAudit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl U"));
		menuItemAudit.setText(menuItemAudit.getText().replaceAll("\\(A\\)", "")+"(A)");
		menuItemAudit.setMnemonic('A');
		
		actionAudit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift U"));
		menuItemUnAudit.setText(menuItemUnAudit.getText().replaceAll("\\(U\\)", "")+"(U)");
		menuItemUnAudit.setAccelerator(KeyStroke.getKeyStroke("ctrl shift U"));
		menuItemUnAudit.setMnemonic('U');
		
//		btnVersion.setIcon(Action.SMALL_ICON);
		actionVersion.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_emend"));
		menuItemVersion.setMnemonic('V');
		menuItemVersion.setText(menuItemVersion.getText()+"(V)");
		menuItemVersion.setAccelerator(KeyStroke.getKeyStroke("ctrl R"));
		
		this.menuItemCopyTo.setVisible(false);
		this.menuItemCopyTo.setEnabled(false);
		
		this.actionCancel.setEnabled(true);
		this.actionCancelCancel.setEnabled(true);
		this.actionCancel.setVisible(true);
		this.actionCancelCancel.setVisible(true);
		this.actionVersion.setVisible(false);
		
		
		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);
		
		if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())) {
			this.btnAddNew.setEnabled(true);
			this.btnEdit.setEnabled(true);
			this.btnRemove.setEnabled(true);
			this.btnAudit.setEnabled(true);
			this.btnUnAudit.setEnabled(true);
			this.btnVersion.setEnabled(true);
			this.menuItemAddNew.setEnabled(true);
			this.menuItemEdit.setEnabled(true);
			this.menuItemRemove.setEnabled(true);
			this.menuItemAudit.setEnabled(true);
			this.menuItemUnAudit.setEnabled(true);
			this.menuItemVersion.setEnabled(true);
		} else {
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnAudit.setEnabled(false);
			this.btnUnAudit.setEnabled(false);
			this.btnVersion.setEnabled(false);
			this.btnRemove.setEnabled(false);
			this.menuItemAddNew.setEnabled(false);
			this.menuItemEdit.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
			this.menuItemAudit.setEnabled(false);
			this.menuItemUnAudit.setEnabled(false);
			this.menuItemVersion.setEnabled(false);
		}
		
    }
    
    private ITreeBuilder treeBuilder;
    private TreeSelectionListener treeSelectionListener;
    private KDTree getContractTypeTree() {
		return kdTree;
	}

    /**
	 * ��ʼ��ILNTreeNodeCtrl��һ�㲻�����أ� �ڸ��ݱ������ݹ����ض���ʱ��������˵�����Ҫ����Ȩ�޽��й��ˣ�
	 * ������Ҫ��д�ض���ILNTreeNodeCtrl��ʵ���ࣨʵ����ı�д���Բ���DefaultLNTreeNodeCtrl)��
	 * ��ʱ�����Ҫ���أ�����Ϊʾ��������ض����ͣ���
	 */
	protected ILNTreeNodeCtrl getLNTreeNodeCtrl() throws Exception {
		return new DefaultLNTreeNodeCtrl(getTreeInterface());
	}

	private ITreeBase getTreeInterface() {

		ITreeBase treeBase = null;
		try {
			treeBase = InviteTypeFactory.getRemoteInstance();
		} catch (BOSException e) {
			abort(e);
		}

		return treeBase;
	}

	protected int getTreeInitialLevel() {
		return TreeBuilderFactory.DEFAULT_INITIAL_LEVEL;
	}

	protected int getTreeExpandLevel() {
		return TreeBuilderFactory.DEFAULT_EXPAND_LEVEL;
	}

	// ���ε�CU���˴���
	protected FilterInfo getDefaultFilterForTree() {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		return filter;
	}
	
	/**
	 * ������ڵ���ʾ���ƣ�Ĭ�Ϸ���null����û�и���㣨���н�����������ݣ� �̳���������أ������ʾ���
	 */
	protected String getRootName() {
		return "�ɹ����";
	}

	protected Object getRootObject() {
		return getRootName();
	}
	
	/**
	 * ����ɹ������
	 */
	protected void buildInviteTypeTree() throws Exception {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		TreeModel model = FDCClientHelper.createDataTree(InviteTypeFactory.getRemoteInstance(), filter, "�ɹ����");
		this.kdTree.setModel(model);
	}
	
	public SelectorItemCollection getSelectors() {
		// TODO Auto-generated method stub
		return super.getSelectors();
	}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		
    	
    	ItemAction action = getActionFromActionEvent(e);
        if (action.equals(actionAddNew))
        {
        	DefaultKingdeeTreeNode obj = (DefaultKingdeeTreeNode)kdTree.getLastSelectedPathComponent();
        	if(obj!=null){
        		getUIContext().put("obj", obj.getUserObject());
        	}
    		
        }
        
        if (action.equals(actionVersion))
        {
        	uiContext.put("isEditVersion", Boolean.TRUE);
        }
        
		super.prepareUIContext(uiContext, e);
	}
	
	public void actionVersion_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		checkBeforeReversion();
		super.actionEdit_actionPerformed(e);
	}
	
	private void checkBeforeReversion() throws Exception {
		String id = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue().toString();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select fid from T_INV_InviteBaseFile where fid=? and fstate=? and fislastver=? ");
		builder.addParam(id);
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		builder.addParam(new Integer(1));
		IRowSet rs = builder.executeQuery();
		if(rs==null||rs.size()==0){
			MsgBox.showWarning(this, "ֻ�����޶����µ�������״̬�İ汾��");
			SysUtil.abort();
		}
		
		
		
		builder.clear();
		builder.appendSql("select m.fnumber from T_INV_InviteBaseFile m ");
		builder.appendSql("inner join T_INV_InviteBaseFile tm on tm.finvitetypeid=m.finvitetypeid ");
		builder.appendSql("where tm.fid=? and m.fid<> ? and m.fstate<>? ");
		builder.addParam(id);
		builder.addParam(id);
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		rs = builder.executeQuery();
		if(rs!=null&&rs.size()>0){
			FDCMsgBox.showWarning(this,"�òɹ��������汾�ŵĺ�ͬ������δ������");
			this.abort();
		}
		
	}
	
	/**
	 * 
	 * ��������˲����ĵ���ǰ��״̬
	 * @return
	 * @author:liupd
	 * ����ʱ�䣺2006-8-1 <p>
	 */
	protected String getStateForAudit() {
		return FDCBillStateEnum.SUBMITTED_VALUE;
	}
	
	/**
	 * 
	 * ����������˲����ĵ���ǰ��״̬
	 * @return
	 * @author:liupd
	 * ����ʱ�䣺2006-8-1 <p>
	 */
	protected String getStateForUnAudit() {
		return FDCBillStateEnum.AUDITTED_VALUE;
	}
	
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		
		checkBillState(new String[]{getStateForAudit()}, "selectRightRowForAudit");
		
		audit(ContractClientUtils.getSelectedIdValues(tblMain, getKeyFieldName()));
		this.refresh(e);
	}
	
	private void audit(List ids) throws Exception{
		InviteBaseFileFactory.getRemoteInstance().audit(ids);

	}
	
	private void unaudit(List ids) throws Exception{
		InviteBaseFileFactory.getRemoteInstance().unAudit(ids);
	}
	
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		checkBillState(new String[]{getStateForUnAudit()}, "selectRightRowForUnAudit");
		unaudit(ContractClientUtils.getSelectedIdValues(tblMain, getKeyFieldName()));
		this.refresh(e);
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
		List idList = ContractClientUtils.getSelectedIdValues(tblMain, getKeyFieldName());

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
			
			if (!stateSet.contains(element.getString(getBillStatePropertyName()))) {
				MsgBox.showWarning(this, ContractClientUtils.getRes(res));
				abort();
			}

		}
	}
	protected String getBillStatePropertyName() {
    	return "state";
    }
	
	 /**
     * 
     * ���������ݿ��޸ġ�ɾ����״̬
     * @return
     * @author:liupd
     * ����ʱ�䣺2006-8-26 <p>
     */
    protected String[] getBillStateForEditOrRemove() {
    	return new String[]{FDCBillStateEnum.SAVED_VALUE, FDCBillStateEnum.SUBMITTED_VALUE};
    }
    
	  /**
     * 
     * �������޸�ǰ���
     * @author:liupd
     * ����ʱ�䣺2006-8-26 <p>
     * @throws Exception 
     */
    protected void checkBeforeEdit() throws Exception {
	    checkSelected();
		IObjectPK pk = new ObjectUuidPK(getSelectedKeyValue());
		InviteBaseFileInfo billInfo = (InviteBaseFileInfo)getBizInterface().getValue(pk);
		String billState = billInfo.getString(getBillStatePropertyName());
		String[] states = getBillStateForEditOrRemove();
		boolean pass = false;
		for (int i = 0; i < states.length; i++) {
			if(billState.equals(states[i])) {
				pass = true;
			}
		}
		if(!pass) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("cantEdit"));
			SysUtil.abort();
		}
    }
    
    /**
     * 
     * ��������������ɾ��ǰ���
     * @author:liupd
     * ����ʱ�䣺2006-8-26 <p>
     * @throws Exception 
     */
    protected boolean checkBeforeRemove() throws Exception {
    	checkSelected();
    	
    	List idList = ContractClientUtils.getSelectedIdValues(tblMain, getKeyFieldName());

    	if(idList==null || idList.size()==0){
    		return false;
    	}
    	
		Set idSet = ContractClientUtils.listToSet(idList);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add(getBillStatePropertyName());
		FDCBillCollection coll = InviteBaseFileFactory.getRemoteInstance().getFDCBillCollection(view);

		
		String[] states = getBillStateForEditOrRemove();
		
		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBillBaseInfo element = (CoreBillBaseInfo) iter.next();
			String billState = element.getString(getBillStatePropertyName());
			boolean pass = false;
			for (int i = 0; i < states.length; i++) {
				if(billState.equals(states[i])) {
					pass = true;
				}
			}
			if(!pass) {
				MsgBox.showWarning(this, ContractClientUtils.getRes("cantRemove"));
				SysUtil.abort();
			}
			
			ContractClientUtils.checkContractBillRefForRemove(this, element.getId().toString());
		}
		
		return true;
    }
    
	
    protected void kdTree_valueChanged(TreeSelectionEvent e) throws Exception {
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("cu.id",OrgConstants.DEF_CU_ID));
    	
    	DefaultKingdeeTreeNode typeNode = (DefaultKingdeeTreeNode)kdTree.getLastSelectedPathComponent();
    	
    	InviteTypeInfo typeInfo=null; 
    	if(typeNode!=null && typeNode.getUserObject() instanceof InviteTypeInfo){
    		typeInfo = (InviteTypeInfo)typeNode.getUserObject();
    		
    		FilterInfo typefilter =  new FilterInfo();
    		FilterItemCollection typefilterItems = typefilter.getFilterItems();	
    		typefilterItems.add(new FilterItemInfo("inviteType.longNumber",typeInfo.getLongNumber()+"%",CompareType.LIKE));
    		
    		if(filter!=null && typefilter!=null){
    			filter.mergeFilter(typefilter,"and");
    		}
    	}
    	
    	mainQuery.setFilter(filter);

		execQuery();
    	
    	
    }
    protected String[] getLocateNames() {
    	return new String[] {IFWEntityStruct.dataBase_Number, IFWEntityStruct.dataBase_Name,  
				 "state","inviteType.name","creator","createTime","auditTime","auditor"};
    }
    
    public void actionCancelCancel_actionPerformed(ActionEvent actionevent)
    throws Exception
	{
    	doCancelORCancelCancel(true);
    	actionCancel.setEnabled(true);
		actionCancelCancel.setEnabled(false);
	}
    
    private void doCancelORCancelCancel(boolean flag) throws BOSException{
    	checkSelected();
    	List idList = ContractClientUtils.getSelectedIdValues(tblMain, getKeyFieldName());
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder();
    	String message = null;
		if (flag) {
			builder.appendSql("UPDATE T_INV_InviteBaseFile SET FIsLastVer = 1 where 1=1 ");
			message = EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK");
			
		}
		else {
			builder.appendSql("UPDATE T_INV_InviteBaseFile SET FIsLastVer = 0 where 1=1 ");
			message = EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK");
		}
		builder.appendFilter("FID",listToSet(idList),CompareType.INCLUDE);
    	builder.execute();
    	setMessageText(message);
		showMessage();
		tblMain.refresh();
//		tblMain.fireTableClick(rowIndex, colIndex, clickCount)
		loadFields();
		
    }
    
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
    	doCancelORCancelCancel(false);
    	actionCancel.setEnabled(false);
		actionCancelCancel.setEnabled(true);
    }
    
    private Set listToSet(List list){
	   Set idSet = new HashSet();
	   if(list == null ||list.size()<1){
		   return idSet;
	   }
   		for(Iterator it = list.iterator() ; it.hasNext(); ){
   			idSet.add(it.next());
   		}
   			return idSet;
    }
   
    protected void tblMain_tableSelectChanged(KDTSelectEvent kdtselectevent)
    		throws Exception {
    	updateBottonState();
    }

	private void updateBottonState() {
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if(rowIndex < 0 ){
			return;
		}
		IRow row =  tblMain.getRow(rowIndex);
		String state =row.getCell("state").getValue().toString();
		Boolean isCancelCancel = (Boolean)row.getCell("isLastVer").getValue();
		if(FDCBillStateEnum.SAVED.getAlias().equals(state) ||FDCBillStateEnum.SUBMITTED.getAlias().equals(state)){
			this.actionEdit.setEnabled(true);
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
		}else if(FDCBillStateEnum.AUDITTED.getAlias().equals(state)&& !isCancelCancel.booleanValue() ){
			this.actionEdit.setEnabled(false);
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(true);
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(true);
		}else if(FDCBillStateEnum.AUDITTED.getAlias().equals(state)&& isCancelCancel.booleanValue()){
			this.actionEdit.setEnabled(false);
			this.actionCancel.setEnabled(true);
			this.actionCancelCancel.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
		}
		
	}

}