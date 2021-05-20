package com.kingdee.eas.fdc.invite.markesupplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierStockSupplierAttachListEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMarketSupplierStockSupplierAttachListEntryInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierStockSupplierAttachListEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:���������嵥��¼'s ������property 
     */
    public String getNumber()
    {
        return getString("number");
    }
    public void setNumber(String item)
    {
        setString("number", item);
    }
    /**
     * Object:���������嵥��¼'s �������property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object: ���������嵥��¼ 's ��Ӧ�� property 
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
        return new BOSObjectType("495290A3");
    }
}