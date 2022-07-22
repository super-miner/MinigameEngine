package me.super_miner_1.minigameengine;

public class Id {
    private final boolean isNumber;
    private int numberId;
    private String stringId;

    public Id(int id) {
        this.isNumber = true;
        this.numberId = id;
    }

    public Id(String id) {
        this.isNumber = false;
        this.stringId = id;
    }

    public boolean equals(Id id) {
        if (isNumber) {
            return id.isNumber && numberId == id.numberId;
        }
        else {
            return !id.isNumber && stringId.equals(id.stringId);
        }
    }
}
