package com.kingdee.eas.fdc.schedule.util.subway;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.eas.fdc.schedule.util.subway.TestRenderer;

/**
 * ����ͼ�ؼ� <br>
 * ���ؼ�������ʾһ���ؼ��Ľڵ㣬�Լ��ڵ���Ե�״̬��<br>
 * �����ͼվ·��ͼ����־��Щ�Ѿ��������ڵ�����վ����Щ��������<br>
 * 
 */
public class KDSubwayMap extends JComponent {
	private static final long serialVersionUID = 8667100535673641865L;
	
	private Map<Integer,Map> map ;
//	��
	private static Color RED = new Color(255, 0, 0);
//	��
	private static Color GREEN = new Color(146, 208, 80);
//	��
	private static Color ORANGE = new Color(255, 255, 0);
//	��
	private static Color PINK = new Color(230, 185, 184);
//	��
	private static Color BLUE= new Color(79, 129, 189);
//	��ɫ
	private static Color TAN= new Color(196, 189, 151);
	//��������������õ�
	private int stage;
	// ��
	private static int width = 1000;
	// ��
	private static int height = 100;
	// ���ұ߾�
	private int clip = 50;
	// �ϱ߾�
	private int top = 53;
//	private int top = 55;

	// ��ѿ��
//	int preferredWidth = 800;
	int preferredWidth = 300 + ((itemNums - 1) * lineLength);

	// �������
	private JPanel pnlScroll;
	// �����
	private KDSubwayLinePanel pnlLine;
	// �ڵ����
	private JPanel pnlItem;

	// ˵�����
	private static JPanel pnlDesc;

	// �ڵ����Ƽ���
	private String[] names;
	// �����뼯��
	private String[] longNumbers;
	// ��Ŀid
	private String project;
	int[] taskComleteRates ;	
	// �����ڵ㳤���뼯��
	private String[] unionNames = null;
	// ���˰������߸�������
	private int[] CheckLineCounts = null;
	// ���˰������߸�������
	private int[] ExecLineCounts = null;
	// ��ǰ�ڵ㳤���뼯��
	private String[] tmpNames = null;
	private List<String> itemNamesList = new ArrayList<String>();
	// �ڵ����Ƽ���
//	private String[] dates;
	// �ڵ����Ƽ��� ��������Ҫ��ʾ��ɫ��Valueһ�������object��
	private Object[] dates;
	// �ڵ�״̬����
	private KDSubwayItemState[] allStates;
	// �ڵ�״̬
	private int[] itemStates;
	// �ڵ����Լ���
	private  Object[] values;
	// �ڵ㼯��
	private KDSubwayItemButton[] items;
	
	// �����ڵ����
	private static int itemNums = 0;

	// �ڵ��С
	private static int itemSize = 10;

	// �������߳���(���ڵ��е��ľ���)60
	private static int lineLength = 100;
	// �������߿��
	private static int lineHeight = 4;
	// ����������ɫ
//	private static Color lineColor = Color.BLACK;
//	private static Color lineColor = new Color(56, 93, 138);
	private static Color lineColor = new Color(70, 130, 180);
//	private static Color lineColor = new Color(0, 255, 255);
	private static Color completedColor = new Color(124, 252, 0);

//	private static Color lineColor = new Color(74, 126, 187);


	  //Ĭ����������
//	private static Color DARK_BULE =new Color(56, 93, 138);
	// �ڵ���Ⱦ��
//	private KDSubwayItemRenderer renderer;
	private TestRenderer renderer;
	// �Ƿ���ʾ�ڵ�����
	private boolean isShowName = true;
	// �Ƿ���ʾ�ڵ�����
	private boolean isShowDate = true;
	//�Ƿ�����
	private boolean isNeedEvaluation = false;
//	��ɫ
//	private Color  GREY = new Color(143, 153, 102);
	//������Ļ�ֱ������ù�������С
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	//��ȥ�������
	private int scrollWidth = dim.width-280;

	/**
	 * �����ϵĹ��췽��<br>
	 * 
	 * @param names
	 *            ���Ƽ���
	 * @param allStates
	 *            ���п��ܵ�״̬����
	 * @param itemStates
	 *            ���ڵ��Ӧ��״̬
	 * @param values
	 *            ���ڵ���ϸ����ֵ
	 */
	public KDSubwayMap(KDSubwayItemState[] allStates) {
//		this.allStates = allStates;
		addDesc();
	}
	/**
	 * �����µĹ��췽��<br>
	 * 
	 */
	public KDSubwayMap(Map<Integer, Map> map) {
		this.map = map;
		//��ʼ�������ؼ�
		initCompt();
		
	}

