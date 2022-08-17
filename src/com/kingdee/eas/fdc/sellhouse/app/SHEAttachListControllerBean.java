package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
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

import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.BizobjectFacadeFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.sellhouse.SHEAttachListInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCDataBaseCollection;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseControllerBean;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.sellhouse.SHEAttachListCollection;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;

public class SHEAttachListControllerBean extends AbstractSHEAttachListControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.SHEAttachListControllerBean");
    
    protected void _checkNumberDup(Context ctx, IObjectValue model) throws BOSException, EASBizException {
//    	SHEAttachListInfo info = (SHEAttachListInfo) model;
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.dataBase_Number, info.getNumber()));
//		if (info.getId() != null) {
//			filter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.coreBase_ID, info.getId(), CompareType.NOTEQUALS));
//		}
//		if (super._exists(ctx, filter)) {
//			String number = this._getPropertyAlias(ctx, info, IFWEntityStruct.dataBase_Number) + info.getNumber();
//			throw new EASBizException(EASBizException.CHECKDUPLICATED, new Object[] { number });
//		}

	}
	protected void _checkNameDup(Context ctx, IObjectValue model)throws BOSException, EASBizException{
//    	SHEAttachListInfo info = (SHEAttachListInfo) model;
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.dataBase_Name, info.getName()));
//		if (info.getId() != null) {
//			filter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.coreBase_ID, info.getId(), CompareType.NOTEQUALS));
//		}
//		if (super._exists(ctx, filter)) {
//			String name = this._getPropertyAlias(ctx, info, IFWEntityStruct.dataBase_Name) + info.getName();
//			throw new EASBizException(EASBizException.CHECKDUPLICATED, new Object[] { name });
//		}
	}
}