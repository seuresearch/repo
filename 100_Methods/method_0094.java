	/**
	 * Returns the bounding box for drawing a date. It is assumed that the
	 * calendar, _cal, is already set to the date you want to find the offset
	 * for.
	 *
	 * @param bounds
	 *            Bounds of the date to draw in.
	 * @return Point X/Y coordinate to the upper left corner of the bounding box
	 *         for date.
	 */
	private void calculateBoundsForDay(Rectangle bounds, Calendar cal, boolean weekDay, _fdow) {
//		PROJITY_MODIFICATION
		int year;
		int month;
		int _dow;
		int weekOfMonth;
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		_dow = cal.get(Calendar.DAY_OF_WEEK);
		weekOfMonth = cal.get(Calendar.WEEK_OF_MONTH);

		int diffMonths = month - _firstDisplayedMonth
				+ ((year - _firstDisplayedYear) * 12);
		// Determine which number calendar columns we're in.
		int calRowIndex = diffMonths / _ncc;

		int calColIndex = diffMonths - (calRowIndex * _ncc);
		// Modify the index relative to the first day of the week.
		bounds.x = _dow - _fdow;
		if (bounds.x < 0) {
			bounds.x += DAYS_IN_WEEK;
		}

		// Offset for location of the day in the week.
		bounds.x = _ltr ? bounds.x * (_boxPaddingX + _boxWidth + _boxPaddingX)
				: (bounds.x + 1) * (_boxPaddingX + _boxWidth + _boxPaddingX);

		// Offset for the column the calendar is displayed in.
		bounds.x += calColIndex * (_calendarWidth + CALENDAR_SPACING);

		// Adjust by centering value.
		bounds.x = _ltr ? _startX + bounds.x : _startX - bounds.x;

		if (weekDay) { // if drawing weekdays, need to draw on the top line
			weekOfMonth = 0;
		}

		// Initial offset for Month and Days of the Week display.
		bounds.y = 2 * (_boxPaddingY + _boxHeight + _boxPaddingY);

		// Offset for centering and row the calendar is displayed in.
		bounds.y += _startY + calRowIndex
				* (_calendarHeight + CALENDAR_SPACING);

		// Offset for Week of the Month.
		bounds.y += (weekOfMonth - 1)
				* (_boxPaddingY + _boxHeight + _boxPaddingY);

//		System.out.println(cal.getTime() + " week of month " + weekOfMonth + " bounds y " + bounds.y );

		bounds.width = _boxPaddingX + _boxWidth + _boxPaddingX;
		bounds.height = _boxPaddingY + _boxHeight + _boxPaddingY;
	}