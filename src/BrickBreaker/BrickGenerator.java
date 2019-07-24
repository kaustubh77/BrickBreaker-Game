package BrickBreaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class BrickGenerator 
{
	public int map[][];
	public int brickWidth;
	public int brickHeight;
	
	public BrickGenerator(int rows,int cols)
	{
		map= new int[rows][cols];
		for(int i=0;i<map.length;i++)
			for(int j=0;j<map[0].length;j++)
				map[i][j]=1;
		/*	1 as the element tells that that particular 
		 * 	brick was not intersected by the ball and therefore
		 * 	will be displayed on the screen else it wont 
		 * 	be displayed.
		 */
		
		brickWidth=540/cols;
		brickHeight=150/rows;
	}
	
	public void draw(Graphics2D g)
	{
		for(int i=0;i<map.length;i++)
		{
			for(int j=0;j<map[0].length;j++)
			{
				if(map[i][j]>0) 
				{
					g.setColor(Color.white);
					g.fillRect(j*brickWidth+80,i*brickHeight+50,brickWidth,brickHeight);
					
					/* 
					 * Setting border of bricks to black.
					 * Stroking is a process of drawing a shape’s outline 
					 * applying stroke width, line style, and color attribute
					 */
					g.setStroke(new BasicStroke(3));//3 is the width of borderline
					g.setColor(Color.black);
					g.drawRect(j*brickWidth+80,i*brickHeight+50,brickWidth,brickHeight);
				}
			}
		}
	}
	
	public void setBrickValue(int value,int row,int col)
	{
		map[row][col]=value;
	}
}
