/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractRptBarginOnPurchaseListUI extends com.kingdee.eas.framework.client.ListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRptBarginOnPurchaseListUI.class);
    /**
     * output class constructor
     */
    public AbstractRptBarginOnPurchaseListUI() throws Exception
    {
        super();
        this.defaultObjectName = "mainQuery";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractRptBarginOnPurchaseListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.market.app", "BarginOnAnalysePurchaseQuery");
        // CoreUI		
        this.tblMain.setFormatXml(resHelper.getString("tblMain.formatXml"));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","head.id","head.number","head.state","building.id","building.name","room.floor","room.houseProperty","roomModelType.id","roomModelType.name","roomModel.id","roomModel.name","productDetail.id","productDetail.name","productType.id","productType.name","buildingProperty.id","buildingProperty.name","roomForm.id","roomForm.name","sight.id","sight.name","direction.id","direction.name","customer.customerType","customerGrade.id","customerGrade.name","group.name","customerOrigin.id","customerOrigin.name","creator.id","creator.name","salesman.id","salesman.name","customer.tradeTime","province.id","province.name","habitationArea.id","habitationArea.name","customer.sex","famillyEarning.id","famillyEarning.name","receptionType.id","receptionType.name","customer.enterpriceSize","industry.id","industry.name"});


		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        tblMain.setBounds(new Rectangle(10, 10, 996, 612));
        this.add(tblMain, new KDLayout.Constraints(10, 10, 996, 612, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemExportData);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(separatorView1);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuTools
        menuTools.add(menuMail);
        menuTools.add(menuItemStartWorkFlow);
        menuTools.add(menuItemPublishReport);
        //menuMail
        menuMail.add(menuItemToHTML);
        menuMail.add(menuItemCopyScreen);
        menuMail.add(menuItemToExcel);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnView);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);

    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.market.app.RptBarginOnPurchaseListUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }



	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????��??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("head.id"));
        sic.add(new SelectorItemInfo("head.number"));
        sic.add(new SelectorItemInfo("head.state"));
        sic.add(new SelectorItemInfo("building.id"));
        sic.add(new SelectorItemInfo("building.name"));
        sic.add(new SelectorItemInfo("room.floor"));
        sic.add(new SelectorItemInfo("room.houseProperty"));
        sic.add(new SelectorItemInfo("roomModelType.id"));
        sic.add(new SelectorItemInfo("roomModelType.name"));
        sic.add(new SelectorItemInfo("roomModel.id"));
        sic.add(new SelectorItemInfo("roomModel.name"));
        sic.add(new SelectorItemInfo("productDetail.id"));
        sic.add(new SelectorItemInfo("productDetail.name"));
        sic.add(new SelectorItemInfo("productType.id"));
        sic.add(new SelectorItemInfo("productType.name"));
        sic.add(new SelectorItemInfo("buildingProperty.id"));
        sic.add(new SelectorItemInfo("buildingProperty.name"));
        sic.add(new SelectorItemInfo("roomForm.id"));
        sic.add(new SelectorItemInfo("roomForm.name"));
        sic.add(new SelectorItemInfo("sight.id"));
        sic.add(new SelectorItemInfo("sight.name"));
        sic.add(new SelectorItemInfo("direction.id"));
        sic.add(new SelectorItemInfo("direction.name"));
        sic.add(new SelectorItemInfo("customer.customerType"));
        sic.add(new SelectorItemInfo("customerGrade.id"));
        sic.add(new SelectorItemInfo("customerGrade.name"));
        sic.add(new SelectorItemInfo("customerOrigin.id"));
        sic.add(new SelectorItemInfo("customerOrigin.name"));
        sic.add(new SelectorItemInfo("creator.id"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("salesman.id"));
        sic.add(new SelectorItemInfo("salesman.name"));
        sic.add(new SelectorItemInfo("customer.tradeTime"));
        sic.add(new SelectorItemInfo("province.id"));
        sic.add(new SelectorItemInfo("province.name"));
        sic.add(new SelectorItemInfo("habitationArea.id"));
        sic.add(new SelectorItemInfo("habitationArea.name"));
        sic.add(new SelectorItemInfo("customer.sex"));
        sic.add(new SelectorItemInfo("famillyEarning.id"));
        sic.add(new SelectorItemInfo("famillyEarning.name"));
        sic.add(new SelectorItemInfo("receptionType.id"));
        sic.add(new SelectorItemInfo("receptionType.name"));
        sic.add(new SelectorItemInfo("customer.enterpriceSize"));
        sic.add(new SelectorItemInfo("industry.id"));
        sic.add(new SelectorItemInfo("industry.name"));
        sic.add(new SelectorItemInfo("group.name"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "RptBarginOnPurchaseListUI");
    }




}