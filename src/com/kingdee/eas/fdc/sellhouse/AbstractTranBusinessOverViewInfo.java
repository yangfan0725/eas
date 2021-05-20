package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTranBusinessOverViewInfo extends com.kingdee.eas.fdc.sellhouse.TranPayListEntryInfo implements Serializable 
{
    public AbstractTranBusinessOverViewInfo()
    {
        this("id");
    }
    protected AbstractTranBusinessOverViewInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��������ҵ������'s ҵ������property 
     */
    public String getBusinessName()
    {
        return getString("businessName");
    }
    public void setBusinessName(String item)
    {
        setString("businessName", item);
    }
    /**
     * Object:��������ҵ������'s Ӧ���ʱ��property 
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
     * Object:��������ҵ������'s ʵ�����ʱ��property 
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
     * Object:��������ҵ������'s �Ƿ����property 
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
     * Object:��������ҵ������'s ����property 
     */
    public String getDesc()
    {
        return getString("desc");
    }
    public void setDesc(String item)
    {
        setString("desc", item);
    }
    /**
     * Object:��������ҵ������'s ��������property 
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
     * Object: ��������ҵ������ 's ���׵�id property 
     */
    public com.kingdee.eas.fdc.sellhouse.TransactionInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.TransactionInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.TransactionInfo item)
    {
        put("head", item);
    }
    /**
     * Object: ��������ҵ������ 's ������ʷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.TranStateHisInfo getHisHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.TranStateHisInfo)get("hisHead");
    }
    public void setHisHead(com.kingdee.eas.fdc.sellhouse.TranStateHisInfo item)
    {
        put("hisHead", item);
    }
    /**
     * Object:��������ҵ������'s �Ƿ��Ѵ���property 
     */
    public boolean isIsSubstitute()
    {
        return getBoolean("isSubstitute");
    }
    public void setIsSubstitute(boolean item)
    {
        setBoolean("isSubstitute", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7FB3321F");
    }
}