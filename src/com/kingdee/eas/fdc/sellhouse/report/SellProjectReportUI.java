/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class SellProjectReportUI extends AbstractSellProjectReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(SellProjectReportUI.class);
    
    /**
     * output class constructor
     */
    public SellProjectReportUI() throws Exception
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

	protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return new SellProjectReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return null;
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	protected void query() {
		tblMain.checkParsed();
		getTableForPrintSetting().removeRows();
		try {
			Map map1 = new HashMap();
			RptParams pp=(RptParams) params.clone();
			pp.setObject("tree", null);
			map1.put("params", pp);
			Map mapData = SellProjectReportFacadeFactory.getRemoteInstance().getTableData(map1);
			if(mapData.get("list")!=null){
				List list = (List)mapData.get("list");
				for(int i=0;i<list.size();i++){
					IRow row = this.tblMain.addRow();
					Map map = (Map)list.get(i);
					row.getCell("sellProject").setValue(map.get("sellProject"));//��Ŀ
					row.getCell("saleBuildingArea").setValue(map.get("saleBuildingArea"));//���۽������
					row.getCell("saleRoomArea").setValue(map.get("saleRoomArea"));//�����������
					row.getCell("sellAmount").setValue(map.get("sellAmount"));//���۽��
					row.getCell("revAmount").setValue(map.get("revAmount"));//������ۼƻ������
					row.getCell("yearSalePercent").setValue("");//�ؿ���
					row.getCell("yearReturnPercent").setValue("");//��ȼƻ��ؿ������
				}
				this.tblMain.setRowCount(list.size());
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	public void tableDataRequest(KDTDataRequestEvent arg0) {
		
	}
    /* (non-Javadoc)
	 * @see com.kingdee.eas.framework.report.client.CommRptBaseUI#onLoad()
	 */
	public void onLoad() throws Exception {
		super.onLoad();
		initTable();
	}
	protected void initTable(){
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
        tblMain.getDataRequestManager().addDataRequestListener(this);
        tblMain.getDataRequestManager().setDataRequestMode(1);
        this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
        enableExportExcel(tblMain);
        //���ô�ֱ������
        getTableForPrintSetting().setScrollStateVertical(KDTStyleConstants.SCROLL_STATE_SHOW);
        //����ˮƽ������
        getTableForPrintSetting().setScrollStateHorizon(KDTStyleConstants.SCROLL_STATE_SHOW);
        
		String[] fields=new String[tblMain.getColumnCount()];
		for(int i=0;i<tblMain.getColumnCount();i++){
			fields[i]=tblMain.getColumnKey(i);
		}
		KDTableHelper.setSortedColumn(tblMain,fields);
	}


}