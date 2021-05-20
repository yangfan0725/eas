package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSubstituteTransfOutEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSubstituteTransfOutEntryInfo()
    {
        this("id");
    }
    protected AbstractSubstituteTransfOutEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��¼ 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.basecrm.SubstituteTransfOutInfo getParent()
    {
        return (com.kingdee.eas.fdc.basecrm.SubstituteTransfOutInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.basecrm.SubstituteTransfOutInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��¼'s ������������property 
     */
    public com.kingdee.eas.fdc.basecrm.RelatBizType getRevBillType()
    {
        return com.kingdee.eas.fdc.basecrm.RelatBizType.getEnum(getString("revBillType"));
    }
    public void setRevBillType(com.kingdee.eas.fdc.basecrm.RelatBizType item)
    {
		if (item != null) {
        setString("revBillType", item.getValue());
		}
    }
    /**
     * Object: ��¼ 's ���� property 
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
     * Object:��¼'s ֧�����property 
     */
    public java.math.BigDecimal getPayAmount()
    {
        return getBigDecimal("payAmount");
    }
    public void setPayAmount(java.math.BigDecimal item)
    {
        setBigDecimal("payAmount", item);
    }
    /**
     * Object:��¼'s �������ݱ���property 
     */
    public String getRelateBillNumber()
    {
        return getString("relateBillNumber");
    }
    public void setRelateBillNumber(String item)
    {
        setString("relateBillNumber", item);
    }
    /**
     * Object:��¼'s �������׵�����ϸidproperty 
     */
    public String getRelateBillEntryId()
    {
        return getString("relateBillEntryId");
    }
    public void setRelateBillEntryId(String item)
    {
        setString("relateBillEntryId", item);
    }
    /**
     * Object: ��¼ 's ¥�� property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingInfo getBuilding()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingInfo)get("building");
    }
    public void setBuilding(com.kingdee.eas.fdc.sellhouse.BuildingInfo item)
    {
        put("building", item);
    }
    /**
     * Object:��¼'s �ͻ�property 
     */
    public String getCustomer()
    {
        return getString("customer");
    }
    public void setCustomer(String item)
    {
        setString("customer", item);
    }
    /**
     * Object:��¼'s ����ҵ�񵥾�idproperty 
     */
    public String getRelateBizId()
    {
        return getString("relateBizId");
    }
    public void setRelateBizId(String item)
    {
        setString("relateBizId", item);
    }
    /**
     * Object:��¼'s ʵ�ս��property 
     */
    public java.math.BigDecimal getActRevAmount()
    {
        return getBigDecimal("actRevAmount");
    }
    public void setActRevAmount(java.math.BigDecimal item)
    {
        setBigDecimal("actRevAmount", item);
    }
    /**
     * Object:��¼'s Ӧ�ս��property 
     */
    public java.math.BigDecimal getAppAmount()
    {
        return getBigDecimal("appAmount");
    }
    public void setAppAmount(java.math.BigDecimal item)
    {
        setBigDecimal("appAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EC719538");
    }
}