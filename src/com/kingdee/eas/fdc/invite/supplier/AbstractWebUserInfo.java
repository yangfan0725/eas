package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWebUserInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractWebUserInfo()
    {
        this("id");
    }
    protected AbstractWebUserInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.invite.supplier.UserSupplierAssoCollection());
    }
    /**
     * Object:�����û�'s �ֻ�����property 
     */
    public String getMobileNumber()
    {
        return getString("mobileNumber");
    }
    public void setMobileNumber(String item)
    {
        setString("mobileNumber", item);
    }
    /**
     * Object:�����û�'s ����property 
     */
    public String getPassword()
    {
        return getString("password");
    }
    public void setPassword(String item)
    {
        setString("password", item);
    }
    /**
     * Object:�����û�'s ��˾����property 
     */
    public String getCompanyName()
    {
        return getString("companyName");
    }
    public void setCompanyName(String item)
    {
        setString("companyName", item);
    }
    /**
     * Object:�����û�'s �绰property 
     */
    public String getTelephone()
    {
        return getString("telephone");
    }
    public void setTelephone(String item)
    {
        setString("telephone", item);
    }
    /**
     * Object:�����û�'s �ʼ�property 
     */
    public String getEmail()
    {
        return getString("email");
    }
    public void setEmail(String item)
    {
        setString("email", item);
    }
    /**
     * Object:�����û�'s ���˵��/ְλproperty 
     */
    public String getPosition()
    {
        return getString("position");
    }
    public void setPosition(String item)
    {
        setString("position", item);
    }
    /**
     * Object:�����û�'s ע��״̬property 
     */
    public com.kingdee.eas.fdc.invite.supplier.RegistStateEnum getRegisterState()
    {
        return com.kingdee.eas.fdc.invite.supplier.RegistStateEnum.getEnum(getString("registerState"));
    }
    public void setRegisterState(com.kingdee.eas.fdc.invite.supplier.RegistStateEnum item)
    {
		if (item != null) {
        setString("registerState", item.getValue());
		}
    }
    /**
     * Object: �����û� 's ��Ӧ�̹�����¼ property 
     */
    public com.kingdee.eas.fdc.invite.supplier.UserSupplierAssoCollection getEntry()
    {
        return (com.kingdee.eas.fdc.invite.supplier.UserSupplierAssoCollection)get("entry");
    }
    /**
     * Object:�����û�'s ����ע���û����ɵ�IDproperty 
     */
    public String getWebUserID()
    {
        return getString("webUserID");
    }
    public void setWebUserID(String item)
    {
        setString("webUserID", item);
    }
    /**
     * Object:�����û�'s רҵproperty 
     */
    public String getSpecialty()
    {
        return getString("specialty");
    }
    public void setSpecialty(String item)
    {
        setString("specialty", item);
    }
    /**
     * Object:�����û�'s ������Ӧ����Ϣproperty 
     */
    public String getRelateSupplier()
    {
        return getString("relateSupplier");
    }
    public void setRelateSupplier(String item)
    {
        setString("relateSupplier", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4AA8D807");
    }
}