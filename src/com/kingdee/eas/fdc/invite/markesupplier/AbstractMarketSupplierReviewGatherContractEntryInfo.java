package com.kingdee.eas.fdc.invite.markesupplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierReviewGatherContractEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMarketSupplierReviewGatherContractEntryInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierReviewGatherContractEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��ͬ��¼'s ���޺�ͬproperty 
     */
    public boolean isIsHasContract()
    {
        return getBoolean("isHasContract");
    }
    public void setIsHasContract(boolean item)
    {
        setBoolean("isHasContract", item);
    }
    /**
     * Object:��ͬ��¼'s ��ͬ����property 
     */
    public String getContract()
    {
        return getString("contract");
    }
    public void setContract(String item)
    {
        setString("contract", item);
    }
    /**
     * Object: ��ͬ��¼ 's ��ͬ���� property 
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
     * Object: ��ͬ��¼ 's ��Ӧ��������� property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherInfo getHead()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherInfo item)
    {
        put("head", item);
    }
    /**
     * Object:��ͬ��¼'s ��ͬ���(Ԫ)property 
     */
    public java.math.BigDecimal getContractAmount()
    {
        return getBigDecimal("contractAmount");
    }
    public void setContractAmount(java.math.BigDecimal item)
    {
        setBigDecimal("contractAmount", item);
    }
    /**
     * Object:��ͬ��¼'s ��Ŀ����property 
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
     * Object:��ͬ��¼'s ��Ŀ������ϵ�绰property 
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
     * Object:��ͬ��¼'s ��ͬ����(Ԫ)property 
     */
    public java.math.BigDecimal getContractPrice()
    {
        return getBigDecimal("contractPrice");
    }
    public void setContractPrice(java.math.BigDecimal item)
    {
        setBigDecimal("contractPrice", item);
    }
    /**
     * Object:��ͬ��¼'s ����property 
     */
    public java.math.BigDecimal getQuantity()
    {
        return getBigDecimal("Quantity");
    }
    public void setQuantity(java.math.BigDecimal item)
    {
        setBigDecimal("Quantity", item);
    }
    /**
     * Object: ��ͬ��¼ 's ��λ property 
     */
    public com.kingdee.eas.basedata.assistant.MeasureUnitInfo getUnit()
    {
        return (com.kingdee.eas.basedata.assistant.MeasureUnitInfo)get("unit");
    }
    public void setUnit(com.kingdee.eas.basedata.assistant.MeasureUnitInfo item)
    {
        put("unit", item);
    }
    /**
     * Object:��ͬ��¼'s �Լ۱�property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.ValueForMoneyEnum getValueForMoney()
    {
        return com.kingdee.eas.fdc.invite.markesupplier.ValueForMoneyEnum.getEnum(getString("ValueForMoney"));
    }
    public void setValueForMoney(com.kingdee.eas.fdc.invite.markesupplier.ValueForMoneyEnum item)
    {
		if (item != null) {
        setString("ValueForMoney", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6F716337");
    }
}