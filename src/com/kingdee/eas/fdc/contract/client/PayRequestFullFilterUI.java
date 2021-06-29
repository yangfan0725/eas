package com.kingdee.eas.fdc.contract.client;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.extendcontrols.QueryAgent;
import com.kingdee.bos.ctrl.swing.KDButton;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDRadioButton;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.basedata.master.cssp.ISupplier;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OUException;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.contract.IContractWithoutText;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
public class PayRequestFullFilterUI extends AbstractPayRequestFullFilterUI
{
            public PayRequestFullFilterUI(ListUI listUI, ItemAction actionListOnLoad)
                throws Exception
            {








/*  73*/        param = null;









/*  83*/        this.listUI = listUI;
/*  84*/        this.actionListOnLoad = actionListOnLoad;

/*  86*/        pkdate = FDCClientHelper.getCompanyCurrentDate();
            }
            protected void btnCompanySelect_actionPerformed(java.awt.event.ActionEvent e)
                throws Exception
            {
/*  91*/        if(companySelectDlg == null)
/*  92*/            initCompanyDlg(null);

/*  94*/        Object object = companySelectDlg.showDialog();
/*  95*/        setCompanyByTree(object);
/*  96*/        super.btnCompanySelect_actionPerformed(e);
            }
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
/* 110*/        companySelectDlg = null;
/* 111*/        txtCompany.setText(null);
/* 112*/        txtCompany.setUserObject(null);
/* 113*/        CostCenterOrgUnitInfo currentCompany = SysContext.getSysContext().getCurrentCostUnit();

/* 115*/        if(currentCompany.isIsBizUnit())
                {/* 116*/            btnCompanySelect.setEnabled(false);
/* 117*/            txtCompany.setText(currentCompany.getName());
/* 118*/            txtCompany.setUserObject(new String[] {/* 118*/                currentCompany.getId().toString()
                    });
                }
/* 121*/        projectSelectDlg = null;
/* 122*/        txtProject.setText(null);
/* 123*/        txtProject.setUserObject(null);
/* 124*/        f7Contract.setValue(null);

/* 126*/        f7Payee.setData(null);
/* 127*/        pkDateFrom.setValue(null);
/* 128*/        pkDateTo.setValue(null);
/* 129*/        radioAudited.setSelected(true);
chkIncludeClose.setSelected(true);
this.radioAllPay.setSelected(true);
this.radioAllVoucher.setSelected(true);
this.radioHasPartRealPay.setSelected(true);
/* 130*/        f7Contract.setDisplayFormat("$name$");
/* 131*/        f7Contract.setEditFormat("$number$");
/* 132*/        f7Contract.setQueryInfo("com.kingdee.eas.fdc.contract.app.ContractAndWithoutUnionQuery");


/* 135*/        f7Contract.addSelectorListener(new SelectorListener() {
                    public void willShow(SelectorEvent e)
                    {
/* 138*/                if(txtProject.getUserObject() == null || ((Object[])(Object[])txtProject.getUserObject()).length < 1)
                        {/* 139*/                    MsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.contract.client.ContractFullResource", "SelectCurProj"));
/* 140*/                    SysUtil.abort();
                        }


/* 144*/                Object arrays[] = (Object[])(Object[])txtProject.getUserObject();
/* 145*/                HashSet idSet = new HashSet();
/* 146*/                for(int i = 0; i < arrays.length; i++)
/* 147*/                    idSet.add(arrays[i]);

/* 149*/                KDBizPromptBox f7 = (KDBizPromptBox)e.getSource();
/* 150*/                f7.getQueryAgent().resetRuntimeEntityView();
/* 151*/                EntityViewInfo view = new EntityViewInfo();
/* 152*/                FilterInfo filter = new FilterInfo();
/* 153*/                filter.getFilterItems().add(new FilterItemInfo("curProject.id", idSet, CompareType.INCLUDE));
/* 154*/                view.setFilter(filter);
/* 155*/                f7.setEntityViewInfo(view);
                    }
        }
);
/* <-MISALIGNED-> */ /* 159*/        f7Payee.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");
            }
            public FilterInfo getFilterInfo()
            {
/* <-MISALIGNED-> */ /* 164*/        FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
/* <-MISALIGNED-> */ /* 165*/        FilterInfo filter = new FilterInfo();
/* <-MISALIGNED-> */ /* 166*/        filter.getFilterItems().add(new FilterItemInfo("id", null, CompareType.NOTEQUALS));
/* <-MISALIGNED-> */ /* 168*/        if(para.getStringArray("companyIds") != null)
                {
/* <-MISALIGNED-> */ /* 169*/            filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", FDCHelper.getSetByArray(para.getStringArray("companyIds")), CompareType.INCLUDE));
                } else
                {
/* <-MISALIGNED-> */ /* 174*/            if(companySelectDlg == null)
/* <-MISALIGNED-> */ /* 176*/                try
                        {
/* <-MISALIGNED-> */ /* 176*/                    initCompanyDlg(null);
                        }
/* <-MISALIGNED-> */ /* 177*/                catch(Exception e)
                        {
/* <-MISALIGNED-> */ /* 178*/                    e.printStackTrace();
/* <-MISALIGNED-> */ /* 179*/                    SysUtil.abort();
                        }
/* <-MISALIGNED-> */ /* 182*/            TreeModel tree = (DefaultTreeModel)companySelectDlg.getTree();
/* <-MISALIGNED-> */ /* 183*/            DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode)tree.getRoot();
/* <-MISALIGNED-> */ /* 184*/            List list = new ArrayList();
/* <-MISALIGNED-> */ /* 185*/            if(root.getUserObject() != null)
/* <-MISALIGNED-> */ /* 186*/                list.add(root.getUserObject());
/* <-MISALIGNED-> */ /* 188*/            popNode(list, root);
/* <-MISALIGNED-> */ /* 189*/            HashSet set = new HashSet();
                    OrgStructureInfo company;
/* <-MISALIGNED-> */ /* 190*/            for(Iterator iter = list.iterator(); iter.hasNext(); set.add(company.getUnit().getId().toString()))
/* <-MISALIGNED-> */ /* 191*/                company = (OrgStructureInfo)iter.next();
/* <-MISALIGNED-> */ /* 195*/            if(set.size() > 0)
/* <-MISALIGNED-> */ /* 196*/                filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", set, CompareType.INCLUDE));
                }