	/**
	 * ������Ⱦ<br>
	 * 
	 */
	public void setRenderer (TestRenderer renderer) {
		if(renderer!=null){
			
			this.renderer = renderer;
			for (int i = 0; i < items.length; i++) {
				if(items[i]!=null){					
					items[i].setRenderer(renderer);
				}
				
			}
		}
	}
	/**
	 * ��ȡ��Ⱦ
	 */
	public KDSubwayItemRenderer getRenderer() {
		return renderer;
	}
	/**
	 * ��ʼ�������ؼ�
	 * �Ȼ����ߣ��ٻ�������ߣ���󻭵�
	 * �ߺ͵㶼��������ģ������ͬʱ�������������������������
	 */
	protected void initCompt() {
		setLayout(null);
		pnlScroll = new JPanel();
		pnlScroll.setLayout(null);
		//���������ʵĻ�������ĸ��Ӷ��ּӴ���
		//��ʱ�����������飬��Ϊ�鲢��
		String[] tmpNamesA = null;
		String[] tmpNamesB = null;
		//Ŀǰֻ���������ݿ��˰��ִ�а棬������2
		for (int i = 0; i < 2; i++) {
			if(map.containsKey(i)){
				if(map.get(i) != null){
					Map innerMap = map.get(i);
					//���˰������
					tmpNamesA = (String[]) innerMap.get("names");

					//��ǰNames���ϡ�ִ�а���߶���汾
					//Ŀǰֻ���������ݿ��˰��ִ�а棬������2
					if(i+1 < 2 && map.get(i+1) != null ){
						Map innerMapB = map.get(i+1);
						tmpNamesB = (String[]) innerMapB.get("names");
					}
					//�ϲ��ϴ�Names
					itemNamesList  = sort(tmpNamesA,this.unionNames);
					tmpNamesA = (String[])itemNamesList.toArray(new String[itemNamesList.size()]);			
					itemNamesList = sort(tmpNamesA,tmpNamesB);					
					this.unionNames = (String[])itemNamesList.toArray(new String[itemNamesList.size()]);
					//������Names
					itemNums = this.unionNames.length;
				}
			}
		}
		//Ŀǰֻ���������ݿ��˰��ִ�а棬������
		for (int i = 0; i < 2; i++) {		
			if(map.containsKey(i)){
				if(map.get(i) != null){				
					Map innerMap = map.get(i);		
					this.names = (String[]) innerMap.get("names");
					tmpNames = (String[]) innerMap.get("names");
					this.longNumbers = (String[]) innerMap.get("longNumbers");
					this.project = (String) innerMap.get("project");
//					itemNums = names.length;
					this.dates = (Object[]) innerMap.get("dates");
					//����״̬
					this.allStates = (KDSubwayItemState[]) innerMap.get("allStates");
					//���״̬
					this.itemStates = (int[]) innerMap.get("itemStates");
					//�����
					this.taskComleteRates = (int[]) innerMap.get("taskComleteRates");
					//�Ƿ���Ҫ����
					if(innerMap.get("isNeedEvaluation")!=null){
						this.isNeedEvaluation =  innerMap.get("isNeedEvaluation").equals(true);						
					}
					//�Ƿ���ʾ����Ĭ�϶���true
					this.isShowName = true;
					//�Ƿ���ʾ����
					this.isShowDate = true;
					if (innerMap.get("values") != null) {
						this.values = (Object[]) innerMap.get("values");
					} else {
						this.values = names;
					}
//					addLine(i);
//					if(i>0){						
//						addCompletedRateLine(i);
//					}
					
					//����鲢�������Ƴ�����ȣ��Ͳ���Ҫ���Ƕ�������
					if(this.names.length==itemNums){			
						//�Ȼ�����
						addLine(i);
						//�ٻ��������
						if(i>0){						
							addCompletedRateLine(i);
						}
					}else{
						//������
						//�˴��ֱ�ԱȺϲ������飬�����µĻ���������
						initLineCounts(i);
						//�Ȼ�����
						addLine4diff(i);
						//�ٻ��������
						if(i>0){						
							addCompletedRateLine4diff(i);
						}
					}
					//��󻭵�
					addItem(i);
				}
			}
		}
		//���չʾ����
		preferredWidth = 300 + ((itemNums - 1) * lineLength);
		pnlScroll.setPreferredSize(new Dimension(preferredWidth, 0));
		//��ӹ�����
		KDScrollPane sp = new KDScrollPane(pnlScroll);
		//���������������λ�ã�����ģ���Ҫ������
		sp.setBounds(0, 0, scrollWidth, 208);
		add(sp);
	}
	
