package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractExpertAppraiseEntryScoreInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractExpertAppraiseEntryScoreInfo()
    {
        this("id");
    }
    protected AbstractExpertAppraiseEntryScoreInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��Ӧ�̵÷� 's ��Ӧ�� property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getSupplier()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object:��Ӧ�̵÷�'s �÷�property 
     */
    public double getScore()
    {
        return getDouble("score");
    }
    public void setScore(double item)
    {
        setDouble("score", item);
    }
    /**
     * Object: ��Ӧ�̵÷� 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.invite.ExpertAppraiseEntryInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.ExpertAppraiseEntryInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.ExpertAppraiseEntryInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("94AA34F9");
    }
}