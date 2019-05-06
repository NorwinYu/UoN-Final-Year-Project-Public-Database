package pl.put.boardgamemanager;

import java.util.List;

public class ListDTO<T> extends DTO {

    private List<T> values;

    public boolean validate() {
        if(values == null) {
            this.setErrorMessage("The given values is null");
            return false;
        }
        return true;
    }

    public List<T> getValues() {
        return values;
    }

    public void setValues(List<T> values) {
        this.values = values;
    }

}
