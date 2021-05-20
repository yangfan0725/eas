package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteFileItemInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractInviteFileItemInfo()
    {
        this("id");
    }
    protected AbstractInviteFileItemInfo(String pkField)
    {
        super(pkField);
        put("itemEntry", new com.kingdee.eas.fdc.invite.InviteFileItemEntryCollection());
    }
    /**
     * Object:�б��ļ�ģ��'s �ļ�����property 
     */
    public com.kingdee.eas.fdc.invite.InviteFileItemTypeEnum getFileItemType()
    {
        return com.kingdee.eas.fdc.invite.InviteFileItemTypeEnum.getEnum(getString("fileItemType"));
    }
    public void setFileItemType(com.kingdee.eas.fdc.invite.InviteFileItemTypeEnum item)
    {
		if (item != null) {
        setString("fileItemType", item.getValue());
		}
    }
    /**
     * Object: �б��ļ�ģ�� 's ���Ʋ��� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getRespDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("respDept");
    }
    public void setRespDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("respDept", item);
    }
    /**
     * Object: �б��ļ�ģ�� 's �б����� property 
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
     * Object: �б��ļ�ģ�� 's �ļ�ģ�� property 
     */
    public com.kingdee.eas.fdc.invite.TemplateFileInfo getFileTemplate()
    {
        return (com.kingdee.eas.fdc.invite.TemplateFileInfo)get("fileTemplate");
    }
    public void setFileTemplate(com.kingdee.eas.fdc.invite.TemplateFileInfo item)
    {
        put("fileTemplate", item);
    }
    /**
     * Object: �б��ļ�ģ�� 's ģ���¼ property 
     */
    public com.kingdee.eas.fdc.invite.InviteFileItemEntryCollection getItemEntry()
    {
        return (com.kingdee.eas.fdc.invite.InviteFileItemEntryCollection)get("itemEntry");
    }
    /**
     * Object:�б��ļ�ģ��'s �汾��property 
     */
    public double getVersion()
    {
        return getDouble("version");
    }
    public void setVersion(double item)
    {
        setDouble("version", item);
    }
    /**
     * Object:�б��ļ�ģ��'s �Ƿ����°汾property 
     */
    public boolean isIsLastVersion()
    {
        return getBoolean("isLastVersion");
    }
    public void setIsLastVersion(boolean item)
    {
        setBoolean("isLastVersion", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("418074FC");
    }
}