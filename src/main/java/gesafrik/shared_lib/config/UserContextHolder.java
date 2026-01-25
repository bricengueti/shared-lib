package gesafrik.shared_lib.config;


import gesafrik.shared_lib.config.UserGlobal;

public class UserContextHolder {
    private static final ThreadLocal<UserGlobal> context = new ThreadLocal<>();

    public static void set(UserGlobal user) {
        context.set(user);
    }

    public static UserGlobal get() {
        return context.get();
    }

    public static void clear() {
        context.remove();
    }
}
