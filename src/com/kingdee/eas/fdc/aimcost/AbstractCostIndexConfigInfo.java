package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCostIndexConfigInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractCostIndexConfigInfo()
    {
        this("id");
    }
    protected AbstractCostIndexConfigInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.aimcost.CostIndexConfigEntryCollection());
    }
    /**
     * Object: ���ָ������� 's �ɹ���� property 
     */
    public com.kingdee.eas.fdc.invite.InviteTypeInfo getInviteType()
    {
        return (com.kingdee.eas.fdc.invite.InviteTypeInfo)get("inviteType");
    }
    public void setInviteType(com.kingdee.eas.fdc.invite.InviteTypeInfo item)
    {
        put("inviteType", item);
    }
    /**
     * Object: ���ָ������� 's ���ָ����¼ property 
     */
    public com.kingdee.eas.fdc.aimcost.CostIndexConfigEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.aimcost.CostIndexConfigEntryCollection)get("entry");
    }
    /**
     * Object:���ָ�������'s ָ�����property 
     */
    public com.kingdee.eas.fdc.aimcost.CostIndexEntryTypeEnum getEntryType()
    {
        return com.kingdee.eas.fdc.aimcost.CostIndexEntryTypeEnum.getEnum(getString("entryType"));
    }
    public void setEntryType(com.kingdee.eas.fdc.aimcost.CostIndexEntryTypeEnum item)
    {
		if (item != null) {
        setString("entryType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BFD03670");
    }
}