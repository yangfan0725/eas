/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.report;

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
public abstract class AbstractSupplierAnnualSummaryListUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSupplierAnnualSummaryListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel searchPanel;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblSupplier;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kdtApproveYear;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kdtValidateYear;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSearch;
    protected com.kingdee.bos.ctrl.swing.KDSpinner kDSApproveYear;
    protected com.kingdee.bos.ctrl.swing.KDSpinner kDSValidateYear;
    protected ActionSearchSupplier actionSearchSupplier = null;
    /**
     * output class constructor
     */
    public AbstractSupplierAnnualSummaryListUI() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractSupplierAnnualSummaryListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSearchSupplier
        this.actionSearchSupplier = new ActionSearchSupplier(this);
        getActionManager().registerAction("actionSearchSupplier", actionSearchSupplier);
         this.actionSearchSupplier.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.searchPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblSupplier = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtApproveYear = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtValidateYear = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnSearch = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDSApproveYear = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDSValidateYear = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.searchPanel.setName("searchPanel");
        this.tblSupplier.setName("tblSupplier");
        this.kdtApproveYear.setName("kdtApproveYear");
        this.kdtValidateYear.setName("kdtValidateYear");
        this.btnSearch.setName("btnSearch");
        this.kDSApproveYear.setName("kDSApproveYear");
        this.kDSValidateYear.setName("kDSValidateYear");
        // CoreUI		
        this.btnPageSetup.setVisible(false);		
        this.btnCloud.setVisible(false);		
        this.btnXunTong.setVisible(false);		
        this.kDSeparatorCloud.setVisible(false);		
        this.menuItemPageSetup.setVisible(false);		
        this.menuItemCloudFeed.setVisible(false);		
        this.menuItemCloudScreen.setEnabled(false);		
        this.menuItemCloudScreen.setVisible(false);		
        this.menuItemCloudShare.setVisible(false);		
        this.kdSeparatorFWFile1.setVisible(false);
        // searchPanel
        // tblSupplier
		String tblSupplierStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:NumberFormat>###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"inviteType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"supplierQuanty\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"percentOfSupplier\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"approveOfYear\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"validateOfYear\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"percentOfApprove\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"newQualifyCount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"newQualifyPercent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"newInviteCount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"newInvitePercent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"newWinCount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /><t:Column t:key=\"newWinPercent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"newSignCount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"newSignPercent\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{inviteType}</t:Cell><t:Cell>$Resource{supplierQuanty}</t:Cell><t:Cell>$Resource{percentOfSupplier}</t:Cell><t:Cell>$Resource{approveOfYear}</t:Cell><t:Cell>$Resource{validateOfYear}</t:Cell><t:Cell>$Resource{percentOfApprove}</t:Cell><t:Cell>$Resource{newQualifyCount}</t:Cell><t:Cell>$Resource{newQualifyPercent}</t:Cell><t:Cell>$Resource{newInviteCount}</t:Cell><t:Cell>$Resource{newInvitePercent}</t:Cell><t:Cell>$Resource{newWinCount}</t:Cell><t:Cell>$Resource{newWinPercent}</t:Cell><t:Cell>$Resource{newSignCount}</t:Cell><t:Cell>$Resource{newSignPercent}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{inviteType_Row2}</t:Cell><t:Cell>$Resource{supplierQuanty_Row2}</t:Cell><t:Cell>$Resource{percentOfSupplier_Row2}</t:Cell><t:Cell>$Resource{approveOfYear_Row2}</t:Cell><t:Cell>$Resource{validateOfYear_Row2}</t:Cell><t:Cell>$Resource{percentOfApprove_Row2}</t:Cell><t:Cell>$Resource{newQualifyCount_Row2}</t:Cell><t:Cell>$Resource{newQualifyPercent_Row2}</t:Cell><t:Cell>$Resource{newInviteCount_Row2}</t:Cell><t:Cell>$Resource{newInvitePercent_Row2}</t:Cell><t:Cell>$Resource{newWinCount_Row2}</t:Cell><t:Cell>$Resource{newWinPercent_Row2}</t:Cell><t:Cell>$Resource{newSignCount_Row2}</t:Cell><t:Cell>$Resource{newSignPercent_Row2}</t:Cell></t:Row><t:Row t:name=\"header3\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{inviteType_Row3}</t:Cell><t:Cell>$Resource{supplierQuanty_Row3}</t:Cell><t:Cell>$Resource{percentOfSupplier_Row3}</t:Cell><t:Cell>$Resource{approveOfYear_Row3}</t:Cell><t:Cell>$Resource{validateOfYear_Row3}</t:Cell><t:Cell>$Resource{percentOfApprove_Row3}</t:Cell><t:Cell>$Resource{newQualifyCount_Row3}</t:Cell><t:Cell>$Resource{newQualifyPercent_Row3}</t:Cell><t:Cell>$Resource{newInviteCount_Row3}</t:Cell><t:Cell>$Resource{newInvitePercent_Row3}</t:Cell><t:Cell>$Resource{newWinCount_Row3}</t:Cell><t:Cell>$Resource{newWinPercent_Row3}</t:Cell><t:Cell>$Resource{newSignCount_Row3}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"2\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"2\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"2\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"2\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"2\" t:right=\"4\" /><t:Block t:top=\"0\" t:left=\"5\" t:bottom=\"2\" t:right=\"5\" /><t:Block t:top=\"0\" t:left=\"6\" t:bottom=\"0\" t:right=\"7\" /><t:Block t:top=\"1\" t:left=\"6\" t:bottom=\"2\" t:right=\"6\" /><t:Block t:top=\"1\" t:left=\"7\" t:bottom=\"2\" t:right=\"7\" /><t:Block t:top=\"0\" t:left=\"8\" t:bottom=\"0\" t:right=\"9\" /><t:Block t:top=\"1\" t:left=\"8\" t:bottom=\"2\" t:right=\"8\" /><t:Block t:top=\"1\" t:left=\"9\" t:bottom=\"2\" t:right=\"9\" /><t:Block t:top=\"0\" t:left=\"10\" t:bottom=\"0\" t:right=\"11\" /><t:Block t:top=\"1\" t:left=\"10\" t:bottom=\"2\" t:right=\"10\" /><t:Block t:top=\"1\" t:left=\"11\" t:bottom=\"2\" t:right=\"11\" /><t:Block t:top=\"0\" t:left=\"12\" t:bottom=\"0\" t:right=\"13\" /><t:Block t:top=\"1\" t:left=\"12\" t:bottom=\"2\" t:right=\"12\" /><t:Block t:top=\"1\" t:left=\"13\" t:bottom=\"2\" t:right=\"13\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblSupplier.setFormatXml(resHelper.translateString("tblSupplier",tblSupplierStrXML));

        

        // kdtApproveYear		
        this.kdtApproveYear.setBoundLabelText(resHelper.getString("kdtApproveYear.boundLabelText"));		
        this.kdtApproveYear.setBoundLabelUnderline(true);		
        this.kdtApproveYear.setBoundLabelLength(100);
        // kdtValidateYear		
        this.kdtValidateYear.setBoundLabelText(resHelper.getString("kdtValidateYear.boundLabelText"));		
        this.kdtValidateYear.setBoundLabelUnderline(true);		
        this.kdtValidateYear.setBoundLabelLength(100);
        // btnSearch
        this.btnSearch.setAction((IItemAction)ActionProxyFactory.getProxy(actionSearchSupplier, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSearch.setText(resHelper.getString("btnSearch.text"));
        // kDSApproveYear
        // kDSValidateYear
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        searchPanel.setBounds(new Rectangle(9, 12, 999, 52));
        this.add(searchPanel, new KDLayout.Constraints(9, 12, 999, 52, 0));
        tblSupplier.setBounds(new Rectangle(10, 76, 999, 540));
        this.add(tblSupplier, new KDLayout.Constraints(10, 76, 999, 540, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //searchPanel
        searchPanel.setLayout(new KDLayout());
        searchPanel.putClientProperty("OriginalBounds", new Rectangle(9, 12, 999, 52));        kdtApproveYear.setBounds(new Rectangle(17, 14, 270, 19));
        searchPanel.add(kdtApproveYear, new KDLayout.Constraints(17, 14, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kdtValidateYear.setBounds(new Rectangle(331, 14, 270, 19));
        searchPanel.add(kdtValidateYear, new KDLayout.Constraints(331, 14, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnSearch.setBounds(new Rectangle(633, 16, 46, 19));
        searchPanel.add(btnSearch, new KDLayout.Constraints(633, 16, 46, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //kdtApproveYear
        kdtApproveYear.setBoundEditor(kDSApproveYear);
        //kdtValidateYear
        kdtValidateYear.setBoundEditor(kDSValidateYear);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemCloudShare);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
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
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(kDSeparatorCloud);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.supplier.report.SupplierAnnualSummaryListUIHandler";
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
	 * ????????§µ??
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
     * output actionSearchSupplier_actionPerformed method
     */
    public void actionSearchSupplier_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSearchSupplier(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSearchSupplier() {
    	return false;
    }

    /**
     * output ActionSearchSupplier class
     */     
    protected class ActionSearchSupplier extends ItemAction {     
    
        public ActionSearchSupplier()
        {
            this(null);
        }

        public ActionSearchSupplier(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSearchSupplier.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSearchSupplier.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSearchSupplier.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierAnnualSummaryListUI.this, "ActionSearchSupplier", "actionSearchSupplier_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.supplier.report", "SupplierAnnualSummaryListUI");
    }




}