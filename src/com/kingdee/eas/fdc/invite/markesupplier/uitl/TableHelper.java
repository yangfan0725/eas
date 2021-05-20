package com.kingdee.eas.fdc.invite.markesupplier.uitl;

import java.awt.Component;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JMenuItem;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTViewManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDPopupMenu;
import com.kingdee.bos.ctrl.swing.KDSeparator;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.multiDetail.DetailPanel;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;

public class TableHelper {
	//�Ҽ��˵�����
	public static void addPopMenu(CoreUI ui,KDTable tblMain, ItemAction[] action)
    {

		KDPopupMenu kdmenu = new KDPopupMenu();
        for(int i = 0; i < action.length; i++){            
            JMenuItem item = new JMenuItem();
            item.setAction(action[i]);
            kdmenu.add(item);
            item.setEnabled(action[i].isEnabled());
        }

        kdmenu.add(new KDSeparator());
        
        KDPopupMenu menu = ui.getMenuManager(tblMain).getMenu();
        int count = menu.getComponentCount();
        for(int i = 0; i < count; i++){
            kdmenu.add(menu.getComponent(0));
        }
        ui.getMenuManager(tblMain).setMenu(kdmenu);

    }
	//���÷����ں�
	public static void setMerge(KDTable table, String[] columnNames,
			boolean isMerge) {

		for (int i = 0, count = columnNames.length; i < count; i++) {
			table.getColumn(columnNames[i]).setMergeable(isMerge);
		}

	}
	/**
	 * �����ں�
	 * */
	public static void mergeThemeRow(KDTable table, String columnName,int colIndex) {
		String theme = "";
		String lastTheme = "";
		KDTMergeManager mm = table.getMergeManager();
		int rowIndx = 0;
		int endIndx = 0;
		for (int i = 0; i < table.getRowCount(); i++) {
			endIndx = i;
			theme = UIRuleUtil.getString(table.getRow(i).getCell(columnName).getValue()) ; // ��ǰ����
			if (i > 0) {
				lastTheme = UIRuleUtil.getString(table.getRow(i - 1).getCell(columnName).getValue()); // ��һ����
				if (!theme.equals(lastTheme)) { // ��ȡ��ǰ���� �� ��һ���� ����ͬ�����ڵ��к�
					mm.mergeBlock(rowIndx, colIndex, endIndx - 1, colIndex); // �������ͬ�������ں�
					rowIndx = endIndx;
				}
			}
		}
		mm.mergeBlock(rowIndx, colIndex, endIndx, colIndex); // �������ͬ�������ں�
	}

	
	/**
	 * ���úϼ��� ���ԶԶ�����ͣ�
	 * @param columnName ����
	 * **/
	public static void getFootRow(KDTable tblMain, String[] columnName) {
		IRow footRow = null;
		KDTFootManager footRowManager = tblMain.getFootManager();
		if (footRowManager == null) {
			String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
			footRowManager = new KDTFootManager(tblMain);
			footRowManager.addFootView();
			tblMain.setFootManager(footRowManager);
			footRow = footRowManager.addFootRow(0);
			footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
			tblMain.getIndexColumn().setWidthAdjustMode((short) 1);
			tblMain.getIndexColumn().setWidth(30);
			footRowManager.addIndexText(0, total);
		} else {
			footRow = footRowManager.getFootRow(0);
		}
		int columnCount = tblMain.getColumnCount();
		for (int c = 0; c < columnCount; c++) {
			String fieldName = tblMain.getColumn(c).getKey();
			for (int i = 0; i < columnName.length; i++) {
				String colName = (String) columnName[i];
				if (colName.equalsIgnoreCase(fieldName)) {
					ICell cell = footRow.getCell(c);
					cell.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
					cell.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
					cell.getStyleAttributes().setFontColor(java.awt.Color.BLACK);
					cell.setValue(getColumnValueSum(tblMain, colName));
				}
			}

		}
		footRow.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
	}
	/**
	 * ���ñ���������
	 * */
	public static void setTableAutoSort(KDTable tblMain)
	  {
	    KDTSortManager sm = new KDTSortManager(tblMain);
	    sm.setSortAuto(true);
	    for (int index = 0; index < tblMain.getColumnCount(); index++)
	      tblMain.getColumn(index).setSortable(true);
	  }
	