/* 201*/        if(para.getStringArray("projectIds") != null)
/* 202*/            filter.getFilterItems().add(new FilterItemInfo("curProject.id", FDCHelper.getSetByArray(para.getStringArray("projectIds")), CompareType.INCLUDE));




/* 207*/        else/* 207*/            filter.getFilterItems().add(new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));

/* 209*/        if(para.getStringArray("contractIds") != null)
/* 210*/            filter.getFilterItems().add(new FilterItemInfo("contractId", FDCHelper.getSetByArray(para.getStringArray("contractIds")), CompareType.INCLUDE));




/* 215*/        if(para.get("payeeId") != null)
/* 216*/            filter.getFilterItems().add(new FilterItemInfo("supplier.id", para.get("payeeId")));


/* 219*/        if(para.getDate("dateFrom") != null)
/* 220*/            filter.getFilterItems().add(new FilterItemInfo("payDate", para.getDate("dateFrom"), CompareType.GREATER_EQUALS));



/* 224*/        if(para.getDate("dateTo") != null)
/* 225*/            filter.getFilterItems().add(new FilterItemInfo("payDate", FDCHelper.getNextDay(para.getDate("dateTo")), CompareType.LESS));


/* 228*/        String state = null;
/* 229*/        if(para.isNotNull("payState"))
/* 230*/            if(para.getInt("payState") == 0)
/* 231*/                state = "1SAVED";
/* 232*/            else/* 232*/            if(para.getInt("payState") == 1)
/* 233*/                state = "2SUBMITTED";
/* 234*/            else/* 234*/            if(para.getInt("payState") == 2)
/* 235*/                state = "3AUDITTING";
/* 236*/            else/* 236*/            if(para.getInt("payState") == 3)
/* 237*/                state = "4AUDITTED";



/* 241*/        if(state != null)
/* 242*/            filter.getFilterItems().add(new FilterItemInfo("state", state));

