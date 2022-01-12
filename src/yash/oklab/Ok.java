package yash.oklab;

import processing.core.*;

/**
 * Oklab for Processing.
 * 
 * Translated (with added functions) by Yash Gupta to
 * Processing code, from the file 'colorconversion.js'
 * on GitHub by Björn Ottosson.
 */

//Functions created for use in Processing:
//(H stands for Hue, S for Saturation, V for value, and L for Lightness.)
//(p defines the parent PApplet.)
//(H accepts values from 0 to 360.)
//(S, V, L, and A accept values from 0 to 100.)

//Ok.HSV
//Accepts (H,S,V) , (H,S,V,A) , (V) , (V,A) ; returns color.

//Ok.HSL
//Accepts (H,S,L) , (H,S,L,A) , (L) , (L,A) ; returns color.

//Ok.getHSV
//Accepts color; returns a float[] array containing {H,S,V,A}.

//Ok.getHSL
//Accepts color; returns a float[] array containing {H,S,L,A}.

//Ok.getH
//Accepts color; returns float.

//Ok.getS
//Accepts color; returns float.

//Ok.getV
//Accepts color; returns float.

//Ok.getL
//Accepts color; returns float.

//About the alpha channel:
//I didn't create a function to get alpha values, because it's meaningless to
//do so. Alpha values are not processed by OkLab functions; only remapped
//from 0-255 to 0-100 for consistency with other channels.

//Creating an OkLab function to do just this could be misleading.

//You can extract alpha values from array indices of functions which return
//alpha values (see above). You can also grab alpha values using Processing's
//alpha() function. You can then remap it to (0,100) be consistent with these
//functions' values, either using Processing's map() function, or with this
//formula: round((alpha/n)*100) where 'n' is the max value for the alpha
//channel in your Processing sketch (255 by default).

public class Ok {
	
	//p is a reference to the parent sketch.
	public static PApplet p;
	
	/**
	 * a Constructor.
	 * 
	 * @param q the parent PApplet
	 */
	public Ok(PApplet q) {
		p = q;
	}
	
	/**
	 * returns OkHSV color.
	 * 
	 * @param H hue between 0 and 360.
	 * @param S saturation between 0 and 100.
	 * @param V value between 0 and 100.
	 * @return int
	 */
	public static int HSV(float H, float S, float V) // 360,100,100
	{
		int mode = p.g.colorMode;
		float modeX = p.g.colorModeX;
		float modeY = p.g.colorModeY;
		float modeZ = p.g.colorModeZ;
		p.colorMode(PConstants.RGB, 255, 255, 255, 255);
		int returned = 0;
		float[] srgbTemp = okhsv_to_srgb(H / 360, S / 100, V / 100);
		if (p.g.colorMode == PConstants.RGB || p.g.colorMode == PConstants.ARGB) {
			returned = p.color(PApplet.round(srgbTemp[0]), PApplet.round(srgbTemp[1]), PApplet.round(srgbTemp[2]));
		} else if (p.g.colorMode == PConstants.HSB) {
			srgbTemp = rgb_to_hsv(srgbTemp[0], srgbTemp[1], srgbTemp[2]);
			returned = p.color(srgbTemp[0], srgbTemp[1], srgbTemp[2]);
		}
		p.colorMode(mode, modeX, modeY, modeZ);
		return returned;
	}
	
	/**
	 * returns OkHSV color with custom alpha value.
	 * 
	 * @param H hue between 0 and 360.
	 * @param S saturation between 0 and 100.
	 * @param V value between 0 and 100.
	 * @param A alpha between 0 and 100.
	 * @return int
	 */
	public static int HSV(float H, float S, float V, float A) // 360,100,100,100
	{
		int mode = p.g.colorMode;
		float modeX = p.g.colorModeX;
		float modeY = p.g.colorModeY;
		float modeZ = p.g.colorModeZ;
		p.colorMode(PConstants.RGB, 255, 255, 255, 255);
		int returned = 0;
		float[] srgbTemp = okhsv_to_srgb(H / 360, S / 100, V / 100);
		float alpha = (A / 100) * 255;
		if (p.g.colorMode == PConstants.RGB || p.g.colorMode == PConstants.ARGB) {
			returned = p.color(PApplet.round(srgbTemp[0]), PApplet.round(srgbTemp[1]), PApplet.round(srgbTemp[2]),
					PApplet.round(alpha));
		} else if (p.g.colorMode == PConstants.HSB) {
			srgbTemp = rgb_to_hsv(srgbTemp[0], srgbTemp[1], srgbTemp[2]);
			returned = p.color(srgbTemp[0], srgbTemp[1], srgbTemp[2], alpha);
		}
		p.colorMode(mode, modeX, modeY, modeZ);
		return returned;
	}
	/**
	 * returns a grayscale OkHSV color.
	 * 
	 * @param V value between 0 and 100.
	 * @return int
	 */
	public static int HSV(float V) // 100
	{
		int mode = p.g.colorMode;
		float modeX = p.g.colorModeX;
		float modeY = p.g.colorModeY;
		float modeZ = p.g.colorModeZ;
		p.colorMode(PConstants.RGB, 255, 255, 255, 255);
		int returned = 0;
		float[] srgbTemp = okhsv_to_srgb(0, 0, V / 100);
		if (p.g.colorMode == PConstants.RGB || p.g.colorMode == PConstants.ARGB) {
			returned = p.color(PApplet.round(srgbTemp[0]), PApplet.round(srgbTemp[1]), PApplet.round(srgbTemp[2]));
		} else if (p.g.colorMode == PConstants.HSB) {
			srgbTemp = rgb_to_hsv(srgbTemp[0], srgbTemp[1], srgbTemp[2]);
			returned = p.color(srgbTemp[0], srgbTemp[1], srgbTemp[2]);
		}
		p.colorMode(mode, modeX, modeY, modeZ);
		return returned;
	}
	
