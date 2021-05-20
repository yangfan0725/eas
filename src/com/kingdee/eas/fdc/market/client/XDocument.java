package com.kingdee.eas.fdc.market.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.MediaPrintableArea;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JViewport;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

import com.kingdee.eas.fdc.market.DocumentInfo;
import com.kingdee.eas.fdc.market.DocumentProperties;
import com.kingdee.eas.fdc.market.DocumentSubjectCollection;
import com.kingdee.eas.fdc.market.DocumentSubjectInfo;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerEntryCollection;


public class XDocument extends JPanel implements Scrollable,Printable {
	
	private boolean enableRightMouseEvent = true;
	private DocumentInfo doc;
	private XCellsAndSubjectAndAnswer cellAndAnswer;

	/**
	 * �洢���е�ҳ��
	 */
	private List pageList ;

	/**
	 * ��ѡ��������
	 */
	private int subjectNumberThatBeSelect;
	
	/**
	 * ���һ�ѡ������
	 */
	private int subjectNumberThatBeRightClicked=-1;
	
	/**
	 * ���캯����ҳ��������Ϊ 10
	 */
	public XDocument(){
		super(new XDocumentLayout(10/*ҳ����*/));
		setBackground(Color.GRAY);
	}
	/**
	 * ����Ϊ���ĵ�����
	 */
	public void setEmptyDoc(){
		setDoc(getDefaultDoc());
	}
	/**
	 * �����ĵ���Ϣ
	 * @param doc
	 */
	public void setDoc(DocumentInfo doc) {
		this.doc = doc;
		reBuild();
	}
	/**
	 * ���½����ĵ�����
	 */
	public void reBuild(){
		initDocument(doc);
	}
	/**
	 * ��ʼ�����棬ʹ���ĵ���Ϣ
	 * @param doc
	 */
	private void initDocument(DocumentInfo doc){
		this.removeAll();
		pageList = new ArrayList();//�洢���е�ҳ��
		cellAndAnswer = new XCellsAndSubjectAndAnswer();//�洢���еĴ𰸡��ؼ���ϵ
		//����һ�հ�ҳ
		addXPage(createXPage(doc));
		//�ӵ�һ��ſ�ʼ������Ŀ���ӵ�ҳ����
		realAddDSubjectFromIndex(0);
		//ˢ��ҳ��
		updateUI();
	}
	/**
	 * ��ȡ��ǰ�����ĵ���Ϣ
	 * @return DocumentInfo
	 */
	public DocumentInfo getDoc() {
		return doc;
	}
	/**
	 * ��ȡ��ǰ�ĵ��ġ��ش���Ϣ
	 * @return QuestionPaperAnswerEntryCollection
	 */
	public  QuestionPaperAnswerEntryCollection getAnswerCollection(){
		return cellAndAnswer.getAnswer();
	}
	/**
	 * ���õ�ǰ�ĵ��ġ��ش���Ϣ
	 * @param questionPaperAnswerEntryCollection
	 */
	public void setAnswerCollection(QuestionPaperAnswerEntryCollection questionPaperAnswerEntryCollection){
		cellAndAnswer.setAnswer(questionPaperAnswerEntryCollection);
	}

	//��Xpage�У���������¼�����
	public void selectedSubjectNumber1(int selectedSubjectNumber){
		if(subjectNumberThatBeRightClicked >=0 && selectedSubjectNumber != subjectNumberThatBeRightClicked){
			//��˵���ڵ�����ǰ���б�˫��ѡ�е�����
			//���б��λ�õĲ���
			DocumentSubjectInfo curr = getSubjectInfo(selectedSubjectNumber);
			DocumentSubjectInfo willBeMoved = getSubjectInfo(subjectNumberThatBeRightClicked);
			if(willBeMoved != null && curr != null && willBeMoved.getSubjectNumber() != curr.getSubjectNumber() + 1){
				//�����ǰ��ѡ���գ�����˫����ѡ���գ������µ�λ����ԭ��λ�ò�ͬ
				deleteDSubject(willBeMoved);
				willBeMoved.setSubjectNumber(curr.getSubjectNumber() + 1);
				insertDSubject(willBeMoved);
			}
		}
		setPageSubjectNumberAndColor(selectedSubjectNumber,Color.RED);
		subjectNumberThatBeSelect = selectedSubjectNumber;
		subjectNumberThatBeRightClicked = -1;
	}
	//��Xpage�У���������¼�����
	public void selectedSubjectNumber2(int selectedSubjectNumber){
		if(!enableRightMouseEvent){
			//����Ӧ����һ��¼�
			return ;
		}
		setPageSubjectNumberAndColor(selectedSubjectNumber,Color.BLACK);
		subjectNumberThatBeSelect = selectedSubjectNumber;
		subjectNumberThatBeRightClicked = selectedSubjectNumber;
	}
	
