/****************************************************************************
 * NCSA HDF                                                                 *
 * National Comptational Science Alliance                                   *
 * University of Illinois at Urbana-Champaign                               *
 * 605 E. Springfield, Champaign IL 61820                                   *
 *                                                                          *
 * For conditions of distribution and use, see the accompanying             *
 * hdf/COPYING file.                                                        *
 *                                                                          *
 ****************************************************************************/

package ncsa.hdf.jhv;

import  java.awt.*;
import java.awt.event.*;
import  java.lang.*;
import  java.util.Vector;


//----------------------------------------------------------------------------
//
//  Upgraded to the JDK 1.1.1b Event Model
//   - Apu Kapadia May 21st, 1997
//
//----------------------------------------------------------------------------

/* This class will make a plot for selected HDF Vdata
   Modified Data: 04-02-97
		  Update the method, MakeAxis, to make a axis label "Value" for
		  y-axis and "Row Value" for x-axis.
 */
class JHVVdataPlotCanvas extends Canvas implements MouseListener{

	// my parent
	JHVVdataPlotFrame plotFrame;

	// the data will be ploted
	double  data[][];

	// annotation for each xy-plot
	String  annotation[] ;

	double xMin, xMax;
	double yMin, yMax;

	// plot line
	int plotLine;

	// ploted point
	Vector  plotPoints[];

	// color for each drawable point
	Color   plotLineColor[];
	
	// some of parameter to make a graphic
	public static final int  borderLen = 40;
	public static final int  extraLen    = 10;
	public static final int  arrowLen    = 05;
	public static final int  longScaleSize    = 04;
	public static final int  shortScaleSize   = 02;

	// define draw mode parameter
	public static final int  LINE  = 0;
	public static final int  POINT = LINE+1;

	// draw mode (default)
	int     drawMode = LINE;

	// the boundary of the rectangle(plot)
	Rectangle  boundary = new Rectangle();
 
    	// Flicker-free update with translation by scrollbars
    	Image 		offScreenImage = null;
    	Dimension 	offscreensize;		
    	Graphics 	offGraphics;

	//  the indicator of the plot frame
    	boolean frameDisplayed = false;

	// set font
	// Create the new font for displaying the  data correctly 
    	// in the specified canvas within the scrollbar
    	Font dataFont        = new Font("Fixed", Font.PLAIN, 14);

	int  fontWidth = 14;
	int  fontHeight= 15;

	// zooming factor keep the drawable data range
	int  zoomFactor = 0;

	// current mouse coordinates
	int xPos=0, yPos=0;

	int oldCursor = Cursor.DEFAULT_CURSOR;

	// need to redraw the plot
	boolean  drawPlot = false;

    /** Default new constructor */
    public JHVVdataPlotCanvas() {
	// set plotLineColor
	plotLineColor = new Color[8];

	plotLineColor[0] = Color.red;
	plotLineColor[1] = Color.blue;
	plotLineColor[2] = Color.orange.darker();
	plotLineColor[3] = Color.cyan;
	plotLineColor[4] = Color.green;
	plotLineColor[5] = Color.magenta.darker();
	plotLineColor[6] = Color.black;

	// set font
	setFont(dataFont);
	addMouseListener(this);
    }

    /** Default new constructor */
    public JHVVdataPlotCanvas(JHVVdataPlotFrame frame, double data[][]) {

	// set color
	this();

	// set my parent
	plotFrame = frame;

	// set data
	setData(data);
	addMouseListener(this);	
    }

 //  Set preferred  size at SxS for current canvas  
  public Dimension getPreferredSize() {
    return getMinimumSize();
  }

 // Set minimum size at SxS for current canvas  
  public Dimension getMinimumSize(){
    return new Dimension(360,360);
  }

  /** 
   * Sets the font of the component.
   * @param f the font
   */
  public synchronized void setFont(Font f) {
    // set Font for component
    super.setFont(f);
    
    // get new FontMetrics
    FontMetrics fontMetrics = getFontMetrics(getFont());
    
    // set font width & height
    fontHeight = fontMetrics.getHeight();
    fontWidth  = fontMetrics.charWidth('A');
    
  }

   /** Set x-axis data range by the specified  data range */ 
   public void setXAxisRange(double data[]) {
	
	xMin = xMax = data[0];
	for (int i=1; i<data.length; i++) {
	    double tmp = data[i];

	    xMin = Math.min(tmp, xMin);
	    xMax = Math.max(tmp, xMax);
	}
	
	repaint();
    }

