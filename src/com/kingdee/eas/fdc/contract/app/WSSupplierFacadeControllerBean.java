package com.kingdee.eas.fdc.contract.app;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK; //import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean; //import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;

import com.kingdee.eas.basedata.master.cssp.CSSPGroupCollection;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardInfo;
import com.kingdee.eas.basedata.master.cssp.StandardTypeEnum;
import com.kingdee.eas.basedata.master.cssp.SupplierCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierGroupDetailInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.master.material.UsedStatusEnum;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitCollection;
import com.kingdee.eas.basedata.org.CtrlUnitFactory;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.framework.DeletedStatusEnum;

public class WSSupplierFacadeControllerBean extends
		AbstractWSSupplierFacadeControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.contract.app.WSSupplierFacadeControllerBean");

	protected String _synSupplier(Context ctx, String str) throws BOSException,
			EASBizException {
		JSONObject obj = JSONObject.fromObject(str);
		String lastUpdateTime = obj.getString("lastUpdateTime");

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("lastUpdateTime", lastUpdateTime,
						CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("companyOrgUnit", OrgConstants.DEF_CU_ID));
		view.setFilter(filter);
		SupplierCompanyInfoCollection comCol = SupplierCompanyInfoFactory
				.getLocalInstance(ctx).getSupplierCompanyInfoCollection(view);
		Set supplierSet = new HashSet();
		for (int i = 0; i < comCol.size(); i++) {
			supplierSet.add(comCol.get(i).getSupplier().getId().toString());
		}
		view = new EntityViewInfo();
		filter = new FilterInfo();
		if (supplierSet.size() > 0) {
			filter.getFilterItems().add(
					new FilterItemInfo("lastUpdateTime", lastUpdateTime,
							CompareType.GREATER_EQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("id", supplierSet, CompareType.INCLUDE));
			filter.getFilterItems().add(
					new FilterItemInfo("usedStatus",
							UsedStatusEnum.APPROVED_VALUE));
			filter.getFilterItems().add(
					new FilterItemInfo("CU.id", OrgConstants.DEF_CU_ID));
			filter.setMaskString("(#0 or #1) and #2 and #3");
		} else {
			filter.getFilterItems().add(
					new FilterItemInfo("lastUpdateTime", lastUpdateTime,
							CompareType.GREATER_EQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("usedStatus",
							UsedStatusEnum.APPROVED_VALUE));
			filter.getFilterItems().add(
					new FilterItemInfo("CU.id", OrgConstants.DEF_CU_ID));
		}
		view.setFilter(filter);
		SupplierCollection col = SupplierFactory.getLocalInstance(ctx)
				.getSupplierCollection(view);
		JSONArray array = new JSONArray();
		for (int i = 0; i < col.size(); i++) {
			JSONObject supplier = new JSONObject();
			supplier.put("number", col.get(i).getNumber());
			supplier.put("name", col.get(i).getName());

			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("supplier", col.get(i).getId()
							.toString()));
			filter.getFilterItems()
					.add(
							new FilterItemInfo("companyOrgUnit",
									OrgConstants.DEF_CU_ID));
			view.setFilter(filter);
			comCol = SupplierCompanyInfoFactory.getLocalInstance(ctx)
					.getSupplierCompanyInfoCollection(view);
			if (comCol.size() > 0 && comCol.get(0).getSupplierBank().size() > 0) {
				supplier.put("bank", comCol.get(0).getSupplierBank().get(0)
						.getBank());
				supplier.put("bankAccount", comCol.get(0).getSupplierBank()
						.get(0).getBankAccount());
				supplier.put("code", comCol.get(0).getSupplierBank().get(0)
						.getBankAddress());
			}

			array.add(supplier);
		}
		return array.toString();
	}
}