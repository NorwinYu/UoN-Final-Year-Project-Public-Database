package com.sarality.validation;

import android.app.Activity;
import android.view.View;

import com.sarality.error.ErrorCode;
import com.sarality.form.FormData;
import com.sarality.form.FormField;
import com.sarality.validation.error.ErrorMessage;
import com.sarality.validation.error.ErrorRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that defines the set of validators on a Form or set of Fields.
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class Validators {

  private final Map<String, List<FieldValidatorWrapper>> validatorListMap = new HashMap<>();
  private final Map<ErrorCode, List<ErrorMessage>> errorMessageMap = new HashMap<>();

  public Validators register(FormField field, Validator validator, ErrorMessage errorMessage) {
    return register(field.getName(), validator, errorMessage);
  }

  public Validators register(FieldValidator validator, ErrorMessage errorMessage) {
    return register(validator.getField(), validator, errorMessage);
  }

  public Validators register(ErrorCode errorCode, ErrorMessage errorMessage) {
    if (!errorMessageMap.containsKey(errorCode)) {
      errorMessageMap.put(errorCode, new ArrayList<ErrorMessage>());
    }
    errorMessageMap.get(errorCode).add(errorMessage);
    return this;
  }

  private Validators register(String fieldName, Validator validator, ErrorMessage errorMessage) {
    if (!validatorListMap.containsKey(fieldName)) {
      validatorListMap.put(fieldName, new ArrayList<FieldValidatorWrapper>());
    }
    List<FieldValidatorWrapper> validatorList = validatorListMap.get(fieldName);
    validatorList.add(new FieldValidatorWrapper(validator, errorMessage));
    return this;
  }

  public boolean validate(Activity activity, View contextView, FormData formFieldData) {
    boolean isValidForm = true;
    for (String fieldName : validatorListMap.keySet()) {
      boolean isValidField = validateField(activity, contextView, formFieldData, fieldName);
      isValidForm = isValidForm & isValidField;
    }
    return isValidForm;
  }

  public boolean validate(Activity activity, FormData formFieldData) {
    return validate(activity, null, formFieldData);
  }

  public boolean processErrors(Activity activity, View contextView, List<ErrorCode> errorCodes) {
    if (errorCodes != null && !errorCodes.isEmpty()) {
      for (ErrorCode errorCode : errorCodes) {
        List<ErrorMessage> messages = errorMessageMap.get(errorCode);
        if (messages != null && !messages.isEmpty()) {
          for (ErrorMessage message : messages) {
            displayError(activity, contextView, message);
          }
        } else {
          throw new RuntimeException("Error Code " + errorCode.getCode() + " cannot be displayed");
        }
      }
      return false;
    }
    return true;
  }

  public boolean processErrors(Activity activity, List<ErrorCode> errorCodes) {
    return processErrors(activity, null, errorCodes);
  }

  private boolean validateField(Activity activity, View contextView, FormData formFieldData, String fieldName) {
    boolean isValid = true;
    List<FieldValidatorWrapper> validatorList = validatorListMap.get(fieldName);
    for (FieldValidatorWrapper validator : validatorList) {
      if (!validator.getValidator().isValid(formFieldData)) {
        isValid = false;
        displayError(activity, contextView, validator.getErrorMessage());
        break;
      } else {
        resetError(activity, contextView, validator.getErrorMessage().getErrorRenderer());
      }
    }
    return isValid;
  }

  public void resetAll(Activity activity, View contextView) {
    for (List<ErrorMessage> messages : errorMessageMap.values()) {
      for (ErrorMessage message : messages) {
        resetError(activity, contextView, message.getErrorRenderer());
      }
    }
  }

  private void resetError(Activity activity, View contextView, ErrorRenderer renderer) {
    renderer.init(activity, contextView);
    renderer.resetError(activity);
  }

  private void displayError(Activity activity, View contextView, ErrorMessage errorMessage) {
    ErrorRenderer renderer = errorMessage.getErrorRenderer();
    renderer.init(activity, contextView);
    renderer.displayError(activity, errorMessage.getMessageResourceId());
  }
}
