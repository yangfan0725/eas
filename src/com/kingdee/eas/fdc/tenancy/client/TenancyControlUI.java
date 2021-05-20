package com.kingdee.eas.fdc.tenancy.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basecrm.client.FDCReceivingBillEditUI;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.EffectImageCollection;
import com.kingdee.eas.fdc.sellhouse.EffectImageElementEntryCollection;
import com.kingdee.eas.fdc.sellhouse.EffectImageElementEntryInfo;
import com.kingdee.eas.fdc.sellhouse.EffectImageEnum;
import com.kingdee.eas.fdc.sellhouse.EffectImageFactory;
import com.kingdee.eas.fdc.sellhouse.EffectImageInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PlanisphereElementEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PlanisphereElementEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PlanisphereElementEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PlanisphereElementEnum;
import com.kingdee.eas.fdc.sellhouse.PlanisphereInfo;
import com.kingdee.eas.fdc.sellhouse.PlanisphereTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.EffectImageAndPlanisphereInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.ISolidSideImageListener;
import com.kingdee.eas.fdc.sellhouse.client.RoomEditUI;
import com.kingdee.eas.fdc.sellhouse.client.RoomSourceEditUI;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEImagePanel;
import com.kingdee.eas.fdc.sellhouse.client.SolidSideImage;
import com.kingdee.eas.fdc.sellhouse.client.SolidSideInfo;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryCollection;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryInfo;
import com.kingdee.eas.fdc.tenancy.FlagAtTermEnum;
import com.kingdee.eas.fdc.tenancy.KeepRoomDownFactory;
import com.kingdee.eas.fdc.tenancy.QuitTenancyFactory;
import com.kingdee.eas.fdc.tenancy.RentFreeEntryCollection;
import com.kingdee.eas.fdc.tenancy.RentRemissionFactory;
import com.kingdee.eas.fdc.tenancy.SincerObligateCollection;
import com.kingdee.eas.fdc.tenancy.SincerObligateInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillCollection;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyContractTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyDisPlaySetting;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyModificationFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;
import com.sun.xml.messaging.saaj.util.ByteInputStream;

/**
 * ���޿��ƽ���
 * 
 * @author laiquan_luo
 */
public class TenancyControlUI extends AbstractTenancyControlUI implements ISolidSideImageListener
{

	private RoomInfo roomInfo=null;
	private static final Logger logger = CoreUIObject.getLogger(TenancyControlUI.class);
	TenancyDisPlaySetting setting = new TenancyDisPlaySetting();
	private MoneyDefineInfo rentMoneyName = null;// ����������
	private SolidSideImage ssi = null; //����ͼ��������
	private KDScrollPane solidSideImagePanel=new KDScrollPane();
	private BigDecimal selectedZoom=null; //����ͼ����
	
