package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCBaseLinkManInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractFDCBaseLinkManInfo()
    {
        this("id");
    }
    protected AbstractFDCBaseLinkManInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��ϵ�˻���'s �Ƿ�����ͻ�property 
     */
    public boolean isIsAssociation()
    {
        return getBoolean("isAssociation");
    }
    public void setIsAssociation(boolean item)
    {
        setBoolean("isAssociation", item);
    }
    /**
     * Object:��ϵ�˻���'s ��������property 
     */
    public com.kingdee.eas.fdc.basecrm.AssociationTypeEnum getAssociationType()
    {
        return com.kingdee.eas.fdc.basecrm.AssociationTypeEnum.getEnum(getString("associationType"));
    }
    public void setAssociationType(com.kingdee.eas.fdc.basecrm.AssociationTypeEnum item)
    {
		if (item != null) {
        setString("associationType", item.getValue());
		}
    }
    /**
     * Object:��ϵ�˻���'s ����������property 
     */
    public boolean isIsEmergencyContact()
    {
        return getBoolean("isEmergencyContact");
    }
    public void setIsEmergencyContact(boolean item)
    {
        setBoolean("isEmergencyContact", item);
    }
    /**
     * Object:��ϵ�˻���'s ί����property 
     */
    public boolean isIsPrincipal()
    {
        return getBoolean("isPrincipal");
    }
    public void setIsPrincipal(boolean item)
    {
        setBoolean("isPrincipal", item);
    }
    /**
     * Object:��ϵ�˻���'s ί������property 
     */
    public String getPrincipalNo()
    {
        return getString("principalNo");
    }
    public void setPrincipalNo(String item)
    {
        setString("principalNo", item);
    }
    /**
     * Object:��ϵ�˻���'s ��ϵproperty 
     */
    public String getRelation()
    {
        return getString("relation");
    }
    public void setRelation(String item)
    {
        setString("relation", item);
    }
    /**
     * Object:��ϵ�˻���'s �Ա�property 
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
     * Object:��ϵ�˻���'s �ƶ��绰property 
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
     * Object:��ϵ�˻���'s סլ�绰property 
     */
    public String getTel()
    {
        return getString("tel");
    }
    public void setTel(String item)
    {
        setString("tel", item);
    }
    /**
     * Object:��ϵ�˻���'s �칫�绰property 
     */
    public String getOfficeTel()
    {
        return getString("officeTel");
    }
    public void setOfficeTel(String item)
    {
        setString("officeTel", item);
    }
    /**
     * Object:��ϵ�˻���'s ����property 
     */
    public String getFax()
    {
        return getString("fax");
    }
    public void setFax(String item)
    {
        setString("fax", item);
    }
    /**
     * Object:��ϵ�˻���'s �����绰property 
     */
    public String getOtherTel()
    {
        return getString("otherTel");
    }
    public void setOtherTel(String item)
    {
        setString("otherTel", item);
    }
    /**
     * Object:��ϵ�˻���'s �����ʼ�property 
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
     * Object:��ϵ�˻���'s ֤������property 
     */
    public String getCertificateNumber()
    {
        return getString("certificateNumber");
    }
    public void setCertificateNumber(String item)
    {
        setString("certificateNumber", item);
    }
    /**
     * Object:��ϵ�˻���'s ��������property 
     */
    public String getDayOfBirth()
    {
        return getString("dayOfBirth");
    }
    public void setDayOfBirth(String item)
    {
        setString("dayOfBirth", item);
    }
    /**
     * Object:��ϵ�˻���'s ͨ�ŵ�ַproperty 
     */
    public String getMailAddress()
    {
        return getString("mailAddress");
    }
    public void setMailAddress(String item)
    {
        setString("mailAddress", item);
    }
    /**
     * Object:��ϵ�˻���'s ֤����ַproperty 
     */
    public String getBookedAddress()
    {
        return getString("bookedAddress");
    }
    public void setBookedAddress(String item)
    {
        setString("bookedAddress", item);
    }
    /**
     * Object:��ϵ�˻���'s ��������property 
     */
    public String getPostalCode()
    {
        return getString("postalCode");
    }
    public void setPostalCode(String item)
    {
        setString("postalCode", item);
    }
    /**
     * Object: ��ϵ�˻��� 's ֤������ property 
     */
    public com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo getCertificateType()
    {
        return (com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo)get("certificateType");
    }
    public void setCertificateType(com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo item)
    {
        put("certificateType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E633C0A6");
    }
}