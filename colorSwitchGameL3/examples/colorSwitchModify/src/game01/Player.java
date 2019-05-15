package game01;

import game01.actors.DoubleBar;
import game01.actors.DoubleCircle;
import game01.actors.RotatingBen;
import game01.actors.RotatingDoubleCross;
import game01.actors.RotatingPlus;
import game01.actors.RotatingPlusOther;
import game01.actors.RotatingRectangle;
import game01.actors.RotatingSwagRectangle;
import game01.actors.RotatingTriangle;
import game01.actors.RotatingWhiteBar;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import beta02.Actor;
import beta02.ColorSwitch;
import java.util.Random;
public class Player {
	private int size;
	private Rectangle viewPort = new Rectangle(1280, 1024);
	int viewPortWidth = 451;
	int viewPortHeight = 800;
	int leftX = 50;
	int rightX = leftX+viewPortWidth;
	//make all position positive
	int topY = 100; //top y position
	int middle = (leftX+rightX)/2;
	long seed; //seed for random actor generator
	Random randomGenerator;
	
	private ArrayList<Actor> actors = new ArrayList<Actor>();
	private ArrayList<Switcher> switchers = new ArrayList<Switcher>();
	private ArrayList<ExplosionParticle> particles = new ArrayList<ExplosionParticle>();
	
	private boolean playing;
	private boolean readyToJump;
	private double screenBrightness = 0;
	private Color color;
	
	private int colorType;
	private int playerID;
	private int points;
	private double threshold = 0.45;
	
	private Ellipse2D.Double body;
	
	private double position = 700;
	private double trackerPosition = 0;
	
	private double velocity;
	private double jumpVelocity;
	private double maxFallVelocity;
	private double gravity;
	private int diameter = 32;
	private boolean dead = false;
	private ColorSwitch cs = null;
	