/* 244*/        if(para.isNotNull("chkIncludeClose") && !para.getBoolean("chkIncludeClose"))
/* 245*/            filter.getFilterItems().add(new FilterItemInfo("hasClosed", Boolean.FALSE));

/* <-MISALIGNED-> */ /* 142*/            Boolean voucherState = null;
/* <-MISALIGNED-> */ /* 143*/            if(para.getInt("voucherState") == 0)
/* <-MISALIGNED-> */ /* 144*/                voucherState = Boolean.TRUE;
/* <-MISALIGNED-> */ /* 145*/            else
/* <-MISALIGNED-> */ /* 145*/            if(para.getInt("voucherState") == 1)
/* <-MISALIGNED-> */ /* 146*/                voucherState = Boolean.FALSE;
/* <-MISALIGNED-> */ /* 148*/            if(voucherState != null)
/* <-MISALIGNED-> */ /* 149*/                filter.getFilterItems().add(new FilterItemInfo("fivouchered", voucherState));


/* <-MISALIGNED-> */ /* 142*/            Boolean createPayState = null;
/* <-MISALIGNED-> */ /* 143*/            if(para.getInt("createPayState") == 0)
/* <-MISALIGNED-> */ /* 144*/                createPayState = Boolean.TRUE;
/* <-MISALIGNED-> */ /* 145*/            else
/* <-MISALIGNED-> */ /* 145*/            if(para.getInt("createPayState") == 1)
/* <-MISALIGNED-> */ /* 146*/                createPayState = Boolean.FALSE;
/* <-MISALIGNED-> */ /* 148*/            if(createPayState != null)
/* <-MISALIGNED-> */ /* 149*/                filter.getFilterItems().add(new FilterItemInfo("isCreatePay", createPayState));

/* 252*/        FilterInfo withouttext = new FilterInfo();
/* 253*/        withouttext.getFilterItems().add(new FilterItemInfo("source", "0D6DD1F4"));

/* 255*/        withouttext.getFilterItems().add(new FilterItemInfo("source", "3D9A5388"));

/* 257*/        withouttext.getFilterItems().add(new FilterItemInfo("state", "4AUDITTED"));

/* 259*/        withouttext.setMaskString("#0 or (#1 and #2)");

/* 261*/        try
                {
/* <-MISALIGNED-> */ /* 261*/            filter.mergeFilter(withouttext, "and");
                }
/* <-MISALIGNED-> */ /* 262*/        catch(BOSException e)
                {
/* <-MISALIGNED-> */ /* 263*/            e.printStackTrace();
/* <-MISALIGNED-> */ /* 264*/            SysUtil.abort();
                }
/* <-MISALIGNED-> */ /* 267*/        return filter;
            }
            private void initCompanyDlg(String selectCompayIds[])
                throws OUException, Exception
            {













































/* 320*/        OrgUnitInfo orgUnitInfo = SysContext.getSysContext().getCurrentFIUnit();
/* 321*/        if(orgUnitInfo == null)
/* 322*/            orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit();

/* 324*/        TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COSTCENTER, "", orgUnitInfo.getId().toString(), null, FDCHelper.getActionPK(actionListOnLoad));



