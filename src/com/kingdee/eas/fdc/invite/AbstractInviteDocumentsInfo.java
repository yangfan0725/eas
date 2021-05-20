package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteDocumentsInfo extends com.kingdee.eas.fdc.invite.BaseInviteInfo implements Serializable 
{
    public AbstractInviteDocumentsInfo()
    {
        this("id");
    }
    protected AbstractInviteDocumentsInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �б��ļ� 's ��׼�б��ļ� property 
     */
    public com.kingdee.eas.fdc.invite.InviteBaseFileInfo getInviteBaseFile()
    {
        return (com.kingdee.eas.fdc.invite.InviteBaseFileInfo)get("inviteBaseFile");
    }
    public void setInviteBaseFile(com.kingdee.eas.fdc.invite.InviteBaseFileInfo item)
    {
        put("inviteBaseFile", item);
    }
    /**
     * Object:�б��ļ�'s ����Э�����ҵ��IDproperty 
     */
    public String getAgreementID()
    {
        return getString("agreementID");
    }
    public void setAgreementID(String item)
    {
        setString("agreementID", item);
    }
    /**
     * Object: �б��ļ� 's ��׼��ͬ���� property 
     */
    public com.kingdee.eas.fdc.contract.ContractModelInfo getContractModel()
    {
        return (com.kingdee.eas.fdc.contract.ContractModelInfo)get("contractModel");
    }
    public void setContractModel(com.kingdee.eas.fdc.contract.ContractModelInfo item)
    {
        put("contractModel", item);
    }
    /**
     * Object:�б��ļ�'s �б귶Χproperty 
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
     * Object:�б��ļ�'s ��������property 
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
     * Object:�б��ļ�'s �������嵥property 
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
     * Object:�б��ļ�'s ���ʽproperty 
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
     * Object:�б��ļ�'s ���ĵ��ʽproperty 
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
     * Object:�б��ļ�'s ���㷽ʽproperty 
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
     * Object:�б��ļ�'s ���׼�ı���һ�´�property 
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
     * Object:�б��ļ�'s ����property 
     */
    public String getOther()
    {
        return getString("other");
    }
    public void setOther(String item)
    {
        setString("other", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("76766E0B");
    }
}