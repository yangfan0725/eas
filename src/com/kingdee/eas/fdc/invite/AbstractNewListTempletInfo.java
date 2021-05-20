package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractNewListTempletInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractNewListTempletInfo()
    {
        this("id");
    }
    protected AbstractNewListTempletInfo(String pkField)
    {
        super(pkField);
        put("pages", new com.kingdee.eas.fdc.invite.NewListTempletPageCollection());
    }
    /**
     * Object: �嵥ģ�� 's �б����� property 
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
     * Object: �嵥ģ�� 's ԭ��֯ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOriOrg()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("oriOrg");
    }
    public void setOriOrg(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("oriOrg", item);
    }
    /**
     * Object:�嵥ģ��'s ģ�����������property 
     */
    public byte[] getTableData()
    {
        return (byte[])get("tableData");
    }
    public void setTableData(byte[] item)
    {
        put("tableData", item);
    }
    /**
     * Object: �嵥ģ�� 's ҳǩ property 
     */
    public com.kingdee.eas.fdc.invite.NewListTempletPageCollection getPages()
    {
        return (com.kingdee.eas.fdc.invite.NewListTempletPageCollection)get("pages");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("56BD65AD");
    }
}