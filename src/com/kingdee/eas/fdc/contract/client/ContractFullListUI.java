/*jadclipse*/package com.kingdee.eas.fdc.contract.client;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTTreeColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.KDMenu;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecuteOption;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.multiapprove.MultiApproveInfo;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.ContractSourceInfo;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillWFFacadeFactory;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IFDCBillWFFacade;
import com.kingdee.eas.fdc.basedata.LandDeveloperInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ConNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillEntryCollection;
import com.kingdee.eas.fdc.contract.ContractBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.ForWriteMarkHelper;
import com.kingdee.eas.fdc.contract.IConNoCostSplit;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.contract.IContractCostSplit;
import com.kingdee.eas.fdc.contract.app.OaUtil;
import com.kingdee.eas.fdc.finance.client.PaymentBillListUI;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.AbstractCoreBillListUI;
import com.kingdee.eas.framework.client.AbstractCoreUI;
import com.kingdee.eas.framework.client.AbstractListUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
public class ContractFullListUI extends AbstractContractFullListUI
{
            public ContractFullListUI()
                throws Exception
            {
/* <-MISALIGNED-> */ /* 101*/        filterUI = null;/* 103*/        commonQueryDialog = null;


/* 106*/        contentMap = new HashMap();


/* 109*/        attachMap = new HashMap();


/* 112*/        auditMap = new HashMap();


/* 115*/        currentUserInfoId = "";
/* 116*/        currentOrgUnitId = "";































































































































































































/* 308*/        hasCurrency = false;
            }
            protected String getEditUIName()
            {
/* <-MISALIGNED-> */ /* 126*/        return ContractFullInfoUI.class.getName();
            }
            protected void refresh(java.awt.event.ActionEvent e)
                throws Exception
            {
/* <-MISALIGNED-> */ /* 133*/        if(e != null && e.getSource() != null && !e.getSource().equals(btnRefresh))
                {
/* <-MISALIGNED-> */ /* 134*/            return;
                } else
                {
/* <-MISALIGNED-> */ /* 137*/            refreshTblMain(getMainQuery());
/* <-MISALIGNED-> */ /* 138*/            return;
                }
            }
            protected void execQuery()
            {
/* <-MISALIGNED-> */ /* 149*/        super.execQuery();
/* <-MISALIGNED-> */ /* 150*/        auditMap.clear();
/* <-MISALIGNED-> */ /* 151*/        FDCClientUtils.fmtFootNumber(tblMain, new String[] {
/* <-MISALIGNED-> */ /* 151*/            "amount", "originalAmount"
                });
            }
            protected ICoreBase getBizInterface()
                throws Exception
            {
/* <-MISALIGNED-> */ /* 259*/        return ContractBillFactory.getRemoteInstance();
            }
            protected CommonQueryDialog initCommonQueryDialog()
            {
/* <-MISALIGNED-> */ /* 272*/        if(commonQueryDialog != null)
                {
/* <-MISALIGNED-> */ /* 273*/            return commonQueryDialog;
                } else
                {
/* <-MISALIGNED-> */ /* 275*/            commonQueryDialog = super.initCommonQueryDialog();
/* <-MISALIGNED-> */ /* 276*/            commonQueryDialog.setWidth(400);
/* <-MISALIGNED-> */ /* 277*/            commonQueryDialog.addUserPanel(getFilterUI());
/* <-MISALIGNED-> */ /* 278*/            return commonQueryDialog;
                }
            }
            protected boolean isShowAttachmentAction()
            {
/* <-MISALIGNED-> */ /* 283*/        return true;
            }
            private CustomerQueryPanel getFilterUI()
            {
/* <-MISALIGNED-> */ /* 297*/        if(filterUI == null)
/* <-MISALIGNED-> */ /* 299*/            try
                    {
/* <-MISALIGNED-> */ /* 299*/                filterUI = new ContractFullFilterUI(this, actionOnLoad);
                    }
/* <-MISALIGNED-> */ /* 301*/            catch(Exception e)
                    {
/* <-MISALIGNED-> */ /* 302*/                handUIExceptionAndAbort(e);
                    }
/* <-MISALIGNED-> */ /* 305*/        return filterUI;
            }
            protected boolean initDefaultFilter()
            {
/* <-MISALIGNED-> */ /* 317*/        return !FDCMsgBox.isHidedBox();
            }
            public void onLoad()
                throws Exception
            {
/* <-MISALIGNED-> */ /* 321*/        currentUserInfoId = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
/* <-MISALIGNED-> */ /* 322*/        currentOrgUnitId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
/* <-MISALIGNED-> */ /* 324*/        tblMain.getDataRequestManager().addDataFillListener(new KDTDataFillListener() {
                    public void afterDataFill(KDTDataRequestEvent e)
                    {
                        boolean isMainOrder;
/* <-MISALIGNED-> */ /* 327*/label0:
                        {
/* <-MISALIGNED-> */ /* 327*/                    isMainOrder = false;
/* <-MISALIGNED-> */ /* 328*/                    SorterItemCollection sortItems =  ContractFullListUI.this.mainQuery.getSorter();
/* <-MISALIGNED-> */ /* 329*/                    if(sortItems == null || sortItems.size() <= 0)
/* <-MISALIGNED-> */ /* 330*/                        break label0;
/* <-MISALIGNED-> */ /* 330*/                    Iterator it = sortItems.iterator();
                            SorterItemInfo sortItem;
/* <-MISALIGNED-> */ /* 330*/                    do
                            {
/* <-MISALIGNED-> */ /* 330*/                        if(!it.hasNext())
/* <-MISALIGNED-> */ /* 331*/                            break label0;
/* <-MISALIGNED-> */ /* 331*/                        sortItem = (SorterItemInfo)it.next();
                            } while(!sortItem.getPropertyName().equals("mainContractNumber"));
/* <-MISALIGNED-> */ /* 333*/                    isMainOrder = true;
                        }
/* <-MISALIGNED-> */ /* 338*/                if(attachMap == null)
/* <-MISALIGNED-> */ /* 339*/                    attachMap = new HashMap();
/* <-MISALIGNED-> */ /* 341*/                if(auditMap == null)
/* <-MISALIGNED-> */ /* 342*/                    auditMap = new HashMap();
/* <-MISALIGNED-> */ /* 344*/                if(contentMap == null)
/* <-MISALIGNED-> */ /* 345*/                    contentMap = new HashMap();
/* <-MISALIGNED-> */ /* 347*/                String preNumber = null;
/* <-MISALIGNED-> */ /* 348*/                for(int i = e.getFirstRow(); i <= e.getLastRow(); i++)
                        {
/* <-MISALIGNED-> */ /* 349*/                    IRow row = tblMain.getRow(i);
/* <-MISALIGNED-> */ /* 350*/                    String idkey = row.getCell("id").getValue().toString();
/* <-MISALIGNED-> */ /* 351*/                    if(contentMap.containsKey(idkey))
/* <-MISALIGNED-> */ /* 352*/                        row.getCell("content").setValue(Boolean.TRUE);
/* <-MISALIGNED-> */ /* 355*/                    else
/* <-MISALIGNED-> */ /* 355*/                        row.getCell("content").setValue(Boolean.FALSE);
/* <-MISALIGNED-> */ /* 357*/                    if(attachMap.containsKey(idkey))
/* <-MISALIGNED-> */ /* 358*/                        row.getCell("attachment").setValue(Boolean.TRUE);
/* <-MISALIGNED-> */ /* 361*/                    else
/* <-MISALIGNED-> */ /* 361*/                        row.getCell("attachment").setValue(Boolean.FALSE);
/* <-MISALIGNED-> */ /* 363*/                    if(auditMap.containsKey(idkey))
                            {
/* <-MISALIGNED-> */ /* 364*/                        MultiApproveInfo info = (MultiApproveInfo)auditMap.get(idkey);
/* <-MISALIGNED-> */ /* 367*/                        row.getCell("wfAuditName").setValue(info.getCreator().getName());
/* <-MISALIGNED-> */ /* 370*/                        row.getCell("wfAuditTime").setValue(info.getCreateTime());
                            }
/* <-MISALIGNED-> */ /* 373*/                    if(!isMainOrder)
/* <-MISALIGNED-> */ /* 375*/                        continue;
/* <-MISALIGNED-> */ /* 375*/                    String number = "";
/* <-MISALIGNED-> */ /* 376*/                    if(row.getCell("mainContractNumber") == null)
/* <-MISALIGNED-> */ /* 377*/                        continue;
/* <-MISALIGNED-> */ /* 377*/                    if(row.getCell("mainContractNumber").getValue() != null)
/* <-MISALIGNED-> */ /* 378*/                        number = row.getCell("mainContractNumber").getValue().toString();
/* <-MISALIGNED-> */ /* 380*/                    if(!number.equals(preNumber))
/* <-MISALIGNED-> */ /* 381*/                        row.setTreeLevel(0);
/* <-MISALIGNED-> */ /* 382*/                    else
/* <-MISALIGNED-> */ /* 382*/                    if(!"".equals(number))
                            {
/* <-MISALIGNED-> */ /* 383*/                        row.setTreeLevel(1);
/* <-MISALIGNED-> */ /* 384*/                        tblMain.getTreeColumn().setDepth(2);
/* <-MISALIGNED-> */ /* 385*/                        row.getCell("number").setValue((new StringBuilder()).append("   ").append(row.getCell("number").getValue()).toString());
                            }
/* <-MISALIGNED-> */ /* 387*/                    preNumber = number;
                        }
                    }
                    final ContractFullListUI this$0;
                    
                    {
/* <-MISALIGNED-> */ /* 326*/                this$0 = ContractFullListUI.this;
                    }
        }
);
/* <-MISALIGNED-> */ /* 396*/        FDCClientUtils.checkCurrentCostCenterOrg(this);
/* <-MISALIGNED-> */ /* 397*/        tblMain.checkParsed();
/* <-MISALIGNED-> */ /* 398*/        FDCClientHelper.initTableListener(tblMain, this);
/* <-MISALIGNED-> */ /* 400*/        FDCHelper.formatTableNumber(tblMain, new String[] {
/* <-MISALIGNED-> */ /* 400*/            "amount", "originalAmount", "exRate", "conPaid"
                });
/* <-MISALIGNED-> */ /* 401*/        FDCHelper.formatTableDateTime(tblMain, "wfAuditTime");
/* <-MISALIGNED-> */ /* 402*/        FDCClientHelper.addSqlMenu(this, menuEdit);
/* <-MISALIGNED-> */ /* 403*/        super.onLoad();
/* <-MISALIGNED-> */ /* 404*/        afterOnLoad();
/* <-MISALIGNED-> */ /* 405*/        tblMain.getSelectManager().setSelectMode(2);
/* <-MISALIGNED-> */ /* 407*/        initWorkButton();
/* <-MISALIGNED-> */ /* 408*/        initTableStyle();
/* <-MISALIGNED-> */ /* 410*/        refreshTblMain(getMainQuery());
            }
            private void afterOnLoad()
            {
/* <-MISALIGNED-> */ /* 415*/        actionAddNew.setEnabled(false);
/* <-MISALIGNED-> */ /* 416*/        actionEdit.setEnabled(false);
/* <-MISALIGNED-> */ /* 417*/        actionRemove.setEnabled(false);
/* <-MISALIGNED-> */ /* 418*/        actionLocate.setEnabled(false);
/* <-MISALIGNED-> */ /* 419*/        actionCreateTo.setEnabled(false);
/* <-MISALIGNED-> */ /* 420*/        actionTraceDown.setEnabled(false);
/* <-MISALIGNED-> */ /* 421*/        actionTraceUp.setEnabled(false);
/* <-MISALIGNED-> */ /* 422*/        actionAudit.setEnabled(false);
/* <-MISALIGNED-> */ /* 423*/        actionAntiAudit.setEnabled(false);
/* <-MISALIGNED-> */ /* 424*/        actionAuditResult.setEnabled(false);
/* <-MISALIGNED-> */ /* 425*/        actionCopyTo.setEnabled(false);
/* <-MISALIGNED-> */ /* 426*/        actionMultiapprove.setEnabled(false);
/* <-MISALIGNED-> */ /* 427*/        actionNextPerson.setEnabled(false);
/* <-MISALIGNED-> */ /* 429*/        actionAddNew.setVisible(false);
/* <-MISALIGNED-> */ /* 430*/        actionEdit.setVisible(false);
/* <-MISALIGNED-> */ /* 431*/        actionRemove.setVisible(false);
/* <-MISALIGNED-> */ /* 432*/        actionLocate.setVisible(false);
/* <-MISALIGNED-> */ /* 433*/        actionCreateTo.setVisible(false);
/* <-MISALIGNED-> */ /* 434*/        actionTraceDown.setVisible(false);
/* <-MISALIGNED-> */ /* 435*/        actionTraceUp.setVisible(false);
/* <-MISALIGNED-> */ /* 436*/        actionAudit.setVisible(false);
/* <-MISALIGNED-> */ /* 437*/        actionAntiAudit.setVisible(false);
/* <-MISALIGNED-> */ /* 438*/        actionAuditResult.setVisible(false);
/* <-MISALIGNED-> */ /* 439*/        actionCopyTo.setVisible(false);
/* <-MISALIGNED-> */ /* 440*/        actionMultiapprove.setVisible(false);
/* <-MISALIGNED-> */ /* 441*/        actionNextPerson.setVisible(false);
/* <-MISALIGNED-> */ /* 443*/        FDCClientHelper.initTable(tblMain);
/* <-MISALIGNED-> */ /* 449*/        actionViewContent.setVisible(true);
/* <-MISALIGNED-> */ /* 450*/        actionViewContent.setEnabled(true);
            }
            protected String getEditUIModal()
            {
/* <-MISALIGNED-> */ /* 454*/        return UIFactoryName.NEWTAB;
            }
            protected boolean isIgnoreCUFilter()
            {
/* <-MISALIGNED-> */ /* 458*/        return true;
            }
            public void actionAntiAudit_actionPerformed(java.awt.event.ActionEvent actionevent)
                throws Exception
            {
            }
            public void actionAudit_actionPerformed(java.awt.event.ActionEvent actionevent)
                throws Exception
            {
            }
            public void actionRemove_actionPerformed(java.awt.event.ActionEvent actionevent)
                throws Exception
            {
            }
            protected OrgType getMainBizOrgType()
            {
/* <-MISALIGNED-> */ /* 482*/        return OrgType.CostCenter;
            }
            protected boolean isFootVisible()
            {
/* <-MISALIGNED-> */ /* 491*/        return true;
            }
            public void actionViewContent_actionPerformed(java.awt.event.ActionEvent arg0)
                throws Exception
            {
/* <-MISALIGNED-> */ /* 498*/        checkSelected();
/* <-MISALIGNED-> */ /* 500*/        com.kingdee.bos.util.BOSObjectType bosType = BOSUuid.read(getSelectedKeyValue()).getType();
/* <-MISALIGNED-> */ /* 502*/        ICoreBase coreBase = (ICoreBase)BOSObjectFactory.createRemoteBOSObject(bosType, ICoreBase.class);
/* <-MISALIGNED-> */ /* 504*/        com.kingdee.eas.framework.CoreBaseInfo objectInfo = coreBase.getValue(new ObjectUuidPK(getSelectedKeyValue()));
/* <-MISALIGNED-> */ /* 507*/        boolean isUseWriteMark = FDCUtils.getDefaultFDCParamByKey(null, null, "FDC213_WRITEMARK");
/* <-MISALIGNED-> */ /* 510*/        if(isUseWriteMark && objectInfo != null && (objectInfo instanceof FDCBillInfo))
/* <-MISALIGNED-> */ /* 511*/            ForWriteMarkHelper.isUseWriteMarkForListUI((FDCBillInfo)objectInfo, OprtState.VIEW, this);
/* <-MISALIGNED-> */ /* 513*/        else
/* <-MISALIGNED-> */ /* 513*/            ContractClientUtils.viewContent(this, getSelectedKeyValue());
            }
            public void actionContractSplit_actionPerformed(java.awt.event.ActionEvent e)
                throws Exception
            {
/* <-MISALIGNED-> */ /* 524*/        ContractFullListUtils.verifyAudited(tblMain);
/* <-MISALIGNED-> */ /* 526*/        Object contractId = tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue();
/* <-MISALIGNED-> */ /* 527*/        String UI = "com.kingdee.eas.fdc.contract.client.ContractCostSplitListUI";
/* <-MISALIGNED-> */ /* 528*/        String edit_action_permission = "ActionCostSplit";
/* <-MISALIGNED-> */ /* 529*/        String view_action_permission = "ActionView";
/* <-MISALIGNED-> */ /* 531*/        boolean hasEditPermission = ContractFullListUtils.verifyPermission(UI, edit_action_permission, currentUserInfoId, currentOrgUnitId);
/* <-MISALIGNED-> */ /* 532*/        boolean hasViewPermission = ContractFullListUtils.verifyPermission(UI, view_action_permission, currentUserInfoId, currentOrgUnitId);
/* <-MISALIGNED-> */ /* 534*/        if(ContractFullListUtils.isCostSplit(tblMain))
                {
/* <-MISALIGNED-> */ /* 535*/            EntityViewInfo view = new EntityViewInfo();
/* <-MISALIGNED-> */ /* 536*/            view.getSelector().add("id");
/* <-MISALIGNED-> */ /* 537*/            view.getSelector().add("state");
/* <-MISALIGNED-> */ /* 538*/            FilterInfo filter = new FilterInfo();
/* <-MISALIGNED-> */ /* 539*/            filter.getFilterItems().add(new FilterItemInfo("contractBill", contractId != null ? ((Object) (contractId.toString())) : ""));
/* <-MISALIGNED-> */ /* 540*/            filter.getFilterItems().add(new FilterItemInfo("isInvalid", Boolean.FALSE));
/* <-MISALIGNED-> */ /* 541*/            view.setFilter(filter);
/* <-MISALIGNED-> */ /* 542*/            ContractCostSplitCollection conCostSplitColl = ContractCostSplitFactory.getRemoteInstance().getContractCostSplitCollection(view);
/* <-MISALIGNED-> */ /* 543*/            if(conCostSplitColl != null && conCostSplitColl.size() == 0)
                    {
/* <-MISALIGNED-> */ /* 544*/                if(hasEditPermission)
                        {
/* <-MISALIGNED-> */ /* 547*/                    UIContext costSplitUIContext = new UIContext(this);
/* <-MISALIGNED-> */ /* 548*/                    costSplitUIContext.put("costBillID", contractId != null ? ((Object) (contractId.toString())) : "");
/* <-MISALIGNED-> */ /* 549*/                    IUIWindow costSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractCostSplitEditUI.class.getName(), costSplitUIContext, null, "ADDNEW");
/* <-MISALIGNED-> */ /* 550*/                    costSplitUIWindow.show();
                        } else
                        {
/* <-MISALIGNED-> */ /* 555*/                    FDCMsgBox.showWarning(this, "\u8BE5\u5408\u540C\u5C1A\u672A\u62C6\u5206\uFF0C\u4F46\u7531\u4E8E\u4F60\u6CA1\u6709\u7EF4\u62A4\u6743\u9650\uFF0C\u64CD\u4F5C\u7EC8\u6B62\uFF01");
/* <-MISALIGNED-> */ /* 556*/                    SysUtil.abort();
                        }
                    } else
/* <-MISALIGNED-> */ /* 558*/            if(conCostSplitColl != null && conCostSplitColl.size() != 0)
                    {
/* <-MISALIGNED-> */ /* 559*/                ContractCostSplitInfo conCostSplitInfo = conCostSplitColl.get(0);
/* <-MISALIGNED-> */ /* 560*/                if(conCostSplitInfo.getId() != null && conCostSplitInfo.getState() != null)
/* <-MISALIGNED-> */ /* 561*/                    if(conCostSplitInfo.getState().equals(FDCBillStateEnum.AUDITTED))
                            {
/* <-MISALIGNED-> */ /* 562*/                        if(hasViewPermission)
                                {
/* <-MISALIGNED-> */ /* 563*/                            UIContext costSplitUIContext = new UIContext(this);
/* <-MISALIGNED-> */ /* 564*/                            costSplitUIContext.put("ID", conCostSplitInfo.getId().toString());
/* <-MISALIGNED-> */ /* 565*/                            IUIWindow costSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractCostSplitEditUI.class.getName(), costSplitUIContext, null, "VIEW");
/* <-MISALIGNED-> */ /* 566*/                            costSplitUIWindow.show();
                                } else
                                {
/* <-MISALIGNED-> */ /* 568*/                            FDCMsgBox.showWarning(this, "\u8BE5\u5408\u540C\u5BF9\u5E94\u7684\u5408\u540C\u62C6\u5206\u5DF2\u7ECF\u5BA1\u6279\uFF0C\u7531\u4E8E\u4F60\u6CA1\u6709\u67E5\u770B\u6743\u9650\uFF0C\u64CD\u4F5C\u7EC8\u6B62\uFF01");
/* <-MISALIGNED-> */ /* 569*/                            SysUtil.abort();
                                }
                            } else
/* <-MISALIGNED-> */ /* 572*/                    if(hasEditPermission)
                            {
/* <-MISALIGNED-> */ /* 573*/                        boolean canSplit = ContractFullListUtils.verifyCanSplit(tblMain, true);
/* <-MISALIGNED-> */ /* 574*/                        if(canSplit)
                                {
/* <-MISALIGNED-> */ /* 575*/                            UIContext costSplitUIContext = new UIContext(this);
/* <-MISALIGNED-> */ /* 576*/                            costSplitUIContext.put("ID", conCostSplitInfo.getId().toString());
/* <-MISALIGNED-> */ /* 577*/                            IUIWindow costSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractCostSplitEditUI.class.getName(), costSplitUIContext, null, "EDIT");
/* <-MISALIGNED-> */ /* 578*/                            costSplitUIWindow.show();
                                } else
/* <-MISALIGNED-> */ /* 580*/                        if(hasViewPermission)
                                {
/* <-MISALIGNED-> */ /* 581*/                            UIContext costSplitUIContext = new UIContext(this);
/* <-MISALIGNED-> */ /* 582*/                            costSplitUIContext.put("ID", conCostSplitInfo.getId().toString());
/* <-MISALIGNED-> */ /* 583*/                            IUIWindow costSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractCostSplitEditUI.class.getName(), costSplitUIContext, null, "VIEW");
/* <-MISALIGNED-> */ /* 584*/                            costSplitUIWindow.show();
                                } else
                                {
/* <-MISALIGNED-> */ /* 586*/                            FDCMsgBox.showWarning("\u8BE5\u5408\u540C\u5BF9\u5E94\u7684\u5408\u540C\u62C6\u5206\u5DF2\u7ECF\u88AB\u5F15\u7528\uFF0C\u4E0D\u80FD\u518D\u4FEE\u6539\u62C6\u5206\u5355\u636E\uFF0C\u4EC5\u4F9B\u67E5\u770B\u3002\u4F46\u662F\u7531\u4E8E\u4F60\u6CA1\u6709\u67E5\u770B\u6743\u9650\uFF0C\u64CD\u4F5C\u7EC8\u6B62\uFF01");
/* <-MISALIGNED-> */ /* 587*/                            SysUtil.abort();
                                }
                            } else
