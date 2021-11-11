package com.scan;

import com.menu.R;
import com.util.Constants;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

/*
 * Classe qui permet d'ouvrir le scanner 
 * , chaque classe qui utilise la fn de scan doit hériter de cette classe
 */
public class CallsScanner extends Activity
{

	protected Dialog d=null;
	 private static final int ZBAR_SCANNER_REQUEST = 0;
	 private static final int ZBAR_QR_SCANNER_REQUEST = 1;
	   @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
 }
	 
	   public void launchScanner(Dialog d) {
		   this.d=d;
	        if (isCameraAvailable()) {
	            Intent intent = new Intent(this, ZBarScannerActivity.class);
	            startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
	        } else {
	            Toast.makeText(this, "Camera non disponible", Toast.LENGTH_SHORT).show();
	        }
	    }
	   public boolean isCameraAvailable() 
	    {
	        PackageManager pm = getPackageManager();
	        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
	    }

	   //Obtenir le résultat du scan 
	   @Override
	    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        switch (requestCode) {
	            case ZBAR_SCANNER_REQUEST:
	            case ZBAR_QR_SCANNER_REQUEST:
	                if (resultCode == RESULT_OK) {
	                	EditText res;
	               if( d==null) res=(EditText) findViewById(R.id.res);
	               else  res=(EditText) d.findViewById(R.id.res);
	                res.setText(data.getStringExtra(Constants.SCAN_RESULT));
	                } else if(resultCode == RESULT_CANCELED && data != null) {
	                    String error = data.getStringExtra(Constants.ERROR_INFO);
	                    if(!TextUtils.isEmpty(error)) {
	                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
	                    }
	                }
	                break;
	        }
	    }
	

}
