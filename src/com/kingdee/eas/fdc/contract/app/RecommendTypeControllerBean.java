package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectReferedException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.contract.ThirdPartyExpenseBillEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ChangeManageFactory;
import com.kingdee.eas.util.app.DbUtil;

public class RecommendTypeControllerBean extends AbstractRecommendTypeControllerBean{

	@Override
	protected boolean _disEnabled(Context ctx, IObjectPK ctPK)
			throws BOSException, EASBizException {
		 String sql = "update T_CON_RecommendType set  FIsEnabled = ?  where FID = ? ";
		 /* <-MISALIGNED-> */ /*  53*/        Object params[] = {
		 /* <-MISALIGNED-> */ /*  53*/            new Integer(0), ctPK.toString()
		                 };
		 /* <-MISALIGNED-> */ /*  54*/        DbUtil.execute(ctx, sql, params);
		 /* <-MISALIGNED-> */ /*  55*/        return true;
	}

	@Override
	protected boolean _enabled(Context ctx, IObjectPK ctPK)
			throws BOSException, EASBizException {
		/*  46*/        String sql = "update T_CON_RecommendType set  FIsEnabled = ?  where FID = ? ";
		/*  47*/        Object params[] = {/*  47*/            new Integer(1), ctPK.toString()
		                };/*  48*/        DbUtil.execute(ctx, sql, params);
		/*  49*/        return true;
	}

	@Override
	protected void _isReferenced(Context ctx, IObjectPK pk)
			throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("recommendType.id", pk.toString()));
		
		if(ThirdPartyExpenseBillEntryFactory.getLocalInstance(ctx).exists(filter))
		{
			throw new ObjectReferedException(ctx);
		}
	}

}
