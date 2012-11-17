package com.tonybeltramelli.mobile.lib.loader;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.tonybeltramelli.mobile.lib.event.LoaderEvent;

/**
 * 
 * @author Tony Beltramelli www.tonybeltramelli.com
 *
 */
public class ViewLoader extends AsyncTask<Void, Integer, Void> {
	
	private ProgressDialog _progressDialog;
	private Context _context;
	private boolean _useBar;
	private LoaderEvent _event;
	private String _title;
	private String _message;
	
	public void build(String title, String message, Context context)
	{
		build(title, message, context, null, false);
	}
	
	public void build(String title, String message, Context context, boolean useBar)
	{
		build(title, message, context, null, useBar);
	}
	
	public void build(String title, String message, Context context, LoaderEvent event)
	{
		build(title, message, context, event, false);
	}
	
	public void build(String title, String message, Context context, LoaderEvent event, boolean useBar)
	{
		_title = title;
		_message = message;
		
		_context = context;
		_useBar = useBar;
		
		_event = event;
	}
	
	@Override  
    protected void onPreExecute()  
    {  
		if(_useBar)
		{
			_progressDialog = new ProgressDialog(_context);  
	        _progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  
	        _progressDialog.setTitle(_title);  
	        _progressDialog.setMessage(_message);  
	        _progressDialog.setCancelable(false);  
	        _progressDialog.setIndeterminate(false);  
	        _progressDialog.setMax(100);  
	        _progressDialog.setProgress(0);  
	        _progressDialog.show(); 
		}else{
			_progressDialog = ProgressDialog.show(_context, _title, _message, false, false);
		}
    }  
	
	@Override
	protected Void doInBackground(Void... params) {
		try  
        {  
            synchronized (this)  
            {  
                int counter = 0;  
                while(counter <= 4)  
                {  
                    this.wait(500);  
                    counter++;  
                    publishProgress(counter*25);  
                }  
            }  
        }  
        catch (InterruptedException e)  
        {  
            e.printStackTrace();  
        }
		return null;
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		
		_progressDialog.setProgress(values[0]); 
		_event.onProgress();
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		
		_progressDialog.dismiss();
		_event.onComplete();
	}
	
	public void setTitle(String title) {
		_title = title;
		_progressDialog.setTitle(_title);  
	}
	
	public void setMessage(String message) {
		_message = message;
		_progressDialog.setMessage(_message);  
	}
}
