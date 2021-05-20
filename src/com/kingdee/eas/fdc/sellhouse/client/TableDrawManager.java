package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.LineStyle;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.Position;
import com.kingdee.eas.fdc.sellhouse.PlanisphereElementEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PlanisphereElementEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;

/**
 * �����õĿ�ȣ�
 * */
public class TableDrawManager {

	public static final String COL_KEY_PREFIX = "colKey";
	private static final LineStyle LIINE_STYLE = LineStyle.DOUBLE_LINE_A;
	private KDTable table;
	public static final int VIEW_MODEL = 0;
	public static final int EDIT_MODEL = 1;
	
	
	public TableDrawManager(){
	}
	public TableDrawManager(KDTable table){
		this.table = table;
	}
	
	public void setTable(KDTable table) {
		this.table = table;
	}

	public KDTable getTable() {
		return table;
	}
	
	/**
	 * ������Щ����ɵ�ͼ�εı߿�
	 * @param list ��ļ���List<Point>
	 * */
	public void setBorder(List list) {
		setBorder(list, null,null);
	}
	
	/**
	 * ������Щ����ɵ�ͼ�εı߿�,��������ɫ
	 * @param list ��ļ���List<Point>
	 * */
	public void setBorder(List list, Color bkColor,Color boarderColor) {
		for(int i=0; i<list.size(); i++){
			Point p = (Point) list.get(i);
			
			ICell cell = table.getCell(p.x, p.y);
			if(cell == null){
				continue;
			}
			
			Point upP = new Point(p.x - 1, p.y);
			Point downP = new Point(p.x + 1, p.y);
			Point leftP = new Point(p.x, p.y - 1);
			Point rightP = new Point(p.x, p.y + 1);
			
//			LineStyle topLineStyle = LIINE_STYLE;
//			LineStyle dowmLineStyle = LIINE_STYLE;
//			LineStyle leftLineStyle = LIINE_STYLE;
//			LineStyle rightLineStyle = LIINE_STYLE;
			
			if(!list.contains(upP)){				
				if(table.getCell(upP.x, upP.y) != null){
					table.getCell(upP.x, upP.y).getStyleAttributes().setBorderLineStyle(Position.BOTTOM, LIINE_STYLE);
					table.getCell(upP.x, upP.y).getStyleAttributes().setBorderColor(Position.BOTTOM,boarderColor);
				}else{
					cell.getStyleAttributes().setBorderLineStyle(Position.TOP, LIINE_STYLE);
					cell.getStyleAttributes().setBorderColor(Position.TOP,boarderColor);
				}
			}
			if(!list.contains(downP)){				
				if(table.getCell(downP.x, downP.y) != null){
					table.getCell(downP.x, downP.y).getStyleAttributes().setBorderLineStyle(Position.TOP, LIINE_STYLE);
					table.getCell(downP.x, downP.y).getStyleAttributes().setBorderColor(Position.TOP, boarderColor);
				}else{
					cell.getStyleAttributes().setBorderLineStyle(Position.BOTTOM, LIINE_STYLE);
					cell.getStyleAttributes().setBorderColor(Position.BOTTOM, boarderColor);
				}
			}
			if(!list.contains(leftP)){
				if(table.getCell(leftP.x, leftP.y) != null){
					table.getCell(leftP.x, leftP.y).getStyleAttributes().setBorderLineStyle(Position.RIGHT, LIINE_STYLE);
					table.getCell(leftP.x, leftP.y).getStyleAttributes().setBorderColor(Position.RIGHT, boarderColor);
				}else{
					cell.getStyleAttributes().setBorderLineStyle(Position.LEFT, LIINE_STYLE);
					cell.getStyleAttributes().setBorderColor(Position.LEFT, boarderColor);
				}
			}
			if(!list.contains(rightP)){
				if(table.getCell(rightP.x, rightP.y) != null){
					table.getCell(rightP.x, rightP.y).getStyleAttributes().setBorderLineStyle(Position.LEFT, LIINE_STYLE);
					table.getCell(rightP.x, rightP.y).getStyleAttributes().setBorderColor(Position.LEFT, boarderColor);
				}else{
					cell.getStyleAttributes().setBorderLineStyle(Position.RIGHT, LIINE_STYLE);
					cell.getStyleAttributes().setBorderColor(Position.RIGHT, boarderColor);
				}
			}
			
			if(bkColor != null){
				cell.getStyleAttributes().setBackground(bkColor);
			}
		}
	}


	
	/**
	 * ȡ����Щ�㼯�ϵı߿� ,������䱳��
	 * */
	public void clearBorder(List list){
		for(int i=0; i<list.size(); i++){
			Point p = (Point) list.get(i);
			
			ICell cell = table.getCell(p.x, p.y);
			if(cell == null){
				continue;
			}
			
			if(table.getCell(p.x -1, p.y) != null){
				table.getCell(p.x -1, p.y).getStyleAttributes().setBorderLineStyle(Position.BOTTOM, LineStyle.NULL_LINE);
			}else{
				cell.getStyleAttributes().setBorderLineStyle(Position.TOP, LineStyle.NULL_LINE);
			}
			
			if(table.getCell(p.x +1, p.y) != null){
				table.getCell(p.x +1, p.y).getStyleAttributes().setBorderLineStyle(Position.TOP, LineStyle.NULL_LINE);
			}else{
				cell.getStyleAttributes().setBorderLineStyle(Position.BOTTOM, LineStyle.NULL_LINE);
			}
			
			if(table.getCell(p.x, p.y-1) != null){
				table.getCell(p.x, p.y-1).getStyleAttributes().setBorderLineStyle(Position.RIGHT, LineStyle.NULL_LINE);
			}else{
				cell.getStyleAttributes().setBorderLineStyle(Position.LEFT, LineStyle.NULL_LINE);
			}
			
			if(table.getCell(p.x, p.y+1) != null){
				table.getCell(p.x, p.y+1).getStyleAttributes().setBorderLineStyle(Position.LEFT, LineStyle.NULL_LINE);
			}else{
				cell.getStyleAttributes().setBorderLineStyle(Position.RIGHT, LineStyle.NULL_LINE);
			}			
			
//			cell.getStyleAttributes().setBorderLineStyle(Position.BOTTOM, LineStyle.NULL_LINE);
			cell.getStyleAttributes().setBackground(Color.WHITE);
//			cell.getStyleAttributes().setBorderLineStyle(Position.TOP, LineStyle.NULL_LINE);
			cell.getStyleAttributes().setBackground(Color.WHITE);
//			cell.getStyleAttributes().setBorderLineStyle(Position.RIGHT, LineStyle.NULL_LINE);
			cell.getStyleAttributes().setBackground(Color.WHITE);
//			cell.getStyleAttributes().setBorderLineStyle(Position.LEFT, LineStyle.NULL_LINE);
			cell.getStyleAttributes().setBackground(Color.WHITE);
		}
	}
	
