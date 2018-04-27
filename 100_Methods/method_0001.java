	/**
	 * Create a new instance of the <code>JXMonthView</code> class using the
	 * month and year from <code>initialTime</code> as the first date to
	 * display.
	 *
	 * @param initialTime
	 *            The first month to display.
	 */
	public JXXMonthView(long initialTime) {
		super();

		_ltr = getComponentOrientation().isLeftToRight();

		// Set up calendar instance.
		_cal = calendarInstance(); //PROJITY_MODIFICATION
		_cal.setFirstDayOfWeek(_firstDayOfWeek);
		_cal.setMinimalDaysInFirstWeek(1); //PROJITY_MODIFICATION // needed for weeks with less than 3 days so that they start on week 1 instead of week 0

		// Keep track of today.
		_cal.set(Calendar.HOUR_OF_DAY, 0);
		_cal.set(Calendar.MINUTE, 0);
		_cal.set(Calendar.SECOND, 0);
		_cal.set(Calendar.MILLISECOND, 0);
		_today = _cal.getTimeInMillis();

		_cal.setTimeInMillis(initialTime);
		setFirstDisplayedDate(_cal.getTimeInMillis());

		// Get string representation of the months of the year.
		_cal.set(Calendar.MONTH, _cal.getMinimum(Calendar.MONTH));
		_cal.set(Calendar.DAY_OF_MONTH, _cal
				.getActualMinimum(Calendar.DAY_OF_MONTH));
		_monthsOfTheYear = new String[MONTHS_IN_YEAR];
		SimpleDateFormat fullMonthNameFormatter = dateFormatInstance("MMMM");
		for (int i = 0; i < MONTHS_IN_YEAR; i++) {
			_monthsOfTheYear[i] = fullMonthNameFormatter.format(_cal.getTime());
			_cal.add(Calendar.MONTH, 1);
		}

		setOpaque(true);
		setBackground(Color.WHITE);
		setFont(new Font("Dialog", Font.PLAIN, 12));
		_todayBackgroundColor = getForeground();

		// Restore original time value.
		_cal.setTimeInMillis(_firstDisplayedDate);

		enableEvents(AWTEvent.MOUSE_EVENT_MASK);
		enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);
