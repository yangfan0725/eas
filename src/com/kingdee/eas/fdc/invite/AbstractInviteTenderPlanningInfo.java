package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteTenderPlanningInfo extends com.kingdee.eas.fdc.invite.BaseInviteInfo implements Serializable 
{
    public AbstractInviteTenderPlanningInfo()
    {
        this("id");
    }
    protected AbstractInviteTenderPlanningInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.invite.InviteTenderPlanningEntryCollection());
    }
    /**
     * Object: �б�߻� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.invite.InviteTenderPlanningEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.invite.InviteTenderPlanningEntryCollection)get("entry");
    }
    /**
     * Object:�б�߻�'s �б귶Χproperty 
     */
    public String getScope()
    {
        return getString("scope");
    }
    public void setScope(String item)
    {
        setString("scope", item);
    }
    /**
     * Object:�б�߻�'s ���ʽproperty 
     */
    public String getPayMethod()
    {
        return getString("payMethod");
    }
    public void setPayMethod(String item)
    {
        setString("payMethod", item);
    }
    /**
     * Object:�б�߻�'s �б�ͼֽ��������property 
     */
    public String getZbtzpsms()
    {
        return getString("zbtzpsms");
    }
    public void setZbtzpsms(String item)
    {
        setString("zbtzpsms", item);
    }
    /**
     * Object:�б�߻�'s ���Ʒ�������property 
     */
    public String getShsjfams()
    {
        return getString("shsjfams");
    }
    public void setShsjfams(String item)
    {
        setString("shsjfams", item);
    }
    /**
     * Object:�б�߻�'s ������׼Ҫ��property 
     */
    public String getJsbzyq()
    {
        return getString("jsbzyq");
    }
    public void setJsbzyq(String item)
    {
        setString("jsbzyq", item);
    }
    /**
     * Object:�б�߻�'s ��λ���Ҫ��property 
     */
    public String getBdhfyq()
    {
        return getString("bdhfyq");
    }
    public void setBdhfyq(String item)
    {
        setString("bdhfyq", item);
    }
    /**
     * Object:�б�߻�'s �Գа���λ��Ҫ��property 
     */
    public String getDcbdwdyq()
    {
        return getString("dcbdwdyq");
    }
    public void setDcbdwdyq(String item)
    {
        setString("dcbdwdyq", item);
    }
    /**
     * Object:�б�߻�'s ���������property 
     */
    public String getJcldx()
    {
        return getString("jcldx");
    }
    public void setJcldx(String item)
    {
        setString("jcldx", item);
    }
    /**
     * Object:�б�߻�'s רҵ����property 
     */
    public com.kingdee.eas.fdc.invite.InviteTenderTypeEnum getType()
    {
        return com.kingdee.eas.fdc.invite.InviteTenderTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.invite.InviteTenderTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    /**
     * Object:�б�߻�'s �������property 
     */
    public com.kingdee.eas.fdc.invite.TenderwfTypeEnum getWfType()
    {
        return com.kingdee.eas.fdc.invite.TenderwfTypeEnum.getEnum(getString("wfType"));
    }
    public void setWfType(com.kingdee.eas.fdc.invite.TenderwfTypeEnum item)
    {
		if (item != null) {
        setString("wfType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2277AADE");
    }
}