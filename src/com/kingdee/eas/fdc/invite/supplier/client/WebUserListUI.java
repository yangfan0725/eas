/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.ActionEvent;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.supplier.WebUserFactory;
import com.kingdee.eas.fdc.invite.supplier.WebUserInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class WebUserListUI extends AbstractWebUserListUI
{
    private static final Logger logger = CoreUIObject.getLogger(WebUserListUI.class);
    
    /**
     * output class constructor
     */
    public WebUserListUI() throws Exception
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
    protected ICoreBase getBizInterface() throws Exception {
    	return WebUserFactory.getRemoteInstance();
    }
    
    @Override
    protected String getEditUIName() {
    	// TODO Auto-generated method stub
    	return WebUserEditUI.class.getName();
    }
    
    @Override
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	// TODO Auto-generated method stub
    	super.actionAudit_actionPerformed(e);
    	checkSelected();
        int rowIndex =  this.tblMain.getSelectManager().getActiveRowIndex();
        String billId = this.tblMain.getCell(rowIndex, "id").getValue()+"";
        WebUserFactory.getRemoteInstance().audit(BOSUuid.read(billId));
        this.tblMain.removeRows();
      
    }
    
    @Override
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	// TODO Auto-generated method stub
    	super.actionUnAudit_actionPerformed(e);
    	checkSelected();
        int rowIndex =  this.tblMain.getSelectManager().getActiveRowIndex();
        String billId = this.tblMain.getCell(rowIndex, "id").getValue()+"";
        WebUserFactory.getRemoteInstance().unAudit(BOSUuid.read(billId));
        this.tblMain.removeRows();
    }
    
    @Override
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	super.onLoad();
//    	this.actionAddNew.setVisible(false);
//    	this.actionAddNew.setEnabled(false);
    	this.actionAssoSupplier.setEnabled(true);
    	this.btnAssoSupplier.setEnabled(true);
    }
    
    @Override
    public void actionAssoSupplier_actionPerformed(ActionEvent e)
    		throws Exception {
    	// TODO Auto-generated method stub
    	super.actionAssoSupplier_actionPerformed(e);
    	//检查是否选择了注册用户信息
    	checkSelected();
    	 int rowIndex =  this.tblMain.getSelectManager().getActiveRowIndex();
         String billId = this.tblMain.getCell(rowIndex, "id").getValue()+"";
         
         SelectorItemCollection selector = new SelectorItemCollection();
         selector.add("*");
         selector.add("entry.supplier.*");
         selector.add("entry.supplier.inviteType.*");
         
         WebUserInfo info = WebUserFactory.getRemoteInstance().getWebUserInfo(new ObjectUuidPK(BOSUuid.read(billId)), selector);
         
         if(!FDCBillStateEnum.AUDITTED.equals(info.getState())){
        	 FDCMsgBox.showError("只有审批通过的用户才能进行供应商关联。");
        	 SysUtil.abort();
         }
    	
    	UIContext uiContext = new UIContext();
    	uiContext.put("pUI", this);
    	uiContext.put("webUser", info);
    	UIFactory.createUIFactory().create(UserSupplierAssoUI.class.getName(), uiContext).show();
    }
    
    @Override
    public void actionInvalid_actionPerformed(ActionEvent e) throws Exception {
    	
    	checkSelected();
        int rowIndex =  this.tblMain.getSelectManager().getActiveRowIndex();
        String billId = this.tblMain.getCell(rowIndex, "id").getValue()+"";
        WebUserFactory.getRemoteInstance().invalidRegister(BOSUuid.read(billId));
        this.tblMain.removeRows();
    }
    
    @Override
    protected String getEditUIModal() {
    	// TODO Auto-generated method stub
    	return UIFactoryName.NEWTAB;
    }
    
    @Override
    public void actionChangePasswd_actionPerformed(ActionEvent e)
    		throws Exception {
    	// TODO Auto-generated method stub
    	super.actionChangePasswd_actionPerformed(e);
    	checkSelected();
    	String  id = getSelectedKeyValue();
        UIContext context = new UIContext();
        context.put("id", id);
        UIFactory.createUIFactory().create(ChangeUserPasswdUI.class.getName(), context).show();
    }
    

}