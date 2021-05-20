/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctFactory;
import com.kingdee.eas.framework.ICoreBase;

/**
 * �����¶ȼƻ��걨��
 * ����Ŀ�¶ȼƻ��걨����ͬ��ά��������ͬʱ���У��������ع�
 */
public class FDCDepMonBudgetAcctListUI extends AbstractFDCDepMonBudgetAcctListUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCDepMonBudgetAcctListUI.class);
    
    /**
     * output class constructor
     */
    public FDCDepMonBudgetAcctListUI() throws Exception
    {
        super();
    }
	protected ICoreBase getRemoteInterface() throws BOSException {
		return FDCDepMonBudgetAcctFactory.getRemoteInstance();
	}
	
	protected String getEditUIName() {
		return FDCDepMonBudgetAcctEditUI.class.getName();
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		actionRecension.setVisible(false);
		actionRecension.setEnabled(false);
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		//�汾�ű���һλ
		tblMain.getColumn("verNumber").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(1));
		Object isFromWorkflow = getUIContext().get("isFromWorkflow");
		if(isFromWorkflow!=null 
    			&& isFromWorkflow.toString().equals("true")){
    		actionRemove.setEnabled(true);
    	}
	}
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		
		super.actionAddNew_actionPerformed(e);
	}
}