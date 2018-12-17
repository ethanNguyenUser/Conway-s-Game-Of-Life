package mainPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;

// Note that the JComponent is set up to listen for mouse clicks
// and mouse movement.  To achieve this, the MouseListener and
// MousMotionListener interfaces are implemented and there is additional
// code in init() to attach those interfaces to the JComponent.


public class Display extends JComponent implements MouseListener, MouseMotionListener {
	public static final int ROWS = 80;
	public static final int COLS = 100;
	public static Cell[][] cell = new Cell[ROWS][COLS];
	private final int X_GRID_OFFSET = 25; // 25 pixels from left
	private final int Y_GRID_OFFSET = 40; // 40 pixels from top
	private final int CELL_WIDTH = 5;
	private final int CELL_HEIGHT = 5;

	// Note that a final field can be initialized in constructor
	private final int DISPLAY_WIDTH;   
	private final int DISPLAY_HEIGHT;
	private StartButton startStop;
	private ToggleWrap turnWrap;
	private ClearButton clear;
	private StepButton step;
	private QuitButton quit;
	private SpeedButton slowMediumFast;
	private GGGButton GGG;
	private YmButton ym;
	// add jcombobox
	private boolean paintloop = false;
	private static boolean canWrap = true;
	private static int timeBetweenReplots = 700; // change to your liking

	public Display(int width, int height) {
		DISPLAY_WIDTH = width;
		DISPLAY_HEIGHT = height;
		init();
	}


	public void init() {
		setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		initCells();

		addMouseListener(this);
		addMouseMotionListener(this);

		// Example of setting up a button.
		// See the StartButton class nested below.
		startStop = new StartButton();
		startStop.setBounds(25, 550, 100, 36);
		add(startStop);
		startStop.setVisible(true);
		
		// Pause button
		turnWrap = new ToggleWrap();
		turnWrap.setBounds(150, 550, 100, 36);
		add(turnWrap);
		turnWrap.setVisible(true);
		
		// Clear button
		clear = new ClearButton();
		clear.setBounds(275, 550, 100, 36);
		add(clear);
		clear.setVisible(true);
		
		// Step button
		step = new StepButton();
		step.setBounds(400, 550, 100, 36);
		add(step);
		step.setVisible(true);
		
		// Quit button
		quit = new QuitButton();
		quit.setBounds(525, 550, 100, 36);
		add(quit);
		quit.setVisible(true);
		
		// Speed button
		slowMediumFast = new SpeedButton();
		slowMediumFast.setBounds(25, 611, 100, 36);
		add(slowMediumFast);
		slowMediumFast.setVisible(true);
		
		// GGG button
		GGG = new GGGButton();
		GGG.setBounds(650, 40, 100, 36);
		add(GGG);
		GGG.setVisible(true);
		
		// Ym button
		ym = new YmButton();
		ym.setBounds(0, 0, 800, 700);
		add(ym);
		ym.setVisible(false);
		
		repaint();
	}


	public void paintComponent(Graphics g) {
		
		g.setColor(Color.BLACK);
		drawGrid(g);
		drawCells(g);
		drawButtons();

		if (paintloop) {
			try {
				Thread.sleep(getTimeBetweenReplots());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			nextGeneration();
			repaint();
		}
	}


	public void initCells() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				cell[row][col] = new Cell(row, col);
			}
		}
		