/* <-MISALIGNED-> */ /* 591*/                    if(!hasViewPermission)
                            {
/* <-MISALIGNED-> */ /* 592*/                        FDCMsgBox.showWarning(this, "\u8BE5\u5408\u540C\u5BF9\u5E94\u7684\u5408\u540C\u62C6\u5206\u5C1A\u672A\u5BA1\u6279\uFF0C\u4F46\u7531\u4E8E\u4F60\u6CA1\u6709\u7EF4\u62A4\u6216\u8005\u662F\u67E5\u770B\u5408\u540C\u62C6\u5206\u7684\u6743\u9650\uFF0C\u64CD\u4F5C\u7EC8\u6B62\uFF01");
/* <-MISALIGNED-> */ /* 593*/                        SysUtil.abort();
                            } else
                            {
/* <-MISALIGNED-> */ /* 595*/                        UIContext costSplitUIContext = new UIContext(this);
/* <-MISALIGNED-> */ /* 596*/                        costSplitUIContext.put("ID", conCostSplitInfo.getId().toString());
/* <-MISALIGNED-> */ /* 597*/                        IUIWindow costSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractCostSplitEditUI.class.getName(), costSplitUIContext, null, "VIEW");
/* <-MISALIGNED-> */ /* 598*/                        costSplitUIWindow.show();
                            }
                    }
                } else
                {
/* <-MISALIGNED-> */ /* 605*/            EntityViewInfo view = new EntityViewInfo();
/* <-MISALIGNED-> */ /* 606*/            view.getSelector().add("id");
/* <-MISALIGNED-> */ /* 607*/            view.getSelector().add("state");
/* <-MISALIGNED-> */ /* 608*/            FilterInfo filter = new FilterInfo();
/* <-MISALIGNED-> */ /* 609*/            filter.getFilterItems().add(new FilterItemInfo("contractBill", contractId != null ? ((Object) (contractId.toString())) : ""));
/* <-MISALIGNED-> */ /* 610*/            filter.getFilterItems().add(new FilterItemInfo("isInvalid", Boolean.FALSE));
/* <-MISALIGNED-> */ /* 611*/            view.setFilter(filter);
/* <-MISALIGNED-> */ /* 612*/            ConNoCostSplitCollection conNoCostSplitColl = ConNoCostSplitFactory.getRemoteInstance().getConNoCostSplitCollection(view);
/* <-MISALIGNED-> */ /* 613*/            if(conNoCostSplitColl != null && conNoCostSplitColl.size() == 0)
                    {
/* <-MISALIGNED-> */ /* 614*/                if(hasEditPermission)
                        {
/* <-MISALIGNED-> */ /* 615*/                    UIContext nonCostSplitUIContext = new UIContext(this);
/* <-MISALIGNED-> */ /* 616*/                    nonCostSplitUIContext.put("costBillID", contractId != null ? ((Object) (contractId.toString())) : "");
/* <-MISALIGNED-> */ /* 617*/                    IUIWindow nonCostSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ConNoCostSplitEditUI.class.getName(), nonCostSplitUIContext, null, "ADDNEW");
/* <-MISALIGNED-> */ /* 618*/                    nonCostSplitUIWindow.show();
                        } else
                        {
/* <-MISALIGNED-> */ /* 620*/                    FDCMsgBox.showWarning(this, "\u8BE5\u5408\u540C\u5C1A\u672A\u62C6\u5206\uFF0C\u4F46\u7531\u4E8E\u4F60\u6CA1\u6709\u7EF4\u62A4\u6743\u9650\uFF0C\u64CD\u4F5C\u7EC8\u6B62\uFF01");
/* <-MISALIGNED-> */ /* 621*/                    SysUtil.abort();
                        }
                    } else
