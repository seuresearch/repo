	/**
	 * Return a long representing the date at the specified x/y position. The
	 * date returned will have a valid day, month and year. Other fields such as
	 * hour, minute, second and milli-second will be set to 0.
	 *
	 * @param x
	 *            X position
	 * @param y
	 *            Y position
	 * @return long The date, -1 if position does not contain a date.
	 */
//	PROJITY_MODIFICATION
	public long getDayAt(int x, int y) {
		if (_ltr ? (_startX > x) : (_startX < x) || _startY > y) {
			return -1;
		}

		// Determine which column of calendars we're in.
		int calCol = (_ltr ? (x - _startX) : (_startX - x))
				/ (_calendarWidth + CALENDAR_SPACING);

		
		int calRow = (y - _startY) / (_calendarHeight + CALENDAR_SPACING);
		
		// Determine which number calendar rows we're in.
		if (calRow > _numCalR - 1 || calCol > _numCalCols - 1) {
			return -1;
		}

		// Determine what row (week) in the selected month we're in.
		int row;
		row = ((y - _startY) - (calRow * (_calendarHeight + CALENDAR_SPACING)))
				/ (_boxPaddingY + _boxHeight + _boxPaddingY);
		// The first two lines in the calendar are the month and the days
		// of the week. Ignore them.
		row -= 2;

		//hk		if (row < 0 || row > 5) {
		if (row < -1 || row > 5) { // allow top row
			return -1;
		}

		// Determine which column in the selected month we're in.
		int col = ((_ltr ? (x - _startX) : (_startX - x)) - (calCol * (_calendarWidth + CALENDAR_SPACING)))
				/ (_boxPaddingX + _boxWidth + _boxPaddingX);

		if (col > DAYS_IN_WEEK - 1) {
			return -1;
		}

		//hk
		if (row == -1) {
			return (WEEKDAY_OFFSET - col);
		}

		// Use the first day of the month as a key point for determining the
		// date of our click.
		// The week index of the first day will always be 0.
		_cal.setTimeInMillis(_firstDisplayedDate);
		//_cal.set(Calendar.DAY_OF_MONTH, 1);
		_cal.add(Calendar.MONTH, calCol + (calRow * _numCalCols));

		int dayOfWeek = _cal.get(Calendar.DAY_OF_WEEK);
		int firstDayIndex = dayOfWeek - _firstDayOfWeek;
		if (firstDayIndex < 0) {
			firstDayIndex += DAYS_IN_WEEK;
		}

		int daysToAdd = (row * DAYS_IN_WEEK) + (col - firstDayIndex);
		if (daysToAdd < 0
				|| daysToAdd > (_cal.getActualMaximum(Calendar.DAY_OF_MONTH) - 1)) {
			return -1;
		}

		_cal.add(Calendar.DAY_OF_MONTH, daysToAdd);

		long selected = _cal.getTimeInMillis();

		// Restore the time.
		_cal.setTimeInMillis(_firstDisplayedDate);

		return selected;
	}