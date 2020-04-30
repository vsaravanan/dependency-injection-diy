package saravan.didiy;


import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Di {

    private static Map<Class, Object> mapSingle = new HashMap<>();
    public static Map< ObjectKey, Object> mapProto = new HashMap<>();

    private static DIFactory dif = new DIFactory();

    static {
        // Populate mapSingle during startup
        try {
            Reflections reflections = new Reflections("");
            Set< Class< ? > > comps = reflections.getTypesAnnotatedWith((Class< ? extends Annotation >) Singleton.class);
            for (Class< ? > implementationClass : comps) {
                String classname = implementationClass.getName();
                Object another = null;
                another = dif.getBean(implementationClass);
                mapSingle.put(implementationClass, another);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // getBean for singleton
    public synchronized Object getBean(Class theInterface) {
        return mapSingle.get(theInterface);
    }

    // getBean for prototype
    public synchronized Object getBean(ObjectKey key)  {
        Object obj = mapProto.get(key);
        if (obj == null) {
            obj = dif.getBean(key);
            mapProto.put(key, obj);
        }

        return obj;
    }

    // added common getBean for both singleton and prototype
    public synchronized Object getBean(String... params) {

        ObjectKey key = new ObjectKey();

        int i = 0;


        for (String param : params) {
            i++;
            if (i == 1) {

                key.type = param;
                String packageName = this.getClass().getPackage().getName();
                if (!key.type.startsWith(packageName)) {
                    key.type = packageName + "." + param;
                }

            } else if (i == 2) {
                key.param = param;
            }

        }

        if (key.param == null) {
            // if param is null it is assumed that it is going to be singleton type
            try {
                Class< ? > clazz = Class.forName(key.type);
                return getBean(clazz);
            } catch (ClassNotFoundException e) {
                throw new BeanNotFoundException("Unable to instantiate Singleton Class " + key.type);
            }

        }


        Object obj = mapProto.get(key);
        if (obj == null) {
            obj = dif.getBean(key);
            mapProto.put(key, obj);
        }


        return obj;

    }

    // simply created just for junit
    public int numberOfProtoObjects() {
        return mapProto.size();
    }

    // empty mapProto
    public void clearProto() {
        mapProto.clear();
    }

}

class DIFactory {
    // Singleton Factory
    public Object getBean(Class theInterface) {
        try {

            Class<?> clazz = Class.forName(theInterface.getName());

            Method method = clazz.getMethod("getInstance");
            Object another = method.invoke(null);
            another.hashCode(); // throw npe
            attachAutowiredBeans(another);

            return another;
        } catch (ReflectiveOperationException e) {
            throw new BeanNotFoundException("Unable to instantiate Singleton Class " + theInterface.getName());
        }
    }

    // Prototype Factory
    public Object getBean(ObjectKey key)  {
        try {
            Class<?> clazz = Class.forName(key.type);

            Constructor constructor = clazz.getConstructor(String.class);
            Object another = constructor.newInstance(key.param);
            another.hashCode(); // throw npe
            attachAutowiredBeans(another);

            return another;
        } catch (ReflectiveOperationException e) {
            throw new BeanNotFoundException("Unable to instantiate Prototype Class " + key.type + " with param (" + key.param + ") ");
        }
    }

    // applicable for both Singleton and Prototype
    public void attachAutowiredBeans(Object another) throws IllegalAccessException {
        Field[] fields = another.getClass().getDeclaredFields();
        for (Field f  : fields) {
            if (f.getAnnotationsByType(Autowired.class).length > 0 ) {
                Object autowireBean =  getBean(f.getType());
                if ( autowireBean == null ) {
                    throw new BeanNotFoundException("Bean " + f.getType().getName() + " not found " );
                }
                f.set(another, autowireBean);
            }

        }
    }


}
