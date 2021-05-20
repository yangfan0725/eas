package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectCostChangeLogInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractProjectCostChangeLogInfo()
    {
        this("id");
    }
    protected AbstractProjectCostChangeLogInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��Ŀ�ɱ��仯��'s ��ĿIdproperty 
     */
    public String getProjectId()
    {
        return getString("projectId");
    }
    public void setProjectId(String item)
    {
        setString("projectId", item);
    }
    /**
     * Object:��Ŀ�ɱ��仯��'s �Ƿ��Ѹı�property 
     */
    public boolean isHasChanged()
    {
        return getBoolean("hasChanged");
    }
    public void setHasChanged(boolean item)
    {
        setBoolean("hasChanged", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7839CD65");
    }
}