package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketGradeSetUpInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractMarketGradeSetUpInfo()
    {
        this("id");
    }
    protected AbstractMarketGradeSetUpInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�ȼ�����'s �Ƿ�ϸ�property 
     */
    public com.kingdee.eas.fdc.invite.supplier.IsGradeEnum getIsPass()
    {
        return com.kingdee.eas.fdc.invite.supplier.IsGradeEnum.getEnum(getInt("isPass"));
    }
    public void setIsPass(com.kingdee.eas.fdc.invite.supplier.IsGradeEnum item)
    {
		if (item != null) {
        setInt("isPass", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4C48410F");
    }
}