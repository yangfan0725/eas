package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCostIndexInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractCostIndexInfo()
    {
        this("id");
    }
    protected AbstractCostIndexInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.aimcost.CostIndexEntryCollection());
    }
    /**
     * Object: ���ָ��� 's �ɹ���� property 
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
     * Object: ���ָ��� 's ��ͬ property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContract()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contract");
    }
    public void setContract(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contract", item);
    }
    /**
     * Object: ���ָ��� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.aimcost.CostIndexEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.aimcost.CostIndexEntryCollection)get("entry");
    }
    /**
     * Object:���ָ���'s ���浥��property 
     */
    public java.math.BigDecimal getBuildPrice()
    {
        return getBigDecimal("buildPrice");
    }
    public void setBuildPrice(java.math.BigDecimal item)
    {
        setBigDecimal("buildPrice", item);
    }
    /**
     * Object:���ָ���'s ҵ̬property 
     */
    public String getProductType()
    {
        return getString("productType");
    }
    public void setProductType(String item)
    {
        setString("productType", item);
    }
    /**
     * Object:���ָ���'s �Ƿ�����property 
     */
    public boolean isIsLatest()
    {
        return getBoolean("isLatest");
    }
    public void setIsLatest(boolean item)
    {
        setBoolean("isLatest", item);
    }
    /**
     * Object:���ָ���'s �汾��property 
     */
    public int getVersion()
    {
        return getInt("version");
    }
    public void setVersion(int item)
    {
        setInt("version", item);
    }
    /**
     * Object:���ָ���'s ��ͬ�׶�property 
     */
    public com.kingdee.eas.fdc.aimcost.ContractPhaseEnum getContractPhase()
    {
        return com.kingdee.eas.fdc.aimcost.ContractPhaseEnum.getEnum(getString("contractPhase"));
    }
    public void setContractPhase(com.kingdee.eas.fdc.aimcost.ContractPhaseEnum item)
    {
		if (item != null) {
        setString("contractPhase", item.getValue());
		}
    }
    /**
     * Object:���ָ���'s �������property 
     */
    public java.math.BigDecimal getSaleArea()
    {
        return getBigDecimal("saleArea");
    }
    public void setSaleArea(java.math.BigDecimal item)
    {
        setBigDecimal("saleArea", item);
    }
    /**
     * Object:���ָ���'s ���۵���property 
     */
    public java.math.BigDecimal getSalePrice()
    {
        return getBigDecimal("salePrice");
    }
    public void setSalePrice(java.math.BigDecimal item)
    {
        setBigDecimal("salePrice", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E919A98E");
    }
}