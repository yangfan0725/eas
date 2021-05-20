package com.kingdee.eas.fdc.schedule.client;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JMenuItem;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDPopupMenu;
import com.kingdee.bos.ctrl.swing.KDSeparator;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * ������а�����
 * @author Administrator
 *
 */
public class TableHelper {

	//���÷����ں�
	public static void setMerge(KDTable table, String[] columnNames,
			boolean isMerge) {

		for (int i = 0, count = columnNames.length; i < count; i++) {
			table.getColumn(columnNames[i]).setMergeable(isMerge);
		}

	}

	//��������
	public static void setLocked(KDTable table, String[] columnNames,
			boolean isLock) {

		for (int i = 0, count = columnNames.length; i < count; i++) {
			table.getColumn(columnNames[i]).getStyleAttributes().setLocked(
					isLock);
		}

	}

	//��������
	public static void setHided(KDTable table, String[] columnNames,
			boolean isHide) {

		for (int i = 0, count = columnNames.length; i < count; i++) {
			table.getColumn(columnNames[i]).getStyleAttributes().setHided(
					isHide);
		}

	}

	//���ñ༭��
	public static void setEditor(KDTable table, String[] columnNames,
			ICellEditor[] editors) {
		for (int i = 0, count = columnNames.length; i < count; i++) {
			table.getColumn(columnNames[i]).setEditor(editors[i]);
		}
	}
	
	//���ɾ���У�����ͳһ�¼�
	public static void deleteLine(CoreUIObject ui, KDTable tblEntry) {

		if ((tblEntry.getSelectManager().size() == 0)) {
			MsgBox.showInfo(ui, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_NoneEntry"));
			return;
		}

		if (MsgBox.isYes(MsgBox
				.showConfirm2(ui, EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Confirm_Delete")))) {
			//��ȡѡ�����ܸ���
			KDTSelectManager selectManager = tblEntry.getSelectManager();
			int size = selectManager.size();
			if (size <= 0) {
				return;
			}

			KDTSelectBlock selectBlock = null;
			Set indexSet = new HashSet();
			for (int blockIndex = size - 1; blockIndex >= 0; blockIndex--) {

				selectBlock = selectManager.get(blockIndex);
				int top = selectBlock.getBeginRow();
				int bottom = selectBlock.getEndRow();
				for (int delIndex = bottom; delIndex >= top; delIndex--) {
					indexSet.add(new Integer(delIndex));
				}
			}
			//ɾ���е�����
			Integer[] indexArr = new Integer[indexSet.size()];
			Object[] indexObj = indexSet.toArray();
			System.arraycopy(indexObj, 0, indexArr, 0, indexArr.length);
			Arrays.sort(indexArr);
			if (indexArr == null || indexArr.length == 0)
				return;
			for (int i = indexArr.length - 1; i >= 0; i--) {
				int rowIndex = Integer.parseInt(String.valueOf(indexArr[i]));
				IObjectValue detailData = (IObjectValue) tblEntry.getRow(
						rowIndex).getUserObject();
				tblEntry.removeRow(rowIndex);
				IObjectCollection collection = (IObjectCollection) tblEntry
						.getUserObject();

				if (collection != null) {
					// Modify By Jacky Zhang
					if (detailData != null) {
						int index = getCollectionIndex(collection, detailData);
						//�����кϼ��еķ�¼����
						if (index >= 0 && collection.size() > index) {
							collection.removeObject(index);
						}
					}
				}

			}
		}

		//��������м�¼��λ����һ��
		if (tblEntry.getRow(0) != null) {
			tblEntry.getSelectManager().select(0, 0);
		}
	}
	
    //��ΪĿǰObjectValue�Ƚ��ǰ�ֵ�Ƚϣ���������ʹ�ã������¼ֵ��ͬ��
    //����ɾ���ҵ��ĵ�һ�������������ʵ�ְ�ָ��Ƚϡ�2007-2-5
	public static int getCollectionIndex(IObjectCollection collection, IObjectValue obj)
    {
        int index =-1;
        if (collection == null)
        {
            return index;
        }
        for (int i = collection.size() - 1; i >= 0; i--)
        {
            if (obj == collection.getObject(i))
            {
                index = i;
                return index;
            }
        }
        return index;
    }
	
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
	
	public static void setSelect(CoreUI ui,KDTable tblMain,String cloumnKey,boolean selected)
    {
		//��ȡѡ�����ܸ���
		KDTSelectManager selectManager = tblMain.getSelectManager();
		int size = selectManager.size();
		if (size <= 0) {
			return;
		}
	
		KDTSelectBlock selectBlock = null;
		for (int blockIndex = size - 1; blockIndex >= 0; blockIndex--) {
	
			selectBlock = selectManager.get(blockIndex);
			int top = selectBlock.getBeginRow();
			int bottom = selectBlock.getEndRow();
			for (int delIndex = bottom; delIndex >= top; delIndex--) {
				 IRow rowAss = tblMain.getRow(delIndex);
				rowAss.getCell(cloumnKey).setValue(Boolean.valueOf(selected));
			}
		}
    }
}
