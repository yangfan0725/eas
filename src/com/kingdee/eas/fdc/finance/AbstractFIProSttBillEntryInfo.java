package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFIProSttBillEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractFIProSttBillEntryInfo()
    {
        this("id");
    }
    protected AbstractFIProSttBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �����������¼ 's ���������� property 
     */
    public com.kingdee.eas.fdc.finance.FIProductSettleBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.FIProductSettleBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.FIProductSettleBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: �����������¼ 's �ɱ���Ʒ���㵥 property 
     */
    public com.kingdee.eas.fdc.finance.ProductSettleBillInfo getProductBill()
    {
        return (com.kingdee.eas.fdc.finance.ProductSettleBillInfo)get("productBill");
    }
    public void setProductBill(com.kingdee.eas.fdc.finance.ProductSettleBillInfo item)
    {
        put("productBill", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("949642B3");
    }
}