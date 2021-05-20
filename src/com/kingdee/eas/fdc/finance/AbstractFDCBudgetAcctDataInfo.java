package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCBudgetAcctDataInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractFDCBudgetAcctDataInfo()
    {
        this("id");
    }
    protected AbstractFDCBudgetAcctDataInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��ĿԤ���Ŀ����'s ��ĿԤ���IDproperty 
     */
    public String getFdcbudgetId()
    {
        return getString("fdcbudgetId");
    }
    public void setFdcbudgetId(String item)
    {
        setString("fdcbudgetId", item);
    }
    /**
     * Object:��ĿԤ���Ŀ����'s �ɱ���ĿIDproperty 
     */
    public String getCostAccountId()
    {
        return getString("costAccountId");
    }
    public void setCostAccountId(String item)
    {
        setString("costAccountId", item);
    }
    /**
     * Object:��ĿԤ���Ŀ����'s Ŀ��ɱ�property 
     */
    public java.math.BigDecimal getAimCost()
    {
        return getBigDecimal("aimCost");
    }
    public void setAimCost(java.math.BigDecimal item)
    {
        setBigDecimal("aimCost", item);
    }
    /**
     * Object:��ĿԤ���Ŀ����'s ��̬�ɱ�property 
     */
    public java.math.BigDecimal getDynCost()
    {
        return getBigDecimal("dynCost");
    }
    public void setDynCost(java.math.BigDecimal item)
    {
        setBigDecimal("dynCost", item);
    }
    /**
     * Object:��ĿԤ���Ŀ����'s ��ֹ����/����ĩ�ۼƷ����ɱ�property 
     */
    public java.math.BigDecimal getLstCost()
    {
        return getBigDecimal("lstCost");
    }
    public void setLstCost(java.math.BigDecimal item)
    {
        setBigDecimal("lstCost", item);
    }
    /**
     * Object:��ĿԤ���Ŀ����'s ��ֹ����/����ĩ�ۼƷ�������property 
     */
    public java.math.BigDecimal getLstPay()
    {
        return getBigDecimal("lstPay");
    }
    public void setLstPay(java.math.BigDecimal item)
    {
        setBigDecimal("lstPay", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4E341258");
    }
}