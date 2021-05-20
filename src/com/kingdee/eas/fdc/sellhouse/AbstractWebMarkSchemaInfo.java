package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWebMarkSchemaInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractWebMarkSchemaInfo()
    {
        this("id");
    }
    protected AbstractWebMarkSchemaInfo(String pkField)
    {
        super(pkField);
        put("functionEntrys", new com.kingdee.eas.fdc.sellhouse.WebMarkFunctionCollection());
    }
    /**
     * Object:��ǩ����'s nullproperty 
     */
    public com.kingdee.bos.util.BOSUuid getId()
    {
        return getBOSUuid("id");
    }
    public void setId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("id", item);
    }
    /**
     * Object: ��ǩ���� 's ���ܷ�¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.WebMarkFunctionCollection getFunctionEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.WebMarkFunctionCollection)get("functionEntrys");
    }
    /**
     * Object: ��ǩ���� 's ������Ŀ property 
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
        return new BOSObjectType("526A7E47");
    }
}