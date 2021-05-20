package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCustomerChangeLogInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractCustomerChangeLogInfo()
    {
        this("id");
    }
    protected AbstractCustomerChangeLogInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �ͻ����ϱ����¼ 's �ͻ����� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECustomerInfo getSheCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECustomerInfo)get("sheCustomer");
    }
    public void setSheCustomer(com.kingdee.eas.fdc.sellhouse.SHECustomerInfo item)
    {
        put("sheCustomer", item);
    }
    /**
     * Object: �ͻ����ϱ����¼ 's ������֯ property 
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
     * Object:�ͻ����ϱ����¼'s ���ֶ���Ϣproperty 
     */
    public String getNewField()
    {
        return getString("newField");
    }
    public void setNewField(String item)
    {
        setString("newField", item);
    }
    /**
     * Object:�ͻ����ϱ����¼'s ԭ�ֶ���Ϣproperty 
     */
    public String getOldField()
    {
        return getString("oldField");
    }
    public void setOldField(String item)
    {
        setString("oldField", item);
    }
    /**
     * Object: �ͻ����ϱ����¼ 's ��֯�ͻ� property 
     */
    public com.kingdee.eas.fdc.basecrm.FDCOrgCustomerInfo getOrgCustomer()
    {
        return (com.kingdee.eas.fdc.basecrm.FDCOrgCustomerInfo)get("orgCustomer");
    }
    public void setOrgCustomer(com.kingdee.eas.fdc.basecrm.FDCOrgCustomerInfo item)
    {
        put("orgCustomer", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E7FDDB5A");
    }
}