	/**
	 * returns a grayscale OkHSV color with custom alpha value.
	 * 
	 * @param V value between 0 and 100.
	 * @param A alpha between 0 and 100.
	 * @return int
	 */
	public static int HSV(float V, float A) // 100,100
	{
		int mode = p.g.colorMode;
		float modeX = p.g.colorModeX;
		float modeY = p.g.colorModeY;
		float modeZ = p.g.colorModeZ;
		p.colorMode(PConstants.RGB, 255, 255, 255, 255);
		int returned = 0;
		float[] srgbTemp = okhsv_to_srgb(0, 0, V / 100);
		float alpha = (A / 100) * 255;
		if (p.g.colorMode == PConstants.RGB || p.g.colorMode == PConstants.ARGB) {
			returned = p.color(PApplet.round(srgbTemp[0]), PApplet.round(srgbTemp[1]), PApplet.round(srgbTemp[2]),
					PApplet.round(alpha));
		} else if (p.g.colorMode == PConstants.HSB) {
			srgbTemp = rgb_to_hsv(srgbTemp[0], srgbTemp[1], srgbTemp[2]);
			returned = p.color(srgbTemp[0], srgbTemp[1], srgbTemp[2], alpha);
		}
		p.colorMode(mode, modeX, modeY, modeZ);
		return returned;
	}

	/**
	 * returns OkHSL color.
	 * 
	 * @param H hue between 0 and 360.
	 * @param S saturation between 0 and 100.
	 * @param L value between 0 and 100.
	 * @return int
	 */
	public static int HSL(float H, float S, float L) // 360,100,100
	{
		int mode = p.g.colorMode;
		float modeX = p.g.colorModeX;
		float modeY = p.g.colorModeY;
		float modeZ = p.g.colorModeZ;
		p.colorMode(PConstants.RGB, 255, 255, 255, 255);
		int returned = 0;
		float[] srgbTemp = okhsl_to_srgb(H / 360, S / 100, L / 100);
		if (p.g.colorMode == PConstants.RGB || p.g.colorMode == PConstants.ARGB) {
			returned = p.color(PApplet.round(srgbTemp[0]), PApplet.round(srgbTemp[1]), PApplet.round(srgbTemp[2]));
		} else if (p.g.colorMode == PConstants.HSB) {
			srgbTemp = rgb_to_hsv(srgbTemp[0], srgbTemp[1], srgbTemp[2]);
			returned = p.color(srgbTemp[0], srgbTemp[1], srgbTemp[2]);
		}
		p.colorMode(mode, modeX, modeY, modeZ);
		return returned;
	}

	/**
	 * returns OkHSL color with custom alpha value.
	 * 
	 * @param H hue between 0 and 360.
	 * @param S saturation between 0 and 100.
	 * @param L value between 0 and 100.
	 * @param A alpha between 0 and 100.
	 * @return int
	 */
	public static int HSL(float H, float S, float L, float A) // 360,100,100,100
	{
		int mode = p.g.colorMode;
		float modeX = p.g.colorModeX;
		float modeY = p.g.colorModeY;
		float modeZ = p.g.colorModeZ;
		p.colorMode(PConstants.RGB, 255, 255, 255, 255);
		int returned = 0;
		float[] srgbTemp = okhsl_to_srgb(H / 360, S / 100, L / 100);
		float alpha = (A / 100) * 255;
		if (p.g.colorMode == PConstants.RGB || p.g.colorMode == PConstants.ARGB) {
			returned = p.color(PApplet.round(srgbTemp[0]), PApplet.round(srgbTemp[1]), PApplet.round(srgbTemp[2]),
					PApplet.round(alpha));
		} else if (p.g.colorMode == PConstants.HSB) {
			srgbTemp = rgb_to_hsv(srgbTemp[0], srgbTemp[1], srgbTemp[2]);
			returned = p.color(srgbTemp[0], srgbTemp[1], srgbTemp[2], alpha);
		}
		p.colorMode(mode, modeX, modeY, modeZ);
		return returned;
	}

