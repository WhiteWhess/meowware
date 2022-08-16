package wtf.whess.meowware.client.api.eventsystem;

import java.lang.reflect.InvocationTargetException;

/**
 * @author DarkMagician6
 *
 * @since 2014-07-07
 */
public abstract class Event {
    private boolean cancelled;

    public Event call() {
        this.cancelled = false;
        Event.call(this);
        return this;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean state) {
        this.cancelled = state;
    }

    private static final void call(Event event) {
        FlexibleArray<MethodData> dataList = EventManager.get(event.getClass());
        if (dataList != null) {
            for (MethodData data : dataList) {
                try {
                    data.target.invoke(data.source, event);
                }
                catch (IllegalAccessException e) {
                    System.out.println("Can't invoke '" + data.target.getName() + "' because it's not accessible.");
                }
                catch (IllegalArgumentException e) {
                    System.out.println("Can't invoke '" + data.target.getName() + "' because the parameter/s don't match.");
                }
                catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public enum State {
        PRE,
        POST;
    }
}