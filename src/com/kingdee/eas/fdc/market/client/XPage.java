package com.kingdee.eas.fdc.market.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;

import com.kingdee.eas.fdc.market.DocumentSubjectInfo;



public class XPage extends XJPanel{
	private XDocument xDocument;
	private String header;//ҳü
	private String footer;//ҳ��
	private Font hfFont;//ҳüҳ�ŵ�����
	
	
	//ԭʼ�Ŀռ�
	private List rL;
	//ʣ��Ŀռ�
	private List rR;
	//�Ѿ���ռ�õ�����
	private List rN;
	//�Ѿ���ռ�õ������Ӧ��ԭ��Ŀ��
	private List rN2dN;
	//ԭʼ����Ŀ
	private List dN;//���Ӧ���Ǵ�С���󣬵����ڻ�û�д�����
	//��ǰ��ָ��
	private int dNPointer;//��ʾ���Ӳ��ֵ�Index
	private int beSelectedSubjectNumber;
	private Color beSelectedColor;
	
	private Rectangle marge;
	
	private int column;
	

	public XPage(XDocument xDocument){
		super(null);
		this.xDocument = xDocument;
		this.setBackground(Color.WHITE);
		this.addMouseListener(new XMouseListenerPerXPage(this));
	}
	
	public void init(){
		double length = marge.getWidth();
		int columnWidth = (new Double(length/column)).intValue();
		rL = new ArrayList();
		Rectangle[] rLs = new Rectangle[column];
		for(int i=0 ; i<column ; i++){
			rLs[i] = new Rectangle(marge.x + (new Double(i*length/column)).intValue(),marge.y,0,marge.height);
		}
		rLs[column - 1].width = columnWidth;
		for(int i=column - 2 ; i>=0 ; i--){
			rLs[i].width = rLs[i+1].x - 1 - rLs[i].x;
		}
		for(int i=0 ; i<column ; i++){
			rL.add(rLs[i]);
		}
		rN = new ArrayList();
		dN = new ArrayList();
		dNPointer = 0;//ָ��
		rN2dN = new ArrayList();
		for(int i=0 ; i<rL.size() ; i++){
			rN.add(null);
			rN2dN.add(null);
		}
		rR = new ArrayList();
		for(int i=0 ; i<rL.size() ; i++){
			rR.add(new Rectangle((Rectangle)rL.get(i)));
		}
		//
		add(createHF(header,hfFont,true));
		add(createHF(footer,hfFont,false));
		//
	}
	
	public void clickAtPoint(Point point,int clickCount,boolean isRigth){
		if(xDocument != null){
			int number = getNumberOfDN(point);
			if(isRigth){
				//�һ�ѡ��
				xDocument.selectedSubjectNumber2(number);
			}else{
				//���ѡ��
				xDocument.selectedSubjectNumber1(number);
			}
		}
	}
	
	public void setHeaderFooter(String header,String footer,Font hfFont){
		this.header = header;
		this.footer = footer;
		this.hfFont = hfFont;
	}
	
	/**
	 * ����ҳü��ҳ��
	 * @param str �����Ե�����
	 * @param font ��������
	 * @param isHeader �Ƿ�ҳü��true��ҳü��false��ҳ��
	 * @return
	 */
	public XJLabel createHF(String str,Font font,boolean isHeader){
		XJLabel l = new XJLabel(str);
		l.setFont(font);
		l.setHorizontalAlignment(SwingConstants.CENTER);
		l.setVerticalAlignment(SwingConstants.CENTER);
		if(isHeader){
			l.setBounds(0, 0,this.getSize().width, this.getMarge().y);
		}else{
			l.setBounds(0, this.getMarge().y + this.getMarge().height,this.getSize().width, this.getSize().height - this.getMarge().y - this.getMarge().height);
		}
		return l;
	}
	