	/**
	 * returns a grayscale OkHSL color.
	 * 
	 * @param L value between 0 and 100.
	 * @return int
	 */
	public static int HSL(float L) // 100
	{
		int mode = p.g.colorMode;
		float modeX = p.g.colorModeX;
		float modeY = p.g.colorModeY;
		float modeZ = p.g.colorModeZ;
		p.colorMode(PConstants.RGB, 255, 255, 255, 255);
		int returned = 0;
		float[] srgbTemp = okhsl_to_srgb(0, 0, L / 100);
		if (p.g.colorMode == PConstants.RGB || p.g.colorMode == PConstants.ARGB) {
			returned = p.color(PApplet.round(srgbTemp[0]), PApplet.round(srgbTemp[1]), PApplet.round(srgbTemp[2]));
		} else if (p.g.colorMode == PConstants.HSB) {
			srgbTemp = rgb_to_hsv(srgbTemp[0], srgbTemp[1], srgbTemp[2]);
			returned = p.color(srgbTemp[0], srgbTemp[1], srgbTemp[2]);
		}
		p.colorMode(mode, modeX, modeY, modeZ);
		return returned;
	}

	/**
	 * returns a grayscale OkHSL color with custom alpha value.
	 * 
	 * @param L value between 0 and 100.
	 * @param A alpha between 0 and 100.
	 * @return int
	 */
	public static int HSL(float L, float A) // 100,100
	{
		int mode = p.g.colorMode;
		float modeX = p.g.colorModeX;
		float modeY = p.g.colorModeY;
		float modeZ = p.g.colorModeZ;
		p.colorMode(PConstants.RGB, 255, 255, 255, 255);
		int returned = 0;
		float[] srgbTemp = okhsl_to_srgb(0, 0, L / 100);
		float alpha = (A / 100) * 255;
		if (p.g.colorMode == PConstants.RGB || p.g.colorMode == PConstants.ARGB) {
			returned = p.color(PApplet.round(srgbTemp[0]), PApplet.round(srgbTemp[1]), PApplet.round(srgbTemp[2]),
					PApplet.round(alpha));
		} else if (p.g.colorMode == PConstants.HSB) {
			srgbTemp = rgb_to_hsv(srgbTemp[0], srgbTemp[1], srgbTemp[2]);
			returned = p.color(srgbTemp[0], srgbTemp[1], srgbTemp[2], alpha);
		}
		p.colorMode(mode, modeX, modeY, modeZ);
		return returned;
	}

	/**
	 * returns an array containing OkHSV and alpha values of a color.
	 * 
	 * @param input color value.
	 * @return float[]
	 */
	public static float[] getHSV(int input) {
		int mode = p.g.colorMode;
		float modeX = p.g.colorModeX;
		float modeY = p.g.colorModeY;
		float modeZ = p.g.colorModeZ;
		p.colorMode(PConstants.RGB, 255, 255, 255, 255);
		float[] okhsvtemp = srgb_to_okhsv(p.red(input), p.green(input), p.blue(input));
		float alpha = p.alpha(input) / 255;
		float[] returned = { PApplet.round(okhsvtemp[0] * 360), PApplet.round(okhsvtemp[1] * 100),
				PApplet.round(okhsvtemp[2] * 100), PApplet.round(alpha * 100) };
		p.colorMode(mode, modeX, modeY, modeZ);
		return returned;
	}

	/**
	 * returns an array containing OkHSL and alpha values of a color.
	 * 
	 * @param input color value.
	 * @return float[]
	 */
	public static float[] getHSL(int input) {
		int mode = p.g.colorMode;
		float modeX = p.g.colorModeX;
		float modeY = p.g.colorModeY;
		float modeZ = p.g.colorModeZ;
		p.colorMode(PConstants.RGB, 255, 255, 255, 255);
		float[] okhsvtemp = srgb_to_okhsl(p.red(input), p.green(input), p.blue(input));
		float alpha = p.alpha(input) / 255;
		float[] returned = { PApplet.round(okhsvtemp[0] * 360), PApplet.round(okhsvtemp[1] * 100),
				PApplet.round(okhsvtemp[2] * 100), PApplet.round(alpha * 100) };
		p.colorMode(mode, modeX, modeY, modeZ);
		return returned;
	}

	/**
	 * returns the hue parameter of a color, relative to the OkHSV and OkHSL scales.
	 * 
	 * @param input color value.
	 * @return float
	 */
	public static float getH(int input) {
		int mode = p.g.colorMode;
		float modeX = p.g.colorModeX;
		float modeY = p.g.colorModeY;
		float modeZ = p.g.colorModeZ;
		p.colorMode(PConstants.RGB, 255, 255, 255, 255);
		float[] okhsvtemp = srgb_to_okhsv(p.red(input), p.green(input), p.blue(input));
		float returned = PApplet.round(okhsvtemp[0] * 360);
		p.colorMode(mode, modeX, modeY, modeZ);
		return returned;
	}

