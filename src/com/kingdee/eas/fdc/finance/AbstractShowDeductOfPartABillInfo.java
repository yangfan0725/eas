package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractShowDeductOfPartABillInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractShowDeductOfPartABillInfo()
    {
        this("id");
    }
    protected AbstractShowDeductOfPartABillInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�׹��Ŀۿ��οۿ������ϸ's �ۿ��ܽ��property 
     */
    public java.math.BigDecimal getDeductAmount()
    {
        return getBigDecimal("deductAmount");
    }
    public void setDeductAmount(java.math.BigDecimal item)
    {
        setBigDecimal("deductAmount", item);
    }
    /**
     * Object:�׹��Ŀۿ��οۿ������ϸ's ���οۿ���property 
     */
    public java.math.BigDecimal getThisTimeDeductAmount()
    {
        return getBigDecimal("thisTimeDeductAmount");
    }
    public void setThisTimeDeductAmount(java.math.BigDecimal item)
    {
        setBigDecimal("thisTimeDeductAmount", item);
    }
    /**
     * Object:�׹��Ŀۿ��οۿ������ϸ's ���ۿ����property 
     */
    public java.math.BigDecimal getStayDeductAmount()
    {
        return getBigDecimal("stayDeductAmount");
    }
    public void setStayDeductAmount(java.math.BigDecimal item)
    {
        setBigDecimal("stayDeductAmount", item);
    }
    /**
     * Object:�׹��Ŀۿ��οۿ������ϸ's �������뵥IDproperty 
     */
    public String getPayReqID()
    {
        return getString("payReqID");
    }
    public void setPayReqID(String item)
    {
        setString("payReqID", item);
    }
    /**
     * Object:�׹��Ŀۿ��οۿ������ϸ's �ۿ��¼IDproperty 
     */
    public String getDeductEntryID()
    {
        return getString("deductEntryID");
    }
    public void setDeductEntryID(String item)
    {
        setString("deductEntryID", item);
    }
    /**
     * Object:�׹��Ŀۿ��οۿ������ϸ's �Ƿ��ѱ�����property 
     */
    public boolean isIsRelated()
    {
        return getBoolean("isRelated");
    }
    public void setIsRelated(boolean item)
    {
        setBoolean("isRelated", item);
    }
    /**
     * Object:�׹��Ŀۿ��οۿ������ϸ's �ѿۿ���property 
     */
    public java.math.BigDecimal getHasDeductAmount()
    {
        return getBigDecimal("hasDeductAmount");
    }
    public void setHasDeductAmount(java.math.BigDecimal item)
    {
        setBigDecimal("hasDeductAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("33470BCF");
    }
}