	/**
	 * ��ʼ��Table��������Ŀ�����еĿ��
	 * @param rowCount
	 * @param colCount
	 * @param rowHeight
	 * @param colWidth
	 * @param model �쿴���߱༭ģʽ,���������Ƿ���ʵ������
	 * */
	public void initTable(int rowCount, int colCount, int rowHeight, int colWidth, int model) {
		if(table == null){
			throw new RuntimeException("table is null.");
		}
		table.removeColumns();		
		
		for(int i=0; i<colCount; i++){
			IColumn col = table.addColumn();
			col.setKey(COL_KEY_PREFIX + i);
			col.setWidth(colWidth);
		}
		
		IRow headRow = table.addHeadRow();
		for(int i=0; i<colCount; i++){
			headRow.getCell(i).setValue(String.valueOf(i+1));
		}
		
		for(int i=0; i<rowCount; i++){
			IRow row = table.addRow();
			row.setHeight(rowHeight);
		}
		
		if(model == VIEW_MODEL){
			table.setHorizonGridLineVisible(false);
			table.setVerticalGridLineVisible(false);
			
			table.setVerticalHeadGridLineVisible(false);
			table.setHorizonHeadGridLineVisible(false);	
		}
	}
	
	/**
	 * ��ʼ��Table�����м�ÿ��ÿ�еĿ��,֧��ÿ�еĿ�Ȳ�һ�������
	 * @deprecated �ݲ�ʵ��
	 * */
	public void initTable(int rowCount, int colCount, int[] rowHeight, int[] ColWidth) {
		// TODO �Զ����ɷ������
	}
	
