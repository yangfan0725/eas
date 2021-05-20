/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;

import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class TenancyRevenueFilterUI extends AbstractTenancyRevenueFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(TenancyRevenueFilterUI.class);
    
    /**
     * output class constructor
     */
    public TenancyRevenueFilterUI() throws Exception
    {
        super();
    }
    @Override
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	super.onLoad();
    	this.chkExecuting.setSelected(true);
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	@Override
	public RptParams getCustomCondition() {
		// TODO Auto-generated method stub
		 RptParams pp = new RptParams();
		 if(prmtTenancybill.getValue() != null){
			 pp.setObject("tenancy", prmtTenancybill.getValue());
		 }else{
			 pp.setObject("tenancy", null);
		 }
		 pp.setBoolean("isAll", chkAll.isSelected());
		 pp.setBoolean("isExecuting", chkExecuting.isSelected());
		 pp.setBoolean("isQuitTenancy", chkQuitTenancy.isSelected());
		 pp.setBoolean("isExpiration", chkExpiration.isSelected());
		return pp;
	}

	@Override
	public void onInit(RptParams rptparams) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCustomCondition(RptParams param) {
		// TODO Auto-generated method stub
		this.prmtTenancybill.setValue(param.getObject("tenancy"));
		boolean selected = param.getBoolean("isAll");
		this.chkAll.setSelected(selected);
		
		selected = param.getBoolean("isExecuting");
		this.chkExecuting.setSelected(selected);
		
		selected = param.getBoolean("isQuitTenancy");
		this.chkQuitTenancy.setSelected(selected);
		
		selected = param.getBoolean("isExpiration");
		this.chkExpiration.setSelected(selected);
		
	}
	
	@Override
	protected void chkAll_stateChanged(ChangeEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.chkAll_stateChanged(e);
		if(chkAll.isSelected()){
			this.chkExecuting.setSelected(true);
			this.chkExpiration.setSelected(true);
			this.chkQuitTenancy.setSelected(true);
		}
		if(!chkAll.isSelected()){
			this.chkExecuting.setSelected(true);
			this.chkExpiration.setSelected(false);
			this.chkQuitTenancy.setSelected(false);
		}
	}
	
	@Override
	protected void chkQuitTenancy_stateChanged(ChangeEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.chkQuitTenancy_stateChanged(e);
		if(!chkQuitTenancy.isSelected()){
			if(chkAll.isSelected()){
				chkAll.setSelected(false);
			}
			if(!chkExecuting.isSelected()&&!chkExpiration.isSelected()){
				this.chkExecuting.setSelected(true);
			}
		}
		
		if(chkQuitTenancy.isSelected()){
			if(chkExecuting.isSelected()&&chkExpiration.isSelected()){
				this.chkAll.setSelected(true);
			}
		}
	}
	
	@Override
	protected void chkExecuting_stateChanged(ChangeEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.chkExecuting_stateChanged(e);
		if(!chkExecuting.isSelected()){
			if(chkAll.isSelected()){
				chkAll.setSelected(false);
			}
			if(!chkQuitTenancy.isSelected()&&!chkExpiration.isSelected()){
				this.chkExecuting.setSelected(true);
			}
		}
		if(chkExecuting.isSelected()){
			if(chkQuitTenancy.isSelected()&&chkExpiration.isSelected()){
				this.chkAll.setSelected(true);
			}
		}
	}
	
	@Override
	protected void chkExpiration_stateChanged(ChangeEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.chkExpiration_stateChanged(e);
		if(!chkExpiration.isSelected()){
			if(chkAll.isSelected()){
				chkAll.setSelected(false);
			}
			if(!chkExecuting.isSelected()&&!chkQuitTenancy.isSelected()){
				this.chkExecuting.setSelected(true);
			}
		}
		if(chkExpiration.isSelected()){
			if(chkExecuting.isSelected()&&chkQuitTenancy.isSelected()){
				this.chkAll.setSelected(true);
			}
		}
	}

}