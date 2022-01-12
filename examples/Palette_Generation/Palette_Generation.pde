import yash.oklab.*;                                     //Import the library.

int res = 4;
int offset = 20;

void setup()
{
  size(512,512);
  noSmooth();
  noStroke();
  
  Ok.p = this;                                           //Assign the value 'this' to the PApplet-type variable 'p', to help the library grab data (such as the current colorMode) from the current sketch.
}

void draw()
{
  for(float i = 1; i <= res; i++)                        //NOTE: H = Hue (0-360), S = Saturation (0-100), V = Value (0-100), L = Lightness (0-100), A = Alpha (0-100).
  {
    fill(Ok.HSV((i*(360/res)-(360/res))+offset,100,100));//Ok.HSV returns int; similar Processing's color() function. Accepts 1, 2, 3, or 4 values: (H, S, V), (H, S, V, A), (V), (V, A).
    rect((i-1)*(width/res),0,width/res,height/4);
    
    fill(Ok.HSV(i*(100/res)));                           //Grayscale. Calculates Value because the function being used is Ok.HSV.
    rect((i-1)*(width/res),height/4,width/res,height/4);
    
    fill(Ok.HSL((i*(360/res)-(360/res))+offset,100,80)); //Ok.HSL also returns int; similar Processing's color() function. Accepts 1, 2, 3, or 4 values: (H, S, L), (H, S, L, A), (L), (L, A).
    rect((i-1)*(width/res),height/2,width/res,height/4);
    
    fill(Ok.HSL(i*(100/res)));                           //Grayscale. Calculates Lightness because the function being used is Ok.HSL.
    rect((i-1)*(width/res),(height/4)*3,width/res,height/4);
  }
}
