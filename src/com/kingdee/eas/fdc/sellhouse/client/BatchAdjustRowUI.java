/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.sellhouse.PriceTypeForPriceBillEnum;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPriceAdjustEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPriceAdjustEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SymbolEnum;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class BatchAdjustRowUI extends AbstractBatchAdjustRowUI {
	private static final Logger logger = CoreUIObject.getLogger(BatchAdjustRowUI.class);
	
	private int[] paramArray = null;
	
	private final static String FLOOW="��";

	public BatchAdjustRowUI() throws Exception {
		super();
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		
		paramArray = (int[])this.getUIContext().get("paramArray");
	}

	public void storeFields() {
		super.storeFields();
	}

	protected void btnYes_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnYes_actionPerformed(e);
		this.verifyInput();

		this.dealSinglePrice();

		this.destroyWindow();
	}

	protected void btnNo_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnNo_actionPerformed(e);

		this.destroyWindow();
	}

	protected void comboSymbol_itemStateChanged(java.awt.event.ItemEvent e)
			throws Exception {
		super.comboSymbol_itemStateChanged(e);
		
		if(e.getItem()!=null && e.getItem().equals(SymbolEnum.MULTIPLY)){
			this.contYuan.setVisible(false);
		}
		else{
			this.contYuan.setVisible(true);
		}
	}

	public static RoomPriceAdjustEntryCollection showUI(CoreUI ui, boolean isSelectAdjustSoldRoom, boolean isAdjustSoldRoom,
			RoomPriceAdjustEntryCollection entryCollection, int[] paramArray,String number,String colNumber) throws BOSException {
		// ����ѡ�е�¥������
		UIContext uiContext = new UIContext(ui);
		uiContext.put("isSelectAdjustSoldRoom", new Boolean(isSelectAdjustSoldRoom));
		uiContext.put("isAdjustSoldRoom", new Boolean(isAdjustSoldRoom));
		uiContext.put("entryCollection", entryCollection);
		uiContext.put("paramArray", paramArray);
		uiContext.put("rowNumber", number);
		uiContext.put("colNumber", colNumber);

		// ����UI������ʾ
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(BatchAdjustRowUI.class.getName(), uiContext, null,
						OprtState.ADDNEW);
		uiWindow.show();

		return entryCollection;
	}

	/**
	 * ��������
	 */
	private void verifyInput() {
		if (this.comboSymbol.getSelectedItem() == null) {
			FDCMsgBox.showInfo("��ѡ������������");
			this.comboSymbol.requestFocus();
			SysUtil.abort();
		}
		if (this.txtYuan.getNumberValue() == null) {
			FDCMsgBox.showInfo("����������۸�");
			this.txtYuan.requestFocus();
			SysUtil.abort();
		}
	}

	/**
	 * ��������ĵ�����ʽ����������
	 */
	private void dealSinglePrice() throws BOSException{
		RoomPriceAdjustEntryCollection entryCollection = (RoomPriceAdjustEntryCollection) uiWindow
				.getUIObject().getUIContext().get("entryCollection");
		
		String rowNumber = "";
		String colNumber = "";
		
		if(uiWindow.getUIObject().getUIContext().get("rowNumber")!=null){
			rowNumber = uiWindow.getUIObject().getUIContext().get("rowNumber").toString();
		}
		if(uiWindow.getUIObject().getUIContext().get("colNumber")!=null){
			colNumber = uiWindow.getUIObject().getUIContext().get("colNumber").toString();
		}
		
		int index = rowNumber.indexOf(FLOOW);
		rowNumber = rowNumber.substring(0,index);
		
		int symbol = this.comboSymbol.getSelectedIndex();
		BigDecimal priceFactor = this.txtYuan.getBigDecimalValue();
		
		for(int i=0; i<entryCollection.size(); i++){
			RoomPriceAdjustEntryInfo priceEntryInfo = entryCollection.get(i);
			//���۷�Դ�Ƿ�������
			if(!this.isPriceEditable(priceEntryInfo.getRoom())){
				continue;
			}
			
			if(!"".equals(rowNumber) && priceEntryInfo.getRoom()!=null){
				if(!rowNumber.equals(String.valueOf(priceEntryInfo.getRoom().getFloor()))){
					continue;
				}
			}
			if(entryCollection.get(i+1)!=null){
				RoomPriceAdjustEntryInfo priceEntryNextInfo = entryCollection.get(i+1);
				if(priceEntryInfo.getRoom().getSerialNumber()==priceEntryNextInfo.getRoom().getSerialNumber()){
					continue;
				}
			}
			//if(!"".equals(colNumber) && priceEntryInfo.getRoom()!=null){
				//if(!colNumber.equals(String.valueOf(priceEntryInfo.getRoom().getSerialNumber()))){
					//continue;
				//}
			//}
			
			BigDecimal singlePrice = null;
			BigDecimal newSinglePrice = null;
			PriceTypeForPriceBillEnum priceType = priceEntryInfo.getPriceType();
			if(PriceTypeForPriceBillEnum.UseBuildingArea.equals(priceType)){
				singlePrice = priceEntryInfo.getNewBuildingPrice();
			}
			else if(PriceTypeForPriceBillEnum.UseRoomArea.equals(priceType)){
				singlePrice = priceEntryInfo.getNewRoomPrice();
			}
			if(singlePrice == null){
				singlePrice = FDCHelper.ZERO;
			}
			// ����������������ֵ�����㵥��
			if (SymbolEnum.ADD_VALUE == symbol) {
				newSinglePrice = singlePrice.add(priceFactor).setScale(paramArray[0], paramArray[1]);
			}
			else if (SymbolEnum.MINUS_VALUE == symbol) {
				newSinglePrice = singlePrice.subtract(priceFactor).setScale(paramArray[0], paramArray[1]);
			} 
			else if (SymbolEnum.MULTIPLY_VALUE == symbol) {
				newSinglePrice = singlePrice.multiply(priceFactor).setScale(paramArray[0], paramArray[1]);
			}
			
			//����¼۸�Ϊ������������Ϊ0
			if(newSinglePrice.compareTo(FDCHelper.ZERO) < 0){
				newSinglePrice = FDCHelper.ZERO;
			}
			
			//�����µ���
			if(PriceTypeForPriceBillEnum.UseBuildingArea.equals(priceType)){
				BigDecimal area = priceEntryInfo.getNewBuildingArea();
				if(area == null){
					continue;
				}
				priceEntryInfo.setNewBuildingPrice(newSinglePrice);
				//�����ܼ�
				priceEntryInfo.setNewSumAmount(newSinglePrice.multiply(area).setScale(paramArray[2], paramArray[3]));
				priceEntryInfo.setModyfied(true);
			}
			else if(PriceTypeForPriceBillEnum.UseRoomArea.equals(priceType)){
				BigDecimal area = priceEntryInfo.getNewRoomArea();
				if(area == null){
					continue;
				}
				priceEntryInfo.setNewRoomPrice(newSinglePrice);
				//�����ܼ�
				priceEntryInfo.setNewSumAmount(newSinglePrice.multiply(area).setScale(paramArray[2], paramArray[3]));
				priceEntryInfo.setModyfied(true);
			}
		}
	}
	
	/**
	 * �Ƿ�������۷�Դ��������������������
	 * @return
	 */
	private boolean isPriceEditable(RoomInfo room){
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