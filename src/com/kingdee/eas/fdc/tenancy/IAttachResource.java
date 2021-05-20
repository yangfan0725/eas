package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IAttachResource extends IFDCDataBase
{
    public AttachResourceInfo getAttachResourceInfo(IObjectPK pk) throws BOSException, EASBizException;
    public AttachResourceInfo getAttachResourceInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public AttachResourceInfo getAttachResourceInfo(String oql) throws BOSException, EASBizException;
    public AttachResourceCollection getAttachResourceCollection() throws BOSException;
    public AttachResourceCollection getAttachResourceCollection(EntityViewInfo view) throws BOSException;
    public AttachResourceCollection getAttachResourceCollection(String oql) throws BOSException;
}