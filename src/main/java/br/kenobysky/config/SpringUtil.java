package br.kenobysky.config;

import org.springframework.context.ConfigurableApplicationContext;

/**
 *
 * @author Shadows
 */
public class SpringUtil {

    private static ConfigurableApplicationContext context;

    public SpringUtil() {
    }

    public SpringUtil(ConfigurableApplicationContext context) {
        SpringUtil.context = context;
    }

    static {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println("Adding ShutdownHook...");

                Runtime.getRuntime().addShutdownHook(new Thread() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("\n---------------------------");
                            System.out.println("Disposing Spring...");
                            System.out.println("---------------------------\n");
                            dispose();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                });
            }
        });

        t.setDaemon(true);
        t.start();
    }

    public static void dispose() {
        try {
            context.stop();
            context.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
