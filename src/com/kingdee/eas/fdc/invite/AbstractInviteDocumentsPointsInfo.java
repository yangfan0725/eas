package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteDocumentsPointsInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractInviteDocumentsPointsInfo()
    {
        this("id");
    }
    protected AbstractInviteDocumentsPointsInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�б�/����ļ�---�б�Ҫ���'s �б귶Χproperty 
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
     * Object:�б�/����ļ�---�б�Ҫ���'s ��������property 
     */
    public String getRule()
    {
        return getString("rule");
    }
    public void setRule(String item)
    {
        setString("rule", item);
    }
    /**
     * Object:�б�/����ļ�---�б�Ҫ���'s �������嵥/����嵥property 
     */
    public String getList()
    {
        return getString("list");
    }
    public void setList(String item)
    {
        setString("list", item);
    }
    /**
     * Object:�б�/����ļ�---�б�Ҫ���'s ���ʽproperty 
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
     * Object:�б�/����ļ�---�б�Ҫ���'s ���ĵ��ʽproperty 
     */
    public String getFormula()
    {
        return getString("formula");
    }
    public void setFormula(String item)
    {
        setString("formula", item);
    }
    /**
     * Object:�б�/����ļ�---�б�Ҫ���'s ���㷽ʽproperty 
     */
    public String getSettle()
    {
        return getString("settle");
    }
    public void setSettle(String item)
    {
        setString("settle", item);
    }
    /**
     * Object:�б�/����ļ�---�б�Ҫ���'s ���׼�ı���һ�´�property 
     */
    public String getDiff()
    {
        return getString("diff");
    }
    public void setDiff(String item)
    {
        setString("diff", item);
    }
    /**
     * Object:�б�/����ļ�---�б�Ҫ���'s ����property 
     */
    public String getOther()
    {
        return getString("other");
    }
    public void setOther(String item)
    {
        setString("other", item);
    }
    /**
     * Object: �б�/����ļ�---�б�Ҫ��� 's null property 
     */
    public com.kingdee.eas.fdc.invite.InviteDocumentsInfo getHead()
    {
        return (com.kingdee.eas.fdc.invite.InviteDocumentsInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.invite.InviteDocumentsInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0821C2CE");
    }
}