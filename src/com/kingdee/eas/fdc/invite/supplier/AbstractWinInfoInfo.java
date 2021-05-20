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
     * Object: 中标信息 's inviteProejct property 
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
     * Object: 中标信息 's 招标信息发布 property 
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
     * Object:中标信息's 中标时间property 
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
     * Object:中标信息's 招标部联系人property 
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
     * Object:中标信息's 招标部联系电话property 
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
     * Object:中标信息's 审计部受理人property 
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
     * Object:中标信息's 审计部受理人电话 property 
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
     * Object:中标信息's 中标信息详细内容property 
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
     * Object:中标信息's 发布状态property 
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
     * Object: 中标信息 's 中标供应商分录 property 
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