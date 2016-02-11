package isd.model;

public class Message {

    private boolean isHeartbeat;
    private String timeReceived;

    private int lightSensorVal;
    private boolean pirSensorVal;

    // ---------------- GETTERS & SETTERS --------------------

    public void setPirSensorVal(boolean pirSensorVal) { this.pirSensorVal = pirSensorVal; }

    public void setLightSensorVal(int lightSensorVal) { this.lightSensorVal = lightSensorVal; }

    public void setTimeReceived(String timeReceived) { this.timeReceived = timeReceived; }

    public void setHeartbeat(boolean heartbeat) { isHeartbeat = heartbeat; }

    public boolean getPirSensorVal() { return pirSensorVal; }

    public int getLightSensorVal() { return lightSensorVal; }

    public String getTimeReceived() { return timeReceived; }

    public boolean isHeartbeat() { return isHeartbeat; }

    // -------------------------------------------------------

    /**
     * Default CONTRUCTOR
     */
    public Message(){

    }

    /**
     * CONSTRUCTOR
     * @param isHeartbeat boolean - Specifies if it is a control message from Arduino
     * @param pirSensorVal boolean - Value indicated by the PIR Sensor can be either 1 (movement present) or 0 (no movement)
     * @param lightSensorVal
     * @param timeReceived
     */
    public Message(boolean isHeartbeat, boolean pirSensorVal, int lightSensorVal, String timeReceived) {

        this.timeReceived = timeReceived;
        this.isHeartbeat = isHeartbeat;
        this.pirSensorVal = pirSensorVal;
        this.lightSensorVal = lightSensorVal;
    }

    /**
     * Returns the textual representation of a message
     * @Override
     * @return String
     */
    public String toString() {
        return "Message{" +
        		"isHeartbeat=" + isHeartbeat +
                "pirSensorVal=" + pirSensorVal +
                ", lightSensorVal=" + lightSensorVal +
                ", timeReceived='" + timeReceived + '}';
    }


    /**
     * Parses the textual representation of a message received from
     * Arduino board into a <code>Message</code> object
     * @param data <code>String</code>
     * @return <code>Message</code>
     */
    public static Message parse(String data){

        String[] splitMessage = data.split("[|]");
        Message message = new Message();
        message.setHeartbeat(false);
        for (String info:splitMessage) {
            String[] tmp = info.split(" ");
            if(tmp[0].toUpperCase().startsWith("P")){
                boolean pirSensorValue = Boolean.valueOf(tmp[1]);
                message.setPirSensorVal(pirSensorValue);
            }
            if (tmp[0].toUpperCase().startsWith("L")) {
                int lightSensorValue = Integer.parseInt(tmp[1]);
                message.setLightSensorVal(lightSensorValue);
            }
            if (tmp[0].toUpperCase().startsWith("H")) {
                int isHeartbeatMessage = Integer.parseInt(tmp[1]);
                message.setHeartbeat(true);
            }
        }
        return message;

    }

}
