package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFeeEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractFeeEntryInfo()
    {
        this("id");
    }
    protected AbstractFeeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���ñ������¼ 's ͷ property 
     */
    public com.kingdee.eas.fdc.contract.ContractWithoutTextInfo getHead()
    {
        return (com.kingdee.eas.fdc.contract.ContractWithoutTextInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.contract.ContractWithoutTextInfo item)
    {
        put("head", item);
    }
    /**
     * Object:���ñ������¼'s ������ϸproperty 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:���ñ������¼'s ����˵��property 
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
     * Object:���ñ������¼'s ���property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6496E7C7");
    }
}