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
import com.kingdee.eas.fdc.sellhouse.RptCustomerFacade;
import com.kingdee.eas.fdc.sellhouse.RptCustomerFacadeFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.bireport.IBireportBaseFacade;
import com.kingdee.eas.framework.bireport.client.BireportBaseFilterUI;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class RptFDCCustomerMainUI extends AbstractRptFDCCustomerMainUI
{
    private static final Logger logger = CoreUIObject.getLogger(RptFDCCustomerMainUI.class);
    
    /**
     * output class constructor
     */
    public RptFDCCustomerMainUI() throws Exception
    {
        super();
    }


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
		return RptCustomerFacadeFactory.getRemoteInstance();
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
		return new RptFDCCustomerFilterUI();
	}

}