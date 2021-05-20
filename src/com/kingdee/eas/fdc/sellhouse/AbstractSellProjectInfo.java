package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSellProjectInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractSellProjectInfo()
    {
        this("id");
    }
    protected AbstractSellProjectInfo(String pkField)
    {
        super(pkField);
        put("orgUnitShareList", new com.kingdee.eas.fdc.sellhouse.ShareOrgUnitCollection());
        put("subarea", new com.kingdee.eas.fdc.sellhouse.SubareaCollection());
        put("projectArchEntrys", new com.kingdee.eas.fdc.sellhouse.ProjectArchivesEntryCollection());
        put("shareRoomModels", new com.kingdee.eas.fdc.sellhouse.ShareRoomModelsCollection());
        put("buildings", new com.kingdee.eas.fdc.sellhouse.BuildingCollection());
    }
    /**
     * Object: ��Ŀ���� 's ��֯ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    /**
     * Object: ��Ŀ���� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object: ��Ŀ���� 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SubareaCollection getSubarea()
    {
        return (com.kingdee.eas.fdc.sellhouse.SubareaCollection)get("subarea");
    }
    /**
     * Object: ��Ŀ���� 's ¥�� property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingCollection getBuildings()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingCollection)get("buildings");
    }
    /**
     * Object:��Ŀ����'s �ؿ���property 
     */
    public String getTerraNumber()
    {
        return getString("terraNumber");
    }
    public void setTerraNumber(String item)
    {
        setString("terraNumber", item);
    }
    /**
     * Object:��Ŀ����'s �������property 
     */
    public java.math.BigDecimal getTerraArea()
    {
        return getBigDecimal("terraArea");
    }
    public void setTerraArea(java.math.BigDecimal item)
    {
        setBigDecimal("terraArea", item);
    }
    /**
     * Object: ��Ŀ���� 's �������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo getBuildingProperty()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo)get("buildingProperty");
    }
    public void setBuildingProperty(com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo item)
    {
        put("buildingProperty", item);
    }
    /**
     * Object:��Ŀ����'s �������֤property 
     */
    public String getTerraLicence()
    {
        return getString("terraLicence");
    }
    public void setTerraLicence(String item)
    {
        setString("terraLicence", item);
    }
    /**
     * Object:��Ŀ����'s ��ʼ����property 
     */
    public java.util.Date getTermBegin()
    {
        return getDate("termBegin");
    }
    public void setTermBegin(java.util.Date item)
    {
        setDate("termBegin", item);
    }
    /**
     * Object:��Ŀ����'s ��������property 
     */
    public java.util.Date getTermEnd()
    {
        return getDate("termEnd");
    }
    public void setTermEnd(java.util.Date item)
    {
        setDate("termEnd", item);
    }
    /**
     * Object:��Ŀ����'s ������;property 
     */
    public String getTerraUse()
    {
        return getString("terraUse");
    }
    public void setTerraUse(String item)
    {
        setString("terraUse", item);
    }
    /**
     * Object:��Ŀ����'s �ṹ����property 
     */
    public String getFrameProperty()
    {
        return getString("frameProperty");
    }
    public void setFrameProperty(String item)
    {
        setString("frameProperty", item);
    }
    /**
     * Object:��Ŀ����'s �ݻ���property 
     */
    public java.math.BigDecimal getCubageRate()
    {
        return getBigDecimal("cubageRate");
    }
    public void setCubageRate(java.math.BigDecimal item)
    {
        setBigDecimal("cubageRate", item);
    }
    /**
     * Object:��Ŀ����'s ���ز�֤��property 
     */
    public String getRealtyPaperNumber()
    {
        return getString("realtyPaperNumber");
    }
    public void setRealtyPaperNumber(String item)
    {
        setString("realtyPaperNumber", item);
    }
    /**
     * Object:��Ŀ����'s Ԥ�����֤property 
     */
    public String getPresellLicence()
    {
        return getString("presellLicence");
    }
    public void setPresellLicence(String item)
    {
        setString("presellLicence", item);
    }
    /**
     * Object:��Ŀ����'s �������֤property 
     */
    public String getExportLicence()
    {
        return getString("exportLicence");
    }
    public void setExportLicence(String item)
    {
        setString("exportLicence", item);
    }
    /**
     * Object:��Ŀ����'s ��Ŀ��ַproperty 
     */
    public String getProAddress()
    {
        return getString("proAddress");
    }
    public void setProAddress(String item)
    {
        setString("proAddress", item);
    }
    /**
     * Object:��Ŀ����'s �ܱ߻�����Ϣproperty 
     */
    public String getSideEntironment()
    {
        return getString("sideEntironment");
    }
    public void setSideEntironment(String item)
    {
        setString("sideEntironment", item);
    }
    /**
     * Object:��Ŀ����'s ��¥����property 
     */
    public boolean isIsForSHE()
    {
        return getBoolean("isForSHE");
    }
    public void setIsForSHE(boolean item)
    {
        setBoolean("isForSHE", item);
    }
    /**
     * Object:��Ŀ����'s ��������property 
     */
    public boolean isIsForTen()
    {
        return getBoolean("isForTen");
    }
    public void setIsForTen(boolean item)
    {
        setBoolean("isForTen", item);
    }
    /**
     * Object:��Ŀ����'s ��ҵ����property 
     */
    public boolean isIsForPPM()
    {
        return getBoolean("isForPPM");
    }
    public void setIsForPPM(boolean item)
    {
        setBoolean("isForPPM", item);
    }
    /**
     * Object: ��Ŀ���� 's ������֯��¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.ShareOrgUnitCollection getOrgUnitShareList()
    {
        return (com.kingdee.eas.fdc.sellhouse.ShareOrgUnitCollection)get("orgUnitShareList");
    }
    /**
     * Object:��Ŀ����'s ��Ŀ��Դproperty 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectResourceEnum getProjectResource()
    {
        return com.kingdee.eas.fdc.sellhouse.SellProjectResourceEnum.getEnum(getString("projectResource"));
    }
    public void setProjectResource(com.kingdee.eas.fdc.sellhouse.SellProjectResourceEnum item)
    {
		if (item != null) {
        setString("projectResource", item.getValue());
		}
    }
    /**
     * Object: ��Ŀ���� 's Ͷ�ʷ�Դ property 
     */
    public com.kingdee.eas.fdc.sellhouse.InvestorHouseInfo getInvestorHouse()
    {
        return (com.kingdee.eas.fdc.sellhouse.InvestorHouseInfo)get("investorHouse");
    }
    public void setInvestorHouse(com.kingdee.eas.fdc.sellhouse.InvestorHouseInfo item)
    {
        put("investorHouse", item);
    }
    /**
     * Object: ��Ŀ���� 's ��Ŀ������¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.ProjectArchivesEntryCollection getProjectArchEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.ProjectArchivesEntryCollection)get("projectArchEntrys");
    }
    /**
     * Object:��Ŀ����'s �����ֹ��property 
     */
    public java.util.Date getBalanceEndDate()
    {
        return getDate("balanceEndDate");
    }
    public void setBalanceEndDate(java.util.Date item)
    {
        setDate("balanceEndDate", item);
    }
    /**
     * Object:��Ŀ����'s �Ƿ�����ʼ��property 
     */
    public boolean isIsEndInit()
    {
        return getBoolean("isEndInit");
    }
    public void setIsEndInit(boolean item)
    {
        setBoolean("isEndInit", item);
    }
    /**
     * Object:��Ŀ����'s �½������ڼ�property 
     */
    public String getSaleTerm()
    {
        return getString("saleTerm");
    }
    public void setSaleTerm(String item)
    {
        setString("saleTerm", item);
    }
    /**
     * Object:��Ŀ����'s �½ᵱǰ�ڼ�property 
     */
    public String getSaleNowTerm()
    {
        return getString("saleNowTerm");
    }
    public void setSaleNowTerm(String item)
    {
        setString("saleNowTerm", item);
    }
    /**
     * Object: ��Ŀ���� 's ������Ŀ������ʼ���Ĵ����� property 
     */
    public com.kingdee.eas.base.permission.UserInfo getDisposePerson()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("disposePerson");
    }
    public void setDisposePerson(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("disposePerson", item);
    }
    /**
     * Object:��Ŀ����'s ��������property 
     */
    public java.util.Date getDisposeDate()
    {
        return getDate("disposeDate");
    }
    public void setDisposeDate(java.util.Date item)
    {
        setDate("disposeDate", item);
    }
    /**
     * Object: ��Ŀ���� 's �����ڼ� property 
     */
    public com.kingdee.eas.basedata.assistant.PeriodInfo getSalePeriod()
    {
        return (com.kingdee.eas.basedata.assistant.PeriodInfo)get("salePeriod");
    }
    public void setSalePeriod(com.kingdee.eas.basedata.assistant.PeriodInfo item)
    {
        put("salePeriod", item);
    }
    /**
     * Object: ��Ŀ���� 's ��ǰ�ڼ� property 
     */
    public com.kingdee.eas.basedata.assistant.PeriodInfo getSaleNowPeriod()
    {
        return (com.kingdee.eas.basedata.assistant.PeriodInfo)get("saleNowPeriod");
    }
    public void setSaleNowPeriod(com.kingdee.eas.basedata.assistant.PeriodInfo item)
    {
        put("saleNowPeriod", item);
    }
    /**
     * Object: ��Ŀ���� 's ��˾ property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getCompanyOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("companyOrgUnit");
    }
    public void setCompanyOrgUnit(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("companyOrgUnit", item);
    }
    /**
     * Object: ��Ŀ���� 's �ϼ���Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��Ŀ����'s �Ƿ�ʹ��property 
     */
    public boolean isIsUse()
    {
        return getBoolean("isUse");
    }
    public void setIsUse(boolean item)
    {
        setBoolean("isUse", item);
    }
    /**
     * Object: ��Ŀ���� 's �����б� property 
     */
    public com.kingdee.eas.fdc.sellhouse.ShareRoomModelsCollection getShareRoomModels()
    {
        return (com.kingdee.eas.fdc.sellhouse.ShareRoomModelsCollection)get("shareRoomModels");
    }
    /**
     * Object:��Ŀ����'s ������ĿIDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getImportID()
    {
        return getBOSUuid("importID");
    }
    public void setImportID(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("importID", item);
    }
    /**
     * Object: ��Ŀ���� 's ��������Ŀ��Ϣ property 
     */
    public com.kingdee.eas.fdc.basedata.ProjectBaseInfo getProjectBase()
    {
        return (com.kingdee.eas.fdc.basedata.ProjectBaseInfo)get("projectBase");
    }
    public void setProjectBase(com.kingdee.eas.fdc.basedata.ProjectBaseInfo item)
    {
        put("projectBase", item);
    }
    /**
     * Object:��Ŀ����'s ������������property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2FFBE5AC");
    }
}