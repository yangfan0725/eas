package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAcceptanceLetterInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractAcceptanceLetterInfo()
    {
        this("id");
    }
    protected AbstractAcceptanceLetterInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�б�֪ͨ��'s �Ƿ����ɺ�ͬproperty 
     */
    public boolean isIsCreateContract()
    {
        return getBoolean("isCreateContract");
    }
    public void setIsCreateContract(boolean item)
    {
        setBoolean("isCreateContract", item);
    }
    /**
     * Object: �б�֪ͨ�� 's �б굥λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getSupplier()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object: �б�֪ͨ�� 's �б����� property 
     */
    public com.kingdee.eas.fdc.invite.InviteProjectInfo getInviteProject()
    {
        return (com.kingdee.eas.fdc.invite.InviteProjectInfo)get("inviteProject");
    }
    public void setInviteProject(com.kingdee.eas.fdc.invite.InviteProjectInfo item)
    {
        put("inviteProject", item);
    }
    /**
     * Object:�б�֪ͨ��'s ǩԼ����property 
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
     * Object:�б�֪ͨ��'s �б���property 
     */
    public java.math.BigDecimal getInviteAmount()
    {
        return getBigDecimal("inviteAmount");
    }
    public void setInviteAmount(java.math.BigDecimal item)
    {
        setBigDecimal("inviteAmount", item);
    }
    /**
     * Object:�б�֪ͨ��'s ��λ����property 
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
     * Object:�б�֪ͨ��'s �б귽ʽproperty 
     */
    public com.kingdee.eas.fdc.invite.InviteFormEnum getInviteForm()
    {
        return com.kingdee.eas.fdc.invite.InviteFormEnum.getEnum(getString("inviteForm"));
    }
    public void setInviteForm(com.kingdee.eas.fdc.invite.InviteFormEnum item)
    {
		if (item != null) {
        setString("inviteForm", item.getValue());
		}
    }
    /**
     * Object:�б�֪ͨ��'s ���Ͷ���property 
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
     * Object:�б�֪ͨ��'s �ε�Ͷ���property 
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
     * Object:�б�֪ͨ��'s �м�Ͷ���property 
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
     * Object:�б�֪ͨ��'s �θ�Ͷ���property 
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
     * Object:�б�֪ͨ��'s ���Ͷ���property 
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
     * Object: �б�֪ͨ�� 's ���Ͷ��۵�λ property 
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
     * Object: �б�֪ͨ�� 's �ε�Ͷ��۵�λ property 
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
     * Object: �б�֪ͨ�� 's �м�Ͷ��۵�λ property 
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
     * Object: �б�֪ͨ�� 's �θ�Ͷ��۵�λ property 
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
     * Object: �б�֪ͨ�� 's ���Ͷ��۵�λ property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getHighestPriceUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("highestPriceUnit");
    }
    public void setHighestPriceUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("highestPriceUnit", item);
    }
    /**
     * Object:�б�֪ͨ��'s �汾��property 
     */
    public double getVersion()
    {
        return getDouble("version");
    }
    public void setVersion(double item)
    {
        setDouble("version", item);
    }
    /**
     * Object:�б�֪ͨ��'s �Ƿ����հ汾property 
     */
    public boolean isIsLastVersion()
    {
        return getBoolean("isLastVersion");
    }
    public void setIsLastVersion(boolean item)
    {
        setBoolean("isLastVersion", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1DD52081");
    }
}