/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

import java.awt.Color;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.bim.project.BuildInfo;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
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
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectTypeInfo;
import com.kingdee.eas.fdc.contract.client.AccActOnLoadBgUI;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
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
public class RoomAccountReport1UI extends AbstractRoomAccountReport1UI
{
    private static final Logger logger = CoreUIObject.getLogger(RoomAccountReport1UI.class);
    private TreeModel tree;
    
    private Date fromDate = null;
    private Date toDate = null;
    private int fromMonth = 0;
    private int toMonth = 0;

	/**
     * output class constructor
     */
    public RoomAccountReport1UI() throws Exception
    {
        super();
        tblMain.checkParsed();
        tblMain.getDataRequestManager().addDataRequestListener(this);
        tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        enableExportExcel(tblMain);
    }

    @Override
	public void onShow() throws Exception {
		// TODO Auto-generated method stub
		super.onShow();
	}

	protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return new RoomAccountReport1FilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return RoomAccountReport1FacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	protected void query() {
		tblMain.removeColumns();
		tblMain.removeRows();
		sum();
		CRMClientHelper.changeTableNumberFormat(tblMain, 
			new String[]{
				"�ܻ�ֵ�������","�ܻ�ֵ�ܽ��","�ܻ�ֵ����",
				"���۷�Դ�������","���۷�Դ�ܽ��","���۷�Դ����",
				"�Ϲ�δתǩԼ�������","�Ϲ�δתǩԼ�ܽ��","�Ϲ�δתǩԼ����",
				"δ���̷�Դ�������","δ���̷�Դ�ܽ��","δ���̷�Դ����",
				"���۷�Դ�������","���۷�Դ�ܽ��","���۷�Դ����",
				"��Ԥ��Դ�������","��Ԥ��Դ�ܽ��","��Ԥ��Դ����",
				"���۷�Դ�������","���۷�Դ�ܽ��","���۷�Դ����",
				"1�½������","1���ܽ��","1�¾���",
				"2�½������","2���ܽ��","2�¾���",
				"3�½������","3���ܽ��","3�¾���",
				"4�½������","4���ܽ��","4�¾���",
				"5�½������","5���ܽ��","5�¾���",
				"6�½������","6���ܽ��","6�¾���",
				"7�½������","7���ܽ��","7�¾���",
				"8�½������","8���ܽ��","8�¾���",
				"9�½������","9���ܽ��","9�¾���",
				"10�½������","10���ܽ��","10�¾���",
				"11�½������","11���ܽ��","11�¾���",
				"12�½������","12���ܽ��","12�¾���"
			});
		tblMain.getColumn("�ܻ�ֵ����").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // �Ҷ���
		tblMain.getColumn("���۷�Դ����").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // �Ҷ���
		tblMain.getColumn("�Ϲ�δתǩԼ����").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // �Ҷ���
		tblMain.getColumn("δ���̷�Դ����").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // �Ҷ���
		tblMain.getColumn("���۷�Դ����").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // �Ҷ���
		tblMain.getColumn("��Ԥ��Դ����").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // �Ҷ���
		tblMain.getColumn("���۷�Դ����").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // �Ҷ���
		tblMain.getColumn("1������").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // �Ҷ���
		tblMain.getColumn("2������").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // �Ҷ���
		tblMain.getColumn("3������").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // �Ҷ���
		tblMain.getColumn("4������").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // �Ҷ���
		tblMain.getColumn("5������").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // �Ҷ���
		tblMain.getColumn("6������").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // �Ҷ���
		tblMain.getColumn("7������").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // �Ҷ���
		tblMain.getColumn("8������").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // �Ҷ���
		tblMain.getColumn("9������").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // �Ҷ���
		tblMain.getColumn("10������").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // �Ҷ���
		tblMain.getColumn("11������").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // �Ҷ���
		tblMain.getColumn("12������").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // �Ҷ���
		
		tblMain.getColumn("�ܻ�ֵ����").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("���۷�Դ����").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("�Ϲ�δתǩԼ����").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("δ���̷�Դ����").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("���۷�Դ����").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("��Ԥ��Դ����").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("���۷�Դ����").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("1������").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("2������").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("3������").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("4������").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("5������").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("6������").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("7������").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("8������").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("9������").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("10������").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("11������").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("12������").getStyleAttributes().setFontColor(Color.BLUE);
		
    	//�·�������ʾ
		int initSize = 35;
    	int colSize = tblMain.getColumns().size();
		int fromSize = initSize + (fromMonth)*4;
		int toSize = initSize + (toMonth+1)*4 - 1;
		for (int i=initSize;i<colSize;i++){
			if (i>=fromSize && i<=toSize){
	    		tblMain.getColumn(i).getStyleAttributes().setHided(false);
			} else{
	    		tblMain.getColumn(i).getStyleAttributes().setHided(true);
			}
		}
		
		//�ϲ���
		String[]  columns={"sellProjectId","sellProjectName","productTypeId","productTypeName","¥�����","¥������"};
		setMergeColumn(this.tblMain, columns);		

//����չʾ��ϸ		
//		tblMain.getColumn("���۷�Դ����").getStyleAttributes().setFontColor(Color.BLUE);
//		tblMain.getColumn("�Ϲ�δתǩԼ����").getStyleAttributes().setFontColor(Color.BLUE);
//		tblMain.getColumn("δ���̷�Դ����").getStyleAttributes().setFontColor(Color.BLUE);
//		tblMain.getColumn("���۷�Դ����").getStyleAttributes().setFontColor(Color.BLUE);
//		tblMain.getColumn("��Ԥ��Դ����").getStyleAttributes().setFontColor(Color.BLUE);
//		tblMain.getColumn("���۷�Դ����").getStyleAttributes().setFontColor(Color.BLUE);
	}
	
