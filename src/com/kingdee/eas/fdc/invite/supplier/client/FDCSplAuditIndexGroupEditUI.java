/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexGroupFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexGroupInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 
 * @(#)			FDCSplAuditIndexGroupEditUI.java			
 * ��Ȩ��		�����������������޹�˾��Ȩ����<P>		 	
 * ������		��������.��������ָ������.����ά��<P>
 *
 * @author		�ﱦƽ
 * @version		EAS7.0		
 * @createDate	2010-12-1	 
 * @see
 */
public class FDCSplAuditIndexGroupEditUI extends AbstractFDCSplAuditIndexGroupEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCSplAuditIndexGroupEditUI.class);
    /*
     * ��������ָ���parent
     */
    private FDCSplAuditIndexGroupInfo parentInfo = null;
    private FDCSplAuditIndexGroupInfo parent = null;
    /*
     * �����ַ������Գ�ʼ����������
     */
    private String strTemp="";
    private String parentnumber = null;
    /*
     * ����ı��״̬
     */
    private int state = 0;
    
    /*
     * ��ȡ��Դ�ļ�
     */
    private String getResource(String msg) {
		return EASResource.getString("com.kingdee.eas.fdc.invite.supplier.SupplierResource", msg);
	}
    
	public void onLoad() throws Exception {
		super.onLoad();
		
		if(getOprtState().equals(STATUS_ADDNEW)){
    		state = 1;
		}else{
			
		}
		
		/*
		 * ��Ӽ���,��ʼ����������
		 */
		txtNumber.addKeyListener(new KeyListener() {
			
			public void keyPressed(KeyEvent e) {
			}
			
			public void keyReleased(KeyEvent e) {
				/*
				 * ��ʼ�����������ʽ
				 */
				initTxtNum();
			}

			public void keyTyped(KeyEvent e) {
			}
		});
		storeFields();
	}
	
	public void loadFields() {
		
    	parentInfo = (FDCSplAuditIndexGroupInfo) getUIContext().get(UIContext.PARENTNODE);

    	super.loadFields();
    	
    	/*
    	 * ����ʱ��ʼ����������
    	 */
		if(getOprtState().equals(STATUS_ADDNEW)){
			if (getUIContext().get(UIContext.PARENTNODE) != null) {
				strTemp = parentInfo.getNumber();
				strTemp = strTemp.replace('!', '.');
				this.txtNumber.setText(strTemp + ".");
			}else{
				
			}
		}
		/*
		 * �޸�ʱ����ʼ����������
		 */
		else if(getOprtState().equals(STATUS_EDIT)){
			parentInfo = editData.getParent();

			if(parentInfo == null){
				this.txtNumber.setText(((FDCSplAuditIndexGroupInfo)(editData)).getNumber());
			}else{
				this.txtNumber.setText(editData.getNumber());
			}
		}
		/*
		 * �鿴ʱ��ʼ����������
		 */
		else if(getOprtState().equals(STATUS_VIEW)){
			strTemp = this.editData.getNumber();
			strTemp = strTemp.replace('!', '.');
			this.txtNumber.setText(strTemp);
		}
    }
	
    /**
     * 
    * @description		��ʼ����������
    * @author			�ﱦƽ	
    * @createDate		2010-11-29
    * @param	
    * @return			void		
    *	
    * @version			EAS7.0
    * @see
     */
	protected void initTxtNum() {
		
		if(parentnumber == null ){
	    	 parent = (FDCSplAuditIndexGroupInfo)( getUIContext().get(UIContext.PARENTNODE));
		     if (parent == null)
		    	 return ;
		     parentnumber = parent.getNumber().replace('!', '.')+".";
		}
		if(getOprtState().equals(STATUS_ADDNEW) || state == 1){
			if (!txtNumber.getText().startsWith(parentnumber)){
				txtNumber.setText(parentnumber);
				txtNumber.setSelectionStart(parentnumber.length());
			}
		}
		if(getOprtState().equals(STATUS_EDIT)  && state != 1){
			String number = parent.getNumber();
			number = number.replace('!', '.');
			if(number.lastIndexOf(".") > 0){
				String parentNumber = number.substring(0,number.lastIndexOf("."))+".";
				if (!txtNumber.getText().startsWith(parentNumber)) {
					txtNumber.setText(parentNumber);
					txtNumber.setSelectionStart(parentNumber.length());
				}
			}
		}
	}

	protected IObjectValue createNewData() {
		FDCSplAuditIndexGroupInfo object = new FDCSplAuditIndexGroupInfo();
		Map map = getUIContext();
		FDCSplAuditIndexGroupInfo parentInfo = (FDCSplAuditIndexGroupInfo) map
				.get(UIContext.PARENTNODE);
		if(parentInfo!=null)
			object.setParent(parentInfo);
		return object;
	}
	
	protected KDBizMultiLangBox getNameCtrl() {
		
		return this.txtName;
	}

	protected KDTextField getNumberCtrl() {
	
		return this.txtNumber;
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return FDCSplAuditIndexGroupFactory.getRemoteInstance();
	}
	
	protected String getClassAlise()
	{
		return "��Ӧ������ά��";
	}
	
    public FDCSplAuditIndexGroupEditUI() throws Exception
    {
        super();
        this.setUITitle("��Ӧ������ά��");
    }
    
	/**
	 * 
	* @description		������֤
	* @author			�ﱦƽ	
	* @createDate		2010-12-1
	*	
	* @version			EAS7.0
	* @see com.kingdee.eas.framework.client.EditUI#verifyInput(java.awt.event.ActionEvent)
	 */
	protected void verifyInput(ActionEvent actionevent) throws Exception {
		
	 	if(editData.getNumber() == null || editData.getNumber().toString().trim().length() == 0){
			FDCMsgBox.showWarning(this,getResource("level")+getResource("notNull"));
			abort();
		}
	 	
		if(editData.getName() == null || editData.getName().toString().trim().length() == 0){
			FDCMsgBox.showWarning(this,getResource("name")+getResource("notNull"));
			abort();
		}
		
//		if(isExist("number",editData.getNumber().toString().trim()) == true){
//    		MsgBox.showInfo(this,getResource("level")+editData.getNumber()+getResource("againInput"));
//    		abort();
//    	}
//		
//    	if(isExist("name",editData.getName().toString().trim()) == true){
//    		MsgBox.showInfo(this,getResource("name")+editData.getName()+getResource("againInput"));
//    		abort();
//    	}  
		/*
		 * ����ǰ�������ǰ��Ŀո�
		 */
		editData.setName(editData.getName().toString().trim());
		editData.setNumber(editData.getNumber().toString().trim());
		
		if(editData.getDescription() != null && editData.getDescription().trim().length() > 0){
			editData.setDescription(editData.getDescription().toString().trim());
		}else{
			editData.setDescription("");
		}
	}

	/**
	 * 
	* @description		����Ψһ�Լ��
	* @author			�ﱦƽ	
	* @createDate		2010-12-1
	* @param	
	* @return			boolean		
	*	
	* @version			EAS7.0
	* @see
	 */
    private boolean isExist(String FiledName,String filedValue)throws Exception{
    	
	    FilterInfo filterInfo=new FilterInfo();
	    /*
	     * nameΨһ�Լ��
	     */
	    FilterItemInfo itemInfo=new FilterItemInfo(FiledName,filedValue,com.kingdee.bos.metadata.query.util.CompareType.EQUALS);
	    filterInfo.getFilterItems().add(itemInfo);
	    filterInfo.setMaskString(" #0");
	    
	    if(STATUS_EDIT.equals(this.getOprtState())){
	    	/*
	    	 * IDΨһ�Լ��
	    	 */
	    	FilterItemInfo itemInfo_2=new FilterItemInfo("id",editData.getId().toString(),com.kingdee.bos.metadata.query.util.CompareType.NOTEQUALS);
	    	filterInfo.getFilterItems().add(itemInfo_2);
	    	filterInfo.setMaskString("#0 and #1");
	    }
	    
	    /*
	     * ����id��name�Ƿ�Ψһ�ı��
	     */
	    boolean flag=FDCSplAuditIndexGroupFactory.getRemoteInstance().exists(filterInfo);
	    
	    return flag;
    }
    
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

    /**
     * output actionReset_actionPerformed
     */
    public void actionReset_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionReset_actionPerformed(e);
    }

    /**
     * output actionMsgFormat_actionPerformed
     */
    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMsgFormat_actionPerformed(e);
    }
    
    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sic = super.getSelectors();
    	sic.add(new SelectorItemInfo("parent.*"));
    	return sic;
    }

}