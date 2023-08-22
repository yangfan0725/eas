/*jadclipse*/package com.kingdee.eas.fdc.basedata.client;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.swing.KDCheckBoxMenuItem;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.monitor.client.BasicWorkFlowMonitorPanel;
import com.kingdee.bos.workflow.monitor.client.ProcessRunningListUI;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.util.FdcClassUtil;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
public abstract class FDCBillListUI extends AbstractFDCBillListUI
{
            public FDCBillListUI()
                throws Exception
            {







/*  77*/        isIncorporation = false;

/*  79*/        isFinacial = false;

/*  81*/        orgUnit = null;


/*  84*/        company = null;
            }
            public void storeFields()
            {











/*  99*/        super.storeFields();
            }
            protected void tblMain_tableClicked(KDTMouseEvent e)
                throws Exception
            {



/* 107*/        super.tblMain_tableClicked(e);
            }
            protected void tblMain_tableSelectChanged(KDTSelectEvent e)
                throws Exception
            {



/* 115*/        super.tblMain_tableSelectChanged(e);
            }
            protected void menuItemImportData_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 123*/        super.menuItemImportData_actionPerformed(e);
            }
            public void actionPageSetup_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 131*/        super.actionPageSetup_actionPerformed(e);
            }
            public void actionExitCurrent_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 139*/        super.actionExitCurrent_actionPerformed(e);
            }
            public void actionHelp_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 147*/        super.actionHelp_actionPerformed(e);
            }
            public void actionAbout_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 155*/        super.actionAbout_actionPerformed(e);
            }
            public void actionOnLoad_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 163*/        super.actionOnLoad_actionPerformed(e);
            }
            public void actionSendMessage_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 171*/        super.actionSendMessage_actionPerformed(e);
            }
            public void actionCalculator_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 179*/        super.actionCalculator_actionPerformed(e);
            }
            public void actionAddNew_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 187*/        super.actionAddNew_actionPerformed(e);
            }
            public void actionView_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 195*/        super.actionView_actionPerformed(e);
            }
            public void actionEdit_actionPerformed(ActionEvent e)
                throws Exception
            {




/* 204*/        super.actionEdit_actionPerformed(e);
            }
            public void actionRemove_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 212*/        super.actionRemove_actionPerformed(e);
            }
            public void actionRefresh_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 220*/        super.actionRefresh_actionPerformed(e);
            }
            public void actionPrint_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 228*/        super.actionPrint_actionPerformed(e);
            }
            public void actionPrintPreview_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 236*/        super.actionPrintPreview_actionPerformed(e);
            }
            public void actionLocate_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 244*/        super.actionLocate_actionPerformed(e);
            }
            public void actionQuery_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 252*/        super.actionQuery_actionPerformed(e);
            }
            public void actionImportData_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 260*/        if(getClass().getName().equalsIgnoreCase("com.kingdee.eas.fdc.contract.client.contractBillListUI"))
/* 261*/            fdcDataImport();

/* 263*/        else/* 263*/            super.actionImportData_actionPerformed(e);
            }
            public void actionAttachment_actionPerformed(ActionEvent e)
                throws Exception
            {






/* 274*/        boolean isEdit = false;
/* 275*/        AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
/* 276*/        String boID = getSelectedKeyValue();
/* 277*/        checkSelected();
/* 278*/        if(boID == null)

/* 280*/            return;

/* 282*/        if(getBillStatePropertyName() != null)
                {/* 283*/            int rowIdx = getMainTable().getSelectManager().getActiveRowIndex();
/* 284*/            ICell cell = getMainTable().getCell(rowIdx, getBillStatePropertyName());
/* 285*/            Object obj = cell.getValue();
/* 286*/            if(obj != null && (obj.toString().equals(FDCBillStateEnum.SAVED.toString()) || obj.toString().equals(FDCBillStateEnum.SUBMITTED.toString()) || obj.toString().equals(FDCBillStateEnum.AUDITTING.toString()) || obj.toString().equals(BillStatusEnum.SAVE.toString()) || obj.toString().equals(BillStatusEnum.SUBMIT.toString()) || obj.toString().equals(BillStatusEnum.AUDITING.toString())))






/* 293*/                isEdit = true;

/* 295*/            else/* 295*/                isEdit = false;
                }


/* 299*/        acm.showAttachmentListUIByBoID(boID, this, isEdit);
            }
            public void actionExportData_actionPerformed(ActionEvent e)
                throws Exception
            {
































/* 336*/        super.actionExportData_actionPerformed(e);
            }
            public void actionToExcel_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 344*/        super.actionToExcel_actionPerformed(e);
            }
            public void actionStartWorkFlow_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 352*/        super.actionStartWorkFlow_actionPerformed(e);
            }
            public void actionPublishReport_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 360*/        super.actionPublishReport_actionPerformed(e);
            }
            public void actionCancel_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 368*/        checkSelected();