	/**
	 * ����������������кϲ�
	 * ���ڷ��ص������飬���ȿ��ܲ�Ϊa,b����֮�ͣ���Ϊ�������ظ���������Ҫȥ����
	 * ��a��b�������ֵ���бȽϣ�����С��ֵ�ӵ�c�������������±�+1��
	 * �����ȣ���������һ���ӵ�c���������±��+1��
	 * ����±곬�������鳤�ȣ����˳�ѭ����
	 * ��û�г��������±����������ȫ���ӵ�����c�У�
	 * ��������ĩ�����ӣ��޳��ظ�Ԫ�ء�
	 * 
	 * @param a
	 * @param b
	 * @return c �ϲ������������
	 */
	private static List<String> sort(String[] a, String[] b) {
		// ���ڷ��ص������飬���ȿ��ܲ�Ϊa,b����֮�ͣ���Ϊ�������ظ���������Ҫȥ��
		List<String> c = new ArrayList<String>();
		// a�����±�
		int aIndex = 0;
		// b�����±�
		int bIndex = 0;
		// ��a��b�������ֵ���бȽϣ�����С��ֵ�ӵ�c�������������±�+1��
		// �����ȣ���������һ���ӵ�c���������±��+1
		// ����±곬�������鳤�ȣ����˳�ѭ��
		while (true) {
			if(a == null || b == null){
				break;
			}
			if (aIndex > a.length - 1 || bIndex > b.length - 1) {
				break;
			}
			if(a.length>=b.length){				
				if(a[aIndex] != null && b[bIndex] != null){				
					if (a[aIndex].compareTo(b[bIndex])<0) {
						c.add(a[aIndex]);
						aIndex++;
					} else if (a[aIndex].compareTo(b[bIndex])>0) {
						c.add(b[bIndex]);
						bIndex++;
					} else {
						c.add(a[aIndex]);
						aIndex++;
						bIndex++;
					}
				}
			}else{
				if(a[aIndex] != null && b[bIndex] != null){				
					if (b[bIndex].compareTo(a[aIndex])<0) {
						c.add(b[bIndex]);
						bIndex++;
					} else if (b[bIndex].compareTo(a[aIndex])>0) {
						c.add(a[aIndex]);
						aIndex++;
					} else {
						c.add(a[aIndex]);
						aIndex++;
						bIndex++;
					}
				}
			}
		}
		// ��û�г��������±����������ȫ���ӵ�����c��
		// ���a���黹������û�д���
		if (aIndex <= a.length - 1) {
			for (int i = aIndex; i <= a.length - 1; i++) {
				//��������ĩ�����ӣ��޳��ظ�Ԫ��
				if(c.contains(a[i])){
					c.remove(a[i]);
				}
				c.add(a[i]);
			}
			// ���b�����л�������û�д���
		} else if (bIndex <= b.length - 1) {
			for (int i = bIndex; i <= b.length - 1; i++) {
				//��������ĩ�����ӣ��޳��ظ�Ԫ��
				if(c.contains(b[i])){
					c.remove(b[i]);
				}
				c.add(b[i]);
			}
		}
		return c;
	}
	
	/**
	 * �˴��ֱ�ԱȺϲ������飬�����µĻ���������
	 * ���￼�Ƕ������⣬ȷ����͵�֮��Ŀ������ߵĸ���
	 */
	protected void initLineCounts(int i) {
		//��ʼ����
		if(i>0){		
			//ִ�а�
			this.ExecLineCounts = new int[this.names.length];
			int sameIndex=0;
			for(int j = 0 ; j < this.names.length;j++){				
				for(int unionIndex = sameIndex,count=1; unionIndex < this.unionNames.length ; unionIndex++,count++){				
					if(this.names[j].equals(this.unionNames[unionIndex])){
						sameIndex=unionIndex+1;						
						this.ExecLineCounts[j]=count;
						break;
					}
				}
			}
		}else{		
			//���˰�
			this.CheckLineCounts = new int[this.names.length];
			int sameIndex=0;
			for(int j = 0 ; j < this.names.length;j++){				
				for(int unionIndex = sameIndex,count=1; unionIndex < this.unionNames.length ; unionIndex++,count++){				
					if(this.names[j].equals(this.unionNames[unionIndex])){
						sameIndex=unionIndex+1;
						this.CheckLineCounts[j]=count;
						break;
					}
				}
			}
		}
	}
	
