package Managers;

import Database.Helper;
import DeviceObjects.*;
import com.pi4j.io.gpio.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class MainManager
{
    public static void main(String[] args)
    {
        ServerManager serverManager = null;

        try
        {
            int PORT_NUMBER = Integer.parseInt(args[0]);
            int MAX_CLIENTS = Integer.parseInt(args[1]);
            serverManager = new ServerManager(PORT_NUMBER, MAX_CLIENTS);
        }
        catch (NumberFormatException | ArrayIndexOutOfBoundsException e)
        {
            System.out.println("> [" + getDate() + "] ERROR: Params must be integers in the form: #PORT #CLIENTS");
            System.exit(0);
        }
        catch (IOException e)
        {
            System.out.println("> [" + getDate() + "] ERROR: Failed to initialize server");
            System.exit(0);
        }

        // Create a new resource database if one does not already exist
        Helper.connectToOrCreateNewDB();

        // Set up pins to be in OFF state
        initializePins();

        // Run the server
        serverManager.start();

        // Listen for commands from the server administrator
        Scanner scanner = new Scanner(System.in);

        while(scanner.hasNext())
        {
            String command = scanner.next();

            if (command.equals("help"))
            {
                System.out.println("showclients --- Prints a list of connected clients");
                System.out.println("shutdown --- Terminates the server after updating the database");
            }

            else if (command.equals("shutdown"))
            {
                DeviceList devices = Helper.selectAllDevices();

                for (Device d: devices.getDevices())
                {
                    if(d instanceof Led)
                    {
                        d.setDeviceMode(Led.LedMode.OFF);
                        d.setDeviceStatus(Device.DeviceStatus.AVAILABLE);

                        Helper.updateDevice(d.getDeviceId(),
                                d.getDeviceName(),
                                Device.DeviceType.LED.toString(),
                                d.getDeviceStatus().toString(),
                                d.getDeviceMode().toString());
                    }
                    else if (d instanceof RgbLed)
                    {
                        d.setDeviceMode(RgbLed.RgbLedMode.OFF);
                        d.setDeviceStatus(Device.DeviceStatus.AVAILABLE);

                        Helper.updateDevice(d.getDeviceId(),
                                d.getDeviceName(),
                                Device.DeviceType.RGB_LED.toString(),
                                d.getDeviceStatus().toString(),
                                d.getDeviceMode().toString());
                    }
                    else if (d instanceof RelayModule)
                    {
                        d.setDeviceMode(RelayModule.RelayModuleMode.OFF);
                        d.setDeviceStatus(Device.DeviceStatus.AVAILABLE);

                        Helper.updateDevice(d.getDeviceId(),
                                d.getDeviceName(),
                                Device.DeviceType.RELAY_MOD.toString(),
                                d.getDeviceStatus().toString(),
                                d.getDeviceMode().toString());
                    }
                    else if (d instanceof StepperMotor)
                    {
                        d.setDeviceMode(StepperMotor.StepperMotorMode.OFF);
                        d.setDeviceStatus(Device.DeviceStatus.AVAILABLE);

                        Helper.updateDevice(d.getDeviceId(),
                                d.getDeviceName(),
                                Device.DeviceType.STEP_MOTOR.toString(),
                                d.getDeviceStatus().toString(),
                                d.getDeviceMode().toString());
                    }
                }

                System.exit(0);
            }

            else if (command.equals("showclients"))
            {
                ClientManager[] clientConnections = serverManager.getClientConnections();

                String divider = " ---------------------------------------------------------------------------";

                System.out.println(divider);
                System.out.printf("%-12s %-22s %-16s %-22s %s %n",
                        "| ThreadId", "| Username", "| Role", "| Address", "|");
                System.out.println(divider);

                for (ClientManager cm: clientConnections)
                {
                    if(cm != null)
                    {
                        System.out.printf("%-12s %-22s %-16s %-22s %s %n",
                                "| " + cm.getThreadId(),
                                "| " + cm.getAuthenticatedUserName(),
                                "| " + cm.getAuthenticatedUserRole(),
                                "| " + cm.getAuthenticatedUserAddress(), "|");

                        System.out.println(divider);
                    }
                }
            }

            else
            {
                System.out.println("> [" + getDate() + "] Invalid command.");
            }
        }
    }

    /**
     * Gets the current date and time.
     */
    public static String getDate()
    {
        java.util.Date date = new java.util.Date();
        String dateString = date.toString();

        return dateString.substring(4,19);
    }

    private static void initializePins()
    {
        System.out.print("> [" + getDate() + "] Initializing RPi's GPIO Pins...");

        try
        {
            GpioController gpio = GpioFactory.getInstance();
            DeviceList devices = Helper.selectAllDevices();

            for (Device device : devices.getDevices())
            {
                for (int p : device.getDevicePins())
                {
                    GpioPinDigitalOutput pin =
                            gpio.provisionDigitalOutputPin(getGpioPin(p),
                                    device.getDeviceName(), PinState.LOW);

                    pin.setShutdownOptions(true, PinState.LOW);
                    pin.setState(PinState.LOW);
                    gpio.unprovisionPin(pin);
                }
            }
            System.out.print(" Success\n");
        }
        catch (UnsatisfiedLinkError e)
        {
            System.out.print(" Failure\n");
        }
    }
    /**
     * Converts Device.pin (int) to GPIO.pin (Pin).
     * @param x pin int from Device to be converted
     * @return GPIO Pin used for issuing commands
     */
    public static Pin getGpioPin(int x)
    {
        Pin resultPin = null;
        Pin[] p = RaspiPin.allPins();
        ArrayList<Pin> pins = new ArrayList<>(Arrays.asList(p));
        Collections.sort(pins);

        for (Pin pin : pins)
        {
            String pinString = pin.toString();

            if(Integer.toString(x).length() == 2)
            {
                if (pinString.substring(pinString.length() - 2).equals(Integer.toString(x)))
                {
                    resultPin = pin;
                    break;
                }
            }
            else if (Integer.toString(x).length() == 1)
            {
                if (pinString.substring(pinString.length() - 1).equals(Integer.toString(x)))
                {
                    resultPin = pin;
                    break;
                }
            }
        }
        return resultPin;
    }
}