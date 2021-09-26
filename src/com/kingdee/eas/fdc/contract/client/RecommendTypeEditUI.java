package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.fdc.basedata.ChangeTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.contract.RecommendTypeInfo;

public class RecommendTypeEditUI extends AbstractRecommendTypeEditUI{

	public RecommendTypeEditUI() throws Exception {
		super();
	}

	@Override
	protected FDCDataBaseInfo getEditData() {
		// TODO Auto-generated method stub
		return editData;
	}

	@Override
	protected KDBizMultiLangBox getNameCtrl() {
		// TODO Auto-generated method stub
		return bizName;
	}

	@Override
	protected KDTextField getNumberCtrl() {
		// TODO Auto-generated method stub
		return txtNumber;
	}

	@Override
	protected IObjectValue createNewData() {
		RecommendTypeInfo info = new RecommendTypeInfo();
		info.setIsEnabled(isEnabled);
		return info;
	}
	 public SelectorItemCollection getSelectors()
     {

/* 117*/        SelectorItemCollection sic = new SelectorItemCollection();
/* 118*/        sic.add(new SelectorItemInfo("number"));
/* 119*/        sic.add(new SelectorItemInfo("isEnabled"));
/* 120*/        sic.add(new SelectorItemInfo("description"));
/* 121*/        sic.add(new SelectorItemInfo("name"));
/* 122*/        sic.add(new SelectorItemInfo("CU.id"));
sic.add(new SelectorItemInfo("costAccount.*"));
/* 123*/        return sic;
     }
     public void actionEdit_actionPerformed(ActionEvent e)
         throws Exception
     {
/* 149*/        super.actionEdit_actionPerformed(e);
/* 150*/        if("11111111-1111-1111-1111-111111111111CCE7AED4".equals(editData.getCU().getId().toString()))
         {/* 151*/            btnRemove.setEnabled(false);
/* 152*/            menuItemRemove.setEnabled(false);
         }
     }

	@Override
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("fullOrgUnit.id", OrgConstants.DEF_CU_ID));
		filterItems.add(new FilterItemInfo("isMarket", Boolean.TRUE));
		filterItems.add(new FilterItemInfo("isLeaf", Boolean.TRUE));
		
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(filter);
		this.prmtCostAccount.setEntityViewInfo(view);
	}
	 protected void verifyInput(ActionEvent e)
     throws Exception
 {FDCClientVerifyHelper.verifyEmpty(this, this.prmtCostAccount);
		 super.verifyInput(e);
 }
}
