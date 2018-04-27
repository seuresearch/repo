	public void initSDL()
	{
		setScreenOrientation();
		updateScreenOrientation();
		DimSystemStatusBar.get().dim(_videoLayout);
		(new Thread(new Runnable()
		{
			public void run()
			{
				if( Globals.AutoDetectOrientation )
					Globals.HorizontalOrientation = isCurrentOrientationHorizontal();
				while( isCurrentOrientationHorizontal() != Globals.HorizontalOrientation ||
						((KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE)).inKeyguardRestrictedInputMode() )
				{
					Log.d("SDL", "libSDL: Waiting for screen orientation to change to " + (Globals.HorizontalOrientation ? "landscape" : "portrait") + ", and for disabling lockscreen mode");
					try {
						Thread.sleep(500);
					} catch( Exception e ) {}
					if( _isPaused )
					{
						Log.i("SDL", "libSDL: Application paused, cancelling SDL initialization until it will be brought to foreground");
						return;
					}
					DimSystemStatusBar.get().dim(_videoLayout);
				}
				runOnUiThread(new Runnable()
				{
					public void run()
					{
						// Hide navigation buttons, and sleep a bit so OS will process the event.
						// Do not check the display size in a loop - we may have several displays of different sizes,
						// so app may stuck in infinite loop
						DisplayMetrics dm = new DisplayMetrics();
						getWindowManager().getDefaultDisplay().getMetrics(dm);
						if( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && Globals.ImmersiveMode &&
							(_videoLayout.getHeight() != dm.widthPixels || _videoLayout.getWidth() != dm.heightPixels) )
						{
							DimSystemStatusBar.get().dim(_videoLayout);
							try {
								Thread.sleep(300);
							} catch( Exception e ) {}
						}
						initSDLInternal();
					}
				});
			}
		})).start();
	}

	private void initSDLInternal()
	{
		if(sdlInited)
			return;
		Log.i("SDL", "libSDL: Initializing video and SDL application");
		
		sdlInited = true;
		DimSystemStatusBar.get().dim(_videoLayout);
		_videoLayout.removeView(_layout);
		if( _ad.getView() != null )
			_videoLayout.removeView(_ad.getView());
		_layout = null;
		_layout2 = null;
		_btn = null;
		_tv = null;
		_inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		_videoLayout = new FrameLayout(this);
		SetLayerType.get().setLayerType(_videoLayout);
		setContentView(_videoLayout);
		mGLView = new DemoGLSurfaceView(this);
		SetLayerType.get().setLayerType(mGLView);
