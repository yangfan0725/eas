package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractStageResultApproveInfo extends com.kingdee.eas.fdc.invite.BaseInviteInfo implements Serializable 
{
    public AbstractStageResultApproveInfo()
    {
        this("id");
    }
    protected AbstractStageResultApproveInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.invite.StageResultApproveEntryCollection());
    }
    /**
     * Object: �׶����Գɹ����� 's �б����� property 
     */
    public com.kingdee.eas.fdc.invite.TenderAccepterResultInfo getResult()
    {
        return (com.kingdee.eas.fdc.invite.TenderAccepterResultInfo)get("result");
    }
    public void setResult(com.kingdee.eas.fdc.invite.TenderAccepterResultInfo item)
    {
        put("result", item);
    }
    /**
     * Object:�׶����Գɹ�����'s ʱ���������property 
     */
    public java.util.Date getActDate()
    {
        return getDate("actDate");
    }
    public void setActDate(java.util.Date item)
    {
        setDate("actDate", item);
    }
    /**
     * Object:�׶����Գɹ�����'s �ƻ��������property 
     */
    public java.util.Date getPlanDate()
    {
        return getDate("planDate");
    }
    public void setPlanDate(java.util.Date item)
    {
        setDate("planDate", item);
    }
    /**
     * Object:�׶����Գɹ�����'s �׶��Գɹ�����property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object: �׶����Գɹ����� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.invite.StageResultApproveEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.invite.StageResultApproveEntryCollection)get("entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BF4D2436");
    }
}