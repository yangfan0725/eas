package com.kingdee.eas.fdc.market.client;



/**
 * ��090317��ʼ����������
 */
public class XCreaterTesting {
//	
//	public static DocumentInfo doc(){
//		DocumentInfo doc = new DocumentInfo();
//		doc.setWidth(200);
//		doc.setHeight(240);
//		doc.setTopMarge(15);
//		doc.setBottomMarge(15);
//		doc.setRightMarge(20);
//		doc.setLeftMarge(0);
//		doc.setColumnCount(3);
//		doc.setHeader("������-�ʾ����");
//		doc.setFooter("www.kingdee.com");
//		
//		doc.getSubjects().add(one());
//		doc.getSubjects().add(two());
//		doc.getSubjects().add(three());
//		doc.getSubjects().add(four());
//		doc.getSubjects().add(five());
//		doc.getSubjects().add(six());
//		doc.getSubjects().add(seven());
//		doc.getSubjects().add(eight());
//		doc.getSubjects().add(nine());
//		doc.getSubjects().add(ten());
//		
//		return doc;
//	}
//	
//	
//	
//	public static DocumentSubjectInfo ten(){
//		
//
//		DocumentSubjectInfo dTen = new DocumentSubjectInfo();
//		dTen.setFont(new Font("Dialog",Font.PLAIN,16));
//		dTen.setTopic("������Ŀ");
//		dTen.setColumnCount(2);
//		dTen.setSubjectNumber(10);
//		dTen.setShowNumber(8);
//		dTen.setIsShowNumber(1);
//		dTen.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_FLOW);//ALIGN_TYPE_FLOW
//		dTen.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_SINGLE);
//		dTen.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
//		dTen.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellFDCCreater");
//		
//		DocumentItemInfo dItemAnimal = new DocumentItemInfo("���",new Font("Dialog",Font.BOLD,16));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("�ϻ�",new Font("Dialog",Font.PLAIN,16)));
//		dTen.getItems().add(dItemAnimal);
//
//		
//		XDimension three = new XDimension(199,0,1,3);
//		three.setDSubject(dTen);
//		return dTen;
//	
//	
//	}
//	
//	
//	public static DocumentSubjectInfo nine(){
//
//		DocumentSubjectInfo dNine = new DocumentSubjectInfo();
//		dNine.setFont(new Font("Dialog",Font.PLAIN,16));
//		dNine.setTopic("��ϲ��������Щ��ֲ�");
//		dNine.setColumnCount(2);
//		dNine.setSubjectNumber(9);
//		dNine.setShowNumber(8);
//		dNine.setIsShowNumber(1);
//		dNine.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_FLOW);//ALIGN_TYPE_FLOW
//		dNine.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_SINGLE);
//		dNine.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
//		dNine.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
//		
//		DocumentItemInfo dItemAnimal = new DocumentItemInfo("���",new Font("Dialog",Font.BOLD,16));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("�ϻ�",new Font("Dialog",Font.PLAIN,16)));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("ʨ��",new Font("Dialog",Font.PLAIN,16)));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("����",new Font("Dialog",Font.PLAIN,16)));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("���ϻ�",new Font("Dialog",Font.PLAIN,16)));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("��ʨ��",new Font("Dialog",Font.PLAIN,16)));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("������",new Font("Dialog",Font.PLAIN,16)));
//		dNine.getItems().add(dItemAnimal);
//
//		DocumentItemInfo dItemPlenty = new DocumentItemInfo("ֲ�",new Font("Dialog",Font.BOLD,50));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("����",new Font("Dialog",Font.PLAIN,40)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("��",new Font("Dialog",Font.PLAIN,40)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("����",new Font("Dialog",Font.PLAIN,40)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("������",new Font("Dialog",Font.PLAIN,16)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("���",new Font("Dialog",Font.PLAIN,20)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("����",new Font("Dialog",Font.PLAIN,16)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("������",new Font("Dialog",Font.PLAIN,12)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("����",new Font("Dialog",Font.PLAIN,12)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("������",new Font("Dialog",Font.PLAIN,12)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("������",new Font("Dialog",Font.PLAIN,40)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("����",new Font("Dialog",Font.PLAIN,40)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("������",new Font("Dialog",Font.PLAIN,40)));
//		dNine.getItems().add(dItemPlenty);
//		
//		
//		XDimension three = new XDimension(199,0,1,3);
//		three.setDSubject(dNine);
//		return dNine;
//	
//	}
//
//	public static DocumentSubjectInfo eight(){
//		DocumentSubjectInfo dEight = new DocumentSubjectInfo();
//		dEight.setFont(new Font("Dialog",Font.PLAIN,16));
//		dEight.setTopic("��ϲ��������Щ��ֲ�");
//		dEight.setColumnCount(1);
//		dEight.setXFontSize(14);
//		dEight.setXFontName("Dialog");
//		dEight.setSubjectNumber(8);
//		dEight.setShowNumber(7);
//		dEight.setIsShowNumber(1);
//		dEight.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_LINE);//ALIGN_TYPE_FLOW
//		dEight.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_MULTIPLE);
//		dEight.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
//		dEight.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
//		
//		DocumentItemInfo dItemAnimal = new DocumentItemInfo("���",new Font("Dialog",Font.BOLD,12));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("�ϻ�",new Font("Dialog",Font.PLAIN,12)));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("ʨ��",new Font("Dialog",Font.PLAIN,12)));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("����",new Font("Dialog",Font.PLAIN,12)));
//		dEight.getItems().add(dItemAnimal);
//
//		DocumentItemInfo dItemPlenty = new DocumentItemInfo("ֲ�",new Font("Dialog",Font.BOLD,12));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("����",new Font("Dialog",Font.PLAIN,12)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("��",new Font("Dialog",Font.PLAIN,12)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("����",new Font("Dialog",Font.PLAIN,12)));
//		dEight.getItems().add(dItemPlenty);
//		
//		
//		XDimension three = new XDimension(199,0,1,3);
//		three.setDSubject(dEight);
//		return dEight;
//	}
//
//	public static DocumentSubjectInfo seven(){
//		DocumentSubjectInfo dSeven = new DocumentSubjectInfo();
//		dSeven.setFont(new Font("Dialog",Font.PLAIN,16));
//		dSeven.setTopic("����ϲ���Ķ�ֲ���ǣ�");
//		dSeven.setColumnCount(1);
//		dSeven.setSubjectNumber(7);
//		dSeven.setShowNumber(6);
//		dSeven.setIsShowNumber(1);
//		dSeven.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_LINE);//ALIGN_TYPE_FLOW
//		dSeven.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_SINGLE);
//		dSeven.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
//		dSeven.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
//		DocumentItemInfo dItemAnimal = new DocumentItemInfo("���",new Font("Dialog",Font.BOLD,12));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("�ϻ�",new Font("Dialog",Font.PLAIN,12)));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("ʨ��",new Font("Dialog",Font.PLAIN,12)));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("����",new Font("Dialog",Font.PLAIN,12)));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("����",new Font("Dialog",Font.PLAIN,12)));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("����",new Font("Dialog",Font.PLAIN,12)));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("����",new Font("Dialog",Font.PLAIN,12)));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("����¹",new Font("Dialog",Font.PLAIN,12)));
//		dSeven.getItems().add(dItemAnimal);
//		
//
//		DocumentItemInfo dItemPlenty = new DocumentItemInfo("ֲ�",new Font("Dialog",Font.BOLD,12));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("����",new Font("Dialog",Font.PLAIN,12)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("��",new Font("Dialog",Font.PLAIN,12)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("����",new Font("Dialog",Font.PLAIN,12)));
//		dSeven.getItems().add(dItemPlenty);
//		
//		
//		
//		XDimension three = new XDimension(199,0,1,3);
//		three.setDSubject(dSeven);
//		return dSeven;
//	}
//
//	public static DocumentSubjectInfo six(){
//		DocumentSubjectInfo dSix = new DocumentSubjectInfo();
//		dSix.setFont(new Font("Dialog",Font.PLAIN,16));
//		dSix.setTopic("�����Ǹ�������ϲ�������ǣ�");
//		dSix.setColumnCount(1);
//		dSix.setSubjectNumber(6);
//		dSix.setShowNumber(5);
//		dSix.setIsShowNumber(1);
//		dSix.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_LINE);//ALIGN_TYPE_FLOW
//		dSix.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_SINGLE);
//		dSix.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
//		dSix.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
//		DocumentItemInfo dItem = new DocumentItemInfo();//"�ҵı���",new Font("Dialog",Font.PLAIN,14)
//		dSix.getItems().add(dItem);
//		dItem.getOptions().add(new DocumentOptionInfo("³��",new Font("Dialog",Font.PLAIN,12)));
//		dItem.getOptions().add(new DocumentOptionInfo("���˺�ķ",new Font("Dialog",Font.PLAIN,12)));
//		dItem.getOptions().add(new DocumentOptionInfo("C��",new Font("Dialog",Font.PLAIN,12)));
//		dItem.getOptions().add(new DocumentOptionInfo("����",new Font("Dialog",Font.PLAIN,12)));
//		XDimension three = new XDimension(199,0,1,3);
//		three.setDSubject(dSix);
//		return dSix;
//	}
//
//	public static DocumentSubjectInfo five(){
//		DocumentSubjectInfo dFive = new DocumentSubjectInfo();
//		dFive.setFont(new Font("Dialog",Font.PLAIN,16));
//		dFive.setTopic("���µ�Ӱ����ϲ���Ǹ���");
//		dFive.setColumnCount(1);
//		dFive.setSubjectNumber(5);
//		dFive.setShowNumber(4);
//		dFive.setIsShowNumber(1);
//		dFive.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_LINE);//ALIGN_TYPE_FLOW
//		dFive.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_SINGLE);
//		dFive.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
//		dFive.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
//		DocumentItemInfo dItem = new DocumentItemInfo();//"�ҵı���",new Font("Dialog",Font.PLAIN,14)
//		dFive.getItems().add(dItem);
//		dItem.getOptions().add(new DocumentOptionInfo("����֮��",new Font("Dialog",Font.PLAIN,12)));
//		dItem.getOptions().add(new DocumentOptionInfo("��������",new Font("Dialog",Font.PLAIN,12)));
//		//dItem.getOptions().add(new DOption("���",new Font("Dialog",Font.PLAIN,12)));
//		dItem.getOptions().add(new DocumentOptionInfo("��ɱϣ����",new Font("Dialog",Font.PLAIN,12)));
//		XDimension three = new XDimension(199,0,1,3);
//		three.setDSubject(dFive);
//		return dFive;
//	}
//
//	public static DocumentSubjectInfo four(){
//		DocumentSubjectInfo dFour = new DocumentSubjectInfo();
//		dFour.setFont(new Font("Dialog",Font.PLAIN,16));
//		dFour.setTopic("���µ�Ӱ��ϲ������Щ��");
//		dFour.setColumnCount(1);
//		dFour.setSubjectNumber(4);
//		dFour.setShowNumber(3);
//		dFour.setIsShowNumber(1);
//		dFour.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_LINE);//ALIGN_TYPE_FLOW
//		dFour.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_MULTIPLE);
//		dFour.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
//		dFour.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
//		DocumentItemInfo dItem = new DocumentItemInfo();//"�ҵı���",new Font("Dialog",Font.PLAIN,14)
//		dFour.getItems().add(dItem);
//		dItem.getOptions().add(new DocumentOptionInfo("����֮��",new Font("Dialog",Font.PLAIN,12)));
//		dItem.getOptions().add(new DocumentOptionInfo("��������",new Font("Dialog",Font.PLAIN,12)));
//		dItem.getOptions().add(new DocumentOptionInfo("���",new Font("Dialog",Font.PLAIN,12)));
//		dItem.getOptions().add(new DocumentOptionInfo("��ɱϣ����",new Font("Dialog",Font.PLAIN,12)));
//		XDimension three = new XDimension(199,0,1,3);
//		three.setDSubject(dFour);
//		return dFour;
//	}
//
//	public static DocumentSubjectInfo three(){
//		DocumentSubjectInfo dThree = new DocumentSubjectInfo();
//		dThree.setFont(new Font("Dialog",Font.PLAIN,16));
//		dThree.setTopic("�����˶���ϲ���Ǹ���");
//		dThree.setXFontSize(16);
//		dThree.setColumnCount(1);
//		dThree.setSubjectNumber(3);
//		dThree.setShowNumber(2);
//		dThree.setIsShowNumber(1);
//		dThree.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_LINE);//ALIGN_TYPE_FLOW
//		dThree.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_MULTIPLE);
//		dThree.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
//		dThree.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
//		DocumentItemInfo dItem = new DocumentItemInfo();//"�ҵı���",new Font("Dialog",Font.PLAIN,14)
//		dItem.setTopic("����");
//		dItem.setXFontSize(13);
//		dItem.setItemNumber(0);
//		dThree.getItems().add(dItem);
//		DocumentOptionInfo o1 = null;
//		//
//		o1 = new DocumentOptionInfo();
//		o1.setTopic("����");
//		o1.setXFontName("Dialog");
//		o1.setXFontSize(12);
//		o1.setDesc01("��ע1");
//		o1.setXLength(0);
//		o1.setXHeight(0);
//		o1.setOptionNumber(0);
//		dItem.getOptions().add(o1);
//		//
//		o1 = new DocumentOptionInfo();
//		o1.setTopic("���");
//		o1.setXFontName("Dialog");
//		o1.setXFontSize(12);
//		o1.setDesc01("��ע2");
//		o1.setXLength(0);
//		o1.setXHeight(0);
//		o1.setOptionNumber(0);
//		dItem.getOptions().add(o1);
//		//
//		o1 = new DocumentOptionInfo();
//		o1.setTopic("��Ӿ");
//		o1.setXFontName("Dialog");
//		o1.setXFontSize(12);
//		o1.setDesc01("��ע3");
//		o1.setXLength(0);
//		o1.setXHeight(0);
//		o1.setOptionNumber(0);
//		dItem.getOptions().add(o1);
//		//
//		XDimension three = new XDimension(199,0,1,3);
//		three.setDSubject(dThree);
//		return dThree;
//	}
//
//	public static DocumentSubjectInfo two(){
//		DocumentSubjectInfo dTwo = new DocumentSubjectInfo();
//		dTwo.setColumnCount(1);
//		dTwo.setSubjectNumber(2);
//		dTwo.setShowNumber(1);
//		dTwo.setIsShowNumber(1);
//		dTwo.setFont(new Font("Dialog",Font.PLAIN,16));
//		dTwo.setTopic("���������ǣ�");
//		dTwo.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_LINE);
//		dTwo.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_FILL);
//		dTwo.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
//		dTwo.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
//		return dTwo;
//	}
//
//	public static DocumentSubjectInfo one(){
//		DocumentSubjectInfo dOne = new DocumentSubjectInfo();
//		dOne.setColumnCount(2);
//		dOne.setSubjectNumber(1);
//		dOne.setIsShowNumber(0);
//		dOne.setFont(new Font("Dialog",Font.PLAIN,40));
//		dOne.setTopic("���ڻ�Ա�İ��õĵ���");
//		dOne.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_CENTER);
//		dOne.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_LINE);
//		dOne.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION);
//		dOne.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
//		return dOne;
//	}
}
