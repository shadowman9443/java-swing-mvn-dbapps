package com.java.montu;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  public static void main(final String[] args) {
    DbHelper.getInstance().init();
    DbHelper.getInstance().registerShutdownHook();

    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        Main.LOGGER.debug("Starting application");

        final Application app = new Application();
        app.setTitle("Simple Java Database Swing Application");
        app.setSize(800, 600);
        app.setLocationRelativeTo(null);
        app.setDefaultCloseOperation(Application.EXIT_ON_CLOSE);
        app.setVisible(true);

        // app.addWindowListener(new WindowAdapter() {
        // @Override
        // public void windowClosing(WindowEvent e) {
        // Main.LOGGER.info("Done");
        // DbHelper.getInstance().close();
        // }
        // });
      }
    });
  }
}
