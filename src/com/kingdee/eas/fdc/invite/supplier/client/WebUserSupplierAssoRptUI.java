/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class WebUserSupplierAssoRptUI extends AbstractWebUserSupplierAssoRptUI
{
    private static final Logger logger = CoreUIObject.getLogger(WebUserSupplierAssoRptUI.class);
    
    /**
     * output class constructor
     */
    public WebUserSupplierAssoRptUI() throws Exception
    {
        super();
    }
    
    @Override
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	super.onLoad();
    }
    
    @Override
    protected void kdtQueryUserSupplierAssoQuery_doRequestRowSet(
    		RequestRowSetEvent e) {
    	// TODO Auto-generated method stub
    	super.kdtQueryUserSupplierAssoQuery_doRequestRowSet(e);
    	this.kdtQueryUserSupplierAssoQuery.getGroupManager().group();
    }
    
   

    
    
    

}