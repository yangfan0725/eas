package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSettlementTypeEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSettlementTypeEntryInfo()
    {
        this("id");
    }
    protected AbstractSettlementTypeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���㷽ʽ��¼ 's �������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("head", item);
    }
    /**
     * Object: ���㷽ʽ��¼ 's ���㷽ʽ property 
     */
    public com.kingdee.eas.basedata.assistant.SettlementTypeInfo getSettlementType()
    {
        return (com.kingdee.eas.basedata.assistant.SettlementTypeInfo)get("settlementType");
    }
    public void setSettlementType(com.kingdee.eas.basedata.assistant.SettlementTypeInfo item)
    {
        put("settlementType", item);
    }
    /**
     * Object:���㷽ʽ��¼'s �Զ�����property 
     */
    public boolean isIsBrought()
    {
        return getBoolean("isBrought");
    }
    public void setIsBrought(boolean item)
    {
        setBoolean("isBrought", item);
    }
    /**
     * Object:���㷽ʽ��¼'s Ĭ���տ��ʺ�property 
     */
    public String getPaymentAccount()
    {
        return getString("paymentAccount");
    }
    public void setPaymentAccount(String item)
    {
        setString("paymentAccount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9F3C0F74");
    }
}