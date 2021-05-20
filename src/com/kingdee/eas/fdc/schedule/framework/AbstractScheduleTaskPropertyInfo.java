package com.kingdee.eas.fdc.schedule.framework;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractScheduleTaskPropertyInfo extends com.kingdee.eas.fdc.schedule.framework.SchedulePropBaseInfo implements Serializable 
{
    public AbstractScheduleTaskPropertyInfo()
    {
        this("id");
    }
    protected AbstractScheduleTaskPropertyInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��������'s ����property 
     */
    public com.kingdee.eas.fdc.schedule.framework.TaskPropertyTypeEnum getType()
    {
        return com.kingdee.eas.fdc.schedule.framework.TaskPropertyTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.schedule.framework.TaskPropertyTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    /**
     * Object:��������'s ֵ����property 
     */
    public String getValueType()
    {
        return getString("valueType");
    }
    public void setValueType(String item)
    {
        setString("valueType", item);
    }
    /**
     * Object:��������'s ����IDproperty 
     */
    public String getPropertyId()
    {
        return getString("propertyId");
    }
    public void setPropertyId(String item)
    {
        setString("propertyId", item);
    }
    /**
     * Object:��������'s ���Լ�property 
     */
    public String getKey()
    {
        return getString("key");
    }
    public void setKey(String item)
    {
        setString("key", item);
    }
    /**
     * Object:��������'s ӳ���property 
     */
    public String getMapKey()
    {
        return getString("mapKey");
    }
    public void setMapKey(String item)
    {
        setString("mapKey", item);
    }
    /**
     * Object:��������'s ֵ����property 
     */
    public String getValueAtt()
    {
        return getString("valueAtt");
    }
    public void setValueAtt(String item)
    {
        setString("valueAtt", item);
    }
    /**
     * Object:��������'s ��עproperty 
     */
    public String getDesc()
    {
        return getString("desc");
    }
    public void setDesc(String item)
    {
        setString("desc", item);
    }
    /**
     * Object:��������'s ��������ʾ����property 
     */
    public int getDisplayUI()
    {
        return getInt("displayUI");
    }
    public void setDisplayUI(int item)
    {
        setInt("displayUI", item);
    }
    /**
     * Object:��������'s ���������ؽ���property 
     */
    public int getHideUI()
    {
        return getInt("hideUI");
    }
    public void setHideUI(int item)
    {
        setInt("hideUI", item);
    }
    /**
     * Object:��������'s Ĭ���п�property 
     */
    public int getWidth()
    {
        return getInt("width");
    }
    public void setWidth(int item)
    {
        setInt("width", item);
    }
    /**
     * Object:��������'s ����property 
     */
    public int getAlign()
    {
        return getInt("align");
    }
    public void setAlign(int item)
    {
        setInt("align", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6CD1CA37");
    }
}