package com.kingdee.eas.fdc.sellhouse.app;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDesCollection;
import com.kingdee.eas.fdc.sellhouse.RoomDesInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;

public class RoomDesControllerBean extends AbstractRoomDesControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.RoomDesControllerBean");

	protected void _checkNameBlank(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
	}

	protected void _checkNameDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
	}

	protected void _checkNumberBlank(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
	}

	protected void _checkNumberDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

	}
	protected void updateCheck(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
	}

	protected boolean _cleanBuilding(Context ctx, IObjectValue building) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		BuildingInfo build = (BuildingInfo)building;
		String buildingId = null;
		if(null!=((BuildingInfo)building).getId()){
			buildingId = ((BuildingInfo)building).getId().toString();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("building",buildingId));
			RoomFactory.getLocalInstance(ctx).delete(filter);
			BuildingUnitFactory.getLocalInstance(ctx).delete(filter);
			build.setUnitCount(0);
//			BuildingFactory.getLocalInstance(ctx).update(new ObjectUuidPK(build.getId()), build);
			SelectorItemCollection selector =new SelectorItemCollection();
			selector.add("unitCount");
			BuildingFactory.getLocalInstance(ctx).updatePartial(build, selector);
			this._delete(ctx, filter);
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * <p>���鷳
	 * ��Ҫ��������Ҫ�޸ģ��ж�û��ID����ID���Ƿ�ɾ���ȵȵȵȣ��벻���������鷳
	 * @description 1.��Ϊ��Ԫһ��һ����û��ID�����жϵ�Ԫ��û�в�����ID��Ҫ��seq
	 *              2.param buildingUnitӦ�ÿ��Բ��ã��������
	 * @author tim_gao       
	 */
	protected boolean _roomDesSumbit(Context ctx, IObjectValue building, IObjectCollection Roomdes, IObjectCollection buildingUnit) throws BOSException, EASBizException {
		BuildingInfo build =(BuildingInfo)building;
		RoomDesCollection roomDesColl = (RoomDesCollection)Roomdes;
		BuildingUnitCollection buildingUnitColl = (BuildingUnitCollection)buildingUnit;
		//�ȵõ�ԭ��¥���µ����е�Ԫ
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("building",build.getId().toString()));
		view.setFilter(filter);
		BuildingUnitCollection dataUnitCol = null;
		Set delSet = new HashSet();
		List roomDesUpdList = new ArrayList();
		for(int i = 0 ; i< roomDesColl.size() ;i++){
			RoomDesInfo roomdes = roomDesColl.get(i);
			boolean isAddUnit = true;
			if(null!=roomdes.getUnit()){
				//ÿ��һ��ѭ��Ҫ������һ�Σ���Ϊ�����������ǿ�������ͬ��ǰһ������
				dataUnitCol = BuildingUnitFactory.getLocalInstance(ctx).getBuildingUnitCollection(view);
				BuildingUnitInfo dataUnit =null;
				for(int d =0 ; d<dataUnitCol.size(); d++){
					 dataUnit = dataUnitCol.get(d);
					if(dataUnit.getSeq()==roomdes.getUnit().getSeq()){
						isAddUnit = false;
						break;
					}
				}
				BuildingUnitInfo tempUnit = roomdes.getUnit();
					if(isAddUnit){//����
						IObjectPK idPk = BuildingUnitFactory.getLocalInstance(ctx).submit(roomdes.getUnit());
						tempUnit.setId(BOSUuid.read(idPk.getKeyValue(null).toString()));
						roomdes.setUnit(tempUnit);
					}else{//�޸�
						if(null!=dataUnit){
							//��Ϊ��ͬ��ID�������䶨�嵥�����Ϊ�����ܶ����ͬIDʱ�������û��ID
							roomdes.getUnit().setId(dataUnit.getId());
							if(!dataUnit.getName().equals(roomdes.getUnit().getName())
									||!(dataUnit.isHaveUnit()==roomdes.getUnit().isHaveUnit())){//���ֲ�ͬʱ,�Ƿ���ʾ��Ԫ�б仯ʱ�޸�
								//������updateҪ��selector ��Ȼ�ἶ�����£���պܶණ��
								SelectorItemCollection selector = new SelectorItemCollection();
								selector.add("name");
								selector.add("haveUnit");
//								BuildingUnitFactory.getLocalInstance(ctx).
//								update(new ObjectUuidPK(roomdes.getUnit().getId()),roomdes.getUnit());
								BuildingUnitFactory.getLocalInstance(ctx).updatePartial(roomdes.getUnit(), selector);
							}
							
						}
							
						
					}
				
				
			}
			if(null==roomdes.getId()){
				IObjectPK idPk = _submit(ctx,roomdes);
				roomdes.setId(BOSUuid.read(idPk.getKeyValue(null).toString()));
			}else{
				//���ַ��������޷����³��򣬸Ľ�sqlƴװ
//				SelectorItemCollection selec = new SelectorItemCollection();
//			
////				selec.add("*");
////				selec.add("roomModel");
////				selec.add("buildingProperty");
////				selec.add("productType");
//				selec.add("unitBegin");
//				selec.add("unitEnd");
//				selec.add("floorBegin");
//				selec.add("floorEnd");
//				selec.add("natrueFloorBegin");
//				selec.add("natrueFloorEnd");
//				selec.add("serialNumberBegin");
//				selec.add("serialNumberEnd");
//				selec.add("buildingArea");
//				selec.add("roomArea");
//				selec.add("apportionArea");
//				selec.add("balconyArea");
//				selec.add("floorHeight");
//				selec.add("roomModel.*");
//				selec.add("buildingProperty.*");
//				selec.add("houseProperty");
//				
//				selec.add("direction.*");
//				
//				selec.add("actualBuildingArea");
//				selec.add("actualRoomArea");
//			
//				selec.add("sight.*");
//				selec.add("numPrefixType");
//				selec.add("productType.*");
//				selec.add("isForSHE");
//				selec.add("isForTen");
//				selec.add("isForPPM");
//				selec.add("noise.*");
//				selec.add("decoraStd.*");
//				selec.add("eyeSignht.*");
//				selec.add("posseStd.*");
//				selec.add("roomUsage.*");
//				selec.add("flatArea");
//				selec.add("carbarnArea");
//				selec.add("planRoomArea");
//				selec.add("planBuildingArea");
//				selec.add("userArea");
//				selec.add("guardenArea");
//				selec.add("buildStruct.*");
//				selec.add("bizDate");
//				selec.add("newNoise.*");
//				selec.add("newSight.*");
//				selec.add("newEyeSight.*");
//				selec.add("newDecorastd.*");
//				selec.add("newPossstd.*");
//				selec.add("newRoomUsage.*");
//				selec.add("newProduceRemark.*");
//				selec.add("isConvertToChar");
////				selec.add("unit");
//				selec.add("unit.*");
//				this._updatePartial(ctx, roomdes, selec);
				roomDesUpdList.add(roomdes);
			
			}
			
			delSet.add(roomdes.getId().toString());
		}
		
		//ÿ����Ҫ���µķ��䶨��
		if (roomDesUpdList != null && roomDesUpdList.size() > 0) {
			try {
				String sql = "update T_SHE_RoomDes set funitBegin=?,funitEnd=?,fFloorBegin=? ,ffloorEnd = ?,fSerialNumberBegin =? , fSerialNumberEnd = ?" +
						",fnatrueFloorBegin = ?,fnatrueFloorEnd = ?,fbuildingArea = ?,froomArea = ? ,fapportionArea = ?,fbalconyArea = ?" +
						",ffloorHeight = ?,fRoomModelID = ? ,fBuildingPropertyID = ?,fhouseProperty = ?,fDirectionID = ?,factualBuildingArea = ?" +
						",fActualRoomArea = ?,fsightid = ? ,fnumPrefixType = ?,fproductTypeid = ?,fisForSHE = ?,fisForTen = ? , fisForPPM = ?" +
						",fNoiseID = ? , fDecoraStdID = ? , fEyeSignhtID = ? , fPosseStdID = ? , fRoomUsageID = ? , fFlatArea = ?" +
						",fCarbarnArea = ? , fPlanRoomArea = ?, fplanBuildingArea = ? , fUseArea= ? , fguardenArea = ? , fBuilStructID = ?" +
						",fbizDate = ? ,fnewNoiseid = ? ,fnewSightid =? , fnewEyeSightid = ? , fnewDecorastdid = ? , fnewPossstdid = ? " +
						",fnewRoomUsageid = ? , fNewProduceRemarkID = ? ,fIsConvertToChar = ?, fUnitID = ?"+
						" where fid=?";
				FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
				sqlBuilder.setPrepareStatementSql(sql);
				sqlBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
		
				for (int i = 0; i < roomDesUpdList.size(); i++) {
					RoomDesInfo info = (RoomDesInfo) roomDesUpdList.get(i);
					sqlBuilder.addParam(new Integer(info.getUnitBegin()));
					sqlBuilder.addParam(new Integer(info.getUnitEnd()));
					sqlBuilder.addParam(new Integer(info.getFloorBegin()));
					sqlBuilder.addParam(new Integer(info.getFloorEnd()));
					sqlBuilder.addParam(new Integer(info.getSerialNumberBegin()));
					sqlBuilder.addParam(new Integer(info.getSerialNumberEnd()));
					sqlBuilder.addParam(new Integer(info.getNatrueFloorBegin()));
					sqlBuilder.addParam(new Integer(info.getNatrueFloorEnd()));
					sqlBuilder.addParam(info.getBuildingArea());
					sqlBuilder.addParam(info.getRoomArea());
					sqlBuilder.addParam(info.getApportionArea());
					sqlBuilder.addParam(info.getBalconyArea());
					sqlBuilder.addParam(info.getFloorHeight());
					sqlBuilder.addParam(info.getRoomModel()!=null?info.getRoomModel().getId().toString():null);
					sqlBuilder.addParam(info.getBuildingProperty()!=null?info.getBuildingProperty().getId().toString():null);
					sqlBuilder.addParam(info.getHouseProperty());
					sqlBuilder.addParam(info.getDirection()!=null?info.getDirection().getId().toString():null);
					sqlBuilder.addParam(info.getActualBuildingArea());
					sqlBuilder.addParam(info.getActualRoomArea());
					sqlBuilder.addParam(info.getSight()!=null?info.getSight().getId().toString():null);
					sqlBuilder.addParam(info.getNumPrefixType());
					sqlBuilder.addParam(info.getProductType()!=null?info.getProductType().getId().toString():null);
					sqlBuilder.addParam(Boolean.TRUE);
					sqlBuilder.addParam(Boolean.FALSE);
					sqlBuilder.addParam(Boolean.FALSE);
					sqlBuilder.addParam(info.getNoise()!=null?info.getNoise().getId().toString():null);
					sqlBuilder.addParam(info.getDecoraStd()!=null?info.getDecoraStd().getId().toString():null);
					sqlBuilder.addParam(info.getEyeSignht()!=null?info.getEyeSignht().getId().toString():null);
					sqlBuilder.addParam(info.getPosseStd()!=null?info.getPosseStd().getId().toString():null);
					sqlBuilder.addParam(info.getRoomUsage()!=null?info.getRoomUsage().getId().toString():null);
					sqlBuilder.addParam(info.getFlatArea());
					sqlBuilder.addParam(info.getCarbarnArea());
					sqlBuilder.addParam(info.getPlanRoomArea());
					sqlBuilder.addParam(info.getPlanBuildingArea());
					sqlBuilder.addParam(info.getUserArea());
					sqlBuilder.addParam(info.getGuardenArea());
					sqlBuilder.addParam(info.getBuildStruct()!=null?info.getBuildStruct().getId().toString():null);
					sqlBuilder.addParam(info.getBizDate());
					sqlBuilder.addParam(info.getNewNoise()!=null?info.getNewNoise().getId().toString():null);
					sqlBuilder.addParam(info.getNewSight()!=null?info.getNewSight().getId().toString():null);
					sqlBuilder.addParam(info.getNewEyeSight()!=null?info.getNewEyeSight().getId().toString():null);
					sqlBuilder.addParam(info.getNewDecorastd()!=null?info.getNewDecorastd().getId().toString():null);
					sqlBuilder.addParam(info.getNewPossstd()!=null?info.getNewPossstd().getId().toString():null);
					sqlBuilder.addParam(info.getNewRoomUsage()!=null?info.getNewRoomUsage().getId().toString():null);
					sqlBuilder.addParam(info.getNewProduceRemark()!=null?info.getNewProduceRemark().getId().toString():null);
					sqlBuilder.addParam(info.isIsConvertToChar()==true?Boolean.TRUE:Boolean.FALSE);
					sqlBuilder.addParam(info.getUnit()!=null?info.getUnit().getId().toString():null);
					sqlBuilder.addParam(info.getId().toString());
				
					
					sqlBuilder.addBatch();
				}
				sqlBuilder.executeBatch();
			} catch (BOSException ex) {
				logger.error(ex.getMessage() + "���·�����Ϣʧ��!");
				throw new BOSException(ex.getMessage() + "���·�����Ϣʧ��!");
			}
		}
		
		
		//�������жϣ����֮ǰ���ڱ����ύ��û����ɾ����֮ǰ�Ǹ���Ԫ
		//Ҳ�����������ѭ������ԭ��¥���µĵ�Ԫ���жϣ���Ϊ���ԭ��û�еĻ��������ѭ���Ͳ�����
		Set unitDel = new HashSet();
		for(int d =0 ; d<dataUnitCol.size(); d++){
			boolean isDel =true;
			BuildingUnitInfo dataUnit = dataUnitCol.get(d);
			for(int i = 0 ; i< roomDesColl.size() ;i++){
				RoomDesInfo roomdes = roomDesColl.get(i);
				if(roomdes.getUnit().getSeq()==dataUnit.getSeq()){//���������ɾ�����
					isDel = false;
					
				}
			}
				if(isDel){
				//ɾ������Ӧ����
					unitDel.add(dataUnit.getId().toString());
//				BuildingUnitFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(dataUnit.getId()));
				}
		}
		//ɾ������û�еĵ�Ԫ
		if(null!=unitDel&&unitDel.size()>0){
			FilterInfo filterinfo = new FilterInfo();
			filterinfo.getFilterItems().add(new FilterItemInfo("id",unitDel,CompareType.INCLUDE)); 
			filterinfo.getFilterItems().add(new FilterItemInfo("building.id",build.getId().toString()));
			BuildingUnitFactory.getLocalInstance(ctx).delete(filterinfo);
		}
		
		//ɾ�������ύû�еķ��䶨�����
		
		FilterInfo filterinfo = new FilterInfo();
		filterinfo.getFilterItems().add(new FilterItemInfo("id",delSet,CompareType.NOTINCLUDE)); 
		filterinfo.getFilterItems().add(new FilterItemInfo("building.id",build.getId().toString()));
		this._delete(ctx, filterinfo);
		
		BuildingUnitCollection units = BuildingUnitFactory.getLocalInstance(ctx).getBuildingUnitCollection(view);
		
		build.setUnitCount(units.size());
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("unitCount");//������updateҪ��selector ��Ȼ�ἶ�����£���պܶණ��
		BuildingFactory.getLocalInstance(ctx).updatePartial(build, sel);
		return true;
		
	}
}