    /** reset axis data range for x-axis */
    public void setXAxisRange(double xmin, double xmax) {

	xMin = xmin;
	xMax = xmax;

	repaint();

    }

   /** Set y-axis data range by the specified  data range */ 
   public void setYAxisRange(double data[]) {
		
	for (int i=0; i<data.length; i++) {
	    double tmp = data[i];

	    yMin = Math.min(tmp, yMin);
	    yMax = Math.max(tmp, yMax);
	}
	
	repaint();
    }

    /** reset axis data range for y-axis */
    public void setYAxisRange(double ymin, double ymax) {

	yMin = ymin;
	yMax = ymax;

	repaint();

    }

    /** Set annotation
     * @param strs the annotation for each xy-plot
     */
    public void setAnnotation(String[] strs) {
	annotation = strs;	
    }

    /** set new plot data 
     * @param data the plot data
     */
    public void setData(double data[][]) {

	/*  origanize data .... 
	x-axis:(data[0])
	    1 2 3 4 5 6 .....
	y1-axis:(data[1])
	     11.1 23.2 ...
	y2-axis:(data[2])
	     -12.32 -34.1 ... */
	
	this.data = data;
	
	// set x-axis
	setXAxisRange(data[0]);

	repaint();
	
    }

    /** Get point refer to the graphic device
     * @param xVal the x  value(physical)
     * @param yVal the y  value(physical)
     * @return the point refer to the current graphic device 
     */
    public Point getDevicePoint(double xVal, double yVal) {
	
	// device value
	int x,y;
		
	x = (int)((xVal-xMin)/(xMax-xMin)*( boundary.width +1 )+boundary.x   );
	y = (int)((yMax-yVal)/(yMax-yMin)*( boundary.height+1 )+boundary.y   );
	return (new Point(x,y));
    }

    /** Get data point from the graphic device
     * @param x the x value(device)
     * @return the double value from the current graphic device 
     */
    public double  getDataPointx(int x) {
	
	// data value
	double xVal;
		
	xVal = (xMax-xMin) * (double)(x - boundary.x)/ ((double)(boundary.width+1)) + xMin;
	
	return xVal;
	
    }

    /** Get data point from the graphic device
     * @param y the y value(device)
     * @return the double value from the current graphic device 
     */
    public double  getDataPointy(int y) {
	
	// data value
	double yVal;
		
	// yVal = (1.0 - (y - boundary.y)/(boundary.height+1)) * (yMax - yMin) + yMin;
	yVal = yMax - ((y - boundary.y)/(boundary.height+1)*(yMax-yMin)) ;

	return yVal;
    }

    /**
     * Append draw point
     */
    public void appendPlotedPoints() {

	// default
	appendPlotedPoints(data);

    }

    /**
     * Append draw point for the whole dataset
     * @param data the whole ploted dataset
     */
    public void appendPlotedPoints(double data[][]) {

     // how many line will be ploted ?
     plotLine  = data.length - 1;

     if (plotLine > 0) {

	plotPoints = null;
	plotPoints = new Vector[plotLine];

	for (int kk=1; kk<=plotLine; kk++) {

	    // specify the new vector to hold the drawable points
	    plotPoints[kk-1] = new Vector();
    
	    // how many points?
	    int  size = data[0].length;
	    
	    for (int i=0; i<size; i++) {

	    	// x value
	    	double   x = data[0][i];

	    	// y value
	    	double   y = data[kk][i];

	        Point pp = getDevicePoint(x,y);
	 
	        // append point
	        plotPoints[kk-1].addElement(pp);

	    }
	} 
      } 
    }

    /** Draw annotation */
    public void drawAnnotation(Graphics gc) {

	// current color
	Color oldColor = gc.getColor();

	int h = getSize().height;
	int w = getSize().width;
	int x = borderLen + 8;
	int y = h - fontHeight/2;

	// draw each line
	for (int kk=0; kk<plotLine; kk++) { 
	
	    // set default color
	    gc.setColor(plotLineColor[kk]);

	   if (annotation[kk] != null) { // enough point to draw
		
		int strLen = (annotation[kk].length() + 1) * fontWidth + 18  ;
		if ((x+strLen) > w) { // move to next line
		   
		    // adjust y
		    y -= (fontHeight - 1);
		
		    // adjust x
		    x = borderLen + 8;
		}

		// symbol
		gc.fillRect(x,y-8,12,8);
		
		gc.setColor(oldColor);

		// annotation
		gc.drawString( annotation[kk], x+18 ,y);
		
		x += strLen ;	

	    } // if (annotation[kk] != null) { // enough point to draw
		
	}

	// reset the color
	gc.setColor(oldColor);

    }

