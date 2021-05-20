/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.invite.supplier.ChooseFactory;
import com.kingdee.eas.fdc.invite.supplier.ChooseInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class ChooseListUI extends AbstractChooseListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ChooseListUI.class);
    public ChooseListUI() throws Exception
    {
        super();
    }
    protected FDCDataBaseInfo getBaseDataInfo() {
		return new ChooseInfo();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return ChooseFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return ChooseEditUI.class.getName();
	}
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}
}