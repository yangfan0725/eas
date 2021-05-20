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
public abstract class AbstractSupplierWinInfoRptUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSupplierWinInfoRptUI.class);
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton actionFresh;
    protected ActionWinRefresh actionWinRefresh = null;
    /**
     * output class constructor
     */
    public AbstractSupplierWinInfoRptUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSupplierWinInfoRptUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionWinRefresh
        this.actionWinRefresh = new ActionWinRefresh(this);
        getActionManager().registerAction("actionWinRefresh", actionWinRefresh);
         this.actionWinRefresh.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDTable1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.actionFresh = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDTable1.setName("kDTable1");
        this.actionFresh.setName("actionFresh");
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
        // kDTable1
		String kDTable1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>###.0000</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>###,###.0000</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>###,###.0000</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol14\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol15\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol16\"><c:NumberFormat>###,###.0000</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol17\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol18\"><c:NumberFormat>###,###.0000</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol19\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol20\"><c:NumberFormat>###,###.0000</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol21\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol22\"><c:NumberFormat>###,###.0000</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol23\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol24\"><c:NumberFormat>###,###.0000</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol25\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol26\"><c:NumberFormat>###,###.0000</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol27\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol28\"><c:NumberFormat>###,###.0000</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol29\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol30\"><c:NumberFormat>###,###.0000</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol31\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol32\"><c:NumberFormat>###,###.0000</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol33\"><c:NumberFormat>###,###.00</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol34\"><c:NumberFormat>###,###.0000</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"puchaseCatagory\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"inviteType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"inviteCount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"inviteAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"selectedCount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"selectedRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol5\" /><t:Column t:key=\"selectedAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol6\" /><t:Column t:key=\"selectedAmtRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol7\" /><t:Column t:key=\"firstBid\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /><t:Column t:key=\"secondBid\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /><t:Column t:key=\"thirdBid\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol10\" /><t:Column t:key=\"firstSignCount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"firstSignRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"firstSignAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /><t:Column t:key=\"firstSignAmtCount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /><t:Column t:key=\"normalCount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol15\" /><t:Column t:key=\"normalRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol16\" /><t:Column t:key=\"normalAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol17\" /><t:Column t:key=\"normalAmtRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol18\" /><t:Column t:key=\"inquiryCount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol19\" /><t:Column t:key=\"inquiryRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol20\" /><t:Column t:key=\"inquiryAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol21\" /><t:Column t:key=\"inquiryAmtRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol22\" /><t:Column t:key=\"inquiryFirstBidCount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol23\" /><t:Column t:key=\"inquiryFirstBidCRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol24\" /><t:Column t:key=\"inquiryFirstBidAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol25\" /><t:Column t:key=\"inquiryFirstBidAmtRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol26\" /><t:Column t:key=\"uniqueCount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol27\" /><t:Column t:key=\"uniqueRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol28\" /><t:Column t:key=\"uniqueAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol29\" /><t:Column t:key=\"uniqueAmtRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol30\" /><t:Column t:key=\"uniqueFirstBidCount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol31\" /><t:Column t:key=\"uniqueFirstBidRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol32\" /><t:Column t:key=\"uniqueFirstBidAmt\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol33\" /><t:Column t:key=\"uniqueFirstBidAmtRate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol34\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{puchaseCatagory}</t:Cell><t:Cell>$Resource{inviteType}</t:Cell><t:Cell>$Resource{inviteCount}</t:Cell><t:Cell>$Resource{inviteAmt}</t:Cell><t:Cell>$Resource{selectedCount}</t:Cell><t:Cell>$Resource{selectedRate}</t:Cell><t:Cell>$Resource{selectedAmt}</t:Cell><t:Cell>$Resource{selectedAmtRate}</t:Cell><t:Cell>$Resource{firstBid}</t:Cell><t:Cell>$Resource{secondBid}</t:Cell><t:Cell>$Resource{thirdBid}</t:Cell><t:Cell>$Resource{firstSignCount}</t:Cell><t:Cell>$Resource{firstSignRate}</t:Cell><t:Cell>$Resource{firstSignAmt}</t:Cell><t:Cell>$Resource{firstSignAmtCount}</t:Cell><t:Cell>$Resource{normalCount}</t:Cell><t:Cell>$Resource{normalRate}</t:Cell><t:Cell>$Resource{normalAmt}</t:Cell><t:Cell>$Resource{normalAmtRate}</t:Cell><t:Cell>$Resource{inquiryCount}</t:Cell><t:Cell>$Resource{inquiryRate}</t:Cell><t:Cell>$Resource{inquiryAmt}</t:Cell><t:Cell>$Resource{inquiryAmtRate}</t:Cell><t:Cell>$Resource{inquiryFirstBidCount}</t:Cell><t:Cell>$Resource{inquiryFirstBidCRate}</t:Cell><t:Cell>$Resource{inquiryFirstBidAmt}</t:Cell><t:Cell>$Resource{inquiryFirstBidAmtRate}</t:Cell><t:Cell>$Resource{uniqueCount}</t:Cell><t:Cell>$Resource{uniqueRate}</t:Cell><t:Cell>$Resource{uniqueAmt}</t:Cell><t:Cell>$Resource{uniqueAmtRate}</t:Cell><t:Cell>$Resource{uniqueFirstBidCount}</t:Cell><t:Cell>$Resource{uniqueFirstBidRate}</t:Cell><t:Cell>$Resource{uniqueFirstBidAmt}</t:Cell><t:Cell>$Resource{uniqueFirstBidAmtRate}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{puchaseCatagory_Row2}</t:Cell><t:Cell>$Resource{inviteType_Row2}</t:Cell><t:Cell>$Resource{inviteCount_Row2}</t:Cell><t:Cell>$Resource{inviteAmt_Row2}</t:Cell><t:Cell>$Resource{selectedCount_Row2}</t:Cell><t:Cell>$Resource{selectedRate_Row2}</t:Cell><t:Cell>$Resource{selectedAmt_Row2}</t:Cell><t:Cell>$Resource{selectedAmtRate_Row2}</t:Cell><t:Cell>$Resource{firstBid_Row2}</t:Cell><t:Cell>$Resource{secondBid_Row2}</t:Cell><t:Cell>$Resource{thirdBid_Row2}</t:Cell><t:Cell>$Resource{firstSignCount_Row2}</t:Cell><t:Cell>$Resource{firstSignRate_Row2}</t:Cell><t:Cell>$Resource{firstSignAmt_Row2}</t:Cell><t:Cell>$Resource{firstSignAmtCount_Row2}</t:Cell><t:Cell>$Resource{normalCount_Row2}</t:Cell><t:Cell>$Resource{normalRate_Row2}</t:Cell><t:Cell>$Resource{normalAmt_Row2}</t:Cell><t:Cell>$Resource{normalAmtRate_Row2}</t:Cell><t:Cell>$Resource{inquiryCount_Row2}</t:Cell><t:Cell>$Resource{inquiryRate_Row2}</t:Cell><t:Cell>$Resource{inquiryAmt_Row2}</t:Cell><t:Cell>$Resource{inquiryAmtRate_Row2}</t:Cell><t:Cell>$Resource{inquiryFirstBidCount_Row2}</t:Cell><t:Cell>$Resource{inquiryFirstBidCRate_Row2}</t:Cell><t:Cell>$Resource{inquiryFirstBidAmt_Row2}</t:Cell><t:Cell>$Resource{inquiryFirstBidAmtRate_Row2}</t:Cell><t:Cell>$Resource{uniqueCount_Row2}</t:Cell><t:Cell>$Resource{uniqueRate_Row2}</t:Cell><t:Cell>$Resource{uniqueAmt_Row2}</t:Cell><t:Cell>$Resource{uniqueAmtRate_Row2}</t:Cell><t:Cell>$Resource{uniqueFirstBidCount_Row2}</t:Cell><t:Cell>$Resource{uniqueFirstBidRate_Row2}</t:Cell><t:Cell>$Resource{uniqueFirstBidAmt_Row2}</t:Cell><t:Cell>$Resource{uniqueFirstBidAmtRate_Row2}</t:Cell></t:Row><t:Row t:name=\"header3\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{puchaseCatagory_Row3}</t:Cell><t:Cell>$Resource{inviteType_Row3}</t:Cell><t:Cell>$Resource{inviteCount_Row3}</t:Cell><t:Cell>$Resource{inviteAmt_Row3}</t:Cell><t:Cell>$Resource{selectedCount_Row3}</t:Cell><t:Cell>$Resource{selectedRate_Row3}</t:Cell><t:Cell>$Resource{selectedAmt_Row3}</t:Cell><t:Cell>$Resource{selectedAmtRate_Row3}</t:Cell><t:Cell>$Resource{firstBid_Row3}</t:Cell><t:Cell>$Resource{secondBid_Row3}</t:Cell><t:Cell>$Resource{thirdBid_Row3}</t:Cell><t:Cell>$Resource{firstSignCount_Row3}</t:Cell><t:Cell>$Resource{firstSignRate_Row3}</t:Cell><t:Cell>$Resource{firstSignAmt_Row3}</t:Cell><t:Cell>$Resource{firstSignAmtCount_Row3}</t:Cell><t:Cell>$Resource{normalCount_Row3}</t:Cell><t:Cell>$Resource{normalRate_Row3}</t:Cell><t:Cell>$Resource{normalAmt_Row3}</t:Cell><t:Cell>$Resource{normalAmtRate_Row3}</t:Cell><t:Cell>$Resource{inquiryCount_Row3}</t:Cell><t:Cell>$Resource{inquiryRate_Row3}</t:Cell><t:Cell>$Resource{inquiryAmt_Row3}</t:Cell><t:Cell>$Resource{inquiryAmtRate_Row3}</t:Cell><t:Cell>$Resource{inquiryFirstBidCount_Row3}</t:Cell><t:Cell>$Resource{inquiryFirstBidCRate_Row3}</t:Cell><t:Cell>$Resource{inquiryFirstBidAmt_Row3}</t:Cell><t:Cell>$Resource{inquiryFirstBidAmtRate_Row3}</t:Cell><t:Cell>$Resource{uniqueCount_Row3}</t:Cell><t:Cell>$Resource{uniqueRate_Row3}</t:Cell><t:Cell>$Resource{uniqueAmt_Row3}</t:Cell><t:Cell>$Resource{uniqueAmtRate_Row3}</t:Cell><t:Cell>$Resource{uniqueFirstBidCount_Row3}</t:Cell><t:Cell>$Resource{uniqueFirstBidRate_Row3}</t:Cell><t:Cell>$Resource{uniqueFirstBidAmt_Row3}</t:Cell><t:Cell>$Resource{uniqueFirstBidAmtRate_Row3}</t:Cell></t:Row><t:Row t:name=\"header4\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{puchaseCatagory_Row4}</t:Cell><t:Cell>$Resource{inviteType_Row4}</t:Cell><t:Cell>$Resource{inviteCount_Row4}</t:Cell><t:Cell>$Resource{inviteAmt_Row4}</t:Cell><t:Cell>$Resource{selectedCount_Row4}</t:Cell><t:Cell>$Resource{selectedRate_Row4}</t:Cell><t:Cell>$Resource{selectedAmt_Row4}</t:Cell><t:Cell>$Resource{selectedAmtRate_Row4}</t:Cell><t:Cell>$Resource{firstBid_Row4}</t:Cell><t:Cell>$Resource{secondBid_Row4}</t:Cell><t:Cell>$Resource{thirdBid_Row4}</t:Cell><t:Cell>$Resource{firstSignCount_Row4}</t:Cell><t:Cell>$Resource{firstSignRate_Row4}</t:Cell><t:Cell>$Resource{firstSignAmt_Row4}</t:Cell><t:Cell>$Resource{firstSignAmtCount_Row4}</t:Cell><t:Cell>$Resource{normalCount_Row4}</t:Cell><t:Cell>$Resource{normalRate_Row4}</t:Cell><t:Cell>$Resource{normalAmt_Row4}</t:Cell><t:Cell>$Resource{normalAmtRate_Row4}</t:Cell><t:Cell>$Resource{inquiryCount_Row4}</t:Cell><t:Cell>$Resource{inquiryRate_Row4}</t:Cell><t:Cell>$Resource{inquiryAmt_Row4}</t:Cell><t:Cell>$Resource{inquiryAmtRate_Row4}</t:Cell><t:Cell>$Resource{inquiryFirstBidCount_Row4}</t:Cell><t:Cell>$Resource{inquiryFirstBidCRate_Row4}</t:Cell><t:Cell>$Resource{inquiryFirstBidAmt_Row4}</t:Cell><t:Cell>$Resource{inquiryFirstBidAmtRate_Row4}</t:Cell><t:Cell>$Resource{uniqueCount_Row4}</t:Cell><t:Cell>$Resource{uniqueRate_Row4}</t:Cell><t:Cell>$Resource{uniqueAmt_Row4}</t:Cell><t:Cell>$Resource{uniqueAmtRate_Row4}</t:Cell><t:Cell>$Resource{uniqueFirstBidCount_Row4}</t:Cell><t:Cell>$Resource{uniqueFirstBidRate_Row4}</t:Cell><t:Cell>$Resource{uniqueFirstBidAmt_Row4}</t:Cell><t:Cell>$Resource{uniqueFirstBidAmtRate_Row4}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"3\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"3\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"2\" t:right=\"3\" /><t:Block t:top=\"1\" t:left=\"4\" t:bottom=\"1\" t:right=\"18\" /><t:Block t:top=\"2\" t:left=\"4\" t:bottom=\"3\" t:right=\"4\" /><t:Block t:top=\"2\" t:left=\"5\" t:bottom=\"3\" t:right=\"5\" /><t:Block t:top=\"2\" t:left=\"6\" t:bottom=\"3\" t:right=\"6\" /><t:Block t:top=\"2\" t:left=\"7\" t:bottom=\"3\" t:right=\"7\" /><t:Block t:top=\"2\" t:left=\"8\" t:bottom=\"3\" t:right=\"8\" /><t:Block t:top=\"2\" t:left=\"9\" t:bottom=\"3\" t:right=\"9\" /><t:Block t:top=\"2\" t:left=\"10\" t:bottom=\"3\" t:right=\"10\" /><t:Block t:top=\"2\" t:left=\"11\" t:bottom=\"2\" t:right=\"14\" /><t:Block t:top=\"2\" t:left=\"15\" t:bottom=\"2\" t:right=\"18\" /><t:Block t:top=\"2\" t:left=\"19\" t:bottom=\"3\" t:right=\"19\" /><t:Block t:top=\"2\" t:left=\"20\" t:bottom=\"3\" t:right=\"20\" /><t:Block t:top=\"2\" t:left=\"21\" t:bottom=\"3\" t:right=\"21\" /><t:Block t:top=\"2\" t:left=\"22\" t:bottom=\"3\" t:right=\"22\" /><t:Block t:top=\"2\" t:left=\"23\" t:bottom=\"2\" t:right=\"26\" /><t:Block t:top=\"1\" t:left=\"19\" t:bottom=\"1\" t:right=\"26\" /><t:Block t:top=\"2\" t:left=\"27\" t:bottom=\"3\" t:right=\"27\" /><t:Block t:top=\"2\" t:left=\"28\" t:bottom=\"3\" t:right=\"28\" /><t:Block t:top=\"2\" t:left=\"29\" t:bottom=\"3\" t:right=\"29\" /><t:Block t:top=\"2\" t:left=\"30\" t:bottom=\"3\" t:right=\"30\" /><t:Block t:top=\"2\" t:left=\"31\" t:bottom=\"2\" t:right=\"34\" /><t:Block t:top=\"1\" t:left=\"27\" t:bottom=\"1\" t:right=\"34\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"0\" t:right=\"34\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kDTable1.setFormatXml(resHelper.translateString("kDTable1",kDTable1StrXML));

        

        // actionFresh
        this.actionFresh.setAction((IItemAction)ActionProxyFactory.getProxy(actionWinRefresh, new Class[] { IItemAction.class }, getServiceContext()));		
        this.actionFresh.setText(resHelper.getString("actionFresh.text"));		
        this.actionFresh.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_refresh"));
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
        kDTable1.setBounds(new Rectangle(11, 17, 991, 601));
        this.add(kDTable1, new KDLayout.Constraints(11, 17, 991, 601, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));

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
        this.toolBar.add(actionFresh);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.supplier.report.SupplierWinInfoRptUIHandler";
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
     * output actionWinRefresh_actionPerformed method
     */
    public void actionWinRefresh_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionWinRefresh(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionWinRefresh() {
    	return false;
    }

    /**
     * output ActionWinRefresh class
     */     
    protected class ActionWinRefresh extends ItemAction {     
    
        public ActionWinRefresh()
        {
            this(null);
        }

        public ActionWinRefresh(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionWinRefresh.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionWinRefresh.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionWinRefresh.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSupplierWinInfoRptUI.this, "ActionWinRefresh", "actionWinRefresh_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.supplier.report", "SupplierWinInfoRptUI");
    }




}