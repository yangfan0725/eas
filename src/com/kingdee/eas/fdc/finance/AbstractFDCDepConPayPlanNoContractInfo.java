package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCDepConPayPlanNoContractInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractFDCDepConPayPlanNoContractInfo()
    {
        this("id");
    }
    protected AbstractFDCDepConPayPlanNoContractInfo(String pkField)
    {
        super(pkField);
        put("entrys1", new com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractEntryCollection());
    }
    /**
     * Object: �޺�ͬ����ƻ� 's ��ͷ property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object: �޺�ͬ����ƻ� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object:�޺�ͬ����ƻ�'s �����������property 
     */
    public String getPayMatters()
    {
        return getString("payMatters");
    }
    public void setPayMatters(String item)
    {
        setString("payMatters", item);
    }
    /**
     * Object:�޺�ͬ����ƻ�'s ������������property 
     */
    public String getPayMattersName()
    {
        return getString("payMattersName");
    }
    public void setPayMattersName(String item)
    {
        setString("payMattersName", item);
    }
    /**
     * Object: �޺�ͬ����ƻ� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractEntryCollection getEntrys1()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractEntryCollection)get("entrys1");
    }
    /**
     * Object:�޺�ͬ����ƻ�'s �Ƿ񱻴��property 
     */
    public boolean isIsBack()
    {
        return getBoolean("isBack");
    }
    public void setIsBack(boolean item)
    {
        setBoolean("isBack", item);
    }
    /**
     * Object:�޺�ͬ����ƻ�'s ��غ��Ƿ��޸Ĺ�property 
     */
    public boolean isIsEdit()
    {
        return getBoolean("isEdit");
    }
    public void setIsEdit(boolean item)
    {
        setBoolean("isEdit", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EB0C8D3B");
    }
}