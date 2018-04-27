		//TODO what about resources that are unassigned, do they have a fictive task associated?
		_openProjTask.setName( truncName( mpxTask.getName() ) );

		if (mpxTask.getWBS() != null)
		{
			_openProjTask.setWbs( mpxTask.getWBS() );
		}

		//projityTask.setUniqueId(mpxTask.getUniqueIDValue());
		_openProjTask.setNotes( mpxTask.getNotes() );
		_openProjTask.getCurrentSchedule().setStart( DateTime.gmt( mpxTask.getStart() ) ); // start needs to be correct for assignment import
		_openProjTask.getCurrentSchedule().setFinish( DateTime.gmt( mpxTask.getFinish() ) ); // finish needs to be correct for assignment import

		if (mpxTask.getID() == null)
		{ // if no task id, generate one
			mpxTask.setID( ++autoId );
		}

		_openProjTask.setId( mpxTask.getID() );
		_openProjTask.setCreated( toNormalDate( mpxTask.getCreateDate() ) );
		_openProjTask.setDuration( toProjityDuration( mpxTask.getDuration(), context ) ); // set duration without controls
		_openProjTask.setEstimated( mpxTask.getEstimated() );

		if (mpxTask.getDeadline() != null)
		{
			_openProjTask.setDeadline( DateTime.gmt( mpxTask.getDeadline() ) );
		}

		net.sf.mpxj.Priority priority = mpxTask.getPriority();

		if (priority != null)
		{
			_openProjTask.setPriority( mpxTask.getPriority().getValue() );
		}

		Number fc = mpxTask.getFixedCost();

		if (fc != null)
		{
			_openProjTask.setFixedCost( fc.doubleValue() );
		}

		Date constraintDate = DateTime.gmtDate( mpxTask.getConstraintDate() );
		final net.sf.mpxj.ConstraintType constraintType = mpxTask.getConstraintType();

		if (constraintType != null)
		{
			_openProjTask.setScheduleConstraint( constraintType.ordinal(), (constraintDate == null)
				? 0
				: constraintDate.getTime() );
		}

		net.sf.mpxj.ProjectCalendar mpxCalendar = mpxTask.getCalendar();

		if (mpxCalendar != null)
		{
			WorkCalendar cal = ImportedCalendarService.getInstance().findImportedCalendar( mpxCalendar );

			if (cal == null)
			{
				//System.out.println( "Error finding imported calendar " + mpxCalendar.getName() );
				log.severe( "Error finding imported calendar " + mpxCalendar.getName() );
			}
			else
			{
				_openProjTask.setWorkCalendar( cal );
			}
		}

		//		System.out.println("reading %" + mpxTask.getPercentageComplete().doubleValue());
		//		projityTask.setPercentComplete(mpxTask.getPercentageComplete().doubleValue());

		//use stop and not percent complete because of rounding issues - this is a little odd, but true. setting stop in assignment can set % complete
