package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenancyModificationPayInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractTenancyModificationPayInfo()
    {
        this("id");
    }
    protected AbstractTenancyModificationPayInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:���ޱ������ƻ�'s �Ƿ��¸���ƻ�property 
     */
    public boolean isIsNewPayList()
    {
        return getBoolean("isNewPayList");
    }
    public void setIsNewPayList(boolean item)
    {
        setBoolean("isNewPayList", item);
    }
    /**
     * Object: ���ޱ������ƻ� 's ���� property 
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
     * Object: ���ޱ������ƻ� 's ���ޱ���� property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyModificationInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyModificationInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.TenancyModificationInfo item)
    {
        put("head", item);
    }
    /**
     * Object:���ޱ������ƻ�'s ��������property 
     */
    public int getLeaseSeq()
    {
        return getInt("leaseSeq");
    }
    public void setLeaseSeq(int item)
    {
        setInt("leaseSeq", item);
    }
    /**
     * Object:���ޱ������ƻ�'s ��ʼ����property 
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
     * Object:���ޱ������ƻ�'s ��������property 
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
     * Object:���ޱ������ƻ�'s Ӧ������property 
     */
    public java.util.Date getActPayDate()
    {
        return getDate("actPayDate");
    }
    public void setActPayDate(java.util.Date item)
    {
        setDate("actPayDate", item);
    }
    /**
     * Object:���ޱ������ƻ�'s Ӧ�����property 
     */
    public java.math.BigDecimal getActPayAmount()
    {
        return getBigDecimal("actPayAmount");
    }
    public void setActPayAmount(java.math.BigDecimal item)
    {
        setBigDecimal("actPayAmount", item);
    }
    /**
     * Object: ���ޱ������ƻ� 's �������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getMoneyDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("moneyDefine");
    }
    public void setMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("moneyDefine", item);
    }
    /**
     * Object:���ޱ������ƻ�'s ʵ�����property 
     */
    public java.math.BigDecimal getActRevAmount()
    {
        return getBigDecimal("actRevAmount");
    }
    public void setActRevAmount(java.math.BigDecimal item)
    {
        setBigDecimal("actRevAmount", item);
    }
    /**
     * Object:���ޱ������ƻ�'s ʵ������property 
     */
    public java.util.Date getActRevDate()
    {
        return getDate("actRevDate");
    }
    public void setActRevDate(java.util.Date item)
    {
        setDate("actRevDate", item);
    }
    /**
     * Object:���ޱ������ƻ�'s ������property 
     */
    public int getTreeLevel()
    {
        return getInt("treeLevel");
    }
    public void setTreeLevel(int item)
    {
        setInt("treeLevel", item);
    }
    /**
     * Object:���ޱ������ƻ�'s ��������property 
     */
    public String getMoneyDefineName()
    {
        return getString("moneyDefineName");
    }
    public void setMoneyDefineName(String item)
    {
        setString("moneyDefineName", item);
    }
    /**
     * Object:���ޱ������ƻ�'s ��property 
     */
    public int getRow()
    {
        return getInt("row");
    }
    public void setRow(int item)
    {
        setInt("row", item);
    }
    /**
     * Object:���ޱ������ƻ�'s ��property 
     */
    public int getCell()
    {
        return getInt("cell");
    }
    public void setCell(int item)
    {
        setInt("cell", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("22C9FB35");
    }
}