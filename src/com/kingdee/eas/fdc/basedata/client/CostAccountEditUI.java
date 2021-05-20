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
 * ����:�ɱ���Ŀ�༭
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
	 * ֻ�ж����ɱ���Ŀ�ſ��Ա༭ ��Ŀ���<br>
	 * ��Ϊһ����Ŀû�д����ԣ����������µ������ܶ�����Ŀ���ơ�
	 * 
	 * @param parentLongNumber
	 *            ����Ŀ������
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
			// //����parent
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
				//�Զ������ϼ����,������Ϊ�����޸�,����ϼ�û�����,���������Լ��������
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
//			 ���ñ༭�Ͳ鿴״̬�½�����ϼ�����ؼ��ͱ�������ؼ�
			String longNumber = editData.getLongNumber();
			if(longNumber!=null){
				longNumber = longNumber.replace('!', '.');
				this.txtLongNumber.setText(longNumber);
				if (longNumber.indexOf(".") > 0) {
					disEnableBoxType(longNumber.substring(0, longNumber.lastIndexOf(".")));
					this.txtParentNumber.setText(longNumber.substring(0, longNumber.lastIndexOf(".")));
				}
			}
			
//			�����ǰ����֯���Ǽ���
			if((SysContext.getSysContext().getCurrentFIUnit()!=null&&(!SysContext.getSysContext().getCurrentFIUnit().isIsOnlyUnion()))
					||(SysContext.getSysContext().getCurrentCostUnit()!=null&&SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit())){
				if(!this.editData.isIsLeaf()){
					this.btnAddNew.setEnabled(false);
					this.menuItemAddNew.setEnabled(false);
				}
			}else{//�����ǰ����֯�Ǽ��ţ����ҵ�ǰѡ�еĽڵ㲻�Ǽ��ţ���ô����������
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
			if(this.editData.isAssigned()){//�ѷ���Ĳ�����ɾ��
				this.btnRemove.setEnabled(false);
				this.menuItemRemove.setEnabled(false);
			}			
			if(!this.editData.isIsSource()){//���������Ĳ������޸�ɾ��
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
		if (STATUS_ADDNEW.equals(getOprtState())) {//����״̬
			this.btnCancelCancel.setVisible(false);//���ð�ť���ɼ�
			this.btnCancel.setVisible(false);//���ð�ť���ɼ�
		} else if (STATUS_EDIT.equals(getOprtState())) {//�޸�״̬
			if (this.editData.isIsEnabled()) {//�����ǰΪ����״̬
				this.btnCancel.setVisible(true);//���ð�ť����    			
				this.btnCancel.setEnabled(true);//���ð�ť����
				this.btnCancelCancel.setVisible(false);//���ð�ť���ɼ�
			} else {//�����ǰΪ����״̬
				this.btnCancelCancel.setVisible(true);//���ð�ť�ɼ�
				this.btnCancelCancel.setEnabled(true);//���ð�ť����    			
				this.btnCancel.setEnabled(false);//���ð�ť���ɼ�    			
			}
		} else if (STATUS_VIEW.equals(getOprtState())) {//�鿴״̬			
//			if(OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())){
//				if (this.editData.isIsEnabled()) {//�����ǰΪ����״̬
//					this.btnCancel.setVisible(true);//���ð�ť����    			
//					this.btnCancel.setEnabled(true);//���ð�ť����
//					this.btnCancelCancel.setVisible(false);//���ð�ť���ɼ�
//				} else {//�����ǰΪ����״̬
//					this.btnCancelCancel.setVisible(true);//���ð�ť�ɼ�
//					this.btnCancelCancel.setEnabled(true);//���ð�ť����    			
//					this.btnCancel.setEnabled(false);//���ð�ť���ɼ�    			
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
	 * У��ֵ����ĺϷ���
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		// �����Ƿ�Ϊ��
		if (this.txtNumber.getText() == null || this.txtNumber.getText().trim().equals("")) {
			txtNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
		}
		// �����Ƿ�Ϊ��
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
	 * ����
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
	 * ����
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
				//1����Ŀ���������
				costAccountInfo.put("type", null);
				boxType.setEnabled(false);
			}*/
		}
		costAccountInfo.setIsSource(true);
		return costAccountInfo;
	}
	private AbstractObjectValue oldData = null;
    /**
     * ��¡��ֵ����������Ƿ��޸�
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
		
            //��ԭ��
           
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
     * �жϵ�ǰ�༭�������Ƿ����仯
     */
    public boolean isModify() {

        //�����Onloadʱ���жϴ����򲻻�������ݱȽϡ�2006-8-22 by psu_s
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
			// ��������Ҫ֪���Ƿ������쳣
			//wfContext.setThrowException(true);

			abort();
        }

        //        return false;
        /* ȥ������жϡ�û��ʲô������ġ� 2006-9-21
        if(isSave())
        {
            return false;
        }*/
        //�鿴״̬���ж��Ƿ��޸�
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