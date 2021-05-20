/**
 * output package name
 */
package com.kingdee.eas.fdc.market.report;

import java.awt.event.*;
import java.util.ArrayList;
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

/**
 * output class name
 */
public class MediaCostAssessmentReportFilterUI extends AbstractMediaCostAssessmentReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(MediaCostAssessmentReportFilterUI.class);
    
    public MediaCostAssessmentReportFilterUI() throws Exception
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

	public RptParams getCustomCondition() {
//		RptParams rp = new RptParams();
//		if(this.txtSellProject.getUserObject()!=null){
//			rp.setObject("tree", this.txtSellProject.getUserObject());
//        }else{
//        	rp.setObject("tree", null);
//        }
//		return rp;
		RptParams pp = new RptParams();
        if(this.startDate.getValue()!=null){
   		 pp.setObject("beginDate", this.startDate.getValue());
        }else{
       	 pp.setObject("beginDate", null);
        }
        if(this.endDate.getValue()!=null){
   		 pp.setObject("endDate", this.endDate.getValue());
        }else{
       	 pp.setObject("endDate", null);
        }
		 return pp;
	}

	public void setCustomCondition(RptParams params) {
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
		TreeSelectDialog dialog=new TreeSelectDialog(this,FDCTreeHelper.getSellProjectForSHESellProject(null, MoneySysTypeEnum.SalehouseSys));
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

	public void onInit(RptParams rptparams) throws Exception {
		// TODO Auto-generated method stub
		
	}
}