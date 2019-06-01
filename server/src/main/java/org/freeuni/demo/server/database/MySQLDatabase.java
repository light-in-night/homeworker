package org.freeuni.demo.server.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQLDatabase implements Database {
    private final Source src;
    private final Query qr;
    private final Creator cr;


    MySQLDatabase(Source src, Query qr, Creator cr) {
        this.src = src;
        this.qr = qr;
        this.cr = cr;
    }

    @Override
    public void addEntry(Entry e) {
        try(Connection con = src.getConnection()) {
            Statement st = con.createStatement();
            qr.compileAdd(e);
            st.execute(qr.toString());
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }

    @Override
    public List<Entry> getEntries(Query q) {
        List<Entry> result = new ArrayList<>();
        try(Connection con = src.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(qr.toString());
            while(rs.next()) {
                result.add(cr.fromResultSet(rs));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return result;
    }
}
