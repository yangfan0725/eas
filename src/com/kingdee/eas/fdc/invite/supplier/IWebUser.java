package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public interface IWebUser extends IFDCBill
{
    public WebUserCollection getWebUserCollection() throws BOSException;
    public WebUserCollection getWebUserCollection(EntityViewInfo view) throws BOSException;
    public WebUserCollection getWebUserCollection(String oql) throws BOSException;
    public WebUserInfo getWebUserInfo(IObjectPK pk) throws BOSException, EASBizException;
    public WebUserInfo getWebUserInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public WebUserInfo getWebUserInfo(String oql) throws BOSException, EASBizException;
    public void invalidRegister(BOSUuid billId) throws BOSException, EASBizException;
}