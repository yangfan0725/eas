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

public interface ICustomerGrade extends IFDCDataBase
{
    public CustomerGradeInfo getCustomerGradeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CustomerGradeInfo getCustomerGradeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CustomerGradeInfo getCustomerGradeInfo(String oql) throws BOSException, EASBizException;
    public CustomerGradeCollection getCustomerGradeCollection() throws BOSException;
    public CustomerGradeCollection getCustomerGradeCollection(EntityViewInfo view) throws BOSException;
    public CustomerGradeCollection getCustomerGradeCollection(String oql) throws BOSException;
}