	private KDScrollPane effectImagePanel=new KDScrollPane();
	private int topX,topY,downX,downY;// ��ǰѡ���ȵ�����߿�����
	private SHEImagePanel pnlRoomPic=new SHEImagePanel(){
		public void paint(Graphics g) {
			super.paint(g);
			
			//������� �ȵ�������� ������ɫ���� ����Ӧ�����������������  Ŀǰֻ����¥��Ч��ͼ ��������Ч��ͼ���ڴ���
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			
			//Update by zhiyuan_tang 2010/10/26 ����Ч��ͼ��ʾ������������
			//������ʱ�ڵ�����֮ǰ���ڵ�Ԫ���Ļ�����������ƽ��ʾ��ͼ�ڵ㣬���������Ļ���Ч��ͼ�ͱ���������ƽ��ʾ��ͼ
			//���һ��¥��������Ч��ͼ��û������ƽ��ʾ��ͼ������ڵ���ʾ��������Ч��ͼ��û�а취�鿴��
			//�������ڲ�������ƽ��ʾ��ͼ�ڵ㣬��������һ��ƽ��ʾ��ͼ��Ч��ͼ�ĺϲ��ڵ㡣
			if(node.getUserObject() instanceof EffectImageAndPlanisphereInfo){
//			if(node.getUserObject() instanceof PlanisphereInfo){
//				FilterInfo filter=new FilterInfo();
//				BuildingFloorEntryInfo bf=getSelectBF((PlanisphereInfo)node.getUserObject());
//	    		filter.getFilterItems().add(new FilterItemInfo("type",EffectImageEnum.PIC_BUILDINGFLOOR_VALUE));
//	    		filter.getFilterItems().add(new FilterItemInfo("buildingFloor.id",bf.getId().toString()));
//				DefaultKingdeeTreeNode parent = (DefaultKingdeeTreeNode)node.getParent();
//				//���ڵ��ǵ�Ԫʱ�������ֵ�Ԫ��¥��Ч��ͼ
//				if(parent.getUserObject()!=null && parent.getUserObject() instanceof BuildingUnitInfo){
//					filter.getFilterItems().add(new FilterItemInfo("isShowUnit",Boolean.TRUE));
//				}
//				//���ڵ���¥��ʱ���ز����ֵ�Ԫ��¥��Ч��ͼ
//				if(parent.getUserObject()!=null && parent.getUserObject() instanceof BuildingInfo){
//					filter.getFilterItems().add(new FilterItemInfo("isShowUnit",Boolean.FALSE));
//				}
//				EffectImageInfo info=getEffectImageInfo(filter);
				EffectImageAndPlanisphereInfo effectPlaininfo = (EffectImageAndPlanisphereInfo)node.getUserObject();
				EffectImageInfo info = effectPlaininfo.getEffectImageInfo();
				if(info==null || info.getElementEntry()==null){//���û�������ȵ����û�е���Ч��ͼ�����κδ���
					return;
				}
				else{
					EffectImageElementEntryCollection entryColl=info.getElementEntry();
					Set set=new HashSet();//����װ ���з����ID
					for(int i=0;i<entryColl.size();i++){
						EffectImageElementEntryInfo entry=entryColl.get(i);
						set.add(entry.getElementID());
					}
					RoomCollection rc=null;
					try {
						rc=getRoomCollectionByEffect(set);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					for(int i=0;i<entryColl.size();i++){
						EffectImageElementEntryInfo entry=entryColl.get(i);
						if(entry.getHotspot1()==null || entry.getHotspot2()==null){
							continue;
						}
//						try {
//							RoomInfo room=RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(entry.getElementID()));
//							roomInfo=SHEHelper.queryRoomInfo(room.getId().toString());
//							TenancyDisPlaySetting tdps=new TenancyDisPlaySetting();
//							Color c=Color.GRAY;
//							if(roomInfo.getTenancyState()!=null){
//								c=tdps.getCellBackgroundColor(roomInfo.getTenancyState().getValue());
//							}
//							g.setColor(new Color(c.getRed(),c.getGreen(),c.getBlue(),255));//Ԥ�������÷�����������ȡ�õı�����ɫ͸���ȵ����� 
//						} catch (EASBizException e) {
//							e.printStackTrace();
//						} catch (BOSException e) {
//							e.printStackTrace();
//						}
						//����ĳ����� ����RPC����������ܡ���
						if(rc!=null){
							for(int j=0;j<rc.size();j++){
								RoomInfo roomInfo=rc.get(j);
								if(entry.getElementID().equals(roomInfo.getId().toString())){
									TenancyDisPlaySetting tdps=new TenancyDisPlaySetting();
									Color c=Color.GRAY;
									if(roomInfo.getTenancyState()!=null){
										c=tdps.getCellBackgroundColor(roomInfo.getTenancyState().getValue());
									}
									g.setColor(new Color(c.getRed(),c.getGreen(),c.getBlue(),255));//Ԥ�������÷�����������ȡ�õı�����ɫ͸���ȵ�����
									break;
								}
							}
						}
						String hotspot1=entry.getHotspot1();
						String hotspot2=entry.getHotspot2();
						int hotspot1_x=Integer.parseInt(hotspot1.substring(0,hotspot1.indexOf(',')));
						int hotspot1_y=Integer.parseInt(hotspot1.substring(hotspot1.indexOf(',')+1,hotspot1.length()));
						int hotspot2_x=Integer.parseInt(hotspot2.substring(0,hotspot2.indexOf(',')));
						int hotspot2_y=Integer.parseInt(hotspot2.substring(hotspot2.indexOf(',')+1,hotspot2.length()));
						int x=0,y=0;
						if(hotspot1_x<hotspot2_x){
							if(hotspot1_y<hotspot2_y){
								g.fillRect(hotspot1_x, hotspot1_y, hotspot2_x-hotspot1_x, hotspot2_y-hotspot1_y);
								g.setColor(Color.BLACK);
								x=(hotspot2_x-hotspot1_x)/2+hotspot1_x-16;
								y=(hotspot2_y-hotspot1_y)/2+hotspot1_y+4;
								g.drawString(entry.getElementName(), x, y);
							}
							else{
								g.fillRect(hotspot1_x, hotspot2_y, hotspot2_x-hotspot1_x, hotspot1_y-hotspot2_y);
								g.setColor(Color.BLACK);
								x=(hotspot2_x-hotspot1_x)/2+hotspot1_x-16;
								y=(hotspot1_y-hotspot2_y)/2+hotspot1_y+4;
								g.drawString(entry.getElementName(), x, y);
							}
						}
						else{
							if(hotspot2_y<hotspot1_y){
								g.fillRect(hotspot2_x, hotspot2_y, hotspot1_x-hotspot2_x, hotspot1_y-hotspot2_y);
								g.setColor(Color.BLACK);
								x=(hotspot1_x-hotspot2_x)/2+hotspot1_x-16;
								y=(hotspot1_y-hotspot2_y)/2+hotspot1_y+4;
								g.drawString(entry.getElementName(), x, y);
							}
							else{
								g.fillRect(hotspot2_x, hotspot1_y, hotspot1_x-hotspot2_x, hotspot2_y-hotspot1_y);
								g.setColor(Color.BLACK);
								x=(hotspot1_x-hotspot2_x)/2+hotspot1_x-16;
								y=(hotspot2_y-hotspot1_y)/2+hotspot1_y+4;
								g.drawString(entry.getElementName(), x, y);
							}
						}
						
					}
				}
			}
			//�Ȼ���ǰѡ���ȵ�����߿�
			g.setColor(new Color(255,0,255));
			if(topX<downX){
				if(topY<downY){
					g.drawRect(topX, topY, downX-topX, downY-topY);
					
				}
				else{
					g.drawRect(topX, downY, downX-topX, topY-downY);
				}
			}
			else{
				if(downY<topY){
					g.drawRect(downX, downY, topX-downX, topY-downY);
				}
				else{
					g.drawRect(downX, topY, topX-downX, downY-topY);
				}
			}
		}
	};
	MouseListener moListener;
	MouseMotionListener mmListener;
	public TenancyControlUI() throws Exception
	{
		super();
		
	}

	CoreUIObject detailUI = null;

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	public void onLoad() throws Exception
	{
		initControl();
		initTable();
		super.onLoad();
	if (!saleOrg.isIsBizUnit())
		{
			this.actionReceiveBill.setEnabled(false);
			this.actionCancelTenancy.setEnabled(false);
			this.actionHandleTenancy.setEnabled(false);
			this.actionApplyTenancy.setEnabled(false);
			this.actionContinueTenancy.setEnabled(false);
			this.actionRejiggerTenancy.setEnabled(false);
			this.actionQuitTenancy.setEnabled(false);
			this.actionKeepRoom.setEnabled(false);
			this.actionChangeName.setEnabled(false);
			this.actionReceiveBill.setEnabled(false);
		}
		//����״̬��ɫ���
		RoomStateColorUI.insertUIToScrollPanel(this.kDScrollPane1);
		setComboBoxZoomItem();
		actionLocate.setVisible(true);
		actionLocate.setEnabled(true);
		
		this.solidSideRadioBtn.setVisible(false);
		this.planeRadioBtn.setVisible(false);
		this.effectRadioBtn.setVisible(false);
		this.actionReceiveBill.setVisible(false);
//		this.actionKeepRoom.setVisible(false);
		
		this.btnKeepRoom.setText("���");
		this.btnSpecial.setVisible(false);
		
		this.menuBiz.setVisible(false);
		this.actionImportData.setVisible(false);
	}
	
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null ||node.getUserObject() instanceof OrgStructureInfo
				||node.getUserObject() instanceof SellProjectInfo
				||node.getUserObject() instanceof SubareaInfo  
				||node.getUserObject() instanceof EffectImageAndPlanisphereInfo ){
//				||node.getUserObject() instanceof PlanisphereInfo ){
			return;
		}
		//ѡ�� ��Ԫ�ڵ�  ���� ¥���ڵ㲢��¥�����޵�Ԫ�ڵ�ʱ
		if(node.getUserObject() instanceof BuildingUnitInfo
				|| (node.getUserObject() instanceof BuildingInfo && node.getChildCount()==0)){
			if(this.planeRadioBtn.isSelected()){//ѡ��ƽ��ͼ
				String number=JOptionPane.showInputDialog(this,"�������:","��λ",JOptionPane.PLAIN_MESSAGE);
				if(number!=null && !number.trim().equals("")){
					for(int i=0;i<tblMain.getRowCount();i++){
						for(int j=0;j<tblMain.getColumnCount();j++){
							if(tblMain.getCell(i, j).getValue()!=null && number.equals(tblMain.getCell(i, j).getValue().toString())){
								tblMain.getSelectManager().select(i, j);
								tblMain.scrollToVisible(i,j);

							}
						}
					}
				}
			}
		}
	}
	
	//��������Ҫ���� ����״̬�����ˡ���Ϊ�����ܡ���
	private RoomCollection getRoomCollectionByEffect(Set set) throws Exception{
		EntityViewInfo view=new EntityViewInfo();
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("tenancyState");
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
		view.setSelector(sic);
		view.setFilter(filter);
		return RoomFactory.getRemoteInstance().getRoomCollection(view);
	}
	
	/**
	 *  �жϵ����ĵ��Ƿ��ھ��εķ�Χ�� <br>
	 *  
	 * @param x Ҫ�жϵĵ��X����
	 * @param y Ҫ�жϵĵ��Y����
	 * @param hotspot1 ���εĶԽǶ�������1
	 * @param hotspot2 ���εĶԽǶ�������2
	 * @return
	 */
	public boolean hotspotInRec(int x,int y,String hotspot1,String hotspot2){
		int hotspot1_x=Integer.parseInt(hotspot1.substring(0,hotspot1.indexOf(',')));
		int hotspot1_y=Integer.parseInt(hotspot1.substring(hotspot1.indexOf(',')+1,hotspot1.length()));
		int hotspot2_x=Integer.parseInt(hotspot2.substring(0,hotspot2.indexOf(',')));
		int hotspot2_y=Integer.parseInt(hotspot2.substring(hotspot2.indexOf(',')+1,hotspot2.length()));
		//��Ϊ�������ĶԽǵ㲻һ���Ǿ��ε����ϽǺ����½� ����
		//���ȼ�������ε� ���ϽǶ�����������½Ƕ������� ����
		//�ķ����������m.m ....
		if(hotspot1_x<hotspot2_x){
			if(hotspot1_y<hotspot2_y){ //��ʱ���϶�������hotspot1_x��hotspot1_y
				if(x>=hotspot1_x && y>=hotspot1_y && x<=hotspot2_x && y<=hotspot2_y){
					return true;
				}
				else{
					return false;
				}
			}
			else{//��ʱ���϶�������hotspot1_x��hotspot2_y
				if(x>=hotspot1_x && y>=hotspot2_y && x<=hotspot2_x && y<=hotspot1_y){
					return true;
				}
				else{
					return false;
				}
			}
		}
		else{
			if(hotspot2_y<hotspot1_y){//��ʱ���϶�������hotspot2_x��hotspot2_y
				if(x>=hotspot2_x && y>=hotspot2_y && x<=hotspot1_x && y<=hotspot1_y){
					return true;
				}
				else{
					return false;
				}
			}
			else{//��ʱ���϶�������hotspot2_x��hotspot1_y
				if(x>=hotspot2_x && y>=hotspot1_y && x<=hotspot1_x && y<=hotspot2_y){
					return true;
				}
				else{
					return false;
				}
			}
		}
	}

	public void onShow() throws Exception
	{
		super.onShow();
	}

	protected void initTable()
	{
		this.tblTenStat.checkParsed();
		this.tblTenStat.setEnabled(false);
		this.tblTenStat.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);	

		ObjectValueRender render_scale = new ObjectValueRender();
		render_scale.setFormat(new IDataFormat() {
			public String format(Object o) {
				if(o==null){
					return null;
				}else{
					String str = o.toString();
					return str + "%";
				}
				
			}
		});
		this.tblTenStat.getColumn("ten").setRenderer(render_scale);
	}

	public void initControl()
	{
		this.menuItemImportData.setVisible(false);
		this.menuEdit.setVisible(false);
		this.actionImportData.setVisible(true);
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setVisible(false);
		this.actionRemove.setEnabled(false);
		this.actionView.setVisible(false);
		this.actionView.setEnabled(false);
		this.actionQuery.setVisible(false);
		this.actionQuery.setEnabled(false);
		this.actionLocate.setVisible(false);
		this.actionQuery.setEnabled(false);
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		this.treeView.setShowControlPanel(true);

		this.menuItemChangeTenancy.setVisible(false);
		this.btnChangeTenancy.setVisible(false);
	}

	/**
	 * ��Ҫ����˫���������ʱ�򣬵�������Ľ�������
	 */
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception
	{
		if (e.getButton() == 1 && e.getClickCount() == 2)
		{
			RoomInfo room = this.getSelectRoom(false);
			if (room != null)
			{
				UIContext uiContext = new UIContext(this);
				uiContext.put("ID", room.getId().toString());
				IUIWindow uiWindow = UIFactory.createUIFactory(
						UIFactoryName.NEWTAB).create(RoomSourceEditUI.class.getName(),
								uiContext, null, "VIEW");
				uiWindow.show();
			}
		}
		System.out.println(tblMain.getCell(e.getRowIndex(), e.getColIndex()).getValue()!=null?tblMain.getCell(e.getRowIndex(), e.getColIndex()).getValue().toString():"123456789");
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
	throws Exception
	{
		//��������������֯����������action״̬ 
		if(saleOrg.isIsBizUnit()){
			changeButtonState();
		}
//		this.actionReceiveBill.setEnabled(true);
		RoomInfo room = this.getSelectRoom(true);
		if (room != null)
		{
			if (detailUI == null)
			{
				UIContext uiContext = new UIContext(ui);
				uiContext.put(UIContext.ID, room.getId().toString());
				detailUI = (CoreUIObject) UIFactoryHelper
				.initUIObject(RoomDetailInfoUI.class.getName(),
						uiContext, null, "VIEW");
				detailUI.setDataObject(room);
				sclPanel.setViewportView(detailUI);
				sclPanel.setKeyBoardControl(true);
				return;
			}
			detailUI.getUIContext().put(UIContext.ID, room.getId().toString());
			detailUI.setDataObject(room);
			detailUI.loadFields();
		}
	}

	/**
	 * ���ݷ����״̬���ı乤������ҵ��ť��״̬
	 * @throws UuidException 
	 * @throws BOSException 
	 * @throws EASBizException 
	 *
	 */
	private void changeButtonState() throws EASBizException, BOSException, UuidException
	{
		RoomInfo room = this.getSelectRoom(false);
		
//		if (!saleOrg.isIsBizUnit() || room == null)
//		{
//			this.actionReceiveBill.setEnabled(false);
//			return;
//		}
		if ( room == null){
				this.actionReceiveBill.setEnabled(false);
				return;
			}
		TenancyStateEnum state = room.getTenancyState();
		//δ����
		if (TenancyStateEnum.unTenancy.equals(state) || state == null)
		{
			this.actionApplyTenancy.setEnabled(false);
			this.actionHandleTenancy.setEnabled(false);
			this.actionKeepRoom.setEnabled(false);
			this.actionCancelTenancy.setEnabled(false);
		} //����Ƿ���״̬��
		else if (TenancyStateEnum.waitTenancy.equals(state))
		{
			this.actionApplyTenancy.setEnabled(true);
			this.actionHandleTenancy.setEnabled(true);
			this.actionKeepRoom.setEnabled(true);
			this.actionCancelTenancy.setEnabled(true);
		}
		//���� 
		else if (TenancyStateEnum.newTenancy.equals(state) ||
				TenancyStateEnum.continueTenancy.equals(state) ||
				TenancyStateEnum.enlargeTenancy.equals(state)){
			this.actionApplyTenancy.setEnabled(false);
			this.actionHandleTenancy.setEnabled(false);
			this.actionKeepRoom.setEnabled(false);
			this.actionCancelTenancy.setEnabled(false);
		} //��������Ǳ�����״̬ 
		else if (TenancyStateEnum.keepTenancy.equals(state) || TenancyStateEnum.sincerObligate.equals(state))
		{
			this.actionApplyTenancy.setEnabled(false);
			this.actionHandleTenancy.setEnabled(false);
			this.actionKeepRoom.setEnabled(false);
			this.actionCancelTenancy.setEnabled(false);
		}
	}

	/**
	 * ���ѡ��ķ���
	 */
	public RoomInfo getSelectRoom(boolean reQuery) throws EASBizException, BOSException, UuidException{
		if(this.solidSideRadioBtn.isSelected() || this.effectRadioBtn.isSelected()){
			return roomInfo;
		}
		if(this.planeRadioBtn.isSelected()){
			KDTSelectBlock block = this.tblMain.getSelectManager().get();
			if (block == null) {
				return null;
			}
			int left = block.getLeft();
			int top = block.getTop();
			ICell cell = this.tblMain.getCell(top, left);
			if (cell == null) {
				return null;
			}
			RoomInfo room = null;
			if(cell.getUserObject()!=null && cell.getUserObject() instanceof RoomInfo)
				room = (RoomInfo) cell.getUserObject();		
			if(room == null){
				return null;
			}
	
			//Ϊ��Ч�ʴ�userObject��ֻ����һ��ID��������Ҫ�ٲ�һ��
			if(reQuery)	{
				room = SHEHelper.queryRoomInfo(room.getId().toString());
				cell.setUserObject(room);
			}
			return room;
		}
		return null;
	}

	protected void setActionState()
	{
		// super.setActionState();
	}

	protected void initTree() throws Exception
	{
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		this.treeMain.setModel(SHEHelper.getFloorTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
	throws Exception
	{
		refresh(null);
		if(effectRadioBtn.isSelected()){
			topX=0;topY=0;downX=0;downY=0;
			pnlRoomPic.repaint();
		}
	}
	 public void actionRefresh_actionPerformed(ActionEvent e)
     throws Exception
 {
		 refresh(e);
 }
	protected void refresh(ActionEvent e) throws BOSException
	{
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node == null)
		{
			return;
		}
		//��ѡ�� ƽ��ͼʱ
		if(this.planeRadioBtn.isSelected()){
			//����ƽ��ͼ�����ڵ�Ԫ���Ļ���������ƽ��ͼ�ڵ����ɵģ���Ե�Ԫ����depth����Ӱ��,��Ӱ�췿���ͷ����ʾ��
			//��������ѡ�ڵ��¡һ�ݣ�����ȥ���е�ƽ��ͼ�ڵ㣬Ȼ���ٴ��ݽ�ȥ���������б�
			if(node.getUserObject() instanceof EffectImageAndPlanisphereInfo) {
				//��ʾƽ��ͼ
				//Update by zhiyuan_tang 2010/10/26 ���ӵĽڵ��PlanisphereInfo�����EffectImageAndPlanisphereInfo
				EffectImageAndPlanisphereInfo info = (EffectImageAndPlanisphereInfo)node.getUserObject();
//				PlanisphereInfo phInfo = (PlanisphereInfo)node.getUserObject();
				PlanisphereInfo phInfo = info.getPlanisphereInfo();
				if (phInfo != null) {
					
					if(phInfo.getPtype().equals(PlanisphereTypeEnum.PicBuildPlane)) {
						this.kDSplitPane1.setDividerLocation(800);
						this.kDScrollPane1.setVisible(true);
					}else {
						this.kDSplitPane1.setDividerLocation(this.kDSplitPane1.getWidth());
						this.kDScrollPane1.setVisible(false);
					}
					SHEHelper.showPlanisphereTable(this.tblMain,phInfo,setting);
					
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("planisphere.id",phInfo.getId().toString()));
					view.setFilter(filter);
					view.getSelector().add(new SelectorItemInfo("*"));
					if(setting.getSysType().equals(MoneySysTypeEnum.SalehouseSys)) {
						view.getSelector().add(new SelectorItemInfo("roomEntry.sellState"));
						view.getSelector().add(new SelectorItemInfo("roomEntry.isForSHE"));			
					}else if(setting.getSysType().equals(MoneySysTypeEnum.TenancySys)) {
						view.getSelector().add(new SelectorItemInfo("roomEntry.tenancyState"));
						view.getSelector().add(new SelectorItemInfo("roomEntry.isForTen"));
					}
					
					//Object object = ((DefaultMutableTreeNode)node.getParent()).getUserObject();
					PlanisphereElementEntryCollection elementColl = PlanisphereElementEntryFactory.getRemoteInstance().getPlanisphereElementEntryCollection(view);
					this.tblTenStat.removeRows();
					Map buildProMap = new HashMap();
					String[] roomIDSet = new String[100];
					String[] buildIDSet = new String[100];
					String[] projectIDSet = new String[100];
					
					FDCSQLBuilder builder = new FDCSQLBuilder();
					this.setBeforeBuilder(builder);
					
					FDCSQLBuilder builder2 = new FDCSQLBuilder();
					this.setBeforeTenState(builder2);
					//this.setBuilder(builder2, object);
					
					FDCSQLBuilder builder3 = new FDCSQLBuilder();
					this.setBeforeRecAmountBuilder(builder3);
					//this.setReceiveBuilder(builder3, object);
					
					FDCSQLBuilder builder4 = new FDCSQLBuilder();
					this.setBeforeAppAmount(builder4);
					//¥��ƽ��ͼ
					if(phInfo.getPtype().equals(PlanisphereTypeEnum.PicBuildPlane)) {
						for(int i=0;i<elementColl.size();i++) {
							PlanisphereElementEntryInfo phElementInfo = elementColl.get(i);
							if(phElementInfo.getType().equals(PlanisphereElementEnum.room))	{  //��������
								RoomInfo roomInfo = phElementInfo.getRoomEntry();
								roomIDSet[i] = roomInfo.getId().toString();
								//roomIDSet.add(roomInfo.getId().toString());
							}
						}				
						builder.appendSql(" where room.fisforten=1 and ");
						builder.appendParam("room.fid", roomIDSet);
						builder.appendSql(" group by buildPro.fid,buildPro.fname_l2");
						this.getRow(buildProMap,builder);
						
						builder2.appendSql(" where room.fisforten=1 and ");
						builder2.appendParam("room.fid", roomIDSet);
						builder2.appendSql(" group by buildPro.fid,room.ftenancyState,buildPro.fname_l2");
						this.getRowSet(buildProMap,builder2);
						
						builder3.appendSql(" where room.fisforten=1 and ");
						builder3.appendParam("room.fid", roomIDSet);
						this.appendFilterSql(builder3, "cas.FBizDate");
						builder3.appendSql(" group by buildPro.fid");			
						this.getRecAmount(buildProMap,builder3);
						
						builder4.appendSql(" where room.fisforten=1 and ");
						builder4.appendParam("room.fid", roomIDSet);
						builder4.appendSql(" and (room.FTenancyState='NewTenancy' or room.FTenancyState='ContinueTenancy' or room.FTenancyState='EnlargeTenancy')");
						builder4.appendSql(" group by buildPro.fid,tenBill.fid");
						this.getAppAmount(buildProMap,builder4);
					}else if(phInfo.getPtype().equals(PlanisphereTypeEnum.PicBuildDist)) {  /*  ¥���ֲ�ͼ */
						for(int i=0;i<elementColl.size();i++) {
							PlanisphereElementEntryInfo phElementInfo = elementColl.get(i);
							if(phElementInfo.getType().equals(PlanisphereElementEnum.building)) {
								BuildingInfo buildInfo = phElementInfo.getBuildingEntry();
								buildIDSet[i] = buildInfo.getId().toString();
								//buildIDSet.add(buildInfo.getId().toString());
							}
						}			
						builder.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");				
						builder.appendSql(" where room.fisforten=1 and ");
						builder.appendParam("build.fid", buildIDSet);
						builder.appendSql(" group by buildPro.fid,buildPro.fname_l2");
						this.getRow(buildProMap,builder);
						
						builder2.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
						builder2.appendSql(" where room.fisforten=1 and ");
						builder2.appendParam("build.fid", buildIDSet);
						builder2.appendSql(" group by buildPro.fid,room.ftenancyState,buildPro.fname_l2");
						this.getRowSet(buildProMap,builder2);
						
						builder3.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
						builder3.appendSql(" where room.fisforten=1 and ");
						builder3.appendParam("build.fid", buildIDSet);
						this.appendFilterSql(builder3, "cas.FBizDate");
						builder3.appendSql(" group by buildPro.fid");
						this.getRecAmount(buildProMap,builder3);
						
						builder4.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
						builder4.appendSql(" where room.fisforten=1 and ");
						builder4.appendParam("build.fid", buildIDSet);
						builder4.appendSql(" and (room.FTenancyState='NewTenancy' or room.FTenancyState='ContinueTenancy' or room.FTenancyState='EnlargeTenancy')");
						builder4.appendSql(" group by buildPro.fid,tenBill.fid");
						this.getAppAmount(buildProMap,builder4);
					}else if(phInfo.getPtype().equals(PlanisphereTypeEnum.PicSellProject)) {   /* ��Ŀͼ */
						for(int i=0;i<elementColl.size();i++) {
							PlanisphereElementEntryInfo phElementInfo = elementColl.get(i);
							if(phElementInfo.getType().equals(PlanisphereElementEnum.sellProject)) {
								SellProjectInfo proInfo = phElementInfo.getSellProjectEntry();
								projectIDSet[i] = proInfo.getId().toString();
								//projectIDSet.add(proInfo.getId().toString());
							}
						}
						builder.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
						builder.appendSql(" left join t_she_sellproject project on build.fsellprojectid = project.fid ");
						builder.appendSql(" where room.fisforten=1 and ");
						builder.appendParam("project.fid", projectIDSet);
						builder.appendSql(" group by buildPro.fid,buildPro.fname_l2");
						this.getRow(buildProMap,builder);
						
						builder2.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
						builder2.appendSql(" left join t_she_sellproject project on build.fsellprojectid = project.fid ");
						builder2.appendSql(" where room.fisforten=1 and ");
						builder2.appendParam("project.fid", projectIDSet);
						builder2.appendSql(" group by buildPro.fid,room.ftenancyState,buildPro.fname_l2");
						this.getRowSet(buildProMap,builder2);
						
						builder3.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
						builder3.appendSql(" left join t_she_sellproject project on build.fsellprojectid = project.fid ");
						builder3.appendSql(" where room.fisforten=1 and ");
						builder3.appendParam("project.fid", projectIDSet);
						this.appendFilterSql(builder3, "cas.FBizDate");
						builder3.appendSql(" group by buildPro.fid");
						this.getRecAmount(buildProMap,builder3);
						
						builder4.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
						builder4.appendSql(" left join t_she_sellproject project on build.fsellprojectid = project.fid ");
						builder4.appendSql(" where room.fisforten=1 and ");
						builder4.appendParam("project.fid", projectIDSet);
						builder4.appendSql(" and (room.FTenancyState='NewTenancy' or room.FTenancyState='ContinueTenancy' or room.FTenancyState='EnlargeTenancy')");
						builder4.appendSql(" group by buildPro.fid,tenBill.fid");
						this.getAppAmount(buildProMap,builder4);
					}
				} else {
					this.kDSplitPane1.setDividerLocation(800);
					this.kDScrollPane1.setVisible(true);
					DefaultKingdeeTreeNode newNode = (DefaultKingdeeTreeNode)node.clone();
					CommerceHelper.cloneTree(newNode,node);
					newNode.setParent((DefaultKingdeeTreeNode)node.getParent());  //���ýӿ�ʱ���ѯ�丸�ڵ�Ķ���
					CommerceHelper.removePlanisphereNode(newNode);
					SHEHelper.fillRoomTableByNode(this.tblMain,newNode, MoneySysTypeEnum.TenancySys, null,setting);
					Object object = node.getUserObject();
					this.tblTenStat.removeRows();
					Map buildProMap = new HashMap();
					this.getRow(buildProMap, this.getAllSql(object));
					this.getRowSet(buildProMap,this.getTenStateSql(object));
					this.getRecAmount(buildProMap,this.getRecAmountSql(object));
					this.getAppAmount(buildProMap,this.getTenancyIDSet(object));
				}
			}else{
				this.kDSplitPane1.setDividerLocation(800);
				this.kDScrollPane1.setVisible(true);
				DefaultKingdeeTreeNode newNode = (DefaultKingdeeTreeNode)node.clone();
				CommerceHelper.cloneTree(newNode,node);
				newNode.setParent((DefaultKingdeeTreeNode)node.getParent());  //���ýӿ�ʱ���ѯ�丸�ڵ�Ķ���
				CommerceHelper.removePlanisphereNode(newNode);
				SHEHelper.fillRoomTableByNode(this.tblMain,newNode, MoneySysTypeEnum.TenancySys, null,setting);
				Object object = node.getUserObject();
//				this.tblTenStat.removeRows();
//				Map buildProMap = new HashMap();
//				this.getRow(buildProMap, this.getAllSql(object));
//				this.getRowSet(buildProMap,this.getTenStateSql(object));
//				this.getRecAmount(buildProMap,this.getRecAmountSql(object));
//				this.getAppAmount(buildProMap,this.getTenancyIDSet(object));
				
				if(object instanceof BuildingInfo)
				{
					BuildingInfo building = (BuildingInfo)object;
					FDCSQLBuilder builder = new FDCSQLBuilder();
					builder.appendSql("select sp.fname_l2 sellProject,head.fname_l2 building,fseq floor,farea area from t_she_buildingAreaEntry entry left join t_she_building head on head.fid=entry.fheadId left join t_she_sellProject sp on sp.fid=head.fsellProjectid where head.fid='"+building.getId().toString()+"' order by entry.fseq");
					IRowSet rs=builder.executeQuery();
					try {
						this.tblTenStat.setVisible(true);
						this.tblTenStat.removeRows();
						while(rs.next()){
							IRow row=tblTenStat.addRow();
							int floor=rs.getInt("floor");
							row.getCell("sellProject").setValue(rs.getString("sellProject"));
							row.getCell("building").setValue(rs.getString("building"));
							row.getCell("floor").setValue(Integer.valueOf(floor));
							row.getCell("totalArea").setValue(rs.getBigDecimal("area"));
							
							FDCSQLBuilder builder1 = new FDCSQLBuilder();
							builder1.appendSql("select sum(ftenancyArea) alreadyArea from t_she_room where ftenancyState in('NewTenancy','ContinueTenancy','EnlargeTenancy') and fbuildingid='"+building.getId().toString()+"' and ffloor="+floor);
							IRowSet rs1=builder1.executeQuery();
							while(rs1.next()){
								row.getCell("alreadyArea").setValue(rs1.getBigDecimal("alreadyArea"));
								BigDecimal ten=FDCHelper.multiply(FDCHelper.divide(row.getCell("alreadyArea").getValue(), row.getCell("totalArea").getValue(), 4, BigDecimal.ROUND_HALF_UP), new BigDecimal(100));
								row.getCell("ten").setValue(ten);
							}
							builder1 = new FDCSQLBuilder();
							builder1.appendSql("select sum(ftenancyArea) unTenArea from t_she_room where ftenancyState in('UNTenancy') and fbuildingid='"+building.getId().toString()+"' and ffloor="+floor);
							rs1=builder1.executeQuery();
							while(rs1.next()){
								row.getCell("unTenArea").setValue(rs1.getBigDecimal("unTenArea"));
							}
							builder1 = new FDCSQLBuilder();
							builder1.appendSql("select sum(ftenancyArea) waitArea from t_she_room where ftenancyState in('WaitTenancy') and fbuildingid='"+building.getId().toString()+"' and ffloor="+floor);
							rs1=builder1.executeQuery();
							while(rs1.next()){
								row.getCell("waitArea").setValue(rs1.getBigDecimal("waitArea"));
							}
							tblTenStat.getMergeManager().mergeBlock(0, 0, floor-1, 0);
							tblTenStat.getMergeManager().mergeBlock(0, 1, floor-1, 1);
						}
						if(tblTenStat.getRowCount()>0){
							spnlTenancy.setDividerLocation(400);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}else{
					tblTenStat.setVisible(false);
					
				}
			}
			//RoomStateColorUI.insertUIToScrollPanel(this.kDScrollPane1);
		}
		//��ѡ������ͼʱ
		if(this.solidSideRadioBtn.isSelected()){
			this.kDPanel1.remove(solidSideImagePanel);
			//ѡ����֯ʱ
			if(node.getUserObject() instanceof OrgStructureInfo){
				solidSideImagePanel.removeAll();
			}
			//ѡ�񹤳���Ŀʱ
			if(node.getUserObject() instanceof SellProjectInfo){
				solidSideImagePanel.removeAll();	
			}
			//ѡ�����ʱ
			if(node.getUserObject() instanceof SubareaInfo){
				solidSideImagePanel.removeAll();
			}
			//ѡ��¥��ʱ
			if(node.getUserObject() instanceof BuildingInfo){
				BuildingInfo info=(BuildingInfo)node.getUserObject();
				BuildingUnitCollection coll=getBuildingUnits(info);
				if(coll!=null && coll.size()>0){//¥�� �е�Ԫʱ
					KDPanel building=new KDPanel();
					int x=0;
					for(Iterator it=coll.iterator();it.hasNext();){
						BuildingUnitInfo buildingUnitInfo=(BuildingUnitInfo)it.next();
						KDPanel unit=loadBuildingUnit(buildingUnitInfo,selectedZoom);					
						unit.setBounds(x, 0, unit.getWidth(),unit.getHeight());
						building.add(unit);
						x+=(unit.getWidth()+10);
					}
					solidSideImagePanel=null;
					solidSideImagePanel=new KDScrollPane(building);
				}
				else{//¥�� û�����ֵ�Ԫʱ
					EntityViewInfo view=new EntityViewInfo();
					view.getSelector().add("id");
					view.getSelector().add("buildingArea");
					view.getSelector().add("floorHeight");
					view.getSelector().add("buildingFloor.floor");
					view.getSelector().add("buildingFloor.floorAlias");	
					view.getSelector().add("building.floorCount");
					view.getSelector().add("building.subFloorCount");
					view.getSelector().add("serialNumber");
					view.getSelector().add("buildUnit.name");
					view.getSelector().add("number");
					view.getSelector().add("tenancyState");
					FilterInfo filter=new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("building.id",info.getId()));
					view.setFilter(filter);
					RoomCollection roomColl=RoomFactory.getRemoteInstance().getRoomCollection(view);
					int floor=0;
					if(roomColl.size()>0){
						floor=roomColl.get(0).getBuilding().getFloorCount()+Math.abs(roomColl.get(0).getBuilding().getSubFloorCount());
					}else{
						MsgBox.showWarning("��ѡ��¥��û��¼�뷿�䣡");
						solidSideImagePanel=null;
						solidSideImagePanel=new KDScrollPane();
						this.kDPanel1.add(solidSideImagePanel,BorderLayout.CENTER);
						kDPanel1.repaint();
						kDPanel1.validate();
						kDPanel1.revalidate();
						abort();
					}
					
					List list= getSolidSideInfoList(roomColl);
					TenancyDisPlaySetting tdps=new TenancyDisPlaySetting();
					ssi=new SolidSideImage(list,floor,tdps,this);
					if(selectedZoom!=null){
						ssi.setScale(selectedZoom);
					}
					solidSideImagePanel = null;
					solidSideImagePanel=new KDScrollPane(ssi.getPanel());
				}
			}
			//ѡ��Ԫʱ
			if(node.getUserObject() instanceof BuildingUnitInfo){
				BuildingUnitInfo info=(BuildingUnitInfo)node.getUserObject();
				solidSideImagePanel=null;
				solidSideImagePanel=new KDScrollPane(loadBuildingUnit(info,selectedZoom));
			}
			//ѡ��¥��ʱ
//			if(node.getUserObject() instanceof PlanisphereInfo){
			if(node.getUserObject() instanceof EffectImageAndPlanisphereInfo){
				solidSideImagePanel.removeAll();
			}
			this.kDPanel1.add(solidSideImagePanel,BorderLayout.CENTER);
			kDPanel1.repaint();
			kDPanel1.validate();
			kDPanel1.revalidate();
		}
		if(this.effectRadioBtn.isSelected()){//ѡ��Ч��ͼʱ
			this.kDPanel1.remove(effectImagePanel);
			//ѡ����֯ʱ  ���ص�ǰ��֯����Ŀ�ֲ�ͼ
			if(node.getUserObject() instanceof OrgStructureInfo){
				OrgStructureInfo orgStructInfo = (OrgStructureInfo)node.getUserObject();
	    		if(orgStructInfo.getUnit()!=null) {
		    		FullOrgUnitInfo info=orgStructInfo.getUnit();
		    		FilterInfo filter=new FilterInfo();
		    		filter.getFilterItems().add(new FilterItemInfo("type",EffectImageEnum.PIC_SELLPROJECT_VALUE));
		    		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",info.getId().toString()));
		    		setEffectImagePanelView(getEffectImageInfo(filter));
	    		}
			}
			//ѡ�񹤳���Ŀʱ ����¥����λͼ
			if(node.getUserObject() instanceof SellProjectInfo){
				SellProjectInfo prj=(SellProjectInfo)node.getUserObject();
	    		FilterInfo filter=new FilterInfo();
	    		filter.getFilterItems().add(new FilterItemInfo("type",EffectImageEnum.PIC_BUILDING_VALUE));
	    		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",SHEManageHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node, "SellProject").keySet()),CompareType.INNER));
	    		setEffectImagePanelView(getEffectImageInfo(filter));
			}
			//ѡ�����ʱ
			if(node.getUserObject() instanceof SubareaInfo){
				effectImagePanel.setViewportView(null);
			}
			//ѡ��¥��ʱ 
			if(node.getUserObject() instanceof BuildingInfo){
				effectImagePanel.setViewportView(null);
			}
			//ѡ��Ԫʱ
			if(node.getUserObject() instanceof BuildingUnitInfo){
				effectImagePanel.setViewportView(null);
			}
			//ѡ��¥��ʱ���� ¥��Ч��ͼ   ��Ϊ�����һ��¥�� ��������¥��Ч��ͼ ���� ���ֵ�Ԫ��¥��Ч��ͼ
			//��ʱ���ṹ Ҳ��Ϊ  ¥������ ��¥�� �͵�Ԫ Ȼ��Ԫ���滹��¥�㡣�����������ֶ��˸��жϡ�������
			//Update by zhiyuan_tang 2010/10/26 ���ӵĽڵ��PlanisphereInfo�����EffectImageAndPlanisphereInfo
			if(node.getUserObject() instanceof EffectImageAndPlanisphereInfo){
				EffectImageAndPlanisphereInfo info = (EffectImageAndPlanisphereInfo)node.getUserObject();
				if (info.getEffectImageInfo() == null) {
					effectImagePanel.setViewportView(null);
				} else {
					EffectImageInfo effectInfo = info.getEffectImageInfo();
					FilterInfo filter=new FilterInfo();
		    		filter.getFilterItems().add(new FilterItemInfo("id", effectInfo.getId()));
					setEffectImagePanelView(getEffectImageInfo(filter));
				}
				
//				if(getSelectBF((PlanisphereInfo)node.getUserObject())==null){
//					return;
//				}
//				BuildingFloorEntryInfo bf=getSelectBF((PlanisphereInfo)node.getUserObject());
//				FilterInfo filter=new FilterInfo();
//	    		filter.getFilterItems().add(new FilterItemInfo("type",EffectImageEnum.PIC_BUILDINGFLOOR_VALUE));
//	    		filter.getFilterItems().add(new FilterItemInfo("buildingFloor.id",bf.getId().toString()));
//				DefaultKingdeeTreeNode parent = (DefaultKingdeeTreeNode)node.getParent();
//				//���ڵ��ǵ�Ԫʱ�������ֵ�Ԫ��¥��Ч��ͼ
//				if(parent.getUserObject()!=null && parent.getUserObject() instanceof BuildingUnitInfo){
//					filter.getFilterItems().add(new FilterItemInfo("isShowUnit",Boolean.TRUE));
//				}
//				//���ڵ���¥��ʱ���ز����ֵ�Ԫ��¥��Ч��ͼ
//				if(parent.getUserObject()!=null && parent.getUserObject() instanceof BuildingInfo){
//					filter.getFilterItems().add(new FilterItemInfo("isShowUnit",Boolean.FALSE));
//				}
//				setEffectImagePanelView(getEffectImageInfo(filter));
			}
    		this.kDPanel1.add(effectImagePanel,BorderLayout.CENTER);
    		kDPanel1.repaint();
			kDPanel1.validate();
			kDPanel1.revalidate();
		}
	}
	
	private BuildingFloorEntryInfo getSelectBF(PlanisphereInfo plInfo){
		EntityViewInfo view=new EntityViewInfo();
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("id");
		sic.add("floor");
		sic.add("building.id");
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("building.id",plInfo.getBuilding().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("floor",new Integer(plInfo.getFloor())));
		view.setSelector(sic);
		view.setFilter(filter);
		try {
			BuildingFloorEntryCollection coll=BuildingFloorEntryFactory.getRemoteInstance().getBuildingFloorEntryCollection(view);
			if(coll!=null && coll.size()>0){
				return coll.get(0);
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *  ���ݴ����filter ��ѯ��Ч��ͼ����
	 * @param filter
	 * @return
	 */
	private EffectImageInfo getEffectImageInfo(FilterInfo filter){
		EntityViewInfo view =new EntityViewInfo();
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("id");
		sic.add("effectImage");
		sic.add("elementEntry.id");
		sic.add("elementEntry.hotspot1");
		sic.add("elementEntry.hotspot2");
		sic.add("elementEntry.type");
		sic.add("elementEntry.elementID");
		sic.add("elementEntry.elementName");
		view.setSelector(sic);
		view.setFilter(filter);
		try {
			EffectImageCollection coll=EffectImageFactory.getRemoteInstance().getEffectImageCollection(view);
			return coll.get(0);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ���ݴ����Ч��ͼ��������Ч��ͼ
	 * @param info
	 */
	private void setEffectImagePanelView(EffectImageInfo info){
		if (info !=null && info.getEffectImage() != null) {
			try {
				File file = File.createTempFile("tmp1111", "a.jpg");
				ByteInputStream stream = new ByteInputStream(info.getEffectImage(), info.getEffectImage().length);
				FileOutputStream out = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int size = -1;
				while((size = stream.read(buffer)) != -1){
					out.write(buffer, 0, size);
				}
				out.flush();
				stream.close();
				out.close();
				pnlRoomPic.setImageFile(file);
				effectImagePanel.setViewportView(pnlRoomPic);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			//Add by zhiyuan_tang 2010/10/21 ��û�е���ͼƬʱ����ʾ��
			effectImagePanel.setViewportView(null);
		}
	}
	
	/**
	 *  ��ȡ¥���ĵ�Ԫ����
	 */
	private BuildingUnitCollection getBuildingUnits(BuildingInfo info) throws BOSException{
		EntityViewInfo view=new EntityViewInfo();
		view.getSelector().add("id");
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("building",info.getId().toString()));
		view.setFilter(filter);
		return BuildingUnitFactory.getRemoteInstance().getBuildingUnitCollection(view);
	}
	
	/**
	 *  ��ȡ����ͼ��ʾ�Ļ�����Ԫ���
	 */
	private KDPanel loadBuildingUnit(BuildingUnitInfo info,BigDecimal zoom) throws BOSException{
		EntityViewInfo view=new EntityViewInfo();
		view.getSelector().add("id");
		//���佨�����    ��ʾ���
		view.getSelector().add("buildingArea");
		//���   ��ʾ�߶�
		view.getSelector().add("floorHeight");
		//¥��  
		view.getSelector().add("buildingFloor.floor");
		//¥����ʾ����
		view.getSelector().add("buildingFloor.floorAlias");	
		//����¥����   ���ڼ���¥���ж��ٲ�
		view.getSelector().add("building.floorCount");
		//����¥����   ���ڼ���¥���ж��ٲ�
		view.getSelector().add("building.subFloorCount");
		//�������    ���������� 
		view.getSelector().add("serialNumber");
		//��Ԫ����
		view.getSelector().add("buildUnit.name");
		//������   ��ʾ��
		view.getSelector().add("number");
		//��������״̬   ����������ɫ
		view.getSelector().add("tenancyState");
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("buildUnit",info.getId()));
		view.setFilter(filter);
		RoomCollection coll=RoomFactory.getRemoteInstance().getRoomCollection(view);
		int floor=0;
		if(coll.size()>0){
			floor=coll.get(0).getBuilding().getFloorCount()+Math.abs(coll.get(0).getBuilding().getSubFloorCount());
		}else{
			MsgBox.showWarning("��ѡ��Ԫû��¼�뷿�䣡");
			abort();
		}
		
		List list= getSolidSideInfoList(coll);
		TenancyDisPlaySetting tdps=new TenancyDisPlaySetting();
		ssi=new SolidSideImage(list,floor,tdps,this);
		if(zoom!=null){
			ssi.setScale(zoom);
		}
		return ssi.getPanel();
	}
	/**
	 *  ��������ͼʱ �Ѽ���ת���� ���Ϲ�������ͼ�Ľṹ
	 * @param coll
	 * @return
	 */
	private List getSolidSideInfoList(RoomCollection coll){
		List list=new ArrayList();
		if(coll.size()>0){
			for(int i=0;i<coll.size();i++){
				RoomInfo room=(RoomInfo)coll.get(i);
				if(room!=null && room.getId()!=null){
					SolidSideInfo info=new SolidSideInfo();
					info.setBizObj(room);
					info.setId(room.getId().toString());
					info.setExtent(room.getBuildingArea());
					info.setColIndex(room.getSerialNumber());
					info.setRowhigh(room.getFloorHeight());
					info.setRowIndex(room.getBuildingFloor().getFloor());
					info.setRowName(room.getBuildingFloor().getFloorAlias());
					if(room.getBuildUnit()!=null && room.getBuildUnit().getName()!=null){
						info.setName(room.getBuildUnit().getName());
					}else{
						info.setName("һ��Ԫ");
					}
					if(room.getTenancyState()==null){
						info.setState(null);
					}else{
						info.setState(room.getTenancyState().getValue());
					}
					if(room.getBuildingArea()!=null){
						info.setText("<html>"+room.getNumber()+"<br><font align='right'>"+room.getBuildingArea()+" m2</font></html>");
					}
					else{
						info.setText(room.getNumber());
					}
					list.add(info);
				}
			}
		}
		return list;
	}
	
	//����Ӧ�����
	protected void getAppAmount(Map buildProMap,FDCSQLBuilder builder)
	{
		Set tenancySet = new HashSet();
//		Map buildingProMap = new HashMap();
		IRowSet tenBillSet = null;
		//�治�ظ��Ľ�������ID,����������Ƚ�  xin_wang 2010.11.17
		Set buildingProIDSet = new HashSet();
		try {
			tenBillSet = builder.executeQuery();
			while(tenBillSet.next())
			{
				String buildingProID = tenBillSet.getString("buildingProID");
				String tenBillID = tenBillSet.getString("tenBillID");
				tenancySet.add(tenBillID);
//				buildingProMap.put(tenBillID, buildingProID);
				buildingProIDSet.add(buildingProID);
			}
		}catch(Exception e)
		{
			this.handleException(e);
		}
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("*");
		sel.add("rentFrees.*");
		sel.add("tenancyRoomList.*");
		sel.add("tenancyRoomList.room.*");
		sel.add("tenancyRoomList.room.buildingProperty.*");		
		sel.add("tenancyRoomList.dealAmounts.*");	
		sel.add("tenancyRoomList.dealAmounts.moneyDefine.*");	
		filterInfo.getFilterItems().add(new FilterItemInfo("id",tenancySet,CompareType.INCLUDE));
		view.setSelector(sel);
		view.setFilter(filterInfo);
		Date beginDate = FDCDateHelper.getFirstDayOfCurMonth();
		Date endDate = FDCDateHelper.getLastDayOfCurMonth();
		DealAmountEntryCollection newDealAmount = new DealAmountEntryCollection();
		BigDecimal totalRentAmount = FDCHelper.ZERO;
		Date[] date1 =null;
		Date[] date2  =null;
		try {
			TenancyBillCollection tenBillColl = TenancyBillFactory.getRemoteInstance().getTenancyBillCollection(view);
			for(Iterator itor =buildingProIDSet.iterator(); itor.hasNext(); ){
				String buildingId = (String)itor.next();
				IRow row = (IRow)buildProMap.get(buildingId);
				for(int i=0;i<tenBillColl.size();i++){
					TenancyBillInfo tenBill = tenBillColl.get(i);
					Date tenBillStartDate = tenBill.getStartDate();
					date1 = new Date[]{beginDate,endDate};
					date2 = new Date[]{tenBillStartDate,tenBillStartDate};
					//�����¼
					RentFreeEntryCollection rentFreeColl =  tenBill.getRentFrees();
					//���޷����¼
					TenancyRoomEntryCollection tenRoomColl = tenBill.getTenancyRoomList();
					for(int j=0;j<tenRoomColl.size();j++){
						TenancyRoomEntryInfo tenRoomEntryInfo = tenRoomColl.get(j);
						if(tenRoomEntryInfo.getRoom().getBuildingProperty().getId()!=null){
							//�ɽ��۸��¼
							DealAmountEntryCollection dealAmountColl = tenRoomEntryInfo.getDealAmounts();
							if(buildingId.equals(tenRoomEntryInfo.getRoom().getBuildingProperty().getId().toString())){
								//�����ͬ����ʼʱ���ٲ�ѯʱ�䷶Χ�����Ǽ���Ӧ�����+Ѻ��+�����Է���
								if(TenancyHelper.getWrapDates(date1,date2)!=null){
									//���
									totalRentAmount = totalRentAmount.add(TenancyHelper.getRentBetweenDate(this.getRentMoneyDefine(),beginDate, endDate, dealAmountColl, rentFreeColl));
									
									for(int z =0; z<dealAmountColl.size();z++){
										DealAmountEntryInfo dealAmounteEntry = dealAmountColl.get(z);
										if(MoneyTypeEnum.DepositAmount.equals(dealAmounteEntry.getMoneyDefine().getMoneyType())){//Ѻ��
											totalRentAmount = totalRentAmount.add(dealAmounteEntry.getAmount());
										}else if(MoneyTypeEnum.PeriodicityAmount.equals(dealAmounteEntry.getMoneyDefine().getMoneyType())){//�����Է���
											totalRentAmount = totalRentAmount.add(TenancyHelper.getRentBetweenDate(dealAmounteEntry.getMoneyDefine(),beginDate, endDate, dealAmountColl, rentFreeColl));
										}
									}
								}else{
									totalRentAmount=totalRentAmount.add(TenancyHelper.getRentBetweenDate(this.getRentMoneyDefine(),beginDate, endDate, newDealAmount, rentFreeColl));
									for(int z =0; z<dealAmountColl.size();z++){
										DealAmountEntryInfo dealAmounteEntry = dealAmountColl.get(z);
										if(MoneyTypeEnum.PeriodicityAmount.equals(dealAmounteEntry.getMoneyDefine().getMoneyType())){//�����Է���
											totalRentAmount = totalRentAmount.add(TenancyHelper.getRentBetweenDate(dealAmounteEntry.getMoneyDefine(),beginDate, endDate, dealAmountColl, rentFreeColl));
										}
									}
								}
							}else{
								break;
							}
						}
					}
					
				}
//				row.getCell("appAmount").setValue(totalRentAmount);
			}
//			for(int i=0;i<tenBillColl.size();i++)
//			{
//				TenancyBillInfo tenBill = tenBillColl.get(i);				
//				String buildingProID = (String)buildingProMap.get(tenBill.getId().toString());
//				IRow row = (IRow)buildProMap.get(buildingProID);
//				Date tenBillStartDate = tenBill.getStartDate();
//				Date[] date1 = new Date[]{beginDate,endDate};
//				Date[] date2 = new Date[]{tenBillStartDate,tenBillStartDate};
//				//�����¼
//				RentFreeEntryCollection rentFreeColl =  tenBill.getRentFrees();
//				//���޷����¼
//				TenancyRoomEntryCollection tenRoomColl = tenBill.getTenancyRoomList();
//				for(int j=0;j<tenRoomColl.size();j++)
//				{
//					TenancyRoomEntryInfo tenRoomEntryInfo = tenRoomColl.get(j);
//					//�ɽ��۸��¼
//					DealAmountEntryCollection dealAmountColl = tenRoomEntryInfo.getDealAmounts();
//					//�����ͬ����ʼʱ�䲻�ڲ�ѯʱ�䷶Χ������ֻ����Ӧ�����
//					if(TenancyHelper.getWrapDates(date1,date2)==null)
//					{
//						totalRentAmount = totalRentAmount.add(TenancyHelper.getRentBetweenDate(this.getRentMoneyDefine(),beginDate, endDate, dealAmountColl, rentFreeColl));
//						row.getCell("appAmount").setValue(totalRentAmount);
//					}else
//					{
//						//�����ͬ����ʼʱ���ٲ�ѯʱ�䷶Χ�����ǳ��˼���Ӧ�����Ӧ�ü���Ѻ��
//						totalRentAmount = totalRentAmount.add(TenancyHelper.getRentBetweenDate(this.getRentMoneyDefine(),beginDate, endDate, dealAmountColl, rentFreeColl));
//						totalDepositAmount = totalDepositAmount.add(tenRoomEntryInfo.getDepositAmount()==null?new BigDecimal(0):tenRoomEntryInfo.getDepositAmount());
//						totalRentAmount = totalRentAmount.add(totalDepositAmount);
//						row.getCell("appAmount").setValue(totalRentAmount);
//					}					
//				}								
//			}
		} catch (BOSException e) {
			this.handleException(e);
		}
		
	}
	
	private MoneyDefineInfo getRentMoneyDefine() throws BOSException {
		if(rentMoneyName == null){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.RentAmount));
			filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.TenancySys));
			view.setFilter(filter);
			MoneyDefineCollection moneyNames = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);
			if (!moneyNames.isEmpty()) {
				rentMoneyName = moneyNames.get(0);
			}
		}
		if (rentMoneyName == null) {
			MsgBox.showInfo(this, "���ȶ���������͵Ŀ������ͣ�");
			abort();
		}
		return rentMoneyName;
	}
	protected void setBeforeAppAmount(FDCSQLBuilder builder)
	{
		builder.appendSql("select buildPro.fid buildingProID,tenBill.fid tenBillID from t_she_room room ");
		builder.appendSql("left join t_she_buildingProperty buildPro on room.fbuildingPropertyid = buildPro.fid ");
		builder.appendSql("left join t_ten_tenancybill tenBill on room.FLastTenancyID = tenBill.fid");
	}
	
	protected FDCSQLBuilder getTenancyIDSet(Object object)
	{
		FDCSQLBuilder builder = new FDCSQLBuilder();
		this.setBeforeAppAmount(builder);
		this.setTenancyBuilder(builder, object);
		builder.appendSql(" group by buildPro.fid,tenBill.fid");
		return builder;
	}
	
	protected void setTenancyBuilder(FDCSQLBuilder builder,Object object)
	{
		if(object instanceof SellProjectInfo)
		{
			SellProjectInfo project = (SellProjectInfo)object;
			builder.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
			builder.appendSql(" left join t_she_sellproject project on build.fsellprojectid = project.fid ");
			builder.appendSql(" where room.fisforten=1 and project.fid ='"+project.getId().toString()+"'");
			builder.appendSql(" and (room.FTenancyState='NewTenancy' or room.FTenancyState='ContinueTenancy' or room.FTenancyState='EnlargeTenancy')");
		}else if(object instanceof BuildingInfo)
		{
			BuildingInfo building = (BuildingInfo)object;
			builder.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
			builder.appendSql("  where room.fisforten=1 and build.fid ='"+building.getId().toString()+"'");
			builder.appendSql("  and (room.FTenancyState='NewTenancy' or room.FTenancyState='ContinueTenancy' or room.FTenancyState='EnlargeTenancy')");
		}else if(object instanceof SubareaInfo)
		{
			SubareaInfo subarea = (SubareaInfo)object;
			builder.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
			builder.appendSql(" left join t_she_subarea subarea on build.FSubareaID = subarea.fid ");
			builder.appendSql("  where room.fisforten=1 and subarea.fid ='"+subarea.getId().toString()+"'");
			builder.appendSql("  and (room.FTenancyState='NewTenancy' or room.FTenancyState='ContinueTenancy' or room.FTenancyState='EnlargeTenancy')");
		}else if(object instanceof BuildingUnitInfo)
		{
			BuildingUnitInfo buildingUnit = (BuildingUnitInfo)object;
			builder.appendSql(" left join t_she_buildingUnit buildUnit on room.FBuildUnitID = buildUnit.fid ");
			builder.appendSql(" where room.fisforten=1 and buildUnit.fid ='"+buildingUnit.getId().toString()+"'");
			builder.appendSql("  and (room.FTenancyState='NewTenancy' or room.FTenancyState='ContinueTenancy' or room.FTenancyState='EnlargeTenancy')");
		}else
		{
			builder.appendSql(" where room.fid is null");
		}
	}

	//��ѯ���������������SQL
	protected FDCSQLBuilder getAllSql(Object object)
	{
		FDCSQLBuilder builder = new FDCSQLBuilder(); 
		this.setBeforeBuilder(builder);
		this.setBuilder(builder, object);
		builder.appendSql(" group by buildPro.fid,buildPro.fname_l2");
		return builder;
	}


	protected void setBeforeBuilder(FDCSQLBuilder builder)
	{
		builder.appendSql("select buildPro.fid buildingProID,buildPro.fname_l2 name,");
		builder.appendSql(" sum(FbuildingArea) buildingArea ,count(room.fid) count from t_she_room room");
		builder.appendSql(" left join t_she_buildingProperty buildPro on room.fbuildingPropertyid = buildPro.fid ");
	}

	//�����ڸ�������������������������
	protected void setBuilder(FDCSQLBuilder builder,Object object)
	{
		if(object instanceof SellProjectInfo)
		{
			SellProjectInfo project = (SellProjectInfo)object;
			builder.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
			builder.appendSql(" left join t_she_sellproject project on build.fsellprojectid = project.fid ");
			builder.appendSql("  where room.fisforten=1 and project.fid ='"+project.getId().toString()+"'");
		}else if(object instanceof BuildingInfo)
		{
			BuildingInfo building = (BuildingInfo)object;
			builder.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
			builder.appendSql("  where room.fisforten=1 and build.fid ='"+building.getId().toString()+"'");
		}else if(object instanceof SubareaInfo)
		{
			SubareaInfo subarea = (SubareaInfo)object;
			builder.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
			builder.appendSql(" left join t_she_subarea subarea on build.FSubareaID = subarea.fid ");
			builder.appendSql("  where room.fisforten=1 and subarea.fid ='"+subarea.getId().toString()+"'");
		}else if(object instanceof BuildingUnitInfo)
		{
			BuildingUnitInfo buildingUnit = (BuildingUnitInfo)object;
			builder.appendSql(" left join t_she_buildingUnit buildUnit on room.FBuildUnitID = buildUnit.fid ");
			builder.appendSql(" where room.fisforten=1 and buildUnit.fid ='"+buildingUnit.getId().toString()+"'");
		}else
		{
			builder.appendSql(" where room.fid is null");
		}
	}

	//�����ڸ�����������տ��ܶ������
	protected void setReceiveBuilder(FDCSQLBuilder builder,Object object)
	{
		if(object instanceof SellProjectInfo)
		{			
			SellProjectInfo project = (SellProjectInfo)object;
			builder.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
			builder.appendSql(" left join t_she_sellproject project on build.fsellprojectid = project.fid ");			
			builder.appendSql(" where (money.FMoneyType ='RentAmount' or money.FMoneyType ='DepositAmount' or money.FMoneyType ='PeriodicityAmount' and money.FSysType ='TenancySys')");
			builder.appendSql(" and project.fid ='"+project.getId().toString()+"'");
		}else if(object instanceof BuildingInfo)
		{
			BuildingInfo building = (BuildingInfo)object;
			builder.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
			builder.appendSql(" where (money.FMoneyType ='RentAmount' or money.FMoneyType ='DepositAmount' or money.FMoneyType ='PeriodicityAmount'  and money.FSysType ='TenancySys')");
			builder.appendSql(" and build.fid ='"+building.getId().toString()+"'");
		}else if(object instanceof SubareaInfo)
		{
			SubareaInfo subarea = (SubareaInfo)object;
			builder.appendSql(" left join t_she_building build on room.fbuildingid = build.fid ");
			builder.appendSql(" left join t_she_subarea subarea on build.FSubareaID = subarea.fid ");
			builder.appendSql(" where (money.FMoneyType ='RentAmount' or money.FMoneyType ='DepositAmount' or money.FMoneyType ='PeriodicityAmount'  and money.FSysType ='TenancySys')");
			builder.appendSql(" and subarea.fid ='"+subarea.getId().toString()+"'");
		}else if(object instanceof BuildingUnitInfo)
		{
			BuildingUnitInfo buildingUnit = (BuildingUnitInfo)object;
			builder.appendSql(" left join t_she_buildingUnit buildUnit on room.FBuildUnitID = buildUnit.fid ");
			builder.appendSql(" where (money.FMoneyType ='RentAmount' or money.FMoneyType ='DepositAmount' or money.FMoneyType ='PeriodicityAmount'  and money.FSysType ='TenancySys')");
			builder.appendSql(" and buildUnit.fid ='"+buildingUnit.getId().toString()+"'");
		}else
		{
			builder.appendSql(" where room.fid is null");
		}
	}

	protected void setBeforeTenState(FDCSQLBuilder builder)
	{
		builder.appendSql("select buildPro.fid buildingProID,buildPro.fname_l2 name,room.ftenancyState tenState,");
		builder.appendSql(" sum(FTenancyArea) buildingArea ,count(room.fid) count from t_she_room room");
		builder.appendSql(" left join t_she_buildingProperty buildPro on room.fbuildingPropertyid = buildPro.fid ");
	}

	//ͳ�Ʋ�ͬ����״̬�����������
	protected FDCSQLBuilder getTenStateSql(Object object)
	{
		FDCSQLBuilder builder = new FDCSQLBuilder();
		this.setBeforeTenState(builder);
		this.setBuilder(builder, object);
		builder.appendSql(" group by buildPro.fid,room.ftenancyState,buildPro.fname_l2");
		return builder;
	}

	protected void setBeforeRecAmountBuilder(FDCSQLBuilder builder)
	{
		builder.appendSql("select buildPro.fid buildingProID,sum(fdcEntry.frevAmount) sumRevAmount from T_BDC_FDCReceivingBillEntry fdcEntry ");
//		builder.appendSql(" left join t_cas_receivingbill cas on cas.fid=fdcentry.FReceivingBillID ");
		builder.appendSql(" left join T_BDC_FDCReceivingBill fdc on fdcEntry.fheadid=fdc.fid ");
		builder.appendSql(" left join t_she_room room on fdc.froomid=room.fid  ");
		builder.appendSql(" left join t_she_buildingProperty buildPro on room.fbuildingPropertyid = buildPro.fid ");
		builder.appendSql(" left join t_ten_TenancyBill as tenBill on room.FLastTenancyID= tenBill.fid");
		builder.appendSql(" left join t_she_moneyDefine money on  fdcentry.FMoneyDefineId=money.fid ");	
	}
	
	//�������տ��ܶ��SQL
	protected FDCSQLBuilder getRecAmountSql(Object object)
	{
		FDCSQLBuilder builder = new FDCSQLBuilder();
		this.setBeforeRecAmountBuilder(builder);
		this.setReceiveBuilder(builder, object);
		this.appendFilterSql(builder, "fdc.FBizDate");
		builder.appendSql(" group by buildPro.fid");
		//return builder.getSql();
		return builder;
	}

	
	public void appendFilterSql(FDCSQLBuilder builder, String proName) {
		Date beginDate = FDCDateHelper.getFirstDayOfCurMonth();
		if (beginDate != null) {
			builder.appendSql(" and " + proName + " >= ? ");
			builder.addParam(FDCDateHelper.getSqlDate(beginDate));
		}
		Date endDate = FDCDateHelper.getLastDayOfCurMonth();
		if (endDate != null) {
			builder.appendSql(" and " + proName + " < ? ");
			builder.addParam(FDCDateHelper.getNextDay(endDate));
		}
	}

	//���������
	protected void getRow(Map buildProMap,FDCSQLBuilder builder)	
	{
		IRowSet countSet = null;			
		try {
			countSet =  builder.executeQuery();
			while(countSet.next())
			{
				String buildPro = countSet.getString("buildingProID");					
				String name = countSet.getString("name");
				BigDecimal buildingArea = countSet.getBigDecimal("buildingArea");
				if(buildingArea==null)
				{
					buildingArea = FDCHelper.ZERO;
				}else
				{
					buildingArea = buildingArea.divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
				}
				int totalCount = countSet.getInt("count");
				IRow row = this.tblTenStat.addRow();
				buildProMap.put(buildPro, row);
				row.getCell("tenType").setValue(name);
				row.getCell("allArea").setValue(totalCount+"/"+buildingArea+"m2");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	//�������տ��ܶ�
	protected void getRecAmount(Map buildProMap,FDCSQLBuilder builder)
	{
		Map recAmountMap = new HashMap();
		IRowSet countSet3 = null;
		try {
			countSet3 =  builder.executeQuery();
			while(countSet3.next())
			{
				String buildPro = countSet3.getString("buildingProID");	
				BigDecimal sumRecAmount = countSet3.getBigDecimal("sumRevAmount");
				recAmountMap.put(buildPro, sumRecAmount);

			}

			Set keySet = buildProMap.keySet();
			Iterator iterator = keySet.iterator();
			while(iterator.hasNext()) {
				String buidIdStr = (String)iterator.next();
				IRow thisRow = (IRow)buildProMap.get(buidIdStr);
				BigDecimal sumRecAmount = (BigDecimal)recAmountMap.get(buidIdStr);
				if(sumRecAmount==null)
				{
					sumRecAmount = new BigDecimal(0);
				}
				thisRow.getCell("actAmount").setValue(sumRecAmount);
			}
		}catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	//�����������������ͳ�����
	protected void getRowSet(Map buildProMap,FDCSQLBuilder builder)
	{
		//δ��������,���
		Map UNTenancyMap = new HashMap();
		Map unAreaMap = new HashMap();
		//��������,���
		Map WaitTenancyMap = new HashMap();
		Map waitAreaMap = new HashMap();
		//��������,���
		Map KeepTenancyMap = new HashMap();
		Map keepAreaMap = new HashMap();
		//��������,���
		Map newTenancyMap = new HashMap();
		Map newAreaMap = new HashMap();
		//��������,���
		Map conTenaycyMap = new HashMap();
		Map conAreaMap = new HashMap();
		//��������,���
		Map enlargTenMap = new HashMap();
		Map enlargAreaMap = new HashMap();
		IRowSet countSet2 = null;
		try {
			countSet2 = builder.executeQuery();
			while(countSet2.next())
			{
				String buildPro = countSet2.getString("buildingProID");					
				String tenState = countSet2.getString("tenState");
				BigDecimal buildingArea = countSet2.getBigDecimal("buildingArea");
				if(buildingArea==null)
				{
					buildingArea = FDCHelper.ZERO;
				}
				int totalCount = countSet2.getInt("count");
				if(newTenancyMap.get(buildPro)==null || conTenaycyMap.get(buildPro)==null || enlargTenMap.get(buildPro)==null
						|| UNTenancyMap.get(buildPro)==null || WaitTenancyMap.get(buildPro)==null || KeepTenancyMap.get(buildPro)==null) {
					if(tenState !=null && (TenancyStateEnum.NEWTENANCY_VALUE).equals(tenState))
					{
						newTenancyMap.put(buildPro,new Integer(totalCount));
						newAreaMap.put(buildPro,buildingArea);
					}
					else if(tenState !=null && (TenancyStateEnum.CONTINUETENANCY_VALUE).equals(tenState))
					{
						conTenaycyMap.put(buildPro,new Integer(totalCount));
						conAreaMap.put(buildPro,buildingArea);
					}else if(tenState !=null && (TenancyStateEnum.ENLARGETENANCY_VALUE).equals(tenState))
					{
						enlargTenMap.put(buildPro,new Integer(totalCount));
						enlargAreaMap.put(buildPro,buildingArea);
					}else if(tenState !=null && (TenancyStateEnum.UNTENANCY_VALUE).equals(tenState))
					{
						UNTenancyMap.put(buildPro,new Integer(totalCount));
						unAreaMap.put(buildPro,buildingArea);
					}else if(tenState !=null && (TenancyStateEnum.WAITTENANCY_VALUE).equals(tenState))
					{
						WaitTenancyMap.put(buildPro,new Integer(totalCount));
						waitAreaMap.put(buildPro,buildingArea);
					}else if(tenState !=null && (TenancyStateEnum.KEEPTENANCY_VALUE).equals(tenState))
					{
						KeepTenancyMap.put(buildPro,new Integer(totalCount));
						keepAreaMap.put(buildPro,buildingArea);
					}
				}else
				{
					if(tenState!=null && (TenancyStateEnum.NEWTENANCY_VALUE).equals(tenState))
					{
						int newCount = ((Integer)newTenancyMap.get(buildPro)).intValue();
						newTenancyMap.put(buildPro,new Integer(newCount+totalCount));
						BigDecimal area = (BigDecimal)newAreaMap.get(buildPro);
						newAreaMap.put(buildPro,area.add(buildingArea));
					}else if(tenState !=null && (TenancyStateEnum.CONTINUETENANCY_VALUE).equals(tenState))
					{
						int continueCount = ((Integer)conTenaycyMap.get(buildPro)).intValue();
						conTenaycyMap.put(buildPro,new Integer(continueCount+totalCount));
						BigDecimal area = (BigDecimal)conAreaMap.get(buildPro);
						conAreaMap.put(buildPro,area.add(buildingArea));
					}else if(tenState !=null && (TenancyStateEnum.ENLARGETENANCY_VALUE).equals(tenState))
					{
						int enlargCount = ((Integer)enlargTenMap.get(buildPro)).intValue();
						enlargTenMap.put(buildPro,new Integer(enlargCount+totalCount));
						BigDecimal area = (BigDecimal)enlargAreaMap.get(buildPro);
						enlargAreaMap.put(buildPro,area.add(buildingArea));
					}else if(tenState !=null && (TenancyStateEnum.UNTENANCY_VALUE).equals(tenState))
					{
						int unTenCount = ((Integer)UNTenancyMap.get(buildPro)).intValue();
						UNTenancyMap.put(buildPro,new Integer(unTenCount+totalCount));
						BigDecimal area = (BigDecimal)unAreaMap.get(buildPro);
						unAreaMap.put(buildPro,area.add(buildingArea));
					}else if(tenState !=null && (TenancyStateEnum.WAITTENANCY_VALUE).equals(tenState))
					{
						int waitTenCount = ((Integer)WaitTenancyMap.get(buildPro)).intValue();
						WaitTenancyMap.put(buildPro,new Integer(waitTenCount+totalCount));
						BigDecimal area = (BigDecimal)waitAreaMap.get(buildPro);
						waitAreaMap.put(buildPro,area.add(buildingArea));
					}else if(tenState !=null && (TenancyStateEnum.KEEPTENANCY_VALUE).equals(tenState))
					{
						int keepTenCount = ((Integer)KeepTenancyMap.get(buildPro)).intValue();
						KeepTenancyMap.put(buildPro,new Integer(keepTenCount+totalCount));
						BigDecimal area = (BigDecimal)keepAreaMap.get(buildPro);
						keepAreaMap.put(buildPro,area.add(buildingArea));
					}
				}	

				Set keySet = buildProMap.keySet();
				Iterator iterator = keySet.iterator();
				while(iterator.hasNext()) {
					String buidIdStr = (String)iterator.next();
					IRow thisRow = (IRow)buildProMap.get(buidIdStr);
					//��������,���
					Integer newTenCount = (Integer)newTenancyMap.get(buidIdStr);
					BigDecimal newArea = (BigDecimal)newAreaMap.get(buidIdStr);
					//��������,���
					Integer expandTenCount = (Integer)enlargTenMap.get(buidIdStr);
					BigDecimal enlargArea = (BigDecimal)enlargAreaMap.get(buidIdStr);
					//��������,���
					Integer continueCount = (Integer)conTenaycyMap.get(buidIdStr);
					BigDecimal conArea = (BigDecimal)conAreaMap.get(buidIdStr);
					//δ��������,���
					Integer unTenCount = (Integer)UNTenancyMap.get(buidIdStr);
					BigDecimal unArea = (BigDecimal)unAreaMap.get(buidIdStr);
					//��������,���
					Integer waitTenCount = (Integer)WaitTenancyMap.get(buidIdStr);
					BigDecimal waitArea = (BigDecimal)waitAreaMap.get(buidIdStr);
					//��������,���
					Integer keepTenCount = (Integer)KeepTenancyMap.get(buidIdStr);
					BigDecimal keepArea = (BigDecimal)keepAreaMap.get(buidIdStr);
					if(newTenCount ==null)
					{
						newTenCount = new Integer(0);
					}if(expandTenCount ==null)
					{
						expandTenCount = new Integer(0);
					}
					if(continueCount ==null)
					{
						continueCount = new Integer(0);
					}if(unTenCount ==null)
					{
						unTenCount = new Integer(0);
					}
					if(waitTenCount ==null)
					{
						waitTenCount = new Integer(0);
					}if(keepTenCount ==null)
					{
						keepTenCount = new Integer(0);
					}if(newArea == null)
					{
						newArea = new BigDecimal(0);
					}if(enlargArea == null)
					{
						enlargArea = new BigDecimal(0);
					}
					if(conArea == null)
					{
						conArea = new BigDecimal(0);
					}
					if(unArea == null)
					{
						unArea = new BigDecimal(0);
					}
					if(waitArea == null)
					{
						waitArea = new BigDecimal(0);
					}if(keepArea == null)
					{
						keepArea = new BigDecimal(0);
					}
					//�����
					BigDecimal allArea = newArea.add(enlargArea).add(conArea).add(unArea).add(waitArea).add(keepArea);
					//��������
					int tenCount = newTenCount.intValue()+expandTenCount.intValue()+continueCount.intValue()
					+waitTenCount.intValue()+keepTenCount.intValue();
					Integer tenCount2 = new Integer(tenCount);
					//�������
					BigDecimal tenArea = newArea.add(enlargArea).add(conArea).add(waitArea).add(keepArea);
					tenArea = tenArea.divide(new BigDecimal(1), 2,BigDecimal.ROUND_HALF_UP);
					//��������
					int alreadyCount = newTenCount.intValue()+expandTenCount.intValue()+continueCount.intValue();
					//�������
					BigDecimal alreadyArea = newArea.add(enlargArea).add(conArea);
					alreadyArea = alreadyArea.divide(new BigDecimal(1), 2,BigDecimal.ROUND_HALF_UP);
					BigDecimal area = allArea;
					if(area.equals(new BigDecimal(0)))
					{
						area = new BigDecimal(1);
					}
					//�ȱ���4λ�ٳ�100�ٳ�1����2λ������Ϊ�˾�������
					BigDecimal tenRate =  alreadyArea.divide(area,4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).divide(new BigDecimal(1), 2,BigDecimal.ROUND_HALF_UP);
					String tenArea2 = tenCount2+"/"+tenArea+"m2";
					thisRow.getCell("totalArea").setValue(tenArea2);
					thisRow.getCell("alreadyArea").setValue(alreadyCount +"/"+alreadyArea+"m2");
					thisRow.getCell("alreadyArea").setValue(alreadyCount +"/"+alreadyArea+"m2");
				}
			}					
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * output storeFields method
	 */
	public void storeFields()
	{
		super.storeFields();
	}

	protected ITreeBase getTreeInterface() throws Exception
	{
		return null;
	}

	protected String getEditUIName()
	{
		return null;
	}

	/**
	 * ���䱣��
	 */
	public void actionKeepRoom_actionPerformed(ActionEvent e) throws Exception
	{
		RoomInfo room = this.getSelectRoom(false);

		if(room == null)
			return;

		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(
				new FilterItemInfo("room", room.getId().toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("tenancy.state",FDCBillStateEnum.SAVED,CompareType.NOTEQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("tenancy.tenancyState",TenancyBillStateEnum.BlankOut,CompareType.NOTEQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("tenancy.tenancyState",TenancyBillStateEnum.Expiration,CompareType.NOTEQUALS));

		if (TenancyRoomEntryFactory.getRemoteInstance().exists(filter))
		{
			MsgBox.showInfo("�÷������δ����ĺ�ͬ,���ܽ��д������!");
			return;
		}
		TenancyStateEnum tenancyState = room.getTenancyState();
		//����
		if (TenancyStateEnum.waitTenancy.equals(tenancyState))
		{
			UIContext uiContext = new UIContext(this);
			uiContext.put("room", room);
			uiContext.put("building", room.getBuilding());
			uiContext.put("buildUnit", room.getBuildUnit());
			// ����UI������ʾ
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
			.create(KeepRoomDownEditUI.class.getName(), uiContext,
					null, "ADDNEW");
			uiWindow.show();
		} else if (TenancyStateEnum.keepTenancy.equals(tenancyState))
		{
			int result = MsgBox.showConfirm2("�Ƿ�ȷ��ȡ���ѷ��÷��䣿");
			if (result != MsgBox.YES)
			{
				SysUtil.abort();
			}
			KeepRoomDownFactory.getRemoteInstance().cancelKeepRoom(room.getId().toString());
		}
		this.refresh(null);
	}

	public void actionReceiveBill_actionPerformed(ActionEvent e)
	throws Exception
	{
		super.actionReceiveBill_actionPerformed(e);
		RoomInfo room = this.getSelectRoom(false);
		if (room == null)
		{
			return;
		}
		UIContext uiContext = new UIContext(this);
		if(TenancyStateEnum.sincerObligate.equals(room.getTenancyState()))
		{
			Set billStateSet = TenancyHelper.getQueryBillStates(RevBizTypeEnum.obligate,RevBillTypeEnum.gathering);
			SincerObligateCollection sincerColl = TenancyHelper.getValidObligateByRoomAndState(room, billStateSet);
			if(sincerColl.size()==1)
			{				
				SincerObligateInfo obligateInfo = sincerColl.get(0);
				uiContext.put(FDCReceivingBillEditUI.KEY_REV_BIZ_TYPE, RevBizTypeEnum.obligate);		    
		    	uiContext.put(FDCReceivingBillEditUI.KEY_SINCER_OBLIGATE, obligateInfo);
			}else if(sincerColl.size()==0)//û�ҵ���Ӧ�ĳ���Ԥ����������ת�����˼������������������ĺ�ͬ
			{
				Set tenbillStateSet = TenancyHelper.getQueryBillStates(RevBizTypeEnum.tenancy,RevBillTypeEnum.gathering);
				TenancyBillCollection tenancyBillColl = TenancyHelper.getValidTenancyContractByRoomAndState(room, tenbillStateSet);
				if(tenancyBillColl != null && tenancyBillColl.size()<1)
				{
					logger.warn("��δȡ�÷����Ϊ "+ room.getNumber() +" ����ЧԤ����Ҳδȡ����Ч��ͬ��");
					if(this.getOprtState() != OprtState.VIEW && this.getOprtState() != OprtState.EDIT)
					{
						MsgBox.showInfo("�÷����û�н����տ������Ԥ����Ҳû�н����տ�����ĺ�ͬ��");
					}
					this.abort();
				}else{
					TenancyBillInfo tenancy = new TenancyBillInfo();
					if(tenancyBillColl != null){
						//����ͻ����ұߵķ�����Ϣ�ġ�ҵ��ҳǩ��ѡ����һ������ ���ǿ��Խ����տ���ģ������տ����Ĭ�ϴ�������ѡ�еĺ�ͬ eirc_wang 2010.08.23
						if(this.detailUI!=null&&getSelectTenancyBillInfo()!=null){
								boolean flag =true;
								for(int i =0;i<tenancyBillColl.size();i++){
									TenancyBillInfo tenancy1 = tenancyBillColl.get(i);
									if(tenancy1.getId().toString().equals(getSelectTenancyBillInfo())){
										tenancy =tenancy1;
										flag =false;
										break;
									}
								}
									if(flag){
										//��ѡ��ĺ�ͬ�������տ����
										MsgBox.showInfo("����ѡ��ĺ�ͬ����������տ�");
										this.abort();
									}
								
						}else{
							tenancy = tenancyBillColl.get(0);
						}
					}
					uiContext.put(FDCReceivingBillEditUI.KEY_REV_BIZ_TYPE, RevBizTypeEnum.tenancy);
			    	uiContext.put(FDCReceivingBillEditUI.KEY_TENANCY_BILL, tenancy);			
				}
			}
		}else{
			Set billStateSet = TenancyHelper.getQueryBillStates(RevBizTypeEnum.tenancy,RevBillTypeEnum.gathering);
			TenancyBillCollection tenancyBillColl = TenancyHelper.getValidTenancyContractByRoomAndState(room, billStateSet);
			if(tenancyBillColl != null && tenancyBillColl.size() < 1)
			{
				logger.warn("δȡ�÷����Ϊ "+ room.getNumber() +" ����Ч��ͬ��");
				if(this.getOprtState() != OprtState.VIEW && this.getOprtState() != OprtState.EDIT)
				{
					MsgBox.showInfo("�÷���û�н����տ�����ĺ�ͬ��");
				}
				this.abort();
			}else{
					TenancyBillInfo tenancy = new TenancyBillInfo();
					if(tenancyBillColl != null){
						//����ͻ����ұߵķ�����Ϣ�ġ�ҵ��ҳǩ��ѡ����һ������ ���ǿ��Խ����տ���ģ������տ����Ĭ�ϴ�������ѡ�еĺ�ͬ eirc_wang 2010.08.23
						if(this.detailUI!=null&&getSelectTenancyBillInfo()!=null){
								boolean flag =true;
								for(int i =0;i<tenancyBillColl.size();i++){
									TenancyBillInfo tenancy1 = tenancyBillColl.get(i);
									if(tenancy1.getId().toString().equals(getSelectTenancyBillInfo())){
										tenancy =tenancy1;
										flag =false;
										break;
									}
								}
									if(flag){
										//��ѡ��ĺ�ͬ�������տ����
										MsgBox.showInfo("����ѡ��ĺ�ͬ����������տ�");
										this.abort();
									}
								
						}else{
							tenancy = tenancyBillColl.get(0);
						}
					}
					uiContext.put(FDCReceivingBillEditUI.KEY_REV_BIZ_TYPE, RevBizTypeEnum.tenancy);
			    	uiContext.put(FDCReceivingBillEditUI.KEY_TENANCY_BILL, tenancy);			
				}
			}
		
		uiContext.put("sellProject", room.getBuilding().getSellProject());
		uiContext.put(FDCReceivingBillEditUI.KEY_REV_BILL_TYPE, RevBillTypeEnum.gathering);
    	uiContext.put(FDCReceivingBillEditUI.KEY_IS_LOCK_BILL_TYPE, Boolean.TRUE);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
		.create(TENReceivingBillEditUI.class.getName(), uiContext, null,
		"ADDNEW");
		uiWindow.show();
		this.refresh(null);
		
	}


	public void actionView_actionPerformed(ActionEvent e) throws Exception
	{
		RoomInfo room = this.getSelectRoom(false);
		if (room != null)
		{
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", room.getId().toString());
			IUIWindow uiWindow = UIFactory
			.createUIFactory(UIFactoryName.MODEL)
			.create(RoomEditUI.class.getName(), uiContext, null, "VIEW");
			uiWindow.show();
		}
	}

	/**
	 * ������� 
	 * i. ֻ�д���״̬�ķ�����Գ���. 
	 * ii. У������÷���ĺ�ͬ�������ϻ���ֹ״̬. 
	 * iii.������ʾ���Ƿ���?��,���򽫷�����Ϊδ����״̬.
	 */
	public void actionCancelTenancy_actionPerformed(ActionEvent e)
	throws Exception
	{
		RoomInfo room = this.getSelectRoom(true);

		if (room == null)
			return;

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("room", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("tenancy.tenancyState",TenancyBillStateEnum.Expiration,
				CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("tenancy.tenancyState",TenancyBillStateEnum.BlankOut,
				CompareType.NOTEQUALS));
		if (TenancyRoomEntryFactory.getRemoteInstance().exists(filter))
		{
			MsgBox.showInfo("�÷������δ������ɵĺ�ͬ�����ܽ��г������!");
			return;
		} else
		{
			int result = MsgBox.showConfirm2("�Ƿ�ȷ������ѡ��ķ�����г��������");
			if (result != MsgBox.YES)
			{
				this.abort();
			}
			room.setTenancyState(TenancyStateEnum.unTenancy);

			SelectorItemCollection selectorItemColl = new SelectorItemCollection();
			selectorItemColl.add("tenancyState");

			RoomFactory.getRemoteInstance().updatePartial(room,selectorItemColl);
		}
		this.refresh(null);
	}

	protected TenancyRoomEntryCollection getTenRoomColl(RoomInfo room,Set tempSet) throws BOSException
	{
		FilterInfo filter = new FilterInfo();		
		filter.getFilterItems().add(new FilterItemInfo("room",room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("tenancy.tenancyState",tempSet,CompareType.INCLUDE));

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("tenancy.*");
		view.getSelector().add("tenancy.sellProject.*");
		view.setFilter(filter);

		TenancyRoomEntryCollection tenancyRoomEntryColl = TenancyRoomEntryFactory.getRemoteInstance().getTenancyRoomEntryCollection(view);
		return tenancyRoomEntryColl;
	}
	
	/**
	 * ���佻�ӵĲ���
	 */
	public void actionHandleTenancy_actionPerformed(ActionEvent e) throws Exception
	{
		RoomInfo room = this.getSelectRoom(true);

		if (room == null)
			return;

		//��������Ǵ����״̬����ֱ�����Ƿ�����÷�����ѽ�Ѻ��������߰�ִ�еĺ�ͬ
		if(TenancyStateEnum.waitTenancy.equals(room.getTenancyState())||TenancyStateEnum.sincerObligate.equals(room.getTenancyState()))
		{
			Set tempSet = new HashSet();
			tempSet.add(TenancyBillStateEnum.PARTEXECUTED_VALUE);
			tempSet.add(TenancyBillStateEnum.AUDITED_VALUE);
			TenancyRoomEntryCollection tenancyRoomEntryColl = this.getTenRoomColl(room,tempSet);
			if(tenancyRoomEntryColl == null)
			{
				logger.warn("����Զ�̽ӿ�ȡ����������"+e);
				MsgBox.showInfo("����Զ�̽ӿڲ��Һ�ͬ����ϵͳ����");
				return;
			}
			if (tenancyRoomEntryColl.size()<1)
			{
				MsgBox.showInfo("�÷���û�д��ڽ��������ĺ�ͬ��");
				return;
			}else if(tenancyRoomEntryColl.size() > 1)
			{
				logger.warn("����״̬�ķ��䣬�ҵ���һ�����ϵ� ������ ״̬�ĺ�ͬ���������޺�ͬģ������߼�....");
				MsgBox.showInfo("ϵͳ�߼������Ѽ�¼��־�����֪����Ա��");
				return;
			}else// ���ҵ��˺�ͬ������£������µ���Щ����
			{
				//�ҵ���������ں�ͬ���Ƿ񽻹���Ѻ���׸�
				TenancyRoomEntryInfo tenEntryInfo = tenancyRoomEntryColl.get(0);
				BigDecimal depositAmount = tenEntryInfo.getDepositAmount();
				depositAmount = depositAmount==null?new BigDecimal(0):depositAmount;
				BigDecimal firstPayRent = tenEntryInfo.getFirstPayAmount();
				firstPayRent = firstPayRent==null?new BigDecimal(0):firstPayRent;
				BigDecimal rent = depositAmount.add(firstPayRent);
				TenancyBillInfo tenBillInfo = tenEntryInfo.getTenancy();
				BigDecimal recRent = TenancyHelper.getRecAmount(room.getId().toString(),tenBillInfo.getId().toString());
				if(recRent==null)
				{
					recRent = new BigDecimal(0);
				}
				//�����չ�Ǯ��û��
//				if(recRent.equals(new BigDecimal(0)))
//				{
//					MsgBox.showInfo("����"+tenEntryInfo.getRoomLongNum()+"��û�н���");
//					this.abort();
//				}else if(recRent.compareTo(new BigDecimal(0))>0 && recRent.compareTo(rent)<0)
//				{
//					MsgBox.showInfo("����"+tenEntryInfo.getRoomLongNum()+"û�н�������Ѻ��");
//					this.abort();
//				}else 
				if(tenEntryInfo.getActDeliveryRoomDate()!=null)
				{
					MsgBox.showInfo("����"+tenEntryInfo.getRoomLongNum()+"�Ѿ���������");
					this.abort();
				}else
				{
					// �������ӽ���Ĳ���
					if(!tenEntryInfo.getTenancy().getTenancyType().equals(TenancyContractTypeEnum.NewTenancy)
							&&(TenancyStateEnum.sincerObligate.equals(room.getTenancyState()))){
						MsgBox.showInfo("����"+tenEntryInfo.getRoomLongNum()+"����Ϊ����״̬");
						this.abort();
						
					}
					UIContext uiContext = new UIContext(this);
					uiContext.put("ID", room.getId().toString());
					uiContext.put("sellProject", tenEntryInfo.getTenancy().getSellProject());
					uiContext.put("tenancyBillInfo",tenEntryInfo.getTenancy());
					uiContext.put("tenancyBillId",tenEntryInfo.getTenancy().getId().toString());
					IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(HandleRoomTenancyEditUI.class.getName(), uiContext,
							null, OprtState.ADDNEW);
					uiWindow.show();
				}				
			}

		}else if(TenancyStateEnum.sincerObligate.equals(room.getTenancyState()))
		{
			MsgBox.showInfo("�÷����Ѿ�Ԥ������Ҫ���ӣ�");
			return;
		}
		//�����������״̬�����������ִ�еĺ�ͬ----------------���ﻹû�п��ǵ���ִ��״̬�ĺ�ͬ�ܷ�������⣬���⣬ת���Ȳ���
		else 
		{
			Set tempSet = new HashSet();
			tempSet.add(TenancyBillStateEnum.CONTINUETENANCYING_VALUE);
			tempSet.add(TenancyBillStateEnum.REJIGGERTENANCYING_VALUE);
			tempSet.add(TenancyBillStateEnum.CHANGENAMING_VALUE);
			tempSet.add(TenancyBillStateEnum.TENANCYCHANGING_VALUE);
			tempSet.add(TenancyBillStateEnum.QUITTENANCYING_VALUE);
			tempSet.add(TenancyBillStateEnum.AUDITED_VALUE);
			tempSet.add(TenancyBillStateEnum.PARTEXECUTED_VALUE);
			TenancyRoomEntryCollection tenancyRoomEntryColl = this.getTenRoomColl(room,tempSet);
			if(tenancyRoomEntryColl == null)
			{
				logger.warn("����Զ�̽ӿ�ȡ����������"+e);
				MsgBox.showInfo("����Զ�̽ӿڲ��Һ�ͬ����ϵͳ����");
				return;
			}
			if (tenancyRoomEntryColl.size()<1)
			{
				MsgBox.showInfo("�÷����Ӧ�ĺ�ͬ״̬��<ִ����>������ɽ��ӣ��������ٶԴ˷�����н��Ӳ�����");
				return;
			}
			// ���ҵ��˺�ͬ������£������µ���Щ����
			else
			{
				TenancyRoomEntryInfo tenEntryInfo = tenancyRoomEntryColl.get(0);
				TenancyBillInfo tenBillInfo = tenEntryInfo.getTenancy();
				// �������ӽ���Ĳ���
				UIContext uiContext = new UIContext(this);
				uiContext.put("ID", room.getId().toString());
				uiContext.put("sellProject", tenEntryInfo.getTenancy().getSellProject());
				uiContext.put("tenancyBillInfo",tenBillInfo);
				uiContext.put("tenancyBillId",tenEntryInfo.getTenancy().getId().toString());
				uiContext.put("tenancyRoomEntryColl",tenancyRoomEntryColl);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(HandleRoomTenancyEditUI.class.getName(), uiContext,
						null, OprtState.ADDNEW);
				uiWindow.show();
			}
		}

	}

	/**
	 * ��������Ĳ���
	 */
	public void actionApplyTenancy_actionPerformed(ActionEvent e) throws Exception 
	{
		RoomInfo room = this.getSelectRoom(true);

		if (room == null)
			return;

		/*
		 * �ֲ�ͬ�ķ�������״̬ȥ����
		 */
		//����״̬�ķ��䣬ֱ�ӵ��ú�ͬ����
		if(TenancyStateEnum.waitTenancy.equals(room.getTenancyState())||(TenancyStateEnum.sincerObligate.equals(room.getTenancyState())))
		{
			//			 �������ӽ���Ĳ���
			RoomCollection roomColl = new RoomCollection();
			roomColl.add(room);
			UIContext uiContext = new UIContext(this);
			uiContext.put(TenancyBillConstant.KEY_ROOMS, roomColl);
			uiContext.put(TenancyBillConstant.KEY_SELL_PROJECT, room.getBuilding().getSellProject());
			uiContext.put(TenancyBillConstant.KEY_TENANCY_CONTRACT_TYPE,TenancyContractTypeEnum.NewTenancy);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
			.create(TenancyBillEditUI.class.getName(), uiContext,
					null, OprtState.ADDNEW);
			uiWindow.show();
		}//���Ҹ÷��䵱ǰ��ͬ
		else
		{
			TenancyBillInfo tenancy = room.getLastTenancy();
			if(tenancy == null){
				logger.error("�����߼�����������ݣ�����ķ���lastTenancy��ô��Ϊ����.");
				abort();
			}

			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("room", room.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("tenancy.id", tenancy.getId().toString()));

			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("flagAtTerm");
			view.setFilter(filter);

			TenancyRoomEntryCollection tenancyRoomEntryColl = TenancyRoomEntryFactory
			.getRemoteInstance().getTenancyRoomEntryCollection(view);
			if (tenancyRoomEntryColl.size() < 1)
			{
				logger.error("�߼������ݴ���");
				abort();
			}
			//			��ǰ��ͬ��Ӧ�������ִ�еĺ�ͬ��������Ӧ�ò����ڵġ� 
			else if(tenancyRoomEntryColl.size()>1)
			{
				logger.error("�߼������ݴ���");
				abort();
			}
			// ���ҵ��˺�ͬ������£��жϸķ���ĵ��ڱ��
			else
			{
				TenancyRoomEntryInfo tenancyRoomEntryInfo = tenancyRoomEntryColl
				.get(0);
				if (FlagAtTermEnum.QuitAtTerm.equals(tenancyRoomEntryInfo
						.getFlagAtTerm()))
				{

					// �������ӽ���Ĳ���
					RoomCollection roomColl = new RoomCollection();
					roomColl.add(room);

					UIContext uiContext = new UIContext(this);
					uiContext.put(TenancyBillConstant.KEY_ROOMS, roomColl);
					uiContext.put(TenancyBillConstant.KEY_SELL_PROJECT, room.getBuilding().getSellProject());

					uiContext.put(TenancyBillConstant.KEY_TENANCY_CONTRACT_TYPE,TenancyContractTypeEnum.NewTenancy);
					IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
					.create(TenancyBillEditUI.class.getName(), uiContext,
							null, OprtState.ADDNEW);
					uiWindow.show();
				}else{
					MsgBox.showInfo("����ִ�е����޺�ͬ��,�÷��䵽�ڱ�ǲ�Ϊ��������,���ʺ�����!");
					return;
				}
			}
		}
	}

	/**
	 * ��������Ĳ���
	 */
	public void actionContinueTenancy_actionPerformed(ActionEvent e) throws Exception
	{
		RoomInfo room = this.getSelectRoom(true);
		if (room == null)
			return;

		//���ҵ�ǰ����ִ�еĺ�ͬ
		TenancyBillInfo tenancy = room.getLastTenancy();
		if(tenancy == null){
			logger.error("����ķ���lastTenancyΪ��.");
			abort();
		}

		commonVerify2(tenancy.getId().toString());

		UIContext uiContext = new UIContext(this);
		uiContext.put(TenancyBillConstant.KEY_OLD_TENANCY_BILL_ID,tenancy.getId().toString());
		uiContext.put(TenancyBillConstant.KEY_TENANCY_CONTRACT_TYPE,TenancyContractTypeEnum.ContinueTenancy);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
		.create(TenancyBillEditUI.class.getName(), uiContext,
				null, OprtState.ADDNEW);
		uiWindow.show();
	}

	/**
	 * �жϸú�ͬ�Ƿ���ڷǱ���״̬�����ⵥ,�Ǳ���״̬�ĸ�������������ĺ�ͬ
	 * �������,�Ի�����ʾ���жϲ���
	 * */
	private void commonVerify2(String tenId) throws EASBizException, BOSException {
		if (TenancyHelper.existQuitTenBillByTenBill(QuitTenancyFactory.getRemoteInstance(), tenId, null)) {
			MsgBox.showInfo(this, "��ǰ���������Ч״̬�����ⵥ�����ܽ��иò�������");
			this.abort();
		}

		String targetTenId = TenancyHelper.getTargetTenIdBySrcTenancyId(tenId);
		if (targetTenId != null) {
			MsgBox.showInfo(this, "�÷��������Ч״̬�����������ת���ĺ�ͬ�����ܽ��иò�����");
			this.abort();
		}

		if(TenancyHelper.existRentRemissionByTenBill(RentRemissionFactory.getRemoteInstance(), tenId)){
			MsgBox.showInfo(this, "�÷������ڽ��������⣬���ܽ��иò�����");
			this.abort();
		}

		if(TenancyHelper.existTenancyModificationByTenBill(TenancyModificationFactory.getRemoteInstance(), tenId)){
			MsgBox.showInfo(this, "�÷������ڽ��к�ͬ��������ܽ��иò�����");
			this.abort();
		}
	}

	/**
	 * �������
	 */
	public void actionRejiggerTenancy_actionPerformed(ActionEvent e) throws Exception
	{

		RoomInfo room = this.getSelectRoom(true);
		if (room == null)
			return;

		//���ҵ�ǰ����ִ�еĺ�ͬ
		TenancyBillInfo tenancy = room.getLastTenancy();
		if(tenancy == null){
			logger.error("����ķ���lastTenancyΪ��.");
			abort();
		}

		commonVerify2(tenancy.getId().toString());

		// �����������Ĳ���
		UIContext uiContext = new UIContext(this);
		uiContext.put(TenancyBillConstant.KEY_OLD_TENANCY_BILL_ID, tenancy.getId().toString());
		uiContext.put(TenancyBillConstant.KEY_TENANCY_CONTRACT_TYPE, TenancyContractTypeEnum.RejiggerTenancy);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(TenancyBillEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}

	/**
	 * �������� �߼�ͬ�������
	 */
	public void actionQuitTenancy_actionPerformed(ActionEvent e) throws Exception
	{
		RoomInfo room = this.getSelectRoom(true);
		if (room == null)
			return;

		//���ҵ�ǰ����ִ�еĺ�ͬ
		TenancyBillInfo tenancy = room.getLastTenancy();
		if(tenancy == null){
			logger.error("����ķ���lastTenancyΪ��.");
			abort();
		}
		commonVerify2(tenancy.getId().toString());
		// �����������Ĳ���
		UIContext uiContext = new UIContext(this);
		uiContext.put(QuitTenancyEditUI.KEY_TENANCY_ID, tenancy.getId().toString());
		uiContext.put("sellProject",tenancy.getSellProject());
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(QuitTenancyEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}

	/**
	 * ת������ �߼�ͬ�������
	 */
	public void actionChangeName_actionPerformed(ActionEvent e) throws Exception
	{
		RoomInfo room = this.getSelectRoom(true);
		if (room == null)
			return;

		//���ҵ�ǰ����ִ�еĺ�ͬ
		TenancyBillInfo tenancy = room.getLastTenancy();
		if(tenancy == null){
			logger.error("����ķ���lastTenancyΪ��.");
			abort();
		}
		commonVerify2(tenancy.getId().toString());
		UIContext uiContext = new UIContext(this);
		uiContext.put(TenancyBillConstant.KEY_OLD_TENANCY_BILL_ID, tenancy.getId().toString());
		uiContext.put(TenancyBillConstant.KEY_TENANCY_CONTRACT_TYPE, TenancyContractTypeEnum.ChangeName);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(TenancyBillEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}
	
	protected void solidSideRadioBtn_actionPerformed(ActionEvent e)
			throws Exception {
		refresh(null);
		this.kDPanel1.remove(tblMain);
		this.kDPanel1.remove(effectImagePanel);
		this.kDPanel1.add(solidSideImagePanel,BorderLayout.CENTER);
		kDPanel1.repaint();
		kDPanel1.validate();
		kDPanel1.revalidate();
		refresh(null);
		kDLabelContainerZoom.setVisible(true);
		pnlRoomPic.removeMouseListener(moListener);
		pnlRoomPic.removeMouseMotionListener(mmListener);
		topX=0;topY=0;downX=0;downY=0;
	}
	
	protected void planeRadioBtn_actionPerformed(ActionEvent e)
			throws Exception {
		refresh(null);
		this.kDPanel1.remove(solidSideImagePanel);
		this.kDPanel1.remove(effectImagePanel);
		this.kDPanel1. add(tblMain,BorderLayout.CENTER);
		kDPanel1.repaint();
		kDPanel1.validate();
		kDPanel1.revalidate();
		refresh(null);
		kDLabelContainerZoom.setVisible(false);
		pnlRoomPic.removeMouseListener(moListener);
		pnlRoomPic.removeMouseMotionListener(mmListener);
		topX=0;topY=0;downX=0;downY=0;
	}
	
	protected void effectRadioBtn_actionPerformed(ActionEvent e)
			throws Exception {
		refresh(null);
		this.kDPanel1.remove(tblMain);
		this.kDPanel1.remove(solidSideImagePanel);
		this.kDPanel1.add(effectImagePanel,BorderLayout.CENTER);
		refresh(null);
		kDLabelContainerZoom.setVisible(false);
		pnlRoomPic.repaint();
		pnlRoomPic.addMouseListener(moListener=new MouseListener(){

			public void mouseClicked(MouseEvent e) {
				DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
				//  Ŀǰֻ����¥��Ч��ͼ ��������Ч��ͼ���ڴ���
//				if(node.getUserObject() instanceof PlanisphereInfo){//ֻ��¥��Ч��ͼʱ �����Ż���ط�����Ϣ
				if(node.getUserObject() instanceof EffectImageAndPlanisphereInfo){//ֻ��¥��Ч��ͼʱ �����Ż���ط�����Ϣ
					int x=e.getX();
					int y=e.getY();
//					FilterInfo filter=new FilterInfo();
//					BuildingFloorEntryInfo bf=getSelectBF((PlanisphereInfo)node.getUserObject());
//		    		filter.getFilterItems().add(new FilterItemInfo("type",EffectImageEnum.PIC_BUILDINGFLOOR_VALUE));
//		    		filter.getFilterItems().add(new FilterItemInfo("buildingFloor.id",bf.getId().toString()));
//					DefaultKingdeeTreeNode parent = (DefaultKingdeeTreeNode)node.getParent();
//					//���ڵ��ǵ�Ԫʱ�������ֵ�Ԫ��¥��Ч��ͼ
//					if(parent.getUserObject()!=null && parent.getUserObject() instanceof BuildingUnitInfo){
//						filter.getFilterItems().add(new FilterItemInfo("isShowUnit",Boolean.TRUE));
//					}
//					//���ڵ���¥��ʱ���ز����ֵ�Ԫ��¥��Ч��ͼ
//					if(parent.getUserObject()!=null && parent.getUserObject() instanceof BuildingInfo){
//						filter.getFilterItems().add(new FilterItemInfo("isShowUnit",Boolean.FALSE));
//					}
//					EffectImageInfo info=getEffectImageInfo(filter);
					EffectImageAndPlanisphereInfo effectPlainInfo = (EffectImageAndPlanisphereInfo)node.getUserObject();
					EffectImageInfo info = effectPlainInfo.getEffectImageInfo();
					if(info==null || info.getElementEntry()==null){//���û�������ȵ����û�е���Ч��ͼ�����κδ���
						return;
					}
					else{
						EffectImageElementEntryCollection entryColl=info.getElementEntry();
						for(int i=0;i<entryColl.size();i++){
							EffectImageElementEntryInfo entry=entryColl.get(i);
							if(entry.getHotspot1()==null || entry.getHotspot2()==null){
								continue;
							}
							String hotspot1=entry.getHotspot1();
							String hotspot2=entry.getHotspot2();
							if(hotspotInRec(x, y, hotspot1, hotspot2)){//�������ĸ��ȵ�����ͼ�����ط�����Ϣ
								topX=Integer.parseInt(hotspot1.substring(0,hotspot1.indexOf(',')));
								topY=Integer.parseInt(hotspot1.substring(hotspot1.indexOf(',')+1,hotspot1.length()));
								downX=Integer.parseInt(hotspot2.substring(0,hotspot2.indexOf(',')));
								downY=Integer.parseInt(hotspot2.substring(hotspot2.indexOf(',')+1,hotspot2.length()));
								try {
									RoomInfo room=RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(entry.getElementID()));
									roomInfo=SHEHelper.queryRoomInfo(room.getId().toString());
									changeButtonState();
									actionReceiveBill.setEnabled(true);
									if (roomInfo != null)
									{
										if (detailUI == null)
										{
											UIContext uiContext = new UIContext(ui);
											uiContext.put(UIContext.ID, roomInfo.getId().toString());
											detailUI = (CoreUIObject) UIFactoryHelper
											.initUIObject(RoomDetailInfoUI.class.getName(),
													uiContext, null, "VIEW");
											detailUI.setDataObject(roomInfo);
											sclPanel.setViewportView(detailUI);
											sclPanel.setKeyBoardControl(true);
											return;
										}
										detailUI.getUIContext().put(UIContext.ID, roomInfo.getId().toString());
										detailUI.setDataObject(roomInfo);
										detailUI.loadFields();
									}
								} catch (EASBizException e1) {
									e1.printStackTrace();
								} catch (BOSException e1) {
									e1.printStackTrace();
								}
								break;//������ν��ѭ��
							}
						}
					}
				}
				pnlRoomPic.repaint();
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseReleased(MouseEvent e) {
			}
			
		});
		pnlRoomPic.addMouseMotionListener(mmListener=new MouseMotionListener(){

			public void mouseDragged(MouseEvent e) {
			}

			public void mouseMoved(MouseEvent e) {
				DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
				//  Ŀǰֻ����¥��Ч��ͼ ��������Ч��ͼ���ڴ���
//				if(node.getUserObject() instanceof PlanisphereInfo){//ֻ��¥��Ч��ͼʱ ��ͣ�Ż���ط�������
				if(node.getUserObject() instanceof EffectImageAndPlanisphereInfo){//ֻ��¥��Ч��ͼʱ ��ͣ�Ż���ط�������
					int x=e.getX();
					int y=e.getY();
//					FilterInfo filter=new FilterInfo();
//					BuildingFloorEntryInfo bf=getSelectBF((PlanisphereInfo)node.getUserObject());
//		    		filter.getFilterItems().add(new FilterItemInfo("type",EffectImageEnum.PIC_BUILDINGFLOOR_VALUE));
//		    		filter.getFilterItems().add(new FilterItemInfo("buildingFloor.id",bf.getId().toString()));
//					DefaultKingdeeTreeNode parent = (DefaultKingdeeTreeNode)node.getParent();
//					//���ڵ��ǵ�Ԫʱ�������ֵ�Ԫ��¥��Ч��ͼ
//					if(parent.getUserObject()!=null && parent.getUserObject() instanceof BuildingUnitInfo){
//						filter.getFilterItems().add(new FilterItemInfo("isShowUnit",Boolean.TRUE));
//					}
//					//���ڵ���¥��ʱ���ز����ֵ�Ԫ��¥��Ч��ͼ
//					if(parent.getUserObject()!=null && parent.getUserObject() instanceof BuildingInfo){
//						filter.getFilterItems().add(new FilterItemInfo("isShowUnit",Boolean.FALSE));
//					}
//					EffectImageInfo info=getEffectImageInfo(filter);
					EffectImageAndPlanisphereInfo effectPlainInfo = (EffectImageAndPlanisphereInfo)node.getUserObject();
					EffectImageInfo info = effectPlainInfo.getEffectImageInfo();
					if(info==null || info.getElementEntry()==null){//���û�������ȵ����û�е���Ч��ͼ�����κδ���
						return;
					}
					else{
						EffectImageElementEntryCollection entryColl=info.getElementEntry();
						for(int i=0;i<entryColl.size();i++){
							EffectImageElementEntryInfo entry=entryColl.get(i);
							if(entry.getHotspot1()==null || entry.getHotspot2()==null){
								continue;
							}
							String hotspot1=entry.getHotspot1();
							String hotspot2=entry.getHotspot2();
							if(hotspotInRec(x, y, hotspot1, hotspot2)){//��ͣ���ĸ��ȵ�����ͼ�����ط�������
								try {
									RoomInfo room=RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(entry.getElementID()));
									roomInfo=SHEHelper.queryRoomInfo(room.getId().toString());
									pnlRoomPic.setToolTipText(roomInfo.getName());
								} catch (EASBizException e1) {
									e1.printStackTrace();
								} catch (BOSException e1) {
									e1.printStackTrace();
								}
							}
						}
					}
				}
			}
			
		});
	}

	public void actionResponse(Object obj) throws Exception {
		RoomInfo room=(RoomInfo)obj;
		roomInfo=SHEHelper.queryRoomInfo(room.getId().toString());
		changeButtonState();
		this.actionReceiveBill.setEnabled(true);
		if (roomInfo != null)
		{
			if (detailUI == null)
			{
				UIContext uiContext = new UIContext(ui);
				uiContext.put(UIContext.ID, roomInfo.getId().toString());
				detailUI = (CoreUIObject) UIFactoryHelper
				.initUIObject(RoomDetailInfoUI.class.getName(),
						uiContext, null, "VIEW");
				detailUI.setDataObject(roomInfo);
				sclPanel.setViewportView(detailUI);
				sclPanel.setKeyBoardControl(true);
				return;
			}
			detailUI.getUIContext().put(UIContext.ID, roomInfo.getId().toString());
			detailUI.setDataObject(roomInfo);
			detailUI.loadFields();
		}
	}

	public void actionBothClicked(Object obj) throws Exception {
		RoomInfo room=(RoomInfo)obj;
		roomInfo=SHEHelper.queryRoomInfo(room.getId().toString());
		if (roomInfo != null)
		{
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", roomInfo.getId().toString());
			IUIWindow uiWindow = UIFactory.createUIFactory(
					UIFactoryName.NEWTAB).create(RoomEditUI.class.getName(),
							uiContext, null, "VIEW");
			uiWindow.show();
		}
	}
	/**
	 *  ����ͼ���ű�������
	 */
	protected void comboBoxZoom_itemStateChanged(ItemEvent e) throws Exception
    {
		if(e.getStateChange()==ItemEvent.SELECTED){
			String zoom=this.comboBoxZoom.getSelectedItem().toString();
			if(zoom.equals("1 m2:32 px")){
				selectedZoom=new BigDecimal("32");
				refresh(null);
			}
			if(zoom.equals("1 m2:16 px")){
				selectedZoom=new BigDecimal("16");
				refresh(null);
			}
			if(zoom.equals("1 m2:8 px")){
				selectedZoom=new BigDecimal("8");
				refresh(null);
			}
			if(zoom.equals("1 m2:4 px")){
				selectedZoom=new BigDecimal("4");
				refresh(null);
			}
			if(zoom.equals("1 m2:2 px")){
				selectedZoom=new BigDecimal("2");
				refresh(null);
			}
			if(zoom.equals("1 m2:1 px")){
				selectedZoom=new BigDecimal("1");
				refresh(null);								
			}
			if(zoom.equals("2 m2:1 px")){
				selectedZoom=new BigDecimal("0.5");
				refresh(null);								
			}
			if(zoom.equals("4 m2:1 px")){
				selectedZoom=new BigDecimal("0.25");
				refresh(null);								
			}
			if(zoom.equals("8 m2:1 px")){
				selectedZoom=new BigDecimal("0.125");
				refresh(null);								
			}
			if(zoom.equals("16 m2:1 px")){
				selectedZoom=new BigDecimal("0.063");
				refresh(null);								
			}
			if(zoom.equals("32 m2:1 px")){
				selectedZoom=new BigDecimal("0.032");
				refresh(null);
			}
		}
    }
	/**
	 * ���ÿ�ѡ����
	 */
	private void setComboBoxZoomItem(){
		this.kDLabelContainerZoom.setVisible(false);
		this.comboBoxZoom.addItem("1 m2:32 px");
		this.comboBoxZoom.addItem("1 m2:16 px");
		this.comboBoxZoom.addItem("1 m2:8 px");
		this.comboBoxZoom.addItem("1 m2:4 px");
		this.comboBoxZoom.addItem("1 m2:2 px");
		this.comboBoxZoom.addItem("1 m2:1 px");
		this.comboBoxZoom.addItem("2 m2:1 px");
		this.comboBoxZoom.addItem("4 m2:1 px");
		this.comboBoxZoom.addItem("8 m2:1 px");
		this.comboBoxZoom.addItem("16 m2:1 px");
		this.comboBoxZoom.addItem("32 m2:1 px");
		this.comboBoxZoom.setSelectedItem("1 m2:1 px");
		
	}
	
	private JButton lastBtn=null; //��ʱ������¼�������ĵ�Ԫ��
	private Color lastColor;//��¼�ϸ�������İ�ť����ɫ	
	
	public void setColor(JButton btn, Color c) throws Exception {
		if(lastBtn!=null){
			lastBtn.setForeground(lastColor);
		}
		Color test=new Color(255,0,255);
		btn.setForeground(test);
//		btn.setBackground(test);
		if(c.getRGB()!=test.getRGB()){
			lastColor=Color.BLACK;
		}
		lastBtn=btn;
	}
	
	/**
	 * ��÷�����Ϣ��ϸ�µ�ҵ��ҳǩ��һ����ͬ��¼
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private String getSelectTenancyBillInfo() throws EASBizException, BOSException{
		RoomDetailInfoUI rdiUI =(RoomDetailInfoUI)this.detailUI;
		String id=null;
		id = FDCClientHelper.getSelectedKeyValue(rdiUI.getBizTable(), "id");
		return id;
	}
	
	@Override
	public void actionReturnTenancy_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.actionReturnTenancy_actionPerformed(e);
		
		RoomInfo room = this.getSelectRoom(true);

		if (room == null)
			return;
		
		if(!room.isIsReturn()){
			FDCMsgBox.showError("��ѡ���䲻֧�ַ���.");
			abort();
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put("room", room);
		uiContext.put("fromTC", Boolean.TRUE);
		IUIWindow uiWindows = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ReturnTenancyBillEditUI.class.getName(), uiContext,null,"ADDNEW");
		uiWindows.show();
	}
}
