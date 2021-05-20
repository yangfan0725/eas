/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

import java.awt.Color;
import java.awt.event.*;
import java.util.Date;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.contract.client.ClientHelper;
import com.kingdee.eas.fdc.sellhouse.CommissionSettlementBillCollection;
import com.kingdee.eas.fdc.sellhouse.CommissionSettlementBillFactory;
import com.kingdee.eas.fdc.sellhouse.CommissionSettlementBillInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommissionSettlementEditUI;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.DefaultKDTableInsertHandler;
import com.kingdee.eas.framework.report.util.KDTableInsertHandler;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableHeader;

/**
 * output class name
 */
public class CommissionReportUI extends AbstractCommissionReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(CommissionReportUI.class);
    
    /**
     * output class constructor
     */
    public CommissionReportUI() throws Exception
    {
        super();
        tblMain.setEditable(false);
        tblMain.checkParsed();
        tblMain.getDataRequestManager().addDataRequestListener(this);
        tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        enableExportExcel(tblMain);
        
        this.treeMain.setModel(TimeAccountReportFilterUI.getSellProjectForSHESellProject(actionOnLoad, MoneySysTypeEnum.SalehouseSys,true));
 		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
    }
    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
    	DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(treeNode!=null){
			Object obj = treeNode.getUserObject();
			if (obj instanceof OrgStructureInfo) {
				params.setObject("sellProject", null);
				String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(treeNode, "SellProject").keySet());
				if(allSpIdStr.trim().length()!=0){
					params.setObject("org", allSpIdStr);
				}else{
					params.setObject("org", null);
				}
			}else if(obj instanceof SellProjectInfo){
				params.setObject("sellProject", treeNode.getUserObject());
			}
			query();
		}
	}
    protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return new CommissionReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return CommissionReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}
	protected void query() {
		tblMain.removeColumns();
		tblMain.removeRows();
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		for(int i=0;i<tblMain.getColumnCount();i++){
			if(!tblMain.getColumnKey(i).equals("person")&&tblMain.getColumnKey(i).indexOf("position")<0){
				tblMain.getColumn(i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				CRMClientHelper.changeTableNumberFormat(tblMain,tblMain.getColumnKey(i));
				ClientHelper.getFootRow(tblMain,new String []{tblMain.getColumnKey(i)});
				if(i>4&&treeNode!=null&&treeNode.isLeaf()){
					tblMain.getColumn(i).getStyleAttributes().setFontColor(Color.BLUE);
				}
			}
		}
	}
	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
       try {
        	RptParams pp=(RptParams)params.clone();
        	RptParams rpt = getRemoteInstance().createTempTable(pp);
            RptTableHeader header = (RptTableHeader)rpt.getObject("header");
            KDTableUtil.setHeader(header, tblMain);
            rpt=getRemoteInstance().query(pp);
	        tblMain.setRowCount(rpt.getInt("count"));
	        RptRowSet rs = (RptRowSet)rpt.getObject("rowset");
	        KDTableUtil.insertRows(rs, 0, tblMain);
        } catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if(e.getType() == 1 && e.getButton() == 1 && e.getClickCount() == 2){
			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
			if(treeNode!=null){
				Object obj = treeNode.getUserObject();
				if(obj instanceof SellProjectInfo && treeNode.isLeaf()){
					String key=tblMain.getHeadRow(0).getCell(e.getColIndex()).getValue().toString();
					if(key.indexOf("ÔÂ")>0){
						SellProjectInfo sp=(SellProjectInfo) treeNode.getUserObject();
						CommissionSettlementBillCollection info=CommissionSettlementBillFactory.getRemoteInstance().getCommissionSettlementBillCollection("select id from where state='4AUDITTED' and sellProject.id='"+sp.getId().toString()+"' and year="+params.getInt("year")+" and month="+key.split("ÔÂ")[0]);
						if(info.size()>0){
							UIContext uiContext = new UIContext(this);
							uiContext.put("Owner", this);
							uiContext.put("ID", info.get(0).getId());
							IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(CommissionSettlementEditUI.class.getName(), uiContext, null, OprtState.VIEW);
							uiWindow.show();
						}
					}
				}
			}
        }
	}
}