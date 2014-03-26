package GUI;

import java.awt.Color;
import java.awt.Font;

import javax.swing.UIManager;

public  interface StyleSheet 
{
	
	//eventuell Fonts mit dem Projekt mitliefern und dann hier erstellen??

	
	//**************************  FONTS  **********************
	
	public static final String FontName_Text = "Arial";
	public static final String FontName_Headline = "Arial";
	
	//Headline Fonts - Plain
	/**Font: Header, 16, Plain*/
	public static Font FONT_H_16 = new Font(FontName_Headline, Font.PLAIN, 16);
	/**Font: Header, 14, Plain*/
	public static Font FONT_H_14 = new Font(FontName_Headline, Font.PLAIN, 14);
	/**Font: Header, 12, Plain*/
	public static Font FONT_H_12 = new Font(FontName_Headline, Font.PLAIN, 12);
	
	//Headline Fonts - Bold
	/**Font: Header, 16, Bold*/
	public static Font FONT_H_B_16 = new Font(FontName_Headline, Font.BOLD, 16);
	/**Font: Header, 14, Bold*/
	public static Font FONT_H_B_14 = new Font(FontName_Headline, Font.BOLD, 14);
	/**Font: Header, 12, Bold*/
	public static Font FONT_H_B_12 = new Font(FontName_Headline, Font.BOLD, 12);
	
	//Headline Fonts - Italic
	/**Font: Header, 16, Italic*/
	public static Font FONT_H_I_16 = new Font(FontName_Headline, Font.ITALIC, 16);
	/**Font: Header, 14, Italic*/
	public static Font FONT_H_I_14 = new Font(FontName_Headline, Font.ITALIC, 14);
	/**Font: Header, 12, Italic*/
	public static Font FONT_H_I_12 = new Font(FontName_Headline, Font.ITALIC, 12);
	
	
	//Standard Fonts - Plain
	/**Font: Standard, 16, Plain*/
	public static Font FONT_S_16 = new Font(FontName_Text, Font.PLAIN, 16);
	/**Font: Standard, 14, Plain*/
	public static Font FONT_S_14 = new Font(FontName_Text, Font.PLAIN, 14);
	/**Font: Standard, 12, Plain*/
	public static Font FONT_S_12 = new Font(FontName_Text, Font.PLAIN, 12);
	/**Font: Standard, 10, Plain*/
	public static Font FONT_S_10 = new Font(FontName_Text, Font.PLAIN, 10);
	/**Font: Standard, 8, Plain*/
	public static Font FONT_S_8 = new Font(FontName_Text, Font.PLAIN, 8);
	
	//Standard Fonts- Italic
	/**Font: Standard, 16, Italic*/
	public static Font FONT_S_I_16 = new Font(FontName_Text, Font.ITALIC, 16);
	/**Font: Standard, 14, Italic*/
	public static Font FONT_S_I_14 = new Font(FontName_Text, Font.ITALIC, 14);
	/**Font: Standard, 12, Italic*/
	public static Font FONT_S_I_12 = new Font(FontName_Text, Font.ITALIC, 12);
	
	//Standard Fonts- Bold
	/**Font: Standard, 16, Bold*/
	public static Font FONT_S_B_16 = new Font(FontName_Text, Font.BOLD, 16);
	/**Font: Standard, 14, Bold*/
	public static Font FONT_S_B_14 = new Font(FontName_Text, Font.BOLD, 14);
	/**Font: Standard, 12, Bold*/
	public static Font FONT_S_B_12 = new Font(FontName_Text, Font.BOLD, 12);
	
	
	//**************************  COLORS  **********************
	public static Color CORTEX_GREY = new Color(0x2d2d2d);
	//public static Color CORTEX_BLUE = new Color(0x353aff);
	public static Color CORTEX_BLUE = new Color(0x2E9AFE);
	public static Color CORTEX_WHITE = new Color(0xffffff);
	/**Das Blau für den Hintergrund des SchülerPopups*/
	public static Color CORTEX_POPUPBLUE = new Color(0x406fff);
	public static Color CORTEX_RED = new Color(0xFF6666);
	public static Color CORTEX_LIGHTGRAY= new Color(0xCFCFCF);
	public static Color CORTEX_LIGHTGREEN= new Color(0xA1EA96);
	
}
