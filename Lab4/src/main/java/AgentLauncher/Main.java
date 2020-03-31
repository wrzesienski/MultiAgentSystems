package AgentLauncher;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.ControllerException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting launcher app");
        FileInputStream fis;
        Properties property = new Properties();
        try {
            System.out.println("Parsing config");
            fis = new FileInputStream(args.length>0?args[0]:"config.properties");
            property.load(fis);
            System.out.println("Config OK");
        } catch (IOException e) {
            System.err.println("ОШИБКА: файл конфигурации не найден!");
            return;
        }

        jade.core.Runtime runtime = jade.core.Runtime.instance();
        System.out.println("Connecting to JADE");

        ProfileImpl profile = createProfile(property);
        ContainerController container = runtime.createMainContainer(profile);
        System.out.println("Connected to JADE");

        try {
            AgentController ag = container.createNewAgent(
                    property.getProperty("ag.name","LauncherX"),
                    property.getProperty("ag.class","AgentLauncher.LauncherAgent"),
                    new Object[]{property.getProperty("xml.filepath", "config.xml")});// arguments

            // здесь импортируем в xml файлы из excel
            ImportFromExcel.importLoad(property.getProperty("xml.filepath"));
            ImportFromExcel.importPower(property.getProperty("xml.filepath"));

            ag.start();
            System.out.println("Launcher agent has been created");
        } catch (ControllerException e) {
            System.out.println("Невозможно созадать агента лончера: " + e.getMessage());
        }
    }

    private static ProfileImpl createProfile(Properties property) {
        ProfileImpl profile = new ProfileImpl();
        profile.setParameter(Profile.CONTAINER_NAME, property.getProperty("ag.container", "Container"));
        profile.setParameter(Profile.MAIN_HOST, property.getProperty("ag.host", "127.0.0.1"));
        profile.setParameter(Profile.MAIN_PORT, property.getProperty("ag.port", "1099"));
        profile.setParameter(Profile.LOCAL_PORT, property.getProperty("ag.local-port", "1201"));
        if (property.getProperty("ag.gui", "false").equals("true")){
            profile.setParameter("gui", "true");
        }
        if (property.containsKey("ag.services")) {
            profile.setParameter("services", property.getProperty("ag.services", ""));
        }
        if (property.containsKey("ag.mtp")){
            profile.setParameter("mtps", property.getProperty("ag.mtp",""));
        }
        return profile;
    }
}
