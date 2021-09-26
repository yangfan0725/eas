package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractContractBillInfo()
    {
        this("id");
    }
    protected AbstractContractBillInfo(String pkField)
    {
        super(pkField);
        put("yzEntry", new com.kingdee.eas.fdc.contract.ContractYZEntryCollection());
        put("otherLandDevelpers", new com.kingdee.eas.fdc.contract.ContractLandDeveloperCollection());
        put("rateEntry", new com.kingdee.eas.fdc.contract.ContractBillRateEntryCollection());
        put("contractPlan", new com.kingdee.eas.fdc.contract.ContractBillContractPlanCollection());
        put("entrys", new com.kingdee.eas.fdc.contract.ContractBillEntryCollection());
        put("marketEntry", new com.kingdee.eas.fdc.contract.ContractMarketEntryCollection());
        put("payItems", new com.kingdee.eas.fdc.contract.ContractPayItemCollection());
    }
    /**
     * Object: ��ͬ���� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillEntryCollection)get("entrys");
    }
    /**
     * Object:��ͬ����'s �����ʾ����property 
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
     * Object:��ͬ����'s ������ʾ����property 
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
     * Object:��ͬ����'s ǩԼ����property 
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
     * Object:��ͬ����'s ���Ͷ���property 
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
     * Object:��ͬ����'s �ε�Ͷ���property 
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
     * Object:��ͬ����'s �м�Ͷ���property 
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
     * Object:��ͬ����'s �θ�Ͷ���property 
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
     * Object:��ͬ����'s ���Ͷ���property 
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
     * Object:��ͬ����'s �б��property 
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
     * Object:��ͬ����'s ��λ����property 
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
     * Object:��ͬ����'s �б��ļ���property 
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
     * Object:��ͬ����'s �׼�property 
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
     * Object:��ͬ����'s �����property 
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
     * Object: ��ͬ���� 's �׷� property 
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
     * Object: ��ͬ���� 's �����׷� property 
     */
    public com.kingdee.eas.fdc.contract.ContractLandDeveloperCollection getOtherLandDevelpers()
    {
        return (com.kingdee.eas.fdc.contract.ContractLandDeveloperCollection)get("otherLandDevelpers");
    }
    /**
     * Object: ��ͬ���� 's ��ͬ���� property 
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
     * Object: ��ͬ���� 's �б����� property 
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
     * Object: ��ͬ���� 's ���Ͷ��۵�λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getLowestPriceUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("lowestPriceUnit");
    }
    public void setLowestPriceUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("lowestPriceUnit", item);
    }
    /**
     * Object: ��ͬ���� 's �м�Ͷ��۵�λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getMiddlePriceUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("middlePriceUnit");
    }
    public void setMiddlePriceUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("middlePriceUnit", item);
    }
    /**
     * Object: ��ͬ���� 's �ε�Ͷ��۵�λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getLowerPriceUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("lowerPriceUnit");
    }
    public void setLowerPriceUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("lowerPriceUnit", item);
    }
    /**
     * Object: ��ͬ���� 's �θ�Ͷ��۵�λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getHigherPriceUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("higherPriceUnit");
    }
    public void setHigherPriceUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("higherPriceUnit", item);
    }
    /**
     * Object: ��ͬ���� 's ���Ͷ��۵�λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getHighestPriceUni()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("highestPriceUni");
    }
    public void setHighestPriceUni(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("highestPriceUni", item);
    }
    /**
     * Object: ��ͬ���� 's �б굥λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getWinUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("winUnit");
    }
    public void setWinUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("winUnit", item);
    }
    /**
     * Object:��ͬ����'s �������property 
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
     * Object:��ͬ����'s ��ͬ����property 
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
     * Object:��ͬ����'s �γɷ�ʽproperty 
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
     * Object: ��ͬ���� 's �ҷ� property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getPartB()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("partB");
    }
    public void setPartB(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("partB", item);
    }
    /**
     * Object: ��ͬ���� 's ���� property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getPartC()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("partC");
    }
    public void setPartC(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("partC", item);
    }
    /**
     * Object: ��ͬ���� 's ��ͬ�ƻ� property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillContractPlanCollection getContractPlan()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillContractPlanCollection)get("contractPlan");
    }
    /**
     * Object: ��ͬ���� 's ������Ŀ property 
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
     * Object:��ͬ����'s �Ƿ�ɱ����property 
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
     * Object:��ͬ����'s �Ƿ��ѽ���property 
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
     * Object:��ͬ����'s ��ͬ�ڹ��̿��ۼ�ʵ����property 
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
     * Object:��ͬ����'s ���ӹ��̿��ۼ�ʵ��property 
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
     * Object:��ͬ����'s Ӧ���׹������ۼ�ʵ����property 
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
     * Object:��ͬ����'s �����property 
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
     * Object: ��ͬ���� 's �ұ� property 
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
     * Object:��ͬ����'s ���޽��property 
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
     * Object:��ͬ����'s ���ޱ�λ�ҽ��property 
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
     * Object:��ͬ����'s ����property 
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
     * Object:��ͬ����'s ���޽����property 
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
     * Object:��ͬ����'s ��עproperty 
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
     * Object:��ͬ����'s ���״̬property 
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
     * Object: ��ͬ���� 's ���β��� property 
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
     * Object:��ͬ����'s �������property 
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
     * Object:��ͬ����'s �Ƿ񲻼Ƴɱ��Ľ��property 
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
     * Object:��ͬ����'s ӡ��˰��property 
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
     * Object:��ͬ����'s ӡ��˰���property 
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
     * Object: ��ͬ���� 's ������ property 
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
     * Object: ��ͬ���� 's �������� property 
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
     * Object:��ͬ����'s ����ͬ����property 
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
     * Object:��ͬ����'s �Ƿ��ѹ鵵property 
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
     * Object:��ͬ����'s ִ��״̬property 
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
     * Object:��ͬ����'s ս�Ժ�������property 
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
     * Object:��ͬ����'s �Ƽ۷�ʽproperty 
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
     * Object:��ͬ����'s ������״̬property 
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
     * Object:��ͬ����'s �ⲿ���ݺ���property 
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
     * Object:��ͬ����'s ������Դ��ʽproperty 
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
     * Object:��ͬ����'s �鵵ǰ����property 
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
     * Object:��ͬ����'s �Ƿ�ײ��Ϻ�ͬproperty 
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
     * Object: ��ͬ���� 's ��ͬ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.ContractChargeTypeInfo getConChargeType()
    {
        return (com.kingdee.eas.fdc.basedata.ContractChargeTypeInfo)get("conChargeType");
    }
    public void setConChargeType(com.kingdee.eas.fdc.basedata.ContractChargeTypeInfo item)
    {
        put("conChargeType", item);
    }
    /**
     * Object:��ͬ����'s �Ƿ����۲��property 
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
     * Object: ��ͬ���� 's �γɷ�ʽ property 
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
     * Object:��ͬ����'s ���Ƴɱ��Ľ��property 
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
     * Object: ��ͬ���� 's ��ͬ�������� property 
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
     * Object:��ͬ����'s ��ͬ����property 
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
     * Object: ��ͬ���� 's �������� property 
     */
    public com.kingdee.eas.fdc.contract.ContractPayItemCollection getPayItems()
    {
        return (com.kingdee.eas.fdc.contract.ContractPayItemCollection)get("payItems");
    }
    /**
     * Object: ��ͬ���� 's ��Լ��֤�𼰷������� property 
     */
    public com.kingdee.eas.fdc.contract.ContractBailInfo getBail()
    {
        return (com.kingdee.eas.fdc.contract.ContractBailInfo)get("bail");
    }
    public void setBail(com.kingdee.eas.fdc.contract.ContractBailInfo item)
    {
        put("bail", item);
    }
    /**
     * Object:��ͬ����'s ����������ʾ����property 
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
     * Object:��ͬ����'s �鵵��ͬ���property 
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
     * Object:��ͬ����'s �Ƿ�ս���Ӻ�ͬproperty 
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
     * Object:��ͬ����'s ��Ч��ʼ����property 
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
     * Object:��ͬ����'s ��Ч��������property 
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
     * Object: ��ͬ���� 's ս������ͬ property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getMainContract()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("mainContract");
    }
    public void setMainContract(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("mainContract", item);
    }
    /**
     * Object:��ͬ����'s ��ͬժҪ��Ϣproperty 
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
     * Object: ��ͬ���� 's ��Լ�滮 property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo getProgrammingContract()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo)get("programmingContract");
    }
    public void setProgrammingContract(com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo item)
    {
        put("programmingContract", item);
    }
    /**
     * Object:��ͬ����'s �Ƿ����ʷ��ͬ������ܺ�Լproperty 
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
     * Object:��ͬ����'s Դ��ܺ�ԼIDproperty 
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
     * Object: ��ͬ���� 's ��ͬ���� property 
     */
    public com.kingdee.eas.base.attachment.AttachmentInfo getModel()
    {
        return (com.kingdee.eas.base.attachment.AttachmentInfo)get("model");
    }
    public void setModel(com.kingdee.eas.base.attachment.AttachmentInfo item)
    {
        put("model", item);
    }
    /**
     * Object: ��ͬ���� 's �Ƶ���ְλ property 
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
     * Object: ��ͬ���� 's �Ƶ����� property 
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
     * Object:��ͬ����'s �Ƿ��׼��ͬproperty 
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
     * Object:��ͬ����'s �Ƿ�տں�ͬproperty 
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
     * Object:��ͬ����'s ��ͬ��������property 
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
     * Object:��ͬ����'s ��ͬ����property 
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
     * Object:��ͬ����'s ����Э�����ҵ��IDproperty 
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
     * Object:��ͬ����'s �������̷�����֯property 
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
     * Object: ��ͬ���� 's ��ͬ�������� property 
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
     * Object:��ͬ����'s ԭ����ͬ���property 
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
     * Object:��ͬ����'s �Ƿ�¼�����ָ���property 
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
     * Object: ��ͬ���� 's ������ property 
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
     * Object: ��ͬ���� 's ������ property 
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
     * Object: ��ͬ���� 's ��ͬǩ����ϸ property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillRateEntryCollection getRateEntry()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillRateEntryCollection)get("rateEntry");
    }
    /**
     * Object:��ͬ����'s �����˺�property 
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
     * Object:��ͬ����'s ��˰������property 
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
     * Object:��ͬ����'s ��˰��ʶ���property 
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
     * Object:��ͬ����'s ��������property 
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
     * Object:��ͬ����'s ��ִͬ����ֹʱ��property 
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
     * Object:��ͬ����'s ��ִͬ�н���ʱ��property 
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
     * Object: ��ͬ���� 's �б����� property 
     */
    public com.kingdee.eas.fdc.invite.TenderAccepterResultEntryInfo getTaEntry()
    {
        return (com.kingdee.eas.fdc.invite.TenderAccepterResultEntryInfo)get("taEntry");
    }
    public void setTaEntry(com.kingdee.eas.fdc.invite.TenderAccepterResultEntryInfo item)
    {
        put("taEntry", item);
    }
    /**
     * Object: ��ͬ���� 's Ӫ������ property 
     */
    public com.kingdee.eas.fdc.contract.MarketProjectInfo getMarketProject()
    {
        return (com.kingdee.eas.fdc.contract.MarketProjectInfo)get("marketProject");
    }
    public void setMarketProject(com.kingdee.eas.fdc.contract.MarketProjectInfo item)
    {
        put("marketProject", item);
    }
    /**
     * Object: ��ͬ���� 's ��ͬ�������ϸ property 
     */
    public com.kingdee.eas.fdc.contract.ContractMarketEntryCollection getMarketEntry()
    {
        return (com.kingdee.eas.fdc.contract.ContractMarketEntryCollection)get("marketEntry");
    }
    /**
     * Object: ��ͬ���� 's �ɱ���Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getMpCostAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("mpCostAccount");
    }
    public void setMpCostAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("mpCostAccount", item);
    }
    /**
     * Object: ��ͬ���� 's ���к� property 
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
     * Object:��ͬ����'s ִ������Ƿ���Ҫ���ź�����property 
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
     * Object:��ͬ����'s oaְλproperty 
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
     * Object:��ͬ����'s �������property 
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
     * Object: ��ͬ���� 's ӡ�·�¼ property 
     */
    public com.kingdee.eas.fdc.contract.ContractYZEntryCollection getYzEntry()
    {
        return (com.kingdee.eas.fdc.contract.ContractYZEntryCollection)get("yzEntry");
    }
    /**
     * Object:��ͬ����'s ����״̬property 
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
     * Object:��ͬ����'s ��������property 
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
     * Object:��ͬ����'s ��ʼʱ��property 
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
     * Object:��ͬ����'s ����ʱ��property 
     */
    public java.util.Date getJzEndDate()
    {
        return getDate("jzEndDate");
    }
    public void setJzEndDate(java.util.Date item)
    {
        setDate("jzEndDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0D6DD1F4");
    }
}