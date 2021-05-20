package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDepConPayPlanSplitEntryInfo extends com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo implements Serializable 
{
    public AbstractDepConPayPlanSplitEntryInfo()
    {
        this("id");
    }
    protected AbstractDepConPayPlanSplitEntryInfo(String pkField)
    {
        super(pkField);
        put("items", new com.kingdee.eas.fdc.finance.DepConPayPlanSplitItemCollection());
    }
    /**
     * Object: ���ź�ͬ����ƻ���ַ�¼ 's ͷ property 
     */
    public com.kingdee.eas.fdc.finance.DepConPayPlanSplitBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.DepConPayPlanSplitBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.DepConPayPlanSplitBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ���ź�ͬ����ƻ���ַ�¼ 's ��Ŀ property 
     */
    public com.kingdee.eas.fdc.finance.DepConPayPlanSplitItemCollection getItems()
    {
        return (com.kingdee.eas.fdc.finance.DepConPayPlanSplitItemCollection)get("items");
    }
    /**
     * Object: ���ź�ͬ����ƻ���ַ�¼ 's ��Ӧ����ƻ���¼ property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanEntryInfo getPayPlanEntry()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanEntryInfo)get("payPlanEntry");
    }
    public void setPayPlanEntry(com.kingdee.eas.fdc.finance.FDCDepConPayPlanEntryInfo item)
    {
        put("payPlanEntry", item);
    }
    /**
     * Object:���ź�ͬ����ƻ���ַ�¼'s �Ƿ��Ǵ�ǩ����ͬproperty 
     */
    public boolean isIsUnsettledCon()
    {
        return getBoolean("isUnsettledCon");
    }
    public void setIsUnsettledCon(boolean item)
    {
        setBoolean("isUnsettledCon", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("339A63A7");
    }
}