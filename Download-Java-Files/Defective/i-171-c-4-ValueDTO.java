package pl.put.boardgamemanager;

public class ValueDTO<T> extends DTO {

    private T value;

    public boolean validate() {
        if(value == null) {
            this.setErrorMessage("The given value is null");
            return false;
        }
        return true;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

}
