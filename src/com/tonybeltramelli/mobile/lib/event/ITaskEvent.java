package com.tonybeltramelli.mobile.lib.event;

/**
 * 
 * @author Tony Beltramelli www.tonybeltramelli.com
 *
 */
public interface ITaskEvent {
	void onStart();
	void onProgress();
	void onComplete();
	void onError(Throwable error);
}
