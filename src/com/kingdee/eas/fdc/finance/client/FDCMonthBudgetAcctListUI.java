/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;

import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

public class FDCMonthBudgetAcctListUI extends AbstractFDCMonthBudgetAcctListUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCMonthBudgetAcctListUI.class);
    public FDCMonthBudgetAcctListUI() throws Exception
    {
        super();
    }
    
	protected ICoreBase getRemoteInterface() throws BOSException {
		return FDCMonthBudgetAcctFactory.getRemoteInstance();
	}
	
	protected String getEditUIName() {
		return FDCMonthBudgetAcctEditUI.class.getName();
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
//		actionRecension.setVisible(true);
//		actionRecension.setEnabled(true);
		actionRecension.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift R"));
		menuItemRecension.setAccelerator(KeyStroke.getKeyStroke("ctrl shift R"));
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		boolean isDep = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_DEPCONPAYPLAN);
		if(isDep){
			FDCMsgBox.showWarning(this, "�����ò�����\"��ͬ�¶ȸ���ƻ��Ƿ��в��ź�ͬ����ƻ�ֱ������\",���Ƚ��øò���!");
			SysUtil.abort();
		}
		//�汾�ű���һλ
		tblMain.getColumn("verNumber").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(1));
		actionRecension.setVisible(actionEdit.isVisible());
		actionRecension.setEnabled(actionEdit.isEnabled());
		Object isFromWorkflow = getUIContext().get("isFromWorkflow");
		if(isFromWorkflow!=null 
    			&& isFromWorkflow.toString().equals("true")){
    		actionRemove.setEnabled(true);
    	}
	}
	public void actionRecension_actionPerformed(ActionEvent e) throws Exception {
		super.checkSelected();
		boolean isLatestVer = ((Boolean) this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isLatestVer").getValue()).booleanValue();
		if (!isLatestVer) {
			MsgBox.showWarning(this, "ֻ�����޶����µ�������״̬�İ汾��");
			SysUtil.abort();
		}
		String projectId = ((String) this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("curProject.id").getValue());
		String periodId = ((String) this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("period.id").getValue());
		String oSql = "select id where curProject.id='" +projectId+"' and fdcPeriod.id='"+periodId+"' and state<>'4AUDITTED'";
		if (FDCMonthBudgetAcctFactory.getRemoteInstance().exists(oSql)) {
			MsgBox.showWarning(this, "��ǰ�ƻ����°汾��δ�����������޶���");
			SysUtil.abort();
		}
		Object o = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue();
		if(o!=null&&!"".equals(o)){
			String recensionID = o.toString();
			getUIContext().put("recensionID", recensionID);
		}
		super.actionRecension_actionPerformed(e);
	}
}