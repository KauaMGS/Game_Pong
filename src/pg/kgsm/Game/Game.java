package pg.kgsm.Game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	public static final int WIDTH = 210, HEIGHT = 120, SCALE = 3;
	private boolean isRunning = true;
	public static int pts1, pts2;
	public BufferedImage image;
	public static Player player;
	public static Player player2;
	public static Ball ball = new Ball(100, HEIGHT/2 - 1);
	public static boolean pause = true, fezPonto = true;
	public static int cont = 0;
	
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		player = new Player(5, 40);
		player2 = new Player(197, 40);
		this.addKeyListener(this);
		initFrame();
	}
	
	public static void reset() {
		cont = 0;
		player.up = false;
		player.down = false;
		player.x = 5;
		player.y = 40;
		player2.up = false;
		player2.down = false;
		player2.x = 197;
		player2.y = 40;
		ball = new Ball(100, HEIGHT/2 - 1);
	}
	
	public void initFrame() {
		frame = new JFrame("Pong");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {		
		player.tick();
		player2.tick();	
		ball.tick();
	}
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs==null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.getGraphics();
		Font fonte = new Font("Arial", 18, 18);
		Font font2 = new Font("Arial", 18, 11);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
		g.setColor(Color.WHITE);
		g.setFont(fonte);
		g.drawString(""+pts1, 40, 20);
		g.drawString(""+pts2, 160, 20);
		if(fezPonto) {
			g.setFont(font2);
			g.drawString("Pressione espaço para começar", 26, 40);
		}
		player.render(g);	
		player2.render(g);
		ball.render(g);
		g.dispose();	
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);	
		bs.show();
	}
	
	
	@Override
	public void run() {
		requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		
		while(isRunning){
			long now = System.nanoTime();
			delta += (now-lastTime) / ns;
			lastTime = now;
		
			if(delta>=1){
				if(!(pause)) {
					tick();
					render();
				}
				render();
				frames++;	
				delta--;
			}
			
			if((System.currentTimeMillis() - timer) >= 1000){
				frame.setTitle("Pong    FPS : "+frames);
				frames = 0;
				timer += 1000;
				cont++;
			}	
		}
		stop();
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
		}else if(e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			player2.up = true;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			player2.down = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE && fezPonto == true) {
			pause = false;
			fezPonto = false;
			reset();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
		}else if(e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
		}	
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			player2.up = false;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			player2.down = false;
		}	
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

}