    /** Draw the point */
    public void drawPlotPoint(Graphics gc) {

	// current color
	Color oldColor = gc.getColor();

	// draw each line
	for (int kk=0; kk<plotLine; kk++) { 
	
	    // set default color
	    gc.setColor(plotLineColor[kk]);

	   // get size of drawable point
	   int size = plotPoints[kk].size();

	   if (size >1) { // enough point to draw

		Point prePoint = (Point)plotPoints[kk].elementAt(0);

		for (int i=1; i<size; i++) {

		     // current point		    
		     Point cPoint = (Point)plotPoints[kk].elementAt(i);
	
		     switch(drawMode) {
		     case LINE:
		     	gc.drawLine(prePoint.x, prePoint.y, cPoint.x, cPoint.y);
			break;
		     case POINT:
			gc.drawOval(prePoint.x-2, prePoint.y-2, 4, 4);
			break;
		     }

		     // move to next point
		     prePoint = cPoint;
		}
		
	    }
	   

	} 

	// reset the color
	gc.setColor(oldColor);

    }

    /** Make the axis by current canvas size & drawable data */
    public void makeAxis(Graphics gc) {

	// get current canvas size
	int width = getSize().width;
	int height= getSize().height;

	// get new boundary
	int lx = 3*borderLen;
	int rx = width - 2*borderLen;
	if (rx<0)
	   rx = lx;

	int uy = 2*borderLen;
	int dy = height - 2*borderLen;
	if (dy<0)
	   dy = uy;

	// reshape the boundary
	boundary.setBounds(lx,     // x
			 uy,     // y
			 rx-lx,  // width
			 dy-uy); // height 

	// draw axis
	gc.drawLine (lx,dy,rx+extraLen,dy);
        gc.drawLine (lx,dy,lx,uy-extraLen);

	// draw label of value
	gc.drawString("Value", lx-3*fontWidth, uy-extraLen-fontHeight);

	// darw arrow   ^
	gc.drawLine (lx,uy-extraLen,lx+arrowLen,uy-extraLen+arrowLen);
	gc.drawLine (lx,uy-extraLen,lx-arrowLen,uy-extraLen+arrowLen);

	// darw arrow  >
	gc.drawLine (rx+extraLen,dy,rx+extraLen-arrowLen,dy+arrowLen);
	gc.drawLine (rx+extraLen,dy,rx+extraLen-arrowLen,dy-arrowLen);

	// draw label of row number
	gc.drawString("[Row Number]", (width-3*borderLen)/2, dy+3*fontHeight);

	// draw scale (5 points)
	for (int i=0;i<4;i++) {

   	    // long size
    	    gc.drawLine(lx+i*(rx-lx+1)/3,dy-longScaleSize,
	                lx+i*(rx-lx+1)/3,dy+longScaleSize);

	   // compute scale label by specify data value
    	   double yVal = yMin + (yMax - yMin)*i/3.0;   
    	   double xVal = xMin + (xMax - xMin)*i/3.0;

    	   String yLabel = Double.toString(yVal);  // vdata value fof selected field
	  
    	   if (yLabel.length() > 12) yLabel = yLabel.substring(0,12);
      
	   String xLabel = Integer.toString((int)xVal);  // colnum
           if (xLabel.length() > 5) xLabel = xLabel.substring(0,5);
  
   	   int xPos;
	   if  (xLabel.length() > 3)
 		xPos = lx+i*(rx-lx+1)/3-8 ;
	   else
		xPos = lx+i*(rx-lx+1)/3   ;

	   // colnum label
    	   gc.drawString(xLabel,  // string
		 	 xPos, dy+20);

    	   gc.drawLine(lx-longScaleSize,dy+i*(uy-dy+1)/3,
	       	       lx+longScaleSize,dy+i*(uy-dy+1)/3);  // y-axis

	   // vdata label (20) 30-yLabel.trim().length()
   	   gc.drawString(yLabel,
		 	 lx-(yLabel.trim().length()*fontWidth)-2*fontWidth,dy+i*(uy-dy+1)/3 );
  


  	   // short scale
    	   if (i != 3) {
      		gc.drawLine(lx+(int)((i+0.5)*(rx-lx+1)/3),dy-shortScaleSize,
		            lx+(int)((i+0.5)*(rx-lx+1)/3),dy+shortScaleSize);

      		gc.drawLine(lx-shortScaleSize,dy+(int)((i+0.5)*(uy-dy+1)/3),
		            lx+shortScaleSize,dy+(int)((i+0.5)*(uy-dy+1)/3));
   	   }

  	}  // for (int i=0; ...)

    }

