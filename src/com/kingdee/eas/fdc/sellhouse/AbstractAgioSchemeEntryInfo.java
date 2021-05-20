package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAgioSchemeEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractAgioSchemeEntryInfo()
    {
        this("id");
    }
    protected AbstractAgioSchemeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �ۿ۷�����¼ 's �ۿ۹��� property 
     */
    public com.kingdee.eas.fdc.sellhouse.AgioBillInfo getAgioBill()
    {
        return (com.kingdee.eas.fdc.sellhouse.AgioBillInfo)get("agioBill");
    }
    public void setAgioBill(com.kingdee.eas.fdc.sellhouse.AgioBillInfo item)
    {
        put("agioBill", item);
    }
    /**
     * Object:�ۿ۷�����¼'s �ۿ�����property 
     */
    public com.kingdee.eas.fdc.sellhouse.AgioCalTypeEnum getAgioType()
    {
        return com.kingdee.eas.fdc.sellhouse.AgioCalTypeEnum.getEnum(getString("agioType"));
    }
    public void setAgioType(com.kingdee.eas.fdc.sellhouse.AgioCalTypeEnum item)
    {
		if (item != null) {
        setString("agioType", item.getValue());
		}
    }
    /**
     * Object: �ۿ۷�����¼ 's �ۿ۷���ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.AgioSchemeInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.AgioSchemeInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.AgioSchemeInfo item)
    {
        put("head", item);
    }
    /**
     * Object:�ۿ۷�����¼'s �����property 
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
        return new BOSObjectType("1E27B226");
    }
}