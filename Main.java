import javax.swing.JFrame;

public class Main {
	
	private static JFrame f;
	
	public static void main(String[] args) {

		// Bring up a JFrame with squares to represent the cells

		final int DISPLAY_WIDTH = 800;
		
		final int DISPLAY_HEIGHT = 700;

		f = new JFrame();

		f.setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);

		Display display = new Display(DISPLAY_WIDTH, DISPLAY_HEIGHT);

		f.setLayout(null);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.setTitle("Nguyen_Zajac: Conway's Game of Life");

		f.add(display);
		
		// Adds your game to the frame, and frame is just the window

		f.setVisible(true);

	}
	
	public static void setFrameTitle(String str) {
		f.setTitle(str);
	}

}