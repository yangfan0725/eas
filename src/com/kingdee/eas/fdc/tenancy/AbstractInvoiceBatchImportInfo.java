package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInvoiceBatchImportInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractInvoiceBatchImportInfo()
    {
        this("id");
    }
    protected AbstractInvoiceBatchImportInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.tenancy.InvoiceBatchImportEntryCollection());
    }
    /**
     * Object: 发票批量开具 's 营销项目 property 
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
     * Object:发票批量开具's 开始日期property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:发票批量开具's 结束日期property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object: 发票批量开具 's 款项类型 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getMoneyDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("moneyDefine");
    }
    public void setMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("moneyDefine", item);
    }
    /**
     * Object: 发票批量开具 's 分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.InvoiceBatchImportEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.InvoiceBatchImportEntryCollection)get("entry");
    }
    /**
     * Object:发票批量开具's 导出开票明细property 
     */
    public boolean isIsExported()
    {
        return getBoolean("isExported");
    }
    public void setIsExported(boolean item)
    {
        setBoolean("isExported", item);
    }
    /**
     * Object:发票批量开具's 发票类型property 
     */
    public String getInvoiceType()
    {
        return getString("invoiceType");
    }
    public void setInvoiceType(String item)
    {
        setString("invoiceType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0BD9F37B");
    }
}