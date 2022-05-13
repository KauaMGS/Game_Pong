package pg.kgsm.Game;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JOptionPane;

public class Player {
	public int x, y;
	public int width = 4, height = 35;
	public boolean up = false, down = false;
		
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void tick() {
		if(up) {
			y-=2;
		}else if(down){
			y+=2;
		}
		
		if(y+height > Game.HEIGHT) {
			y = Game.HEIGHT - 35;
		}else if(y+height < 35) {
			y = 0;
		}
		
		if(Game.pts1 == 3) {
			Game.pause = true;
			String[] n = {"Sim", "Não"}; 
			int x = JOptionPane.showOptionDialog(null, "O Jogador 1 venceu! \nDeseja jogar novamente?", "Pong", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, n, n[0]);
			switch(x) {
				case 0:
					Game.pts1 = 0;
					Game.pts2 = 0;
					Game.fezPonto = true;
					Game.reset();
				break;
				default:
					System.exit(0);
				break;	
			}
		}
		
		if(Game.pts2 == 3) {
			Game.pause = true;
			String[] n = {"Sim", "Não"}; 
			int x = JOptionPane.showOptionDialog(null, "O Jogador 2 venceu! \nDeseja jogar novamente?", "Pong", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, n, n[0]);
			switch(x) {
				case 0:
					Game.pts1 = 0;
					Game.pts2 = 0;
					Game.fezPonto = true;
					Game.reset();
				break;
				default:
					System.exit(0);
				break;	
			}
		}
		
	}
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y, width, height);
	}
	
}
