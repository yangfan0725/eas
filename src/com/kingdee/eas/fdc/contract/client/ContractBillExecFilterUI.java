/*jadclipse*/package com.kingdee.eas.fdc.contract.client;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDLabel;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDRadioButton;
import com.kingdee.bos.ctrl.swing.KDSpinner;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ICurrency;
import com.kingdee.eas.basedata.master.cssp.ISupplier;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.SpinnerNumberModel;
import javax.swing.tree.TreeModel;
public class ContractBillExecFilterUI extends AbstractContractBillExecFilterUI
{
            public ContractBillExecFilterUI(ListUI listUI, ItemAction actionListOnLoad)
                throws Exception
            {
















































































































































































































































































































































































































































































































/* 545*/        hasCurrency = false;
/* <-MISALIGNED-> */ /*  96*/        this.listUI = listUI;
/* <-MISALIGNED-> */ /*  97*/        this.actionListOnLoad = actionListOnLoad;
/* <-MISALIGNED-> */ /*  99*/        pkdate = FDCClientHelper.getCompanyCurrentDate();
            }
            protected void btnContractTypeSelectProjectSelect_actionPerformed(java.awt.event.ActionEvent e)
                throws Exception
            {
/* <-MISALIGNED-> */ /* 103*/        if(contractTypeSelectDlg == null)
/* <-MISALIGNED-> */ /* 104*/            initContractTypeDlg(null);
/* <-MISALIGNED-> */ /* 106*/        Object object = contractTypeSelectDlg.showDialog();
/* <-MISALIGNED-> */ /* 107*/        setContractTypeByTree(object);
/* <-MISALIGNED-> */ /* 108*/        super.btnContractTypeSelectProjectSelect_actionPerformed(e);
            }
            private void initProjectDlg(String projectIds[])
            throws Exception
        {
/* 333*/        ProjectTreeBuilder builder = new ProjectTreeBuilder(true);
/* 334*/        KDTree tree = new KDTree();
/* 335*/        builder.build(listUI, tree, actionListOnLoad);

/* 341*/        TreeModel model = tree.getModel();
/* 342*/        FDCHelper.setTreeForbidNode((DefaultKingdeeTreeNode)model.getRoot(), "isIsEnabled", Boolean.FALSE);

/* 344*/        projectSelectDlg = new TreeSelectDialog(this, model, "getId,toString", projectIds);
        }
            private void setProjectByTree(Object object)
            {
/* 600*/        List projectIdList = new ArrayList();
/* 601*/        if(object != null)
                {/* 602*/            List projectList = (List)object;
/* 603*/            String text = "";
/* 604*/            for(int i = 0; i < projectList.size(); i++)
                    {/* 605*/                if(!(projectList.get(i) instanceof ProjectInfo))
/* 606*/                    continue;/* 606*/                ProjectInfo project = (ProjectInfo)projectList.get(i);
/* 607*/                if(project.isIsLeaf())
                        {/* 608*/                    if(!text.equals(""))
/* 609*/                        text = (new StringBuilder()).append(text).append(",").toString();

/* 611*/                    text = (new StringBuilder()).append(text).append(project.getName()).toString();
                        }
/* 613*/                projectIdList.add(project.getId().toString());
                    }

/* 616*/            txtProject.setText(text);
/* 617*/            Object ids[] = projectIdList.toArray(new String[0]);
/* 618*/            if(FDCHelper.isEmpty(ids))
/* 619*/                txtProject.setUserObject(null);

/* 621*/            else/* 621*/                txtProject.setUserObject(((Object) (ids)));
                }
            }
            private TreeSelectDialog projectSelectDlg;
            protected void btnProjectSelect_actionPerformed(java.awt.event.ActionEvent e)
            throws Exception
        {
/* 101*/        if(projectSelectDlg == null)
/* 102*/            initProjectDlg(null);

/* 104*/        Object object = projectSelectDlg.showDialog();
/* 105*/        setProjectByTree(object);
/* 106*/        super.btnProjectSelect_actionPerformed(e);
        }
            public void clear()
            {
            	this.cbIsPc.setSelected(true);
/* <-MISALIGNED-> */ /* 112*/        contractTypeSelectDlg = null;
/* <-MISALIGNED-> */ /* 113*/        txtContractType.setText(null);
/* <-MISALIGNED-> */ /* 114*/        txtContractType.setUserObject(null);
/* <-MISALIGNED-> */ /* 116*/        f7Provider.setData(null);
/* <-MISALIGNED-> */ /* 117*/        pkDateFrom.setValue(pkdate[0]);
/* <-MISALIGNED-> */ /* 118*/        pkDateTo.setValue(pkdate[1]);
/* <-MISALIGNED-> */ /* 119*/        radioByDay.setSelected(true);
/* <-MISALIGNED-> */ /* 120*/        radioSettleAll.setSelected(true);
/* <-MISALIGNED-> */ /* 121*/        chkAllContract.setSelected(false);
/* <-MISALIGNED-> */ /* 122*/        f7Provider.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");
/* <-MISALIGNED-> */ /* 123*/        initControlByDateType();
            }
            public FilterInfo getFilterInfo()
            {
/* <-MISALIGNED-> */ /* 127*/        FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
/* <-MISALIGNED-> */ /* 129*/        FilterInfo filter = new FilterInfo();
/* <-MISALIGNED-> */ /* 132*/        if(para.isNotNull("contractTypeIds"))
/* <-MISALIGNED-> */ /* 133*/            filter.getFilterItems().add(new FilterItemInfo("contractType.id", FDCHelper.getSetByArray(para.getStringArray("contractTypeIds")), CompareType.INCLUDE));
/* <-MISALIGNED-> */ /* 136*/        else
/* <-MISALIGNED-> */ /* 136*/            filter.getFilterItems().add(new FilterItemInfo("contractType.isEnabled", Boolean.TRUE));
									 if(para.getBoolean("isPC")){
										 filter.getFilterItems().add(new FilterItemInfo("programmingContract.id", null,CompareType.NOTEQUALS)); 
									 }else{
										 filter.getFilterItems().add(new FilterItemInfo("programmingContract.id", null,CompareType.EQUALS)); 
									 }
									if(para.getStringArray("projectIds") != null)
	/* 202*/            				filter.getFilterItems().add(new FilterItemInfo("curProject.id", FDCHelper.getSetByArray(para.getStringArray("projectIds")), CompareType.INCLUDE));

/* <-MISALIGNED-> */ /* 138*/        if(para.isNotNull("providerId"))
/* <-MISALIGNED-> */ /* 139*/            filter.getFilterItems().add(new FilterItemInfo("partB.id", para.getString("providerId")));
/* <-MISALIGNED-> */ /* 141*/        if(para.isNotNull("settleState"))
                {
/* <-MISALIGNED-> */ /* 142*/            Boolean settle = null;
/* <-MISALIGNED-> */ /* 143*/            if(para.getInt("settleState") == 0)
/* <-MISALIGNED-> */ /* 144*/                settle = Boolean.TRUE;
/* <-MISALIGNED-> */ /* 145*/            else
/* <-MISALIGNED-> */ /* 145*/            if(para.getInt("settleState") == 1)
/* <-MISALIGNED-> */ /* 146*/                settle = Boolean.FALSE;
/* <-MISALIGNED-> */ /* 148*/            if(settle != null)
/* <-MISALIGNED-> */ /* 149*/                filter.getFilterItems().add(new FilterItemInfo("hasSettled", settle));
                }
/* <-MISALIGNED-> */ /* 152*/        if(para.isNotNull("isShowAll") && !para.getBoolean("isShowAll") && para.isNotNull("dateType"))
                {
/* <-MISALIGNED-> */ /* 154*/            if(getBeginQueryDate(para) != null)
/* <-MISALIGNED-> */ /* 155*/                filter.getFilterItems().add(new FilterItemInfo("signDate", getBeginQueryDate(para), CompareType.GREATER_EQUALS));
/* <-MISALIGNED-> */ /* 157*/            if(getEndQueryDate(para) != null)
/* <-MISALIGNED-> */ /* 158*/                filter.getFilterItems().add(new FilterItemInfo("signDate", FDCHelper.getNextDay(getEndQueryDate(para)), CompareType.LESS));
                }
/* <-MISALIGNED-> */ /* 162*/        if(para.isNotNull("currencyId"))
                {
/* <-MISALIGNED-> */ /* 163*/            filter.getFilterItems().add(new FilterItemInfo("currency.id", para.get("currencyId")));
/* <-MISALIGNED-> */ /* 164*/            hasCurrency = true;
                } else
                {
/* <-MISALIGNED-> */ /* 166*/            hasCurrency = false;
                }
/* <-MISALIGNED-> */ /* 168*/        return filter;
            }
            private Date getBeginQueryDate(FDCCustomerParams para)
            {
/* <-MISALIGNED-> */ /* 172*/        Date date = null;
/* <-MISALIGNED-> */ /* 173*/        int dateType = para.getInt("dateType");
/* <-MISALIGNED-> */ /* 174*/        if(dateType == 0)
/* <-MISALIGNED-> */ /* 175*/            date = para.getDate("dateFrom");
/* <-MISALIGNED-> */ /* 176*/        else
/* <-MISALIGNED-> */ /* 176*/        if(dateType == 1)
                {
/* <-MISALIGNED-> */ /* 177*/            Calendar cal = Calendar.getInstance();
/* <-MISALIGNED-> */ /* 178*/            cal.set(1, para.getInt("yearFrom"));
/* <-MISALIGNED-> */ /* 179*/            cal.set(2, para.getInt("monthFrom") - 1);
/* <-MISALIGNED-> */ /* 180*/            cal.set(5, 1);
/* <-MISALIGNED-> */ /* 181*/            date = cal.getTime();
                } else
/* <-MISALIGNED-> */ /* 182*/        if(dateType == 2)
                {
/* <-MISALIGNED-> */ /* 183*/            Calendar cal = Calendar.getInstance();
/* <-MISALIGNED-> */ /* 184*/            cal.set(1, para.getInt("yearFrom"));
/* <-MISALIGNED-> */ /* 185*/            cal.set(2, (para.getInt("monthFrom") - 1) * 3);
/* <-MISALIGNED-> */ /* 186*/            cal.set(5, 1);
/* <-MISALIGNED-> */ /* 187*/            date = cal.getTime();
                } else
/* <-MISALIGNED-> */ /* 188*/        if(dateType == 3)
                {
/* <-MISALIGNED-> */ /* 189*/            Calendar cal = Calendar.getInstance();
/* <-MISALIGNED-> */ /* 190*/            cal.set(1, para.getInt("yearFrom"));
/* <-MISALIGNED-> */ /* 191*/            cal.set(2, 0);
/* <-MISALIGNED-> */ /* 192*/            cal.set(5, 1);
/* <-MISALIGNED-> */ /* 193*/            date = cal.getTime();
                }
/* <-MISALIGNED-> */ /* 195*/        return DateTimeUtils.truncateDate(date);
            }
            private Date getEndQueryDate(FDCCustomerParams para)
            {
/* <-MISALIGNED-> */ /* 199*/        Date date = null;
/* <-MISALIGNED-> */ /* 200*/        int dateType = para.getInt("dateType");
/* <-MISALIGNED-> */ /* 201*/        if(dateType == 0)
/* <-MISALIGNED-> */ /* 202*/            date = para.getDate("dateTo");
/* <-MISALIGNED-> */ /* 203*/        else
/* <-MISALIGNED-> */ /* 203*/        if(dateType == 1)
                {
/* <-MISALIGNED-> */ /* 204*/            Calendar cal = Calendar.getInstance();
/* <-MISALIGNED-> */ /* 205*/            cal.set(1, para.getInt("yearTo"));
/* <-MISALIGNED-> */ /* 206*/            cal.set(2, para.getInt("monthTo"));
/* <-MISALIGNED-> */ /* 207*/            cal.set(5, 0);
/* <-MISALIGNED-> */ /* 208*/            date = cal.getTime();
                } else
/* <-MISALIGNED-> */ /* 209*/        if(dateType == 2)
                {
/* <-MISALIGNED-> */ /* 210*/            Calendar cal = Calendar.getInstance();
/* <-MISALIGNED-> */ /* 211*/            cal.set(1, para.getInt("yearTo"));
/* <-MISALIGNED-> */ /* 212*/            cal.set(2, para.getInt("monthTo") * 3);
/* <-MISALIGNED-> */ /* 213*/            cal.set(5, 0);
/* <-MISALIGNED-> */ /* 214*/            date = cal.getTime();
                } else
/* <-MISALIGNED-> */ /* 215*/        if(dateType == 3)
                {
/* <-MISALIGNED-> */ /* 216*/            Calendar cal = Calendar.getInstance();
/* <-MISALIGNED-> */ /* 217*/            cal.set(1, para.getInt("yearTo") + 1);
/* <-MISALIGNED-> */ /* 218*/            cal.set(2, 0);
/* <-MISALIGNED-> */ /* 219*/            cal.set(5, 0);
/* <-MISALIGNED-> */ /* 220*/            date = cal.getTime();
                }
/* <-MISALIGNED-> */ /* 222*/        return DateTimeUtils.truncateDate(date);
            }
            public CustomerParams getCustomerParams()
            {
/* <-MISALIGNED-> */ /* 226*/        FDCCustomerParams param = new FDCCustomerParams();
/* <-MISALIGNED-> */ /* 228*/        String contractTypeIds[] = (String[])(String[])txtContractType.getUserObject();
/* <-MISALIGNED-> */ /* 229*/        if(!FDCHelper.isEmpty(contractTypeIds))
/* <-MISALIGNED-> */ /* 230*/            param.add("contractTypeIds", contractTypeIds);

									 param.add("projectIds", (String[])(String[])txtProject.getUserObject());
/* <-MISALIGNED-> */ /* 233*/        SupplierInfo supplierInfo = (SupplierInfo)f7Provider.getValue();
/* <-MISALIGNED-> */ /* 234*/        if(supplierInfo != null)
/* <-MISALIGNED-> */ /* 235*/            param.add("providerId", supplierInfo.getId().toString());
/* <-MISALIGNED-> */ /* 237*/        if(radioByDay.isSelected())
                {
/* <-MISALIGNED-> */ /* 238*/            param.add("dateFrom", (Date)pkDateFrom.getValue());
/* <-MISALIGNED-> */ /* 239*/            param.add("dateTo", (Date)pkDateTo.getValue());
/* <-MISALIGNED-> */ /* 240*/            param.add("dateType", 0);
                } else
/* <-MISALIGNED-> */ /* 241*/        if(radioByMonth.isSelected())
                {
/* <-MISALIGNED-> */ /* 242*/            param.add("yearFrom", ((Integer)spiYearFrom.getValue()).intValue());
/* <-MISALIGNED-> */ /* 243*/            param.add("yearTo", ((Integer)spiYearTo.getValue()).intValue());
/* <-MISALIGNED-> */ /* 244*/            param.add("monthFrom", ((Integer)spiMonthFrom.getValue()).intValue());
/* <-MISALIGNED-> */ /* 245*/            param.add("monthTo", ((Integer)spiMonthTo.getValue()).intValue());
/* <-MISALIGNED-> */ /* 246*/            param.add("dateType", 1);
                } else
/* <-MISALIGNED-> */ /* 247*/        if(radioByQuarter.isSelected())
                {
/* <-MISALIGNED-> */ /* 248*/            param.add("yearFrom", ((Integer)spiYearFrom.getValue()).intValue());
/* <-MISALIGNED-> */ /* 249*/            param.add("yearTo", ((Integer)spiYearTo.getValue()).intValue());
/* <-MISALIGNED-> */ /* 250*/            param.add("monthFrom", ((Integer)spiMonthFrom.getValue()).intValue());
/* <-MISALIGNED-> */ /* 251*/            param.add("monthTo", ((Integer)spiMonthTo.getValue()).intValue());
/* <-MISALIGNED-> */ /* 252*/            param.add("dateType", 2);
                } else
/* <-MISALIGNED-> */ /* 253*/        if(radioByYear.isSelected())
                {
/* <-MISALIGNED-> */ /* 254*/            param.add("yearFrom", ((Integer)spiYearFrom.getValue()).intValue());
/* <-MISALIGNED-> */ /* 255*/            param.add("yearTo", ((Integer)spiYearTo.getValue()).intValue());
/* <-MISALIGNED-> */ /* 256*/            param.add("dateType", 3);
                }
/* <-MISALIGNED-> */ /* 259*/        if(radioHasSettled.isSelected())
/* <-MISALIGNED-> */ /* 260*/            param.add("settleState", 0);
/* <-MISALIGNED-> */ /* 261*/        else
/* <-MISALIGNED-> */ /* 261*/        if(radioNoSettled.isSelected())
/* <-MISALIGNED-> */ /* 262*/            param.add("settleState", 1);
/* <-MISALIGNED-> */ /* 263*/        else
/* <-MISALIGNED-> */ /* 263*/        if(radioSettleAll.isSelected())
/* <-MISALIGNED-> */ /* 264*/            param.add("settleState", 2);
/* <-MISALIGNED-> */ /* 266*/        param.add("isShowAll", chkAllContract.isSelected());
param.add("isPC", this.cbIsPc.isSelected());
/* <-MISALIGNED-> */ /* 268*/        if(prmtCurrency.getValue() != null)
                {
/* <-MISALIGNED-> */ /* 269*/            CurrencyInfo currency = (CurrencyInfo)prmtCurrency.getValue();
/* <-MISALIGNED-> */ /* 270*/            param.add("currencyId", currency.getId().toString());
                }
/* <-MISALIGNED-> */ /* 273*/        return param.getCustomerParams();
            }
            private void initControlByDateType()
            {
/* <-MISALIGNED-> */ /* 277*/        SpinnerNumberModel yearMo1 = new SpinnerNumberModel(Calendar.getInstance().get(1), 1900, 2100, 1);
/* <-MISALIGNED-> */ /* 278*/        spiYearFrom.setModel(yearMo1);
/* <-MISALIGNED-> */ /* 279*/        SpinnerNumberModel yearMo2 = new SpinnerNumberModel(Calendar.getInstance().get(1), 1900, 2100, 1);
/* <-MISALIGNED-> */ /* 280*/        spiYearTo.setModel(yearMo2);
/* <-MISALIGNED-> */ /* 282*/        if(radioByDay.isSelected())
                {
/* <-MISALIGNED-> */ /* 283*/            contYearFrom.setVisible(false);
/* <-MISALIGNED-> */ /* 284*/            contYearTo.setVisible(false);
/* <-MISALIGNED-> */ /* 285*/            lblMonthFrom.setVisible(false);
/* <-MISALIGNED-> */ /* 286*/            lblMonthTo.setVisible(false);
/* <-MISALIGNED-> */ /* 287*/            lblQuarterFrom.setVisible(false);
/* <-MISALIGNED-> */ /* 288*/            lblQuarterTo.setVisible(false);
/* <-MISALIGNED-> */ /* 289*/            spiMonthFrom.setVisible(false);
/* <-MISALIGNED-> */ /* 290*/            spiMonthTo.setVisible(false);
/* <-MISALIGNED-> */ /* 291*/            contDateFrom.setVisible(true);
/* <-MISALIGNED-> */ /* 292*/            contDateTo.setVisible(true);
                } else
/* <-MISALIGNED-> */ /* 294*/        if(radioByMonth.isSelected())
                {
/* <-MISALIGNED-> */ /* 295*/            contYearFrom.setVisible(true);
/* <-MISALIGNED-> */ /* 296*/            contYearTo.setVisible(true);
/* <-MISALIGNED-> */ /* 297*/            lblMonthFrom.setVisible(true);
/* <-MISALIGNED-> */ /* 298*/            lblMonthTo.setVisible(true);
/* <-MISALIGNED-> */ /* 299*/            lblQuarterFrom.setVisible(false);
/* <-MISALIGNED-> */ /* 300*/            lblQuarterTo.setVisible(false);
/* <-MISALIGNED-> */ /* 301*/            spiMonthFrom.setVisible(true);
/* <-MISALIGNED-> */ /* 302*/            spiMonthTo.setVisible(true);
/* <-MISALIGNED-> */ /* 303*/            contDateFrom.setVisible(false);
/* <-MISALIGNED-> */ /* 304*/            contDateTo.setVisible(false);
/* <-MISALIGNED-> */ /* 305*/            SpinnerNumberModel monthMo1 = new SpinnerNumberModel(Calendar.getInstance().get(2) + 1, 1, 12, 1);
/* <-MISALIGNED-> */ /* 306*/            spiMonthFrom.setModel(monthMo1);
/* <-MISALIGNED-> */ /* 307*/            SpinnerNumberModel monthMo2 = new SpinnerNumberModel(Calendar.getInstance().get(2) + 1, 1, 12, 1);
/* <-MISALIGNED-> */ /* 308*/            spiMonthTo.setModel(monthMo2);
                } else
/* <-MISALIGNED-> */ /* 309*/        if(radioByQuarter.isSelected())
                {
/* <-MISALIGNED-> */ /* 310*/            contYearFrom.setVisible(true);
/* <-MISALIGNED-> */ /* 311*/            contYearTo.setVisible(true);
/* <-MISALIGNED-> */ /* 312*/            lblMonthFrom.setVisible(false);
/* <-MISALIGNED-> */ /* 313*/            lblMonthTo.setVisible(false);
/* <-MISALIGNED-> */ /* 314*/            lblQuarterFrom.setVisible(true);
/* <-MISALIGNED-> */ /* 315*/            lblQuarterTo.setVisible(true);
/* <-MISALIGNED-> */ /* 316*/            spiMonthFrom.setVisible(true);
/* <-MISALIGNED-> */ /* 317*/            spiMonthTo.setVisible(true);
/* <-MISALIGNED-> */ /* 318*/            contDateFrom.setVisible(false);
/* <-MISALIGNED-> */ /* 319*/            contDateTo.setVisible(false);
/* <-MISALIGNED-> */ /* 320*/            int SEASON[] = {
/* <-MISALIGNED-> */ /* 320*/                1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 
/* <-MISALIGNED-> */ /* 320*/                4, 4
                    };
/* <-MISALIGNED-> */ /* 321*/            SpinnerNumberModel quarterMo1 = new SpinnerNumberModel(SEASON[Calendar.getInstance().get(2)], 1, 4, 1);
/* <-MISALIGNED-> */ /* 322*/            spiMonthFrom.setModel(quarterMo1);
/* <-MISALIGNED-> */ /* 323*/            SpinnerNumberModel quarterMo2 = new SpinnerNumberModel(SEASON[Calendar.getInstance().get(2)], 1, 4, 1);
/* <-MISALIGNED-> */ /* 324*/            spiMonthTo.setModel(quarterMo2);
                } else
/* <-MISALIGNED-> */ /* 326*/        if(radioByYear.isSelected())
                {
/* <-MISALIGNED-> */ /* 327*/            contYearFrom.setVisible(true);
/* <-MISALIGNED-> */ /* 328*/            contYearTo.setVisible(true);
/* <-MISALIGNED-> */ /* 329*/            lblMonthFrom.setVisible(false);
/* <-MISALIGNED-> */ /* 330*/            lblMonthTo.setVisible(false);
/* <-MISALIGNED-> */ /* 331*/            lblQuarterFrom.setVisible(false);
/* <-MISALIGNED-> */ /* 332*/            lblQuarterTo.setVisible(false);
/* <-MISALIGNED-> */ /* 333*/            spiMonthFrom.setVisible(false);
/* <-MISALIGNED-> */ /* 334*/            spiMonthTo.setVisible(false);
/* <-MISALIGNED-> */ /* 335*/            contDateFrom.setVisible(false);
/* <-MISALIGNED-> */ /* 336*/            contDateTo.setVisible(false);
                }
/* <-MISALIGNED-> */ /* 339*/        if(chkAllContract.isSelected())
                {
/* <-MISALIGNED-> */ /* 340*/            radioByDay.setEnabled(false);
/* <-MISALIGNED-> */ /* 341*/            radioByMonth.setEnabled(false);
/* <-MISALIGNED-> */ /* 342*/            radioByQuarter.setEnabled(false);
/* <-MISALIGNED-> */ /* 343*/            radioByYear.setEnabled(false);
/* <-MISALIGNED-> */ /* 344*/            spiYearFrom.setEnabled(false);
/* <-MISALIGNED-> */ /* 345*/            spiYearTo.setEnabled(false);
/* <-MISALIGNED-> */ /* 346*/            spiMonthFrom.setEnabled(false);
/* <-MISALIGNED-> */ /* 347*/            spiMonthTo.setEnabled(false);
/* <-MISALIGNED-> */ /* 348*/            pkDateFrom.setEnabled(false);
/* <-MISALIGNED-> */ /* 349*/            pkDateTo.setEnabled(false);
                } else
                {
/* <-MISALIGNED-> */ /* 351*/            radioByDay.setEnabled(true);
/* <-MISALIGNED-> */ /* 352*/            radioByMonth.setEnabled(true);
/* <-MISALIGNED-> */ /* 353*/            radioByQuarter.setEnabled(true);
/* <-MISALIGNED-> */ /* 354*/            radioByYear.setEnabled(true);
/* <-MISALIGNED-> */ /* 355*/            plDateType.setEnabled(true);
/* <-MISALIGNED-> */ /* 356*/            spiYearFrom.setEnabled(true);
/* <-MISALIGNED-> */ /* 357*/            spiYearTo.setEnabled(true);
/* <-MISALIGNED-> */ /* 358*/            spiMonthFrom.setEnabled(true);
/* <-MISALIGNED-> */ /* 359*/            spiMonthTo.setEnabled(true);
/* <-MISALIGNED-> */ /* 360*/            pkDateFrom.setEnabled(true);
/* <-MISALIGNED-> */ /* 361*/            pkDateTo.setEnabled(true);
                }
            }
            private void initContractTypeDlg(String contractTypeIds[])
                throws Exception, BOSException
            {
/* <-MISALIGNED-> */ /* 366*/        TreeModel treeModel = FDCClientHelper.createDataTree(ContractTypeFactory.getRemoteInstance(), null);
/* <-MISALIGNED-> */ /* 367*/        FDCHelper.setTreeForbidNode((DefaultKingdeeTreeNode)treeModel.getRoot(), "isIsEnabled", Boolean.FALSE);
/* <-MISALIGNED-> */ /* 368*/        contractTypeSelectDlg = new TreeSelectDialog(this, treeModel, "getId,toString", contractTypeIds);
            }
            public void onLoad()
                throws Exception
            {
/* <-MISALIGNED-> */ /* 372*/        super.onLoad();
/* <-MISALIGNED-> */ /* 373*/        if(!isLoaded)
/* <-MISALIGNED-> */ /* 374*/            clear();
/* <-MISALIGNED-> */ /* 376*/        isLoaded = true;
            }
            protected void radioByDay_actionPerformed(java.awt.event.ActionEvent e)
                throws Exception
            {
/* <-MISALIGNED-> */ /* 380*/        super.radioByDay_actionPerformed(e);
/* <-MISALIGNED-> */ /* 381*/        initControlByDateType();
            }
            protected void radioByMonth_actionPerformed(java.awt.event.ActionEvent e)
                throws Exception
            {
/* <-MISALIGNED-> */ /* 385*/        super.radioByMonth_actionPerformed(e);
/* <-MISALIGNED-> */ /* 386*/        initControlByDateType();
            }
            protected void radioByQuarter_actionPerformed(java.awt.event.ActionEvent e)
                throws Exception
            {
/* <-MISALIGNED-> */ /* 390*/        super.radioByQuarter_actionPerformed(e);
/* <-MISALIGNED-> */ /* 391*/        initControlByDateType();
            }
            protected void radioByYear_actionPerformed(java.awt.event.ActionEvent e)
                throws Exception
            {
/* <-MISALIGNED-> */ /* 395*/        super.radioByYear_actionPerformed(e);
/* <-MISALIGNED-> */ /* 396*/        initControlByDateType();
            }
            private void setContractTypeByTree(Object object)
            {
/* <-MISALIGNED-> */ /* 400*/        List contractTypeIdList = new ArrayList();
/* <-MISALIGNED-> */ /* 401*/        if(object != null)
                {
/* <-MISALIGNED-> */ /* 402*/            List contractTypeList = (List)object;
/* <-MISALIGNED-> */ /* 403*/            String text = "";
/* <-MISALIGNED-> */ /* 404*/            for(int i = 0; i < contractTypeList.size(); i++)
                    {
/* <-MISALIGNED-> */ /* 405*/                if(!(contractTypeList.get(i) instanceof ContractTypeInfo))
/* <-MISALIGNED-> */ /* 406*/                    continue;
/* <-MISALIGNED-> */ /* 406*/                ContractTypeInfo contractType = (ContractTypeInfo)contractTypeList.get(i);
/* <-MISALIGNED-> */ /* 407*/                contractTypeIdList.add(contractType.getId().toString());
/* <-MISALIGNED-> */ /* 410*/                if(!contractType.isIsLeaf())
/* <-MISALIGNED-> */ /* 411*/                    continue;
/* <-MISALIGNED-> */ /* 411*/                if(!text.equals(""))
/* <-MISALIGNED-> */ /* 412*/                    text = (new StringBuilder()).append(text).append(",").toString();
/* <-MISALIGNED-> */ /* 414*/                text = (new StringBuilder()).append(text).append(contractType.getName()).toString();
                    }
/* <-MISALIGNED-> */ /* 418*/            txtContractType.setText(text);
/* <-MISALIGNED-> */ /* 419*/            Object ids[] = contractTypeIdList.toArray(new String[0]);
/* <-MISALIGNED-> */ /* 420*/            if(FDCHelper.isEmpty(ids))
/* <-MISALIGNED-> */ /* 421*/                txtContractType.setUserObject(null);
/* <-MISALIGNED-> */ /* 423*/            else
/* <-MISALIGNED-> */ /* 423*/                txtContractType.setUserObject(((Object) (ids)));
                }
            }
            public void setCustomerParams(CustomerParams cp)
            {
/* <-MISALIGNED-> */ /* 431*/        if(cp == null)
/* <-MISALIGNED-> */ /* 432*/            return;
/* <-MISALIGNED-> */ /* 434*/        FDCCustomerParams para = new FDCCustomerParams(cp);
/* <-MISALIGNED-> */ /* 436*/        try
                {
/* <-MISALIGNED-> */ /* 436*/            initContractTypeDlg(para.getStringArray("contractTypeIds"));
/* <-MISALIGNED-> */ /* 437*/            setContractTypeByTree(contractTypeSelectDlg.getUserObject());
										 initProjectDlg(para.getStringArray("projectIds"));
/* <-MISALIGNED-> */ /* 406*/            setProjectByTree(projectSelectDlg.getUserObject());
                }
/* <-MISALIGNED-> */ /* 438*/        catch(Exception e)
                {
/* <-MISALIGNED-> */ /* 439*/            e.printStackTrace();
/* <-MISALIGNED-> */ /* 440*/            SysUtil.abort();
                }
/* <-MISALIGNED-> */ /* 442*/        if(para.get("providerId") != null)
                {
/* <-MISALIGNED-> */ /* 443*/            SupplierInfo supplier = null;
/* <-MISALIGNED-> */ /* 445*/            try
                    {
/* <-MISALIGNED-> */ /* 445*/                supplier = SupplierFactory.getRemoteInstance().getSupplierInfo(new ObjectUuidPK(BOSUuid.read(para.get("providerId"))));
                    }
/* <-MISALIGNED-> */ /* 446*/            catch(EASBizException e)
                    {
/* <-MISALIGNED-> */ /* 447*/                e.printStackTrace();
/* <-MISALIGNED-> */ /* 448*/                SysUtil.abort();
                    }
/* <-MISALIGNED-> */ /* 449*/            catch(BOSException e)
                    {
/* <-MISALIGNED-> */ /* 450*/                e.printStackTrace();
/* <-MISALIGNED-> */ /* 451*/                SysUtil.abort();
                    }
/* <-MISALIGNED-> */ /* 453*/            f7Provider.setValue(supplier);
                } else
                {
/* <-MISALIGNED-> */ /* 455*/            f7Provider.setValue(null);
                }
/* <-MISALIGNED-> */ /* 457*/        if(para.getInt("dateType") == 0)
/* <-MISALIGNED-> */ /* 458*/            radioByDay.setSelected(true);
/* <-MISALIGNED-> */ /* 459*/        else
/* <-MISALIGNED-> */ /* 459*/        if(para.getInt("dateType") == 1)
/* <-MISALIGNED-> */ /* 460*/            radioByMonth.setSelected(true);
/* <-MISALIGNED-> */ /* 461*/        else
/* <-MISALIGNED-> */ /* 461*/        if(para.getInt("dateType") == 2)
/* <-MISALIGNED-> */ /* 462*/            radioByQuarter.setSelected(true);
/* <-MISALIGNED-> */ /* 463*/        else
/* <-MISALIGNED-> */ /* 463*/        if(para.getInt("dateType") == 3)
/* <-MISALIGNED-> */ /* 464*/            radioByYear.setSelected(true);
/* <-MISALIGNED-> */ /* 467*/        if(para.getInt("settleState") == 0)
/* <-MISALIGNED-> */ /* 468*/            radioHasSettled.setSelected(true);
/* <-MISALIGNED-> */ /* 469*/        else
/* <-MISALIGNED-> */ /* 469*/        if(para.getInt("settleState") == 1)
/* <-MISALIGNED-> */ /* 470*/            radioNoSettled.setSelected(true);
/* <-MISALIGNED-> */ /* 471*/        else
/* <-MISALIGNED-> */ /* 471*/        if(para.getInt("settleState") == 2)
/* <-MISALIGNED-> */ /* 472*/            radioSettleAll.setSelected(true);
/* <-MISALIGNED-> */ /* 474*/        chkAllContract.setSelected(para.getBoolean("isShowAll"));
this.cbIsPc.						 setSelected(para.getBoolean("isPC"));
/* <-MISALIGNED-> */ /* 475*/        initControlByDateType();
/* <-MISALIGNED-> */ /* 477*/        if(para.getInt("dateType") == 0)
                {
/* <-MISALIGNED-> */ /* 478*/            pkDateFrom.setValue(para.getDate("dateFrom"));
/* <-MISALIGNED-> */ /* 479*/            pkDateTo.setValue(para.getDate("dateTo"));
                } else
/* <-MISALIGNED-> */ /* 480*/        if(para.getInt("dateType") == 1)
                {
/* <-MISALIGNED-> */ /* 481*/            spiYearFrom.setValue(new Integer(para.getInt("yearFrom")));
/* <-MISALIGNED-> */ /* 482*/            spiYearTo.setValue(new Integer(para.getInt("yearTo")));
/* <-MISALIGNED-> */ /* 483*/            spiMonthFrom.setValue(new Integer(para.getInt("monthFrom")));
/* <-MISALIGNED-> */ /* 484*/            spiMonthTo.setValue(new Integer(para.getInt("monthTo")));
                } else
/* <-MISALIGNED-> */ /* 485*/        if(para.getInt("dateType") == 2)
                {
/* <-MISALIGNED-> */ /* 486*/            spiYearFrom.setValue(new Integer(para.getInt("yearFrom")));
/* <-MISALIGNED-> */ /* 487*/            spiYearTo.setValue(new Integer(para.getInt("yearTo")));
/* <-MISALIGNED-> */ /* 488*/            spiMonthFrom.setValue(new Integer(para.getInt("monthFrom")));
/* <-MISALIGNED-> */ /* 489*/            spiMonthTo.setValue(new Integer(para.getInt("monthTo")));
                } else
/* <-MISALIGNED-> */ /* 490*/        if(para.getInt("dateType") == 3)
                {
/* <-MISALIGNED-> */ /* 491*/            spiYearFrom.setValue(new Integer(para.getInt("yearFrom")));
/* <-MISALIGNED-> */ /* 492*/            spiYearTo.setValue(new Integer(para.getInt("yearTo")));
                }
/* <-MISALIGNED-> */ /* 495*/        if(para.get("currencyId") != null)
                {
/* <-MISALIGNED-> */ /* 496*/            String currencyId = para.get("currencyId");
/* <-MISALIGNED-> */ /* 497*/            CurrencyInfo currencyInfo = null;
/* <-MISALIGNED-> */ /* 499*/            try
                    {
/* <-MISALIGNED-> */ /* 499*/                currencyInfo = CurrencyFactory.getRemoteInstance().getCurrencyInfo(new ObjectUuidPK(currencyId));
                    }
/* <-MISALIGNED-> */ /* 500*/            catch(Exception e)
                    {
/* <-MISALIGNED-> */ /* 501*/                handUIException(e);
/* <-MISALIGNED-> */ /* 502*/                SysUtil.abort();
                    }
/* <-MISALIGNED-> */ /* 504*/            prmtCurrency.setValue(currencyInfo);
                }
/* <-MISALIGNED-> */ /* 507*/        super.setCustomerParams(para.getCustomerParams());
            }
            public void storeFields()
            {
/* <-MISALIGNED-> */ /* 514*/        super.storeFields();
            }
            public boolean verify()
            {
/* <-MISALIGNED-> */ /* 518*/        FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
if(!para.isNotNull("projectIds")){
	 MsgBox.showWarning(this, "工程项目不能为空！");
	 /* <-MISALIGNED-> */ /* 522*/            return false;
}
/* <-MISALIGNED-> */ /* 519*/        if(para.isNotNull("endQueryDate") && para.isNotNull("beginQueryDate") && para.getDate("endQueryDate").before(para.getDate("beginQueryDate")))
                {
/* <-MISALIGNED-> */ /* 521*/            MsgBox.showWarning(this, EASResource.getString("com.kingdee.eas.fdc.contract.client.ContractFullResource", "DateBoundErrer"));
/* <-MISALIGNED-> */ /* 522*/            return false;
                }
/* <-MISALIGNED-> */ /* 525*/        Date start = (Date)pkDateFrom.getValue();
/* <-MISALIGNED-> */ /* 526*/        Date end = (Date)pkDateTo.getValue();
/* <-MISALIGNED-> */ /* 528*/        if(para.isNotNull("isShowAll") && !para.getBoolean("isShowAll") && start != null && end != null && start.after(end))
                {
/* <-MISALIGNED-> */ /* 531*/            MsgBox.showWarning(this, "\u4ED8\u6B3E\u65E5\u671F\u7684\u5F00\u59CB\u65E5\u671F\u4E0D\u80FD\u5927\u4E8E\u7ED3\u675F\u65E5\u671F!");
/* <-MISALIGNED-> */ /* 532*/            pkDateFrom.requestFocus();
/* <-MISALIGNED-> */ /* 533*/            return false;
                } else
                {
/* <-MISALIGNED-> */ /* 537*/            return true;
                }
            }
            protected void chkAllContract_actionPerformed(java.awt.event.ActionEvent e)
                throws Exception
            {
/* <-MISALIGNED-> */ /* 541*/        super.chkAllContract_actionPerformed(e);
/* <-MISALIGNED-> */ /* 542*/        initControlByDateType();
            }
            public boolean hasCurrency()
            {
/* <-MISALIGNED-> */ /* 548*/        return hasCurrency;
            }
            private static final String IS_ARCHIVED = "isArchived";
            private static final String CURRENCY_ID = "currencyId";
            private static final String MONTH_TO = "monthTo";
            private static final String MONTH_FROM = "monthFrom";
            private static final String YEAR_TO = "yearTo";
            private static final String YEAR_FROM = "yearFrom";
            private static final String DATE_FROM = "dateFrom";
            private static final String DATE_TO = "dateTo";
            private static final String IS_SHOW_ALL = "isShowAll";
            private static final String SETTLE_STATE = "settleState";
            private static final String DATE_TYPE = "dateType";
            private static final String PROVIDER_ID = "providerId";
            private static final String CONTRACT_TYPE_IDS = "contractTypeIds";
            public static final String resourcePath = "com.kingdee.eas.fdc.contract.client.ContractFullResource";
            protected ItemAction actionListOnLoad;
            private TreeSelectDialog contractTypeSelectDlg;
            private boolean isLoaded;
            protected ListUI listUI;
            private Date pkdate[];
            boolean hasCurrency;
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\patch\sp-fdc_contract-client.jar
	Total time: 135 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/