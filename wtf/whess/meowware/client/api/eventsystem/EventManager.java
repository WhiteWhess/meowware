package wtf.whess.meowware.client.api.eventsystem;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class EventManager {
    private static final Map<Class<? extends Event>, FlexibleArray<MethodData>> REGISTRY_MAP = new HashMap<Class<? extends Event>, FlexibleArray<MethodData>>();

    private EventManager() {
    }

    public static void register(Object object) {
        Method[] methodArray = object.getClass().getDeclaredMethods();
        int n = methodArray.length;
        int n2 = 0;
        while (n2 < n) {
            Method method = methodArray[n2];
            if (!EventManager.isMethodBad(method)) {
                EventManager.register(method, object);
            }
            ++n2;
        }
    }

    public static void register(Object object, Class<? extends Event> eventClass) {
        Method[] methodArray = object.getClass().getDeclaredMethods();
        int n = methodArray.length;
        int n2 = 0;
        while (n2 < n) {
            Method method = methodArray[n2];
            if (!EventManager.isMethodBad(method, eventClass)) {
                EventManager.register(method, object);
            }
            ++n2;
        }
    }

    public static void unregister(Object object) {
        for (FlexibleArray<MethodData> dataList : REGISTRY_MAP.values()) {
            for (MethodData data : dataList) {
                if (!data.source.equals(object)) continue;
                dataList.remove(data);
            }
        }
        EventManager.cleanMap(true);
    }

    public static void unregister(Object object, Class<? extends Event> eventClass) {
        if (REGISTRY_MAP.containsKey(eventClass)) {
            for (MethodData data : REGISTRY_MAP.get(eventClass)) {
                if (!data.source.equals(object)) continue;
                REGISTRY_MAP.get(eventClass).remove(data);
            }
            EventManager.cleanMap(true);
        }
    }

    private static void register(Method method, Object object) {
        Class<? extends Event> indexClass = (Class<? extends Event>) method.getParameterTypes()[0];
        final MethodData data = new MethodData(object, method, method.getAnnotation(EventTarget.class).value());
        if (!data.target.isAccessible()) {
            data.target.setAccessible(true);
        }
        if (REGISTRY_MAP.containsKey(indexClass)) {
            if (!REGISTRY_MAP.get(indexClass).contains(data)) {
                REGISTRY_MAP.get(indexClass).add(data);
                EventManager.sortListValue(indexClass);
            }
        } else {
            REGISTRY_MAP.put(indexClass, new FlexibleArray<MethodData>(){
                private static final long serialVersionUID = 666L;
                {
                    this.add(data);
                }
            });
        }
    }

    public static void removeEntry(Class<? extends Event> indexClass) {
        Iterator<Map.Entry<Class<? extends Event>, FlexibleArray<MethodData>>> mapIterator = REGISTRY_MAP.entrySet().iterator();
        while (mapIterator.hasNext()) {
            if (!mapIterator.next().getKey().equals(indexClass)) continue;
            mapIterator.remove();
            break;
        }
    }

    public static void cleanMap(boolean onlyEmptyEntries) {
        Iterator<Map.Entry<Class<? extends Event>, FlexibleArray<MethodData>>> mapIterator = REGISTRY_MAP.entrySet().iterator();
        while (mapIterator.hasNext()) {
            if (onlyEmptyEntries && !mapIterator.next().getValue().isEmpty()) continue;
            mapIterator.remove();
        }
    }

    private static void sortListValue(Class<? extends Event> indexClass) {
        FlexibleArray<MethodData> sortedList = new FlexibleArray<MethodData>();
        byte[] byArray = Priority.VALUE_ARRAY;
        int n = Priority.VALUE_ARRAY.length;
        int n2 = 0;
        while (n2 < n) {
            byte priority = byArray[n2];
            for (MethodData data : REGISTRY_MAP.get(indexClass)) {
                if (data.priority != priority) continue;
                sortedList.add(data);
            }
            ++n2;
        }
        REGISTRY_MAP.put(indexClass, sortedList);
    }

    private static boolean isMethodBad(Method method) {
        return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(EventTarget.class);
    }

    private static boolean isMethodBad(Method method, Class<? extends Event> eventClass) {
        return EventManager.isMethodBad(method) || !method.getParameterTypes()[0].equals(eventClass);
    }

    public static FlexibleArray<MethodData> get(Class<? extends Event> clazz) {
        return REGISTRY_MAP.get(clazz);
    }
}