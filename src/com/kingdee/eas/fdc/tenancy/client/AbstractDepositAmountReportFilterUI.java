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
public abstract class AbstractDepositAmountReportFilterUI extends com.kingdee.eas.framework.report.client.CommRptBaseConditionUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractDepositAmountReportFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoom;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chsZero;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMoneyDefine;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTenancyState;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkAll;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkExecuting;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkQuitTenancy;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkExpiration;
    protected com.kingdee.bos.ctrl.swing.KDLabel contSubAmountState;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chsAll;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chsNotZero;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRoom;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCustomer;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMoneyDefine;
    /**
     * output class constructor
     */
    public AbstractDepositAmountReportFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractDepositAmountReportFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chsZero = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMoneyDefine = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTenancyState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkAll = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkExecuting = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkQuitTenancy = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkExpiration = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contSubAmountState = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.chsAll = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chsNotZero = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.prmtRoom = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCustomer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtMoneyDefine = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contRoom.setName("contRoom");
        this.chsZero.setName("chsZero");
        this.contCustomer.setName("contCustomer");
        this.contMoneyDefine.setName("contMoneyDefine");
        this.contTenancyState.setName("contTenancyState");
        this.chkAll.setName("chkAll");
        this.chkExecuting.setName("chkExecuting");
        this.chkQuitTenancy.setName("chkQuitTenancy");
        this.chkExpiration.setName("chkExpiration");
        this.contSubAmountState.setName("contSubAmountState");
        this.chsAll.setName("chsAll");
        this.chsNotZero.setName("chsNotZero");
        this.prmtRoom.setName("prmtRoom");
        this.prmtCustomer.setName("prmtCustomer");
        this.prmtMoneyDefine.setName("prmtMoneyDefine");
        // CustomerQueryPanel
        // contRoom		
        this.contRoom.setBoundLabelText(resHelper.getString("contRoom.boundLabelText"));		
        this.contRoom.setBoundLabelLength(100);		
        this.contRoom.setBoundLabelUnderline(true);
        // chsZero		
        this.chsZero.setText(resHelper.getString("chsZero.text"));
        this.chsZero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chsZero_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.chsZero.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    chsZero_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contCustomer		
        this.contCustomer.setBoundLabelText(resHelper.getString("contCustomer.boundLabelText"));		
        this.contCustomer.setBoundLabelLength(100);		
        this.contCustomer.setBoundLabelUnderline(true);
        // contMoneyDefine		
        this.contMoneyDefine.setBoundLabelText(resHelper.getString("contMoneyDefine.boundLabelText"));		
        this.contMoneyDefine.setBoundLabelLength(100);		
        this.contMoneyDefine.setBoundLabelUnderline(true);
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
        // contSubAmountState		
        this.contSubAmountState.setText(resHelper.getString("contSubAmountState.text"));
        // chsAll		
        this.chsAll.setText(resHelper.getString("chsAll.text"));
        this.chsAll.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    chsAll_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // chsNotZero		
        this.chsNotZero.setText(resHelper.getString("chsNotZero.text"));
        this.chsNotZero.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    chsNotZero_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtRoom		
        this.prmtRoom.setEditFormat("$name$");		
        this.prmtRoom.setDisplayFormat("$name$");		
        this.prmtRoom.setCommitFormat("$name$");		
        this.prmtRoom.setEnabledMultiSelection(true);
        // prmtCustomer
        // prmtMoneyDefine
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
        this.setBounds(new Rectangle(10, 10, 600, 150));
        this.setLayout(null);
        contRoom.setBounds(new Rectangle(27, 29, 552, 19));
        this.add(contRoom, null);
        chsZero.setBounds(new Rectangle(250, 123, 78, 19));
        this.add(chsZero, null);
        contCustomer.setBounds(new Rectangle(27, 51, 552, 19));
        this.add(contCustomer, null);
        contMoneyDefine.setBounds(new Rectangle(27, 73, 552, 19));
        this.add(contMoneyDefine, null);
        contTenancyState.setBounds(new Rectangle(27, 97, 81, 19));
        this.add(contTenancyState, null);
        chkAll.setBounds(new Rectangle(141, 100, 78, 19));
        this.add(chkAll, null);
        chkExecuting.setBounds(new Rectangle(250, 100, 78, 19));
        this.add(chkExecuting, null);
        chkQuitTenancy.setBounds(new Rectangle(359, 100, 78, 19));
        this.add(chkQuitTenancy, null);
        chkExpiration.setBounds(new Rectangle(468, 100, 78, 19));
        this.add(chkExpiration, null);
        contSubAmountState.setBounds(new Rectangle(27, 123, 100, 19));
        this.add(contSubAmountState, null);
        chsAll.setBounds(new Rectangle(141, 123, 78, 19));
        this.add(chsAll, null);
        chsNotZero.setBounds(new Rectangle(359, 123, 118, 19));
        this.add(chsNotZero, null);
        //contRoom
        contRoom.setBoundEditor(prmtRoom);
        //contCustomer
        contCustomer.setBoundEditor(prmtCustomer);
        //contMoneyDefine
        contMoneyDefine.setBoundEditor(prmtMoneyDefine);

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
	    return "com.kingdee.eas.fdc.tenancy.app.DepositAmountReportFilterUIHandler";
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
     * output chsZero_actionPerformed method
     */
    protected void chsZero_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output chsZero_stateChanged method
     */
    protected void chsZero_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
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
     * output chsAll_stateChanged method
     */
    protected void chsAll_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output chsNotZero_stateChanged method
     */
    protected void chsNotZero_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "DepositAmountReportFilterUI");
    }




}