/* 328*/        companySelectDlg = new TreeSelectDialog(this, orgTreeModel, "getUnit,getId,toString", selectCompayIds);
            }
            private void initProjectDlg(String projectIds[])
                throws Exception
            {
/* 333*/        ProjectTreeBuilder builder = new ProjectTreeBuilder(true);
/* 334*/        KDTree tree = new KDTree();
/* 335*/        if(companySelectDlg != null && companySelectDlg.getSelectTree() != null)

/* 337*/            builder.buildByCostOrgTree(tree, companySelectDlg.getSelectTree());

/* 339*/        else/* 339*/            builder.build(listUI, tree, actionListOnLoad);

/* 341*/        TreeModel model = tree.getModel();
/* 342*/        FDCHelper.setTreeForbidNode((DefaultKingdeeTreeNode)model.getRoot(), "isIsEnabled", Boolean.FALSE);

/* 344*/        projectSelectDlg = new TreeSelectDialog(this, model, "getId,toString", projectIds);
            }
            public void onLoad()
                throws Exception
            {
/* 349*/        super.onLoad();
/* 350*/        if(!isLoaded)
/* 351*/            clear();

/* 353*/        isLoaded = true;
            }
            private void setCompanyByTree(Object object)
            {
/* 357*/        List companyIdList = new ArrayList();
/* 358*/        if(object != null)
                {/* 359*/            List companyList = (List)object;
/* 360*/            String text = "";
/* 361*/            for(int i = 0; i < companyList.size(); i++)
                    {/* 362*/                OrgStructureInfo company = (OrgStructureInfo)companyList.get(i);

/* 364*/                companyIdList.add(company.getUnit().getId().toString());
/* 365*/                if(!company.getUnit().isIsCostOrgUnit() && !company.getUnit().isIsProfitOrgUnit() || !company.isIsLeaf())


/* 368*/                    continue;/* 368*/                if(!text.equals(""))
/* 369*/                    text = (new StringBuilder()).append(text).append(",").toString();

/* 371*/                text = (new StringBuilder()).append(text).append(company.getUnit().getName()).toString();
                    }


/* 375*/            Object ids[] = companyIdList.toArray(new String[0]);
/* 376*/            ArrayList oldArray = new ArrayList(FDCHelper.getSetByArray((String[])(String[])ids));

/* 378*/            ArrayList newArray = new ArrayList(FDCHelper.getSetByArray((String[])(String[])txtCompany.getUserObject()));

/* 380*/            if(!oldArray.equals(newArray))
                    {
/* 382*/                try
                        {
/* <-MISALIGNED-> */ /* 382*/                    initProjectDlg(null);
                        }
/* <-MISALIGNED-> */ /* 383*/                catch(Exception e)
                        {
/* <-MISALIGNED-> */ /* 384*/                    e.printStackTrace();
/* <-MISALIGNED-> */ /* 385*/                    SysUtil.abort();
                        }
/* <-MISALIGNED-> */ /* 387*/                txtProject.setUserObject(null);
/* <-MISALIGNED-> */ /* 388*/                txtProject.setText(null);
                    }
/* <-MISALIGNED-> */ /* 390*/            txtCompany.setText(text);
/* <-MISALIGNED-> */ /* 391*/            if(FDCHelper.isEmpty(ids))
/* <-MISALIGNED-> */ /* 392*/                txtCompany.setUserObject(null);
/* <-MISALIGNED-> */ /* 394*/            else
/* <-MISALIGNED-> */ /* 394*/                txtCompany.setUserObject(((Object) (ids)));
                }
            }
            public void setCustomerParams(CustomerParams cp)
            {
/* <-MISALIGNED-> */ /* 401*/        FDCCustomerParams para = new FDCCustomerParams(cp);/* 403*/        try
                {
/* <-MISALIGNED-> */ /* 403*/            initCompanyDlg(para.getStringArray("companyIds"));
/* <-MISALIGNED-> */ /* 404*/            setCompanyByTree(companySelectDlg.getUserObject());
/* <-MISALIGNED-> */ /* 405*/            initProjectDlg(para.getStringArray("projectIds"));
/* <-MISALIGNED-> */ /* 406*/            setProjectByTree(projectSelectDlg.getUserObject());
                }
/* <-MISALIGNED-> */ /* 407*/        catch(Exception e)
                {
/* <-MISALIGNED-> */ /* 408*/            e.printStackTrace();
/* <-MISALIGNED-> */ /* 409*/            SysUtil.abort();
                }
/* <-MISALIGNED-> */ /* 412*/        if(!FDCHelper.isEmpty(para.getStringArray("contractIds")))
                {
/* <-MISALIGNED-> */ /* 413*/            ContractBillCollection contractBills = null;
/* <-MISALIGNED-> */ /* 414*/            ContractWithoutTextCollection contractBillWithoutTexts = null;
/* <-MISALIGNED-> */ /* 416*/            try
                    {
/* <-MISALIGNED-> */ /* 416*/                contractBills = ContractBillFactory.getRemoteInstance().getContractBillCollection(FDCHelper.getIncludeEntityView("id", para.getStringArray("contractIds")));
/* <-MISALIGNED-> */ /* 419*/                contractBillWithoutTexts = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextCollection(FDCHelper.getIncludeEntityView("id", para.getStringArray("contractIds")));
                    }
/* <-MISALIGNED-> */ /* 422*/            catch(BOSException e)
                    {
/* <-MISALIGNED-> */ /* 423*/                e.printStackTrace();
/* <-MISALIGNED-> */ /* 424*/                SysUtil.abort();
                    }
/* <-MISALIGNED-> */ /* 426*/            int size = contractBills.size() + contractBillWithoutTexts.size();
/* <-MISALIGNED-> */ /* 427*/            Object objects[] = new Object[size];
/* <-MISALIGNED-> */ /* 428*/            for(int i = 0; i < contractBills.size(); i++)
/* <-MISALIGNED-> */ /* 429*/                objects[i] = contractBills.get(i);
/* <-MISALIGNED-> */ /* 431*/            for(int j = contractBills.size(); j < size; j++)
/* <-MISALIGNED-> */ /* 432*/                objects[j] = contractBillWithoutTexts.get(j - contractBills.size());/* 434*/            f7Contract.setValue(((Object) (objects)));
                }

/* 437*/        if(para.get("payeeId") != null)
                {/* 438*/            SupplierInfo supplier = null;

/* 440*/            try
                    {
/* <-MISALIGNED-> */ /* 440*/                supplier = SupplierFactory.getRemoteInstance().getSupplierInfo(new ObjectUuidPK(BOSUuid.read(para.get("payeeId"))));
                    }
/* <-MISALIGNED-> */ /* 442*/            catch(EASBizException e)
                    {
/* <-MISALIGNED-> */ /* 443*/                e.printStackTrace();
/* <-MISALIGNED-> */ /* 444*/                SysUtil.abort();
                    }
/* <-MISALIGNED-> */ /* 445*/            catch(BOSException e)
                    {
/* <-MISALIGNED-> */ /* 446*/                e.printStackTrace();
/* <-MISALIGNED-> */ /* 447*/                SysUtil.abort();
                    }
/* <-MISALIGNED-> */ /* 449*/            f7Payee.setValue(supplier);
                } else
                {
/* <-MISALIGNED-> */ /* 451*/            f7Payee.setValue(null);
                }
/* <-MISALIGNED-> */ /* 453*/        pkDateFrom.setValue(para.getDate("dateFrom"));
/* <-MISALIGNED-> */ /* 454*/        pkDateTo.setValue(para.getDate("dateTo"));
/* <-MISALIGNED-> */ /* 455*/        if(para.isNotNull("payState"))
/* <-MISALIGNED-> */ /* 456*/            if(para.getInt("payState") == 0)
/* <-MISALIGNED-> */ /* 457*/                radioSave.setSelected(true);
/* <-MISALIGNED-> */ /* 458*/            else
/* <-MISALIGNED-> */ /* 458*/            if(para.getInt("payState") == 1)
/* <-MISALIGNED-> */ /* 459*/                radioSubmit.setSelected(true);
/* <-MISALIGNED-> */ /* 460*/            else
/* <-MISALIGNED-> */ /* 460*/            if(para.getInt("payState") == 2)
/* <-MISALIGNED-> */ /* 461*/                radioAuditing.setSelected(true);
/* <-MISALIGNED-> */ /* 462*/            else
/* <-MISALIGNED-> */ /* 462*/            if(para.getInt("payState") == 3)
/* <-MISALIGNED-> */ /* 463*/                radioAudited.setSelected(true);
/* <-MISALIGNED-> */ /* 464*/            else
/* <-MISALIGNED-> */ /* 464*/            if(para.getInt("payState") == 4)
/* <-MISALIGNED-> */ /* 465*/                radioStateAll.setSelected(true);
/* <-MISALIGNED-> */ /* 468*/        if(para.isNotNull("chkIncludeClose"))
/* <-MISALIGNED-> */ /* 469*/            chkIncludeClose.setSelected(para.getBoolean("chkIncludeClose"));

/* <-MISALIGNED-> */ /* 455*/        if(para.isNotNull("voucherState"))
	/* <-MISALIGNED-> */ /* 456*/            if(para.getInt("voucherState") == 0)
	/* <-MISALIGNED-> */ /* 457*/                radioHasVoucher.setSelected(true);
	/* <-MISALIGNED-> */ /* 458*/            else
	/* <-MISALIGNED-> */ /* 458*/            if(para.getInt("voucherState") == 1)
	/* <-MISALIGNED-> */ /* 459*/                radioNotVoucher.setSelected(true);
	/* <-MISALIGNED-> */ /* 460*/            else
	/* <-MISALIGNED-> */ /* 460*/            if(para.getInt("voucherState") == 2)
	/* <-MISALIGNED-> */ /* 461*/                radioAllVoucher.setSelected(true);

/* <-MISALIGNED-> */ /* 455*/        if(para.isNotNull("createPayState"))
	/* <-MISALIGNED-> */ /* 456*/            if(para.getInt("createPayState") == 0)
	/* <-MISALIGNED-> */ /* 457*/                radioHasPay.setSelected(true);
	/* <-MISALIGNED-> */ /* 458*/            else
	/* <-MISALIGNED-> */ /* 458*/            if(para.getInt("createPayState") == 1)
	/* <-MISALIGNED-> */ /* 459*/                radioNotPay.setSelected(true);
	/* <-MISALIGNED-> */ /* 460*/            else
	/* <-MISALIGNED-> */ /* 460*/            if(para.getInt("createPayState") == 2)
	/* <-MISALIGNED-> */ /* 461*/                radioAllPay.setSelected(true);


/* <-MISALIGNED-> */ /* 455*/        if(para.isNotNull("realPayState"))
	/* <-MISALIGNED-> */ /* 456*/            if(para.getInt("realPayState") == 0)
	/* <-MISALIGNED-> */ /* 457*/                radioHasRealPay.setSelected(true);
	/* <-MISALIGNED-> */ /* 458*/            else
	/* <-MISALIGNED-> */ /* 458*/            if(para.getInt("realPayState") == 1)
	/* <-MISALIGNED-> */ /* 459*/                radioHasPartRealPay.setSelected(true);
	/* <-MISALIGNED-> */ /* 460*/            else
	/* <-MISALIGNED-> */ /* 460*/            if(para.getInt("realPayState") == 2)
	/* <-MISALIGNED-> */ /* 461*/                radioHasRealPayAll.setSelected(true);

/* <-MISALIGNED-> */ /* 471*/        super.setCustomerParams(cp);
            }
            public CustomerParams getCustomerParams()
            {































































/* 545*/        FDCCustomerParams param = new FDCCustomerParams();
/* 546*/        param.add("companyIds", (String[])(String[])txtCompany.getUserObject());
/* 547*/        param.add("projectIds", (String[])(String[])txtProject.getUserObject());




/* 552*/        Object contracts[] = (Object[])(Object[])f7Contract.getValue();
/* 553*/        if(!FDCHelper.isEmpty(contracts))
                {/* 554*/            String ids[] = new String[contracts.length];
/* 555*/            for(int i = 0; i < contracts.length; i++)
                    {/* 556*/                if(contracts[i] instanceof ContractBillInfo)
                        {/* 557*/                    ids[i] = ((ContractBillInfo)contracts[i]).getId().toString();

/* 559*/                    continue;
                        }
/* <-MISALIGNED-> */ /* 559*/                if(contracts[i] instanceof ContractWithoutTextInfo)
/* <-MISALIGNED-> */ /* 560*/                    ids[i] = ((ContractWithoutTextInfo)contracts[i]).getId().toString();
                    }

/* 565*/            param.add("contractIds", ids);
                }

/* 568*/        SupplierInfo supplierInfo = (SupplierInfo)f7Payee.getValue();
/* 569*/        if(supplierInfo != null)
/* 570*/            param.add("payeeId", supplierInfo.getId().toString());


/* 573*/        param.add("dateFrom", (Date)pkDateFrom.getValue());
/* 574*/        param.add("dateTo", (Date)pkDateTo.getValue());



/* 578*/        if(radioSave.isSelected())
/* 579*/            param.add("payState", 0);

/* 581*/        else/* 581*/        if(radioSubmit.isSelected())
/* 582*/            param.add("payState", 1);

/* 584*/        else/* 584*/        if(radioAuditing.isSelected())
/* 585*/            param.add("payState", 2);

/* 587*/        else/* 587*/        if(radioAudited.isSelected())
/* 588*/            param.add("payState", 3);

/* 590*/        else/* 590*/        if(radioStateAll.isSelected())
/* 591*/            param.add("payState", 4);


/* 594*/        param.add("chkIncludeClose", chkIncludeClose.isSelected());

/* <-MISALIGNED-> */ /* 259*/        if(radioHasVoucher.isSelected())
	/* <-MISALIGNED-> */ /* 260*/            param.add("voucherState", 0);
	/* <-MISALIGNED-> */ /* 261*/        else
	/* <-MISALIGNED-> */ /* 261*/        if(radioNotVoucher.isSelected())
	/* <-MISALIGNED-> */ /* 262*/            param.add("voucherState", 1);
	/* <-MISALIGNED-> */ /* 263*/        else
	/* <-MISALIGNED-> */ /* 263*/        if(radioAllVoucher.isSelected())
	/* <-MISALIGNED-> */ /* 264*/            param.add("voucherState", 2);


/* <-MISALIGNED-> */ /* 259*/        if(radioHasPay.isSelected())
	/* <-MISALIGNED-> */ /* 260*/            param.add("createPayState", 0);
	/* <-MISALIGNED-> */ /* 261*/        else
	/* <-MISALIGNED-> */ /* 261*/        if(radioNotPay.isSelected())
	/* <-MISALIGNED-> */ /* 262*/            param.add("createPayState", 1);
	/* <-MISALIGNED-> */ /* 263*/        else
	/* <-MISALIGNED-> */ /* 263*/        if(radioAllPay.isSelected())
	/* <-MISALIGNED-> */ /* 264*/            param.add("createPayState", 2);


/* <-MISALIGNED-> */ /* 259*/        if(radioHasRealPay.isSelected())
	/* <-MISALIGNED-> */ /* 260*/            param.add("realPayState", 0);
	/* <-MISALIGNED-> */ /* 261*/        else
	/* <-MISALIGNED-> */ /* 261*/        if(radioHasPartRealPay.isSelected())
	/* <-MISALIGNED-> */ /* 262*/            param.add("realPayState", 1);
	/* <-MISALIGNED-> */ /* 263*/        else
	/* <-MISALIGNED-> */ /* 263*/        if(radioHasRealPayAll.isSelected())
	/* <-MISALIGNED-> */ /* 264*/            param.add("realPayState", 2);

/* 596*/        return param.getCustomerParams();
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
            public void storeFields()
            {




/* 630*/        super.storeFields();
            }
            public boolean verify()
            {
/* 634*/        if(pkDateTo.getValue() != null && pkDateFrom.getValue() != null && ((Date)pkDateTo.getValue()).before((Date)pkDateFrom.getValue()))
                {


/* 638*/            MsgBox.showWarning(this, EASResource.getString("com.kingdee.eas.fdc.contract.client.ContractFullResource", "DateBoundErrer"));

/* 640*/            return false;
                } else
                {
/* 643*/            return true;
                }
            }
            private void popNode(List list, DefaultKingdeeTreeNode root)
            {
                DefaultKingdeeTreeNode node;
/* <-MISALIGNED-> */ /* 647*/        for(Enumeration c = root.children(); c.hasMoreElements(); popNode(list, node))
                {
/* <-MISALIGNED-> */ /* 648*/            node = (DefaultKingdeeTreeNode)c.nextElement();
/* <-MISALIGNED-> */ /* 650*/            if(node.isLeaf())
/* <-MISALIGNED-> */ /* 651*/                list.add(node.getUserObject());
                }
            }
            public static final String resourcePath = "com.kingdee.eas.fdc.contract.client.ContractFullResource";
            protected ItemAction actionListOnLoad;
            private TreeSelectDialog companySelectDlg;
            private boolean isLoaded;
            protected ListUI listUI;
            private TreeSelectDialog projectSelectDlg;
            private Object param;
            private Date pkdate[];
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\patch\sp-fdc_contract-client.jar
	Total time: 109 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/