package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractBillReceiveInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractContractBillReceiveInfo()
    {
        this("id");
    }
    protected AbstractContractBillReceiveInfo(String pkField)
    {
        super(pkField);
        put("rateEntry", new com.kingdee.eas.fdc.contract.ContractBillReceiveRateEntryCollection());
        put("entrys", new com.kingdee.eas.fdc.contract.ContractBillReceiveEntryCollection());
    }
    /**
     * Object: ��ͬ���ݣ������ࣩ 's ��¼ property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillReceiveEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillReceiveEntryCollection)get("entrys");
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �����ʾ����property 
     */
    public java.math.BigDecimal getChgPercForWarn()
    {
        return getBigDecimal("chgPercForWarn");
    }
    public void setChgPercForWarn(java.math.BigDecimal item)
    {
        setBigDecimal("chgPercForWarn", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ������ʾ����property 
     */
    public java.math.BigDecimal getPayPercForWarn()
    {
        return getBigDecimal("payPercForWarn");
    }
    public void setPayPercForWarn(java.math.BigDecimal item)
    {
        setBigDecimal("payPercForWarn", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ǩԼ����property 
     */
    public java.util.Date getSignDate()
    {
        return getDate("signDate");
    }
    public void setSignDate(java.util.Date item)
    {
        setDate("signDate", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ���Ͷ���property 
     */
    public java.math.BigDecimal getLowestPrice()
    {
        return getBigDecimal("lowestPrice");
    }
    public void setLowestPrice(java.math.BigDecimal item)
    {
        setBigDecimal("lowestPrice", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �ε�Ͷ���property 
     */
    public java.math.BigDecimal getLowerPrice()
    {
        return getBigDecimal("lowerPrice");
    }
    public void setLowerPrice(java.math.BigDecimal item)
    {
        setBigDecimal("lowerPrice", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �м�Ͷ���property 
     */
    public java.math.BigDecimal getMiddlePrice()
    {
        return getBigDecimal("middlePrice");
    }
    public void setMiddlePrice(java.math.BigDecimal item)
    {
        setBigDecimal("middlePrice", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �θ�Ͷ���property 
     */
    public java.math.BigDecimal getHigherPrice()
    {
        return getBigDecimal("higherPrice");
    }
    public void setHigherPrice(java.math.BigDecimal item)
    {
        setBigDecimal("higherPrice", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ���Ͷ���property 
     */
    public java.math.BigDecimal getHighestPrice()
    {
        return getBigDecimal("highestPrice");
    }
    public void setHighestPrice(java.math.BigDecimal item)
    {
        setBigDecimal("highestPrice", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �б��property 
     */
    public java.math.BigDecimal getWinPrice()
    {
        return getBigDecimal("winPrice");
    }
    public void setWinPrice(java.math.BigDecimal item)
    {
        setBigDecimal("winPrice", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ��λ����property 
     */
    public java.math.BigDecimal getQuantity()
    {
        return getBigDecimal("quantity");
    }
    public void setQuantity(java.math.BigDecimal item)
    {
        setBigDecimal("quantity", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �б��ļ���property 
     */
    public String getFileNo()
    {
        return getString("fileNo");
    }
    public void setFileNo(String item)
    {
        setString("fileNo", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �׼�property 
     */
    public java.math.BigDecimal getBasePrice()
    {
        return getBigDecimal("basePrice");
    }
    public void setBasePrice(java.math.BigDecimal item)
    {
        setBigDecimal("basePrice", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �����property 
     */
    public java.math.BigDecimal getSecondPrice()
    {
        return getBigDecimal("secondPrice");
    }
    public void setSecondPrice(java.math.BigDecimal item)
    {
        setBigDecimal("secondPrice", item);
    }
    /**
     * Object: ��ͬ���ݣ������ࣩ 's �׷� property 
     */
    public com.kingdee.eas.fdc.basedata.LandDeveloperInfo getLandDeveloper()
    {
        return (com.kingdee.eas.fdc.basedata.LandDeveloperInfo)get("landDeveloper");
    }
    public void setLandDeveloper(com.kingdee.eas.fdc.basedata.LandDeveloperInfo item)
    {
        put("landDeveloper", item);
    }
    /**
     * Object: ��ͬ���ݣ������ࣩ 's ��ͬ���� property 
     */
    public com.kingdee.eas.fdc.basedata.ContractTypeInfo getContractType()
    {
        return (com.kingdee.eas.fdc.basedata.ContractTypeInfo)get("contractType");
    }
    public void setContractType(com.kingdee.eas.fdc.basedata.ContractTypeInfo item)
    {
        put("contractType", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �������property 
     */
    public com.kingdee.eas.fdc.contract.CostPropertyEnum getCostProperty()
    {
        return com.kingdee.eas.fdc.contract.CostPropertyEnum.getEnum(getString("costProperty"));
    }
    public void setCostProperty(com.kingdee.eas.fdc.contract.CostPropertyEnum item)
    {
		if (item != null) {
        setString("costProperty", item.getValue());
		}
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ��ͬ����property 
     */
    public com.kingdee.eas.fdc.contract.ContractPropertyEnum getContractPropert()
    {
        return com.kingdee.eas.fdc.contract.ContractPropertyEnum.getEnum(getString("contractPropert"));
    }
    public void setContractPropert(com.kingdee.eas.fdc.contract.ContractPropertyEnum item)
    {
		if (item != null) {
        setString("contractPropert", item.getValue());
		}
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �γɷ�ʽproperty 
     */
    public com.kingdee.eas.fdc.contract.ContractSourceEnum getContractSource()
    {
        return com.kingdee.eas.fdc.contract.ContractSourceEnum.getEnum(getString("contractSource"));
    }
    public void setContractSource(com.kingdee.eas.fdc.contract.ContractSourceEnum item)
    {
		if (item != null) {
        setString("contractSource", item.getValue());
		}
    }
    /**
     * Object: ��ͬ���ݣ������ࣩ 's �ҷ� property 
     */
    public com.kingdee.eas.basedata.master.cssp.CustomerInfo getPartB()
    {
        return (com.kingdee.eas.basedata.master.cssp.CustomerInfo)get("partB");
    }
    public void setPartB(com.kingdee.eas.basedata.master.cssp.CustomerInfo item)
    {
        put("partB", item);
    }
    /**
     * Object: ��ͬ���ݣ������ࣩ 's ���� property 
     */
    public com.kingdee.eas.basedata.master.cssp.CustomerInfo getPartC()
    {
        return (com.kingdee.eas.basedata.master.cssp.CustomerInfo)get("partC");
    }
    public void setPartC(com.kingdee.eas.basedata.master.cssp.CustomerInfo item)
    {
        put("partC", item);
    }
    /**
     * Object: ��ͬ���ݣ������ࣩ 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �Ƿ�ɱ����property 
     */
    public boolean isIsCoseSplit()
    {
        return getBoolean("isCoseSplit");
    }
    public void setIsCoseSplit(boolean item)
    {
        setBoolean("isCoseSplit", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �Ƿ��ѽ���property 
     */
    public boolean isHasSettled()
    {
        return getBoolean("hasSettled");
    }
    public void setHasSettled(boolean item)
    {
        setBoolean("hasSettled", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ��ͬ�ڹ��̿��ۼ�ʵ����property 
     */
    public java.math.BigDecimal getPrjPriceInConPaid()
    {
        return getBigDecimal("prjPriceInConPaid");
    }
    public void setPrjPriceInConPaid(java.math.BigDecimal item)
    {
        setBigDecimal("prjPriceInConPaid", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ���ӹ��̿��ۼ�ʵ��property 
     */
    public java.math.BigDecimal getAddPrjAmtPaid()
    {
        return getBigDecimal("addPrjAmtPaid");
    }
    public void setAddPrjAmtPaid(java.math.BigDecimal item)
    {
        setBigDecimal("addPrjAmtPaid", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's Ӧ���׹������ۼ�ʵ����property 
     */
    public java.math.BigDecimal getPaidPartAMatlAmt()
    {
        return getBigDecimal("paidPartAMatlAmt");
    }
    public void setPaidPartAMatlAmt(java.math.BigDecimal item)
    {
        setBigDecimal("paidPartAMatlAmt", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �����property 
     */
    public java.math.BigDecimal getSettleAmt()
    {
        return getBigDecimal("settleAmt");
    }
    public void setSettleAmt(java.math.BigDecimal item)
    {
        setBigDecimal("settleAmt", item);
    }
    /**
     * Object: ��ͬ���ݣ������ࣩ 's �ұ� property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getCurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("currency");
    }
    public void setCurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("currency", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ���޽��property 
     */
    public java.math.BigDecimal getGrtAmount()
    {
        return getBigDecimal("grtAmount");
    }
    public void setGrtAmount(java.math.BigDecimal item)
    {
        setBigDecimal("grtAmount", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ���ޱ�λ�ҽ��property 
     */
    public java.math.BigDecimal getGrtLocalAmount()
    {
        return getBigDecimal("grtLocalAmount");
    }
    public void setGrtLocalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("grtLocalAmount", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ����property 
     */
    public java.math.BigDecimal getExRate()
    {
        return getBigDecimal("exRate");
    }
    public void setExRate(java.math.BigDecimal item)
    {
        setBigDecimal("exRate", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ���޽����property 
     */
    public java.math.BigDecimal getGrtRate()
    {
        return getBigDecimal("grtRate");
    }
    public void setGrtRate(java.math.BigDecimal item)
    {
        setBigDecimal("grtRate", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ��עproperty 
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
     * Object:��ͬ���ݣ������ࣩ's ���״̬property 
     */
    public com.kingdee.eas.fdc.basedata.CostSplitStateEnum getSplitState()
    {
        return com.kingdee.eas.fdc.basedata.CostSplitStateEnum.getEnum(getString("splitState"));
    }
    public void setSplitState(com.kingdee.eas.fdc.basedata.CostSplitStateEnum item)
    {
		if (item != null) {
        setString("splitState", item.getValue());
		}
    }
    /**
     * Object: ��ͬ���ݣ������ࣩ 's ���β��� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getRespDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("respDept");
    }
    public void setRespDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("respDept", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �������property 
     */
    public java.math.BigDecimal getPayScale()
    {
        return getBigDecimal("payScale");
    }
    public void setPayScale(java.math.BigDecimal item)
    {
        setBigDecimal("payScale", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �Ƿ񲻼Ƴɱ��Ľ��property 
     */
    public boolean isIsAmtWithoutCost()
    {
        return getBoolean("isAmtWithoutCost");
    }
    public void setIsAmtWithoutCost(boolean item)
    {
        setBoolean("isAmtWithoutCost", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ӡ��˰��property 
     */
    public java.math.BigDecimal getStampTaxRate()
    {
        return getBigDecimal("stampTaxRate");
    }
    public void setStampTaxRate(java.math.BigDecimal item)
    {
        setBigDecimal("stampTaxRate", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ӡ��˰���property 
     */
    public java.math.BigDecimal getStampTaxAmt()
    {
        return getBigDecimal("stampTaxAmt");
    }
    public void setStampTaxAmt(java.math.BigDecimal item)
    {
        setBigDecimal("stampTaxAmt", item);
    }
    /**
     * Object: ��ͬ���ݣ������ࣩ 's ������ property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getRespPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("respPerson");
    }
    public void setRespPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("respPerson", item);
    }
    /**
     * Object: ��ͬ���ݣ������ࣩ 's �������� property 
     */
    public com.kingdee.eas.fdc.basedata.ContractCodingTypeInfo getCodeType()
    {
        return (com.kingdee.eas.fdc.basedata.ContractCodingTypeInfo)get("codeType");
    }
    public void setCodeType(com.kingdee.eas.fdc.basedata.ContractCodingTypeInfo item)
    {
        put("codeType", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ����ͬ����property 
     */
    public String getMainContractNumber()
    {
        return getString("mainContractNumber");
    }
    public void setMainContractNumber(String item)
    {
        setString("mainContractNumber", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �Ƿ��ѹ鵵property 
     */
    public boolean isIsArchived()
    {
        return getBoolean("isArchived");
    }
    public void setIsArchived(boolean item)
    {
        setBoolean("isArchived", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ִ��״̬property 
     */
    public com.kingdee.eas.fdc.contract.ContractExecStateEnum getExecState()
    {
        return com.kingdee.eas.fdc.contract.ContractExecStateEnum.getEnum(getString("execState"));
    }
    public void setExecState(com.kingdee.eas.fdc.contract.ContractExecStateEnum item)
    {
		if (item != null) {
        setString("execState", item.getValue());
		}
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ս�Ժ�������property 
     */
    public com.kingdee.eas.fdc.contract.CoopLevelEnum getCoopLevel()
    {
        return com.kingdee.eas.fdc.contract.CoopLevelEnum.getEnum(getString("coopLevel"));
    }
    public void setCoopLevel(com.kingdee.eas.fdc.contract.CoopLevelEnum item)
    {
		if (item != null) {
        setString("coopLevel", item.getValue());
		}
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �Ƽ۷�ʽproperty 
     */
    public com.kingdee.eas.fdc.contract.PriceTypeEnum getPriceType()
    {
        return com.kingdee.eas.fdc.contract.PriceTypeEnum.getEnum(getString("priceType"));
    }
    public void setPriceType(com.kingdee.eas.fdc.contract.PriceTypeEnum item)
    {
		if (item != null) {
        setString("priceType", item.getValue());
		}
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ������״̬property 
     */
    public com.kingdee.eas.fdc.contract.ConSplitExecStateEnum getConSplitExecState()
    {
        return com.kingdee.eas.fdc.contract.ConSplitExecStateEnum.getEnum(getString("conSplitExecState"));
    }
    public void setConSplitExecState(com.kingdee.eas.fdc.contract.ConSplitExecStateEnum item)
    {
		if (item != null) {
        setString("conSplitExecState", item.getValue());
		}
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �ⲿ���ݺ���property 
     */
    public String getWebSrvNumber()
    {
        return getString("webSrvNumber");
    }
    public void setWebSrvNumber(String item)
    {
        setString("webSrvNumber", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ������Դ��ʽproperty 
     */
    public com.kingdee.eas.fdc.basedata.SourceTypeEnum getSourceType()
    {
        return com.kingdee.eas.fdc.basedata.SourceTypeEnum.getEnum(getInt("sourceType"));
    }
    public void setSourceType(com.kingdee.eas.fdc.basedata.SourceTypeEnum item)
    {
		if (item != null) {
        setInt("sourceType", item.getValue());
		}
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �鵵ǰ����property 
     */
    public String getArchivingNumber()
    {
        return getString("archivingNumber");
    }
    public void setArchivingNumber(String item)
    {
        setString("archivingNumber", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �Ƿ�ײ��Ϻ�ͬproperty 
     */
    public boolean isIsPartAMaterialCon()
    {
        return getBoolean("isPartAMaterialCon");
    }
    public void setIsPartAMaterialCon(boolean item)
    {
        setBoolean("isPartAMaterialCon", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �Ƿ����۲��property 
     */
    public boolean isIsMeasureContract()
    {
        return getBoolean("isMeasureContract");
    }
    public void setIsMeasureContract(boolean item)
    {
        setBoolean("isMeasureContract", item);
    }
    /**
     * Object: ��ͬ���ݣ������ࣩ 's �γɷ�ʽ property 
     */
    public com.kingdee.eas.fdc.basedata.ContractSourceInfo getContractSourceId()
    {
        return (com.kingdee.eas.fdc.basedata.ContractSourceInfo)get("contractSourceId");
    }
    public void setContractSourceId(com.kingdee.eas.fdc.basedata.ContractSourceInfo item)
    {
        put("contractSourceId", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ���Ƴɱ��Ľ��property 
     */
    public java.math.BigDecimal getAmtWithoutCost()
    {
        return getBigDecimal("amtWithoutCost");
    }
    public void setAmtWithoutCost(java.math.BigDecimal item)
    {
        setBigDecimal("amtWithoutCost", item);
    }
    /**
     * Object: ��ͬ���ݣ������ࣩ 's ��ͬ�������� property 
     */
    public com.kingdee.eas.fdc.contract.ContractBaseDataInfo getContractBaseData()
    {
        return (com.kingdee.eas.fdc.contract.ContractBaseDataInfo)get("contractBaseData");
    }
    public void setContractBaseData(com.kingdee.eas.fdc.contract.ContractBaseDataInfo item)
    {
        put("contractBaseData", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ��ͬ����property 
     */
    public String getCodingNumber()
    {
        return getString("codingNumber");
    }
    public void setCodingNumber(String item)
    {
        setString("codingNumber", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ����������ʾ����property 
     */
    public double getOverRate()
    {
        return getDouble("overRate");
    }
    public void setOverRate(double item)
    {
        setDouble("overRate", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �鵵��ͬ���property 
     */
    public String getArchivedNumber()
    {
        return getString("archivedNumber");
    }
    public void setArchivedNumber(String item)
    {
        setString("archivedNumber", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �Ƿ�ս���Ӻ�ͬproperty 
     */
    public boolean isIsSubContract()
    {
        return getBoolean("isSubContract");
    }
    public void setIsSubContract(boolean item)
    {
        setBoolean("isSubContract", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ��Ч��ʼ����property 
     */
    public java.util.Date getEffectiveStartDate()
    {
        return getDate("effectiveStartDate");
    }
    public void setEffectiveStartDate(java.util.Date item)
    {
        setDate("effectiveStartDate", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ��Ч��������property 
     */
    public java.util.Date getEffectiveEndDate()
    {
        return getDate("effectiveEndDate");
    }
    public void setEffectiveEndDate(java.util.Date item)
    {
        setDate("effectiveEndDate", item);
    }
    /**
     * Object: ��ͬ���ݣ������ࣩ 's ս������ͬ property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillReceiveInfo getMainContract()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillReceiveInfo)get("mainContract");
    }
    public void setMainContract(com.kingdee.eas.fdc.contract.ContractBillReceiveInfo item)
    {
        put("mainContract", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ��ͬժҪ��Ϣproperty 
     */
    public String getInformation()
    {
        return getString("information");
    }
    public void setInformation(String item)
    {
        setString("information", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �Ƿ����ʷ��ͬ������ܺ�Լproperty 
     */
    public int getIsRenewRelateProg()
    {
        return getInt("isRenewRelateProg");
    }
    public void setIsRenewRelateProg(int item)
    {
        setInt("isRenewRelateProg", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's Դ��ܺ�ԼIDproperty 
     */
    public String getSrcProID()
    {
        return getString("srcProID");
    }
    public void setSrcProID(String item)
    {
        setString("srcProID", item);
    }
    /**
     * Object: ��ͬ���ݣ������ࣩ 's �Ƶ���ְλ property 
     */
    public com.kingdee.eas.basedata.org.PositionInfo getCreatorPosition()
    {
        return (com.kingdee.eas.basedata.org.PositionInfo)get("creatorPosition");
    }
    public void setCreatorPosition(com.kingdee.eas.basedata.org.PositionInfo item)
    {
        put("creatorPosition", item);
    }
    /**
     * Object: ��ͬ���ݣ������ࣩ 's �Ƶ����� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getCreateDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("createDept");
    }
    public void setCreateDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("createDept", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �Ƿ��׼��ͬproperty 
     */
    public boolean isIsStardContract()
    {
        return getBoolean("isStardContract");
    }
    public void setIsStardContract(boolean item)
    {
        setBoolean("isStardContract", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �Ƿ�տں�ͬproperty 
     */
    public boolean isIsOpenContract()
    {
        return getBoolean("isOpenContract");
    }
    public void setIsOpenContract(boolean item)
    {
        setBoolean("isOpenContract", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ��ͬ��������property 
     */
    public com.kingdee.eas.fdc.contract.ContractSettleTypeEnum getContractSettleType()
    {
        return com.kingdee.eas.fdc.contract.ContractSettleTypeEnum.getEnum(getString("contractSettleType"));
    }
    public void setContractSettleType(com.kingdee.eas.fdc.contract.ContractSettleTypeEnum item)
    {
		if (item != null) {
        setString("contractSettleType", item.getValue());
		}
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ��ͬ����property 
     */
    public String getContractModel()
    {
        return getString("contractModel");
    }
    public void setContractModel(String item)
    {
        setString("contractModel", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ����Э�����ҵ��IDproperty 
     */
    public String getAgreementID()
    {
        return getString("agreementID");
    }
    public void setAgreementID(String item)
    {
        setString("agreementID", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �������̷�����֯property 
     */
    public com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum getOrgType()
    {
        return com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum.getEnum(getString("orgType"));
    }
    public void setOrgType(com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum item)
    {
		if (item != null) {
        setString("orgType", item.getValue());
		}
    }
    /**
     * Object: ��ͬ���ݣ������ࣩ 's ��ͬ�������� property 
     */
    public com.kingdee.eas.fdc.contract.ContractWFTypeInfo getContractWFType()
    {
        return (com.kingdee.eas.fdc.contract.ContractWFTypeInfo)get("contractWFType");
    }
    public void setContractWFType(com.kingdee.eas.fdc.contract.ContractWFTypeInfo item)
    {
        put("contractWFType", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ԭ����ͬ���property 
     */
    public java.math.BigDecimal getSrcAmount()
    {
        return getBigDecimal("srcAmount");
    }
    public void setSrcAmount(java.math.BigDecimal item)
    {
        setBigDecimal("srcAmount", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �Ƿ�¼�����ָ���property 
     */
    public boolean isIsHasCostIndex()
    {
        return getBoolean("isHasCostIndex");
    }
    public void setIsHasCostIndex(boolean item)
    {
        setBoolean("isHasCostIndex", item);
    }
    /**
     * Object: ��ͬ���ݣ������ࣩ 's ������ property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getNeedDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("needDept");
    }
    public void setNeedDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("needDept", item);
    }
    /**
     * Object: ��ͬ���ݣ������ࣩ 's ������ property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getNeedPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("needPerson");
    }
    public void setNeedPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("needPerson", item);
    }
    /**
     * Object: ��ͬ���ݣ������ࣩ 's ��ͬǩ����ϸ property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillReceiveRateEntryCollection getRateEntry()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillReceiveRateEntryCollection)get("rateEntry");
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �����˺�property 
     */
    public String getBankAccount()
    {
        return getString("bankAccount");
    }
    public void setBankAccount(String item)
    {
        setString("bankAccount", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ��˰������property 
     */
    public com.kingdee.eas.fdc.contract.app.TaxerQuaEnum getTaxerQua()
    {
        return com.kingdee.eas.fdc.contract.app.TaxerQuaEnum.getEnum(getString("taxerQua"));
    }
    public void setTaxerQua(com.kingdee.eas.fdc.contract.app.TaxerQuaEnum item)
    {
		if (item != null) {
        setString("taxerQua", item.getValue());
		}
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ��˰��ʶ���property 
     */
    public String getTaxerNum()
    {
        return getString("taxerNum");
    }
    public void setTaxerNum(String item)
    {
        setString("taxerNum", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ��������property 
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
     * Object:��ͬ���ݣ������ࣩ's ��ִͬ����ֹʱ��property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ��ִͬ�н���ʱ��property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object: ��ͬ���ݣ������ࣩ 's ���к� property 
     */
    public com.kingdee.eas.fdc.contract.BankNumInfo getLxNum()
    {
        return (com.kingdee.eas.fdc.contract.BankNumInfo)get("lxNum");
    }
    public void setLxNum(com.kingdee.eas.fdc.contract.BankNumInfo item)
    {
        put("lxNum", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ִ������Ƿ���Ҫ���ź�����property 
     */
    public boolean isIsJT()
    {
        return getBoolean("isJT");
    }
    public void setIsJT(boolean item)
    {
        setBoolean("isJT", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's oaְλproperty 
     */
    public String getOaPosition()
    {
        return getString("oaPosition");
    }
    public void setOaPosition(String item)
    {
        setString("oaPosition", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �������property 
     */
    public String getOaOpinion()
    {
        return getString("oaOpinion");
    }
    public void setOaOpinion(String item)
    {
        setString("oaOpinion", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ����״̬property 
     */
    public String getOaState()
    {
        return getString("oaState");
    }
    public void setOaState(String item)
    {
        setString("oaState", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ��������property 
     */
    public com.kingdee.eas.fdc.contract.JZTypeEnum getJzType()
    {
        return com.kingdee.eas.fdc.contract.JZTypeEnum.getEnum(getString("jzType"));
    }
    public void setJzType(com.kingdee.eas.fdc.contract.JZTypeEnum item)
    {
		if (item != null) {
        setString("jzType", item.getValue());
		}
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ��ʼʱ��property 
     */
    public java.util.Date getJzStartDate()
    {
        return getDate("jzStartDate");
    }
    public void setJzStartDate(java.util.Date item)
    {
        setDate("jzStartDate", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ����ʱ��property 
     */
    public java.util.Date getJzEndDate()
    {
        return getDate("jzEndDate");
    }
    public void setJzEndDate(java.util.Date item)
    {
        setDate("jzEndDate", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �Ƿ�ʱproperty 
     */
    public String getIsTimeOut()
    {
        return getString("isTimeOut");
    }
    public void setIsTimeOut(String item)
    {
        setString("isTimeOut", item);
    }
    /**
     * Object: ��ͬ���ݣ������ࣩ 's ������ͬ property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContractBill()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contractBill");
    }
    public void setContractBill(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contractBill", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ��˾��property 
     */
    public String getCompanyNameA()
    {
        return getString("companyNameA");
    }
    public void setCompanyNameA(String item)
    {
        setString("companyNameA", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ��˾��property 
     */
    public String getCompanyNameB()
    {
        return getString("companyNameB");
    }
    public void setCompanyNameB(String item)
    {
        setString("companyNameB", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �˺�property 
     */
    public String getAccountA()
    {
        return getString("accountA");
    }
    public void setAccountA(String item)
    {
        setString("accountA", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �˺�property 
     */
    public String getAccountB()
    {
        return getString("accountB");
    }
    public void setAccountB(String item)
    {
        setString("accountB", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ������property 
     */
    public String getBankA()
    {
        return getString("bankA");
    }
    public void setBankA(String item)
    {
        setString("bankA", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ������property 
     */
    public String getBankB()
    {
        return getString("bankB");
    }
    public void setBankB(String item)
    {
        setString("bankB", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ��Ʊ��˾��property 
     */
    public String getKpCompanyNameA()
    {
        return getString("kpCompanyNameA");
    }
    public void setKpCompanyNameA(String item)
    {
        setString("kpCompanyNameA", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ��Ʊ��˾��property 
     */
    public String getKpCompanyNameB()
    {
        return getString("kpCompanyNameB");
    }
    public void setKpCompanyNameB(String item)
    {
        setString("kpCompanyNameB", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ˰��property 
     */
    public String getTaxNumA()
    {
        return getString("taxNumA");
    }
    public void setTaxNumA(String item)
    {
        setString("taxNumA", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ˰��property 
     */
    public String getTaxNumB()
    {
        return getString("taxNumB");
    }
    public void setTaxNumB(String item)
    {
        setString("taxNumB", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ��ַproperty 
     */
    public String getAddressA()
    {
        return getString("addressA");
    }
    public void setAddressA(String item)
    {
        setString("addressA", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ��ַproperty 
     */
    public String getAddressB()
    {
        return getString("addressB");
    }
    public void setAddressB(String item)
    {
        setString("addressB", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �绰property 
     */
    public String getTelA()
    {
        return getString("telA");
    }
    public void setTelA(String item)
    {
        setString("telA", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �绰property 
     */
    public String getTelB()
    {
        return getString("telB");
    }
    public void setTelB(String item)
    {
        setString("telB", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ����property 
     */
    public String getKpBankA()
    {
        return getString("kpBankA");
    }
    public void setKpBankA(String item)
    {
        setString("kpBankA", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ����property 
     */
    public String getKpBankB()
    {
        return getString("kpBankB");
    }
    public void setKpBankB(String item)
    {
        setString("kpBankB", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �˺�property 
     */
    public String getKpAccountA()
    {
        return getString("kpAccountA");
    }
    public void setKpAccountA(String item)
    {
        setString("kpAccountA", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �˺�property 
     */
    public String getKpAccountB()
    {
        return getString("kpAccountB");
    }
    public void setKpAccountB(String item)
    {
        setString("kpAccountB", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ����֧������ͬ��Ҫ����property 
     */
    public String getHttk()
    {
        return getString("httk");
    }
    public void setHttk(String item)
    {
        setString("httk", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ��˾��property 
     */
    public String getCompanyNameC()
    {
        return getString("companyNameC");
    }
    public void setCompanyNameC(String item)
    {
        setString("companyNameC", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �˺�property 
     */
    public String getAccountC()
    {
        return getString("accountC");
    }
    public void setAccountC(String item)
    {
        setString("accountC", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ������property 
     */
    public String getBankC()
    {
        return getString("bankC");
    }
    public void setBankC(String item)
    {
        setString("bankC", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ��Ʊ��˾��property 
     */
    public String getKpCompanyNameC()
    {
        return getString("kpCompanyNameC");
    }
    public void setKpCompanyNameC(String item)
    {
        setString("kpCompanyNameC", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ˰��property 
     */
    public String getTaxNumC()
    {
        return getString("taxNumC");
    }
    public void setTaxNumC(String item)
    {
        setString("taxNumC", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ��ַproperty 
     */
    public String getAddressC()
    {
        return getString("addressC");
    }
    public void setAddressC(String item)
    {
        setString("addressC", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �绰property 
     */
    public String getTelC()
    {
        return getString("telC");
    }
    public void setTelC(String item)
    {
        setString("telC", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's ����property 
     */
    public String getKpBankC()
    {
        return getString("kpBankC");
    }
    public void setKpBankC(String item)
    {
        setString("kpBankC", item);
    }
    /**
     * Object:��ͬ���ݣ������ࣩ's �˺�property 
     */
    public String getKpAccountC()
    {
        return getString("kpAccountC");
    }
    public void setKpAccountC(String item)
    {
        setString("kpAccountC", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E9EEDE4F");
    }
}