/* <-MISALIGNED-> */ /* 623*/            if(conNoCostSplitColl != null && conNoCostSplitColl.size() != 0)
                    {
/* <-MISALIGNED-> */ /* 624*/                ConNoCostSplitInfo conCostSplitInfo = conNoCostSplitColl.get(0);
/* <-MISALIGNED-> */ /* 625*/                if(conCostSplitInfo.getId() != null && conCostSplitInfo.getState() != null)
/* <-MISALIGNED-> */ /* 626*/                    if(conCostSplitInfo.getState().equals(FDCBillStateEnum.AUDITTED))
                            {
/* <-MISALIGNED-> */ /* 627*/                        if(hasViewPermission)
                                {
/* <-MISALIGNED-> */ /* 628*/                            UIContext nonCostSplitUIContext = new UIContext(this);
/* <-MISALIGNED-> */ /* 629*/                            nonCostSplitUIContext.put("ID", conCostSplitInfo.getId().toString());
/* <-MISALIGNED-> */ /* 630*/                            IUIWindow nonCostSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ConNoCostSplitEditUI.class.getName(), nonCostSplitUIContext, null, "VIEW");
/* <-MISALIGNED-> */ /* 631*/                            nonCostSplitUIWindow.show();
                                } else
                                {
/* <-MISALIGNED-> */ /* 633*/                            FDCMsgBox.showWarning(this, "\u8BE5\u5408\u540C\u5BF9\u5E94\u7684\u5408\u540C\u62C6\u5206\u5DF2\u7ECF\u5BA1\u6279\uFF0C\u7531\u4E8E\u4F60\u6CA1\u6709\u67E5\u770B\u6743\u9650\uFF0C\u64CD\u4F5C\u7EC8\u6B62\uFF01");
/* <-MISALIGNED-> */ /* 634*/                            SysUtil.abort();
                                }
                            } else
