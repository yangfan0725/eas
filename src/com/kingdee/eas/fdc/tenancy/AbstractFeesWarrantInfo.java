package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFeesWarrantInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractFeesWarrantInfo()
    {
        this("id");
    }
    protected AbstractFeesWarrantInfo(String pkField)
    {
        super(pkField);
        put("feesWarrantEntry", new com.kingdee.eas.fdc.tenancy.FeesWarrantEntrysCollection());
    }
    /**
     * Object: Ӧ�շ���ƾ֤ 's ������Ŀ property 
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
     * Object: Ӧ�շ���ƾ֤ 's Ӧ�շ���ƾ֤��¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.FeesWarrantEntrysCollection getFeesWarrantEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.FeesWarrantEntrysCollection)get("feesWarrantEntry");
    }
    /**
     * Object:Ӧ�շ���ƾ֤'s �Ƿ�������ƾ֤property 
     */
    public boolean isFiVouchered()
    {
        return getBoolean("fiVouchered");
    }
    public void setFiVouchered(boolean item)
    {
        setBoolean("fiVouchered", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("AA2EA367");
    }
}