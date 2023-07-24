package halq.misericordia.fun.core.modulecore;

/**
 * @author accessmodifier364
 * @since 28-Nov-2021
 */
public enum Category {

    COMBAT("Combat"),
    EXPLOITS("Exploits"),
    MISCELLANEOUS("Miscellaneous"),
    MOVEMENT("Movement"),
    RENDER("Render"),
    WORLD("World"),
    OTHER("Client");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
