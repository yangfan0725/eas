package com.kingdee.eas.fdc.sellhouse.client;


/**
 * SHEHelper  1.36.8.32 09-2-11 6.45pm �ĸ���
 * ��SHEHelper �ȶ��ˣ����Կ�������ʹ�ã�Ŀǰʹ�ø���ĵط�ֻ�� RoomSelectUIForF7
 * @author laiquan_luo
 * @deprecated 
 */
public class CopyOfSHEHelper {
//	private static final Logger log = Logger.getLogger(CopyOfSHEHelper.class);
//	
//	public final static String STATUS_ADDNEW = "ADDNEW";
//	
//	public static TreeModel getTree(String type, ItemAction actionOnLoad)
//			throws Exception {
//		SaleOrgUnitInfo saleOrg = getCurrentSaleOrg();
//		MetaDataPK dataPK = null;
//		if (actionOnLoad != null) {
//			String actoinName = actionOnLoad.getClass().getName();
//			if (actoinName.indexOf("$") >= 0) {
//				actoinName = actoinName.substring(actoinName.indexOf("$") + 1);
//			}
//			dataPK = new MetaDataPK(actoinName);
//		}
//		TreeModel tree = NewOrgUtils.getTreeModel(OrgViewType.SALE, "", saleOrg
//				.getId().toString(), null, dataPK);
//		if (!type.equals("saleOrg")) {
//			fillNode((DefaultMutableTreeNode) tree.getRoot(), type);
//		}
//		return tree;
//	}
//
//	private static void fillNode(DefaultMutableTreeNode node, String type)
//			throws BOSException {
//		OrgStructureInfo org = (OrgStructureInfo) node.getUserObject();
//		UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
//		FullOrgUnitInfo saleOrg = org.getUnit();
//		if (saleOrg == null) {
//			MsgBox.showError("too many level!");
//			return;
//		}
//		if (node.isLeaf()) {
//			Map sellOrderMap = new HashMap();		
//			EntityViewInfo view1 = new EntityViewInfo();
//			view1.getSorter().add(new SorterItemInfo("number"));
//			FilterInfo filter1 = new FilterInfo();
//			filter1.getFilterItems()
//					.add(
//							new FilterItemInfo("orgUnit.id", saleOrg.getId()
//									.toString()));
//			view1.setFilter(filter1);
//			view1.getSelector().add("*");
//			view1.getSelector().add("parent.*");
//			view1.getSelector().add("sellProject.*");
//			view1.getSelector().add("sellProject.sellProject.*");
//			view1.getSelector().add("dutyPerson.*");
//			view1.getSelector().add("dutyPerson.dutyPerson.*");
//			view1.getSelector().add("member.*");
//			view1.getSelector().add("member.member.*");
//			Set set = new HashSet();
//			MarketingUnitCollection markeUnitColl = MarketingUnitFactory
//			.getRemoteInstance().getMarketingUnitCollection(view1);
//			for(int a=0;a<markeUnitColl.size();a++)
//			{
//				MarketingUnitInfo marketingUnitInfo = markeUnitColl.get(a);
//				MarketingUnitDutyPersonCollection dutyPersonColl = marketingUnitInfo.getDutyPerson();
//				for(int b=0;b<dutyPersonColl.size();b++)
//				{
//					MarketingUnitDutyPersonInfo dutyPersonInfo = dutyPersonColl.get(b);
//					UserInfo dutyUserInfo = dutyPersonInfo.getDutyPerson();
//					if(dutyUserInfo.getId().toString().equals(userInfo.getId().toString()))
//					{
//						set.add(marketingUnitInfo);
//					}
//				}
//				MarketingUnitMemberCollection memberColl = marketingUnitInfo.getMember();
//				for(int c=0;c<memberColl.size();c++)
//				{
//					MarketingUnitMemberInfo memberInfo = memberColl.get(c);
//					UserInfo memberInfo2 = memberInfo.getMember();
//					if(memberInfo2.getId().toString().equals(userInfo.getId().toString()))
//					{
//						set.add(marketingUnitInfo);
//					}
//				}
//			}
//			Iterator iterator = set.iterator();
//			Set projectSet = new HashSet();
//			while(iterator.hasNext())
//			{
//				MarketingUnitInfo marketingUnitInfo2 = (MarketingUnitInfo)iterator.next();
//				MarketingUnitSellProjectCollection sellProjectColl = marketingUnitInfo2.getSellProject();
//				for(int m=0;m<sellProjectColl.size();m++)
//				{
//					MarketingUnitSellProjectInfo markUnitSellProjectInfo = sellProjectColl.get(m);
//					SellProjectInfo markSellProjectInfo = markUnitSellProjectInfo.getSellProject();
//					String id = markSellProjectInfo.getId().toString();
//					projectSet.add(id);	
//				}
//			}
//			EntityViewInfo view = new EntityViewInfo();
//			view.getSorter().add(new SorterItemInfo("number"));
//			FilterInfo filter = new FilterInfo();
//			if(projectSet.size()>0)
//			filter.getFilterItems()
//					.add(
//							new FilterItemInfo("id",projectSet, CompareType.INCLUDE));
//			
//			filter.getFilterItems()
//			.add(
//					new FilterItemInfo("orgUnit.id", saleOrg.getId()
//							.toString()));
//			view.setFilter(filter);
//			if (type.equals("sellProject")) {
//				view.getSelector().add("*");
//			} else if (type.equals("subarea")) {
//				view.getSelector().add("*");
//				view.getSelector().add("buildings.*");
//				view.getSelector().add("subarea.*");
//				view.getSelector().add(
//						"subarea.sellProject.buildingProperty.id");
//			} else if (type.equals("building")) {
//				view.getSelector().add("*");
//				view.getSelector().add("subarea.*");
//				view.getSelector().add("buildings.*");
//				view.getSelector().add("buildings.subarea.*");
//			} else if (type.equals("unit")) {
//				view.getSelector().add("*");
//				view.getSelector().add("subarea.*");
//				view.getSelector().add("buildings.*");
//				view.getSelector().add("buildings.subarea.*");
//			} else if (type.equals("sellOrder")) {
//				view.getSelector().add("*");
//			}
//			SellProjectCollection sellProjects = SellProjectFactory
//					.getRemoteInstance().getSellProjectCollection(view);
//			
//			if (type.equals("sellOrder")) {
//				view = new EntityViewInfo();
//				view.getSelector().add("*");
//				view.getSelector().add("project.*");
//				SellOrderCollection sellOrderCollection = SellOrderFactory
//						.getRemoteInstance().getSellOrderCollection(view);
//				//�����Ϲ������̹�����ͬһ��Ŀ������̴���ʱ��ͬ��ȥ���˾�ͺ��ˡ�
//				//sortCollection(sellOrderCollection, "number");
//				for (int i = 0; i < sellOrderCollection.size(); i++) {
//					SellOrderInfo info = sellOrderCollection.get(i);
//					if(!info.isIsEnabled()){
//						continue;
//					}
//					SellProjectInfo project = info.getProject();
//					if (sellOrderMap.containsKey(project.getId().toString())) {
//						SellOrderCollection sellOrders = (SellOrderCollection) sellOrderMap
//								.get(project.getId().toString());
//						sellOrders.add(info);
//					} else {
//						SellOrderCollection sellOrders = new SellOrderCollection();
//						sellOrders.add(info);
//						sellOrderMap
//								.put(project.getId().toString(), sellOrders);
//					}
//				}
//			}
//			view = new EntityViewInfo();
//			filter = new FilterInfo();
//			for (int i = 0; i < sellProjects.size(); i++) {
//				SellProjectInfo sellProjectInfo = (SellProjectInfo)sellProjects.get(i);
//				KDTreeNode projectNode = new KDTreeNode(
//						sellProjectInfo.getName());
//				projectNode.setUserObject(sellProjectInfo);
//				projectNode.setCustomIcon(EASResource
//						.getIcon("imgTbtn_assistantaccount"));
//				if (type.equals("sellOrder")) {
//					SellOrderCollection sellOrders = (SellOrderCollection) sellOrderMap
//							.get(sellProjectInfo.getId().toString());
//					if (sellOrders != null) {
//						for (int j = 0; j < sellOrders.size(); j++) {
//							SellOrderInfo sellOrder = sellOrders.get(j);
//							KDTreeNode sellOrderNode = new KDTreeNode(
//									sellOrder.getName());
//							sellOrderNode.setCustomIcon(EASResource
//									.getIcon("imgTbtn_sortorder"));
//							sellOrderNode.setUserObject(sellOrder);
//							projectNode.add(sellOrderNode);
//						}
//					}
//				}
//				if (type.equals("subarea") || type.equals("building")
//						|| type.equals("unit")) {
//					SubareaCollection subareas = sellProjectInfo.getSubarea();
//					sortCollection(subareas, "number");
//					BuildingCollection buildings = sellProjectInfo
//							.getBuildings();
//					sortCollection(buildings, "number");
//					if (type.equals("building") || type.equals("unit")) {
//						for (int k = 0; k < buildings.size(); k++) {
//							BuildingInfo building = buildings.get(k);
//							SubareaInfo buildingSubArea = building.getSubarea();
//							if (buildingSubArea == null) {
//								KDTreeNode buildingNode = new KDTreeNode(
//										building.getName());
//								buildingNode.setCustomIcon(EASResource
//										.getIcon("imgTbtn_copytotier"));
//								buildingNode.setUserObject(building);
//								if (type.equals("unit")) {
//									addBuildingUnit(building, buildingNode);
//								}
//								projectNode.add(buildingNode);
//							}
//						}
//					}
//					for (int j = 0; j < subareas.size(); j++) {
//						SubareaInfo subarea = subareas.get(j);
//						KDTreeNode subareaNode = new KDTreeNode(
//								subarea.getName());
//						subareaNode.setUserObject(subarea);
//						subareaNode.setCustomIcon(EASResource
//								.getIcon("imgTbtn_showparent"));
//						if (type.equals("building") || type.equals("unit")) {
//							for (int k = 0; k < buildings.size(); k++) {
//								BuildingInfo building = buildings.get(k);
//								SubareaInfo buildingSubArea = building
//										.getSubarea();
//								if (buildingSubArea != null) {
//									String buildingAreaId = buildingSubArea
//											.getId().toString();
//									String subAreaId = subarea.getId()
//											.toString();
//									if (buildingAreaId.equals(subAreaId)) {
//										KDTreeNode buildingNode = new KDTreeNode(
//												building.getName());
//										buildingNode.setUserObject(building);
//										buildingNode.setCustomIcon(EASResource
//												.getIcon("imgTbtn_copytotier"));
//										if (type.equals("unit")) {
//											addBuildingUnit(building,
//													buildingNode);
//										}
//										subareaNode.add(buildingNode);
//									}
//								}
//							}
//						}
//						projectNode.add(subareaNode);
//					}
//				}
//				node.add(projectNode);
//
//			}
//		} else {
//			for (int i = 0; i < node.getChildCount(); i++) {
//				fillNode((DefaultMutableTreeNode) node.getChildAt(i), type);
//			}
//		}
//	}
//
//	private static void addBuildingUnit(BuildingInfo building,
//			DefaultKingdeeTreeNode buildingNode) {
//		if (building.getCodingType().equals(CodingTypeEnum.UnitFloorNum)) {
//			// ���뵥Ԫ
//			for (int index = 0; index < building.getUnitCount(); index++) {
//				KDTreeNode unitNode = new KDTreeNode(
//						(index + 1) + "��Ԫ");
//				unitNode.setUserObject(new Integer(index + 1));
//				buildingNode.add(unitNode);
//			}
//		}
//	}
//
//	public static TreeModel getSaleOrgTree(ItemAction actionOnLoad)
//			throws Exception {
//		TreeModel tree = getTree("saleOrg", actionOnLoad);
//		return tree;
//	}
//
//	public static TreeModel getSellProjectTree(ItemAction actionOnLoad)
//			throws Exception {
//		TreeModel tree = getTree("sellProject", actionOnLoad);
//		return tree;
//	}
//
//	public static TreeModel getSellOrderTree(ItemAction actionOnLoad)
//			throws Exception {
//		TreeModel tree = getTree("sellOrder", actionOnLoad);
//		return tree;
//	}
//
//	public static TreeModel getSubareaTree(ItemAction actionOnLoad)
//			throws Exception {
//		TreeModel tree = getTree("subarea", actionOnLoad);
//		return tree;
//	}
//
//	public static TreeModel getBuildingTree(ItemAction actionOnLoad)
//			throws Exception {
//		TreeModel tree = getTree("building", actionOnLoad);
//		return tree;
//	}
//
//	public static TreeModel getUnitTree(ItemAction actionOnLoad)
//			throws Exception {
//		TreeModel tree = getTree("unit", actionOnLoad);
//		return tree;
//	}
//
//	// public static void fillRoomTableByUnit(KDTable table, String buildingId,
//	// int unit) throws BOSException {
//	// FilterInfo filter = new FilterInfo();
//	// filter.getFilterItems().add(
//	// new FilterItemInfo("building.id", buildingId));
//	// filter.getFilterItems().add(
//	// new FilterItemInfo("unit", new Integer(unit)));
//	// EntityViewInfo view = new EntityViewInfo();
//	// view.getSelector().add("*");
//	// view.getSelector().add("roomModel.*");
//	// view.getSelector().add("buildingProperty.name");
//	// view.getSelector().add("building.name");
//	// view.getSelector().add("building.sellProject.id");
//	// view.getSelector().add("building.sellProject.name");
//	// view.getSelector().add("sellOrder.*");
//	// view.getSelector().add("lastPurchase.auditor.name");
//	// view.getSelector().add("lastPurchase.payType.id");
//	// view.getSelector().add("direction.*");
//	// view.getSelector().add("sight.*");
//	// view.getSelector().add("attachmentEntry.room.number");
//	// view.getSelector().add("lastKeepDownBill.bizDate");
//	// view.getSelector().add("lastKeepDownBill.description");
//	// view.getSelector().add("lastKeepDownBill.handler.name");
//	// view.setFilter(filter);
//	// RoomCollection rooms = RoomFactory.getRemoteInstance()
//	// .getRoomCollection(view);
//	// // fillRoomTableByCol(table, rooms);
//	// }
//
//	private static int setTableHeadByNode(KDTable table,
//			DefaultMutableTreeNode node, int colIndex, int level,
//			Map buildingMinNum, Map buildingMaxNum) {
//		int childRowIndex = colIndex;
//		for (int i = 0; i < node.getChildCount(); i++) {
//			DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) node
//					.getChildAt(i);
//			childRowIndex += setTableHeadByNode(table, child, childRowIndex,
//					level + 1, buildingMinNum, buildingMaxNum);
//		}
//		if (node.isLeaf()) {
//			Object object = node.getUserObject();
//			String key = null;
//			if (object instanceof BuildingInfo) {
//				key = ((BuildingInfo) object).getId().toString() + 0;
//			} else if (object instanceof Integer) {
//				int unit = ((Integer) object).intValue();
//				DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node
//						.getParent();
//				BuildingInfo building = (BuildingInfo) parent.getUserObject();
//				key = building.getId().toString() + unit;
//			} else {
//				return 0;
//			}
//			Integer minNum = (Integer) buildingMinNum.get(key);
//			Integer maxNum = (Integer) buildingMaxNum.get(key);
//			if (minNum == null || maxNum == null) {
//				return 0;
//			}
//			for (int i = minNum.intValue(); i <= maxNum.intValue(); i++) {
//				table.getHeadRow(level + 1).getCell(
//						i + colIndex - minNum.intValue()).setValue(i + "��");
//			}
//			table.getHeadRow(level).getCell(colIndex).setValue(
//					node.getUserObject());
//			table.getHeadMergeManager().mergeBlock(level, colIndex, level,
//					colIndex + maxNum.intValue() - minNum.intValue());
//			return maxNum.intValue() - minNum.intValue() + 1;
//		} else {
//			for (int i = 0; i < childRowIndex - colIndex; i++) {
//				table.getHeadRow(level).getCell(colIndex + i).setValue(
//						node.getUserObject());
//			}
//			table.getHeadMergeManager().mergeBlock(level, colIndex, level,
//					childRowIndex - 1);
//			return childRowIndex - colIndex;
//		}
//
//	}
//
//	/**
//	 * 
//	 * @param table
//	 * @param root
//	 * @param moneySysTypeEnum
//	 * @param roomColl �Է��䷶Χ��������
//	 * @throws BOSException
//	 * @author laiquan_luo
//	 */
//	public static void fillRoomTableByNode(KDTable table,
//			DefaultMutableTreeNode root, MoneySysTypeEnum moneySysTypeEnum, RoomCollection roomColl) throws BOSException {
//		if (root.getDepth() > 10) {
//			MsgBox.showInfo("̫�����֯��,���ܲ�ѯ!");
//		}
//		RoomDisplaySetting setting = new RoomDisplaySetting();
//		table.getStyleAttributes().setLocked(true);
//		table.getStyleAttributes().setHorizontalAlign(
//				HorizontalAlignment.CENTER);
//		table.getIndexColumn().getStyleAttributes().setHided(true);
//		table.getStyleAttributes().setWrapText(true);
//		table.removeColumns();
//		table.removeHeadRows();
//		table.removeRows();
//		Object objectNode = root.getUserObject();
//		FilterInfo filter = new FilterInfo();
//		if (objectNode instanceof Integer) {
//			BuildingInfo building = (BuildingInfo) ((DefaultMutableTreeNode) root
//					.getParent()).getUserObject();
//			Integer unit = (Integer) root.getUserObject();
//			filter.getFilterItems().add(
//					new FilterItemInfo("building.id", building.getId().toString()));
//			filter.getFilterItems().add(new FilterItemInfo("unit", unit));
//		} else {
//			Set idSet = new HashSet();
//			Enumeration enumeration = root.breadthFirstEnumeration();
//			while (enumeration.hasMoreElements()) {
//				DefaultMutableTreeNode element = (DefaultMutableTreeNode) enumeration
//						.nextElement();
//				if (element.getUserObject() instanceof BuildingInfo) {
//					idSet.add(((BuildingInfo) element.getUserObject()).getId()
//							.toString());
//				}
//			}
//			if (idSet.size() == 0) {
//				return;
//			}
//			filter.getFilterItems().add(
//					new FilterItemInfo("building.id", idSet,
//							CompareType.INCLUDE));
//		}
//		//����Է��䷶Χ��������
//		if(roomColl != null && roomColl.size() > 0)
//		{
//			Set tempSet = new HashSet();
//			for(int i = 0; i < roomColl.size(); i ++)
//			{
//				tempSet.add(roomColl.get(i).getId().toString());
//			}
//			filter.getFilterItems().add(new FilterItemInfo("id",tempSet,CompareType.INCLUDE));
//		}
//		
//		EntityViewInfo view = new EntityViewInfo();
//		view.getSelector().add("*");
//		view.getSelector().add("roomModel.*");
//		view.getSelector().add("buildingProperty.name");
//		view.getSelector().add("building.number");
//		view.getSelector().add("building.name");
//		view.getSelector().add("building.subarea.name");
//		view.getSelector().add("building.sellProject.id");
//		view.getSelector().add("building.sellProject.name");
//		view.getSelector().add("sellOrder.*");
//		view.getSelector().add("lastPurchase.id");
//		//-----ǩԼʱҪ��֤���Ϲ���״̬-by zhicheng_jin 081118
//		view.getSelector().add("lastPurchase.purchaseState");
//		view.getSelector().add("lastPurchase.contractTotalAmount");
//		//---------------------
//		view.getSelector().add("lastPurchase.auditor.name");
//		view.getSelector().add("lastPurchase.payType.id");
//		view.getSelector().add("lastSignContract.signDate");
//		view.getSelector().add("direction.*");
//		view.getSelector().add("sight.*");
//		view.getSelector().add("productType.*");
//		view.getSelector().add("attachmentEntry.room.number");
//		view.getSelector().add("lastKeepDownBill.bizDate");
//		view.getSelector().add("lastKeepDownBill.description");
//		view.getSelector().add("lastKeepDownBill.handler.name");
//		view.setFilter(filter);
//		RoomCollection rooms = RoomFactory.getRemoteInstance()
//				.getRoomCollection(view);
//		if (rooms.size() > 0) {
//			Map buildingMaxFloor = new HashMap();
//			Map buildingMinFloor = new HashMap();
//			Map buildingMaxNum = new HashMap();
//			Map buildingMinNum = new HashMap();
//			for (int i = 0; i < rooms.size(); i++) {
//				RoomInfo room = rooms.get(i);
//				String key = room.getBuilding().getId().toString()
//						+ room.getUnit();
//				Integer maxFloor = (Integer) buildingMaxFloor.get(key);
//				Integer minFloor = (Integer) buildingMinFloor.get(key);
//				Integer maxNum = (Integer) buildingMaxNum.get(key);
//				Integer minNum = (Integer) buildingMinNum.get(key);
//				if (minFloor == null || room.getFloor() < minFloor.intValue()) {
//					buildingMinFloor.put(key, new Integer(room.getFloor()));
//				}
//				if (maxFloor == null || room.getFloor() > maxFloor.intValue()) {
//					buildingMaxFloor.put(key, new Integer(room.getFloor()));
//				}
//				if (minNum == null
//						|| room.getSerialNumber() < minNum.intValue()) {
//					buildingMinNum
//							.put(key, new Integer(room.getSerialNumber()));
//				}
//				if (maxNum == null
//						|| room.getEndSerialNumber() > maxNum.intValue()) {
//					buildingMaxNum.put(key, new Integer(room
//							.getEndSerialNumber()));
//				}
//			}
//			IColumn column = table.addColumn();
//			column.setKey("floor");
//			column.setWidth(30);
//			Enumeration enumeration = root.depthFirstEnumeration();
//			boolean haveUnit = false;
//			while (enumeration.hasMoreElements()) {
//				DefaultMutableTreeNode element = (DefaultMutableTreeNode) enumeration
//						.nextElement();
//				Object object = element.getUserObject();
//				String key = null;
//				if (object instanceof Integer) {
//					int unit = ((Integer) object).intValue();
//					DefaultMutableTreeNode parent = (DefaultMutableTreeNode) element
//							.getParent();
//					BuildingInfo building = (BuildingInfo) parent
//							.getUserObject();
//					key = building.getId().toString() + unit;
//					haveUnit = true;
//				} else if (object instanceof BuildingInfo) {
//					BuildingInfo building = (BuildingInfo) object;
//					key = building.getId().toString() + 0;
//				}
//				Integer minNum = (Integer) buildingMinNum.get(key);
//				Integer maxNum = (Integer) buildingMaxNum.get(key);
//				if (minNum == null || maxNum == null) {
//					continue;
//				}
//				for (int i = minNum.intValue(); i <= maxNum.intValue(); i++) {
//					column = table.addColumn();
//					column.setKey(key + i);
//					column.setWidth(setting.getRoomWidth());
//				}
//			}
//			int minRow = 1;
//			int maxRow = 0;
//			Collection collection = buildingMaxFloor.values();
//			for (Iterator iter = collection.iterator(); iter.hasNext();) {
//				Integer num = (Integer) iter.next();
//				if (num.intValue() > maxRow)
//					maxRow = num.intValue();
//			}
//			for (int i = minRow; i <= maxRow; i++) {
//				IRow row = table.addRow();
//				row.setUserObject(new Integer(i));
//				row.setHeight(setting.getRoomHeight());
//				row.getCell("floor").setValue(
//						new Integer(maxRow - i + minRow) + "��");
//			}
//			fillRoomTable(table, rooms, setting, moneySysTypeEnum);
//			for (int i = 0; i < root.getDepth() + 1; i++) {
//				IRow row = table.addHeadRow(0);
//
//				String text[] = null;
//				if (haveUnit) {
//					text = new String[] { "���", "��Ԫ", "¥��", "����", "��Ŀ", "��֯",
//							"��֯", "��֯", "��֯", "��֯", "��֯", "��֯" };
//				} else {
//					text = new String[] { "���", "¥��", "����", "��Ŀ", "��֯", "��֯",
//							"��֯", "��֯", "��֯", "��֯", "��֯" };
//				}
//				row.getCell(0).setValue(text[i]);
//			}
//
//			int count = 1;
//			if (root.getChildCount() == 0) {
//				String key = null;
//				if (objectNode instanceof BuildingInfo) {
//					key = ((BuildingInfo) objectNode).getId().toString() + 0;
//				} else if (objectNode instanceof Integer) {
//					int unit = ((Integer) objectNode).intValue();
//					DefaultMutableTreeNode parent = (DefaultMutableTreeNode) root
//							.getParent();
//					BuildingInfo building = (BuildingInfo) parent
//							.getUserObject();
//					key = building.getId().toString() + unit;
//				} else {
//					return;
//				}
//				Integer minNum = (Integer) buildingMinNum.get(key);
//				Integer maxNum = (Integer) buildingMaxNum.get(key);
//				if (minNum == null || maxNum == null) {
//					return;
//				}
//				for (int i = minNum.intValue(); i <= maxNum.intValue(); i++) {
//					table.getHeadRow(0).getCell(i + 1 - minNum.intValue())
//							.setValue(i + "��");
//				}
//			} else {
//				for (int i = 0; i < root.getChildCount(); i++) {
//					count += setTableHeadByNode(table,
//							(DefaultMutableTreeNode) root.getChildAt(i), count,
//							0, buildingMinNum, buildingMaxNum);
//				}
//			}
//		}
//	}
//
//	private static void fillRoomTable(KDTable table, RoomCollection rooms,
//			RoomDisplaySetting setting, MoneySysTypeEnum moneySysTypeEnum) {
//		Integer minRow = (Integer) table.getRow(0).getUserObject();
//		for (int i = 0; i < rooms.size(); i++) {
//			RoomInfo room = rooms.get(i);
//			String buildingId = room.getBuilding().getId().toString();
//			String key = buildingId + room.getUnit();
//			for (int j = room.getSerialNumber(); j <= room.getEndSerialNumber(); j++) {
//				ICell cell = table.getCell(table.getRowCount()
//						+ minRow.intValue() - 1 - room.getFloor(), key + j);
//				cell.getStyleAttributes().setFont(setting.getFont());
//				cell.getStyleAttributes().setFontColor(setting.getFrontColor());
//				String text = "";
//				String displayField = setting.getDisplayField();
//				Object object = room.get(displayField);
//				if (displayField.equals("houseProperty")) {
//					object = room.getHouseProperty();
//				}
//				if (object != null) {
//					if (object instanceof BigDecimal) {
//						text += ((BigDecimal) object).setScale(2,
//								BigDecimal.ROUND_HALF_UP);
//					} else {
//						text += object;
//					}
//				}
//				if (cell.getUserObject() != null) {
//					MsgBox.showError("���巿���ص�,�������:" + text);
//					SysUtil.abort();
//				}
//				if (setting.getAttachDisType() == 0) {
//					if (room.getAttachmentEntry().size() > 0) {
//						text += "\n��" + room.getAttachmentEntry().size()
//								+ "������";
//					}
//				} else if (setting.getAttachDisType() == 1) {
//					if (room.getAttachmentEntry().size() > 0) {
//						text += "\n��:";
//						for (int k = 0; k < room.getAttachmentEntry().size(); k++) {
//							if (k != 0) {
//								text += ",";
//							}
//							text += room.getAttachmentEntry().get(k).getRoom()
//									.getNumber();
//						}
//					}
//				} else if (setting.getAttachDisType() == 2) {
//
//				}
//				cell.setValue(text);
//				
//				/*
//				 * �����޵ĸ���¥�ķ�����ɫ�ֿ�����
//				 */
//				if (MoneySysTypeEnum.TenancySys.equals(moneySysTypeEnum))
//				{
////					���ݷ��� ���� ��״̬����䲻ͬ����ɫ
//					TenancyStateEnum tenancyStatEnum = room.getTenancyState();
//					if (tenancyStatEnum == null || TenancyStateEnum.unTenancy.equals(tenancyStatEnum))
//					{
//						cell.getStyleAttributes().setBackground(
//								Color.GRAY);
//					}
//					else if(TenancyStateEnum.waitTenancy.equals(tenancyStatEnum))
//					{
//						cell.getStyleAttributes().setBackground(
//								Color.WHITE);
//					}
//					else if(TenancyStateEnum.newTenancy.equals(tenancyStatEnum))
//					{
//						cell.getStyleAttributes().setBackground(Color.RED);
//					}
//					else if(TenancyStateEnum.continueTenancy.equals(tenancyStatEnum))
//					{
//						cell.getStyleAttributes().setBackground(Color.BLUE);
//					}
//					else if(TenancyStateEnum.enlargeTenancy.equals(tenancyStatEnum))
//					{
//						cell.getStyleAttributes().setBackground(Color.YELLOW);
//					}
//					else if (TenancyStateEnum.keepTenancy.equals(tenancyStatEnum))
//					{
//						cell.getStyleAttributes().setBackground(
//								Color.GREEN);
//					}
//				}
//				else
//				{
//					RoomSellStateEnum state = room.getSellState();
//					if (state.equals(RoomSellStateEnum.Init))
//					{
//						cell.getStyleAttributes().setBackground(
//								setting.getInitColor());
//					} else if (state.equals(RoomSellStateEnum.OnShow))
//					{
//						cell.getStyleAttributes().setBackground(
//								setting.getOnShowColor());
//					} else if (state.equals(RoomSellStateEnum.PrePurchase))
//					{
//						cell.getStyleAttributes().setBackground(
//								setting.getPrePurColor());
//					} else if (state.equals(RoomSellStateEnum.Purchase))
//					{
//						cell.getStyleAttributes().setBackground(
//								setting.getPurColor());
//					} else if (state.equals(RoomSellStateEnum.Sign))
//					{
//						cell.getStyleAttributes().setBackground(
//								setting.getSignColor());
//					} else if (state.equals(RoomSellStateEnum.KeepDown))
//					{
//						cell.getStyleAttributes().setBackground(
//								setting.getKeepDownColor());
//					}
//				}
//				cell.setUserObject(room);
//			}
//			if (room.getSerialNumber() != room.getEndSerialNumber()) {
//				int columnOff = table.getColumnIndex(key
//						+ room.getSerialNumber());
//				table.getMergeManager().mergeBlock(
//						table.getRowCount() + minRow.intValue() - 1
//								- room.getFloor(),
//						columnOff,
//						table.getRowCount() + minRow.intValue() - 1
//								- room.getFloor(),
//						columnOff + room.getEndSerialNumber()
//								- room.getSerialNumber());
//			}
//		}
//		for (int i = 0; i < table.getRowCount(); i++) {
//			for (int j = 0; j < table.getColumnCount(); j++) {
//				ICell cell = table.getRow(i).getCell(j);
//				if (cell.getUserObject() == null) {
//					if (j == 0) {
//						cell.getStyleAttributes().setBackground(
//								new Color(184, 204, 221));
//					} else {
//						cell.getStyleAttributes().setBackground(Color.GRAY);
//					}
//				}
//			}
//
//		}
//	}
//
//	public static void fillRoomTableByCol(KDTable table, RoomCollection rooms) {
//		RoomDisplaySetting setting = new RoomDisplaySetting();
//		table.getStyleAttributes().setLocked(true);
//		table.getStyleAttributes().setHorizontalAlign(
//				HorizontalAlignment.CENTER);
//		table.getIndexColumn().getStyleAttributes().setHided(true);
//		table.removeColumns();
//		table.removeHeadRows();
//		table.removeRows();
//		int minRow = 9999;
//		int maxRow = -1;
//		int minCol = 9999;
//		int maxCol = -1;
//		String key = null;
//		for (int i = 0; i < rooms.size(); i++) {
//			RoomInfo room = rooms.get(i);
//			String buildingId = room.getBuilding().getId().toString();
//			if (key == null) {
//				key = buildingId + room.getUnit();
//			}
//			if (room.getFloor() < minRow) {
//				minRow = room.getFloor();
//			}
//			if (room.getFloor() > maxRow) {
//				maxRow = room.getFloor();
//			}
//			if (room.getSerialNumber() < minCol) {
//				minCol = room.getSerialNumber();
//			}
//			if (room.getEndSerialNumber() > maxCol) {
//				maxCol = room.getEndSerialNumber();
//			}
//		}
//		if (minRow <= maxRow && minCol <= maxCol) {
//			IColumn column = table.addColumn();
//			column.setWidth(30);
//			column.setKey(0 + "");
//			for (int i = minCol; i <= maxCol; i++) {
//				column = table.addColumn();
//				column.setWidth(setting.getRoomWidth());
//				column.getStyleAttributes().setWrapText(true);
//				column.setKey(key + i);
//			}
//			IRow headRow = table.addHeadRow();
//			headRow.getCell(0 + "").setValue("¥��");
//			for (int i = minCol; i <= maxCol; i++) {
//				headRow.getCell(key + i).setValue(i + "��");
//			}
//			for (int i = minRow; i <= maxRow; i++) {
//				IRow row = table.addRow();
//				row.setUserObject(new Integer(i));
//				row.setHeight(setting.getRoomHeight());
//				row.getCell(0 + "").setValue(
//						new Integer(maxRow - i + minRow) + "��");
//			}
//		}
//		fillRoomTable(table, rooms, setting, null);
//	}
//
//	public static RoomInfo createBaseRoom(RoomDesInfo roomDes) {
//		RoomInfo room = new RoomInfo();
//		room.setBuilding(roomDes.getBuilding());
//		room.setBuildingArea(roomDes.getBuildingArea());
//		room.setApportionArea(roomDes.getApportionArea());
//		room.setBalconyArea(roomDes.getBalconyArea());
//		room.setRoomArea(roomDes.getRoomArea());
//		room.setActualBuildingArea(roomDes.getActualBuildingArea());
//		room.setActualRoomArea(roomDes.getActualRoomArea());
//		room.setRoomModel(roomDes.getRoomModel());
//		room.setFloorHeight(roomDes.getFloorHeight());
//		room.setBuildingProperty(roomDes.getBuildingProperty());
//		room.setHouseProperty(roomDes.getHouseProperty());
//		room.setDirection(roomDes.getDirection());
//		room.setProductType(roomDes.getProductType());
//		room.setSight(roomDes.getSight());
//		room.setSellState(RoomSellStateEnum.Init);
//		room.setRoomJoinState(RoomJoinStateEnum.NOTJOIN);
//		room.setRoomBookState(RoomBookStateEnum.NOTBOOKED);
//		room.setRoomCompensateState(RoomCompensateStateEnum.NOCOMPENSATE);
//		room.setRoomLoanState(RoomLoanStateEnum.NOTLOANED);
//		room.setRoomACCFundState(RoomACCFundStateEnum.NOTFUND);
//		return room;
//	}
//
//	public static RoomCollection getRoomsByDes(String buildingId)
//			throws BOSException {
//		EntityViewInfo view = new EntityViewInfo();
//		view.getSelector().add("*");
//		view.getSelector().add("buildingProperty.*");
//		view.getSelector().add("roomModel.*");
//		view.getSelector().add("direction.*");
//		view.getSelector().add("sight.*");
//		view.getSelector().add("building.name");
//		view.getSelector().add("building.number");
//		view.getSelector().add("productType.*");
//		FilterInfo filter = new FilterInfo();
//		view.setFilter(filter);
//		filter.getFilterItems().add(
//				new FilterItemInfo("building.id", buildingId));
//		RoomDesCollection dess = RoomDesFactory.getRemoteInstance()
//				.getRoomDesCollection(view);
//		RoomCollection rooms = new RoomCollection();
//		Timestamp currentTime = new Timestamp(new Date().getTime());
//		try {
//			//currentTime = FDCCommonServerHelper.getServerTimeStamp();
//			currentTime = FDCSQLFacadeFactory.getRemoteInstance().getServerTime();//Ǩ��֧��Ҫ�������������ǻ�ȡ������ʱ��
//		} catch (BOSException e) {
//			e.printStackTrace();
//		}
//		UserInfo currentUser = SysContext.getSysContext().getCurrentUserInfo();
//		CtrlUnitInfo currentCU = SysContext.getSysContext()
//				.getCurrentCtrlUnit();
//		for (int i = 0; i < dess.size(); i++) {
//			RoomDesInfo des = dess.get(i);
//			BuildingInfo building = des.getBuilding();
//			RoomNumPrefixEnum numPrefix = des.getNumPrefixType();
//			if (des.getUnitBegin() == 0) {
//				if (des.getFloorEnd() == 0) {
//					for (int num = des.getSerialNumberBegin(); num <= des
//							.getSerialNumberEnd(); num++) {
//						RoomInfo room = createBaseRoom(des);
//						room.setUnit(0);
//						room.setFloor(des.getFloorBegin());
//						room.setSerialNumber(num);
//						room.setEndSerialNumber(num);
//						String strNum = getSerialNumberStr(num, des
//								.isIsConvertToChar());
//						room.setNumber(getRoomNumIncludeBuildPrefix(strNum, building, numPrefix));
//						room.setName(room.getNumber());
//						room.setCreateTime(currentTime);
//						room.setLastUpdateTime(currentTime);
//						room.setCreator(currentUser);
//						room.setLastUpdateUser(currentUser);
//						room.setCU(currentCU);
//						rooms.add(room);
//					}
//				} else {
//					for (int floor = des.getFloorBegin(); floor <= des
//							.getFloorEnd(); floor++) {
//						for (int num = des.getSerialNumberBegin(); num <= des
//								.getSerialNumberEnd(); num++) {
//							RoomInfo room = createBaseRoom(des);
//							room.setUnit(0);
//							room.setFloor(floor);
//							room.setSerialNumber(num);
//							room.setEndSerialNumber(num);
//							String strNum = getSerialNumberStr(num, des
//									.isIsConvertToChar());
//							room.setNumber(getRoomNumIncludeBuildPrefix(floor + "" + strNum, building, numPrefix));
//							room.setName(room.getNumber());
//							room.setCreateTime(currentTime);
//							room.setLastUpdateTime(currentTime);
//							room.setCreator(currentUser);
//							room.setLastUpdateUser(currentUser);
//							room.setCU(currentCU);
//							rooms.add(room);
//						}
//					}
//				}
//			} else {
//				for (int unit = des.getUnitBegin(); unit <= des.getUnitEnd(); unit++) {
//					for (int floor = des.getFloorBegin(); floor <= des
//							.getFloorEnd(); floor++) {
//						for (int num = des.getSerialNumberBegin(); num <= des
//								.getSerialNumberEnd(); num++) {
//							RoomInfo room = createBaseRoom(des);
//							room.setUnit(unit);
//							room.setFloor(floor);
//							room.setSerialNumber(num);
//							room.setEndSerialNumber(num);
//							String strNum = getSerialNumberStr(num, des
//									.isIsConvertToChar());
//							room.setNumber(getRoomNumIncludeBuildPrefix(unit + "-" + floor + "" + strNum, building, numPrefix));
//							room.setName(room.getNumber());
//							room.setCreateTime(currentTime);
//							room.setLastUpdateTime(currentTime);
//							room.setCreator(currentUser);
//							room.setLastUpdateUser(currentUser);
//							room.setCU(currentCU);
//							rooms.add(room);
//						}
//					}
//				}
//			}
//		}
//		return rooms;
//	}
//
//	/**
//	 * ��ü���¥��ǰ׺��ķ������
//	 * @param strNum
//	 * 			����¥��ǰ׺ǰ�ķ������
//	 * @param building
//	 * 			¥��
//	 * @param numPrefixType
//	 * 			¥������ǰ׺����֯��ʽ
//	 * @return ����¥��ǰ׺��ķ������
//	 * */
//	private static String getRoomNumIncludeBuildPrefix(String strNum, BuildingInfo building, RoomNumPrefixEnum numPrefixType){
//		String res = "";
//		if(RoomNumPrefixEnum.buildNumPrefix.equals(numPrefixType)){
//			String buildNum = building.getNumber();
//			if(buildNum != null){
//				res = buildNum + "-" + strNum;
//			}else{
//				log.warn("roomDes's buildingNum is null.check it carefully.");
//			}
//		}else if(RoomNumPrefixEnum.buildNamePrefix.equals(numPrefixType)){
//			String buildName = building.getName();
//			if(buildName != null){
//				res = buildName + "-" + strNum;
//			}else{
//				log.warn("roomDes's buildingName is null.check it carefully.");
//			}
//		}else{
//			res = strNum;
//		}
//		return res;
//	}
//	
//	private static String getSerialNumberStr(int num, boolean isConvertToChar) {
//		String strNum = "";
//		if (isConvertToChar) {
//			char a = 'A';
//			a += (num - 1);
//			strNum += a;
//		} else {
//			strNum = num + "";
//			if (num < 10) {
//				strNum = "0" + strNum;
//			}
//		}
//		return strNum;
//	}
//
//	// public static RoomSignContractInfo getRoomSignContract(RoomInfo room) {
//	// if (!room.getSellState().equals(RoomSellStateEnum.PrePurchase)
//	// && !room.getSellState().equals(RoomSellStateEnum.Purchase)
//	// && !room.getSellState().equals(RoomSellStateEnum.Sign)) {
//	// return null;
//	// }
//	// if (room.getHouseProperty().equals(HousePropertyEnum.Attachment)) {
//	// return null;
//	// }
//	// EntityViewInfo view = new EntityViewInfo();
//	// FilterInfo filter = new FilterInfo();
//	// filter.getFilterItems().add(
//	// new FilterItemInfo("room.id", room.getId().toString()));
//	// filter.getFilterItems().add(
//	// new FilterItemInfo("isBlankOut", Boolean.FALSE));
//	// view.setFilter(filter);
//	// RoomSignContractCollection signs = null;
//	// try {
//	// signs = RoomSignContractFactory.getRemoteInstance()
//	// .getRoomSignContractCollection(view);
//	// } catch (BOSException e) {
//	// e.printStackTrace();
//	// }
//	// if (signs.size() > 1) {
//	// MsgBox.showInfo("�����ЧǩԼ��ͬ,ϵͳ����!");
//	// SysUtil.abort();
//	// }
//	// if (signs.size() == 0) {
//	// if (room.getSellState().equals(RoomSellStateEnum.Sign)) {
//	// MsgBox.showInfo("û����ЧǩԼ��ͬ,ϵͳ����!");
//	// SysUtil.abort();
//	// }
//	// return null;
//	// }
//	// return signs.get(0);
//	// }
//
//	public static RoomJoinInfo getRoomJoinIn(RoomInfo room) {
//		if (room.getRoomJoinState() == null
//				|| room.getRoomJoinState().equals(RoomJoinStateEnum.NOTJOIN)) {
//			return null;
//		}
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(
//				new FilterItemInfo("room.id", room.getId().toString()));
//		view.setFilter(filter);
//		RoomJoinCollection joins = null;
//		try {
//			joins = RoomJoinFactory.getRemoteInstance().getRoomJoinCollection(
//					view);
//		} catch (BOSException e) {
//			e.printStackTrace();
//		}
//		if (joins.size() > 1) {
//			MsgBox.showInfo("������,ϵͳ����!");
//			SysUtil.abort();
//		}
//		if (joins.size() == 0) {
//			return null;
//			// MsgBox.showInfo("û�����,ϵͳ����!");
//			// SysUtil.abort();
//		}
//		return joins.get(0);
//	}
//
//	public static RoomAreaCompensateInfo getRoomAreaCompensation(RoomInfo room) {
//		if (room.getRoomCompensateState() == null
//				|| room.getRoomCompensateState().equals(
//						RoomCompensateStateEnum.NOCOMPENSATE)) {
//			return null;
//		}
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(
//				new FilterItemInfo("room.id", room.getId().toString()));
//		view.setFilter(filter);
//		RoomAreaCompensateCollection roomAreaCompens = null;
//		try {
//			roomAreaCompens = RoomAreaCompensateFactory.getRemoteInstance()
//					.getRoomAreaCompensateCollection(view);
//		} catch (BOSException e) {
//			e.printStackTrace();
//		}
//		if (roomAreaCompens.size() > 1) {
//			MsgBox.showInfo("�������,ϵͳ����!");
//			SysUtil.abort();
//		}
//		if (roomAreaCompens.size() == 0) {
//			return null;
//			// MsgBox.showInfo("û�в���,ϵͳ����!");
//			// SysUtil.abort();
//		}
//		return roomAreaCompens.get(0);
//	}
//
//	public static RoomLoanInfo getRoomLoan(RoomInfo room) {
//		if (room.getRoomLoanState() == null
//				|| room.getRoomLoanState().equals(RoomLoanStateEnum.NOTLOANED)
//				&& room.getRoomACCFundState().equals(
//						RoomACCFundStateEnum.NOTFUND)) {
//			return null;
//		}
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(
//				new FilterItemInfo("room.id", room.getId().toString()));
//		view.setFilter(filter);
//		RoomLoanCollection joins = null;
//		try {
//			joins = RoomLoanFactory.getRemoteInstance().getRoomLoanCollection(
//					view);
//		} catch (BOSException e) {
//			e.printStackTrace();
//		}
//		if (joins.size() > 1) {
//			MsgBox.showInfo("�������,ϵͳ����!");
//			SysUtil.abort();
//		}
//		if (joins.size() == 0) {
//			return null;
//			// MsgBox.showInfo("û�а���,ϵͳ����!");
//			// SysUtil.abort();
//		}
//		return joins.get(0);
//	}
//
//	public static RoomPropertyBookInfo getRoomPropertyBook(RoomInfo room) {
//		if (room.getRoomBookState() == null
//				|| room.getRoomBookState().equals(RoomBookStateEnum.NOTBOOKED)) {
//			return null;
//		}
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(
//				new FilterItemInfo("room.id", room.getId().toString()));
//		view.setFilter(filter);
//		RoomPropertyBookCollection joins = null;
//		try {
//			joins = RoomPropertyBookFactory.getRemoteInstance()
//					.getRoomPropertyBookCollection(view);
//		} catch (BOSException e) {
//			e.printStackTrace();
//		}
//		if (joins.size() > 1) {
//			MsgBox.showInfo("�����Ȩ�Ǽ�,ϵͳ����!");
//			SysUtil.abort();
//		}
//		if (joins.size() == 0) {
//			return null;
//			// MsgBox.showInfo("û�в�Ȩ�Ǽ�,ϵͳ����!");
//			// SysUtil.abort();
//		}
//		return joins.get(0);
//	}
//
//	public static SaleOrgUnitInfo getCurrentSaleOrg() {
//		SaleOrgUnitInfo saleUnit = SysContext.getSysContext()
//				.getCurrentSaleUnit();
//		if (saleUnit == null) {
//			MsgBox.showInfo("��ǰ��֯������������,���ܽ���!");
//			SysUtil.abort();
//		}
//		return saleUnit;
//	}
//
//	private static void fillIdSetNode(Set idSet, DefaultKingdeeTreeNode node) {
//		if (node.isLeaf()) {
//			OrgStructureInfo org = (OrgStructureInfo) node.getUserObject();
//			idSet.add(org.getUnit().getId().toString());
//		} else {
//			for (int i = 0; i < node.getChildCount(); i++) {
//				fillIdSetNode(idSet, (DefaultKingdeeTreeNode) node
//						.getChildAt(i));
//			}
//		}
//
//	}
//
//	public static Set getLeafCompanyIdSet(ItemAction actionOnLoad) {
//		TreeModel orgTree = null;
//		Set idSet = new HashSet();
//		try {
//			orgTree = getSaleOrgTree(actionOnLoad);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if(orgTree == null)
//			return idSet;
//		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) orgTree
//				.getRoot();
//		
//		fillIdSetNode(idSet, node);
//		return idSet;
//	}
//
//	/**
//	 * ͨ��excel�ļ�����е������� ǰ���Ǵ�����ͬ��ָ���е�ֵ
//	 * @param fileName		excel�ļ�
//	 * @param table			
//	 * @param headRowCount	��ͷ������
//	 * @param keyColumnCount  ָ����Ϊ�Ƚϵ��е�λ��
//	 * @return
//	 * @throws Exception
//	 */
//	public static int importExcelToTable(String fileName, KDTable table,
//			int headRowCount, int keyColumnCount) throws Exception {
//		if (headRowCount > 1) {
//			MsgBox.showError("����ͷû��ʵ��,��Ը��д����д!");
//			SysUtil.abort();
//		}
//		KDSBook kdsbook = null;
//		try {
//
//			kdsbook = POIXlsReader.parse2(fileName);
//		} catch (Exception e) {
//			e.printStackTrace();
//			MsgBox.showError("��EXCEL����,EXCEl��ʽ��ƥ��!");
//			SysUtil.abort();
//		}
//		if (kdsbook == null) {
//			SysUtil.abort();
//		}
//		Sheet excelSheet = KDSBookToBook.traslate(kdsbook).getSheet(0);
//		Map colNameMap = new HashMap();
//		int maxRow = excelSheet.getMaxRowIndex();
//		int maxColumn = excelSheet.getMaxColIndex();
//		for (int col = 0; col <= maxColumn; col++) {
//			String excelColName = excelSheet.getCell(0, col, true).getText();
//			colNameMap.put(excelColName, new Integer(col));
//		}
//		int successCount = 0;
//		for (int rowIndex = 1; rowIndex <= maxRow; rowIndex++) {
//			String key = "";
//			int totalKeyCount = 0;
//			int count = 0;
//			for (int i = 0; i < table.getColumnCount(); i++) {
//				if (count >= keyColumnCount) {
//					break;
//				}
//				totalKeyCount++;
//				if (table.getColumn(i).getStyleAttributes().isHided()) {
//					continue;
//				}
//				String colName = (String) table.getHeadRow(0).getCell(i)
//						.getValue();
//				Integer colInt = (Integer) colNameMap.get(colName);
//				if (colInt == null) {
//					MsgBox.showError("��ͷ�ṹ��һ��!����ϵĹؼ���:" + colName
//							+ "��EXCEL��û�г���!");
//					SysUtil.abort();
//				}
//				String text = excelSheet.getCell(rowIndex, colInt.intValue(),
//						true).getText();
//				if (!StringUtils.isEmpty(text)) {
//					key += text;
//				}
//				count++;
//			}
//
//			IRow row = getRowByRoomKey(table, key, keyColumnCount);
//			if (row == null) {
//				continue;
//			}
//			successCount++;
//			for (int i = totalKeyCount; i < table.getColumnCount(); i++) {
//				ICell tblCell = row.getCell(i);
//				if (tblCell == null || tblCell.getStyleAttributes().isLocked())
//					continue;
//				String colName = (String) table.getHeadRow(0).getCell(i)
//						.getValue();
//				Integer colInt = (Integer) colNameMap.get(colName);
//				if (colInt == null) {
//					continue;
//				}
//				Variant cellRawVal = excelSheet.getCell(rowIndex,
//						colInt.intValue(), true).getValue();
//				if (Variant.isNull(cellRawVal)) {
//					continue;
//				}
//				if (cellRawVal.isNumeric()) {
//					BigDecimal colValue = cellRawVal.toBigDecimal();
//					tblCell.setValue(colValue);
//				} else if (cellRawVal.isString()) {
//					String colValue = cellRawVal.toString();
//					tblCell.setValue(colValue);
//				}
//			}
//		}
//		return successCount;
//	}
//
//	private static IRow getRowByRoomKey(KDTable table, String key,
//			int keyColumnCount) {
//		for (int i = 0; i < table.getRowCount(); i++) {
//			IRow row = table.getRow(i);
//			String aKey = "";
//			int count = 0;
//			for (int j = 0; j < table.getColumnCount(); j++) {
//				if (count >= keyColumnCount) {
//					break;
//				}
//				if (table.getColumn(j).getStyleAttributes().isHided()) {
//					continue;
//				}
//				Object value = row.getCell(j).getValue();
//				if (value != null) {
//					aKey += value.toString();
//				}
//				count++;
//			}
//			if (aKey.equals(key)) {
//				return row;
//			}
//		}
//		return null;
//	}
//
//	/**
//	 * ����ѡ��excel�ļ��ĶԻ���
//	 * @param ui
//	 * @return  ����ѡ����ļ���ַ
//	 */
//	public static String showExcelSelectDlg(CoreUIObject ui) {
//		KDFileChooser chsFile = new KDFileChooser();
//		String XLS = "xls";
//		String Key_File = "Key_File";
//		SimpleFileFilter Filter_Excel = new SimpleFileFilter(XLS, "MS Excel"
//				+ LanguageManager.getLangMessage(Key_File, WizzardIO.class,
//						"����ʧ��"));
//		chsFile.addChoosableFileFilter(Filter_Excel);
//		int ret = chsFile.showOpenDialog(ui);
//		if (ret != JFileChooser.APPROVE_OPTION)
//			SysUtil.abort();
//
//		File file = chsFile.getSelectedFile();
//		String fileName = file.getAbsolutePath();
//		return fileName;
//	}
//
//	public static void sortCollection(IObjectCollection coll, String key) {
//		Map map = new TreeMap();
//		Iterator iterator = coll.iterator();
//		while (iterator.hasNext()) {
//			CoreBaseInfo info = (CoreBaseInfo) iterator.next();
//			map.put(info.get(key), info.clone());
//		}
//		coll.clear();
//		iterator = map.values().iterator();
//		while (iterator.hasNext()) {
//			CoreBaseInfo info = (CoreBaseInfo) iterator.next();
//			coll.addObject(info);
//		}
//	}
//
//	/**
//	 * ����ȡ��Ľ��,��decimal%unit
//	 */
//	public static BigDecimal remainder(BigDecimal decimal, BigDecimal unit) {
//		BigDecimal res = decimal.divide(unit, 0, BigDecimal.ROUND_DOWN);
//		return decimal.subtract(unit.multiply(res));
//	}
//	
//	/**
//	 * ȡ��������ǰʱ�䣬���쳣ʱ�����ر��ص�ǰʱ��
//	 * */
//	public static Timestamp getCurrentTime(){
//		Timestamp currentTime = new Timestamp(new Date().getTime());
//		try {
//			currentTime = FDCSQLFacadeFactory.getRemoteInstance().getServerTime();
//		} catch (BOSException e) {
//			e.printStackTrace();
//		}
//		return currentTime;
//	}
//	
//	/**
//	 * ��ʼ��KDFormattedTextField����ػ�������
//	 * */
//	public static void setTextFormat(KDFormattedTextField textField) {
//		textField.setRemoveingZeroInDispaly(false);
//		textField.setRemoveingZeroInEdit(false);
//		textField.setPrecision(2);
//		textField.setHorizontalAlignment(JTextField.RIGHT);
//		textField.setSupportedEmpty(true);
//	}
//	
//	/**
//	 * ��÷���Ĳ����
//	 * @param room
//	 * 			�����㲹��ķ���
//	 * @return �����.����ΪNull����ʵ�����δ��������·���Null
//	 * */
//	public static BigDecimal getCompensateAmount(RoomInfo room) {
//		if (room == null || !room.isIsActualAreaAudited()) {
//			return null;
//		}		
//		boolean isCalByRoomArea = room.isIsCalByRoomArea();
//		BigDecimal areaCompensateAmount = null;
//		if (isCalByRoomArea) {
//			BigDecimal actualRoomArea = room.getActualRoomArea();
//			if (actualRoomArea != null) {
//				areaCompensateAmount = room.getRoomPrice().multiply(
//						actualRoomArea.subtract(room.getRoomArea()));
//			}
//		} else {
//			BigDecimal actualBuildingArea = room.getActualBuildingArea();
//			if (actualBuildingArea != null) {
//				areaCompensateAmount = room.getBuildPrice().multiply(
//						actualBuildingArea.subtract(room.getBuildingArea()));
//			}
//		}
//		return areaCompensateAmount;
//	}
//	
//	
//	/**
//	 * ��ָ���������ʹ�ñ������
//	 * ������ڱ�����������ָ���������;�����������ʾ,�����������ı���
//	 * @param editData
//	 * @param oprtState
//	 * @param txtNumber
//	 */
//	public static void setNumberEnabled(CoreBaseInfo editData,String oprtState,KDTextField txtNumber)
//	{
//		if(editData == null)
//		return ;
//			
//		OrgUnitInfo crrOu = SysContext.getSysContext().getCurrentSaleUnit();   //��ǰ������֯;
//		if(crrOu==null)
//			crrOu = SysContext.getSysContext().getCurrentOrgUnit();            //��ǰ��֯
//		
//		
//		try{
//			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
//			if(iCodingRuleManager.isExist(editData, crrOu.getId().toString())){			
//				if(txtNumber.isVisible() && txtNumber.isEnabled() && txtNumber.isEditable()) {
//						txtNumber.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
//						txtNumber.setEditable(false);	
//				}	
//				
//				if(oprtState.equals(STATUS_ADDNEW) &&  iCodingRuleManager.isAddView(editData,crrOu.getId().toString())) {
//					txtNumber.setText(iCodingRuleManager.getNumber(editData,crrOu.getId().toString()));
//					if(editData instanceof DataBaseInfo) {
//						DataBaseInfo dataBase = (DataBaseInfo)editData;
//						dataBase.setNumber(txtNumber.getText());
//					}
//				}	
//			}		
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	private static String getNumberFromCodeRule(CoreBaseInfo editData)throws CodingRuleException, 
//	EASBizException, BOSException {
//		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
//		.getRemoteInstance();
//		String sysNumber = null;
//			String companyID = iCodingRuleManager.getCurrentAppOUID(editData);
//			if (iCodingRuleManager.isExist(editData, companyID)) {
//				// û�����öϺ�֧�ֹ��ܣ���ʱ��ȡ�˱����������ı���
//				sysNumber = iCodingRuleManager.getNumber(editData, companyID);
//			}
//		return sysNumber;
//	}
//	
//	/**
//	 * �Ƿ���Ҫ���ɵ��ݱ��
//	 * 
//	 * @return
//	 * @throws CodingRuleException
//	 * @throws EASBizException
//	 * @throws BOSException
//	 *             2006-11-24 wangyb
//	 */
//	private static boolean isRequireGenNewNumber(CoreBaseInfo editData,String oprtState,KDTextField txtNumber) throws CodingRuleException,
//	EASBizException, BOSException {
//		if (!oprtState.equals(STATUS_ADDNEW)
//				|| !StringUtils.isEmpty(txtNumber.getText()) || !isAutoNumber(editData)
//				|| !isAddView(editData)) {
//			return false;
//		}
//		return true;
//	} 
//	
//	/**
//	 * �Ƿ������Զ����ɱ���ı������
//	 * @return Ҫ�Զ�����ʱ����(true)���򷵻�(false)
//	 * @throws CodingRuleException
//	 * @throws EASBizException
//	 * @throws BOSException
//	 *             2006-2-22 wangyb
//	 */
//	private static boolean isAutoNumber(CoreBaseInfo editData) throws CodingRuleException,
//	EASBizException, BOSException {
//		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
//		.getRemoteInstance();
//		OrgUnitInfo crrOu = SysContext.getSysContext().getCurrentSaleUnit();   
//		if(crrOu==null)
//			crrOu = SysContext.getSysContext().getCurrentOrgUnit();
//		if (iCodingRuleManager.isExist(editData, crrOu.getId().toString())) {
//			// ����������������Ϊ�Զ����ɱ���
//			return true;
//		}
//		return false;
//	}
//	
//	/**
//	 * 
//	 * �������ж��Ƿ���������ʾ
//	 * 
//	 * @return boolean
//	 * @throws BOSException
//	 * @throws EASBizException
//	 * @throws CodingRuleException
//	 * @author:daij ����ʱ�䣺2005-8-22
//	 *              <p>
//	 */
//	private static boolean isAddView(CoreBaseInfo editData) throws CodingRuleException, EASBizException,
//	BOSException {
//		OrgUnitInfo crrOu = SysContext.getSysContext().getCurrentSaleUnit();   
//		if(crrOu==null)
//			crrOu = SysContext.getSysContext().getCurrentOrgUnit();
//		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
//		.getRemoteInstance();
//		return iCodingRuleManager.isAddView(editData, crrOu.getId()
//				.toString());
//	}
//	
//	public static RoomCollection getRooms(BuildingInfo info) throws BOSException
//	{
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		view.setFilter(filter);
//		view.getSelector().add("*");
//		filter.getFilterItems().add(
//				new FilterItemInfo("building.id", info.getId().toString()));
//		RoomCollection rooms = RoomFactory.getRemoteInstance().getRoomCollection(view);
//		return rooms;
//	}
//	
//	public static RoomAreaCompensateInfo getRoomAreaCompensationInReport(RoomInfo room) {
//		if (room.getRoomCompensateState() == null
//				|| room.getRoomCompensateState().equals(
//						RoomCompensateStateEnum.NOCOMPENSATE)) {
//			return null;
//		}
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(
//				new FilterItemInfo("room.id", room.getId().toString()));
//		view.setFilter(filter);
//		RoomAreaCompensateCollection roomAreaCompens = null;
//		try {
//			roomAreaCompens = RoomAreaCompensateFactory.getRemoteInstance()
//					.getRoomAreaCompensateCollection(view);
//		} catch (BOSException e) {
//			e.printStackTrace();
//		}
//		if (roomAreaCompens.size() > 1) {
//			log.error("�������,ϵͳ����!");
//		}
//		if (roomAreaCompens.size() == 0) {
//			return null;
//			// MsgBox.showInfo("û�в���,ϵͳ����!");
//			// SysUtil.abort();
//		}
//		return roomAreaCompens.get(0);
//	}
//	
//	public static RoomJoinInfo getRoomJoinInInReport(RoomInfo room) {
//		if (room.getRoomJoinState() == null
//				|| room.getRoomJoinState().equals(RoomJoinStateEnum.NOTJOIN)) {
//			return null;
//		}
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(
//				new FilterItemInfo("room.id", room.getId().toString()));
//		view.setFilter(filter);
//		RoomJoinCollection joins = null;
//		try {
//			joins = RoomJoinFactory.getRemoteInstance().getRoomJoinCollection(
//					view);
//		} catch (BOSException e) {
//			e.printStackTrace();
//		}
//		if (joins.size() > 1) {
//			log.error("������,ϵͳ����!");
//		}
//		if (joins.size() == 0) {
//			return null;
//			// MsgBox.showInfo("û�����,ϵͳ����!");
//			// SysUtil.abort();
//		}
//		return joins.get(0);
//	}
//	
////	kuangbiao add���ڈ�� 
//	public static RoomLoanInfo getRoomLoanInReport(RoomInfo room) {
//		if (room.getRoomLoanState() == null
//				|| room.getRoomLoanState().equals(RoomLoanStateEnum.NOTLOANED)
//				&& room.getRoomACCFundState().equals(
//						RoomACCFundStateEnum.NOTFUND)) {
//			return null;
//		}
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(
//				new FilterItemInfo("room.id", room.getId().toString()));
//		view.setFilter(filter);
//		RoomLoanCollection joins = null;
//		try {
//			joins = RoomLoanFactory.getRemoteInstance().getRoomLoanCollection(
//					view);
//		} catch (BOSException e) {
//			e.printStackTrace();
//		}
//		if (joins.size() > 1) {
//			log.error("�������,ϵͳ����!");
//		}
//		if (joins.size() == 0) {
//			return null;
//			// MsgBox.showInfo("û�а���,ϵͳ����!");
//			// SysUtil.abort();
//		}
//		return joins.get(0);
//	}
//	
//	public static RoomPropertyBookInfo getRoomPropertyBookInReport(RoomInfo room) {
//		if (room.getRoomBookState() == null
//				|| room.getRoomBookState().equals(RoomBookStateEnum.NOTBOOKED)) {
//			return null;
//		}
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(
//				new FilterItemInfo("room.id", room.getId().toString()));
//		view.setFilter(filter);
//		RoomPropertyBookCollection joins = null;
//		try {
//			joins = RoomPropertyBookFactory.getRemoteInstance()
//					.getRoomPropertyBookCollection(view);
//		} catch (BOSException e) {
//			e.printStackTrace();
//		}
//		if (joins.size() > 1) {
//			log.error("�����Ȩ�Ǽ�,ϵͳ����!");
//		}
//		if (joins.size() == 0) {
//			return null;
//			// MsgBox.showInfo("û�в�Ȩ�Ǽ�,ϵͳ����!");
//			// SysUtil.abort();
//		}
//		return joins.get(0);
//	}
//	/**
//	 * �鿴�������ܷ�ɾ�������޸ģ���Ҫ�Ǵ�ҵ����ȥ����
//	 * ���ǵ���ǰ�����ݣ��տ������û��seq�����ŵģ��ж����������ݶ��ǲ����޸ĵ�
//	 * ����һ��lenght Ϊ2 �����飬�����false �򷵻�   new String{"false","ԭ��"};
//	 * @param billId
//	 * @return
//	 * @author laiquan_luo
//	 * @throws BOSException 
//	 */
//	public static String[] isReceiveBillCanRemoveOrEdit(String billId) throws BOSException
//	{
//		ReceivingBillInfo receivingBillInfo = null;
//		SelectorItemCollection selColl = new SelectorItemCollection();
//		selColl.add("fdcReceiveBill.*");
//		selColl.add("fdcReceiveBill.moneyDefine.*");
//		selColl.add("fdcReceiveBill.purchase.*");
//		selColl.add("fdcReceiveBill.tenancyContract");
//		selColl.add("fdcReceiveBill.purchase.payListEntry.*");
//		try
//		{
//			receivingBillInfo = ReceivingBillFactory.getRemoteInstance().getReceivingBillInfo(new ObjectUuidPK(BOSUuid.read(billId)),selColl);
//		} catch (EASBizException e)
//		{
//			throw new BOSException(e);
//		} catch (BOSException e)
//		{
//			throw new BOSException(e);
//		}
//		if(MoneySysTypeEnum.SalehouseSys.equals(receivingBillInfo.getFdcReceiveBill().getMoneySysType()))
//		{
//			return isReceiveBillCanRemoveOrEditForSHE(receivingBillInfo);
//		}
//		else if(MoneySysTypeEnum.TenancySys.equals(receivingBillInfo.getFdcReceiveBill().getMoneySysType()))
//		{
//			return isReceiveBillCanRemoveOrEditForTEN(receivingBillInfo);
//		}
//		else
//		{
//			return new String [] {"false","�õ��ݲ��ܽ���ɾ�����޸Ĳ�����"};
//		}
//	}
//	/**
//	 * �ж������տ�Ƿ���ɾ���޸�
//	 * @param receivingBillInfo
//	 * @return
//	 * @throws BOSException
//	 */
//	private static String[] isReceiveBillCanRemoveOrEditForTEN(ReceivingBillInfo receivingBillInfo) throws BOSException
//	{
//		FDCReceiveBillInfo fdcReceiveBillInfo = receivingBillInfo.getFdcReceiveBill();
//		//�˿�����޸�
//		if(ReceiveBillTypeEnum.refundment.equals(fdcReceiveBillInfo.getBillTypeEnum()))
//		{
//			return new String[]{"true",null};
//		}
//		
//		TenancyBillInfo tenancyBillInfo = fdcReceiveBillInfo.getTenancyContract();
//		RoomInfo room = fdcReceiveBillInfo.getRoom();
//		//��ǰ���տ���ݵ�seq��Ϊ 0 �ģ��ж���ǰ�Ķ�����ɾ��
//		int seq = fdcReceiveBillInfo.getSeq();
//		
//		FilterInfo filter = new FilterInfo();
//		EntityViewInfo view = new EntityViewInfo();
//		view.setFilter(filter);
//		
//		filter.getFilterItems().add(new FilterItemInfo("tenRoom.room",room.getId().toString()));
//		filter.getFilterItems().add(new FilterItemInfo("tenRoom.tenancy",tenancyBillInfo.getId().toString()));
//		
//		TenancyRoomPayListEntryCollection tenancyRoomPayListEntryColl = TenancyRoomPayListEntryFactory.getRemoteInstance().getTenancyRoomPayListEntryCollection(view);
//		
//		
//		int hasSeq = hasReceiveSeqForTEN(tenancyRoomPayListEntryColl);
//		//������˿������ȥ�жϸ�����ϸ�տ�˵ڼ�����
//		if (!ReceiveBillTypeEnum.refundment.equals(fdcReceiveBillInfo
//				.getBillTypeEnum()))
//		{
//			// �����ǰ�տ���Ѿ��տ�seqС������ɾ���޸�
//			if (seq < hasSeq)
//			{
//				return new String[] {"false","�õ����к������տ�ݣ����ܽ����޸Ļ�ɾ��������"};
//			} else
//			{
//				// ��ʱû�д���һЩ��������
//				return new String [] {"true",null};
//			}
//		} else
//		{
//			return new String [] {"true",null};
//		}
//	}
//
//	/**
//	 * ��¥ϵͳ���Ƿ���ɾ���޸�����
//	 * 
//	 * @param receivingBillInfo
//	 * @return
//	 * @throws BOSException
//	 * @author laiquan_luo
//	 */
//	private static String[] isReceiveBillCanRemoveOrEditForSHE(ReceivingBillInfo receivingBillInfo) throws BOSException
//	{
//		FDCReceiveBillInfo fdcReceiveBillInfo = receivingBillInfo.getFdcReceiveBill();
//		PurchaseInfo purchase = fdcReceiveBillInfo.getPurchase();
//		//�˿�������޸�
//		if(ReceiveBillTypeEnum.refundment.equals(fdcReceiveBillInfo.getBillTypeEnum()))
//		{
//			return new String[]{"true",null};
//		}
//		//�ڴ˸���֮ǰ�Ĵ�����û�� billtypeenum �ֶεģ�֮ǰ���տ
//		if(fdcReceiveBillInfo.getBillTypeEnum() == null)
//		{
//			return new String[]{"false","�õ��ݲ��ܽ���ɾ�����޸Ĳ���"};
//		}
//		
//		//����һ��û�д����Ϲ�������Щ����
//		FilterInfo filter = new FilterInfo();
//		EntityViewInfo view = new EntityViewInfo();
//		view.setFilter(filter);
//		
//		filter.getFilterItems().add(new FilterItemInfo("head",purchase.getId().toString()));
//		view.getSelector().add("*");
//		view.getSelector().add("payListEntry.*");
//		
//		PurchasePayListEntryCollection purchasePayListEntryColl = PurchasePayListEntryFactory.getRemoteInstance().getPurchasePayListEntryCollection(view);
//		
//		
//		RoomInfo room = fdcReceiveBillInfo.getRoom();
//		MoneyDefineInfo moneyDefineInfo = fdcReceiveBillInfo.getMoneyDefine();
//		//��ǰ���տ���ݵ�seq��Ϊ 0 �ģ��ж���ǰ�Ķ�����ɾ��
//		int seq = fdcReceiveBillInfo.getSeq();
//		/*if(seq == 0)
//		{
//			return false;
//		}*/
//		int hasSeq = hasReceiveSeqForSHE(purchasePayListEntryColl);
//		//�����ǰ�տ���Ѿ��տ�seqС������ɾ���޸�
//		if(seq < hasSeq)
//		{
//			return new String[] {"false","�õ����к������տ�ݣ����ܽ����޸Ļ�ɾ��������"};
//		}
//		else
//		{
//			//��������ڿ��Ҫ�����Ƿ��Ѿ���ǩԼ����
//			if(MoneyTypeEnum.FisrtAmount.equals(moneyDefineInfo.getMoneyType()))
//			{
//				if(isHaveRoomSignContract(purchase,room))
//				{
//					return new String [] {"false","�õ����к�����ǩԼ�������ܽ����޸Ļ�ɾ��������"};
//				}
//				else
//				{
//					return new String [] {"true",null};
//				}
//			}
//			//�����Ԥ�տ���жϸ��Ϲ����Ƿ��Ѿ�����
//			else if(MoneyTypeEnum.PreMoney.equals(moneyDefineInfo.getMoneyType()))
//			{
//				if(!PurchaseStateEnum.PrePurchaseApply.equals(purchase.getPurchaseState()) && !PurchaseStateEnum.PrePurchaseCheck.equals(purchase.getPurchaseState())
//						&& !PurchaseStateEnum.PurchaseApply.equals(purchase.getPurchaseState()) && !PurchaseStateEnum.PurchaseAuditing.equals(purchase.getPurchaseState()))
//				{
//					return new String [] {"false","�õ������յĿ����ѷ����������������ܽ����޸Ļ�ɾ��������"};
//				}
//				else
//				{
//					return new String [] {"true",null};
//				}
//			}
//			else
//			{
//				return new String [] {"true",null};
//			}
//		}
//	}
//	/**
//	 * �Ƿ���ǩԼ��
//	 * @param purchase
//	 * @param room
//	 * @return
//	 * @throws BOSException
//	 * @author laiquan_luo
//	 */
//	private static boolean isHaveRoomSignContract(PurchaseInfo purchase,RoomInfo room) throws BOSException
//	{
//		FilterInfo filter = new FilterInfo();
//		EntityViewInfo view = new EntityViewInfo();
//	    view.setFilter(filter);
//	    
//	    filter.getFilterItems().add(new FilterItemInfo("room",room.getId().toString()));
//	    filter.getFilterItems().add(new FilterItemInfo("purchase",purchase.getId().toString()));
//	    filter.getFilterItems().add(new FilterItemInfo("isBlankOut",Boolean.FALSE));
//	    
//	    RoomSignContractCollection roomSignColl = RoomSignContractFactory.getRemoteInstance().getRoomSignContractCollection(view);
//	    if(roomSignColl.size() > 0)
//	    {
//	    	return true;
//	    }
//	    else
//	    {
//	    	return false;
//	    }
//	}
//	/**
//	 *  ��¥ϵͳ  ��øø�����ϸ�У��Ѹ����ˣ����˵ڼ���
//	 * @param purchasePayListEntryColl
//	 * @return
//	 * @author laiquan_luo
//	 */
//	private static int hasReceiveSeqForSHE(PurchasePayListEntryCollection purchasePayListEntryColl)
//	{
//		int tempSeq = 0;
//		for(int i = 0; i < purchasePayListEntryColl.size(); i ++)
//		{
//			PurchasePayListEntryInfo purchasePayListEntryInfo = purchasePayListEntryColl.get(i);
//			if(purchasePayListEntryInfo.getActPayDate() != null)
//			{
//				if(purchasePayListEntryInfo.getSeq() > tempSeq)
//				{
//					tempSeq = purchasePayListEntryInfo.getSeq();
//				}
//			}
//		}
//		return tempSeq;
//	}
//	
//	/**
//	 * ����ϵͳ ��øø�����ϸ�У��Ѹ����ˣ����˵ڼ���
//	 * @param purchasePayListEntryColl
//	 * @return
//	 * @author laiquan_luo
//	 */
//	private static int hasReceiveSeqForTEN(TenancyRoomPayListEntryCollection tenancyRoomPayListEntryColl)
//	{
//		int tempSeq = 0;
//		for(int i = 0; i < tenancyRoomPayListEntryColl.size(); i ++)
//		{
//			TenancyRoomPayListEntryInfo tenancyRoomPayListEntryInfo = tenancyRoomPayListEntryColl.get(i);
//			if(tenancyRoomPayListEntryInfo.getActPayDate() != null)
//			{
//				if(tenancyRoomPayListEntryInfo.getSeq() > tempSeq)
//				{
//					tempSeq = tenancyRoomPayListEntryInfo.getSeq();
//				}
//			}
//		}
//		return tempSeq;
//	}
}