	/**
	 * �����������壬�����Ƕ��룬ֱ�ӻ�����ֱ��
	 */
	protected void addLine() {
		int count = names.length;
		for(int i = 1; i < count; i++){
			pnlLine = new KDSubwayLinePanel(2, lineLength, lineHeight, lineColor, longNumbers[i-1], longNumbers[i], project, isNeedEvaluation,true);
			pnlLine.setBounds(clip + (i-1) * lineLength , top + 3 , pnlLine.getWidth(), pnlLine.getHeight());
			pnlScroll.add(pnlLine,-1);
		}
	}
	
	/**
	 * �������������汾�����벻��Ҫ���ǵ�����������
	 */
	protected void addLine(int j) {
		int count = names.length;
		int tmpCount = 1;
		for (int UnionIndex = 1; UnionIndex < itemNums; UnionIndex++) {
			//��ƥ�仭��Ȧ �Ȳ���
			if(UnionIndex >= tmpCount){
				
			}
			for(int tmpIndex = 1; tmpIndex < count; tmpIndex++){
				if(tmpNames != null && tmpNames[tmpIndex] != null && this.unionNames[UnionIndex] != null){	
					//��ͬ��ʾ������Ϣ����ͬ��ʾ
					if(this.unionNames[UnionIndex].equals(tmpNames[tmpIndex])){
						//���л���
						++tmpCount;
						if(j>0){				
							pnlLine = new KDSubwayLinePanel(2, lineLength, lineHeight, lineColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project, isNeedEvaluation,true);
						}else{
							pnlLine = new KDSubwayLinePanel(2, lineLength, lineHeight, lineColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project, isNeedEvaluation,false);
							
						}
						pnlLine.setBounds(clip  + 100 + (UnionIndex - 1) * lineLength , top + 3 + j * 95 , pnlLine.getWidth(), pnlLine.getHeight());
						pnlScroll.add(pnlLine,-1);
						break;
					}
				}
			}
		}
	}
	
	/**
	 * �������������汾,������������
	 */
	protected void addLine4diff(int j) {
		int count = names.length;
		int tmpCount = 1;
		for (int UnionIndex = 1; UnionIndex < itemNums; UnionIndex++) {
			//��ƥ�仭��Ȧ �Ȳ���
			if(UnionIndex >= tmpCount){
				
			}
			for(int tmpIndex = 1; tmpIndex < count; tmpIndex++){
				if(tmpNames != null && tmpNames[tmpIndex] != null && this.unionNames[UnionIndex] != null){	
					//��ͬ��ʾ������Ϣ����ͬ��ʾ
					if(this.unionNames[UnionIndex].equals(tmpNames[tmpIndex])){
						//���л���
						++tmpCount;
						if(j>0){				
							//ִ�а�
							if( ExecLineCounts[tmpIndex] > 1){
								pnlLine = new KDSubwayLinePanel(2, lineLength * ExecLineCounts[tmpIndex], lineHeight, lineColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project, isNeedEvaluation,true);
							}else{	
								if(ExecLineCounts[tmpIndex-1]>1){
									pnlLine = new KDSubwayLinePanel(2, lineLength * ExecLineCounts[tmpIndex], lineHeight, lineColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project, isNeedEvaluation,true);
								}else{									
									pnlLine = new KDSubwayLinePanel(2, lineLength * ExecLineCounts[tmpIndex-1], lineHeight, lineColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project, isNeedEvaluation,true);
								}
							}
							pnlLine.setBounds(clip  + 100 + (UnionIndex - ExecLineCounts[tmpIndex]) * lineLength  , top + 3 + j * 95 , pnlLine.getWidth(), pnlLine.getHeight());

						}else{
							//���˰�
							if(CheckLineCounts[tmpIndex]>1){
								pnlLine = new KDSubwayLinePanel(2, lineLength * CheckLineCounts[tmpIndex] , lineHeight, lineColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project, isNeedEvaluation,false);

							}else{
								if(CheckLineCounts[tmpIndex-1]>1){
									pnlLine = new KDSubwayLinePanel(2, lineLength * CheckLineCounts[tmpIndex] , lineHeight, lineColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project, isNeedEvaluation,false);
								}else{									
									pnlLine = new KDSubwayLinePanel(2, lineLength * CheckLineCounts[tmpIndex-1] , lineHeight, lineColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project, isNeedEvaluation,false);
								}
							}
							pnlLine.setBounds(clip  + 100 + (UnionIndex - CheckLineCounts[tmpIndex]) * lineLength , top + 3 + j * 95 , pnlLine.getWidth(), pnlLine.getHeight());

						}
						pnlScroll.add(pnlLine,-1);
						break;
					}
				}
			}
		}
	}
	
