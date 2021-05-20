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
     * Object:外网用户's 手机号码property 
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
     * Object:外网用户's 密码property 
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
     * Object:外网用户's 公司名称property 
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
     * Object:外网用户's 电话property 
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
     * Object:外网用户's 邮件property 
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
     * Object:外网用户's 身份说明/职位property 
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
     * Object:外网用户's 注册状态property 
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
     * Object: 外网用户 's 供应商关联分录 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.UserSupplierAssoCollection getEntry()
    {
        return (com.kingdee.eas.fdc.invite.supplier.UserSupplierAssoCollection)get("entry");
    }
    /**
     * Object:外网用户's 外网注册用户生成的IDproperty 
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
     * Object:外网用户's 专业property 
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
     * Object:外网用户's 关联供应商信息property 
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