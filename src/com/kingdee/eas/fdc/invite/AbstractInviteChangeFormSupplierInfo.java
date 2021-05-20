package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteChangeFormSupplierInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractInviteChangeFormSupplierInfo()
    {
        this("id");
    }
    protected AbstractInviteChangeFormSupplierInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�ı�ɹ���ʽ��ʾ��Ӧ�̷�¼'s ��Ӧ������property 
     */
    public String getSupplierName()
    {
        return getString("supplierName");
    }
    public void setSupplierName(String item)
    {
        setString("supplierName", item);
    }
    /**
     * Object:�ı�ɹ���ʽ��ʾ��Ӧ�̷�¼'s ��Ӧ�̱���property 
     */
    public String getSupplierNumber()
    {
        return getString("supplierNumber");
    }
    public void setSupplierNumber(String item)
    {
        setString("supplierNumber", item);
    }
    /**
     * Object:�ı�ɹ���ʽ��ʾ��Ӧ�̷�¼'s ��Ӧ��IDproperty 
     */
    public String getSupplierID()
    {
        return getString("supplierID");
    }
    public void setSupplierID(String item)
    {
        setString("supplierID", item);
    }
    /**
     * Object: �ı�ɹ���ʽ��ʾ��Ӧ�̷�¼ 's null property 
     */
    public com.kingdee.eas.fdc.invite.InviteChangeFormEntryInfo getHead()
    {
        return (com.kingdee.eas.fdc.invite.InviteChangeFormEntryInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.invite.InviteChangeFormEntryInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B0D6B88D");
    }
}