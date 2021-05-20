package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomPayEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractRoomPayEntryInfo()
    {
        this("id");
    }
    protected AbstractRoomPayEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��¥��ϸ 's ���� property 
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
     * Object:��¥��ϸ's ���property 
     */
    public int getSeq()
    {
        return getInt("seq");
    }
    public void setSeq(int item)
    {
        setInt("seq", item);
    }
    /**
     * Object:��¥��ϸ's ��������property 
     */
    public java.util.Date getDate()
    {
        return getDate("date");
    }
    public void setDate(java.util.Date item)
    {
        setDate("date", item);
    }
    /**
     * Object:��¥��ϸ's ��������property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum getMoneyType()
    {
        return com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum.getEnum(getString("moneyType"));
    }
    public void setMoneyType(com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum item)
    {
		if (item != null) {
        setString("moneyType", item.getValue());
		}
    }
    /**
     * Object:��¥��ϸ's ���property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object: ��¥��ϸ 's �ұ� property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getCurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("currency");
    }
    public void setCurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("currency", item);
    }
    /**
     * Object:��¥��ϸ's ���ɽ�property 
     */
    public java.math.BigDecimal getLateFee()
    {
        return getBigDecimal("lateFee");
    }
    public void setLateFee(java.math.BigDecimal item)
    {
        setBigDecimal("lateFee", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("95A06100");
    }
}