package shikimori.record;

import java.util.concurrent.ConcurrentMap;

public class Block {
    private String name;
    private ConcurrentMap<String, Cover> covers;

    public ConcurrentMap<String, Cover> getCovers() {
        return covers;
    }

    public String getName() {
        return name;
    }

    public Block(String name,ConcurrentMap<String, Cover> covers){
        this.name = name;
        this.covers = covers;
    }
}