	/**
	 * ����ĳ��ŵ���ɫ
	 * @param selectSubjectNumber ��ţ����subjectNumber������showNumber��
	 * @param color ��ɫ
	 */
	private void setPageSubjectNumberAndColor(int selectSubjectNumber,Color color){
		for(int i=0 ; i<pageList.size() ; i++){
			XPage xPage = (XPage)pageList.get(i);
			if(xPage.getMinSubjectNumber() <= selectSubjectNumber && xPage.getMaxSubjectNumber() >= selectSubjectNumber){
					xPage.setBeSelectedSubjectNumberAndColor(selectSubjectNumber,color);
					xPage.updateUI();
			}else{
				if(xPage.getBeSelectedSubjectNumber() != 0){
					xPage.setBeSelectedSubjectNumberAndColor(0,color);//Ϊɶ����Ϊ0�أ���Ϊ����Ǵ�1��ʼ��O(��_��)O����~��
					xPage.updateUI();
				}
			}
		}
	}
	
	
	public DocumentSubjectInfo getSelectedSubjectInfo(){
		return getSubjectInfo(subjectNumberThatBeSelect);
	}
	
	public DocumentSubjectInfo getSubjectInfo(int selectedSubjectNumber){
		DocumentSubjectCollection sc = doc.getSubjects();
		for(int i=0 ;i <sc.size() ; i++){
			DocumentSubjectInfo s = sc.get(i);
			if(s.getSubjectNumber() == selectedSubjectNumber){
				return s;
			}
		}
		return null;
	}
	
	public void addXPage(XPage xPage){
		pageList.add(xPage);
		add(xPage);
	}
	
	public XPage createXPage(DocumentProperties dp){
		int pWidth = metric2Pixed(dp.getWidth());
		int pHeight = metric2Pixed(dp.getHeight());
		int x = metric2Pixed(dp.getRightMarge());
		int y = metric2Pixed(dp.getTopMarge());
		int rW = metric2Pixed(dp.getWidth()-dp.getRightMarge()-dp.getLeftMarge());
		int rH = metric2Pixed(dp.getHeight()-dp.getTopMarge()-dp.getBottomMarge());
		//
		XPage xPage = new XPage(this);
		xPage.setSize(new Dimension(pWidth, pHeight));
		xPage.setMarge(new Rectangle(x,y,rW,rH));
		xPage.setColumn(dp.getColumnCount());
		//����ҳü��ҳ��
		xPage.setHeaderFooter(dp.getHeader(), dp.getFooter(), dp.getFont());
		xPage.init();
		return xPage;
	}
	
	public MediaPrintableArea getMediaPrintableArea(){
		//�ұ߾�
		float x = (float)doc.getRightMarge();
		//�ϱ߾�
		//float fontHeight = pixed2Metric(getFontMetrics(doc.getFont()).getHeight());
		float y = 4f;//((float)doc.getTopMarge() - fontHeight)/2;
		//���
		float width = (float)(doc.getWidth()-doc.getRightMarge()-doc.getLeftMarge());
		if(width <= 0){
			width = 1.0f;
		}  
		//�߶�
		float height = (float)doc.getHeight() - (2*y);
		return new MediaPrintableArea(x,y,width,height,MediaPrintableArea.MM);
	}
	
