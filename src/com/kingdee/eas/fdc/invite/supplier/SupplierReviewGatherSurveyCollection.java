package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierReviewGatherSurveyCollection extends AbstractObjectCollection 
{
    public SupplierReviewGatherSurveyCollection()
    {
        super(SupplierReviewGatherSurveyInfo.class);
    }
    public boolean add(SupplierReviewGatherSurveyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierReviewGatherSurveyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierReviewGatherSurveyInfo item)
    {
        return removeObject(item);
    }
    public SupplierReviewGatherSurveyInfo get(int index)
    {
        return(SupplierReviewGatherSurveyInfo)getObject(index);
    }
    public SupplierReviewGatherSurveyInfo get(Object key)
    {
        return(SupplierReviewGatherSurveyInfo)getObject(key);
    }
    public void set(int index, SupplierReviewGatherSurveyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierReviewGatherSurveyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierReviewGatherSurveyInfo item)
    {
        return super.indexOf(item);
    }
}