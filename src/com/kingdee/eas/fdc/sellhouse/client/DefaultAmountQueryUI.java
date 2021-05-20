/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountCreatEntryInfo;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountCreatInfo;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerCollection;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerFactory;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionInfo;
import com.kingdee.eas.fm.common.DateHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * Ƿ��ҵ���ѯ����
 */
public class DefaultAmountQueryUI extends AbstractDefaultAmountQueryUI
{
    private static final Logger logger = CoreUIObject.getLogger(DefaultAmountQueryUI.class);
    
    /**
     * by..... wancheng
     */
    public DefaultAmountQueryUI() throws Exception
    {
        super();
    }
    protected SellProjectInfo sellProject = null;
    public void onLoad() throws Exception {
		super.onLoad();
		/**�������ֿؼ�����*/
		Object[] obj = {txtArgDay,txtargDayTo};
		FDCHelper.setComponentPrecision(obj,0);
		this.actionAddNew.setVisible(false);
		this.actionAbout.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionAudit.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionCalculator.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionSubmit.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionUnAudit.setVisible(false);
		this.prmtProject.setEnabled(false);
		this.actionExitCurrent.setVisible(false);
		this.actionExitCurrent.setEnabled(false);
		if (( this.getUIContext().get("sellProject") != null) && ( this.getUIContext().get("sellProject") != "")) {
			SellProjectInfo projectInfo = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(this.getUIContext().get("sellProject").toString()));
			this.prmtProject.setValue(projectInfo);
			sellProject = projectInfo;
		}
		sign.setSelected(true);
		setRoomFilter();
		