	/**
	 * �޸ĺ󱣴档ֻ�޸������ݣ����޸���Ŀ�����
	 */
	public void updateDSubject(DocumentSubjectInfo dSubject){
		int subjectNumber = dSubject.getSubjectNumber();
		int index = subjectNumber-1;
		if(index < 0){
			//������������������ɣ���ʵҲ��ɲ���
			insertDSubject(dSubject);
			return;
		}
		
		int thePageWillBeChanged = Integer.MAX_VALUE;
		for(int pageIndex = 0; pageIndex<pageList.size() ; pageIndex++){
			XPage xPage = (XPage)pageList.get(pageIndex);
			if(this.haveTheSubjectNumber(xPage,subjectNumber)){
				thePageWillBeChanged = pageIndex;
				int point = xPage.getPointerBySubjectNumber(subjectNumber);
				xPage.goBackPoint(point);//����ʾ������ع���ָ��λ��
				xPage.getLeavings();//�������ܰ�������δ����Ŀؼ�ɾ���ˡ�
				break;
			}
		}
		for(int i=pageList.size()-1 ; i>thePageWillBeChanged ; i--){
			//�ӻ�����ɾ�����ҳ��
			XPage xp = (XPage)pageList.remove(i);
			//�ӽ�����ɾ�����ҳ��
			this.remove(xp);
		}
		realAddDSubjectFromIndex(index);
		
	}
	
	public void insertDSubject(DocumentSubjectInfo dSubject){
		//�����Ȼ�� size + 1�����ǲ����ʱ��ɲ�������Ŷ��size ���ǲ���ʱʹ�õ�����������Ҫ��1��
		int totalCount = doc.getSubjects().size();
		if(dSubject.getSubjectNumber() <= 0){
			dSubject.setSubjectNumber(totalCount+1);
		}
		int subjectNumber = dSubject.getSubjectNumber();
		int index = subjectNumber-1;
		if(index < totalCount){
			int indexBeReset = doc.getSubjects().get(index).getSubjectNumber();
			//���»��ƽ���
			int pageIndex = 0;
			for(; pageIndex<pageList.size() ; pageIndex++){
				XPage xPage = (XPage)pageList.get(pageIndex);
				if(this.haveTheSubjectNumber(xPage,indexBeReset)){
					int point = xPage.getPointerBySubjectNumber(indexBeReset);
					xPage.goBackPoint(point);//����ʾ������ع���ָ��λ��
					xPage.getLeavings();//�������ܰ�������δ����Ŀؼ�ɾ���ˡ�
					break;
				}
			}
			for(int i=pageList.size()-1 ; i>pageIndex ; i--){
				//�ӻ�����ɾ�����ҳ��
				XPage xp = (XPage)pageList.remove(i);;
				//�ӽ�����ɾ�����ҳ��
				this.remove(xp);
			}
		}
		//���°������
		doc.getSubjects().addObject(index,dSubject);
		resetSubjectNumber(index);
		realAddDSubjectFromIndex(index);
	}
	
	public void deleteDSubject(DocumentSubjectInfo dSubject){
		int totalCount = doc.getSubjects().size();
		int subjectNumber = dSubject.getSubjectNumber();
		int index = subjectNumber-1;
		if(index < totalCount){
			int indexBeReset = doc.getSubjects().get(index).getSubjectNumber();
			//���»��ƽ���
			int pageIndex = 0;
			for(; pageIndex<pageList.size() ; pageIndex++){
				XPage xPage = (XPage)pageList.get(pageIndex);
				if(this.haveTheSubjectNumber(xPage,subjectNumber)){
					int point = xPage.getPointerBySubjectNumber(indexBeReset);
					xPage.goBackPoint(point);//����ʾ������ع���ָ��λ��
					xPage.getLeavings();//�������ܰ�������δ����Ŀؼ�ɾ���ˡ�
					break;
				}
			}
			for(int i=pageList.size()-1 ; i>pageIndex ; i--){
				//�ӻ�����ɾ�����ҳ��
				XPage xp = (XPage)pageList.remove(i);;
				//�ӽ�����ɾ�����ҳ��
				this.remove(xp);
				
			}
			doc.getSubjects().removeObject(index);
			//���°������
			resetSubjectNumber(index);
		}
		//doc.getSubjects().add(dSubject);
		realAddDSubjectFromIndex(index);
	}
	

