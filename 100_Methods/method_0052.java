        for (int i = 0; i < sessions.length; i++) {
            if (sessions[i].isClosed()) {
                continue;
            }
            s              = sessions[i];
            row            = t.getEmptyRowData();
            row[isid]      = ValuePool.getLong(s.getId());
            row[ict]       = new TimestampData(s.getConnectTime() / 1000);
            row[iuname]    = s.getUsername();
            row[iis_admin] = s.isAdmin() ? Boolean.TRUE
                                         : Boolean.FALSE;
            row[iautocmt]  = Boolean.valueOf(s.sessionContext.isAutoCommit);
            row[ireadonly] = Boolean.valueOf(s.isReadOnlyDefault);
            Number lastId = s.getLastIdentity();
            if (lastId != null) {
                row[ilast_id] = ValuePool.getLong(lastId.longValue());
            }
            row[it_tx]   = s.isInMidTransaction() ? Boolean.TRUE
                                                  : Boolean.FALSE;
            row[it_size] = ValuePool.getLong(s.getTransactionSize());
            HsqlName name = s.getCurrentSchemaHsqlName();
            if (name != null) {
                row[it_schema] = name.name;
            }

            row[it_waiting] = "";
            row[it_waited]  = "";

            if (s.waitingSessions.size() > 0) {
                StringBuffer sbr    = new StringBuffer();//String Buffer
                Session[]    array = new Session[s.waitingSessions.size()];

                s.waitingSessions.toArray(array);

                for (int j = 0; j < array.length; j++) {
                    if (j > 0) {
                        sbr.append(',');
                    }

                    sbr.append(array[j].getId());
                }

                row[it_waiting] = sbr.toString();
            }

            if (s.waitedSessions.size() > 0) {
                StringBuffer sbr    = new StringBuffer();
                Session[]    array = new Session[s.waitedSessions.size()];

                s.waitedSessions.toArray(array);

                for (int j = 0; j < array.length; j++) {
                    if (j > 0) {
                        sbr.append(',');
                    }

                    sbr.append(array[j].getId());
                }

                row[it_waited] = sbr.toString();
            }

            Statement st = s.sessionContext.currentStatement;

            row[it_statement]   = st == null ? ""
                                             : st.getSQL();
            row[it_latch_count] = new Long(s.latch.getCount());
		}
