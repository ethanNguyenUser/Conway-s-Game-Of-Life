package mainPackage;

import java.awt.Color;

import java.awt.Graphics;



public class Cell {

	private int myX, myY; // x,y position on grid

	private boolean myAlive; // alive (true) or dead (false)

	private int myNeighbors; // count of neighbors with respect to x,y

	private boolean myAliveNextTurn; // Used for state in next iteration

	private Color myColor; // Based on alive/dead rules

	private final Color DEFAULT_ALIVE = Color.ORANGE;

	private final Color DEFAULT_DEAD = Color.GRAY;



	public Cell(int x, int y) {

		this(x, y, false, Color.GRAY);

	}



	public Cell(int row, int col, boolean alive, Color color) {

		myAlive = alive;

		myColor = color;

		myX = col;

		myY = row;

	}



	public boolean getAlive() {

		return myAlive;

	}



	public int getX() {

		return myX;

	}



	public int getY() {

		return myY;

	}



	public Color getColor() {

		return myColor;

	}



	public void setAlive(boolean alive) {

		if (alive) {

			setAlive(true, DEFAULT_ALIVE);

		} else {

			setAlive(false, DEFAULT_DEAD);

		}

	}



	private void setAlive(boolean alive, Color color) {

		myColor = color;

		myAlive = alive;

	}



	public void setAliveNextTurn(boolean alive) {

		myAliveNextTurn = alive;

	}



	public boolean getAliveNextTurn() {

		return myAliveNextTurn;

	}



	public void setColor(Color color) {

		myColor = color;

	}



	public int getNeighbors() {

		return myNeighbors;

	}



	public void calcNeighbors(Cell[][] cell) {
	
		//1 2 3
		//4 x 5
		//6 7 8
		
		myNeighbors = 0;
		
		int cellX = this.getX();
		int cellY = this.getY();
		
		if(Display.getCanWrap()) {
			
			if (cell[(cellY + 81) % 80][(cellX + 101) % 100].getAlive())
				myNeighbors++;
			//8
			
			if (cell[(cellY + 81) % 80][cellX].getAlive())
				myNeighbors++;
			//7
			
			if (cell[(cellY + 81) % 80][(cellX + 99) % 100].getAlive())
				myNeighbors++;
			//6
			
			if (cell[cellY][(cellX + 101) % 100].getAlive())
				myNeighbors++;
			//5
			
			if (cell[cellY][(cellX + 99) % 100].getAlive())
				myNeighbors++;
			//4
			
			if (cell[(cellY + 79) % 80][(cellX + 101) % 100].getAlive())
				myNeighbors++;
			//3
			
			if (cell[(cellY + 79) % 80][cellX].getAlive())
				myNeighbors++;
			//2
			
			if (cell[(cellY + 79) % 80][(cellX + 99) % 100].getAlive())
				myNeighbors++;
			//1
		
		}
		else{
			
			if (cellX != 99 && cellY != 79 && cell[cellY + 1][cellX + 1].getAlive())
				myNeighbors++;
			//8
			
			if (cellY != 79 && cell[cellY + 1][cellX].getAlive())
				myNeighbors++;
			//7
			
			if (cellX != 0 && cellY != 79 && cell[cellY + 1][cellX - 1].getAlive())
				myNeighbors++;
			//6
			
			if (cellX != 99 && cell[cellY][cellX + 1].getAlive())
				myNeighbors++;
			//5
			
			if (cellX != 0 && cell[cellY][cellX - 1].getAlive())
				myNeighbors++;
			//4
			
			if (cellX != 99 && cellY != 0 && cell[cellY - 1][cellX + 1].getAlive())
				myNeighbors++;
			//3
			
			if (cellY != 0 && cell[cellY - 1][cellX].getAlive())
				myNeighbors++;
			//2
			
			if (cellX != 0 && cellY != 0 && cell[cellY - 1][cellX - 1].getAlive())
				myNeighbors++;
			//1
		
		}
		
		// Check if in corner
			
		// Check if against left side
		
		// Check if against right side
		
		// Check if against top
		
		// Check if against bottom

	}




	public void draw(int x_offset, int y_offset, int width, int height,

			Graphics g) {

		// I leave this understanding to the reader

		int xleft = x_offset + 1 + (myX * (width + 1));

		int xright = x_offset + width + (myX * (width + 1));

		int ytop = y_offset + 1 + (myY * (height + 1));

		int ybottom = y_offset + height + (myY * (height + 1));

		Color temp = g.getColor();



		g.setColor(myColor);

		g.fillRect(xleft, ytop, width, height);

	}

}