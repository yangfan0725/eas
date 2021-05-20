/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.util.FdcManagementUtil;

/**
 * output class name
 */
public class MainProgressReportBaseSchTaskPropertiesNewUI extends AbstractMainProgressReportBaseSchTaskPropertiesNewUI
{
    private static final Logger logger = CoreUIObject.getLogger(MainProgressReportBaseSchTaskPropertiesNewUI.class);
    
    /**
     * output class constructor
     */
    public MainProgressReportBaseSchTaskPropertiesNewUI() throws Exception
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
    public void onShow() throws Exception {
		super.onShow();
		kDLabelContainer1.setEnabled(true);
		try {
			String	paramValue = ParamControlFactory.getRemoteInstance().getParamValue(new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId()), "YF_SINGLE");
			if("false".equals(paramValue)){
				this.actionSaveReport.setEnabled(false);
				this.kdDpActualStartDate.setEnabled(false);
				this.kdDpFinishDate.setEnabled(false);
				this.txtCompletePercent.setEnabled(false);
				this.txtWorkload.setEnabled(false);
				this.txtDesciption.setEnabled(false);
				this.schedulereportbtndel.setEnabled(false);
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
    
    
    /**
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.framework.client.CoreUI#actionExitCurrent_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception {
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		Map actionExitCurrentMap = FdcManagementUtil.recodeExeTimeBefore(this.getClass(), "actionExitCurrent_actionPerformed");
		// ////////////////////////////////////////////////////////////////////////
		
		super.actionExitCurrent_actionPerformed(e);
		
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		FdcManagementUtil.recodeExeTimeAfter(this.getClass(), "actionExitCurrent_actionPerformed", actionExitCurrentMap);
		// ////////////////////////////////////////////////////////////////////////
	}
 }