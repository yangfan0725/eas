package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class StoreSubjectInfo extends AbstractStoreSubjectInfo implements Serializable 
{
    public StoreSubjectInfo()
    {
        super();
    }
    protected StoreSubjectInfo(String pkField)
    {
        super(pkField);
    }
    
    /**
     * Object: ������ 's �������� property 
     */
    public void setItems(StoreItemCollection items)
    {
    	put("items", items);
    }
    
    
}