	void eliminateWeekdayDuplicates(boolean weekDays[]) {
		Calendar cal = calendarInstance();
		for (Iterator i=iterator();i.hasNext();){ //a more optimized version can be found
			DateSpan interval=(DateSpan)i.next();
			cal.setTimeInMillis(interval.getStart());
			int dow = cal.get(Calendar.DAY_OF_WEEK) -1;

			// remove any days which correspond to a selected week day
			for (int d = 0; d < 7; d++) {
				if (weekDays[d] && d == dow) {
					i.remove();
				}
			}
		}
	}