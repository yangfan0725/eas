package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractNewListingValueInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractNewListingValueInfo()
    {
        this("id");
    }
    protected AbstractNewListingValueInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �嵥���� 's �����ĸ���¼ property 
     */
    public com.kingdee.eas.fdc.invite.NewListingEntryInfo getEntry()
    {
        return (com.kingdee.eas.fdc.invite.NewListingEntryInfo)get("entry");
    }
    public void setEntry(com.kingdee.eas.fdc.invite.NewListingEntryInfo item)
    {
        put("entry", item);
    }
    /**
     * Object: �嵥���� 's �� property 
     */
    public com.kingdee.eas.fdc.invite.NewListingColumnInfo getColumn()
    {
        return (com.kingdee.eas.fdc.invite.NewListingColumnInfo)get("column");
    }
    public void setColumn(com.kingdee.eas.fdc.invite.NewListingColumnInfo item)
    {
        put("column", item);
    }
    /**
     * Object: �嵥���� 's ���� property 
     */
    public com.kingdee.eas.fdc.invite.NewQuotingPriceInfo getQuotingPrice()
    {
        return (com.kingdee.eas.fdc.invite.NewQuotingPriceInfo)get("quotingPrice");
    }
    public void setQuotingPrice(com.kingdee.eas.fdc.invite.NewQuotingPriceInfo item)
    {
        put("quotingPrice", item);
    }
    /**
     * Object:�嵥����'s ���property 
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
     * Object:�嵥����'s �ı���Ϣproperty 
     */
    public String getText()
    {
        return getString("text");
    }
    public void setText(String item)
    {
        setString("text", item);
    }
    /**
     * Object:�嵥����'s ������ֵproperty 
     */
    public java.util.Date getDateValue()
    {
        return getDate("dateValue");
    }
    public void setDateValue(java.util.Date item)
    {
        setDate("dateValue", item);
    }
    /**
     * Object:�嵥����'s ��������property 
     */
    public com.kingdee.eas.fdc.invite.QuotingPriceTypeEnum getType()
    {
        return com.kingdee.eas.fdc.invite.QuotingPriceTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.invite.QuotingPriceTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D6CFB769");
    }
}