	private int screenWidth = 1280;
	private int screenHeight = 1024;
	private int score;
	private int particleCount = 5000;
	private double particleSizeMultiplier = 0.3;
	
	
	public Player() {
		dead = false;
		score = 0;
		position = 700; 
		velocity = 0;
		gravity = 0.22;
		jumpVelocity = -6;
		maxFallVelocity = 5;
		colorType = 1;
		playing = false;
		randomGenerator = new Random(0);
		seed = 0;
	}
	public Player(int playerNum, ColorSwitch cs) {
		this();
		this.cs = cs;
		this.screenWidth = cs.pWidth();
		this.screenHeight = cs.pHeight();
		middle = (leftX+rightX)/2;
		viewPort = new Rectangle(leftX, topY, 
				viewPortWidth, viewPortHeight);
	}
	public void setSeed(long l) {
		seed =l;
		randomGenerator = new Random(l);
	}
	public void initDefaultActors() {
		//actors.add(new Rotator(viewPort, 150));
		
		//actors.add(new DoubleCircle(viewPort, 200, 2, 0, 250));
		//actors.add(new DoubleBar(viewPort, 400));
		actors.add(new Switcher(viewPort, 500));
		actors.add(new Rotator(viewPort, 100, 1, 0, 250));
		actors.add(new Rotator(viewPort, -300, -1.3, 0, 200));
//		actors.add(new MovingBar(viewPort, -500));
//		MovingBar temp = new MovingBar(viewPort, -800); temp.setSpeed(2);
//		actors.add(temp);
//		actors.add(new RotatingBen(viewPort, -1400, viewPort.width/2, 0, 1, 100));
//		actors.add(new RotatingRectangle(viewPort, -1800, viewPort.width/2-80, 0, 1, 160));
//		actors.add(new RotatingDoubleCross(viewPort, -2100, 100));
//		
//		actors.add(new RotatingWhiteBar(viewPort, -3000, viewPort.width/2, 0, 1, 100));
		
		//actors.add(new Rotator(viewPort, -3200, 2, 0, 300));
		//actors.add(new DoubleBar(viewPort, -4000));
		//actors.add(new RotatingSwagRectangle(viewPort, 600, viewPort.width/2-50, 0, 1, 100));
		//actors.add(new movingBar(viewPort, -1000));
		//ColorChanger switch1 = new ColorChanger(50, 1000, this);
		//test.setBoundaries(leftX, rightX, 200, 950);
	}
	public void updateActors() {
		if(actors.size()<4) {
			double minPos = 0;
			for(Actor actor: actors) {
				if(actor.getTopPosition() < minPos) {
					minPos = actor.getTopPosition();
				}
			}
			//specifies the difficulty
			int maxVal = 1; 
			if(trackerPosition<100) {
				maxVal = 4;
			} else if(trackerPosition <1600) {
				maxVal = 6;
			} else if(trackerPosition < 3200) {
				maxVal = 8;
			} else {
				maxVal = 11;
			}
			int randomNumber = randomGenerator.nextInt(maxVal);	
			
			//debug mode
			//randomNumber =  1;
			//randomNumber actors are arranged from easy to hard
			
			if(randomNumber == 0) {
				actors.add(new Rotator(viewPort, minPos-450, 2, 0, 250));
			}
			if(randomNumber == 1) {
				MovingBar temp = new MovingBar(viewPort, minPos-200);
				temp.setSpeed(2-(4*(int)(randomGenerator.nextDouble()+0.5)));
				actors.add(temp);
			}
			if(randomNumber == 2) {
				actors.add(new DoubleBar(viewPort, minPos-230));
			}
			if(randomNumber == 3) {
				if(randomGenerator.nextDouble() >0.5) {
					actors.add(new RotatingDoubleCross(viewPort, minPos-380, 120, 0.5));
				} else {
					actors.add(new RotatingDoubleCross(viewPort, minPos-380, 150, -1));
				}
			}
			if(randomNumber == 4) {
				DoubleCircle temp = new DoubleCircle(viewPort, minPos - 400, 2, 0, 260);
				actors.add(temp);
			}
			if(randomNumber == 5) {
				DoubleCircle temp = new DoubleCircle(viewPort, minPos-380, 2, 0, 200);
				actors.add(temp);
			}
			if(randomNumber == 6) {
				actors.add(new RotatingBen(viewPort, minPos - 400, viewPort.width/2, 0, 1, 100));
			}
			if(randomNumber == 7) {
				RotatingDoubleCross cross = new RotatingDoubleCross(viewPort, minPos - 300, 70 + randomGenerator.nextDouble()*100);
				cross.setSpeed(3.5-randomGenerator.nextDouble()*5.7);
				if(cross.getSpeed() <0.4 && cross.getSpeed() > -0.4) cross.setSpeed(0.4);
				actors.add(cross);
			}
			if(randomNumber == 8) {
				DoubleCircle temp = new DoubleCircle(viewPort, minPos-400, -0.5, 0, 250);
				actors.add(temp);
			}
			if(randomNumber == 9) {
				actors.add(new RotatingBen(viewPort, minPos - 400, viewPort.width/2, 0, 1.2, 95));
			}
			if(randomNumber == 10) {
				actors.add(new RotatingRectangle(viewPort, minPos - 480, 0, 
						(1-2*(int)(randomGenerator.nextDouble()+0.5))*(1.4+1.4*randomGenerator.nextDouble()), 170+120*randomGenerator.nextDouble()));
			}
		}
	}
	public Rectangle getViewPort() {
		return viewPort;
	}
	public void draw(Graphics2D g) {
		g.setColor(new Color(40,40,40));
		g.fill(viewPort);
		g.setColor(Color.white);
		g.draw(viewPort);
		
		for (Actor obstacle : actors) {
			if(obstacle.inViewPort()) {
				obstacle.render(g);
			}
		}
		
		if(!playing && !dead) {
			g.setFont(g.getFont().deriveFont((float) 100));
			drawTitle(g, (int) viewPort.getCenterX()-125, (int)viewPort.getCenterY()+100, 100);
			//Doesn't work properly b/c of font spacing, have to use JLabel
			//drawColorString(g, "Color", (int) viewPort.getCenterX()-100, (int) viewPort.getCenterY()+110, 100);
			//drawColorString(g, "Switch!", (int) viewPort.getCenterX()-100, (int) viewPort.getCenterY()+180, 100);
			g.setFont(g.getFont().deriveFont((float) 30.0));
			g.setColor(Color.white);
			g.drawString("Press Z/M to play", (int) viewPort.getCenterX()-100, (int) viewPort.getCenterY());
			
		}
		if(!dead) {
			g.setColor(determineColor());
			g.fill(new Ellipse2D.Double(viewPort.getMinX()+body.getX(), viewPort.getMinY()+body.getY(), diameter, diameter));
		}
		if(dead && particles.size()==0) {
			g.setFont(g.getFont().deriveFont((float) 50.0));
			g.setColor(Color.white);
			g.drawString("Press Left Arrow", (int) viewPort.getCenterX()-180, (int) viewPort.getCenterY()+15);
			g.drawString("To Restart all", (int) viewPort.getCenterX()-150, (int) viewPort.getCenterY()+70);
		}
		
		g.setColor(Color.white);
		g.setFont(g.getFont().deriveFont((float) 30.0));
		g.drawString("Distance:"+(int)trackerPosition, (int) viewPort.getCenterX()+40, (int) viewPort.getMinY()+40);
		g.setFont(g.getFont().deriveFont((float) 30.0));
		
		//draws rectangles to cover top and bottom
		g.setColor(Color.black);
		g.fillRect(0, 0, cs.getWidth(), topY);
		g.fillRect(0, (int) viewPort.getMaxY(), (int) cs.getWidth(), (int) (cs.getHeight()-viewPort.getMinY()));
		g.setColor(Color.white);
		g.drawString("SCORE: " + score, viewPort.x+20, viewPort.y+40);
		
		if(dead == true) {
			if(particles.size() != 0) {
					for(ExplosionParticle particle: particles) {
					particle.draw(g);
				}
			}
			if(screenBrightness>1){
				screenBrightness-=2;
			} else {
				screenBrightness = 0;
			}
			g.setColor(new Color(240, 240, 240, (int) (255*(screenBrightness/100))));
			g.fill(viewPort);
		}
	}
	
