package com.scan;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

import com.menu.R;
import com.util.Constants;

public class ZBarScannerActivity extends Activity implements Camera.PreviewCallback {

    private CameraPreview mPreview;
    private Camera mCamera;
    private ImageScanner mScanner;
    private Handler mAutoFocusHandler;
    private boolean mPreviewing = true;
    LayoutInflater controlInflater = null;
    int a,b ;
    int pos=0;
    ArrayList<String> conseils=new ArrayList<String>();
    boolean on=false;
    static {
        System.loadLibrary("iconv");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        if(!isCameraAvailable()) {
            // Cancel request if there is no rear-facing camera.
            cancelRequest();
            return;
        }
            // Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAutoFocusHandler = new Handler();
        // Create and configure the ImageScanner;
        setupScanner();
        initConseils();
        // Create a RelativeLayout container that will hold a SurfaceView,
        // and set it as the content of our activity.
        mPreview = new CameraPreview(this, this, autoFocusCB);
        setContentView(mPreview);
        controlInflater = LayoutInflater.from(getBaseContext());
        View viewControl = controlInflater.inflate(R.layout.capture, null);
        ouvrirRecommandations();
        
        @SuppressWarnings("deprecation")
		LayoutParams layoutParamsControl= new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
        this.addContentView(viewControl, layoutParamsControl);     
    }

    public void setupScanner() {
        mScanner = new ImageScanner();
        mScanner.setConfig(0, Config.X_DENSITY, 3);
        mScanner.setConfig(0, Config.Y_DENSITY, 3);

        int[] symbols = getIntent().getIntArrayExtra(Constants.SCAN_MODES);
        if (symbols != null) {
            mScanner.setConfig(Symbol.NONE, Config.ENABLE, 0);
            for (int symbol : symbols) {
                mScanner.setConfig(symbol, Config.ENABLE, 1);
            }
        }
    }
    public void initConseils()
    {
    	conseils.add("Pour scanner un produit ou une carte , veuillez mettre le code à barre à " +
        		"l'interieur du rectangle ");
    	conseils.add("Gardez le code à barre à une distance au moins de 7cm et pas plus que 12 cm ");
    	conseils.add("Evitez les reflets et les ombres ." +
    			"Pour activer/désactiver la lampe utilisez le bouton à droite");
    }
    public void ouvrirRecommandations()
    {
        final Dialog d= new Dialog(this);
        d.setCancelable(true);
        d.setContentView(R.layout.conseil_scan);
        d.setTitle("Recommandations");
       final Button b = (Button) d.findViewById(R.id.f);
        final TextView v=(TextView)d.findViewById(R.id.text);
        v.setText(conseils.get(0));
		b.setOnClickListener(new View.OnClickListener() {
			public void onClick(View vp) 
			{
				pos++;
				if(pos<=conseils.size()-2)v.setText(conseils.get(pos));					
				else if (pos==conseils.size()-1)
					{
					v.setText(conseils.get(pos));
					b.setText("Fermer");								
					}
				else d.dismiss();	
			}			
    });
		Window window = d.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.alpha = 0.9f;
		window.setAttributes(lp);
		d.show();	
    }
    public void fermer(View v)
    {
    	finish();
    }
    //gérer le bouton de flush
    public void flush(View v)
    {
    
    	    Parameters params = mCamera.getParameters();
    	   if( !on)
    	   {
    		   params.setFlashMode(Parameters.FLASH_MODE_TORCH);
    		   on=true;
    	   }
    	   else
    	   {  
    		   params.setFlashMode(Parameters.FLASH_MODE_OFF);
    		   on=false;
    	   }    
    	    mCamera.setParameters(params);
    	    mCamera.startPreview();
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Open the default i.e. the first rear facing camera.
        mCamera = Camera.open();
        if(mCamera == null) {
            // Cancel request if mCamera is null.
            cancelRequest();
            return;
        }

        mPreview.setCamera(mCamera);
        mPreview.showSurfaceView();

        mPreviewing = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCamera != null) {
            mPreview.setCamera(null);
            mCamera.cancelAutoFocus();
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mPreview.hideSurfaceView();

            mPreviewing = false;
            mCamera = null;
        }
    }

    public boolean isCameraAvailable() {
        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public void cancelRequest() {
        Intent dataIntent = new Intent();
        dataIntent.putExtra(Constants.ERROR_INFO, "Camera unavailable");
        setResult(Activity.RESULT_CANCELED, dataIntent);
        finish();
    }

    public void onPreviewFrame(byte[] data, Camera camera) {
    	a= (int) System.currentTimeMillis();
    	Camera.Parameters parameters = camera.getParameters();
        Camera.Size size = parameters.getPreviewSize();
        Image barcode = new Image(size.width, size.height, "Y800");
        barcode.setData(data);

        int result = mScanner.scanImage(barcode);

        if (result != 0) {
            mCamera.cancelAutoFocus();
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mPreviewing = false;
            SymbolSet syms = mScanner.getResults();
            for (Symbol sym : syms) {
                String symData = sym.getData();
                if (!TextUtils.isEmpty(symData)) {
                	final MediaPlayer mp = MediaPlayer.create(this, R.raw.beep);
                     mp.start();
                    Intent dataIntent = new Intent();
                    dataIntent.putExtra(Constants.SCAN_RESULT, symData);
                    dataIntent.putExtra(Constants.SCAN_RESULT_TYPE, sym.getType());
                    setResult(Activity.RESULT_OK, dataIntent);
                    b= (int) System.currentTimeMillis();
                    Log.e("Scan: temps de réponse",(b-a) +" ms" );
                    finish();
                    break;
                }
            }
        }
    }
    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if(mCamera != null && mPreviewing) {
                mCamera.autoFocus(autoFocusCB);
            }
        }
    };

    // Mimic continuous auto-focusing
    Camera.AutoFocusCallback autoFocusCB = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            mAutoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };
    @Override
    protected void onStop() {
    	
    	
        super.onStop(); 
    }
}
