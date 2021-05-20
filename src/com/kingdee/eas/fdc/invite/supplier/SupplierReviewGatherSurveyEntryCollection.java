package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierReviewGatherSurveyEntryCollection extends AbstractObjectCollection 
{
    public SupplierReviewGatherSurveyEntryCollection()
    {
        super(SupplierReviewGatherSurveyEntryInfo.class);
    }
    public boolean add(SupplierReviewGatherSurveyEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierReviewGatherSurveyEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierReviewGatherSurveyEntryInfo item)
    {
        return removeObject(item);
    }
    public SupplierReviewGatherSurveyEntryInfo get(int index)
    {
        return(SupplierReviewGatherSurveyEntryInfo)getObject(index);
    }
    public SupplierReviewGatherSurveyEntryInfo get(Object key)
    {
        return(SupplierReviewGatherSurveyEntryInfo)getObject(key);
    }
    public void set(int index, SupplierReviewGatherSurveyEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierReviewGatherSurveyEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierReviewGatherSurveyEntryInfo item)
    {
        return super.indexOf(item);
    }
}