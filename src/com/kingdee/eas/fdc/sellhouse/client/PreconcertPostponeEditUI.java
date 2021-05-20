/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PreconcertPostponeFactory;
import com.kingdee.eas.fdc.sellhouse.PreconcertPostponeInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * PreconcertPostponeEditUI
 * @author can_liu
 */
public class PreconcertPostponeEditUI extends AbstractPreconcertPostponeEditUI
{
    private Object prmtpurchase;
	public PreconcertPostponeEditUI() throws Exception
    {
        super();
    }
  
    public void onLoad() throws Exception {
    	super.onLoad();
    	//����F7ѡ���ͼ�ν���
    	prmtroom.setSelector(new FDCRoomPromptDialog(Boolean.FALSE,null,null,MoneySysTypeEnum.SalehouseSys, null,null));
    	btnAuditResult.setVisible(false);
    }
    
    public void loadFields()
    {
        super.loadFields();
    }

    public void storeFields()
    {
        super.storeFields();
    }
    
    protected void initDataStatus() {
    	super.initDataStatus();
    	actionWorkFlowG.setVisible(false);
    	actionCreateFrom.setVisible(false);
    	actionCreateTo.setVisible(false);
    	actionMultiapprove.setVisible(false);
    	actionNextPerson.setVisible(false);
    	actionCalculator.setVisible(false);
    	actionAttachment.setVisible(false);
    	actionAuditResult.setVisible(false);
    	btnAudit.setVisible(false);
    	this.menuBiz.setVisible(false);
		this.menuTable1.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
//		this.actionCreateFrom.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
//		this.actionAttachment.setVisible(false);		
		this.chkMenuItemSubmitAndAddNew.setSelected(true);
		this.menuSubmitOption.setVisible(false);
    }
    
    protected void verifyInput(ActionEvent e) throws Exception {
    	editData.setName(this.txtNumber.getText());
    	super.verifyInput(e);

    	if(pkoriginalAvailab.getValue()==null || pknowavailab.getValue()==null) {
    		MsgBox.showError("��Ԥ����Ч�ں�ԭԤ����Ч�ڶ�����Ϊ�գ�");
			SysUtil.abort();
    	}
    	
//    	if(SysUtil.getAppServerTime(null).after(editData.getNowavailab())){
    	if(!((Date)pkoriginalAvailab.getValue()).before((Date)pknowavailab.getValue())){
    		MsgBox.showError("��Ԥ����Ч�ڱ������ԭԤ����Ч�ڣ�");
			SysUtil.abort();
//			((Date)pkoriginalAvailab.getValue()).after((Date)pknowavailab.getValue());
    	}
    }
  
    protected void prmtroom_dataChanged(DataChangeEvent e) throws Exception {
    	if(prmtroom.getData() instanceof RoomInfo){
    		RoomInfo roomInfo = (RoomInfo)prmtroom.getData();
    		roomInfo.getSellState().getValue();
    		if(!roomInfo.getSellState().getValue().equals("PrePurchase")){    			
    			MsgBox.showError("��ѡ�񷿼�״̬ΪԤ���ķ��䣡");
    			SysUtil.abort();
    		}
    		PurchaseInfo info = PurchaseFactory.getRemoteInstance().getPurchaseInfo(new ObjectUuidPK(roomInfo.getLastPurchase().getId()));
    		txtpurchase.setUserObject(info);
    		txtpurchase.setText(info.getNumber());
    		this.pknowDat.setValue(info.getPrePurchaseDate());
    		this.pkoriginalDate.setValue(info.getPrePurchaseDate());
    		this.pkoriginalAvailab.setValue(info.getPrePurchaseValidDate());
    		editData.setOrgUnit(info.getOrgUnit());
    	}    	
    }
        
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	PreconcertPostponeInfo info = getSelectInfo();
    	if(info.getState().equals(FDCBillStateEnum.AUDITTED)){
			MsgBox.showInfo("����������,���ܱ༭��");
			SysUtil.abort();
    	}
    	super.actionEdit_actionPerformed(e);
    }
    
  //����ѡ�е���
	private PreconcertPostponeInfo getSelectInfo() throws EASBizException, BOSException{
		PreconcertPostponeInfo info = PreconcertPostponeFactory.getRemoteInstance().
		getPreconcertPostponeInfo( new ObjectUuidPK(editData.getId()));
		return info;
	}
    
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	PreconcertPostponeInfo info = getSelectInfo();
    	if(info.getState().equals(FDCBillStateEnum.AUDITTED)){
    		MsgBox.showInfo("����������,����ɾ����");
    		SysUtil.abort();
    	}
    	super.actionRemove_actionPerformed(e);
    }

    
    protected ICoreBase getBizInterface() throws Exception
    {
        return  PreconcertPostponeFactory.getRemoteInstance();
    }

    protected IObjectValue createNewDetailData(KDTable table)
    {
		
        return null;
    }

    protected IObjectValue createNewData()
    {
         PreconcertPostponeInfo objectValue = new  PreconcertPostponeInfo();
        objectValue.setCreator((UserInfo)(SysContext.getSysContext().getCurrentUser()));

        objectValue.setCreateTime(new Timestamp(new Date().getTime()));
        
        return objectValue;
    }

	protected void attachListeners() {
		
	}
	protected void detachListeners() {
		
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	
	/**
	 * �ڸ������Ѿ���ż�Ǹ���handroleCode���������˱��룬
	 * ����ڴ��ٴε���setAutoNumberByOrg�����Ļ����ͻ����
	 * �������� by renliang at 2010-12-4
	 */
	protected void setAutoNumberByOrg(String orgType) {
		//super.setAutoNumberByOrg(orgType);
	}

}