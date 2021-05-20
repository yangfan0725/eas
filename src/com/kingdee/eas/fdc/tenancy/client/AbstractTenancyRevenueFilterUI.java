/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

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
public abstract class AbstractTenancyRevenueFilterUI extends com.kingdee.eas.framework.report.client.CommRptBaseConditionUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTenancyRevenueFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenancybill;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenancyState;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkAll;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkExecuting;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkQuitTenancy;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkExpiration;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtTenancybill;
    /**
     * output class constructor
     */
    public AbstractTenancyRevenueFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractTenancyRevenueFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contTenancybill = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTenancyState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkAll = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkExecuting = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkQuitTenancy = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkExpiration = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.prmtTenancybill = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contTenancybill.setName("contTenancybill");
        this.contTenancyState.setName("contTenancyState");
        this.chkAll.setName("chkAll");
        this.chkExecuting.setName("chkExecuting");
        this.chkQuitTenancy.setName("chkQuitTenancy");
        this.chkExpiration.setName("chkExpiration");
        this.prmtTenancybill.setName("prmtTenancybill");
        // CustomerQueryPanel
        // contTenancybill		
        this.contTenancybill.setBoundLabelText(resHelper.getString("contTenancybill.boundLabelText"));		
        this.contTenancybill.setBoundLabelUnderline(true);		
        this.contTenancybill.setBoundLabelLength(100);
        // contTenancyState		
        this.contTenancyState.setBoundLabelText(resHelper.getString("contTenancyState.boundLabelText"));
        // chkAll		
        this.chkAll.setText(resHelper.getString("chkAll.text"));
        this.chkAll.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    chkAll_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // chkExecuting		
        this.chkExecuting.setText(resHelper.getString("chkExecuting.text"));
        this.chkExecuting.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    chkExecuting_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // chkQuitTenancy		
        this.chkQuitTenancy.setText(resHelper.getString("chkQuitTenancy.text"));
        this.chkQuitTenancy.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    chkQuitTenancy_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // chkExpiration		
        this.chkExpiration.setText(resHelper.getString("chkExpiration.text"));
        this.chkExpiration.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    chkExpiration_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtTenancybill		
        this.prmtTenancybill.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.TenancyBillQuery");		
        this.prmtTenancybill.setDisplayFormat("$name$");		
        this.prmtTenancybill.setEditFormat("$name$");
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
        this.setBounds(new Rectangle(10, 10, 600, 160));
        this.setLayout(null);
        contTenancybill.setBounds(new Rectangle(24, 13, 535, 19));
        this.add(contTenancybill, null);
        contTenancyState.setBounds(new Rectangle(24, 63, 81, 19));
        this.add(contTenancyState, null);
        chkAll.setBounds(new Rectangle(136, 66, 78, 19));
        this.add(chkAll, null);
        chkExecuting.setBounds(new Rectangle(245, 66, 78, 19));
        this.add(chkExecuting, null);
        chkQuitTenancy.setBounds(new Rectangle(354, 66, 78, 19));
        this.add(chkQuitTenancy, null);
        chkExpiration.setBounds(new Rectangle(463, 66, 78, 19));
        this.add(chkExpiration, null);
        //contTenancybill
        contTenancybill.setBoundEditor(prmtTenancybill);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.tenancy.app.TenancyRevenueFilterUIHandler";
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
     * output chkAll_stateChanged method
     */
    protected void chkAll_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output chkExecuting_stateChanged method
     */
    protected void chkExecuting_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output chkQuitTenancy_stateChanged method
     */
    protected void chkQuitTenancy_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output chkExpiration_stateChanged method
     */
    protected void chkExpiration_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "TenancyRevenueFilterUI");
    }




}