/* <-MISALIGNED-> */ /* 637*/                    if(hasEditPermission)
                            {
/* <-MISALIGNED-> */ /* 639*/                        boolean canSplit = ContractFullListUtils.verifyCanSplit(tblMain, false);
/* <-MISALIGNED-> */ /* 640*/                        if(canSplit)
                                {
/* <-MISALIGNED-> */ /* 641*/                            UIContext nonCostSplitUIContext = new UIContext(this);
/* <-MISALIGNED-> */ /* 642*/                            nonCostSplitUIContext.put("ID", conCostSplitInfo.getId().toString());
/* <-MISALIGNED-> */ /* 643*/                            IUIWindow nonCostSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ConNoCostSplitEditUI.class.getName(), nonCostSplitUIContext, null, "EDIT");
/* <-MISALIGNED-> */ /* 644*/                            nonCostSplitUIWindow.show();
                                } else
/* <-MISALIGNED-> */ /* 646*/                        if(hasViewPermission)
                                {
/* <-MISALIGNED-> */ /* 647*/                            UIContext nonCostSplitUIContext = new UIContext(this);
/* <-MISALIGNED-> */ /* 648*/                            nonCostSplitUIContext.put("ID", conCostSplitInfo.getId().toString());
/* <-MISALIGNED-> */ /* 649*/                            IUIWindow nonCostSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ConNoCostSplitEditUI.class.getName(), nonCostSplitUIContext, null, "VIEW");
/* <-MISALIGNED-> */ /* 650*/                            nonCostSplitUIWindow.show();
                                } else
                                {
/* <-MISALIGNED-> */ /* 652*/                            FDCMsgBox.showWarning("\u8BE5\u5408\u540C\u5BF9\u5E94\u7684\u5408\u540C\u62C6\u5206\u5DF2\u7ECF\u88AB\u5F15\u7528\uFF0C\u4E0D\u80FD\u518D\u4FEE\u6539\u62C6\u5206\u5355\u636E\uFF0C\u4EC5\u4F9B\u67E5\u770B\u3002\u4F46\u662F\u7531\u4E8E\u4F60\u6CA1\u6709\u67E5\u770B\u6743\u9650\uFF0C\u64CD\u4F5C\u7EC8\u6B62\uFF01");
/* <-MISALIGNED-> */ /* 653*/                            SysUtil.abort();
                                }
                            } else
