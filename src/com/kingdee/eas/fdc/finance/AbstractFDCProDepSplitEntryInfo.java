package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCProDepSplitEntryInfo extends com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo implements Serializable 
{
    public AbstractFDCProDepSplitEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCProDepSplitEntryInfo(String pkField)
    {
        super(pkField);
        put("details", new com.kingdee.eas.fdc.finance.FDCProDepSplitEntryEntryCollection());
    }
    /**
     * Object: �����ƻ������� 's ��ֵ��� property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepSplitInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepSplitInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.FDCProDepSplitInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: �����ƻ������� 's ��ϸ property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepSplitEntryEntryCollection getDetails()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepSplitEntryEntryCollection)get("details");
    }
    /**
     * Object: �����ƻ������� 's �����ƻ���¼ property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepConPayPlanContractInfo getFdcProDepEntry()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepConPayPlanContractInfo)get("fdcProDepEntry");
    }
    public void setFdcProDepEntry(com.kingdee.eas.fdc.finance.FDCProDepConPayPlanContractInfo item)
    {
        put("fdcProDepEntry", item);
    }
    /**
     * Object:�����ƻ�������'s �Ƿ��ǩ����ͬproperty 
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
        return new BOSObjectType("04F10150");
    }
}