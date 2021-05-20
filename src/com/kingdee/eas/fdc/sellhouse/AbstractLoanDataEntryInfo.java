package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractLoanDataEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractLoanDataEntryInfo()
    {
        this("id");
    }
    protected AbstractLoanDataEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:����������������'s ����property 
     */
    public int getFixedYear()
    {
        return getInt("fixedYear");
    }
    public void setFixedYear(int item)
    {
        setInt("fixedYear", item);
    }
    /**
     * Object:����������������'s ���ʣ�%��property 
     */
    public java.math.BigDecimal getInterestRate()
    {
        return getBigDecimal("interestRate");
    }
    public void setInterestRate(java.math.BigDecimal item)
    {
        setBigDecimal("interestRate", item);
    }
    /**
     * Object: ���������������� 's �������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.LoanDataInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.LoanDataInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.LoanDataInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("88F020DD");
    }
}