	/**
     * ��ȡ���ѡ���е�ĳ��ֵ
     * SystemStatusCtrolUtils
     */
	public static String[] getSelectRowFieldValue(KDTable kdtTable, String fieldname)
    {
        if(!isSelectRow(kdtTable))
            return null;
        KDTSelectManager sm = kdtTable.getSelectManager();
        int blockCount = sm.size();
        int keyField = kdtTable.getColumnIndex(fieldname);
        ArrayList retList = new ArrayList();
        Object value = null;
        for(int i = 0; i < blockCount; i++)
        {
            KDTSelectBlock block = sm.get(i);
            int j = block.getTop();
            for(int bottom = block.getBottom(); j <= bottom; j++)
            {
                IRow row = kdtTable.getRow(j);
                value = row.getCell(keyField).getValue();
                if(value != null && value.toString().trim().length() != 0)
                {
                    String retStr = value.toString();
                    retList.add(retStr);
                }
            }

        }
        retList.trimToSize();
        if(retList.size() == 0)
        {
            return null;
        } else
        {
            String retStrs[] = new String[retList.size()];
            retStrs = (String[])retList.toArray(retStrs);
            return retStrs;
        }
    }

    public static boolean isSelectRow(KDTable kdtTable)
    {
        KDTSelectManager sm = kdtTable.getSelectManager();
        return sm.size() > 0;
    }
	
    /**
     * �������ǰ����
     * 
     * @param table ���
     * @param count ��������
     * @see com.kingdee.eas.fi.fa.basedata.FaUtils#freezeTable(KDTable table, int count)
     */
    public static void freezeTable(KDTable table, int count) {
    	
        if (table == null) {
            return;
        }
        KDTViewManager viewmgr = table.getViewManager();
        if (viewmgr != null && !viewmgr.isFreeze()) {
            viewmgr.setFreezeView(0, count);
            table.reLayoutAndPaint();
        }
    }
    
    
    /**
     * ɾ����������ѡ�е��� ���û��ѡ���У�ɾ�����һ�С�
     * 
     * @param kDTable ���
     * @see com.kingdee.eas.fi.fa.manage.client.FaClientUtils#tableDelLine(KDTable kDTable)
     */
    public static void tableDelLine(KDTable kDTable) {
        if (kDTable.getSelectManager().get() == null) {
            return;
        }

        int ibeginRow = kDTable.getSelectManager().get().getBeginRow();
        int iEndrow = kDTable.getSelectManager().get().getEndRow();
        int ilastRow = kDTable.getRowCount();

        if ((ibeginRow != -1) && (iEndrow != -1)) {
            for (int i = ibeginRow; i < (iEndrow + 1); i++) {
                IRow row = kDTable.getRow(i);

                if (row == null) {
                    continue;
                }
                row = kDTable.removeRow(i);
				iEndrow--;
				i--;
            }
        } else { // û��ѡ����ʱɾ�����һ�У���Ҫ��Ҫ��
            if (ilastRow > 0) {
                kDTable.removeRow(ilastRow - 1);
            }
        }
    }

    /**
     * �ڱ���δ�м�һ�� �������ӵ���
     * 
     * @param kDTable ���
     * @see com.kingdee.eas.fi.fa.manage.client.FaClientUtils#tableDelLine(KDTable kDTable)
     */
    public static IRow tableAddLastLine(KDTable kDTable) {
        IRow row = null;
        int iRow = kDTable.getRowCount();

        if (iRow != -1) {
            row = kDTable.addRow(iRow);
        }
        return row;
    }
    
