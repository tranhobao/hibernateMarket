package BLL;

import DAL.Vegetable;
import DAL.VegetableDAL;
import GUI.Mess;

import java.util.ArrayList;
import java.util.List;

public class VegetableBLL {

    private VegetableDAL vegetableDAL;
    private Mess mess;

    public VegetableBLL() {
        vegetableDAL = new VegetableDAL();
        mess = new Mess();
    }

    public List<Vegetable> getVegetableList() {
        return vegetableDAL.getVegetableList();
    }

    public List<Vegetable> getListWithCategory(int catagoryID) {
        return vegetableDAL.getVegetableList(catagoryID);
    }

    public List<Vegetable> getSearch(String textSearch, Integer select) {
        List<Vegetable> vegetables = new ArrayList<>();
        switch (select) {
            case 0:
                try {
                    Vegetable vegetable = getVegetable(Integer.parseInt(textSearch));
                    if (vegetable == null) {
                        mess.message("Search vegetable with id", String.format("NOT FOUND VEGETABLE WITH ID %s", textSearch));
                    }
                    else {
                        vegetables.add(vegetable);
                    }
                }
                catch (NumberFormatException e) {
                    mess.message("Search vegetable with id", String.format("id must be number %s", textSearch));
                }
                break;
            case 1:
                vegetables = getVegetableWithName(textSearch);
                if (vegetables.isEmpty()) {
                    mess.message("Search vegetable with name", String.format("NOT FOUND ANY VEGETABLE WITH NAME %s", textSearch));
                }
                break;
            default:
                mess.message("Search vegetable", String.format("Select ??? %s", select));

        }
        return vegetables;
    }

    private List<Vegetable> getVegetableWithName(String textSearch) {
        return vegetableDAL.getVegetableListWithName(textSearch);
    }

    private Vegetable getVegetable(Integer id) {
        return vegetableDAL.getVegetable(id);
    }

    public void save(Vegetable vegetable) {
        List<Vegetable> list = getVegetableList();
        vegetable.setVegetableID(list.get(list.size()-1).getVegetableID()+1);
        vegetableDAL.save(vegetable);
    }

    public void delete(Integer id) {
        Vegetable vegetable = getVegetable(id);
        vegetableDAL.delete(vegetable);
    }

    public void update(Vegetable vegetable) {
        vegetableDAL.update(vegetable);
    }

    public void updateAmount(Integer id, Integer quantity) {
        Vegetable vegetable = getVegetable(id);
        vegetable.setAmount(vegetable.getAmount()-quantity);
        update(vegetable);
    }
}
