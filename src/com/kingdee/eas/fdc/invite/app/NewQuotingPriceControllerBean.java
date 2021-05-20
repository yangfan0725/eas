package com.kingdee.eas.fdc.invite.app;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.invite.DescriptionEnum;
import com.kingdee.eas.fdc.invite.FDCInviteException;
import com.kingdee.eas.fdc.invite.HeadColumnInfo;
import com.kingdee.eas.fdc.invite.HeadTypeFactory;
import com.kingdee.eas.fdc.invite.HeadTypeInfo;
import com.kingdee.eas.fdc.invite.ListingItemCollection;
import com.kingdee.eas.fdc.invite.ListingItemFactory;
import com.kingdee.eas.fdc.invite.ListingItemInfo;
import com.kingdee.eas.fdc.invite.NewListingEntryCollection;
import com.kingdee.eas.fdc.invite.NewListingEntryFactory;
import com.kingdee.eas.fdc.invite.NewListingEntryInfo;
import com.kingdee.eas.fdc.invite.NewListingFactory;
import com.kingdee.eas.fdc.invite.NewListingInfo;
import com.kingdee.eas.fdc.invite.NewListingValueCollection;
import com.kingdee.eas.fdc.invite.NewListingValueInfo;
import com.kingdee.eas.fdc.invite.NewQuotingPriceFactory;
import com.kingdee.eas.fdc.invite.NewQuotingPriceInfo;
import com.kingdee.eas.fdc.invite.QuotingPriceStatusEnum;
import com.kingdee.eas.fdc.invite.QuotingPriceTypeEnum;
import com.kingdee.eas.fdc.invite.RefPriceEntryInfo;
import com.kingdee.eas.fdc.invite.RefPriceFactory;
import com.kingdee.eas.fdc.invite.RefPriceInfo;
import com.kingdee.eas.util.app.ContextUtil;