/* 370*/        checkBillState(getBillStateEnum(), "selectRightRowForCancel");

/* 372*/        checkBeforeCancel();

/* 374*/        int confirm = MsgBox.showConfirm2(this, "\u786E\u8BA4\u7EC8\u6B62? \u8BE5\u64CD\u4F5C\u4E0D\u53EF\u64A4\u9500\uFF01");
/* 375*/        if(confirm == 0)
                {/* 376*/            cancel(ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName()));

/* 378*/            showOprtOKMsgAndRefresh();
                }
            }
            protected String[] getBillStateEnum()
            {
/* 383*/        return (new String[] {/* 383*/            "4AUDITTED"
                });
            }
            protected void cancel(java.util.List list)
                throws Exception
            {
            }
            protected void cancelCancel(java.util.List list)
                throws Exception
            {
            }
            public void actionCancelCancel_actionPerformed(ActionEvent e)
                throws Exception
            {







/* 404*/        checkSelected();

/* 406*/        checkBillState(new String[] {/* 406*/            "5CANCEL"
                }, "selectRightRowForCancelCancel");
/* 408*/        cancelCancel(ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName()));

/* 410*/        showOprtOKMsgAndRefresh();
            }
            protected void checkBeforeCancel()
                throws Exception
            {
            }
            protected void checkIsInWorkflow()
                throws Exception
            {









/* 428*/        checkSelected();
/* 429*/        java.util.List idList = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());

/* 431*/        Set idSet = ContractClientUtils.listToSet(idList);

/* 433*/        EntityViewInfo view = new EntityViewInfo();
/* 434*/        FilterInfo filter = new FilterInfo();
/* 435*/        filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));

/* 437*/        view.setFilter(filter);
/* 438*/        view.getSelector().add("id");
/* 439*/        view.getSelector().add(getBillStatePropertyName());
/* 440*/        CoreBaseCollection coll = getBizInterface().getCollection(view);
                CoreBillBaseInfo element;
/* 442*/        for(Iterator iter = coll.iterator(); iter.hasNext(); FDCClientUtils.checkBillInWorkflow(this, element.getId().toString()))
/* 443*/            element = (CoreBillBaseInfo)iter.next();
            }
            protected void checkBillState(String states[], String res)
                throws Exception
            {
















/* 464*/        java.util.List idList = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());

/* 466*/        if(idList == null)
                {/* 467*/            MsgBox.showWarning(this, "\u8BF7\u9009\u4E2D\u884C");
/* 468*/            abort();
/* 469*/            return;
                }

/* 472*/        Set idSet = ContractClientUtils.listToSet(idList);
/* 473*/        Set stateSet = FDCHelper.getSetByArray(states);

/* 475*/        EntityViewInfo view = new EntityViewInfo();
/* 476*/        FilterInfo filter = new FilterInfo();
/* 477*/        filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));

/* 479*/        view.setFilter(filter);
/* 480*/        view.getSelector().add("id");
/* 481*/        view.getSelector().add(getBillStatePropertyName());
/* 482*/        CoreBaseCollection coll = getBizInterface().getCollection(view);

/* 484*/        Iterator iter = coll.iterator();/* 484*/        do
                {
/* <-MISALIGNED-> */ /* 484*/            if(!iter.hasNext())
/* <-MISALIGNED-> */ /* 485*/                break;
/* <-MISALIGNED-> */ /* 485*/            CoreBillBaseInfo element = (CoreBillBaseInfo)iter.next();/* 488*/            FDCClientUtils.checkBillInWorkflow(this, element.getId().toString());


/* 491*/            if(!stateSet.contains(element.getString(getBillStatePropertyName())))
                    {/* 492*/                MsgBox.showWarning(this, ContractClientUtils.getRes(res));
/* 493*/                abort();
                    }
                } while(true);
            }
            protected String getBillStatePropertyName()
            {








/* 507*/        return "state";
            }
            protected KDTable getBillListTable()
            {







/* 518*/        return getMainTable();
            }
            protected SelectorItemCollection getStateSelectors()
            {
/* 522*/        SelectorItemCollection selectors = new SelectorItemCollection();
/* 523*/        selectors.add("state");

/* 525*/        return selectors;
            }
            public void actionCreateTo_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 533*/        super.actionCreateTo_actionPerformed(e);
            }
            public void actionCopyTo_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 541*/        super.actionCopyTo_actionPerformed(e);
            }
            public void actionTraceUp_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 549*/        super.actionTraceUp_actionPerformed(e);
            }
            public void actionTraceDown_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 557*/        super.actionTraceDown_actionPerformed(e);
            }
            public void actionVoucher_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 565*/        super.actionVoucher_actionPerformed(e);
            }
            public void actionDelVoucher_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 573*/        super.actionDelVoucher_actionPerformed(e);
            }
            public void actionAuditResult_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 581*/        super.actionAuditResult_actionPerformed(e);
            }
            public void actionViewDoProccess_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 589*/        checkSelected();
