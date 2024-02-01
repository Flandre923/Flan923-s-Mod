package net.flandre923.flansmod.common.item.ball;

public enum MaterialSize {
    WOOD(3),
    STONE(4),
    IRON(5),
    DIAMOND(6);

    public final int size;

    private MaterialSize(int size){
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