	/**
	 * ������Ŀ��Ϣ������Ŀ�ؼ�
	 * @param buttonAndTestList List �����ɵĿؼ���ϱ��浽��� List ��
	 * @param dSubject DocumentSubjectInfo ��Ŀ��Ϣ
	 * 
	 * @return XDimension ��Ŀ�ؼ�
	 */	
	public XDimension createXDimension(List buttonAndTestList,DocumentSubjectInfo dSubject){
		double length = marge.getWidth();
		int columnWidth = (new Double(length/column)).intValue();
		XDimension d = new XDimension(columnWidth*dSubject.getColumnCount()-1,0,dSubject.getColumnCount(),dSubject.getSubjectNumber());
		d.setDSubject(buttonAndTestList,dSubject);
		return d;
	}
	
	public void addXDimension(XDimension newD){
		dN.add(newD);
		reAddAllXDimension();
	}
	
	public int reAddAllXDimension(){
		return reAddAllXDimension(-1);
	}
	
	private int reAddAllXDimension(int toSubjectNumber){
		//int returnValue = 0;//����ֵ��-1��ʾ�޷����ӣ�0��ʾ�޿��ÿռ䣬1��ʾ�������
		int firstFreeIndex = 0;
		for(;firstFreeIndex < rR.size() && rR.get(firstFreeIndex)==null;firstFreeIndex++);
		for(;firstFreeIndex < rR.size() && dNPointer < dN.size() && ( toSubjectNumber < 0 || ((XDimension)dN.get(dNPointer)).getSubjectNumber() <= toSubjectNumber );){
			XDimension newD = (XDimension)dN.get(dNPointer);
			if(!rearrange(newD.getXWidthMultiple(),firstFreeIndex) ){
				//����һ������
				rR.set(firstFreeIndex, null);
				firstFreeIndex++;
			}else{
				for(firstFreeIndex = 0;firstFreeIndex < rR.size() && rR.get(firstFreeIndex)==null;firstFreeIndex++);
				if(firstFreeIndex+1>rR.size()) return 1; 
				
				Rectangle rr = (Rectangle)rR.get(firstFreeIndex);
				int minH = Math.min(rr.height,newD.height);
				if(newD.getMinSplitHeight() > minH){
					for(int i=0 ; i<newD.getXWidthMultiple() ; i++,firstFreeIndex++){
						ifTrueThenSet(true,rR,firstFreeIndex,null);
					}
				}else if(newD.height > minH){
					//��������ӵ�������ʣ�࣬����Ҫ�����µ�����
					XDimension theNewDimension = newD.split(0, minH, minH);
					if(theNewDimension == null){
						theNewDimension = new XDimension(0,0,newD.width,minH,newD.getXWidthMultiple(),newD.getSubjectNumber());
					}
					dN.add((dNPointer),theNewDimension);
				}else{
					ifTrueThenSet(rN.get(firstFreeIndex) == null,rN,firstFreeIndex,ArrayList.class);
					ifTrueThenSet(rN2dN.get(firstFreeIndex)==null,rN2dN,firstFreeIndex,ArrayList.class);
					newD.xAxis = rr.x;
					newD.yAxis = rr.y;
					//
					((List)rN.get(firstFreeIndex)).add(newD);//������ͬ��ȵ���������newD��һ����
					addXCellToPanel(newD);
					//
					((List)rN2dN.get(firstFreeIndex)).add(new Integer(dNPointer));
					for(int i=0 ; i<newD.getXWidthMultiple() ; i++){
						if(minH == rr.height){
							ifTrueThenSet(true,rR,firstFreeIndex+i,null);
						} else {
							Rectangle temp_r = (Rectangle)rR.get(firstFreeIndex + i);
							int temp_r_h = temp_r.y + temp_r.height - newD.yAxis - newD.height - 1;
							temp_r.setBounds(temp_r.x,newD.yAxis + newD.height + 1, temp_r.width,temp_r_h);
						}
					}
					dNPointer++;
				}
			}
		}
		updateUI();
		return 1;
	}
	