	/**
	 * �����Ƿ���ʾ������
	 * @param model ȡֵΪ #VIEW_MODEL #EDIT_MODEL��VIEWģʽ����ʾ������
	 * */
	public void setModel(int model){
		boolean isEditModel = model == EDIT_MODEL;
		table.setHorizonGridLineVisible(isEditModel);
		table.setVerticalGridLineVisible(isEditModel);
		
		table.setVerticalHeadGridLineVisible(isEditModel);
		table.setHorizonHeadGridLineVisible(isEditModel);	
	}
	
	/**
	 * ��ѡ�еĵ�Ԫ����Ʊ߿򣬲���ѡ�е���Щ��ļ��Ϸ���
	 * */
	public List setBorderOfSelectedCells(Color bkColor,Color borderColor) {
		List list = getSelectPointList();
		setBorder(list, bkColor,borderColor);
		return list;
	}

	
	//�����ѡ��Ԫ������Χ����
	public List getMaxNameList(List nameList) {
		if(nameList==null) return null;
		
		int minRowNum = -1;
		int minColNum = -1;
		int maxRowNum = 0;
		int maxColNum = 0;
		for(int i=0;i<nameList.size();i++) {
			Point point = (Point)nameList.get(i);
			if(minRowNum<0 ||(int)point.getX()<minRowNum)
				minRowNum = (int)point.getX();
			if((int)point.getX()>maxRowNum) 
				maxRowNum = (int)point.getX();				
			if(minColNum<0 || (int)point.getY()<minColNum)
				minColNum = (int)point.getY();
			if((int)point.getY()>maxColNum)
				maxColNum = (int)point.getY();	
		}
		
		nameList.clear();
		for(int i=minRowNum;i<=maxRowNum;i++) {
			for(int j=minColNum;j<=maxColNum;j++){
				nameList.add(new Point(i,j));
			}			
		}
		return nameList;
	}
	
	
	private List setNameOfNameList(List nameList,String name,Color bkColor,Color nameColor){
		//�ϲ���ѡ��Ԫ������Χ����
		int minRowNum = -1;
		int minColNum = -1;
		int maxRowNum = 0;
		int maxColNum = 0;
		for(int i=0;i<nameList.size();i++) {
			Point point = (Point)nameList.get(i);
			if(minRowNum<0 ||(int)point.getX()<minRowNum)
				minRowNum = (int)point.getX();
			if((int)point.getX()>maxRowNum) 
				maxRowNum = (int)point.getX();				
			if(minColNum<0 || (int)point.getY()<minColNum)
				minColNum = (int)point.getY();
			if((int)point.getY()>maxColNum)
				maxColNum = (int)point.getY();	
		}
		
		nameList.clear();
		for(int i=minRowNum;i<=maxRowNum;i++) {
			for(int j=minColNum;j<=maxColNum;j++){
				nameList.add(new Point(i,j));
			}			
		}		
		
		KDTMergeManager mm = this.table.getMergeManager();
		mm.mergeBlock(minRowNum,minColNum,maxRowNum,maxColNum);
		
		setTextName(minRowNum, minColNum, name,nameColor);
		ICell cell = table.getCell(minRowNum,minColNum);
		if(cell != null)  {
			//cell.getStyleAttributes().setBackground(bkColor);
			cell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
			
			cell.getStyleAttributes().setFontSize(5*(maxRowNum-minRowNum+1));
		}
		
		return nameList;
	}
	
	
	/**
	 * ����ѡ�еĵ�Ԫ�������  
	 * @param name Ҫ��ʾ������
	 * */
	public List setNameOfSelectedCells(String name,Color bkColor,Color nameColor){
		List list = getSelectPointList();
		if(list.isEmpty()){
			return null;
		}	
		return setNameOfNameList(list,name,bkColor,nameColor);		
	}
	
	
	/**
	 * ��ѡ�еĵ㼯�Ϸ���
	 * @return List<Point> ѡ�еĵ�ļ���
	 * */
	public List getSelectPointList(){
		List list = new ArrayList();
		for (int i = 0; i < this.table.getSelectManager().size(); i++) {
			KDTSelectBlock block = this.table.getSelectManager().get(i);
			if (block == null) {
				return list;
			}
			for (int row = block.getBeginRow(); row <= block.getEndRow(); row++) {
				for (int col = block.getBeginCol(); col <= block
						.getEndCol(); col++) {
					list.add(new Point(row, col));
//					ICell cell = this.table.getCell(row, col);
//					if (cell == null) {
//						continue;
//					}
				}
			}
		}
		return list;
	}
	
