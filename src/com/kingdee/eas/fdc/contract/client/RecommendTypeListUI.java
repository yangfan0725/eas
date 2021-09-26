package com.kingdee.eas.fdc.contract.client;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.util.EASResource;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.contract.RecommendTypeFactory;
import com.kingdee.eas.fdc.contract.RecommendTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

public class RecommendTypeListUI extends AbstractRecommendTypeListUI{

	 public RecommendTypeListUI() throws Exception {
		super();
	}
	protected String getEditUIName()
     {
		 return RecommendTypeEditUI.class.getName();
     }
	 protected ICoreBase getBizInterface()
     throws Exception
 {/*  87*/        return RecommendTypeFactory.getRemoteInstance();
 }
 private boolean verifySysDataEdit()
 {
/* 175*/        if(tblMain.getSelectManager().getActiveRowIndex() == -1)
/* 176*/            return false;

/* 178*/        int i = tblMain.getSelectManager().getActiveRowIndex();
/* 179*/        if("11111111-1111-1111-1111-111111111111CCE7AED4".equals(tblMain.getRow(i).getCell("CU.id").getValue().toString()))
     {/* 180*/            MsgBox.showError(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Update_SysData"));
/* 181*/            return false;
     } else
     {/* 183*/            return true;
     }
 }
 private void verifyNotAccepted(IRow row)
 {
/* 203*/        if(row.getCell("CU.id").getValue() != null && "11111111-1111-1111-1111-111111111111CCE7AED4".equals(row.getCell("CU.id").getValue().toString()))
     {/* 204*/            MsgBox.showError(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Delete_SysData"));
/* 205*/            SysUtil.abort();
     }
 }
 protected FDCDataBaseInfo getBaseDataInfo()
 {/* 209*/        return new RecommendTypeInfo();
 }
}