	private int reAddAll(int suggestdHeight,int maxXColumnWidth,int toSubjectNumber){
		int originalFirstFreeIndex = 0;//ԭʼ�ĵ�һ��������
		for(;originalFirstFreeIndex < rR.size() && rR.get(originalFirstFreeIndex)==null;originalFirstFreeIndex++);
		int firstFreeIndex = originalFirstFreeIndex;//��ǰ�Ŀ�����
		int realSuggestHeigth = suggestdHeight;//��ǰ������ʣ����Ƽ��߶�
		int minRHeigth = ((Rectangle)rR.get(firstFreeIndex)).height;
		//�����ǽ����еĿ����ӵ��������ڣ����Ƽ��߶�Ϊ����
		int temp_lastPoint = -1;//�������㵱ǰ��point�������Ĵ���
		while(dNPointer < dN.size() && firstFreeIndex<rR.size() && ( toSubjectNumber < 0 || ((XDimension)dN.get(dNPointer)).getSubjectNumber() <= toSubjectNumber )){
			if(realSuggestHeigth <= 0){
				//��ǰ��û��ʣ��ռ䣬��ôǰ��
				firstFreeIndex ++;
				realSuggestHeigth = suggestdHeight;
			}else{
				XDimension newD = (XDimension)dN.get(dNPointer);
				Rectangle rFree = (Rectangle)rR.get(firstFreeIndex);
				int minH = Math.max(Math.min(realSuggestHeigth,newD.height),newD.getMinSplitHeight());
				if(newD.height > minH && temp_lastPoint != dNPointer){
					//��¼�˵㽫����֤
					temp_lastPoint = dNPointer;
					//��������ӵ�������ʣ�࣬����Ҫ�����µ�����
					XDimension theNewDimension = newD.split(0, minH, rFree.height);
					if(theNewDimension == null){
						theNewDimension = new XDimension(0,0,newD.width,minH,newD.getXWidthMultiple(),newD.getSubjectNumber());
					}
					if(newD.height < 0){
						dN.remove(dNPointer);
					}
					dN.add((dNPointer),theNewDimension);
				}else{
					realSuggestHeigth -= minH;
					ifTrueThenSet(rN.get(firstFreeIndex) == null,rN,firstFreeIndex,ArrayList.class);
					ifTrueThenSet(rN2dN.get(firstFreeIndex)==null,rN2dN,firstFreeIndex,ArrayList.class);
					newD.xAxis = rFree.x;
					newD.yAxis = rFree.y;
					//
					((List)rN.get(firstFreeIndex)).add(newD);
					addXCellToPanel(newD);
					//
					((List)rN2dN.get(firstFreeIndex)).add(new Integer(dNPointer));
					rFree.setBounds(rFree.x,(rFree.y + newD.height + 1), rFree.width,(rFree.height - newD.height - 1));
					minRHeigth = Math.min(rFree.height, minRHeigth);
					dNPointer ++;
				}
			}
		}
		if(firstFreeIndex - originalFirstFreeIndex >= maxXColumnWidth){
			return -1;
		}else{
			for(int i=originalFirstFreeIndex ; originalFirstFreeIndex!=firstFreeIndex && i<=firstFreeIndex ; i++){
				if(minRHeigth == 0){
					rR.set(i,null);
				}else{
					Rectangle temp = (Rectangle)rR.get(i);
					temp.y = temp.y + temp.height - minRHeigth;
					temp.height = minRHeigth;
				}
			}
			return 1;
		}
	}
	
