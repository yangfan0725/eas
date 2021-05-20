package com.kingdee.eas.fdc.sellhouse;

import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.kdf.data.logging.Logger;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.client.ChooseRoomListUI;
import com.kingdee.eas.fdc.sellhouse.client.SellControlUI;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.util.StringUtils;
import com.sun.xml.messaging.saaj.util.ByteInputStream;
public class ChooseRoomDisplaySetting implements Serializable,IDisplayRule {

	private static final long serialVersionUID = -927599730681101074L;	
	
	//�Ѵ洢���ݷּ�¼�洢����ͬ��¼�Բ�ͬ�ı������ֿ���
	private final String FaithAmount = "FaithAmount";	//���������õı���
	private final String PreMoneyNumber = "PreMoneyNumber";  //Ԥ���������õı���
	private final String FunctionNumber = "FunctionNumber";  //�������õı���
	private final String CasNumber = "CasNumber";  //����������õı���
	private final String BaseRoomNumber = "BaseRoomNumber";  //��������ı���
	//����Ƿ��Զ���Ԥ����ת������ eric_wang 2010.08.12
	private final String ISPRETOOTHERMONEY ="isPreToOtherMoney";

	private Map functionSetMap = new HashMap();	//��������	��Ŀid ��Ӧ ������FunctionSetting
	private Map faithAmountSetMap = new HashMap();	//����������  ��Ŀid ��Ӧ ������FaithAmountSetting
	private Map preMoneySetMap = new HashMap();	//Ԥ����������  ��Ŀid ��Ӧ ������PreMoneySetting
	private CasSetting casSetting;	//�����������
	private BaseRoomSetting baseRoomSetting = new BaseRoomSetting(); //�������������
	//����Ƿ��Զ���Ԥ����ת������ eric_wang 2010.08.12
	private Map isPreToOtherMoneyMap = new HashMap();
	
	
	

	
//	public Color getNoSellhouseColor() {
//		return noSellhouseColor;
//	}
//
//	public void setNoSellhouseColor(Color noSellhouseColor) {
//		this.noSellhouseColor = noSellhouseColor;
//	}

	public Color getChooseRoomAffirm() {
		return chooseRoomAffirm;
	}

	public void setChooseRoomAffirm(Color chooseRoomAffirm) {
		this.chooseRoomAffirm = chooseRoomAffirm;
	}

	public Color getChooseRoomSelled() {
		return chooseRoomSelled;
	}

	public void setChooseRoomSelled(Color chooseRoomSelled) {
		this.chooseRoomSelled = chooseRoomSelled;
	}

	public Color getChooseRoomUnconfirmed() {
		return chooseRoomUnconfirmed;
	}

	public void setChooseRoomUnconfirmed(Color chooseRoomUnconfirmed) {
		this.chooseRoomUnconfirmed = chooseRoomUnconfirmed;
	}

//	private Color noSellhouseColor = Color.blue;	//û����¥����
	
	public static Color chooseRoomAffirm = new Color(89,172,255);	//ѡ��ȷ��״̬
	
	public static Color chooseRoomSelled = Color.red ;	//ѡ������״̬
	
	public static Color chooseRoomUnconfirmed = Color.green ;	//ѡ��δȷ��״̬
	
	
	


	public ChooseRoomDisplaySetting(){
		this(null);
	}
	
