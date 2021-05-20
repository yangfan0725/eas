package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCBaseCustomerInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractFDCBaseCustomerInfo()
    {
        this("id");
    }
    protected AbstractFDCBaseCustomerInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�ͻ����ϻ���'s �ͻ�����property 
     */
    public com.kingdee.eas.fdc.basecrm.CustomerTypeEnum getCustomerType()
    {
        return com.kingdee.eas.fdc.basecrm.CustomerTypeEnum.getEnum(getString("customerType"));
    }
    public void setCustomerType(com.kingdee.eas.fdc.basecrm.CustomerTypeEnum item)
    {
		if (item != null) {
        setString("customerType", item.getValue());
		}
    }
    /**
     * Object:�ͻ����ϻ���'s �ͻ�����property 
     */
    public String getCode()
    {
        return getString("code");
    }
    public void setCode(String item)
    {
        setString("code", item);
    }
    /**
     * Object: �ͻ����ϻ��� 's ��Ա property 
     */
    public com.kingdee.eas.fdc.insider.InsiderInfo getInsider()
    {
        return (com.kingdee.eas.fdc.insider.InsiderInfo)get("insider");
    }
    public void setInsider(com.kingdee.eas.fdc.insider.InsiderInfo item)
    {
        put("insider", item);
    }
    /**
     * Object: �ͻ����ϻ��� 's ������ҵ property 
     */
    public com.kingdee.eas.basedata.assistant.IndustryInfo getIndustry()
    {
        return (com.kingdee.eas.basedata.assistant.IndustryInfo)get("industry");
    }
    public void setIndustry(com.kingdee.eas.basedata.assistant.IndustryInfo item)
    {
        put("industry", item);
    }
    /**
     * Object:�ͻ����ϻ���'s ��ѡ��ϵ��property 
     */
    public String getFirstLinkman()
    {
        return getString("firstLinkman");
    }
    public void setFirstLinkman(String item)
    {
        setString("firstLinkman", item);
    }
    /**
     * Object:�ͻ����ϻ���'s �ƶ��绰property 
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
     * Object:�ͻ����ϻ���'s סլ�绰property 
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
     * Object:�ͻ����ϻ���'s �칫�绰property 
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
     * Object:�ͻ����ϻ���'s ����property 
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
     * Object:�ͻ����ϻ���'s �����绰property 
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
     * Object:�ͻ����ϻ���'s Emailproperty 
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
     * Object:�ͻ����ϻ���'s ֤������property 
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
     * Object:�ͻ����ϻ���'s ����/ע������property 
     */
    public java.util.Date getDayOfbirth()
    {
        return getDate("dayOfbirth");
    }
    public void setDayOfbirth(java.util.Date item)
    {
        setDate("dayOfbirth", item);
    }
    /**
     * Object:�ͻ����ϻ���'s �Ա�property 
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
     * Object: �ͻ����ϻ��� 's ����/���� property 
     */
    public com.kingdee.eas.basedata.assistant.CountryInfo getCountry()
    {
        return (com.kingdee.eas.basedata.assistant.CountryInfo)get("country");
    }
    public void setCountry(com.kingdee.eas.basedata.assistant.CountryInfo item)
    {
        put("country", item);
    }
    /**
     * Object: �ͻ����ϻ��� 's ʡ/�� property 
     */
    public com.kingdee.eas.basedata.assistant.ProvinceInfo getProvince()
    {
        return (com.kingdee.eas.basedata.assistant.ProvinceInfo)get("province");
    }
    public void setProvince(com.kingdee.eas.basedata.assistant.ProvinceInfo item)
    {
        put("province", item);
    }
    /**
     * Object: �ͻ����ϻ��� 's ���� property 
     */
    public com.kingdee.eas.basedata.assistant.CityInfo getCity()
    {
        return (com.kingdee.eas.basedata.assistant.CityInfo)get("city");
    }
    public void setCity(com.kingdee.eas.basedata.assistant.CityInfo item)
    {
        put("city", item);
    }
    /**
     * Object: �ͻ����ϻ��� 's ��/�� property 
     */
    public com.kingdee.eas.basedata.assistant.RegionInfo getRegion()
    {
        return (com.kingdee.eas.basedata.assistant.RegionInfo)get("region");
    }
    public void setRegion(com.kingdee.eas.basedata.assistant.RegionInfo item)
    {
        put("region", item);
    }
    /**
     * Object:�ͻ����ϻ���'s ͨ��/��˾��ַproperty 
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
     * Object:�ͻ����ϻ���'s ֤��/ע���ַproperty 
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
     * Object:�ͻ����ϻ���'s ��������property 
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
     * Object:�ͻ����ϻ���'s ��ҵ����property 
     */
    public String getCorporate()
    {
        return getString("corporate");
    }
    public void setCorporate(String item)
    {
        setString("corporate", item);
    }
    /**
     * Object:�ͻ����ϻ���'s ������ϵ�绰property 
     */
    public String getCorporateTel()
    {
        return getString("corporateTel");
    }
    public void setCorporateTel(String item)
    {
        setString("corporateTel", item);
    }
    /**
     * Object: �ͻ����ϻ��� 's �ͻ���� property 
     */
    public com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo getIdentity()
    {
        return (com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo)get("identity");
    }
    public void setIdentity(com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo item)
    {
        put("identity", item);
    }
    /**
     * Object:�ͻ����ϻ���'s �Ƿ�����property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    /**
     * Object:�ͻ����ϻ���'s �Ƿ�ѡ���ͻ�property 
     */
    public boolean isIsChooseRoom()
    {
        return getBoolean("isChooseRoom");
    }
    public void setIsChooseRoom(boolean item)
    {
        setBoolean("isChooseRoom", item);
    }
    /**
     * Object:�ͻ����ϻ���'s �Ƿ��Աproperty 
     */
    public boolean isIsInsider()
    {
        return getBoolean("isInsider");
    }
    public void setIsInsider(boolean item)
    {
        setBoolean("isInsider", item);
    }
    /**
     * Object: �ͻ����ϻ��� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object: �ͻ����ϻ��� 's ������֯ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getCreateUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("createUnit");
    }
    public void setCreateUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("createUnit", item);
    }
    /**
     * Object: �ͻ����ϻ��� 's ����޸���֯ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getLastUpdateUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("lastUpdateUnit");
    }
    public void setLastUpdateUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("lastUpdateUnit", item);
    }
    /**
     * Object:�ͻ����ϻ���'s ������ʽproperty 
     */
    public com.kingdee.eas.fdc.basecrm.CreateWayEnum getCreateWay()
    {
        return com.kingdee.eas.fdc.basecrm.CreateWayEnum.getEnum(getString("createWay"));
    }
    public void setCreateWay(com.kingdee.eas.fdc.basecrm.CreateWayEnum item)
    {
		if (item != null) {
        setString("createWay", item.getValue());
		}
    }
    /**
     * Object:�ͻ����ϻ���'s �ͻ�����property 
     */
    public String getSimpleCode()
    {
        return getString("simpleCode");
    }
    public void setSimpleCode(String item)
    {
        setString("simpleCode", item);
    }
    /**
     * Object: �ͻ����ϻ��� 's �ͻ���Դ property 
     */
    public com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo getCustomerOrigin()
    {
        return (com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo)get("customerOrigin");
    }
    public void setCustomerOrigin(com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo item)
    {
        put("customerOrigin", item);
    }
    /**
     * Object:�ͻ����ϻ���'s ����״��property 
     */
    public com.kingdee.eas.fdc.basecrm.MaritalStatusEnum getMaritalStatus()
    {
        return com.kingdee.eas.fdc.basecrm.MaritalStatusEnum.getEnum(getString("maritalStatus"));
    }
    public void setMaritalStatus(com.kingdee.eas.fdc.basecrm.MaritalStatusEnum item)
    {
		if (item != null) {
        setString("maritalStatus", item.getValue());
		}
    }
    /**
     * Object:�ͻ����ϻ���'s ������λproperty 
     */
    public String getWorkCompany()
    {
        return getString("workCompany");
    }
    public void setWorkCompany(String item)
    {
        setString("workCompany", item);
    }
    /**
     * Object:�ͻ����ϻ���'s ��ѡ��ϵ��ʽproperty 
     */
    public com.kingdee.eas.fdc.basecrm.ContactPreferencesEnum getContactPreferences()
    {
        return com.kingdee.eas.fdc.basecrm.ContactPreferencesEnum.getEnum(getString("contactPreferences"));
    }
    public void setContactPreferences(com.kingdee.eas.fdc.basecrm.ContactPreferencesEnum item)
    {
		if (item != null) {
        setString("contactPreferences", item.getValue());
		}
    }
    /**
     * Object:�ͻ����ϻ���'s ����������property 
     */
    public String getMotorcycles()
    {
        return getString("motorcycles");
    }
    public void setMotorcycles(String item)
    {
        setString("motorcycles", item);
    }
    /**
     * Object: �ͻ����ϻ��� 's ��ס���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo getHabitationArea()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo)get("habitationArea");
    }
    public void setHabitationArea(com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo item)
    {
        put("habitationArea", item);
    }
    /**
     * Object: �ͻ����ϻ��� 's �������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo getWorkArea()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo)get("workArea");
    }
    public void setWorkArea(com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo item)
    {
        put("workArea", item);
    }
    /**
     * Object:�ͻ����ϻ���'s ע���ʱ�property 
     */
    public String getBookedCapital()
    {
        return getString("bookedCapital");
    }
    public void setBookedCapital(String item)
    {
        setString("bookedCapital", item);
    }
    /**
     * Object:�ͻ����ϻ���'s ��Ӫ��Χproperty 
     */
    public String getBusinessScope()
    {
        return getString("businessScope");
    }
    public void setBusinessScope(String item)
    {
        setString("businessScope", item);
    }
    /**
     * Object:�ͻ����ϻ���'s Ա������property 
     */
    public String getEmployees()
    {
        return getString("employees");
    }
    public void setEmployees(String item)
    {
        setString("employees", item);
    }
    /**
     * Object:�ͻ����ϻ���'s ˰��ǼǺ�(��)property 
     */
    public String getTaxRegistrationNoG()
    {
        return getString("taxRegistrationNoG");
    }
    public void setTaxRegistrationNoG(String item)
    {
        setString("taxRegistrationNoG", item);
    }
    /**
     * Object:�ͻ����ϻ���'s ˰��ǼǺ�(��)property 
     */
    public String getTaxRegistrationNoD()
    {
        return getString("taxRegistrationNoD");
    }
    public void setTaxRegistrationNoD(String item)
    {
        setString("taxRegistrationNoD", item);
    }
    /**
     * Object: �ͻ����ϻ��� 's ����ģʽ property 
     */
    public com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo getCooperateModel()
    {
        return (com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo)get("cooperateModel");
    }
    public void setCooperateModel(com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo item)
    {
        put("cooperateModel", item);
    }
    /**
     * Object: �ͻ����ϻ��� 's ��ҵ���� property 
     */
    public com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo getEnterpriceProperty()
    {
        return (com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo)get("enterpriceProperty");
    }
    public void setEnterpriceProperty(com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo item)
    {
        put("enterpriceProperty", item);
    }
    /**
     * Object: �ͻ����ϻ��� 's ֤������ property 
     */
    public com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo getCertificateType()
    {
        return (com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo)get("certificateType");
    }
    public void setCertificateType(com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo item)
    {
        put("certificateType", item);
    }
    /**
     * Object: �ͻ����ϻ��� 's ���� property 
     */
    public com.kingdee.eas.basedata.assistant.RegionInfo getNativePlace()
    {
        return (com.kingdee.eas.basedata.assistant.RegionInfo)get("nativePlace");
    }
    public void setNativePlace(com.kingdee.eas.basedata.assistant.RegionInfo item)
    {
        put("nativePlace", item);
    }
    /**
     * Object: �ͻ����ϻ��� 's ��ҵ��ģ property 
     */
    public com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo getEnterpriceSize()
    {
        return (com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo)get("enterpriceSize");
    }
    public void setEnterpriceSize(com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo item)
    {
        put("enterpriceSize", item);
    }
    /**
     * Object: �ͻ����ϻ��� 's ����/ע��� property 
     */
    public com.kingdee.eas.basedata.assistant.CountryInfo getBookedPlace()
    {
        return (com.kingdee.eas.basedata.assistant.CountryInfo)get("bookedPlace");
    }
    public void setBookedPlace(com.kingdee.eas.basedata.assistant.CountryInfo item)
    {
        put("bookedPlace", item);
    }
    /**
     * Object: �ͻ����ϻ��� 's ��ס��� property 
     */
    public com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo getHabitationStatus()
    {
        return (com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo)get("habitationStatus");
    }
    public void setHabitationStatus(com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo item)
    {
        put("habitationStatus", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BD3B36F8");
    }
}