package com.example.app_war.model;


public class Terminal {

    private long id;

    private String terminalId;

    /**
     * linked to channel.identifier.
     */
    private String channelName;

    private String hash;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "[" + this.id + " - " + this.terminalId + "]";
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (!(o instanceof Terminal)) {
            return false;
        }

        Terminal t = (Terminal) o;

        if ((id != 0 && t.id != 0) && this.id == t.id) {
            return true;
        } else
            return (id == 0 || t.id == 0) && (this.terminalId != null) && this.terminalId.equals(t.terminalId);
    }

    @Override
    public int hashCode() {

        int result = 17;
        result = 31 * result + (int) (id ^ (id >>> 32));
        if (terminalId != null) {
            result = 31 * result + terminalId.hashCode();
        }

        return result;
    }
}
