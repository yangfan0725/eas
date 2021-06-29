package com.kingdee.eas.fdc.sellhouse;

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

public interface IWSSellHouseFacade extends IBizCtrl
{
    public String sysCustomer(String str) throws BOSException, EASBizException;
    public String sysCustomerValid(String str) throws BOSException, EASBizException;
    public String isOldCustomer(String str) throws BOSException, EASBizException;
    public String synTransaction(String str) throws BOSException, EASBizException;
}