/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.ctrl.extendcontrols.IFormatter;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMenuManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.base.commonquery.IQuerySolutionFacade;
import com.kingdee.eas.base.commonquery.QuerySolutionFacadeFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CommonQueryParam;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.invite.ColumnTypeEnum;
import com.kingdee.eas.fdc.invite.DescriptionEnum;
import com.kingdee.eas.fdc.invite.HeadColumnCollection;
import com.kingdee.eas.fdc.invite.HeadColumnFactory;
import com.kingdee.eas.fdc.invite.HeadColumnInfo;
import com.kingdee.eas.fdc.invite.HeadTypeInfo;
import com.kingdee.eas.fdc.invite.RefPriceCollection;
import com.kingdee.eas.fdc.invite.RefPriceEntryInfo;
import com.kingdee.eas.fdc.invite.RefPriceFactory;
import com.kingdee.eas.fdc.invite.RefPriceInfo;
import com.kingdee.eas.fi.gl.client.AcctCussentFilterPanel;
import com.kingdee.eas.fi.gl.common.RptClientUtil;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class ListingItemPriceQueryUI extends AbstractListingItemPriceQueryUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ListingItemPriceQueryUI.class);
	
	protected CommonQueryDialog queryDlg;
	protected ListingItemQueryProcessor queryHelper;
	ListingItemPriceQueryFilterUI myPanel;
	EntityViewInfo view;
	
	//	 �Ƿ��һ��ʹ��
    private boolean isFirstDefaultQuery = true;
	/**
	 * output class constructor
	 */
	public ListingItemPriceQueryUI() throws Exception {
		super();
	}
	/**
     * ��ȡquery�е����������ƣ����ع��༭/ɾ��ʱ��ȡ�����ã�Ĭ��ֵΪ"id"���̳����������
     *
     * @return query�е�����������
     */
    protected String getKeyFieldName()
    {
        return "entrys.id";
    }
    protected void execQuery() {

        return;
    }
    protected void refresh(ActionEvent e) throws Exception
    {
        //CacheServiceFactory.getInstance().discardQuery(this.mainQueryPK);
        //execQuery();
    	return;
    }
	
	//
	protected boolean isAllowDefaultSolutionNull() {
		return true;
	}

	protected boolean initDefaultFilter() {
		return false;
	}
	
	protected boolean isIgnoreCUFilter()
    {
        return true;
    }	

	public void onLoad() throws Exception {
		
		if (!PermissionFactory
				.getRemoteInstance()
				.hasFunctionPermission(
						new ObjectUuidPK(SysContext.getSysContext()
								.getCurrentUserInfo().getId().toString()),
						new ObjectUuidPK(SysContext.getSysContext()
								.getCurrentOrgUnit().getId().toString()),
						new MetaDataPK(
								"com.kingdee.eas.fdc.invite.client.ListingItemPriceQueryUI"),
						new MetaDataPK("ActionOnLoad"))) {
			// throw new
			// Permission//AccountBooksException(AccountBooksException.NOSWITCH,new
			// Object[]{scheme.getName()});
			MsgBox.showError(this, "û�в鿴Ȩ��");
			SysUtil.abort();
		}
		
		tblMain.setVisible(false);
		myPanel = new ListingItemPriceQueryFilterUI();
		myPanel.setPanelName("����");
		
		queryHelper = new ListingItemQueryProcessor(this,this.kDTabbedPane1,myPanel);
		
		queryDlg = new CommonQueryDialog();
		
		view = new EntityViewInfo();
        SorterItemCollection sic = view.getSorter();
        sic.add(new SorterItemInfo("account.number"));
        queryDlg.setEntityViewInfo(view);
        queryDlg.setOwner((Component) getUIContext().get(UIContext.OWNER));
        queryDlg.setParentUIClassName(this.getClass().getName());
        
        queryDlg.setQueryObjectPK(mainQueryPK);
        
        queryDlg.setShowFilter(true);
        queryDlg.setShowSorter(false);
        queryDlg.setShowToolbar(true);
        
        queryDlg.setProcessor(queryHelper);
        queryDlg.setHeight(380);
        queryDlg.setWidth(420);
        queryDlg.setTitle(myPanel.getUITitle());

        queryDlg.addUserPanel(queryHelper.getUserPanel());

        queryDlg.init();
        queryDlg.getCommonQueryParam().setDirty(false);
        queryHelper.setCommonFilterPanel(queryDlg.getCommonFilterPanel());
        queryHelper.addKDLisener(queryDlg.getCommonFilterPanel());
        queryHelper.addKDLisener(queryHelper.getUserPanel(),queryDlg.getCommonFilterPanel());
		
		//setIsNeedDefaultFilter(false);
		btnQuery.setVisible(false);
        super.onLoad();
        btnQuery.setVisible(true);
        //btnQuery.setAction(null);
        btnQuery.setEnabled(true);
        
        tblMain.addKDTDataFillListener(new KDTDataFillListener() {

            /**
             * ȡ����������ݺ󴥷����¼���
             * 
             * @param e
             *            ���������¼�����
             */            
            public void afterDataFill(KDTDataRequestEvent e) {
                try {
                    return;
                    
                } catch (Exception e2) {
                    handUIException(e2);
                }
            }
        });
		
		//FDCTableHelper.disableExportByPerimission(this, this.getTableForCommon(), "ActionExport");
		//û�м̳�coreUI,��Ҫ�����Ƿ���Ȩ��
		//У��Ȩ��:ĳ����֯ĳ���û�ĳ�����ܵ�Ȩ���Ƿ���Ȩ��
		

		if(!this.showQueryDlg()){
			SysUtil.abort();
		}

		initControl();
		//this.kDTabbedPane1.setVisible(false);
		//fillTable();
		setQueryPreference(true);
	}
	
	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
