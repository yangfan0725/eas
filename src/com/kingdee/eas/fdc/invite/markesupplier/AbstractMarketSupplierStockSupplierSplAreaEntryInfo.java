package com.kingdee.eas.fdc.invite.markesupplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierStockSupplierSplAreaEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractMarketSupplierStockSupplierSplAreaEntryInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierStockSupplierSplAreaEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���������¼ 's �������� property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAreaInfo getFdcSplArea()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAreaInfo)get("fdcSplArea");
    }
    public void setFdcSplArea(com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAreaInfo item)
    {
        put("fdcSplArea", item);
    }
    /**
     * Object: ���������¼ 's ��Ӧ�� property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo getHead()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("365C6C82");
    }
}