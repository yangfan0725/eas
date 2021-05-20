package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPartABOMInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractPartABOMInfo()
    {
        this("id");
    }
    protected AbstractPartABOMInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.invite.PartABOMEntryCollection());
    }
    /**
     * Object: �׹������嵥 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object: �׹������嵥 's ��¼ property 
     */
    public com.kingdee.eas.fdc.invite.PartABOMEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.invite.PartABOMEntryCollection)get("entrys");
    }
    /**
     * Object:�׹������嵥's �׹������嵥����property 
     */
    public com.kingdee.eas.fdc.invite.PartABOMTypeEnum getPartABOMType()
    {
        return com.kingdee.eas.fdc.invite.PartABOMTypeEnum.getEnum(getString("partABOMType"));
    }
    public void setPartABOMType(com.kingdee.eas.fdc.invite.PartABOMTypeEnum item)
    {
		if (item != null) {
        setString("partABOMType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C403DF56");
    }
}