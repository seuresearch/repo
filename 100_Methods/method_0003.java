	public static void toMPXVoid( 
		VoidNodeImpl projityVoid,
		net.sf.mpxj.Task mpxTask )
	{
		mpxTask.setID( (int)projityVoid.getId() );
		mpxTask.setUniqueID( (int)projityVoid.getId() );
		mpxTask.setNull( true );

		// below is for mpxj 2007. These values need to be set
		mpxTask.setCritical( false );
		mpxTask.setTotalSlack( toMPXDuration( 0 ) );
	}

	public static void toMpxCalendar( 
		WorkingCalendar workCalendar,
		net.sf.mpxj.ProjectCalendar _mpxProjectCalendar )
	{
		_mpxProjectCalendar.setName( workCalendar.getName() );

		//		mpx.setUniqueID((int) workCalendar.getId()); // TODO watch out for int overrun
		WorkingCalendar wc = workCalendar;

		if (workCalendar.isBaseCalendar())
		{
			wc = (WorkingCalendar)workCalendar.getBaseCalendar();
		}

		for (int i = 0; i < 7; i++)
		{ // MPX days go from SUNDAY=1 to SATURDAY=7

			WorkDay day = workCalendar.isBaseCalendar()
				? workCalendar.getDerivedWeekDay( i )
				: workCalendar.getWeekDay( i );
			net.sf.mpxj.ProjectCalendarHours mpxDay = null;
			net.sf.mpxj.Day d = net.sf.mpxj.Day.getInstance( i + 1 );

			if (day == null)
			{
//				mpx.setWorkingDay( d, net.sf.mpxj.ProjectCalendar.DEFAULT );
			}
			else
			{
				_mpxProjectCalendar.setWorkingDay( d, day.isWorking() );

				if (day.isWorking())
				{
					mpxDay = _mpxProjectCalendar.addCalendarHours( net.sf.mpxj.Day.getInstance( i + 1 ) );
					toMpxCalendarDay( day, mpxDay );
				}
			}
		}

		WorkDay[] workDays = workCalendar.getExceptionDays();

		if (workDays != null)
		{
			for (int i = 0; i < workDays.length; i++)
			{
