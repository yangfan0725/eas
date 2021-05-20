package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractQuitPayListEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractQuitPayListEntryInfo()
    {
        this("id");
    }
    protected AbstractQuitPayListEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �˷����˽���¼ 's ���ⵥ property 
     */
    public com.kingdee.eas.fdc.sellhouse.QuitRoomInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.QuitRoomInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.QuitRoomInfo item)
    {
        put("head", item);
    }
    /**
     * Object:�˷����˽���¼'s ���˽��property 
     */
    public java.math.BigDecimal getCanRefundmentAmount()
    {
        return getBigDecimal("canRefundmentAmount");
    }
    public void setCanRefundmentAmount(java.math.BigDecimal item)
    {
        setBigDecimal("canRefundmentAmount", item);
    }
    /**
     * Object: �˷����˽���¼ 's �������� property 
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
     * Object:�˷����˽���¼'s Ӧ�����property 
     */
    public java.math.BigDecimal getApAmount()
    {
        return getBigDecimal("apAmount");
    }
    public void setApAmount(java.math.BigDecimal item)
    {
        setBigDecimal("apAmount", item);
    }
    /**
     * Object:�˷����˽���¼'s ʵ�����property 
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
     * Object:�˷����˽���¼'s �����˽��property 
     */
    public java.math.BigDecimal getMaxCanRefundAmount()
    {
        return getBigDecimal("maxCanRefundAmount");
    }
    public void setMaxCanRefundAmount(java.math.BigDecimal item)
    {
        setBigDecimal("maxCanRefundAmount", item);
    }
    /**
     * Object:�˷����˽���¼'s ���к�property 
     */
    public int getSeq()
    {
        return getInt("seq");
    }
    public void setSeq(int item)
    {
        setInt("seq", item);
    }
    /**
     * Object:�˷����˽���¼'s ��������property 
     */
    public com.kingdee.eas.fdc.sellhouse.FeeFromTypeEnum getFeeFromType()
    {
        return com.kingdee.eas.fdc.sellhouse.FeeFromTypeEnum.getEnum(getString("feeFromType"));
    }
    public void setFeeFromType(com.kingdee.eas.fdc.sellhouse.FeeFromTypeEnum item)
    {
		if (item != null) {
        setString("feeFromType", item.getValue());
		}
    }
    /**
     * Object:�˷����˽���¼'s ��ת���property 
     */
    public java.math.BigDecimal getHasRemitAmount()
    {
        return getBigDecimal("hasRemitAmount");
    }
    public void setHasRemitAmount(java.math.BigDecimal item)
    {
        setBigDecimal("hasRemitAmount", item);
    }
    /**
     * Object:�˷����˽���¼'s ��ϸidproperty 
     */
    public String getPayListId()
    {
        return getString("payListId");
    }
    public void setPayListId(String item)
    {
        setString("payListId", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("62D08F76");
    }
}