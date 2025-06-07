package com.caiofeiria.planit.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static final String LOG_DIR = "logs";

    public static void logError(String message, Exception e) {
        try {
            LocalDate date = LocalDate.now();
            String fileName = date + ".txt";
            File dir = new File(LOG_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File logFile = new File(dir, fileName);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                writer.write("[" + timestamp + "] " + message);
                writer.newLine();
                if (e != null) {
                    writer.write("Exception: " + e.toString());
                    writer.newLine();
                    for (StackTraceElement element : e.getStackTrace()) {
                        writer.write("\tat " + element);
                        writer.newLine();
                    }
                }
                writer.newLine();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
