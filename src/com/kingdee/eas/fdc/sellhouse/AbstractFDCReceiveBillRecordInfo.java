package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCReceiveBillRecordInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractFDCReceiveBillRecordInfo()
    {
        this("id");
    }
    protected AbstractFDCReceiveBillRecordInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�տ������¼'s ����ͷproperty 
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
     * Object:�տ������¼'s ͷ����property 
     */
    public int getHeadType()
    {
        return getInt("headType");
    }
    public void setHeadType(int item)
    {
        setInt("headType", item);
    }
    /**
     * Object:�տ������¼'s ����property 
     */
    public com.kingdee.eas.fdc.sellhouse.RecordTypeEnum getRecordType()
    {
        return com.kingdee.eas.fdc.sellhouse.RecordTypeEnum.getEnum(getString("recordType"));
    }
    public void setRecordType(com.kingdee.eas.fdc.sellhouse.RecordTypeEnum item)
    {
		if (item != null) {
        setString("recordType", item.getValue());
		}
    }
    /**
     * Object:�տ������¼'s ����property 
     */
    public String getContent()
    {
        return getString("content");
    }
    public void setContent(String item)
    {
        setString("content", item);
    }
    /**
     * Object: �տ������¼ 's ������ property 
     */
    public com.kingdee.eas.base.permission.UserInfo getOperatingUser()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("operatingUser");
    }
    public void setOperatingUser(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("operatingUser", item);
    }
    /**
     * Object:�տ������¼'s ��������property 
     */
    public java.util.Date getOperatingDate()
    {
        return getDate("operatingDate");
    }
    public void setOperatingDate(java.util.Date item)
    {
        setDate("operatingDate", item);
    }
    /**
     * Object:�տ������¼'s ��עproperty 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("732AA4F1");
    }
}