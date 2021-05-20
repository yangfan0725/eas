/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.ActionEvent;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.supplier.WebUserFactory;
import com.kingdee.eas.fdc.invite.supplier.WebUserInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class ChangeUserPasswdUI extends AbstractChangeUserPasswdUI
{
    private static final Logger logger = CoreUIObject.getLogger(ChangeUserPasswdUI.class);
    
    /**
     * output class constructor
     */
    public ChangeUserPasswdUI() throws Exception
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
    	
    	//从context中获取当前用户信息ID，并加载用户信息
    	Map uiContext =this.getUIContext();
    	String id = (String) uiContext.get("id");
    	
    	WebUserInfo userInfo = WebUserFactory.getRemoteInstance().getWebUserInfo(new ObjectUuidPK(BOSUuid.read(id)));
    	this.txtPhoneNumber.setText(userInfo.getMobileNumber());
    	this.txtName.setText(userInfo.getName());
    	this.txtCompanyName.setText(userInfo.getCompanyName());
    	
    	this.setUserObject(userInfo);
    	
    	
    	
    	
    }

   
    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
        
        WebUserInfo userInfo = (WebUserInfo) this.getUserObject();
        
        //校验两次输入的密码是否一致
        String  newPasswd= this.txtNewPasswd.getText().trim();
        
        if(StringUtils.isEmpty(newPasswd)){
        	FDCMsgBox.showError("新密码不能为空。");
        	SysUtil.abort();
        }
        
        String confirmPasswd = this.txtPasswdConfirm.getText().trim();
        if(!confirmPasswd.equals(newPasswd)){
        	FDCMsgBox.showError("两次输入的密码不一致。");
        	SysUtil.abort();
        }
        
        
        userInfo.setBoolean("isChangePasswd", Boolean.TRUE);
        userInfo.setPassword(newPasswd);
        WebUserFactory.getRemoteInstance().save(userInfo); 
        
        FDCMsgBox.showInfo("密码修改成功。");
    }

}