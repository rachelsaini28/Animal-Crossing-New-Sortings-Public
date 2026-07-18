package animalcrossing;

public class Creature {
    
    private char creatureType;
    private String creatureName;

    public Creature(char type, String name){
        this.creatureType = type;
        this.creatureName = name;
    }

    public char getType(){
        return this.creatureType;
    }

    public void setType(char newType){
        this.creatureType = newType;
    }

    public String getName(){
        return this.creatureName;
    }

    public void setName(String name){
        this.creatureName = name;
    }

    @Override
    public boolean equals(Object object){
        if (object instanceof Creature){
            Creature creature = (Creature) object;
            if (creatureType == creature.getType()){
                if (creatureName.equals(creature.getName())){
                    return true;
                }
            }
        }
        return false;
    }
    
}
