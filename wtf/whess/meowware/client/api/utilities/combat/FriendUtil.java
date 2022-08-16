package wtf.whess.meowware.client.api.utilities.combat;

import lombok.Getter;
import net.minecraft.entity.Entity;
import wtf.whess.meowware.client.api.utilities.Utility;

import java.util.ArrayList;

public final class FriendUtil extends Utility {
    @Getter
    private static final ArrayList<Entity> friendList = new ArrayList<>();

    public static void addFriend(Entity entity) {
        if (!friendList.contains(entity))
            friendList.add(entity);
    }

    public static boolean isFriend(Entity entity) {
        return !friendList.contains(entity);
    }

    public static void removeFriend(Entity entity) {
        friendList.remove(entity);
    }
}
