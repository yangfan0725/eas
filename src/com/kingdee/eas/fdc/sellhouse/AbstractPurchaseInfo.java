package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPurchaseInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractPurchaseInfo()
    {
        this("id");
    }
    protected AbstractPurchaseInfo(String pkField)
    {
        super(pkField);
        put("attachmentEntries", new com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryCollection());
        put("payListEntry", new com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection());
        put("distillCommisionEntry", new com.kingdee.eas.fdc.sellhouse.PurDistillCommisionEntryCollection());
        put("agioEntrys", new com.kingdee.eas.fdc.sellhouse.PurchaseAgioEntryCollection());
        put("purCustomer", new com.kingdee.eas.fdc.sellhouse.PurCustomerCollection());
        put("customerInfo", new com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection());
        put("secondSaleMan", new com.kingdee.eas.fdc.sellhouse.PurchaseSaleManCollection());
        put("elsePayListEntry", new com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryCollection());
    }
    /**
     * Object:�Ϲ�'s Ԥ������property 
     */
    public java.util.Date getPrePurchaseDate()
    {
        return getDate("prePurchaseDate");
    }
    public void setPrePurchaseDate(java.util.Date item)
    {
        setDate("prePurchaseDate", item);
    }
    /**
     * Object:�Ϲ�'s ��Ч����property 
     */
    public java.util.Date getValaidDate()
    {
        return getDate("valaidDate");
    }
    public void setValaidDate(java.util.Date item)
    {
        setDate("valaidDate", item);
    }
    /**
     * Object:�Ϲ�'s Ԥ����ͽ��property 
     */
    public java.math.BigDecimal getPrePurLevelAmount()
    {
        return getBigDecimal("prePurLevelAmount");
    }
    public void setPrePurLevelAmount(java.math.BigDecimal item)
    {
        setBigDecimal("prePurLevelAmount", item);
    }
    /**
     * Object:�Ϲ�'s Ԥ�����property 
     */
    public java.math.BigDecimal getPrePurchaseAmount()
    {
        return getBigDecimal("prePurchaseAmount");
    }
    public void setPrePurchaseAmount(java.math.BigDecimal item)
    {
        setBigDecimal("prePurchaseAmount", item);
    }
    /**
     * Object: �Ϲ� 's Ԥ���ұ� property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getPrePurchaseCurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("prePurchaseCurrency");
    }
    public void setPrePurchaseCurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("prePurchaseCurrency", item);
    }
    /**
     * Object: �Ϲ� 's �����Ϲ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo getSincerityPurchase()
    {
        return (com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo)get("sincerityPurchase");
    }
    public void setSincerityPurchase(com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo item)
    {
        put("sincerityPurchase", item);
    }
    /**
     * Object:�Ϲ�'s �Ϲ�����property 
     */
    public java.util.Date getPurchaseDate()
    {
        return getDate("purchaseDate");
    }
    public void setPurchaseDate(java.util.Date item)
    {
        setDate("purchaseDate", item);
    }
    /**
     * Object:�Ϲ�'s �Ϲ�֤��property 
     */
    public String getPurchaseCardNumber()
    {
        return getString("purchaseCardNumber");
    }
    public void setPurchaseCardNumber(String item)
    {
        setString("purchaseCardNumber", item);
    }
    /**
     * Object: �Ϲ� 's �Ϲ��ұ� property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getPurchaseCurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("purchaseCurrency");
    }
    public void setPurchaseCurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("purchaseCurrency", item);
    }
    /**
     * Object:�Ϲ�'s �Ϲ����property 
     */
    public java.math.BigDecimal getPurchaseAmount()
    {
        return getBigDecimal("purchaseAmount");
    }
    public void setPurchaseAmount(java.math.BigDecimal item)
    {
        setBigDecimal("purchaseAmount", item);
    }
    /**
     * Object: �Ϲ� 's ����Ա property 
     */
    public com.kingdee.eas.base.permission.UserInfo getSalesman()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("salesman");
    }
    public void setSalesman(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("salesman", item);
    }
    /**
     * Object:�Ϲ�'s װ�ޱ�׼property 
     */
    public String getFitmentStandard()
    {
        return getString("fitmentStandard");
    }
    public void setFitmentStandard(String item)
    {
        setString("fitmentStandard", item);
    }
    /**
     * Object:�Ϲ�'s װ�޵���property 
     */
    public java.math.BigDecimal getFitmentPrice()
    {
        return getBigDecimal("fitmentPrice");
    }
    public void setFitmentPrice(java.math.BigDecimal item)
    {
        setBigDecimal("fitmentPrice", item);
    }
    /**
     * Object:�Ϲ�'s װ���ܶ�property 
     */
    public java.math.BigDecimal getFitmentAmount()
    {
        return getBigDecimal("fitmentAmount");
    }
    public void setFitmentAmount(java.math.BigDecimal item)
    {
        setBigDecimal("fitmentAmount", item);
    }
    /**
     * Object:�Ϲ�'s �Ƿ�װ�޼۸����ͬproperty 
     */
    public boolean isIsFitmentToContract()
    {
        return getBoolean("isFitmentToContract");
    }
    public void setIsFitmentToContract(boolean item)
    {
        setBoolean("isFitmentToContract", item);
    }
    /**
     * Object: �Ϲ� 's ����� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo getPayType()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo)get("payType");
    }
    public void setPayType(com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo item)
    {
        put("payType", item);
    }
    /**
     * Object:�Ϲ�'s �����׼property 
     */
    public java.math.BigDecimal getEarnestBase()
    {
        return getBigDecimal("earnestBase");
    }
    public void setEarnestBase(java.math.BigDecimal item)
    {
        setBigDecimal("earnestBase", item);
    }
    /**
     * Object:�Ϲ�'s ������property 
     */
    public java.math.BigDecimal getAttachmentAmount()
    {
        return getBigDecimal("attachmentAmount");
    }
    public void setAttachmentAmount(java.math.BigDecimal item)
    {
        setBigDecimal("attachmentAmount", item);
    }
    /**
     * Object: �Ϲ� 's �ɽ��ұ� property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getDealCurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("dealCurrency");
    }
    public void setDealCurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("dealCurrency", item);
    }
    /**
     * Object:�Ϲ�'s �ɽ�����property 
     */
    public java.math.BigDecimal getDealPrice()
    {
        return getBigDecimal("dealPrice");
    }
    public void setDealPrice(java.math.BigDecimal item)
    {
        setBigDecimal("dealPrice", item);
    }
    /**
     * Object:�Ϲ�'s �ɽ����property 
     */
    public java.math.BigDecimal getDealAmount()
    {
        return getBigDecimal("dealAmount");
    }
    public void setDealAmount(java.math.BigDecimal item)
    {
        setBigDecimal("dealAmount", item);
    }
    /**
     * Object:�Ϲ�'s ���Ҵ�����property 
     */
    public java.math.BigDecimal getLoanAmount()
    {
        return getBigDecimal("loanAmount");
    }
    public void setLoanAmount(java.math.BigDecimal item)
    {
        setBigDecimal("loanAmount", item);
    }
    /**
     * Object:�Ϲ�'s ��������property 
     */
    public int getLoanYearCount()
    {
        return getInt("loanYearCount");
    }
    public void setLoanYearCount(int item)
    {
        setInt("loanYearCount", item);
    }
    /**
     * Object:�Ϲ�'s ��������property 
     */
    public java.math.BigDecimal getLoanInterestRate()
    {
        return getBigDecimal("loanInterestRate");
    }
    public void setLoanInterestRate(java.math.BigDecimal item)
    {
        setBigDecimal("loanInterestRate", item);
    }
    /**
     * Object:�Ϲ�'s �������property 
     */
    public java.util.Date getLoanExtendDate()
    {
        return getDate("loanExtendDate");
    }
    public void setLoanExtendDate(java.util.Date item)
    {
        setDate("loanExtendDate", item);
    }
    /**
     * Object:�Ϲ�'s ��������property 
     */
    public java.math.BigDecimal getAccumulationFundAmount()
    {
        return getBigDecimal("accumulationFundAmount");
    }
    public void setAccumulationFundAmount(java.math.BigDecimal item)
    {
        setBigDecimal("accumulationFundAmount", item);
    }
    /**
     * Object:�Ϲ�'s ����������property 
     */
    public int getAccumulationFundYearCout()
    {
        return getInt("accumulationFundYearCout");
    }
    public void setAccumulationFundYearCout(int item)
    {
        setInt("accumulationFundYearCout", item);
    }
    /**
     * Object:�Ϲ�'s ����������property 
     */
    public java.math.BigDecimal getAccFundExInterestRate()
    {
        return getBigDecimal("accFundExInterestRate");
    }
    public void setAccFundExInterestRate(java.math.BigDecimal item)
    {
        setBigDecimal("accFundExInterestRate", item);
    }
    /**
     * Object:�Ϲ�'s �����𷢷���property 
     */
    public java.util.Date getAccumulationFundExtendDate()
    {
        return getDate("accumulationFundExtendDate");
    }
    public void setAccumulationFundExtendDate(java.util.Date item)
    {
        setDate("accumulationFundExtendDate", item);
    }
    /**
     * Object: �Ϲ� 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("room");
    }
    public void setRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("room", item);
    }
    /**
     * Object: �Ϲ� 's �ۿ۷�¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseAgioEntryCollection getAgioEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseAgioEntryCollection)get("agioEntrys");
    }
    /**
     * Object: �Ϲ� 's �ͻ���Ϣ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection getCustomerInfo()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection)get("customerInfo");
    }
    /**
     * Object:�Ϲ�'s ��������property 
     */
    public boolean isIsSellBySet()
    {
        return getBoolean("isSellBySet");
    }
    public void setIsSellBySet(boolean item)
    {
        setBoolean("isSellBySet", item);
    }
    /**
     * Object: �Ϲ� 's ������ϸ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection getPayListEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection)get("payListEntry");
    }
    /**
     * Object:�Ϲ�'s �ͻ�����property 
     */
    public String getCustomerNames()
    {
        return getString("customerNames");
    }
    public void setCustomerNames(String item)
    {
        setString("customerNames", item);
    }
    /**
     * Object:�Ϲ�'s �ͻ��绰����property 
     */
    public String getCustomerPhones()
    {
        return getString("customerPhones");
    }
    public void setCustomerPhones(String item)
    {
        setString("customerPhones", item);
    }
    /**
     * Object:�Ϲ�'s �ͻ����֤��property 
     */
    public String getCustomerIDCards()
    {
        return getString("customerIDCards");
    }
    public void setCustomerIDCards(String item)
    {
        setString("customerIDCards", item);
    }
    /**
     * Object:�Ϲ�'s �����property 
     */
    public java.math.BigDecimal getAreaCompensateAmount()
    {
        return getBigDecimal("areaCompensateAmount");
    }
    public void setAreaCompensateAmount(java.math.BigDecimal item)
    {
        setBigDecimal("areaCompensateAmount", item);
    }
    /**
     * Object: �Ϲ� 's �Ϲ��������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryCollection getAttachmentEntries()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryCollection)get("attachmentEntries");
    }
    /**
     * Object:�Ϲ�'s �Ϲ���״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum getPurchaseState()
    {
        return com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum.getEnum(getString("purchaseState"));
    }
    public void setPurchaseState(com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum item)
    {
		if (item != null) {
        setString("purchaseState", item.getValue());
		}
    }
    /**
     * Object: �Ϲ� 's ���������� property 
     */
    public com.kingdee.eas.base.permission.UserInfo getPrePurchaseAuditor()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("prePurchaseAuditor");
    }
    public void setPrePurchaseAuditor(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("prePurchaseAuditor", item);
    }
    /**
     * Object:�Ϲ�'s Ԥ������ʱ��property 
     */
    public java.util.Date getPrePurchaseAuditDate()
    {
        return getDate("prePurchaseAuditDate");
    }
    public void setPrePurchaseAuditDate(java.util.Date item)
    {
        setDate("prePurchaseAuditDate", item);
    }
    /**
     * Object:�Ϲ�'s Ԥ����Ч��property 
     */
    public java.util.Date getPrePurchaseValidDate()
    {
        return getDate("prePurchaseValidDate");
    }
    public void setPrePurchaseValidDate(java.util.Date item)
    {
        setDate("prePurchaseValidDate", item);
    }
    /**
     * Object:�Ϲ�'s ���۷�ʽproperty 
     */
    public com.kingdee.eas.fdc.sellhouse.SellTypeEnum getSellType()
    {
        return com.kingdee.eas.fdc.sellhouse.SellTypeEnum.getEnum(getString("sellType"));
    }
    public void setSellType(com.kingdee.eas.fdc.sellhouse.SellTypeEnum item)
    {
		if (item != null) {
        setString("sellType", item.getValue());
		}
    }
    /**
     * Object:�Ϲ�'s �����ۿ�����property 
     */
    public com.kingdee.eas.fdc.sellhouse.SpecialAgioEnum getSpecialAgioType()
    {
        return com.kingdee.eas.fdc.sellhouse.SpecialAgioEnum.getEnum(getString("specialAgioType"));
    }
    public void setSpecialAgioType(com.kingdee.eas.fdc.sellhouse.SpecialAgioEnum item)
    {
		if (item != null) {
        setString("specialAgioType", item.getValue());
		}
    }
    /**
     * Object:�Ϲ�'s �����ۿ�property 
     */
    public java.math.BigDecimal getSpecialAgio()
    {
        return getBigDecimal("specialAgio");
    }
    public void setSpecialAgio(java.math.BigDecimal item)
    {
        setBigDecimal("specialAgio", item);
    }
    /**
     * Object:�Ϲ�'s ��ͬ�ܼ�property 
     */
    public java.math.BigDecimal getContractTotalAmount()
    {
        return getBigDecimal("contractTotalAmount");
    }
    public void setContractTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("contractTotalAmount", item);
    }
    /**
     * Object:�Ϲ�'s ��ͬ��������property 
     */
    public java.math.BigDecimal getContractBuildPrice()
    {
        return getBigDecimal("contractBuildPrice");
    }
    public void setContractBuildPrice(java.math.BigDecimal item)
    {
        setBigDecimal("contractBuildPrice", item);
    }
    /**
     * Object:�Ϲ�'s ��ͬ���ڵ���property 
     */
    public java.math.BigDecimal getContractRoomPrice()
    {
        return getBigDecimal("contractRoomPrice");
    }
    public void setContractRoomPrice(java.math.BigDecimal item)
    {
        setBigDecimal("contractRoomPrice", item);
    }
    /**
     * Object:�Ϲ�'s �ɽ���������property 
     */
    public java.math.BigDecimal getDealBuildPrice()
    {
        return getBigDecimal("dealBuildPrice");
    }
    public void setDealBuildPrice(java.math.BigDecimal item)
    {
        setBigDecimal("dealBuildPrice", item);
    }
    /**
     * Object:�Ϲ�'s �ɽ����ڵ���property 
     */
    public java.math.BigDecimal getDealRoomPrice()
    {
        return getBigDecimal("dealRoomPrice");
    }
    public void setDealRoomPrice(java.math.BigDecimal item)
    {
        setBigDecimal("dealRoomPrice", item);
    }
    /**
     * Object:�Ϲ�'s �Ϲ�ʱ�Ľ������ۿ���property 
     */
    public java.math.BigDecimal getSnapBuildPrice()
    {
        return getBigDecimal("snapBuildPrice");
    }
    public void setSnapBuildPrice(java.math.BigDecimal item)
    {
        setBigDecimal("snapBuildPrice", item);
    }
    /**
     * Object:�Ϲ�'s �Ϲ�ʱ�����ڵ��ۿ���property 
     */
    public java.math.BigDecimal getSnapRoomPrice()
    {
        return getBigDecimal("snapRoomPrice");
    }
    public void setSnapRoomPrice(java.math.BigDecimal item)
    {
        setBigDecimal("snapRoomPrice", item);
    }
    /**
     * Object:�Ϲ�'s �Ϲ�ʱ���ܼۿ���property 
     */
    public java.math.BigDecimal getSnapTotalPrice()
    {
        return getBigDecimal("snapTotalPrice");
    }
    public void setSnapTotalPrice(java.math.BigDecimal item)
    {
        setBigDecimal("snapTotalPrice", item);
    }
    /**
     * Object:�Ϲ�'s Լ��ǩԼʱ��property 
     */
    public int getPlanSignTimeLimit()
    {
        return getInt("planSignTimeLimit");
    }
    public void setPlanSignTimeLimit(int item)
    {
        setInt("planSignTimeLimit", item);
    }
    /**
     * Object: �Ϲ� 's ������Ŀ property 
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
     * Object:�Ϲ�'s �Ƿ��Զ�ȡ��property 
     */
    public boolean isIsAutoToInteger()
    {
        return getBoolean("isAutoToInteger");
    }
    public void setIsAutoToInteger(boolean item)
    {
        setBoolean("isAutoToInteger", item);
    }
    /**
     * Object:�Ϲ�'s ȡ������property 
     */
    public com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum getToIntegerType()
    {
        return com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum.getEnum(getString("toIntegerType"));
    }
    public void setToIntegerType(com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum item)
    {
		if (item != null) {
        setString("toIntegerType", item.getValue());
		}
    }
    /**
     * Object:�Ϲ�'s λ��property 
     */
    public com.kingdee.eas.fdc.sellhouse.DigitEnum getDigit()
    {
        return com.kingdee.eas.fdc.sellhouse.DigitEnum.getEnum(getString("digit"));
    }
    public void setDigit(com.kingdee.eas.fdc.sellhouse.DigitEnum item)
    {
		if (item != null) {
        setString("digit", item.getValue());
		}
    }
    /**
     * Object: �Ϲ� 's ����������ϸ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryCollection getElsePayListEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryCollection)get("elsePayListEntry");
    }
    /**
     * Object:�Ϲ�'s תԤ������property 
     */
    public java.util.Date getToPrePurchaseDate()
    {
        return getDate("toPrePurchaseDate");
    }
    public void setToPrePurchaseDate(java.util.Date item)
    {
        setDate("toPrePurchaseDate", item);
    }
    /**
     * Object:�Ϲ�'s ת�Ϲ�����property 
     */
    public java.util.Date getToPurchaseDate()
    {
        return getDate("toPurchaseDate");
    }
    public void setToPurchaseDate(java.util.Date item)
    {
        setDate("toPurchaseDate", item);
    }
    /**
     * Object:�Ϲ�'s ת��������property 
     */
    public java.util.Date getToSaleDate()
    {
        return getDate("toSaleDate");
    }
    public void setToSaleDate(java.util.Date item)
    {
        setDate("toSaleDate", item);
    }
    /**
     * Object:�Ϲ�'s �����׼�ܼ�property 
     */
    public java.math.BigDecimal getStandardTotalAmount()
    {
        return getBigDecimal("standardTotalAmount");
    }
    public void setStandardTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("standardTotalAmount", item);
    }
    /**
     * Object:�Ϲ�'s תǩԼ����property 
     */
    public java.util.Date getToSignDate()
    {
        return getDate("toSignDate");
    }
    public void setToSignDate(java.util.Date item)
    {
        setDate("toSignDate", item);
    }
    /**
     * Object:�Ϲ�'s ����֤��property 
     */
    public boolean isIsPaidOff()
    {
        return getBoolean("isPaidOff");
    }
    public void setIsPaidOff(boolean item)
    {
        setBoolean("isPaidOff", item);
    }
    /**
     * Object:�Ϲ�'s �۸���㷽ʽproperty 
     */
    public com.kingdee.eas.fdc.sellhouse.PriceAccountTypeEnum getPriceAccountType()
    {
        return com.kingdee.eas.fdc.sellhouse.PriceAccountTypeEnum.getEnum(getString("priceAccountType"));
    }
    public void setPriceAccountType(com.kingdee.eas.fdc.sellhouse.PriceAccountTypeEnum item)
    {
		if (item != null) {
        setString("priceAccountType", item.getValue());
		}
    }
    /**
     * Object: �Ϲ� 's ��Ӷ��¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurDistillCommisionEntryCollection getDistillCommisionEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurDistillCommisionEntryCollection)get("distillCommisionEntry");
    }
    /**
     * Object:�Ϲ�'s �����Ƿ���뷿��property 
     */
    public boolean isIsEarnestInHouseAmount()
    {
        return getBoolean("isEarnestInHouseAmount");
    }
    public void setIsEarnestInHouseAmount(boolean item)
    {
        setBoolean("isEarnestInHouseAmount", item);
    }
    /**
     * Object: �Ϲ� 's ���Ϲ�ʱӪ���������ڵ�Ӫ����Ԫ property 
     */
    public com.kingdee.eas.fdc.tenancy.MarketingUnitInfo getMarketUnit()
    {
        return (com.kingdee.eas.fdc.tenancy.MarketingUnitInfo)get("marketUnit");
    }
    public void setMarketUnit(com.kingdee.eas.fdc.tenancy.MarketingUnitInfo item)
    {
        put("marketUnit", item);
    }
    /**
     * Object:�Ϲ�'s �Ƿ�׼�����property 
     */
    public boolean isIsBasePriceSell()
    {
        return getBoolean("isBasePriceSell");
    }
    public void setIsBasePriceSell(boolean item)
    {
        setBoolean("isBasePriceSell", item);
    }
    /**
     * Object: �Ϲ� 's �ڶ����۹��ʷ�¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseSaleManCollection getSecondSaleMan()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseSaleManCollection)get("secondSaleMan");
    }
    /**
     * Object:�Ϲ�'s Լ��ǩԼ����property 
     */
    public java.util.Date getPlanSignDate()
    {
        return getDate("planSignDate");
    }
    public void setPlanSignDate(java.util.Date item)
    {
        setDate("planSignDate", item);
    }
    /**
     * Object: �Ϲ� 's �����Ƽ��� property 
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
     * Object:�Ϲ�'s �Ƿ���������property 
     */
    public boolean isIsAfterAudit()
    {
        return getBoolean("isAfterAudit");
    }
    public void setIsAfterAudit(boolean item)
    {
        setBoolean("isAfterAudit", item);
    }
    /**
     * Object: �Ϲ� 's �ͻ��������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurCustomerCollection getPurCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurCustomerCollection)get("purCustomer");
    }
    /**
     * Object:�Ϲ�'s ��ҵ����property 
     */
    public com.kingdee.eas.fdc.sellhouse.PropertyFeaturesEnum getPropertyFeatures()
    {
        return com.kingdee.eas.fdc.sellhouse.PropertyFeaturesEnum.getEnum(getString("propertyFeatures"));
    }
    public void setPropertyFeatures(com.kingdee.eas.fdc.sellhouse.PropertyFeaturesEnum item)
    {
		if (item != null) {
        setString("propertyFeatures", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D2CB60DC");
    }
}