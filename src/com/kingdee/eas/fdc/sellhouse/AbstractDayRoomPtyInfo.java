package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDayRoomPtyInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractDayRoomPtyInfo()
    {
        this("id");
    }
    protected AbstractDayRoomPtyInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �½᷿���Ʒ���� 's �����½� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SaleBalanceInfo getSaleBalance()
    {
        return (com.kingdee.eas.fdc.sellhouse.SaleBalanceInfo)get("saleBalance");
    }
    public void setSaleBalance(com.kingdee.eas.fdc.sellhouse.SaleBalanceInfo item)
    {
        put("saleBalance", item);
    }
    /**
     * Object: �½᷿���Ʒ���� 's ��Ʒ���� property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProductType()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("productType");
    }
    public void setProductType(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("productType", item);
    }
    /**
     * Object: �½᷿���Ʒ���� 's ������� property 
     */
    public com.kingdee.eas.basedata.assistant.PeriodInfo getPeriod()
    {
        return (com.kingdee.eas.basedata.assistant.PeriodInfo)get("period");
    }
    public void setPeriod(com.kingdee.eas.basedata.assistant.PeriodInfo item)
    {
        put("period", item);
    }
    /**
     * Object:�½᷿���Ʒ����'s ����״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum getSellState()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum.getEnum(getString("sellState"));
    }
    public void setSellState(com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum item)
    {
		if (item != null) {
        setString("sellState", item.getValue());
		}
    }
    /**
     * Object:�½᷿���Ʒ����'s ������property 
     */
    public int getRoomCount()
    {
        return getInt("roomCount");
    }
    public void setRoomCount(int item)
    {
        setInt("roomCount", item);
    }
    /**
     * Object:�½᷿���Ʒ����'s Ԥ�⽨�����property 
     */
    public java.math.BigDecimal getPreArea()
    {
        return getBigDecimal("preArea");
    }
    public void setPreArea(java.math.BigDecimal item)
    {
        setBigDecimal("preArea", item);
    }
    /**
     * Object:�½᷿���Ʒ����'s ʵ�⽨�����property 
     */
    public java.math.BigDecimal getActArea()
    {
        return getBigDecimal("actArea");
    }
    public void setActArea(java.math.BigDecimal item)
    {
        setBigDecimal("actArea", item);
    }
    /**
     * Object:�½᷿���Ʒ����'s ��׼�ܼ�property 
     */
    public java.math.BigDecimal getStandAmout()
    {
        return getBigDecimal("standAmout");
    }
    public void setStandAmout(java.math.BigDecimal item)
    {
        setBigDecimal("standAmout", item);
    }
    /**
     * Object:�½᷿���Ʒ����'s ��ͬ��property 
     */
    public int getContractCount()
    {
        return getInt("contractCount");
    }
    public void setContractCount(int item)
    {
        setInt("contractCount", item);
    }
    /**
     * Object:�½᷿���Ʒ����'s ��ͬ�ܼ�property 
     */
    public java.math.BigDecimal getContractAmount()
    {
        return getBigDecimal("contractAmount");
    }
    public void setContractAmount(java.math.BigDecimal item)
    {
        setBigDecimal("contractAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A53F25F9");
    }
}