	public void goBackPoint(int toThisDnPointer){
		while(toThisDnPointer >= 0 && dNPointer > toThisDnPointer){
			dNPointer--;
			for(int column = (rN2dN.size() - 1); column >=0 ; column--){
				List columnL = (List)rN2dN.get(column);
				int row = (columnL==null)?-1:(columnL.size()-1);
				if(row < 0 || ((Integer)columnL.get(row)).compareTo(new Integer(dNPointer))<0){
					continue;
				}
				for(; row>=0; row--){
					Integer rowInteger = (Integer)columnL.get(row);
					if(rowInteger.intValue() == dNPointer){
						Rectangle theOriginal = (Rectangle)rL.get(column);//ԭʼ������
						//
						XDimension theRectangle = (XDimension)((List)rN.get(column)).remove(row);//��ռ�õ�����
						removeXCellFromPanel(theRectangle);
						//
						columnL.remove(row);
						Rectangle theFreeRectangle = new Rectangle(theRectangle.xAxis, theRectangle.yAxis, theRectangle.width, (theOriginal.y + theOriginal.height- theRectangle.yAxis ));//��ԭ���γɵ��¿հ�����
						rR.remove(column);
						rR.add(column,theFreeRectangle);
					}else if(rowInteger.intValue() < dNPointer){
						break;
					} 
				}
				ifTrueThenSet(((List)rN2dN.get(column)).size()==0,rN2dN,column,null);
				ifTrueThenSet(((List)rN.get(column)).size() == 0,rN,column,null);
			}
		}
		for(int integratePoint = dNPointer ; integratePoint < dN.size() - 1 ; integratePoint++){
			XDimension dOne = (XDimension)dN.get(integratePoint);
			XDimension dTwo = (XDimension)dN.get(integratePoint + 1);
			if(dOne.getSubjectNumber() == dTwo.getSubjectNumber()){
				dOne.unit(dTwo);
				dN.remove(integratePoint + 1);
				integratePoint--;
			}
		}
	}

	
	/**
	 * ��ָ�����е�����ָ����ȵĿ�����
	 * @param xWidth ���
	 * @param columnIndex �У��Ӵ��п�ʼ�������
	 * @return �Ƿ�������
	 */
	private boolean rearrange(int xWidth,int columnIndex){
		boolean isOK = (xWidth == 1);//������Ϊ1����ô����Ҫ�κε�����ֱ�ӷ���true
		if(isOK){
			return isOK;
		}else{
			int lastSubjectNumber = getNumberOfDN(dNPointer - 1);//��¼��Ŀ��
			int firstFreeColumn = columnIndex, originalFirstFreeColumn = columnIndex;
			//��һ��ִ�е������δ���к��˲�������ô�����ǰҪ���ӵ�����͸ո�������ɵ���������ͬһSubject����ʱ����ռ乻����ôֱ�ӷ���TRUE������FALSE
			if(dNPointer < dN.size() && lastSubjectNumber == getNumberOfDN(dNPointer)){
				//����ͬһSubject�����пռ��Ƿ������ж�
				int searchColumn = firstFreeColumn;
				for( ;searchColumn < rR.size() && ((Rectangle)rR.get(firstFreeColumn)).y == ((Rectangle)rR.get(searchColumn)).y && searchColumn < firstFreeColumn + xWidth ; searchColumn++ );
				if((searchColumn - firstFreeColumn) >= xWidth){
					isOK = true;
				}else{
					isOK = false;
				}
				return isOK;
			}
			int minPointer = dNPointer;//maxPointer = dNPointer;
			//ѭ�����˵Ĵ������ܳ����¿���Ŀ��
			boolean flag = true;
			while(flag && originalFirstFreeColumn - firstFreeColumn < xWidth){
				int searchColumn = firstFreeColumn;
				for( ;searchColumn < rR.size() && ((Rectangle)rR.get(firstFreeColumn)).y == ((Rectangle)rR.get(searchColumn)).y && searchColumn < firstFreeColumn + xWidth ; searchColumn++ );
				if((searchColumn - firstFreeColumn) >= xWidth){
					flag = false;//��ֹѭ������Ϊ�Ѿ��ҵ����ʵĿ����
					//��Ȼ�ҵ��˺��ʵĿ�ȣ���������Ҫ��ָ�����Ƚ��е�����
					//���ǣ������һ����Ÿ���ǰҪ���ӵ������ͬ����ô���ܽ��е�����
					int xDimensionLong = 0;//�ܳ���
					for(int i=dNPointer ; i<dN.size() && ((XDimension)dN.get(i)).getSubjectNumber() <= lastSubjectNumber ; i++){
						xDimensionLong += ((XDimension)dN.get(i)).height;
					}
					int suggestedHight = (xDimensionLong + xWidth-1) / xWidth;
					//���ո������Ƽ��߶Ƚ�����䣬���reAddAll����1����ʾ���ɹ�
					isOK = suggestedHight==0?true:reAddAll(suggestedHight,xWidth,lastSubjectNumber) == 1;
				} else {
					if(dNPointer == 0 || ((XDimension)dN.get(dNPointer - 1)).getXWidthMultiple() > 1){
						//������ȴ���1�Ĳ��������ˣ�����ʧ��
						flag = false;
					} else {
						goBackPoint(dNPointer - 1);//��ϲ�ͬ��ŵ�����
						minPointer--;
						for(firstFreeColumn = 0 ;firstFreeColumn<rR.size() && rR.get(firstFreeColumn) == null ; firstFreeColumn++);
					}
				}
			}
			if(!isOK){
				//��ԭ���б��
				goBackPoint(minPointer);
				reAddAllXDimension(lastSubjectNumber);
			}
			return isOK;
		}
	}

	
	public List getLeavings(){
		
		List xdl = new ArrayList();
		while(dNPointer < dN.size()){
			xdl.add(dN.remove(dNPointer));
		}
		return xdl;
		
	}
	
