package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPushManageInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractPushManageInfo()
    {
        this("id");
    }
    protected AbstractPushManageInfo(String pkField)
    {
        super(pkField);
        put("his", new com.kingdee.eas.fdc.sellhouse.PushManageHisEntryCollection());
    }
    /**
     * Object: ���̹��� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object:���̹���'s ��������property 
     */
    public int getPushTotal()
    {
        return getInt("pushTotal");
    }
    public void setPushTotal(int item)
    {
        setInt("pushTotal", item);
    }
    /**
     * Object:���̹���'s ��������property 
     */
    public int getPullTotal()
    {
        return getInt("pullTotal");
    }
    public void setPullTotal(int item)
    {
        setInt("pullTotal", item);
    }
    /**
     * Object: ���̹��� 's ������ property 
     */
    public com.kingdee.eas.base.permission.UserInfo getPusher()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("pusher");
    }
    public void setPusher(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("pusher", item);
    }
    /**
     * Object: ���̹��� 's ������ property 
     */
    public com.kingdee.eas.base.permission.UserInfo getPuller()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("puller");
    }
    public void setPuller(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("puller", item);
    }
    /**
     * Object:���̹���'s ��������property 
     */
    public java.util.Date getPushDate()
    {
        return getDate("pushDate");
    }
    public void setPushDate(java.util.Date item)
    {
        setDate("pushDate", item);
    }
    /**
     * Object:���̹���'s ��������property 
     */
    public java.util.Date getPullDate()
    {
        return getDate("pullDate");
    }
    public void setPullDate(java.util.Date item)
    {
        setDate("pullDate", item);
    }
    /**
     * Object: ���̹��� 's ������־ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PushManageHisEntryCollection getHis()
    {
        return (com.kingdee.eas.fdc.sellhouse.PushManageHisEntryCollection)get("his");
    }
    /**
     * Object:���̹���'s �̴�property 
     */
    public String getSellOrder()
    {
        return getString("sellOrder");
    }
    public void setSellOrder(String item)
    {
        setString("sellOrder", item);
    }
    /**
     * Object: ���̹��� 's ������֯ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    /**
     * Object:���̹���'s ��������property 
     */
    public java.util.Date getOpenDate()
    {
        return getDate("openDate");
    }
    public void setOpenDate(java.util.Date item)
    {
        setDate("openDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8FC1CE3A");
    }
}