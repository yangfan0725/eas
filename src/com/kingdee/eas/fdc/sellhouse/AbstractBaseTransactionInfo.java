package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBaseTransactionInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractBaseTransactionInfo()
    {
        this("id");
    }
    protected AbstractBaseTransactionInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:���׻���'s ��������idproperty 
     */
    public com.kingdee.bos.util.BOSUuid getTransactionID()
    {
        return getBOSUuid("transactionID");
    }
    public void setTransactionID(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("transactionID", item);
    }
    /**
     * Object:���׻���'s ��������property 
     */
    public String getTackDesc()
    {
        return getString("tackDesc");
    }
    public void setTackDesc(String item)
    {
        setString("tackDesc", item);
    }
    /**
     * Object: ���׻��� 's ���� property 
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
     * Object: ���׻��� 's �Ƽ��ˣ���Ա�� property 
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
     * Object: ���׻��� 's �ۿ۷��� property 
     */
    public com.kingdee.eas.fdc.sellhouse.AgioSchemeInfo getAgioScheme()
    {
        return (com.kingdee.eas.fdc.sellhouse.AgioSchemeInfo)get("agioScheme");
    }
    public void setAgioScheme(com.kingdee.eas.fdc.sellhouse.AgioSchemeInfo item)
    {
        put("agioScheme", item);
    }
    /**
     * Object: ���׻��� 's ������Ŀ property 
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
     * Object: ���׻��� 's ԭ���۹��� property 
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
     * Object:���׻���'s ���۷�ʽproperty 
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
     * Object:���׻���'s �ɽ��ܼ�property 
     */
    public java.math.BigDecimal getDealTotalAmount()
    {
        return getBigDecimal("dealTotalAmount");
    }
    public void setDealTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("dealTotalAmount", item);
    }
    /**
     * Object:���׻���'s �ɽ���������property 
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
     * Object:���׻���'s �ɽ����ڵ���property 
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
     * Object:���׻���'s ��ͬ�ܼ�property 
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
     * Object:���׻���'s ��ͬ��������property 
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
     * Object:���׻���'s ��ͬ���ڵ���property 
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
     * Object:���׻���'s ���а��ҽ��property 
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
     * Object:���׻���'s �����𰴽ҽ��property 
     */
    public java.math.BigDecimal getAccFundAmount()
    {
        return getBigDecimal("accFundAmount");
    }
    public void setAccFundAmount(java.math.BigDecimal item)
    {
        setBigDecimal("accFundAmount", item);
    }
    /**
     * Object:���׻���'s �ͻ�����property 
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
     * Object:���׻���'s �ͻ���ϵ�绰property 
     */
    public String getCustomerPhone()
    {
        return getString("customerPhone");
    }
    public void setCustomerPhone(String item)
    {
        setString("customerPhone", item);
    }
    /**
     * Object: ���׻��� 's ����� property 
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
     * Object:���׻���'s װ���ܼ�property 
     */
    public java.math.BigDecimal getFitmentTotalAmount()
    {
        return getBigDecimal("fitmentTotalAmount");
    }
    public void setFitmentTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("fitmentTotalAmount", item);
    }
    /**
     * Object: ���׻��� 's װ�ޱ�׼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.DecorationStandardInfo getFitmentStandard()
    {
        return (com.kingdee.eas.fdc.sellhouse.DecorationStandardInfo)get("fitmentStandard");
    }
    public void setFitmentStandard(com.kingdee.eas.fdc.sellhouse.DecorationStandardInfo item)
    {
        put("fitmentStandard", item);
    }
    /**
     * Object:���׻���'s װ�޵���property 
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
     * Object:���׻���'s �Ƿ�װ�޼۸����ͬproperty 
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
     * Object:���׻���'s �Ƽ۷�ʽproperty 
     */
    public com.kingdee.eas.fdc.sellhouse.CalcTypeEnum getValuationType()
    {
        return com.kingdee.eas.fdc.sellhouse.CalcTypeEnum.getEnum(getString("valuationType"));
    }
    public void setValuationType(com.kingdee.eas.fdc.sellhouse.CalcTypeEnum item)
    {
		if (item != null) {
        setString("valuationType", item.getValue());
		}
    }
    /**
     * Object:���׻���'s ҵ���������property 
     */
    public java.sql.Timestamp getBusAdscriptionDate()
    {
        return getTimestamp("busAdscriptionDate");
    }
    public void setBusAdscriptionDate(java.sql.Timestamp item)
    {
        setTimestamp("busAdscriptionDate", item);
    }
    /**
     * Object:���׻���'s ���������ܼ�property 
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
     * Object:���׻���'s �Ƿ�����property 
     */
    public boolean isIsValid()
    {
        return getBoolean("isValid");
    }
    public void setIsValid(boolean item)
    {
        setBoolean("isValid", item);
    }
    /**
     * Object:���׻���'s ��ЧʧЧproperty 
     */
    public com.kingdee.eas.fdc.sellhouse.TranEffectEnum getTranEffect()
    {
        return com.kingdee.eas.fdc.sellhouse.TranEffectEnum.getEnum(getString("tranEffect"));
    }
    public void setTranEffect(com.kingdee.eas.fdc.sellhouse.TranEffectEnum item)
    {
		if (item != null) {
        setString("tranEffect", item.getValue());
		}
    }
    /**
     * Object:���׻���'s �����ۿ�property 
     */
    public java.math.BigDecimal getLastAgio()
    {
        return getBigDecimal("lastAgio");
    }
    public void setLastAgio(java.math.BigDecimal item)
    {
        setBigDecimal("lastAgio", item);
    }
    /**
     * Object:���׻���'s ҵ��״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.TransactionStateEnum getBizState()
    {
        return com.kingdee.eas.fdc.sellhouse.TransactionStateEnum.getEnum(getString("bizState"));
    }
    public void setBizState(com.kingdee.eas.fdc.sellhouse.TransactionStateEnum item)
    {
		if (item != null) {
        setString("bizState", item.getValue());
		}
    }
    /**
     * Object:���׻���'s ҵ����property 
     */
    public String getBizNumber()
    {
        return getString("bizNumber");
    }
    public void setBizNumber(String item)
    {
        setString("bizNumber", item);
    }
    /**
     * Object: ���׻��� 's ��ǩԼʱӪ���������ڵ�Ӫ����Ԫ property 
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
     * Object:���׻���'s �����׼�ܼۿ���property 
     */
    public java.math.BigDecimal getStrdTotalAmount()
    {
        return getBigDecimal("strdTotalAmount");
    }
    public void setStrdTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("strdTotalAmount", item);
    }
    /**
     * Object:���׻���'s �����׼�������ۿ���property 
     */
    public java.math.BigDecimal getStrdBuildingPrice()
    {
        return getBigDecimal("strdBuildingPrice");
    }
    public void setStrdBuildingPrice(java.math.BigDecimal item)
    {
        setBigDecimal("strdBuildingPrice", item);
    }
    /**
     * Object:���׻���'s �����׼���ڵ��ۿ���property 
     */
    public java.math.BigDecimal getStrdRoomPrice()
    {
        return getBigDecimal("strdRoomPrice");
    }
    public void setStrdRoomPrice(java.math.BigDecimal item)
    {
        setBigDecimal("strdRoomPrice", item);
    }
    /**
     * Object:���׻���'s �ۿ�˵��property 
     */
    public String getAgioDesc()
    {
        return getString("agioDesc");
    }
    public void setAgioDesc(String item)
    {
        setString("agioDesc", item);
    }
    /**
     * Object:���׻���'s ԭ����Idproperty 
     */
    public com.kingdee.bos.util.BOSUuid getSrcId()
    {
        return getBOSUuid("srcId");
    }
    public void setSrcId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("srcId", item);
    }
    /**
     * Object:���׻���'s ���佨������property 
     */
    public java.math.BigDecimal getBulidingArea()
    {
        return getBigDecimal("bulidingArea");
    }
    public void setBulidingArea(java.math.BigDecimal item)
    {
        setBigDecimal("bulidingArea", item);
    }
    /**
     * Object:���׻���'s ���������������property 
     */
    public java.math.BigDecimal getRoomArea()
    {
        return getBigDecimal("roomArea");
    }
    public void setRoomArea(java.math.BigDecimal item)
    {
        setBigDecimal("roomArea", item);
    }
    /**
     * Object:���׻���'s �����Ƿ���뷿��property 
     */
    public boolean isIsEarnestInHouseAmount()
    {
        return getBoolean("IsEarnestInHouseAmount");
    }
    public void setIsEarnestInHouseAmount(boolean item)
    {
        setBoolean("IsEarnestInHouseAmount", item);
    }
    /**
     * Object:���׻���'s �Ƿ�׼�����property 
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
     * Object:���׻���'s ȡ������property 
     */
    public String getToIntegerType()
    {
        return getString("toIntegerType");
    }
    public void setToIntegerType(String item)
    {
        setString("toIntegerType", item);
    }
    /**
     * Object:���׻���'s λ��property 
     */
    public String getDigit()
    {
        return getString("digit");
    }
    public void setDigit(String item)
    {
        setString("digit", item);
    }
    /**
     * Object:���׻���'s �ܼ�ȡ����ʽproperty 
     */
    public String getPriceToIntegerType()
    {
        return getString("priceToIntegerType");
    }
    public void setPriceToIntegerType(String item)
    {
        setString("priceToIntegerType", item);
    }
    /**
     * Object:���׻���'s �ܼ۱���λ��property 
     */
    public String getPriceDigit()
    {
        return getString("priceDigit");
    }
    public void setPriceDigit(String item)
    {
        setString("priceDigit", item);
    }
    /**
     * Object:���׻���'s ����Ԥ���������property 
     */
    public java.math.BigDecimal getStrdPlanBuildingArea()
    {
        return getBigDecimal("strdPlanBuildingArea");
    }
    public void setStrdPlanBuildingArea(java.math.BigDecimal item)
    {
        setBigDecimal("strdPlanBuildingArea", item);
    }
    /**
     * Object:���׻���'s ����Ԥ�����������property 
     */
    public java.math.BigDecimal getStrdPlanRoomArea()
    {
        return getBigDecimal("strdPlanRoomArea");
    }
    public void setStrdPlanRoomArea(java.math.BigDecimal item)
    {
        setBigDecimal("strdPlanRoomArea", item);
    }
    /**
     * Object:���׻���'s ����ʵ���������property 
     */
    public java.math.BigDecimal getStrdActualRoomArea()
    {
        return getBigDecimal("strdActualRoomArea");
    }
    public void setStrdActualRoomArea(java.math.BigDecimal item)
    {
        setBigDecimal("strdActualRoomArea", item);
    }
    /**
     * Object:���׻���'s ����ʵ�⽨�����property 
     */
    public java.math.BigDecimal getStrdActualBuildingArea()
    {
        return getBigDecimal("strdActualBuildingArea");
    }
    public void setStrdActualBuildingArea(java.math.BigDecimal item)
    {
        setBigDecimal("strdActualBuildingArea", item);
    }
    /**
     * Object: ���׻��� 's �ͻ��̻� property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo getNewCommerceChance()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo)get("newCommerceChance");
    }
    public void setNewCommerceChance(com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo item)
    {
        put("newCommerceChance", item);
    }
    /**
     * Object:���׻���'s recommendedproperty 
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
     * Object:���׻���'s ֤������property 
     */
    public String getCustomerCertificateNumber()
    {
        return getString("customerCertificateNumber");
    }
    public void setCustomerCertificateNumber(String item)
    {
        setString("customerCertificateNumber", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E7775F12");
    }
}