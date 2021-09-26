package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractThirdPartyExpenseBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractThirdPartyExpenseBillInfo()
    {
        this("id");
    }
    protected AbstractThirdPartyExpenseBillInfo(String pkField)
    {
        super(pkField);
        put("handEntry", new com.kingdee.eas.fdc.contract.ThirdPartyExpenseBillHandEntryCollection());
        put("entry", new com.kingdee.eas.fdc.contract.ThirdPartyExpenseBillEntryCollection());
    }
    /**
     * Object: �������������뵥 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object: �������������뵥 's ��¼ property 
     */
    public com.kingdee.eas.fdc.contract.ThirdPartyExpenseBillEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.contract.ThirdPartyExpenseBillEntryCollection)get("entry");
    }
    /**
     * Object:�������������뵥's �·�property 
     */
    public String getMonth()
    {
        return getString("month");
    }
    public void setMonth(String item)
    {
        setString("month", item);
    }
    /**
     * Object: �������������뵥 's �ֹ���¼ property 
     */
    public com.kingdee.eas.fdc.contract.ThirdPartyExpenseBillHandEntryCollection getHandEntry()
    {
        return (com.kingdee.eas.fdc.contract.ThirdPartyExpenseBillHandEntryCollection)get("handEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D45131E5");
    }
}