package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;

public class FDCReceivingBillEntryInfo extends AbstractFDCReceivingBillEntryInfo implements Serializable 
{
    public FDCReceivingBillEntryInfo()
    {
        super();
    }
    protected FDCReceivingBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    
    /**
     * ����Ԥ��,ֱ���ύʱ���ܻ�û���տ���ϸ,���տ�ύʱ��ͬ�������տ���ϸ
     * ���տ���ϸ�Ķ����װ���տ��¼��,�������տ�ύʱͬ�������տ���ϸ
     * */
    public IRevListInfo getRevListInfo(){
    	return (IRevListInfo) get("revListInfo");
    }
    
    public void setRevListInfo(IRevListInfo revListInfo){
    	put("revListInfo", revListInfo);
    }
}