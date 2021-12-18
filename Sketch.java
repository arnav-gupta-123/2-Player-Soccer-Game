import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {

  private boolean wPress = false;
  private boolean aPress = false;
  private boolean sPress = false;
  private boolean dPress = false;
  private boolean upPress = false;
  private boolean rightPress = false;
  private boolean leftPress = false;
  private boolean downPress = false;
  
  private double char1X = 100;
  private double char1Y = 145;
  private double char2X = 400;
  private double char2Y = 145;
  private double char1Xvel = 0;
  private double char1Yvel = 0;
  private double char2Xvel = 0;
  private double char2Yvel = 0;
  private double ballSize = 100;
  private double ballY = 170;
  private double ballX = 265;
  private double ballYvel = 0;
  private double ballXvel = 0;
  double char1XCenter = char1X+32;
  double char1YCenter = char1Y+38;
  double char2XCenter = char2X+32;
  double char2YCenter = char2X+38;
  double ballXCenter = ballX+ballSize/2;
  double ballYCenter = ballY+ballSize/2;
  int CHARRADIUS = 32;

  private double ACCELERATION = 0.5;
  private double DECELERATION = 0.25;
  private double MAXSPEED = 3;
  private double BALLACCELERATION = 1.015;

  private PImage background;
  private PImage player1;
  private PImage player2;
  private PImage soccerball;
  private PImage player1flip;
  private PImage player2flip;
  private boolean isFlip1 = false;
  private boolean isFlip2 = false;
  private int blueScore = 0;
  private int redScore = 0;

  public void settings() {
    size(555, 360);
  }

  public void setup() {
    background = loadImage("Soccer_Field.png");
    player1 = loadImage("Soccer_Player_Red.png");
    player2 = loadImage("Soccer_Player_Blue.png");
    soccerball = loadImage("Soccer_Ball.png");
    player1flip = loadImage("Soccer_Player_Red_Flip.png");
    player2flip = loadImage("Soccer_Player_Blue_Flip.png");
  }

  public void draw() {
    image(background, 0, 0);
    if (char1Xvel < 0) {
      isFlip1 = true;
    }
    else if(char1Xvel > 0) {
      isFlip1 = false;
    }
    if (char2Xvel > 0) {
      isFlip2 = true;
    }
    else if(char2Xvel < 0) {
      isFlip2 = false;
    }

    if(isFlip1 == true){
      image(player1flip, (int)char1X, (int)char1Y);
    }
    else {
      image(player1, (int)char1X, (int)char1Y);
    }
    if(isFlip2 == true){
      image(player2, (int)char2X, (int)char2Y);
    }
    else {
      image(player2flip, (int)char2X, (int)char2Y);
    }
    image(soccerball, (int)ballX,(int)ballY);
    keyAction();
    updateChar();
    kick();
    ballMove();
    changeBallSize(50.0);

    textSize(24);
    text("Red Score: " + redScore + " ---- Blue Score:" + blueScore, 100, 25);
  }

  public void keyAction(){
    //player 1 w
    if (wPress && char1Yvel > -MAXSPEED) {
      char1Yvel -= ACCELERATION;
    }
    else if (!wPress && char1Yvel < 0) {
      char1Yvel += DECELERATION;
    }
    
    //player 1 a
    if (aPress && char1Xvel > -MAXSPEED) {
      char1Xvel -= ACCELERATION;
    }
    else if(!aPress && char1Xvel < 0){
      char1Xvel += DECELERATION;
    }
    
    //player 1 s
    if (sPress && char1Yvel < MAXSPEED) {
      char1Yvel += ACCELERATION;
    }
    else if(!sPress && char1Yvel > 0){
      char1Yvel -= DECELERATION;
    }
    //player 1 d
    if (dPress && char1Xvel < MAXSPEED) {
      char1Xvel += ACCELERATION;
    }
    else if(dPress == false && char1Xvel > 0){
      char1Xvel -= DECELERATION;
    }
    
    
    //player 2 up
    if (upPress && char2Yvel > -MAXSPEED) {
      char2Yvel -= ACCELERATION;
    }
    else if(!upPress && char2Yvel < 0){
      char2Yvel += DECELERATION;
    }

    //player 2 down
    if (downPress && char2Yvel < MAXSPEED) {
      char2Yvel += ACCELERATION;
    }
    else if(!downPress && char2Yvel > 0){
      char2Yvel -= DECELERATION;
    }
    //Player 2 left
    if (leftPress && char2Xvel > -MAXSPEED) {
      char2Xvel -= ACCELERATION;
    }
    else if (!leftPress && char2Xvel < 0){
      char2Xvel += DECELERATION;
    }
    //player 2 right
    if (rightPress && char2Xvel < MAXSPEED) {
      char2Xvel += ACCELERATION;
    }
    else if(!rightPress && char2Xvel > 0){
      char2Xvel -= DECELERATION;
    }
  }

  public void playerBoundries(){
    //////555x, 360y
    if (char1XCenter < 0){
      char1X += 1;
      char1Xvel = 0;
    }
    if (char1XCenter > 550){
      char1X -= 1;
      char1Xvel = 0;
    }
    if (char1YCenter < 0){
      char1Y += 1;
      char1Yvel = 0;
    }
    if (char1Y +75 > 360){
      char1Y -= 1;
      char1Yvel = 0;
    }
    if (char2XCenter < 0){
      char2X += 1;
      char2Xvel = 0;
    }
    if (char2XCenter > 550){
      char2X -= 1;
      char2Xvel = 0;
    }
    if (char2YCenter < 0){
      char2Y += 1;
      char2Yvel = 0;
    }
    if (char2Y +75 > 360){
      char2Y -= 1;
      char2Yvel = 0;
    }
    
  }
  
  public void checkGoal(){
    //left goal
    if (ballX <= 0 && ((ballYCenter + ballSize/2) < 230 && (ballYCenter - ballSize/2) > 120 )){
      blueScore += 1;
      reset();
      System.out.println("Red Score: " + redScore + " ---- Blue Score: "+ blueScore);
    }
    
    if (ballX+ballSize >= 555 && ((ballYCenter + ballSize/2) < 230 && (ballYCenter - ballSize/2) > 120 )){
      redScore += 1;
      reset();
      System.out.println("Red Score: " + redScore + " ---- Blue Score: "+ blueScore);
    }
  }
  
  public void noOuts(){
    //left side
    if (ballX < 0){
      ballXvel = -ballXvel;
      ballX = 0;
    }
    //right side
    if (ballX + ballSize > 555 ){
      ballXvel = -ballXvel;
      ballX = 555 - ballSize;
    }
    //top
    if (ballY < 0 ) {
      ballYvel = -ballYvel;
      ballY = 0;
    }
    //bottom 
    if (ballY + ballSize > 360){
      ballYvel = -ballYvel;
      ballY = 360 - ballSize;
    }
    
  }
  
  public void ballMove(){
    ballY += ballYvel;
    ballX += ballXvel;
    
    ballYvel /= BALLACCELERATION;
    ballXvel /= BALLACCELERATION;
    
    if (ballYvel*ballYvel + ballXvel*ballXvel < 0.4 && ballYvel*ballYvel + ballXvel*ballXvel > -0.4){
      ballYvel = 0;
      ballXvel = 0;
    }
  }

  public void updateChar(){
    char1X += char1Xvel;
    char1Y += char1Yvel;

    char2X += char2Xvel;
    char2Y += char2Yvel;
    
    
    //find centers
    char1XCenter = char1X+32;
    char1YCenter = char1Y+38;
    char2XCenter = char2X+32;
    char2YCenter = char2Y+38;
    ballXCenter = ballX+ballSize/2;
    ballYCenter = ballY+ballSize/2;
    CHARRADIUS = 32;

    playerBoundries();
    checkGoal();
    noOuts();
    

  }

  public void kick(){
    //characters are 64x75px
    double BOUNCINESS = 0.5;
    double KICKPOWER = 70;
    double ballSpeed = Math.pow((Math.pow(ballXvel, 2) + Math.pow(ballYvel, 2)), 0.5);
    
    if (Math.pow(char1XCenter-ballXCenter, 2) + Math.pow(char1YCenter-ballYCenter, 2) < Math.pow(32 + ballSize/2, 2)){
      ballXvel = KICKPOWER*char1Xvel/ballSize + BOUNCINESS*ballSpeed*(ballX-char1X)/(ballSize/2 + 32);
      ballYvel = KICKPOWER*char1Yvel/ballSize + BOUNCINESS*ballSpeed*(ballY-char1Y)/(ballSize/2 + 32);
    }

    if (Math.pow(char2XCenter-ballXCenter, 2) + Math.pow(char2YCenter-ballYCenter, 2) < Math.pow(32 + ballSize/2, 2)){
      ballXvel = KICKPOWER*char2Xvel/ballSize + BOUNCINESS*ballSpeed*(ballX-char2X)/(ballSize/2 + 32);
      ballYvel = KICKPOWER*char2Yvel/ballSize + BOUNCINESS*ballSpeed*(ballY-char2Y)/(ballSize/2 + 32);
    }

  };

  public void keyPressed(){
    if(key=='w'){
      wPress = true;
    }
    if(key=='a'){
      aPress = true;
    }
    if(key=='s'){
      sPress = true;
    }
    if(key=='d'){
      dPress = true;
    }
    if(key==CODED && keyCode == UP){
      upPress = true;
    }
    if(key==CODED && keyCode == LEFT){
      leftPress = true;
    }
    if(key==CODED && keyCode == DOWN){
      downPress = true;
    }
    if(key==CODED && keyCode == RIGHT){
      rightPress = true;
    }
  }

  public void keyReleased(){
    if(key=='w'){
      wPress = false;
    }
    if(key=='a'){
      aPress = false;
    }
    if(key=='s'){
      sPress = false;
    }
    if(key=='d'){
      dPress = false;
    }
    if(key==CODED && keyCode == UP){
      upPress = false;
    }
    if(key==CODED && keyCode == LEFT){
      leftPress = false;
    }
    if(key==CODED && keyCode == DOWN){
      downPress = false;
    }
    if(key==CODED && keyCode == RIGHT){
      rightPress = false;
    }
  }

  public void reset(){
    char1X = 100;
    char1Y = 145;
    char2X = 400;
    char2Y = 145;
    char1Xvel = 0;
    char1Yvel = 0;
    char2Xvel = 0;
    char2Yvel = 0;
    ballSize = 100;
    ballY = 170;
    ballX = 265;
    ballYvel = 0;
    ballXvel = 0;
  }

  public void changeBallSize(double newSize){
    ballSize = newSize;
    soccerball.resize((int)ballSize, (int)ballSize);
  }
}
//https://discourse.processing.org/t/solved-question-about-flipping-images/7391/2 

//https://stackoverflow.com/questions/54575628/is-there-a-way-to-make-circular-hitboxes-in-java
