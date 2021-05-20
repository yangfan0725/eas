package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteEntSuppChkBillEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractInviteEntSuppChkBillEntryInfo()
    {
        this("id");
    }
    protected AbstractInviteEntSuppChkBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��Χ��λ��������¼ 's �������� property 
     */
    public com.kingdee.eas.fdc.invite.InviteEntSuppChkBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.InviteEntSuppChkBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.InviteEntSuppChkBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��Χ��λ��������¼'s ��ϵ��property 
     */
    public String getLinkman()
    {
        return getString("linkman");
    }
    public void setLinkman(String item)
    {
        setString("linkman", item);
    }
    /**
     * Object:��Χ��λ��������¼'s ��ϵ�绰property 
     */
    public String getPhone()
    {
        return getString("phone");
    }
    public void setPhone(String item)
    {
        setString("phone", item);
    }
    /**
     * Object:��Χ��λ��������¼'s ������Դproperty 
     */
    public String getProvider()
    {
        return getString("provider");
    }
    public void setProvider(String item)
    {
        setString("provider", item);
    }
    /**
     * Object:��Χ��λ��������¼'s �ֳ�����property 
     */
    public String getSiteInspection()
    {
        return getString("siteInspection");
    }
    public void setSiteInspection(String item)
    {
        setString("siteInspection", item);
    }
    /**
     * Object:��Χ��λ��������¼'s ������Ŀ����property 
     */
    public String getSameProjct()
    {
        return getString("sameProjct");
    }
    public void setSameProjct(String item)
    {
        setString("sameProjct", item);
    }
    /**
     * Object: ��Χ��λ��������¼ 's null property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getSupplier()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("supplier", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F34CDE45");
    }
}