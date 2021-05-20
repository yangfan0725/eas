package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryCollection;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryFactory;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryInfo;
import com.kingdee.eas.fdc.tenancy.HandleRoomEntrysInfo;
import com.kingdee.eas.fdc.tenancy.HandleStateEnum;
import com.kingdee.eas.fdc.tenancy.ITenancyEntryInfo;
import com.kingdee.eas.fdc.tenancy.QuitTenancyFactory;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyContractTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

public class SelectHandleRoomUI extends AbstractSelectHandleRoomUI
{
	private static final Logger logger = CoreUIObject.getLogger(SelectHandleRoomUI.class);

	public SelectHandleRoomUI() throws Exception
	{
		super();
	}

	protected void inOnload() throws Exception {
		// super.inOnload();
	}

	public void onLoad() throws Exception {
		initControl();
		this.tblRooms.checkParsed();
		initUI();
		if(pnlMain.getTabCount() == 2){
			this.pnlMain.remove(1);
		}
		TenancyBillInfo tenancyBillInfo = (TenancyBillInfo)this.getUIContext().get("tenancyBillInfo");
		if(tenancyBillInfo!=null)
		{
			TenancyRoomEntryCollection tenRoomColl = tenancyBillInfo.getTenancyRoomList();
			TenAttachResourceEntryCollection tenAttachColl = tenancyBillInfo.getTenAttachResourceList();
			if(tenAttachColl.size()+tenRoomColl.size()==0)
			{
				logger.warn("�ú�ͬ����Ӧ�ķ����������Դû�ˣ��ε�...");
			}else
			{
				for(int i=0;i<tenRoomColl.size();i++)
				{
					if(tenRoomColl.get(i).getRoom() != null)
					{
						TenancyRoomEntryInfo tenancyRoomEntryInfo = tenRoomColl.get(i);
						SelectorItemCollection sels = new SelectorItemCollection();
						sels.add("*");
						sels.add("tenancy.*");
						TenancyRoomEntryInfo tenRoomEntryInfo = TenancyRoomEntryFactory.getRemoteInstance().getTenancyRoomEntryInfo(new ObjectUuidPK(tenancyRoomEntryInfo.getId()),sels);
						tenRoomEntryInfo.setTenancy(tenancyBillInfo);
						this.tblRooms.checkParsed();
						IRow row = this.tblRooms.addRow();

						row.setUserObject(tenRoomEntryInfo);

						setRoomHandleStateOnRow(row, tenRoomEntryInfo,tenancyBillInfo);
					}
				}
				for(int i=0;i<tenAttachColl.size();i++)
				{
					if(tenAttachColl.get(i).getAttachResource()!=null)
					{
						TenAttachResourceEntryInfo tenAttachEntryInfo = tenAttachColl.get(i);
						SelectorItemCollection sels = new SelectorItemCollection();
						sels.add("*");
						sels.add("tenancyBill.*");
						TenAttachResourceEntryInfo tenAttEntryInfo = TenAttachResourceEntryFactory.getRemoteInstance().getTenAttachResourceEntryInfo(new ObjectUuidPK(tenAttachEntryInfo.getId()),sels);
						tenAttEntryInfo.setTenancyBill(tenancyBillInfo);
						this.tblAttach.checkParsed();
						IRow row = this.tblAttach.addRow();
						
						row.setUserObject(tenAttEntryInfo);
						setAttachHandleStateOnRow(row,tenAttEntryInfo);
					}
				}
			}
			
		}
	}
	
	/*
	 * ��ʾ��ͬ�е�������Դ��Ϣ
	 * */
	private void setAttachHandleStateOnRow(IRow row,TenAttachResourceEntryInfo info)
	{
		row.getCell("attachNumber").setValue(info.getAttachLongNum());
		row.getCell("item").setValue(Boolean.valueOf(false));
		row.getCell("tenancyState").setValue(info.getTenAttachState());
		if(info.getActDeliveryAttachResDate()==null)
		{
			row.getCell("handleDate").setValue(null);
		}else
		{
			row.getCell("handleDate").setValue(info.getActDeliveryAttachResDate());
		}
		row.getCell("handleState").setValue(info.getHandleState());
	}

	/*
	 * ��ʾ��ͬ�еķ�����Ϣ
	 * */
	private void setRoomHandleStateOnRow(IRow row, TenancyRoomEntryInfo tenancyRoomEntryInfo,TenancyBillInfo billInfo) throws BOSException, EASBizException{
		row.setUserObject(tenancyRoomEntryInfo);
		row.getCell("roomNumber").setValue(tenancyRoomEntryInfo.getRoomLongNum());
		row.getCell("item").setValue(Boolean.valueOf(false));
		row.getCell("tenancyState").setValue(tenancyRoomEntryInfo.getTenRoomState());
		if(tenancyRoomEntryInfo.getActDeliveryRoomDate()==null)
		{
			row.getCell("handleDate").setValue(null);
		}else
		{
			row.getCell("handleDate").setValue(tenancyRoomEntryInfo.getActDeliveryRoomDate());
		}
		row.getCell("handleState").setValue(tenancyRoomEntryInfo.getHandleState());
	}

