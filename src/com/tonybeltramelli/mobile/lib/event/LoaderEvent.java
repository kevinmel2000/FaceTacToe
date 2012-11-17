package com.tonybeltramelli.mobile.lib.event;

/**
 * 
 * @author Tony Beltramelli www.tonybeltramelli.com
 *
 */
public class LoaderEvent {
	private ITaskEvent _listener = null;
	
	public LoaderEvent(ITaskEvent listener) {
		_listener = listener;
	}
	
	public void onProgress()
	{
		_listener.onProgress();
	}
	
	public void onComplete()
	{
		_listener.onComplete();
	}
}