    /**
     * �ڱ�������һ��
     * 
     * @param kDTable	���
     * @param keyName	�м���
     * @param celName	����
     * @param index	����
     * @param width	�п�
     */
    public static void tableAddColumn(KDTable table, String keyValue, String celName, int index, int width) {
    	//	��ָ������λ��
    	table.addColumn(index);
    	//	��ͷָ������λ��
    	IRow headRow = table.getHeadRow(0);
    	//	��ȡ��ͷ�ж���
    	headRow.getCell(index).setValue(celName);
    	headRow.setCell(index, headRow.getCell(index));
    	
    	//	��ȡ�ж���
    	IColumn col = table.getColumn(index);
    	col.setKey(keyValue);
    	col.setWidth(width);
    }
    
    
    /**
     * �ڱ��е�һ������Ϊ��ѡ��������ʾ״̬
     * @param table ���
     * @param cellName ��Ԫ����
     * @param flag ѡ��״̬ true ѡ�� fales ûѡ��
     */
    public static void checkRows(KDTable table, String cellName, boolean flag) {
    	
        Boolean check = Boolean.valueOf(flag);
        int iCount = table.getRowCount();
        for (int i = 0; i < iCount; i++) {
            IRow row = table.getRow(i);
            if(!"locked".equals(row.getUserObject()))
                row.getCell(cellName).setValue(check);
        }
    }
    
    
	/**
	 * �����еĸ�ѡ��������ʾ״̬���¼�������
	 * @param kdtable ���
	 * @param cellName ����
	 * @param e �¼�����
	 */
	public static void tableCheckChanged(KDTable kdtable, String cellName, KDTEditEvent e) {
    	
        if(!cellName.equals(kdtable.getColumnKey(e.getColIndex())))
            return;
        kdtable.getRow(e.getRowIndex()).getCell(e.getColIndex()).setValue(e.getValue());
    }
    
	
    /**
     * ��ʽ�����ڣ������ڸ�ʽ��Ϊ��YYYY-MM-DD��
     * 
     * @param table ���
     * @param columnKey �м���
     * @see com.kingdee.eas.fi.fa.manage.client.FaClientUtils#fmtDate(KDTable table, String columnKey)
     */
    public static void fmtDate(KDTable table, String columnKey) {
	    if(table.getColumn(columnKey)!=null)	
	    	table.getColumn(columnKey).getStyleAttributes().setNumberFormat("yyyy-MM-dd");
    }

    
    /**
     * ��ʽ�����ڣ������ڸ�ʽ��Ϊ��YYYY-MM-DD��
     * 
     * @param table ���
     * @param columnKeys �м���
     * @see com.kingdee.eas.fi.fa.manage.client.FaClientUtils#fmtDate(KDTable table, String[] columnKey)
     */
    public static void fmtDate(KDTable table, String[] columnKeys) {
        for (int i = 0; i < columnKeys.length; i++) {
            fmtDate(table, columnKeys[i]);
        }
    }
    
    /**
     * ��ָ��table�趨����ʽ�������Ҷ���
     * 
     * @param table ���
     * @param columnName ����
     */
    public static void changeTableNumberFormat(KDTable table, String columnName) {
    	if(table.getColumn(columnName)!=null){
    		table.getColumn(columnName).getStyleAttributes().setNumberFormat(strDataFormat);
            table.getColumn(columnName).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // �Ҷ���
    	}
    }

    /**
     * ��ָ��table�趨����ʽ�������Ҷ���
     * 
     * @param table ���
     * @param columnIndex ������
     * @param format ��ʽ��
     */
    public static void changeTableNumberFormat(KDTable table, int columnIndex, String format) {
    	if(table.getColumn(columnIndex)!=null){
	        table.getColumn(columnIndex).getStyleAttributes().setNumberFormat(format);
	        table.getColumn(columnIndex).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // �Ҷ���
    	}
    }

    /**
     * ��ָ��table�趨����ʽ�������Ҷ���
     * 
     * @param table ���
     * @param columnNames ��������
     */
    public static void changeTableNumberFormat(KDTable table, String[] columnNames) {
        for (int i = 0; i < columnNames.length; i++)
            changeTableNumberFormat(table, columnNames[i]);
    }

    /**
     * ��ָ��table�趨����ʽ�������Ҷ���
     * 
     * @param table ���
     * @param columnNames ��������
     * @param format ��ʽ��
     */
    public static void changeTableNumberFormat(KDTable table, String[] columnNames, String format) {
        for (int i = 0; i < columnNames.length; i++)
            changeTableNumberFormat(table, columnNames[i], format);
    }
    