	//����˼·�������ӵ�һ�����������
	/**
	 * ��������������
	 */
	protected void addCompletedRateLine() {
		int count = names.length;		
		for(int i = 1; i < count; i++){
			pnlLine = new KDSubwayLinePanel(2, lineLength/100 * (taskComleteRates[i]) , 5, completedColor, longNumbers[i-1], longNumbers[i], project,isNeedEvaluation,true);
			pnlLine.setBounds(clip + (i-1) * lineLength , top + 3 , pnlLine.getWidth(), pnlLine.getHeight());
			pnlScroll.add(pnlLine,0);
		}
	}
	
	/**
	 * �������������ʣ����벻��Ҫ���ǵ����������Ŀ
	 */
	protected void addCompletedRateLine(int j) {
		int count = names.length;
		int tmpCount = 1;
		for (int UnionIndex = 1; UnionIndex < itemNums; UnionIndex++) {
			//��ƥ�仭��Ȧ �Ȳ�����
			if(UnionIndex >= tmpCount){
				
			}
			for(int tmpIndex = 1; tmpIndex < count; tmpIndex++){
				if(tmpNames != null && tmpNames[tmpIndex] != null && this.unionNames[UnionIndex] != null){	
					//��ͬ��ʾ������Ϣ����ͬ��ʾ
					if(this.unionNames[UnionIndex].equals(tmpNames[tmpIndex])){
						//���л���ť
						++tmpCount;
						if(j>0){				
							pnlLine = new KDSubwayLinePanel(2, lineLength/100 * (taskComleteRates[tmpIndex]) , 5, completedColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project,isNeedEvaluation,true);
						}else{
							pnlLine = new KDSubwayLinePanel(2, lineLength/100 * (taskComleteRates[tmpIndex]) , 5, completedColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project,isNeedEvaluation,false);							
						}
						pnlLine.setBounds(clip + 100 + (UnionIndex-1) * lineLength , top + 3 + j * 95 , pnlLine.getWidth(), pnlLine.getHeight());
						pnlScroll.add(pnlLine,0);
						break;
					}
				}
			}
		}
	}

	/**
	 * ��������������,������������
	 */
	protected void addCompletedRateLine4diff(int j) {
		int count = names.length;
		int tmpCount = 1;
		for (int UnionIndex = 1; UnionIndex < itemNums; UnionIndex++) {
			//��ƥ�仭��Ȧ �Ȳ�����
			if(UnionIndex >= tmpCount){
				
			}
			for(int tmpIndex = 1; tmpIndex < count; tmpIndex++){
				if(tmpNames != null && tmpNames[tmpIndex] != null && this.unionNames[UnionIndex] != null){	
					//��ͬ��ʾ������Ϣ����ͬ��ʾ
					if(this.unionNames[UnionIndex].equals(tmpNames[tmpIndex])){
						//���л���ť
						++tmpCount;
						//����Ƚ�������⣬������˵��������������Щ���Կ���~
						if(j>0){			
							//ִ�а�
							if(ExecLineCounts[tmpIndex]>1){
								pnlLine = new KDSubwayLinePanel(2, lineLength/100  * ExecLineCounts[tmpIndex] * (taskComleteRates[tmpIndex]) , 5, completedColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project,isNeedEvaluation,true);

							}else{
								if(ExecLineCounts[tmpIndex-1]>1){
									pnlLine = new KDSubwayLinePanel(2, lineLength/100  * ExecLineCounts[tmpIndex] * (taskComleteRates[tmpIndex]) , 5, completedColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project,isNeedEvaluation,true);
								}else{									
									pnlLine = new KDSubwayLinePanel(2, lineLength/100  * ExecLineCounts[tmpIndex-1] * (taskComleteRates[tmpIndex]) , 5, completedColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project,isNeedEvaluation,true);
								}

							}
							pnlLine.setBounds(clip + 100 + (UnionIndex-ExecLineCounts[tmpIndex]) * lineLength  , top + 3 + j * 95 , pnlLine.getWidth(), pnlLine.getHeight());


						}else{
							//���˰�
							if(CheckLineCounts[tmpIndex]>1){
								pnlLine = new KDSubwayLinePanel(2, lineLength/100  * CheckLineCounts[tmpIndex] * (taskComleteRates[tmpIndex]) , 5, completedColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project,isNeedEvaluation,false);	

							}else{	
								if(CheckLineCounts[tmpIndex-1]>1){
									pnlLine = new KDSubwayLinePanel(2, lineLength/100  * CheckLineCounts[tmpIndex] * (taskComleteRates[tmpIndex]) , 5, completedColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project,isNeedEvaluation,false);	
								}else{									
									pnlLine = new KDSubwayLinePanel(2, lineLength/100  * CheckLineCounts[tmpIndex-1] * (taskComleteRates[tmpIndex]) , 5, completedColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project,isNeedEvaluation,false);	
								}
							}
							pnlLine.setBounds(clip + 100 + (UnionIndex-CheckLineCounts[tmpIndex]) * lineLength , top + 3 + j * 95 , pnlLine.getWidth(), pnlLine.getHeight());

						}
						pnlScroll.add(pnlLine,0);
						break;
					}
				}
			}
		}
	}
	