/* 590*/        String fieldName = getQueryFieldNameBindingWf();
/* 591*/        String id = (String)getSelectedFDCFieldValues(fieldName).get(0);
/* 592*/        IEnactmentService service = EnactmentServiceFactory.createRemoteEnactService();
/* 593*/        ProcessInstInfo processInstInfo = null;
/* 594*/        ProcessInstInfo procInsts[] = service.getProcessInstanceByHoldedObjectId(id);
/* 595*/        int i = 0;/* 595*/        for(int n = procInsts.length; i < n; i++)
/* 596*/            if(procInsts[i].getState().startsWith("open"))
/* 597*/                processInstInfo = procInsts[i];


/* 600*/        if(processInstInfo == null)
                {
/* 602*/            procInsts = service.getAllProcessInstancesByBizobjId(id);
/* 603*/            if(procInsts == null || procInsts.length <= 0)
/* 604*/                MsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_WFHasNotInstance"));
/* 605*/            else/* 605*/            if(procInsts.length == 1)
                    {/* 606*/                showWorkflowDiagram(procInsts[0]);
                    } else
                    {/* 608*/                UIContext uiContext = new UIContext(this);
/* 609*/                uiContext.put("procInsts", procInsts);
/* 610*/                String className = ProcessRunningListUI.class.getName();
/* 611*/                IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(className, uiContext);
/* 612*/                uiWindow.show();
                    }
                } else
                {/* 615*/            showWorkflowDiagram(processInstInfo);
                }
            }
            public final ArrayList getSelectedFDCFieldValues(String fieldName)
            {





/* 625*/        ArrayList list = new ArrayList();
/* 626*/        int selectRows[] = KDTableUtil.getSelectedRows(getBillListTable());
/* 627*/        for(int i = 0; i < selectRows.length; i++)
                {/* 628*/            ICell cell = getBillListTable().getRow(selectRows[i]).getCell(fieldName);
/* 629*/            if(cell == null)
                    {/* 630*/                MsgBox.showError(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Error_KeyField_Fail"));
/* 631*/                SysUtil.abort();
                    }
/* 633*/            if(cell.getValue() == null)

/* 635*/                continue;/* 635*/            String id = cell.getValue().toString();
/* 636*/            if(!list.contains(id))
/* 637*/                list.add(id);
                }


/* 641*/        return list;
            }
            private void showWorkflowDiagram(ProcessInstInfo processInstInfo)
                throws Exception
            {
/* 646*/        UIContext uiContext = new UIContext(this);
/* 647*/        uiContext.put("id", processInstInfo.getProcInstId());
/* 648*/        uiContext.put("processInstInfo", processInstInfo);
/* 649*/        String className = BasicWorkFlowMonitorPanel.class.getName();
/* 650*/        IUIWindow uiWindow = UIFactory.createUIFactory(getEditUIModal()).create(className, uiContext);

/* 652*/        uiWindow.show();
            }
            public void actionMultiapprove_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 660*/        super.actionMultiapprove_actionPerformed(e);
            }
            public void actionNextPerson_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 668*/        super.actionNextPerson_actionPerformed(e);
            }
            public void actionWorkFlowG_actionPerformed(ActionEvent e)
                throws Exception
            {



/* 676*/        super.actionWorkFlowG_actionPerformed(e);
            }
            protected String getEditUIName()
            {

/* 681*/        return null;
            }
            protected ICoreBase getBizInterface()
                throws Exception
            {
/* 686*/        return null;
            }
            protected boolean isNeedfetchInitData()
                throws Exception
            {




/* 695*/        return true;
            }
            protected void fetchInitData()
                throws Exception
            {
/* 700*/        if(!isNeedfetchInitData())
/* 701*/            return;








/* 710*/        try
                {
/* <-MISALIGNED-> */ /* 710*/            HashMap paramItem = (HashMap)ActionCache.get("FDCBillListUIHandler.paramItem");
/* <-MISALIGNED-> */ /* 711*/            if(paramItem == null)
                    {
/* <-MISALIGNED-> */ /* 712*/                CompanyOrgUnitInfo fiUnit = SysContext.getSysContext().getCurrentFIUnit();
/* <-MISALIGNED-> */ /* 713*/                if(fiUnit != null && fiUnit.getId() != null)
                        {
/* <-MISALIGNED-> */ /* 714*/                    isIncorporation = FDCUtils.IsInCorporation(null, SysContext.getSysContext().getCurrentFIUnit().getId().toString());
                        } else
                        {
/* <-MISALIGNED-> */ /* 716*/                    logger.warn("CurrentOrgUnit is not FIUnit...");
/* <-MISALIGNED-> */ /* 717*/                    isIncorporation = FDCUtils.IsInCorporation(null, SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
                        }
                    } else
/* <-MISALIGNED-> */ /* 719*/            if(paramItem.get("FDC003_INCORPORATION") != null)
/* <-MISALIGNED-> */ /* 720*/                isIncorporation = Boolean.valueOf(paramItem.get("FDC003_INCORPORATION").toString()).booleanValue();
                }
/* <-MISALIGNED-> */ /* 723*/        catch(Exception e)
                {
/* <-MISALIGNED-> */ /* 724*/            handUIExceptionAndAbort(e);
                }
/* <-MISALIGNED-> */ /* 728*/        Map initData = (Map)ActionCache.get("FDCBillListUIHandler.initData");
/* <-MISALIGNED-> */ /* 729*/        if(initData == null)
                {
/* <-MISALIGNED-> */ /* 730*/            Map param = new HashMap();
/* <-MISALIGNED-> */ /* 731*/            initData = ((IFDCBill)getBillInterface()).fetchInitData(param);
                }
/* <-MISALIGNED-> */ /* 735*/        orgUnit = (FullOrgUnitInfo)initData.get("orgUnitId");
/* <-MISALIGNED-> */ /* 737*/        company = (CompanyOrgUnitInfo)initData.get("company");
/* <-MISALIGNED-> */ /* 738*/        if(company == null)
/* <-MISALIGNED-> */ /* 739*/            company = SysContext.getSysContext().getCurrentFIUnit();/* 741*/        isFinacial = FDCUtils.IsFinacial(null, company.getId().toString());
            }
            public void onLoad()
                throws Exception
            {/* 745*/        menuItemSwitchView.setEnabled(false);
/* 746*/        menuItemSwitchView.setVisible(false);


/* 749*/        fetchInitData();

/* 751*/        super.onLoad();
/* 752*/        if(isUseMainMenuAsTitle())
/* 753*/            FDCClientHelper.setUIMainMenuAsTitle(this);


/* 756*/        initTable();

/* 758*/        getMainTable().setColumnMoveable(true);

/* 760*/        FDCClientHelper.addSqlMenu(this, menuEdit);

				String param="false";
				param = ParamControlFactory.getRemoteInstance().getParamValue(new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId()), "YF_BANKNUM");
				if("true".equals(param)){
					this.btnAttachment.setVisible(false);
					this.actionAttachment.setVisible(false);
					this.MenuItemAttachment.setVisible(false);
				}
            }
            protected void initTable()
            {








/* 772*/        getMainTable().setColumnMoveable(true);


/* 775*/        if(getBillListTable() != null)
                {
/* 777*/            getBillListTable().getSelectManager().setSelectMode(10);
/* 778*/            if(getBillListTable().getColumn("createTime") != null)
/* 779*/                FDCHelper.formatTableDate(getBillListTable(), "createTime");

/* 781*/            if(getBillListTable().getColumn("auditTime") != null)
/* 782*/                FDCHelper.formatTableDate(getBillListTable(), "auditTime");

/* 784*/            if(getBillListTable().getColumn("createDate") != null)
/* 785*/                FDCHelper.formatTableDate(getBillListTable(), "createDate");

/* 787*/            if(getBillListTable().getColumn("createTime") != null)
/* 788*/                FDCHelper.formatTableDate(getBillListTable(), "createTime");

/* 790*/            if(getBillListTable().getColumn("signDate") != null)
/* 791*/                FDCHelper.formatTableDate(getBillListTable(), "signDate");

/* 793*/            if(getBillListTable().getColumn("entrys.deductDate") != null)
/* 794*/                FDCHelper.formatTableDate(getBillListTable(), "entrys.deductDate");




/* 799*/            if(getBillListTable().getColumn("bookedDate") != null)
/* 800*/                FDCHelper.formatTableDate(getBillListTable(), "bookedDate");
                }


/* 804*/        if(getMainTable().getColumn("amount") != null)
/* 805*/            FDCHelper.formatTableNumber(getMainTable(), "amount");

/* 807*/        if(getMainTable().getColumn("originalAmount") != null)
/* 808*/            FDCHelper.formatTableNumber(getMainTable(), "originalAmount");




/* 813*/        if(!isIncorporation)
                {


/* 817*/            if(getMainTable().getColumn("period") != null)
/* 818*/                getMainTable().getColumn("period").getStyleAttributes().setHided(true);

/* 820*/            if(getMainTable().getColumn("period.number") != null)
/* 821*/                getMainTable().getColumn("period.number").getStyleAttributes().setHided(true);




/* 826*/            if(getBillListTable().getColumn("period") != null)
/* 827*/                getBillListTable().getColumn("period").getStyleAttributes().setHided(true);

/* 829*/            if(getBillListTable().getColumn("period.number") != null)
/* 830*/                getBillListTable().getColumn("period.number").getStyleAttributes().setHided(true);
                }



/* 835*/        getMainTable().getDataRequestManager().addDataFillListener(new KDTDataFillListener() {
                    public void afterDataFill(KDTDataRequestEvent e)
                    {
/* 838*/                FDCClientHelper.tblMainAfterDataFill(e, getMainTable(), FDCBillListUI.this);
                    }
        }
);
            }
            protected void showOprtOKMsgAndRefresh()
                throws Exception
            {

/* 853*/        FDCClientUtils.showOprtOK(this);
/* 854*/        refreshList();
            }
            protected void initWorkButton()
            {

/* 859*/        super.initWorkButton();
/* 860*/        btnCancel.setTextIconDisStyle((short)3);
/* 861*/        btnCancelCancel.setTextIconDisStyle((short)3);
            }
            protected void fdcDataImport()
                throws Exception
            {
            }
            protected ArrayList getFDCDataImportParam()
            {






/* 875*/        DatataskParameter param = new DatataskParameter();


/* 878*/        String solutionName = "eas.fdc.bill.contractBill";
/* 879*/        param.solutionName = solutionName;

/* 881*/        param.alias = "\u5408\u540C";
/* 882*/        Hashtable dataTaskCtx = new Hashtable();

/* 884*/        param.setContextParam(dataTaskCtx);

/* 886*/        ArrayList paramList = new ArrayList();
/* 887*/        paramList.add(param);

/* 889*/        return paramList;
            }
            protected boolean isUseMainMenuAsTitle()
            {





/* 898*/        return true;
            }
            public boolean isPrepareInit()
            {




/* 906*/        return true;
            }
            protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo)
            {
/* 911*/        IQueryExecutor queryExecutor = super.getQueryExecutor(queryPK, viewInfo);

/* 913*/        String sql = null;

/* 915*/        try
                {
/* <-MISALIGNED-> */ /* 915*/            sql = queryExecutor.getSQL();
/* <-MISALIGNED-> */ /* 916*/            String classSimpleName = FdcClassUtil.getSimpleName(getClass());/* 918*/            logger.info("================================================================");
/* 919*/            logger.info((new StringBuilder()).append(classSimpleName).append(".getQueryExecutor()\uFF0Csql:").append(sql).toString());
/* 920*/            logger.info((new StringBuilder()).append(classSimpleName).append(".getQueryExecutor()\uFF0CviewInfo:").append(viewInfo).toString());
/* 921*/            logger.info("================================================================");
                }/* 922*/        catch(Exception e)
                {/* 923*/            e.printStackTrace();
                }

/* 926*/        return queryExecutor;
            }
            private static final Logger logger = CoreUIObject.getLogger(FDCBillListUI.class.getName());
            public static final String COL_PERIOD = "period";
            public static final String COL_PERIODNUMBER = "period.number";
            public static final String COL_DATE = "bookedDate";
            protected boolean isIncorporation;
            protected boolean isFinacial;
            protected FullOrgUnitInfo orgUnit;
            protected CompanyOrgUnitInfo company;
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\patch\sp-fdc_basedata-client.jar
	Total time: 54 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/