package com.kingdee.eas.fdc.invite.supplier.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonF7Collection;
import com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonF7Info;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCDataBaseCollection;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseControllerBean;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;

public class SupplierLinkPersonF7ControllerBean extends AbstractSupplierLinkPersonF7ControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.app.SupplierLinkPersonF7ControllerBean");

	@Override
	protected void _checkNameBlank(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
	}

	@Override
	protected void _checkNameDup(Context arg0, IObjectValue arg1)
			throws BOSException, EASBizException {
	}

	@Override
	protected void _checkNumberBlank(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
	}

	@Override
	protected void _checkNumberDup(Context arg0, IObjectValue arg1)
			throws BOSException, EASBizException {
	}
	protected void updateCheck(Context ctx, IObjectPK pk, IObjectValue model)
    throws BOSException, EASBizException
{








/*  98*/        _checkNumberBlank(ctx, model);
/*  99*/        _checkNameBlank(ctx, model);
/* 100*/        DataBaseInfo oldModel = getDataBaseInfo(ctx, pk);
///* 101*/        if(!((DataBaseInfo)model).getNumber().equals(oldModel.getNumber()))
///* 102*/            _checkNumberDup(ctx, model);


///* 105*/        if(!((DataBaseInfo)model).getName().equals(oldModel.getName()))
///* 106*/            _checkNameDup(ctx, model);
}

}