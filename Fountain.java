//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: Water Fountain 
// Course: Spring 2022 CS 300 
//
// Author: Sreya Sarathy 
// Email: sarathy2@wisc.edu 
// Lecturer: Professor Mouna Kacem
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: NONE
// Online Sources: NONE
//
///////////////////////////////////////////////////////////////////////////////

/**
 * It is the main class, it implements the various methods for the items in the cart. 
 * @author Sreya Sarathy 
 *
 */

import java.util.Random;
import java.io.File;

import processing.core.PImage;

public class Fountain {
  // Random object
  private static Random randGen;

  // the image of the fountain is represented 
  
  private static PImage fountainImage;

  // the image of the fountain is in the x position 
  public static int positionX;

  // the image of the fountain is in the y position 
 
  public static int positionY;

  public static float velocityX;

  public static float velocityY;


  // the perfect size of the array is defined for the droplets in this case 
  
  public static Droplet[] droplets;

  // the end color and the start color of the droplets are initialized 

  private static int startColor; // blue: Utility.color(23,141,235)
  private static int endColor; // lighter blue: Utility.color(23,200,255)

  /**
   * Main method - the main method calls the runApplication through the Utility class 
   *
   * @param args - the input arguments return none in this case 
   * 
   *
   */
  
  public static void main(String args[]) {
    Utility.runApplication();
  }



  /**
   * This method initializes the values that were declared at at the beginning of the class. 
   * The correctness of both updateDroplet() and remoevOlddroplets() methods are checked 
   * This is done by using the tester methods - testupdateDroplet() and testremoveOlddroplets()
   *
   */
  
  public static void setup() {
    // tester methods
    testUpdateDroplet();
    testRemoveOldDroplets();

    if (testUpdateDroplet() == false) {
      System.out.println("UpdateDroplet() failed");
    }

    if (testRemoveOldDroplets() == false) {
      System.out.println("removeOldDroplets() failed");
    }

    // initalizes ranGen to a new Random object 
    
    randGen = new Random();
   
    // startColor and endColor are initialized for the droplets 
    startColor = Utility.color(23, 141, 235);
    endColor = Utility.color(23, 200, 255);

    // the water fountain is set to the center of the screen 
    positionX = Utility.width() / 2;
    positionY = Utility.height() / 2;

    // the image of the fountain is loaded 
    fountainImage = Utility.loadImage("images" + File.separator + "fountain.png");

    // the new droplet is initalized to an array. This array has 800 null values. 
    
    droplets = new Droplet[800];

  }

  /**
   *
   */
  
  public static void draw() {


    // 10 droplets are created 
    createNewDroplets(10);
   
    // the background is set and filled 
   
    Utility.background(Utility.color(253, 245, 230));
    Utility.fill(Utility.color(23, 141, 235));

    // the image is displayed on the screen 
    Utility.image(fountainImage, positionX, positionY);

    // passes through the droplets array and then it calls updateDroplets for all non null values 
   
    for (int i = 0; i < droplets.length; i++) {
      if (droplets[i] != null) {
        updateDroplet(i);
      }
    }

    // old droplets is removed. In this case, the max age of input is 80 

    removeOldDroplets(80);
  }

