import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*Created by Bartosz Kosakowski
* 02/02/2018 (dd/mm/yyyy)
* Draws the grid used to display Conway's Game of Life
*/
@SuppressWarnings("serial")
public class GridGui extends JPanel {
	
	//all buttons required to implement desired features
	private static JButton startButton = new JButton("Start");
	private static JButton pauseButton = new JButton("Pause");
	private static JButton randPop = new JButton("Randomly Populate");
	private static JButton squarePop = new JButton("Centered Square Population");
	private static JButton randSquare = new JButton("Random Square Populations");
	private static JButton noPop = new JButton("Empty Grid");
	private static JButton stopSim = new JButton("Stop");
	
	//create a JFrame object and a Grid object used to hold the UI and grid of cells
	private static JFrame myFrame = new JFrame();
	private static Grid grid;

	// adds the buttons to the frame and positions them
	private static void setUpButtons() {
		startButton.addActionListener(new StartSim());
		startButton.setBounds(175, 600, 100, 30);
		startButton.setEnabled(false);

		pauseButton.addActionListener(new PauseSim());
		pauseButton.setBounds(175, 600, 100, 30); // 400, 600, 100, 30
		pauseButton.setVisible(false);
		pauseButton.setEnabled(false);
		
		randPop.addActionListener(new RandomPop());
		randPop.setBounds(560, 200, 200, 30);
		
		squarePop.addActionListener(new SquarePop());
		squarePop.setBounds(560, 250, 200, 30);
		
		randSquare.addActionListener(new RandSquarePop());
		randSquare.setBounds(560, 300, 200, 30);
		
		noPop.addActionListener(new NoPop());
		noPop.setBounds(560, 350, 200, 30);
		
		stopSim.addActionListener(new StopSim());
		stopSim.setBounds(300, 600, 100, 30);
		
		myFrame.add(startButton);
		myFrame.add(pauseButton);
		myFrame.add(randPop);
		myFrame.add(squarePop);
		myFrame.add(randSquare);
		myFrame.add(noPop);
		myFrame.add(stopSim);
	}// end of setUpButtons
	
	//draws the UI
	public static void drawUI() {
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setSize(850, 750);
		myFrame.setVisible(true);
		myFrame.setTitle("Conway's Game of Life");
		
		// we add the buttons in after configuring our window
		setUpButtons();
	}// end of drawUI
	
	//graws the grid onto the frame
	public static void drawGrid(Cell[][] cells, int rows, int columns){
		//creates a grid that will contain our cells
		grid = new Grid(cells, rows, columns);
		// we add the grid to the window after setting up the buttons
		myFrame.add(grid);
	}

	// updates the grid based on current cell statuses
	public static void updateGrid() {
		myFrame.repaint();
	}// end of updateGrid
	
	public static void startVisibility(boolean isvis){
		startButton.setVisible(isvis);
		startButton.setEnabled(isvis);
		pauseButton.setVisible(!isvis);
		pauseButton.setEnabled(!isvis);
	}//end of startVisibility
	
	public static void controlVisibility(boolean setVis){
		randPop.setVisible(setVis);
		squarePop.setVisible(setVis);
		randSquare.setVisible(setVis);
		noPop.setVisible(setVis);
		
		randPop.setEnabled(setVis);
		squarePop.setEnabled(setVis);
		randSquare.setEnabled(setVis);
		noPop.setEnabled(setVis);
	}//end of controlVisibility
	
	public static void disableStart(){
		startButton.setEnabled(false);
	}
}// end of GridGui

// The following actionlistener classes allow for each button to
// perform a particular function
class StartSim implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		GridGui.startVisibility(false);
		GridGui.controlVisibility(false);
		ConwaysGame.startGame();
		ConwaysGame.pauseUnpause(false);
		System.out.println("START");
	}
}// end of StartSim

class PauseSim implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		GridGui.startVisibility(true);
		GridGui.controlVisibility(true);
		GridGui.updateGrid();
		ConwaysGame.pauseUnpause(true);
		System.out.println("PAUSED");
	}
}// end of PauseSim

class RandomPop implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("selected random pop");
		GridGui.startVisibility(true);
		ConwaysGame.setPopType(0);
		ConwaysGame.decisionMade();
	}
}// end of RandomPop

class SquarePop implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("selected square pop");
		GridGui.startVisibility(true);
		ConwaysGame.setPopType(1);
		ConwaysGame.decisionMade();
	}
}// end of SquarePop

class RandSquarePop implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("selected random square pop");
		GridGui.startVisibility(true);
		ConwaysGame.setPopType(2);
		ConwaysGame.decisionMade();
	}
}// end of RandSquarePop

class NoPop implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("selected no pop");
		GridGui.startVisibility(true);
		ConwaysGame.setPopType(3);
		ConwaysGame.decisionMade();
	}
}// end of NoPop

class StopSim implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		ConwaysGame.endGame();
		GridGui.startVisibility(true);
		GridGui.controlVisibility(true);
		GridGui.disableStart();
	}
}// end of StopSim

// used to paint the grid shown on the frame
@SuppressWarnings("serial")
class Grid extends JPanel {
	private int rows;
	private int columns;
	private Cell[][] cells;

	public Grid(Cell[][] cells, int rows, int columns) {
		this.cells = cells;
		this.rows = rows;
		this.columns = columns;
	}

	public void paintComponent(Graphics g) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				g.setColor(this.cells[i][j].getColor());
				g.fillRect(i * 5 + 50, j * 5 + 50, 5, 5);
			}
		}
	}
}// end of Grid