	/**
	 * returns the saturation parameter of a color, relative to the OkHSV and OkHSL scales.
	 * 
	 * @param input color value.
	 * @return float
	 */
	public static float getS(int input) {
		int mode = p.g.colorMode;
		float modeX = p.g.colorModeX;
		float modeY = p.g.colorModeY;
		float modeZ = p.g.colorModeZ;
		p.colorMode(PConstants.RGB, 255, 255, 255, 255);
		float[] okhsvtemp = srgb_to_okhsv(p.red(input), p.green(input), p.blue(input));
		float returned = PApplet.round(okhsvtemp[1] * 100);
		p.colorMode(mode, modeX, modeY, modeZ);
		return returned;
	}

	/**
	 * returns the value parameter of a color, relative to the OkHSV scale.
	 * 
	 * @param input color value.
	 * @return float
	 */
	public static float getV(int input) {
		int mode = p.g.colorMode;
		float modeX = p.g.colorModeX;
		float modeY = p.g.colorModeY;
		float modeZ = p.g.colorModeZ;
		p.colorMode(PConstants.RGB, 255, 255, 255, 255);
		float[] okhsvtemp = srgb_to_okhsv(p.red(input), p.green(input), p.blue(input));
		float returned = PApplet.round(okhsvtemp[2] * 100);
		p.colorMode(mode, modeX, modeY, modeZ);
		return returned;
	}

	/**
	 * returns the lightness parameter of a color, relative to the OkHSL scale.
	 * 
	 * @param input color value.
	 * @return float
	 */
	public static float getL(int input) {
		int mode = p.g.colorMode;
		float modeX = p.g.colorModeX;
		float modeY = p.g.colorModeY;
		float modeZ = p.g.colorModeZ;
		p.colorMode(PConstants.RGB, 255, 255, 255, 255);
		float[] okhsvtemp = srgb_to_okhsl(p.red(input), p.green(input), p.blue(input));
		float returned = PApplet.round(okhsvtemp[2] * 100);
		p.colorMode(mode, modeX, modeY, modeZ);
		return returned;
	}

	// All functions below were translated from Björn Ottosson's
	// colorconversion.js on GitHub.

	// Copyright (c) 2021 Björn Ottosson

	// Permission is hereby granted, free of charge, to any person obtaining a copy
	// of
	// this software and associated documentation files (the "Software"), to deal in
	// the Software without restriction, including without limitation the rights to
	// use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
	// of the Software, and to permit persons to whom the Software is furnished to
	// do
	// so, subject to the following conditions:
	// The above copyright notice and this permission notice shall be included in
	// all
	// copies or substantial portions of the Software.
	// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	// SOFTWARE.

//	private static float[] rgb_to_hsl(float r, float g, float b) {
//		r /= 255;
//		g /= 255;
//		b /= 255;
//
//		float max = Math.max(r, Math.max(g, b));
//		float min = Math.min(r, Math.min(g, b));
//		float h = 0.0f, s = 0.0f;
//		float l = (max + min) / 2;
//
//		if (max == min) {
//			h = s = 0;
//		} else {
//			float d = max - min;
//			s = l > 0.5 ? d / (2 - max - min) : d / (max + min);
//			if (max == r) {
//				h = (g - b) / d + (g < b ? 6 : 0);
//			} else if (max == g) {
//				h = (b - r) / d + 2;
//			} else if (max == b) {
//				h = (r - g) / d + 4;
//			}
//			h /= 6;
//		}
//
//		float[] returned = { h, s, l };
//
//		return returned;
//	}

//	private static float[] hsl_to_rgb(float h, float s, float l) {
//		float r, g, b;
//
//		if (s == 0) {
//			r = g = b = l;
//		} else {
//			float q = l < 0.5 ? l * (1 + s) : l + s - l * s;
//			float p = 2 * l - q;
//			r = hue_to_rgb(p, q, h + 1 / 3);
//			g = hue_to_rgb(p, q, h);
//			b = hue_to_rgb(p, q, h - 1 / 3);
//		}
//
//		float[] returned = { r * 255, g * 255, b * 255 };
//
//		return returned;
//	}

//	private static float hue_to_rgb(float p, float q, float t) {
//		if (t < 0) {
//			t += 1;
//		}
//		if (t > 1) {
//			t -= 1;
//		}
//		if (t < 1 / 6) {
//			return p + (q - p) * 6 * t;
//		}
//		if (t < 1 / 2) {
//			return q;
//		}
//		if (t < 2 / 3) {
//			return p + (q - p) * (2 / 3 - t) * 6;
//		}
//		return p;
//	}

