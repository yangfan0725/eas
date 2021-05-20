/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.contract.client.TreeSelectDialog;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class RoomAccountReport1FilterUI extends AbstractRoomAccountReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(RoomAccountReport1FilterUI.class);
    private TreeSelectDialog dialog=new TreeSelectDialog(this,TimeAccountReportFilterUI.getSellProjectForSHESellProject(null, MoneySysTypeEnum.SalehouseSys,true));
    
    /**
     * output class constructor
     */
    public RoomAccountReport1FilterUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
		super.onLoad();
		this.pkFromDate.setValue(null);
		this.pkToDate.setValue(null);
		this.contFromDate.setVisible(true);
		this.contToDate.setVisible(true);
	}

	public RptParams getCustomCondition() {
		 RptParams pp = new RptParams();
         if(this.pkFromDate.getValue()!=null){
    		 pp.setObject("fromDate", this.pkFromDate.getValue());
         }else{
MsgBox.showInfo("请选择开始日期！");
SysUtil.abort();
        	 pp.setObject("fromDate", null);
         }
         if(this.pkToDate.getValue()!=null){
    		 pp.setObject("toDate", this.pkToDate.getValue());
         }else{
MsgBox.showInfo("请选择截止日期！");
SysUtil.abort();
        	 pp.setObject("toDate", null);
         }
         if(this.txtSellProject.getUserObject()!=null){
        	 pp.setObject("tree", this.txtSellProject.getUserObject());
         }else{
        	 pp.setObject("tree", null);
         }
         Calendar calendar = Calendar.getInstance();
		 calendar.setTime((Date) this.pkFromDate.getValue());
	     String fromYear = String.valueOf(calendar.get(Calendar.YEAR));
			
	     calendar.setTime((Date) this.pkToDate.getValue());
	     String toYear = String.valueOf(calendar.get(Calendar.YEAR));
			
	     if(!fromYear.equals(toYear)){
	    	 MsgBox.showInfo("请选择同一年份！");
	    	 SysUtil.abort();
	     }
		 return pp;
	}
	public void onInit(RptParams params) throws Exception {
		
	}
	public void setCustomCondition(RptParams params) {
		this.pkFromDate.setValue(params.getObject("fromDate"));
		this.pkToDate.setValue(params.getObject("toDate"));
		if(params.getObject("tree")!=null){
			try {
				List sellProject=new ArrayList();
				getSellProjectName(sellProject,((DefaultKingdeeTreeNode) ((TreeModel)params.getObject("tree")).getRoot()));
				String spText="";
				for(int i=0;i<sellProject.size();i++){
					spText=spText+sellProject.get(i).toString()+";";
				}
				this.txtSellProject.setText(spText);
				this.txtSellProject.setUserObject(params.getObject("tree"));
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
	}
	public void getSellProjectName(List sellProject,DefaultKingdeeTreeNode node) throws BOSException{
		if(node.getUserObject() instanceof SellProjectInfo) {
			sellProject.add(((SellProjectInfo)node.getUserObject()).getName());
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			getSellProjectName(sellProject,(DefaultKingdeeTreeNode) node.getChildAt(i));
		}
	}
	protected void btnSelect_actionPerformed(ActionEvent e) throws Exception {
		dialog.show();
		if(dialog.getSelectTree()!=null){
			List sellProject=new ArrayList();
			getSellProjectName(sellProject,(DefaultKingdeeTreeNode) dialog.getSelectTree().getRoot());
			String spText="";
			for(int i=0;i<sellProject.size();i++){
				spText=spText+sellProject.get(i).toString()+";";
			}
			this.txtSellProject.setText(spText);
			this.txtSellProject.setUserObject(dialog.getSelectTree());
		}
	}

}