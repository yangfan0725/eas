/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.text.ParseException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.agent.AgentUtility;
import com.kingdee.bos.framework.agent.IObjectValueAgent;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ObjectValueForEditUIUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * 描述:成本科目编辑
 * @author jackwang  date:2006-9-6 <p>
 * @version EAS5.1
 */
public class CostAccountEditUI extends AbstractCostAccountEditUI{
    private static final Logger logger = CoreUIObject.getLogger(CostAccountEditUI.class);
    private FullOrgUnitInfo addFullOrgUnitCache = null;
    private CurProjectInfo addCurProjectCache = null;
    private CostAccountInfo addParentInfo = null; 
    /**
     * output class constructor
     */
    public CostAccountEditUI() throws Exception
    {
        super();
    }
    
    
    private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, FDCBaseDataClientUtils.COSTACCOUNT));
	}

	/**
	 * 只有二级成本科目才可以编辑 科目类别<br>
	 * 因为一级科目没有此属性，三级及以下的属性受二级科目控制。
	 * 
	 * @param parentLongNumber
	 *            父科目长编码
	 */
	private void disEnableBoxType(String parentLongNumber) {
		if (parentLongNumber.indexOf('.') != -1) {
			boxType.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			boxType.setEnabled(false);
		}
	}
    
	public void onLoad() throws Exception {
		super.onLoad();
		if(editData.getParent()==null){
			boxType.setEnabled(false);
			chkIsCostAccount.setEnabled(true);
		}else{
			chkIsCostAccount.setEnabled(false);
		}
		if(boxType.isEnabled()) {
			boxType.setSelectedItem(CostAccountTypeEnum.MAIN);	
		}
		FDCClientHelper.setActionEnable(actionAddNew, false);
		
		setTitle();
		setBtnStatus();		
		if (STATUS_ADDNEW.equals(this.getOprtState())) {
			this.chkIsEnabled.setEnabled(true);
			// //设置parent
			if(getUIContext().get("upId") != null){
				chkIsCostAccount.setEnabled(false);
				CostAccountInfo costAccountInfo = CostAccountFactory.getRemoteInstance().getCostAccountInfo(new ObjectStringPK(getUIContext().get("upId").toString()));
//				costAccountInfo.setId(BOSUuid.read(getUIContext().get("parentID").toString()));
				if(costAccountInfo.getFullOrgUnit()!=null){
					this.editData.setFullOrgUnit(costAccountInfo.getFullOrgUnit());
					this.addFullOrgUnitCache = costAccountInfo.getFullOrgUnit();
				}else if(costAccountInfo.getCurProject()!=null){
					this.editData.setCurProject(costAccountInfo.getCurProject());
					this.addCurProjectCache = costAccountInfo.getCurProject();
				}
				this.editData.setParent(costAccountInfo);
				if(costAccountInfo.isIsEnabled()){
					this.chkIsEnabled.setSelected(true);
					this.editData.setIsEnabled(true);		
					((CostAccountInfo)oldData).setIsEnabled(true);
				}else{
					this.chkIsEnabled.setSelected(false);
					this.editData.setIsEnabled(false);
					((CostAccountInfo)oldData).setIsEnabled(false);
				}
				//自动带入上级类别,并设置为不可修改,如果上级没有类别,本级可以自己设置类别
				if(costAccountInfo.getType()==null){
					this.boxType.setEnabled(true);
				}else{
					this.boxType.setEnabled(false);
					this.boxType.setSelectedItem(costAccountInfo.getType());
				}
				this.addParentInfo = costAccountInfo;
				String longNumber = costAccountInfo.getLongNumber();
				longNumber = longNumber.replace('!', '.');
				disEnableBoxType(longNumber);
				this.txtParentNumber.setText(longNumber);
				this.chkIsCostAccount.setSelected(costAccountInfo.isIsCostAccount());
			}else{
				chkIsCostAccount.setEnabled(true);
				this.chkIsCostAccount.setSelected(true);
				if (getUIContext().get("source") != null) {
					if (getUIContext().get("source") instanceof FullOrgUnitInfo) {
						FullOrgUnitInfo fullOrgUnitInfo = new FullOrgUnitInfo();
						fullOrgUnitInfo.setId(BOSUuid.read(((FullOrgUnitInfo)getUIContext().get("source")).getId().toString()));
						this.editData.setFullOrgUnit(fullOrgUnitInfo);
						this.addFullOrgUnitCache = fullOrgUnitInfo;
					} else if (getUIContext().get("source") instanceof CurProjectInfo) {		
						this.editData.setCurProject((CurProjectInfo) getUIContext().get("source"));
						this.addCurProjectCache = (CurProjectInfo) getUIContext().get("source");
					}
				}
				this.editData.setParent(null);
			}
		}else {
			setDataObject(getValue(new ObjectUuidPK(editData.getId())));
	        loadFields();
//			 设置编辑和查看状态下界面的上级编码控件和本级简码控件
			String longNumber = editData.getLongNumber();
			if(longNumber!=null){
				longNumber = longNumber.replace('!', '.');
				this.txtLongNumber.setText(longNumber);
				if (longNumber.indexOf(".") > 0) {
					disEnableBoxType(longNumber.substring(0, longNumber.lastIndexOf(".")));
					this.txtParentNumber.setText(longNumber.substring(0, longNumber.lastIndexOf(".")));
				}
			}
			
//			如果当前的组织不是集团
			if((SysContext.getSysContext().getCurrentFIUnit()!=null&&(!SysContext.getSysContext().getCurrentFIUnit().isIsOnlyUnion()))
					||(SysContext.getSysContext().getCurrentCostUnit()!=null&&SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit())){
				if(!this.editData.isIsLeaf()){
					this.btnAddNew.setEnabled(false);
					this.menuItemAddNew.setEnabled(false);
				}
			}else{//如果当前的组织是集团，并且当前选中的节点不是集团，那么不允许新增
				if(this.editData.getFullOrgUnit()!=null){
					if(!OrgConstants.DEF_CU_ID.equals(this.editData.getFullOrgUnit().getId().toString())){
						this.btnAddNew.setEnabled(false);
						this.btnEdit.setEnabled(false);
						this.btnRemove.setEnabled(false);
						this.menuItemAddNew.setEnabled(false);
						this.menuItemEdit.setEnabled(false);
						this.menuItemRemove.setEnabled(false);
					}else{
						this.btnAddNew.setEnabled(true);
						this.btnEdit.setEnabled(true);
						this.btnRemove.setEnabled(true);
						this.menuItemAddNew.setEnabled(true);
						this.menuItemEdit.setEnabled(true);
						this.menuItemRemove.setEnabled(true);
					}
				}else{
					this.btnAddNew.setEnabled(false);
					this.btnEdit.setEnabled(false);
					this.btnRemove.setEnabled(false);
					this.menuItemAddNew.setEnabled(false);
					this.menuItemEdit.setEnabled(false);
					this.menuItemRemove.setEnabled(false);
				}
			}
			if(STATUS_EDIT.equals(this.getOprtState())){
				this.btnEdit.setEnabled(false);
				this.menuItemEdit.setEnabled(false);
			}
			if(this.editData.isAssigned()){//已分配的不允许删除
				this.btnRemove.setEnabled(false);
				this.menuItemRemove.setEnabled(false);
			}			
			if(!this.editData.isIsSource()){//分配下来的不允许修改删除
				this.btnEdit.setEnabled(false);
				this.menuItemEdit.setEnabled(false);
				this.btnRemove.setEnabled(false);
				this.menuItemRemove.setEnabled(false);
			}
			if(STATUS_VIEW.equals(this.getOprtState())){
		        this.addCurProjectCache = editData.getCurProject();
		        this.addFullOrgUnitCache = editData.getFullOrgUnit();
		        this.addParentInfo = editData.getParent();
		        chkIsCostAccount.setEnabled(false);
			}
		}		
		storeFields();
		initOldData(editData);
	}
	private void setBtnStatus() {
		if (STATUS_ADDNEW.equals(getOprtState())) {//新增状态
			this.btnCancelCancel.setVisible(false);//启用按钮不可见
			this.btnCancel.setVisible(false);//禁用按钮不可见
		} else if (STATUS_EDIT.equals(getOprtState())) {//修改状态
			if (this.editData.isIsEnabled()) {//如果当前为启用状态
				this.btnCancel.setVisible(true);//禁用按钮可用    			
				this.btnCancel.setEnabled(true);//禁用按钮可用
				this.btnCancelCancel.setVisible(false);//启用按钮不可见
			} else {//如果当前为禁用状态
				this.btnCancelCancel.setVisible(true);//启用按钮可见
				this.btnCancelCancel.setEnabled(true);//启用按钮可用    			
				this.btnCancel.setEnabled(false);//禁用按钮不可见    			
			}
		} else if (STATUS_VIEW.equals(getOprtState())) {//查看状态			
//			if(OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())){
//				if (this.editData.isIsEnabled()) {//如果当前为启用状态
//					this.btnCancel.setVisible(true);//禁用按钮可用    			
//					this.btnCancel.setEnabled(true);//禁用按钮可用
//					this.btnCancelCancel.setVisible(false);//启用按钮不可见
//				} else {//如果当前为禁用状态
//					this.btnCancelCancel.setVisible(true);//启用按钮可见
//					this.btnCancelCancel.setEnabled(true);//启用按钮可用    			
//					this.btnCancel.setEnabled(false);//禁用按钮不可见    			
//				}
				this.btnAddNew.setEnabled(true);
				this.btnEdit.setEnabled(true);
				this.menuItemAddNew.setEnabled(true);
				this.menuItemEdit.setEnabled(true);
				this.menuItemRemove.setEnabled(true);
				this.btnCancel.setVisible(false);//
				this.btnCancelCancel.setVisible(false);//
//			}else{
//				this.btnAddNew.setEnabled(false);
//				this.btnEdit.setEnabled(false);
//				this.btnRemove.setEnabled(false);
//				this.btnCancel.setVisible(false);
//				this.btnCancelCancel.setVisible(false);
//				this.menuItemAddNew.setEnabled(false);
//				this.menuItemEdit.setEnabled(false);
//				this.menuItemRemove.setEnabled(false);
//			}			
		}
	}
	/**
	 * 校验值对象的合法性
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		// 编码是否为空
		if (this.txtNumber.getText() == null || this.txtNumber.getText().trim().equals("")) {
			txtNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
		}
		// 名称是否为空
		boolean flag = FDCBaseDataClientUtils.isMultiLangBoxInputNameEmpty(bizName, this.editData, "name");
		if (flag) {
			bizName.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NAME_IS_EMPTY);
		}
		if (!(this.txtNumber.getText().indexOf(".") < 0) || !(this.txtNumber.getText().indexOf("!") < 0)) {
			txtNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_CHECK_3);
		}
		if (editData.getParent()!=null&&(this.boxType.getSelectedItem()==null)) {
			boxType.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.COSTACCOUNTTYPE_ISNULL);
		}
	}
	protected void showResultMessage(String message) {
		setMessageText(message);
		showMessage();
	}
	/**
	 * 禁用
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {		
		IObjectPK pk = new ObjectStringPK(this.editData.getId().toString());
		if (((ICostAccount) getBizInterface()).disable(pk)) {
			this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
			this.btnCancelCancel.setVisible(true);
			this.btnCancelCancel.setEnabled(true);
			this.btnCancel.setVisible(false);		
			setDataObject(getValue(new ObjectUuidPK(editData.getId())));
	        loadFields();
			setSave(true);
	        setSaved(true);
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
		super.actionEdit_actionPerformed(e);
		if(this.editData.getFullOrgUnit() != null && this.editData.getParent() == null && 
				OrgConstants.DEF_CU_ID.equals(this.editData.getFullOrgUnit().getId().toString())){
				chkIsCostAccount.setEnabled(true);		
		}
    }
	/**
	 * 启用
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		IObjectPK pk = new ObjectStringPK(this.editData.getId().toString());
		if (((ICostAccount) getBizInterface()).enable(pk)) {
			this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
			this.btnCancel.setVisible(true);
			this.btnCancel.setEnabled(true);
			this.btnCancelCancel.setVisible(false);
			setDataObject(getValue(new ObjectUuidPK(editData.getId())));
	        loadFields();
			setSave(true);
	        setSaved(true);
		}
	}
    /**
     * output storeFields method
     * @author xiaobin_li
     * @work debug the bug that the value is not consistent when change the old value
     */
    public void storeFields()
    {
    	super.storeFields(); 
    	
    	if (this.txtNumber.getText() != null) {
			if (this.txtParentNumber.getText() != null && (!"".equals(this.txtParentNumber.getText().trim()))) {
				this.txtLongNumber.setText(this.txtParentNumber.getText() + "." + this.txtNumber.getText());
			} else {
				this.txtLongNumber.setText(this.txtNumber.getText());
			}
	    }
   
    	if(STATUS_EDIT.equals(this.getOprtState())){
    	    String longNumber = this.txtLongNumber.getText();
    		longNumber = longNumber.replace('.','!');
    		this.editData.setLongNumber(longNumber);
    		((CostAccountInfo)oldData).setLongNumber(longNumber);
    	} 	 
    }

	protected IObjectValue createNewData() {

		CostAccountInfo costAccountInfo = new CostAccountInfo();
		costAccountInfo.setFullOrgUnit(this.addFullOrgUnitCache);
		costAccountInfo.setCurProject(this.addCurProjectCache);	
		costAccountInfo.setParent(this.addParentInfo);
		costAccountInfo.setIsProgramming(true);
		if(this.addParentInfo!=null && addParentInfo.getType() != null){
			costAccountInfo.setType(this.addParentInfo.getType());
			costAccountInfo.setIsCostAccount(this.addParentInfo.isIsCostAccount());
		}else{
			if(getUIContext().get("upId") != null){
				try {
					CostAccountInfo tmp = CostAccountFactory.getRemoteInstance().getCostAccountInfo(new ObjectStringPK(getUIContext().get("upId").toString()));
					if(tmp.getType() != null) {
						costAccountInfo.setType(tmp.getType());
						costAccountInfo.setIsCostAccount(tmp.isIsCostAccount());
					}
					else {
						this.boxType.setEnabled(true);
						costAccountInfo.setIsCostAccount(tmp.isIsCostAccount());
					}
				} catch (EASBizException e) {
				} catch (BOSException e) {	
				}
			}/*else{
				//1级科目不设置类别
				costAccountInfo.put("type", null);
				boxType.setEnabled(false);
			}*/
		}
		costAccountInfo.setIsSource(true);
		return costAccountInfo;
	}
	private AbstractObjectValue oldData = null;
    /**
     * 克隆旧值，用来检查是否修改
     *
     * @param dataObject
     */
    protected void initOldData(IObjectValue dataObject) 
    {
        AbstractObjectValue objectValue = (AbstractObjectValue) dataObject;
        /*if(STATUS_ADDNEW.equals(getOprtState()))
        {
            objectValue = (AbstractObjectValue) createNewData();

			if(getUIContext().get("upId") != null){
				CostAccountInfo costAccountInfo;
				try {
					costAccountInfo = CostAccountFactory.getRemoteInstance().getCostAccountInfo(new ObjectStringPK(getUIContext().get("upId").toString()));
					if(costAccountInfo.getFullOrgUnit()!=null){
//						this.editData.setFullOrgUnit(costAccountInfo.getFullOrgUnit());
						((CostAccountInfo)objectValue).setFullOrgUnit(costAccountInfo.getFullOrgUnit());
					}else if(costAccountInfo.getCurProject()!=null){
//						this.editData.setCurProject(costAccountInfo.getCurProject());
						((CostAccountInfo)objectValue).setCurProject(costAccountInfo.getCurProject());
					}
//					this.editData.setParent(costAccountInfo);
					((CostAccountInfo)objectValue).setParent(costAccountInfo);
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
	
				}				
			}else{
				if (getUIContext().get("source") != null) {
					if (getUIContext().get("source") instanceof FullOrgUnitInfo) {
						FullOrgUnitInfo fullOrgUnitInfo = new FullOrgUnitInfo();
						fullOrgUnitInfo.setId(BOSUuid.read(((FullOrgUnitInfo)getUIContext().get("source")).getId().toString()));
//						this.editData.setFullOrgUnit(fullOrgUnitInfo);
						((CostAccountInfo)objectValue).setFullOrgUnit(fullOrgUnitInfo);
					} else if (getUIContext().get("source") instanceof CurProjectInfo) {		
//						this.editData.setCurProject((CurProjectInfo) getUIContext().get("source"));
						((CostAccountInfo)objectValue).setCurProject((CurProjectInfo) getUIContext().get("source"));
					}
				}
//				this.editData.setParent(null);
				((CostAccountInfo)objectValue).setParent(null);
			}
		
            //还原。
           
        }*/
//        oldData = (AbstractObjectValue) objectValue.clone();        
        //Appicable for Agent. Modified by Charse Wong at 2006-4-12 in EASv5.1
        //Begin
        if (objectValue instanceof IObjectValueAgent) {
            oldData = (AbstractObjectValue)AgentUtility.deepCopyAgentValue((IObjectValueAgent)objectValue);
        } else {
        	if (objectValue != null) {
				oldData = (AbstractObjectValue) objectValue.clone();
			}else{
				this.destroyWindow();
			}
        }
        //End

    }
    /**
     * 判断当前编辑的数据是否发生变化
     */
    public boolean isModify() {

        //如果是Onload时的中断处理，则不会进行数据比较。2006-8-22 by psu_s
//        if(isOnLoadExceptionAbort)
//        {
//            return false;
//        }
        try
        {
            com.kingdee.bos.ctrl.common.util.ControlUtilities.checkFocusAndCommit();
        }
        catch (ParseException e)
        {
			handleControlException();
			// 工作流需要知道是否发生了异常
			//wfContext.setThrowException(true);

			abort();
        }

        //        return false;
        /* 去掉这个判断。没有什么性能损耗。 2006-9-21
        if(isSave())
        {
            return false;
        }*/
        //查看状态不判断是否修改
        if (OprtState.VIEW.equals(getOprtState())) {
            return false;
        }

        try {
            storeFields();
        } catch (Exception exc) {
            return false;
        }

        return !ObjectValueForEditUIUtil.objectValueEquals(oldData, editData);
    }
	protected ICoreBase getBizInterface() throws Exception {
		return CostAccountFactory.getRemoteInstance();
	}
    /**
     * output txtNumber_focusLost method
     */
    protected void txtNumber_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    	if (this.txtNumber.getText() != null) {
			if (this.txtParentNumber.getText() != null && (!"".equals(this.txtParentNumber.getText().trim()))) {
				this.txtLongNumber.setText(this.txtParentNumber.getText() + "." + this.txtNumber.getText());
			} else {
				this.txtLongNumber.setText(this.txtNumber.getText());
			}
	    }
    }
    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("isSource"));
        sic.add(new SelectorItemInfo("assigned"));
        sic.add(new SelectorItemInfo("isEnabled"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("type"));
        sic.add(new SelectorItemInfo("isLeaf"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("longNumber"));
        sic.add(new SelectorItemInfo("isCostAccount"));
        sic.add(new SelectorItemInfo("parent.id"));
        sic.add(new SelectorItemInfo("curProject.*"));
        sic.add(new SelectorItemInfo("fullOrgUnit.*"));
        sic.add(new SelectorItemInfo("isProgramming"));
        sic.add(new SelectorItemInfo("rate"));
        sic.add(new SelectorItemInfo("isMarket"));
        return sic;
    }  
}