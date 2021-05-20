/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.contract.client.ContractChangeBillEditUI;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;

/**
 * ������������ҳǩ�Ľ�������㱨
 */
public class ProgressReportBaseSchTaskPropertiesNewUI extends AbstractProgressReportBaseSchTaskPropertiesNewUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProgressReportBaseSchTaskPropertiesNewUI.class);
    
    public ProgressReportBaseSchTaskPropertiesNewUI() throws Exception
    {
        super();
    }
    
    public boolean isFromExecuteUI() {
		return true;
	}
    
	public void onLoad() throws Exception {
		super.onLoad();
		setUITitle("����㱨");
		this.btnAttachment.setText("�鿴����");
	}

	public void onShow() throws Exception {
		super.onShow();
		kDLabelContainer1.setEnabled(true);
	}
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionSave_actionPerformed(e);
		
		
	}
	
	@Override
	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAttachment_actionPerformed(e);
		int selectedRow = this.scheduleReportTable.getSelectManager().getActiveRowIndex();
		if(selectedRow !=-1){
			String reportID = scheduleReportTable.getCell(selectedRow, "id").getValue()+"";
			AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
			acm.showAttachmentListUIByBoID(reportID, this, false);
		}else{
			FDCMsgBox.showError("��ѡ��㱨����󣬲��ܲ鿴����");
		}
      }
	

}