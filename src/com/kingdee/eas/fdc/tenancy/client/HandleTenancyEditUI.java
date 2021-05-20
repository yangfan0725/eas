/**
 * ���޽��ӽ���
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.Room;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.tenancy.QuitTenancyFactory;
import com.kingdee.eas.fdc.tenancy.QuitTenancyInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillCollection;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyContractTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntry;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;


/**
 * output class name
 */
public class HandleTenancyEditUI extends AbstractHandleTenancyEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(HandleTenancyEditUI.class);
    
    private ItemAction[] hiddenAction = {this.actionEdit,this.actionAddNew,this.actionSave,this.actionCopy
    		,this.actionPrint,this.actionPrintPreview,this.actionFirst,this.actionPre,this.actionNext,
    		this.actionLast,this.actionSubmit,this.actionRemove,this.actionCancel,this.actionCancelCancel,this.actionAttachment};
    
    //���մ������ĺ�ͬ
	TenancyBillInfo tenancyBillInfo = null;
    
    /**
     * output class constructor
     */
    public HandleTenancyEditUI() throws Exception
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

	protected IObjectValue createNewData()
	{
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception
	{
		return RoomFactory.getRemoteInstance();
	}
	
	public void onLoad() throws Exception
	{
		super.onLoad();
		
		this.customerTable.checkParsed();
		this.roomTable.checkParsed();
		this.initUI();
		TenancyClientHelper.hideButton(hiddenAction);
		String tenancyBillId = (String) this.getUIContext().get("tenancyBillId");

		if(tenancyBillId == null)
		{
			logger.error("���������л�ȡ tenancyBillId ������ʱ��Ϊ��ֵ...");
			MsgBox.showInfo("û���ҵ�������ĺ�ͬ���޷����н��Ӳ�����");
			this.abort();
		}
		tenancyBillInfo = TenancyHelper.getTenancyBillInfo(tenancyBillId);
		
		this.fillRoomTableByTenancyBill(tenancyBillInfo);
		this.fillCustomerTableByTenancyBill(tenancyBillInfo);
		this.fillTenancyBillInfo(tenancyBillInfo);
	}
	
	/**
	 * ���ݵõ��ĺ�ͬ��Ϣ����䷿�����Ϣ
	 * @param tenancyBillInfo
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void fillRoomTableByTenancyBill(TenancyBillInfo tenancyBillInfo) throws BOSException, EASBizException
	{
		if(tenancyBillInfo == null)
		{
			logger.warn("�������ĺ�ͬΪ��ѽ��զ���Ŷ...");
			return;
		}
		
		TenancyRoomEntryCollection tenancyRoomEntryColl = tenancyBillInfo.getTenancyRoomList();

		for(int i = 0; i< tenancyRoomEntryColl.size(); i ++)
		{
			//һ����ͬ������Ӧ�ķ���û���ˣ�������������ô��
			if(tenancyRoomEntryColl.get(i).getRoom() != null)
			{
				TenancyRoomEntryInfo tenancyRoomEntryInfo = tenancyRoomEntryColl.get(i);
				tenancyRoomEntryInfo.setTenancy(tenancyBillInfo);
				
				IRow row = this.roomTable.addRow();
				
				row.setUserObject(tenancyRoomEntryInfo);
				
				setRoomHandleStateOnRow(row, tenancyRoomEntryInfo);
			}
			else
			{
				logger.warn("�ú�ͬ����Ӧ�ķ���û�ˣ��ε�...");
			}
		}
	}
	
	/**
	 * ���ݺ�ͬ��Ϣ�����ͻ����
	 * @param tenancyBillInfo
	 */
	private void fillCustomerTableByTenancyBill(TenancyBillInfo tenancyBillInfo)
	{
		if(tenancyBillInfo == null)
		{
			logger.warn("�������ĺ�ͬΪ��ѽ��զ���Ŷ...");
			return;
		}
		
		TenancyCustomerEntryCollection tenancyCustomerEntryColl = tenancyBillInfo.getTenCustomerList();

		for(int i = 0; i< tenancyCustomerEntryColl.size(); i ++)
		{
			//һ����ͬ������Ӧ�Ŀͻ�û���ˣ�������������ô��
			if(tenancyCustomerEntryColl.get(i).getFdcCustomer() != null)
			{
				FDCCustomerInfo fdcCustomer =tenancyCustomerEntryColl.get(i).getFdcCustomer();
				
				IRow row = this.customerTable.addRow();
				
				row.setUserObject(fdcCustomer);
				row.getCell("proporation").setValue(tenancyCustomerEntryColl.get(i).getPropertyPercent());
				row.getCell("customerName").setValue(fdcCustomer.getName());
				row.getCell("postalCode").setValue(fdcCustomer.getPostalcode());
				row.getCell("communicateTel").setValue(fdcCustomer.getPhone());
				row.getCell("certificateName").setValue(fdcCustomer.getCertificateName());
				row.getCell("certificateId").setValue(fdcCustomer.getCertificateNumber());
				row.getCell("communicateAddress").setValue(fdcCustomer.getAddress());
				row.getCell("bookedDate").setValue(fdcCustomer.getCreateTime());
				row.getCell("remark").setValue(fdcCustomer.getDescription());
			}
			else
			{
				logger.warn("�����ͬ����Ӧ�Ŀͻ�û��Ŷ...");
			}
		}
	
	}
	
	/**
	 * ����ͬ�������Ϣ
	 * @param tenancyBillInfo
	 */
	private void fillTenancyBillInfo(TenancyBillInfo tenancyBillInfo)
	{
		if(tenancyBillInfo == null)
			return;
		
		this.txtBillNumber.setText(tenancyBillInfo.getNumber());
		if(tenancyBillInfo.getAgency() != null)
		{
			this.txtAgencyCompany.setText(tenancyBillInfo.getAgency().getName());
		}
		if(tenancyBillInfo.getTenancyAdviser() != null)
		{
			this.txtBillAdviser.setText(tenancyBillInfo.getTenancyAdviser().getName());
		}
		this.txtBillCreator.setText(tenancyBillInfo.getCreator().getName());
		this.txtBillLease.setText(new Integer(tenancyBillInfo.getLeaseTime()).toString());
		
		this.txtBillName.setText(tenancyBillInfo.getName());
		if(tenancyBillInfo.getTenancyType() != null)
		{
			this.txtBillType.setText(tenancyBillInfo.getTenancyType().getAlias());
		}
	}
	
	/**
	 * ��ʼ����Ԫ
	 *
	 */
	private void initUI()
	{
	    KDCheckBox roomItem = new KDCheckBox();
	    roomItem.addActionListener(new ActionListener()
	    		{
					public void actionPerformed(ActionEvent arg0)
					{
						//����÷�̫�����ˣ�ֻ��Ϊ���ø�ѡ��� ����ת�ƣ��Ա�ڶ��ε�����ʱ�������� table_Clicked�¼�
						kDPanel1.requestFocus();
					}
	    		});
	    ICellEditor roomEditorItem = new KDTDefaultCellEditor(roomItem);
	    this.roomTable.getColumn("item").setEditor(roomEditorItem);
	    this.customerTable.getColumn("proporation").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	    this.customerTable.getColumn("bookedDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
	    this.roomTable.getColumn("bizDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
	    
	    for(int i = 0; i < this.customerTable.getColumnCount(); i ++)
	    {
	    	this.customerTable.getColumn(i).getStyleAttributes().setLocked(true);
	    }
	    for(int i = 1; i < this.roomTable.getColumnCount(); i ++)
	    {
	    	this.roomTable.getColumn(i).getStyleAttributes().setLocked(true);
	    }
	}
	
	private void setRoomHandleStateOnRow(IRow row, TenancyRoomEntryInfo tenancyRoomEntryInfo) throws BOSException, EASBizException{
		row.getCell("room").setValue(tenancyRoomEntryInfo.getRoomLongNum());
		row.getCell("item").setValue(Boolean.FALSE);
		
    	//���жϵ�ǰ�������ĺ�ͬ������Ѻ�����⣬����������Ļ�����˵����ν��ӵķ����� ���⽻�ӵ�
    	if(/*TenancyBillStateEnum.DepositReved.equals(tenancyBillInfo.getTenancyState())
    			||*/ TenancyBillStateEnum.PartExecuted.equals(tenancyBillInfo.getTenancyState()))
    	{
    		row.getCell("handleType").setValue("����");
    		if(tenancyRoomEntryInfo.getActDeliveryRoomDate() != null){
    			row.getCell("item").setValue(Boolean.TRUE);
    			row.getCell("bizDate").setValue(tenancyRoomEntryInfo.getActDeliveryRoomDate());
    			row.getStyleAttributes().setLocked(true);
    			return;
    		}
    	}
    	//ִ��״̬��
    	else if(TenancyBillStateEnum.Executing.equals(tenancyBillInfo.getTenancyState()))
    	{
    		//�ȿ�������û�����ⵥ,�����ⵥ�Ļ����ж����Ƿ��Ѿ��շ�
    		if(this.getQuitTenancyInfoByTenancyBill(this.tenancyBillInfo))
    		{
    			row.getCell("handleType").setValue("�շ�");
    			if (tenancyRoomEntryInfo.getActQuitTenDate() != null){
    				row.getCell("item").setValue(Boolean.TRUE);
    				row.getCell("bizDate").setValue(tenancyRoomEntryInfo.getActQuitTenDate());
    				row.getStyleAttributes().setLocked(true);
    				return;
    			}
    		}else{
    			// û�����ⵥ����ȥ�������Ƿ��з��Ͻ���������������ͬ
    			String targetTenId = TenancyHelper.getTargetTenIdBySrcTenancyId(tenancyBillInfo.getId().toString());
    			if(targetTenId != null){
    				TenancyBillInfo targetTen = TenancyHelper.getTenancyBillInfo(targetTenId);
    				//Ŀ���ͬ��������Ѻ����ִ��״̬���Խ��н��ӷ���
    				if(/*TenancyBillStateEnum.DepositReved.equals(targetTen.getTenancyState())  ||*/  
    						TenancyBillStateEnum.PartExecuted.equals(targetTen.getTenancyState())){
    					row.getCell("handleType").setValue(targetTen.getTenancyType() + "����");
    					if (tenancyRoomEntryInfo.getActQuitTenDate() != null){
    	    				row.getCell("item").setValue(Boolean.TRUE);
    	    				row.getCell("bizDate").setValue(tenancyRoomEntryInfo.getActQuitTenDate());
    	    				row.getStyleAttributes().setLocked(true);
    	    				return;
    	    			}
    				}else{
    					//���Ŀ���ͬΪ����״̬(δ��������Ѻ��),���÷�����Ŀ���ͬ�в�����,���Խ����˷�����
    					boolean existInTargetBill = false;
    					TenancyRoomEntryCollection targetTenRooms = targetTen.getTenancyRoomList();
    					for(int j=0; j<targetTenRooms.size(); j++){
    						TenancyRoomEntryInfo targetTenRoom = targetTenRooms.get(j);
    						if(targetTenRoom.getRoomLongNum() != null  &&  targetTenRoom.getRoomLongNum().equals(tenancyRoomEntryInfo.getRoomLongNum())){
    							existInTargetBill = true;
    							break;
    						}
    					}
    					if(!existInTargetBill){
    						row.getCell("handleType").setValue("�շ�");
    						if (tenancyRoomEntryInfo.getActQuitTenDate() != null){
        	    				row.getCell("item").setValue(Boolean.TRUE);
        	    				row.getCell("bizDate").setValue(tenancyRoomEntryInfo.getActQuitTenDate());
        	    				row.getStyleAttributes().setLocked(true);
        	    				return;
        	    			}
    					}else{
    						row.getCell("handleType").setValue("���ܽ���");
    						row.getStyleAttributes().setLocked(true);
    					}
    				}
    			}else{
    				row.getCell("handleType").setValue("���ܽ���");
    				row.getStyleAttributes().setLocked(true);
    			}    			
    		}
    	}// û������״̬��
    	else
    	{
    		row.getCell("handleType").setValue("���ܽ���");
    		row.getStyleAttributes().setLocked(true);
//    		logger.warn("SHIT���ҵ��˼�����ִ�У����ǰ�ִ�У�Ҳ��������Ѻ������״̬�ĺ�ͬ....");
//    		MsgBox.showInfo("ϵͳ�߼������Ѽ�¼��־�����֪����Ա��");
//    		this.abort();
    	}
	}
	
	/**
	 * �ж�ÿһ�з�¼�����ܽ��еķ��佻�Ӳ���
	 */
	protected void roomTable_tableClicked(KDTMouseEvent e) throws Exception
	{
		/*
		this.roomTable.checkParsed();
	    if(roomTable.getRowCount()<1)
	    {
	    	return;
	    }
	    //ֻ�����ǵ���Ǹ�����ѡ�����У���ȥ���д���
	    if(this.roomTable.getSelectManager().getActiveColumnIndex() != this.roomTable.getColumnIndex("item"))
	    {	
	    	return;
	    }
	    int rowIndex = this.roomTable.getSelectManager().getActiveRowIndex();
	    IRow row = this.roomTable.getRow(rowIndex);
	    TenancyRoomEntryInfo tenancyRoomEntryInfo = (TenancyRoomEntryInfo) row.getUserObject();
	    //����֮��Ľ����ԭ�еĽ�����෴��
	    if(row.getCell("item").getValue() == Boolean.TRUE)
	    {
	    	return;
	    }//ֻ�и�ѡ��ѡ���ˣ���ȥ�����ж�
	    else
	    {
	    	
	    }
	    */
	}
	
	public void onShow() throws Exception
	{
		super.onShow();
	}
	/**
	 * ���Ҹú�ͬ�Ƿ��������״̬�����ⵥ
	 * @param tenancyBillInfo
	 * @return
	 */
	private boolean getQuitTenancyInfoByTenancyBill(TenancyBillInfo tenancyBillInfo)
	{
		if(tenancyBillInfo == null)
			return false;
		boolean debug = false;
	
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenancyBill.id",tenancyBillInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		
		try
		{
			if(QuitTenancyFactory.getRemoteInstance().exists(filter))
				debug = true;
			else
				debug = false;
		} catch (EASBizException e)
		{
			super.handUIException(e);
		} catch (BOSException e)
		{
			super.handUIException(e);
		}
		return debug;
	}
	
	/**
	 * �ڽ��Ӳ�����ȥ�ı�һ����ͬ��״̬
	 * ��Ϊ����ͬ��Ϊ ����ִ�� ״̬�� ִ��״̬����ֹ״̬��
	 * @param tenancyBillInfo
	 */
	private void setTenancyBillExpiration(TenancyBillInfo tenancyBillInfo)
	{	
		if(tenancyBillInfo == null)
			return;
		
		//����ú�ͬ�Ѿ���ִ���е�״̬����֤�����ǶԵ�ǰ��ͬ���ڲ��������շ��Ĳ��������жϷ����¼���շ�ʱ�伴�ɡ�
		if(TenancyBillStateEnum.Executing.equals(tenancyBillInfo.getTenancyState()))
		{
			//����շ�
			if(this.isCompleteDeal("shoufang",tenancyBillInfo))
			{
				//��Ϊ������ֹ״̬
				tenancyBillInfo.setTenancyState(TenancyBillStateEnum.Expiration);
				
				SelectorItemCollection selectorItemColl = new SelectorItemCollection();
				selectorItemColl.add("tenancyState");
				
				try
				{
					TenancyBillFactory.getRemoteInstance().updatePartial(tenancyBillInfo,selectorItemColl);
				} catch (EASBizException e)
				{
					super.handUIException(e);
				} catch (BOSException e)
				{
					super.handUIException(e);
				}
			}
		}
		//����ú�ͬ���ѽ�Ѻ�����⣬���߲���ִ��״̬����֤����ǰ���е�ʱ�򽻷��Ĳ�����
		else if(/*TenancyBillStateEnum.DepositReved.equals(tenancyBillInfo.getTenancyState())
				||*/ TenancyBillStateEnum.PartExecuted.equals(tenancyBillInfo.getTenancyState()))
		{
			//��ɽ���
			if(this.isCompleteDeal("jiaofang",tenancyBillInfo))
			{
				//��Ϊִ����״̬
				tenancyBillInfo.setTenancyState(TenancyBillStateEnum.Executing);
				
				SelectorItemCollection selectorItemColl = new SelectorItemCollection();
				selectorItemColl.add("tenancyState");
				
				try
				{
					TenancyBillFactory.getRemoteInstance().updatePartial(tenancyBillInfo,selectorItemColl);
				} catch (EASBizException e)
				{
					super.handUIException(e);
				} catch (BOSException e)
				{
					super.handUIException(e);
				}
			}//û��ȫ����
			else if(this.isCompleteDeal("banjiaofang",tenancyBillInfo))
			{

				//��Ϊ��ִ����״̬
				tenancyBillInfo.setTenancyState(TenancyBillStateEnum.PartExecuted);
				
				SelectorItemCollection selectorItemColl = new SelectorItemCollection();
				selectorItemColl.add("tenancyState");
				
				try
				{
					TenancyBillFactory.getRemoteInstance().updatePartial(tenancyBillInfo,selectorItemColl);
				} catch (EASBizException e)
				{
					super.handUIException(e);
				} catch (BOSException e)
				{
					super.handUIException(e);
				}
			
			}
		}
	}
	
	/**
	 * ���ݺ�ͬȥ�ҵ�����������з���
	 * @param tenancyBillInfo
	 * @return
	 */
	private TenancyRoomEntryCollection getTenancyRoomEntryCollByTenancyBill(TenancyBillInfo tenancyBillInfo)
	{
		if(tenancyBillInfo == null)
			return null;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenancy.id",tenancyBillInfo.getId().toString()));
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.setFilter(filter);
		
		TenancyRoomEntryCollection tenancyRoomEntryColl = null;
		
		try
		{
			tenancyRoomEntryColl = TenancyRoomEntryFactory.getRemoteInstance().getTenancyRoomEntryCollection(view);
		} catch (BOSException e)
		{
			super.handUIException(e);
		}
		return tenancyRoomEntryColl;
	}
	
	/**
	 * ͨ�������ʱ���ֶ�����ȥ�жϸú�ͬ�µķ�����û����ɽ���
	 * @param deanName  ex:  jiaofang   shoufang banjiaofang
	 * @param tenancyBillInfo
	 * @return
	 */
	private boolean isCompleteDeal(String dealName,TenancyBillInfo tenancyBillInfo)
	{
		boolean debug = false;
		int temp = 0;
		
		TenancyRoomEntryCollection tenancyRoomEntryColl = this.getTenancyRoomEntryCollByTenancyBill(tenancyBillInfo);
		if(tenancyRoomEntryColl == null)
			return false;
		for(int i = 0;i < tenancyRoomEntryColl.size(); i ++)
		{
			TenancyRoomEntryInfo tenancyRoomEntryInfo = tenancyRoomEntryColl.get(i);
			if("jiaofang".equalsIgnoreCase(dealName) || "banjiaofang".equalsIgnoreCase(dealName))
			{
				if(tenancyRoomEntryInfo.getActDeliveryRoomDate() != null)
				{
					temp ++;
				}
			}
			else if("shoufang".equalsIgnoreCase(dealName))
			{
				if(tenancyRoomEntryInfo.getActQuitTenDate() != null)
				{
					temp ++;
				}
			}
		}
		// �Ƿ���û����ȫ����
		if ("banjiaofang".equalsIgnoreCase(dealName))
		{
			if(temp == 0)
			{
				debug = false;
			}
			else if(temp<tenancyRoomEntryColl.size()&&temp>0)
			{
				debug =true;
			}

		} else
		{
			if (temp == tenancyRoomEntryColl.size())
			{
				debug = true;
			} else
			{
				debug = false;
			}
		}
		return debug;
	}
	
	/**
	 * �жϸ÷����Ƿ����������⣬���⣬ת������Ч״̬�µĺ�ͬ
	 * ��ǰ����ִ�еĺ�ͬ����Ӧ���ų�����
	 * ��Ч״̬ �����ѽ�Ѻ�����⣬��ִ��״̬��
	 * @param room
	 * @param tenancyBillInfo
	 * @return
	 */
	private boolean isExistValidTenancyBillExceptSelf(TenancyRoomEntryInfo tenancyRoomEntryInfo,TenancyBillInfo tenancyBillInfo)
	{
		if(tenancyRoomEntryInfo == null || tenancyBillInfo == null)
			return false;
		boolean debug = false;
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id",tenancyRoomEntryInfo.getRoom().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("tenancy.id",tenancyBillInfo.getId().toString(),CompareType.NOTINCLUDE));
//		filter.getFilterItems().add(new FilterItemInfo("tenancy.tenancyState",TenancyBillStateEnum.DEPOSITREVED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("tenancy.tenancyState",TenancyBillStateEnum.PartExecuted));
		
		filter.setMaskString("(#0 and #1 and (#2 or #3))");
		
		try
		{
			debug = TenancyRoomEntryFactory.getRemoteInstance().exists(filter);
		} catch (EASBizException e)
		{
			super.handUIException(e);
		} catch (BOSException e)
		{
			super.handUIException(e);
		}
		return debug;
	}
	
	/**
	 * ������Ӱ�ť
	 */
	public void actionHandleTenancy_actionPerformed(ActionEvent e) throws Exception
	{
		if(this.roomTable.getRowCount() < 1)
		{
			MsgBox.showInfo("û�з���ɽ��н��Ӳ�����");
			this.abort();
		}
		/* �������tenancyRoomEntryColl�����ж�
		int temp = 0;
		for(int i = 0; i < this.roomTable.getRowCount(); i ++)
		{
			IRow row = this.roomTable.getRow(i);
			if(row.getCell("item").getValue() == Boolean.FALSE)
			{
				temp ++;
			}
		}
		if(temp == this.roomTable.getRowCount())
		{
			MsgBox.showInfo("��ѡ��Ҫ���ӵķ��䣡");
			this.abort();
		}
		*/
		
		TenancyRoomEntryCollection tenancyRoomEntryColl = new TenancyRoomEntryCollection();
		for(int i = 0; i < this.roomTable.getRowCount(); i ++)
		{
			IRow row = this.roomTable.getRow(i);
			//����ס�ı�ʾ֮ǰ�Ѿ����ӹ�����,���߲��ܽ��н���
			if(row.getStyleAttributes().isLocked()){
				continue;
			}
			if(row.getCell("item").getValue() == Boolean.TRUE)
			{
				TenancyRoomEntryInfo tenancyRoomEntryInfo = (TenancyRoomEntryInfo) row.getUserObject();
				tenancyRoomEntryColl.add(tenancyRoomEntryInfo);
			}
		}
		
		if(tenancyRoomEntryColl.isEmpty()){
			MsgBox.showInfo(this, "û����Ҫ���ӵķ���!");
			return;
		}
		
		//TODO �����з��գ���Ҫ�����������
		//�����������º�ͬ���Ϻ�ͬ���潻��ʱ�䣬��Դ�ϵ�״̬�ĸı�
		//TenancyBillFactory.getRemoteInstance().handleTenancyRoom(tenancyRoomEntryColl,tenancyBillInfo);
		//���ĺ�ͬ״̬
		this.updateTenancyBillState(tenancyBillInfo);
		MsgBox.showInfo("���ӳɹ���");
		this.disposeUIWindow();
	}
	
	/**
	 * ���ĺ�ͬ��״̬��������ĵĲ����Ǵ���ĺ�ͬ��״̬����Ҫ�ж����ĺ�����ͬ�������߰���ʲô�ĺ�ͬһ�����
	 * @param tenancyBillInfo
	 */
	private void updateTenancyBillState(TenancyBillInfo billInfo)
	{
		if(billInfo == null)
			return;
		//��Ҫ�����ݿ�������ȡֵһ��
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",billInfo.getId().toString()));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("oldTenancyBill.*");
		view.getSelector().add("*");
		view.setFilter(filter);
		
		TenancyBillInfo tenancyBillInfo = null;
		try
		{
			tenancyBillInfo = TenancyBillFactory.getRemoteInstance().getTenancyBillCollection(view).get(0);
		} catch (BOSException e)
		{
			super.handUIException(e);
		}
		if(tenancyBillInfo == null)
		{
			logger.warn("������...");
			MsgBox.showInfo("ϵͳ�����Ѽ�¼��־��");
			this.abort();
		}
		//����ǰ�ִ��״̬ ���ѽ�Ѻ�����⣬��˵����ǰ�ǽ������������п����оɺ�ͬ��Ҳ
		if(TenancyBillStateEnum.PartExecuted.equals(tenancyBillInfo.getTenancyState())
				/*|| TenancyBillStateEnum.DepositReved.equals(tenancyBillInfo.getTenancyState())*/)
		{
			//�ɺ�ͬ
			TenancyBillInfo oldTenancyBillInfo = tenancyBillInfo.getOldTenancyBill();
			if(oldTenancyBillInfo == null)
			{
				//��ɽ���
				if(isCompleteDeal("jiaofang",tenancyBillInfo))
				{
					tenancyBillInfo.setTenancyState(TenancyBillStateEnum.Executing);
				}
				else
				{
					//�Ƿ��ǰ뽻��
					if(isCompleteDeal("banjiaofang",tenancyBillInfo))
					{
						tenancyBillInfo.setTenancyState(TenancyBillStateEnum.PartExecuted);
					}
				}
			}//�оɺ�ͬ�������
			else
			{
//				��ɽ���
				if(isCompleteDeal("jiaofang",tenancyBillInfo))
				{
					tenancyBillInfo.setTenancyState(TenancyBillStateEnum.Executing);
				}
				else
				{
					//�Ƿ��ǰ뽻��
					if(isCompleteDeal("banjiaofang",tenancyBillInfo))
					{
						tenancyBillInfo.setTenancyState(TenancyBillStateEnum.PartExecuted);
					}
				}
				//����ɺ�ͬ
				if(isCompleteDeal("shoufang",oldTenancyBillInfo))
				{
					oldTenancyBillInfo.setTenancyState(TenancyBillStateEnum.Expiration);
				}
				SelectorItemCollection selectorColl = new SelectorItemCollection();
				selectorColl.add("tenancyState");
				try
				{
					TenancyBillFactory.getRemoteInstance().updatePartial(oldTenancyBillInfo,selectorColl);
				} catch (EASBizException e)
				{
					super.handUIException(e);
				} catch (BOSException e)
				{
					super.handUIException(e);
				}
			}
			
		}
		else if(TenancyBillStateEnum.Executing.equals(tenancyBillInfo.getTenancyState()))
		{
			FilterInfo filter1 = new FilterInfo();
			filter1.getFilterItems().add(new FilterItemInfo("oldTenancyBill.id",tenancyBillInfo.getId().toString()));
//			filter1.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.DEPOSITREVED_VALUE));
			filter1.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.PARTEXECUTED_VALUE));
			filter1.setMaskString("#0 and (#1 or #2)");
			
			EntityViewInfo view1 = new EntityViewInfo();
			view1.getSelector().add("*");
			view1.setFilter(filter1);
			
			TenancyBillCollection tenancyBillColl = null;
			try
			{
				tenancyBillColl = TenancyBillFactory.getRemoteInstance().getTenancyBillCollection(view1);
			} catch (BOSException e)
			{
				super.handUIException(e);
			}
			if(tenancyBillColl.size()>1)
			{
				logger.warn("һ������ִ�еĺ�ͬ���ҵ��˶������״̬���ѽ�Ѻ��������߰�ִ��״̬�ĺ�ͬ");
				MsgBox.showInfo("�����߼������Ѽ�¼��־��");
				this.abort();
			}
			
			TenancyBillInfo newTenancyBillInfo = tenancyBillColl.get(0);
			//���п���Ϊ�յ�
			if (newTenancyBillInfo != null)
			{
				// ��ɽ���
				if (isCompleteDeal("jiaofang", newTenancyBillInfo))
				{
					newTenancyBillInfo
							.setTenancyState(TenancyBillStateEnum.Executing);
				} else
				{
					// �Ƿ��ǰ뽻��
					if (isCompleteDeal("banjiaofang", newTenancyBillInfo))
					{
						newTenancyBillInfo
								.setTenancyState(TenancyBillStateEnum.PartExecuted);
					}
				}
				
				SelectorItemCollection selectorColl = new SelectorItemCollection();
				selectorColl.add("tenancyState");
				try
				{
					TenancyBillFactory.getRemoteInstance().updatePartial(newTenancyBillInfo,selectorColl);
				} catch (EASBizException e)
				{
					super.handUIException(e);
				} catch (BOSException e)
				{
					super.handUIException(e);
				}
			}
			if(isCompleteDeal("shoufang",tenancyBillInfo))
			{
				tenancyBillInfo.setTenancyState(TenancyBillStateEnum.Expiration);
			}
		}
		SelectorItemCollection selectorColl = new SelectorItemCollection();
		selectorColl.add("tenancyState");
		try
		{
			TenancyBillFactory.getRemoteInstance().updatePartial(tenancyBillInfo,selectorColl);
		} catch (EASBizException e)
		{
			super.handUIException(e);
		} catch (BOSException e)
		{
			super.handUIException(e);
		}
	}
}