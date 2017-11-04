package Database;

/**
 * Methods for returning various SQL query strings.
 */
class Queries
{
    /**
     * I. Table Creation.
     */
    static String getUsersTableCreationQuery()
    {
        return "CREATE TABLE IF NOT EXISTS users (\n"
                + "user_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n"
                + "user_username TEXT NOT NULL UNIQUE,\n"
                + "user_password TEXT NOT NULL,\n"
                + "user_email TEXT NOT NULL,\n"
                + "user_first_name TEXT NOT NULL,\n"
                + "user_last_name TEXT NOT NULL,\n"
                + "role_id INTEGER NOT NULL,\n"
                + "FOREIGN KEY(role_id) REFERENCES roles(role_id)\n"
                + ");";
    }

    static String getRolesTableCreationQuery()
    {
        return "CREATE TABLE IF NOT EXISTS roles (\n"
                + "role_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n"
                + "role_name TEXT NOT NULL UNIQUE,\n"
                + "role_priority INT NOT NULL\n"
                + ");";
    }

    static String getRulesTableCreationQuery()
    {
        return "CREATE TABLE IF NOT EXISTS rules (\n"
                + "rule_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n"
                + "role_id INTEGER NOT NULL,\n"
                + "permission_id INTEGER NOT NULL,\n"
                + "device_id INTEGER NOT NULL,\n"
                + "FOREIGN KEY(role_id) REFERENCES roles(role_id),\n"
                + "FOREIGN KEY(permission_id) REFERENCES permissions(permission_id),\n"
                + "FOREIGN KEY(device_id) REFERENCES devices(device_id)\n"
                + ");";
    }

    static String getPermissionsTableCreationQuery()
    {
        return "CREATE TABLE IF NOT EXISTS permissions (\n"
                + "permission_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n"
                + "permission_value TEXT NOT NULL UNIQUE\n"
                + ");";
    }

    static String getDevicesTableCreationQuery()
    {
        return "CREATE TABLE IF NOT EXISTS devices (\n"
                + "device_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n"
                + "device_name TEXT NOT NULL UNIQUE,\n"
                + "device_type TEXT NOT NULL,\n"
                + "device_status TEXT NOT NULL,\n"
                + "device_mode TEXT NOT NULL\n"
                + ");";
    }

    static String getPinsTableCreationQuery()
    {
        return "CREATE TABLE IF NOT EXISTS pins (\n"
                + "pin_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n"
                + "pin_number INTEGER NOT NULL UNIQUE\n"
                + ");";
    }

    static String getPinAssignmentTableCreationQuery()
    {
        return "CREATE TABLE IF NOT EXISTS pin_assignments (\n"
                + "pa_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n"
                + "pin_id INTEGER NOT NULL,\n"
                + "device_id INTEGER NOT NULL,\n"
                + "FOREIGN KEY(pin_id) REFERENCES pins(pin_id),\n"
                + "FOREIGN KEY(device_id) REFERENCES devices(device_id)\n"
                + ");";
    }

    /**
     * II. Row Insertion.
     */
    static String getInsertUserQuery()
    {
        return "INSERT INTO users(user_username,user_password,user_email," +
                "user_first_name,user_last_name,role_id) VALUES(?,?,?,?,?,?)";
    }

    static String getInsertRoleQuery()
    {
        return "INSERT INTO roles(role_name,role_priority) VALUES(?,?)";
    }

    static String getInsertPermissionQuery()
    {
        return "INSERT INTO permissions(permission_value) VALUES(?)";
    }

    static String getInsertDeviceQuery()
    {
        return "INSERT INTO devices(device_name,device_type,device_status,device_mode) VALUES(?,?,?,?)";
    }

    static String getInsertPinQuery()
    {
        return "INSERT INTO pins(pin_number) VALUES(?)";
    }

    static String getInsertPinAssignmentQuery()
    {
        return "INSERT INTO pin_assignments(pin_id,device_id) VALUES(?,?)";
    }

    static String getInsertRuleQuery()
    {
        return "INSERT INTO rules(role_id,permission_id,device_id) VALUES(?,?,?)";
    }

    /**
     * III. Row Selections.
     */
    static String getSelectUserByUsernameAndPassword()
    {
        return "SELECT user_id,user_email,user_first_name,user_last_name,role_id " +
                "FROM users WHERE user_username = ? AND user_password = ?";
    }

    static String getSelectAllDevices()
    {
        return "SELECT device_id,device_name,device_type,device_status,device_mode FROM devices";
    }

    static String getSelectAllUsers()
    {
        return "SELECT user_id,user_username,user_password,user_email,user_first_name,user_last_name,role_id FROM users";
    }

    static String getSelectAllRoles()
    {
        return "SELECT role_id,role_name,role_priority FROM roles";
    }

    static String getSelectAllRules()
    {
        return "SELECT rule_id,role_id,permission_id,device_id FROM rules";
    }

    static String getSelectDevicePinIdsByDeviceId()
    {
        return "SELECT pin_id FROM pin_assignments WHERE device_id = ?";
    }

    static String getSelectRoleById()
    {
        return "SELECT role_name FROM roles WHERE role_id = ?";
    }

    static String getSelectRoleIdByRoleName()
    {
        return "SELECT role_id FROM roles WHERE role_name = ?";
    }

    static String getSelectRolePriorityById()
    {
        return "SELECT role_priority FROM roles WHERE role_id = ?";
    }

    static String getSelectRulesByRoleId()
    {
        return "SELECT permission_id,device_id FROM rules WHERE role_id = ?";
    }

    static String getSelectDeviceIdByName()
    {
        return "SELECT device_id FROM devices WHERE device_name = ?";
    }

    static String getSelectDeviceNameById()
    {
        return "SELECT device_name FROM devices WHERE device_id = ?";
    }

    static String getSelectPermissionValueById()
    {
        return "SELECT permission_value FROM permissions WHERE permission_id = ?";
    }

    static String getSelectPermissionIdByValue()
    {
        return "SELECT permission_id FROM permissions WHERE permission_value = ?";
    }

    static String getSelectPinIdByPinNumber()
    {
        return "SELECT pin_id FROM pins WHERE pin_number = ?";
    }

    static String getSelectPinNumberByPinId()
    {
        return "SELECT pin_number FROM pins WHERE pin_id = ?";
    }

    /**
     * IV. Row Updates.
     */
    static String getUpdateDeviceQuery()
    {
        return "UPDATE devices SET "
                + "device_name = ? , "
                + "device_type = ? , "
                + "device_status = ? , "
                + "device_mode = ? "
                + "WHERE device_id = ?";
    }
}
