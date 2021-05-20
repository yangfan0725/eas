package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import java.util.Date;

public interface IWebInvitationWSFacade extends IBizCtrl
{
    public String syncEASBasedata(Date lastUpdateDate) throws BOSException, EASBizException;
    public String registerUser(String userParam) throws BOSException, EASBizException;
    public String apply(String applyParam) throws BOSException, EASBizException;
    public byte[] download(String downloadParam) throws BOSException, EASBizException;
    public String saveSupplier(String supplier) throws BOSException, EASBizException;
    public String changePassword(String param) throws BOSException, EASBizException;
}