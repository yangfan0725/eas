package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCProDepSplitEntryEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractFDCProDepSplitEntryEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCProDepSplitEntryEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��������ƻ������ϸ 's ��ַ�¼ property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepSplitEntryInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepSplitEntryInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.FDCProDepSplitEntryInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ��������ƻ������ϸ 's �����ϸ property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepConPayContractEntryInfo getDetails()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepConPayContractEntryInfo)get("details");
    }
    public void setDetails(com.kingdee.eas.fdc.finance.FDCProDepConPayContractEntryInfo item)
    {
        put("details", item);
    }
    /**
     * Object:��������ƻ������ϸ's ����깤������property 
     */
    public java.math.BigDecimal getSptWorkload()
    {
        return getBigDecimal("sptWorkload");
    }
    public void setSptWorkload(java.math.BigDecimal item)
    {
        setBigDecimal("sptWorkload", item);
    }
    /**
     * Object:��������ƻ������ϸ's ��ּƻ�֧��property 
     */
    public java.math.BigDecimal getSptPay()
    {
        return getBigDecimal("sptPay");
    }
    public void setSptPay(java.math.BigDecimal item)
    {
        setBigDecimal("sptPay", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("AB0B6902");
    }
}