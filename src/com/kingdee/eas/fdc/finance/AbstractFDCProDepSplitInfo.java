package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCProDepSplitInfo extends com.kingdee.eas.fdc.basedata.FDCSplitBillInfo implements Serializable 
{
    public AbstractFDCProDepSplitInfo()
    {
        this("id");
    }
    protected AbstractFDCProDepSplitInfo(String pkField)
    {
        super(pkField);
        put("noCons", new com.kingdee.eas.fdc.finance.FDCProDepSplitNoConCollection());
        put("unCons", new com.kingdee.eas.fdc.finance.FDCProDepSplitUnConCollection());
        put("hasCons", new com.kingdee.eas.fdc.finance.FDCProDepSplitHasConCollection());
    }
    /**
     * Object: ��������ƻ���� 's ����� property 
     */
    public com.kingdee.eas.base.permission.UserInfo getSplitUser()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("splitUser");
    }
    public void setSplitUser(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("splitUser", item);
    }
    /**
     * Object: ��������ƻ���� 's ��������ƻ� property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepConPayPlanInfo getFdcProDep()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepConPayPlanInfo)get("fdcProDep");
    }
    public void setFdcProDep(com.kingdee.eas.fdc.finance.FDCProDepConPayPlanInfo item)
    {
        put("fdcProDep", item);
    }
    /**
     * Object:��������ƻ����'s �������property 
     */
    public java.util.Date getSplitDate()
    {
        return getDate("splitDate");
    }
    public void setSplitDate(java.util.Date item)
    {
        setDate("splitDate", item);
    }
    /**
     * Object: ��������ƻ���� 's ��ǩ����ͬ��ַ�¼ property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepSplitHasConCollection getHasCons()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepSplitHasConCollection)get("hasCons");
    }
    /**
     * Object: ��������ƻ���� 's ��ǩ����ͬ��ַ�¼ property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepSplitUnConCollection getUnCons()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepSplitUnConCollection)get("unCons");
    }
    /**
     * Object: ��������ƻ���� 's �޺�ͬ��ַ�¼ property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepSplitNoConCollection getNoCons()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepSplitNoConCollection)get("noCons");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A544F042");
    }
}