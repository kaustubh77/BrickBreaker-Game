package BrickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

/* This class will be actually our panel in which we will run our game
*  First we add this panel inside JFrame. Therefore we create object of
*  this panel inside the main function. The gameplay class must extend
*  the JPanel for us to be able to use the add function of JFrame. The
*  add function adds a component to the Frame that will be occupying 
*  our frame.
*/
public class GamePlay extends JPanel implements KeyListener,ActionListener
{
	private boolean play=false;
	private int score=0;
	private int totalBricks=21; // 3 x 7 map
	private Timer timer; //Tells how fast the ball should move
	private int delay=6;
	
	//Starting positions of the slider and ball
	private int playerX=310;
	private int ballposX=120;
	private int ballposY=350;
	private int ballXDirection=-1;
	private int ballYDirection=-2;
	
	//Object of Brick generator
	private BrickGenerator bricksGrid;
	
	public GamePlay()
	{
		bricksGrid=new BrickGenerator(3,7);
		addKeyListener(this); //We add keyListener in order to work with that library
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer= new Timer(delay,this);// delay,context are the arguments
		timer.start();
	}
	
	public void paint(Graphics g)
	{
		//Creating background
		g.setColor(Color.black);
		g.fillRect(0, 0, 692,592);
		
		//Creating the borders
		g.setColor(Color.yellow);
		g.fillRect(0,0,3,592);
		g.fillRect(0,0,692,3);
		g.fillRect(691,0,3,592);
		
		//Drawing the Bricks grid
		bricksGrid.draw((Graphics2D)g);
		
		//scores
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD,25));
		g.drawString("Score: "+score,590,30);
	
		//the paddle
		g.setColor(Color.green);
		g.fillRect(playerX,550,100,8);
		
		//the ball
		g.setColor(Color.yellow);
		g.fillOval(ballposX,ballposY,20,20);
		
		//You Won
		if(totalBricks<=0)
		{
			play=false;
			ballXDirection=0;
			ballYDirection=0;
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("You Won!,Score: "+score,200,300);
			
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Press 1 to go to the next level",230,350);
			
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Press Enter to Restart",230,450);
			
			
		}
		
		//Game Over display
		if(ballposY > 570)
		{
			play=false;
			ballXDirection=0;
			ballYDirection=0;
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("Game Over \n,Score: "+score,190,300);
			
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Press Enter to Restart",230,350);
		}
		
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		timer.start();
		
		if(play)
		{
			/*
			 *	intersects is an in-built function which takes 
			 *	argument as two rectangles. Since our ball is an 
			 *	oval we create a new rectangle around the ball
			 *	and pass that as the parameter.
			 */
			
			//Handles collision with the paddle
			if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8)))
			{
				ballYDirection*=-1;
			}
			
			A: for(int i=0;i<bricksGrid.map.length;i++)
			{
				for(int j=0;j<bricksGrid.map[0].length;j++)
				{
					/*
					 * We check intersection of ball and a brick 
					 * only if the brick is not yet hit by the
					 * ball before.
					 */
					if(bricksGrid.map[i][j]>0)
					{
						int brickX = j*bricksGrid.brickWidth + 80;
						int brickY = i*bricksGrid.brickHeight + 50;
						int brickWidth=bricksGrid.brickWidth;
						int brickHeight=bricksGrid.brickHeight;
						
						Rectangle currBrick=new Rectangle(brickX,brickY,brickWidth,brickHeight);
						Rectangle currBallPositionRectangle=new Rectangle(ballposX,ballposY,20,20);
						
						if(currBallPositionRectangle.intersects(currBrick))
						{
							bricksGrid.setBrickValue(0,i,j);
							totalBricks--;
							score+=5;
							
							if(ballposX + 19 <=currBrick.x || ballposX + 1>=currBrick.x + currBrick.width )
							{
								ballXDirection = - ballXDirection;
							}
							else
							{
								ballYDirection = -ballYDirection;
							}
							break A;
						}
					}
				}
			}
			
			ballposX+=ballXDirection;
			ballposY+=ballYDirection;
			if(ballposX<0)//Handles Collision with left wall
			{
				ballXDirection*=-1;
			}
			if(ballposY<0)//Handles collision with the top wall
			{
				ballYDirection*=-1;
			}
			if(ballposX>670)//Handles collision with the right wall
			{
				ballXDirection*=-1;
			}
		}
		
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		//Not required for this project but we need to declare
		//the methods because they are abstract methods that need
		//to be implemented
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		//Move paddle to the left
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			if(playerX>=600)
			{
				playerX=600;
			}
			else
			{
				moveRight();
			}
		}
		
		//Move paddle to the right
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			if(playerX<10)
			{
				playerX=10;
			}
			else
			{
				moveLeft();
			}
		}
		
		//Restart the game
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
		{
			if(!play)
			{
				play=true;
				ballposX=120;
				ballposY=350;
				ballXDirection=-1;
				ballYDirection=-2;
				playerX=310;
				score=0;
				totalBricks=21;
				bricksGrid= new BrickGenerator(3,7);
				repaint();
			}
		}
		
		//Go to the next level
		if(e.getKeyCode()==KeyEvent.VK_1)
		{
			if(!play)
			{
				play=true;
				ballposX=120;
				ballposY=350;
				delay-=1;
				ballXDirection=-1;
				ballYDirection=-2;
				playerX=310;
				score=0;
				totalBricks=21;
				bricksGrid= new BrickGenerator(3,7);
				repaint();
			}
		}
	}
	
	public void moveRight()
	{
		play=true;
		playerX+=30;
	}
	
	public void moveLeft()
	{
		play=true;
		playerX-=30;
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		//Not required for this project but we need to declare
		//the methods because they are abstract methods that need
		//to be implemented
	}
	
}
