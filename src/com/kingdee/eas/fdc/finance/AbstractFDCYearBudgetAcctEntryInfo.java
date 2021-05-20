package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCYearBudgetAcctEntryInfo extends com.kingdee.eas.fdc.finance.FDCBudgetAcctEntryInfo implements Serializable 
{
    public AbstractFDCYearBudgetAcctEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCYearBudgetAcctEntryInfo(String pkField)
    {
        super(pkField);
        put("items", new com.kingdee.eas.fdc.finance.FDCYearBudgetAcctItemCollection());
    }
    /**
     * Object: ��Ŀ���Ԥ���걨���¼ 's �¶�Ԥ�� property 
     */
    public com.kingdee.eas.fdc.finance.FDCYearBudgetAcctInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.FDCYearBudgetAcctInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.FDCYearBudgetAcctInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ��Ŀ���Ԥ���걨���¼ 's �ƻ��� property 
     */
    public com.kingdee.eas.fdc.finance.FDCYearBudgetAcctItemCollection getItems()
    {
        return (com.kingdee.eas.fdc.finance.FDCYearBudgetAcctItemCollection)get("items");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E198A487");
    }
}