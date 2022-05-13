package pg.kgsm.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ball {
	public double x, y;
	public int width = 6, height = 6;
	public double dx, dy;
	public double speed = 1.8;
	
	public Ball(int x, int y) {
		this.x = x;
		this.y = y;
			
		do {
			int angle = new Random().nextInt(359);
			dx = Math.cos(Math.toRadians(angle))*1.2;
			dy = Math.cos(Math.toRadians(angle))*1.2;
			if(dx>0.95) {
				break;
			}else if(dx<-0.95) {
				break;
			}
		}while(true);
	}
	
	public void tick() {		
		if(y+(dy*speed)+height >= Game.HEIGHT) {
			dy *= -1;
			Sound.boardHit.play();
		}else if(y+(dy*speed) < 0) {
			dy *= -1;			
			Sound.boardHit.play();
		}
		
		if(x+dx+width >= Game.WIDTH) {
			Game.pts1++;
			Sound.point.play();
			if (Game.pts1 < 3) {Game.pause = true; Game.fezPonto = true;}
		}else if(x+(dx*speed) < 0) {
			Game.pts2++;
			Sound.point.play();
			if (Game.pts2 < 3) {Game.pause = true; Game.fezPonto = true;}	
		}
		
		Rectangle bounds = new Rectangle((int)(x+(dx)),(int)(y+(dy)),width,height);
		Rectangle boundsPlayer = new Rectangle(Game.player.x,Game.player.y,Game.player.width,Game.player.height);
		Rectangle boundsPlayer2 = new Rectangle(Game.player2.x,Game.player2.y,Game.player2.width,Game.player2.height);
		
		if(bounds.intersects(boundsPlayer)) {
			dx *= -1;
			Sound.playerHit.play();
		}else if(bounds.intersects(boundsPlayer2)) {
			dx *= -1;
			Sound.playerHit.play();
		}
		
		if(Game.cont == 10) {
			speed += 0.1;
			Game.cont = 0;
		}
		
		x += dx * speed;
		y += dy * speed;
		
	}
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect((int)x,(int)y, width, height);
	}
	
	
}
