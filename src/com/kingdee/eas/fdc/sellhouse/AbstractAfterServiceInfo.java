package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAfterServiceInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractAfterServiceInfo()
    {
        this("id");
    }
    protected AbstractAfterServiceInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �ۺ���� 's ǩԼ��id property 
     */
    public com.kingdee.eas.fdc.sellhouse.SignManageInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.SignManageInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.SignManageInfo item)
    {
        put("head", item);
    }
    /**
     * Object:�ۺ����'s ������Ŀproperty 
     */
    public com.kingdee.eas.fdc.sellhouse.AfterServiceItemEnum getServiceItem()
    {
        return com.kingdee.eas.fdc.sellhouse.AfterServiceItemEnum.getEnum(getString("serviceItem"));
    }
    public void setServiceItem(com.kingdee.eas.fdc.sellhouse.AfterServiceItemEnum item)
    {
		if (item != null) {
        setString("serviceItem", item.getValue());
		}
    }
    /**
     * Object:�ۺ����'s ��������property 
     */
    public java.util.Date getAppDate()
    {
        return getDate("appDate");
    }
    public void setAppDate(java.util.Date item)
    {
        setDate("appDate", item);
    }
    /**
     * Object:�ۺ����'s ��ŵ�������property 
     */
    public java.util.Date getFinishDate()
    {
        return getDate("finishDate");
    }
    public void setFinishDate(java.util.Date item)
    {
        setDate("finishDate", item);
    }
    /**
     * Object:�ۺ����'s ����״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.AfterServiceStateEnum getServiceState()
    {
        return com.kingdee.eas.fdc.sellhouse.AfterServiceStateEnum.getEnum(getString("serviceState"));
    }
    public void setServiceState(com.kingdee.eas.fdc.sellhouse.AfterServiceStateEnum item)
    {
		if (item != null) {
        setString("serviceState", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C1C33F94");
    }
}