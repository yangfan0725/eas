package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCDepMonBudgetAcctInfo extends com.kingdee.eas.fdc.finance.FDCBudgetAcctInfo implements Serializable 
{
    public AbstractFDCDepMonBudgetAcctInfo()
    {
        this("id");
    }
    protected AbstractFDCDepMonBudgetAcctInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctEntryCollection());
    }
    /**
     * Object: �����¶ȼƻ��걨�� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctEntryCollection)get("entrys");
    }
    /**
     * Object: �����¶ȼƻ��걨�� 's ���� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getDeptment()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("deptment");
    }
    public void setDeptment(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("deptment", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("361EFD6B");
    }
}