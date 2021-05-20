/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.finance.FDCCostCloseSortEnum;

/**
 * output class name
 */
public class FDCFinanceCostCloseRptUI extends AbstractFDCFinanceCostCloseRptUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCFinanceCostCloseRptUI.class);
    
    /**
     * output class constructor
     */
    public FDCFinanceCostCloseRptUI() throws Exception
    {
        super();
    }
    
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }
    
    public void onLoad() throws Exception {
    	isCost=false;
    	super.onLoad();
    }
    
    protected void loadRow(IRow row) {
    	Object[] statictisics = getStatisticsData(row);
		if (statictisics == null)
			return;
		if (isClose()) {
			if(getItem(row).toString().startsWith("invalid")){
				row.getCell("thisCount").setValue(statictisics[1]);
				return;
			}
			row.getCell("itemCount").setValue(statictisics[0]);
			row.getCell("thisCount").setValue(statictisics[1]);
			row.getCell("allSplit").setValue(statictisics[2]);
		} else {
			if(getItem(row).toString().startsWith("invalid")){
				row.getCell("thisCount").setValue(statictisics[1]);
				return;
			}
			row.getCell("save").setValue(statictisics[0]);
			row.getCell("submit").setValue(statictisics[1]);
			row.getCell("audit").setValue(statictisics[2]);
			row.getCell("noSplit").setValue(statictisics[3]);
			row.getCell("partSplit").setValue(statictisics[4]);
			row.getCell("allSplit").setValue(statictisics[5]);
		}
    }
    
    private void printMothed(ActionEvent e, boolean b) throws Exception {
		
		IRow row = tblMain.getHeadRow(0);// ��ñ�ͷ�ĵ�һ��
		int headColCount = tblMain.getColumnCount() - 1;// ȡ����������Ҫ��ȥid��
		row.setHeight(19);// �����и� ���õ�һ���пɼ�
		// �õ���һ����Ԫ�� ��������
		ICell cell = row.getCell(0);
		cell.setValue("����ͳ��");
		// �õ���2����Ԫ�� ��������
		ICell cell1 = row.getCell(1);
		String str = "";
		if (comboCostCloseSort.getSelectedIndex() == 0) {
			str = FDCCostCloseSortEnum.COSTCLOSERPT.getAlias();
		} else {
			str = FDCCostCloseSortEnum.COSTUNCLOSERPT.getAlias();
		}
		cell1.setValue(str);
		row.setCell(1, cell1);
		// �õ���3����Ԫ�� ��������
		ICell cell2 = row.getCell(2);
		cell2.setValue("�¶�");
		row.setCell(2, cell2);
		// �õ���4����Ԫ�� ��������
		String year = spiYear.getValue().toString();
		String month = spiMonth.getValue().toString();
		String monthDate = year + "\u5e74" + month +"\u6708" ;
		ICell cell3 = row.getCell(3);
		cell3.setValue(monthDate);
		row.setCell(3, cell3);
		// �ϲ���Ԫ��
		KDTMergeManager mm = tblMain.getHeadMergeManager();
		mm.mergeBlock(0, 4, 0, (headColCount - 1),KDTMergeManager.SPECIFY_MERGE);
		// �鿴�ǲ��������ݣ����û�����ݣ�ϵͳ����ʾ����ӡ�����ʱ����Ҫ����ͷ�ĸ߶�����Ϊ0�����û�������
		if (tblMain.getBody().size() == 0) {
			row.setHeight(0);
		}
		if (b) {// ��ӡ Ԥ��
			super.actionPrintPreview_actionPerformed(e);
		} else {// ��ӡ
			super.actionPrint_actionPerformed(e);
		}
		// �رմ�ӡԤ���Ժ󣬽���ͷ��һ�еĸ߶����ó�0 �����û�����
		tblMain.getHeadRow(0).setHeight(0);
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		this.printMothed(e, true);
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		this.printMothed(e, false);
	}  

}