	public void storeFields()
	{
		super.storeFields();
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
				pnlMain.requestFocus();
			}
		});
		ICellEditor roomEditorItem = new KDTDefaultCellEditor(roomItem);
		this.tblRooms.getColumn("item").setEditor(roomEditorItem);
		this.tblRooms.getColumn("handleDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		for(int i = 1; i < this.tblRooms.getColumnCount(); i ++)
		{
			this.tblRooms.getColumn(i).getStyleAttributes().setLocked(true);
		}
	}

	/**
	 * ���ض��ఴť
	 */
	private void initControl() {
		this.btnPrint.setEnabled(true);
		this.btnPrintPreview.setEnabled(true);
		this.menuEdit.setVisible(false);
		this.menuView.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuItemAddNew.setVisible(false);
		this.menuItemSave.setVisible(false);
		this.menuItemSubmit.setVisible(false);
		this.rMenuItemSubmit.setVisible(false);
		this.rMenuItemSubmitAndAddNew.setVisible(false);
		this.rMenuItemSubmitAndPrint.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.rMenuItemSubmitAndAddNew.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		this.btnAddNew.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnSave.setVisible(false);
		this.btnSubmit.setVisible(false);
		this.btnCopy.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.btnFirst.setVisible(false);
		this.btnPre.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnLast.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionPrint.setVisible(false);

	}

	/**
	 * �������ύ��Ҫ���ӵķ����������Դ��Ϣ
	 * <p>
	 * 
	 * �޸��ˣ�<tim_gao> <p>
	 * �޸�ʱ�䣺<2010/8/29> <p>
	 * �޸�������<488��,��Ҫ�ύ�ķ��������º�ͬ�ķ���ѭ�������> <p>
	 */

	protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e) throws Exception
	{
		super.btnConfirm_actionPerformed(e);
		List list = new ArrayList();
		List attachList = new ArrayList();
		if(this.tblRooms.getRowCount()>0)
		{
			for(int i=0;i<this.tblRooms.getRowCount();i++)
			{
				IRow row = tblRooms.getRow(i);
				if(row.getCell("item").getValue().equals(Boolean.valueOf(true)))
				{
					//���޷����¼
					TenancyRoomEntryInfo tenRoomEntrys = (TenancyRoomEntryInfo)row.getUserObject();
					//���ӷ�¼
					HandleRoomEntrysInfo handleRoomEntrys = new HandleRoomEntrysInfo();
					String roomid = tenRoomEntrys.getRoom().getId().toString();
					DealAmountEntryCollection dealColl = tenRoomEntrys.getDealAmounts();
					BigDecimal depositAmount = new BigDecimal(0);
					for(int j=0;j<dealColl.size();j++)
					{
						DealAmountEntryInfo dealInfo = dealColl.get(j);
						SelectorItemCollection sel = new SelectorItemCollection();
						sel.add("*");
						sel.add("moneyDefine.*");
						dealInfo = DealAmountEntryFactory.getRemoteInstance().getDealAmountEntryInfo(new ObjectUuidPK(dealInfo.getId()), sel);
						if(MoneyTypeEnum.DepositAmount.equals(dealInfo.getMoneyDefine().getMoneyType()))
						{
							BigDecimal amount = dealInfo.getAmount();
							amount = amount==null?new BigDecimal(0):amount;
							depositAmount = depositAmount.add(amount);
						}
					}
					//Ѻ����׸�
//					BigDecimal depositAmount = tenRoomEntrys.getDepositAmount();
//					depositAmount = depositAmount==null?new BigDecimal(0):depositAmount;
					BigDecimal firstPayRent = tenRoomEntrys.getFirstPayAmount();
					firstPayRent = firstPayRent==null?new BigDecimal(0):firstPayRent;
					BigDecimal rent = depositAmount.add(firstPayRent);
					//���޺�ͬ
					TenancyBillInfo info = tenRoomEntrys.getTenancy();
					//�÷����ѽ������
					BigDecimal recRent = TenancyHelper.getRecAmount(roomid,info.getId().toString());
					if(recRent==null)
					{
						recRent = new BigDecimal(0);
					}
					TenancyBillStateEnum tenState = info.getTenancyState();
					//��������Ϊ������ֻ�������������չ���ĺ�ͬ�Ͳ���ִ����Ҫ���ӵķ����չ���ĺ�ͬ
					if(TenancyBillStateEnum.Audited.equals(tenState) || TenancyBillStateEnum.PartExecuted.equals(tenState))
					{
							TenancyBillInfo oldBillInfo = info.getOldTenancyBill();
							//����º�ͬ��ת���Ļ����������ջ���û��
							if(TenancyContractTypeEnum.ChangeName.equals(info.getTenancyType()))
							{
								//���ԭ��ͬ��������ֹ״̬˵�����з���û���ջ���
								if(!TenancyBillStateEnum.Expiration.equals(oldBillInfo.getTenancyState()))
								{
									MsgBox.showInfo("ԭ��ͬ���仹û���ջ���");
									this.abort();
								}else
								{
									//�ҵ�ԭ��ͬ
									TenancyBillInfo targetTen = TenancyHelper.getTenancyBillInfo(oldBillInfo.getId().toString());
									TenancyRoomEntryCollection tenEntryColl = targetTen.getTenancyRoomList();
									//�鿴���ӵķ����Ƿ���ԭ��ͬ���䷶Χ��
									for(int k=0;k<tenEntryColl.size();k++)
									{
										TenancyRoomEntryInfo tenRoomInfo = tenEntryColl.get(k);
										if(tenRoomInfo.getRoom().getId().toString().equals(roomid))
										{
//											//�����չ�Ǯ��û��
//											if(recRent.equals(new BigDecimal(0)))
//											{
//												MsgBox.showInfo("����"+tenRoomEntrys.getRoomLongNum()+"��û�н���");
//												this.abort();
//											}else 
//											if(recRent.compareTo(rent)<0)
//											{
//												MsgBox.showInfo("����"+tenRoomEntrys.getRoomLongNum()+"û�н�������Ѻ��");
//												this.abort();
//											}else 
											if(tenRoomEntrys.getActDeliveryRoomDate()!=null)
											{
												MsgBox.showInfo("����"+tenRoomEntrys.getRoomLongNum()+"�Ѿ���������");
												this.abort();
											}else if(tenRoomEntrys.getActDeliveryRoomDate()==null)
											{
//											else if(recRent.compareTo(rent)>=0 && tenRoomEntrys.getActDeliveryRoomDate()==null)
//											{
												handleRoomEntrys.setHandleType("����");
												handleRoomEntrys.setOldHandleState(tenRoomEntrys.getHandleState());
												handleRoomEntrys.setTenancyRoom(tenRoomEntrys);
												list.add(handleRoomEntrys);
												break;
											}              					
										}
									}
								}
								//���ԭ��ͬ��ת���������ĺ�ͬ�Ļ����������;�Ϊת�⽻�Ӻ����⽻��
							}else if(oldBillInfo!=null && (TenancyBillStateEnum.ContinueTenancying.equals(oldBillInfo.getTenancyState())
									|| TenancyBillStateEnum.RejiggerTenancying.equals(oldBillInfo.getTenancyState())))
							{
								//ת���������Ļ�ֻ�ܶ��º�ͬ�������������㽻��������ͬʱ��ԭ��ͬ�ķ������ˣ�������״̬��д�����޺�ͬ�����¼�ͷ�����
								TenancyBillInfo targetTen = TenancyHelper.getTenancyBillInfo(oldBillInfo.getId().toString());
								TenancyRoomEntryCollection tenEntryColl = targetTen.getTenancyRoomList();
								List roomidlist = new ArrayList();
								for(int k=0;k<tenEntryColl.size();k++)
								{
									TenancyRoomEntryInfo tenRoomEnInfo = tenEntryColl.get(k);
									roomidlist.add(tenRoomEnInfo.getRoom().getId().toString());
								}
								for(int k=0;k<tenEntryColl.size();k++)
								{
									//TenancyRoomEntryInfo tenRoomEnInfo = tenEntryColl.get(k);
									//ת���������ķ�����ԭ��ͬ��Ҳ�еĽ�ת������⽻��
									if(TenancyClientHelper.isInclude(roomid, roomidlist))
									//if(tenRoomEnInfo.getRoom().getId().toString().equals(roomid))
									{
										//�����չ�Ǯ��û��
//										if(recRent.equals(new BigDecimal(0)))
//										{
//											MsgBox.showInfo("����"+tenRoomEntrys.getRoomLongNum()+"��û�н���");
//											this.abort();
//										}else 
//										if(recRent.compareTo(rent)<0)
//										{
//											MsgBox.showInfo("����"+tenRoomEntrys.getRoomLongNum()+"û�н�������Ѻ��");
//											this.abort();
//										}else 
										if(tenRoomEntrys.getActDeliveryRoomDate()!=null)
										{
											MsgBox.showInfo("����"+tenRoomEntrys.getRoomLongNum()+"�Ѿ���������");
											this.abort();
										}else if(tenRoomEntrys.getActDeliveryRoomDate()==null)
										{
//										else if(recRent.compareTo(rent)>=0 && tenRoomEntrys.getActDeliveryRoomDate()==null)
//										{
											handleRoomEntrys.setHandleType(info.getTenancyType()+"����");
											handleRoomEntrys.setOldHandleState(tenRoomEntrys.getHandleState());
											handleRoomEntrys.setTenancyRoom(tenRoomEntrys);
											list.add(handleRoomEntrys);
											break;
										}  
									}else
									{
										//ԭ��ͬ��û�з��������ͨ�Ľ�������
										//�����չ�Ǯ��û��
//										if(recRent.equals(new BigDecimal(0)))
//										{
//											MsgBox.showInfo("����"+tenRoomEntrys.getRoomLongNum()+"��û�н���");
//											this.abort();
//										}else 
//										if(recRent.compareTo(new BigDecimal(0))>0 && recRent.compareTo(rent)<0)
//										{
//											MsgBox.showInfo("����"+tenRoomEntrys.getRoomLongNum()+"û�н�������Ѻ��");
//											this.abort();
//										}
//										if(recRent.compareTo(rent)<0)
//										{
//											MsgBox.showInfo("����"+tenRoomEntrys.getRoomLongNum()+"û�н�������Ѻ��");
//											this.abort();
//										}else 
										if(tenRoomEntrys.getActDeliveryRoomDate()!=null)
										{
											MsgBox.showInfo("����"+tenRoomEntrys.getRoomLongNum()+"�Ѿ���������");
											this.abort();
										}else if(tenRoomEntrys.getActDeliveryRoomDate()==null)
										{
//										else if(recRent.compareTo(rent)>=0 && tenRoomEntrys.getActDeliveryRoomDate()==null)
//										{
											handleRoomEntrys.setHandleType("����");
											handleRoomEntrys.setOldHandleState(tenRoomEntrys.getHandleState());
											handleRoomEntrys.setTenancyRoom(tenRoomEntrys);
											list.add(handleRoomEntrys);
											break;
										}  
									}

								}
							}else
							{
								//����˵����ֱ�������ĺ�ͬ��Ҫ�����Ļ���Ҫ�����չ��������
//								if(recRent.equals(new BigDecimal(0)))
//								{
//									MsgBox.showInfo("����"+tenRoomEntrys.getRoomLongNum()+"��û�н���");
//									this.abort();
//								}else 
//								if(recRent.compareTo(rent)<0)
//								{
//									MsgBox.showInfo("����"+tenRoomEntrys.getRoomLongNum()+"û�н�������Ѻ��");
//									this.abort();
//								}else 
								if(tenRoomEntrys.getActDeliveryRoomDate()!=null)
								{
									MsgBox.showInfo("����"+tenRoomEntrys.getRoomLongNum()+"�Ѿ���������");
									this.abort();
								}
								else if(tenRoomEntrys.getActDeliveryRoomDate()==null)
								{
//								else if(recRent.compareTo(rent)>=0 && tenRoomEntrys.getActDeliveryRoomDate()==null)
//								{
									handleRoomEntrys.setHandleType("����");
									handleRoomEntrys.setOldHandleState(tenRoomEntrys.getHandleState());
									handleRoomEntrys.setTenancyRoom(tenRoomEntrys);
									list.add(handleRoomEntrys);
								}
							}
					}else if(TenancyBillStateEnum.RejiggerTenancying.equals(tenState)
							|| TenancyBillStateEnum.ContinueTenancying.equals(tenState))
					{
						String targetTenId = TenancyHelper.getTargetTenIdBySrcTenancyId(info.getId().toString());
						TenancyBillInfo targetTen = TenancyHelper.getTenancyBillInfo(targetTenId);
						TenancyRoomEntryCollection tenEntryColl = targetTen.getTenancyRoomList();
						//����º�ͬ�з������Ϊ0.˵���º�ֻͬ����������Դ����ôԭ��ͬ���з������ֱ���ջ�
						if(tenEntryColl.size()==0)
						{
							handleRoomEntrys.setHandleType("�շ�");
							handleRoomEntrys.setOldHandleState(tenRoomEntrys.getHandleState());
							handleRoomEntrys.setTenancyRoom(tenRoomEntrys);
							list.add(handleRoomEntrys);
						}else
						{
							if(this.getQuitTenancyInfoByTenancyBill(targetTen))
							{
								for(int m=0;m<tenEntryColl.size();m++)
								{
									TenancyRoomEntryInfo tenInfo = tenEntryColl.get(m);
									if(roomid.equals(tenInfo.getRoom().getId().toString()) && (HandleStateEnum.AlreadyCallBack.equals(tenInfo.getHandleState())
											|| HandleStateEnum.livingHouse.equals(tenInfo.getHandleState())))
									{
										MsgBox.showInfo("�÷����Ѿ����º�ͬ�н�����");
										this.abort();
									}else if(roomid.equals(tenInfo.getRoom().getId().toString()))
									{
										handleRoomEntrys.setHandleType("�շ�");
										handleRoomEntrys.setOldHandleState(tenRoomEntrys.getHandleState());
										handleRoomEntrys.setTenancyRoom(tenRoomEntrys);
										list.add(handleRoomEntrys);
									}
								}
								
							}else
							{
								for(int k=0;k<tenEntryColl.size();k++)
								{
									TenancyRoomEntryInfo tenRoomInfo = tenEntryColl.get(k);
									if(tenRoomInfo.getRoom().getId().toString().equals(roomid))
									{
										MsgBox.showInfo(targetTen.getTenancyType()+"���ӵķ���ֻ�ܵ��º�ͬ��ȥ����");
										this.abort();
									}else
									{
										if(TenancyBillStateEnum.Saved.equals(targetTen.getTenancyState()) || TenancyBillStateEnum.Submitted.equals(targetTen.getTenancyState())
												|| TenancyBillStateEnum.BlankOut.equals(targetTen.getTenancyState()))
										{
											MsgBox.showInfo("Ŀ���ͬ���������������������ĺ�ͬ�����շ�");
											this.abort();
										}
									}
								} //�����º�ͬ��Ҫ���ӵķ��������ķ�Ӧ�÷���ѭ���⣬��ȻҪ�����º�ͬ�еĽ��ӷ���ѭ��     tim_gao
								handleRoomEntrys.setHandleType("�շ�");
								handleRoomEntrys.setOldHandleState(tenRoomEntrys.getHandleState());
								handleRoomEntrys.setTenancyRoom(tenRoomEntrys);
								list.add(handleRoomEntrys);
							}
						}
						//��Ӽ۸��� xin_wang 2010.12.22										             		
					}else if(TenancyBillStateEnum.ChangeNaming.equals(tenState)||TenancyBillStateEnum.PriceChanging.equals(tenState))
					{
						if(HandleStateEnum.AlreadyCallBack.equals(tenRoomEntrys.getHandleState()))
						{
							MsgBox.showInfo("�����Ѿ��ջ�");
							this.abort();
						}else if(HandleStateEnum.CallBacking.equals(tenRoomEntrys.getHandleState()))
						{
							MsgBox.showInfo("�������ڻ���");
							this.abort();
						}else
						{
							handleRoomEntrys.setHandleType("�շ�");
							handleRoomEntrys.setOldHandleState(tenRoomEntrys.getHandleState());
							handleRoomEntrys.setTenancyRoom(tenRoomEntrys);
							list.add(handleRoomEntrys);
						}              		
					}
					else if(TenancyBillStateEnum.QuitTenancying.equals(tenState))
					{
						if(HandleStateEnum.AlreadyCallBack.equals(tenRoomEntrys.getHandleState()))
						{
							MsgBox.showInfo("�����Ѿ��ջ�");
							this.abort();
						}else if(HandleStateEnum.CallBacking.equals(tenRoomEntrys.getHandleState()))
						{
							MsgBox.showInfo("�������ڻ���");
							this.abort();
						}
						else
						{
							if(this.getQuitTenancyInfoByTenancyBill(info) && tenRoomEntrys.getHandleState().equals(HandleStateEnum.NoHandleRoom))
							{
								MsgBox.showInfo("��������״̬�����ⵥ�����ٽ��н���");
								this.abort();
							}else if(!this.getQuitTenancyInfoByTenancyBill(info) && tenRoomEntrys.getHandleState().equals(HandleStateEnum.NoHandleRoom))
							{
								TenancyRoomEntryCollection tenEntryColl = info.getTenancyRoomList();
								for(int m=0;m<tenEntryColl.size();m++)
								{
									TenancyRoomEntryInfo tenInfo = tenEntryColl.get(m);
									if(tenInfo.getRoom().getId().toString().equals(roomid))
									{
										//�����չ�Ǯ��û��
//										if(recRent.equals(new BigDecimal(0)))
//										{
//											MsgBox.showInfo("����"+tenRoomEntrys.getRoomLongNum()+"��û�н���");
//											this.abort();
//										}else 
//										if(recRent.compareTo(new BigDecimal(0))>0 && recRent.compareTo(rent)<0)
//										{
//											MsgBox.showInfo("����"+tenRoomEntrys.getRoomLongNum()+"û�н�������Ѻ��");
//											this.abort();
//										}else 
										if(tenRoomEntrys.getActDeliveryRoomDate()!=null)
										{
											MsgBox.showInfo("����"+tenRoomEntrys.getRoomLongNum()+"�Ѿ���������");
											this.abort();
										}else if(tenRoomEntrys.getActDeliveryRoomDate()==null)
										{
//										else if(recRent.compareTo(rent)>=0 && tenRoomEntrys.getActDeliveryRoomDate()==null)
//										{
											handleRoomEntrys.setHandleType(info.getTenancyType()+"����");
											handleRoomEntrys.setOldHandleState(tenRoomEntrys.getHandleState());
											handleRoomEntrys.setTenancyRoom(tenRoomEntrys);
											list.add(handleRoomEntrys);
											break;
										}  
									}
								}
							}else
							{
								handleRoomEntrys.setHandleType("�շ�");
								handleRoomEntrys.setOldHandleState(tenRoomEntrys.getHandleState());
								handleRoomEntrys.setTenancyRoom(tenRoomEntrys);
								list.add(handleRoomEntrys);
							}
						}
					}
					else if(TenancyBillStateEnum.TenancyChanging.equals(tenState))
					{
						handleRoomEntrys.setHandleType("�շ�");
						handleRoomEntrys.setOldHandleState(tenRoomEntrys.getHandleState());
						handleRoomEntrys.setTenancyRoom(tenRoomEntrys);
						list.add(handleRoomEntrys);
					}else
					{
						MsgBox.showInfo(tenRoomEntrys.getRoomLongNum()+"�����Ϸ��佻�ӵ�������");
						abort();
					}
				}
			}
			this.getUIContext().put("list", list);
		}
		if(this.tblAttach.getRowCount()>0)
		{
			for(int i=0;i<this.tblAttach.getRowCount();i++)
			{
				IRow row = tblAttach.getRow(i);
				if(row.getCell("item").getValue().equals(Boolean.valueOf(true)))
				{
					TenAttachResourceEntryInfo tenAttEntryInfo = (TenAttachResourceEntryInfo)row.getUserObject();
					this.setAttachHandle(tenAttEntryInfo,attachList);
				}				
			}
			this.getUIContext().put("attachList", attachList);
		}
		this.destroyWindow();       
	}
	
	/*
	 * ������ķ�������ʱ����д
	 * */
