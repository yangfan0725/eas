package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectMonthPlanProEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProjectMonthPlanProEntryInfo()
    {
        this("id");
    }
    protected AbstractProjectMonthPlanProEntryInfo(String pkField)
    {
        super(pkField);
        put("dateEntry", new com.kingdee.eas.fdc.finance.ProjectMonthPlanProDateEntryCollection());
    }
    /**
     * Object: ��ǩ��ͬ&&�޺�ͬ���ø���ƻ���¼ 's �Ϲ�滮 property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo getProgrammingContract()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo)get("programmingContract");
    }
    public void setProgrammingContract(com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo item)
    {
        put("programmingContract", item);
    }
    /**
     * Object: ��ǩ��ͬ&&�޺�ͬ���ø���ƻ���¼ 's ��ǩ��ͬ&&�޺�ͬ���ø���ƻ� property 
     */
    public com.kingdee.eas.fdc.finance.ProjectMonthPlanProInfo getHead()
    {
        return (com.kingdee.eas.fdc.finance.ProjectMonthPlanProInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.finance.ProjectMonthPlanProInfo item)
    {
        put("head", item);
    }
    /**
     * Object: ��ǩ��ͬ&&�޺�ͬ���ø���ƻ���¼ 's ��ϸ��¼ property 
     */
    public com.kingdee.eas.fdc.finance.ProjectMonthPlanProDateEntryCollection getDateEntry()
    {
        return (com.kingdee.eas.fdc.finance.ProjectMonthPlanProDateEntryCollection)get("dateEntry");
    }
    /**
     * Object:��ǩ��ͬ&&�޺�ͬ���ø���ƻ���¼'s ����property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:��ǩ��ͬ&&�޺�ͬ���ø���ƻ���¼'s ���property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2C8EE8A4");
    }
}