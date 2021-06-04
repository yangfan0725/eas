/*jadclipse*/package com.kingdee.eas.fdc.contract.client;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.IPermission;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.IFullOrgUnit;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeSettleBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeSettleBillInfo;
import com.kingdee.eas.fdc.contract.IContractChangeSettleBill;
import com.kingdee.eas.fdc.contract.app.OaUtil;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.AbstractCoreUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

import java.awt.event.ActionEvent;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.event.TreeSelectionEvent;
import org.apache.log4j.Logger;
public class ContractChangeSettleBillListUI extends AbstractContractChangeSettleBillListUI
{
            public ContractChangeSettleBillListUI()
                throws Exception
            {













































/* 100*/        authorizedOrgs = null;
            }
            protected ICoreBase getBizInterface()
                throws Exception
            {
/* <-MISALIGNED-> */ /*  67*/        return ContractChangeSettleBillFactory.getRemoteInstance();
            }
            protected String getEditUIName()
            {
/* <-MISALIGNED-> */ /*  71*/        return ContractChangeSettleBillEditUI.class.getName();
            }
            protected String getEditUIModal()
            {
/* <-MISALIGNED-> */ /*  75*/        return UIFactoryName.NEWWIN;
            }
            public void actionView_actionPerformed(java.awt.event.ActionEvent e)
                throws Exception
            {
/* <-MISALIGNED-> */ /*  79*/        super.actionView_actionPerformed(e);
            }
            protected void prepareUIContext(UIContext uiContext, java.awt.event.ActionEvent e)
            {
/* <-MISALIGNED-> */ /*  83*/        super.prepareUIContext(uiContext, e);
/* <-MISALIGNED-> */ /*  84*/        uiContext.put("parent", this);
            }
            public void doRefresh()
                throws Exception
            {
/* <-MISALIGNED-> */ /*  87*/        refresh(null);
            }
            public void onLoad()
                throws Exception
            {
/* <-MISALIGNED-> */ /*  91*/        super.onLoad();
/* <-MISALIGNED-> */ /*  92*/        buildProjectTree();
/* <-MISALIGNED-> */ /*  93*/        projectTree.setSelectionRow(0);
/* <-MISALIGNED-> */ /*  95*/        btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
/* <-MISALIGNED-> */ /*  96*/        btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
            }
            public void buildProjectTree()
                throws Exception
            {
/* <-MISALIGNED-> */ /* 103*/        ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
/* <-MISALIGNED-> */ /* 105*/        projectTreeBuilder.build(this, projectTree, actionOnLoad);
/* <-MISALIGNED-> */ /* 107*/        authorizedOrgs = (Set)ActionCache.get("FDCBillListUIHandler.authorizedOrgs");
/* <-MISALIGNED-> */ /* 108*/        if(authorizedOrgs == null)
                {
/* <-MISALIGNED-> */ /* 109*/            authorizedOrgs = new HashSet();
/* <-MISALIGNED-> */ /* 110*/            Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()), OrgType.CostCenter, null, null, null);
/* <-MISALIGNED-> */ /* 114*/            if(orgs != null)
                    {
/* <-MISALIGNED-> */ /* 115*/                Set orgSet = orgs.keySet();
/* <-MISALIGNED-> */ /* 116*/                for(Iterator it = orgSet.iterator(); it.hasNext(); authorizedOrgs.add(it.next()));
                    }
                }
            }
            protected void projectTree_valueChanged(TreeSelectionEvent e)
                throws Exception
            {
/* <-MISALIGNED-> */ /* 127*/        treeSelectChange();
            }
            protected void treeSelectChange()
                throws Exception
            {
/* <-MISALIGNED-> */ /* 131*/        DefaultKingdeeTreeNode projectNode = getProjSelectedTreeNode();
/* <-MISALIGNED-> */ /* 132*/        Object project = null;
/* <-MISALIGNED-> */ /* 133*/        if(projectNode != null)
/* <-MISALIGNED-> */ /* 134*/            project = projectNode.getUserObject();
/* <-MISALIGNED-> */ /* 136*/        mainQuery.setFilter(getTreeSelectFilter(project));
/* <-MISALIGNED-> */ /* 138*/        execQuery();
/* <-MISALIGNED-> */ /* 141*/        tblMain.getSelectManager().select(0, 0);
            }
            public DefaultKingdeeTreeNode getProjSelectedTreeNode()
            {
/* <-MISALIGNED-> */ /* 145*/        return (DefaultKingdeeTreeNode)projectTree.getLastSelectedPathComponent();
            }
            protected FilterInfo getTreeSelectFilter(Object projectNode)
                throws Exception
            {
/* <-MISALIGNED-> */ /* 155*/        FilterInfo filter = new FilterInfo();
/* <-MISALIGNED-> */ /* 156*/        FilterItemCollection filterItems = filter.getFilterItems();
/* <-MISALIGNED-> */ /* 157*/        if(projectNode != null && (projectNode instanceof CoreBaseInfo))
                {
/* <-MISALIGNED-> */ /* 158*/            CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo)projectNode;
/* <-MISALIGNED-> */ /* 159*/            BOSUuid id = null;
/* <-MISALIGNED-> */ /* 161*/            if((projTreeNodeInfo instanceof OrgStructureInfo) || (projTreeNodeInfo instanceof FullOrgUnitInfo))
                    {
/* <-MISALIGNED-> */ /* 162*/                if(projTreeNodeInfo instanceof OrgStructureInfo)
/* <-MISALIGNED-> */ /* 163*/                    id = ((OrgStructureInfo)projTreeNodeInfo).getUnit().getId();
/* <-MISALIGNED-> */ /* 165*/                else
/* <-MISALIGNED-> */ /* 165*/                    id = ((FullOrgUnitInfo)projTreeNodeInfo).getId();
/* <-MISALIGNED-> */ /* 167*/                String orgUnitLongNumber = null;
/* <-MISALIGNED-> */ /* 168*/                if(orgUnit != null && id.toString().equals(orgUnit.getId().toString()))
                        {
/* <-MISALIGNED-> */ /* 169*/                    orgUnitLongNumber = orgUnit.getLongNumber();
                        } else
                        {
/* <-MISALIGNED-> */ /* 171*/                    FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(id));
/* <-MISALIGNED-> */ /* 173*/                    orgUnitLongNumber = orgUnitInfo.getLongNumber();
                        }
/* <-MISALIGNED-> */ /* 176*/                filter.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", (new StringBuilder(String.valueOf(orgUnitLongNumber))).append("%").toString(), CompareType.LIKE));
/* <-MISALIGNED-> */ /* 177*/                filter.getFilterItems().add(new FilterItemInfo("orgUnit.isCostOrgUnit", Boolean.TRUE));
/* <-MISALIGNED-> */ /* 179*/                filter.setMaskString("#0 and #1 ");
                    } else
