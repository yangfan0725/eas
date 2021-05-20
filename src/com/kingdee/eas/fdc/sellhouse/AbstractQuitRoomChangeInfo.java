package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractQuitRoomChangeInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractQuitRoomChangeInfo()
    {
        this("id");
    }
    protected AbstractQuitRoomChangeInfo(String pkField)
    {
        super(pkField);
        put("quitNewPayList", new com.kingdee.eas.fdc.sellhouse.QuitNewPayListEntryCollection());
        put("quitOldPayList", new com.kingdee.eas.fdc.sellhouse.QuitOldPayListEntryCollection());
    }
    /**
     * Object: �˷���� 's �˷��� property 
     */
    public com.kingdee.eas.fdc.sellhouse.QuitRoomInfo getQuitRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.QuitRoomInfo)get("quitRoom");
    }
    public void setQuitRoom(com.kingdee.eas.fdc.sellhouse.QuitRoomInfo item)
    {
        put("quitRoom", item);
    }
    /**
     * Object: �˷���� 's ���� property 
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
     * Object: �˷���� 's �ɵĿ��˽���¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.QuitOldPayListEntryCollection getQuitOldPayList()
    {
        return (com.kingdee.eas.fdc.sellhouse.QuitOldPayListEntryCollection)get("quitOldPayList");
    }
    /**
     * Object: �˷���� 's �µĿ��˽���¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.QuitNewPayListEntryCollection getQuitNewPayList()
    {
        return (com.kingdee.eas.fdc.sellhouse.QuitNewPayListEntryCollection)get("quitNewPayList");
    }
    /**
     * Object: �˷���� 's �ɵķ��ÿ��� property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getOldFeeMoneyDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("oldFeeMoneyDefine");
    }
    public void setOldFeeMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("oldFeeMoneyDefine", item);
    }
    /**
     * Object: �˷���� 's �µķ��ÿ��� property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getNewFeeMoneyDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("newFeeMoneyDefine");
    }
    public void setNewFeeMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("newFeeMoneyDefine", item);
    }
    /**
     * Object: �˷���� 's �ɵķ��ÿ�Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getOldFeeAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("oldFeeAccount");
    }
    public void setOldFeeAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("oldFeeAccount", item);
    }
    /**
     * Object: �˷���� 's �µķ��ÿ�Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getNewFeeAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("newFeeAccount");
    }
    public void setNewFeeAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("newFeeAccount", item);
    }
    /**
     * Object:�˷����'s ���ԭ��property 
     */
    public String getQuitChangeReason()
    {
        return getString("quitChangeReason");
    }
    public void setQuitChangeReason(String item)
    {
        setString("quitChangeReason", item);
    }
    /**
     * Object:�˷����'s ԭ���ý��property 
     */
    public java.math.BigDecimal getOldFeeAmount()
    {
        return getBigDecimal("oldFeeAmount");
    }
    public void setOldFeeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("oldFeeAmount", item);
    }
    /**
     * Object:�˷����'s �µķ��ý��property 
     */
    public java.math.BigDecimal getNewFeeAmount()
    {
        return getBigDecimal("newFeeAmount");
    }
    public void setNewFeeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("newFeeAmount", item);
    }
    /**
     * Object:�˷����'s ԭ�˷����Ƿ����property 
     */
    public boolean isIsOldBlance()
    {
        return getBoolean("isOldBlance");
    }
    public void setIsOldBlance(boolean item)
    {
        setBoolean("isOldBlance", item);
    }
    /**
     * Object:�˷����'s ����Ƿ����property 
     */
    public boolean isIsNewBlance()
    {
        return getBoolean("isNewBlance");
    }
    public void setIsNewBlance(boolean item)
    {
        setBoolean("isNewBlance", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("37C4A3B5");
    }
}