//		cell[36][22].setAlive(true);
//		cell[38][22].setAlive(true);
//		cell[37][23].setAlive(true);
//		cell[37][24].setAlive(true);
//		cell[37][25].setAlive(true);
		
	}


	public void togglePaintLoop() {
		paintloop = !paintloop;
	}


	public void setPaintLoop(boolean value) {
		paintloop = value;
	}
	
	public boolean getPaintLoop() {
		return paintloop;
	}


	void drawGrid(Graphics g) {
		for (int row = 0; row <= ROWS; row++) {
			g.drawLine(X_GRID_OFFSET,
					Y_GRID_OFFSET + (row * (CELL_HEIGHT + 1)), X_GRID_OFFSET
					+ COLS * (CELL_WIDTH + 1), Y_GRID_OFFSET
					+ (row * (CELL_HEIGHT + 1)));
		}
		for (int col = 0; col <= COLS; col++) {
			g.drawLine(X_GRID_OFFSET + (col * (CELL_WIDTH + 1)), Y_GRID_OFFSET,
					X_GRID_OFFSET + (col * (CELL_WIDTH + 1)), Y_GRID_OFFSET
					+ ROWS * (CELL_HEIGHT + 1));
		}
	}

	
	void drawCells(Graphics g) {
		// Have each cell draw itself
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				// The cell cannot know for certain the offsets nor the height
				// and width; it has been set up to know its own position, so
				// that need not be passed as an argument to the draw method
				cell[row][col].draw(X_GRID_OFFSET, Y_GRID_OFFSET, CELL_WIDTH,
						CELL_HEIGHT, g);
			}
		}
	}

	private void drawButtons() {
		startStop.repaint();
	}


	private void nextGeneration() {
		
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				cell[i][j].calcNeighbors(cell);
				
				if ((cell[i][j].getAlive() && cell[i][j].getNeighbors() >= 2 && cell[i][j].getNeighbors() <= 3) || cell[i][j].getNeighbors() == 3) {
					cell[i][j].setAliveNextTurn(true);
				}
				else {
					cell[i][j].setAliveNextTurn(false);
				}
			}
		}
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				cell[i][j].setAlive(cell[i][j].getAliveNextTurn());
				
			}
		}
		togglePaintLoop();
		repaint();
		togglePaintLoop();
	}


	public void mouseClicked(MouseEvent arg0) {
		int clickX = arg0.getX();
		int clickY = arg0.getY();
		if (clickX >= 25 && clickX < 625 && clickY >= 40 && clickY < 520) {
			int cellX = (clickX - 25) / 6;
			int cellY = (clickY - 40) / 6;
			cell[cellY][cellX].setAlive(!cell[cellY][cellX].getAlive());
			repaint();
		}

	}


	public void mouseEntered(MouseEvent arg0) {

	}


	public void mouseExited(MouseEvent arg0) {

	}


	public void mousePressed(MouseEvent arg0) {
		
	}


	public void mouseReleased(MouseEvent arg0) {
		
	}


	public void mouseDragged(MouseEvent arg0) {
		int clickX = arg0.getX();
		int clickY = arg0.getY();
		if (clickX >= 25 && clickX < 625 && clickY >= 40 && clickY < 520) {
			int cellX = (clickX - 25) / 6;
			int cellY = (clickY - 40) / 6;
			cell[cellY][cellX].setAlive(true);
			repaint();
		}
	}


	public void mouseMoved(MouseEvent arg0) {
		
	}
	

	private class StartButton extends JButton implements ActionListener {
		StartButton() {
			super("Start");
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent arg0) {
			if (this.getText().equals("Start")) {
				togglePaintLoop();
				setText("Stop");
			} else {
				togglePaintLoop();
				setText("Start");
			}
			repaint();
		}
	}
	private class ToggleWrap extends JButton implements ActionListener {
		ToggleWrap() {
			super("Wrap");
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent arg0) {
			if (this.getText().equals("No Wrap")) {
				setText("Wrap");
				canWrap = true;
			} else {
				setText("No Wrap");
				canWrap = false;
			}
		}
	}
	private class ClearButton extends JButton implements ActionListener {
		ClearButton() {
			super("Clear");
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent arg0) {
			clearEverything();
		}
	}
	
	private class StepButton extends JButton implements ActionListener {
		StepButton() {
			super("Step");
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent arg0) {
			nextGeneration();
		}
	}
	
	private class QuitButton extends JButton implements ActionListener {
		QuitButton() {
			super("Quit");
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
			//ym.setVisible(true);
		}
	}
	
	private class SpeedButton extends JButton implements ActionListener {
		SpeedButton() {
			super("Slow");
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent arg0) {
			if (this.getText().equals("Slow")) {
				setText("Medium");
				setTimeBetweenReplots(50);
			} 
			else if(this.getText().equals("Medium")) {
				setText("Fast");
				setTimeBetweenReplots(10);
			}
			else {
				setText("Slow");
				setTimeBetweenReplots(700);
			}
		}
	}
	
	private class GGGButton extends JButton implements ActionListener {
		GGGButton() {
			super("Spawn GGG");
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent arg0) {
			clearEverything();
			cell[25][25].setAlive(true);
			cell[26][25].setAlive(true);
			cell[25][26].setAlive(true);
			cell[26][26].setAlive(true);
			
			cell[23][59].setAlive(true);
			cell[24][59].setAlive(true);
			cell[23][60].setAlive(true);
			cell[24][60].setAlive(true);
			
			cell[25][34].setAlive(true);
			cell[25][35].setAlive(true);
			cell[26][33].setAlive(true);
			cell[26][35].setAlive(true);
			cell[27][33].setAlive(true);
			cell[27][34].setAlive(true);
			
			cell[25][47].setAlive(true);
			cell[25][48].setAlive(true);
			cell[24][47].setAlive(true);
			cell[24][49].setAlive(true);
			cell[23][48].setAlive(true);
			cell[23][49].setAlive(true);
			
			cell[27][41].setAlive(true);
			cell[27][42].setAlive(true);
			cell[28][41].setAlive(true);
			cell[28][43].setAlive(true);
			cell[29][41].setAlive(true);
			
			cell[35][49].setAlive(true);
			cell[35][50].setAlive(true);
			cell[35][51].setAlive(true);
			cell[36][49].setAlive(true);
			cell[37][50].setAlive(true);
			
			cell[30][60].setAlive(true);
			cell[30][61].setAlive(true);
			cell[31][60].setAlive(true);
			cell[31][62].setAlive(true);
			cell[32][60].setAlive(true);
			
		}
	}
	
	private class YmButton extends JButton implements ActionListener {
		YmButton() {
			super("Ym");
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent arg0) {
			
			if (this.getText().equals("Your")) {
				setText("Mom");
			} 
			else if(this.getText().equals("Mom")) {
				setText("Gei");
			}
			else {
				setText("Your");
			}
		}
	}
	
	public static boolean getCanWrap() {
		return canWrap;
	}
	
	public static void setCanWrap(boolean value) {
		canWrap = value;
	}
	
	public static int getTimeBetweenReplots() {
		return timeBetweenReplots;
	}
	
	public static void setTimeBetweenReplots(int value) {
		timeBetweenReplots = value;
	}
	
	public void clearEverything(){
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				cell[i][j].setAlive(false);
			}
		}
		nextGeneration();
	}
}