	public void setTextName(int x, int y, String name,Color nameColor) {
		ICell cell = table.getCell(x, y);
		if(cell != null)  {
			cell.setValue(name);
			
			if(nameColor!=null)
				cell.getStyleAttributes().setFontColor(nameColor);
		}		
	}

	public void setTextName(List plist, String name,Color bkColor,Color nameColor) {
		if(plist.isEmpty()) return;
		
		setNameOfNameList(plist,name,bkColor,nameColor);
	}

	
	/**
	 * ���㼯��ѡ��
	 * @param list List<Point>
	 * */
	public void setSelected(List list) {
		for(int i=0; i<list.size(); i++){
			Point p = (Point) list.get(i);
			ICell cell = table.getCell(p.x, p.y);
			if(cell != null){
				table.getSelectManager().add(p.x, p.y, p.x,p.y);
			}
		}		
	}
	

	/**
	 * ���㼯��ѡ��,������Ԫ�����÷������
	 * @param list List<Point>
	 * */
	public void setSelected(List list,RoomInfo room) {
		for(int i=0; i<list.size(); i++){
			Point p = (Point) list.get(i);
			ICell cell = table.getCell(p.x, p.y);
			if(cell != null){
				table.getSelectManager().add(p.x, p.y, p.x,p.y);
				if(room!=null) cell.setUserObject(room);
			}
		}		
	}	

	
	/**
	 * ����Ԫ�صı߿�
	 * @param elem
	 */
	public void showElementBorder(PlanisphereElementEntryInfo elem) {
		byte[] lineData = elem.getOutLineLocationData();
		List lineList = CommerceHelper.ByteArrayToListObject(lineData);			
		setBorder(lineList,new Color(elem.getOutLineBackColor()),new Color(elem.getOutLineColor()));
	}
	
	
	/**
	 * ����Ԫ�ص�����  -- ����ֻ��һ����Ԫ���ϻ���
	 * @param elem
	 */
	public void showElementName(PlanisphereElementEntryInfo elem) {
		byte[] nameData = elem.getNameLocationData();
		List nameList = CommerceHelper.ByteArrayToListObject(nameData);
		if(nameList.isEmpty()) return;

		setTextName(nameList, elem.getName(),new Color(elem.getNameBackColor()),new Color(elem.getNameColor()));
	}
	
	
	/**
	 * ����ĳ��Ԫ�� (����+����)
	 * @param elem Ԫ��
	 * */
	public void showElement(PlanisphereElementEntryInfo elem){
			showElementBorder(elem);			
			showElementName(elem);
	}
	
	
	/**
	 * ����Ԫ�ؼ�����ÿ��Ԫ�� (����+����)
	 * @param elementColl Ԫ�ؼ���
	 * */
	public void showElementCollection(PlanisphereElementEntryCollection elementColl){	
		for(int i=0; i<elementColl.size(); i++){
			PlanisphereElementEntryInfo elem = elementColl.get(i);
			showElement(elem);
		}
	}
	

	
	/**
	 * ѡ��һ��Ԫ�ص�����(�����ڵ����е�)
	 * */
	public void setSelectedBorder(PlanisphereElementEntryInfo elem){
		byte[] pos = elem.getOutLineLocationData();
		setSelected(CommerceHelper.ByteArrayToListObject(pos));
	}	
	
