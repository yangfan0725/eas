package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWinInfoInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractWinInfoInfo()
    {
        this("id");
    }
    protected AbstractWinInfoInfo(String pkField)
    {
        super(pkField);
        put("winSupplierEntry", new com.kingdee.eas.fdc.invite.supplier.WinSupplierEntryCollection());
    }
    /**
     * Object: �б���Ϣ 's inviteProejct property 
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
     * Object: �б���Ϣ 's �б���Ϣ���� property 
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
     * Object:�б���Ϣ's �б�ʱ��property 
     */
    public java.util.Date getWinDate()
    {
        return getDate("winDate");
    }
    public void setWinDate(java.util.Date item)
    {
        setDate("winDate", item);
    }
    /**
     * Object:�б���Ϣ's �б겿��ϵ��property 
     */
    public String getInvitor()
    {
        return getString("invitor");
    }
    public void setInvitor(String item)
    {
        setString("invitor", item);
    }
    /**
     * Object:�б���Ϣ's �б겿��ϵ�绰property 
     */
    public String getInvitorPhone()
    {
        return getString("invitorPhone");
    }
    public void setInvitorPhone(String item)
    {
        setString("invitorPhone", item);
    }
    /**
     * Object:�б���Ϣ's ��Ʋ�������property 
     */
    public String getChecker()
    {
        return getString("checker");
    }
    public void setChecker(String item)
    {
        setString("checker", item);
    }
    /**
     * Object:�б���Ϣ's ��Ʋ������˵绰 property 
     */
    public String getCheckerPhone()
    {
        return getString("checkerPhone");
    }
    public void setCheckerPhone(String item)
    {
        setString("checkerPhone", item);
    }
    /**
     * Object:�б���Ϣ's �б���Ϣ��ϸ����property 
     */
    public String getDetailInfo()
    {
        return getString("detailInfo");
    }
    public void setDetailInfo(String item)
    {
        setString("detailInfo", item);
    }
    /**
     * Object:�б���Ϣ's ����״̬property 
     */
    public String getPublishState()
    {
        return getString("publishState");
    }
    public void setPublishState(String item)
    {
        setString("publishState", item);
    }
    /**
     * Object: �б���Ϣ 's �б깩Ӧ�̷�¼ property 
     */
    public com.kingdee.eas.fdc.invite.supplier.WinSupplierEntryCollection getWinSupplierEntry()
    {
        return (com.kingdee.eas.fdc.invite.supplier.WinSupplierEntryCollection)get("winSupplierEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("521FCD72");
    }
}