/* <-MISALIGNED-> */ /* 181*/            if(projTreeNodeInfo instanceof CurProjectInfo)
                    {
/* <-MISALIGNED-> */ /* 182*/                id = projTreeNodeInfo.getId();
/* <-MISALIGNED-> */ /* 183*/                Set idSet = FDCClientUtils.genProjectIdSet(id);
/* <-MISALIGNED-> */ /* 184*/                filterItems.add(new FilterItemInfo("curProject.id", idSet, CompareType.INCLUDE));
                    } else
                    {
/* <-MISALIGNED-> */ /* 187*/                filterItems.add(new FilterItemInfo("id", "nullnull"));
                    }
                }
/* <-MISALIGNED-> */ /* 192*/        return filter;
            }
            protected boolean isIgnoreCUFilter()
            {
/* <-MISALIGNED-> */ /* 196*/        return true;
            }
            public void actionEdit_actionPerformed(java.awt.event.ActionEvent e)
                throws Exception
            {
/* <-MISALIGNED-> */ /* 200*/        checkIsInWorkflow();
/* <-MISALIGNED-> */ /* 201*/        checkBeforeEdit();
/* <-MISALIGNED-> */ /* 202*/        super.actionEdit_actionPerformed(e);
            }
            protected void checkBeforeEdit()
                throws Exception
            {
/* <-MISALIGNED-> */ /* 207*/        checkSelected();
/* <-MISALIGNED-> */ /* 208*/        List idList = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
/* <-MISALIGNED-> */ /* 209*/        if(idList.size() < 1)
/* <-MISALIGNED-> */ /* 209*/            return;
/* <-MISALIGNED-> */ /* 210*/        ContractChangeSettleBillInfo info = ContractChangeSettleBillFactory.getRemoteInstance().getContractChangeSettleBillInfo(new ObjectUuidPK(idList.get(0).toString()));
/* <-MISALIGNED-> */ /* 211*/        String states[] = getBillStateForEditOrRemove();
/* <-MISALIGNED-> */ /* 212*/        boolean pass = false;
/* <-MISALIGNED-> */ /* 213*/        for(int i = 0; i < states.length; i++)
/* <-MISALIGNED-> */ /* 214*/            if(states[i].equals(info.getState().getValue()))
/* <-MISALIGNED-> */ /* 215*/                pass = true;
/* <-MISALIGNED-> */ /* 218*/        if(!pass)
                {
/* <-MISALIGNED-> */ /* 219*/            MsgBox.showWarning(this, ContractClientUtils.getRes("cantEdit"));
/* <-MISALIGNED-> */ /* 220*/            SysUtil.abort();
                }
            }
            protected String[] getBillStateForEditOrRemove()
            {
/* <-MISALIGNED-> */ /* 232*/        return (new String[] {
/* <-MISALIGNED-> */ /* 232*/            "1SAVED", "2SUBMITTED"
                });
            }
            public void actionAudit_actionPerformed(java.awt.event.ActionEvent e)
                throws Exception
            {
/* <-MISALIGNED-> */ /* 236*/        checkSelected();
/* <-MISALIGNED-> */ /* 237*/        SelectorItemCollection sels = new SelectorItemCollection();
/* <-MISALIGNED-> */ /* 238*/        sels.add("state");
/* <-MISALIGNED-> */ /* 239*/        ArrayList id = getSelectedIdValues();
/* <-MISALIGNED-> */ /* 240*/        for(int i = 0; i < id.size(); i++)
                {
/* <-MISALIGNED-> */ /* 241*/            FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
/* <-MISALIGNED-> */ /* 242*/            ContractChangeSettleBillInfo info = ContractChangeSettleBillFactory.getRemoteInstance().getContractChangeSettleBillInfo(new ObjectUuidPK(id.get(i).toString()), sels);
/* <-MISALIGNED-> */ /* 243*/            if(!FDCBillStateEnum.SUBMITTED.equals(info.getState()))
                    {
/* <-MISALIGNED-> */ /* 244*/                FDCMsgBox.showWarning("\u5355\u636E\u4E0D\u662F\u63D0\u4EA4\u72B6\u6001\uFF0C\u4E0D\u80FD\u8FDB\u884C\u5BA1\u6279\u64CD\u4F5C\uFF01");
/* <-MISALIGNED-> */ /* 245*/                return;
                    }
/* <-MISALIGNED-> */ /* 247*/            ((IContractChangeSettleBill)getBizInterface()).audit(BOSUuid.read(id.get(i).toString()));
                }
/* <-MISALIGNED-> */ /* 249*/        FDCClientUtils.showOprtOK(this);
/* <-MISALIGNED-> */ /* 250*/        refresh(null);
            }
            public void actionUnAudit_actionPerformed(java.awt.event.ActionEvent e)
                throws Exception
            {
/* <-MISALIGNED-> */ /* 253*/        checkSelected();
/* <-MISALIGNED-> */ /* 254*/        SelectorItemCollection sels = new SelectorItemCollection();
/* <-MISALIGNED-> */ /* 255*/        sels.add("state");
/* <-MISALIGNED-> */ /* 256*/        ArrayList id = getSelectedIdValues();
/* <-MISALIGNED-> */ /* 257*/        for(int i = 0; i < id.size(); i++)
                {
/* <-MISALIGNED-> */ /* 258*/            FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
/* <-MISALIGNED-> */ /* 259*/            ContractChangeSettleBillInfo info = ContractChangeSettleBillFactory.getRemoteInstance().getContractChangeSettleBillInfo(new ObjectUuidPK(id.get(i).toString()), sels);
/* <-MISALIGNED-> */ /* 260*/            if(!FDCBillStateEnum.AUDITTED.equals(info.getState()))
                    {
/* <-MISALIGNED-> */ /* 261*/                FDCMsgBox.showWarning("\u5355\u636E\u4E0D\u662F\u5BA1\u6279\u72B6\u6001\uFF0C\u4E0D\u80FD\u8FDB\u884C\u53CD\u5BA1\u6279\u64CD\u4F5C\uFF01");
/* <-MISALIGNED-> */ /* 262*/                return;
                    }
/* <-MISALIGNED-> */ /* 264*/            ((IContractChangeSettleBill)getBizInterface()).unAudit(BOSUuid.read(id.get(i).toString()));
                }
/* <-MISALIGNED-> */ /* 266*/        FDCClientUtils.showOprtOK(this);
/* <-MISALIGNED-> */ /* 267*/        refresh(null);
            }
            public void actionRemove_actionPerformed(java.awt.event.ActionEvent e)
                throws Exception
            {
/* <-MISALIGNED-> */ /* 270*/        checkSelected();
/* <-MISALIGNED-> */ /* 271*/        ArrayList id = getSelectedIdValues();
/* <-MISALIGNED-> */ /* 272*/        for(int i = 0; i < id.size(); i++)
/* <-MISALIGNED-> */ /* 273*/            FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
/* <-MISALIGNED-> */ /* 275*/        super.actionRemove_actionPerformed(e);
            }
            
            public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
        		checkSelected();
        		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
        		IRow row = this.tblMain.getRow(rowIndex);
            	String id = (String) row.getCell("id").getValue();
            	ContractChangeSettleBillInfo info=ContractChangeSettleBillFactory.getRemoteInstance().getContractChangeSettleBillInfo(new ObjectUuidPK(id));
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
            public void actionAuditResult_actionPerformed(ActionEvent e)
    		throws Exception {
            	checkSelected();
        		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
        		IRow row = this.tblMain.getRow(rowIndex);
            	String id = (String) row.getCell("id").getValue();
            	ContractChangeSettleBillInfo info=ContractChangeSettleBillFactory.getRemoteInstance().getContractChangeSettleBillInfo(new ObjectUuidPK(id));
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
            		super.actionAuditResult_actionPerformed(e);
            	}
            }
            private static final Logger logger = CoreUIObject.getLogger(ContractChangeSettleBillListUI.class.getName());
            protected Set authorizedOrgs;
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\sp\sp-fdc_contract_client.jar
	Total time: 45 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/