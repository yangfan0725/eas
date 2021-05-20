package com.kingdee.eas.fdc.invite.app;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.invite.HeadColumnFactory;
import com.kingdee.eas.fdc.invite.HeadColumnInfo;
import com.kingdee.eas.fdc.invite.RefPriceCollection;
import com.kingdee.eas.fdc.invite.RefPriceEntryCollection;
import com.kingdee.eas.fdc.invite.RefPriceEntryFactory;
import com.kingdee.eas.fdc.invite.RefPriceEntryInfo;
import com.kingdee.eas.fdc.invite.RefPriceFactory;
import com.kingdee.eas.fdc.invite.RefPriceInfo;

public class RefPriceEntryControllerBean extends AbstractRefPriceEntryControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.RefPriceEntryControllerBean");
    
    public final static String EXTEND_VALUE = "extend_value_";
	public final static String EXTEND_TEXT = "extend_text_";
	public final static String EXTEND_DATE = "extend_date_";

	protected IObjectCollection _getCollection(Context ctx, EntityViewInfo view, HashMap cols) throws BOSException {
		// TODO �Զ����ɷ������
		FilterInfo filter = view.getFilter();
		
		if(filter == null )
			return null;

		for (Iterator it = filter.getFilterItems().iterator(); it.hasNext();) {

			FilterItemInfo filterItemInfo = (FilterItemInfo) it.next();
			String property = filterItemInfo.getPropertyName();
			
			/**
			 * 6.1��ѯ���������Ż�����������Ϊ = �� like �Ĳ���������ת��Ϊ��д����,
			 * ͨ����ѯ��������ʱ����ԭgetPropertyName()ȡֵΪname������ΪUPPER(name)����δȡ��������BOS��Ա��ͨ�������⴦��
			 */
			if(property.startsWith("UPPER")){
				property = property.substring(6, property.length()-1);
				filterItemInfo.setPropertyName(property);
			}
			if(filterItemInfo.getCompareType().equals(CompareType.IS)
					&&filterItemInfo.getCompareValue().equals("EMPTY")){
				filterItemInfo.setCompareType(CompareType.EQUALS);
				filterItemInfo.setCompareValue(null);
			}
			else if(filterItemInfo.getCompareType().equals(CompareType.ISNOT)
					&&filterItemInfo.getCompareValue().equals("EMPTY")){
				filterItemInfo.setCompareType(CompareType.NOTEQUALS);
				filterItemInfo.setCompareValue(null);
			}

			if (cols.containsKey(property)) {
				//
				/***
				 * �˴��ĺ���Ŀ�ľ���Ϊ�˽����ҵĲ�������
				 * �����ǽ��,�����ı�,��������,���ǵ�ֵ����Ӧһ��refPriceId
				 * �����Ƚ����ڵ�refPriceId�ҵ�
				 * Ȼ���滻Ϊin (refPriceIds)
				 * �����һ��ĵ���,in,����ķ�����û������
				 * ���ǵ�����������,�����Ƶ�ʱ��,��Ϊ�����ݿ���û�д�������,
				 * ���Ծͻ�����д��ֶ�Ϊ�յ����ݹ��˵�;�������Ҫ��Ϊ�յ����ݼӽ�ȥ
				 * ����Ϊ��,ͬ��
				 */
				String colid = (String) cols.get(property);
				if (property.startsWith(EXTEND_VALUE)) {
					// ���
					filterItemInfo.setPropertyName("value");
				} else if (property.startsWith(EXTEND_TEXT)) {
					filterItemInfo.setPropertyName("text");
				} else if (property.startsWith(EXTEND_DATE)) {
					filterItemInfo.setPropertyName("datevalue");
				}

				EntityViewInfo tempView = new EntityViewInfo();
				Set itemIdSet = new HashSet();
				if ((filterItemInfo.getCompareType().equals(CompareType.EQUALS)
						&&filterItemInfo.getCompareValue() == null)||
						(filterItemInfo.getCompareType().equals(CompareType.IS)
								&&filterItemInfo.getCompareValue().equals("EMPTY"))) {
					//�����Ϊ�ղ�ѯ
					tempView.getSelector().add("head.id");
					FilterInfo tempFilter = new FilterInfo();
					
					tempFilter.getFilterItems().add(
							new FilterItemInfo("column", colid));

					tempView.setFilter(tempFilter);
					RefPriceEntryCollection tempprice = RefPriceEntryFactory.getLocalInstance(ctx)
					.getRefPriceEntryCollection(tempView);
					
					for (int priceIndex = 0; priceIndex < tempprice.size(); priceIndex++) {
						RefPriceEntryInfo entryinfo = (RefPriceEntryInfo) tempprice
								.get(priceIndex);
						itemIdSet.add(entryinfo.getHead().getId().toString());
					}
				}
				else {
					tempView = new EntityViewInfo();
					tempView.getSelector().add("*");
					tempView.getSelector().add("head.id");
					FilterInfo tempFilter = new FilterInfo();
					tempFilter.getFilterItems().add(
							new FilterItemInfo("column", colid));
					tempFilter.getFilterItems().add(filterItemInfo);

					tempView.setFilter(tempFilter);
					RefPriceEntryCollection tempprice = RefPriceEntryFactory
							.getLocalInstance(ctx).getRefPriceEntryCollection(
									tempView);
					for (int priceIndex = 0; priceIndex < tempprice.size(); priceIndex++) {
						RefPriceEntryInfo entryinfo = (RefPriceEntryInfo) tempprice
								.get(priceIndex);
						itemIdSet.add(entryinfo.getHead().getId().toString());
					}
					if((filterItemInfo.getCompareType().equals(CompareType.NOTEQUALS)||
							filterItemInfo.getCompareType().equals(CompareType.NOTLIKE))
							&&filterItemInfo.getCompareValue() != null){
						tempView.getSelector().clear();
						tempView.getSelector().add("head.id");
						tempFilter = new FilterInfo();
						
						tempFilter.getFilterItems().add(
								new FilterItemInfo("column", colid));

						tempView.setFilter(tempFilter);
						RefPriceEntryCollection temppricenull = RefPriceEntryFactory.getLocalInstance(ctx)
						.getRefPriceEntryCollection(tempView);
						Set notInIds = new HashSet();
						for (int priceIndex = 0; priceIndex < temppricenull.size(); priceIndex++) {
							RefPriceEntryInfo entryinfo = (RefPriceEntryInfo) temppricenull
									.get(priceIndex);
							notInIds.add(entryinfo.getHead().getId().toString());
						}
						Set inIds  = new HashSet();
						//���ҵ����column�ı�ͷ����
						try {
							HeadColumnInfo headColumnInfo = HeadColumnFactory.getLocalInstance(ctx)
							.getHeadColumnInfo("select * where id='"+colid+"'");
							tempView.getSelector().clear();
							tempView.getSelector().add("head.id");
							tempFilter = new FilterInfo();
							tempFilter.getFilterItems().add(new FilterItemInfo("column.headtype",headColumnInfo.getHeadType().getId().toString()));
							
							tempView.setFilter(tempFilter);
							temppricenull = RefPriceEntryFactory.getLocalInstance(ctx)
							.getRefPriceEntryCollection(tempView);
							
							for (int priceIndex = 0; priceIndex < temppricenull.size(); priceIndex++) {
								RefPriceEntryInfo entryinfo = (RefPriceEntryInfo) temppricenull
										.get(priceIndex);
								inIds.add(entryinfo.getHead().getId().toString());
							}
						} catch (EASBizException e) {
							// TODO �Զ����� catch ��
							e.printStackTrace();
						} catch (BOSException e) {
							// TODO �Զ����� catch ��
							e.printStackTrace();
						}
						//not in () and in()
						if(notInIds.size()>0||inIds.size()>0){
							tempView.getSelector().clear();
							tempView.getSelector().add("id");
							tempFilter = new FilterInfo();
							if(notInIds.size()>0)
								tempFilter.getFilterItems().add(new FilterItemInfo("id",notInIds,CompareType.NOTINCLUDE));
							if(inIds.size()>0)
								tempFilter.getFilterItems().add(new FilterItemInfo("id",inIds,CompareType.INCLUDE));
							tempView.setFilter(tempFilter);
							RefPriceCollection refs = RefPriceFactory.getLocalInstance(ctx)
							.getRefPriceCollection(tempView);
							for(int i=0;i<refs.size();i++){
								RefPriceInfo ref = refs.get(i);
								itemIdSet.add(ref.getId());
							}
						}
					}
				}
				
				if(itemIdSet.size()>0){
					if (filterItemInfo.getCompareType().equals(
							CompareType.EQUALS)
							&& filterItemInfo.getCompareValue() == null) {
						filterItemInfo.setPropertyName("head.id");
						filterItemInfo.setCompareValue(itemIdSet);
						filterItemInfo.setCompareType(CompareType.NOTINCLUDE);
					} else {
						filterItemInfo.setPropertyName("head.id");
						filterItemInfo.setCompareValue(itemIdSet);
						filterItemInfo.setCompareType(CompareType.INCLUDE);
					}
				}
				else{
					filterItemInfo.setPropertyName("head.id");
					filterItemInfo.setCompareValue(null);
					filterItemInfo.setCompareType(CompareType.EQUALS);
				}
				
				// reset extendcol to id
				
				
			} else if (property.equals("head.quotingContent.supplier.name") || property.endsWith("head.listing.name")) {
				if(filterItemInfo.getCompareValue()!=null){
					String value = (String) filterItemInfo.getCompareValue();
					if(property.equals("head.quotingContent.supplier.name")&&
							(
									value.endsWith("��絼��")||
									value.startsWith("��絼��")||
									value.startsWith("%��絼��")
							)
					  ) {
						// ���۵�λ ������,������ ��絼��
						if (filterItemInfo.getCompareType().equals(
								CompareType.NOTEQUALS)
								|| filterItemInfo.getCompareType().equals(
										CompareType.NOTLIKE)) {
							filterItemInfo
									.setPropertyName("head.quotingContent");
							filterItemInfo.setCompareValue(null);
							filterItemInfo
									.setCompareType(CompareType.NOTEQUALS);
						} else {
							filterItemInfo
									.setPropertyName("head.quotingContent");
							filterItemInfo.setCompareValue(null);
							filterItemInfo.setCompareType(CompareType.EQUALS);
						}
						
					}
					else if(filterItemInfo.getCompareType().equals(
							CompareType.NOTEQUALS)
							|| filterItemInfo.getCompareType().equals(
									CompareType.NOTLIKE)){
						//���ڲ����ڣ������ƣ���Ҫ��nullֵ���뵽�����
						Set itemIdSet = new HashSet();
						EntityViewInfo tempView = new EntityViewInfo();
						tempView.getSelector().add("id");
						FilterInfo tempFilter = new FilterInfo();
						if(property.equals("head.quotingContent.supplier.name")){
							tempFilter.getFilterItems().add(new FilterItemInfo("quotingContent",null,CompareType.EQUALS));
							filterItemInfo.setPropertyName("quotingContent.supplier.name");
						}
						else{
							tempFilter.getFilterItems().add(new FilterItemInfo("listing",null,CompareType.EQUALS));
							filterItemInfo.setPropertyName("listing.name");
						}
						
						tempFilter.getFilterItems().add(filterItemInfo);
						tempFilter.setMaskString("#0 or #1");
						tempView.setFilter(tempFilter);
						
						RefPriceCollection refs = RefPriceFactory.getLocalInstance(ctx)
						.getRefPriceCollection(tempView);
						for(int i=0;i<refs.size();i++){
							RefPriceInfo ref = refs.get(i);
							itemIdSet.add(ref.getId());
						}
						if(itemIdSet.size()>0){
							filterItemInfo.setPropertyName("head.id");
							filterItemInfo.setCompareValue(itemIdSet);
							filterItemInfo.setCompareType(CompareType.INCLUDE);
						}
						else{
							filterItemInfo.setPropertyName("head.id");
							filterItemInfo.setCompareValue(null);
							filterItemInfo.setCompareType(CompareType.EQUALS);
						}
					}
				
				}
				
			} 
		}
		return RefPriceEntryFactory.getLocalInstance(ctx).getRefPriceEntryCollection(view);
	}
}