	private static float[] rgb_to_hsv(float r, float g, float b) {
		r = r / 255;
		g = g / 255;
		b = b / 255;

		float max = Math.max(r, Math.max(g, b));
		float min = Math.min(r, Math.max(g, b));
		float h = 0.0f, s = 0.0f;
		float v = max;

		float d = max - min;
		s = max == 0 ? 0 : d / max;

		if (max == min) {
			h = 0; // achromatic
		} else {
			if (max == r) {
				h = (g - b) / d + (g < b ? 6 : 0);
			} else if (max == g) {
				h = (b - r) / d + 2;
			} else if (max == g) {
				h = (r - g) / d + 4;
			}
			h /= 6;
		}

		float[] returned = { h, s, v };

		return returned;
	}

//	private static float[] hsv_to_rgb(float h, float s, float v) {
//		float r = 0, g = 0, b = 0;
//
//		float i = PApplet.floor(h * 6);
//		float f = h * 6 - i;
//		float p = v * (1 - s);
//		float q = v * (1 - f * s);
//		float t = v * (1 - (1 - f) * s);
//
//		if (i % 6 == 0) {
//			r = v;
//			g = t;
//			b = p;
//		} else if (i % 6 == 1) {
//			r = q;
//			g = v;
//			b = p;
//		} else if (i % 6 == 2) {
//			r = p;
//			g = v;
//			b = t;
//		} else if (i % 6 == 3) {
//			r = p;
//			g = q;
//			b = v;
//		} else if (i % 6 == 4) {
//			r = t;
//			g = p;
//			b = v;
//		} else if (i % 6 == 5) {
//			r = v;
//			g = p;
//			b = q;
//		}
//
//		float[] returned = { r * 255, g * 255, b * 255 };
//
//		return returned;
//	}

	private static float srgb_transfer_function(float a) {
		return (float) (.0031308 >= a ? 12.92 * a : 1.055 * Math.pow(a, .4166666666666667) - .055);
	}

	private static float srgb_transfer_function_inv(float a) {
		return (float) (.04045 < a ? Math.pow((a + .055) / 1.055, 2.4) : a / 12.92);
	}

	private static float[] linear_srgb_to_oklab(float r, float g, float b) {
		float l = 0.4122214708f * r + 0.5363325363f * g + 0.0514459929f * b;
		float m = 0.2119034982f * r + 0.6806995451f * g + 0.1073969566f * b;
		float s = 0.0883024619f * r + 0.2817188376f * g + 0.6299787005f * b;

		float l_ = (float) (Math.cbrt(l));
		float m_ = (float) (Math.cbrt(m));
		float s_ = (float) (Math.cbrt(s));

		float[] returned = { 0.2104542553f * ((float) l_) + 0.7936177850f * ((float) m_) - 0.0040720468f * ((float) s_),
				1.9779984951f * ((float) l_) - 2.4285922050f * ((float) m_) + 0.4505937099f * ((float) s_),
				0.0259040371f * ((float) l_) + 0.7827717662f * ((float) m_) - 0.8086757660f * ((float) s_) };

		return returned;
	}

	private static float[] oklab_to_linear_srgb(float L, float a, float b) {

		float l_ = L + 0.3963377774f * a + 0.2158037573f * b;
		float m_ = L - 0.1055613458f * a - 0.0638541728f * b;
		float s_ = L - 0.0894841775f * a - 1.2914855480f * b;

		float l = l_ * l_ * l_;
		float m = m_ * m_ * m_;
		float s = s_ * s_ * s_;

		float[] returned = { (+4.0767416621f * l - 3.3077115913f * m + 0.2309699292f * s),
				(-1.2684380046f * l + 2.6097574011f * m - 0.3413193965f * s),
				(-0.0041960863f * l - 0.7034186147f * m + 1.7076147010f * s) };

		return returned;
	}

	private static float toe(float x) {
		final float k_1 = 0.206f;
		final float k_2 = 0.03f;
		final float k_3 = (1 + k_1) / (1 + k_2);

		return (float) (0.5 * (k_3 * x - k_1 + Math.sqrt((k_3 * x - k_1) * (k_3 * x - k_1) + 4 * k_2 * k_3 * x)));
	}

	private static float toe_inv(float x) {
		final float k_1 = 0.206f;
		final float k_2 = 0.03f;
		final float k_3 = (1 + k_1) / (1 + k_2);
		return (x * x + k_1 * x) / (k_3 * (x + k_2));
	}

