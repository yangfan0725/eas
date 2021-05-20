package com.kingdee.eas.fdc.sellhouse;

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

public interface ICustomerOrigin extends IFDCDataBase
{
    public CustomerOriginCollection getCustomerOriginCollection(String oql) throws BOSException;
    public CustomerOriginCollection getCustomerOriginCollection(EntityViewInfo view) throws BOSException;
    public CustomerOriginCollection getCustomerOriginCollection() throws BOSException;
    public CustomerOriginInfo getCustomerOriginInfo(String oql) throws BOSException, EASBizException;
    public CustomerOriginInfo getCustomerOriginInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CustomerOriginInfo getCustomerOriginInfo(IObjectPK pk) throws BOSException, EASBizException;
}