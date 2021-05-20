package com.kingdee.eas.fdc.sellhouse.client;

import java.text.Collator;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.kingdee.bos.ctrl.kdf.table.ISortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;

/**
 * ҵ��Ӧ�ÿ�ܵ�ListUI�ж�δ�󶨵��в�������ֱ��ʹ��KDTSortManger�����ҽϸ��ӣ�����дKDTSortManager
 * @author zhicheng_jin
 * */
public class SimpleKDTSortManager extends KDTSortManager {
	private Collator comparator = Collator.getInstance(Locale.getDefault());
	private SimpleKDTSortManager(KDTable table) {
		super(table);
		this.setClickCount(1);//������������
	}
	
	/**
	 * ����Table�ɰ�������
	 * ע���ü�����������ڷǴ��������ļ�Table������
	 * @param table
	 * 			Ҫ�����Table
	 * */
	public static void setTableSortable(KDTable table){
		new SimpleKDTSortManager(table);
	}
	
	public void tableClicked(KDTMouseEvent e) {
		if ((e.getButton() == getButton())
				&& (e.getClickCount() == getClickCount())
				&& (e.getType() == KDTStyleConstants.HEAD_ROW)) {
			setRefresh(false);
			List rows = this.table.getBody().getRows();
			final int col = e.getColIndex();
			//������һ�ε��������ͬ���仯����ʽ
			if (col == colIndex) {
				if (sortType == ISortManager.SORT_ASCEND) {
					sortType = ISortManager.SORT_DESCEND;
				} else {
					sortType = ISortManager.SORT_ASCEND;
				}
			}

			Collections.sort(rows, new Comparator() {
				public int compare(Object arg0, Object arg1) {
					KDTRow row0 = (KDTRow) arg0;
					KDTRow row1 = (KDTRow) arg1;
					//���ƣ������Ȼ�п�ָ�룬���մ����-<����������ϸ���еġ�����Ӧ�տ�н�������ʱ���жϣ�>
					Object obj0 = row0.getCell(col) == null ? null : row0.getCell(col).getValue();
					Object obj1 = row1.getCell(col) == null ? null : row1.getCell(col).getValue();
					int tmp = doCompare(obj0, obj1);
					if (sortType == ISortManager.SORT_ASCEND) {
						return tmp;
					} else {
						return -tmp;
					}
				}
			});
			changeHeadStatus(colIndex, col, sortType);
			colIndex = col;
			setRefresh(true);
		}
	}
	
	private int doCompare(Object o1, Object o2)
	{
		if (o1 == o2)
		{
			return 0;
		}
		else if (o1 == null && o2 != null)
		{
			return -1;
		}
		else if (o1 != null && o2 == null)
		{
			return 1;
		}
		else
		{
			if (o1 instanceof Number)
			{
				if (o2 instanceof Number)
				{
					double d1 = ((Number) o1).doubleValue();
					double d2 = ((Number) o2).doubleValue();
					if (d1 == d2)
					{
						return 0;
					}
					else if (d1 < d2)
					{
						return -1;
					}
					else
					{
						return 1;
					}
				}
				else
				{
					return o1.toString().compareTo(o2.toString());
				}
			}
			else if (o1 instanceof String)
			{
				if (o2 instanceof String)
				{
					// ʹ��Collatorʵ������������;�����ܻ��в��ֺ��ֲ��ᰴƴ������
					// ������Ϊ:
					// �ڼ�������������ʹ�ñȽ϶���ַ����� GB2312-80�����Ϊ GB2312������ַ���������Ŀǰ��õĺ��ֹ��� 6736 �������еĺ��ַ�Ϊ�����ࣺ
					// ���ú���,�γ��ú���;���ú��ְ��պ���ƴ�������򣬶��γ��ú��ְ��ձʻ����׽�������
					return comparator.compare(o1, o2);
					//return ((String) o1).compareTo((String) o2);
				}
				else
				{
					return ((String) o1).compareTo(o2.toString());
				}
			}
			else if (o1 instanceof Date)
			{
				if (o2 instanceof Date)
				{
					return ((Date) o1).compareTo((Date) o2);
				}
				else
				{
					return o1.toString().compareTo(o2.toString());
				}
			}
			else if (o1 instanceof Calendar)
			{
				if (o2 instanceof Calendar)
				{
					long d1 = ((Calendar) o1).getTimeInMillis();
					long d2 = ((Calendar) o2).getTimeInMillis();
					if (d1 == d2)
					{
						return 0;
					}
					else if (d1 < d2)
					{
						return -1;
					}
					else
					{
						return 1;
					}
				}
				else
				{
					return o1.toString().compareTo(o2.toString());
				}
			}
			else
			{
				return o1.toString().compareTo(o2.toString());
			}
		}
	}
}