  /**
   * This particular method sets and updates the position along with the movement. 
   * It also moderates the drawing of the droplet at a given index. 
   * 
   * @param index -  is the index of a certain droplet within the array
   */
  public static void updateDroplet(int index) {
	  
    // drawing and filling droplets
    Utility.circle(droplets[index].getPositionX(), droplets[index].getPositionY(),
        droplets[index].getSize());
    Utility.fill(Utility.color(23, 141, 235), droplets[index].getTransparency());

    // gravitational effect
    droplets[index].setVelocityY(droplets[index].getVelocityY() + 0.3f);

    // increasing x position by x velocity 
   
    droplets[index].setPositionX(droplets[index].getPositionX() + droplets[index].getVelocityX());
    // increasing x position by x velocity
    
    droplets[index].setPositionY(droplets[index].getPositionY() + droplets[index].getVelocityY());

    // increasing the age of droplets
    droplets[index].setAge(droplets[index].getAge() + 1);
  }

 
  /**
   * The references of the null values in the droplets change to the newly created array. 
   * It also sets a randomized value for the position, age and size of the velocity along 
   * with color. 
   * @param numofDroplets - number of new droplets
   */
  
 
  private static void createNewDroplets(int numOfNewDroplets) {
	  
    // the number of droplets are counted and then created instead of null values. 
	  
    int numOfDroplets = 0;

    for (int i = 0; i < droplets.length; i++) {
      if (droplets[i] == null) {
        numOfDroplets++;
        droplets[i] = new Droplet();
       


        droplets[i].setPositionX(positionX + randGen.nextFloat() * 6 - 3);
        
        droplets[i].setPositionY(positionY + randGen.nextFloat() * 6 - 3);
        
        float dropletVelocityX = randGen.nextFloat() * (1-(-1))+ - 1;
        
        droplets[i].setVelocityX(dropletVelocityX);
        
        float dropletVelocityY = randGen.nextFloat() * (-5 -(-10))+ - 5;
        
        droplets[i].setVelocityY(dropletVelocityY);
        
        int ageOfDroplet = randGen.nextInt(40);
        
        droplets[i].setAge(ageOfDroplet);
        
        int transparencyOfDroplet = randGen.nextInt(96) + 32;
        
        droplets[i].setTransparency(transparencyOfDroplet);
        
        float dropletSize = randGen.nextFloat() * 7 + 4;
        
        droplets[i].setSize(dropletSize);
        
        int dropletColor = Utility.lerpColor(startColor, endColor, randGen.nextInt());
        
        droplets[i].setColor(dropletColor);
        
      }
     
      if (numOfDroplets >= numOfNewDroplets) {
        break;
      }
    }
  }

  /**
   * In this method, if the droplet has a value greater than 88 then the reference of the droplet 
   * array becomes 0 at that particular index 
   *	
   * @param maximumAge
   */
  public static void removeOldDroplets(int maximumAge) {
    for (int i = 0; i < droplets.length; i++) {
      if (droplets[i] != null) {
        if (droplets[i].getAge() > maximumAge) {
          droplets[i] = null;
        }

      }
    }

  }

  /**
   * This method is used to move the x position and y position of the fountain to match the position of the mouse
   *
   */
  
  public static void mousePressed() {
    Fountain.positionX = Utility.mouseX();
    Fountain.positionY = Utility.mouseY();
  }

  /**
   * if the s key is pressed then a screenshot is taken 
   *
   * @param letKey - key pressed
   */
  public static void keyPressed(char letKey) {
    if (letKey == 's' || letKey == 'S') {
      Utility.save("Screenshot.png");
    }
  }

  /**
   * This tester initializes the droplets array to hold at least 3 droplets together. 
   * It creates a single droplet at the position (3,3) with the velocity (-1,-2). 
   * It then checks whether calling updateDroplet() makes sense for this index.
   * It then verifies if it can result in a change to its position to (2.0,1.3) 
   *
   * @return if true is returned then no defect is found. if false is returned then it means otherwise. 
   */
  
  private static boolean testUpdateDroplet() {

    droplets = new Droplet[1];
    droplets[0] = new Droplet(3, 3, 4, 4);

    droplets[0].setVelocityX(-1);
    droplets[0].setVelocityY(-2);


    updateDroplet(0);

    if (Math.abs(droplets[0].getPositionX() - 2.0) < 0.001 && (Math.abs(droplets[0].getPositionY() - 1.3) < 0.001)) {
      return true;
    }
    return false;
  }

  /**
   * This particular tester initalizes the droplets array to hold 3 droplets together. 
   * It calls removeOldDroplets(6) on an array with 3 droplets. However, two of the droplets have 
   * ages over 6 and the other one does not. 
   * Then it checks whether the old droplet was removed and if the young droplet was dismissed. 
   *
   * @return if true is returned then no defect is found and if false is returned
   * then there is a defect 
   */
  
  private static boolean testRemoveOldDroplets() {

    droplets = new Droplet[3];
    droplets[0] = new Droplet();
    droplets[1] = new Droplet();
    droplets[2] = new Droplet();

    droplets[0].setAge(9);
    droplets[1].setAge(11);
    droplets[2].setAge(2);

    removeOldDroplets(6);

    if (droplets[0] == null && droplets[1] == null && droplets[2].getAge() == 2) {
      return true;
    }
    return false;
  }
}