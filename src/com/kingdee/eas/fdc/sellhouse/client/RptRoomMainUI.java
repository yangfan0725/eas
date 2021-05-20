/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.headfootdesigner.HeadFootModel;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.sellhouse.RptRoomFacadeFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.bireport.IBireportBaseFacade;
import com.kingdee.eas.framework.bireport.client.BireportBaseFilterUI;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class RptRoomMainUI extends AbstractRptRoomMainUI
{
    private static final Logger logger = CoreUIObject.getLogger(RptRoomMainUI.class);
    
    /**
     * output class constructor
     */
    public RptRoomMainUI() throws Exception
    {
        super();
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
    	handlePermissionForItemAction(actionExport);
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
    	handlePermissionForItemAction(actionExportSelected);
        super.actionExportSelected_actionPerformed(e);
    }

	protected IBireportBaseFacade getRemoteInstance() throws BOSException
	{
		// TODO �Զ����ɷ������
		return RptRoomFacadeFactory.getRemoteInstance();
	}

	protected RptParams getParamsForInit()
	{
		// TODO �Զ����ɷ������
		return null;
	}

	protected RptParams getParamsForRequest()
	{
		// TODO �Զ����ɷ������
		return this.params;
	}

	protected void onBeforeQuery() throws Exception
	{
		// TODO �Զ����ɷ������
		
	}

	protected void onAfterQuery() throws Exception
	{
		// TODO �Զ����ɷ������
		
	}

	protected KDTable getTableForPrintSetting()
	{
		// TODO �Զ����ɷ������
		return this.kDTable1;
	}

	protected void preparePrintPageHeader(HeadFootModel header)
	{
		// TODO �Զ����ɷ������
		
	}

	protected Map preparePrintVariantMap()
	{
		// TODO �Զ����ɷ������
		return null;
	}
	
	protected BireportBaseFilterUI getQueryDialogUserPanel() throws Exception
	{
		// TODO �Զ����ɷ������
		return new RptRoomFilterUI();
	}

}