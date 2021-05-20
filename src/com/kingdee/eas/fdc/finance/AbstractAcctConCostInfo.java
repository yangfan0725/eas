package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAcctConCostInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractAcctConCostInfo()
    {
        this("id");
    }
    protected AbstractAcctConCostInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��Ŀ��ͬ���깤's ��Ŀproperty 
     */
    public String getAccount()
    {
        return getString("account");
    }
    public void setAccount(String item)
    {
        setString("account", item);
    }
    /**
     * Object:��Ŀ��ͬ���깤's ��ͬproperty 
     */
    public String getContract()
    {
        return getString("contract");
    }
    public void setContract(String item)
    {
        setString("contract", item);
    }
    /**
     * Object:��Ŀ��ͬ���깤's ���깤property 
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
     * Object:��Ŀ��ͬ���깤's ͷproperty 
     */
    public String getHead()
    {
        return getString("head");
    }
    public void setHead(String item)
    {
        setString("head", item);
    }
    /**
     * Object:��Ŀ��ͬ���깤's ������Ŀproperty 
     */
    public String getProject()
    {
        return getString("project");
    }
    public void setProject(String item)
    {
        setString("project", item);
    }
    /**
     * Object:��Ŀ��ͬ���깤's �ڼ�property 
     */
    public String getFdcPeriod()
    {
        return getString("fdcPeriod");
    }
    public void setFdcPeriod(String item)
    {
        setString("fdcPeriod", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CC31746D");
    }
}