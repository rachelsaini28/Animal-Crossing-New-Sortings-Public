package animalcrossing;

public class Catalog {
    
    private String name;
    private int price;

    public Catalog(String name, int price){
        this.name = name;
        this.price = price;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public int getPrice(){
        return price;
    }

    @Override
    public boolean equals(Object object){
        if (object instanceof Catalog){
            Catalog item = (Catalog) object;
            if (name.equals(item.getName())){
                if (price == item.getPrice()){
                    return true;
                }
            }
        }
        return false;
    }

}