	// Finds the maximum saturation possible for a given hue that fits in sRGB
	// Saturation here is defined as S = C/L
	// a and b must be normalized so a^2 + b^2 == 1
	private static float compute_max_saturation(float a, float b) {
		// Max saturation will be when one of r, g or b goes below zero.

		// Select different coefficients depending on which component goes below zero
		// first
		float k0, k1, k2, k3, k4, wl, wm, ws;

		if (-1.88170328 * a - 0.80936493 * b > 1) {
			// Red component
			k0 = +1.19086277f;
			k1 = +1.76576728f;
			k2 = +0.59662641f;
			k3 = +0.75515197f;
			k4 = +0.56771245f;
			wl = +4.0767416621f;
			wm = -3.3077115913f;
			ws = +0.2309699292f;
		} else if (1.81444104 * a - 1.19445276 * b > 1) {
			// Green component
			k0 = +0.73956515f;
			k1 = -0.45954404f;
			k2 = +0.08285427f;
			k3 = +0.12541070f;
			k4 = +0.14503204f;
			wl = -1.2684380046f;
			wm = +2.6097574011f;
			ws = -0.3413193965f;
		} else {
			// Blue component
			k0 = +1.35733652f;
			k1 = -0.00915799f;
			k2 = -1.15130210f;
			k3 = -0.50559606f;
			k4 = +0.00692167f;
			wl = -0.0041960863f;
			wm = -0.7034186147f;
			ws = +1.7076147010f;
		}

		// Approximate max saturation using a polynomial:
		float S = k0 + k1 * a + k2 * b + k3 * a * a + k4 * a * b;

		// Do one step Halley's method to get closer
		// this gives an error less than 10e6, except for some blue hues where the dS/dh
		// is close to infinite
		// this should be sufficient for most applications, otherwise do two/three steps

		float k_l = +0.3963377774f * a + 0.2158037573f * b;
		float k_m = -0.1055613458f * a - 0.0638541728f * b;
		float k_s = -0.0894841775f * a - 1.2914855480f * b;

		{
			float l_ = 1 + S * k_l;
			float m_ = 1 + S * k_m;
			float s_ = 1 + S * k_s;

			float l = l_ * l_ * l_;
			float m = m_ * m_ * m_;
			float s = s_ * s_ * s_;

			float l_dS = 3 * k_l * l_ * l_;
			float m_dS = 3 * k_m * m_ * m_;
			float s_dS = 3 * k_s * s_ * s_;

			float l_dS2 = 6 * k_l * k_l * l_;
			float m_dS2 = 6 * k_m * k_m * m_;
			float s_dS2 = 6 * k_s * k_s * s_;

			float f = wl * l + wm * m + ws * s;
			float f1 = wl * l_dS + wm * m_dS + ws * s_dS;
			float f2 = wl * l_dS2 + wm * m_dS2 + ws * s_dS2;

			S = S - f * f1 / (f1 * f1 - 0.5f * f * f2);
		}

		return S;
	}

	private static float[] find_cusp(float a, float b) {
		// First, find the maximum saturation (saturation S = C/L)
		float S_cusp = compute_max_saturation(a, b);

		// Convert to linear sRGB to find the first point where at least one of r,g or b
		// >= 1:
		float[] rgb_at_max = oklab_to_linear_srgb(1, S_cusp * a, S_cusp * b);
		float L_cusp = (float) (Math.cbrt(1 / Math.max(Math.max(rgb_at_max[0], rgb_at_max[1]), rgb_at_max[2])));
		float C_cusp = L_cusp * S_cusp;

		float[] returned = { L_cusp, C_cusp };

		return returned;
	}

	// Finds intersection of the line defined by
	// L = L0 * (1 - t) + t * L1;
	// C = t * C1;
	// a and b must be normalized so a^2 + b^2 == 1
	private static float find_gamut_intersection(float a, float b, float L1, float C1, float L0, float[] cusp) {
		if (cusp == null) {
			// Find the cusp of the gamut triangle
			cusp = find_cusp(a, b);
		}

		// Find the intersection for upper and lower half seprately
		float t;
		if (((L1 - L0) * cusp[1] - (cusp[0] - L0) * C1) <= 0) {
			// Lower half

			t = cusp[1] * L0 / (C1 * cusp[0] + cusp[1] * (L0 - L1));
		} else {
			// Upper half

			// First intersect with triangle
			t = cusp[1] * (L0 - 1) / (C1 * (cusp[0] - 1) + cusp[1] * (L0 - L1));

			// Then one step Halley's method
			{
				float dL = L1 - L0;
				float dC = C1;

				float k_l = +0.3963377774f * a + 0.2158037573f * b;
				float k_m = -0.1055613458f * a - 0.0638541728f * b;
				float k_s = -0.0894841775f * a - 1.2914855480f * b;

				float l_dt = dL + dC * k_l;
				float m_dt = dL + dC * k_m;
				float s_dt = dL + dC * k_s;

				// If higher accuracy is required, 2 or 3 iterations of the following block can
				// be used:
				{
					float L = L0 * (1 - t) + t * L1;
					float C = t * C1;

					float l_ = L + C * k_l;
					float m_ = L + C * k_m;
					float s_ = L + C * k_s;

					float l = l_ * l_ * l_;
					float m = m_ * m_ * m_;
					float s = s_ * s_ * s_;

					float ldt = 3 * l_dt * l_ * l_;
					float mdt = 3 * m_dt * m_ * m_;
					float sdt = 3 * s_dt * s_ * s_;

					float ldt2 = 6 * l_dt * l_dt * l_;
					float mdt2 = 6 * m_dt * m_dt * m_;
					float sdt2 = 6 * s_dt * s_dt * s_;

					float r = 4.0767416621f * l - 3.3077115913f * m + 0.2309699292f * s - 1;
					float r1 = 4.0767416621f * ldt - 3.3077115913f * mdt + 0.2309699292f * sdt;
					float r2 = 4.0767416621f * ldt2 - 3.3077115913f * mdt2 + 0.2309699292f * sdt2;

					float u_r = r1 / (r1 * r1 - 0.5f * r * r2);
					float t_r = -r * u_r;

					float g = -1.2684380046f * l + 2.6097574011f * m - 0.3413193965f * s - 1;
					float g1 = -1.2684380046f * ldt + 2.6097574011f * mdt - 0.3413193965f * sdt;
					float g2 = -1.2684380046f * ldt2 + 2.6097574011f * mdt2 - 0.3413193965f * sdt2;

					float u_g = g1 / (g1 * g1 - 0.5f * g * g2);
					float t_g = -g * u_g;

					b = -0.0041960863f * l - 0.7034186147f * m + 1.7076147010f * s - 1;
					float b1 = -0.0041960863f * ldt - 0.7034186147f * mdt + 1.7076147010f * sdt;
					float b2 = -0.0041960863f * ldt2 - 0.7034186147f * mdt2 + 1.7076147010f * sdt2;

					float u_b = b1 / (b1 * b1 - 0.5f * b * b2);
					float t_b = -b * u_b;

					t_r = u_r >= 0 ? t_r : 10e5f;
					t_g = u_g >= 0 ? t_g : 10e5f;
					t_b = u_b >= 0 ? t_b : 10e5f;

					t += Math.min(t_r, Math.min(t_g, t_b));
				}
			}
		}

		return t;
	}