    /**
     * Paints the component.
     * @param g the specified Graphics window
     * @see java.awt.Component#paint
     */
    public void paint(Graphics g) {

	// make axis
	makeAxis(g);

	appendPlotedPoints();

	// plot the point;
	drawPlotPoint(g);

	// make annotation
	drawAnnotation(g);

    	// get current graphics color 
    	Color gColor = g.getColor();
 
 	// set color
    	g.setColor(Color.lightGray);
  
	// separate line
	g.draw3DRect(0,getSize().height-1, getSize().width, 1, true);

 	// reset the color
    	g.setColor(gColor);
  
    }

 /** Updates the component. This method is called in
   * response to a call to repaint. You can assume that
   * the background is not cleared.
   * @param g the specified Graphics window
   * @see java.awt.Component#update
   */
 public void update(Graphics g) {

    Dimension d = getSize();
    
    if (offScreenImage == null) {
      // offScreenImage not created; create it.
      offScreenImage = createImage(d.width*2, d.height*2);	
      
      // get the off-screen graphics context    
      offGraphics    = offScreenImage.getGraphics();
      
      // set the font for offGraphics
      offGraphics.setFont(getFont()); 
    }
    
    // paint the background on the off-screen graphics context
    offGraphics.setColor(getBackground());
    offGraphics.fillRect(1,1,d.width-2,d.height-2);    
    offGraphics.setColor(getForeground());
    
    // draw the current frame to the off-screen 
    paint(offGraphics);

    //then draw the image to the on-screen 
    g.drawImage(offScreenImage, 0, 0, null);

 }

   public int  getZoomFactor() {
	return zoomFactor;
    }

    // set plot mode (line or plot)
    public void setPlotMode(int mode) {

	this.drawMode = mode;
	
	repaint();
    }

  /**
   * If the size of the plot canvas is appropriate 
   * the plot canvas will be displayed
   */
  public synchronized void checkSize() {
    
    // set canvas size
    setSize(500,400);
    
    if (!frameDisplayed) {
      // popup frame
      plotFrame.popup();
      frameDisplayed = true;
    }
    
  }

  public void mouseEntered(MouseEvent e)
  {
    xPos = e.getX();
    yPos = e.getY();
    
    oldCursor = ((Component)plotFrame).getCursor().getType();
    ((Component)plotFrame).setCursor(new Cursor(Cursor.HAND_CURSOR));
    getToolkit().sync();

  }

  public void mouseExited(MouseEvent e)
  {
    xPos = e.getX();
    yPos = e.getY();
   
    // cursor 	
    ((Component)plotFrame).setCursor(new Cursor(oldCursor));
    getToolkit().sync();
  }

  public void mouseReleased(MouseEvent e)
  { 
    if (drawPlot) {
      // data processing
      plotFrame.vdataCanvas.setFindDataRange(false); // max. or min. known
      plotFrame.vdataCanvas.drawXYPlot(zoomFactor);

      // redraw the plot
      repaint();
    }
  }
  
  public void mousePressed(MouseEvent e)
  {
    int  oldZoomFactor = zoomFactor;

    if  (plotFrame.zoomState == plotFrame.ZOOM_OUT) { // shrink
      if (plotFrame.vdataCanvas.canMakeZoom(zoomFactor-1))  
	zoomFactor -= 1;
    } else {
      if (plotFrame.vdataCanvas.canMakeZoom(zoomFactor+1))  
	zoomFactor += 1;
    }
    
    if (oldZoomFactor != zoomFactor)
      drawPlot = true;
    else
      drawPlot = false;

  }

  public void mouseClicked(MouseEvent e) {}

}

/** This class will make a plot for selected HDF Vdata */
public class JHVVdataPlotFrame extends Frame implements ItemListener, ActionListener{

	// keep zoom state
	public static final int	ZOOM_IN	= 0;
	public static final int	ZOOM_OUT= 1;

	// my parant
	JHVVdataCanvas   vdataCanvas;

	// the canvas that will make a x-y plot on that
	JHVVdataPlotCanvas      plotCanvas = null;

 	// Plot data
	double data[][];

	// mode choice
	Choice  plotModeChoice = new Choice(); 

	// for zooming;
	CheckboxGroup  zoomInOut ;
  	Checkbox       zoomIn    ;
  	Checkbox       zoomOut   ;

	// zoom state 
	int 	zoomState = ZOOM_IN;

