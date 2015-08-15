package com.wpadvert.kernel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.content.Context;
import dalvik.system.DexClassLoader;

/**
 * Created by SCWANG on 2015-07-26.
 */
public class DynnamicJar {

    private static final String JAR_NAME = "2.4.3.txt";
    private static DexClassLoader mDexClassLoader;

    public static String jarpath(Context context){
        return context.getApplicationInfo().dataDir+"/"+JAR_NAME+".jar";
    }

    public static boolean initize(Context context) {
        File dynamicjar = new File(jarpath(context));
        if (!dynamicjar.exists()){
            try{
                final InputStream in = context.getAssets().open(JAR_NAME);
                byte[] bytes = new byte[in.available()];
                in.read(bytes);
                in.close();

                FileOutputStream out = new FileOutputStream(dynamicjar);
                out.write(bytes);
                out.close();
            } catch (Throwable e){
                e.printStackTrace();
            }
        }else{
//            dynamicjar.delete();
        }
        return dynamicjar.exists();
    }

    public static DexClassLoader getDexClassLoader(Context context) {
        File dynamicjar = new File(jarpath(context));
        if (mDexClassLoader == null && dynamicjar.exists()){
            String dexOutputDir = context.getApplicationInfo().dataDir;
            return mDexClassLoader = new DexClassLoader(dynamicjar.getAbsolutePath(),
                    dexOutputDir,
                    null,
                    context.getClassLoader());
        }
        return mDexClassLoader;
    }

    public static Class<?> loadClass(Context context,String classname) {
        DexClassLoader cl = getDexClassLoader(context);
        if (cl != null){
            try {
                return cl.loadClass(classname);
            } catch (Throwable e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Object newInstance(Context context,String classname) {
        DexClassLoader cl = getDexClassLoader(context);
        if (cl != null){
            try {
                final Class<?> aClass = cl.loadClass(classname);
                return aClass.newInstance();
            } catch (Throwable e){
            }
        }
        return null;
    }
}
