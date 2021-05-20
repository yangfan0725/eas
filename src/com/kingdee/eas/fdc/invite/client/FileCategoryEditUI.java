/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.invite.TemplateFileCategoryCollection;
import com.kingdee.eas.fdc.invite.TemplateFileCategoryFactory;
import com.kingdee.eas.fdc.invite.TemplateFileCategoryInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * �б��ļ�ģ�����Ŀ¼ �༭����
 */
public class FileCategoryEditUI extends AbstractFileCategoryEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(FileCategoryEditUI.class);
    
    private TemplateFileCategoryInfo  parentCategoryInfo = null ;
    
    /**
     * output class constructor
     */
    public FileCategoryEditUI() throws Exception
    {
        super();
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    	String category = this.txtCategory.getText();
    	if(category == null || category.trim().length() <= 0)
    	{
    		MsgBox.showWarning("����Ŀ¼����Ϊ��");
    		SysUtil.abort();
    	}
    	else
    	{
    		//���Ŀ¼�Ƿ��ظ�
    		if(getUIContext().get("CUR_CATEGORY_SET") != null)
    		{
    			TemplateFileCategoryCollection categoryCol = (TemplateFileCategoryCollection)getUIContext().get("CUR_CATEGORY_SET");
    			
    			Iterator iter = categoryCol.iterator();
    			while(iter.hasNext())
    			{
    				TemplateFileCategoryInfo tmpInfo = (TemplateFileCategoryInfo)iter.next();
    				if(category.equals(tmpInfo.getName()))
    				{
    					MsgBox.showWarning("Ŀ¼�����ظ������ܱ���");
    					SysUtil.abort();
    				}
    			}
    		}
    		getUIContext().put("CATEGORY", category);

    		MsgBox.showConfirm2("����ɹ�");
    		this.actionExitCurrent_actionPerformed(e);
    	}
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {

    }
    
	protected IObjectValue createNewData() 
	{
		TemplateFileCategoryInfo info = new TemplateFileCategoryInfo();
		parentCategoryInfo = (TemplateFileCategoryInfo)(getUIContext().get("PARENT_CATEGORY"));
	
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception 
	{
		return TemplateFileCategoryFactory.getRemoteInstance();
	}
	public void onLoad() throws Exception {
		super.onLoad();
		initHeadStyle();
		if(((Boolean)getUIContext().get("IS_EDIT")).booleanValue())
		{
			this.setUITitle("�ļ�Ŀ¼-�༭");
			String tmpCategory = getUIContext().get("CATEGORY").toString();
			this.txtCategory.setText(tmpCategory);
		}
		else
		{
			this.setUITitle("�ļ�Ŀ¼-����");
		}
		
	
	}
	protected void initHeadStyle()
	{
		this.actionAddNew.setEnabled(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setEnabled(false);
		this.actionEdit.setVisible(false);
		
		this.actionSave.setEnabled(false);
		this.actionSave.setVisible(false);
		this.actionCopy.setEnabled(false);
		this.actionCopy.setVisible(false);
		
		this.actionRemove.setEnabled(false);
		this.actionRemove.setVisible(false);
		this.actionPrintPreview.setEnabled(false);
		this.actionPrintPreview.setVisible(false);
		
		this.actionPrint.setEnabled(false);
		this.actionPrint.setVisible(false);
		this.actionPre.setEnabled(false);
		this.actionPre.setVisible(false);
		
		this.actionFirst.setEnabled(false);
		this.actionFirst.setVisible(false);
		this.actionNext.setEnabled(false);
		this.actionNext.setVisible(false);
		
		this.actionLast.setVisible(false);
		this.actionLast.setEnabled(false);
		this.actionCancel.setEnabled(false);
		this.actionCancel.setVisible(false);
		
		this.actionCancelCancel.setEnabled(false);
		this.actionCancelCancel.setVisible(false);
		
		this.contParentCategory.setEnabled(false);
		this.prmtParentCategory.setEnabled(false);
		
		//nameָ���Ǳ���
		this.prmtParentCategory.setDisplayFormat("$name$");
		this.prmtParentCategory.setValue(parentCategoryInfo);
		
		this.txtCategory.setRequired(true);
	}
}