	/**
	 * ѡ��һ��Ԫ�ص�����(�����ڵ����е�)
	 */
	public void setSelectedName(PlanisphereElementEntryInfo elem){
		byte[] pos = elem.getNameLocationData();
		setSelected(CommerceHelper.ByteArrayToListObject(pos));
	}	

	
	/**
	 * �����Ԫ�صı߿� ,������䱳��
	 * @param elem Ԫ�ض���
	 * */
	public void clearElementBorder(PlanisphereElementEntryInfo elem){
		byte[] pos = elem.getOutLineLocationData();
		 clearBorder(CommerceHelper.ByteArrayToListObject(pos));
		 
	}	
	
	
	/**
	 * ���Ԫ�ص����� ,������䱳�� ,���ŷֺϲ��ĵ�Ԫ��
	 * @param elem Ԫ�ض���
	 * */
	public void clearElementName(PlanisphereElementEntryInfo elem){
		List nameList = CommerceHelper.ByteArrayToListObject(elem.getNameLocationData());
		if(nameList.isEmpty()) return;
		Point p = (Point)nameList.get(0);
		setTextName(nameList, "",Color.WHITE,Color.WHITE);

		//�ϲ���ѡ��Ԫ������Χ����
		int minRowNum = -1;
		int minColNum = -1;
		int maxRowNum = 0;
		int maxColNum = 0;
		for(int i=0;i<nameList.size();i++) {
			Point point = (Point)nameList.get(i);
			if(minRowNum<0 || (int)point.getX()<minRowNum)
				minRowNum = (int)point.getX();
			if((int)point.getX()>maxRowNum) 
				maxRowNum = (int)point.getX();				
			if(minColNum<0 || (int)point.getY()<minColNum)
				minColNum = (int)point.getY();
			if((int)point.getY()>maxColNum)
				maxColNum = (int)point.getY();				
		}
		this.table.getMergeManager().splitBlock(minRowNum,minColNum,maxRowNum,maxColNum);
	}	
	
	
	/**
	 * ���ĳ��Ԫ�� (����+����)
	 * @param elem Ԫ�ض���
	 * */
	public void clearElement(PlanisphereElementEntryInfo elem){
		clearElementBorder(elem);
		clearElementName(elem);
	}
	
	
	/**
	 * Ԫ�ص����Ʒ�Χ�Ƿ���������Χ��
	 */
	public boolean isNameInOutLineList(List nameList,List outLineList) {
		boolean isIn = true;
		for(int i=0;i<nameList.size();i++) {
			Point point = (Point)nameList.get(i);
			if(!outLineList.contains(point))
				return false;
		}
		return isIn;
	}
	
	
	/**
	 * Ԫ�������Ƿ�����ص��Ĳ���
	 */
	public boolean isExistSuperpose(List outLineList,List allOutLineList) {
		boolean isSuperpose = false;
		for(int i=0;i<outLineList.size();i++) {
			Point point = (Point)outLineList.get(i);
			if(allOutLineList.contains(point))
				return true;
		}		
		return isSuperpose;
	}
	
	
	
	
	
	
	
	
	
}
