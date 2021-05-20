package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAgioEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractAgioEntryInfo()
    {
        this("");
    }
    protected AbstractAgioEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �ۿ۷�¼ 's �ۿ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.AgioBillInfo getAgio()
    {
        return (com.kingdee.eas.fdc.sellhouse.AgioBillInfo)get("agio");
    }
    public void setAgio(com.kingdee.eas.fdc.sellhouse.AgioBillInfo item)
    {
        put("agio", item);
    }
    /**
     * Object:�ۿ۷�¼'s �Żݽ��property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:�ۿ۷�¼'s �ٷֱ�property 
     */
    public java.math.BigDecimal getPro()
    {
        return getBigDecimal("pro");
    }
    public void setPro(java.math.BigDecimal item)
    {
        setBigDecimal("pro", item);
    }
    /**
     * Object:�ۿ۷�¼'s ˳��property 
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
     * Object:�ۿ۷�¼'s �����property 
     */
    public com.kingdee.eas.fdc.sellhouse.OperateEnum getOperate()
    {
        return com.kingdee.eas.fdc.sellhouse.OperateEnum.getEnum(getString("operate"));
    }
    public void setOperate(com.kingdee.eas.fdc.sellhouse.OperateEnum item)
    {
		if (item != null) {
        setString("operate", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5EC88F8B");
    }
}