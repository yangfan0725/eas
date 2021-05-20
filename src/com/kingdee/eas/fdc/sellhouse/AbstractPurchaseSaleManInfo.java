package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPurchaseSaleManInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractPurchaseSaleManInfo()
    {
        this("id");
    }
    protected AbstractPurchaseSaleManInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �Ϲ����ڶ����۹��ʷ�¼ 's �Ϲ��� property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.PurchaseInfo item)
    {
        put("head", item);
    }
    /**
     * Object: �Ϲ����ڶ����۹��ʷ�¼ 's �ڶ����۹��� property 
     */
    public com.kingdee.eas.base.permission.UserInfo getSecondMan()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("secondMan");
    }
    public void setSecondMan(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("secondMan", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0B777B17");
    }
}