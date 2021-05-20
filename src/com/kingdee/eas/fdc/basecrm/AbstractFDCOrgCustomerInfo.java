package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCOrgCustomerInfo extends com.kingdee.eas.fdc.basecrm.FDCBaseCustomerInfo implements Serializable 
{
    public AbstractFDCOrgCustomerInfo()
    {
        this("id");
    }
    protected AbstractFDCOrgCustomerInfo(String pkField)
    {
        super(pkField);
        put("linkMan", new com.kingdee.eas.fdc.basecrm.OrgCustomerLinkManCollection());
    }
    /**
     * Object: ��֯�ͻ� 's ���ز����ͻ� property 
     */
    public com.kingdee.eas.fdc.basecrm.FDCMainCustomerInfo getMainCustomer()
    {
        return (com.kingdee.eas.fdc.basecrm.FDCMainCustomerInfo)get("mainCustomer");
    }
    public void setMainCustomer(com.kingdee.eas.fdc.basecrm.FDCMainCustomerInfo item)
    {
        put("mainCustomer", item);
    }
    /**
     * Object: ��֯�ͻ� 's ������֯ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getBelongUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("belongUnit");
    }
    public void setBelongUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("belongUnit", item);
    }
    /**
     * Object: ��֯�ͻ� 's ��ϵ�� property 
     */
    public com.kingdee.eas.fdc.basecrm.OrgCustomerLinkManCollection getLinkMan()
    {
        return (com.kingdee.eas.fdc.basecrm.OrgCustomerLinkManCollection)get("linkMan");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("79DAF899");
    }
}