	/**
	 * ��ӽڵ����
	 * <p>
	 * ����Ϊ͸�����������ϲ㣬���ɸ���������
	 */	
	protected void addItem() {
		pnlItem = new JPanel();
		pnlItem.setLayout(null);
		pnlItem.setOpaque(false);
//		pnlItem.setBounds(0, 2 + stage * 95, 300 + ((itemNums - 1) * lineLength), 100);
		pnlItem.setBounds(0, 2 , 300 + ((itemNums - 1) * lineLength), 100);

		int count = names.length;
		items = new KDSubwayItemButton[itemNums];
		for(int i = 0; i < count; i++){
			Color circleColor = allStates[itemStates[i]].getStatusColor();
			//������ɫ����ť
			RButton item = new RButton(circleColor);
			item.setName(names[i]);
			item.setOpaque(true);
			item.setPreferredSize(new Dimension(itemSize * 2, itemSize * 2));
			item.setBackground(circleColor);
			//
			item.setDetail(values[i]);
			item.setBounds(clip + i * lineLength - itemSize , top - itemSize + lineHeight / 2, itemSize * 2, itemSize * 2);
			items[i] = item;
			pnlItem.add(item,0);
//			if (circleColor.equals(TAN)){
//				RButton item = new RButton(TAN);
//				item.setName(names[i]);
//				item.setOpaque(true);
//				item.setPreferredSize(new Dimension(itemSize * 2, itemSize * 2));
//				item.setBackground(circleColor);
//				//
//				item.setDetail(values[i]);
//				item.setBounds(clip + i * lineLength - itemSize + 100, top - itemSize + lineHeight / 2, itemSize * 2, itemSize * 2);
//				items[i] = item;
//				pnlItem.add(item,0);
//				//���δ�㱨
//			}else if(circleColor.equals(GREEN)){
//				RButton item = new RButton(GREEN);
//				item.setName(names[i]);
//				item.setOpaque(true);
//				item.setPreferredSize(new Dimension(itemSize * 2, itemSize * 2));
//				item.setDetail(values[i]);
//				item.setBounds(clip + i * lineLength - itemSize + 100, top - itemSize + lineHeight / 2, itemSize * 2, itemSize * 2);
//				item.setRenderer(null);
//				items[i] = item;
//				pnlItem.add(item,0);
//			}else{					
//				KDSubwayItemButton item = new KDSubwayItemButton(true);
//				item.setName(names[i]);
//				item.setOpaque(true);
//				item.setPreferredSize(new Dimension(itemSize * 6, itemSize * 2));
//				item.setBackground(circleColor);
//				item.setDetail(values[i]);
//				item.setBounds(clip + i * lineLength - itemSize * 3 + 100, top - itemSize + lineHeight / 2, itemSize * 6, itemSize * 2);
//				items[i] = item;
//				pnlItem.add(item,0);
//			}
			Map datesValue = (Map) dates[i];
			if (isShowDate) {
				if (datesValue != null){
					JLabel date = new JLabel((String)datesValue.get("date"), SwingConstants.CENTER);
					date.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
					date.setText((String)datesValue.get("date"));
					date.setBounds(clip + i * lineLength - lineLength / 2 + 5, top + lineHeight / 2 + itemSize + 5, lineLength - 10, 14);
					pnlItem.add(date,0);
				}
			}
			if (isShowName) {
				JTextArea name = new JTextArea();
				name.setAutoscrolls(true);
				name.setLineWrap(true);  
				name.setEditable(false);
				name.setText(names[i]);
				name.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2));
				name.setToolTipText(names[i]);
				name.setText(names[i]);
				name.setBackground(Color.LIGHT_GRAY);
				name.setBounds(clip + i * lineLength - lineLength / 2 + 5, top + lineHeight / 2 - itemSize - 45, lineLength - 10, 40);
				pnlItem.add(name,0);
			}
		}

		// ����Ϊ���ϲ㣬�������ܸ���ס�����ˣ�����������
		pnlScroll.add(pnlItem, 0);
	}
	/**
	 * ��ӽڵ����
	 * <p>
	 * ����Ϊ͸�����������ϲ㣬���ɸ���������
	 * �������ƣ��������ڣ��Ѿ�����ͼǰ��˵���汾�ı�־
	 */	
	protected void addItem(int stage) {
		pnlItem = new JPanel();
		pnlItem.setLayout(null);
		pnlItem.setOpaque(false);
//		pnlItem.setBounds(0, 5 + stage * 100, 300 + ((itemNums - 1) * lineLength), 100);
		pnlItem.setBounds(0, 2 + stage * 95, 300 + ((itemNums - 1) * lineLength), 100);
		//����ͼǰ��˵���汾�ı�־
		JLabel lbl = new JLabel();
		Font font = new Font("΢���ź�", Font.BOLD, 16);
		Dimension lblSize = new Dimension(100, 80);
		lbl.setFont(font);
		lbl.setBackground(Color.PINK);
		lbl.setOpaque(true);
		lbl.setSize(lblSize);
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		switch (stage) {
		case 0:
			lbl.setText("���˰�");
			break;
		default:
			lbl.setText("ִ�а�");
			break;			
		}
		lbl.setLocation(0, 0);
		pnlItem.add(lbl,0);
		int count = names.length;
		items = new KDSubwayItemButton[itemNums];
		int tmpCount = 0;
		for (int UnionIndex = 0; UnionIndex < itemNums; UnionIndex++) {
			//��ƥ�仭��Ȧ �Ȳ�����Ȧ
			if(UnionIndex >= tmpCount){
				
			}
			for(int tmpIndex = 0; tmpIndex < count; tmpIndex++){
				if(tmpNames != null && tmpNames[tmpIndex] != null && this.unionNames[UnionIndex] != null){	
					//��ͬ��ʾ������Ϣ����ͬ��ʾԲȦ
					if(this.unionNames[UnionIndex].equals(tmpNames[tmpIndex])){
						//���л���ť
						++tmpCount;
						if(stage==0){					
							RButton item = new RButton();
							item.setName(names[tmpIndex]);
							item.setOpaque(true);
							item.setPreferredSize(new Dimension(itemSize * 2, itemSize * 2));
//	item.setBackground(circleColor);
							item.setDetail(values[tmpIndex]);
							item.setBounds(clip + UnionIndex * lineLength - itemSize + 100, top - itemSize + lineHeight / 2, itemSize * 2, itemSize * 2);
							item.setRenderer(null);
							
							//modified by zhaoqin for R140821-0099 on 2014/12/26
							//items[tmpIndex] = item;
							items[UnionIndex] = item;
							
							pnlItem.add(item,0);
							Map datesValue = (Map) dates[tmpIndex];
							if (isShowDate) {
								if (datesValue != null){
									JLabel date = new JLabel((String)datesValue.get("date"), SwingConstants.CENTER);
									date.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
									date.setText((String)datesValue.get("date"));
									date.setBounds(clip + UnionIndex * lineLength - lineLength / 2 + 105, top + lineHeight / 2 + itemSize + 5, lineLength - 10, 14);
									pnlItem.add(date,0);
								}
							}
							if (isShowName) {
								JTextArea name = new JTextArea();
								name.setAutoscrolls(true);
								name.setLineWrap(true);  
								name.setEditable(false);
//		name.setOpaque(false);
								name.setText(names[tmpIndex]);
//		name.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200),2));
								name.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2));
								name.setToolTipText(names[tmpIndex]);
								name.setText(names[tmpIndex]);
								name.setBackground(Color.LIGHT_GRAY);
								// name.setText("2012-01-01");
								name.setBounds(clip + UnionIndex * lineLength - lineLength / 2 + 105, top + lineHeight / 2 - itemSize - 45, lineLength - 10, 40);
								pnlItem.add(name,0);
							}
							//stage==1
						}else{
							Color circleColor = allStates[itemStates[tmpIndex]].getStatusColor();
							//������ɫ����ť
							RButton item = new RButton(circleColor);
							item.setName(names[tmpIndex]);
							item.setOpaque(true);
							item.setPreferredSize(new Dimension(itemSize * 2, itemSize * 2));
							item.setBackground(circleColor);
							//
							item.setDetail(values[tmpIndex]);
							item.setBounds(clip + UnionIndex * lineLength - itemSize + 100 , top - itemSize + lineHeight / 2 , itemSize * 2, itemSize * 2);
							if(tmpIndex>=itemNums){
								System.err.println("�п��ܳ�����̱�������������Խ��");
							}
							
							//modified by zhaoqin for R140821-0099 on 2014/12/26
							//items[tmpIndex] = item;
							items[UnionIndex] = item;
							
							pnlItem.add(item,0);
							Map datesValue = (Map) dates[tmpIndex];
							if (isShowDate) {
								if (datesValue != null){
									JLabel date = new JLabel((String)datesValue.get("date"), SwingConstants.CENTER);
									date.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
									date.setText((String)datesValue.get("date"));
									date.setBounds(clip + UnionIndex * lineLength - lineLength / 2 + 105, top + lineHeight / 2 + itemSize + 5, lineLength - 10, 14);
									pnlItem.add(date,0);
								}
							}
							if (isShowName) {
								JTextArea name = new JTextArea();
								name.setAutoscrolls(true);
								name.setLineWrap(true);  
								name.setEditable(false);
								name.setText(names[tmpIndex]);
								name.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2));
								name.setToolTipText(names[tmpIndex]);
								name.setText(names[tmpIndex]);
								name.setBackground(Color.LIGHT_GRAY);
								name.setBounds(clip + UnionIndex * lineLength - lineLength / 2 + 105, top + lineHeight / 2 - itemSize - 45, lineLength - 10, 40);
								pnlItem.add(name,0);
							}
							
						}	
						break;
					}
				}
			}
		}
		
		// ����Ϊ���ϲ㣬�������ܸ���ס�����ˣ�����������
		pnlScroll.add(pnlItem, 0);
	}
	
	/**
	 * ���˵�����
	 */
	public static  JPanel addDesc() {	
		KDSubwayItemState check = new KDSubwayItemState("�ƻ��������", BLUE);
		KDSubwayItemState FINISH = new KDSubwayItemState("��ʱ���", GREEN);		
		KDSubwayItemState HALFPEND = new KDSubwayItemState("��ʱ���", ORANGE);
		KDSubwayItemState UNFINISH = new KDSubwayItemState("��ʱ��δ���", RED);
		KDSubwayItemState undo = new KDSubwayItemState("δ�ﵽ�������", TAN);
		KDSubwayItemState[] allStatesDesc = new KDSubwayItemState[] {check, FINISH, HALFPEND, UNFINISH, undo};
		pnlDesc = new JPanel();
		pnlDesc.setOpaque(false);
		for (int i = 0; i < allStatesDesc.length; i++) {
			Color circleColor = allStatesDesc[i].getStatusColor();
			//�����Ǹ�Բ��ť��ɫ
			RButton btn = new RButton(circleColor);
			btn.setOpaque(true);
			btn.setPreferredSize(new Dimension(itemSize * 2, itemSize * 2));
			btn.setBackground(circleColor);
			pnlDesc.add(btn);
//			}else{
//				KDSubwayItemButton btn = new KDSubwayItemButton(true);
//				btn.setOpaque(true);
//				btn.setPreferredSize(new Dimension(itemSize * 6, itemSize * 2));
//				btn.setBackground(circleColor);
//				pnlDesc.add(btn);
//			}
			JLabel label = new JLabel(allStatesDesc[i].getStatusName());
			pnlDesc.add(label);
		}
		return pnlDesc;
	}

}