	private void drawColorString(Graphics2D g, String string, int x, int y, double size) {
		for(int i = 0; i<string.length(); i++) {
			g.setColor(determineColor(i%5));
			g.drawString(string.substring(i, i+1), x+(int)(i*size/3), y);
		}
	}
	private void drawTitle(Graphics2D g, int x, int y, double size) {
		String string = "Color";
		String string2 = "Switch";
		int offset = 0;
		for(int i = 0; i<string.length(); i++) {
			g.setColor(determineColor(i%5));
			g.drawString(string.substring(i, i+1), x+offset, y);
			if(string.charAt(i) != 'l') 
				offset += size/1.5;
			else
				offset += size/5;
		}
		offset = 0;
		for(int i = 0; i<string2.length(); i++) {
			g.setColor(determineColor(i%5));
			g.drawString(string2.substring(i, i+1), x+offset, (int) (y+size*1.05));
			if(string2.charAt(i)=='i'|| string2.charAt(i) == 't') 
				offset += size/5;
			else if(string2.charAt(i) != 'c')
				offset += size/1.5;
			else 
				offset += size/2;
			
		}
	}
	private Color determineColor() {
		switch(colorType) {
		case 1:
			return ColorSwitch.colors[0];
		case 2:
			return ColorSwitch.colors[1];
		case 3: 
			return ColorSwitch.colors[2];
		case 4:
			return ColorSwitch.colors[3];
		default: 
			return Color.WHITE;
		}
	}
	private Color determineColor(int color) {
		switch(color) {
		case 1:
			return ColorSwitch.colors[0];
		case 2:
			return ColorSwitch.colors[1];
		case 3: 
			return ColorSwitch.colors[2];
		case 4:
			return ColorSwitch.colors[3];
		default: 
			return Color.WHITE;
		}
	}
	public int getScore() {
		return score;
	}
	public void setScore(int points) {
		this.score = points;
	}
	public void increaseScore(int increase) {
		
	}
	public void explode() {
		playSound("explosion.wav");
		screenBrightness = 100;
		for(int i = 0; i<particleCount; i++) {
			ExplosionParticle particle = new ExplosionParticle(viewPort, this.viewPortWidth/2, position, Math.random()*360, Math.random()*3, (int) (1+ Math.random()*4), Math.random()*30*particleSizeMultiplier);
			particle.boostYvel(-1*(Math.random()*10));
			particles.add(particle);
		}
	}
	public void move() {
		if(playing && !dead) {
			if(velocity < maxFallVelocity) {
				velocity += gravity;
			}
			
			double relativeThreshold = threshold*viewPort.height;
			if (position + velocity > relativeThreshold) {
				position += velocity;
			} else {
				position = relativeThreshold;
				for(Actor object: actors) {
					object.update(-1*(position+velocity-relativeThreshold)); 
					//negative to account that instead of object going up, with negative velocity, we displace the actors with positive velocity
				}
				trackerPosition += -1*(position+velocity-relativeThreshold);
			}
			
			if(position > viewPort.getHeight()) {
				playing = false;
				dead = true;
				explode();
				velocity = 0;
				position = viewPort.getHeight()-200;
			}
			
		}
	}
	public void accelerate(double a) {
		
	}
	public void setViewPort(Rectangle viewPort) {
		this.viewPort = viewPort;
	}
	public boolean checkBadCollision() {
		return true;
	}

