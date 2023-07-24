package halq.misericordia.fun.core.eventcore;

import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * @author Halq
 * @since 30/05/2023 at 12:17
 */

public class MisericordiaEvents extends Event {

    Stage stage;

    public MisericordiaEvents() {}

    public MisericordiaEvents(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return this.stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        this.setCanceled(false);
    }

    public enum Stage {
        PRE,
        POST
    }
}