	private void realAddDSubjectFromIndex(int subjectIndexInDoc){
		//subjectL.add(dSubject);
		DocumentSubjectCollection subjectCollection = doc.getSubjects();
		for(int i=subjectIndexInDoc ; i<subjectCollection.size() ; i++){
			XPage xPage = (XPage)pageList.get(pageList.size() - 1);
			//Ӧ���и�У�����
			List buttonAndTextList = new ArrayList();
			XDimension xd = xPage.createXDimension(buttonAndTextList,subjectCollection.get(i));
			//��������
			addXDimension(xd);
			//���ӿؼ���ϵ
			addButtonAndText(buttonAndTextList);
		}
	}
	
	private void addButtonAndText(List buttonAndTextList){
		for(int i=0 ; i<buttonAndTextList.size() ; i++){
			XButtonAndText bt = (XButtonAndText)buttonAndTextList.get(i);
			cellAndAnswer.put(bt.optionId,bt);
		}
	}
	
	private void resetSubjectNumber(int index){
		DocumentSubjectCollection sc = doc.getSubjects();
		for(int i=index; i<sc.size() ; i++){
			sc.get(i).setSubjectNumber(i+1);
		}
	}
	


	protected boolean isEnableRightMouseEvent() {
		return this.enableRightMouseEvent;
	}

	public void setEnableRightMouseEvent(boolean enableRightMouseEvent) {
		this.enableRightMouseEvent = enableRightMouseEvent;
	}

	private boolean haveTheSubjectNumber( XPage page, int subjectNumber){
		return page.getMinSubjectNumber() <= subjectNumber && page.getMaxSubjectNumber()>= subjectNumber;
	}
	
	
	private void addXDimension(XDimension dimension){
		XPage xPage = (XPage)pageList.get(pageList.size() - 1);
		xPage.addXDimension(dimension);
		List leavings =  xPage.getLeavings();
		if(leavings.size() > 0){
			addXPage(createXPage(this.doc));
			for(int i=0 ; i<leavings.size() ; i++){
				addXDimension((XDimension)leavings.get(i));
			}
		}
	}
	
	public List getXPageList(){
		return pageList;
	}
	
	public int getXPageCount(){
		if(pageList == null){
			return 0;
		}else{
			return pageList.size();
		}
	}
	/**
	 * @see Printable
	 */
	public int print(Graphics graphics, PageFormat pageFormat, int page)
			throws PrinterException {
		//Ĭ�ϴ�ӡ��Ϊ 72 dpi��Dot Per Inch��
		if(page >= getXPageCount()){
			return Printable.NO_SUCH_PAGE;
		}
		
		double scale = 72f/getScreenResolution();//�������ű���
		
		Graphics2D graphics2d = (Graphics2D) graphics;
		graphics2d.scale(scale, scale);
		((XPage)getXPageList().get(page)).xPrint(0, 0, graphics);
		return Printable.PAGE_EXISTS;
	}
	
	public int getScreenResolution(){
		Toolkit tk = Toolkit.getDefaultToolkit();
		return tk.getScreenResolution();
	}
	
	/**
	 * ������ת��Ϊ��Ļ��������
	 * @return ����
	 */
	private int metric2Pixed(int mm){
		Double pix = new Double(getScreenResolution() * mm / 25.4f);
		return pix.intValue();
	}
	
	private float pixed2Metric(int pix){
		Double metric = new Double(pix * 25.4/getScreenResolution());
		return metric.floatValue();
	}
	
	private DocumentInfo getDefaultDoc(){
		DocumentInfo doc = new DocumentInfo();
		doc.setWidth(200);
		doc.setHeight(240);
		doc.setTopMarge(15);
		doc.setBottomMarge(15);
		doc.setRightMarge(10);
		doc.setLeftMarge(10);
		doc.setColumnCount(1);
		doc.setHeader("������-�ʾ����");
		doc.setFooter("www.kingdee.com");
		return doc;
	}