	public void jump() {
		// TODO Auto-generated method stub
		if(!dead) {
			playing = true;
			velocity = jumpVelocity;
			playSound("jump1.wav");
		}
	}
	public void update() {
		body = new Ellipse2D.Double(viewPort.getWidth()/2-diameter/2, position, diameter, diameter);
		updateActors();
		for(int i = 0; i<actors.size(); i++) {
			Actor object = actors.get(i);
			if(object.outOfScreen()) {
				actors.remove(i);
				i--;
			}
			if(object.inViewPort()) {
				object.update();
				if(object instanceof Switcher) {
					if(object.collidesWith(body, colorType)) {
						actors.remove(i);
						i--; //to account for the loss of object
						playSound("jump.wav");
						randomizeColor();
						
					}
				} else if(object instanceof ScoreStar) {
					if(object.collidesWith(body, colorType)) {
						actors.remove(i);
						playSound("score.wav");
						i--;
						score+=1;
					}
				}
				else {
					if(object.starCollision(body, colorType)) {
						score+=1;
						playSound("score.wav");
					}
					if(object.switcherCollision(body, colorType)) {
						randomizeColor();
						playSound("jump.wav");
					}
					if (object.collidesWith(body, colorType)) {
						if(dead != true) {
							explode();
						}
						dead = true;
						playing = false;
						//score = 0;
					}
				}
				
			}
		}
		if(dead == true) {
			for(int i = 0; i<particles.size(); i++) {
				particles.get(i).update();
				//if(!particles.get(i).inViewPort()) {
					//particles.remove(i);
				//}
			}
		}
	}

	/**
	 * this method creates an arraylist of size 3 with the 3 colors not used, then chooses one randomly
	 */
	public void randomizeColor() {
		ArrayList<Integer> possibleColors = new ArrayList<Integer>();
		for(int i = 1; i<=4; i++) {
			if(i!=colorType) { 
				possibleColors.add(i);
			}
		}
		colorType = possibleColors.get((int) (Math.random()*3));
		
	}

	public void restart(long seed) {
		particles.clear();
		setSeed(seed);
		screenBrightness = 100;
		score = 0;
		playing = false;
		dead = false;
		position =  700;
		trackerPosition = 0;
		actors.clear();
		initDefaultActors();
	}
	public static synchronized void playSound(String url) {
		      try {
		        AudioInputStream audioIn = AudioSystem.getAudioInputStream(Player.class.getResource("Sounds/"+url));
		        Clip clip = AudioSystem.getClip();
		        clip.open(audioIn);
		        clip.start();
		      } catch (Exception e) {
		        e.printStackTrace();
		      }
		}
}
