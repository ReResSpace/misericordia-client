package halq.misericordia.fun.events;

import halq.misericordia.fun.core.eventcore.MisericordiaEvents;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;


@Cancelable
public class PacketEvent extends MisericordiaEvents {

    Packet<?> packet;

    public PacketEvent(Packet<?> packet, Stage stage) {
        super(stage);
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return this.packet;
    }

    public static class PacketSendEvent extends PacketEvent {
        public PacketSendEvent(Packet<?> packet, Stage stage) {
            super(packet, stage);
        }
    }

    public static class PacketReceiveEvent extends PacketEvent {
        public PacketReceiveEvent(Packet<?> packet, Stage stage) {
            super(packet, stage);
        }
    }
}

