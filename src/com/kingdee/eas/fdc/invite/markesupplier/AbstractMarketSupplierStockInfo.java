package com.kingdee.eas.fdc.invite.markesupplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierStockInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractMarketSupplierStockInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierStockInfo(String pkField)
    {
        super(pkField);
        put("supplierSplAreaEntry", new com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockSupplierSplAreaEntryCollection());
        put("achievement", new com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockAchievementCollection());
        put("linkPerson", new com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockLinkPersonCollection());
        put("yearSale", new com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockYearSaleCollection());
        put("supplierServiceType", new com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockSupplierServiceTypeCollection());
        put("supplierAttachListEntry", new com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockSupplierAttachListEntryCollection());
        put("supplierPersonEntry", new com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockSupplierPersonEntryCollection());
        put("aptitudeFile", new com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockAptitudeFileCollection());
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's ��Ӧ������ property 
     */
    public com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo getSupplierType()
    {
        return (com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo)get("supplierType");
    }
    public void setSupplierType(com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo item)
    {
        put("supplierType", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��ҵ����property 
     */
    public com.kingdee.eas.fdc.invite.supplier.EnterpriseKindEnum getEnterpriseKind()
    {
        return com.kingdee.eas.fdc.invite.supplier.EnterpriseKindEnum.getEnum(getString("enterpriseKind"));
    }
    public void setEnterpriseKind(com.kingdee.eas.fdc.invite.supplier.EnterpriseKindEnum item)
    {
		if (item != null) {
        setString("enterpriseKind", item.getValue());
		}
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ���˴���property 
     */
    public String getEnterpriseMaster()
    {
        return getString("enterpriseMaster");
    }
    public void setEnterpriseMaster(String item)
    {
        setString("enterpriseMaster", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��������property 
     */
    public java.util.Date getBuildDate()
    {
        return getDate("buildDate");
    }
    public void setBuildDate(java.util.Date item)
    {
        setDate("buildDate", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ע���ʽ�property 
     */
    public java.math.BigDecimal getRegisterMoney()
    {
        return getBigDecimal("registerMoney");
    }
    public void setRegisterMoney(java.math.BigDecimal item)
    {
        setBigDecimal("registerMoney", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��˾��ҳproperty 
     */
    public String getWebSite()
    {
        return getString("webSite");
    }
    public void setWebSite(String item)
    {
        setString("webSite", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s Ӫҵִ��property 
     */
    public String getBusinessNum()
    {
        return getString("businessNum");
    }
    public void setBusinessNum(String item)
    {
        setString("businessNum", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��Ա����property 
     */
    public java.math.BigDecimal getPeopleCount()
    {
        return getBigDecimal("peopleCount");
    }
    public void setPeopleCount(java.math.BigDecimal item)
    {
        setBigDecimal("peopleCount", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ������Ա����property 
     */
    public java.math.BigDecimal getMainPeopleCount()
    {
        return getBigDecimal("mainPeopleCount");
    }
    public void setMainPeopleCount(java.math.BigDecimal item)
    {
        setBigDecimal("mainPeopleCount", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ������Ա����property 
     */
    public java.math.BigDecimal getManagePeopleCount()
    {
        return getBigDecimal("managePeopleCount");
    }
    public void setManagePeopleCount(java.math.BigDecimal item)
    {
        setBigDecimal("managePeopleCount", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s �з���Ա����property 
     */
    public java.math.BigDecimal getDevelopPeopleCount()
    {
        return getBigDecimal("developPeopleCount");
    }
    public void setDevelopPeopleCount(java.math.BigDecimal item)
    {
        setBigDecimal("developPeopleCount", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��˾�绰property 
     */
    public String getLinkPhone()
    {
        return getString("linkPhone");
    }
    public void setLinkPhone(String item)
    {
        setString("linkPhone", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ����property 
     */
    public String getLinkFax()
    {
        return getString("linkFax");
    }
    public void setLinkFax(String item)
    {
        setString("linkFax", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��˾����property 
     */
    public String getLinkMail()
    {
        return getString("linkMail");
    }
    public void setLinkMail(String item)
    {
        setString("linkMail", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��˾��ַproperty 
     */
    public String getAddress()
    {
        return getString("address");
    }
    public void setAddress(String item)
    {
        setString("address", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��������property 
     */
    public String getServiceArea()
    {
        return getString("serviceArea");
    }
    public void setServiceArea(String item)
    {
        setString("serviceArea", item);
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's ��ϵ�� property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockLinkPersonCollection getLinkPerson()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockLinkPersonCollection)get("linkPerson");
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's ���۶� property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockYearSaleCollection getYearSale()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockYearSaleCollection)get("yearSale");
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ���ʲ�property 
     */
    public java.math.BigDecimal getNetMoney()
    {
        return getBigDecimal("netMoney");
    }
    public void setNetMoney(java.math.BigDecimal item)
    {
        setBigDecimal("netMoney", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s �ʲ���ծ��property 
     */
    public String getMoneyPercent()
    {
        return getString("moneyPercent");
    }
    public void setMoneyPercent(String item)
    {
        setString("moneyPercent", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��������property 
     */
    public String getBankName()
    {
        return getString("bankName");
    }
    public void setBankName(String item)
    {
        setString("bankName", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s �����ʺ�property 
     */
    public String getBankCount()
    {
        return getString("bankCount");
    }
    public void setBankCount(String item)
    {
        setString("bankCount", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s �������õȼ�property 
     */
    public String getCreditLevel()
    {
        return getString("creditLevel");
    }
    public void setCreditLevel(String item)
    {
        setString("creditLevel", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ������ϵ�˵绰property 
     */
    public String getBankLinkPhone()
    {
        return getString("bankLinkPhone");
    }
    public void setBankLinkPhone(String item)
    {
        setString("bankLinkPhone", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s Ӫҵ��Χproperty 
     */
    public String getMainWork()
    {
        return getString("mainWork");
    }
    public void setMainWork(String item)
    {
        setString("mainWork", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��ӪƷ��property 
     */
    public String getWorkClass()
    {
        return getString("workClass");
    }
    public void setWorkClass(String item)
    {
        setString("workClass", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��עproperty 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's �����ļ� property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockAptitudeFileCollection getAptitudeFile()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockAptitudeFileCollection)get("aptitudeFile");
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's ҵ�� property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockAchievementCollection getAchievement()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockAchievementCollection)get("achievement");
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��Ӧ�̿�״̬property 
     */
    public String getStockStata()
    {
        return getString("stockStata");
    }
    public void setStockStata(String item)
    {
        setString("stockStata", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s �ұ�property 
     */
    public com.kingdee.eas.fdc.invite.supplier.CurrencyEnum getCurrency()
    {
        return com.kingdee.eas.fdc.invite.supplier.CurrencyEnum.getEnum(getString("currency"));
    }
    public void setCurrency(com.kingdee.eas.fdc.invite.supplier.CurrencyEnum item)
    {
		if (item != null) {
        setString("currency", item.getValue());
		}
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s �ʱ�property 
     */
    public String getPostNumber()
    {
        return getString("postNumber");
    }
    public void setPostNumber(String item)
    {
        setString("postNumber", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ״̬property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierStateEnum getState()
    {
        return com.kingdee.eas.fdc.invite.supplier.SupplierStateEnum.getEnum(getInt("state"));
    }
    public void setState(com.kingdee.eas.fdc.invite.supplier.SupplierStateEnum item)
    {
		if (item != null) {
        setInt("state", item.getValue());
		}
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's ʡ property 
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
     * Object: ��Ӧ�̵����Ǽ� 's ���� property 
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
     * Object:��Ӧ�̵����Ǽ�'s ��Χ��ʷproperty 
     */
    public java.math.BigDecimal getEnterHis()
    {
        return getBigDecimal("enterHis");
    }
    public void setEnterHis(java.math.BigDecimal item)
    {
        setBigDecimal("enterHis", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ǩԼ��ʷproperty 
     */
    public java.math.BigDecimal getConstractHis()
    {
        return getBigDecimal("constractHis");
    }
    public void setConstractHis(java.math.BigDecimal item)
    {
        setBigDecimal("constractHis", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ������ʷproperty 
     */
    public java.math.BigDecimal getAppraiseHis()
    {
        return getBigDecimal("appraiseHis");
    }
    public void setAppraiseHis(java.math.BigDecimal item)
    {
        setBigDecimal("appraiseHis", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s �����ʷproperty 
     */
    public java.math.BigDecimal getChangeHis()
    {
        return getBigDecimal("changeHis");
    }
    public void setChangeHis(java.math.BigDecimal item)
    {
        setBigDecimal("changeHis", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s �汾property 
     */
    public java.math.BigDecimal getVersion()
    {
        return getBigDecimal("version");
    }
    public void setVersion(java.math.BigDecimal item)
    {
        setBigDecimal("version", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s �Ƿ����°汾property 
     */
    public boolean isIsLatestVer()
    {
        return getBoolean("isLatestVer");
    }
    public void setIsLatestVer(boolean item)
    {
        setBoolean("isLatestVer", item);
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's ԭʼ��Ӧ�� property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo getSrcSupplier()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo)get("srcSupplier");
    }
    public void setSrcSupplier(com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo item)
    {
        put("srcSupplier", item);
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's ��Ӧ�̷������� property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockSupplierServiceTypeCollection getSupplierServiceType()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockSupplierServiceTypeCollection)get("supplierServiceType");
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ���ʱ�Ƿ����ɹ������ݵĹ�Ӧ��property 
     */
    public boolean isHasCreatedSupplierBill()
    {
        return getBoolean("hasCreatedSupplierBill");
    }
    public void setHasCreatedSupplierBill(boolean item)
    {
        setBoolean("hasCreatedSupplierBill", item);
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's ������֯ property 
     */
    public com.kingdee.eas.basedata.org.CtrlUnitInfo getAdminCU()
    {
        return (com.kingdee.eas.basedata.org.CtrlUnitInfo)get("adminCU");
    }
    public void setAdminCU(com.kingdee.eas.basedata.org.CtrlUnitInfo item)
    {
        put("adminCU", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s �Ƿ�ϸ�property 
     */
    public com.kingdee.eas.fdc.invite.supplier.IsGradeEnum getIsPass()
    {
        return com.kingdee.eas.fdc.invite.supplier.IsGradeEnum.getEnum(getInt("isPass"));
    }
    public void setIsPass(com.kingdee.eas.fdc.invite.supplier.IsGradeEnum item)
    {
		if (item != null) {
        setInt("isPass", item.getValue());
		}
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ������property 
     */
    public String getStorageNumber()
    {
        return getString("storageNumber");
    }
    public void setStorageNumber(String item)
    {
        setString("storageNumber", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s �������property 
     */
    public java.util.Date getStorageDate()
    {
        return getDate("storageDate");
    }
    public void setStorageDate(java.util.Date item)
    {
        setDate("storageDate", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s �������Ƿ��з��ɾ��ף��Ƿ��������Ŵ���property 
     */
    public String getPunish()
    {
        return getString("punish");
    }
    public void setPunish(String item)
    {
        setString("punish", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ˰��ǼǺ�property 
     */
    public String getTaxRegisterNo()
    {
        return getString("taxRegisterNo");
    }
    public void setTaxRegisterNo(String item)
    {
        setString("taxRegisterNo", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ����ע���property 
     */
    public String getBizRegisterNo()
    {
        return getString("bizRegisterNo");
    }
    public void setBizRegisterNo(String item)
    {
        setString("bizRegisterNo", item);
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's ���������¼ property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockSupplierSplAreaEntryCollection getSupplierSplAreaEntry()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockSupplierSplAreaEntryCollection)get("supplierSplAreaEntry");
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's �������� property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierFileTypInfo getSupplierFileType()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierFileTypInfo)get("supplierFileType");
    }
    public void setSupplierFileType(com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierFileTypInfo item)
    {
        put("supplierFileType", item);
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's ��Ӫģʽ property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierBusinessModeInfo getSupplierBusinessMode()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierBusinessModeInfo)get("supplierBusinessMode");
    }
    public void setSupplierBusinessMode(com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierBusinessModeInfo item)
    {
        put("supplierBusinessMode", item);
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's ְԱ���ɷ�¼ property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockSupplierPersonEntryCollection getSupplierPersonEntry()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockSupplierPersonEntryCollection)get("supplierPersonEntry");
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's ���������嵥��¼ property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockSupplierAttachListEntryCollection getSupplierAttachListEntry()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockSupplierAttachListEntryCollection)get("supplierAttachListEntry");
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s �ʲ��ܶ��Ԫ��property 
     */
    public java.math.BigDecimal getTotalAssets()
    {
        return getBigDecimal("totalAssets");
    }
    public void setTotalAssets(java.math.BigDecimal item)
    {
        setBigDecimal("totalAssets", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s �̶��ʲ�����Ԫ��property 
     */
    public java.math.BigDecimal getFixedAssets()
    {
        return getBigDecimal("fixedAssets");
    }
    public void setFixedAssets(java.math.BigDecimal item)
    {
        setBigDecimal("fixedAssets", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s �����ʲ�����Ԫ��property 
     */
    public java.math.BigDecimal getCurrentAssets()
    {
        return getBigDecimal("currentAssets");
    }
    public void setCurrentAssets(java.math.BigDecimal item)
    {
        setBigDecimal("currentAssets", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��ծ�ܶ��Ԫ��property 
     */
    public java.math.BigDecimal getTotalLiabilities()
    {
        return getBigDecimal("totalLiabilities");
    }
    public void setTotalLiabilities(java.math.BigDecimal item)
    {
        setBigDecimal("totalLiabilities", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ���ڸ�ծ����Ԫ��property 
     */
    public java.math.BigDecimal getLongLiabilities()
    {
        return getBigDecimal("longLiabilities");
    }
    public void setLongLiabilities(java.math.BigDecimal item)
    {
        setBigDecimal("longLiabilities", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ������ծ����Ԫ��property 
     */
    public java.math.BigDecimal getCurrentLiabilities()
    {
        return getBigDecimal("currentLiabilities");
    }
    public void setCurrentLiabilities(java.math.BigDecimal item)
    {
        setBigDecimal("currentLiabilities", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s �����������Ԫ��property 
     */
    public java.math.BigDecimal getLastYearProfit()
    {
        return getBigDecimal("lastYearProfit");
    }
    public void setLastYearProfit(java.math.BigDecimal item)
    {
        setBigDecimal("lastYearProfit", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��ʩ������property 
     */
    public String getAbility()
    {
        return getString("ability");
    }
    public void setAbility(String item)
    {
        setString("ability", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s Ŀǰ��ģproperty 
     */
    public String getScale()
    {
        return getString("scale");
    }
    public void setScale(String item)
    {
        setString("scale", item);
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's ��׼�� property 
     */
    public com.kingdee.eas.base.permission.UserInfo getAuditor()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("auditor");
    }
    public void setAuditor(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("auditor", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��׼����property 
     */
    public java.util.Date getAuditDate()
    {
        return getDate("auditDate");
    }
    public void setAuditDate(java.util.Date item)
    {
        setDate("auditDate", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s �������Ŀproperty 
     */
    public String getPartProject()
    {
        return getString("partProject");
    }
    public void setPartProject(String item)
    {
        setString("partProject", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��Ȩ��ϵ��property 
     */
    public String getAuthorizePerson()
    {
        return getString("authorizePerson");
    }
    public void setAuthorizePerson(String item)
    {
        setString("authorizePerson", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��Ȩ��ϵ�˵绰property 
     */
    public String getAuthorizePhone()
    {
        return getString("authorizePhone");
    }
    public void setAuthorizePhone(String item)
    {
        setString("authorizePhone", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ְ��property 
     */
    public String getAuthorizeJob()
    {
        return getString("authorizeJob");
    }
    public void setAuthorizeJob(String item)
    {
        setString("authorizeJob", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ʵ�ʳа���property 
     */
    public String getContractor()
    {
        return getString("contractor");
    }
    public void setContractor(String item)
    {
        setString("contractor", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ʵ�ʳа�����ϵ�绰property 
     */
    public String getContractorPhone()
    {
        return getString("contractorPhone");
    }
    public void setContractorPhone(String item)
    {
        setString("contractorPhone", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��Ŀ����property 
     */
    public String getManager()
    {
        return getString("manager");
    }
    public void setManager(String item)
    {
        setString("manager", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��Ŀ������ϵ�绰property 
     */
    public String getManagerPhone()
    {
        return getString("managerPhone");
    }
    public void setManagerPhone(String item)
    {
        setString("managerPhone", item);
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's �ȼ� property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketGradeSetUpInfo getGrade()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketGradeSetUpInfo)get("grade");
    }
    public void setGrade(com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketGradeSetUpInfo item)
    {
        put("grade", item);
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's �ɹ���� property 
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
     * Object: ��Ӧ�̵����Ǽ� 's ������֯ property 
     */
    public com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo getPurchaseOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo)get("purchaseOrgUnit");
    }
    public void setPurchaseOrgUnit(com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo item)
    {
        put("purchaseOrgUnit", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s �Ƽ���property 
     */
    public String getRecommended()
    {
        return getString("recommended");
    }
    public void setRecommended(String item)
    {
        setString("recommended", item);
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's �����ݹ�Ӧ�� property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getSysSupplier()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("sysSupplier");
    }
    public void setSysSupplier(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("sysSupplier", item);
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's ���� property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketLevelSetUpInfo getLevel()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketLevelSetUpInfo)get("level");
    }
    public void setLevel(com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketLevelSetUpInfo item)
    {
        put("level", item);
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's ���ʵȼ� property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarktQuaLevelInfo getQuaLevel()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarktQuaLevelInfo)get("quaLevel");
    }
    public void setQuaLevel(com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarktQuaLevelInfo item)
    {
        put("quaLevel", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��������property 
     */
    public String getSplArea()
    {
        return getString("splArea");
    }
    public void setSplArea(String item)
    {
        setString("splArea", item);
    }
    /**
     * Object: ��Ӧ�̵����Ǽ� 's ֪���� property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.marketbase.VisibilityInfo getVisibility()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.VisibilityInfo)get("Visibility");
    }
    public void setVisibility(com.kingdee.eas.fdc.invite.markesupplier.marketbase.VisibilityInfo item)
    {
        put("Visibility", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s �������property 
     */
    public String getServerfees()
    {
        return getString("serverfees");
    }
    public void setServerfees(String item)
    {
        setString("serverfees", item);
    }
    /**
     * Object:��Ӧ�̵����Ǽ�'s ��עproperty 
     */
    public String getMarketRemake()
    {
        return getString("marketRemake");
    }
    public void setMarketRemake(String item)
    {
        setString("marketRemake", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("98690D80");
    }
}