		prePurchase.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setRoomFilter();
			}
		});
		
		purchase.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setRoomFilter();
			}
		});
		
		sign.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setRoomFilter();
			}
		});
    }

    private void setRoomFilter(){
    	EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
		
		Set inS = new HashSet();
		if(prePurchase.isSelected()){
			inS.add(RoomSellStateEnum.PREPURCHASE_VALUE);
		}
		if(purchase.isSelected()){
			inS.add(RoomSellStateEnum.PURCHASE_VALUE);
		}
		if(sign.isSelected()){
			inS.add(RoomSellStateEnum.SIGN_VALUE);
		}
		
		if(inS.isEmpty()){
			inS.add(RoomSellStateEnum.PREPURCHASE_VALUE);
			inS.add(RoomSellStateEnum.PURCHASE_VALUE);
			inS.add(RoomSellStateEnum.SIGN_VALUE);
		}
		
		filter.getFilterItems().add(new FilterItemInfo("sellState", inS, CompareType.INCLUDE));
		
		view.setFilter(filter);
		prmtRoom.setEntityViewInfo(view);
    }
    
    
	/**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
  protected List room=new ArrayList();
  protected List moneyDefine=new ArrayList();
    public void loadFields() 
    {
		super.loadFields();
	}

	/**
     * ��ѯ��ť
     */
    public void actionArgQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionArgQuery_actionPerformed(e);
        if(sign.isSelected()==false&&prePurchase.isSelected()==false&&purchase.isSelected()==false){
        	 MsgBox.showInfo("������ѡ��һ��ҵ�����ͽ��в�ѯ!");
		      SysUtil.abort();
        }
        StringBuffer sb = new StringBuffer();
        Object[] obj = (Object[])prmtRoom.getValue();
        if(obj!=null && obj.length>0){
        	for(int i=0;i<obj.length &&obj[i]!=null;i++){
            	if(i==0)
            		sb.append("'"+((RoomInfo)obj[i]).getId().toString()+"'");
            	else
            		sb.append(",'"+((RoomInfo)obj[i]).getId().toString()+"'");
            }
        }
        kDTable1.removeRows();//d
        
        StringBuffer sql = new StringBuffer();
        sql.append(" select zz.����,zz.�ͻ�,zz.ҵ������,zz.ҵ����,zz.��ҵ����,zz.��ͬ�ܼ�,zz.��ϵ�绰,zz.Ӧ������,zz.Ӧ�ս��,zz.ʵ������,zz.ʵ�ս��,");
        sql.append(" zz.Ƿ����,zz.��������,zz.Ƿ������,zz.Ƿ���ܽ��,zz.�����,zz.����ID,zz.�����ID,zz.��������ID,zz.ҵ������ID,zz.������ĿID,zz.����ID");
        sql.append(" from (");
        if(sign.isSelected()==true){
        	 sql.append(" select sg.����,sg.����ID,sg.�ͻ�,sg.ҵ������,sg.����ID,sg.ҵ����,sg.��ҵ����,sg.ҵ������ID,sg.��ͬ�ܼ�,sg.��ϵ�绰,sg.Ӧ������,");
             sql.append(" sg.Ӧ�ս��,sg.ʵ������,sg.ʵ�ս��,sg.Ƿ����,sg.��������,sg.Ƿ������,sg.�����,sg.�����ID,sg.Ƿ���ܽ��,sg.��������ID,sg.������ĿID");
             sql.append(" from (select room.fname_l2 as ����,room.fid as ����ID,");
             sql.append(" zx.FCustomerNames as �ͻ�, zx.FTranDate as ҵ������,zx.fid as ����ID,");
             sql.append(" sm.fnumber as ҵ����,sm.FSaleManNames as ��ҵ����,sm.fid as ҵ������ID,");
             sql.append(" sm.FContractTotalAmount as ��ͬ�ܼ�,sm.FCustomerPhone as ��ϵ�绰,");
             sql.append(" mx.FAppDate as Ӧ������,mx.FAppAmount as Ӧ�ս��,mx.FActualFinishDate as ʵ������,mx.FBusinessName as ��������,");
             sql.append(" mx.FActRevAmount as ʵ�ս��,(mx.FAppAmount-mx.FActRevAmount) as Ƿ����,");
             sql.append(" DATEDIFF(day,  mx.FAppDate, getdate()) as Ƿ������,");
             sql.append(" pt.fname_l2 as �����,pt.fid as �����ID,");
             sql.append(" tt.Ƿ���� as Ƿ���ܽ��,md.fid as ��������ID,sp.fid as ������ĿID");
             sql.append(" from   T_SHE_Transaction zx");
             sql.append(" left join  T_SHE_SignManage sm on sm.fid = zx.fbillid ");
             sql.append(" left join  T_SHE_SellProject sp on sp.fid = sm.FSellProjectID ");
             sql.append(" left join  T_SHE_SHEPayType pt on pt.fid = sm.FPayTypeID ");
             sql.append(" left join  T_SHE_Room room on room.fid = zx.froomid");
             sql.append(" left join  T_SHE_TranBusinessOverView mx on zx.fid =mx.fheadid ");
             sql.append(" left join (select sum(mm.FAppAmount) as Ӧ�ս��,sum( mm.FActRevAmount) as ʵ�ս��, sum((mm.FAppAmount-mm.FActRevAmount)) as Ƿ����, zz.fid as ����ID from T_SHE_Transaction zz  left join  T_SHE_TranBusinessOverView mm on zz.fid =mm.fheadid group by zz.fid )tt on tt.����ID = zx.fid ");
             sql.append(" left join  T_SHE_MoneyDefine   md  on md.fid =mx.FMoneyDefineID");
             sql.append(" where (mx.FAppAmount-mx.FActRevAmount)>0 and ");
             sql.append(" sm.fBizState in ('SignApple','SignAuditing','SignAudit','ChangePirceAuditing','ChangeRoomAuditing','QuitRoomAuditing','ChangeNameAuditing')");
             sql.append(" and DATEDIFF(day,  mx.FAppDate, getdate())>0");
             sql.append(" order by zx.fid ) sg");
        }
        if(prePurchase.isSelected()==true){
        	if(sign.isSelected()==true){
        		sql.append(" UNION all ");
        	}
             sql.append(" select pre.����,pre.����ID,pre.�ͻ�,pre.ҵ������,pre.����ID,pre.ҵ����,pre.��ҵ����,pre.ҵ������ID,pre.��ͬ�ܼ�,pre.��ϵ�绰,pre.Ӧ������, ");
             sql.append(" pre.Ӧ�ս��,pre.ʵ������,pre.ʵ�ս��,pre.Ƿ����,pre.��������,pre.Ƿ������,pre.�����,pre.�����ID,pre.Ƿ���ܽ��,pre.��������ID,pre.������ĿID ");
             sql.append(" from ( select room.fname_l2 as ����,room.fid as ����ID,");
             sql.append(" zx.FCustomerNames as �ͻ�, zx.FTranDate as ҵ������,zx.fid as ����ID,");
             sql.append(" pm.fnumber as ҵ����,pm.FSaleManNames as ��ҵ����,pm.fid as ҵ������ID,");
             sql.append(" pm.FContractTotalAmount as ��ͬ�ܼ�,pm.FCustomerPhone as ��ϵ�绰, ");
             sql.append(" mx.FAppDate as Ӧ������,mx.FAppAmount as Ӧ�ս��,mx.FActualFinishDate as ʵ������,mx.FBusinessName as ��������, ");
             sql.append(" mx.FActRevAmount as ʵ�ս��,(mx.FAppAmount-mx.FActRevAmount) as Ƿ����,");
             sql.append(" DATEDIFF(day,  mx.FAppDate, getdate()) as Ƿ������, ");
             sql.append(" pt.fname_l2 as �����,pt.fid as �����ID,");
             sql.append(" tt.Ƿ���� as Ƿ���ܽ��,md.fid as ��������ID,sp.fid as ������ĿID");
             sql.append(" from   T_SHE_Transaction zx  ");
             sql.append(" left join  T_SHE_PrePurchaseManage pm on pm.fid = zx.fbillid ");
             sql.append(" left join  T_SHE_SellProject sp on sp.fid = pm.FSellProjectID ");
             sql.append(" left join  T_SHE_SHEPayType pt on pt.fid = pm.FPayTypeID ");
             sql.append("  join  T_SHE_Room room on room.fid = zx.froomid ");
             sql.append(" left join  T_SHE_TranBusinessOverView mx on zx.fid =mx.fheadid ");
             sql.append(" left join  (select sum(mm.FAppAmount) as Ӧ�ս��,sum( mm.FActRevAmount) as ʵ�ս��, sum((mm.FAppAmount-mm.FActRevAmount)) as Ƿ����, zz.fid as ����ID from      T_SHE_Transaction zz  left join  T_SHE_TranBusinessOverView mm on zz.fid =mm.fheadid group by zz.fid )tt on tt.����ID = zx.fid ");
             sql.append(" left join  T_SHE_MoneyDefine   md  on md.fid =mx.FMoneyDefineID");
             sql.append(" where (mx.FAppAmount-mx.FActRevAmount)>0 and ");
             sql.append(" pm.fBizState in ('PreApple','PreAuditing''PreAudit','ChangePirceAuditing','ChangeRoomAuditing','QuitRoomAuditing','ChangeNameAuditing') ");
             sql.append(" and DATEDIFF(day,  mx.FAppDate, getdate())>0 ");
             sql.append(" order by zx.fid ) pre");
        }
       if(purchase.isSelected()==true){
    	   if(sign.isSelected()==true){
       		sql.append(" UNION all ");
           }else if(prePurchase.isSelected()==true&&purchase.isSelected()==true){
        	   sql.append(" UNION all ");
          }
           sql.append(" select pur.����,pur.����ID,pur.�ͻ�,pur.ҵ������,pur.����ID,pur.ҵ����,pur.��ҵ����,pur.ҵ������ID,pur.��ͬ�ܼ�,pur.��ϵ�绰,pur.Ӧ������, ");
           sql.append(" pur.Ӧ�ս��,pur.ʵ������,pur.ʵ�ս��,pur.Ƿ����,pur.��������,pur.Ƿ������,pur.�����,pur.�����ID,pur.Ƿ���ܽ��,pur.��������ID,pur.������ĿID ");
           sql.append(" from ( select room.fname_l2 as ����,room.fid as ����ID,");
           sql.append(" zx.FCustomerNames as �ͻ�, zx.FTranDate as ҵ������,zx.fid as ����ID,");
           sql.append(" cm.fnumber as ҵ����,cm.FSaleManNames as ��ҵ����,cm.fid as ҵ������ID,");
           sql.append(" cm.FContractTotalAmount as ��ͬ�ܼ�,cm.FCustomerPhone as ��ϵ�绰, ");
           sql.append(" mx.FAppDate as Ӧ������,mx.FAppAmount as Ӧ�ս��,mx.FActualFinishDate as ʵ������,mx.FBusinessName as ��������, ");
           sql.append(" mx.FActRevAmount as ʵ�ս��,(mx.FAppAmount-mx.FActRevAmount) as Ƿ����,");
           sql.append(" DATEDIFF(day,  mx.FAppDate, getdate()) as Ƿ������, ");
           sql.append(" pt.fname_l2 as �����,pt.fid as �����ID,");
           sql.append(" tt.Ƿ���� as Ƿ���ܽ��, md.fid as ��������ID,sp.fid as ������ĿID");
           sql.append(" from   T_SHE_Transaction zx  ");
           sql.append(" left join  T_SHE_PurchaseManage cm on cm.fid = zx.fbillid ");
           sql.append(" left join  T_SHE_SellProject sp on sp.fid = cm.FSellProjectID ");
           sql.append(" left join  T_SHE_SHEPayType pt on pt.fid = cm.FPayTypeID ");
           sql.append(" left join  T_SHE_Room room on room.fid = zx.froomid ");
           sql.append(" left join  T_SHE_TranBusinessOverView mx on zx.fid =mx.fheadid ");
           sql.append(" left join  (select sum(mm.FAppAmount) as Ӧ�ս��,sum( mm.FActRevAmount) as ʵ�ս��, sum((mm.FAppAmount-mm.FActRevAmount)) as Ƿ����, zz.fid as ����ID from      T_SHE_Transaction zz  left join  T_SHE_TranBusinessOverView mm on zz.fid =mm.fheadid group by zz.fid )tt on tt.����ID = zx.fid ");
           sql.append(" left join  T_SHE_MoneyDefine   md  on md.fid =mx.FMoneyDefineID");
           sql.append(" where (mx.FAppAmount-mx.FActRevAmount)>0 and ");
           sql.append(" cm.fBizState in ('PurApple','PurAuditing''PurAudit','ChangePirceAuditing','ChangeRoomAuditing','QuitRoomAuditing','ChangeNameAuditing') ");
           sql.append(" and DATEDIFF(day,  mx.FAppDate, getdate())>0 ");
           sql.append(" order by zx.fid ) pur");
       }
        sql.append(" ) zz");
        sql.append(" where 1=1 and  zz.������ĿID ='"+sellProject.getId().toString()+"'");
        if(obj!=null && obj.length>0 && obj[0]!=null)
        	sql.append(" and zz.����ID in ("+sb.toString()+")");
        if(this.prmtMoneyDefine.getValue()!=null){
        	MoneyDefineInfo moeInfo = (MoneyDefineInfo)this.prmtMoneyDefine.getValue();
        	String  moneytId = (String)moeInfo.getId().toString();
        	sql.append(" and zz.��������ID in ('"+moneytId+"')");
        }
       if(pkBizDateFrom.getValue()!=null){
    	    Date from = (Date)pkBizDateFrom.getValue();
    		sql.append(" and zz.ҵ������  > {ts '"+ DateHelper.getSQLBegin(DateHelper.getNextDay(from))+"'}");
		}
       if(pkBizDateTo.getValue()!=null){
    	   Date to = (Date)pkBizDateTo.getValue();
   		   sql.append(" and zz.ҵ������  <= {ts '"+ DateHelper.getSQLBegin(DateHelper.getNextDay(to))+"'}");
		}
       if(txtArgDay.getValue()!=null){
    	    int dayFrom = ((BigDecimal)txtArgDay.getValue()).intValue();
     		sql.append(" and zz.Ƿ������  > "+dayFrom+"");
      }
       if(txtargDayTo.getValue()!=null){
    	   int dayTo = ((BigDecimal)txtargDayTo.getValue()).intValue();
     		sql.append(" and zz.Ƿ������  <= "+dayTo+"");
      }
       if(txtArgAmountFrom.getValue()!=null){
    	    BigDecimal AmountFrom = (BigDecimal)txtArgAmountFrom.getValue();
      		sql.append(" and zz.Ƿ���ܽ��  > "+AmountFrom+"");
       }
       if(txtArgAmountTo.getValue()!=null){
   	    BigDecimal AmountTo = (BigDecimal)txtArgAmountTo.getValue();
     		sql.append(" and zz.Ƿ���ܽ��  <= "+AmountTo+"");
       }
        FDCSQLBuilder db = new FDCSQLBuilder();
        db.appendSql(sql.toString());
        
        IRowSet rowset = db.executeQuery();
        Map t = new HashMap();
        while(rowset.next()){
        	String zxID = rowset.getString("����ID");
        	DefaultAmountMangerInfo info = (DefaultAmountMangerInfo) t.get(zxID);
        	if(info == null){
        		 info = new DefaultAmountMangerInfo();
        		 info.setCustomerNames(rowset.getString("�ͻ�"));
        		 info.setTelephone(rowset.getString("��ϵ�绰"));
        		 info.setArgAmount(rowset.getBigDecimal("Ƿ���ܽ��"));
        		 if(rowset.getString("�����ID")!=null){
        			 SHEPayTypeInfo payInfo = SHEPayTypeFactory.getRemoteInstance().getSHEPayTypeInfo(new ObjectUuidPK(rowset.getString("�����ID")));
            		 info.setPayment(payInfo);
        		 }
        		 info.setRemark(rowset.getString("ҵ����"));
        		 String id = rowset.getString("ҵ������ID");
        		 if(id!=null){
        			 Object billInfo = (Object) DynamicObjectFactory.getRemoteInstance().getValue(new ObjectUuidPK(id).getObjectType(),new ObjectUuidPK(id));
            		 if(billInfo instanceof SignManageInfo){ 
            			 info.setBusType("ǩԼ��");
            		 }
            		 if(billInfo instanceof PurchaseManageInfo){ 
            			 info.setBusType("�Ϲ���");
            		 }
            		 if(billInfo instanceof PrePurchaseManageInfo){ 
            			 info.setBusType("Ԥ����");
            		 }
        		 }
        		 info.setContractAmount(rowset.getBigDecimal("��ͬ�ܼ�"));
        		 info.setSaleManNames(rowset.getString("��ҵ����"));
        		 info.setBizDate(rowset.getDate("ҵ������"));
        		 RoomInfo roomInfo =RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(rowset.getString("����ID")));
        		 info.setRoom(roomInfo);
        		 TransactionInfo tranInfo = TransactionFactory.getRemoteInstance().getTransactionInfo(new ObjectUuidPK(rowset.getString("����ID")));
        		 info.setTransaction(tranInfo);
        		 SellProjectInfo sellInfo = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(rowset.getString("������ĿID")));
        		 info.setProject(sellInfo);
        		 info.setBillId(rowset.getString("ҵ������ID"));
        		 info.setSubDeAmount(new BigDecimal(0.00));
        		 info.setId(BOSUuid.create(info.getBOSType()));
        		 info.setNumber(BOSUuid.create(info.getBOSType()).toString());
        		 info.setName(BOSUuid.create(info.getBOSType()).toString());
        	}
        	
        	 DefaultAmountMangerEntryInfo entryInfo = new DefaultAmountMangerEntryInfo();
        	 entryInfo.setHead(info);
        	 MoneyDefineInfo mdInfo =MoneyDefineFactory.getRemoteInstance().getMoneyDefineInfo(new ObjectUuidPK(rowset.getString("��������ID")));
        	 entryInfo.setMoneyDefine(mdInfo);
        	 entryInfo.setAppDate(rowset.getDate("Ӧ������"));
        	 entryInfo.setAppAmount(rowset.getBigDecimal("Ӧ�ս��"));
        	 entryInfo.setActDate(rowset.getDate("ʵ������"));
        	 entryInfo.setActAmount(rowset.getBigDecimal("ʵ�ս��"));
        	 entryInfo.setArgAmount(rowset.getBigDecimal("Ƿ����"));
        	 entryInfo.setArgDays(rowset.getInt("Ƿ������"));
			 info.getEntry().add(entryInfo);
			 t.put(zxID, info);
         }
        Set s = t.keySet();
        for(Iterator itor = s.iterator(); itor.hasNext(); ){
        	DefaultAmountMangerInfo deInfo = (DefaultAmountMangerInfo) t.get(itor.next());
        	IRow row = kDTable1.addRow();
        	kDTable1.getStyleAttributes().setLocked(true);
        	row.getCell("room").setValue(deInfo.getRoom().getName());
        	row.getCell("customerNames").setValue(deInfo.getCustomerNames());
        	row.getCell("telePhone").setValue(deInfo.getTelephone());
			row.getCell("bizDate").setValue(deInfo.getBizDate());
			row.getCell("number").setValue(deInfo.getRemark());
			row.getCell("contractAmount").setValue(deInfo.getContractAmount());
			row.getCell("argAmount").setValue(deInfo.getArgAmount());
			row.getCell("busType").setValue(deInfo.getBusType());
			row.getCell("saleManNames").setValue(deInfo.getSaleManNames());
			row.getCell("id").setValue(deInfo.getTransaction().getId());
			row.setUserObject(deInfo);
        	dams.add(deInfo);
        }
    }

	protected void kDTable1_tableClicked(KDTMouseEvent e) throws Exception {
		super.kDTable1_tableClicked(e);
		
	}
	protected ArrayList getSelectedIdValues()
	{
	    ArrayList selectList = new ArrayList();
	    int selectRows[] = KDTableUtil.getSelectedRows(kDTable1);
	    for(int i=0;i<selectRows.length;i++){
	    	String id= (String)kDTable1.getRow(selectRows[i]).getCell("id").getValue();
	    	selectList.add(id);
	    }
	    return selectList;
	}
	protected void attachListeners() {
		
	}

	protected void detachListeners() {
		
	}

	protected ICoreBase getBizInterface() throws Exception {
		return DefaultAmountMangerFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return null;
	}
	DefaultAmountMangerCollection dams = new DefaultAmountMangerCollection();
	/**
     * ȷ��
     */
	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception 
	{
		super.actionConfirm_actionPerformed(e);
//		getSelectedIdValues();
		int rowIndex = kDTable1.getSelectManager().getActiveRowIndex();
		if(rowIndex ==-1){
			  MsgBox.showInfo("������ѡ��һ��Ƿ����ϸ!");
		      SysUtil.abort();
		}
		if(kDTable1.getRowCount()!=0){
			UIContext context = new UIContext(this);
			context.put("createInfo", setDefaultAmountCreatInfo());
			context.put("damColl", setAmountMangerCollection());
			context.put("SellProject" ,sellProject.getId());
		    UIFactory.createUIFactory(UIFactoryName.MODEL).create(DefaultAmountCreatEditUI.class.getName(),context,null,OprtState.ADDNEW).show();
		}
		else{
			  MsgBox.showInfo("������һ��Ƿ����ϸ����ȷ��!");
		      SysUtil.abort();
		}
	}
	public DefaultAmountMangerCollection setAmountMangerCollection(){
		DefaultAmountMangerCollection damColl = new DefaultAmountMangerCollection();
		int selectRows[] = KDTableUtil.getSelectedRows(kDTable1);
	    for(int i=0;i<selectRows.length;i++){
	    	damColl.add((DefaultAmountMangerInfo)kDTable1.getRow(selectRows[i]).getUserObject());
	    }
		return damColl;
	}
	public DefaultAmountCreatInfo setDefaultAmountCreatInfo() throws EASBizException, BOSException{
		DefaultAmountCreatInfo createInfo = new DefaultAmountCreatInfo();
		int selectRows[] = KDTableUtil.getSelectedRows(kDTable1);
		SellProjectInfo sellInfo = null;
	    for(int i=0;i<selectRows.length;i++){
	    	IRow row = kDTable1.getRow(selectRows[i]);
	    	DefaultAmountCreatEntryInfo entry = new DefaultAmountCreatEntryInfo();
	    	DefaultAmountMangerInfo amInfo = (DefaultAmountMangerInfo)kDTable1.getRow(selectRows[i]).getUserObject();
	    	sellInfo = amInfo.getProject();
	    	entry.setCustomerNames(amInfo.getCustomerNames());
	    	entry.setRoom(amInfo.getRoom());
	    	entry.setTelephone(amInfo.getTelephone());
	    	entry.setBizDate(amInfo.getBizDate());
	    	entry.setNumber(amInfo.getRemark());
	    	entry.setContractAmount(amInfo.getContractAmount());
	    	entry.setArgAmount(amInfo.getArgAmount());
	    	entry.setBusType(amInfo.getBusType());
	    	entry.setSaleManNames(amInfo.getSaleManNames());
	    	entry.setArgAmount(amInfo.getArgAmount());
	    	entry.setSubDeAmount(amInfo.getSubDeAmount());
	    	entry.setRemak(amInfo.getTransaction().getId().toString());
   		 	createInfo.getParent().add(entry);
	    }
	    createInfo.setProject(sellInfo);
		return createInfo;
	}
	/**
     * ȡ��
     */
	public void actionCancels_actionPerformed(ActionEvent e) throws Exception 
	{
//		super.actionCancels_actionPerformed(e);
//		kDTable1.removeRows();
		super.actionExitCurrent_actionPerformed(e);
	}
	protected  void fetchInitData() throws Exception{
		
	}
	 public SelectorItemCollection getSelectors() {
		 SelectorItemCollection sic = super.getSelectors();
		 sic.add("room.*");
		 sic.add("area.*");
	     return sic;
	}   

	protected void prmtRoom_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtRoom_dataChanged(e);

	}
	/**
     * ҵ������  ��
     */
	protected void pkBizDateFrom_dataChanged(DataChangeEvent e)throws Exception {
		super.pkBizDateFrom_dataChanged(e);
		if(this.pkBizDateFrom.getValue()!=null&&this.pkBizDateTo.getValue()!=null){
			Calendar dateFrom = Calendar.getInstance();
			Calendar dateTo = Calendar.getInstance();
			dateFrom.setTime((Date)pkBizDateFrom.getValue());
			dateTo.setTime((Date)pkBizDateTo.getValue());
			if(dateFrom.compareTo(dateTo)>0){
				MsgBox.showInfo("ǰ������ӦС�ں�������!");
				pkBizDateFrom.setValue(null);
				SysUtil.abort();
			}
		} 
		
	}
	/**
     * ҵ������  ��
     */
	protected void pkBizDateTo_dataChanged(DataChangeEvent e) throws Exception {
		super.pkBizDateTo_dataChanged(e);
		if(this.pkBizDateFrom.getValue()!=null&&this.pkBizDateTo.getValue()!=null){
			Calendar dateFrom = Calendar.getInstance();
			Calendar dateTo = Calendar.getInstance();
			dateFrom.setTime((Date)pkBizDateFrom.getValue());
			dateTo.setTime((Date)pkBizDateTo.getValue());
			if(dateFrom.compareTo(dateTo)>0){
				MsgBox.showInfo("��������Ӧ����ǰ������!");
				pkBizDateTo.setValue(null);
				SysUtil.abort();
			}
		}
	}
	/**
     * Ƿ���� ��  
     */
	protected void txtArgAmountFrom_dataChanged(DataChangeEvent e)throws Exception {
		super.txtArgAmountFrom_dataChanged(e);
		if(this.txtArgAmountFrom.getValue()!=null&&this.txtArgAmountTo.getValue()!=null){
			 BigDecimal AmountFrom = (BigDecimal)txtArgAmountFrom.getValue();
			 BigDecimal AmountTo = (BigDecimal)txtArgAmountTo.getValue();
			 if(AmountFrom.compareTo(AmountTo)>0){
				  MsgBox.showInfo("ǰ�߽��ӦС�ں��߽��!");
				  txtArgAmountFrom.setValue(null);
			      SysUtil.abort();
			 }
		}
	}
	/**
     * Ƿ���� ��
     */
	protected void txtArgAmountTo_dataChanged(DataChangeEvent e)throws Exception {
		super.txtArgAmountTo_dataChanged(e);
		if(this.txtArgAmountFrom.getValue()!=null&&this.txtArgAmountTo.getValue()!=null){
			 BigDecimal AmountFrom = (BigDecimal)txtArgAmountFrom.getValue();
			 BigDecimal AmountTo = (BigDecimal)txtArgAmountTo.getValue();
			 if(AmountFrom.compareTo(AmountTo)>0){
				  MsgBox.showInfo("���߽��Ӧ����ǰ�߽��!");
				  txtArgAmountTo.setValue(null);
			      SysUtil.abort();
			 }
		}
	}
	/**
     * ΥԼ���� ��
     */
	protected void txtArgDay_dataChanged(DataChangeEvent e) throws Exception {
		super.txtArgDay_dataChanged(e);
		if(this.txtArgDay.getValue()!=null&&this.txtargDayTo.getValue()!=null){
			 int dayFrom = ((BigDecimal)txtArgDay.getValue()).intValue();
			 int dayTo= ((BigDecimal)txtargDayTo.getValue()).intValue();
			 if(dayFrom>dayTo){
				 MsgBox.showInfo("ǰ������ӦС�ں�������!");
				 txtArgDay.setValue(null);
			      SysUtil.abort();
			 }
		}
	}
	/**
     * ΥԼ���� ��
     */
	protected void txtargDayTo_dataChanged(DataChangeEvent e) throws Exception {
		super.txtargDayTo_dataChanged(e);
		if(this.txtArgDay.getValue()!=null&&this.txtargDayTo.getValue()!=null){
			 int dayFrom = ((BigDecimal)txtArgDay.getValue()).intValue();
			 int dayTo= ((BigDecimal)txtargDayTo.getValue()).intValue();
			 if(dayFrom>dayTo){
				 MsgBox.showInfo("��������Ӧ����ǰ������!");
				 txtargDayTo.setValue(null);
			      SysUtil.abort();
			 }
		}
	}

	/**
     * 
     */
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {
		
	}
}