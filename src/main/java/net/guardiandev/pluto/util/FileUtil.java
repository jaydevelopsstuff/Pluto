package net.guardiandev.pluto.util;

import java.io.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class FileUtil {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    //
    // ASYNC
    //

    public void writeAppendAsync(String content, String file) {
        writeAppendAsync(content, new File(file));
    }

    public void writeAppendAsync(String content, File file) {
        executor.execute(() -> {
            try {
                FileWriter writer = new FileWriter(file, true);
                writer.write(content);
                writer.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void writeAsync(String content, String file) {
        writeAsync(content, new File(file));
    }

    public void writeAsync(String content, File file) {
        executor.execute(() -> {
            try {
                FileWriter writer = new FileWriter(file, false);
                writer.write(content);
                writer.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void clearAsync(String file) {
        clearAsync(new File(file));
    }

    public void clearAsync(File file) {
        executor.execute(() -> {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(file);
            } catch(FileNotFoundException e) {
                e.printStackTrace();
            }
            if(writer == null) return;
            writer.print("");
            writer.close();
        });
    }

    public void deleteAsync(String file) {
        deleteAsync(new File(file));
    }

    public void deleteAsync(File file) {
        executor.execute(() -> {
            file.delete();
        });
    }

    public void writeAppendAsync(String content, String file, Consumer<Boolean> callback) {
        writeAppendAsync(content, new File(file), callback);
    }

    public void writeAppendAsync(String content, File file, Consumer<Boolean> callback) {
        executor.execute(() -> {
            try {
                FileWriter writer = new FileWriter(file, true);
                writer.write(content);
                writer.close();
                callback.accept(true);
            } catch(IOException e) {
                e.printStackTrace();
                callback.accept(false);
            }
        });
    }

    public void writeAsync(String content, String file, Consumer<Boolean> callback) {
        writeAsync(content, new File(file), callback);
    }

    public void writeAsync(String content, File file, Consumer<Boolean> callback) {
        executor.execute(() -> {
            try {
                FileWriter writer = new FileWriter(file, false);
                writer.write(content);
                writer.close();
                callback.accept(true);
            } catch(IOException e) {
                e.printStackTrace();
                callback.accept(false);
            }
        });
    }

    public void clearAsync(String file, Consumer<Boolean> callback) {
        clearAsync(new File(file), callback);
    }

    public void clearAsync(File file, Consumer<Boolean> callback) {
        executor.execute(() -> {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(file);
            } catch(FileNotFoundException e) {
                e.printStackTrace();
            }
            if(writer == null) {
                callback.accept(false);
                return;
            }
            writer.print("");
            writer.close();
            callback.accept(true);
        });
    }

    public void deleteAsync(String file, Consumer<Boolean> callback) {
        deleteAsync(new File(file), callback);
    }

    public void deleteAsync(File file, Consumer<Boolean> callback) {
        executor.execute(() -> {
            callback.accept(file.delete());
        });
    }

    public void shutdown() {
        executor.shutdown();
    }
}
