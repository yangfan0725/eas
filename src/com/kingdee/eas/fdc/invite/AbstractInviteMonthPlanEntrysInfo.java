package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteMonthPlanEntrysInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractInviteMonthPlanEntrysInfo()
    {
        this("id");
    }
    protected AbstractInviteMonthPlanEntrysInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �¶Ȳɹ��б�ƻ���¼ 's �¶Ȳɹ��б�ƻ� property 
     */
    public com.kingdee.eas.fdc.invite.InviteMonthPlanInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.InviteMonthPlanInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.InviteMonthPlanInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:�¶Ȳɹ��б�ƻ���¼'s ��������property 
     */
    public String getWorkContent()
    {
        return getString("workContent");
    }
    public void setWorkContent(String item)
    {
        setString("workContent", item);
    }
    /**
     * Object:�¶Ȳɹ��б�ƻ���¼'s �滮���property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:�¶Ȳɹ��б�ƻ���¼'s ��ܺ�Լ����property 
     */
    public String getLongNumber()
    {
        return getString("longNumber");
    }
    public void setLongNumber(String item)
    {
        setString("longNumber", item);
    }
    /**
     * Object:�¶Ȳɹ��б�ƻ���¼'s ��ܺ�Լ����property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:�¶Ȳɹ��б�ƻ���¼'s �ɱ�����property 
     */
    public String getCostAccountNames()
    {
        return getString("costAccountNames");
    }
    public void setCostAccountNames(String item)
    {
        setString("costAccountNames", item);
    }
    /**
     * Object:�¶Ȳɹ��б�ƻ���¼'s ����property 
     */
    public int getLevel()
    {
        return getInt("level");
    }
    public void setLevel(int item)
    {
        setInt("level", item);
    }
    /**
     * Object:�¶Ȳɹ��б�ƻ���¼'s �б��ļ���������property 
     */
    public java.util.Date getDocumentsAuditDate()
    {
        return getDate("documentsAuditDate");
    }
    public void setDocumentsAuditDate(java.util.Date item)
    {
        setDate("documentsAuditDate", item);
    }
    /**
     * Object:�¶Ȳɹ��б�ƻ���¼'s �б���������property 
     */
    public java.util.Date getResultAuditDate()
    {
        return getDate("resultAuditDate");
    }
    public void setResultAuditDate(java.util.Date item)
    {
        setDate("resultAuditDate", item);
    }
    /**
     * Object:�¶Ȳɹ��б�ƻ���¼'s ��ͬ��������property 
     */
    public java.util.Date getContractAuditDate()
    {
        return getDate("contractAuditDate");
    }
    public void setContractAuditDate(java.util.Date item)
    {
        setDate("contractAuditDate", item);
    }
    /**
     * Object:�¶Ȳɹ��б�ƻ���¼'s Ԥ�ƽ�������property 
     */
    public java.util.Date getEnterAuditDate()
    {
        return getDate("enterAuditDate");
    }
    public void setEnterAuditDate(java.util.Date item)
    {
        setDate("enterAuditDate", item);
    }
    /**
     * Object:�¶Ȳɹ��б�ƻ���¼'s ��ܺ�ԼIDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getProgrammingContractID()
    {
        return getBOSUuid("programmingContractID");
    }
    public void setProgrammingContractID(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("programmingContractID", item);
    }
    /**
     * Object:�¶Ȳɹ��б�ƻ���¼'s ͼֽ����Ʒproperty 
     */
    public java.util.Date getPaperDate()
    {
        return getDate("paperDate");
    }
    public void setPaperDate(java.util.Date item)
    {
        setDate("paperDate", item);
    }
    /**
     * Object: �¶Ȳɹ��б�ƻ���¼ 's �ƶ�Ҫ��Ĳɹ���ʽ property 
     */
    public com.kingdee.eas.fdc.invite.InviteFormInfo getRequiredInviteForm()
    {
        return (com.kingdee.eas.fdc.invite.InviteFormInfo)get("requiredInviteForm");
    }
    public void setRequiredInviteForm(com.kingdee.eas.fdc.invite.InviteFormInfo item)
    {
        put("requiredInviteForm", item);
    }
    /**
     * Object:�¶Ȳɹ��б�ƻ���¼'s Ŀ��ɱ�����ָ��property 
     */
    public java.math.BigDecimal getIndicator()
    {
        return getBigDecimal("indicator");
    }
    public void setIndicator(java.math.BigDecimal item)
    {
        setBigDecimal("indicator", item);
    }
    /**
     * Object:�¶Ȳɹ��б�ƻ���¼'s ���ԭ��property 
     */
    public String getReason()
    {
        return getString("reason");
    }
    public void setReason(String item)
    {
        setString("reason", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7CBDAA7D");
    }
}