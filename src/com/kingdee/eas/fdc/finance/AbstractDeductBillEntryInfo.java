package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDeductBillEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractDeductBillEntryInfo()
    {
        this("id");
    }
    protected AbstractDeductBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��¼ 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.finance.DeductBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.DeductBillInfo)get("Parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.DeductBillInfo item)
    {
        put("Parent", item);
    }
    /**
     * Object:��¼'s ��ͬID��������ͬ�����ı���ͬ��property 
     */
    public String getContractId()
    {
        return getString("contractId");
    }
    public void setContractId(String item)
    {
        setString("contractId", item);
    }
    /**
     * Object: ��¼ 's �ۿλ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getDeductUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("deductUnit");
    }
    public void setDeductUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("deductUnit", item);
    }
    /**
     * Object: ��¼ 's �ۿ����� property 
     */
    public com.kingdee.eas.fdc.basedata.DeductTypeInfo getDeductType()
    {
        return (com.kingdee.eas.fdc.basedata.DeductTypeInfo)get("deductType");
    }
    public void setDeductType(com.kingdee.eas.fdc.basedata.DeductTypeInfo item)
    {
        put("deductType", item);
    }
    /**
     * Object:��¼'s �ۿ�����property 
     */
    public String getDeductItem()
    {
        return getString("deductItem");
    }
    public void setDeductItem(String item)
    {
        setString("deductItem", item);
    }
    /**
     * Object:��¼'s �ۿ���property 
     */
    public java.math.BigDecimal getDeductAmt()
    {
        return getBigDecimal("deductAmt");
    }
    public void setDeductAmt(java.math.BigDecimal item)
    {
        setBigDecimal("deductAmt", item);
    }
    /**
     * Object:��¼'s �ۿ�����property 
     */
    public java.util.Date getDeductDate()
    {
        return getDate("deductDate");
    }
    public void setDeductDate(java.util.Date item)
    {
        setDate("deductDate", item);
    }
    /**
     * Object:��¼'s ��עproperty 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object:��¼'s �Ƿ�������property 
     */
    public boolean isHasApplied()
    {
        return getBoolean("hasApplied");
    }
    public void setHasApplied(boolean item)
    {
        setBoolean("hasApplied", item);
    }
    /**
     * Object:��¼'s ��¼���루Webservice�в��õ���property 
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
     * Object:��¼'s �ۿ�ԭ�ҽ��property 
     */
    public java.math.BigDecimal getDeductOriginalAmt()
    {
        return getBigDecimal("deductOriginalAmt");
    }
    public void setDeductOriginalAmt(java.math.BigDecimal item)
    {
        setBigDecimal("deductOriginalAmt", item);
    }
    /**
     * Object:��¼'s ����property 
     */
    public java.math.BigDecimal getExRate()
    {
        return getBigDecimal("exRate");
    }
    public void setExRate(java.math.BigDecimal item)
    {
        setBigDecimal("exRate", item);
    }
    /**
     * Object: ��¼ 's ��Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getAccountView()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("accountView");
    }
    public void setAccountView(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("accountView", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2081EA39");
    }
}