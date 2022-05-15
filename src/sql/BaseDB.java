package sql;

import java.sql.SQLException;


public interface BaseDB {
    DBTools DB = DBTools.getDBTools();
    void closeAll() throws SQLException;
}