	private int getNumberOfDN(int point){
		if(point < 0){
			return 0;
		}else{
			XDimension d = (XDimension)dN.get(point);
			return d.getSubjectNumber();
		}
	}
	
	private int getNumberOfDN(Point xyPoint){
		for(int i=0 ; i<dNPointer ; i++){
			XDimension d = (XDimension)dN.get(i);
			if(isInTheDimension(d, xyPoint)){
				return d.getSubjectNumber();
			}
		}
		return 0;
	}
	
	/**
	 * �ж�һ�����Ƿ���ָ����������
	 * @param d ����
	 * @param p ������
	 */
	private boolean isInTheDimension(XDimension d,Point p){
		return (d.xAxis <= p.x && d.yAxis <= p.y && d.xAxis + d.width >= p.x && d.yAxis + d.height >= p.y);
	}
	
	/**
	 * ��ʾ
	 */
	public void paint(Graphics g) {
		super.paint(g);
		//�ָ���
		if(column > 1){
			Color old = g.getColor();
			g.setColor(new Color(80,80,80));
			for(int i=column-2 ; i>=0 ; i--){
				Rectangle r = (Rectangle)rL.get(i);
				g.drawLine(r.x + r.width, r.y,r.x + r.width, r.y + r.height);
			}
			g.setColor(old);
		}
		//��ѡ��Ŀ
		for(int i=0 ;i<getRN().size() ; i++){
			paintXDimension((List)getRN().get(i),beSelectedColor,g);
		}
	}
	/**
	 * ��ָ����ʾ��ʾ����
	 * @param rL �����б�
	 * @param c ��ɫ
	 * @param g ϵͳ�ṩ�Ļ���
	 */
	private void paintXDimension(List rL,Color c,Graphics g){
		if(rL == null || c == null){
			return;
		}
		Color oldC = g.getColor();
		g.setColor(c);
		for(int i=0 ;i<rL.size(); i++){
			XDimension r = (XDimension)rL.get(i);
			if(r.getSubjectNumber() == beSelectedSubjectNumber){
				g.setColor(c);
				g.drawRect(r.xAxis, r.yAxis, r.width, r.height);
			}
		}
		g.setColor(oldC);
	}
	
