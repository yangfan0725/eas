package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBusinessOverViewInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractBusinessOverViewInfo()
    {
        this("id");
    }
    protected AbstractBusinessOverViewInfo(String pkField)
    {
        super(pkField);
        put("shePayTypeHis", new com.kingdee.eas.fdc.sellhouse.ShePayTypeHisCollection());
    }
    /**
     * Object: ҵ������ʵ�� 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("room");
    }
    public void setRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("room", item);
    }
    /**
     * Object:ҵ������ʵ��'s ҵ������property 
     */
    public com.kingdee.eas.fdc.sellhouse.BusinessTypeNameEnum getBusinessName()
    {
        return com.kingdee.eas.fdc.sellhouse.BusinessTypeNameEnum.getEnum(getString("businessName"));
    }
    public void setBusinessName(com.kingdee.eas.fdc.sellhouse.BusinessTypeNameEnum item)
    {
		if (item != null) {
        setString("businessName", item.getValue());
		}
    }
    /**
     * Object:ҵ������ʵ��'s Ӧ���ʱ��property 
     */
    public java.util.Date getFinishDate()
    {
        return getDate("finishDate");
    }
    public void setFinishDate(java.util.Date item)
    {
        setDate("finishDate", item);
    }
    /**
     * Object:ҵ������ʵ��'s �Ƿ����property 
     */
    public boolean isIsFinish()
    {
        return getBoolean("isFinish");
    }
    public void setIsFinish(boolean item)
    {
        setBoolean("isFinish", item);
    }
    /**
     * Object:ҵ������ʵ��'s ʵ�����ʱ��property 
     */
    public java.util.Date getActualFinishDate()
    {
        return getDate("actualFinishDate");
    }
    public void setActualFinishDate(java.util.Date item)
    {
        setDate("actualFinishDate", item);
    }
    /**
     * Object:ҵ������ʵ��'s �Ƿ�����property 
     */
    public boolean isIsInvalid()
    {
        return getBoolean("isInvalid");
    }
    public void setIsInvalid(boolean item)
    {
        setBoolean("isInvalid", item);
    }
    /**
     * Object:ҵ������ʵ��'s ���ݵ�ǰ״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.TranLinkEnum getState()
    {
        return com.kingdee.eas.fdc.sellhouse.TranLinkEnum.getEnum(getString("state"));
    }
    public void setState(com.kingdee.eas.fdc.sellhouse.TranLinkEnum item)
    {
		if (item != null) {
        setString("state", item.getValue());
		}
    }
    /**
     * Object:ҵ������ʵ��'s ������ϸid/ҵ����ϸidproperty 
     */
    public com.kingdee.bos.util.BOSUuid getBusId()
    {
        return getBOSUuid("busId");
    }
    public void setBusId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("busId", item);
    }
    /**
     * Object:ҵ������ʵ��'s ��Դ����property 
     */
    public com.kingdee.eas.fdc.sellhouse.BusTypeEnum getType()
    {
        return com.kingdee.eas.fdc.sellhouse.BusTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.sellhouse.BusTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    /**
     * Object:ҵ������ʵ��'s �����idproperty 
     */
    public com.kingdee.bos.util.BOSUuid getShePayTypeId()
    {
        return getBOSUuid("shePayTypeId");
    }
    public void setShePayTypeId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("shePayTypeId", item);
    }
    /**
     * Object: ҵ������ʵ�� 's ��������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.ShePayTypeHisCollection getShePayTypeHis()
    {
        return (com.kingdee.eas.fdc.sellhouse.ShePayTypeHisCollection)get("shePayTypeHis");
    }
    /**
     * Object: ҵ������ʵ�� 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getMoneyDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("moneyDefine");
    }
    public void setMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("moneyDefine", item);
    }
    /**
     * Object: ҵ������ʵ�� 's ���׵�id property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseInfo getPurchase()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseInfo)get("purchase");
    }
    public void setPurchase(com.kingdee.eas.fdc.sellhouse.PurchaseInfo item)
    {
        put("purchase", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D0101654");
    }
}