	private static float[] get_ST_max(float a_, float b_, float[] cusp) {
		if (cusp == null) {
			cusp = find_cusp(a_, b_);
		}

		float L = cusp[0];
		float C = cusp[1];

		float[] returned = { C / L, C / (1 - L) };
		return returned;
	}

//	private static float[] get_ST_mid(float a_, float b_) {
//		float S = 0.11516993f + 1 / (+7.44778970f + 4.15901240f * b_ + a_ * (-2.19557347f + 1.75198401f * b_
//				+ a_ * (-2.13704948f - 10.02301043f * b_ + a_ * (-4.24894561f + 5.38770819f * b_ + 4.69891013f * a_))));
//
//		float T = 0.11239642f + 1 / (+1.61320320f - 0.68124379f * b_ + a_ * (+0.40370612f + 0.90148123f * b_
//				+ a_ * (-0.27087943f + 0.61223990f * b_ + a_ * (+0.00299215f - 0.45399568f * b_ - 0.14661872f * a_))));
//
//		float[] returned = { S, T };
//
//		return returned;
//	}

	private static float[] get_Cs(float L, float a_, float b_) {
		float[] cusp = find_cusp(a_, b_);

		float C_max = find_gamut_intersection(a_, b_, L, 1, L, cusp);
		float[] ST_max = get_ST_max(a_, b_, cusp);

		float S_mid = 0.11516993f + 1 / (+7.44778970f + 4.15901240f * b_ + a_ * (-2.19557347f + 1.75198401f * b_
				+ a_ * (-2.13704948f - 10.02301043f * b_ + a_ * (-4.24894561f + 5.38770819f * b_ + 4.69891013f * a_))));

		float T_mid = 0.11239642f + 1 / (+1.61320320f - 0.68124379f * b_ + a_ * (+0.40370612f + 0.90148123f * b_
				+ a_ * (-0.27087943f + 0.61223990f * b_ + a_ * (+0.00299215f - 0.45399568f * b_ - 0.14661872f * a_))));

		float k = C_max / Math.min((L * ST_max[0]), (1 - L) * ST_max[1]);

		float C_mid;
		{
			float C_a = L * S_mid;
			float C_b = (1 - L) * T_mid;

			C_mid = (float) (0.9 * k
					* Math.sqrt(Math.sqrt(1 / (1 / (C_a * C_a * C_a * C_a) + 1 / (C_b * C_b * C_b * C_b)))));
		}

		float C_0;
		{
			float C_a = L * 0.4f;
			float C_b = (1 - L) * 0.8f;

			C_0 = (float) (Math.sqrt(1 / (1 / (C_a * C_a) + 1 / (C_b * C_b))));
		}

		float[] returned = { C_0, C_mid, C_max };

		return returned;
	}

	private static float[] okhsl_to_srgb(float h, float s, float l) {
		if (l == 1) {
			float[] returned = { 255, 255, 255 };
			return returned;
		} else if (l == 0) {
			float[] returned = { 0, 0, 0 };
			return returned;
		}

		float a_ = (float) (Math.cos(2 * Math.PI * h));
		float b_ = (float) (Math.sin(2 * Math.PI * h));
		float L = toe_inv(l);

		float[] Cs = get_Cs(L, a_, b_);
		float C_0 = Cs[0];
		float C_mid = Cs[1];
		float C_max = Cs[2];

		float C, t, k_0, k_1, k_2;
		if (s < 0.8) {
			t = 1.25f * s;
			k_0 = 0;
			k_1 = 0.8f * C_0;
			k_2 = (1 - k_1 / C_mid);
		} else {
			t = 5 * (s - 0.8f);
			k_0 = C_mid;
			k_1 = 0.2f * C_mid * C_mid * 1.25f * 1.25f / C_0;
			k_2 = (1 - (k_1) / (C_max - C_mid));
		}

		C = k_0 + t * k_1 / (1 - k_2 * t);

		// If we would only use one of the Cs:
		// C = s*C_0;
		// C = s*1.25*C_mid;
		// C = s*C_max;

		float[] rgb = oklab_to_linear_srgb(L, C * a_, C * b_);
		float[] returned = { 255 * srgb_transfer_function(rgb[0]), 255 * srgb_transfer_function(rgb[1]),
				255 * srgb_transfer_function(rgb[2]) };

		return returned;
	}

