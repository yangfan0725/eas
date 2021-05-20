package com.kingdee.eas.fdc.migrate;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IInterfaceColl extends IDataBase
{
    public InterfaceCollInfo getInterfaceCollInfo(IObjectPK pk) throws BOSException, EASBizException;
    public InterfaceCollInfo getInterfaceCollInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public InterfaceCollInfo getInterfaceCollInfo(String oql) throws BOSException, EASBizException;
    public InterfaceCollCollection getInterfaceCollCollection() throws BOSException;
    public InterfaceCollCollection getInterfaceCollCollection(EntityViewInfo view) throws BOSException;
    public InterfaceCollCollection getInterfaceCollCollection(String oql) throws BOSException;
}