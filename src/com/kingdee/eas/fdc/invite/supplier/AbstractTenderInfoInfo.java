package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenderInfoInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractTenderInfoInfo()
    {
        this("id");
    }
    protected AbstractTenderInfoInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �б걨�� 's �б����� property 
     */
    public com.kingdee.eas.fdc.invite.InviteProjectInfo getInviteProject()
    {
        return (com.kingdee.eas.fdc.invite.InviteProjectInfo)get("inviteProject");
    }
    public void setInviteProject(com.kingdee.eas.fdc.invite.InviteProjectInfo item)
    {
        put("inviteProject", item);
    }
    /**
     * Object: �б걨�� 's �б���Ϣ���� property 
     */
    public com.kingdee.eas.fdc.invite.supplier.InvitationInfoInfo getInvitation()
    {
        return (com.kingdee.eas.fdc.invite.supplier.InvitationInfoInfo)get("invitation");
    }
    public void setInvitation(com.kingdee.eas.fdc.invite.supplier.InvitationInfoInfo item)
    {
        put("invitation", item);
    }
    /**
     * Object: �б걨�� 's ������Ӧ�� property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo getSupplier()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object:�б걨��'s ��Ŀ����property 
     */
    public String getProjectManager()
    {
        return getString("projectManager");
    }
    public void setProjectManager(String item)
    {
        setString("projectManager", item);
    }
    /**
     * Object:�б걨��'s ��ϵ�绰property 
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
     * Object:�б걨��'s ����״̬property 
     */
    public String getTenderState()
    {
        return getString("tenderState");
    }
    public void setTenderState(String item)
    {
        setString("tenderState", item);
    }
    /**
     * Object:�б걨��'s �ⲿ����IDproperty 
     */
    public String getWebTenderId()
    {
        return getString("webTenderId");
    }
    public void setWebTenderId(String item)
    {
        setString("webTenderId", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3A7CF5DA");
    }
}