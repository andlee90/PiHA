package Database;

import Managers.MainManager;


/**
 * Holds all piha_server_resources.db build variations.
 */
class BuildVariants
{
    /**
     * Builds piha_server_resources.db with all data necessary for testing purposes.
     */
    static void buildTestDB()
    {
        System.out.println("> [" + MainManager.getDate() + "] Creating piha_server_resources.db with testing variants...");

        Helper.executeStatement(Queries.getRolesTableCreationQuery());
        System.out.println("> [" + MainManager.getDate() + "] Roles table created");

        Helper.executeStatement(Queries.getDevicesTableCreationQuery());
        System.out.println("> [" + MainManager.getDate() + "] Devices table created");

        Helper.executeStatement(Queries.getPermissionsTableCreationQuery());
        System.out.println("> [" + MainManager.getDate() + "] Permissions table created");

        Helper.executeStatement(Queries.getUsersTableCreationQuery());
        System.out.println("> [" + MainManager.getDate() + "] Users table created");

        Helper.executeStatement(Queries.getRulesTableCreationQuery());
        System.out.println("> [" + MainManager.getDate() + "] Rules table created");

        Helper.executeStatement(Queries.getPinsTableCreationQuery());
        System.out.println("> [" + MainManager.getDate() + "] Pins table created");

        Helper.executeStatement(Queries.getPinAssignmentTableCreationQuery());
        System.out.println("> [" + MainManager.getDate() + "] Pin-Assignments table created\n");

        Helper.insertRole("ADMIN", 0);
        System.out.println("> [" + MainManager.getDate() + "] Default admin role added to roles table");

        Helper.insertRole("USER", 1);
        System.out.println("> [" + MainManager.getDate() + "] Default user role added to roles table");

        Helper.insertRole("RESTRICTED_USER", 2);
        System.out.println("> [" + MainManager.getDate() + "] Default user role added to roles table\n");

        Helper.insertPermission("NONE");
        System.out.println("> [" + MainManager.getDate() + "] Default permission NONE added to permissions table");

        Helper.insertPermission("VIEW_ONLY");
        System.out.println("> [" + MainManager.getDate() + "] Default permission VIEW_ONLY added to permissions table");

        Helper.insertPermission("MODIFY");
        System.out.println("> [" + MainManager.getDate() + "] Default permission MODIFY added to permissions table\n");

        Helper.insertUser("admin", "drowssap", "admin@admin.com", "admin", "admin", "ADMIN");
        System.out.println("> [" + MainManager.getDate() + "] Default admin user added to users table");

        Helper.insertUser("andlee", "password", "andlee@andlee.com", "Andrew", "Smith", "USER");
        System.out.println("> [" + MainManager.getDate() + "] User andlee added to users table");

        Helper.insertUser("timzeh", "password", "timzeh@timzeh.com", "Tim", "Kelly", "RESTRICTED_USER");
        System.out.println("> [" + MainManager.getDate() + "] User timzeh added to users table\n");

        Helper.insertPin(0);
        System.out.println("> [" + MainManager.getDate() + "] Pin 0 added tp pins table");

        Helper.insertPin(1);
        System.out.println("> [" + MainManager.getDate() + "] Pin 1 added tp pins table");

        Helper.insertPin(2);
        System.out.println("> [" + MainManager.getDate() + "] Pin 2 added tp pins table");

        Helper.insertPin(3);
        System.out.println("> [" + MainManager.getDate() + "] Pin 3 added tp pins table");

        Helper.insertPin(4);
        System.out.println("> [" + MainManager.getDate() + "] Pin 4 added tp pins table");

        Helper.insertPin(5);
        System.out.println("> [" + MainManager.getDate() + "] Pin 5 added tp pins table");

        Helper.insertPin(6);
        System.out.println("> [" + MainManager.getDate() + "] Pin 6 added tp pins table");

        Helper.insertPin(7);
        System.out.println("> [" + MainManager.getDate() + "] Pin 7 added tp pins table");

        Helper.insertPin(8);
        System.out.println("> [" + MainManager.getDate() + "] Pin 8 added tp pins table");

        Helper.insertPin(9);
        System.out.println("> [" + MainManager.getDate() + "] Pin 9 added tp pins table");

        Helper.insertPin(10);
        System.out.println("> [" + MainManager.getDate() + "] Pin 10 added tp pins table");

        Helper.insertPin(11);
        System.out.println("> [" + MainManager.getDate() + "] Pin 11 added tp pins table");

        Helper.insertPin(12);
        System.out.println("> [" + MainManager.getDate() + "] Pin 12 added tp pins table");

        Helper.insertPin(13);
        System.out.println("> [" + MainManager.getDate() + "] Pin 13 added tp pins table");

        Helper.insertPin(14);
        System.out.println("> [" + MainManager.getDate() + "] Pin 14 added tp pins table");

        Helper.insertPin(15);
        System.out.println("> [" + MainManager.getDate() + "] Pin 15 added tp pins table");

        Helper.insertPin(16);
        System.out.println("> [" + MainManager.getDate() + "] Pin 16 added tp pins table");

        Helper.insertPin(21);
        System.out.println("> [" + MainManager.getDate() + "] Pin 21 added tp pins table");

        Helper.insertPin(22);
        System.out.println("> [" + MainManager.getDate() + "] Pin 22 added tp pins table");

        Helper.insertPin(23);
        System.out.println("> [" + MainManager.getDate() + "] Pin 23 added tp pins table");

        Helper.insertPin(24);
        System.out.println("> [" + MainManager.getDate() + "] Pin 24 added tp pins table");

        Helper.insertPin(25);
        System.out.println("> [" + MainManager.getDate() + "] Pin 25 added tp pins table");

        Helper.insertPin(26);
        System.out.println("> [" + MainManager.getDate() + "] Pin 26 added tp pins table");

        Helper.insertPin(27);
        System.out.println("> [" + MainManager.getDate() + "] Pin 27 added tp pins table");

        Helper.insertPin(28);
        System.out.println("> [" + MainManager.getDate() + "] Pin 28 added tp pins table");

        Helper.insertPin(29);
        System.out.println("> [" + MainManager.getDate() + "] Pin 29 added tp pins table\n");

        Helper.insertDevice( "LED1", "LED", "AVAILABLE", "OFF");
        Helper.insertPinAssignment(1, "LED1");
        Helper.insertRule("ADMIN", "MODIFY", "LED1");
        Helper.insertRule("USER", "VIEW_ONLY", "LED1");
        Helper.insertRule("RESTRICTED_USER", "VIEW_ONLY", "LED1");
        System.out.println("> [" + MainManager.getDate() + "] Default device LED1 added to devices table");
        System.out.println("> [" + MainManager.getDate() + "] LED1 assigned to pin 1");
        System.out.println("> [" + MainManager.getDate() + "] MODIFY permissions given to ADMIN for LED1");
        System.out.println("> [" + MainManager.getDate() + "] VIEW_ONLY permissions given to USER for LED1\n");

        Helper.insertDevice( "LED2", "LED", "AVAILABLE", "OFF");
        Helper.insertPinAssignment(2, "LED2");
        Helper.insertRule("ADMIN", "MODIFY", "LED2");
        Helper.insertRule("USER", "VIEW_ONLY", "LED2");
        Helper.insertRule("RESTRICTED_USER", "NONE", "LED2");
        System.out.println("> [" + MainManager.getDate() + "] Default device LED2 added to devices table");
        System.out.println("> [" + MainManager.getDate() + "] LED2 assigned to pin 2");
        System.out.println("> [" + MainManager.getDate() + "] MODIFY permissions given to ADMIN for LED2");
        System.out.println("> [" + MainManager.getDate() + "] VIEW_ONLY permissions given to USER for LED2\n");

        Helper.insertDevice("LED3", "LED", "AVAILABLE", "OFF");
        Helper.insertPinAssignment(3, "LED3");
        Helper.insertRule("ADMIN", "MODIFY", "LED3");
        Helper.insertRule("USER", "VIEW_ONLY", "LED3");
        Helper.insertRule("RESTRICTED_USER", "VIEW_ONLY", "LED3");
        System.out.println("> [" + MainManager.getDate() + "] Default device LED3 added to devices table");
        System.out.println("> [" + MainManager.getDate() + "] LED3 assigned to pin 3");
        System.out.println("> [" + MainManager.getDate() + "] MODIFY permissions given to ADMIN for LED3");
        System.out.println("> [" + MainManager.getDate() + "] VIEW_ONLY permissions given to USER for LED3\n");

        Helper.insertDevice( "LED4", "LED", "AVAILABLE", "OFF");
        Helper.insertPinAssignment(4, "LED4");
        Helper.insertRule("ADMIN", "MODIFY", "LED4");
        Helper.insertRule("USER", "VIEW_ONLY", "LED4");
        Helper.insertRule("RESTRICTED_USER", "NONE", "LED4");
        System.out.println("> [" + MainManager.getDate() + "] Default device LED4 added to devices table");
        System.out.println("> [" + MainManager.getDate() + "] LED4 assigned to pin 4");
        System.out.println("> [" + MainManager.getDate() + "] MODIFY permissions given to ADMIN for LED4");
        System.out.println("> [" + MainManager.getDate() + "] VIEW_ONLY permissions given to USER for LED4\n");

        Helper.insertDevice("LED5", "LED", "AVAILABLE", "OFF");
        Helper.insertPinAssignment(5, "LED5");
        Helper.insertRule("ADMIN", "MODIFY", "LED5");
        Helper.insertRule("USER", "VIEW_ONLY", "LED5");
        Helper.insertRule("RESTRICTED_USER", "VIEW_ONLY", "LED5");
        System.out.println("> [" + MainManager.getDate() + "] Default device LED5 added to devices table");
        System.out.println("> [" + MainManager.getDate() + "] LED5 assigned to pin 5");
        System.out.println("> [" + MainManager.getDate() + "] MODIFY permissions given to ADMIN for LED5");
        System.out.println("> [" + MainManager.getDate() + "] VIEW_ONLY permissions given to USER for LED5\n");

        Helper.insertDevice( "LED6", "LED", "AVAILABLE", "OFF");
        Helper.insertPinAssignment(9, "LED6");
        Helper.insertRule("ADMIN", "MODIFY", "LED6");
        Helper.insertRule("USER", "VIEW_ONLY", "LED6");
        Helper.insertRule("RESTRICTED_USER", "NONE", "LED6");
        System.out.println("> [" + MainManager.getDate() + "] Default device LED6 added to devices table");
        System.out.println("> [" + MainManager.getDate() + "] LED6 assigned to pin 9");
        System.out.println("> [" + MainManager.getDate() + "] MODIFY permissions given to ADMIN for LED6");
        System.out.println("> [" + MainManager.getDate() + "] VIEW_ONLY permissions given to USER for LED6\n");

        Helper.insertDevice( "LED7", "LED", "AVAILABLE", "OFF");
        Helper.insertPinAssignment(7, "LED7");
        Helper.insertRule("ADMIN", "MODIFY", "LED7");
        Helper.insertRule("USER", "VIEW_ONLY", "LED7");
        Helper.insertRule("RESTRICTED_USER", "VIEW_ONLY", "LED7");
        System.out.println("> [" + MainManager.getDate() + "] Default device LED7 added to devices table");
        System.out.println("> [" + MainManager.getDate() + "] LED7 assigned to pin 7");
        System.out.println("> [" + MainManager.getDate() + "] MODIFY permissions given to ADMIN for LED7");
        System.out.println("> [" + MainManager.getDate() + "] VIEW_ONLY permissions given to USER for LED7\n");

        Helper.insertDevice( "LED8", "LED", "AVAILABLE", "OFF");
        Helper.insertPinAssignment(8, "LED8");
        Helper.insertRule("ADMIN", "MODIFY", "LED8");
        Helper.insertRule("USER", "VIEW_ONLY", "LED8");
        Helper.insertRule("RESTRICTED_USER", "NONE", "LED8");
        System.out.println("> [" + MainManager.getDate() + "] Default device LED8 added to devices table");
        System.out.println("> [" + MainManager.getDate() + "] LED8 assigned to pin 8");
        System.out.println("> [" + MainManager.getDate() + "] MODIFY permissions given to ADMIN for LED8");
        System.out.println("> [" + MainManager.getDate() + "] VIEW_ONLY permissions given to USER for LED8\n");

        Helper.insertDevice( "RGBLED", "RGB_LED", "AVAILABLE", "OFF");
        Helper.insertPinAssignment(21, "RGBLED");
        Helper.insertPinAssignment(22, "RGBLED");
        Helper.insertPinAssignment(23, "RGBLED");
        Helper.insertRule("ADMIN", "MODIFY", "RGBLED");
        Helper.insertRule("USER", "VIEW_ONLY", "RGBLED");
        Helper.insertRule("RESTRICTED_USER", "NONE", "RGBLED");
        System.out.println("> [" + MainManager.getDate() + "] Default device RGBLED added to devices table");
        System.out.println("> [" + MainManager.getDate() + "] RGBLED assigned to pin 21, 22 & 23");
        System.out.println("> [" + MainManager.getDate() + "] MODIFY permissions given to ADMIN for RGBLED");
        System.out.println("> [" + MainManager.getDate() + "] VIEW_ONLY permissions given to USER for RGBLED\n");
    }

