package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCBaseVoucherEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractFDCBaseVoucherEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCBaseVoucherEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:FDC����ƾ֤��¼'s ���property 
     */
    public String getType()
    {
        return getString("type");
    }
    public void setType(String item)
    {
        setString("type", item);
    }
    /**
     * Object:FDC����ƾ֤��¼'s ԭ�ҽ��property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:FDC����ƾ֤��¼'s ���ҽ��property 
     */
    public java.math.BigDecimal getLocateAmount()
    {
        return getBigDecimal("locateAmount");
    }
    public void setLocateAmount(java.math.BigDecimal item)
    {
        setBigDecimal("locateAmount", item);
    }
    /**
     * Object:FDC����ƾ֤��¼'s ����property 
     */
    public java.math.BigDecimal getExchangeRate()
    {
        return getBigDecimal("exchangeRate");
    }
    public void setExchangeRate(java.math.BigDecimal item)
    {
        setBigDecimal("exchangeRate", item);
    }
    /**
     * Object:FDC����ƾ֤��¼'s ����property 
     */
    public String getDesc()
    {
        return getString("desc");
    }
    public void setDesc(String item)
    {
        setString("desc", item);
    }
    /**
     * Object: FDC����ƾ֤��¼ 's ��ƿ�Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getAccountView()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("accountView");
    }
    public void setAccountView(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("accountView", item);
    }
    /**
     * Object: FDC����ƾ֤��¼ 's ��Ӧ�� property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getSupplier()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object: FDC����ƾ֤��¼ 's �ұ� property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getCurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("currency");
    }
    public void setCurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("currency", item);
    }
    /**
     * Object: FDC����ƾ֤��¼ 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object: FDC����ƾ֤��¼ 's �ɱ���Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getCostAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("costAccount");
    }
    public void setCostAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("costAccount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("69327BEB");
    }
}