	public void addSumRow(DefaultKingdeeTreeNode node,int dep) throws EASBizException, BOSException{
		if(node!=null){
			Object obj = node.getUserObject();
			if(obj instanceof OrgStructureInfo||obj instanceof SellProjectInfo){
				IRow row=this.tblMain.addRow();
				this.tblMain.setRowCount(this.tblMain.getRowCount()+1);
				row.setTreeLevel(dep);
				row.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
				if(obj instanceof SellProjectInfo){
					String sellProjectStr=SHEManageHelper.getStringFromSet(SHEManageHelper.getAllSellProjectCollection(null,(SellProjectInfo)obj));
					if(((DefaultKingdeeTreeNode)node.getParent()).getUserObject() instanceof OrgStructureInfo){
						row.getCell("sellProjectId").setValue(sellProjectStr+"*PSP*");
					}else{
						row.getCell("sellProjectId").setValue(sellProjectStr+"*SP*");
					}
					row.getCell("sellProjectName").setValue(((SellProjectInfo)obj).getName());
				}else{
					String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node, "SellProject").keySet());
					row.getCell("sellProjectId").setValue(allSpIdStr+"*ORG*");
					row.getCell("sellProjectName").setValue(((OrgStructureInfo)obj).getUnit().getName());
				}
				++dep;
			}
			for (int i = 0; i < node.getChildCount(); i++) {
				addSumRow((DefaultKingdeeTreeNode) node.getChildAt(i),dep);
			}
		}
	}
	public IRow getParentRow(String sellProjectId){
		for(int i=0;i<this.tblMain.getRowCount();i++){
			IRow row=this.tblMain.getRow(i);
			if(row.getCell("sellProjectId").getValue()!=null&&!"".equals(row.getCell("sellProjectId").getValue().toString().trim())
					&&row.getCell("sellProjectId").getValue().toString().indexOf(",")<0&&row.getCell("sellProjectId").getValue().toString().indexOf(sellProjectId)>0){
				return row;
			}
		}
		return null;
	}
	public void sum(){
		
		for(int i=this.tblMain.getRowCount()-1;i>=0;i--){
			IRow row=this.tblMain.getRow(i);
			String id=row.getCell("sellProjectId").getValue().toString();
			if(id.indexOf("'")<0){
				for(int j=0;j<i;j++){
					IRow sumRow=this.tblMain.getRow(j);
					if(sumRow.getCell("sellProjectId").getValue().toString().indexOf(id)>0&&sumRow.getCell("sellProjectId").getValue().toString().indexOf("'")==0){
						
						SHEManageHelper.addSumColoum(sumRow,row,new String[]{
								"�ܻ�ֵ����","�ܻ�ֵ�������","�ܻ�ֵ�ܽ��","�ܻ�ֵ����",
								"���۷�Դ����","���۷�Դ�������","���۷�Դ�ܽ��","���۷�Դ����",
								"�Ϲ�δתǩԼ����","�Ϲ�δתǩԼ�������","�Ϲ�δתǩԼ�ܽ��","�Ϲ�δתǩԼ����",
								"δ���̷�Դ����","δ���̷�Դ�������","δ���̷�Դ�ܽ��","δ���̷�Դ����",
								"���۷�Դ����","���۷�Դ�������","���۷�Դ�ܽ��","���۷�Դ����",
								"��Ԥ��Դ����","��Ԥ��Դ�������","��Ԥ��Դ�ܽ��","��Ԥ��Դ����",
								"���۷�Դ����","���۷�Դ�������","���۷�Դ�ܽ��","���۷�Դ����",
								"1������","1�½������","1���ܽ��","1�¾���",
								"2������","2�½������","2���ܽ��","2�¾���",
								"3������","3�½������","3���ܽ��","3�¾���",
								"4������","4�½������","4���ܽ��","4�¾���",
								"5������","5�½������","5���ܽ��","5�¾���",
								"6������","6�½������","6���ܽ��","6�¾���",
								"7������","7�½������","7���ܽ��","7�¾���",
								"8������","8�½������","8���ܽ��","8�¾���",
								"9������","9�½������","9���ܽ��","9�¾���",
								"10������","10�½������","10���ܽ��","10�¾���",
								"11������","11�½������","11���ܽ��","11�¾���",
								"12������","12�½������","12���ܽ��","12�¾���"
						});
				    	
						sumRow.getCell("�ܻ�ֵ����").setValue(((BigDecimal)sumRow.getCell("�ܻ�ֵ�������").getValue()).compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:((BigDecimal)sumRow.getCell("�ܻ�ֵ�ܽ��").getValue()).divide((BigDecimal)sumRow.getCell("�ܻ�ֵ�������").getValue(), 2,BigDecimal.ROUND_HALF_UP));
						sumRow.getCell("���۷�Դ����").setValue(((BigDecimal)sumRow.getCell("���۷�Դ�������").getValue()).compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:((BigDecimal)sumRow.getCell("���۷�Դ�ܽ��").getValue()).divide((BigDecimal)sumRow.getCell("���۷�Դ�������").getValue(), 2,BigDecimal.ROUND_HALF_UP));
						sumRow.getCell("�Ϲ�δתǩԼ����").setValue(((BigDecimal)sumRow.getCell("�Ϲ�δתǩԼ�������").getValue()).compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:((BigDecimal)sumRow.getCell("�Ϲ�δתǩԼ�ܽ��").getValue()).divide((BigDecimal)sumRow.getCell("�Ϲ�δתǩԼ�������").getValue(), 2,BigDecimal.ROUND_HALF_UP));
						sumRow.getCell("δ���̷�Դ����").setValue(((BigDecimal)sumRow.getCell("δ���̷�Դ�������").getValue()).compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:((BigDecimal)sumRow.getCell("δ���̷�Դ�ܽ��").getValue()).divide((BigDecimal)sumRow.getCell("δ���̷�Դ�������").getValue(), 2,BigDecimal.ROUND_HALF_UP));
						sumRow.getCell("���۷�Դ����").setValue(((BigDecimal)sumRow.getCell("���۷�Դ�������").getValue()).compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:((BigDecimal)sumRow.getCell("���۷�Դ�ܽ��").getValue()).divide((BigDecimal)sumRow.getCell("���۷�Դ�������").getValue(), 2,BigDecimal.ROUND_HALF_UP));
						sumRow.getCell("��Ԥ��Դ����").setValue(((BigDecimal)sumRow.getCell("��Ԥ��Դ�������").getValue()).compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:((BigDecimal)sumRow.getCell("��Ԥ��Դ�ܽ��").getValue()).divide((BigDecimal)sumRow.getCell("��Ԥ��Դ�������").getValue(), 2,BigDecimal.ROUND_HALF_UP));
						sumRow.getCell("���۷�Դ����").setValue(((BigDecimal)sumRow.getCell("���۷�Դ�������").getValue()).compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:((BigDecimal)sumRow.getCell("���۷�Դ�ܽ��").getValue()).divide((BigDecimal)sumRow.getCell("���۷�Դ�������").getValue(), 2,BigDecimal.ROUND_HALF_UP));

						sumRow.getCell("1�¾���").setValue(((BigDecimal)sumRow.getCell("1�½������").getValue()).compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:((BigDecimal)sumRow.getCell("1���ܽ��").getValue()).divide((BigDecimal)sumRow.getCell("1�½������").getValue(), 2,BigDecimal.ROUND_HALF_UP));
						sumRow.getCell("2�¾���").setValue(((BigDecimal)sumRow.getCell("2�½������").getValue()).compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:((BigDecimal)sumRow.getCell("2���ܽ��").getValue()).divide((BigDecimal)sumRow.getCell("2�½������").getValue(), 2,BigDecimal.ROUND_HALF_UP));
						sumRow.getCell("3�¾���").setValue(((BigDecimal)sumRow.getCell("3�½������").getValue()).compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:((BigDecimal)sumRow.getCell("3���ܽ��").getValue()).divide((BigDecimal)sumRow.getCell("3�½������").getValue(), 2,BigDecimal.ROUND_HALF_UP));
						sumRow.getCell("4�¾���").setValue(((BigDecimal)sumRow.getCell("4�½������").getValue()).compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:((BigDecimal)sumRow.getCell("4���ܽ��").getValue()).divide((BigDecimal)sumRow.getCell("4�½������").getValue(), 2,BigDecimal.ROUND_HALF_UP));
						sumRow.getCell("5�¾���").setValue(((BigDecimal)sumRow.getCell("5�½������").getValue()).compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:((BigDecimal)sumRow.getCell("5���ܽ��").getValue()).divide((BigDecimal)sumRow.getCell("5�½������").getValue(), 2,BigDecimal.ROUND_HALF_UP));
						sumRow.getCell("6�¾���").setValue(((BigDecimal)sumRow.getCell("6�½������").getValue()).compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:((BigDecimal)sumRow.getCell("6���ܽ��").getValue()).divide((BigDecimal)sumRow.getCell("6�½������").getValue(), 2,BigDecimal.ROUND_HALF_UP));
						sumRow.getCell("7�¾���").setValue(((BigDecimal)sumRow.getCell("7�½������").getValue()).compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:((BigDecimal)sumRow.getCell("7���ܽ��").getValue()).divide((BigDecimal)sumRow.getCell("7�½������").getValue(), 2,BigDecimal.ROUND_HALF_UP));
						sumRow.getCell("8�¾���").setValue(((BigDecimal)sumRow.getCell("8�½������").getValue()).compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:((BigDecimal)sumRow.getCell("8���ܽ��").getValue()).divide((BigDecimal)sumRow.getCell("8�½������").getValue(), 2,BigDecimal.ROUND_HALF_UP));
						sumRow.getCell("9�¾���").setValue(((BigDecimal)sumRow.getCell("9�½������").getValue()).compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:((BigDecimal)sumRow.getCell("9���ܽ��").getValue()).divide((BigDecimal)sumRow.getCell("9�½������").getValue(), 2,BigDecimal.ROUND_HALF_UP));
						sumRow.getCell("10�¾���").setValue(((BigDecimal)sumRow.getCell("10�½������").getValue()).compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:((BigDecimal)sumRow.getCell("10���ܽ��").getValue()).divide((BigDecimal)sumRow.getCell("10�½������").getValue(), 2,BigDecimal.ROUND_HALF_UP));
						sumRow.getCell("11�¾���").setValue(((BigDecimal)sumRow.getCell("11�½������").getValue()).compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:((BigDecimal)sumRow.getCell("11���ܽ��").getValue()).divide((BigDecimal)sumRow.getCell("11�½������").getValue(), 2,BigDecimal.ROUND_HALF_UP));
						sumRow.getCell("12�¾���").setValue(((BigDecimal)sumRow.getCell("12�½������").getValue()).compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:((BigDecimal)sumRow.getCell("12���ܽ��").getValue()).divide((BigDecimal)sumRow.getCell("12�½������").getValue(), 2,BigDecimal.ROUND_HALF_UP));
					}
				}
			}
		}
	}
	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
        try {
        	try {
    			initTree();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
        	DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
        	RptParams pp=(RptParams)params.clone();
        	
        	Object objfromDate = pp.getObject("fromDate");        
        	if (objfromDate!=null){
        		fromDate = (Date)objfromDate;
        		fromMonth = fromDate.getMonth();
        	}
        	Object objtoDate = pp.getObject("toDate");        
        	if (objtoDate!=null){
        		toDate = (Date)objtoDate;
        		toMonth = toDate.getMonth();
        	}
        	
        	pp.setObject("tree", null);
        	
//        	RptParams rpt = getRemoteInstance().createTempTable(pp);
        	RptParams rpt = RoomAccountReport1FacadeFactory.getRemoteInstance().createTempTable(pp);
        	
            RptTableHeader header = (RptTableHeader)rpt.getObject("header");
            KDTableUtil.setHeader(header, tblMain);
        	if(treeNode!=null){
//rpt = getRemoteInstance().query(pp);
    	        rpt = RoomAccountReport1FacadeFactory.getRemoteInstance().query(pp);
    	        tblMain.setRowCount(rpt.getInt("count")+tblMain.getRowCount());
    	        RptRowSet rs = (RptRowSet)rpt.getObject("rowset");
    	       
    	        if(!treeNode.isLeaf()){
    	        	tblMain.getTreeColumn().setDepth(treeNode.getDepth()-1);
    		        addSumRow(treeNode,0);
    	        }
    	        tblMain.setRefresh(false);
    	        IRow row;
    	        while(rs.next()){
            		if(treeNode.isLeaf()){
    	        		IRow addRow=tblMain.addRow();
            			((KDTableInsertHandler)(new DefaultKDTableInsertHandler(rs))).setTableRowData(addRow, rs.toRowArray());
    	        	}else{
    	        		String id=rs.getString("sellProjectId");
    	        		row=getParentRow(id);
    		        	if(row!=null){
    		        		IRow addRow=tblMain.addRow(row.getRowIndex()+1);
    	        			((KDTableInsertHandler)(new DefaultKDTableInsertHandler(rs))).setTableRowData(addRow, rs.toRowArray());
            				addRow.setTreeLevel(treeNode.getDepth()-1);
    		        	}
    	        	}
    	        }
    	        tblMain.setRefresh(true);
    	        if(rs.getRowCount() > 0)
    	        	tblMain.reLayoutAndPaint();
    	        else
    	        	tblMain.repaint();
        	}else{
                tblMain.setRowCount(0);
        	}
        } catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		//�ϲ�
		String[]  columns={"sellProjectId","sellProjectName","productTypeId","productTypeName","¥�����","¥������"};
		setMergeColumn(this.tblMain, columns);
	}
	public void onLoad() throws Exception {
		setShowDialogOnLoad(true);
		tblMain.getStyleAttributes().setLocked(true);
		super.onLoad();
		tree=(TreeModel) params.getObject("tree");
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		
		//�ϲ�
		String[]  columns={"sellProjectId","sellProjectName","productTypeId","productTypeName","¥�����","¥������"};
		setMergeColumn(this.tblMain, columns);
	}

	public static void setMergeColumn(KDTable table, String columns[])
	{
		table.checkParsed();
		//table.getMergeManager().setMergeMode(KDTMergeManager.FREE_ROW_MERGE);
		table.gerGroupManager().setGroup(true);
		table.getMergeManager().setMergeMode(KDTMergeManager.GROUP_MERGE);
		table.getGroupManager().group();
		for(int i = 0; i < columns.length; i++)
		{
			table.getColumn(columns[i]).setGroup(true);
			table.getColumn(columns[i]).setMergeable(true);
		}

	}
	
	protected void initTree() throws Exception{
		if((tree==null&&params.getObject("tree")!=null)||(tree!=null&&params.getObject("tree")==null)||(tree!=null&&params.getObject("tree")!=null&&!tree.equals(params.getObject("tree")))||(tree==null&&params.getObject("tree")==null&&!this.isShowing())){
			if(params.getObject("tree")!=null){
				tree=(TreeModel) params.getObject("tree");
				this.treeMain.setModel((TreeModel) params.getObject("tree"));
			}else{
				this.treeMain.setModel(TimeAccountReportFilterUI.getSellProjectForSHESellProject(actionOnLoad, MoneySysTypeEnum.SalehouseSys,true));
			}
		    this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		    SHEManageHelper.getProductTypeNodes(this.treeMain,(DefaultKingdeeTreeNode) this.treeMain.getModel().getRoot());
		}
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
				params.setObject("productType", null);
			}else if(obj instanceof ProductTypeInfo){
				params.setObject("sellProject", ((DefaultKingdeeTreeNode)treeNode.getParent()).getUserObject());
				params.setObject("productType", treeNode.getUserObject());
			}
			query();
		}
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if(e.getType() == 1 && e.getButton() == 1 && e.getClickCount() == 2)
        {
//			int rowIndex = e.getRowIndex();//������0
//			int colIndex = e.getColIndex();//1������=35��12������=79
//			int tmpMonth = (colIndex-35) / 4 + 1;
			String begDate = "";
			String endDate = "";			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fromDate);
			String tmpYear = String.valueOf(calendar.get(Calendar.YEAR));
			
			IRow row = tblMain.getRow(e.getRowIndex());
			BigDecimal amount = (BigDecimal)row.getCell(e.getColIndex()).getValue();
			if(amount == null || !(amount instanceof BigDecimal) || amount.compareTo(FDCHelper.ZERO) <= 0)
				return;

			String sellState=null;
			if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("�ܻ�ֵ����")){
				sellState="('Sign','Purchase','Init','Onshow','KeepDown')";
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("���۷�Դ����")){
				sellState="('Sign')";
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("�Ϲ�δתǩԼ����")){
				sellState="('Purchase')";
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("δ���̷�Դ����")){
				sellState="('Init')";
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("���۷�Դ����")){
				sellState="('Onshow','KeepDown')";
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("��Ԥ��Դ����")){
				sellState="('KeepDown')";
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("���۷�Դ����")){
				sellState="('Onshow')";
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("1������")){
				sellState="('Sign')";
				begDate = tmpYear+"-01-01 00:00:00";
				endDate = tmpYear+"-01-31 23:59:59";
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("2������")){
				sellState="('Sign')";
				begDate = tmpYear+"-02-01 00:00:00";
				endDate = tmpYear+"-02-28 23:59:59";
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("3������")){
				sellState="('Sign')";
				begDate = tmpYear+"-03-01 00:00:00";
				endDate = tmpYear+"-03-31 23:59:59";
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("4������")){
				sellState="('Sign')";
				begDate = tmpYear+"-04-01 00:00:00";
				endDate = tmpYear+"-04-30 23:59:59";
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("5������")){
				sellState="('Sign')";
				begDate = tmpYear+"-05-01 00:00:00";
				endDate = tmpYear+"-05-31 23:59:59";
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("6������")){
				sellState="('Sign')";
				begDate = tmpYear+"-06-01 00:00:00";
				endDate = tmpYear+"-06-30 23:59:59";
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("7������")){
				sellState="('Sign')";
				begDate = tmpYear+"-07-01 00:00:00";
				endDate = tmpYear+"-07-31 23:59:59";
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("8������")){
				sellState="('Sign')";
				begDate = tmpYear+"-08-01 00:00:00";
				endDate = tmpYear+"-08-31 23:59:59";
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("9������")){
				sellState="('Sign')";
				begDate = tmpYear+"-09-01 00:00:00";
				endDate = tmpYear+"-09-30 23:59:59";
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("10������")){
				sellState="('Sign')";
				begDate = tmpYear+"-10-01 00:00:00";
				endDate = tmpYear+"-10-31 23:59:59";
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("11������")){
				sellState="('Sign')";
				begDate = tmpYear+"-11-01 00:00:00";
				endDate = tmpYear+"-11-30 23:59:59";
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("12������")){
				sellState="('Sign')";
				begDate = tmpYear+"-12-01 00:00:00";
				endDate = tmpYear+"-12-31 23:59:59";
			}else{
				return;
			}
			 UIContext uiContext = new UIContext(this);
			 uiContext.put("Owner", this);
			 RptParams param = new RptParams();
			 param.setObject("sellState", sellState);
			 if(row.getCell("productTypeId").getValue()!=null){
				 Object[] pr=new Object[1];
				 ProductTypeInfo pt=new ProductTypeInfo();
				 pt.setId(BOSUuid.read(row.getCell("productTypeId").getValue().toString()));
				 pr[0]=pt;
				 param.setObject("productType", pr);
			 }
			 String sp=(String)row.getCell("sellProjectId").getValue();
			 if(sp!=null){
				 String sellProjectId=getSellProjectId(sp);
				 param.setObject("sellProject", sellProjectId);
			 }
			 if(row.getCell("buildId").getValue()!=null){
				 BuildingInfo pt=new BuildingInfo();
				 pt.setId(BOSUuid.read(row.getCell("buildId").getValue().toString()));
				 param.setObject("building", pt);
			 }
			 if (begDate.length()>0){
				 param.setObject("begDate", begDate);
			 }
			 if (endDate.length()>0){
				 param.setObject("endDate", endDate);
			 }
//			 if(row.getCell("productTypeId").getValue()!=null){
//				 Object[] pr=new Object[1];
//				 ProductTypeInfo pt=new ProductTypeInfo();
//				 pt.setId(BOSUuid.read(row.getCell("productTypeId").getValue().toString()));
//				 pr[0]=pt;
//				 param.setObject("productType", pr);
//			 }
			 
			 uiContext.put("RPTFilter", param);
			 uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(RoomSourceReportUI.class.getName(), uiContext, null, OprtState.VIEW);
			 uiWindow.show();
        }
	}
	 IUIWindow uiWindow;
	 protected String getSellProjectId(String id){
			if(id.indexOf("*PSP*")>0){
				return id.replace("*PSP*", "");
			}else if(id.indexOf("*SP*")>0){
				return id.replace("*SP*", "");
			}else if(id.indexOf("*ORG*")>0){
				return id.replace("*ORG*", "");
			}else{
				return "'"+id+"'";
			}
		}
}