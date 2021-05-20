package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSellSupplyPlanInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractSellSupplyPlanInfo()
    {
        this("id");
    }
    protected AbstractSellSupplyPlanInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.market.SellSupplyPlanEntryCollection());
    }
    /**
     * Object: ���۹�Ӧ�ƻ� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.market.SellSupplyPlanEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.market.SellSupplyPlanEntryCollection)get("entrys");
    }
    /**
     * Object:���۹�Ӧ�ƻ�'s �Ƿ�����ƾ֤property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object:���۹�Ӧ�ƻ�'s �Ƿ�����property 
     */
    public boolean isUnlocked()
    {
        return getBoolean("unlocked");
    }
    public void setUnlocked(boolean item)
    {
        setBoolean("unlocked", item);
    }
    /**
     * Object: ���۹�Ӧ�ƻ� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("66ED29BB");
    }
}