package edwinotten.philipshue.models;

/**
 * Created by Edwin on 23-9-2014.
 */
public class Lamp {

    public LampState state;
    public String type;
    public String name;
    public String modelId;
    public String swversion;

    public Lamp(LampState state, String type, String name, String modelId, String swversion) {
        this.state = state;
        this.type = type;
        this.name = name;
        this.modelId = modelId;
        this.swversion = swversion;
    }

    public void turnOn() {
        // TODO - implement Lamp.turnOn
        throw new UnsupportedOperationException();
    }

    public void turnOff() {
        // TODO - implement Lamp.turnOff
        throw new UnsupportedOperationException();
    }

    public void setColor() {
        // TODO - implement Lamp.setColor
        throw new UnsupportedOperationException();
    }

}