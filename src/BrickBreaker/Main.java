package BrickBreaker;

import javax.swing.JFrame;

/*
 * author: Kaustubh Butte 
 */

public class Main 
{

	public static void main(String[] args) 
	{
		/*
		 * JFrame basically creates a window with minimize,close options,etc.
		 */ 
		JFrame obj = new JFrame();
		GamePlay gameplay=new GamePlay();
		obj.setBounds(10, 10, 700, 600);
		obj.setTitle("Brick Breaker");
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gameplay);
	}

}
