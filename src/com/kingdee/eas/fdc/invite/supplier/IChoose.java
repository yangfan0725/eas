package com.kingdee.eas.fdc.invite.supplier;

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

public interface IChoose extends IFDCDataBase
{
    public ChooseInfo getChooseInfo(String oql) throws BOSException, EASBizException;
    public ChooseInfo getChooseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ChooseInfo getChooseInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ChooseCollection getChooseCollection(String oql) throws BOSException;
    public ChooseCollection getChooseCollection(EntityViewInfo view) throws BOSException;
    public ChooseCollection getChooseCollection() throws BOSException;
    public void IsNdelete(String areaName) throws BOSException, EASBizException;
}