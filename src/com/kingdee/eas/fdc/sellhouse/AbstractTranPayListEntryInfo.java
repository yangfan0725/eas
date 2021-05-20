package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTranPayListEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractTranPayListEntryInfo()
    {
        this("id");
    }
    protected AbstractTranPayListEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:������ϸ����'s ���property 
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
     * Object: ������ϸ���� 's ���� property 
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
     * Object: ������ϸ���� 's �ұ� property 
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
     * Object:������ϸ����'s Ӧ�ս��property 
     */
    public java.math.BigDecimal getAppAmount()
    {
        return getBigDecimal("appAmount");
    }
    public void setAppAmount(java.math.BigDecimal item)
    {
        setBigDecimal("appAmount", item);
    }
    /**
     * Object:������ϸ����'s Ӧ������property 
     */
    public java.util.Date getAppDate()
    {
        return getDate("appDate");
    }
    public void setAppDate(java.util.Date item)
    {
        setDate("appDate", item);
    }
    /**
     * Object:������ϸ����'s ʵ�ս��property 
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
     * Object:������ϸ����'s ����property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object:������ϸ����'s ���׸�����ϸIdproperty 
     */
    public com.kingdee.bos.util.BOSUuid getTanPayListEntryId()
    {
        return getBOSUuid("tanPayListEntryId");
    }
    public void setTanPayListEntryId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("tanPayListEntryId", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("53F37652");
    }
}