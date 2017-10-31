package Database;

import DeviceObjects.*;
import Managers.MainManager;
import UserObjects.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;

import static Database.Queries.*;

public class Helper
{
    private static final String dbUrl = "jdbc:sqlite:sqlite/db/piha_server_resources.db";

    /**
     * Connects to the existing piha_server_resources.db or creates it from scratch using default settings.
     */
    public static void connectToOrCreateNewDB()
    {
        System.out.print("> [" + MainManager.getDate() + "] Connecting to piha_server_resources.db...");

        try (Connection conn = DriverManager.getConnection(dbUrl))
        {
            if (conn != null)
            {
                DatabaseMetaData dbm = conn.getMetaData();
                ResultSet tables = dbm.getTables(null, null, "Users", null);

                if (tables.next())
                {
                    System.out.print(" Success\n");
                }
                else
                {
                    System.out.print(" Failed\n");

                    BuildVariants.buildTestDB();
                    // BuildVariants.buildProductionDB();
                    // BuildVariants.buildPowerStripDB();
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println("> [" + MainManager.getDate() + "] Database connection and/or creation failed");
            System.out.println("> [" + MainManager.getDate() + "] " + e.getMessage());
        }
    }

    /**
     * Creates a connection to resources.db.
     */
    private static Connection connect()
    {
        Connection conn = null;
        try
        {
            conn = DriverManager.getConnection(dbUrl);
        }
        catch (SQLException e)
        {
            System.out.println("> [" + MainManager.getDate() + "] Database connection failed");
            System.out.println("> [" + MainManager.getDate() + "] " + e.getMessage());
        }
        return conn;
    }

    /**
     * Default SQLite execution method.
     */
    static void executeStatement(String sql)
    {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement())
        {
            stmt.execute(sql);
        }
        catch (SQLException e)
        {
            System.out.println("> [" + MainManager.getDate() + "] Generic statement execution failed");
            System.out.println("> [" + MainManager.getDate() + "] " + e.getMessage());
        }
    }

    /**
     * Insert a new row into the users table.
     * @param user  username of the user to be inserted.
     * @param pass  password of the user to be inserted.
     * @param email email of the user to be inserted.
     * @param fn    first name of the user to be inserted.
     * @param ln    last name of the user to be inserted.
     * @param rn    name of the role of the user to be inserted.
     */
    public static void insertUser(String user, String pass, String email, String fn, String ln, String rn)
    {
        int r_id = selectRoleIdByRoleName(rn);
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(Queries.getInsertUserQuery()))
        {
            pstmt.setString(1, user);
            pstmt.setString(2, pass);
            pstmt.setString(3, email);
            pstmt.setString(4, fn);
            pstmt.setString(5, ln);
            pstmt.setInt(6, r_id);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("> [" + MainManager.getDate() + "] User insertion failed");
            System.out.println("> [" + MainManager.getDate() + "] " + e.getMessage());
        }
    }

