/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.ShareStyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.VerticalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.PriceTypeForPriceBillEnum;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPriceAdjustEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPriceAdjustEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.CoreUI;

/**
 * output class name
 */
public class BatchRoomPriceAdjustUI extends AbstractBatchRoomPriceAdjustUI {
	private static final Logger logger = CoreUIObject.getLogger(BatchRoomPriceAdjustUI.class);
	
	/**
	 * �Ƿ���ն��ۼ�¼
	 */
	private boolean isClearData = true;
	
	private int[] paramArray = null;

	public BatchRoomPriceAdjustUI() throws Exception {
		super();
	}

	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		this.buildingTable.checkParsed();
		super.onLoad();
		paramArray = (int[])this.getUIContext().get("paramArray");		
		this.createBatchAdjustTable();
	}

	protected void btnYes_actionPerformed(ActionEvent e) throws Exception {
		super.btnYes_actionPerformed(e);
		
		this.isClearData = false;
		this.destroyWindow();
}

	protected void btnNo_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnNo_actionPerformed(e);

		this.destroyWindow();
	}
	
	public boolean destroyWindow() {
		if(isClearData){
			this.getUIContext().put("entryCollection", null);
		}
		return super.destroyWindow();
	}

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}
	
	protected void buildingTable_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
			int rowIndex = e.getRowIndex();
			int colIndex = e.getColIndex();
			int type = e.getType();
			
			Object adjustCellValObj = null;
			Object adjustCellObj = null;
			
		
			//0-��ͷ��1-����
			if(type == 0){
				adjustCellValObj = this.buildingTable.getHeadRow(rowIndex).getCell(colIndex).getValue();
				adjustCellObj = this.buildingTable.getHeadRow(rowIndex).getCell(colIndex).getUserObject();
			}
			else{
				adjustCellValObj = this.buildingTable.getCell(rowIndex, colIndex).getValue();
				adjustCellObj = this.buildingTable.getCell(rowIndex, colIndex).getUserObject();
			}
			
			//�����е������͡�����������ĵ�Ԫ��ֵ���ַ��������Լ����ַ��������ж�
			if(adjustCellValObj!=null && adjustCellObj!=null && adjustCellValObj instanceof String){
				//�����У�ȡ���ֵ��ۣ������е�������Ԫ���ֵ����Ϊ�۸��е�key
				if("���е���".equals(adjustCellValObj.toString())){
					RoomPriceAdjustEntryCollection priceEntry = new RoomPriceAdjustEntryCollection();
					for(int i=0; i<this.buildingTable.getRowCount(); i++){
						//�۸���
						ICell priceCell = this.buildingTable.getCell(i, adjustCellObj.toString());
						//����۸��еĶ���Ϊ����Ϊ�����У���������������
						if(priceCell!=null && priceCell.getUserObject()!=null){
							//ȡ�۸��е��������ж�
							String priceName = (String)this.buildingTable.getCell(i, priceCell.getColumnIndex()-1).getUserObject();
							if("newSinglePrice".equals(priceName)){
								priceEntry.add((RoomPriceAdjustEntryInfo)priceCell.getUserObject());
							}
						}
					}
					if(priceEntry.size() > 0){
						Boolean isSelectAdjustSoldRoom = (Boolean)this.getUIContext().get("isSelectAdjustSoldRoom");
						Boolean isAdjustSoldRoom = (Boolean)this.getUIContext().get("isAdjustSoldRoom");
						String colNumber = null;
						colNumber = this.buildingTable.getHeadRow(1).getCell(colIndex).getValue().toString();
						BatchAdjustColUI.showUI(this, isSelectAdjustSoldRoom.booleanValue(), isAdjustSoldRoom.booleanValue(), 
								priceEntry, paramArray,colNumber);
						
						this.buildingTable.removeHeadRows();
						this.buildingTable.removeRows();
						this.buildingTable.removeColumns();
						this.createBatchAdjustTable();
					}
				}
				//�����У�ȡ���ֵ��ۣ��������������Ԫ���ֵ����Ϊ�۸��е��к�
				else if("�������".equals(adjustCellValObj.toString())){
					RoomPriceAdjustEntryCollection priceEntry = new RoomPriceAdjustEntryCollection();
					Integer priceRowIndex = (Integer)adjustCellObj;
					for(int i=0; i<this.buildingTable.getColumnCount(); i++){
						ICell currentCell = this.buildingTable.getCell(priceRowIndex.intValue(), i);
						if(currentCell!=null && currentCell.getUserObject() instanceof RoomPriceAdjustEntryInfo){
							priceEntry.add((RoomPriceAdjustEntryInfo)currentCell.getUserObject());
						}
					}
					if(priceEntry.size() > 0){
						Boolean isSelectAdjustSoldRoom = (Boolean)this.getUIContext().get("isSelectAdjustSoldRoom");
						Boolean isAdjustSoldRoom = (Boolean)this.getUIContext().get("isAdjustSoldRoom");
					
						String rowNumber = null;
						rowNumber = this.buildingTable.getRow(rowIndex).getCell(0).getValue().toString();
						String colNumber = null;
						colNumber = this.buildingTable.getHeadRow(1).getCell(colIndex+1).getValue().toString();
					
						BatchAdjustRowUI.showUI(this, isSelectAdjustSoldRoom.booleanValue(), isAdjustSoldRoom.booleanValue(),
								priceEntry, paramArray,rowNumber,colNumber);
						
						this.buildingTable.removeHeadRows();
						this.buildingTable.removeRows();
						this.buildingTable.removeColumns();
						this.createBatchAdjustTable();
					}
				}
			}
		}
	}

	/**
	 * ��ʾ������������
	 * @param ui
	 * @param isSelectAdjustSoldRoom - �Ƿ�ѡ�����۷�Դ�Ƿ�������
	 * @param isAdjustSoldRoom - ���۷�Դ�Ƿ�������
	 * @param entryCollection - ѡ�е�¥�����伯��
	 * @param paramArray - �۸�ı���λ����ȡ����ʽ
	 * 
	 * @return ������ķ��伯��
	 * @throws UIException
	 */
	public static RoomPriceAdjustEntryCollection showUI(CoreUI ui, boolean isSelectAdjustSoldRoom, boolean isAdjustSoldRoom,
			RoomPriceAdjustEntryCollection entryCollection, int[] paramArray) throws UIException {
		//����ѡ�е�¥������
		UIContext uiContext = new UIContext(ui);
		uiContext.put("entryCollection", entryCollection);
		uiContext.put("isSelectAdjustSoldRoom", new Boolean(isSelectAdjustSoldRoom));
		uiContext.put("isAdjustSoldRoom", new Boolean(isAdjustSoldRoom));
		uiContext.put("paramArray", paramArray);

		// ����UI������ʾ
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(BatchRoomPriceAdjustUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
		RoomPriceAdjustEntryCollection retEntryCollection = (RoomPriceAdjustEntryCollection) uiWindow
			.getUIObject().getUIContext().get("entryCollection");
		return retEntryCollection;
	}
	
	/**
	 * ����¥��������
	 * @throws BOSException
	 */
	private void createBatchAdjustTable() throws BOSException{
		//�����к�
		this.buildingTable.getStyleAttributes().setLocked(true);
		this.buildingTable.getIndexColumn().getStyleAttributes().setHided(true);
		
		RoomPriceAdjustEntryCollection entryCollection = (RoomPriceAdjustEntryCollection) this
			.getUIContext().get("entryCollection");
		
		Map buildingMaxFloor = new HashMap(); // ¥��id�����¥���ӳ��
		Map buildingMinFloor = new HashMap();
		Map buildingMaxNum = new HashMap(); // ¥��id�ͷ����������кŵ�ӳ��
		Map buildingMinNum = new HashMap();
		for (int i = 0; i < entryCollection.size(); i++) {  //�õ�¥����ÿ����Ԫ�Ĳ�����Χ�ͷ�������Χ
			RoomInfo room = entryCollection.get(i).getRoom();

			int unitNum = room.getUnit();
			String key = room.getBuilding().getId().toString() + unitNum;
			Integer maxFloor = (Integer) buildingMaxFloor.get(key);
			Integer minFloor = (Integer) buildingMinFloor.get(key);
			Integer maxNum = (Integer) buildingMaxNum.get(key);
			Integer minNum = (Integer) buildingMinNum.get(key);
			if (minFloor == null || room.getFloor() < minFloor.intValue()) {
				buildingMinFloor.put(key, new Integer(room.getFloor()));
			}
			if (maxFloor == null || room.getFloor() > maxFloor.intValue()) {
				buildingMaxFloor.put(key, new Integer(room.getFloor()));
			}
			if (minNum == null || room.getSerialNumber() < minNum.intValue()) {
				buildingMinNum.put(key, new Integer(room.getSerialNumber()));
			}
			if (maxNum == null || room.getEndSerialNumber() > maxNum.intValue()) {
				buildingMaxNum.put(key, new Integer(room.getEndSerialNumber()));
			}
		}
		//��������
		IColumn column = this.buildingTable.addColumn();
		column.setKey("floor");
		column.setWidth(30);
		
		//�����������������
		IColumn batchColumn = this.buildingTable.addColumn();
		batchColumn.setKey("byFloor");
		batchColumn.setWidth(60);

		BuildingInfo building = entryCollection.get(0).getRoom().getBuilding();
		BuildingUnitCollection unitCollection  = this.getBuildingUnits(building.getId().toString());
		String key = null;
		
		IRow unitRow = this.buildingTable.addHeadRow(0);  //��Ԫ��
		unitRow.getCell(0).setValue("��Ԫ");
		
		IRow roomNumberRow = this.buildingTable.addHeadRow(1);  //�����
		roomNumberRow.getCell(0).setValue("���");
		
		IRow batchAdjustRow = this.buildingTable.addHeadRow(2);  //����������
		batchAdjustRow.getCell(1).setValue("��������");
		
		//��������У�¥����û�е�Ԫ��ֱ�Ӵ��������
		if(unitCollection==null || unitCollection.isEmpty()){
			key = building.getId().toString() + 0;
			Integer minNum = (Integer) buildingMinNum.get(key);
			Integer maxNum = (Integer) buildingMaxNum.get(key);
			if (minNum != null && maxNum != null) {
				for (int j = minNum.intValue(); j <= maxNum.intValue(); j++) { // ��������
					column = this.buildingTable.addColumn();
					column.setKey(key + j + 0);
					
					column = this.buildingTable.addColumn();
					column.setKey(key + j + 1);

					//�ϲ������е�������Ԫ��
					ICell batchAdjustCell = batchAdjustRow.getCell(key + j + 0);
					batchAdjustCell.setUserObject(key + j + 1);  //������ֵ�е�����
					batchAdjustCell.setValue("���е���");
					batchAdjustCell.getStyleAttributes().setFontColor(Color.BLUE);
					batchAdjustCell.getStyleAttributes().setUnderline(true);
					
					int offColumn = batchAdjustRow.getCell(key + j + 1).getColumnIndex() - batchAdjustCell.getColumnIndex();
					this.buildingTable.getHeadMergeManager().mergeBlock(batchAdjustRow.getRowIndex(), batchAdjustCell.getColumnIndex(),
							batchAdjustRow.getRowIndex(), batchAdjustCell.getColumnIndex() + offColumn );
					
					//�ϲ������
					ICell roomNumberCell = roomNumberRow.getCell(key + j + 0);
					roomNumberCell.setValue(new Integer(j));
					this.buildingTable.getHeadMergeManager().mergeBlock(roomNumberRow.getRowIndex(), roomNumberCell.getColumnIndex(), 
							roomNumberRow.getRowIndex(), roomNumberCell.getColumnIndex() + 1);
				}
			}
		}
		else{  //¥���´��ڵ�Ԫ��Ϊÿ����Ԫ�ֱ𴴽������
			for(int i=0; i<unitCollection.size(); i++){
				key = building.getId().toString() + unitCollection.get(i).getSeq();
				Integer minNum = (Integer) buildingMinNum.get(key);
				Integer maxNum = (Integer) buildingMaxNum.get(key);
				if (minNum != null && maxNum != null) {
					for (int j = minNum.intValue(); j <= maxNum.intValue(); j++) { // ��������
						column = this.buildingTable.addColumn();
						column.setKey(key + j + 0);
						
						column = this.buildingTable.addColumn();
						column.setKey(key + j + 1);
						
						//�ϲ������е�������Ԫ��
						ICell batchAdjustCell = batchAdjustRow.getCell(key + j + 0);
						batchAdjustCell.setUserObject(key + j + 1);  //������ֵ�е�����
						batchAdjustCell.setValue("���е���");
						batchAdjustCell.getStyleAttributes().setFontColor(Color.BLUE);
						batchAdjustCell.getStyleAttributes().setUnderline(true);
						
						int offColumn = batchAdjustRow.getCell(key + j + 1).getColumnIndex() - batchAdjustCell.getColumnIndex();
						this.buildingTable.getHeadMergeManager().mergeBlock(batchAdjustRow.getRowIndex(), batchAdjustCell.getColumnIndex(),
								batchAdjustRow.getRowIndex(), batchAdjustCell.getColumnIndex() + offColumn);
						
						//�ϲ������
						ICell roomNumberCell = roomNumberRow.getCell(key + j + 0);
						roomNumberCell.setValue(new Integer(j));
						this.buildingTable.getHeadMergeManager().mergeBlock(roomNumberRow.getRowIndex(), roomNumberCell.getColumnIndex(), 
								roomNumberRow.getRowIndex(), roomNumberCell.getColumnIndex() + 1);
					}
					//�ϲ���Ԫ��
					ICell unitCell = unitRow.getCell(key + minNum.intValue() + 0);
					unitCell.setValue(new Integer(unitCollection.get(i).getSeq()));
					int offSet = (maxNum.intValue() - minNum.intValue()) * 2 + 1;
					this.buildingTable.getHeadMergeManager().mergeBlock(unitRow.getRowIndex(), unitCell.getColumnIndex(), 
							unitRow.getRowIndex(), offSet + unitCell.getColumnIndex());
				}
			}
		}

		//����¥��
		int minRow = 1;
		int maxRow = 0;
		//ȡ���е�Ԫ��߲�
		Collection collection = buildingMaxFloor.values();
		for (Iterator iter = collection.iterator(); iter.hasNext();) {
			Integer num = (Integer) iter.next();
			if (num.intValue() > maxRow)
				maxRow = num.intValue();
		}
		
		//ȡ���е�Ԫ��С��
		Collection tempColl = buildingMinFloor.values();
		for (Iterator iter = tempColl.iterator(); iter.hasNext();) {
			Integer num = (Integer) iter.next();
			if (num.intValue() < minRow)
				minRow = num.intValue();
		}

		//������񷿼���
		for (int i = minRow; i <= maxRow; i++) {
			IRow row = this.buildingTable.addRow();
			
			int currFloor = maxRow - i + minRow;
			row.getCell("floor").setValue(new Integer(currFloor) + "��");
			row.getCell("byFloor").setValue("�������");
			row.getCell("byFloor").getStyleAttributes().setFontColor(Color.BLUE);
			row.getCell("byFloor").getStyleAttributes().setUnderline(true);
			if(currFloor==0){  //0������
				row.getStyleAttributes().setHided(true);
			}
			
			//�����ԭ�ܼۡ�ԭ���ۡ����ܼۡ��ֵ��ۣ�ÿһ�����һ�У����������ȴ����У��������������
			IRow subRow = null;
			for(int j=0; j<4; j++){
				subRow = this.buildingTable.addRow();
				if(currFloor==0){  //0������
					subRow.getStyleAttributes().setHided(true);
				}
			}
			
			//����ÿ�㶼�������ϵķ�����Ϊ���У�������ʾ¥���һ����Ҫ�ϲ�����
			this.buildingTable.getMergeManager().mergeBlock(row.getRowIndex(), 0, subRow.getRowIndex(), 0);  //¥����
			this.buildingTable.getMergeManager().mergeBlock(row.getRowIndex(), 1, subRow.getRowIndex(), 1);  //����������
			row.setUserObject(new Integer(i));
		}
		
		//�����
		this.fillRoomTable(this.buildingTable, entryCollection);
	}
	
	private void fillRoomTable(KDTable table, RoomPriceAdjustEntryCollection entryCollection) throws UIException {
		Integer minRow = (Integer) table.getRow(0).getUserObject();
		for (int i = 0; i < entryCollection.size(); i++) {
			RoomInfo room = entryCollection.get(i).getRoom();
			String buildingId = room.getBuilding().getId().toString();

			int unitNum = room.getUnit();
			String key = buildingId + unitNum;
			//�ںϷ������ʾ
			for (int j = room.getSerialNumber(); j <= room.getEndSerialNumber(); j++) {
				int newFloor = room.getFloor() + (room.getFloor()-minRow.intValue()) * 4;
				int rowIndex = table.getRowCount() + minRow.intValue() - 1 - newFloor;
				
				this.createPriceRow(table, rowIndex, key + j, entryCollection.get(i));
			}
			//�ںϵķ������ڵ�Ԫ�����Һϲ�
			if (room.getSerialNumber() != room.getEndSerialNumber()) {
				int newFloor = room.getFloor() + (room.getFloor()-minRow.intValue()) * 4;
				int curRow = table.getRowCount() + minRow.intValue() - 1 - newFloor;
				int leftBeginCol = table.getColumnIndex(key + room.getSerialNumber() + 1);
				int columnOff = ( room.getEndSerialNumber() - room.getSerialNumber() ) * 2; 
				
				int beginColIndex = leftBeginCol + 1;
				int endColIndex = leftBeginCol + columnOff;
				this.clearCellValue(table, curRow, beginColIndex, endColIndex);
				table.getMergeManager().mergeBlock(curRow, leftBeginCol, curRow, leftBeginCol + columnOff);
				
				curRow--;
				this.clearCellValue(table, curRow, beginColIndex, endColIndex);
				table.getMergeManager().mergeBlock(curRow, leftBeginCol, curRow, leftBeginCol + columnOff);
				
				curRow--;
				this.clearCellValue(table, curRow, beginColIndex, endColIndex);
				table.getMergeManager().mergeBlock(curRow, leftBeginCol, curRow, leftBeginCol + columnOff);
				
				curRow--;
				this.clearCellValue(table, curRow, beginColIndex, endColIndex);
				table.getMergeManager().mergeBlock(curRow, leftBeginCol, curRow, leftBeginCol + columnOff);
				
				curRow--;
				this.clearCellValue(table, curRow, beginColIndex, endColIndex);
				table.getMergeManager().mergeBlock(curRow, leftBeginCol, curRow, leftBeginCol + columnOff);
			}
			//���ºϲ�
			if(room.getSub()!=null&&room.getSub().indexOf(",")>0){
				int newFloor = room.getFloor() + (room.getFloor()-minRow.intValue()) * 4;
				int curRow = table.getRowCount() + minRow.intValue() - 1 - newFloor;
				int titleCol = table.getColumnIndex(key + room.getSerialNumber() + 0);
				int valueCol = table.getColumnIndex(key + room.getSerialNumber() + 1);
				
				String[] sub = room.getSub().split(",");
				int merger = sub.length - 1;
				int rowOffSet = curRow - (merger * 5) - 4;
				boolean isRow = true;
				if (!sub[0].split(";")[0].equals(sub[1].split(";")[0])){
					isRow = false;
				}
				if(!isRow){
					table.getMergeManager().mergeBlock(rowOffSet, titleCol, curRow - 4, titleCol);
					table.getMergeManager().mergeBlock(rowOffSet, valueCol, curRow - 4, valueCol);
					table.getCell(rowOffSet, titleCol).getStyleAttributes().setVerticalAlign(VerticalAlignment.BOTTOM);
					table.getCell(rowOffSet, valueCol).getStyleAttributes().setVerticalAlign(VerticalAlignment.BOTTOM);
				}
			}
		}
		
		for (int i = 0; i < table.getRowCount(); i++) {
			for (int j = 0; j < table.getColumnCount(); j++) {
				ICell cell = table.getRow(i).getCell(j);
				if (cell.getUserObject() == null) {
					if (j == 0) {
						cell.getStyleAttributes().setBackground(new Color(184, 204, 221));
					}
					else if(j == 1){
						cell.getStyleAttributes().setBackground(Color.WHITE);
					}
					else {
						cell.getStyleAttributes().setBackground(Color.GRAY);
					}
				}
			}

		}
	}
	
	/**
	 * �ϲ�ʱ����ձ��ϲ��ĵ�Ԫ��ֵ
	 * @param table
	 * @param curRow
	 * @param columnOffSet
	 */
	private void clearCellValue(KDTable table, int curRowIndex, int beginColIndex, int endColIndex){
		for(int i=beginColIndex; i<=endColIndex; i++){
			table.getCell(curRowIndex, i).setValue(null);
		}
	}
	
	/**
	 * @param table
	 * @param rowIndex
	 * @param colKey
	 * @param entry
	 */
	private void createPriceRow(KDTable table, int rowIndex, String colKey, RoomPriceAdjustEntryInfo entry){
		ICell nameCell = table.getCell(rowIndex, colKey + 0);
		ICell priceCell = table.getCell(rowIndex, colKey + 1);
		if (nameCell == null)
			return;
		else{
			Integer singlePriceRow = null;  //�ֵ��۵��к�
			
			PriceTypeForPriceBillEnum priceType = entry.getPriceType();
			BigDecimal oldSinglePrice = null;
			BigDecimal singlePrice = null;
			BigDecimal area = null;
			if(PriceTypeForPriceBillEnum.UseBuildingArea.equals(priceType)){
				oldSinglePrice = entry.getOldBuildingPrice();
				singlePrice = entry.getNewBuildingPrice();
				area = entry.getNewBuildingArea();
			}
			else if(PriceTypeForPriceBillEnum.UseRoomArea.equals(priceType)){
				oldSinglePrice = entry.getOldRoomPrice();
				singlePrice = entry.getNewRoomPrice();
				area = entry.getNewRoomArea();
			}
			
			//��������
			//���ܼ�
			nameCell.setUserObject("newSumAmount");
			nameCell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			nameCell.setValue("���ܼ�:");
			
			priceCell.setUserObject(entry);  //���涨�۷�¼���󣬹��������ʹ��
			priceCell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			if(entry.getNewSumAmount() != null){
				priceCell.setValue(entry.getNewSumAmount().setScale(paramArray[2], paramArray[3]));
			}
			else{
				priceCell.setValue(null);
			}
			if(this.isCellEditable(entry.getRoom())){
				FDCFormattedTextField sumField = new FDCFormattedTextField(KDFormattedTextField.DECIMAL);
				sumField.setNegatived(false);
				sumField.setPrecision(paramArray[2]);
				sumField.setRoundingMode(paramArray[3]);
				
				priceCell.setEditor(new KDTDefaultCellEditor(sumField));
				priceCell.getStyleAttributes().setLocked(false);
			}
			
			//�ֵ���
			rowIndex--;
			nameCell = table.getCell(rowIndex, colKey + 0);
			nameCell.setUserObject("newSinglePrice");
			nameCell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			nameCell.setValue("�ֵ���:");
			
			priceCell = table.getCell(rowIndex, colKey + 1);
			priceCell.setUserObject(entry);
			priceCell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			
			
			if(singlePrice != null){
				priceCell.setValue(singlePrice.setScale(paramArray[0], paramArray[1]));
			}
			else{
				priceCell.setValue(null);
			}
			if(this.isCellEditable(entry.getRoom()) && area!=null && area.compareTo(FDCHelper.ZERO)!=0){
				FDCFormattedTextField singleField = new FDCFormattedTextField(KDFormattedTextField.DECIMAL);
				singleField.setNegatived(false);
				singleField.setPrecision(paramArray[0]);
				singleField.setRoundingMode(paramArray[1]);
				
				priceCell.setEditor(new KDTDefaultCellEditor(singleField));
				priceCell.getStyleAttributes().setLocked(false);
			}
			
			//��¼�ֵ��۵��кţ��������������ʹ��
			singlePriceRow = new Integer(rowIndex);
			
			//ԭ�ܼ�
			rowIndex--;
			nameCell = table.getCell(rowIndex, colKey + 0);
			nameCell.setUserObject("oldSumAmount");
			nameCell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			nameCell.setValue("ԭ�ܼ�:");
			table.getRow(rowIndex).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			
			priceCell = table.getCell(rowIndex, colKey + 1);
			priceCell.setUserObject(entry);
			if(entry.getOldSumAmount() != null){
				priceCell.setValue(entry.getOldSumAmount().setScale(paramArray[2], paramArray[3]));
			}
			else{
				priceCell.setValue(null);
			}
			
			//ԭ����
			rowIndex--;
			nameCell = table.getCell(rowIndex, colKey + 0);
			nameCell.setUserObject("oldSinglePrice");
			nameCell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			nameCell.setValue("ԭ����:");
			table.getRow(rowIndex).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			
			priceCell = table.getCell(rowIndex, colKey + 1);
			priceCell.setUserObject(entry);
			if(oldSinglePrice != null){
				priceCell.setValue(oldSinglePrice.setScale(paramArray[0], paramArray[1]));
			}
			else{
				priceCell.setValue(null);
			}
			
			//���
			rowIndex--;
			nameCell = table.getCell(rowIndex, colKey + 0);
			nameCell.setUserObject("area");
			nameCell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			nameCell.setValue("���:");
			table.getRow(rowIndex).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);  //add by shilei
			
			priceCell = table.getCell(rowIndex, colKey + 1);
			priceCell.setUserObject(entry);
			if(area != null){
				priceCell.setValue(area.setScale(3,BigDecimal.ROUND_HALF_UP));
			}
			else{
				priceCell.setValue(null);
			}
			
			//���浱ǰ�����е��������������������ʹ�ã��������������Ԫ��ϲ�������Ԫ���������ｫ�ֵ����кű����������һ����
			table.getRow(rowIndex).getCell("byFloor").setUserObject(singlePriceRow);
		}
	}
	
	/**
	 * ����¥����ȡ��Ԫ����
	 * @param builldingId - ¥��Id
	 * @return
	 * @throws BOSException 
	 */
	private BuildingUnitCollection getBuildingUnits(String builldingId) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("seq");		
		view.getSorter().add(new SorterItemInfo("seq"));

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("building.id", builldingId));

		view.setFilter(filter);
		
		BuildingUnitCollection units = BuildingUnitFactory.getRemoteInstance().getBuildingUnitCollection(view);
		
		return units;
	}
	
	protected void buildingTable_editStopped(KDTEditEvent e) throws Exception {
		super.buildingTable_editStopped(e);
		
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();

		IRow currentRow = this.buildingTable.getRow(rowIndex);
		
		RoomPriceAdjustEntryInfo priceEntryInfo = (RoomPriceAdjustEntryInfo)this.buildingTable.getCell(rowIndex, colIndex).getUserObject();
		//ȡ�ñ༭�������
		String priceName = (String)currentRow.getCell(colIndex - 1).getUserObject();
		PriceTypeForPriceBillEnum priceType = priceEntryInfo.getPriceType();
		
		BigDecimal sumAmount = null;
		BigDecimal singlePrice = null;
		BigDecimal area = null;
		
		//�ж��޸ĵ��Ƿ�Ϊ���ܼ�
		if(priceName.equals("newSumAmount")){
			//�½������ۺ����ڵ���
			if(currentRow.getCell(colIndex).getValue() != null){
				sumAmount = KDTableTools.getBigDecimal(currentRow.getCell(colIndex).getValue());
			}
			//��ȡ��������ݽ����˳������ȡ
			if(this.buildingTable.getCell(rowIndex-4, colIndex).getValue() != null){
				area = KDTableTools.getBigDecimal(this.buildingTable.getCell(rowIndex-4, colIndex).getValue());
			}
			//���㵥��
			if(sumAmount!=null && area!=null){
				singlePrice = sumAmount.divide(area, paramArray[0], paramArray[1]);
				this.buildingTable.getCell(rowIndex-1, colIndex).setValue(singlePrice);
			}
		}
		else {  //�޸ĵ����ֵ���
			Object priceObj = this.buildingTable.getCell(rowIndex, colIndex).getValue();
			if(priceObj != null){  //�����ܼ�
				singlePrice = KDTableTools.getBigDecimal(priceObj);
				area = KDTableTools.getBigDecimal(this.buildingTable.getCell(rowIndex-3, colIndex).getValue());
				sumAmount = singlePrice.multiply(area);
				this.buildingTable.getCell(rowIndex+1, colIndex).setValue(sumAmount.setScale(paramArray[2], paramArray[3]));
			}
		}
		
		BigDecimal entrySinglePrice = null;
		if(PriceTypeForPriceBillEnum.UseBuildingArea.equals(priceType)){
			entrySinglePrice = priceEntryInfo.getNewBuildingPrice();
		}
		else if(PriceTypeForPriceBillEnum.UseRoomArea.equals(priceType)){
			entrySinglePrice = priceEntryInfo.getNewRoomPrice();
		}
		
		//���ֵ�Ƿ����仯
		if(sumAmount!=null && priceEntryInfo.getNewSumAmount()==null || sumAmount==null && priceEntryInfo.getNewSumAmount()!=null){
			priceEntryInfo.setModyfied(true);
		}
		else if(sumAmount != null && priceEntryInfo.getNewSumAmount()!=null){
			if(priceEntryInfo.getNewSumAmount().compareTo(sumAmount) != 0){
				priceEntryInfo.setModyfied(true);
			}
		}
		else if(singlePrice!=null && entrySinglePrice==null || singlePrice==null && entrySinglePrice!=null){
			priceEntryInfo.setModyfied(true);
		}
		else if(singlePrice != null && entrySinglePrice!=null){
			if(entrySinglePrice.compareTo(singlePrice) != 0){
				priceEntryInfo.setModyfied(true);
			}
		}
		
		//����۸�
		priceEntryInfo.setNewSumAmount(sumAmount);
		if(PriceTypeForPriceBillEnum.UseBuildingArea.equals(priceType)){  //�����½�������
			priceEntryInfo.setNewBuildingPrice(singlePrice);
		}
		else if(PriceTypeForPriceBillEnum.UseRoomArea.equals(priceType)){  //���������ڵ���
			priceEntryInfo.setNewRoomPrice(singlePrice);
		}
		
	}
	
	/**
	 * �Ƿ�������۷�Դ��������������������
	 * @return
	 */
	private boolean isCellEditable(RoomInfo room){
		boolean result = true;
		Boolean isSelectAdjustSoldRoom = (Boolean)this.getUIContext().get("isSelectAdjustSoldRoom");
		Boolean isAdjustSoldRoom = (Boolean)this.getUIContext().get("isAdjustSoldRoom");
		RoomSellStateEnum sellState = room.getSellState();
		if(RoomSellStateEnum.PrePurchase.equals(sellState) || RoomSellStateEnum.Purchase.equals(sellState) 
				|| RoomSellStateEnum.Sign.equals(sellState)){
			if(isSelectAdjustSoldRoom.booleanValue() && !isAdjustSoldRoom.booleanValue()){
				result = false;
			}
		}
		return result;
	}
}