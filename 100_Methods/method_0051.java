        // column number mappings
        final int catalog     = 0;
        final int schema      = 1;
        final int name        = 2;
        final int type        = 3;
        final int column_name = 4;
        final int remark      = 5;

        //
        Iterator it;
        Object[] row;

        //
        DITableInfo ti = new DITableInfo();

        it = allTables();

        while (it.hasNext()) {
            Table table = (Table) it.next();

            if (!session.getGrantee().isAccessible(table)) {
                continue;
            }

            ti.setTable(table);

            int colCount = table.getColumnCount();

            for (int i = 0; i < colCount; i++) {
                ColumnSchema column = table.getColumn(i);

                if (column.getName().comment == null) {
                    continue;
                }

                row              = t.getEmptyRowData();
                row[catalog]     = database.getCatalogName().name;
                row[schema]      = table.getSchemaName().name;
                row[name]        = table.getName().name;
                row[type]        = "COLUMN";
                row[column_name] = column.getName().name;
                row[remark]      = column.getName().comment;

                t.insertSys(session, store, row);
            }

            if (table.getTableType() != Table.INFO_SCHEMA_TABLE
                    && table.getName().comment == null) {
                continue;
            }
		}