    /**
     * Insert a new row into the roles table.
     * @param name role name of the role to be inserted.
     * @param priority priority of the role to be inserted (0 - n, 0 being highest).
     */
    public static void insertRole(String name, int priority)
    {
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(Queries.getInsertRoleQuery()))
        {
            pstmt.setString(1, name);
            pstmt.setInt(2, priority);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("> [" + MainManager.getDate() + "] Role insertion failed");
            System.out.println("> [" + MainManager.getDate() + "] " + e.getMessage());
        }
    }

    /**
     * Insert a new row into the permissions table.
     * @param value permission value of the permission to be inserted.
     */
    public static void insertPermission(String value)
    {
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(Queries.getInsertPermissionQuery()))
        {
            pstmt.setString(1, value);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("> [" + MainManager.getDate() + "] Permission insertion failed");
            System.out.println("> [" + MainManager.getDate() + "] " + e.getMessage());
        }
    }

    /**
     * Insert a new row into the rules table.
     * @param role role name of the rule to be inserted.
     * @param permission permission value of the rule to be inserted.
     * @param device device name of the rule to be inserted.
     */
    public static void insertRule(String role, String permission, String device)
    {
        int role_id = selectRoleIdByRoleName(role);
        int permission_id = selectPermissionIdByPermissionValue(permission);
        int device_id = selectDeviceIdByDeviceName(device);

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(Queries.getInsertRuleQuery()))
        {
            pstmt.setInt(1, role_id);
            pstmt.setInt(2, permission_id);
            pstmt.setInt(3, device_id);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("> [" + MainManager.getDate() + "] Rule insertion failed");
            System.out.println("> [" + MainManager.getDate() + "] " + e.getMessage());
        }
    }

    /**
     * Insert a new row into the devices table.
     * @param name   device name of the device to be inserted.
     * @param type   device type of the device to be inserted.
     * @param status device status of the device to be inserted.
     * @param mode   device mode of the device to be inserted.
     */
    public static void insertDevice(String name, String type, String status, String mode)
    {
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(Queries.getInsertDeviceQuery()))
        {
            pstmt.setString(1, name);
            pstmt.setString(2, type);
            pstmt.setString(3, status);
            pstmt.setString(4, mode);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("> [" + MainManager.getDate() + "] Device insertion failed");
            System.out.println("> [" + MainManager.getDate() + "] " + e.getMessage());
        }
    }

    /**
     * Insert a new row into the pins table.
     * @param pin    pin number of the pin to be inserted.
     */
    public static void insertPin(int pin)
    {
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(Queries.getInsertPinQuery()))
        {
            pstmt.setInt(1, pin);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("> [" + MainManager.getDate() + "] User insertion failed");
            System.out.println("> [" + MainManager.getDate() + "] " + e.getMessage());
        }
    }

    /**
     * Insert a new row into the pin assignments table.
     * @param pin pin number of the assignment to be inserted.
     * @param device device name of the assignment to be inserted.
     */
    public static void insertPinAssignment(int pin, String device)
    {
        int pin_id = selectPinIdByPinNumber(pin);
        int device_id = selectDeviceIdByDeviceName(device);

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(Queries.getInsertPinAssignmentQuery()))
        {
            pstmt.setInt(1, pin_id);
            pstmt.setInt(2, device_id);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("> [" + MainManager.getDate() + "] Pin assignment insertion failed");
            System.out.println("> [" + MainManager.getDate() + "] " + e.getMessage());
        }
    }

    /**
     * Returns a User object for the corresponding username and password from the users table.
     * @param username the username of the user to select.
     * @param password the password of the user to select.
     */
    public static User selectUserByUsernameAndPassword(String username, String password)
    {
        User user;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(getSelectUserByUsernameAndPassword()))
        {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            int userId = rs.getInt("user_id");
            String email = rs.getString("user_email");
            String firstName = rs.getString("user_first_name");
            String lastName = rs.getString("user_last_name");
            int roleId = rs.getInt("role_id");
            String roleName = selectRoleById(roleId);

            user = new User(userId, username, password, email, firstName, lastName, roleName);
        }
        catch (SQLException e)
        {
            System.out.println("> [" + MainManager.getDate() + "] Failed login attempt with username: " + username + ".");
            return null;
        }

        return user;
    }

    /**
     * Returns the name of the role for a given id.
     * @param id the id of the role to select.
     * @return the name of the selected role.
     */
    public static String selectRoleById(int id)
    {
        String roleName = "";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(getSelectRoleById()))
        {
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            roleName = rs.getString("role_name");
        }
        catch (SQLException e)
        {
            System.out.println("> [" + MainManager.getDate() + "] Role selection failed");
            System.out.println("> [" + MainManager.getDate() + "] " + e.getMessage());
        }

        return roleName;
    }

    /**
     * Returns the priority level of the role for a given id.
     * @param id the id of the role to select.
     * @return the priority level of the selected role.
     */
    public static int selectRolePriorityById(int id)
    {
        int rolePriority = -1;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(getSelectRolePriorityById()))
        {
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            rolePriority = rs.getInt("role_priority");
        }
        catch (SQLException e)
        {
            System.out.println("> [" + MainManager.getDate() + "] Role priority selection failed");
            System.out.println("> [" + MainManager.getDate() + "] " + e.getMessage());
        }

        return rolePriority;
    }

    /**
     * Returns the id of a role for a given role name.
     * @param name the name of the role to select.
     * @return the id of the selected role.
     */
    public static int selectRoleIdByRoleName(String name)
    {
        int roleId = -1;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(getSelectRoleIdByRoleName()))
        {
            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();
            roleId = rs.getInt("role_id");
        }
        catch (SQLException e)
        {
            System.out.println("> [" + MainManager.getDate() + "] Role id selection failed");
            System.out.println("> [" + MainManager.getDate() + "] " + e.getMessage());
        }

        return roleId;
    }

    /**
     * Returns the id of the device for a given name.
     * @param name the name of the device to select.
     * @return the id of the selected device.
     */
    public static int selectDeviceIdByDeviceName(String name)
    {
        int deviceId = 0;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(getSelectDeviceIdByName()))
        {
            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();
            deviceId = rs.getInt("device_id");
        }
        catch (SQLException e)
        {
            System.out.println("> [" + MainManager.getDate() + "] Device id selection failed");
            System.out.println("> [" + MainManager.getDate() + "] " + e.getMessage());
        }

        return deviceId;
    }

    /**
     * Returns the name of the device for a given id.
     * @param id the id of the device to select.
     * @return the name of the selected device.
     */
    public static String selectDeviceNameByDeviceId(int id)
    {
        String deviceName = "";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(getSelectDeviceNameById()))
        {
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            deviceName = rs.getString("device_name");
        }
        catch (SQLException e)
        {
            System.out.println("> [" + MainManager.getDate() + "] Device name selection failed");
            System.out.println("> [" + MainManager.getDate() + "] " + e.getMessage());
        }

        return deviceName;
    }

    /**
     * Returns the value of the permission for a given id.
     * @param permissionId the id of the permission to select.
     * @return the value of the selected permission.
     */
    public static String selectPermissionValueByPermissionId(int permissionId)
    {
        String permissionValue = "";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(getSelectPermissionValueById()))
        {
            pstmt.setInt(1, permissionId);

            ResultSet rs = pstmt.executeQuery();
            permissionValue = rs.getString("permission_value");
        }
        catch (SQLException e)
        {
            System.out.println("> [" + MainManager.getDate() + "] Permission value selection failed");
            System.out.println("> [" + MainManager.getDate() + "] " + e.getMessage());
        }

        return permissionValue;
    }

    /**
     * Returns the id of the permission for a given value.
     * @param value the name of the permission to select.
     * @return the id of the selected permission.
     */
    public static int selectPermissionIdByPermissionValue(String value)
    {
        int permissionId = 0;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(getSelectPermissionIdByValue()))
        {
            pstmt.setString(1, value);

            ResultSet rs = pstmt.executeQuery();
            permissionId = rs.getInt("permission_id");
        }
        catch (SQLException e)
        {
            System.out.println("> [" + MainManager.getDate() + "] Permission value selection failed");
            System.out.println("> [" + MainManager.getDate() + "] " + e.getMessage());
        }

        return permissionId;
    }

    /**
     * Returns the a Hashtable containing all rules for a given role id.
     * @param id the id of the role to select.
     * @return the rules of the selected role.
     */
    public static Hashtable<String, String> selectRulesByRoleId(int id)
    {
        Hashtable<String, String> rules = new Hashtable<>();

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(getSelectRulesByRoleId()))
        {
            pstmt.setInt(1,id);
            ResultSet rs    = pstmt.executeQuery();

            while (rs.next())
            {
                int deviceId = rs.getInt("device_id");
                int permissionId = rs.getInt("permission_id");

                String device = selectDeviceNameByDeviceId(deviceId);
                String permission = selectPermissionValueByPermissionId(permissionId);

                rules.put(device, permission);
            }
        }
        catch (SQLException e)
        {
            System.out.println("> [" + MainManager.getDate() + "] Rules selection failed");
            System.out.println("> [" + MainManager.getDate() + "] " + e.getMessage());
        }

        return rules;
    }

    /**
     * Return all devices currently stored in the db.
     * @return A Devices object containing a List of of Devices in the db.
     */
    public static DeviceList selectAllDevices()
    {
        DeviceList devices = new DeviceList();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(getSelectAllDevices()))
        {
            while (rs.next())
            {
                ArrayList<Integer> pins = selectDevicePinsByDeviceId(rs.getInt("device_id"));

                if (rs.getString("device_type").equals(Device.DeviceType.LED.toString()))
                {
                    Led led = new Led(rs.getInt("device_id"),
                            pins,
                            rs.getString("device_name"),
                            Device.getDeviceStatusFromString(rs.getString("device_status")),
                            Led.getLedModeFromString(rs.getString("device_mode")));
                    devices.addDevice(led);
                }
                else if (rs.getString("device_type").equals(Device.DeviceType.RGB_LED.toString()))
                {
                    RgbLed rgbLed = new RgbLed(rs.getInt("device_id"),
                            pins,
                            rs.getString("device_name"),
                            Device.getDeviceStatusFromString(rs.getString("device_status")),
                            RgbLed.getRgbLedModeFromString(rs.getString("device_mode")));
                    devices.addDevice(rgbLed);
                }
                else if (rs.getString("device_type").equals(Device.DeviceType.RELAY_MOD.toString()))
                {
                    RelayModule relayMod = new RelayModule(rs.getInt("device_id"), pins,
                            rs.getString("device_name"),
                            Device.getDeviceStatusFromString(rs.getString("device_status")),
                            RelayModule.getRelayModuleModeFromString(rs.getString("device_mode")));
                    devices.addDevice(relayMod);
                }
                else if (rs.getString("device_type").equals(Device.DeviceType.STEP_MOTOR.toString()))
                {
                    StepperMotor stepMotor = new StepperMotor(rs.getInt("device_id"), pins,
                            rs.getString("device_name"),
                            Device.getDeviceStatusFromString(rs.getString("device_status")),
                            StepperMotor.getStepperMotorModeFromString(rs.getString("device_mode")));
                    devices.addDevice(stepMotor);
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println("> [" + MainManager.getDate() + "] All device selection failed");
            System.out.println("> [" + MainManager.getDate() + "] " + e.getMessage());
        }

        return devices;
    }

    /**
     * Returns all pins for a given device.
     * @return A ArrayList object containing a list of all pins for the given device in the db.
     */
    public static ArrayList<Integer> selectDevicePinsByDeviceId(int id)
    {
        ArrayList<Integer> pins = new ArrayList<>();

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(getSelectDevicePinIdsByDeviceId()))
        {
            pstmt.setInt(1,id);
            ResultSet rs    = pstmt.executeQuery();

            while (rs.next())
            {
                int pin = selectPinNumberByPinId(rs.getInt("pin_id"));
                pins.add(pin);
            }
        }
        catch (SQLException e)
        {
            System.out.println("> [" + MainManager.getDate() + "] Device pin selection failed");
            System.out.println("> [" + MainManager.getDate() + "] " + e.getMessage());
        }

        return pins;
    }

    /**
     * Returns the pin id for a given pin number.
     * @param pin the pin number of the pin to select.
     * @return the id of the selected pin.
     */
    public static int selectPinIdByPinNumber(int pin)
    {
        int pinId = -1;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(getSelectPinIdByPinNumber()))
        {
            pstmt.setInt(1, pin);

            ResultSet rs = pstmt.executeQuery();
            pinId = rs.getInt("pin_id");
        }
        catch (SQLException e)
        {
            System.out.println("> [" + MainManager.getDate() + "] Pin id selection failed");
            System.out.println("> [" + MainManager.getDate() + "] " + e.getMessage());
        }

        return pinId;
    }

    /**
     * Returns the pin number for a given pin id.
     * @param id the pin id of the pin to select.
     * @return the number of the selected pin.
     */
    public static int selectPinNumberByPinId(int id)
    {
        int pinNumber = -1;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(getSelectPinNumberByPinId()))
        {
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            pinNumber = rs.getInt("pin_number");
        }
        catch (SQLException e)
        {
            System.out.println("> [" + MainManager.getDate() + "] Pin number selection failed");
            System.out.println("> [" + MainManager.getDate() + "] " + e.getMessage());
        }

        return pinNumber;
    }

    /**
     * Updates the selected Device's attributes.
     * @param id the id of the Device to update.
     * @param name the updated Device's name.
     * @param type the updated Device's type.
     * @param status the updated Device's status.
     * @param state the updated Device's state.
     */
    public static void updateDevice(int id, String name, String type, String status, String state)
    {
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(Queries.getUpdateDeviceQuery()))
        {
            pstmt.setString(1, name);
            pstmt.setString(2, type);
            pstmt.setString(3, status);
            pstmt.setString(4, state);
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("> [" + MainManager.getDate() + "] Device update failed");
            System.out.println(e.getMessage());
        }
    }
}


