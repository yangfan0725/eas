package com.kingdee.eas.fdc.invite.supplier.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.json.JSONUtils;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.forewarn.ForewarnRunTimeFactory;
import com.kingdee.eas.basedata.assistant.CityCollection;
import com.kingdee.eas.basedata.assistant.CityFactory;
import com.kingdee.eas.basedata.assistant.CityInfo;
import com.kingdee.eas.basedata.assistant.ProvinceCollection;
import com.kingdee.eas.basedata.assistant.ProvinceFactory;
import com.kingdee.eas.basedata.assistant.ProvinceInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.markesupplier.uitl.StringUtils;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAreaCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAreaFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAreaInfo;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpCollection;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpFactory;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo;
import com.kingdee.eas.fdc.invite.supplier.IQuaLevel;
import com.kingdee.eas.fdc.invite.supplier.InvitationInfoFactory;
import com.kingdee.eas.fdc.invite.supplier.InvitationInfoInfo;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fdc.invite.supplier.QuaLevelCollection;
import com.kingdee.eas.fdc.invite.supplier.QuaLevelFactory;
import com.kingdee.eas.fdc.invite.supplier.QuaLevelInfo;
import com.kingdee.eas.fdc.invite.supplier.RegistStateEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierBusinessModeCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierBusinessModeFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierBusinessModeInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierChangeConfirmFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierChangeConfirmInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierChangeEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierChangeEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierQuaLevelEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierQuaLevelEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStateEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fdc.invite.supplier.TenderInfoFactory;
import com.kingdee.eas.fdc.invite.supplier.TenderInfoInfo;
import com.kingdee.eas.fdc.invite.supplier.WebUserFactory;
import com.kingdee.eas.fdc.invite.supplier.WebUserInfo;
import com.kingdee.eas.fdc.invite.supplier.app.webservice.vo.BusinessModeVO;
import com.kingdee.eas.fdc.invite.supplier.app.webservice.vo.CityVO;
import com.kingdee.eas.fdc.invite.supplier.app.webservice.vo.ProvinceVO;
import com.kingdee.eas.fdc.invite.supplier.app.webservice.vo.QuoLevelVO;
import com.kingdee.eas.fdc.invite.supplier.app.webservice.vo.SplAreaVO;
import com.kingdee.eas.fdc.invite.supplier.app.webservice.vo.SupplierGradeVO;
import com.kingdee.eas.fdc.invite.supplier.app.webservice.vo.SupplierVO;

