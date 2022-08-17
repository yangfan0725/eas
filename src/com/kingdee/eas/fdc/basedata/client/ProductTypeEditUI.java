/*jadclipse*/package com.kingdee.eas.fdc.basedata.client;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.*;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import java.awt.event.ActionEvent;
import org.apache.log4j.Logger;
public class ProductTypeEditUI extends AbstractProductTypeEditUI
{
            public ProductTypeEditUI()
                throws Exception
            {
            }
            public void onLoad()
                throws Exception
            {





























/*  51*/        super.onLoad();
/*  52*/        setTitle();

/*  54*/        EntityViewInfo view = new EntityViewInfo();
/*  55*/        FilterInfo filter = new FilterInfo();
/*  56*/        filter.getFilterItems().add(new FilterItemInfo("group.number", "Z00002"));
/*  57*/        filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
/*  58*/        filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
/*  59*/        view.setFilter(filter);
/*  60*/        prmtGeneralAsstActType.setEntityViewInfo(view);
            }
            protected void verifyInput(ActionEvent e)
                throws Exception
            {
/* <-MISALIGNED-> */ /*  63*/        FDCClientVerifyHelper.verifyEmpty(this, prmtGeneralAsstActType);
FDCClientVerifyHelper.verifyEmpty(this, this.cbProperty);
            }
            private void setTitle()
            {
/* <-MISALIGNED-> */ /*  66*/        FDCBaseDataClientUtils.setupUITitle(this, EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "ProductType"));
            }
            protected IObjectValue createNewData()
            {















































































































/* 184*/        ProductTypeInfo productTypeInfo = new ProductTypeInfo();
/* 185*/        productTypeInfo.setIsEnabled(isEnabled);
/* 186*/        return productTypeInfo;
            }
            protected ICoreBase getBizInterface()
                throws Exception
            {/* 190*/        return ProductTypeFactory.getRemoteInstance();
            }
            protected FDCDataBaseInfo getEditData()
            {
/* 194*/        return editData;
            }
            protected KDBizMultiLangBox getNameCtrl()
            {
/* 198*/        return bizName;
            }
            protected KDTextField getNumberCtrl()
            {
/* 202*/        return txtNumber;
            }
            private static final Logger logger = CoreUIObject.getLogger(ProductTypeEditUI.class.getName());
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\sp\sp-fdc_sellhouse_client.jar
	Total time: 52 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/