//		String path = null;
		
		File tempFile;
				
		KDTable table = getTableForCommon();
		if(table != null){
			Integer rowMax = queryHelper.getCanExportMaxRowIndex(table.getName());
			if(table.getRowCount()>rowMax.intValue()){
				MsgBox.showInfo(this,"��û��ȫ����˾��Ȩ�ޣ���ֻ�ܵ���ǰ"+rowMax.toString()+"��");	
			}
			else{
				tempFile = File.createTempFile("eastemp", ".xls");
				table.getIOManager().setTempFileDirection(
						tempFile.getPath());
				table.getIOManager().exportExcelToTempFile(false);
				tempFile.deleteOnExit();
			}
		}
	}
	
	public void actionExportSelected_actionPerformed(ActionEvent e)  {
//		String path = null;
		File tempFile ;
		try {
			tempFile = File.createTempFile("eastemp", ".xls");
			KDTable table = getTableForCommon();
			KDTSelectManager selectManager = table.getSelectManager();
			int size = selectManager.size();
			KDTSelectBlock selectBlock = null;
			// ��Ϊ������˳����ܲ����Ǳ����е�˳��������Ҫ����ʹѡ����˳����������С����
			List indexList = new ArrayList();

			for (int blockIndex = 0; blockIndex < size; blockIndex++) {
				selectBlock = selectManager.get(blockIndex);
				int top = selectBlock.getBeginRow();
				int bottom = selectBlock.getEndRow();
				if (table.getRow(top) == null) {
					MsgBox.showInfo(this, EASResource
							.getString(FrameWorkClientUtils.strResource
									+ "Msg_NoneEntry"));
					return;
				}
				for (int i = top; i <= bottom; i++) {
					indexList.add(new Integer(i));
				}
			}
			Integer[] indexArr = new Integer[indexList.size()];
			Object[] indexObj = indexList.toArray();
			System.arraycopy(indexObj, 0, indexArr, 0, indexArr.length);
			Arrays.sort(indexArr);
//			if (indexArr == null)
//				return;
			Integer rowMax = queryHelper.getCanExportMaxRowIndex(table.getName());
			
			if(indexArr[indexArr.length-1].intValue() >= rowMax.intValue()){
				MsgBox.showInfo(this,"��ѡ����û��Ȩ�޵��У���ֻ�ܵ���ǰ"+rowMax.toString()+"��");			
			}
			else{
				getTableForCommon().getIOManager().setTempFileDirection(
						tempFile.getPath());
				getTableForCommon().getIOManager().exportExcelToTempFile(true);
				
			}
			tempFile.deleteOnExit();
		} catch (IOException e1) {
			// TODO �Զ����� catch ��
			e1.printStackTrace();
		}
		// path = tempFile.getCanonicalPath();
		
		
	}
	
	
	private void initControl() {
		this.menuItemQuery.setEnabled(true);
		this.menuItemQuery.setVisible(true);
		this.menuEdit.setVisible(false);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}
	
	public KDTable getMainTable() {
		//return this.tblMain;
		
		if(this.kDTabbedPane1.getSelectedComponent() == null)
			return this.tblMain;
		else
			return (KDTable)this.kDTabbedPane1.getSelectedComponent();
	}
	
	public KDTable getTableForCommon(){
		return getMainTable();
	}
	
	public BigDecimal getSelectSumValue() {
		KDTable table = (KDTable)this.kDTabbedPane1.getSelectedComponent();
		//KDTable table = tblMain;
		if (table == null)
			return null;
		if (table.getSelectManager().size() == 0) {
			return null;
		}
		//���ѡ���Ƕ����򣬷���null
		int index = table.getSelectManager().get().getBeginRow();
		int colCount = table.getColumnCount();
		int colIndex = 0;
		for(int i=1;i<colCount;i++){
			if(table.getHeadRow(1).getCell(i).getValue()==null){
				continue;
			}
			if(table.getHeadRow(1).getCell(i).getValue().equals("�ۺϵ���")||
					table.getHeadRow(1).getCell(i).getValue().equals("С��")){	
				colIndex = i;
				break;
			}	
		}
		ICell cell = table.getCell(index, colIndex);
		if (cell == null){
			return null;
		}
		Object value = cell.getValue();
		if(value instanceof BigDecimal)
			return (BigDecimal) value;
		else
			return null;
		
	}
	/**
     * �Ƿ���Ҫ���б������
     */ 
    protected boolean isOrderForClickTableHead() {
        return false;
    }
	protected void btnQuery_actionPerformed(ActionEvent e) throws Exception {
		//super.btnQuery_actionPerformed(e);
		//fillTable();
		if (isFirstDefaultQuery) {
            return;
        }
        showQueryDlg();
	}
	
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		EntityViewInfo v = queryDlg.getEntityViewInfoResult();
		if(v==null||v.getFilter()==null){
			isFirstDefaultQuery = true;
			showQueryDlg();
		}
		else
			queryHelper.fillData(v);
	}
	/**
     * output menuItemQuery_actionPerformed method
     */
    protected void menuItemQuery_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	btnQuery_actionPerformed(e);
    }
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception{
		
	}
	public boolean showQueryDlg(EntityViewInfo ev) throws EASBizException, BOSException, Exception{
		if( queryHelper.fillData(ev)){
			return true;
		}
		else{
			if(MsgBox.YES == MsgBox.showConfirm2(this,"û�в鵽�κν��,�Ƿ��������������ѯ?")){
				return showQueryDlg();
			}
			else{
				return false;
			}
		}
	}
	public boolean showQueryDlg() throws Exception {
					
		IQuerySolutionFacade iQuery = QuerySolutionFacadeFactory.getRemoteInstance();
		String queryName = (getQueryInfo(mainQueryPK)).getFullName();
		EntityViewInfo ev = null;
		if (isFirstDefaultQuery && !isPerformDefaultQuery(iQuery, queryName)){
			ev = (EntityViewInfo) iQuery.getDefaultFilterInfo(this.getClass().getName(), queryName);
			if (ev != null && ev.getFilter() != null){
				isFirstDefaultQuery = false;
				return showQueryDlg(ev);
			}
		}
		
		if(queryDlg.show()){
			isFirstDefaultQuery = false;
            ev = queryDlg.getEntityViewInfoResult();
            return showQueryDlg(ev);
		}
		else
			return false;
    }
	
	protected String getEditUIName() {
		// TODO �Զ����ɷ������
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO �Զ����ɷ������
		return null;
	}
	
	public void initSavedUserConfig(){
		this.initUserConfig();
	}
	
	public void setTableMenu(final KDTable table) {
		try {
			tableMenu = new NewListingTableMenuUI();
		} catch (Exception e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
		enableTableCommonMenus(table);
		//tHelper.addMenuToTable(table);
		tHelper.setCanMoveColumn(false);
		enableExportExcel(table);
		KDTMenuManager tm = getMenuManager(table);
		if (tm == null) {
			tm = new KDTMenuManager(table);
		}
	}
    
}