    /**
     * ��ָ��table�趨����ʽ�������Ҷ���
     * 
     * @param table ���
     * @param columnNames ��������
     * @param format ��ʽ��
     */
    public static void changeTableNumberFormat(KDTable table, String columnName, String format) {
        table.getColumn(columnName).getStyleAttributes().setNumberFormat(format);
        table.getColumn(columnName).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // �Ҷ���
    }
    
    
    /**
     * ��ָ��table�趨������ģ�ö�٣�
     * 
     * @param table ���
     * @param classEnumName ö�ٵ�����
     * @param columnName �������
     */
    public static void setTableComboBox(KDTable table, String classEnumName,String columnName) {
    	
        KDComboBox tableComboBox = new KDComboBox();
        tableComboBox.setName(table.getName()+"_"+columnName+"_ComboBox");
        tableComboBox.setVisible(true);
        List l = EnumUtils.getEnumList(classEnumName);
        tableComboBox.addItem("");//����Ϊ�յ�ѡ��
        tableComboBox.addItems(l.toArray());
        KDTDefaultCellEditor tableColumnCellEditor = new KDTDefaultCellEditor(tableComboBox);
        table.getColumn(columnName).setEditor(tableColumnCellEditor);
    }
    /**
     * ��ָ��table���������
     * 
     * @param table ���
     * @param columnName �������
     */
    public static BigDecimal getColumnValueSum(KDTable table,String columnName) {
    	BigDecimal sum = new BigDecimal(0);
        for(int i=0;i<table.getRowCount();i++){
        	if(table.getRow(i).getCell(columnName).getValue()!=null 
        			&& table.getRow(i).getCell(columnName).getValue() instanceof BigDecimal)
        		sum = sum.add((BigDecimal)table.getRow(i).getCell(columnName).getValue());
        }
        return sum;
    }
    /**
	 * ����table�ϵ���ɾ�尴ť
	 * @param table
	 */
	public static void hidePanelButtons(KDTable table){
		Component c = table.getParent().getParent();  
		if (c instanceof DetailPanel) {  
			 DetailPanel panel = (DetailPanel) c;
			 Component[] components = panel.getComponents();  
			 for(int i = 0; i < components.length; i++){  
				 Component component = components[i];  
				 if (component instanceof KDPanel) {  
					 KDPanel kdPanel = (KDPanel) component;  
					 if("controlPanel".equals(kdPanel.getName())){  
						 panel.remove(kdPanel);  
					 }  
					 if("entryPanel".equals(kdPanel.getName())){  
						 Rectangle entryRect = new Rectangle(0, 0,table.getWidth(),table.getHeight()+ 29);  
						 kdPanel.setBounds(entryRect);  
						 kdPanel.putClientProperty("OriginalBounds",entryRect);  
						 panel.add(kdPanel, new KDLayout.Constraints(KDLayout.Constraints.ANCHOR_TOP  
								 | KDLayout.Constraints.ANCHOR_RIGHT | KDLayout.Constraints.ANCHOR_LEFT  
								 | KDLayout.Constraints.ANCHOR_BOTTOM , entryRect));  
					 }  
				 }  
			 }  	
		}   
	} 
	/**
	 * ��list��ʽ���table
	 */
	protected void fillTableWithList(KDTable table,List records) {
		for (int i = records.size(), j = 0; j < i; j++) {
			Object record = records.get(j);
			if (record instanceof List) {
				List list = (List) record;
				IRow row = table.addRow();
				for (int k = 0, x = list.size(); k < x; k++) {
					row.getCell(k).setValue(list.get(k));
				}
			}
		}
	}
	/**
	 * ִ����Ч��ksql�����ؽ������list��
	 * 
	 * @param sql
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static List execQuery(String sql) throws BOSException, SQLException {
		ArrayList result = new ArrayList();
		IRowSet rowSet = SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
		ResultSetMetaData metaData = rowSet.getMetaData();
		int colCount = metaData.getColumnCount();
		while (rowSet.next()) {
			HashMap resMap = new HashMap();
			for (int i = 1; i <= colCount; i++) {
				String colName = metaData.getColumnName(i);
				resMap.put(colName, rowSet.getObject(i));
			}
			result.add(resMap);
		}
		return result;
	}
	/**
	 * ����Table���Ƿ�������
	 * */
   public static void setHided(KDTable table,String[] strings,Boolean boll){
	   for(int i=0;i<strings.length;i++){
		   String colName = (String) strings[i];
		   table.getColumn(colName).getStyleAttributes().setHided(boll);
	   }
   }
   /**
	 * ����Table���Ƿ񲻿ɱ༭
	 * */
  public static void setLocked(KDTable table,String[] strings,Boolean boll){
	   for(int i=0;i<strings.length;i++){
		   String colName = (String) strings[i];
		   table.getColumn(colName).getStyleAttributes().setLocked(boll);
	   }
  }
  private static String strDataFormat = "#,##0.00;-#,##0.00";
}