	private void addXCellToPanel(XDimension xDimension){
		for(int i=0 ; i<xDimension.getXCellCount() ; i++){
			add(xDimension.getXCell(i));
		}
	}
	
	private void removeXCellFromPanel(XDimension xDimension){
		for(int i=0 ; i<xDimension.getXCellCount() ; i++){
			remove(xDimension.getXCell(i));
		}
	}
	
	/**
	 * ��ȡ��ҳ��С����Ŀ���
	 * @return
	 */
	public int getMinSubjectNumber(){
		int min = -1;
		if(dN.size() > 0){
			min = ((XDimension)dN.get(0)).getSubjectNumber();
		}
		return min;
	}
	/**
	 * ��ȡ��ҳ������Ŀ���
	 * @return ��Ŀ���
	 */
	public int getMaxSubjectNumber(){
		int max = -1;
		if(dN.size() > 0){
			max = ((XDimension)dN.get(dN.size()-1)).getSubjectNumber();
		}
		return max;
	}
	/**
	 * ������Ŀ��ţ���ȡ����Ŀ�ڱ�ҳ���������
	 * @param subjectNumber ��Ŀ��ţ���ȫ�ĵı��
	 * @return ��ţ�δ�ҵ��򷵻�-1
	 */
	public int getPointerBySubjectNumber(int subjectNumber){
		int po = -1;
		for(int i=0 ; i<dN.size() ;i++){
			if(((XDimension)dN.get(i)).getSubjectNumber() == subjectNumber){
				po = i;
				break;
			}
		}
		return po;
	}
	
	
	/**
	 * @param list ������List
	 * @param index �����index��ʼ��ĩβ,��0��ʼ��list.size() - 1��
	 */
	public void emptyList(List list,int index){
		if(index == 0){
			list.clear();
		}else{
			while(list.size() > index){
				list.remove(index);
			}
		}
	}
	/**
	 * �����б�ֵ������flag������ֵ���õ��б�ָ����λ��
	 * @param flag �ж��Ƿ�����ֵ
	 * @param list �б�
	 * @param index �б�����
	 * @param setter ֵ
	 */
	public void ifTrueThenSet(boolean flag,List list,int index,Class setter){
		if(flag){
			list.remove(index);
			try {
				if(setter != null){
					list.add(index,setter.newInstance());
				}else{
					list.add(index,null);
				}
			} catch (Exception e) {
				throw new NullPointerException(setter.getName() + "can't be implementd");
			}
		}
	}

	/**
	 * ��ռ�������б�
	 */
	public List getRN() {
		return rN;
	}

	/**
	 * ʣ��Ŀռ��б�
	 */
	public List getRR() {
		return rR;
	}

	/**
	 * @return ��ҳ������
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * ���ñ�ҳ�ķָ�����
	 * @param column ����
	 */
	public void setColumn(int column) {
		this.column = column;
	}
	/**
	 * ��ȡ��ҳ������
	 */
	public Rectangle getMarge() {
		return marge;
	}

	/**
	 * ���ñ�ҳ������
	 */
	public void setMarge(Rectangle marge) {
		this.marge = marge;
	}
	
	/**
	 * ��ȡ��ѡ�е���Ŀ���
	 * @return ��Ŀ���
	 */
	public int getBeSelectedSubjectNumber() {
		return beSelectedSubjectNumber;
	}

	/**
	 * ���ñ�ѡ�е���Ŀ��ţ�����ѡ�к���ʾ����ɫ
	 * @param beSelectedSubjectNumber ��Ŀ���
	 * @param beSelectedColor ��ʾ��ɫ
	 */
	public void setBeSelectedSubjectNumberAndColor(int beSelectedSubjectNumber,Color beSelectedColor) {
		this.beSelectedSubjectNumber = beSelectedSubjectNumber;
		this.beSelectedColor = beSelectedColor;
	}
	
	public void print(Graphics g) {
		super.print(g);
	}

}
