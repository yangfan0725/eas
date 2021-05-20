package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayReqInvoiceEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractPayReqInvoiceEntryInfo()
    {
        this("id");
    }
    protected AbstractPayReqInvoiceEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 发票管理分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.contract.PayRequestBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.PayRequestBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.PayRequestBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 发票管理分录 's 发票 property 
     */
    public com.kingdee.eas.fdc.contract.ContractInvoiceInfo getInvoice()
    {
        return (com.kingdee.eas.fdc.contract.ContractInvoiceInfo)get("invoice");
    }
    public void setInvoice(com.kingdee.eas.fdc.contract.ContractInvoiceInfo item)
    {
        put("invoice", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1D6604B6");
    }
}