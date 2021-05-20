/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.kdf.fd2.gui.util.MsgBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.IScheduleBizType;
import com.kingdee.eas.fdc.schedule.ScheduleBizTypeFactory;
import com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		ҵ������������ʱ������	
 * @author		���
 * @version		EAS7.0		
 * @createDate	2011-8-1 
 * @see						
 */
public class ScheduleBizTypeEditUI extends AbstractScheduleBizTypeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ScheduleBizTypeEditUI.class);
    
    /**
     * output class constructor
     */
    public ScheduleBizTypeEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
		// this.editData.getName();
		// this.editData.setName("222");
		// this.editData.setNumber("222");
        super.storeFields();
        
        String num = this.txtNumber.getText().toString();
        
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
    	BOSUuid id = this.editData.getId();
		ScheduleBizTypeInfo info = (ScheduleBizTypeInfo) (getBizInterface().getValue(new ObjectUuidPK(id)));
		// �ж��Ƿ���ϵͳԤ������
		if (info.isIsSysDef()) {
			FDCMsgBox.showInfo("ϵͳԤ�����ݣ��������޸ģ�");
			SysUtil.abort();
		}
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


	protected FDCDataBaseInfo getEditData() {
		// TODO Auto-generated method stub
		// return this.editData;
		return editData;
	}
	
	/*
	 * ������
	 * 
	 * @author libing
	 */

	protected KDBizMultiLangBox getNameCtrl() {
		// TODO Auto-generated method stub
		return kDBizMultiLangBox1;
	}


	protected KDTextField getNumberCtrl() {
		// TODO Auto-generated method stub
		return this.txtNumber;
	}

	
	protected IObjectValue createNewData() {
		
		ScheduleBizTypeInfo info = new ScheduleBizTypeInfo();
		IScheduleBizType type = null;
		String num = null;
		try {
			 type = (IScheduleBizType) getBizInterface();
			 // �õ�������
			num = type.getBiggestNumber();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Integer n = Integer.valueOf(num);
		int m = n.intValue();
		String item = null;
		item = (m + 1) + "";
		if (item.length() == 4) {
//			item = item;
		}
		if (item.length() == 3) {
			item = "0" + item;
		}
		if (item.length() == 2) {
			item = "00" + item;
		}
		if (item.length() == 1) {
			item = "000" + item;
		}
		info.setNumber(item);

		return info;
	}


	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return ScheduleBizTypeFactory.getRemoteInstance();
	}

	// ��ʼ����ť
	protected void initWorkButton() {
		// TODO Auto-generated method stub
		super.initWorkButton();
		// ɾ����ť
		this.btnRemove.setVisible(false);
		this.btnRemove.setEnabled(false);
	}
	/**
	 *@author libing
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		
		if (FDCHelper.isEmpty(editData.getName())) {
			FDCMsgBox.showInfo("ҵ���������Ʋ���Ϊ��");
			SysUtil.abort();
		}
		
		String num = this.txtNumber.getText().toString();
		// if (num.length() > 2) {
		// FDCMsgBox.showInfo("���벻�ܳ�����λ");
		// SysUtil.abort();
		// } else {
		// if (num.length() == 2) {
		// num = num;
		// }
		// if (num.length() == 1) {
		// num = "0" + num;
		// }
		// }
		
		if (num.length() > 4) {
			FDCMsgBox.showInfo("���벻�ܳ�����λ");
			SysUtil.abort();
		} else {
			if (num.length() == 3) {
				editData.setNumber("0" + editData.getNumber());
			}
			if (num.length() == 2) {
				editData.setNumber("00" + editData.getNumber());
			}
			if (num.length() == 1) {
				editData.setNumber("000" + editData.getNumber());
			}
			
	}
		
		
		
		if (FDCHelper.isEmpty(num)) {
			FDCMsgBox.showInfo("���벻��Ϊ��");
			SysUtil.abort();
		}
		boolean flag = num.matches("\\d+");
		// �ж�����ı���
		if (flag == false) {
			FDCMsgBox.showInfo(getResource("schNumber"));
			SysUtil.abort();
		}
	}
	// ��ȡ��Դ�ļ�·��
    private String getResource(String msg) {
		return EASResource.getString("com.kingdee.eas.fdc.schedule.ScheduleResource", msg);
	}
    
    public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		// this.CoreUI_toolbar.setToolTipText("ҵ�������б�");
		// ���ε����ð�ť
		btnCancelCancel.setEnabled(false);
		btnCancelCancel.setVisible(false);
		// if (getOprtState() == OprtState.VIEW) {
		// btnEdit.setEnabled(false);
		// btnSave.setEnabled(true);
		// btnAddNew.setEnabled(false);
		// }
		canOprate();
	}

	/**
	 * @description �жϵ�ǰ��֯�Ƿ��Ǽ��ţ�ֻ�м��Ų��ܽ�������ɾ����
	 * @author ����ΰ
	 * @createDate 2011-09-06
	 * @param
	 * @return
	 * 
	 * @version EAS6.0
	 * @see
	 */
	private void canOprate() {
		if (!("00000000-0000-0000-0000-000000000000CCE7AED4".equals(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()))) {
			this.btnAddNew.setEnabled(false);
			this.menuItemAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.menuItemEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
			this.btnPrint.setEnabled(false);
			this.btnPrintPreview.setEnabled(false);
			this.menuItemPrint.setEnabled(false);
			this.menuItemPrintPreview.setEnabled(false);
		}
	}
    
    
    
}
	