	public ChooseRoomDisplaySetting(Context ctx) {
		// �������ݿ�����		
		try {
			IRoomDisplaySet iface = null;
			if(ctx == null){
				iface = RoomDisplaySetFactory.getRemoteInstance();
			}else{
				iface = RoomDisplaySetFactory.getLocalInstance(ctx);
			}
			RoomDisplaySetCollection priceSetCollection = iface.getRoomDisplaySetCollection("select number,value");
			for(int i=0;i<priceSetCollection.size();i++) {
				RoomDisplaySetInfo setInfo = priceSetCollection.get(i);
				if(setInfo.getNumber()!=null) {
					byte[] b = setInfo.getValue();
					ObjectInputStream objectInputStream = new ObjectInputStream(new ByteInputStream(b, 0, b.length));
					if(setInfo.getNumber().equals(this.BaseRoomNumber)){
						try{
							this.baseRoomSetting = (BaseRoomSetting)objectInputStream.readObject();
						}catch(InvalidClassException ee){ Logger.info("��¥����--�������������--ת������"); }
					}else if(setInfo.getNumber().equals(this.PreMoneyNumber)){
						try{
							this.preMoneySetMap = (Map)objectInputStream.readObject();
						}catch(InvalidClassException ee){ Logger.info("��¥����--Ԥ����������--ת������"); }
					}else if(setInfo.getNumber().equals(this.FaithAmount)){
						try{
							this.faithAmountSetMap = (Map)objectInputStream.readObject();
						}catch(InvalidClassException ee){ Logger.info("��¥����--Ĭ�ϳ���������--ת������"); }
					}else if(setInfo.getNumber().equals(this.FunctionNumber)) {
						try{
							this.functionSetMap = (Map)objectInputStream.readObject();;
						}catch(InvalidClassException ee){ Logger.info("��¥����--��������--ת������"); }
					}else if(setInfo.getNumber().equals(this.CasNumber)){
						try{
							this.casSetting = (CasSetting)objectInputStream.readObject();
						}catch(InvalidClassException ee){ Logger.info("��¥����--�����������--ת������"); }
					}else if(this.ISPRETOOTHERMONEY.equals(setInfo.getNumber())){
						try{
							this.isPreToOtherMoneyMap = (Map)objectInputStream.readObject();
						}catch(InvalidClassException ee){ Logger.info("��¥����--��������--Ԥ����ת�������"); }
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	//�����ȷֻȥĳһ�������õĲ����� �����ֻ��ѯ�ù��ܲ��ֵ�����
	public ChooseRoomDisplaySetting(Context ctx,String numberSetString) {
		if(numberSetString==null)	return;	
		try {
			IRoomDisplaySet iface = null;
			if(ctx == null){
				iface = RoomDisplaySetFactory.getRemoteInstance();
			}else{
				iface = RoomDisplaySetFactory.getLocalInstance(ctx);
			}

			RoomDisplaySetInfo roomSetInfo = iface.getRoomDisplaySetInfo("select number,value where number = '"+numberSetString+"'");
			if(roomSetInfo!=null) {
				byte[] b = roomSetInfo.getValue();
				ObjectInputStream objectInputStream = new ObjectInputStream(new ByteInputStream(b, 0, b.length));
				if(numberSetString.equals(this.BaseRoomNumber)){
					this.baseRoomSetting = (BaseRoomSetting)objectInputStream.readObject();
				}else if(numberSetString.equals(this.PreMoneyNumber)){
					this.preMoneySetMap = (Map)objectInputStream.readObject();
				}else if(numberSetString.equals(this.FaithAmount)){
					this.faithAmountSetMap = (Map)objectInputStream.readObject();
				}else if(numberSetString.equals(this.FunctionNumber)) {
					this.functionSetMap = (Map)objectInputStream.readObject();
				}else if(numberSetString.equals(this.CasNumber)){
					this.casSetting = (CasSetting)objectInputStream.readObject();
				}else if(numberSetString.equals(this.ISPRETOOTHERMONEY)){
					this.isPreToOtherMoneyMap = (Map)objectInputStream.readObject();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	
	
	
	private byte[] getBytesFromObject(Object obj) throws Exception {
		ByteArrayOutputStream bOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream oOutputStream = new ObjectOutputStream(bOutputStream);
		oOutputStream.writeObject(obj);  
		return bOutputStream.toByteArray();		
	}
	
	
	public void save() throws Exception {
		byte[] baseRoomBytes = getBytesFromObject(this.baseRoomSetting);	
		RoomDisplaySetInfo baseRoomSetInfo = new RoomDisplaySetInfo();
		baseRoomSetInfo.setValue(baseRoomBytes);
		baseRoomSetInfo.setNumber(this.BaseRoomNumber);		

		byte[] preBytes = getBytesFromObject(this.preMoneySetMap);		
		RoomDisplaySetInfo preSetInfo = new RoomDisplaySetInfo();
		preSetInfo.setValue(preBytes);
		preSetInfo.setNumber(this.PreMoneyNumber);
		
		//Ĭ�ϳ���������  pu_zhang 2010-8-9
		byte[] faithAmountBytes = getBytesFromObject(this.faithAmountSetMap);		
		RoomDisplaySetInfo faithAmountInfo = new RoomDisplaySetInfo();
		faithAmountInfo.setValue(faithAmountBytes);
		faithAmountInfo.setNumber(this.FaithAmount);

		byte[] funcBytes = getBytesFromObject(this.functionSetMap);		
		ByteArrayInputStream input = new ByteArrayInputStream(funcBytes);
		ObjectInputStream in = new ObjectInputStream(input);
		Map obj = (Map) in.readObject();
		
		
		RoomDisplaySetInfo funcSetInfo = new RoomDisplaySetInfo();
		funcSetInfo.setValue(funcBytes);
		funcSetInfo.setNumber(this.FunctionNumber);
		
		byte[] casBytes = getBytesFromObject(this.casSetting);		
		RoomDisplaySetInfo casSetInfo = new RoomDisplaySetInfo();
		casSetInfo.setValue(casBytes);
		casSetInfo.setNumber(this.CasNumber);
		
		byte[] isPreToOtherMoneyBytes = getBytesFromObject(this.isPreToOtherMoneyMap);		
		RoomDisplaySetInfo isPreToOtherMoneyInfo = new RoomDisplaySetInfo();
		isPreToOtherMoneyInfo.setValue(isPreToOtherMoneyBytes);
		isPreToOtherMoneyInfo.setNumber(this.ISPRETOOTHERMONEY);
		
		RoomDisplaySetCollection setOldColl = RoomDisplaySetFactory.getRemoteInstance()
													.getRoomDisplaySetCollection();	

		
		//��������
		CoreBaseCollection coreCollUpdate = new CoreBaseCollection(); 
		CoreBaseCollection coreCollAdd = new CoreBaseCollection(); 
		//BaseRoomNumber
		if (checkIsExist(setOldColl, this.BaseRoomNumber)) {
			for (int i = 0; i < setOldColl.size(); i++) {
				RoomDisplaySetInfo setInfo = setOldColl.get(i);
				if (setInfo.getNumber() != null) {
					if (setInfo.getNumber().equals(this.BaseRoomNumber)) {
						setInfo.setValue(baseRoomBytes);
						coreCollUpdate.add(setInfo);
					}
				}
				
			}
			
		} else {
			coreCollAdd.add(baseRoomSetInfo);
		}
		//PreMoneyNumber
		if (checkIsExist(setOldColl, this.PreMoneyNumber)) {
			for (int i = 0; i < setOldColl.size(); i++) {
				RoomDisplaySetInfo setInfo = setOldColl.get(i);
				if (setInfo.getNumber() != null) {
					if (setInfo.getNumber().equals(this.PreMoneyNumber)) {
						setInfo.setValue(preBytes);
						coreCollUpdate.add(setInfo);
					}
				}
				
			}
		} else {
			coreCollAdd.add(preSetInfo);
		}
		//FaithAmount
		if (checkIsExist(setOldColl, this.FaithAmount)) {
			for (int i = 0; i < setOldColl.size(); i++) {
				RoomDisplaySetInfo setInfo = setOldColl.get(i);
				if (setInfo.getNumber() != null) {
					if (setInfo.getNumber().equals(this.FaithAmount)) {
						setInfo.setValue(faithAmountBytes);
						coreCollUpdate.add(setInfo);
					}
				}
				
			}
		} else {
			coreCollAdd.add(faithAmountInfo);
		}
		
		//FunctionNumber
		if (checkIsExist(setOldColl, this.FunctionNumber)) {
			for (int i = 0; i < setOldColl.size(); i++) {
				RoomDisplaySetInfo setInfo = setOldColl.get(i);
				if (setInfo.getNumber() != null) {
					if (setInfo.getNumber().equals(this.FunctionNumber)) {
						setInfo.setValue(funcBytes);
						coreCollUpdate.add(setInfo);
					}
				}
				
			}
		} else {
			coreCollAdd.add(funcSetInfo);
		}

		//CasNumber
		if (checkIsExist(setOldColl, this.CasNumber)) {
			for (int i = 0; i < setOldColl.size(); i++) {
				RoomDisplaySetInfo setInfo = setOldColl.get(i);
				if (setInfo.getNumber() != null) {
					if (setInfo.getNumber().equals(this.CasNumber)) {
						setInfo.setValue(casBytes);
						coreCollUpdate.add(setInfo);
					}
				}
			}
		} else {
			coreCollAdd.add(casSetInfo);
		}
		
		//isPreToOtherMoney
		if (checkIsExist(setOldColl, this.ISPRETOOTHERMONEY)) {
			for (int i = 0; i < setOldColl.size(); i++) {
				RoomDisplaySetInfo setInfo = setOldColl.get(i);
				if (setInfo.getNumber() != null) {
					if (setInfo.getNumber().equals(this.ISPRETOOTHERMONEY)) {
						setInfo.setValue(isPreToOtherMoneyBytes);
						coreCollUpdate.add(setInfo);
					}
				}
				
			}
		} else {
			coreCollAdd.add(isPreToOtherMoneyInfo);
		}
		RoomDisplaySetFactory.getRemoteInstance().update(coreCollUpdate);
		RoomDisplaySetFactory.getRemoteInstance().addnew(coreCollAdd);	
		
		
		
		
		
		CacheServiceFactory.getInstance().discardType(new RoomDisplaySetInfo().getBOSType());
	}
	private boolean checkIsExist(RoomDisplaySetCollection coll, String number ){
		boolean flag = false;
		for(int i=0;i<coll.size();i++) {
			RoomDisplaySetInfo setInfo = coll.get(i);
			if(setInfo.getNumber()!=null) {
				if(setInfo.getNumber().equals(number)) {
					flag=true;
					break;
				}
			}
		}
		return flag;
	}
	public int getAttachDisType() {
		return this.baseRoomSetting.getAttachDisType();
	}


	public Font getFont() {
		return this.baseRoomSetting.getFont();
	}

	public Color getFrontColor() {
		return this.baseRoomSetting.getFrontColor();
	}	

	public int getRoomHeight() {
		return this.baseRoomSetting.getRoomHeight();
	}	
	
	public int getRoomWidth() {
		return this.baseRoomSetting.getRoomWidth();
	}

	public String getDisplayField() {
		return this.baseRoomSetting.getDisplayField();
	}

	public Color getCellBackgroundColor(String key) {
		if(StringUtils.isEmpty(key)){
			return null;
		}		
		
		if (key.equals(RoomSellStateEnum.INIT_VALUE))	{
			return chooseRoomSelled;
		} else if (key.equals(RoomSellStateEnum.ONSHOW_VALUE))	{
			return chooseRoomUnconfirmed;
		} else if (key.equals(RoomSellStateEnum.PREPURCHASE_VALUE))	{
			return chooseRoomSelled;
		} else if (key.equals(RoomSellStateEnum.PURCHASE_VALUE))		{
			return chooseRoomSelled;
		} else if (key.equals(RoomSellStateEnum.SIGN_VALUE))		{
			return chooseRoomSelled;
		} else if (key.equals(RoomSellStateEnum.KEEPDOWN_VALUE))		{
			return chooseRoomSelled;
		}else if(key.equals(RoomSellStateEnum.SINCERPURCHASE_VALUE)){
			return chooseRoomUnconfirmed;
		}
//		else if("noSellhouse".equals(key))		{
//			return noSellhouseColor;
//		}
		else if(key.equals("FirstRe")){
			return chooseRoomSelled;
		}
		else if(key.equals(ChooseRoomStateEnum.AFFIRM_VALUE)){
			return chooseRoomAffirm;
		}
		else if(key.equals(ChooseRoomStateEnum.UNCONFIRMED_VALUE)){
			return chooseRoomUnconfirmed;
		}
		return null;
	}

	public MoneySysTypeEnum getSysType() {
		return MoneySysTypeEnum.SalehouseSys;
	}

	public Map getFunctionSetMap()
	{
		return functionSetMap;
	}

	public void setFunctionSetMap(Map functionMap)
	{
		this.functionSetMap = functionMap;
	}

	public Map getPreMoneySetMap(){
		return this.preMoneySetMap;
	}
	
	public void setPreMoneySetMap(Map preMoneySetMap) {
		this.preMoneySetMap = preMoneySetMap;
	}

	public BaseRoomSetting getBaseRoomSetting() {
		return baseRoomSetting;
	}

	public void setBaseRoomSetting(BaseRoomSetting baseRoomSetting) {
		this.baseRoomSetting = baseRoomSetting;
	}

	public CasSetting getCasSetting() {
		return casSetting;
	}

	public void setCasSetting(CasSetting casSetting) {
		this.casSetting = casSetting;
	}

	public void setFaithAmountSetMap(Map faithAmountSetMap) {
		this.faithAmountSetMap = faithAmountSetMap;
	}

	public Map getFaithAmountSetMap() {
		return faithAmountSetMap;
	}

	public Map getIsPreToOtherMoneyMap() {
		return isPreToOtherMoneyMap;
	}

	public void setIsPreToOtherMoneyMap(Map isPreToOtherMoneyMap) {
		this.isPreToOtherMoneyMap = isPreToOtherMoneyMap;
	}

	

}