public class WebInvitationWSFacadeControllerBean extends
		AbstractWebInvitationWSFacadeControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.invite.supplier.app.WebInvitationWSFacadeControllerBean");

	private static String [] modifyProperties = new String[]{"province","city","linkPhone","linkFax","taxerQua"};
	
	@Override
	protected String _changePassword(Context ctx, String param)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("code", "-1");
		HashMap<String,String> userMap = (HashMap<String, String>) JSONUtils.convertJsonToObject(ctx, param);
		try{
			
			if(!userMap.isEmpty()){
				String id = userMap.get("id");
				String password = userMap.get("password");
				
				if(StringUtils.isEmpty(id)){
					throw new ContractException(ContractException.WITHMSG,new String[]{"用户ID不能为空"});
				}
				if(StringUtils.isEmpty(password)){
					throw new ContractException(ContractException.WITHMSG,new String[]{"用户密码不能为空"});
				}
				
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder.appendSql("update t_gys_webuser set fpassword=? where fwebuserid = ?");
				builder.addParam(password);
				builder.addParam(id);
				
				builder.executeUpdate();
				resultMap.put("code", "0");
			}
		}catch (Exception e) {
			resultMap.put("msg", e.getMessage());
		}
		
		
		
		return JSONUtils.convertObjectToJson(ctx, resultMap);
	}
	
	
	/**
	 * @param supplier {"id":"xxxx","province":"","city":"","linkPhone":"","linkFax":""}
	 * 
	 * {
    "id": "HScAAAAMX9Q7BBBs",
    "province": "HScAAAAK0zCBjcr7",
    "city": "HScAAAAK1HoMXda2",
    "enterpriseKind": "2",
    "quaLevel": "HScAAAALroHnVQT/",
    "supplierBusinessMode": "HScAAAAKmVvvv1en",
    "bizRegisterNo": "无",
    "enterpriseMaster": "冼焯深",
    "linkPhone": "无",
    "linkFax": "无",
    "address": "深圳市福田区泰然九路212栋815单元",
    "registerMoney": "1200.00",
    "punish": "否",
    "contractor": "无",
    "contractorPhone": "无",
    "manager": "",
    "managerPhone": "",
    "recommoned": ""
}
	 * 
	 * 
	 * 
	 * 
	 */
	protected String _saveSupplier(Context ctx, String supplier)
			throws BOSException, EASBizException {
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("code", "-1");
		HashMap<String,Object> supplierMap = (HashMap<String, Object>) JSONUtils.convertJsonToObject(ctx, supplier);
		IQuaLevel iquaLevel = QuaLevelFactory.getLocalInstance(ctx);
				
		try{
			if(!supplierMap.isEmpty()){
				
				String supplierId = supplierMap.get("id")+"";
				if(StringUtils.isEmpty(supplierId)){
					throw new  ContractException(ContractException.WITHMSG,new String[]{"供应商ID不能为空"});
				}
				SupplierStockInfo supplierInfo = SupplierStockFactory.getLocalInstance(ctx).getSupplierStockInfo(new ObjectUuidPK(BOSUuid.read(supplierId)));
				if(supplierInfo == null){
					throw new  ContractException(ContractException.WITHMSG,new String[]{"非法供应商信息"});
				}
				
				//构建供应商变更审批单信息
				
				SupplierChangeConfirmInfo confirmInfo = new SupplierChangeConfirmInfo();
				confirmInfo.setSupplier(supplierInfo);
				
				OrgUnitInfo org = FullOrgUnitFactory.getLocalInstance(ctx).getOrgUnitInfo(new ObjectUuidPK(BOSUuid.read(OrgConstants.DEF_CU_ID)));
				confirmInfo.setOrgUnit(org.castToFullOrgUnitInfo());
				confirmInfo.setCU(org.getCU());
				
				
				SupplierChangeEntryCollection  changeEntrys = confirmInfo.getEntry();
				
				SupplierChangeEntryInfo entryInfo = null;
				
				
				
				Object value = null;
				Set<String> keySet = supplierMap.keySet();
				Iterator<String> it = keySet.iterator();
				String key = null;
				
				for(;it.hasNext();){
					entryInfo = new SupplierChangeEntryInfo();
					key = it.next();
					value = supplierMap.get(key);
					if(key.equalsIgnoreCase("id")){
						continue;
					}
					if(value == null){
						continue;
					}
					
					
					
					if(key.equalsIgnoreCase("quaLevel")){
						StringBuffer str = new StringBuffer();
						List<String> qualevel = (List<String>)supplierMap.get("quaLevel");
						int size = qualevel.size();
						String s = null;
						for(int i = 0;i<size;i++){
							s = qualevel.get(i);
							str.append(s);
							if(i<size-1){
								str.append(",");
							}
						}
						entryInfo.setPropertyName(key);
						entryInfo.setPropertyValue(str.toString());
						changeEntrys.add(entryInfo);
						continue;
					}
					entryInfo.setPropertyName(key);
					entryInfo.setPropertyValue(value+"");
					
//					if(key.equalsIgnoreCase("province")){
//						ProvinceInfo province = ProvinceFactory.getLocalInstance(ctx).getProvinceInfo(new ObjectUuidPK(BOSUuid.read(value+"")));
//						supplierInfo.put(key, province);
//						continue;
//					}
//					if(key.equalsIgnoreCase("supplierBusinessMode")){
// 					    SupplierBusinessModeInfo supplierBusinessMode = SupplierBusinessModeFactory.getLocalInstance(ctx).getSupplierBusinessModeInfo(new ObjectUuidPK(BOSUuid.read(value+"")));
//						supplierInfo.put(key, supplierBusinessMode);
//						continue;
//					}
//					if(key.equalsIgnoreCase("quaLevel")){
//						QuaLevelInfo quaLevelInfo = QuaLevelFactory.getLocalInstance(ctx).getQuaLevelInfo(new ObjectUuidPK(BOSUuid.read(value+"")));
//						supplierInfo.put(key, quaLevelInfo);
//						continue;
//					}
//					
//					if(key.equalsIgnoreCase("city")){
//						CityInfo city = CityFactory.getLocalInstance(ctx).getCityInfo(new ObjectUuidPK(BOSUuid.read(value+"")));
//						supplierInfo.setCity(city);
//					}
//					
//					supplierInfo.put(key, value);
					changeEntrys.add(entryInfo);
				}
				
				confirmInfo.setName(UUID.randomUUID().toString());
				confirmInfo.setNumber(UUID.randomUUID().toString());
				SupplierChangeConfirmFactory.getLocalInstance(ctx).submit(confirmInfo);
				resultMap.put("code", "0");
				ForewarnRunTimeFactory.getRemoteInstance().execRightnowForewarn("com.kingdee.eas.fdc.invite.supplier.client.SupplierChangeConfirmEditUI", "ActionSave", confirmInfo.getId().toString());
			}
			
			
		}catch(Exception e){
			resultMap.put("msg", e.getMessage());
		}
		
		return JSONUtils.convertObjectToJson(ctx, resultMap);
	}
	
	
	protected String _apply(Context ctx, String applyParam) throws BOSException ,EASBizException {
		OrgUnitInfo org = FullOrgUnitFactory.getLocalInstance(ctx).getOrgUnitInfo(new ObjectUuidPK(BOSUuid.read(OrgConstants.DEF_CU_ID)));
		HashMap<String,String> tenderMap = (HashMap<String, String>) JSONUtils.convertJsonToObject(ctx, applyParam);
		Map<String,String> result = new HashMap<String,String>();
		result.put("code", "-1");
		try{
			
			if(!tenderMap.isEmpty()){
				Set<String> keySet = tenderMap.keySet();
				Iterator<String> it = keySet.iterator();
				TenderInfoInfo tenderInfo = new TenderInfoInfo();
				String key = null;
				String value = null;
				for(;it.hasNext();){
					key = it.next();
					value = tenderMap.get(key);
					if (key.equals("id")) {
						key = "webTenderId";
					}
					if(key.equalsIgnoreCase("supplier")){
						if(!StringUtils.isEmpty(key)){
							SupplierStockInfo supplier = SupplierStockFactory.getLocalInstance(ctx).getSupplierStockInfo(new ObjectUuidPK(BOSUuid.read(value)));
							tenderInfo.setSupplier(supplier);
							continue;
						}
					}
					if(key.equalsIgnoreCase("inviteProject")){
						if(!StringUtils.isEmpty(key)){
							InviteProjectInfo inviteProject = InviteProjectFactory.getLocalInstance(ctx).getInviteProjectInfo(new ObjectUuidPK(BOSUuid.read(value)));
							tenderInfo.setInviteProject(inviteProject);
							continue;
						}
					}
					if(key.equalsIgnoreCase("invitation")){
						if(!StringUtils.isEmpty(key)){
							InvitationInfoInfo invitationInfo = InvitationInfoFactory.getLocalInstance(ctx).getInvitationInfoInfo(new ObjectUuidPK(BOSUuid.read(value)));
							tenderInfo.setInvitation(invitationInfo);
							continue;
						}
					}
					tenderInfo.put(key, value);
				}
				tenderInfo.put("id", BOSUuid.create(tenderInfo.getBOSType()));
				tenderInfo.setState(FDCBillStateEnum.SUBMITTED);
				tenderInfo.setTenderState("1");
				tenderInfo.setOrgUnit(org.castToFullOrgUnitInfo());
				tenderInfo.setCU(org.getCU());
				tenderInfo.setNumber(UUID.randomUUID().toString());
				tenderInfo.setName(UUID.randomUUID().toString());
				
				IObjectPK  pk = TenderInfoFactory.getLocalInstance(ctx).save(tenderInfo);
				
				//招标报名处理：根据当前报名信息关联的招标立项，去查询对应的招标入围信息，如果能将供应商ID对应上，就直接将该报名信息置为审批+报名成功
				
				if(pk != null){
					result.put("code", "0");
					ForewarnRunTimeFactory.getRemoteInstance().execRightnowForewarn("com.kingdee.eas.fdc.invite.supplier.client.TenderInfoEditUI", "ActionSave", tenderInfo.getId().toString());
				}
			}
		}catch (Exception e) {
			result.put("msg", e.getMessage());
			
		}
		return JSONUtils.convertObjectToJson(ctx, result);
	}


	
	
	@Override
	protected byte[] _download(Context ctx, String downloadParam)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(downloadParam)){
			return null;
		}
		
		AttachmentInfo info = AttachmentFactory.getLocalInstance(ctx).getAttachmentInfo(new ObjectUuidPK(BOSUuid.read(downloadParam)));
		
		return info.getFile();
	}
	/**
	 * 功能：用于同步EAS基础数据（省份、城市、资质等级、供应商等级、服务区域、经营模式） 参数：最后更新时间
	 * 
	 */
	
	
	protected String _syncEASBasedata(Context ctx, Date lastUpdateDate)
			throws BOSException, EASBizException {
		Map<String, Object> basedataMap = new HashMap<String, Object>();

	
		
		// 获取省份信息
		List<ProvinceVO> provinces = syncProvince(ctx, lastUpdateDate);
		basedataMap.put("provinces", provinces);
		
		//获取城市
		List<CityVO> citys = syncCity(ctx,lastUpdateDate);
		basedataMap.put("citys", citys);
		
		//获取资质等级
		List<QuoLevelVO> quaLevels = syncQuaLevel(ctx,lastUpdateDate);
		basedataMap.put("quaLevels", quaLevels);
		
		
		
		//获取供应商等级
		List<SupplierGradeVO> supplierGrades = syncGrades(ctx,lastUpdateDate);
		basedataMap.put("supplierGrades", supplierGrades);
		
		//获取服务区域
		List<SplAreaVO> splAreas  = syncSplArea(ctx,lastUpdateDate);
		basedataMap.put("splAreas", splAreas);
		//经营模式
		List<BusinessModeVO> businessModes = syncBusinessMode(ctx,lastUpdateDate);
		basedataMap.put("businessModes", businessModes);
		//获取供应商信息
		List<SupplierVO> suppliers =syncSupplier(ctx,lastUpdateDate);
		basedataMap.put("suppliers",suppliers);
		String result = JSONUtils.convertObjectToJson(ctx, basedataMap);
		return result;
	}

	private List<SupplierVO> syncSupplier(Context ctx, Date lastUpdateDate) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("inviteType.*");
		sic.add("quaLevelEntry.*");
		view.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		FilterItemCollection fic = filter.getFilterItems();
		FilterItemInfo fi = new FilterItemInfo("lastUpdateTime",lastUpdateDate, CompareType.GREATER_EQUALS);
		fic.add(fi);
		fi = new FilterItemInfo("state",SupplierStateEnum.APPROVE_VALUE);
		fic.add(fi);
		view.setFilter(filter);
		
		SupplierStockCollection ssCols = SupplierStockFactory.getLocalInstance(ctx).getSupplierStockCollection(view);
		
		List<SupplierVO> suppliers = new ArrayList<SupplierVO>();
		SupplierStockInfo ssInfo = null;
		SupplierVO ssVO = null;
		for(Iterator<SupplierStockInfo> it = ssCols.iterator();it.hasNext();){
			ssVO = new SupplierVO();
			ssInfo = it.next();
			if(ssInfo.getInviteType() != null && ssInfo.getInviteType().getName() != null){
				String inviteTypeName = ssInfo.getInviteType().getName();
				ssVO.setInviteType(inviteTypeName);
			}
			ssVO.setId(ssInfo.getId().toString());
			ssVO.setName(ssInfo.getName());
			ssVO.setNumber(ssInfo.getNumber());
			ssVO.setCreateDate(ssInfo.getCreateTime());
			ssVO.setLastUpdateDate(ssInfo.getLastUpdateTime());
			ssVO.setCreator("EAS");
			ssVO.setLastUpdator("EAS");
			ssVO.setAddress(ssInfo.getAddress());
			ssVO.setSplArea(ssInfo.getSplArea());
			if(ssInfo.getGrade()!=null){
				ssVO.setGrade(ssInfo.getGrade().getId()+"");
			}
			ssVO.setProvince(ssInfo.getProvince() != null ?ssInfo.getProvince().getId()+"":"");
			ssVO.setCity(ssInfo.getCity() != null ?ssInfo.getCity().getId()+"":"");
			ssVO.setPunish(ssInfo.getPunish());
			ssVO.setContractor(ssInfo.getContractor());
			ssVO.setContractorPhone(ssInfo.getContractorPhone());
			ssVO.setLinkFax(ssInfo.getLinkFax());
			ssVO.setLinkPhone(ssInfo.getLinkPhone());
			ssVO.setManager(ssInfo.getManager());
			ssVO.setManagerPhone(ssInfo.getManagerPhone());
			if(ssInfo.getEnterpriseKind() != null){
				ssVO.setEnterprisekind(ssInfo.getEnterpriseKind().getValue());
			}
			ssVO.setRegisterMoney(ssInfo.getRegisterMoney());
			if(ssInfo.getSupplierBusinessMode() != null){
				ssVO.setSupplierBusinessMode(ssInfo.getSupplierBusinessMode().getId()+"");
			}
			if(ssInfo.getQuaLevelEntry()!= null){
				List<String> qualevels = new ArrayList<String>();
				Iterator<SupplierQuaLevelEntryInfo> quaIter = ssInfo.getQuaLevelEntry().iterator();
				for(;quaIter.hasNext();){
					qualevels.add(quaIter.next().getQuaLevel().getId().toString());
				}
				ssVO.setQuaLevel(qualevels);
			}
			ssVO.setRecommoned(ssInfo.getRecommended());
			ssVO.setEnterpriseMaster(ssInfo.getEnterpriseMaster());
			ssVO.setBizRegisterNo(ssInfo.getBizRegisterNo());
			if(ssInfo.getTaxerQua()!=null){
				ssVO.setTaxerQua(ssInfo.getTaxerQua().getValue());
			}
			ssVO.setTaxRegisterNo(ssInfo.getTaxRegisterNo());
			if(ssInfo.getIsPass()!=null){
				ssVO.setPass(ssInfo.getIsPass().equals(IsGradeEnum.ELIGIBILITY)?true:false);
			}
			ssVO.setBankAccount(ssInfo.getBankCount());
			ssVO.setBankName(ssInfo.getBankName());
			suppliers.add(ssVO);
		}
		
		return suppliers;
	}

	private List<BusinessModeVO> syncBusinessMode(Context ctx,
			Date lastUpdateDate) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		FilterItemCollection fic = filter.getFilterItems();
		FilterItemInfo fi = new FilterItemInfo("lastUpdateTime",lastUpdateDate, CompareType.GREATER_EQUALS);
		fic.add(fi);
		view.setFilter(filter);
		SupplierBusinessModeCollection bmCols =  SupplierBusinessModeFactory.getLocalInstance(ctx).getSupplierBusinessModeCollection(view);
		List<BusinessModeVO> businessModes = new ArrayList<BusinessModeVO>();
		SupplierBusinessModeInfo bmInfo = null;
		BusinessModeVO bmVO = null;
		for(Iterator<SupplierBusinessModeInfo> it = bmCols.iterator();it.hasNext();){
			bmInfo = it.next();
			bmVO = new BusinessModeVO();
			bmVO.setId(bmInfo.getId().toString());
			bmVO.setName(bmInfo.getName());
			bmVO.setNumber(bmInfo.getNumber());
			bmVO.setCreateDate(bmInfo.getCreateTime());
			bmVO.setLastUpdateDate(bmInfo.getLastUpdateTime());
			bmVO.setCreator("EAS");
			bmVO.setLastUpdator("EAS");
			businessModes.add(bmVO);
		}
		return businessModes;
	}

	private List<SplAreaVO> syncSplArea(Context ctx, Date lastUpdateDate) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		FilterItemCollection fic = filter.getFilterItems();
		FilterItemInfo fi = new FilterItemInfo("lastUpdateTime",lastUpdateDate, CompareType.GREATER_EQUALS);
		fic.add(fi);
		view.setFilter(filter);
		FDCSplAreaCollection splAreaCols =  FDCSplAreaFactory.getLocalInstance(ctx).getFDCSplAreaCollection(view);
		List<SplAreaVO> splAreas = new ArrayList<SplAreaVO>();
		FDCSplAreaInfo sInfo = null;
		SplAreaVO sVO = null;
		for(Iterator<FDCSplAreaInfo> it = splAreaCols.iterator();it.hasNext();){
			sInfo = it.next();
			sVO = new SplAreaVO();
			sVO.setId(sInfo.getId().toString());
			sVO.setName(sInfo.getName());
			sVO.setNumber(sInfo.getNumber());
			sVO.setCreateDate(sInfo.getCreateTime());
			sVO.setLastUpdateDate(sInfo.getLastUpdateTime());
			sVO.setCreator("EAS");
			sVO.setLastUpdator("EAS");
			splAreas.add(sVO);
		}
		return splAreas;
	}

	private List<SupplierGradeVO> syncGrades(Context ctx, Date lastUpdateDate) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		FilterItemCollection fic = filter.getFilterItems();
		FilterItemInfo fi = new FilterItemInfo("lastUpdateTime",lastUpdateDate, CompareType.GREATER_EQUALS);
		fic.add(fi);
		view.setFilter(filter);
		GradeSetUpCollection gradeCols = GradeSetUpFactory.getLocalInstance(ctx).getGradeSetUpCollection(view);
		List<SupplierGradeVO> supplierGrades = new ArrayList<SupplierGradeVO>();
		SupplierGradeVO sgVO = null;
		GradeSetUpInfo sgInfo = null;
		for(Iterator<GradeSetUpInfo> it = gradeCols.iterator();it.hasNext();){
			sgInfo = it.next();
			sgVO= new SupplierGradeVO();
			sgVO.setId(sgInfo.getId().toString());
			sgVO.setName(sgInfo.getName());
			sgVO.setNumber(sgInfo.getNumber());
			sgVO.setCreator("EAS");
			sgVO.setLastUpdator("EAS");
			sgVO.setCreateDate(sgInfo.getCreateTime());
			sgVO.setLastUpdateDate(sgInfo.getLastUpdateTime());
			supplierGrades.add(sgVO);
			
		}
		return supplierGrades;
	}

	private List<QuoLevelVO> syncQuaLevel(Context ctx, Date lastUpdateDate) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		FilterItemCollection fic = filter.getFilterItems();
		FilterItemInfo fi = new FilterItemInfo("lastUpdateTime",lastUpdateDate, CompareType.GREATER_EQUALS);
		fic.add(fi);
		view.setFilter(filter);
		QuaLevelCollection quaLevelCols = QuaLevelFactory.getLocalInstance(ctx).getQuaLevelCollection(view);
		List<QuoLevelVO> quaLevels = new ArrayList<QuoLevelVO>();
		QuaLevelInfo qInfo = null;
		QuoLevelVO qVO = null;
		for(Iterator<QuaLevelInfo> it = quaLevelCols.iterator();it.hasNext();){
			qInfo = it.next();
			qVO = new QuoLevelVO();
			qVO.setId(qInfo.getId().toString());
			qVO.setName(qInfo.getName());
			qVO.setNumber(qInfo.getNumber());
			qVO.setCreator("EAS");
			qVO.setLastUpdator("EAS");
			qVO.setCreateDate(qInfo.getCreateTime());
			qVO.setLastUpdateDate(qInfo.getLastUpdateTime());
			quaLevels.add(qVO);
		}
		
		return quaLevels;
	}

	private List<CityVO> syncCity(Context ctx, Date lastUpdateDate) throws BOSException {
		// TODO Auto-generated method stub
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("name");
		sic.add("number");
		sic.add("province.*");
		view.setSelector(sic);
		
		FilterInfo filter = new FilterInfo();
		FilterItemCollection fic = filter.getFilterItems();
		FilterItemInfo fi = new FilterItemInfo("lastUpdateTime",lastUpdateDate, CompareType.GREATER_EQUALS);
		fic.add(fi);
		view.setFilter(filter);
		CityCollection cityCols = CityFactory.getLocalInstance(ctx).getCityCollection(view);
		List<CityVO> citys = new ArrayList<CityVO>();
		CityInfo cityInfo = null;
		CityVO cityVO = null;
		ProvinceInfo pInfo = null;
		ProvinceVO provinceVO = null;
		for(Iterator<CityInfo> it = cityCols.iterator();it.hasNext();){
			cityInfo = it.next();
			cityVO = new CityVO();
			cityVO.setId(cityInfo.getId().toString());
			cityVO.setName(cityInfo.getName());
			cityVO.setNumber(cityInfo.getNumber());
			cityVO.setCreator("EAS");
			cityVO.setLastUpdator("EAS");
			cityVO.setCreateDate(cityInfo.getCreateTime());
			cityVO.setLastUpdateDate(cityInfo.getLastUpdateTime());
			pInfo = cityInfo.getProvince();
			if(pInfo!=null){
			     cityVO.setProvince(pInfo.getId()+"");
			}
			citys.add(cityVO);
		}
		return citys;
	}

	private List<ProvinceVO> syncProvince(Context ctx, Date lastUpdateDate)
			throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("name");
		sic.add("number");
		sic.add("country.number");
		view.setSelector(sic);

		FilterInfo filter = new FilterInfo();
		FilterItemCollection fic = filter.getFilterItems();
		FilterItemInfo fi = new FilterItemInfo("lastUpdateTime",
				lastUpdateDate, CompareType.GREATER_EQUALS);
		fic.add(fi);
		view.setFilter(filter);
		ProvinceCollection provincesCols = ProvinceFactory
				.getLocalInstance(ctx).getProvinceCollection(view);
		List<ProvinceVO> provinces = new ArrayList<ProvinceVO>();
		ProvinceInfo province = null;
		ProvinceVO vo = null;
		for (Iterator<ProvinceInfo> it = provincesCols.iterator(); it.hasNext();) {
			province = it.next();
			vo = new ProvinceVO();
			vo.setCreateDate(province.getCreateTime());
			vo.setCreator("EAS");
			vo.setLastUpdateDate(province.getLastUpdateTime());
			vo.setLastUpdator("EAS");
			vo.setId(province.getId().toString());
			vo.setName(province.getName());
			vo.setNumber(province.getNumber());
			vo.setNational(province.getCountry() != null ?province.getCountry().getNumber():"");
			provinces.add(vo);
		}
		return provinces;
	}

	protected String _registerUser(Context ctx, String userParam)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("code", "-1");
		String result = null;
		try{
			WebUserInfo info = new WebUserInfo();
			OrgUnitInfo org = FullOrgUnitFactory.getLocalInstance(ctx)
					.getOrgUnitInfo(
							new ObjectUuidPK(BOSUuid.read(OrgConstants.DEF_CU_ID)));

			HashMap<String, String> userMap = (HashMap<String, String>) JSONUtils.convertJsonToObject(ctx, userParam);
			
			if (!userMap.isEmpty()) {
				Set<String> keySet = (Set<String>) userMap.keySet();
				
				userParam = userParam.substring(1, userParam.length() - 1);
				String[] params = userParam.split(",");
				String key = null;
				String value = null;
				
				Iterator<String> it = keySet.iterator();
				
				for(;it.hasNext();){
					key = it.next();
					value = userMap.get(key);
					if (key.equals("id")) {
						key = "webUserId";
					}
					info.put(key, value);
				}
				
				

				info.put("id", BOSUuid.create(info.getBOSType()));
				info.setOrgUnit(org.castToFullOrgUnitInfo());
				info.setCU(org.getCU());
				info.setNumber(UUID.randomUUID().toString());
				info.setRegisterState(RegistStateEnum.REGISTAUDITTING);
				info.setState(FDCBillStateEnum.SUBMITTED);

				IObjectPK pk = WebUserFactory.getLocalInstance(ctx).save(info);
				result = JSONUtils.convertObjectToJson(ctx, WebUserFactory
						.getLocalInstance(ctx).getWebUserInfo(pk));
				
				resultMap.put("dataObject",result);
				resultMap.put("code", "0");
				ForewarnRunTimeFactory.getRemoteInstance().execRightnowForewarn("com.kingdee.eas.fdc.invite.supplier.client.WebUserEditUI", "ActionSave", info.getId().toString());
			}
		}catch (Exception e) {
			resultMap.put("msg", e.getMessage());
		}
		

		

		return JSONUtils.convertObjectToJson(ctx, resultMap);

	}

}