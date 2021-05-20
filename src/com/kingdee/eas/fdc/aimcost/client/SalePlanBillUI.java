/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.io.kds.KDSBookToBook;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.ctrl.kdf.export.ExportManager;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBook;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBookVO;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.read.POIXlsReader;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMenuManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigInfo;
import com.kingdee.eas.fdc.aimcost.CostIndexEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class SalePlanBillUI extends AbstractSalePlanBillUI
{
    private static final Logger logger = CoreUIObject.getLogger(SalePlanBillUI.class);
    private  KDTable table=null;
    public SalePlanBillUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		super.onLoad();
		KDWorkButton btnImportExcel = new KDWorkButton();
		KDWorkButton btnExportExcel = new KDWorkButton();

		
		this.actionExcelImport.putValue("SmallIcon", EASResource.getIcon("imgTbtn_input"));
		btnImportExcel = (KDWorkButton)contEntry.add(this.actionExcelImport);
		btnImportExcel.setText("����");
		btnImportExcel.setSize(new Dimension(140, 19));
		
		this.actionExcelExport.putValue("SmallIcon", EASResource.getIcon("imgTbtn_output"));
		btnExportExcel = (KDWorkButton)contEntry.add(this.actionExcelExport);
		btnExportExcel.setText("����");
		btnExportExcel.setSize(new Dimension(140, 19));
	 }
    public void actionExcelExport_actionPerformed(ActionEvent e)throws Exception {
    	ExportManager exportM = new ExportManager();
        String path = null;
        File tempFile = File.createTempFile("eastemp",".xls");
        path = tempFile.getCanonicalPath();

        KDTables2KDSBookVO[] tablesVO = new KDTables2KDSBookVO[1];
        tablesVO[0] = new KDTables2KDSBookVO(table);
		tablesVO[0].setTableName("��Ӧ�ƻ�");
		
        KDSBook book = null;
        book = KDTables2KDSBook.getInstance().exportKDTablesToKDSBook(tablesVO,true,true);
        exportM.exportToExcel(book, path);
        
		KDFileChooser fileChooser = new KDFileChooser();
		fileChooser.setFileSelectionMode(0);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setSelectedFile(new File("��Ӧ�ƻ�.xls"));
		int result = fileChooser.showSaveDialog(this);
		if (result == KDFileChooser.APPROVE_OPTION){
			File dest = fileChooser.getSelectedFile();
			try{
				File src = new File(path);
				if (dest.exists())
					dest.delete();
				src.renameTo(dest);
				FDCMsgBox.showInfo("�����ɹ���");
				KDTMenuManager.openFileInExcel(dest.getAbsolutePath());
			}
			catch (Exception e3)
			{
				handUIException(e3);
			}
		}
		tempFile.delete();
    }
    String path=null;
    public void actionExcelImport_actionPerformed(ActionEvent e)throws Exception {
    	path = SHEHelper.showExcelSelectDlg(this);
		if (path == null) {
			return;
		}
		Window win = SwingUtilities.getWindowAncestor(this);
        LongTimeDialog dialog = null;
        if(win instanceof Frame){
        	dialog = new LongTimeDialog((Frame)win);
        }else if(win instanceof Dialog){
        	dialog = new LongTimeDialog((Dialog)win);
        }
        if(dialog==null){
        	dialog = new LongTimeDialog(new Frame());
        }
        dialog.setLongTimeTask(new ILongTimeTask() {
			public void afterExec(Object arg0) throws Exception {
				Boolean bol=(Boolean)arg0;
				if(bol){
					FDCMsgBox.showInfo("����ɹ���");
				}
			}
			public Object exec() throws Exception {
				boolean bol=importExcelToTable(path);
				return bol;
			}
    	}
	    );
	    dialog.show();
	}
	private boolean importExcelToTable(String fileName) throws Exception {
		KDSBook kdsbook = null;
		try {
			kdsbook = POIXlsReader.parse2(fileName);
		} catch (Exception e) {
			e.printStackTrace();
			FDCMsgBox.showWarning(this,"��EXCEL����,EXCEl��ʽ��ƥ�䣡");
			return false;
		}
		if (kdsbook == null) {
			return false;
		}
		for(int i=0;i<KDSBookToBook.traslate(kdsbook).getSheetCount();i++){
			Sheet excelSheet = KDSBookToBook.traslate(kdsbook).getSheet(i);
			if(excelSheet.getSheetName().indexOf("¥�������ƻ���")>=0){
            	Map e_colNameMap = new HashMap();
        		int e_maxRow = excelSheet.getMaxRowIndex();
        		int e_maxColumn = excelSheet.getMaxColIndex();
        		table=new KDTable();
        		table.checkParsed();
        		table.getStyleAttributes().setLocked(true);
        		IRow headRow=table.addHeadRow();
        		IRow headRowTwo=table.addHeadRow();
        		IRow headRowThree=table.addHeadRow();
        		for (int col = 0; col <= e_maxColumn; col++) {
        			String excelColName = excelSheet.getCell(3, col, true).getText();
        			if(excelColName!=null&&!excelColName.trim().equals("")){
        				if(excelColName.indexOf("����")>=0){
        					e_colNameMap.put(excelColName, new Integer(col));
            				IColumn column=table.addColumn();
            				column.setKey(excelColName);
            				headRow.getCell(excelColName).setValue(excelColName);
            				headRowTwo.getCell(excelColName).setValue(excelColName);
            				headRowThree.getCell(excelColName).setValue(excelColName);
            				table.getHeadMergeManager().mergeBlock(0, column.getColumnIndex(), 2, column.getColumnIndex());
        				}else if(excelColName.indexOf("��Ŀ")>=0){
        					e_colNameMap.put(excelColName, new Integer(col));
            				IColumn column=table.addColumn();
            				column.setKey(excelColName);
            				headRow.getCell(excelColName).setValue(excelColName);
            				headRowTwo.getCell(excelColName).setValue(excelColName);
            				headRowThree.getCell(excelColName).setValue(excelColName);
            				table.getHeadMergeManager().mergeBlock(0, column.getColumnIndex(), 2, column.getColumnIndex());
        				}else if(excelColName.indexOf("��Ʒ����")>=0){
        					e_colNameMap.put("ҵ̬", new Integer(col));
            				IColumn column=table.addColumn();
            				column.setKey("ҵ̬");
            				headRow.getCell("ҵ̬").setValue("ҵ̬");
            				headRowTwo.getCell("ҵ̬").setValue("ҵ̬");
            				headRowThree.getCell("ҵ̬").setValue("ҵ̬");
            				table.getHeadMergeManager().mergeBlock(0, column.getColumnIndex(), 2, column.getColumnIndex());
        				}else if(excelColName.indexOf("�������")>=0){
        					e_colNameMap.put("�������", new Integer(col));
            				IColumn column=table.addColumn();
            				column.setKey("�������");
            				headRow.getCell("�������").setValue("�������");
            				headRowTwo.getCell("�������").setValue("�������");
            				headRowThree.getCell("�������").setValue("�������");
            				table.getHeadMergeManager().mergeBlock(0, column.getColumnIndex(), 2, column.getColumnIndex());
        				}else if(excelColName.indexOf("���ۻ�ֵ")>=0){
        					e_colNameMap.put("�ܻ�ֵ", new Integer(col));
            				IColumn column=table.addColumn();
            				column.setKey("�ܻ�ֵ");
            				headRow.getCell("�ܻ�ֵ").setValue("�ܻ�ֵ");
            				headRowTwo.getCell("�ܻ�ֵ").setValue("�ܻ�ֵ");
            				headRowThree.getCell("�ܻ�ֵ").setValue("�ܻ�ֵ");
            				table.getHeadMergeManager().mergeBlock(0, column.getColumnIndex(), 2, column.getColumnIndex());
            				column.getStyleAttributes().setNumberFormat("#0.00");
        				}else if(excelColName.indexOf("Ԥ��֤��ȡʱ��")>=0){
        					e_colNameMap.put("Ԥ��֤��ȡʱ��", new Integer(col));
        				}else if(excelColName.indexOf("���ۻ�ֵ")>=0){
        					e_colNameMap.put("���ۻ�ֵ", new Integer(col));
        				}
        			}
        		}
//        		e_colNameMap.put("δ����Ӧ", new Integer(table.getColumnCount()-1));
        		
        		IColumn kccolumn=table.addColumn();
        		kccolumn.setKey("���");
				headRow.getCell("���").setValue("���");
				headRowTwo.getCell("���").setValue("���");
				headRowThree.getCell("���").setValue("���");
				table.getHeadMergeManager().mergeBlock(0, kccolumn.getColumnIndex(), 2, kccolumn.getColumnIndex());
				kccolumn.getStyleAttributes().setNumberFormat("#0.00");
				
				IColumn addcolumn=table.addColumn();
				addcolumn.setKey("δ����Ӧ");
				headRow.getCell("δ����Ӧ").setValue("δ����Ӧ");
				headRowTwo.getCell("δ����Ӧ").setValue("δ����Ӧ");
				headRowThree.getCell("δ����Ӧ").setValue("δ����Ӧ");
				table.getHeadMergeManager().mergeBlock(0, addcolumn.getColumnIndex(), 2, addcolumn.getColumnIndex());
				addcolumn.getStyleAttributes().setNumberFormat("#0.00");
				
        		Date now=FDCCommonServerHelper.getServerTimeStamp();
        		Calendar cal = Calendar.getInstance();
    			cal.setTime(now);
    			int year=cal.get(Calendar.YEAR);
    			int month=cal.get(Calendar.MONTH)+1;
    			String nowkey=year+"year"+month+"m";
    			int comYear=year+3;
    			
    			while(true){
    				if(year>comYear){
    					break;
    				}
    				String key=year+"year"+month+"m";
    				IColumn column=table.addColumn();
    				column.setKey(key+"area");
    				headRow.getCell(key+"area").setValue("��Ӧ�ƻ�");
    				headRowTwo.getCell(key+"area").setValue(year+"��"+month+"��");
    				headRowThree.getCell(key+"area").setValue("���");
    				
    				column=table.addColumn();
    				column.setKey(key+"amount");
    				headRow.getCell(key+"amount").setValue("��Ӧ�ƻ�");
    				headRowTwo.getCell(key+"amount").setValue(year+"��"+month+"��");
    				headRowThree.getCell(key+"amount").setValue("��ֵ");
    				
    				column.getStyleAttributes().setNumberFormat("#0.00");
    				++month;
    				if (month > 12) {
    					year += 1;
    					month = 1;
    				}
    				table.getHeadMergeManager().mergeBlock(1, table.getHeadRow(0).getCell(key+"area").getColumnIndex(), 1, table.getHeadRow(0).getCell(key+"amount").getColumnIndex());
    			}
    			int merge=table.getHeadRow(0).getCell(nowkey+"area").getColumnIndex();
    			table.getHeadMergeManager().mergeBlock(0, merge, 0, table.getColumnCount()-1);
    			
    			Map date=new HashMap();
    			String keyProject="";
    			for (int rowIndex = 4; rowIndex <= e_maxRow; rowIndex++) {
    				Integer colInt = (Integer) e_colNameMap.get("����");
    				Variant cellRawVal = excelSheet.getCell(rowIndex, colInt.intValue(), true).getValue();
    				String city="";
    				if(cellRawVal.getValue()==null||cellRawVal.getValue().toString().trim().equals("")){
    					int curIndex=rowIndex;
    					while(true){
    						curIndex--;
    						if(excelSheet.getCell(curIndex, colInt.intValue(), true).getValue()!=null
    								&&!excelSheet.getCell(curIndex, colInt.intValue(), true).getValue().toString().trim().equals("")){
    							city=excelSheet.getCell(curIndex, colInt.intValue(), true).getValue().toString();
    							break;
    						}
    					}
    				}else{
    					city=cellRawVal.getValue().toString();
    				}
    				
    				colInt = (Integer) e_colNameMap.get("��Ŀ");
    				cellRawVal = excelSheet.getCell(rowIndex, colInt.intValue(), true).getValue();
    				String curProject="";
    				if(cellRawVal.getValue()==null||cellRawVal.getValue().toString().trim().equals("")){
    					int curIndex=rowIndex;
    					while(true){
    						curIndex--;
    						if(excelSheet.getCell(curIndex, colInt.intValue(), true).getValue()!=null
    								&&!excelSheet.getCell(curIndex, colInt.intValue(), true).getValue().toString().trim().equals("")){
    							curProject=excelSheet.getCell(curIndex, colInt.intValue(), true).getValue().toString();
    							break;
    						}
    					}
    				}else{
    					curProject=cellRawVal.getValue().toString();
    				}
    				colInt = (Integer) e_colNameMap.get("ҵ̬");
    				cellRawVal = excelSheet.getCell(rowIndex, colInt.intValue(), true).getValue();
    				if(cellRawVal.getValue()==null) continue;
    				String productType=cellRawVal.getValue().toString();
    				
    				if(keyProject.equals("")){
    					keyProject=curProject;
    				}else{
    					if(!keyProject.equals(curProject)){
        					IRow totalRow=table.addRow();
        					totalRow.getCell("��Ŀ").setValue("С��");
        					totalRow.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
        					for(int j=totalRow.getRowIndex()-1;j>=0;j--){
        						if(table.getRow(j).getCell("��Ŀ")==null) continue;
        						String comKey=table.getRow(j).getCell("��Ŀ").getValue().toString();
        						if(comKey.equals(keyProject)){
        							totalRow.getCell("����").setValue(table.getRow(j).getCell("����").getValue());
        							totalRow.getCell("�ܻ�ֵ").setValue(FDCHelper.add(table.getRow(j).getCell("�ܻ�ֵ").getValue(), totalRow.getCell("�ܻ�ֵ").getValue()));
        							totalRow.getCell("���").setValue(FDCHelper.add(table.getRow(j).getCell("���").getValue(), totalRow.getCell("���").getValue()));
        							totalRow.getCell("δ����Ӧ").setValue(FDCHelper.add(table.getRow(j).getCell("δ����Ӧ").getValue(), totalRow.getCell("δ����Ӧ").getValue()));
        							totalRow.getCell("�������").setValue(FDCHelper.add(table.getRow(j).getCell("�������").getValue(), totalRow.getCell("�������").getValue()));
        						}else{
        							break;
        						}
        					}
        					keyProject=curProject;
        				}
    				}
    				
    				IRow row=null;
    				if(date.containsKey(curProject+productType)){
    					row=(IRow) date.get(curProject+productType);
    					
    					Integer areaIndex = (Integer) e_colNameMap.get("�������");
    					Variant areavalue = excelSheet.getCell(rowIndex, areaIndex.intValue(), true).getValue();
    					row.getCell("�������").setValue(FDCHelper.add(areavalue.getValue(), row.getCell("�������").getValue()));
    					
    					Integer totalIndex = (Integer) e_colNameMap.get("�ܻ�ֵ");
    					Variant value = excelSheet.getCell(rowIndex, totalIndex.intValue(), true).getValue();
    					row.getCell("�ܻ�ֵ").setValue(FDCHelper.add(value.getValue(), row.getCell("�ܻ�ֵ").getValue()));
    					
    					Integer sellIndex = (Integer) e_colNameMap.get("���ۻ�ֵ");
    					Variant sellvalue = excelSheet.getCell(rowIndex, sellIndex.intValue(), true).getValue();
    					row.getCell("���").setValue(FDCHelper.add(row.getCell("���").getValue(), FDCHelper.subtract(value.getValue(), sellvalue.getValue())));
    					
    					Integer dateIndex = (Integer) e_colNameMap.get("Ԥ��֤��ȡʱ��");
    					Variant datevalue = excelSheet.getCell(rowIndex, dateIndex.intValue(), true).getValue();
    					
    					if(datevalue.getValue()!=null&&datevalue.getValue() instanceof GregorianCalendar){
			    			cal.setTime(((GregorianCalendar)datevalue.getValue()).getTime());
    			    		year=cal.get(Calendar.YEAR);
    			    		month=cal.get(Calendar.MONTH)+1;
    			    		String key=year+"year"+month+"m";
    			    		
    			    		if(row.getCell(key+"amount")!=null){
    			    			row.getCell("δ����Ӧ").setValue(FDCHelper.add(row.getCell("δ����Ӧ").getValue(), FDCHelper.subtract(value.getValue(), sellvalue.getValue())));
    			    			row.getCell(key+"amount").setValue(FDCHelper.add(row.getCell(key+"amount").getValue(), FDCHelper.subtract(value.getValue(), sellvalue.getValue())));
    			    			row.getCell(key+"area").setValue(FDCHelper.add(row.getCell(key+"area").getValue(), areavalue.getValue()));
    			    		}
    					}
    				}else{
    					row = table.addRow();
    					row.getCell("����").setValue(city);
    					row.getCell("��Ŀ").setValue(curProject);
    					row.getCell("ҵ̬").setValue(productType);
    					
    					Integer areaIndex = (Integer) e_colNameMap.get("�������");
    					Variant areavalue = excelSheet.getCell(rowIndex, areaIndex.intValue(), true).getValue();
    					row.getCell("�������").setValue(areavalue.getValue());
    					
    					Integer totalIndex = (Integer) e_colNameMap.get("�ܻ�ֵ");
    					Variant value = excelSheet.getCell(rowIndex, totalIndex.intValue(), true).getValue();
    					row.getCell("�ܻ�ֵ").setValue(value.getValue());
    					
    					Integer sellIndex = (Integer) e_colNameMap.get("���ۻ�ֵ");
    					Variant sellvalue = excelSheet.getCell(rowIndex, sellIndex.intValue(), true).getValue();
    					row.getCell("���").setValue(FDCHelper.subtract(value.getValue(), sellvalue.getValue()));
    					
    					Integer dateIndex = (Integer) e_colNameMap.get("Ԥ��֤��ȡʱ��");
    					Variant datevalue = excelSheet.getCell(rowIndex, dateIndex.intValue(), true).getValue();
    					
    					if(datevalue.getValue()!=null&&datevalue.getValue() instanceof GregorianCalendar){
			    			cal.setTime(((GregorianCalendar)datevalue.getValue()).getTime());
    			    		year=cal.get(Calendar.YEAR);
    			    		month=cal.get(Calendar.MONTH)+1;
    			    		String key=year+"year"+month+"m";
    			    		
    			    		if(row.getCell(key+"amount")!=null){
    			    			row.getCell("δ����Ӧ").setValue(FDCHelper.subtract(value.getValue(), sellvalue.getValue()));
    			    			row.getCell(key+"amount").setValue(FDCHelper.subtract(value.getValue(), sellvalue.getValue()));
    			    			row.getCell(key+"area").setValue(areavalue.getValue());
    			    		}
    					}
    					date.put(curProject+productType, row);
    				}
        		}
    			table.getGroupManager().setGroup(true);
    			table.getColumn("����").setGroup(true);
    			table.getColumn("����").setMergeable(true);
     			
    			table.getColumn("��Ŀ").setGroup(true);
    			table.getColumn("��Ŀ").setMergeable(true);
			}
		}
		if(table==null){
			FDCMsgBox.showWarning(this,"¥�������ƻ������ڣ�");
			return false;
		}else{
			contEntry.getContentPane().removeAll();
			
			contEntry.setName(table.getName());
			contEntry.getContentPane().setLayout(new BorderLayout(0, 0));        
			contEntry.getContentPane().add(table, BorderLayout.CENTER);
		}
		return true;
	}
	private String getString(Object value){
		if(value==null) return "";
		if(value!=null&&value.toString().trim().equals("")){
			return "";
		}else{
			return value.toString();
		}
	}
	private void mergerTable(KDTable table,String coloum[],String mergeColoum[]){
		int merger=0;
		for(int i=0;i<table.getRowCount();i++){
			if(i>0){
				boolean isMerge=true;
				for(int j=0;j<coloum.length;j++){
					Object curRow=table.getRow(i).getCell(coloum[j]).getValue();
					Object lastRow=table.getRow(i-1).getCell(coloum[j]).getValue();
					if(!getString(curRow).equals(getString(lastRow))){
						isMerge=false;
						merger=i;
					}
				}
				if(isMerge){
					for(int j=0;j<mergeColoum.length;j++){
						table.getMergeManager().mergeBlock(merger, table.getColumnIndex(mergeColoum[j]), i, table.getColumnIndex(mergeColoum[j]));
					}
				}
			}
		}
	}
}