package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractExpertQualifyEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractExpertQualifyEntryInfo()
    {
        this("id");
    }
    protected AbstractExpertQualifyEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��������ר�ҷ�¼ 's ר�� property 
     */
    public com.kingdee.eas.fdc.invite.ExpertInfo getExpert()
    {
        return (com.kingdee.eas.fdc.invite.ExpertInfo)get("expert");
    }
    public void setExpert(com.kingdee.eas.fdc.invite.ExpertInfo item)
    {
        put("expert", item);
    }
    /**
     * Object:��������ר�ҷ�¼'s ��עproperty 
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
     * Object: ��������ר�ҷ�¼ 's ��������ר�� property 
     */
    public com.kingdee.eas.fdc.invite.ExpertQualifyInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.ExpertQualifyInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.ExpertQualifyInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A13AB693");
    }
}