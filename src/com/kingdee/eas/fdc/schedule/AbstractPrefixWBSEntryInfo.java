package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPrefixWBSEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractPrefixWBSEntryInfo()
    {
        this("id");
    }
    protected AbstractPrefixWBSEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ǰ��WBS���� 's ��ͷ property 
     */
    public com.kingdee.eas.fdc.schedule.FDCWBSInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.FDCWBSInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.FDCWBSInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ǰ��WBS���� 's ǰ��WBS���� property 
     */
    public com.kingdee.eas.fdc.schedule.FDCWBSInfo getPrefixWBS()
    {
        return (com.kingdee.eas.fdc.schedule.FDCWBSInfo)get("prefixWBS");
    }
    public void setPrefixWBS(com.kingdee.eas.fdc.schedule.FDCWBSInfo item)
    {
        put("prefixWBS", item);
    }
    /**
     * Object:ǰ��WBS����'s ��������property 
     */
    public com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum getLinkType()
    {
        return com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum.getEnum(getString("linkType"));
    }
    public void setLinkType(com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum item)
    {
		if (item != null) {
        setString("linkType", item.getValue());
		}
    }
    /**
     * Object:ǰ��WBS����'s ���ʱ��property 
     */
    public int getLinkDay()
    {
        return getInt("linkDay");
    }
    public void setLinkDay(int item)
    {
        setInt("linkDay", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DAA99852");
    }
}