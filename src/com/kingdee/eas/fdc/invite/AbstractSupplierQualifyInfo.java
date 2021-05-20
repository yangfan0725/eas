package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierQualifyInfo extends com.kingdee.eas.fdc.invite.BaseInviteInfo implements Serializable 
{
    public AbstractSupplierQualifyInfo()
    {
        this("id");
    }
    protected AbstractSupplierQualifyInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.invite.SupplierQualifyEntryCollection());
    }
    public void  setEntry(com.kingdee.eas.fdc.invite.SupplierQualifyEntryCollection entry)
    {
    	put("entry",entry);
    }
    /**
    
    /**
     * Object: ��Χ��Ӧ�� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.invite.SupplierQualifyEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.invite.SupplierQualifyEntryCollection)get("entry");
    }
    /**
     * Object:��Χ��Ӧ��'s �Ƿ��ѿ���property 
     */
    public boolean isHasStartBid()
    {
        return getBoolean("hasStartBid");
    }
    public void setHasStartBid(boolean item)
    {
        setBoolean("hasStartBid", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8A82503D");
    }
}