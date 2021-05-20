package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFeesWarrantEntrysInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractFeesWarrantEntrysInfo()
    {
        this("id");
    }
    protected AbstractFeesWarrantEntrysInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: Ӧ�շ��÷�¼�� 's ��ͬ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getTenancyBill()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("tenancyBill");
    }
    public void setTenancyBill(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("tenancyBill", item);
    }
    /**
     * Object:Ӧ�շ��÷�¼��'s ��ͬ����property 
     */
    public String getTenancyName()
    {
        return getString("tenancyName");
    }
    public void setTenancyName(String item)
    {
        setString("tenancyName", item);
    }
    /**
     * Object: Ӧ�շ��÷�¼�� 's �ͻ����� property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo getCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo item)
    {
        put("customer", item);
    }
    /**
     * Object: Ӧ�շ��÷�¼�� 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("room");
    }
    public void setRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("room", item);
    }
    /**
     * Object: Ӧ�շ��÷�¼�� 's �������� property 
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
     * Object:Ӧ�շ��÷�¼��'s ��ʼ����property 
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
     * Object:Ӧ�շ��÷�¼��'s ��������property 
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
     * Object:Ӧ�շ��÷�¼��'s Ӧ������property 
     */
    public java.util.Date getAppDate()
    {
        return getDate("appDate");
    }
    public void setAppDate(java.util.Date item)
    {
        setDate("appDate", item);
    }
    /**
     * Object:Ӧ�շ��÷�¼��'s Ӧ�ս��property 
     */
    public java.math.BigDecimal getAppAmount()
    {
        return getBigDecimal("appAmount");
    }
    public void setAppAmount(java.math.BigDecimal item)
    {
        setBigDecimal("appAmount", item);
    }
    /**
     * Object:Ӧ�շ��÷�¼��'s ʵ�ս��property 
     */
    public java.math.BigDecimal getActRevAmount()
    {
        return getBigDecimal("actRevAmount");
    }
    public void setActRevAmount(java.math.BigDecimal item)
    {
        setBigDecimal("actRevAmount", item);
    }
    /**
     * Object:Ӧ�շ��÷�¼��'s ʵ������property 
     */
    public java.util.Date getActRevDate()
    {
        return getDate("actRevDate");
    }
    public void setActRevDate(java.util.Date item)
    {
        setDate("actRevDate", item);
    }
    /**
     * Object: Ӧ�շ��÷�¼�� 's �տ��Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("account");
    }
    public void setAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("account", item);
    }
    /**
     * Object: Ӧ�շ��÷�¼�� 's �Է���Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getOppSubject()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("oppSubject");
    }
    public void setOppSubject(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("oppSubject", item);
    }
    /**
     * Object: Ӧ�շ��÷�¼�� 's ��ͷ property 
     */
    public com.kingdee.eas.fdc.tenancy.FeesWarrantInfo getParent()
    {
        return (com.kingdee.eas.fdc.tenancy.FeesWarrantInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.tenancy.FeesWarrantInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:Ӧ�շ��÷�¼��'s ˰��property 
     */
    public java.math.BigDecimal getTaxRate()
    {
        return getBigDecimal("taxRate");
    }
    public void setTaxRate(java.math.BigDecimal item)
    {
        setBigDecimal("taxRate", item);
    }
    /**
     * Object:Ӧ�շ��÷�¼��'s Ӧ�ս���˰property 
     */
    public java.math.BigDecimal getAppAmountWithOutTax()
    {
        return getBigDecimal("appAmountWithOutTax");
    }
    public void setAppAmountWithOutTax(java.math.BigDecimal item)
    {
        setBigDecimal("appAmountWithOutTax", item);
    }
    /**
     * Object:Ӧ�շ��÷�¼��'s ˰��property 
     */
    public java.math.BigDecimal getTaxAmount()
    {
        return getBigDecimal("taxAmount");
    }
    public void setTaxAmount(java.math.BigDecimal item)
    {
        setBigDecimal("taxAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2C0D2C88");
    }
}