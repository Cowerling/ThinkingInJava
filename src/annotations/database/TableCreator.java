package annotations.database;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

/**
 * Created by dell on 2016/3/8.
 */
public class TableCreator {
    public static void main(String[] args) throws Exception {
        if(args.length < 1) {
            System.out.println("arguments: annotated classes");
            System.exit(0);
        }

        for(String className: args) {
            Class<?> cl = Class.forName(className);
            DBTable dbTable = cl.getAnnotation(DBTable.class);

            if(dbTable == null) {
                System.out.println("No DBTable annotation in class " + className);
                continue;
            }

            String tableName = dbTable.name();
            if(tableName.length() < 1)
                tableName = cl.getName().toUpperCase();

            List<String> columnDefines = new ArrayList<String>();
            for(Field field : cl.getDeclaredFields()) {
                String columnName = null;
                Annotation[] annotations = field.getDeclaredAnnotations();
                if(annotations.length < 1)
                    continue;

                if(annotations[0] instanceof SQLInteger) {
                    SQLInteger sqlInteger = (SQLInteger)annotations[0];
                    if(sqlInteger.name().length() < 1)
                        columnName = field.getName().toUpperCase();
                    else
                        columnName = sqlInteger.name();
                    columnDefines.add(columnName + " INT" + getConstraints(sqlInteger.constraints()));
                } else if(annotations[0] instanceof  SQLString) {
                    SQLString sqlString = (SQLString)annotations[0];
                    if(sqlString.name().length() < 1)
                        columnName = field.getName().toUpperCase();
                    else
                        columnName = sqlString.name();
                    columnDefines.add(columnName + " VARCHAR(" +sqlString.value() + ")" + getConstraints(sqlString.constraints()));
                }
            }

            StringBuilder createCommand = new StringBuilder("CREATE TABLE " + tableName + "(");
            for(String columnDefine : columnDefines)
                createCommand.append("\n    " + columnDefine + ",");
            String tableCreate = createCommand.substring(0, createCommand.length() - 1) + ");";

            System.out.println("Table Creation SQL for " + className + " is :\n" + tableCreate);
        }
    }

    private static String getConstraints(Constraints constraints) {
        String constraintsString = "";
        if(!constraints.allowNull())
            constraintsString += " NOT NULL";
        if(constraints.primaryKey())
            constraintsString += " PRIMARY KEY";
        if(constraints.unique())
            constraintsString += " UNIQUE";
        return constraintsString;
    }
}
