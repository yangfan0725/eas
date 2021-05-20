package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHEShareOrgUnitInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractSHEShareOrgUnitInfo()
    {
        this("id");
    }
    protected AbstractSHEShareOrgUnitInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��¥��Ŀ����-������֯ 's ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEProjectInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEProjectInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.SHEProjectInfo item)
    {
        put("head", item);
    }
    /**
     * Object: ��¥��Ŀ����-������֯ 's ������֯ property 
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
     * Object: ��¥��Ŀ����-������֯ 's ��������� property 
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
     * Object:��¥��Ŀ����-������֯'s ��������property 
     */
    public java.util.Date getShareDate()
    {
        return getDate("shareDate");
    }
    public void setShareDate(java.util.Date item)
    {
        setDate("shareDate", item);
    }
    /**
     * Object: ��¥��Ŀ����-������֯ 's ������֯ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getCreateOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("createOrgUnit");
    }
    public void setCreateOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("createOrgUnit", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B1C2963E");
    }
}