package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChequeRevListEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractChequeRevListEntryInfo()
    {
        this("id");
    }
    protected AbstractChequeRevListEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: Ʊ���տ��ϸ 's Ʊ����ϸͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo getChequeDetail()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo)get("chequeDetail");
    }
    public void setChequeDetail(com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo item)
    {
        put("chequeDetail", item);
    }
    /**
     * Object: Ʊ���տ��ϸ 's �տ��ϸ property 
     */
    public com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo getRevBillEntry()
    {
        return (com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo)get("revBillEntry");
    }
    public void setRevBillEntry(com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo item)
    {
        put("revBillEntry", item);
    }
    /**
     * Object:Ʊ���տ��ϸ's ��Ʊ���property 
     */
    public java.math.BigDecimal getChequeAmount()
    {
        return getBigDecimal("chequeAmount");
    }
    public void setChequeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("chequeAmount", item);
    }
    /**
     * Object:Ʊ���տ��ϸ's �Ƿ�ѡproperty 
     */
    public boolean isIsSelect()
    {
        return getBoolean("isSelect");
    }
    public void setIsSelect(boolean item)
    {
        setBoolean("isSelect", item);
    }
    /**
     * Object:Ʊ���տ��ϸ's �վݺ�property 
     */
    public String getReceipt()
    {
        return getString("receipt");
    }
    public void setReceipt(String item)
    {
        setString("receipt", item);
    }
    /**
     * Object:Ʊ���տ��ϸ's ��Ʊ��property 
     */
    public String getInvoiceNum()
    {
        return getString("invoiceNum");
    }
    public void setInvoiceNum(String item)
    {
        setString("invoiceNum", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("257A2BED");
    }
}