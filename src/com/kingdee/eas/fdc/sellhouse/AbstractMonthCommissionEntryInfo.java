package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMonthCommissionEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMonthCommissionEntryInfo()
    {
        this("id");
    }
    protected AbstractMonthCommissionEntryInfo(String pkField)
    {
        super(pkField);
        put("ptEntry", new com.kingdee.eas.fdc.sellhouse.MonthCommissionPTEntryCollection());
    }
    /**
     * Object: �¶�Ӫ����ɲ�����ϸ���¼ 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.MonthCommissionInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.MonthCommissionInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.MonthCommissionInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: �¶�Ӫ����ɲ�����ϸ���¼ 's ��Ʒ���ͷ�¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.MonthCommissionPTEntryCollection getPtEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.MonthCommissionPTEntryCollection)get("ptEntry");
    }
    /**
     * Object: �¶�Ӫ����ɲ�����ϸ���¼ 's ���� property 
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
     * Object:�¶�Ӫ����ɲ�����ϸ���¼'s ��λproperty 
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
     * Object:�¶�Ӫ����ɲ�����ϸ���¼'s �¶��Ϲ�ָ��property 
     */
    public java.math.BigDecimal getPurTarget()
    {
        return getBigDecimal("purTarget");
    }
    public void setPurTarget(java.math.BigDecimal item)
    {
        setBigDecimal("purTarget", item);
    }
    /**
     * Object:�¶�Ӫ����ɲ�����ϸ���¼'s �¶Ⱥ�ָͬ��property 
     */
    public java.math.BigDecimal getSignTarget()
    {
        return getBigDecimal("signTarget");
    }
    public void setSignTarget(java.math.BigDecimal item)
    {
        setBigDecimal("signTarget", item);
    }
    /**
     * Object:�¶�Ӫ����ɲ�����ϸ���¼'s �¶Ȼؿ�ָ��property 
     */
    public java.math.BigDecimal getBackTarget()
    {
        return getBigDecimal("backTarget");
    }
    public void setBackTarget(java.math.BigDecimal item)
    {
        setBigDecimal("backTarget", item);
    }
    /**
     * Object:�¶�Ӫ����ɲ�����ϸ���¼'s ����Ϲ���property 
     */
    public java.math.BigDecimal getPur()
    {
        return getBigDecimal("pur");
    }
    public void setPur(java.math.BigDecimal item)
    {
        setBigDecimal("pur", item);
    }
    /**
     * Object:�¶�Ӫ����ɲ�����ϸ���¼'s ��ɺ�ͬ��property 
     */
    public java.math.BigDecimal getSign()
    {
        return getBigDecimal("sign");
    }
    public void setSign(java.math.BigDecimal item)
    {
        setBigDecimal("sign", item);
    }
    /**
     * Object:�¶�Ӫ����ɲ�����ϸ���¼'s ��ɻؿ��property 
     */
    public java.math.BigDecimal getBack()
    {
        return getBigDecimal("back");
    }
    public void setBack(java.math.BigDecimal item)
    {
        setBigDecimal("back", item);
    }
    /**
     * Object:�¶�Ӫ����ɲ�����ϸ���¼'s �Ϲ�ָ�������property 
     */
    public java.math.BigDecimal getPurRate()
    {
        return getBigDecimal("purRate");
    }
    public void setPurRate(java.math.BigDecimal item)
    {
        setBigDecimal("purRate", item);
    }
    /**
     * Object:�¶�Ӫ����ɲ�����ϸ���¼'s ��ָͬ�������property 
     */
    public java.math.BigDecimal getSignRate()
    {
        return getBigDecimal("signRate");
    }
    public void setSignRate(java.math.BigDecimal item)
    {
        setBigDecimal("signRate", item);
    }
    /**
     * Object:�¶�Ӫ����ɲ�����ϸ���¼'s �ؿ�ָ�������property 
     */
    public java.math.BigDecimal getBackRate()
    {
        return getBigDecimal("backRate");
    }
    public void setBackRate(java.math.BigDecimal item)
    {
        setBigDecimal("backRate", item);
    }
    /**
     * Object:�¶�Ӫ����ɲ�����ϸ���¼'s ָ���ۺ������property 
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
     * Object:�¶�Ӫ����ɲ�����ϸ���¼'s ������ɺϼƣ�����property 
     */
    public java.math.BigDecimal getSumTotal()
    {
        return getBigDecimal("sumTotal");
    }
    public void setSumTotal(java.math.BigDecimal item)
    {
        setBigDecimal("sumTotal", item);
    }
    /**
     * Object:�¶�Ӫ����ɲ�����ϸ���¼'s ������ɺϼƣ���������property 
     */
    public java.math.BigDecimal getAmountTotal()
    {
        return getBigDecimal("amountTotal");
    }
    public void setAmountTotal(java.math.BigDecimal item)
    {
        setBigDecimal("amountTotal", item);
    }
    /**
     * Object:�¶�Ӫ����ɲ�����ϸ���¼'s �ϼ�Ӷ��property 
     */
    public java.math.BigDecimal getTotal()
    {
        return getBigDecimal("total");
    }
    public void setTotal(java.math.BigDecimal item)
    {
        setBigDecimal("total", item);
    }
    /**
     * Object:�¶�Ӫ����ɲ�����ϸ���¼'s ��עproperty 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("356CA702");
    }
}