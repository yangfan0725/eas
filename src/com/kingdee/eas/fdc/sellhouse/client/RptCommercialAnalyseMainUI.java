
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.headfootdesigner.HeadFootModel;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.sellhouse.RptCommercialAnalyseFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.RptRoomFacadeFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.bireport.IBireportBaseFacade;
import com.kingdee.eas.framework.bireport.client.BireportBaseFilterUI;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class RptCommercialAnalyseMainUI extends AbstractRptCommercialAnalyseMainUI
{
	private static final long serialVersionUID = 7534042410556714506L;
	private static final Logger logger = CoreUIObject.getLogger(RptCommercialAnalyseMainUI.class);
    
    /**
     * output class constructor
     */
    public RptCommercialAnalyseMainUI() throws Exception
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


    
	protected IBireportBaseFacade getRemoteInstance() throws BOSException
	{
		// TODO �Զ����ɷ������
		return RptCommercialAnalyseFacadeFactory.getRemoteInstance();
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
		return new RptCommercialAnalyseFilterUI();
	}


	

}