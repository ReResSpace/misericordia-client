package halq.misericordia.fun.gui.igui.components;

public interface Component {

    void render(int mouseX, int mouseY);

    void mouseClicked(int mouseX, int mouseY, int mouseButton);

    void mouseReleased(int mouseX, int mouseY, int state);
}
