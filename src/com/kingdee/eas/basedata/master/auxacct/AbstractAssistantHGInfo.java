package com.kingdee.eas.basedata.master.auxacct;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAssistantHGInfo extends com.kingdee.eas.framework.ObjectBaseInfo implements Serializable 
{
    public AbstractAssistantHGInfo()
    {
        this("id");
    }
    protected AbstractAssistantHGInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�����˺��'s �����˰���������Ŀ����property 
     */
    public int getCount()
    {
        return getInt("count");
    }
    public void setCount(int item)
    {
        setInt("count", item);
    }
    /**
     * Object:�����˺��'s �������property 
     */
    public String getLongNameGroup()
    {
        return getLongNameGroup((Locale)null);
    }
    public void setLongNameGroup(String item)
    {
		setLongNameGroup(item,(Locale)null);
    }
    public String getLongNameGroup(Locale local)
    {
        return TypeConversionUtils.objToString(get("longNameGroup", local));
    }
    public void setLongNameGroup(String item, Locale local)
    {
        put("longNameGroup", item, local);
    }
    /**
     * Object: �����˺�� 's ������ property 
     */
    public com.kingdee.eas.basedata.master.auxacct.AsstAccountInfo getAsstAccount()
    {
        return (com.kingdee.eas.basedata.master.auxacct.AsstAccountInfo)get("asstAccount");
    }
    public void setAsstAccount(com.kingdee.eas.basedata.master.auxacct.AsstAccountInfo item)
    {
        put("asstAccount", item);
    }
    /**
     * Object: �����˺�� 's ���� property 
     */
    public com.kingdee.eas.basedata.master.material.MaterialInfo getMaterial()
    {
        return (com.kingdee.eas.basedata.master.material.MaterialInfo)get("material");
    }
    public void setMaterial(com.kingdee.eas.basedata.master.material.MaterialInfo item)
    {
        put("material", item);
    }
    /**
     * Object: �����˺�� 's �ͻ� property 
     */
    public com.kingdee.eas.basedata.master.cssp.CustomerInfo getCustomer()
    {
        return (com.kingdee.eas.basedata.master.cssp.CustomerInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.basedata.master.cssp.CustomerInfo item)
    {
        put("customer", item);
    }
    /**
     * Object: �����˺�� 's ��Ӧ�� property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getProvider()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("provider");
    }
    public void setProvider(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("provider", item);
    }
    /**
     * Object: �����˺�� 's ��Ŀ property 
     */
    public com.kingdee.eas.basedata.assistant.ProjectInfo getProject()
    {
        return (com.kingdee.eas.basedata.assistant.ProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.basedata.assistant.ProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object: �����˺�� 's �����˻� property 
     */
    public com.kingdee.eas.basedata.assistant.AccountBankInfo getBankAccount()
    {
        return (com.kingdee.eas.basedata.assistant.AccountBankInfo)get("bankAccount");
    }
    public void setBankAccount(com.kingdee.eas.basedata.assistant.AccountBankInfo item)
    {
        put("bankAccount", item);
    }
    /**
     * Object: �����˺�� 's ��ҵ property 
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
     * Object: �����˺�� 's ���� property 
     */
    public com.kingdee.eas.basedata.assistant.BankInfo getRegion()
    {
        return (com.kingdee.eas.basedata.assistant.BankInfo)get("region");
    }
    public void setRegion(com.kingdee.eas.basedata.assistant.BankInfo item)
    {
        put("region", item);
    }
    /**
     * Object: �����˺�� 's �ֽ�������Ŀ property 
     */
    public com.kingdee.eas.basedata.assistant.CashFlowItemInfo getCashFlowItem()
    {
        return (com.kingdee.eas.basedata.assistant.CashFlowItemInfo)get("cashFlowItem");
    }
    public void setCashFlowItem(com.kingdee.eas.basedata.assistant.CashFlowItemInfo item)
    {
        put("cashFlowItem", item);
    }
    /**
     * Object: �����˺�� 's �ֿ� property 
     */
    public com.kingdee.eas.basedata.scm.im.inv.WarehouseInfo getCountry()
    {
        return (com.kingdee.eas.basedata.scm.im.inv.WarehouseInfo)get("country");
    }
    public void setCountry(com.kingdee.eas.basedata.scm.im.inv.WarehouseInfo item)
    {
        put("country", item);
    }
    /**
     * Object: �����˺�� 's ʡ�� property 
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
     * Object: �����˺�� 's ���� property 
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
     * Object: �����˺�� 's ������ property 
     */
    public com.kingdee.eas.basedata.assistant.AccountCussentInfo getAccountCussent()
    {
        return (com.kingdee.eas.basedata.assistant.AccountCussentInfo)get("accountCussent");
    }
    public void setAccountCussent(com.kingdee.eas.basedata.assistant.AccountCussentInfo item)
    {
        put("accountCussent", item);
    }
    /**
     * Object: �����˺�� 's �ɱ����� property 
     */
    public com.kingdee.eas.basedata.assistant.CostObjectInfo getCostObject()
    {
        return (com.kingdee.eas.basedata.assistant.CostObjectInfo)get("costObject");
    }
    public void setCostObject(com.kingdee.eas.basedata.assistant.CostObjectInfo item)
    {
        put("costObject", item);
    }
    /**
     * Object: �����˺�� 's ������֯  property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getAdminOrg()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("adminOrg");
    }
    public void setAdminOrg(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("adminOrg", item);
    }
    /**
     * Object: �����˺�� 's ��˾ property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getCompanyOrg()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("companyOrg");
    }
    public void setCompanyOrg(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("companyOrg", item);
    }
    /**
     * Object: �����˺�� 's ������֯ property 
     */
    public com.kingdee.eas.basedata.org.SaleOrgUnitInfo getSaleOrg()
    {
        return (com.kingdee.eas.basedata.org.SaleOrgUnitInfo)get("saleOrg");
    }
    public void setSaleOrg(com.kingdee.eas.basedata.org.SaleOrgUnitInfo item)
    {
        put("saleOrg", item);
    }
    /**
     * Object: �����˺�� 's �ɹ���֯ property 
     */
    public com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo getPurchaseOrg()
    {
        return (com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo)get("purchaseOrg");
    }
    public void setPurchaseOrg(com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo item)
    {
        put("purchaseOrg", item);
    }
    /**
     * Object: �����˺�� 's �����֯ property 
     */
    public com.kingdee.eas.basedata.org.StorageOrgUnitInfo getStoreageOrg()
    {
        return (com.kingdee.eas.basedata.org.StorageOrgUnitInfo)get("storeageOrg");
    }
    public void setStoreageOrg(com.kingdee.eas.basedata.org.StorageOrgUnitInfo item)
    {
        put("storeageOrg", item);
    }
    /**
     * Object: �����˺�� 's �ɱ�������֯ property 
     */
    public com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo getCostOrg()
    {
        return (com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo)get("costOrg");
    }
    public void setCostOrg(com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo item)
    {
        put("costOrg", item);
    }
    /**
     * Object: �����˺�� 's ����������֯ property 
     */
    public com.kingdee.eas.basedata.org.ProfitCenterOrgUnitInfo getProfitOrg()
    {
        return (com.kingdee.eas.basedata.org.ProfitCenterOrgUnitInfo)get("profitOrg");
    }
    public void setProfitOrg(com.kingdee.eas.basedata.org.ProfitCenterOrgUnitInfo item)
    {
        put("profitOrg", item);
    }
    /**
     * Object: �����˺�� 's ְԱ property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("person");
    }
    public void setPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("person", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ1 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType1()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType1");
    }
    public void setGeneralAssActType1(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType1", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ2 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType2()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType2");
    }
    public void setGeneralAssActType2(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType2", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ3 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType3()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType3");
    }
    public void setGeneralAssActType3(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType3", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ4 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType4()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType4");
    }
    public void setGeneralAssActType4(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType4", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ5 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType5()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType5");
    }
    public void setGeneralAssActType5(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType5", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ6 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType6()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType6");
    }
    public void setGeneralAssActType6(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType6", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ7 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType7()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType7");
    }
    public void setGeneralAssActType7(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType7", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ8 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType8()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType8");
    }
    public void setGeneralAssActType8(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType8", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ9 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType9()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType9");
    }
    public void setGeneralAssActType9(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType9", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ10 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType10()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType10");
    }
    public void setGeneralAssActType10(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType10", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ11 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType11()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType11");
    }
    public void setGeneralAssActType11(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType11", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ12 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType12()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType12");
    }
    public void setGeneralAssActType12(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType12", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ13 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType13()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType13");
    }
    public void setGeneralAssActType13(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType13", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ14 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType14()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType14");
    }
    public void setGeneralAssActType14(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType14", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ15 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType15()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType15");
    }
    public void setGeneralAssActType15(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType15", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ16 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType16()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType16");
    }
    public void setGeneralAssActType16(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType16", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ17 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType17()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType17");
    }
    public void setGeneralAssActType17(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType17", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ18 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType18()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType18");
    }
    public void setGeneralAssActType18(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType18", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ19 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType19()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType19");
    }
    public void setGeneralAssActType19(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType19", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ20 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType20()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType20");
    }
    public void setGeneralAssActType20(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType20", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ21 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType21()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType21");
    }
    public void setGeneralAssActType21(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType21", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ22 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType22()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType22");
    }
    public void setGeneralAssActType22(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType22", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ23 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType23()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType23");
    }
    public void setGeneralAssActType23(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType23", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ24 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType24()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType24");
    }
    public void setGeneralAssActType24(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType24", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ25 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType25()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType25");
    }
    public void setGeneralAssActType25(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType25", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ26 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType26()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType26");
    }
    public void setGeneralAssActType26(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType26", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ27 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType27()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType27");
    }
    public void setGeneralAssActType27(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType27", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ28 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType28()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType28");
    }
    public void setGeneralAssActType28(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType28", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ29 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType29()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType29");
    }
    public void setGeneralAssActType29(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType29", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ30 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType30()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType30");
    }
    public void setGeneralAssActType30(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType30", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ31 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType31()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType31");
    }
    public void setGeneralAssActType31(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType31", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ32 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType32()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType32");
    }
    public void setGeneralAssActType32(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType32", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ33 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType33()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType33");
    }
    public void setGeneralAssActType33(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType33", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ34 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType34()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType34");
    }
    public void setGeneralAssActType34(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType34", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ35 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType35()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType35");
    }
    public void setGeneralAssActType35(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType35", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ36 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType36()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType36");
    }
    public void setGeneralAssActType36(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType36", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ37 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType37()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType37");
    }
    public void setGeneralAssActType37(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType37", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ38 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType38()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType38");
    }
    public void setGeneralAssActType38(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType38", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ39 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType39()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType39");
    }
    public void setGeneralAssActType39(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType39", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ40 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType40()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType40");
    }
    public void setGeneralAssActType40(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType40", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ41 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType41()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType41");
    }
    public void setGeneralAssActType41(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType41", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ42 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType42()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType42");
    }
    public void setGeneralAssActType42(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType42", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ43 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType43()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType43");
    }
    public void setGeneralAssActType43(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType43", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ44 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType44()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType44");
    }
    public void setGeneralAssActType44(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType44", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ45 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType45()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType45");
    }
    public void setGeneralAssActType45(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType45", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ46 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType46()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType46");
    }
    public void setGeneralAssActType46(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType46", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ47 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType47()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType47");
    }
    public void setGeneralAssActType47(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType47", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ48 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType48()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType48");
    }
    public void setGeneralAssActType48(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType48", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ49 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType49()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType49");
    }
    public void setGeneralAssActType49(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType49", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ50 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType50()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType50");
    }
    public void setGeneralAssActType50(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType50", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ51 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType51()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType51");
    }
    public void setGeneralAssActType51(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType51", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ52 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType52()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType52");
    }
    public void setGeneralAssActType52(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType52", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ53 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType53()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType53");
    }
    public void setGeneralAssActType53(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType53", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ54 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType54()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType54");
    }
    public void setGeneralAssActType54(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType54", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ55 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType55()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType55");
    }
    public void setGeneralAssActType55(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType55", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ56 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType56()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType56");
    }
    public void setGeneralAssActType56(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType56", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ57 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType57()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType57");
    }
    public void setGeneralAssActType57(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType57", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ58 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType58()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType58");
    }
    public void setGeneralAssActType58(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType58", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ59 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType59()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType59");
    }
    public void setGeneralAssActType59(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType59", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ60 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType60()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType60");
    }
    public void setGeneralAssActType60(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType60", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ61 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType61()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType61");
    }
    public void setGeneralAssActType61(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType61", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ62 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType62()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType62");
    }
    public void setGeneralAssActType62(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType62", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ63 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType63()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType63");
    }
    public void setGeneralAssActType63(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType63", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ64 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType64()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType64");
    }
    public void setGeneralAssActType64(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType64", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ65 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType65()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType65");
    }
    public void setGeneralAssActType65(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType65", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ66 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType66()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType66");
    }
    public void setGeneralAssActType66(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType66", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ67 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType67()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType67");
    }
    public void setGeneralAssActType67(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType67", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ68 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType68()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType68");
    }
    public void setGeneralAssActType68(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType68", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ69 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType69()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType69");
    }
    public void setGeneralAssActType69(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType69", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ70 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType70()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType70");
    }
    public void setGeneralAssActType70(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType70", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ71 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType71()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType71");
    }
    public void setGeneralAssActType71(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType71", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ72 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType72()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType72");
    }
    public void setGeneralAssActType72(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType72", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ73 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType73()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType73");
    }
    public void setGeneralAssActType73(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType73", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ74 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType74()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType74");
    }
    public void setGeneralAssActType74(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType74", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ75 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType75()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType75");
    }
    public void setGeneralAssActType75(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType75", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ76 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType76()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType76");
    }
    public void setGeneralAssActType76(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType76", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ77 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType77()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType77");
    }
    public void setGeneralAssActType77(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType77", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ78 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType78()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType78");
    }
    public void setGeneralAssActType78(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType78", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ79 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType79()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType79");
    }
    public void setGeneralAssActType79(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType79", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ80 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType80()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType80");
    }
    public void setGeneralAssActType80(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType80", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ81 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType81()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType81");
    }
    public void setGeneralAssActType81(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType81", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ82 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType82()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType82");
    }
    public void setGeneralAssActType82(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType82", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ83 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType83()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType83");
    }
    public void setGeneralAssActType83(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType83", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ84 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType84()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType84");
    }
    public void setGeneralAssActType84(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType84", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ85 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType85()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType85");
    }
    public void setGeneralAssActType85(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType85", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ86 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType86()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType86");
    }
    public void setGeneralAssActType86(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType86", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ87 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType87()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType87");
    }
    public void setGeneralAssActType87(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType87", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ88 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType88()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType88");
    }
    public void setGeneralAssActType88(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType88", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ89 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType89()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType89");
    }
    public void setGeneralAssActType89(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType89", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ90 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType90()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType90");
    }
    public void setGeneralAssActType90(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType90", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ91 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType91()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType91");
    }
    public void setGeneralAssActType91(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType91", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ92 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType92()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType92");
    }
    public void setGeneralAssActType92(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType92", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ93 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType93()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType93");
    }
    public void setGeneralAssActType93(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType93", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ94 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType94()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType94");
    }
    public void setGeneralAssActType94(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType94", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ95 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType95()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType95");
    }
    public void setGeneralAssActType95(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType95", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ96 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType96()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType96");
    }
    public void setGeneralAssActType96(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType96", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ97 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType97()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType97");
    }
    public void setGeneralAssActType97(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType97", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ98 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType98()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType98");
    }
    public void setGeneralAssActType98(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType98", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ99 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType99()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType99");
    }
    public void setGeneralAssActType99(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType99", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ100 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType100()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType100");
    }
    public void setGeneralAssActType100(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType100", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ101 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType101()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType101");
    }
    public void setGeneralAssActType101(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType101", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ102 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType102()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType102");
    }
    public void setGeneralAssActType102(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType102", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ103 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType103()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType103");
    }
    public void setGeneralAssActType103(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType103", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ104 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType104()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType104");
    }
    public void setGeneralAssActType104(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType104", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ105 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType105()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType105");
    }
    public void setGeneralAssActType105(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType105", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ106 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType106()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType106");
    }
    public void setGeneralAssActType106(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType106", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ107 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType107()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType107");
    }
    public void setGeneralAssActType107(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType107", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ108 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType108()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType108");
    }
    public void setGeneralAssActType108(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType108", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ109 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType109()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType109");
    }
    public void setGeneralAssActType109(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType109", item);
    }
    /**
     * Object: �����˺�� 's �Զ��������Ŀ110 property 
     */
    public com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo getGeneralAssActType110()
    {
        return (com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo)get("generalAssActType110");
    }
    public void setGeneralAssActType110(com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeInfo item)
    {
        put("generalAssActType110", item);
    }
    /**
     * Object:�����˺��'s �����������property 
     */
    public String getDisplayNameGroup()
    {
        return getDisplayNameGroup((Locale)null);
    }
    public void setDisplayNameGroup(String item)
    {
		setDisplayNameGroup(item,(Locale)null);
    }
    public String getDisplayNameGroup(Locale local)
    {
        return TypeConversionUtils.objToString(get("displayNameGroup", local));
    }
    public void setDisplayNameGroup(String item, Locale local)
    {
        put("displayNameGroup", item, local);
    }
    /**
     * Object: �����˺�� 's �ڲ��˻� property 
     */
    public com.kingdee.eas.fm.fs.InnerAccountInfo getInnerAccount()
    {
        return (com.kingdee.eas.fm.fs.InnerAccountInfo)get("innerAccount");
    }
    public void setInnerAccount(com.kingdee.eas.fm.fs.InnerAccountInfo item)
    {
        put("innerAccount", item);
    }
    /**
     * Object: �����˺�� 's �ʽ�ƻ���Ŀ property 
     */
    public com.kingdee.eas.fm.fpl.FpItemInfo getFpItem()
    {
        return (com.kingdee.eas.fm.fpl.FpItemInfo)get("fpItem");
    }
    public void setFpItem(com.kingdee.eas.fm.fpl.FpItemInfo item)
    {
        put("fpItem", item);
    }
    /**
     * Object: �����˺�� 's Ʊ������ property 
     */
    public com.kingdee.eas.fm.nt.NTTypeInfo getNtType()
    {
        return (com.kingdee.eas.fm.nt.NTTypeInfo)get("ntType");
    }
    public void setNtType(com.kingdee.eas.fm.nt.NTTypeInfo item)
    {
        put("ntType", item);
    }
    /**
     * Object:�����˺��'s �����������property 
     */
    public String getNumberGroup()
    {
        return getNumberGroup((Locale)null);
    }
    public void setNumberGroup(String item)
    {
		setNumberGroup(item,(Locale)null);
    }
    public String getNumberGroup(Locale local)
    {
        return TypeConversionUtils.objToString(get("numberGroup", local));
    }
    public void setNumberGroup(String item, Locale local)
    {
        put("numberGroup", item, local);
    }
    /**
     * Object: �����˺�� 's �ո���� property 
     */
    public com.kingdee.eas.fi.cas.FeeTypeInfo getFeeType()
    {
        return (com.kingdee.eas.fi.cas.FeeTypeInfo)get("feeType");
    }
    public void setFeeType(com.kingdee.eas.fi.cas.FeeTypeInfo item)
    {
        put("feeType", item);
    }
    /**
     * Object: �����˺�� 's �ɱ���Ŀ property 
     */
    public com.kingdee.eas.basedata.assistant.CostItemInfo getCostItem()
    {
        return (com.kingdee.eas.basedata.assistant.CostItemInfo)get("costItem");
    }
    public void setCostItem(com.kingdee.eas.basedata.assistant.CostItemInfo item)
    {
        put("costItem", item);
    }
    /**
     * Object: �����˺�� 's ��ǰ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("CurProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("CurProject", item);
    }
    /**
     * Object: �����˺�� 's ��Ʒ���� property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProductType()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("ProductType");
    }
    public void setProductType(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("ProductType", item);
    }
    /**
     * Object: �����˺�� 's ��ͬ�������� property 
     */
    public com.kingdee.eas.fdc.contract.ContractBaseDataInfo getContractBaseData()
    {
        return (com.kingdee.eas.fdc.contract.ContractBaseDataInfo)get("ContractBaseData");
    }
    public void setContractBaseData(com.kingdee.eas.fdc.contract.ContractBaseDataInfo item)
    {
        put("ContractBaseData", item);
    }
    /**
     * Object: �����˺�� 's Ӫ����Ŀ property 
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
     * Object: �����˺�� 's ¥�� property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingInfo getBuilding()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingInfo)get("building");
    }
    public void setBuilding(com.kingdee.eas.fdc.sellhouse.BuildingInfo item)
    {
        put("building", item);
    }
    /**
     * Object: �����˺�� 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("room");
    }
    public void setRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("room", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("418A6CBB");
    }
}