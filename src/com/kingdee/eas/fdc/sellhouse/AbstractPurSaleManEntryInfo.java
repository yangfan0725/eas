package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPurSaleManEntryInfo extends com.kingdee.eas.fdc.sellhouse.TranSaleManEntryInfo implements Serializable 
{
    public AbstractPurSaleManEntryInfo()
    {
        this("id");
    }
    protected AbstractPurSaleManEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �Ϲ���ҵ���ʷ�¼ 's �Ϲ����� property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1D0F2C11");
    }
}