/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.sellhouse.RenameRoomReasonFactory;
import com.kingdee.eas.fdc.sellhouse.RenameRoomReasonInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;

/**
 * output class name
 */
public class RenameRoomReasonListUI extends AbstractRenameRoomReasonListUI
{
    private static final Logger logger = CoreUIObject.getLogger(RenameRoomReasonListUI.class);
    
    /**
     * output class constructor
     */
    public RenameRoomReasonListUI() throws Exception
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

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {	//by tim_gao 2010-10-26
    	String selectID = this.tblMain.getCell(this.tblMain.getSelectManager().getActiveRowIndex(),"id").getValue().toString();
    	String warnings= "�޸�";
    	if(outPutWarningSentanceAndVerifyCancelorCancelCancelByID(warnings,selectID)){//�ж��Ƿ����ý���
    		this.abort();
    	}
    	super.actionEdit_actionPerformed(e);
    }
    /**
     * @author tim_gao
     * @date 2010-10-26
     * @throws BOSException 
     * @throws EASBizException 
     * @description ����ID����������ж��Ƿ�����
     */
    public boolean outPutWarningSentanceAndVerifyCancelorCancelCancelByID(String words,String selectID) throws EASBizException, BOSException{
    	boolean flag=false;
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("id",selectID));
    	filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.valueOf(true)));//�ж��Ƿ�����
    	if(RenameRoomReasonFactory.getRemoteInstance().exists(filter)){//�жϼ�¼�Ƿ����
    		MsgBox.showWarning("����¼�Ѿ����ã�����"+words+"!");
    		flag=true;
    	}
		return flag;
    }
    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {	//by tim_gao 2010-10-26
    	String selectID = this.tblMain.getCell(this.tblMain.getSelectManager().getActiveRowIndex(),"id").getValue().toString();
    	String warnings= "ɾ��";
    	if(outPutWarningSentanceAndVerifyCancelorCancelCancelByID(warnings,selectID)){//�ж��Ƿ����ý���
    		this.abort();
    	}
        super.actionRemove_actionPerformed(e);
    }

    protected FDCDataBaseInfo getBaseDataInfo() {
    	RenameRoomReasonInfo reasonInfo = new RenameRoomReasonInfo();
		return reasonInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RenameRoomReasonFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return RenameRoomReasonEditUI.class.getName();
	}
	protected void initWorkButton() {
		super.initWorkButton();
	}
	public void onLoad() throws Exception {
		super.onLoad();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}
}