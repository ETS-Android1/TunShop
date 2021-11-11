package com.scan;

import java.util.EnumMap;
import java.util.Map;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

//Classe qui pemet de génerer une image à partir du code à barrre
public class GenerateBarcode {
	private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;
	
	public void GetImageBarcode(Context c,LinearLayout lin,String barcode_data)
	{
		String s=barcode_data;
		if (lin.getChildCount() > 0){lin.removeAllViews();}
		LinearLayout l=lin;
	    // barcode image
	    Bitmap bitmap = null;
	    ImageView iv = new ImageView(c);
	    try {
	    	
	    	if(s.length()== 13)   bitmap =  encodeAsBitmap(barcode_data, BarcodeFormat.EAN_13,500,200);
	    	else if(s.length()== 8) bitmap = encodeAsBitmap(barcode_data, BarcodeFormat.EAN_8,500,200);
	    	else if(s.length()>13)  bitmap = encodeAsBitmap(barcode_data, BarcodeFormat.CODE_39,1000,200);	    	
	        iv.setImageBitmap(bitmap);

	    } catch (WriterException e) {Toast.makeText(c,e.getMessage(), Toast.LENGTH_LONG).show(); }

	    l.addView(iv);//ajouter l'image dans le layout
	    //barcode text
	    TextView tv = new TextView(c);
	    tv.setTextColor(Color.parseColor("#000000"));
	    tv.setGravity(Gravity.CENTER_HORIZONTAL);  
	    tv.setText(s);
	    tv.setTextSize(25);
	    l.addView(tv);
	    
	}
	 Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
		    String contentsToEncode = contents;
		    if (contentsToEncode == null) {
		        return null;
		    }
		    Map<EncodeHintType, Object> hints = null;
		    String encoding = guessAppropriateEncoding(contentsToEncode);
		    if (encoding != null) {
		        hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
		        hints.put(EncodeHintType.CHARACTER_SET, encoding);
		    }
		    MultiFormatWriter writer = new MultiFormatWriter();
		    BitMatrix result;
		    try {
		        result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
		    } catch (IllegalArgumentException iae) {
		        // Unsupported format
		        return null;
		    }
		    int width = result.getWidth();
		    int height = result.getHeight();
		    int[] pixels = new int[width * height];
		    for (int y = 0; y < height; y++) {
		        int offset = y * width;
		        for (int x = 0; x < width; x++) {
		        pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
		        }
		    }

		    Bitmap bitmap = Bitmap.createBitmap(width, height,
		        Bitmap.Config.ARGB_8888);
		    bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		    return bitmap;
		    }

		    private static String guessAppropriateEncoding(CharSequence contents) {
		    // Very crude at the moment
		    for (int i = 0; i < contents.length(); i++) {
		        if (contents.charAt(i) > 0xFF) {
		        return "UTF-8";
		        }
		    }
		    return null;
		    }


}