   /** Default new constructor */
   public JHVVdataPlotFrame() {
     plotModeChoice.addItemListener(this);
     addWindowListener(new WindowClosedProcess((Frame)this));
    }

 

   /** Default new constructor */
   public JHVVdataPlotFrame(double data[][]) {

	// set title
	setTitle("HDF Vdata XY-Plot");

	// set data
	this.data = data;

	// plot canvas
	createDisplayGUI();

	// check size
	plotCanvas.checkSize();
	plotModeChoice.addItemListener(this);
	addWindowListener(new WindowClosedProcess());
    }

   /** Default new constructor */
   public JHVVdataPlotFrame(JHVVdataCanvas vdataCanvas, double data[][]) {

	this(data);

	this.vdataCanvas = vdataCanvas; 
	
	plotModeChoice.addItemListener(this);
	addWindowListener(new WindowClosedProcess());
    }

  /** setup the frame tittle
   * @param title a frame title
   */
  public void setTitle(String title) {
   
    // set title	
    super.setTitle(title);
  }

  /** Create display panel */
  public void createDisplayGUI() {

    // set Layout Manager
    setLayout(new BorderLayout());

    // new plot canvas
    plotCanvas = new JHVVdataPlotCanvas(this, data);
	
    // set graphic panel
    add("Center", plotCanvas);
  
    // zoom control area	  
    Panel zCtrPanel = new Panel();
    zCtrPanel.setLayout(new GridLayout(1,0));
 
    // new CheckboxGroup
    CheckboxGroup  zoomInOut = new CheckboxGroup();
    Checkbox       zoomIn    = new Checkbox("Zoom-In",  zoomInOut, true);
    Checkbox       zoomOut   = new Checkbox("Zoom-Out", zoomInOut, false );
 
    zoomIn.addItemListener(this);
    zoomOut.addItemListener(this);

    zCtrPanel.add(new Label());
    zCtrPanel.add(zoomIn);
    zCtrPanel.add(zoomOut);
  
    Panel ctrPanel = new Panel();
    ctrPanel.setLayout(new GridLayout(1,0));

    // for zoom
    ctrPanel.add( zCtrPanel );

    // plot mode
    plotModeChoice.add("Line");
    plotModeChoice.add("Point");
 
    ctrPanel.add(plotModeChoice);
    Button dismiss = new Button("Dismiss");
    dismiss.addActionListener(this);
    ctrPanel.add(dismiss);
    ctrPanel.add(new Label());

    Panel panel_ = new Panel();
    panel_.setLayout(new GridLayout(1,0));  
    panel_.add(zCtrPanel);
    panel_.add( ctrPanel);
  
    add("South", panel_);
     
  }

 /** popup the new frame actually */
  public void popup() {

    // default frame size
    setSize(500,400);

    // paint dataspread sheet
    plotCanvas.repaint();
    
    // show component of the frame
    show();
    
  }

    /**
     * Called if an action occurs in the Component
     * @param evt the event
     * @param what the action that's occuring
     * @see java.awt.Component#action
     */

  public void itemStateChanged(ItemEvent e)
  {
    ItemSelectable target = e.getItemSelectable();
  
    if (target instanceof Choice) {
	    
      Choice choice = (Choice) target;
      String str = choice.getSelectedItem();
	   
      if ("Line".equals(str)) {
	// set line mode
	plotCanvas.setPlotMode(plotCanvas.LINE);
      }
      else {
	if ("Point".equals(str)) {
	  // set line mode
	  plotCanvas.setPlotMode(plotCanvas.POINT);
	}
      }
    }
    
    // checkbox  selected
    if (target instanceof Checkbox) {
      
      Checkbox checkBox = (Checkbox)target;
      String   label    = checkBox.getLabel();
	 
      // "Zoom In" pressed, then set the indentificator to true and set the 
      // zoom factor (positive or negative number)
      if ("Zoom-In".equals(label)) 
	zoomState 	= ZOOM_IN;
      else
	if ("Zoom-Out".equals(label)) 
		zoomState 	= ZOOM_OUT;
      
    }
    
  }

  public void actionPerformed(ActionEvent e)
  {
    String arg = e.getActionCommand();

    if ("Dismiss".equals(arg))
      dispose();
  }

  public void dispose() {
      // set frame unavailable ?
      vdataCanvas.plotFrame = null;
      vdataCanvas.plotFrameDisplayed = false;
      vdataCanvas.selectedColnumVector.removeAllElements();

      vdataCanvas.repaint();
      
      super.dispose();
  }
    
}