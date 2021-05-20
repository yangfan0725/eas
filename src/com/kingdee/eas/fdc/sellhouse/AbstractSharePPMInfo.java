package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSharePPMInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractSharePPMInfo()
    {
        this("id");
    }
    protected AbstractSharePPMInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ������ҵ��¼ 's ��ﵥ property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomJoinInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomJoinInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.RoomJoinInfo item)
    {
        put("head", item);
    }
    /**
     * Object:������ҵ��¼'s ����ʽproperty 
     */
    public com.kingdee.eas.fdc.sellhouse.ShareModelEnum getShareModel()
    {
        return com.kingdee.eas.fdc.sellhouse.ShareModelEnum.getEnum(getString("shareModel"));
    }
    public void setShareModel(com.kingdee.eas.fdc.sellhouse.ShareModelEnum item)
    {
		if (item != null) {
        setString("shareModel", item.getValue());
		}
    }
    /**
     * Object: ������ҵ��¼ 's ������ҵ��Ա property 
     */
    public com.kingdee.eas.base.permission.UserInfo getUser()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("user");
    }
    public void setUser(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("user", item);
    }
    /**
     * Object: ������ҵ��¼ 's ����Ӫ����Ԫ property 
     */
    public com.kingdee.eas.fdc.tenancy.MarketingUnitInfo getMarketingUnit()
    {
        return (com.kingdee.eas.fdc.tenancy.MarketingUnitInfo)get("marketingUnit");
    }
    public void setMarketingUnit(com.kingdee.eas.fdc.tenancy.MarketingUnitInfo item)
    {
        put("marketingUnit", item);
    }
    /**
     * Object: ������ҵ��¼ 's ������֯��Ԫ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3E8D41E9");
    }
}