package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTraEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractTraEntryInfo()
    {
        this("id");
    }
    protected AbstractTraEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���÷ѱ������¼ 's ͷ property 
     */
    public com.kingdee.eas.fdc.contract.ContractWithoutTextInfo getHead()
    {
        return (com.kingdee.eas.fdc.contract.ContractWithoutTextInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.contract.ContractWithoutTextInfo item)
    {
        put("head", item);
    }
    /**
     * Object:���÷ѱ������¼'s ��ʼ����property 
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
     * Object:���÷ѱ������¼'s ��������property 
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
     * Object:���÷ѱ������¼'s ��������property 
     */
    public int getDays()
    {
        return getInt("days");
    }
    public void setDays(int item)
    {
        setInt("days", item);
    }
    /**
     * Object:���÷ѱ������¼'s ���property 
     */
    public String getStartPlace()
    {
        return getString("startPlace");
    }
    public void setStartPlace(String item)
    {
        setString("startPlace", item);
    }
    /**
     * Object:���÷ѱ������¼'s �յ�property 
     */
    public String getEndPlace()
    {
        return getString("endPlace");
    }
    public void setEndPlace(String item)
    {
        setString("endPlace", item);
    }
    /**
     * Object:���÷ѱ������¼'s ��Ʊproperty 
     */
    public java.math.BigDecimal getAirFee()
    {
        return getBigDecimal("airFee");
    }
    public void setAirFee(java.math.BigDecimal item)
    {
        setBigDecimal("airFee", item);
    }
    /**
     * Object:���÷ѱ������¼'s ����Ʊproperty 
     */
    public java.math.BigDecimal getCarFee()
    {
        return getBigDecimal("carFee");
    }
    public void setCarFee(java.math.BigDecimal item)
    {
        setBigDecimal("carFee", item);
    }
    /**
     * Object:���÷ѱ������¼'s ���ڽ�ͨproperty 
     */
    public java.math.BigDecimal getCityFee()
    {
        return getBigDecimal("cityFee");
    }
    public void setCityFee(java.math.BigDecimal item)
    {
        setBigDecimal("cityFee", item);
    }
    /**
     * Object:���÷ѱ������¼'s ����property 
     */
    public java.math.BigDecimal getOtherFee()
    {
        return getBigDecimal("otherFee");
    }
    public void setOtherFee(java.math.BigDecimal item)
    {
        setBigDecimal("otherFee", item);
    }
    /**
     * Object:���÷ѱ������¼'s ����property 
     */
    public int getPersons()
    {
        return getInt("persons");
    }
    public void setPersons(int item)
    {
        setInt("persons", item);
    }
    /**
     * Object:���÷ѱ������¼'s ����property 
     */
    public int getLiveDays()
    {
        return getInt("liveDays");
    }
    public void setLiveDays(int item)
    {
        setInt("liveDays", item);
    }
    /**
     * Object:���÷ѱ������¼'s ���property 
     */
    public java.math.BigDecimal getLiveFee()
    {
        return getBigDecimal("liveFee");
    }
    public void setLiveFee(java.math.BigDecimal item)
    {
        setBigDecimal("liveFee", item);
    }
    /**
     * Object:���÷ѱ������¼'s ���ò���property 
     */
    public java.math.BigDecimal getAllowance()
    {
        return getBigDecimal("allowance");
    }
    public void setAllowance(java.math.BigDecimal item)
    {
        setBigDecimal("allowance", item);
    }
    /**
     * Object:���÷ѱ������¼'s ����property 
     */
    public java.math.BigDecimal getOther()
    {
        return getBigDecimal("other");
    }
    public void setOther(java.math.BigDecimal item)
    {
        setBigDecimal("other", item);
    }
    /**
     * Object:���÷ѱ������¼'s �ϼ�property 
     */
    public java.math.BigDecimal getTotal()
    {
        return getBigDecimal("total");
    }
    public void setTotal(java.math.BigDecimal item)
    {
        setBigDecimal("total", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BBC4FBCA");
    }
}