	   /**
     * Returns the preferred size of the viewport for a view component.
     * This is implemented to do the default behavior of returning
     * the preferred size of the component.
     * 
     * @return the <code>preferredSize</code> of a <code>JViewport</code>
     * whose view is this <code>Scrollable</code>
     */
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }


    /**
     * Components that display logical rows or columns should compute
     * the scroll increment that will completely expose one new row
     * or column, depending on the value of orientation.  Ideally, 
     * components should handle a partially exposed row or column by 
     * returning the distance required to completely expose the item.
     * <p>
     * The default implementation of this is to simply return 10% of
     * the visible area.  Subclasses are likely to be able to provide
     * a much more reasonable value.
     * 
     * @param visibleRect the view area visible within the viewport
     * @param orientation either <code>SwingConstants.VERTICAL</code> or
     *   <code>SwingConstants.HORIZONTAL</code>
     * @param direction less than zero to scroll up/left, greater than
     *   zero for down/right
     * @return the "unit" increment for scrolling in the specified direction
     * @exception IllegalArgumentException for an invalid orientation
     * @see JScrollBar#setUnitIncrement
     */
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        switch(orientation) {
        case SwingConstants.VERTICAL:
            return visibleRect.height / 10;
        case SwingConstants.HORIZONTAL:
            return visibleRect.width / 10;
        default:
            throw new IllegalArgumentException("Invalid orientation: " + orientation);
        }
    }


    /**
     * Components that display logical rows or columns should compute
     * the scroll increment that will completely expose one block
     * of rows or columns, depending on the value of orientation. 
     * <p>
     * The default implementation of this is to simply return the visible
     * area.  Subclasses will likely be able to provide a much more 
     * reasonable value.
     * 
     * @param visibleRect the view area visible within the viewport
     * @param orientation either <code>SwingConstants.VERTICAL</code> or
     *   <code>SwingConstants.HORIZONTAL</code>
     * @param direction less than zero to scroll up/left, greater than zero
     *  for down/right
     * @return the "block" increment for scrolling in the specified direction
     * @exception IllegalArgumentException for an invalid orientation
     * @see JScrollBar#setBlockIncrement
     */
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        switch(orientation) {
        case SwingConstants.VERTICAL:
            return visibleRect.height;
        case SwingConstants.HORIZONTAL:
            return visibleRect.width;
        default:
            throw new IllegalArgumentException("Invalid orientation: " + orientation);
        }
    }  
    

    /**
     * Returns true if a viewport should always force the width of this 
     * <code>Scrollable</code> to match the width of the viewport.
     * For example a normal text view that supported line wrapping
     * would return true here, since it would be undesirable for
     * wrapped lines to disappear beyond the right
     * edge of the viewport.  Note that returning true for a
     * <code>Scrollable</code> whose ancestor is a <code>JScrollPane</code>
     * effectively disables horizontal scrolling.
     * <p>
     * Scrolling containers, like <code>JViewport</code>,
     * will use this method each time they are validated.  
     * 
     * @return true if a viewport should force the <code>Scrollable</code>s
     *   width to match its own
     */
    public boolean getScrollableTracksViewportWidth() {
	if (getParent() instanceof JViewport) {
	    return (((JViewport)getParent()).getWidth() > getPreferredSize().width);
	}
	return false;
    }

    /**
     * Returns true if a viewport should always force the height of this 
     * <code>Scrollable</code> to match the height of the viewport.
     * For example a columnar text view that flowed text in left to
     * right columns could effectively disable vertical scrolling by 
     * returning true here.
     * <p>
     * Scrolling containers, like <code>JViewport</code>,
     * will use this method each time they are validated.  
     * 
     * @return true if a viewport should force the Scrollables height
     *   to match its own
     */
    public boolean getScrollableTracksViewportHeight() {
	if (getParent() instanceof JViewport) {
	    return (((JViewport)getParent()).getHeight() > getPreferredSize().height);
	}
	return false;
    }

}
