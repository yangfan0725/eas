package com.kingdee.eas.fdc.basedata.client;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.render.IBasicRender;
import com.kingdee.bos.ctrl.kdf.util.render.SimpleTextRender;
import com.kingdee.bos.ctrl.kdf.util.style.Style;
import com.kingdee.bos.ctrl.swing.KDSeparator;
import com.kingdee.bos.ctrl.swing.event.TreePopupMenuEvent;
import com.kingdee.bos.ctrl.swing.event.TreePopupMenuListener;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.uiframe.client.UIModelDialogFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.aimcost.CostEntryFactory;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFacadeFactory;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.ICostAccountFacade;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.framework.util.StringUtility;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

public class CostAccountListUI
  extends AbstractCostAccountListUI
{
  private static final Logger logger = CoreUIObject.getLogger(CostAccountListUI.class);
  private CostAccountTreeRender treeRender;
  private boolean isSetTreeDispalyStyle = true;
  
  public CostAccountListUI()
    throws Exception
  {}
  
  public void onLoad()
    throws Exception
  {
    super.onLoad();
    FDCTableHelper.setColumnMoveable(this.tblMain, true);
    this.chkIncludeChild.setSelected(true);
    this.chkIncludeChild.addChangeListener(new ChangeListener()
    {
      public void stateChanged(ChangeEvent e)
      {
        CostAccountListUI.this.isSetTreeDispalyStyle = CostAccountListUI.access$0(CostAccountListUI.this).isSelected();
        try
        {
          CostAccountListUI.access$2(CostAccountListUI.this).removeRows();
        }
        catch (Exception e1)
        {
          e1.printStackTrace();
        }
      }
    });
    this.treeRender = new CostAccountTreeRender(null);
    this.tblMain.getColumn("number").setRenderer(this.treeRender);
    
    buildProjectTree();
    
    this.btnAssign.setIcon(EASResource.getIcon("imgTbtn_distribute"));
    this.btnDisAssign.setIcon(EASResource.getIcon("imgTbtn_undistribute"));
    this.btnProjectAttachment.setIcon(EASResource.getIcon("imgTbtn_createcredence"));
    this.btnCostAccountImport.setIcon(EASResource.getIcon("imgTbtn_citetree"));
    this.btnTemplateImport.setIcon(EASResource.getIcon("imgTbtn_importcyclostyle"));
    
    this.menuItemAssign.setIcon(EASResource.getIcon("imgTbtn_distribute"));
    this.menuItemDisAssign.setIcon(EASResource.getIcon("imgTbtn_undistribute"));
    this.menuItemProjectAttachment.setIcon(EASResource.getIcon("imgTbtn_createcredence"));
    this.menuItemImport.setIcon(EASResource.getIcon("imgTbtn_citetree"));
    this.menuItemTemplateImport.setIcon(EASResource.getIcon("imgTbtn_importcyclostyle"));
    
    this.actionEnterDB.putValue("SmallIcon", EASResource.getIcon("imgTbtn_staruse"));
    this.actionCancelEnterDB.putValue("SmallIcon", EASResource.getIcon("imgTbtn_forbid"));
    
    this.btnProjectAttachment.setEnabled(true);
    this.menuItemProjectAttachment.setEnabled(true);
    if ("00000000-0000-0000-0000-000000000000CCE7AED4".equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString()))
    {
      this.menuItemTemplateImport.setVisible(true);
      this.menuItemTemplateImport.setEnabled(true);
      
      this.btnTemplateImport.setVisible(true);
      this.btnTemplateImport.setEnabled(true);
      
      this.actionEnterDB.setEnabled(true);
      this.actionCancelEnterDB.setEnabled(true);
    }
    else
    {
      this.menuItemTemplateImport.setVisible(false);
      this.btnTemplateImport.setVisible(false);
      this.actionEnterDB.setVisible(false);
      this.actionEnterDB.setEnabled(false);
      this.actionCancelEnterDB.setVisible(false);
      this.actionCancelEnterDB.setEnabled(false);
      this.tblMain.getColumn("isEnterDB").getStyleAttributes().setHided(true);
    }
    if (this.treeMain.getRowCount() >= 1) {
      this.treeMain.setSelectionRow(0);
    }
    this.tblMain.getColumn("number").setWidth(160);
    this.tblMain.getColumn("name").setWidth(140);
    this.tblMain.getColumn("description").setWidth(100);
    
    this.tblMain.getColumn("isEnabled").setWidth(50);
    this.tblMain.getColumn("assigned").setWidth(50);
    this.tblMain.getColumn("isCostAccount").setWidth(50);
    


    this.actionQuery.setEnabled(false);
    this.actionQuery.setVisible(false);
    if ("00000000-0000-0000-0000-000000000000CCE7AED4".equals(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()))
    {
      this.actionImportData.setVisible(true);
      this.actionImportData.setEnabled(true);
    }
    else
    {
      this.actionImportData.setVisible(false);
      this.actionImportData.setEnabled(false);
    }
    this.tblMain.getSelectManager().setSelectMode(2);
    if (SysContext.getSysContext().getCurrentOrgUnit().isIsCompanyOrgUnit()) {
      this.actionAssignToOrg.setEnabled(true);
    } else {
      this.actionAssignToOrg.setEnabled(false);
    }
    this.actionDisAssign.setEnabled(false);
    this.actionDisAssign.setVisible(false);
    
    this.tHelper = new TablePreferencesHelper(this);
  }
  
  protected void initTree()
    throws Exception
  {}
  
  protected void buildProjectTree()
    throws Exception
  {
    ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false);
    projectTreeBuilder.setDevPrjFilter(false);
    this.treeMain.setShowsRootHandles(true);
    projectTreeBuilder.build(this, this.treeMain, this.actionOnLoad);
  }
  
  protected void tblMain_tableClicked(KDTMouseEvent e)
    throws Exception
  {
    if (e.getClickCount() == 1)
    {
      int columnIndex = e.getColIndex();
      if (columnIndex == 0)
      {
        int rowIndex = e.getRowIndex();
        IRow row = this.tblMain.getRow(rowIndex);
        if (row == null) {
          return;
        }
        Object obj = row.getCell("number").getValue();
        if ((obj instanceof NumberExpandInfo))
        {
          NumberExpandInfo numberExpandInfo = (NumberExpandInfo)row.getCell("number").getValue();
          if (this.treeRender.inRect(numberExpandInfo, e.getX(), e.getY())) {
            setTreeDisplayStyle(row);
          }
        }
        return;
      }
    }
    super.tblMain_tableClicked(e);
  }
  
  public DefaultKingdeeTreeNode getSelectedTreeNode1()
  {
    return (DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
  }
  
  public KDTreeNode getSelectedTreeNode()
  {
    return null;
  }
  
  protected void tblMain_tableSelectChanged(KDTSelectEvent e)
    throws Exception
  {
    boolean isSelectCurOrg = false;
    DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
    CostCenterOrgUnitInfo currentCostUnit = SysContext.getSysContext().getCurrentCostUnit();
    CurProjectInfo curProject = null;
    if (!currentCostUnit.isIsBizUnit())
    {
      Object userObject2 = node.getUserObject();
      if ((userObject2 instanceof OrgStructureInfo))
      {
        String id = ((OrgStructureInfo)userObject2).getUnit().getId().toString();
        if (id.equals(currentCostUnit.getId().toString())) {
          isSelectCurOrg = true;
        }
      }
      else if ((userObject2 instanceof CurProjectInfo))
      {
        curProject = (CurProjectInfo)userObject2;
      }
    }
    else
    {
      isSelectCurOrg = true;
    }
    if (this.tblMain.getSelectManager().getActiveRowIndex() != -1)
    {
      if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isEnabled") != null)
      {
        boolean status = ((Boolean)this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isEnabled").getValue()).booleanValue();
        
        changeWBTEnabeld(status);
      }
      if (((SysContext.getSysContext().getCurrentFIUnit() != null) && (!SysContext.getSysContext().getCurrentFIUnit().isIsOnlyUnion())) || (
        (SysContext.getSysContext().getCurrentCostUnit() != null) && (SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit())))
      {
        if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isSource") != null)
        {
          boolean statusSource = ((Boolean)this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isSource").getValue()).booleanValue();
          if (statusSource)
          {
            if ((this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("assigned") != null) && 
              (isSelectCurOrg))
            {
              boolean statusAssign = ((Boolean)this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("assigned").getValue()).booleanValue();
              if (statusAssign)
              {
                this.btnRemove.setEnabled(false);
                this.btnEdit.setEnabled(true);
                this.btnAddNew.setEnabled(true);
                
                this.menuItemRemove.setEnabled(false);
                this.menuItemEdit.setEnabled(true);
                this.menuItemAddNew.setEnabled(true);
              }
              else
              {
                this.btnAddNew.setEnabled(true);
                this.btnEdit.setEnabled(true);
                this.btnRemove.setEnabled(true);
                
                this.menuItemAddNew.setEnabled(true);
                this.menuItemEdit.setEnabled(true);
                this.menuItemRemove.setEnabled(true);
              }
            }
          }
          else
          {
            this.btnRemove.setEnabled(false);
            this.btnEdit.setEnabled(false);
            this.btnCancel.setEnabled(false);
            
            this.menuItemRemove.setEnabled(false);
            this.menuItemEdit.setEnabled(false);
            this.menuItemCancel.setEnabled(false);
            if ((((Boolean)this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isLeaf").getValue()).booleanValue()) && (
              (isSelectCurOrg) || ((curProject != null) && (curProject.isIsLeaf()) && (!curProject.isIsDevPrj()))))
            {
              this.btnAddNew.setEnabled(true);
              this.menuItemAddNew.setEnabled(true);
            }
            else if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex() + 1) != null)
            {
              if ((((Boolean)this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex() + 1).getCell("isSource").getValue()).booleanValue()) && 
                (isSelectCurOrg))
              {
                this.btnAddNew.setEnabled(true);
                this.menuItemAddNew.setEnabled(true);
              }
              else
              {
                this.btnAddNew.setEnabled(false);
                this.menuItemAddNew.setEnabled(false);
              }
            }
            disabledWBT();
          }
        }
      }
      else
      {
        if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("fullOrgUnit.id").getValue() != null)
        {
          String ouid = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("fullOrgUnit.id").getValue().toString();
          if (!"00000000-0000-0000-0000-000000000000CCE7AED4".equals(ouid))
          {
            this.btnAddNew.setEnabled(false);
            this.btnEdit.setEnabled(false);
            this.btnRemove.setEnabled(false);
            this.btnCostAccountImport.setEnabled(false);
            this.btnAssign.setEnabled(false);
            this.btnDisAssign.setEnabled(false);
            
            this.menuItemAddNew.setEnabled(false);
            this.menuItemEdit.setEnabled(false);
            this.menuItemRemove.setEnabled(false);
            this.menuItemImport.setEnabled(false);
            this.menuItemAssign.setEnabled(false);
            this.menuItemDisAssign.setEnabled(false);
          }
          else
          {
            this.btnAddNew.setEnabled(true);
            this.btnEdit.setEnabled(true);
            this.btnCostAccountImport.setEnabled(true);
            this.btnAssign.setEnabled(true);
            this.btnDisAssign.setEnabled(true);
            
            this.menuItemImport.setEnabled(true);
            this.menuItemAddNew.setEnabled(true);
            this.menuItemEdit.setEnabled(true);
            this.menuItemRemove.setEnabled(true);
            this.menuItemAssign.setEnabled(true);
            this.menuItemDisAssign.setEnabled(true);
            if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("assigned") != null)
            {
              boolean status = ((Boolean)this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("assigned").getValue()).booleanValue();
              if (status)
              {
                this.btnRemove.setEnabled(false);
                this.menuItemRemove.setEnabled(false);
              }
              else
              {
                this.btnRemove.setEnabled(true);
                this.menuItemRemove.setEnabled(true);
              }
            }
          }
        }
        else
        {
          this.btnAddNew.setEnabled(false);
          this.btnEdit.setEnabled(false);
          this.btnRemove.setEnabled(false);
          this.btnCostAccountImport.setEnabled(false);
          this.btnAssign.setEnabled(false);
          this.btnDisAssign.setEnabled(false);
          
          this.menuItemAddNew.setEnabled(false);
          this.menuItemEdit.setEnabled(false);
          this.menuItemRemove.setEnabled(false);
          this.menuItemImport.setEnabled(false);
          this.menuItemAssign.setEnabled(false);
          this.menuItemDisAssign.setEnabled(false);
        }
        if (!isSelectCurOrg) {
          disabledWBT();
        }
      }
    }
    else
    {
      disabledWBT();
    }
  }
  
  private void getSelectNode(DefaultKingdeeTreeNode selectNode)
    throws Exception
  {
    if (selectNode.getChildCount() == 0) {
      this.treeMain.setSelectionNode(selectNode);
    } else {
      for (int j = 0; j < selectNode.getChildCount(); j++)
      {
        DefaultKingdeeTreeNode tempNode = (DefaultKingdeeTreeNode)selectNode.getChildAt(j);
        if ((tempNode.getUserObject() instanceof OrgStructureInfo))
        {
          getSelectNode(tempNode);
        }
        else
        {
          TreeBaseInfo tempTree = (TreeBaseInfo)tempNode.getUserObject();
          TreeBaseInfo node = (TreeBaseInfo)selectNode.getUserObject();
          if (StringUtility.isMatch(tempTree.getId().toString(), node.getId().toString(), true))
          {
            this.treeMain.setSelectionNode(tempNode);
            break;
          }
          getSelectNode(tempNode);
        }
      }
    }
  }
  
  private void changeWBTEnabeld(boolean isEnabled)
  {
    this.actionCancel.setEnabled(isEnabled);
    this.actionCancelCancel.setEnabled(!isEnabled);
  }
  
  private void disabledWBT()
  {
    this.actionCancel.setEnabled(false);
    this.actionCancelCancel.setEnabled(false);
  }
  
  protected FilterInfo getDefaultFilterForTree()
  {
    return super.getDefaultFilterForTree();
  }
  
  protected FilterInfo getDefaultFilterForQuery()
  {
    DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
    if (node == null) {
      return null;
    }
    if (OrgViewUtils.isTreeNodeDisable(node)) {
      return null;
    }
    if ((node.getUserObject() instanceof CurProjectInfo))
    {
      CurProjectInfo projectInfo = (CurProjectInfo)node.getUserObject();
      FilterInfo filterInfo = new FilterInfo();
      filterInfo.getFilterItems().add(new FilterItemInfo("curProject.id", projectInfo.getId().toString(), CompareType.EQUALS));
      this.mainQuery.setFilter(filterInfo);
      this.tblMain.removeRows();
    }
    else if ((node.getUserObject() instanceof OrgStructureInfo))
    {
      OrgStructureInfo oui = (OrgStructureInfo)node.getUserObject();
      if ((oui == null) || (oui.getUnit() == null)) {
        return null;
      }
      FullOrgUnitInfo info = oui.getUnit();
      FilterInfo filterInfo = new FilterInfo();
      filterInfo.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", info.getId().toString(), CompareType.EQUALS));
      
      this.mainQuery.setFilter(filterInfo);
      
      this.tblMain.removeRows();
    }
    return this.mainQuery.getFilter();
  }
  
  protected void treeMain_valueChanged(TreeSelectionEvent e)
    throws Exception
  {
    DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
    if (node == null) {
      return;
    }
    if ((node.getUserObject() instanceof CurProjectInfo))
    {
      CurProjectInfo projectInfo = (CurProjectInfo)node.getUserObject();
      FilterInfo filterInfo = new FilterInfo();
      filterInfo.getFilterItems().add(new FilterItemInfo("curProject.id", projectInfo.getId().toString(), CompareType.EQUALS));
      this.mainQuery.setFilter(filterInfo);
      this.tblMain.removeRows();
      if (((SysContext.getSysContext().getCurrentFIUnit() != null) && (!SysContext.getSysContext().getCurrentFIUnit().isIsOnlyUnion())) || (
        (SysContext.getSysContext().getCurrentCostUnit() != null) && (SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit())))
      {
        if (this.tblMain.getSelectManager().getActiveRowIndex() == -1)
        {
          this.btnAddNew.setEnabled(false);
          this.btnEdit.setEnabled(false);
          this.btnRemove.setEnabled(false);
          


          this.menuItemAddNew.setEnabled(false);
          this.menuItemEdit.setEnabled(false);
          this.menuItemRemove.setEnabled(false);
        }
        if ((node.isLeaf()) && (!projectInfo.isIsDevPrj()))
        {
          this.btnAddNew.setEnabled(true);
          this.btnEdit.setEnabled(true);
          this.btnRemove.setEnabled(true);
          this.menuItemAddNew.setEnabled(true);
          this.menuItemEdit.setEnabled(true);
          this.menuItemRemove.setEnabled(true);
        }
      }
      else
      {
        this.btnAddNew.setEnabled(false);
        this.btnEdit.setEnabled(false);
        this.btnRemove.setEnabled(false);
        this.btnCostAccountImport.setEnabled(false);
        this.btnAssign.setEnabled(false);
        this.btnDisAssign.setEnabled(false);
        this.menuItemAddNew.setEnabled(false);
        this.menuItemEdit.setEnabled(false);
        this.menuItemRemove.setEnabled(false);
        
        this.menuItemImport.setEnabled(false);
      }
      if (node.isLeaf())
      {
        this.actionAssign.setEnabled(false);
        this.actionDisAssign.setEnabled(false);
      }
      else
      {
        this.actionAssign.setEnabled(true);
        this.actionDisAssign.setEnabled(true);
      }
    }
    else if ((node.getUserObject() instanceof OrgStructureInfo))
    {
      OrgStructureInfo oui = (OrgStructureInfo)node.getUserObject();
      if ((oui == null) || (oui.getUnit() == null)) {
        return;
      }
      FullOrgUnitInfo info = oui.getUnit();
      FilterInfo filterInfo = new FilterInfo();
      filterInfo.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", info.getId().toString(), CompareType.EQUALS));
      
      this.mainQuery.setFilter(filterInfo);
      
      this.tblMain.removeRows();
      if (((SysContext.getSysContext().getCurrentFIUnit() != null) && (!SysContext.getSysContext().getCurrentFIUnit().isIsOnlyUnion())) || (
        (SysContext.getSysContext().getCurrentCostUnit() != null) && (SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit())))
      {
        if (this.tblMain.getSelectManager().getActiveRowIndex() == -1)
        {
          this.btnAddNew.setEnabled(false);
          this.btnEdit.setEnabled(false);
          this.btnRemove.setEnabled(false);
          


          this.menuItemAddNew.setEnabled(false);
          this.menuItemEdit.setEnabled(false);
          this.menuItemRemove.setEnabled(false);
        }
      }
      else if (!"00000000-0000-0000-0000-000000000000CCE7AED4".equals(info.getId().toString()))
      {
        this.btnAddNew.setEnabled(false);
        this.btnEdit.setEnabled(false);
        this.btnRemove.setEnabled(false);
        this.btnCostAccountImport.setEnabled(false);
        this.btnAssign.setEnabled(false);
        this.btnDisAssign.setEnabled(false);
        this.menuItemAddNew.setEnabled(false);
        this.menuItemEdit.setEnabled(false);
        this.menuItemRemove.setEnabled(false);
        
        this.menuItemImport.setEnabled(false);
      }
      else
      {
        this.btnAddNew.setEnabled(true);
        this.btnEdit.setEnabled(true);
        this.btnRemove.setEnabled(true);
        this.btnCostAccountImport.setEnabled(true);
        this.btnAssign.setEnabled(true);
        this.btnDisAssign.setEnabled(true);
        this.menuItemAddNew.setEnabled(true);
        this.menuItemEdit.setEnabled(true);
        this.menuItemRemove.setEnabled(true);
        
        this.menuItemImport.setEnabled(true);
      }
    }
    if (node.isLeaf())
    {
      this.btnAssign.setEnabled(false);
      this.btnDisAssign.setEnabled(false);
    }
    else
    {
      this.btnAssign.setEnabled(true);
      this.btnDisAssign.setEnabled(true);
    }
    if (("00000000-0000-0000-0000-000000000000CCE7AED4".equals(SysContext.getSysContext().getCurrentOrgUnit().getId().toString())) && 
      ((node.getUserObject() instanceof OrgStructureInfo)) && 
      (node.getLevel() == 0))
    {
      this.actionEnterDB.setEnabled(true);
      this.actionEnterDB.setVisible(true);
      this.actionCancelEnterDB.setEnabled(true);
      this.actionCancelEnterDB.setVisible(true);
      this.tblMain.getColumn("isEnterDB").getStyleAttributes().setHided(false);
    }
    else
    {
      this.actionEnterDB.setEnabled(false);
      this.actionEnterDB.setVisible(false);
      this.actionCancelEnterDB.setEnabled(false);
      this.actionCancelEnterDB.setVisible(false);
      this.tblMain.getColumn("isEnterDB").getStyleAttributes().setHided(true);
    }
  }
  
  protected void showResultMessage(String message)
  {
    setMessageText(message);
    showMessage();
  }
  
  public void actionAddNew_actionPerformed(ActionEvent e)
    throws Exception
  {
    int rowNo = this.tblMain.getSelectManager().getActiveRowIndex();
    if (rowNo != -1)
    {
      IRow row = this.tblMain.getRow(rowNo);
      getUIContext().put("upId", row.getCell("id").getValue().toString());
    }
    else
    {
      DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
      if (node == null)
      {
        MsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "CostAccount_Add_SelectNode"));
        return;
      }
      if (((SysContext.getSysContext().getCurrentFIUnit() != null) && (!SysContext.getSysContext().getCurrentFIUnit().isIsOnlyUnion())) || (
        (SysContext.getSysContext().getCurrentCostUnit() != null) && (SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit())))
      {
        MsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "CostAccount_Add_OnlyDetail"));
        return;
      }
      if (node.getUserObject() != null) {
        if ((node.getUserObject() instanceof CurProjectInfo))
        {
          CurProjectInfo info = (CurProjectInfo)node.getUserObject();
          getUIContext().put("source", info);
        }
        else if ((node.getUserObject() instanceof OrgStructureInfo))
        {
          OrgStructureInfo oui = (OrgStructureInfo)node.getUserObject();
          if ((oui == null) || (oui.getUnit() == null)) {
            return;
          }
          FullOrgUnitInfo info = oui.getUnit();
          getUIContext().put("source", info);
        }
      }
      getUIContext().put("upId", null);
    }
    if (getUIContext().get("upId") != null)
    {
      String id = getUIContext().get("upId").toString();
      if (((ContractCostSplitEntryFactory.getRemoteInstance().exists("select id where costAccount.id = '" + id + "'")) || 
        (SettlementCostSplitEntryFactory.getRemoteInstance().exists("select id where costAccount.id = '" + id + "'")) || 
        (ConChangeSplitEntryFactory.getRemoteInstance().exists("select id where costAccount.id = '" + id + "'")) || 
        (PaymentSplitEntryFactory.getRemoteInstance().exists("select id where costAccount.id = '" + id + "'"))) && 
        (!MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "CostAccountSplit"))))) {
        return;
      }
    }
    if (getUIContext().get("upId") != null)
    {
      String id = getUIContext().get("upId").toString();
      if (CostEntryFactory.getRemoteInstance().exists("select id where costAccount.id = '" + id + "'"))
      {
        MsgBox.showWarning(this, "科目被目标成本引用，不能新增下级！");
        SysUtil.abort();
      }
    }
    super.actionAddNew_actionPerformed(e);
  }
  
  protected void prepareUIContext(UIContext uiContext, ActionEvent e) {}
  
  protected void prepareUIContext1(UIContext uiContext, ActionEvent e) {}
  
  public void actionEdit_actionPerformed(ActionEvent e)
    throws Exception
  {
    checkSelected();
    int selectRow = getMainTable().getSelectManager().getActiveRowIndex();
    super.actionEdit_actionPerformed(e);
    getMainTable().getSelectManager().select(selectRow, 0);
    getMainTable().scrollToVisible(selectRow, 0);
  }
  
  public void actionView_actionPerformed(ActionEvent e)
    throws Exception
  {
    checkSelected();
    super.actionView_actionPerformed(e);
  }
  
  protected void refresh(ActionEvent e)
    throws Exception
  {
    DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
    CacheServiceFactory.getInstance().discardQuery(this.mainQueryPK);
    execQuery();
  }
  
  public void actionQuery_actionPerformed(ActionEvent e)
    throws Exception
  {
    this.mainQuery = new EntityViewInfo();
    this.mainQuery.setFilter(getDefaultFilterForQuery());
    super.actionQuery_actionPerformed(e);
  }
  
  protected String getQueryFieldName()
  {
    return null;
  }
  
  protected String getGroupEditUIName()
  {
    return null;
  }
  
  protected ITreeBase getTreeInterface()
    throws Exception
  {
    return null;
  }
  
  protected String getEditUIName()
  {
    return CostAccountEditUI.class.getName();
  }
  
  protected ICoreBase getBizInterface()
    throws Exception
  {
    return CostAccountFactory.getRemoteInstance();
  }
  
  private class CostAccountTreeRender
    implements IBasicRender
  {
    private int TABSIZE = 8;
    private int ICONSIZE = 10;
    private int margin = 2;
    protected SimpleTextRender simpleRender = new SimpleTextRender();
    protected HashMap costAccountIdToPos = new HashMap();
    
    private CostAccountTreeRender() {}
    
    protected void drawExpanded(Graphics g, int x, int y)
    {
      g.drawRect(x, y, this.ICONSIZE, this.ICONSIZE);
      int lineSize = this.ICONSIZE - 2 * this.margin;
      g.drawLine(x + this.margin, y + this.ICONSIZE / 2, x + this.margin + lineSize, y + this.ICONSIZE / 2);
    }
    
    protected void drawCollapsed(Graphics g, int x, int y)
    {
      g.drawRect(x, y, this.ICONSIZE, this.ICONSIZE);
      int lineSize = this.ICONSIZE - 2 * this.margin;
      g.drawLine(x + this.margin, y + this.ICONSIZE / 2, x + this.margin + lineSize, y + this.ICONSIZE / 2);
      g.drawLine(x + this.ICONSIZE / 2, y + this.margin, x + this.ICONSIZE / 2, y + this.margin + lineSize);
    }
    
    protected void drawLeaf(Graphics g, int x, int y)
    {
      int lineSize = this.ICONSIZE - 2 * this.margin;
    }
    
    public boolean inRect(CostAccountListUI.NumberExpandInfo numberExpandInfo, int x, int y)
    {
      String costAccountId = numberExpandInfo.getcostAccountId();
      Rectangle rec = (Rectangle)this.costAccountIdToPos.get(costAccountId);
      if (rec != null) {
        return (rec.getX() < x) && (rec.getX() + rec.getWidth() > x);
      }
      return false;
    }
    
    public void draw(Graphics graphics, Shape clip, Object obj, Style style)
    {
      if ((obj instanceof CostAccountListUI.NumberExpandInfo))
      {
        CostAccountListUI.NumberExpandInfo numberExpandInfo = (CostAccountListUI.NumberExpandInfo)obj;
        int ident = numberExpandInfo.getLevel() * this.TABSIZE;
        Rectangle rect = clip.getBounds();
        int x = rect.x + ident;
        int y = rect.y + (rect.height - this.ICONSIZE) / 2;
        Rectangle iconRect = new Rectangle(x, y, this.ICONSIZE, this.ICONSIZE);
        this.costAccountIdToPos.put(numberExpandInfo.getcostAccountId(), iconRect);
        
        this.simpleRender.draw(graphics, new Rectangle(x + this.ICONSIZE + this.TABSIZE, rect.y, rect.width - x - this.ICONSIZE - this.TABSIZE, rect.height), numberExpandInfo.getNumber(), style);
        paintIcon(graphics, numberExpandInfo, iconRect);
      }
      else if (obj != null)
      {
        this.simpleRender.draw(graphics, clip, obj.toString(), style);
      }
    }
    
    private void paintIcon(Graphics graphics, CostAccountListUI.NumberExpandInfo numberExpandInfo, Rectangle iconRect)
    {
      if (numberExpandInfo.isLeaf()) {
        drawLeaf(graphics, iconRect.x, iconRect.y);
      } else if (numberExpandInfo.isExpandStatus()) {
        drawExpanded(graphics, iconRect.x, iconRect.y);
      } else if (!numberExpandInfo.isExpandStatus()) {
        drawCollapsed(graphics, iconRect.x, iconRect.y);
      }
    }
  }
  
  private class NumberExpandInfo
  {
    private String costAccountId;
    private String number;
    private boolean isExpandStatus;
    private int level;
    private boolean isLeaf;
    
    private NumberExpandInfo() {}
    
    public boolean isExpandStatus()
    {
      return this.isExpandStatus;
    }
    
    public void setExpandStatus(boolean isExpandStatus)
    {
      this.isExpandStatus = isExpandStatus;
    }
    
    public String getNumber()
    {
      return this.number;
    }
    
    public void setNumber(String number)
    {
      this.number = number;
    }
    
    public String toString()
    {
      return this.number;
    }
    
    public int getLevel()
    {
      return this.level;
    }
    
    public void setLevel(int level)
    {
      this.level = level;
    }
    
    public boolean isLeaf()
    {
      return this.isLeaf;
    }
    
    public void setLeaf(boolean isLeaf)
    {
      this.isLeaf = isLeaf;
    }
    
    public String getcostAccountId()
    {
      return this.costAccountId;
    }
    
    public void setcostAccountId(String costAccountId)
    {
      this.costAccountId = costAccountId;
    }
  }
  
  protected IObjectPK getSelectedTreeKeyValue()
  {
    return null;
  }
  
  protected boolean isOrderForClickTableHead()
  {
    return false;
  }
  
  protected void initPopmenu()
  {
    JPopupMenu menu = this.treeMain.getPopupMenu();
    Object[] ls = (Object[])this.treeMain.getListeners(TreePopupMenuListener.class);
    if ((ls == null) || (ls.length == 0))
    {
      this.treeMain.addTreePopupMenu(new TreePopupMenuListener()
      {
        public boolean popMenu(TreePopupMenuEvent event)
        {
          return true;
        }
      });
      menu.add(new KDSeparator());
      JMenuItem itemEdit = new JMenuItem(this.actionGroupEdit);
      itemEdit.setIcon(EASResource.getIcon("imgTbtn_edit"));
      menu.add(itemEdit);
    }
  }
  
  private void setTreeDisplayStyle(IRow row)
  {
    boolean isCostAccountLeaf = ((Boolean)row.getCell("isLeaf").getValue()).booleanValue();
    if (isCostAccountLeaf) {
      return;
    }
    expandCostAccount(row);
  }
  
  private void expandCostAccount(IRow row)
  {
    NumberExpandInfo costAccountNumberExpandInfo = (NumberExpandInfo)row.getCell("number").getValue();
    String costAccountNumber = costAccountNumberExpandInfo.getNumber();
    boolean isExpandStatus = costAccountNumberExpandInfo.isExpandStatus();
    String costAccountId = (String)row.getCell("id").getValue();
    if (isExpandStatus)
    {
      costAccountNumberExpandInfo.setExpandStatus(false);
      
      int i = row.getRowIndex() + 1;
      this.tblMain.setRefresh(false);
      for (int count = this.tblMain.getRowCount(); i < count; i++)
      {
        IRow child = this.tblMain.getRow(i);
        if ((child.getCell("number").getValue() instanceof NumberExpandInfo))
        {
          NumberExpandInfo childNumberExpandInfo = (NumberExpandInfo)child.getCell("number").getValue();
          String childNumber = childNumberExpandInfo.getNumber();
          if (!childNumber.startsWith(costAccountNumber)) {
            break;
          }
          child.getStyleAttributes().setHided(true);
        }
      }
      this.tblMain.setRefresh(true);
      this.tblMain.reLayoutAndPaint();
    }
    else
    {
      costAccountNumberExpandInfo.setExpandStatus(true);
      int i = row.getRowIndex() + 1;
      boolean hasChildrenDataGotten = false;
      if (i < this.tblMain.getRowCount())
      {
        IRow next = this.tblMain.getRow(i);
        if ((next.getCell("number").getValue() instanceof NumberExpandInfo))
        {
          NumberExpandInfo nextNumberExpandInfo = (NumberExpandInfo)next.getCell("number").getValue();
          if (nextNumberExpandInfo.getNumber().startsWith(costAccountNumber)) {
            hasChildrenDataGotten = true;
          }
        }
      }
      else
      {
        hasChildrenDataGotten = true;
      }
      if (!hasChildrenDataGotten)
      {
        setCursorOfWair();
        EntityViewInfo childQuery = new EntityViewInfo();
        FilterInfo childFilter = new FilterInfo();
        childQuery.setFilter(childFilter);
        childFilter.getFilterItems().add(new FilterItemInfo("parent.id", costAccountId));
        if (row.getCell("curProject.id").getValue() != null)
        {
          String curProjectId = row.getCell("curProject.id").getValue().toString();
          childFilter.getFilterItems().add(new FilterItemInfo("curProject.id", curProjectId));
        }
        else
        {
          String fullOrgUnitId = row.getCell("fullOrgUnit.id").getValue().toString();
          childFilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", fullOrgUnitId));
        }
        IRowSet rowset = null;
        try
        {
          rowset = getQueryExecutor(this.mainQueryPK, childQuery).executeQuery();
          if ((rowset != null) && (rowset.size() > 0))
          {
            int start = row.getRowIndex() + 1;
            int col;
            int colcount;
            for (; rowset.next(); col < colcount)
            {
              IRow child = this.tblMain.addRow(start);
              start++;
              col = 0;colcount = this.tblMain.getColumnCount(); continue;
              IColumn column = this.tblMain.getColumn(col);
              String field = column.getFieldName();
              if ((field != null) && (field.length() >= 1))
              {
                Object value = rowset.getObject(field);
                if (field.equals("number"))
                {
                  NumberExpandInfo childNumberExpandInfo = new NumberExpandInfo(null);
                  childNumberExpandInfo.setcostAccountId(rowset.getString("id"));
                  childNumberExpandInfo.setLevel(rowset.getInt("level"));
                  childNumberExpandInfo.setLeaf(rowset.getBoolean("isLeaf"));
                  childNumberExpandInfo.setNumber(rowset.getString("number"));
                  childNumberExpandInfo.setExpandStatus(false);
                  value = childNumberExpandInfo;
                }
                child.getCell(col).setValue(value);
              }
              col++;
            }
            this.tblMain.setRowCount(this.tblMain.getRowCount() + rowset.size());
          }
        }
        catch (BOSException e)
        {
          handleException(e);
        }
        catch (SQLException e)
        {
          handleException(e);
        }
        finally
        {
          setCursorOfDefault();
        }
      }
      for (int count = this.tblMain.getRowCount(); i < count; i++)
      {
        IRow child = this.tblMain.getRow(i);
        if ((child.getCell("number").getValue() instanceof NumberExpandInfo))
        {
          NumberExpandInfo childNumberExpandInfo = (NumberExpandInfo)child.getCell("number").getValue();
          String childNumber = childNumberExpandInfo.getNumber();
          if (!childNumber.startsWith(costAccountNumber)) {
            break;
          }
          String parentId = (String)child.getCell("parent.id").getValue();
          if ((parentId != null) && (parentId.equals(costAccountId))) {
            expandChildCostAccount(child);
          }
        }
      }
    }
  }
  
  private void expandChildCostAccount(IRow row)
  {
    NumberExpandInfo costAccountNumberExpandInfo = (NumberExpandInfo)row.getCell("number").getValue();
    String costAccountNumber = costAccountNumberExpandInfo.getNumber();
    boolean isExpandStatus = costAccountNumberExpandInfo.isExpandStatus();
    String costAccountId = (String)row.getCell("id").getValue();
    row.getStyleAttributes().setHided(false);
    if (isExpandStatus)
    {
      int i = row.getRowIndex() + 1;
      for (int count = this.tblMain.getRowCount(); i < count; i++)
      {
        IRow child = this.tblMain.getRow(i);
        NumberExpandInfo childNumberExpandInfo = (NumberExpandInfo)child.getCell("number").getValue();
        String childNumber = childNumberExpandInfo.getNumber();
        if (!childNumber.startsWith(costAccountNumber)) {
          break;
        }
        String parentId = (String)child.getCell("parent.id").getValue();
        if ((parentId != null) && (parentId.equals(costAccountId))) {
          expandChildCostAccount(child);
        }
      }
    }
  }
  
  protected void initListener()
  {
    super.initListener();
    this.tblMain.addKDTDataFillListener(new KDTDataFillListener()
    {
      public void afterDataFill(KDTDataRequestEvent e)
      {
        int start = e.getFirstRow();
        int end = e.getLastRow();
        CostAccountListUI.this.setTreeDisplayStyle(start, end + 1);
      }
    });
  }
  
  private void setTreeDisplayStyle(int start, int end)
  {
    int i = start;
    for (int count = end; i < count; i++)
    {
      IRow row = this.tblMain.getRow(i);
      String id = (String)row.getCell("id").getValue();
      int level = ((Integer)row.getCell("level").getValue()).intValue();
      Object numberValue = row.getCell("number").getValue();
      if (!(numberValue instanceof String)) {
        return;
      }
      String costAccountNumber = (String)numberValue;
      boolean isCostAccountLeaf = ((Boolean)row.getCell("isLeaf").getValue()).booleanValue();
      NumberExpandInfo costAccountNumberExpandInfo = new NumberExpandInfo(null);
      costAccountNumberExpandInfo.setcostAccountId(id);
      costAccountNumberExpandInfo.setLevel(level);
      costAccountNumberExpandInfo.setLeaf(isCostAccountLeaf);
      costAccountNumberExpandInfo.setNumber(costAccountNumber);
      costAccountNumberExpandInfo.setExpandStatus(true);
      row.getCell("number").setValue(costAccountNumberExpandInfo);
      if (!this.isSetTreeDispalyStyle)
      {
        if (level == 1)
        {
          row.getStyleAttributes().setHided(false);
          costAccountNumberExpandInfo.setExpandStatus(false);
        }
        else
        {
          row.getStyleAttributes().setHided(true);
        }
      }
      else {
        costAccountNumberExpandInfo.setExpandStatus(true);
      }
    }
  }
  
  public void actionCancelCancel_actionPerformed(ActionEvent e)
    throws Exception
  {
    checkSelected();
    int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
    IRow row = this.tblMain.getRow(activeRowIndex);
    

    String id = row.getCell("id").getValue().toString().trim();
    ICostAccount iCostAccount = CostAccountFactory.getRemoteInstance();
    IObjectPK pk = new ObjectStringPK(id);
    
    CostAccountInfo costAccountInfo = iCostAccount.getCostAccountInfo(pk);
    if ((costAccountInfo.getSrcCostAccountId() != null) && (!costAccountInfo.isIsEnabled()))
    {
      MsgBox.showWarning(this, "上级分配下来的已禁用的科目，下级不能启用");
      SysUtil.abort();
    }
    if (iCostAccount.enable(pk))
    {
      setMessageText(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Enabled_OK"));
      showMessage();
      


      this.tblMain.removeRows();
      




      this.tblMain.getSelectManager().select(activeRowIndex, 0);
      this.tblMain.scrollToVisible(activeRowIndex, 0);
    }
  }
  
  public void actionCancel_actionPerformed(ActionEvent e)
    throws Exception
  {
    checkSelected();
    int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
    IRow row = this.tblMain.getRow(activeRowIndex);
    if (row == null) {
      return;
    }
    String id = row.getCell("id").getValue().toString().trim();
    ICostAccount iCostAccount = CostAccountFactory.getRemoteInstance();
    IObjectPK pk = new ObjectStringPK(id);
    if (iCostAccount.disable(pk))
    {
      setMessageText(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "DisEnabled_OK"));
      showMessage();
      



      this.tblMain.removeRows();
      




      this.tblMain.getSelectManager().select(activeRowIndex, 0);
      this.tblMain.scrollToVisible(activeRowIndex, 0);
    }
  }
  
  public void actionAssign_actionPerformed(ActionEvent e)
    throws Exception
  {
    if (!MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "CostAccountAssign_Note")))) {
      return;
    }
    DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
    if (node == null)
    {
      MsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "CostAccount_Assign_Selected"));
      
      return;
    }
    if ((node.getUserObject() instanceof CurProjectInfo))
    {
      CurProjectInfo projectInfo = (CurProjectInfo)node.getUserObject();
      if (!MsgBox.isYes(MsgBox.showConfirm2(this, "要分配的科目的上级科目如果已经有数据，则无法分配，这样会导致该科目上级汇总数不正确，是否继续？"))) {
        return;
      }
      ICostAccountFacade iCostAccountFacade = CostAccountFacadeFactory.getRemoteInstance();
      List errorList = iCostAccountFacade.assignProjsCostAccount(new ObjectStringPK(projectInfo.getId().toString()));
      
      showErrorInfo(errorList);
      this.tblMain.removeRows();
    }
    else if ((node.getUserObject() instanceof OrgStructureInfo))
    {
      OrgStructureInfo oui = (OrgStructureInfo)node.getUserObject();
      if ((oui == null) || (oui.getUnit() == null)) {
        return;
      }
      FullOrgUnitInfo info = oui.getUnit();
      FilterInfo filter = new FilterInfo();
      filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", info.getId().toString()));
      filter.getFilterItems().add(new FilterItemInfo("assigned", Boolean.TRUE));
      boolean ass = CostAccountFactory.getRemoteInstance().exists(filter);
      if ((ass) && 
        (!MsgBox.isYes(MsgBox.showConfirm2(this, "要分配的科目的上级科目如果已经有数据，则无法分配(仅有数据的无法分配)，这样会导致该科目上级汇总数不正确，是否继续？")))) {
        return;
      }
      if ((node.getChildCount() > 0) && ((((DefaultKingdeeTreeNode)node.getChildAt(0)).getUserObject() instanceof OrgStructureInfo)))
      {
        ICostAccountFacade iCostAccountFacade = CostAccountFacadeFactory.getRemoteInstance();
        iCostAccountFacade.assignOrgsCostAccount(new ObjectStringPK(info.getId().toString()));
        this.tblMain.removeRows();
      }
      else
      {
        ICostAccountFacade iCostAccountFacade = CostAccountFacadeFactory.getRemoteInstance();
        List errorlist = iCostAccountFacade.assignOrgProject(new ObjectStringPK(info.getId().toString()));
        showErrorInfo(errorlist);
        this.tblMain.removeRows();
      }
    }
  }
  
  private void showErrorInfo(List errorlist)
  {
    if ((errorlist == null) || (errorlist.size() == 0)) {
      return;
    }
    StringBuffer errorStr = new StringBuffer();
    String sep = "\n";
    for (Iterator iter = errorlist.iterator(); iter.hasNext();)
    {
      String element = (String)iter.next();
      errorStr.append(element);
      errorStr.append(sep);
    }
    String title = "部分科目由于其上级已经有数据，未分配成功，其余已经分配成功！未分配成功的科目请查看详细信息";
    
    String error = errorStr.toString();
    error = error.replace('!', '.');
    MsgBox.showDetailAndOK(this, title, error, 1);
  }
  
  public void actionDisAssign_actionPerformed(ActionEvent e)
    throws Exception
  {
    if (this.tblMain.getSelectManager().getActiveRowIndex() == -1)
    {
      MsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "CostAccount_DisAssign_CheckSelected"));
      return;
    }
    CostAccountCollection cacSelect = new CostAccountCollection();
    
    KDTSelectBlock selectBlock = null;
    ICell cell = null;
    for (int i = 0; i < this.tblMain.getSelectManager().size(); i++)
    {
      CostAccountInfo caiSelect = new CostAccountInfo();
      selectBlock = this.tblMain.getSelectManager().get(i);
      for (int j = selectBlock.getTop(); j <= selectBlock.getBottom(); j++)
      {
        IRow row = this.tblMain.getRow(j);
        
        cell = row.getCell(getKeyFieldName());
        if (cell == null)
        {
          MsgBox.showError(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Error_KeyField_Fail"));
          SysUtil.abort();
        }
        Object keyValue = cell.getValue();
        
        BOSUuid id = BOSUuid.read((String)keyValue);
        caiSelect.setId(id);
        caiSelect.setLongNumber(row.getCell("longNumber").getValue().toString());
        if (row.getCell("fullOrgUnit.id").getValue() != null)
        {
          FullOrgUnitInfo foui = new FullOrgUnitInfo();
          foui.setId(BOSUuid.read(row.getCell("fullOrgUnit.id").getValue().toString()));
          foui.setLongNumber(row.getCell("fullOrgUnit.longNumber").getValue().toString());
          caiSelect.setFullOrgUnit(foui);
        }
        else if (row.getCell("curProject.id").getValue() != null)
        {
          CurProjectInfo cpi = new CurProjectInfo();
          cpi.setId(BOSUuid.read(row.getCell("curProject.id").getValue().toString()));
          cpi.setLongNumber(row.getCell("curProject.longNumber").getValue().toString());
          caiSelect.setCurProject(cpi);
        }
        if (((Boolean)row.getCell("isSource").getValue()).booleanValue())
        {
          caiSelect.setIsSource(true);
        }
        else
        {
          caiSelect.setIsSource(false);
          if (row.getCell("srcCostAccountId").getValue() != null) {
            caiSelect.setSrcCostAccountId(row.getCell("srcCostAccountId").getValue().toString());
          }
        }
        caiSelect.setName(row.getCell("name").getValue().toString(), SysContext.getSysContext().getLocale());
        cacSelect.add(caiSelect);
      }
    }
    ICostAccountFacade iCostAccountFacade = CostAccountFacadeFactory.getRemoteInstance();
    
    ArrayList al = iCostAccountFacade.disAssignSelectOrgProject(cacSelect);
    if (al.size() != 0)
    {
      ArrayList alOrgAndProject = new ArrayList();
      for (int i = 0; i < al.size(); i++) {
        alOrgAndProject.addAll(i, (ArrayList)al.get(i));
      }
      MsgBox.showDetailAndOK(this, "详细信息列出了无法反分配的明细情况!", alOrgAndProject.toString(), 1);
    }
    actionRefresh_actionPerformed(e);
  }
  
  public void actionTemplateImport_actionPerformed(ActionEvent e)
    throws Exception
  {
    DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
    boolean checkGroupNodeSelected = true;
    if (node != null)
    {
      if (node.getUserObject() != null)
      {
        if ((node.getUserObject() instanceof CurProjectInfo))
        {
          checkGroupNodeSelected = false;
        }
        else if ((node.getUserObject() instanceof OrgStructureInfo))
        {
          OrgStructureInfo oui = (OrgStructureInfo)node.getUserObject();
          if ((oui == null) || (oui.getUnit() == null))
          {
            checkGroupNodeSelected = false;
          }
          else
          {
            FullOrgUnitInfo info = oui.getUnit();
            if (!"00000000-0000-0000-0000-000000000000CCE7AED4".equals(info.getId().toString())) {
              checkGroupNodeSelected = false;
            }
          }
        }
      }
      else {
        checkGroupNodeSelected = false;
      }
    }
    else {
      checkGroupNodeSelected = false;
    }
    if (!checkGroupNodeSelected)
    {
      MsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "CostAccount_TemplateImport_CheckNodeSelected"));
      return;
    }
    HashMap map = new HashMap();
    map.put("Owner", this);
    UIContext uiContext = new UIContext(this);
    

    prepareUIContext(uiContext, e);
    IUIFactory uiFactory = null;
    uiFactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
    IUIWindow uiWindow = uiFactory.create("com.kingdee.eas.fdc.basedata.client.CostAccountTemplateDataImportUI", 
      uiContext, null, OprtState.EDIT);
    uiWindow.show();
    actionRefresh_actionPerformed(e);
  }
  
  public void actionImport_actionPerformed(ActionEvent e)
    throws Exception
  {
    DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
    if (node == null)
    {
      MsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "ProjectImport_CheckNodeSelected"));
      return;
    }
    HashMap map = new HashMap();
    map.put("Owner", this);
    UIContext uiContext = new UIContext(this);
    if (node.getUserObject() != null)
    {
      if ((node.getUserObject() instanceof CurProjectInfo))
      {
        CurProjectInfo info = (CurProjectInfo)node.getUserObject();
        uiContext.put("address", info);
        uiContext.put("isOrgFilter", Boolean.valueOf(true));
      }
      else if ((node.getUserObject() instanceof OrgStructureInfo))
      {
        throw new FDCBasedataException(FDCBasedataException.IMPORT_ONLYTOPROJECT);
      }
    }
    else {
      return;
    }
    prepareUIContext(uiContext, e);
    IUIFactory uiFactory = null;
    uiFactory = UIFactory.createUIFactory(UIModelDialogFactory.class.getName());
    IUIWindow uiWindow = uiFactory.create(CostAccountImportUI.class.getName(), uiContext, null, OprtState.EDIT);
    uiWindow.show();
    actionRefresh_actionPerformed(e);
  }
  
  public void actionProjectAttachment_actionPerformed(ActionEvent e)
    throws Exception
  {
    DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
    CurProjectInfo cpi = null;
    if (node == null) {
      return;
    }
    if (OrgViewUtils.isTreeNodeDisable(node)) {
      return;
    }
    if ((node.getUserObject() instanceof CurProjectInfo))
    {
      cpi = (CurProjectInfo)node.getUserObject();
    }
    else
    {
      MsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Project_CheckSelected"));
      
      return;
    }
    super.actionProjectAttachment_actionPerformed(e);
    AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
    String contractId = getSelectedKeyValue();
    




    acm.showAttachmentListUIByBoID(cpi.getId().toString(), this);
  }
  
  protected String getEditUIModal()
  {
    String openModel = UIConfigUtility.getOpenModel();
    if (openModel != null) {
      return openModel;
    }
    return "com.kingdee.eas.base.uiframe.client.UIModelDialogFactory";
  }
  
  protected ArrayList getImportParam()
  {
    DatataskParameter param = new DatataskParameter();
    String solutionName = "eas.fdc.fdcbasedata.fdccostaccount";
    param.solutionName = solutionName;
    param.alias = "房地产成本科目";
    ArrayList paramList = new ArrayList();
    paramList.add(param);
    return paramList;
  }
  
  public void actionEnterDB_actionPerformed(ActionEvent e)
    throws Exception
  {
    checkSelected();
    
    int index = getMainTable().getSelectManager().getActiveRowIndex();
    CostAccountInfo acct = new CostAccountInfo();
    Object obj = getMainTable().getCell(index, "isEnterDB").getValue();
    Object objID = getMainTable().getCell(index, "id").getValue();
    Object longNumber = getMainTable().getCell(index, "longNumber").getValue();
    if ((obj == null) || (obj.equals(Boolean.TRUE)) || (objID == null) || (longNumber == null)) {
      return;
    }
    acct.setId(BOSUuid.read(objID.toString()));
    acct.setLongNumber(longNumber.toString().trim().replaceAll("\\.", "!"));
    acct.setIsEnabled(false);
    CostAccountFacadeFactory.getRemoteInstance().handleEnterDB(acct, true);
    refreshList();
  }
  
  public void actionCancelEnterDB_actionPerformed(ActionEvent e)
    throws Exception
  {
    checkSelected();
    int index = getMainTable().getSelectManager().getActiveRowIndex();
    CostAccountInfo acct = new CostAccountInfo();
    Object obj = getMainTable().getCell(index, "isEnterDB").getValue();
    Object objID = getMainTable().getCell(index, "id").getValue();
    Object longNumber = getMainTable().getCell(index, "longNumber").getValue();
    if ((obj == null) || (obj.equals(Boolean.FALSE)) || (objID == null) || (longNumber == null)) {
      return;
    }
    acct.setId(BOSUuid.read(objID.toString()));
    acct.setLongNumber(longNumber.toString().trim().replaceAll("\\.", "!"));
    acct.setIsEnterDB(true);
    CostAccountFacadeFactory.getRemoteInstance().handleEnterDB(acct, false);
    refreshList();
  }
  
  public void actionAssignToOrg_actionPerformed(ActionEvent e)
    throws Exception
  {
    if (!MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "CostAccountAssign_Note")))) {
      return;
    }
    if (!MsgBox.isYes(MsgBox.showConfirm2(this, "要分配的科目的上级科目如果已经有数据，则无法分配(仅有数据的无法分配)，这样会导致该科目上级汇总数不正确，是否继续？"))) {
      return;
    }
    UIContext uiContext = new UIContext(this);
    IUIWindow assignUI = UIFactory.createUIFactory().create(CostAccountAssignUI.class.getName(), uiContext);
    assignUI.show();
    
    this.tblMain.removeRows();
  }
}