//	private boolean validate(BigDecimal recRent,BigDecimal rent,HandleRoomEntrysInfo handleRoomEntrys,ITenancyEntryInfo tenancyEntryInfo,List list,TenancyContractTypeEnum tenConType)
//	{
//		boolean boo = false;
//		//�����չ�Ǯ��û��
//		if(recRent.equals(new BigDecimal(0)))
//		{
//			MsgBox.showInfo(tenancyEntryInfo.getLongNumber()+"��û�н���");
//			this.abort();
//		}else if(recRent.compareTo(new BigDecimal(0))>0 && recRent.compareTo(rent)<0)
//		{
//			MsgBox.showInfo(tenancyEntryInfo.getLongNumber()+"û�н�������Ѻ��");
//			this.abort();
//		}else if(tenancyEntryInfo.getActDeliveryDate()!=null)
//		{
//			MsgBox.showInfo(tenancyEntryInfo.getLongNumber()+"�Ѿ���������");
//			this.abort();
//		}else if(recRent.compareTo(rent)>=0 && tenancyEntryInfo.getActDeliveryDate()==null)
//		{
//			handleRoomEntrys.setHandleType(tenConType+"����");
//			handleRoomEntrys.setOldHandleState(tenancyEntryInfo.getHandleState());
//			if(tenancyEntryInfo instanceof TenancyRoomEntryInfo)
//			{
//				handleRoomEntrys.setTenancyRoom((TenancyRoomEntryInfo)tenancyEntryInfo);
//			}else
//			{
//				handleRoomEntrys.setAttach((TenAttachResourceEntryInfo)tenancyEntryInfo);
//			}			
//			list.add(handleRoomEntrys);
//			boo = true;
//		} 
//		return boo;
//	}
//	
//	private BigDecimal getRecRent(ITenancyEntryInfo itenEntryInfo,String tenancyBillID)
//	{
//		if(itenEntryInfo instanceof TenancyRoomEntryInfo)
//		{
//			return TenancyHelper.getRecAmount(((TenancyRoomEntryInfo)itenEntryInfo).getRoom().getId().toString(), tenancyBillID);
//		}else
//		{
//			return TenancyHelper.getRecAttachAmount(((TenAttachResourceEntryInfo)itenEntryInfo).getAttachResource().getId().toString(), tenancyBillID);
//		}
//	}
	
	//����������Դ�ڸ��ֺ�ͬ�µĽ���״̬
	private void setAttachHandle(TenAttachResourceEntryInfo tenAttEntryInfo,List attachList) throws EASBizException, BOSException, SQLException
	{
		HandleRoomEntrysInfo handleRoomEntrys = new HandleRoomEntrysInfo();
		String attachid = tenAttEntryInfo.getAttachResource().getId().toString();
		BigDecimal depositAmount = tenAttEntryInfo.getDepositAmount();
		depositAmount = depositAmount==null?new BigDecimal(0):depositAmount;
		BigDecimal firstPayRent = tenAttEntryInfo.getFirstPayAmount();
		firstPayRent = firstPayRent==null?new BigDecimal(0):firstPayRent;
		BigDecimal rent = depositAmount.add(firstPayRent);
		TenancyBillInfo info = tenAttEntryInfo.getTenancyBill();
		//�÷����ѽ������
		BigDecimal recRent = TenancyHelper.getRecAttachAmount(attachid,info.getId().toString());
		if(recRent==null)
		{
			recRent = new BigDecimal(0);
		}
		TenancyBillStateEnum tenState = info.getTenancyState();
		//��������Ϊ������ֻ�������������չ���ĺ�ͬ�Ͳ���ִ����Ҫ���ӵķ����չ���ĺ�ͬ
		if(TenancyBillStateEnum.Audited.equals(tenState) || TenancyBillStateEnum.PartExecuted.equals(tenState))
		{
				TenancyBillInfo oldBillInfo = info.getOldTenancyBill();
				//����º�ͬ��ת���Ļ����������ջ���û��
				if(TenancyContractTypeEnum.ChangeName.equals(info.getTenancyType()))
				{
					if(!TenancyBillStateEnum.Expiration.equals(oldBillInfo.getTenancyState()))
					{
						MsgBox.showInfo("ԭ��ͬ������Դ��û���ջ���");
						this.abort();
					}else
					{
						TenancyBillInfo targetTen = TenancyHelper.getTenancyBillInfo(oldBillInfo.getId().toString());
						TenAttachResourceEntryCollection tenEntryColl = targetTen.getTenAttachResourceList();
						for(int k=0;k<tenEntryColl.size();k++)
						{
							TenAttachResourceEntryInfo tenRoomInfo = tenEntryColl.get(k);
							if(tenRoomInfo.getAttachResource().getId().toString().equals(attachid))
							{
								//�����չ�Ǯ��û��
//								if(recRent.equals(new BigDecimal(0)))
//								{
//									MsgBox.showInfo("������Դ"+tenAttEntryInfo.getAttachLongNum()+"��û�н���");
//									this.abort();
//								}else if(recRent.compareTo(new BigDecimal(0))>0 && recRent.compareTo(rent)<0)
//								{
//									MsgBox.showInfo("������Դ"+tenAttEntryInfo.getAttachLongNum()+"û�н�������Ѻ��");
//									this.abort();
//								}else 
								if(tenAttEntryInfo.getActDeliveryAttachResDate()!=null)
								{
									MsgBox.showInfo("������Դ"+tenAttEntryInfo.getAttachLongNum()+"�Ѿ���������");
									this.abort();
								}else if(tenAttEntryInfo.getActDeliveryAttachResDate()==null)
								{
//								else if(recRent.compareTo(rent)>=0 && tenAttEntryInfo.getActDeliveryAttachResDate()==null)
//								{
									handleRoomEntrys.setHandleType("����");
									handleRoomEntrys.setOldHandleState(tenAttEntryInfo.getHandleState());
									handleRoomEntrys.setAttach(tenAttEntryInfo);
									attachList.add(handleRoomEntrys);
									break;
								}              					
							}
						}
					}
					//���ԭ��ͬ��ת���������ĺ�ͬ�Ļ����������;�Ϊת�⽻�Ӻ����⽻��
				}else if(oldBillInfo!=null && (TenancyBillStateEnum.ContinueTenancying.equals(oldBillInfo.getTenancyState())
						|| TenancyBillStateEnum.RejiggerTenancying.equals(oldBillInfo.getTenancyState())))
				{
					//ת���������Ļ�ֻ�ܶ��º�ͬ�������������㽻��������ͬʱ��ԭ��ͬ�ķ������ˣ�������״̬��д�����޺�ͬ�����¼�ͷ�����
					TenancyBillInfo targetTen = TenancyHelper.getTenancyBillInfo(oldBillInfo.getId().toString());
					TenAttachResourceEntryCollection tenEntryColl = targetTen.getTenAttachResourceList();
					List attachidlist = new ArrayList();
					for(int k=0;k<tenEntryColl.size();k++)
					{
						TenAttachResourceEntryInfo tenRoomEnInfo = tenEntryColl.get(k);
						attachidlist.add(tenRoomEnInfo.getAttachResource().getId().toString());
					}
					for(int k=0;k<tenEntryColl.size();k++)
					{
						//TenancyRoomEntryInfo tenRoomEnInfo = tenEntryColl.get(k);
						//ת���������ķ�����ԭ��ͬ��Ҳ�еĽ�ת������⽻��
						if(TenancyClientHelper.isInclude(attachid, attachidlist))
						//if(tenRoomEnInfo.getRoom().getId().toString().equals(roomid))
						{
							//�����չ�Ǯ��û��
//							if(recRent.equals(new BigDecimal(0)))
//							{
//								MsgBox.showInfo("������Դ"+tenAttEntryInfo.getAttachLongNum()+"��û�н���");
//								this.abort();
//							}else if(recRent.compareTo(new BigDecimal(0))>0 && recRent.compareTo(rent)<0)
//							{
//								MsgBox.showInfo("������Դ"+tenAttEntryInfo.getAttachLongNum()+"û�н�������Ѻ��");
//								this.abort();
//							}else 
							if(tenAttEntryInfo.getActDeliveryAttachResDate()!=null)
							{
								MsgBox.showInfo("������Դ"+tenAttEntryInfo.getAttachLongNum()+"�Ѿ���������");
								this.abort();
							}else if(tenAttEntryInfo.getActDeliveryAttachResDate()==null)
							{
//							else if(recRent.compareTo(rent)>=0 && tenAttEntryInfo.getActDeliveryAttachResDate()==null)
//							{
								handleRoomEntrys.setHandleType(info.getTenancyType()+"����");
								handleRoomEntrys.setOldHandleState(tenAttEntryInfo.getHandleState());
								handleRoomEntrys.setAttach(tenAttEntryInfo);
								attachList.add(handleRoomEntrys);
								break;
							}  
						}else
						{
							//ԭ��ͬ��û�з��������ͨ�Ľ�������
							//�����չ�Ǯ��û��
//							if(recRent.equals(new BigDecimal(0)))
//							{
//								MsgBox.showInfo("������Դ"+tenAttEntryInfo.getAttachLongNum()+"��û�н���");
//								this.abort();
//							}else if(recRent.compareTo(new BigDecimal(0))>0 && recRent.compareTo(rent)<0)
//							{
//								MsgBox.showInfo("������Դ"+tenAttEntryInfo.getAttachLongNum()+"û�н�������Ѻ��");
//								this.abort();
//							}else 
							if(tenAttEntryInfo.getActDeliveryAttachResDate()!=null)
							{
								MsgBox.showInfo("������Դ"+tenAttEntryInfo.getAttachLongNum()+"�Ѿ���������");
								this.abort();
							}else if(tenAttEntryInfo.getActDeliveryAttachResDate()==null)
							{
//							else if(recRent.compareTo(rent)>=0 && tenAttEntryInfo.getActDeliveryAttachResDate()==null)
//							{
								handleRoomEntrys.setHandleType("����");
								handleRoomEntrys.setOldHandleState(tenAttEntryInfo.getHandleState());
								handleRoomEntrys.setAttach(tenAttEntryInfo);
								attachList.add(handleRoomEntrys);
								break;
							}  
						}

					}
				}else
				{
					//����˵����ֱ�������ĺ�ͬ��Ҫ�����Ļ���Ҫ�����չ��������
//					if(recRent.equals(new BigDecimal(0)))
//					{
//						MsgBox.showInfo("������Դ"+tenAttEntryInfo.getAttachLongNum()+"��û�н���");
//						this.abort();
//					}else if(recRent.compareTo(new BigDecimal(0))>0 && recRent.compareTo(rent)<0)
//					{
//						MsgBox.showInfo("������Դ"+tenAttEntryInfo.getAttachLongNum()+"û�н�������Ѻ��");
//						this.abort();
//					}else 
					if(tenAttEntryInfo.getActDeliveryAttachResDate()!=null)
					{
						MsgBox.showInfo("������Դ"+tenAttEntryInfo.getAttachLongNum()+"�Ѿ���������");
						this.abort();
					}else if(tenAttEntryInfo.getActDeliveryAttachResDate()==null)
					{
//					else if(recRent.compareTo(rent)>=0 && tenAttEntryInfo.getActDeliveryAttachResDate()==null)
//					{
						handleRoomEntrys.setHandleType("����");
						handleRoomEntrys.setOldHandleState(tenAttEntryInfo.getHandleState());
						handleRoomEntrys.setAttach(tenAttEntryInfo);
						attachList.add(handleRoomEntrys);
					}
				}
		}else if(TenancyBillStateEnum.RejiggerTenancying.equals(tenState)
				|| TenancyBillStateEnum.ContinueTenancying.equals(tenState))
		{
			String targetTenId = TenancyHelper.getTargetTenIdBySrcTenancyId(info.getId().toString());
			TenancyBillInfo targetTen = TenancyHelper.getTenancyBillInfo(targetTenId);
			TenAttachResourceEntryCollection tenEntryColl = targetTen.getTenAttachResourceList();
			//����º�ͬ��������Դ�ĸ���Ϊ0.��ô˵��������Դ���º�ͬ�� û�г�����ô��ԭ��ͬ��ֱ���ջؾ�����
			if(tenEntryColl.size()==0)
			{
				handleRoomEntrys.setHandleType("�շ�");
				handleRoomEntrys.setOldHandleState(tenAttEntryInfo.getHandleState());
				handleRoomEntrys.setAttach(tenAttEntryInfo);
				attachList.add(handleRoomEntrys);
			}else
			{
				if(this.getQuitTenancyInfoByTenancyBill(targetTen))
				{
					for(int m=0;m<tenEntryColl.size();m++)
					{
						TenAttachResourceEntryInfo tenInfo = tenEntryColl.get(m);
						if(attachid.equals(tenInfo.getAttachResource().getId().toString()) && (HandleStateEnum.AlreadyCallBack.equals(tenInfo.getHandleState())
								|| HandleStateEnum.livingHouse.equals(tenInfo.getHandleState())))
						{
							MsgBox.showInfo("�÷����Ѿ����º�ͬ�н�����");
							this.abort();
						}else if(attachid.equals(tenInfo.getAttachResource().getId().toString()))
						{
							handleRoomEntrys.setHandleType("�շ�");
							handleRoomEntrys.setOldHandleState(tenAttEntryInfo.getHandleState());
							handleRoomEntrys.setAttach(tenAttEntryInfo);
							attachList.add(handleRoomEntrys);
						}
					}
					
				}else
				{
					for(int k=0;k<tenEntryColl.size();k++)
					{
						TenAttachResourceEntryInfo tenAttachInfo = tenEntryColl.get(k);
						if(tenAttachInfo.getAttachResource().getId().toString().equals(attachid))
						{
							MsgBox.showInfo(targetTen.getTenancyType()+"���ӵķ���ֻ�ܵ��º�ͬ��ȥ����");
							this.abort();
						}else
						{
							if(TenancyBillStateEnum.Saved.equals(targetTen.getTenancyState()) || TenancyBillStateEnum.Submitted.equals(targetTen.getTenancyState())
									|| TenancyBillStateEnum.BlankOut.equals(targetTen.getTenancyState()))
							{
								MsgBox.showInfo("Ŀ���ͬ���������������������ĺ�ͬ�����շ�");
								this.abort();
							}else
							{
								handleRoomEntrys.setHandleType("�շ�");
								handleRoomEntrys.setOldHandleState(tenAttEntryInfo.getHandleState());
								handleRoomEntrys.setAttach(tenAttEntryInfo);
								attachList.add(handleRoomEntrys);
							}																
						}
					}  
				}
			}															             		
		}else if(TenancyBillStateEnum.ChangeNaming.equals(tenState))
		{
			if(HandleStateEnum.AlreadyCallBack.equals(tenAttEntryInfo.getHandleState()))
			{
				MsgBox.showInfo("�����Ѿ��ջ�");
				this.abort();
			}else if(HandleStateEnum.CallBacking.equals(tenAttEntryInfo.getHandleState()))
			{
				MsgBox.showInfo("�������ڻ���");
				this.abort();
			}else
			{
				handleRoomEntrys.setHandleType("�շ�");
				handleRoomEntrys.setOldHandleState(tenAttEntryInfo.getHandleState());
				handleRoomEntrys.setAttach(tenAttEntryInfo);
				attachList.add(handleRoomEntrys);
			}              		
		}else if(TenancyBillStateEnum.QuitTenancying.equals(tenState))
		{
			if(HandleStateEnum.AlreadyCallBack.equals(tenAttEntryInfo.getHandleState()))
			{
				MsgBox.showInfo("�����Ѿ��ջ�");
				this.abort();
			}else if(HandleStateEnum.CallBacking.equals(tenAttEntryInfo.getHandleState()))
			{
				MsgBox.showInfo("�������ڻ���");
				this.abort();
			}
			else
			{
				if(this.getQuitTenancyInfoByTenancyBill(info) && tenAttEntryInfo.getHandleState().equals(HandleStateEnum.NoHandleRoom))
				{
					MsgBox.showInfo("��������״̬�����ⵥ�����ٽ��н���");
					this.abort();
				}else if(!this.getQuitTenancyInfoByTenancyBill(info) && tenAttEntryInfo.getHandleState().equals(HandleStateEnum.NoHandleRoom))
				{
					TenAttachResourceEntryCollection tenEntryColl = info.getTenAttachResourceList();
					for(int m=0;m<tenEntryColl.size();m++)
					{
						TenAttachResourceEntryInfo tenInfo = tenEntryColl.get(m);
						if(tenInfo.getAttachResource().getId().toString().equals(attachid))
						{
							//�����չ�Ǯ��û��
//							if(recRent.equals(new BigDecimal(0)))
//							{
//								MsgBox.showInfo("������Դ"+tenAttEntryInfo.getAttachLongNum()+"��û�н���");
//								this.abort();
//							}else if(recRent.compareTo(new BigDecimal(0))>0 && recRent.compareTo(rent)<0)
//							{
//								MsgBox.showInfo("������Դ"+tenAttEntryInfo.getAttachLongNum()+"û�н�������Ѻ��");
//								this.abort();
//							}else 
							if(tenAttEntryInfo.getActDeliveryAttachResDate()!=null)
							{
								MsgBox.showInfo("������Դ"+tenAttEntryInfo.getAttachLongNum()+"�Ѿ���������");
								this.abort();
							}else if(tenAttEntryInfo.getActDeliveryAttachResDate()==null)
							{
//							else if(recRent.compareTo(rent)>=0 && tenAttEntryInfo.getActDeliveryAttachResDate()==null)
//							{
								handleRoomEntrys.setHandleType(info.getTenancyType()+"����");
								handleRoomEntrys.setOldHandleState(tenAttEntryInfo.getHandleState());
								handleRoomEntrys.setAttach(tenAttEntryInfo);
								attachList.add(handleRoomEntrys);
								break;
							}  
						}
					}
				}else
				{
					handleRoomEntrys.setHandleType("�շ�");
					handleRoomEntrys.setOldHandleState(tenAttEntryInfo.getHandleState());
					handleRoomEntrys.setAttach(tenAttEntryInfo);
					attachList.add(handleRoomEntrys);
				}
			}
		}
		else if(TenancyBillStateEnum.TenancyChanging.equals(tenState))
		{
			handleRoomEntrys.setHandleType("�շ�");
			handleRoomEntrys.setOldHandleState(tenAttEntryInfo.getHandleState());
			handleRoomEntrys.setAttach(tenAttEntryInfo);
			attachList.add(handleRoomEntrys);
		}else
		{
			MsgBox.showInfo(tenAttEntryInfo.getAttachLongNum()+"�����Ϸ��佻�ӵ�������");
			abort();
		}
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

	protected void btnCancel2_actionPerformed(java.awt.event.ActionEvent e) throws Exception
	{
		this.destroyWindow();
	}

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

}