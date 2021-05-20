
package com.kingdee.eas.fdc.sellhouse.client;


import java.math.BigDecimal;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.basedata.assistant.SettlementTypeCollection;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.GenFdcTrasfBillFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.util.client.MsgBox;




/**
 * output class name
 */
public class ZhuanKuanEditUI extends AbstractZhuanKuanEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ZhuanKuanEditUI.class);
    
    private PurchaseInfo purchase = null;
    
    private String purchaseId = null;   
   
   private RoomInfo room = null;   
   
	PurchasePayListEntryInfo proEntryInfo = null;	//��ת���Ķ���
	PurchasePayListEntryCollection payListColl = new PurchasePayListEntryCollection(); //��ת��Ŀ���
	SettlementTypeCollection settleTypeColl = new SettlementTypeCollection();	// ��ת��Ŀ���ļ��㷽ʽ
	
    private PurchaseInfo getPurchaseInfoById(String id)
    {
    	PurchaseInfo purchase = null;
    	if(id == null)
    		return purchase;
    	
    	SelectorItemCollection selColl = new SelectorItemCollection();
    	selColl.add("*");
    	selColl.add("payListEntry.*");
    	selColl.add("payListEntry.moneyDefine.moneyType");
    	selColl.add("payListEntry.moneyDefine.name");
    	
    	try
		{
			purchase = PurchaseFactory.getRemoteInstance().getPurchaseInfo(new ObjectUuidPK(BOSUuid.read(id)), selColl);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return purchase;
    }
    
    private void fillRoom()
    {
    	this.txtBuilding.setText(room.getBuilding() == null ? "":room.getBuilding().getName());
    	this.txtRoom.setText(room.getNumber());
    }
    
    private void initUI()
    {
    	this.kDTable1.checkParsed();
    	for(int i = 1; i < this.kDTable1.getColumnCount(); i ++)
    	{
    		this.kDTable1.getColumn(i).getStyleAttributes().setLocked(true);
    	}
    	
    	KDFormattedTextField field = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
    	field.setPrecision(2);
    	
    	ICellEditor editor = new KDTDefaultCellEditor(field);
    	
    	this.kDTable1.getColumn("appAmount").setEditor(editor);
    	this.kDTable1.getColumn("actAmount").setEditor(editor);
    	this.kDTable1.getColumn("inAmount").setEditor(editor);
    
    	
    	this.kDTable1.getColumn("appAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.kDTable1.getColumn("appAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	this.kDTable1.getColumn("inAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.kDTable1.getColumn("inAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	this.kDTable1.getColumn("actAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.kDTable1.getColumn("actAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	
    	this.kDTable2.checkParsed();
    	
    	
    	for(int i = 1; i < this.kDTable2.getColumnCount(); i ++)
    	{
    		this.kDTable2.getColumn(i).getStyleAttributes().setLocked(true);
    	}
    	this.kDTable2.getColumn("choose").getStyleAttributes().setLocked(false);
    	
    	this.kDTable2.getColumn("actAmount").setEditor(editor);
    	
    	this.kDTable2.getColumn("actAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.kDTable2.getColumn("actAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	

    	/*final KDCheckBox cb = new KDCheckBox();
    	
    	ICellEditor cbEditor = new KDTDefaultCellEditor(cb);
    	this.kDTable2.getColumn("choose").setEditor(cbEditor);*/
    	
    }
    
    protected void kDTable2_tableClicked(KDTMouseEvent e) throws Exception
    {
    	int column = this.kDTable2.getSelectManager().getActiveColumnIndex();
    	if(this.kDTable2.getColumn("choose").getColumnIndex() != column)
    		return;

    	
    	int row = this.kDTable2.getSelectManager().getActiveRowIndex();
    	IRow thisRow = this.kDTable2.getRow(row);
    	if(!(thisRow.getUserObject() instanceof PurchasePayListEntryInfo))	return;
    	
    	Object obj = thisRow.getCell("isOut").getValue();
		if(obj instanceof String)   	{
    		if("��".equalsIgnoreCase(obj.toString()))
    			return;
    	}
    	
    	boolean debug = ((Boolean)this.kDTable2.getRow(row).getCell("choose").getValue()).booleanValue();
    	if(debug)   	{
    		this.kDTable2.getRow(row).getCell("choose").setValue(Boolean.FALSE);
    	} else 	{
    		this.kDTable2.getRow(row).getCell("choose").setValue(Boolean.TRUE);
    	}
    	
    	this.kDTable1.requestFocus();
    	
    }
    
    
    /**
     * �÷�������ж����Ƿ�����
     */
    private boolean isReceived(MoneyTypeEnum type)
    {
    	if(this.purchaseId == null)
    	{
    		return false;
    	}
    	boolean debug = true;
    	
    	PurchasePayListEntryCollection coll = null;
    	try	{
			coll = PurchasePayListEntryFactory.getRemoteInstance()
					.getPurchasePayListEntryCollection("select appAmount,actRevAmount " +
							"where head.id='"+purchaseId+"' and moneyDefine.moneyType='"+type.getValue()+"'");
		} catch (BOSException e)	{
			super.handUIException(e);
		}
		for(int i = 0; i < coll.size(); i ++)	{
			PurchasePayListEntryInfo info = coll.get(i);
			if(info.getAppAmount().compareTo(info.getActRevAmount())>0) {
				debug = false;
				break;
			}
		}
		return debug;
    }
    
    
    public void onLoad() throws Exception
    {
    	super.onLoad();
    	this.initUI();
    	
    	//��������Ϲ�����������Ϲ�'s �����Ƿ���뷿��property Ϊfalse
    	purchaseId = (String)this.getUIContext().get("purchaseId");
    	if(purchaseId != null)
    	{
    		this.purchase = this.getPurchaseInfoById(purchaseId);
    		
    		if(this.purchase.isIsEarnestInHouseAmount())
    		{
    			MsgBox.showWarning("���Ϲ��������Ѽ��뷿����ܽ��ж���ת���������");
    			this.abort();
    		}
    	}
    	
    	if(!this.isReceived(MoneyTypeEnum.EarnestMoney))
    	{
    		MsgBox.showWarning("�÷��䲻�����Ѿ����붨����Ϲ��������ܽ��ж���ת�������");
    		this.abort();
    	}
    	
    	this.room = (RoomInfo)this.getUIContext().get("room");
    	
    	this.fillRoom();
    	
    	this.fillTable1();
    	
    	this.fillTable2();
    	
    }
    /**
     * ��¥�տ�� TABLE   Ӧ����ϸ�Ĳ��֣���ȥ���𲿷֣�
     */
    private void fillTable1()
    {
    	this.kDTable1.checkParsed();
    	this.kDTable1.getColumn("settlementType").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.basedata.assistant.app.SettlementTypeQuery", null));
    	this.kDTable1.getColumn("inAmount").setEditor(CommerceHelper.getKDFormattedTextDecimalEditor());
    	
    	PurchasePayListEntryCollection coll = this.purchase.getPayListEntry();
    	CRMHelper.sortCollection(coll, "seq", true);
    	this.kDTable1.getColumn("settlementType").getStyleAttributes().setLocked(false);
    	this.kDTable1.getColumn("inAmount").getStyleAttributes().setLocked(false);
    	
    	if(coll == null)
    		return;
    	for(int i = 0; i < coll.size(); i ++)   	{
    		PurchasePayListEntryInfo info = coll.get(i);
    		//����ʾ������
    		if(MoneyTypeEnum.EarnestMoney.equals(info.getMoneyDefine().getMoneyType()))
    				continue;
    		
    		IRow row = this.kDTable1.addRow();
    		row.setUserObject(info);
    		row.getCell("id").setValue(info.getId().toString());
    		row.getCell("moneyName").setValue(info.getMoneyDefine());
    		BigDecimal appAmount = info.getAppAmount();
    		if(appAmount == null)
    			appAmount = FDCHelper.ZERO;
    		else
    			appAmount = appAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			row.getCell("appAmount").setValue(appAmount);
    		row.getCell("appDate").setValue(info.getAppDate());
    		BigDecimal actPayAmount = info.getAllRemainAmount();
    		if(actPayAmount == null)
    			actPayAmount = FDCHelper.ZERO;
    		else
    			actPayAmount = actPayAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			row.getCell("actAmount").setValue(actPayAmount);
    		row.getCell("actDate").setValue(info.getActRevDate());
    		
    		if(appAmount.compareTo(actPayAmount) <= 0) 	{  //Ӧ��С��ʵ��ʱ��ɫ
    			row.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
    		}
    	}
    }
    
    /**
     * �����տ��Table  
     */
    private void fillTable2()
    {
    	this.kDTable2.checkParsed();
    	this.kDTable2.getTreeColumn().setDepth(2);
    	
    	this.kDTable2.getColumn("number").getStyleAttributes().setHided(true);
    	int actColumnIndex = this.kDTable2.getColumnIndex("actAmount");
    	IColumn hasTrasfCol = this.kDTable2.addColumn(actColumnIndex+1);
    	hasTrasfCol.setKey("hasTrasfAmount");
    	IRow headRow = this.kDTable2.getHeadRow(0); 
    	int isOutIndex = this.kDTable2.getColumnIndex("isOut");
    	headRow.getCell(hasTrasfCol.getColumnIndex()).setValue("��ת���");
    	headRow.getCell(isOutIndex).setValue("�Ƿ���ת��");
    	headRow.getCell(actColumnIndex).setValue("ʵ�ս��");
    	
    	PurchasePayListEntryCollection coll = this.purchase.getPayListEntry();
    	
    	if(coll == null)
    		return;
    	
		int tempTop = 0;
		String tempReference = "";
    	
    	for(int i = 0; i < coll.size(); i ++)    	{
    		PurchasePayListEntryInfo info = coll.get(i);
    		if(!MoneyTypeEnum.EarnestMoney.equals(info.getMoneyDefine().getMoneyType()))
				continue;
    		
    		String tempId = info.getId().toString();
    		IRow row = this.kDTable2.addRow();
    		row.setTreeLevel(0);
    		row.setUserObject(info);
    		row.getCell("id").setValue(tempId);
    		row.getCell("number").setValue(null);	//...
    		row.getCell("choose").setValue(Boolean.FALSE);
    		row.getCell("actAmount").setValue(info.getAllRemainAmount());
    		row.getCell("moneyName").setValue(info.getMoneyDefine());
    		row.getCell("hasTrasfAmount").setValue(info.getHasTransferredAmount());
    		if(info.getActRevAmount().compareTo(FDCHelper.ZERO)>0 && info.getAllRemainAmount().compareTo(FDCHelper.ZERO)<=0) 	{
    			row.getCell("isOut").setValue(new String("��"));
    			row.getStyleAttributes().setLocked(true);    		
    			row.getCell("choose").getStyleAttributes().setLocked(true);
    			row.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
    		}else {
    			row.getCell("isOut").setValue(new String("��"));
    		}
    		
    		try {
				FDCReceivingBillEntryCollection entryColl = FDCReceivingBillEntryFactory.getRemoteInstance()
						.getFDCReceivingBillEntryCollection("select revAmount,moneyDefine.name,settlementType.name where revListId ='"+tempId+"' ");
				for(int ii=0;ii<entryColl.size();ii++) {
					IRow tempRow = this.kDTable2.addRow();
					tempRow.setTreeLevel(1);
					tempRow.getStyleAttributes().setLocked(true);
					SettlementTypeInfo thisSetType = entryColl.get(ii).getSettlementType();
					tempRow.getCell("settlementType").setValue(thisSetType);
					tempRow.getCell("actAmount").setValue(entryColl.get(ii).getRevAmount());
					tempRow.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
				}
			} catch (BOSException e) {
				e.printStackTrace();
			} 
    		
    		if (!tempReference.equalsIgnoreCase(tempId)){
				tempTop = i;
				tempReference = tempId;
			} else	{
				// �ںϵ�Ԫ��
				this.kDTable2.getMergeManager().mergeBlock(tempTop, 1, i, 1);
			}
    		
        	this.kDTable2.expandTreeColumnTo(0);
    	}
    }
    
    /**
     * output class constructor
     */
    public ZhuanKuanEditUI() throws Exception
    {
        super();
    }

    /**
     * output btOk_actionPerformed method
     */
    protected void btOk_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	boolean debug = false;
    	BigDecimal allLimitAmount = null;
    	for(int i = 0; i < this.kDTable2.getRowCount(); i ++)  	{
    		IRow row = this.kDTable2.getRow(i);
    		if(!(row.getUserObject() instanceof PurchasePayListEntryInfo)) continue;
    		
    		boolean td = ((Boolean)row.getCell("choose").getValue()).booleanValue();
    		if(td)	{
    			if(!debug)	{
    				debug = true;
    				
    				proEntryInfo = (PurchasePayListEntryInfo)row.getUserObject();
    				allLimitAmount = proEntryInfo.getAllRemainAmount();    				
    			}else{
    				MsgBox.showInfo("һ��ֻ��ѡ��һ��������ϸ!");
    				return;
    			}
    		}
    	}
    	if(!debug){
    		MsgBox.showWarning("��ѡ����¥�����տ���ϸ��");
    		return;
    	}
   
    	BigDecimal allTrsfAmount = FDCHelper.ZERO;		//��ת�Ľ��
    	for(int i = 0; i < this.kDTable1.getRowCount(); i ++) 	{
    		IRow row = this.kDTable1.getRow(i);    		
    		BigDecimal inAmount = FDCHelper.ZERO; 
    		Object obj = row.getCell("inAmount").getValue();
    		if(obj instanceof BigDecimal) {
    			inAmount = (BigDecimal)obj;
    			if(inAmount.compareTo(FDCHelper.ZERO)<0) {
            		MsgBox.showWarning("ת�����Ϊ������");
            		return;   				
    			}else if(inAmount.compareTo(FDCHelper.ZERO)>0) {
    				if(row.getCell("settlementType").getValue()==null)	{
                		MsgBox.showWarning("��"+(row.getRowIndex()+1)+"�н��㷽ʽ����Ϊ�գ�");
                		return; 					
    				}
    			}
    		}
    		if(inAmount.compareTo(FDCHelper.ZERO)==0) continue;
    		
    		allTrsfAmount = allTrsfAmount.add(inAmount);
    		
    		PurchasePayListEntryInfo payListInfo = (PurchasePayListEntryInfo)row.getUserObject();
    		payListInfo.setAppAmount(inAmount);	//��ʱ�洢
    		
    		payListColl.add(payListInfo);
    		settleTypeColl.add((SettlementTypeInfo)row.getCell("settlementType").getValue());
    	}
    	
    	if(allTrsfAmount.compareTo(FDCHelper.ZERO) == 0)  	{
    		MsgBox.showWarning("û����Ҫ�����Ϲ����տ���ϸ��");
    		return;
    	}
    	
    	if(allLimitAmount.compareTo(allTrsfAmount) < 0){
    		MsgBox.showWarning("����ת����ϼ�("+allTrsfAmount+")���ܴ��ڶ���Ŀ�ת���("+allLimitAmount+")!");
    		return;
    	}
    	
       
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				LongTimeDialog longTimeDialog = UITools.getDialog(ZhuanKuanEditUI.this);
				longTimeDialog.setLongTimeTask(new ILongTimeTask()
				{

					public void afterExec(Object arg0)	throws Exception
					{
						UITools.showMsg(ZhuanKuanEditUI.this, "ת����ɣ�", false);
					}
					public Object exec() throws Exception
					{
						FDCReceivingBillInfo fdcRevBillInfo = GenFdcTrasfBillFactory.genTrasfBill(null, proEntryInfo.getId().toString()); 
						for(int i=0;i<payListColl.size();i++)
							GenFdcTrasfBillFactory.setTrasfEntry(null, fdcRevBillInfo, payListColl.get(i).getAppAmount(), settleTypeColl.get(i)
												,payListColl.get(i).getMoneyDefine(),null, payListColl.get(i).getId().toString(),RevListTypeEnum.purchaseRev
												,proEntryInfo.getMoneyDefine(), proEntryInfo.getId().toString(), RevListTypeEnum.purchaseRev);
						GenFdcTrasfBillFactory.submitNewTrasfBill(null, fdcRevBillInfo, "com.kingdee.eas.fdc.sellhouse.app.SheRevNoHandle");
						return "";
					}
					
				});
				longTimeDialog.show();
			}
		});
		this.destroyWindow();
    }

    /**
     * output btQuit_actionPerformed method
     */
    protected void btQuit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	this.destroyWindow();
    }    
    

}