/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.ActionEvent;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.invite.supplier.RegistStateEnum;
import com.kingdee.eas.fdc.invite.supplier.WebUserFactory;
import com.kingdee.eas.fdc.invite.supplier.WebUserInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.util.Uuid;

/**
 * output class name
 */
public class WebUserEditUI extends AbstractWebUserEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(WebUserEditUI.class);
    
    /**
     * output class constructor
     */
    public WebUserEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    @Override
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	super.onLoad();
    	this.contCompanyName.setEnabled(true);
    	this.txtCompanyName.setEnabled(true);
    	this.contEmail.setEnabled(true);
    	this.txtEmail.setEnabled(true);
    	this.contMobileNumber.setEnabled(true);
    	this.txtMobileNumber.setEnabled(true);
    	this.contName.setEnabled(true);
    	this.txtName.setEnabled(true);
    	this.contPassword.setEnabled(true);
    	this.txtPassword.setEnabled(true);
    	this.contPosition.setEnabled(true);
    	this.txtPosition.setEnabled(true);
    	this.contTelephone.setEnabled(true);
    	this.txtTelephone.setEnabled(true);
    }

   @Override
	protected void unLockUI() {
		// TODO Auto-generated method stub
		super.unLockUI();
	}
   
   @Override
	protected void unLockUIAndAction() {
		// TODO Auto-generated method stub
		super.unLockUIAndAction();
	}

    /**
     * output actionAudit_actionPerformed
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }

    /**
     * output actionUnAudit_actionPerformed
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }

   
	@Override
	protected void attachListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void detachListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return WebUserFactory.getRemoteInstance();
	}

	@Override
	protected KDTable getDetailTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected KDTextField getNumberCtrl() {
		// TODO Auto-generated method stub
		return this.txtName;
	}
	
	@Override
	protected IObjectValue createNewData() {
		// TODO Auto-generated method stub
		WebUserInfo info = new WebUserInfo();
		info.setNumber(Uuid.create()+"");
		info.setRegisterState(RegistStateEnum.REGISTAUDITTING);
		info.setCreateTime(new Timestamp(System.currentTimeMillis()));
		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		return info;
	}
	
	@Override
	public SelectorItemCollection getSelectors() {
		// TODO Auto-generated method stub
		SelectorItemCollection sic = super.getSelectors();
		sic.add("auditor.name");
		return sic;
	}

}