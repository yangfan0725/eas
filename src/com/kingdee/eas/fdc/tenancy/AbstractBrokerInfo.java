package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBrokerInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractBrokerInfo()
    {
        this("id");
    }
    protected AbstractBrokerInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:������'s �ֻ�property 
     */
    public String getPhone()
    {
        return getString("phone");
    }
    public void setPhone(String item)
    {
        setString("phone", item);
    }
    /**
     * Object:������'s ����property 
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
     * Object:������'s �Ա�property 
     */
    public com.kingdee.eas.fdc.sellhouse.SexEnum getSex()
    {
        return com.kingdee.eas.fdc.sellhouse.SexEnum.getEnum(getString("sex"));
    }
    public void setSex(com.kingdee.eas.fdc.sellhouse.SexEnum item)
    {
		if (item != null) {
        setString("sex", item.getValue());
		}
    }
    /**
     * Object:������'s ���֤��property 
     */
    public String getIdNum()
    {
        return getString("idNum");
    }
    public void setIdNum(String item)
    {
        setString("idNum", item);
    }
    /**
     * Object:������'s ΢�ź�property 
     */
    public String getWeChatNum()
    {
        return getString("weChatNum");
    }
    public void setWeChatNum(String item)
    {
        setString("weChatNum", item);
    }
    /**
     * Object:������'s ��������property 
     */
    public String getBank()
    {
        return getString("bank");
    }
    public void setBank(String item)
    {
        setString("bank", item);
    }
    /**
     * Object:������'s �����˺�property 
     */
    public String getAccountNum()
    {
        return getString("accountNum");
    }
    public void setAccountNum(String item)
    {
        setString("accountNum", item);
    }
    /**
     * Object:������'s ������property 
     */
    public String getAccountName()
    {
        return getString("accountName");
    }
    public void setAccountName(String item)
    {
        setString("accountName", item);
    }
    /**
     * Object:������'s ���֤��Ƭproperty 
     */
    public String getIdCardPictureURL()
    {
        return getString("idCardPictureURL");
    }
    public void setIdCardPictureURL(String item)
    {
        setString("idCardPictureURL", item);
    }
    /**
     * Object:������'s ���˵��property 
     */
    public String getIdentity()
    {
        return getString("identity");
    }
    public void setIdentity(String item)
    {
        setString("identity", item);
    }
    /**
     * Object:������'s �н鹫˾property 
     */
    public String getAgency()
    {
        return getString("agency");
    }
    public void setAgency(String item)
    {
        setString("agency", item);
    }
    /**
     * Object:������'s ��ϵ��property 
     */
    public String getReferrer()
    {
        return getString("referrer");
    }
    public void setReferrer(String item)
    {
        setString("referrer", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E65886C2");
    }
}