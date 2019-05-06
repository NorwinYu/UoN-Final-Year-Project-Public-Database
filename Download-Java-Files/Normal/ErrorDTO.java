package model.DTO;

import utils.constants.ErrorType;

public class ErrorDTO {
    public boolean isError;
    public ErrorType errorType;
    public String message;

    public ErrorDTO(ErrorType _errorType, String _message) {
        isError = true;
        errorType = _errorType;
        message = _message;
    }
}