	private static float[] srgb_to_okhsl(float r, float g, float b) {
		float[] lab = linear_srgb_to_oklab(srgb_transfer_function_inv(r / 255), srgb_transfer_function_inv(g / 255),
				srgb_transfer_function_inv(b / 255));

		float C = (float) (Math.sqrt(lab[1] * lab[1] + lab[2] * lab[2]));
		float a_ = lab[1] / C;
		float b_ = lab[2] / C;

		float L = lab[0];
		float h = (float) (0.5 + 0.5 * Math.atan2(-lab[2], -lab[1]) / Math.PI);

		float[] Cs = get_Cs(L, a_, b_);
		float C_0 = Cs[0];
		float C_mid = Cs[1];
		float C_max = Cs[2];

		float s;
		if (C < C_mid) {
			float k_0 = 0;
			float k_1 = 0.8f * C_0;
			float k_2 = (1 - k_1 / C_mid);

			float t = (C - k_0) / (k_1 + k_2 * (C - k_0));
			s = t * 0.8f;
		} else {
			float k_0 = C_mid;
			float k_1 = 0.2f * C_mid * C_mid * 1.25f * 1.25f / C_0;
			float k_2 = (1 - (k_1) / (C_max - C_mid));

			float t = (C - k_0) / (k_1 + k_2 * (C - k_0));
			s = 0.8f + 0.2f * t;
		}

		float l = toe(L);

		float[] returned = { h, s, l };

		return returned;
	}

	private static float[] okhsv_to_srgb(float h, float s, float v) {
		float a_ = (float) (Math.cos(2 * Math.PI * h));
		float b_ = (float) (Math.sin(2 * Math.PI * h));

		float[] ST_max = get_ST_max(a_, b_, null);
		float S_max = ST_max[0];
		float S_0 = 0.5f;
		float T = ST_max[1];
		float k = 1 - S_0 / S_max;

		float L_v = 1 - s * S_0 / (S_0 + T - T * k * s);
		float C_v = s * T * S_0 / (S_0 + T - T * k * s);

		float L = v * L_v;
		float C = v * C_v;

		// to present steps along the way
		// L = v;
		// C = v*s*S_max;
		// L = v*(1 - s*S_max/(S_max+T));
		// C = v*s*S_max*T/(S_max+T);

		float L_vt = toe_inv(L_v);
		float C_vt = C_v * L_vt / L_v;

		float L_new = toe_inv(L); // * L_v/L_vt;
		C = C * L_new / L;
		L = L_new;

		float[] rgb_scale = oklab_to_linear_srgb(L_vt, a_ * C_vt, b_ * C_vt);
		float scale_L = (float) (Math
				.cbrt(1 / (Math.max(rgb_scale[0], Math.max(rgb_scale[1], Math.max(rgb_scale[2], 0))))));

		// remove to see effect without rescaling
		L = L * scale_L;
		C = C * scale_L;

		float[] rgb = oklab_to_linear_srgb(L, C * a_, C * b_);
		float[] returned = { 255 * srgb_transfer_function(rgb[0]), 255 * srgb_transfer_function(rgb[1]),
				255 * srgb_transfer_function(rgb[2]) };

		return returned;
	}

	private static float[] srgb_to_okhsv(float r, float g, float b) {
		float[] lab = linear_srgb_to_oklab(srgb_transfer_function_inv(r / 255), srgb_transfer_function_inv(g / 255),
				srgb_transfer_function_inv(b / 255));

		float C = (float) (Math.sqrt(lab[1] * lab[1] + lab[2] * lab[2]));
		float a_ = lab[1] / C;
		float b_ = lab[2] / C;

		float L = lab[0];
		float h = (float) (0.5 + 0.5 * Math.atan2(-lab[2], -lab[1]) / Math.PI);

		float[] ST_max = get_ST_max(a_, b_, null);
		float S_max = ST_max[0];
		float S_0 = 0.5f;
		float T = ST_max[1];
		float k = 1 - S_0 / S_max;

		float t = T / (C + L * T);
		float L_v = t * L;
		float C_v = t * C;

		float L_vt = toe_inv(L_v);
		float C_vt = C_v * L_vt / L_v;

		float[] rgb_scale = oklab_to_linear_srgb(L_vt, a_ * C_vt, b_ * C_vt);
		float scale_L = (float) (Math
				.cbrt(1 / (Math.max(rgb_scale[0], Math.max(rgb_scale[1], Math.max(rgb_scale[2], 0))))));

		L = L / scale_L;
		C = C / scale_L;

		C = C * toe(L) / L;
		L = toe(L);

		float v = L / L_v;
		float s = (S_0 + T) * C_v / ((T * S_0) + T * k * C_v);

		float[] returned = { h, s, v };

		return returned;
	}
}