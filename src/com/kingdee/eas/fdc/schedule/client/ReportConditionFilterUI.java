/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;

/**
 * output class name
 */
public class ReportConditionFilterUI extends AbstractReportConditionFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(ReportConditionFilterUI.class);
    
    /**
     * output class constructor
     */
    public ReportConditionFilterUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	FDCClientUtils.setPersonF7(this.prmtRespPerson, this, null);
    	FDCClientUtils.setPersonF7(this.prmtQualityPerson, this, null);
    	FDCClientUtils.setPersonF7(this.prmtPlanPerson, this, null);
    	this.actionConditionQuery.setEnabled(true);
    	this.actionResetCondition.setEnabled(true);
    	
    	Map uiContext = getUIContext();
    	if(uiContext.containsKey("paramMap")){
    		Map map = (Map) uiContext.get("paramMap");
    		this.prmtPlanPerson.setData(map.get("planPerson"));
    		this.prmtRespPerson.setData(map.get("respPerson"));
    		this.prmtQualityPerson.setData(map.get("qualityPerson"));
    		this.kdpStartDate.setValue(map.get("startDate"));
    		this.kdpEndDate.setValue(map.get("endDate"));
    	}
    }


    /**
     * output actionConditionQuery_actionPerformed
     */
    public void actionConditionQuery_actionPerformed(ActionEvent e) throws Exception
    {
         //获取当前界面的参数
    	PersonInfo respPerson =(PersonInfo) prmtRespPerson.getData();
    	PersonInfo qualityPerson =(PersonInfo) prmtQualityPerson.getData();
    	PersonInfo planPerson =(PersonInfo) prmtPlanPerson.getData();
    	Date startDate = (Date) this.kdpStartDate.getValue();
    	Date endDate = (Date) this.kdpEndDate.getValue();
    	
    	Map uiContext = getUIContext();
    	if(uiContext.containsKey("parentWindow")){
    		
    		if(uiContext.get("parentWindow") instanceof ViewProjectWeekReportUI){
    			ViewProjectWeekReportUI prjWeekReportUI = (ViewProjectWeekReportUI) uiContext.get("parentWindow");
    			Map map  = prjWeekReportUI.getParamMap();
    			map.clear();
    			map.put("respPerson", respPerson);
    			map.put("qualityPerson", qualityPerson);
    			map.put("planPerson", planPerson);
    			map.put("startDate", startDate);
    			map.put("endDate", endDate);
    			prjWeekReportUI.initData();
    			this.destroyWindow();
    		}else if(uiContext.get("parentWindow") instanceof ViewProjectMonthReportUI){
    			ViewProjectMonthReportUI prjMonthReportUI = (ViewProjectMonthReportUI) uiContext.get("parentWindow");
    			Map map  = prjMonthReportUI.getParamMap();
    			map.clear();
    			map.put("respPerson", respPerson);
    			map.put("qualityPerson", qualityPerson);
    			map.put("planPerson", planPerson);
    			map.put("startDate", startDate);
    			map.put("endDate", endDate);
    			prjMonthReportUI.initData();
    			this.destroyWindow();
    		}
    		
    	}
    	
    	

    }

    /**
     * output actionResetCondition_actionPerformed
     */
    public void actionResetCondition_actionPerformed(ActionEvent e) throws Exception
    {
        this.prmtRespPerson.setData(null);
        this.prmtQualityPerson.setData(null);
        this.prmtPlanPerson.setData(null);
        this.kdpStartDate.setValue(new Date());
        this.kdpEndDate.setValue(new Date());
        Map uiContext = getUIContext();
        if(uiContext.get("parentWindow") instanceof ViewProjectWeekReportUI){
        	ViewProjectWeekReportUI prjWeekReportUI = (ViewProjectWeekReportUI) uiContext.get("parentWindow");
        	Map map  = prjWeekReportUI.getParamMap();
			map.clear();
        }
        
        
    }

}