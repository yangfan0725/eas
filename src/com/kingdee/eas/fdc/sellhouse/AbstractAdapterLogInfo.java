package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAdapterLogInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractAdapterLogInfo()
    {
        this("id");
    }
    protected AbstractAdapterLogInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ת����ʷ��¼ 's ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo item)
    {
        put("head", item);
    }
    /**
     * Object: ת����ʷ��¼ 's תǰ���۹��� property 
     */
    public com.kingdee.eas.base.permission.UserInfo getBeforeSeller()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("beforeSeller");
    }
    public void setBeforeSeller(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("beforeSeller", item);
    }
    /**
     * Object: ת����ʷ��¼ 's ת�����۹��� property 
     */
    public com.kingdee.eas.base.permission.UserInfo getAfterSeller()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("afterSeller");
    }
    public void setAfterSeller(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("afterSeller", item);
    }
    /**
     * Object: ת����ʷ��¼ 's ת�Ӳ����� property 
     */
    public com.kingdee.eas.base.permission.UserInfo getOperationPerson()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("operationPerson");
    }
    public void setOperationPerson(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("operationPerson", item);
    }
    /**
     * Object:ת����ʷ��¼'s ת������property 
     */
    public java.util.Date getAdapterDate()
    {
        return getDate("adapterDate");
    }
    public void setAdapterDate(java.util.Date item)
    {
        setDate("adapterDate", item);
    }
    /**
     * Object:ת����ʷ��¼'s �Ƿ�ת�������̻�property 
     */
    public boolean isIsAdapterInter()
    {
        return getBoolean("isAdapterInter");
    }
    public void setIsAdapterInter(boolean item)
    {
        setBoolean("isAdapterInter", item);
    }
    /**
     * Object:ת����ʷ��¼'s �Ƿ�ת��ҵ������̻�property 
     */
    public boolean isIsAdapterFunction()
    {
        return getBoolean("isAdapterFunction");
    }
    public void setIsAdapterFunction(boolean item)
    {
        setBoolean("isAdapterFunction", item);
    }
    /**
     * Object:ת����ʷ��¼'s �Ƿ�ת����ֹ�̻�property 
     */
    public boolean isIsEndAdapter()
    {
        return getBoolean("isEndAdapter");
    }
    public void setIsEndAdapter(boolean item)
    {
        setBoolean("isEndAdapter", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("030C6290");
    }
}