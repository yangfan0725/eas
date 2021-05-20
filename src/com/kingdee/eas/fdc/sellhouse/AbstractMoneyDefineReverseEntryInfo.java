package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMoneyDefineReverseEntryInfo extends com.kingdee.eas.framework.ObjectBaseInfo implements Serializable 
{
    public AbstractMoneyDefineReverseEntryInfo()
    {
        this("id");
    }
    protected AbstractMoneyDefineReverseEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �ɺ����� 's ����� property 
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
     * Object: �ɺ����� 's ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineSellProjectInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineSellProjectInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.MoneyDefineSellProjectInfo item)
    {
        put("head", item);
    }
    /**
     * Object:�ɺ�����'s ���˳��property 
     */
    public String getOrderNumber()
    {
        return getString("orderNumber");
    }
    public void setOrderNumber(String item)
    {
        setString("orderNumber", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E3A1EFB0");
    }
}