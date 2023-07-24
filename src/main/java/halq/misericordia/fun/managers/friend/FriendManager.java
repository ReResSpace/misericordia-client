package halq.misericordia.fun.managers.friend;

import java.util.ArrayList;
import java.util.List;

/**
 * @author accessmodifier364
 * @since 24-Nov-2021
 */

public class FriendManager {

    public static List<Friend> friends = new ArrayList<>();

    public static boolean isFriend(String name) {
        return friends.stream().anyMatch(friend -> friend.username.equalsIgnoreCase(name));
    }

    public static Friend getFriendObject(String name) {
        return new Friend(name);
    }

    public static class Friend {
        String username;

        public Friend(String username) {
            this.username = username;
        }

        public String getUsername() {
            return this.username;
        }
    }
}
