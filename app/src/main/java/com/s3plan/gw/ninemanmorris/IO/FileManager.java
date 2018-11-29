package com.s3plan.gw.ninemanmorris.IO;

import android.app.Activity;
import android.content.Context;
import android.media.JetPlayer;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileManager {


    /**
     * Writes and object the a file of a separate thread.
     */
    public static class Write implements Runnable {
        private Activity activity;
        private Object object;
        private String fileName;
        public Write(Activity activity, Object object, String fileName) {
            this.activity = activity;
            this.object = object;
            this.fileName = fileName;
        }
        @Override
        public void run() {
            if (object != null) {
                FileOutputStream outputStream = null;
                ObjectOutputStream objectOutputStream;
                try {
                    outputStream = activity.openFileOutput(fileName, Context.MODE_PRIVATE);
                    objectOutputStream = new ObjectOutputStream(outputStream);
                    objectOutputStream.writeObject(object);
//                    outputStream.write(object.toString().getBytes());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static void writeSync(Activity activity, Object object, String fileName) {
        if (object != null) {
            FileOutputStream outputStream = null;
            ObjectOutputStream objectOutputStream;
            try {
                outputStream = activity.openFileOutput(fileName, Context.MODE_PRIVATE);
                objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(object);
//                    outputStream.write(object.toString().getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Reads Synchronously from a file.
     */
    public static Result readFile(Activity activity, String fileName) {
        FileManager.Result result = null;
        FileInputStream inputStream = null;
        Object j = null;
        try {
            inputStream = activity.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
//            Reader r = new InputStreamReader(inputStream);
//            BufferedReader br = new BufferedReader(r);
            j = objectInputStream.readObject();
//            SaveHandler.SaveFile j = (SaveHandler.SaveFile)objectInputStream.readObject();
            if (j != null)
                result = new FileManager.Result(j);
            else
                result = new FileManager.Result(new NullPointerException());

        } catch (EOFException e) {
            if (j != null)
                result = new FileManager.Result(j);
            else
                result = new FileManager.Result(new NullPointerException());
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
            result = new FileManager.Result(e);
        } catch (IOException e) {
            e.printStackTrace();
            result = new FileManager.Result(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            result = new FileManager.Result(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;

    }

    public static class Result {
        public Object msg;
        Exception ex;
        public Result(Object msg) {
            this.msg = msg;
            ex = null;
        }
        public Result(Exception ex) {
            this.ex = ex;
            msg = null;
        }
    }
}
