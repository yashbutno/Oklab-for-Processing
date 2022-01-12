import yash.oklab.*;               //Import the library.

color one;
color two;

void setup()
{
  size(512,512);
  noSmooth();
  noStroke();
  
  //colorMode(HSB,60);             //Color mode of the sketch has no effect on the color output from this library's functions. All colors are remapped to current colorMode settings before output.
  
  Ok.p = this;                     //Assign the value 'this' to the PApplet-type variable 'p', to help the library grab data (such as the current colorMode) from the current sketch.
  
  one = Ok.HSV(240,50,90);         //Ok.HSV returns int; similar Processing's color() function. Accepts 1, 2, 3, or 4 values: (H, S, V), (H, S, V, A), (V), (V, A).
  two = Ok.HSL(120,70,90);         //Ok.HSL also returns int; similar Processing's color() function. Accepts 1, 2, 3, or 4 values: (H, S, L), (H, S, L, A), (L), (L, A).
}                                  //H = Hue (0-360), S = Saturation (0-100), V = Value (0-100), L = Lightness (0-100), A = Alpha (0-100).

void draw()
{
  fill(one);
  rect(0,0,256,512);
  
  fill(two);
  //fill(Ok.HSL(150,50,90));       //You can also use these color functions direcly. Un-comment this line of code to test it.
  rect(256,0,256,512);
}
