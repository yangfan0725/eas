package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTransferSourceEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractTransferSourceEntryInfo()
    {
        this("id");
    }
    protected AbstractTransferSourceEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ת����Դ��¼ 's ���ز��տ��¼ property 
     */
    public com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo getHeadEntry()
    {
        return (com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo)get("headEntry");
    }
    public void setHeadEntry(com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo item)
    {
        put("headEntry", item);
    }
    /**
     * Object:ת����Դ��¼'s ת���տ���ϸIDproperty 
     */
    public String getFromRevListId()
    {
        return getString("fromRevListId");
    }
    public void setFromRevListId(String item)
    {
        setString("fromRevListId", item);
    }
    /**
     * Object:ת����Դ��¼'s ת���տ���ϸ����property 
     */
    public com.kingdee.eas.fdc.basecrm.RevListTypeEnum getFromRevListType()
    {
        return com.kingdee.eas.fdc.basecrm.RevListTypeEnum.getEnum(getString("fromRevListType"));
    }
    public void setFromRevListType(com.kingdee.eas.fdc.basecrm.RevListTypeEnum item)
    {
		if (item != null) {
        setString("fromRevListType", item.getValue());
		}
    }
    /**
     * Object:ת����Դ��¼'s ת����property 
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
     * Object: ת����Դ��¼ 's ת������ property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getFromMoneyDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("fromMoneyDefine");
    }
    public void setFromMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("fromMoneyDefine", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DCBE7750");
    }
}