public class NewQuotingPriceControllerBean extends
		AbstractNewQuotingPriceControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.invite.app.NewQuotingPriceControllerBean");
	private final static String CURORGITEMS = "CURORGITEMS";
	private final static String UPORGITEMS = "UPORGITEMS";
	private final static String DOWNORGITEMS = "DOWNORGITEMS";

	/**
	 *
	 * �������Ƿ�ʹ�������ֶ�
	 *
	 * @return
	 * @author:liupd ����ʱ�䣺2006-10-17
	 *               <p>
	 */
	protected boolean isUseName() {
		return false;
	}

	/**
	 *
	 * �������Ƿ�ʹ�ñ����ֶ�
	 *
	 * @return
	 * @author:liupd ����ʱ�䣺2006-10-17
	 *               <p>
	 */
	protected boolean isUseNumber() {
		return false;
	}
	private Map listingItemCollectionToMap(ListingItemCollection items){
		Map itemNameMap = new HashMap();
		for (int j = 0; j < items.size(); j++) {
			ListingItemInfo item = items.get(j);
			itemNameMap.put(item.getName(), item);			
		}
		return itemNameMap;
	}
	
	/**
	 * ȡ����֯��ͬ������Ŀ������Map�У�����Ŀ������key
	 *
	 * @param orgName
	 * @param org
	 * @return
	 * @throws BOSException
	 * @throws EASBizException 
	 */
	private Map getSameOrgSameNameItems(Context ctx,CompanyOrgUnitInfo org,BOSUuid headTypeId)
			throws BOSException, EASBizException {
		ListingItemCollection items = ListingItemFactory.getLocalInstance(ctx)
				.getCollectionForCurOrg(org.getId(),headTypeId);
		return listingItemCollectionToMap(items);
	}
	/**
	 * ��ȡ�ϼ���֯�£���ͬ���Ƶ���Ŀ������map������Ŀ������key
	 *
	 * @param org
	 * @return
	 * @throws BOSException
	 * @throws EASBizException 
	 */
	private Map getUpOrgSameNameItems(Context ctx,CompanyOrgUnitInfo org,BOSUuid headTypeId)
			throws BOSException, EASBizException {
		ListingItemCollection items = ListingItemFactory.getLocalInstance(ctx)
				.getCollectionForUpOrg(org.getId(),headTypeId);
		return listingItemCollectionToMap(items);
	}
	/**
	 * ��ȡ�¼���֯�£���ͬ���Ƶ���Ŀ������map������Ŀ������key
	 *
	 * @param org
	 * @return
	 * @throws BOSException
	 * @throws EASBizException 
	 */
	private Map getDownOrgSameNameItems(Context ctx,CompanyOrgUnitInfo org,BOSUuid headTypeId)
			throws BOSException, EASBizException {
		ListingItemCollection items = ListingItemFactory.getLocalInstance(ctx)
				.getCollectionForDownOrg(org.getId(),headTypeId);
		return listingItemCollectionToMap(items);
	}
	protected void _acceptBidExportQuoting(Context ctx, String quotingId,
			String listingId) throws BOSException, EASBizException {
		_acceptBid(ctx,quotingId);
		_exportQuoting(ctx,quotingId,listingId);
	}
	protected Map _fetchInitData(Context ctx, Map paramMap) throws BOSException, EASBizException {
		return new HashMap();
	}
	protected void _exportQuoting(Context ctx, String quotingId, String listingId) throws BOSException, EASBizException {
		NewQuotingPriceInfo quoting = getNewQuotingPriceInfo(ctx, "select * where id = '" + quotingId + "'");
		CompanyOrgUnitInfo currentOrg = ContextUtil.getCurrentFIUnit(ctx);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("pages.id");
		sels.add("pages.headtype");
		NewListingInfo listing = NewListingFactory.getLocalInstance(ctx)
				.getNewListingInfo(new ObjectUuidPK(BOSUuid.read(listingId)),
						sels);
		Set sheetIdSet = new HashSet();
		Map headTypeItems = new HashMap();
		//һ��ҳǩ��Ӧһ����ͷ���ͣ�����ǰ��˾���ϼ���֯���¼���֯��Ӧ�����ͷ�����е�������Ŀ�ֱ�ȡ��
		for (int i = 0; i < listing.getPages().size(); i++) {
			sheetIdSet.add(listing.getPages().get(i).getId().toString());
			BOSUuid headTypeId = listing.getPages().get(i).getHeadType().getId();
			//��ǰ��֯��
			Map items = getSameOrgSameNameItems(ctx,currentOrg,headTypeId);
			headTypeItems.put(headTypeId+this.CURORGITEMS,items);
			//�ϼ���֯��
			items = getUpOrgSameNameItems(ctx,currentOrg,headTypeId);
			headTypeItems.put(headTypeId+this.UPORGITEMS,items);
			//�¼���֯��
			items = getDownOrgSameNameItems(ctx,currentOrg,headTypeId);
			headTypeItems.put(headTypeId+this.DOWNORGITEMS,items);
		}
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("head.*");
		view.getSelector().add("values.*");
		view.getSelector().add("values.column.*");
		view.getSelector().add("values.column.headColumn.name*");
		view.getSelector().add("values.column.headColumn.property*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("head.id", sheetIdSet, CompareType.INCLUDE));
		NewListingEntryCollection entrys = NewListingEntryFactory
				.getLocalInstance(ctx).getNewListingEntryCollection(view);
		for (int i = 0; i < entrys.size(); i++) {
			NewListingEntryInfo entry = entrys.get(i);
			if (!entry.isIsLeaf()) {
				continue;
			}

			// 20101107 zhougang �б�󵼳������ݿ⣬��Ӧ���Զ���ҳǩ����Ŀ����д���������е��嵥��Ŀ
			if(entry.getHead() != null && entry.getHead().getHeadType() != null){
				HeadTypeInfo hdInfo = HeadTypeFactory.getLocalInstance(ctx).getHeadTypeInfo("where id = '" + entry.getHead().getHeadType().getString("id") + "'");
				if(hdInfo.isIsDefined()){
					continue;
				}
			}
			// 20101107 zhougang 
			
			NewListingValueCollection values = entry.getValues();
			RefPriceInfo ref = new RefPriceInfo();
			if (entry.getItem() == null) {
				String itemName = entry.getItemName();
				//�õ���ǰ��֯���Ƿ�����ͬ���Ƶ���Ŀ
				HeadTypeInfo headTypeInfo =  entry.getHead().getHeadType();
				Map curOrgItemNameMap = (HashMap)headTypeItems.get(headTypeInfo.getId().toString()+this.CURORGITEMS);				
				//�õ����¼���ϵ���Ƿ������ͬ���Ƶ���Ŀ
				//2008-05-29 ����				
				Map upOrgItemNameMap= (HashMap)headTypeItems.get(headTypeInfo.getId().toString()+this.UPORGITEMS);	
				Map downOrgItemNameMap= (HashMap)headTypeItems.get(headTypeInfo.getId().toString()+this.DOWNORGITEMS);	
				//�жϵ�ǰ
				if(curOrgItemNameMap.containsKey(itemName)){
					ListingItemInfo itemInfo = (ListingItemInfo) curOrgItemNameMap
					.get(itemName);
					ref.setItem(itemInfo);
					entry.setItem(itemInfo);
				}
				//�ж��ϼ�
				else if (upOrgItemNameMap.containsKey(itemName)) {
					ListingItemInfo itemInfo = (ListingItemInfo) upOrgItemNameMap
					.get(itemName);
					ref.setItem(itemInfo);
					entry.setItem(itemInfo);
					
				}
				//�ж���һ��,�����һ����,����
				else if(downOrgItemNameMap.containsKey(itemName)){
					//do nothing 
					throw new FDCInviteException(FDCInviteException.EXITONDOWNORG);
				}
				else {
					ListingItemInfo newItem = new ListingItemInfo();
					newItem.setId(BOSUuid.create(newItem.getBOSType()));
					newItem.setName(itemName);
					newItem.setHeadType(entry.getHead().getHeadType());
					newItem.setOrgUnit(currentOrg.castToFullOrgUnitInfo());
					for (int j = 0, n = values.size(); j < n; j++) {
						NewListingValueInfo info = values.get(j);
						if (info.getColumn().getHeadColumn().getName().equals("��λ")) {
							newItem.setUnit(info.getText());
							break;
						}
					}

					ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
							.getLocalInstance(ctx);
					if (iCodingRuleManager.isExist(newItem, currentOrg.getId()
							.toString())) {
						// ѡ���˶Ϻ�֧�ֻ���û��ѡ��������ʾ,��ȡ�����ñ��
						if (iCodingRuleManager.isUseIntermitNumber(newItem,
								currentOrg.getId().toString())
								|| !iCodingRuleManager.isAddView(newItem,
										currentOrg.getId().toString()))
						// �˴���orgId�벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
						{
							// �����˶Ϻ�֧�ֹ��ܣ���ʱֻ�Ƕ�ȡ��ǰ���±��룬�����������ڱ���ʱ
							String number = iCodingRuleManager.getNumber(
									newItem, currentOrg.getId().toString());
							newItem.setNumber(number);
						}
					} else {
						newItem.setNumber("N" + itemName);
					}

					ListingItemFactory.getLocalInstance(ctx).submit(newItem);
					ref.setItem(newItem);
					entry.setItem(newItem);
				}
				SelectorItemCollection entrySels = new SelectorItemCollection();
				entrySels.add("item");
				NewListingEntryFactory.getLocalInstance(ctx).updatePartial(
						entry, entrySels);
			} else {
				ref.setItem(entry.getItem());
			}
			ref.setDate(new Date());
			ref.setQuotingContent(quoting);
			ref.setListing(listing);
//			NewListingValueCollection values = entry.getValues();
			for (int j = 0, n = values.size(); j < n; j++) {
				NewListingValueInfo value = values.get(j);
				if (value.getType() != null
						&& value.getType()
								.equals(QuotingPriceTypeEnum.original)) {
					continue;
				}
				HeadColumnInfo headColumn = value.getColumn().getHeadColumn();
				if (headColumn.getProperty().equals(DescriptionEnum.ProjectAmt)
						|| headColumn.getProperty().equals(
								DescriptionEnum.ProjectAmtSum)) {
					continue;
				}
				if (headColumn.getProperty().equals(DescriptionEnum.Amount)
						|| headColumn.getProperty().equals(
								DescriptionEnum.AmountSum)) {
					continue;
				}
				if (!value.getColumn().isIsQuoting()) {
					if (value.getQuotingPrice() == null) {
						RefPriceEntryInfo refValue = new RefPriceEntryInfo();
						refValue.setColumn(headColumn);
						refValue.setValue(value.getAmount());
						//�������ڵ�֧�� 2008-05-28
						refValue.setDateValue(value.getDateValue());
						refValue.setText(value.getText());
						ref.getEntrys().add(refValue);
					}
				} else {
					if (value.getQuotingPrice() != null
							&& value.getQuotingPrice().getId().toString()
									.equals(quoting.getId().toString())) {
						RefPriceEntryInfo refValue = new RefPriceEntryInfo();
						refValue.setColumn(headColumn);
						refValue.setValue(value.getAmount());
						//�������ڵ�֧�� 2008-05-28
						refValue.setDateValue(value.getDateValue());
						refValue.setText(value.getText());
						ref.getEntrys().add(refValue);
					}
				}
			}
			if (ref.getEntrys().size() > 0)
				RefPriceFactory.getLocalInstance(ctx).submit(ref);
		}
		quoting.setStatus(QuotingPriceStatusEnum.ImportToDB);
		SelectorItemCollection sels2 = new SelectorItemCollection();
		sels2.add("status");
		NewQuotingPriceFactory.getLocalInstance(ctx).updatePartial(quoting, sels2);
	}

	protected void _acceptBid(Context ctx, String quotingId) throws BOSException, EASBizException {
		NewQuotingPriceInfo quoting = getNewQuotingPriceInfo(ctx, "select * where id = '" + quotingId + "'");
		quoting.setStatus(QuotingPriceStatusEnum.ImportBase);
		quoting.setHasEffected(true);
		SelectorItemCollection sels2 = new SelectorItemCollection();
		sels2.add("status");
		sels2.add("hasEffected");
		NewQuotingPriceFactory.getLocalInstance(ctx).updatePartial(quoting, sels2);
		
	}

	protected void _unacceptBid(Context ctx, String quotingId) throws BOSException, EASBizException {
		NewQuotingPriceInfo quoting = getNewQuotingPriceInfo(ctx, "select * where id = '" + quotingId + "'");
		quoting.setStatus(QuotingPriceStatusEnum.Evaluated);
		SelectorItemCollection sels2 = new SelectorItemCollection();
		sels2.add("status");
		NewQuotingPriceFactory.getLocalInstance(ctx).updatePartial(quoting, sels2);
	}
	
}