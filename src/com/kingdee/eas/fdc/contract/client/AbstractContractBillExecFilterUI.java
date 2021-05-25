/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

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
public abstract class AbstractContractBillExecFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractContractBillExecFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProvider;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDateTo;
    protected com.kingdee.bos.ctrl.swing.KDPanel plDateType;
    protected com.kingdee.bos.ctrl.swing.KDPanel plSettleState;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkAllContract;
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
    protected com.kingdee.bos.ctrl.swing.KDButton btnContractTypeSelect;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDButton btnProjectSelect;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsPc;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtContractType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Provider;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkDateTo;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioByDay;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioByMonth;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioByQuarter;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioByYear;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup1;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioHasSettled;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioNoSettled;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioSettleAll;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup kDButtonGroup3;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiYearTo;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spiYearFrom;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox kDBizPromptBox1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox kDBizPromptBox2;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCurrency;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProject;
    /**
     * output class constructor
     */
    public AbstractContractBillExecFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractContractBillExecFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contContractType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contProvider = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDateFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDateTo = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.plDateType = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.plSettleState = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.chkAllContract = new com.kingdee.bos.ctrl.swing.KDCheckBox();
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
        this.btnContractTypeSelect = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnProjectSelect = new com.kingdee.bos.ctrl.swing.KDButton();
        this.cbIsPc = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.txtContractType = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7Provider = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkDateFrom = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkDateTo = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.radioByDay = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioByMonth = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioByQuarter = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioByYear = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDButtonGroup1 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.radioHasSettled = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioNoSettled = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioSettleAll = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDButtonGroup3 = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.spiYearTo = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.spiYearFrom = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.kDBizPromptBox1 = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDBizPromptBox2 = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCurrency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contContractType.setName("contContractType");
        this.contProvider.setName("contProvider");
        this.contDateFrom.setName("contDateFrom");
        this.contDateTo.setName("contDateTo");
        this.plDateType.setName("plDateType");
        this.plSettleState.setName("plSettleState");
        this.chkAllContract.setName("chkAllContract");
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
        this.btnContractTypeSelect.setName("btnContractTypeSelect");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.contCurrency.setName("contCurrency");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.btnProjectSelect.setName("btnProjectSelect");
        this.cbIsPc.setName("cbIsPc");
        this.txtContractType.setName("txtContractType");
        this.f7Provider.setName("f7Provider");
        this.pkDateFrom.setName("pkDateFrom");
        this.pkDateTo.setName("pkDateTo");
        this.radioByDay.setName("radioByDay");
        this.radioByMonth.setName("radioByMonth");
        this.radioByQuarter.setName("radioByQuarter");
        this.radioByYear.setName("radioByYear");
        this.radioHasSettled.setName("radioHasSettled");
        this.radioNoSettled.setName("radioNoSettled");
        this.radioSettleAll.setName("radioSettleAll");
        this.spiYearTo.setName("spiYearTo");
        this.spiYearFrom.setName("spiYearFrom");
        this.kDBizPromptBox1.setName("kDBizPromptBox1");
        this.kDBizPromptBox2.setName("kDBizPromptBox2");
        this.prmtCurrency.setName("prmtCurrency");
        this.txtProject.setName("txtProject");
        // CustomerQueryPanel
        // contContractType		
        this.contContractType.setBoundLabelText(resHelper.getString("contContractType.boundLabelText"));		
        this.contContractType.setBoundLabelLength(100);		
        this.contContractType.setBoundLabelUnderline(true);
        // contProvider		
        this.contProvider.setBoundLabelText(resHelper.getString("contProvider.boundLabelText"));		
        this.contProvider.setBoundLabelLength(100);		
        this.contProvider.setBoundLabelUnderline(true);
        // contDateFrom		
        this.contDateFrom.setBoundLabelLength(100);		
        this.contDateFrom.setBoundLabelUnderline(true);		
        this.contDateFrom.setBoundLabelText(resHelper.getString("contDateFrom.boundLabelText"));
        // contDateTo		
        this.contDateTo.setBoundLabelText(resHelper.getString("contDateTo.boundLabelText"));		
        this.contDateTo.setBoundLabelLength(100);		
        this.contDateTo.setBoundLabelUnderline(true);
        // plDateType		
        this.plDateType.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("plDateType.border.title")));
        // plSettleState		
        this.plSettleState.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("plSettleState.border.title")));
        // chkAllContract		
        this.chkAllContract.setText(resHelper.getString("chkAllContract.text"));
        this.chkAllContract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkAllContract_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
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
        // btnContractTypeSelect		
        this.btnContractTypeSelect.setText(resHelper.getString("btnContractTypeSelect.text"));
        this.btnContractTypeSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnContractTypeSelectProjectSelect_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // contCurrency		
        this.contCurrency.setBoundLabelText(resHelper.getString("contCurrency.boundLabelText"));		
        this.contCurrency.setBoundLabelLength(100);		
        this.contCurrency.setBoundLabelUnderline(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // btnProjectSelect		
        this.btnProjectSelect.setText(resHelper.getString("btnProjectSelect.text"));
        this.btnProjectSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnProjectSelect_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // cbIsPc		
        this.cbIsPc.setText(resHelper.getString("cbIsPc.text"));
        // txtContractType		
        this.txtContractType.setEnabled(false);
        // f7Provider		
        this.f7Provider.setEditable(true);		
        this.f7Provider.setDisplayFormat("$name$");		
        this.f7Provider.setEditFormat("$number$");		
        this.f7Provider.setCommitFormat("$number$");
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
        // radioHasSettled		
        this.radioHasSettled.setText(resHelper.getString("radioHasSettled.text"));
        // radioNoSettled		
        this.radioNoSettled.setText(resHelper.getString("radioNoSettled.text"));
        // radioSettleAll		
        this.radioSettleAll.setText(resHelper.getString("radioSettleAll.text"));
        // kDButtonGroup3
        this.kDButtonGroup3.add(this.radioHasSettled);
        this.kDButtonGroup3.add(this.radioNoSettled);
        this.kDButtonGroup3.add(this.radioSettleAll);
        // spiYearTo
        // spiYearFrom
        // kDBizPromptBox1		
        this.kDBizPromptBox1.setEditable(true);		
        this.kDBizPromptBox1.setDisplayFormat("$name$");		
        this.kDBizPromptBox1.setEditFormat("$number$");		
        this.kDBizPromptBox1.setCommitFormat("$number$");
        // kDBizPromptBox2		
        this.kDBizPromptBox2.setEditable(true);		
        this.kDBizPromptBox2.setDisplayFormat("$name$");		
        this.kDBizPromptBox2.setEditFormat("$number$");		
        this.kDBizPromptBox2.setCommitFormat("$number$");
        // prmtCurrency		
        this.prmtCurrency.setDisplayFormat("$name$");		
        this.prmtCurrency.setEditFormat("$number$");		
        this.prmtCurrency.setCommitFormat("$number$");		
        this.prmtCurrency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");
        // txtProject		
        this.txtProject.setEnabled(false);
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
        this.setBounds(new Rectangle(10, 10, 400, 350));
        this.setLayout(null);
        contContractType.setBounds(new Rectangle(15, 30, 270, 19));
        this.add(contContractType, null);
        contProvider.setBounds(new Rectangle(15, 51, 270, 19));
        this.add(contProvider, null);
        contDateFrom.setBounds(new Rectangle(15, 73, 270, 19));
        this.add(contDateFrom, null);
        contDateTo.setBounds(new Rectangle(15, 95, 270, 19));
        this.add(contDateTo, null);
        plDateType.setBounds(new Rectangle(11, 151, 91, 174));
        this.add(plDateType, null);
        plSettleState.setBounds(new Rectangle(118, 151, 103, 174));
        this.add(plSettleState, null);
        chkAllContract.setBounds(new Rectangle(13, 328, 214, 19));
        this.add(chkAllContract, null);
        contYearTo.setBounds(new Rectangle(15, 95, 180, 19));
        this.add(contYearTo, null);
        contYearFrom.setBounds(new Rectangle(15, 73, 180, 19));
        this.add(contYearFrom, null);
        spiMonthFrom.setBounds(new Rectangle(218, 73, 49, 19));
        this.add(spiMonthFrom, null);
        spiMonthTo.setBounds(new Rectangle(218, 95, 49, 19));
        this.add(spiMonthTo, null);
        lblQuarterFrom.setBounds(new Rectangle(279, 73, 29, 19));
        this.add(lblQuarterFrom, null);
        lblQuarterTo.setBounds(new Rectangle(279, 95, 29, 19));
        this.add(lblQuarterTo, null);
        lblMonthFrom.setBounds(new Rectangle(279, 73, 29, 19));
        this.add(lblMonthFrom, null);
        lblMonthTo.setBounds(new Rectangle(279, 95, 29, 19));
        this.add(lblMonthTo, null);
        lblYearFrom.setBounds(new Rectangle(200, 73, 29, 19));
        this.add(lblYearFrom, null);
        lblYearTo.setBounds(new Rectangle(200, 95, 29, 19));
        this.add(lblYearTo, null);
        btnContractTypeSelect.setBounds(new Rectangle(293, 31, 69, 19));
        this.add(btnContractTypeSelect, null);
        kDLabelContainer1.setBounds(new Rectangle(15, 51, 270, 19));
        this.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(15, 51, 270, 19));
        this.add(kDLabelContainer2, null);
        contCurrency.setBounds(new Rectangle(15, 117, 270, 19));
        this.add(contCurrency, null);
        kDLabelContainer3.setBounds(new Rectangle(15, 8, 270, 19));
        this.add(kDLabelContainer3, null);
        btnProjectSelect.setBounds(new Rectangle(293, 7, 69, 19));
        this.add(btnProjectSelect, null);
        cbIsPc.setBounds(new Rectangle(244, 157, 140, 19));
        this.add(cbIsPc, null);
        //contContractType
        contContractType.setBoundEditor(txtContractType);
        //contProvider
        contProvider.setBoundEditor(f7Provider);
        //contDateFrom
        contDateFrom.setBoundEditor(pkDateFrom);
        //contDateTo
        contDateTo.setBoundEditor(pkDateTo);
        //plDateType
        plDateType.setLayout(null);        radioByDay.setBounds(new Rectangle(18, 19, 61, 19));
        plDateType.add(radioByDay, null);
        radioByMonth.setBounds(new Rectangle(18, 45, 61, 19));
        plDateType.add(radioByMonth, null);
        radioByQuarter.setBounds(new Rectangle(18, 71, 61, 19));
        plDateType.add(radioByQuarter, null);
        radioByYear.setBounds(new Rectangle(18, 97, 61, 19));
        plDateType.add(radioByYear, null);
        //plSettleState
        plSettleState.setLayout(null);        radioHasSettled.setBounds(new Rectangle(22, 24, 64, 19));
        plSettleState.add(radioHasSettled, null);
        radioNoSettled.setBounds(new Rectangle(22, 55, 64, 19));
        plSettleState.add(radioNoSettled, null);
        radioSettleAll.setBounds(new Rectangle(22, 85, 52, 19));
        plSettleState.add(radioSettleAll, null);
        //contYearTo
        contYearTo.setBoundEditor(spiYearTo);
        //contYearFrom
        contYearFrom.setBoundEditor(spiYearFrom);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(kDBizPromptBox1);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(kDBizPromptBox2);
        //contCurrency
        contCurrency.setBoundEditor(prmtCurrency);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtProject);

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
	    return "com.kingdee.eas.fdc.contract.app.ContractBillExecFilterUIHandler";
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
     * output chkAllContract_actionPerformed method
     */
    protected void chkAllContract_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnContractTypeSelectProjectSelect_actionPerformed method
     */
    protected void btnContractTypeSelectProjectSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnProjectSelect_actionPerformed method
     */
    protected void btnProjectSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "ContractBillExecFilterUI");
    }




}