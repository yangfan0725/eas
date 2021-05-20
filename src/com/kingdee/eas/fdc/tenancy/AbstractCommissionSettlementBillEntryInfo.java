package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCommissionSettlementBillEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractCommissionSettlementBillEntryInfo()
    {
        this("id");
    }
    protected AbstractCommissionSettlementBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:Ӷ�������������¼'s �¶����̺�ͬ���ָ��property 
     */
    public java.math.BigDecimal getMonthArea()
    {
        return getBigDecimal("monthArea");
    }
    public void setMonthArea(java.math.BigDecimal item)
    {
        setBigDecimal("monthArea", item);
    }
    /**
     * Object:Ӷ�������������¼'s ����������޺�ͬ���property 
     */
    public java.math.BigDecimal getContractArea()
    {
        return getBigDecimal("contractArea");
    }
    public void setContractArea(java.math.BigDecimal item)
    {
        setBigDecimal("contractArea", item);
    }
    /**
     * Object:Ӷ�������������¼'s �¶���������ָ�������property 
     */
    public java.math.BigDecimal getRate()
    {
        return getBigDecimal("rate");
    }
    public void setRate(java.math.BigDecimal item)
    {
        setBigDecimal("rate", item);
    }
    /**
     * Object:Ӷ�������������¼'s ��ɻ���property 
     */
    public java.math.BigDecimal getZcBaseAmount()
    {
        return getBigDecimal("zcBaseAmount");
    }
    public void setZcBaseAmount(java.math.BigDecimal item)
    {
        setBigDecimal("zcBaseAmount", item);
    }
    /**
     * Object:Ӷ�������������¼'s �������޸�Ӷ����property 
     */
    public java.math.BigDecimal getZcRate()
    {
        return getBigDecimal("zcRate");
    }
    public void setZcRate(java.math.BigDecimal item)
    {
        setBigDecimal("zcRate", item);
    }
    /**
     * Object:Ӷ�������������¼'s ��������Ӷ��ϵ��property 
     */
    public java.math.BigDecimal getZcCommissionRate()
    {
        return getBigDecimal("zcCommissionRate");
    }
    public void setZcCommissionRate(java.math.BigDecimal item)
    {
        setBigDecimal("zcCommissionRate", item);
    }
    /**
     * Object:Ӷ�������������¼'s ��ɽ��property 
     */
    public java.math.BigDecimal getZcAmount()
    {
        return getBigDecimal("zcAmount");
    }
    public void setZcAmount(java.math.BigDecimal item)
    {
        setBigDecimal("zcAmount", item);
    }
    /**
     * Object:Ӷ�������������¼'s ʵ�ս����ɻ���property 
     */
    public java.math.BigDecimal getZjBaseAmount()
    {
        return getBigDecimal("zjBaseAmount");
    }
    public void setZjBaseAmount(java.math.BigDecimal item)
    {
        setBigDecimal("zjBaseAmount", item);
    }
    /**
     * Object:Ӷ�������������¼'s ����սɸ�Ӷ����property 
     */
    public java.math.BigDecimal getZjRate()
    {
        return getBigDecimal("zjRate");
    }
    public void setZjRate(java.math.BigDecimal item)
    {
        setBigDecimal("zjRate", item);
    }
    /**
     * Object:Ӷ�������������¼'s ����ս�Ӷ��ϵ��property 
     */
    public java.math.BigDecimal getZjCommissionRate()
    {
        return getBigDecimal("zjCommissionRate");
    }
    public void setZjCommissionRate(java.math.BigDecimal item)
    {
        setBigDecimal("zjCommissionRate", item);
    }
    /**
     * Object:Ӷ�������������¼'s ��ɽ��property 
     */
    public java.math.BigDecimal getZjAmount()
    {
        return getBigDecimal("zjAmount");
    }
    public void setZjAmount(java.math.BigDecimal item)
    {
        setBigDecimal("zjAmount", item);
    }
    /**
     * Object:Ӷ�������������¼'s һ����������ۼ�Ӷ��property 
     */
    public java.math.BigDecimal getQuitAmount()
    {
        return getBigDecimal("quitAmount");
    }
    public void setQuitAmount(java.math.BigDecimal item)
    {
        setBigDecimal("quitAmount", item);
    }
    /**
     * Object:Ӷ�������������¼'s �������property 
     */
    public java.math.BigDecimal getTc()
    {
        return getBigDecimal("tc");
    }
    public void setTc(java.math.BigDecimal item)
    {
        setBigDecimal("tc", item);
    }
    /**
     * Object:Ӷ�������������¼'s �����������property 
     */
    public java.math.BigDecimal getXz()
    {
        return getBigDecimal("xz");
    }
    public void setXz(java.math.BigDecimal item)
    {
        setBigDecimal("xz", item);
    }
    /**
     * Object:Ӷ�������������¼'s ���Ԥ��property 
     */
    public java.math.BigDecimal getYlRate()
    {
        return getBigDecimal("ylRate");
    }
    public void setYlRate(java.math.BigDecimal item)
    {
        setBigDecimal("ylRate", item);
    }
    /**
     * Object:Ӷ�������������¼'s ���Ԥ��property 
     */
    public java.math.BigDecimal getYl()
    {
        return getBigDecimal("yl");
    }
    public void setYl(java.math.BigDecimal item)
    {
        setBigDecimal("yl", item);
    }
    /**
     * Object:Ӷ�������������¼'s ��������property 
     */
    public java.math.BigDecimal getJl()
    {
        return getBigDecimal("jl");
    }
    public void setJl(java.math.BigDecimal item)
    {
        setBigDecimal("jl", item);
    }
    /**
     * Object:Ӷ�������������¼'s ʵ��Ӷ��property 
     */
    public java.math.BigDecimal getYj()
    {
        return getBigDecimal("yj");
    }
    public void setYj(java.math.BigDecimal item)
    {
        setBigDecimal("yj", item);
    }
    /**
     * Object:Ӷ�������������¼'s ��עproperty 
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
     * Object: Ӷ�������������¼ 's ��֯ property 
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
     * Object: Ӷ�������������¼ 's ��Ա property 
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
     * Object:Ӷ�������������¼'s ��λproperty 
     */
    public String getPosition()
    {
        return getString("position");
    }
    public void setPosition(String item)
    {
        setString("position", item);
    }
    /**
     * Object: Ӷ�������������¼ 's ͷ property 
     */
    public com.kingdee.eas.fdc.tenancy.CommissionSettlementBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.CommissionSettlementBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.CommissionSettlementBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object: Ӷ�������������¼ 's ��Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DD14058E");
    }
}