/* <-MISALIGNED-> */ /* 657*/                    if(!hasViewPermission)
                            {
/* <-MISALIGNED-> */ /* 658*/                        FDCMsgBox.showWarning(this, "\u8BE5\u5408\u540C\u5BF9\u5E94\u7684\u5408\u540C\u62C6\u5206\u5C1A\u672A\u5BA1\u6279\uFF0C\u4F46\u7531\u4E8E\u4F60\u6CA1\u6709\u7EF4\u62A4\u6216\u8005\u662F\u67E5\u770B\u5408\u540C\u62C6\u5206\u7684\u6743\u9650\uFF0C\u64CD\u4F5C\u7EC8\u6B62\uFF01");
/* <-MISALIGNED-> */ /* 659*/                        SysUtil.abort();
                            } else
                            {
/* <-MISALIGNED-> */ /* 661*/                        UIContext nonCostSplitUIContext = new UIContext(this);
/* <-MISALIGNED-> */ /* 662*/                        nonCostSplitUIContext.put("ID", conCostSplitInfo.getId().toString());
/* <-MISALIGNED-> */ /* 663*/                        IUIWindow nonCostSplitUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ConNoCostSplitEditUI.class.getName(), nonCostSplitUIContext, null, "VIEW");
/* <-MISALIGNED-> */ /* 664*/                        nonCostSplitUIWindow.show();
                            }
                    }
                }
            }
            public void actionAddChangeAudit_actionPerformed(java.awt.event.ActionEvent e)
                throws Exception
            {
/* <-MISALIGNED-> */ /* 678*/        ContractFullListUtils.verifyHasSettled(tblMain);
/* <-MISALIGNED-> */ /* 679*/        Object contractId = tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue();
/* <-MISALIGNED-> */ /* 680*/        String UI = "com.kingdee.eas.fdc.contract.client.ChangeAuditListUI";
/* <-MISALIGNED-> */ /* 681*/        String view_action_permission = "ActionAddNew";
/* <-MISALIGNED-> */ /* 682*/        boolean hasAddPermission = ContractFullListUtils.verifyPermission(UI, view_action_permission, currentUserInfoId, currentOrgUnitId);
/* <-MISALIGNED-> */ /* 683*/        if(hasAddPermission)
                {
/* <-MISALIGNED-> */ /* 684*/            EntityViewInfo view = new EntityViewInfo();
/* <-MISALIGNED-> */ /* 685*/            view.setFilter(new FilterInfo());
/* <-MISALIGNED-> */ /* 686*/            view.getFilter().getFilterItems().add(new FilterItemInfo("id", contractId));
/* <-MISALIGNED-> */ /* 687*/            SelectorItemCollection sic = new SelectorItemCollection();
/* <-MISALIGNED-> */ /* 688*/            view.setSelector(sic);
/* <-MISALIGNED-> */ /* 689*/            sic.add(new SelectorItemInfo("number"));
/* <-MISALIGNED-> */ /* 690*/            sic.add(new SelectorItemInfo("name"));
/* <-MISALIGNED-> */ /* 691*/            sic.add(new SelectorItemInfo("partB.number"));
/* <-MISALIGNED-> */ /* 692*/            sic.add(new SelectorItemInfo("partB.name"));
/* <-MISALIGNED-> */ /* 693*/            sic.add(new SelectorItemInfo("curProject.id"));
/* <-MISALIGNED-> */ /* 694*/            sic.add(new SelectorItemInfo("curProject.name"));
/* <-MISALIGNED-> */ /* 695*/            sic.add(new SelectorItemInfo("curProject.number"));
/* <-MISALIGNED-> */ /* 696*/            sic.add(new SelectorItemInfo("curProject.cu"));
/* <-MISALIGNED-> */ /* 698*/            sic.add(new SelectorItemInfo("currency.number"));
/* <-MISALIGNED-> */ /* 699*/            sic.add(new SelectorItemInfo("currency.name"));
/* <-MISALIGNED-> */ /* 700*/            sic.add(new SelectorItemInfo("currency.precision"));
/* <-MISALIGNED-> */ /* 701*/            sic.add(new SelectorItemInfo("isCoseSplit"));
/* <-MISALIGNED-> */ /* 702*/            sic.add(new SelectorItemInfo("hasSettled"));
/* <-MISALIGNED-> */ /* 703*/            sic.add(new SelectorItemInfo("currency.precision"));
/* <-MISALIGNED-> */ /* 704*/            ContractBillInfo info = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId.toString())), sic);
/* <-MISALIGNED-> */ /* 705*/            UIContext changeAuditUIContext = new UIContext(this);
/* <-MISALIGNED-> */ /* 706*/            changeAuditUIContext.put("contract", info);
/* <-MISALIGNED-> */ /* 707*/            changeAuditUIContext.put("contractBillId", contractId != null ? ((Object) (contractId.toString())) : "");
/* <-MISALIGNED-> */ /* 708*/            changeAuditUIContext.put("projectId", info.getCurProject().getId());
/* <-MISALIGNED-> */ /* 709*/            IUIWindow changeAuditUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ChangeAuditEditUI.class.getName(), changeAuditUIContext, null, "ADDNEW");
/* <-MISALIGNED-> */ /* 710*/            changeAuditUIWindow.show();
                } else
                {
/* <-MISALIGNED-> */ /* 712*/            FDCMsgBox.showWarning(this, "\u4F60\u6CA1\u6709\u65B0\u589E\u6743\u9650\uFF0C\u64CD\u4F5C\u7EC8\u6B62\uFF01");
/* <-MISALIGNED-> */ /* 713*/            SysUtil.abort();
                }
            }
            public void actionAddContractSettlement_actionPerformed(java.awt.event.ActionEvent e)
                throws Exception
            {
/* <-MISALIGNED-> */ /* 721*/        ContractFullListUtils.verifyAudited(tblMain);
/* <-MISALIGNED-> */ /* 722*/        ContractFullListUtils.verifyHasSettled(tblMain);
/* <-MISALIGNED-> */ /* 724*/        Object contractId = tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue();
/* <-MISALIGNED-> */ /* 725*/        String UI = "com.kingdee.eas.fdc.contract.client.ContractSettlementBillListUI";
/* <-MISALIGNED-> */ /* 726*/        String view_action_permission = "ActionAddNew";
/* <-MISALIGNED-> */ /* 727*/        boolean hasAddPermission = ContractFullListUtils.verifyPermission(UI, view_action_permission, currentUserInfoId, currentOrgUnitId);
/* <-MISALIGNED-> */ /* 728*/        if(hasAddPermission)
                {
/* <-MISALIGNED-> */ /* 729*/            UIContext conSettleUIContext = new UIContext(this);
/* <-MISALIGNED-> */ /* 730*/            conSettleUIContext.put("contractBillId", contractId != null ? ((Object) (contractId.toString())) : "");
/* <-MISALIGNED-> */ /* 731*/            IUIWindow conSettleUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractSettlementBillEditUI.class.getName(), conSettleUIContext, null, "ADDNEW");
/* <-MISALIGNED-> */ /* 732*/            conSettleUIWindow.show();
                } else
                {
/* <-MISALIGNED-> */ /* 734*/            FDCMsgBox.showWarning(this, "\u4F60\u6CA1\u6709\u65B0\u589E\u6743\u9650\uFF0C\u64CD\u4F5C\u7EC8\u6B62\uFF01");
/* <-MISALIGNED-> */ /* 735*/            SysUtil.abort();
                }
            }
            public void actionAddPayRequest_actionPerformed(java.awt.event.ActionEvent e)
                throws Exception
            {
/* <-MISALIGNED-> */ /* 744*/        ContractFullListUtils.verifyAudited(tblMain);
/* <-MISALIGNED-> */ /* 746*/        Object contractId = tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue();
/* <-MISALIGNED-> */ /* 747*/        String UI = "com.kingdee.eas.fdc.contract.client.PayRequestBillListUI";
/* <-MISALIGNED-> */ /* 748*/        String view_action_permission = "ActionAddNew";
/* <-MISALIGNED-> */ /* 749*/        boolean hasAddPermission = ContractFullListUtils.verifyPermission(UI, view_action_permission, currentUserInfoId, currentOrgUnitId);
/* <-MISALIGNED-> */ /* 750*/        if(hasAddPermission)
                {
/* <-MISALIGNED-> */ /* 751*/            UIContext changeAuditUIContext = new UIContext(this);
/* <-MISALIGNED-> */ /* 752*/            changeAuditUIContext.put("contractBillId", contractId != null ? ((Object) (contractId.toString())) : "");
/* <-MISALIGNED-> */ /* 753*/            IUIWindow changeAuditUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(PayRequestBillEditUI.class.getName(), changeAuditUIContext, null, "ADDNEW");
/* <-MISALIGNED-> */ /* 754*/            changeAuditUIWindow.show();
                } else
                {
/* <-MISALIGNED-> */ /* 756*/            FDCMsgBox.showWarning(this, "\u4F60\u6CA1\u6709\u65B0\u589E\u6743\u9650\uFF0C\u64CD\u4F5C\u7EC8\u6B62\uFF01");
/* <-MISALIGNED-> */ /* 757*/            SysUtil.abort();
                }
            }
            public void actionPaymentListUI_actionPerformed(java.awt.event.ActionEvent e)
                throws Exception
            {
/* <-MISALIGNED-> */ /* 765*/        if(tblMain.getRowCount() == 0)
                {
/* <-MISALIGNED-> */ /* 766*/            FDCMsgBox.showWarning("\u8BF7\u5148\u9009\u62E9\u4E00\u884C\u5355\u636E\uFF01");
/* <-MISALIGNED-> */ /* 767*/            SysUtil.abort();
                }
/* <-MISALIGNED-> */ /* 769*/        String UI = "com.kingdee.eas.fdc.finance.client.PaymentBillListUI";
/* <-MISALIGNED-> */ /* 770*/        String view_action_permission = "ActionView";
/* <-MISALIGNED-> */ /* 771*/        boolean hasViewPermission = ContractFullListUtils.verifyPermission(UI, view_action_permission, currentUserInfoId, currentOrgUnitId);
/* <-MISALIGNED-> */ /* 772*/        if(hasViewPermission)
                {
/* <-MISALIGNED-> */ /* 773*/            UIContext paymentListUIContext = new UIContext(this);
/* <-MISALIGNED-> */ /* 774*/            IUIWindow paymentListUIWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(PaymentBillListUI.class.getName(), paymentListUIContext, null, "EDIT");
/* <-MISALIGNED-> */ /* 775*/            paymentListUIWindow.show();
                } else
                {
/* <-MISALIGNED-> */ /* 777*/            FDCMsgBox.showWarning(this, "\u4F60\u6CA1\u6709\u67E5\u770B\u6743\u9650\uFF0C\u64CD\u4F5C\u7EC8\u6B62\uFF01");
/* <-MISALIGNED-> */ /* 778*/            SysUtil.abort();
                }
            }
            protected void initWorkButton()
            {
/* <-MISALIGNED-> */ /* 782*/        super.initWorkButton();
/* <-MISALIGNED-> */ /* 783*/        menuBiz.setVisible(true);
/* <-MISALIGNED-> */ /* 784*/        menuBiz.setEnabled(true);
/* <-MISALIGNED-> */ /* 785*/        btnViewContent.setIcon(EASResource.getIcon("imgTbtn_seeperformance"));
/* <-MISALIGNED-> */ /* 786*/        btnContractSplit.setIcon(EASResource.getIcon("imgTbtn_split"));
/* <-MISALIGNED-> */ /* 787*/        btnAddChangeAudit.setIcon(EASResource.getIcon("imgTbtn_new"));
/* <-MISALIGNED-> */ /* 788*/        btnAddContractSettlement.setIcon(EASResource.getIcon("imgTbtn_new"));
/* <-MISALIGNED-> */ /* 789*/        btnAddPayRequest.setIcon(EASResource.getIcon("imgTbtn_new"));
/* <-MISALIGNED-> */ /* 790*/        btnPaymentListUI.setIcon(EASResource.getIcon("imgTbtn_listfile"));
/* <-MISALIGNED-> */ /* 791*/        menuItemView.setIcon(EASResource.getIcon("imgTbtn_seeperformance"));
/* <-MISALIGNED-> */ /* 792*/        menuItemViewContent.setIcon(EASResource.getIcon("imgTbtn_seeperformance"));
/* <-MISALIGNED-> */ /* 793*/        menuItemViewContent.setAction(btnViewContent.getAction());
/* <-MISALIGNED-> */ /* 794*/        menuItemContractSplit.setIcon(EASResource.getIcon("imgTbtn_split"));
/* <-MISALIGNED-> */ /* 795*/        menuItemAddChangeAudit.setIcon(EASResource.getIcon("imgTbtn_new"));
/* <-MISALIGNED-> */ /* 796*/        menuItemAddConSettlement.setIcon(EASResource.getIcon("imgTbtn_new"));
/* <-MISALIGNED-> */ /* 797*/        menuItemAddPayRequest.setIcon(EASResource.getIcon("imgTbtn_new"));
/* <-MISALIGNED-> */ /* 798*/        menuItemPaymentListUI.setIcon(EASResource.getIcon("imgTbtn_listfile"));
/* <-MISALIGNED-> */ /* 799*/        btnAuditResult.setEnabled(true);
/* <-MISALIGNED-> */ /* 800*/        btnAuditResult.setVisible(true);
            }
            protected void updateButtonStatus()
            {
            }
            public void refreshListForOrder()
                throws Exception
            {
/* <-MISALIGNED-> */ /* 808*/label0:
                {
/* <-MISALIGNED-> */ /* 808*/            SorterItemCollection sortItems = mainQuery.getSorter();
/* <-MISALIGNED-> */ /* 809*/            if(sortItems == null || sortItems.size() <= 0)
/* <-MISALIGNED-> */ /* 810*/                break label0;
/* <-MISALIGNED-> */ /* 810*/            Iterator it = sortItems.iterator();
                    SorterItemInfo sortItem;
/* <-MISALIGNED-> */ /* 810*/            do
                    {
/* <-MISALIGNED-> */ /* 810*/                if(!it.hasNext())
/* <-MISALIGNED-> */ /* 811*/                    break label0;
/* <-MISALIGNED-> */ /* 811*/                sortItem = (SorterItemInfo)it.next();
                    } while(!sortItem.getPropertyName().equals("mainContractNumber"));
/* <-MISALIGNED-> */ /* 813*/            SorterItemInfo cloneSort = (SorterItemInfo)sortItem.clone();
/* <-MISALIGNED-> */ /* 814*/            SorterItemInfo newSort = new SorterItemInfo();
/* <-MISALIGNED-> */ /* 815*/            newSort.setPropertyName("contractPropert");
/* <-MISALIGNED-> */ /* 816*/            newSort.setSortType(SortType.ASCEND);
/* <-MISALIGNED-> */ /* 817*/            mainQuery.getSorter().clear();
/* <-MISALIGNED-> */ /* 818*/            mainQuery.getSorter().add(cloneSort);
/* <-MISALIGNED-> */ /* 819*/            mainQuery.getSorter().add(newSort);
                }
/* <-MISALIGNED-> */ /* 824*/        refreshTblMain(mainQuery);
/* <-MISALIGNED-> */ /* 829*/        auditMap.clear();
/* <-MISALIGNED-> */ /* 830*/        Set contractIds = new HashSet();
/* <-MISALIGNED-> */ /* 831*/        for(int i = 0; i < tblMain.getRowCount(); i++)
                {
/* <-MISALIGNED-> */ /* 833*/            IRow row = tblMain.getRow(i);
/* <-MISALIGNED-> */ /* 834*/            String contractId = row.getCell("id").getValue().toString();
/* <-MISALIGNED-> */ /* 835*/            contractIds.add(contractId);
                }
/* <-MISALIGNED-> */ /* 838*/        try
                {
/* <-MISALIGNED-> */ /* 838*/            auditMap = FDCBillWFFacadeFactory.getRemoteInstance().getWFBillLastAuditorAndTime(contractIds);
                }
/* <-MISALIGNED-> */ /* 839*/        catch(BOSException e)
                {
/* <-MISALIGNED-> */ /* 840*/            handUIExceptionAndAbort(e);
                }
/* <-MISALIGNED-> */ /* 841*/        catch(EASBizException e)
                {
/* <-MISALIGNED-> */ /* 842*/            handUIExceptionAndAbort(e);
                }
/* <-MISALIGNED-> */ /* 844*/        for(int k = 0; k < tblMain.getRowCount(); k++)
                {
/* <-MISALIGNED-> */ /* 846*/            IRow row = tblMain.getRow(k);
/* <-MISALIGNED-> */ /* 848*/            String key = row.getCell("id").getValue().toString();
/* <-MISALIGNED-> */ /* 850*/            if(auditMap.containsKey(key))
                    {
/* <-MISALIGNED-> */ /* 851*/                MultiApproveInfo info = (MultiApproveInfo)auditMap.get(key);
/* <-MISALIGNED-> */ /* 852*/                row.getCell("wfAuditName").setValue(info.getCreator().getName());
/* <-MISALIGNED-> */ /* 853*/                row.getCell("wfAuditTime").setValue(info.getCreateTime());
                    }
                }
            }
            public void actionAttachment_actionPerformed(java.awt.event.ActionEvent e)
                throws Exception
            {
/* <-MISALIGNED-> */ /* 860*/        boolean isEdit = false;
/* <-MISALIGNED-> */ /* 861*/        AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
/* <-MISALIGNED-> */ /* 862*/        String boID = getSelectedKeyValue();
/* <-MISALIGNED-> */ /* 863*/        checkSelected();
/* <-MISALIGNED-> */ /* 864*/        if(boID == null)
                {
/* <-MISALIGNED-> */ /* 866*/            return;
                } else
                {
/* <-MISALIGNED-> */ /* 869*/            acm.showAttachmentListUIByBoID(boID, this, isEdit);
/* <-MISALIGNED-> */ /* 870*/            return;
                }
            }
            protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo)
            {
/* <-MISALIGNED-> */ /* 878*/        IQueryExecutor queryExecutor = super.getQueryExecutor(queryPK, viewInfo);
/* <-MISALIGNED-> */ /* 880*/        queryExecutor.option().isAutoIgnoreZero = false;
/* <-MISALIGNED-> */ /* 882*/        return queryExecutor;
            }
            private void initTableStyle()
            {
/* <-MISALIGNED-> */ /* 890*/        FDCHelper.formatTableNumber(getMainTable(), "amount");
/* <-MISALIGNED-> */ /* 891*/        FDCHelper.formatTableNumber(getMainTable(), "originalAmount");
/* <-MISALIGNED-> */ /* 892*/        FDCHelper.formatTableDate(getMainTable(), "wfAuditTime");
            }
            public boolean isPrepareInit()
            {
/* <-MISALIGNED-> */ /* 900*/        return true;
            }
            private ContractBillEntryCollection getContractBillEntryCols(ContractBillInfo billInfo)
            {
/* <-MISALIGNED-> */ /* 914*/        ContractBillEntryCollection cols = null;
/* <-MISALIGNED-> */ /* 915*/        if(null != billInfo && null != billInfo.getEntrys())
/* <-MISALIGNED-> */ /* 916*/            cols = billInfo.getEntrys();
/* <-MISALIGNED-> */ /* 919*/        return cols;
            }
            private boolean isLonelyCal(ContractBillEntryCollection cols)
            {



/* 933*/        boolean isLonelyCal = false;
/* 934*/        if(null != cols)
                {/* 935*/            ContractBillEntryInfo entryInfo = null;
/* 936*/            for(int i = 0; i < cols.size(); i++)
                    {/* 937*/                entryInfo = cols.get(i);
/* 938*/                if(null == entryInfo.getDetail() || !entryInfo.getDetail().toString().equals("\u662F\u5426\u5355\u72EC\u8BA1\u7B97"))
/* 939*/                    continue;/* 939*/                String content = entryInfo.getContent();
/* 940*/                if(content.equals("\u662F"))
/* 941*/                    isLonelyCal = true;

/* 943*/                else/* 943*/                    isLonelyCal = false;
                    }
                }



/* 949*/        return isLonelyCal;
            }
            private HashSet getSupplyContract(String contractBillId)
                throws BOSException, SQLException
            {











/* 965*/        HashSet suppplySet = new HashSet();

/* 967*/        FDCSQLBuilder buildSQL = new FDCSQLBuilder();
/* 968*/        buildSQL.appendSql("select bill.fid id,bill.fcontractpropert ,entry.* from t_con_contractbill bill left outer join t_con_contractbillentry entry on bill.fid=entry.fparentid ");
/* 969*/        buildSQL.appendSql((new StringBuilder()).append("where bill.fcontractpropert = 'SUPPLY' and entry.fcontent = '").append(contractBillId).append("'").toString());
                String id;/* 970*/        for(IRowSet rowSet = buildSQL.executeQuery(); rowSet.next(); suppplySet.add(id))

/* 972*/            id = rowSet.getString("id");


/* 975*/        return suppplySet;
            }
            private void refreshTblMain(EntityViewInfo view)
                throws BOSException, SQLException, EASBizException
            {








/* 988*/        tblMain.removeRows(false);
/* 989*/        tblMain.getDataRequestManager().setDataRequestMode(0);
/* 990*/        tblMain.getSelectManager().setSelectMode(2);

/* 992*/        EntityViewInfo viewInfo = view;
/* 993*/        viewInfo.getSelector().clear();
/* 994*/        viewInfo.setSelector(getSelectors());
/* 995*/        viewInfo.getSelector();
/* 996*/        ContractBillCollection billColls = ContractBillFactory.getRemoteInstance().getContractBillCollection(viewInfo);
/* 997*/        if(null == billColls || billColls.size() < 0)
/* 998*/            return;


/*1001*/        Map addConMap = new HashMap();
/*1002*/        Set contractIds = new HashSet();
/*1003*/        for(int i = 0; i < billColls.size(); i++)
                {/*1004*/            ContractBillInfo billInfo = billColls.get(i);
/*1005*/            String contractBillId = billInfo.getId().toString();

/*1007*/            String mainConNumber = billInfo.getMainContractNumber();
/*1008*/            List addConList = null;
/*1009*/            if(StringUtils.isNotBlank(mainConNumber))
                    {/*1010*/                addConList = (List)addConMap.get(mainConNumber);
/*1011*/                if(addConList == null)
                        {/*1012*/                    addConList = new ArrayList();
/*1013*/                    addConMap.put(mainConNumber, addConList);
                        }
/*1015*/                addConList.add(billInfo);
                    }


/*1019*/            contractIds.add(contractBillId);
                }


/*1023*/        Set hasInsertCon = new HashSet();

/*1025*/label0:/*1025*/        for(int i = 0; i < billColls.size(); i++)
                {/*1026*/            ContractBillInfo billInfo = billColls.get(i);
/*1027*/            String contractBillId = billInfo.getId().toString();
/*1028*/            hasInsertCon.add(contractBillId);
/*1029*/            if(null != billInfo.getContractPropert() && !billInfo.getContractPropert().toString().equals("\u8865\u5145\u5408\u540C"))
                    {
/*1031*/                String mainConNumber = billInfo.getNumber();
/*1032*/                List addConList = (List)addConMap.get(mainConNumber);
/*1033*/                IRow row = tblMain.addRow();
/*1034*/                int level = row.getTreeLevel();
/*1035*/                int dep = tblMain.getTreeColumn().getDepth();
/*1036*/                if(dep < level + 1)
/*1037*/                    tblMain.getTreeColumn().setDepth(level + 1);

/*1039*/                row.setUserObject(billInfo);
/*1040*/                fillSupplyRow(row, billInfo);

/*1042*/                if(addConList == null || addConList.size() <= 0)
/*1043*/                    continue;/*1043*/                int j = 0;/*1043*/                do
                        {
/* <-MISALIGNED-> */ /*1043*/                    if(j >= addConList.size())
/* <-MISALIGNED-> */ /*1044*/                        continue label0;
/* <-MISALIGNED-> */ /*1044*/                    ContractBillInfo supplyBillInfo = (ContractBillInfo)addConList.get(j);
/* <-MISALIGNED-> */ /*1045*/                    IRow newRow = tblMain.addRow();
/* <-MISALIGNED-> */ /*1046*/                    newRow.setUserObject(supplyBillInfo);
/* <-MISALIGNED-> */ /*1047*/                    newRow.setTreeLevel(level + 1);
/* <-MISALIGNED-> */ /*1048*/                    fillSupplyRow(newRow, supplyBillInfo);
/* <-MISALIGNED-> */ /*1049*/                    if(null != newRow.getCell("isLonelyCal"))
/* <-MISALIGNED-> */ /*1050*/                        newRow.getCell("isLonelyCal").setValue(Boolean.valueOf(isLonelyCal(getContractBillEntryCols(supplyBillInfo))));
/* <-MISALIGNED-> */ /*1052*/                    hasInsertCon.add(supplyBillInfo.getId().toString());
/* <-MISALIGNED-> */ /*1043*/                    j++;
                        } while(true);
                    }




































/*1094*/            if(!isAddSupplyRow(contractIds, billInfo))
/*1095*/                continue;/*1095*/            IRow newRow = tblMain.addRow();
/*1096*/            newRow.setUserObject(billInfo);
/*1097*/            fillSupplyRow(newRow, billInfo);
/*1098*/            if(null != newRow.getCell("isLonelyCal"))
/*1099*/                newRow.getCell("isLonelyCal").setValue(Boolean.valueOf(isLonelyCal(getContractBillEntryCols(billInfo))));
                }





/*1106*/        Map retValue = ContractBillFactory.getRemoteInstance().getOtherInfo(contractIds);
/*1107*/        contentMap = (Map)retValue.get("contentMap");
/*1108*/        attachMap = (Map)retValue.get("attachMap");
/*1109*/        auditMap = (Map)retValue.get("auditMap");

/*1111*/        addTableListener();
            }
            private boolean isHasFilter(String name, FilterInfo filterInfo)
            {








/*1123*/        if(filterInfo == null)
/*1124*/            return false;
/*1125*/        FilterItemCollection filterItems = filterInfo.getFilterItems();
/*1126*/        if(filterItems != null && filterItems.size() > 0)
                {/*1127*/            int i = 0;/*1127*/            for(int size = filterItems.size(); i < size; i++)
/*1128*/                if(name.equals(filterItems.get(i).getPropertyName()))
/*1129*/                    return true;
                }


/*1133*/        return false;
            }
            private boolean isAddSupplyRow(Set idsSet, ContractBillInfo billInfo)
            {










/*1147*/        boolean isAddSupplyRow = false;
/*1148*/        ContractBillEntryCollection cols = getContractBillEntryCols(billInfo);
/*1149*/        if(null != cols && cols.size() > 0)
                {/*1150*/            ContractBillEntryInfo entryInfo = null;
/*1151*/            for(int i = 0; i < cols.size(); i++)
                    {/*1152*/                entryInfo = cols.get(i);
/*1153*/                if(null == entryInfo.getDetail() || !entryInfo.getDetail().toString().equals("\u5BF9\u5E94\u4E3B\u5408\u540C\u7F16\u7801"))
/*1154*/                    continue;/*1154*/                String content = entryInfo.getContent();
/*1155*/                if(!idsSet.contains(content))
/*1156*/                    isAddSupplyRow = true;
                    }
                }


/*1161*/        return isAddSupplyRow;
            }
            private void fillSupplyRow(IRow row, ContractBillInfo info)
            {











/*1176*/        if(null != row.getCell("id") && null != info.getId())
/*1177*/            row.getCell("id").setValue(info.getId().toString());



/*1181*/        if(null != row.getCell("bookedDate") && null != info.getBookedDate())
/*1182*/            row.getCell("bookedDate").setValue(info.getBookedDate());


/*1185*/        if(null != row.getCell("period") && null != info.getPeriod())
/*1186*/            row.getCell("period").setValue(BigDecimal.valueOf(info.getPeriod().getNumber()));



/*1190*/        if(null != row.getCell("project") && null != info.getCurProject() && null != info.getCurProject().getName())
/*1191*/            row.getCell("project").setValue(info.getCurProject().getName());



/*1195*/        if(null != row.getCell("contractType.name") && null != info.getContractType())
/*1196*/            row.getCell("contractType.name").setValue(info.getContractType().getName());



/*1200*/        if(null != row.getCell("number") && null != info.getNumber())
/*1201*/            row.getCell("number").setValue(info.getNumber());



/*1205*/        if(null != row.getCell("contractName") && null != info.getName())
/*1206*/            row.getCell("contractName").setValue(info.getName());



/*1210*/        if(null != row.getCell("conPaid") && null != info.getPrjPriceInConPaid())
/*1211*/            row.getCell("conPaid").setValue(info.getPrjPriceInConPaid());



/*1215*/        if(null != row.getCell("state") && null != info.getState())
/*1216*/            row.getCell("state").setValue(info.getState());



/*1220*/        if(null != row.getCell("hasSettle"))
/*1221*/            row.getCell("hasSettle").setValue(Boolean.valueOf(info.isHasSettled()));

				if(null != row.getCell("settleAmt")&& null != info.getSettleAmt())
	/*1221*/            row.getCell("settleAmt").setValue(info.getSettleAmt());

/*1225*/        if(null != row.getCell("contractPropert") && null != info.getContractPropert())
/*1226*/            row.getCell("contractPropert").setValue(info.getContractPropert());



/*1230*/        if(null != row.getCell("mainContractNumber"))
/*1231*/            if(null != info.getMainContractNumber())
/*1232*/                row.getCell("mainContractNumber").setValue(info.getMainContractNumber());

/*1234*/            else/*1234*/                row.getCell("mainContractNumber").setValue(info.getNumber());





/*1240*/        if(null != row.getCell("currency") && null != info.getCurrency() && null != info.getCurrency().getName())
/*1241*/            row.getCell("currency").setValue(info.getCurrency().getName());



/*1245*/        if(null != row.getCell("originalAmount") && null != info.getOriginalAmount())
/*1246*/            row.getCell("originalAmount").setValue(info.getOriginalAmount());



/*1250*/        if(null != row.getCell("exRate") && null != info.getExRate())
/*1251*/            row.getCell("exRate").setValue(info.getExRate());



/*1255*/        if(null != row.getCell("amount") && null != info.getAmount())
/*1256*/            row.getCell("amount").setValue(info.getAmount());



/*1260*/        if(null != row.getCell("partB.name") && null != info.getPartB() && null != info.getPartB().getName())
/*1261*/            row.getCell("partB.name").setValue(info.getPartB().getName());



/*1265*/        if(null != row.getCell("contractSource") && null != info.getContractSourceId())
/*1266*/            row.getCell("contractSource").setValue(info.getContractSourceId().getName());



/*1270*/        if(null != row.getCell("signDate") && null != info.getSignDate())
/*1271*/            row.getCell("signDate").setValue(info.getSignDate());



/*1275*/        if(null != row.getCell("respDept") && null != info.getRespDept() && null != info.getRespDept().getName())
/*1276*/            row.getCell("respDept").setValue(info.getRespDept().getName());



/*1280*/        if(null != row.getCell("landDeveloper.name") && null != info.getLandDeveloper() && null != info.getLandDeveloper().getName())
/*1281*/            row.getCell("landDeveloper.name").setValue(info.getLandDeveloper().getName());



/*1285*/        if(null != row.getCell("partC.name") && null != info.getPartC() && null != info.getPartC().getName())
/*1286*/            row.getCell("partC.name").setValue(info.getPartC().getName());



/*1290*/        if(null != row.getCell("costProperty") && null != info.getCostProperty())
/*1291*/            row.getCell("costProperty").setValue(info.getCostProperty());



/*1295*/        if(null != row.getCell("isArchived"))
/*1296*/            row.getCell("isArchived").setValue(Boolean.valueOf(info.isIsArchived()));


/*1299*/        if(null != row.getCell("currency.id") && null != info.getCurrency() && null != info.getCurrency().getId())
/*1300*/            row.getCell("currency.id").setValue(info.getCurrency().getId());


/*1303*/        if(null != row.getCell("currency.precision") && null != info.getCurrency())
/*1304*/            row.getCell("currency.precision").setValue(BigDecimal.valueOf(info.getCurrency().getPrecision()));


/*1307*/        if(info.getAuditor() != null)
                {/*1308*/            row.getCell("wfAuditName").setValue(info.getAuditor().getName());
/*1309*/            row.getCell("wfAuditTime").setValue(info.getAuditTime());
                }

/*1312*/        if(info.getCreator() != null)
                {/*1313*/            row.getCell("createTime").setValue(info.getCreateTime());
/*1314*/            row.getCell("creator.name").setValue(info.getCreator().getName());
                }
            }
            public SelectorItemCollection getSelectors()
            {
/*1319*/        SelectorItemCollection sic = super.getSelectors();
/*1320*/        sic.add(new SelectorItemInfo("entrys.detail"));
/*1321*/        sic.add(new SelectorItemInfo("entrys.content"));
/*1322*/        return sic;
            }
            private void addTableListener()
            {
/*1326*/label0:
                {
/* <-MISALIGNED-> */ /*1326*/            boolean isMainOrder = false;
/* <-MISALIGNED-> */ /*1327*/            SorterItemCollection sortItems = mainQuery.getSorter();
/* <-MISALIGNED-> */ /*1328*/            if(sortItems == null || sortItems.size() <= 0)
/* <-MISALIGNED-> */ /*1329*/                break label0;
/* <-MISALIGNED-> */ /*1329*/            Iterator it = sortItems.iterator();
                    SorterItemInfo sortItem;
/* <-MISALIGNED-> */ /*1329*/            do
                    {
/* <-MISALIGNED-> */ /*1329*/                if(!it.hasNext())
/* <-MISALIGNED-> */ /*1330*/                    break label0;
/* <-MISALIGNED-> */ /*1330*/                sortItem = (SorterItemInfo)it.next();
                    } while(!sortItem.getPropertyName().equals("mainContractNumber"));
/* <-MISALIGNED-> */ /*1332*/            isMainOrder = true;
                }
/* <-MISALIGNED-> */ /*1338*/        if(attachMap == null)
/* <-MISALIGNED-> */ /*1339*/            attachMap = new HashMap();
/* <-MISALIGNED-> */ /*1341*/        if(auditMap == null)
/* <-MISALIGNED-> */ /*1342*/            auditMap = new HashMap();
/* <-MISALIGNED-> */ /*1344*/        if(contentMap == null)
/* <-MISALIGNED-> */ /*1345*/            contentMap = new HashMap();
/*1348*/        for(int i = 0; i < tblMain.getRowCount(); i++)
                {/*1349*/            IRow row = tblMain.getRow(i);
/*1350*/            String idkey = row.getCell("id").getValue().toString();
/*1351*/            if(contentMap.containsKey(idkey))
/*1352*/                row.getCell("content").setValue(Boolean.TRUE);


/*1355*/            else/*1355*/                row.getCell("content").setValue(Boolean.FALSE);

/*1357*/            if(attachMap.containsKey(idkey))
/*1358*/                row.getCell("attachment").setValue(Boolean.TRUE);


/*1361*/            else/*1361*/                row.getCell("attachment").setValue(Boolean.FALSE);

/*1363*/            if(auditMap.containsKey(idkey))
                    {/*1364*/                MultiApproveInfo info = (MultiApproveInfo)auditMap.get(idkey);

/*1366*/                row.getCell("wfAuditName").setValue(info.getCreator().getName());

/*1368*/                row.getCell("wfAuditTime").setValue(info.getCreateTime());
                    }
                }
            }
            protected void tblMain_tableSelectChanged(KDTSelectEvent e)
                throws Exception
            {




/*1379*/        boolean isEnabled = true;

/*1381*/        int index = tblMain.getSelectManager().getActiveRowIndex();
/*1382*/        if(index != -1 && "\u8865\u5145\u5408\u540C".equals(tblMain.getCell(index, "contractPropert").getValue().toString().trim()))
                {
/*1384*/            Object isLonelyCal = tblMain.getCell(index, "isLonelyCal").getValue();

/*1386*/            if(isLonelyCal == Boolean.FALSE)
/*1387*/                isEnabled = false;
                }


/*1391*/        actionAddPayRequest.setEnabled(isEnabled);
/*1392*/        actionAddChangeAudit.setEnabled(isEnabled);
/*1393*/        actionAddContractSettlement.setEnabled(isEnabled);

/*1395*/        super.tblMain_tableSelectChanged(e);
            }
            
            public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
        		checkSelected();
        		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
        		IRow row = this.tblMain.getRow(rowIndex);
            	String id = (String) row.getCell("id").getValue();
            	ContractBillInfo info=ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(id));
            	if(info.getSourceFunction()!=null){
            		FDCSQLBuilder builder=new FDCSQLBuilder();
        			builder.appendSql("select fviewurl from t_oa");
        			IRowSet rs=builder.executeQuery();
        			String url=null;
        			while(rs.next()){
        				url=rs.getString("fviewurl");
        			}
        			if(url!=null){
        				String mtLoginNum = OaUtil.encrypt(SysContext.getSysContext().getCurrentUserInfo().getNumber());
        				String s2 = "&MtFdLoinName=";
        				StringBuffer stringBuffer = new StringBuffer();
        	            String oaid = URLEncoder.encode(info.getSourceFunction());
        	            String link = String.valueOf(stringBuffer.append(url).append(oaid).append(s2).append(mtLoginNum));
        				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+link);  
        			}
            	}else{
            		super.actionWorkFlowG_actionPerformed(e);
            	}
        	}
            private static final long serialVersionUID = 1L;
            public static final String resourcePath = "com.kingdee.eas.fdc.contract.client.ContractFullResource";
            private static final String CONTRACT_PROPERT = "\u8865\u5145\u5408\u540C";
            private CustomerQueryPanel filterUI;
            private CommonQueryDialog commonQueryDialog;
            private Map contentMap;
            private Map attachMap;
            private Map auditMap;
            private String currentUserInfoId;
            private String currentOrgUnitId;
            boolean hasCurrency;
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\patch\sp-fdc_contract-client.jar
	Total time: 208 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/