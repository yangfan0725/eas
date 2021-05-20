/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

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
public abstract class AbstractSpecialOprationStatFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSpecialOprationStatFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDateTo;
    protected com.kingdee.bos.ctrl.swing.KDPanel plDateType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contYearTo;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contYearFrom;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiMonthFrom;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiMonthTo;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblQuarterFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblQuarterTo;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblMonthFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblMonthTo;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblYearFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblYearTo;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsShowAll;
    protected com.kingdee.bos.ctrl.swing.KDPanel plClassStatistics;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkDateTo;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioByDay;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioByMonth;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioByQuarter;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioByYear;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup1;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiYearTo;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiYearFrom;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsCheckOut;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsSwap;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsChange;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsRename;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsAdjust;
    /**
     * output class constructor
     */
    public AbstractSpecialOprationStatFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSpecialOprationStatFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contDateFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDateTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.plDateType = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contYearTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contYearFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.spiMonthFrom = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spiMonthTo = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.lblQuarterFrom = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblQuarterTo = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblMonthFrom = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblMonthTo = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblYearFrom = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.lblYearTo = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.chkIsShowAll = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.plClassStatistics = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.pkDateFrom = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkDateTo = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.radioByDay = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioByMonth = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioByQuarter = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioByYear = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDButtonGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.spiYearTo = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spiYearFrom = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.chkIsCheckOut = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsSwap = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsChange = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsRename = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsAdjust = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contDateFrom.setName("contDateFrom");
        this.contDateTo.setName("contDateTo");
        this.plDateType.setName("plDateType");
        this.contYearTo.setName("contYearTo");
        this.contYearFrom.setName("contYearFrom");
        this.spiMonthFrom.setName("spiMonthFrom");
        this.spiMonthTo.setName("spiMonthTo");
        this.lblQuarterFrom.setName("lblQuarterFrom");
        this.lblQuarterTo.setName("lblQuarterTo");
        this.lblMonthFrom.setName("lblMonthFrom");
        this.lblMonthTo.setName("lblMonthTo");
        this.lblYearFrom.setName("lblYearFrom");
        this.lblYearTo.setName("lblYearTo");
        this.chkIsShowAll.setName("chkIsShowAll");
        this.plClassStatistics.setName("plClassStatistics");
        this.pkDateFrom.setName("pkDateFrom");
        this.pkDateTo.setName("pkDateTo");
        this.radioByDay.setName("radioByDay");
        this.radioByMonth.setName("radioByMonth");
        this.radioByQuarter.setName("radioByQuarter");
        this.radioByYear.setName("radioByYear");
        this.spiYearTo.setName("spiYearTo");
        this.spiYearFrom.setName("spiYearFrom");
        this.chkIsCheckOut.setName("chkIsCheckOut");
        this.chkIsSwap.setName("chkIsSwap");
        this.chkIsChange.setName("chkIsChange");
        this.chkIsRename.setName("chkIsRename");
        this.chkIsAdjust.setName("chkIsAdjust");
        // CustomerQueryPanel
        // contDateFrom		
        this.contDateFrom.setBoundLabelText(resHelper.getString("contDateFrom.boundLabelText"));		
        this.contDateFrom.setBoundLabelLength(100);		
        this.contDateFrom.setBoundLabelUnderline(true);
        // contDateTo		
        this.contDateTo.setBoundLabelText(resHelper.getString("contDateTo.boundLabelText"));		
        this.contDateTo.setBoundLabelLength(100);		
        this.contDateTo.setBoundLabelUnderline(true);
        // plDateType		
        this.plDateType.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("plDateType.border.title")));
        // contYearTo		
        this.contYearTo.setBoundLabelText(resHelper.getString("contYearTo.boundLabelText"));		
        this.contYearTo.setBoundLabelLength(100);		
        this.contYearTo.setBoundLabelUnderline(true);
        // contYearFrom		
        this.contYearFrom.setBoundLabelText(resHelper.getString("contYearFrom.boundLabelText"));		
        this.contYearFrom.setBoundLabelLength(100);		
        this.contYearFrom.setBoundLabelUnderline(true);
        // spiMonthFrom
        // spiMonthTo
        // lblQuarterFrom		
        this.lblQuarterFrom.setText(resHelper.getString("lblQuarterFrom.text"));
        // lblQuarterTo		
        this.lblQuarterTo.setText(resHelper.getString("lblQuarterTo.text"));
        // lblMonthFrom		
        this.lblMonthFrom.setText(resHelper.getString("lblMonthFrom.text"));
        // lblMonthTo		
        this.lblMonthTo.setText(resHelper.getString("lblMonthTo.text"));
        // lblYearFrom		
        this.lblYearFrom.setText(resHelper.getString("lblYearFrom.text"));
        // lblYearTo		
        this.lblYearTo.setText(resHelper.getString("lblYearTo.text"));
        // chkIsShowAll		
        this.chkIsShowAll.setText(resHelper.getString("chkIsShowAll.text"));
        this.chkIsShowAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsShowAll_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // plClassStatistics		
        this.plClassStatistics.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("plClassStatistics.border.title")));
        // pkDateFrom
        // pkDateTo
        // radioByDay		
        this.radioByDay.setText(resHelper.getString("radioByDay.text"));
        this.radioByDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    radioByDay_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // radioByMonth		
        this.radioByMonth.setText(resHelper.getString("radioByMonth.text"));
        this.radioByMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    radioByMonth_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // radioByQuarter		
        this.radioByQuarter.setText(resHelper.getString("radioByQuarter.text"));
        this.radioByQuarter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    radioByQuarter_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // radioByYear		
        this.radioByYear.setText(resHelper.getString("radioByYear.text"));
        this.radioByYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    radioByYear_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDButtonGroup1
        this.kDButtonGroup1.add(this.radioByDay);
        this.kDButtonGroup1.add(this.radioByMonth);
        this.kDButtonGroup1.add(this.radioByQuarter);
        this.kDButtonGroup1.add(this.radioByYear);
        // spiYearTo
        // spiYearFrom
        // chkIsCheckOut		
        this.chkIsCheckOut.setText(resHelper.getString("chkIsCheckOut.text"));
        // chkIsSwap		
        this.chkIsSwap.setText(resHelper.getString("chkIsSwap.text"));		
        this.chkIsSwap.setEnabled(false);
        // chkIsChange		
        this.chkIsChange.setText(resHelper.getString("chkIsChange.text"));		
        this.chkIsChange.setEnabled(false);
        // chkIsRename		
        this.chkIsRename.setText(resHelper.getString("chkIsRename.text"));		
        this.chkIsRename.setEnabled(false);
        // chkIsAdjust		
        this.chkIsAdjust.setText(resHelper.getString("chkIsAdjust.text"));		
        this.chkIsAdjust.setEnabled(false);
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
        this.setBounds(new Rectangle(10, 10, 400, 290));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 400, 290));
        contDateFrom.setBounds(new Rectangle(10, 14, 270, 19));
        this.add(contDateFrom, new KDLayout.Constraints(10, 14, 270, 19, 0));
        contDateTo.setBounds(new Rectangle(10, 36, 270, 19));
        this.add(contDateTo, new KDLayout.Constraints(10, 36, 270, 19, 0));
        plDateType.setBounds(new Rectangle(9, 62, 361, 55));
        this.add(plDateType, new KDLayout.Constraints(9, 62, 361, 55, 0));
        contYearTo.setBounds(new Rectangle(10, 36, 180, 19));
        this.add(contYearTo, new KDLayout.Constraints(10, 36, 180, 19, 0));
        contYearFrom.setBounds(new Rectangle(10, 14, 180, 19));
        this.add(contYearFrom, new KDLayout.Constraints(10, 14, 180, 19, 0));
        spiMonthFrom.setBounds(new Rectangle(213, 14, 49, 19));
        this.add(spiMonthFrom, new KDLayout.Constraints(213, 14, 49, 19, 0));
        spiMonthTo.setBounds(new Rectangle(213, 36, 49, 19));
        this.add(spiMonthTo, new KDLayout.Constraints(213, 36, 49, 19, 0));
        lblQuarterFrom.setBounds(new Rectangle(274, 14, 29, 19));
        this.add(lblQuarterFrom, new KDLayout.Constraints(274, 14, 29, 19, 0));
        lblQuarterTo.setBounds(new Rectangle(274, 36, 29, 19));
        this.add(lblQuarterTo, new KDLayout.Constraints(274, 36, 29, 19, 0));
        lblMonthFrom.setBounds(new Rectangle(274, 14, 29, 19));
        this.add(lblMonthFrom, new KDLayout.Constraints(274, 14, 29, 19, 0));
        lblMonthTo.setBounds(new Rectangle(274, 36, 29, 19));
        this.add(lblMonthTo, new KDLayout.Constraints(274, 36, 29, 19, 0));
        lblYearFrom.setBounds(new Rectangle(195, 14, 29, 19));
        this.add(lblYearFrom, new KDLayout.Constraints(195, 14, 29, 19, 0));
        lblYearTo.setBounds(new Rectangle(195, 36, 29, 19));
        this.add(lblYearTo, new KDLayout.Constraints(195, 36, 29, 19, 0));
        chkIsShowAll.setBounds(new Rectangle(15, 122, 194, 19));
        this.add(chkIsShowAll, new KDLayout.Constraints(15, 122, 194, 19, 0));
        plClassStatistics.setBounds(new Rectangle(9, 148, 361, 129));
        this.add(plClassStatistics, new KDLayout.Constraints(9, 148, 361, 129, 0));
        //contDateFrom
        contDateFrom.setBoundEditor(pkDateFrom);
        //contDateTo
        contDateTo.setBoundEditor(pkDateTo);
        //plDateType
        plDateType.setLayout(null);        radioByDay.setBounds(new Rectangle(18, 19, 61, 19));
        plDateType.add(radioByDay, null);
        radioByMonth.setBounds(new Rectangle(95, 19, 61, 19));
        plDateType.add(radioByMonth, null);
        radioByQuarter.setBounds(new Rectangle(168, 19, 61, 19));
        plDateType.add(radioByQuarter, null);
        radioByYear.setBounds(new Rectangle(261, 19, 61, 19));
        plDateType.add(radioByYear, null);
        //contYearTo
        contYearTo.setBoundEditor(spiYearTo);
        //contYearFrom
        contYearFrom.setBoundEditor(spiYearFrom);
        //plClassStatistics
        plClassStatistics.setLayout(null);        chkIsCheckOut.setBounds(new Rectangle(22, 21, 140, 19));
        plClassStatistics.add(chkIsCheckOut, null);
        chkIsSwap.setBounds(new Rectangle(204, 20, 140, 19));
        plClassStatistics.add(chkIsSwap, null);
        chkIsChange.setBounds(new Rectangle(22, 57, 140, 19));
        plClassStatistics.add(chkIsChange, null);
        chkIsRename.setBounds(new Rectangle(204, 59, 140, 19));
        plClassStatistics.add(chkIsRename, null);
        chkIsAdjust.setBounds(new Rectangle(22, 94, 140, 19));
        plClassStatistics.add(chkIsAdjust, null);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.SpecialOprationStatFilterUIHandler";
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
     * output chkIsShowAll_actionPerformed method
     */
    protected void chkIsShowAll_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output radioByDay_actionPerformed method
     */
    protected void radioByDay_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output radioByMonth_actionPerformed method
     */
    protected void radioByMonth_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output radioByQuarter_actionPerformed method
     */
    protected void radioByQuarter_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output radioByYear_actionPerformed method
     */
    protected void radioByYear_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "SpecialOprationStatFilterUI");
    }




}