    /**
     * Builds piha_server_resources.db in a production-ready state.
     */
    private static void buildProductiontDB()
    {
        System.out.println("> [" + MainManager.getDate() + "] Creating piha_server_resources.db with production variants...");

        Helper.executeStatement(Queries.getRolesTableCreationQuery());
        System.out.println("> [" + MainManager.getDate() + "] Roles table created");

        Helper.executeStatement(Queries.getDevicesTableCreationQuery());
        System.out.println("> [" + MainManager.getDate() + "] Devices table created");

        Helper.executeStatement(Queries.getPermissionsTableCreationQuery());
        System.out.println("> [" + MainManager.getDate() + "] Permissions table created");

        Helper.executeStatement(Queries.getUsersTableCreationQuery());
        System.out.println("> [" + MainManager.getDate() + "] Users table created");

        Helper.executeStatement(Queries.getRulesTableCreationQuery());
        System.out.println("> [" + MainManager.getDate() + "] Rules table created");

        Helper.executeStatement(Queries.getPinsTableCreationQuery());
        System.out.println("> [" + MainManager.getDate() + "] Pins table created");

        Helper.executeStatement(Queries.getPinAssignmentTableCreationQuery());
        System.out.println("> [" + MainManager.getDate() + "] Pin-Assignments table created\n");

        Helper.insertRole("ADMIN", 0);
        System.out.println("> [" + MainManager.getDate() + "] Default admin role added to roles table");

        Helper.insertRole("USER", 1);
        System.out.println("> [" + MainManager.getDate() + "] Default user role added to roles table");

        Helper.insertRole("RESTRICTED_USER", 2);
        System.out.println("> [" + MainManager.getDate() + "] Default user role added to roles table\n");

        Helper.insertPermission("NONE");
        System.out.println("> [" + MainManager.getDate() + "] Default permission NONE added to permissions table");

        Helper.insertPermission("VIEW_ONLY");
        System.out.println("> [" + MainManager.getDate() + "] Default permission VIEW_ONLY added to permissions table");

        Helper.insertPermission("MODIFY");
        System.out.println("> [" + MainManager.getDate() + "] Default permission MODIFY added to permissions table\n");

        Helper.insertUser("admin", "drowssap", "admin@admin.com", "admin", "admin", "ADMIN");
        System.out.println("> [" + MainManager.getDate() + "] Default admin user added to users table");

        Helper.insertPin(0);
        System.out.println("> [" + MainManager.getDate() + "] Pin 0 added tp pins table");

        Helper.insertPin(1);
        System.out.println("> [" + MainManager.getDate() + "] Pin 1 added tp pins table");

        Helper.insertPin(2);
        System.out.println("> [" + MainManager.getDate() + "] Pin 2 added tp pins table");

        Helper.insertPin(3);
        System.out.println("> [" + MainManager.getDate() + "] Pin 3 added tp pins table");

        Helper.insertPin(4);
        System.out.println("> [" + MainManager.getDate() + "] Pin 4 added tp pins table");

        Helper.insertPin(5);
        System.out.println("> [" + MainManager.getDate() + "] Pin 5 added tp pins table");

        Helper.insertPin(6);
        System.out.println("> [" + MainManager.getDate() + "] Pin 6 added tp pins table");

        Helper.insertPin(7);
        System.out.println("> [" + MainManager.getDate() + "] Pin 7 added tp pins table");

        Helper.insertPin(8);
        System.out.println("> [" + MainManager.getDate() + "] Pin 8 added tp pins table");

        Helper.insertPin(9);
        System.out.println("> [" + MainManager.getDate() + "] Pin 9 added tp pins table");

        Helper.insertPin(10);
        System.out.println("> [" + MainManager.getDate() + "] Pin 10 added tp pins table");

        Helper.insertPin(11);
        System.out.println("> [" + MainManager.getDate() + "] Pin 11 added tp pins table");

        Helper.insertPin(12);
        System.out.println("> [" + MainManager.getDate() + "] Pin 12 added tp pins table");

        Helper.insertPin(13);
        System.out.println("> [" + MainManager.getDate() + "] Pin 13 added tp pins table");

        Helper.insertPin(14);
        System.out.println("> [" + MainManager.getDate() + "] Pin 14 added tp pins table");

        Helper.insertPin(15);
        System.out.println("> [" + MainManager.getDate() + "] Pin 15 added tp pins table");

        Helper.insertPin(16);
        System.out.println("> [" + MainManager.getDate() + "] Pin 16 added tp pins table");

        Helper.insertPin(21);
        System.out.println("> [" + MainManager.getDate() + "] Pin 21 added tp pins table");

        Helper.insertPin(22);
        System.out.println("> [" + MainManager.getDate() + "] Pin 22 added tp pins table");

        Helper.insertPin(23);
        System.out.println("> [" + MainManager.getDate() + "] Pin 23 added tp pins table");

        Helper.insertPin(24);
        System.out.println("> [" + MainManager.getDate() + "] Pin 24 added tp pins table");

        Helper.insertPin(25);
        System.out.println("> [" + MainManager.getDate() + "] Pin 25 added tp pins table");

        Helper.insertPin(26);
        System.out.println("> [" + MainManager.getDate() + "] Pin 26 added tp pins table");

        Helper.insertPin(27);
        System.out.println("> [" + MainManager.getDate() + "] Pin 27 added tp pins table");

        Helper.insertPin(28);
        System.out.println("> [" + MainManager.getDate() + "] Pin 28 added tp pins table");

        Helper.insertPin(29);
        System.out.println("> [" + MainManager.getDate() + "] Pin 29 added tp pins table\n");
    }
}