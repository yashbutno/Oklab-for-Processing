import yash.oklab.*;                          //Import the library.

void setup()
{
  size(256,256);
  
  Ok.p = this;                                //Assign the value 'this' to the PApplet-type variable 'p', to help the library grab data (such as the current colorMode) from the current sketch.
  
  println(hex(Ok.HSV(60,100,100)));           //Print an OkHSV value in hexadecimal.
  println(Ok.getH(Ok.HSV(60,100,100)));       //Print the hue (OkLab) value calculated from a color.
  println(Ok.getHSV(#FF9000));                //Print the OkHSV and alpha values calculated from a color, returned as a float[] array.
}

void draw()
{
  background(Ok.HSL(15,100,70));
}
