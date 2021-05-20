package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketProjectEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMarketProjectEntryInfo()
    {
        this("id");
    }
    protected AbstractMarketProjectEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 营销立项分录 's 头 property 
     */
    public com.kingdee.eas.fdc.contract.MarketProjectInfo getHead()
    {
        return (com.kingdee.eas.fdc.contract.MarketProjectInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.contract.MarketProjectInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 营销立项分录 's 签约单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getSupplier()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object:营销立项分录's 商定价property 
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
     * Object:营销立项分录's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A6D3CCB0");
    }
}