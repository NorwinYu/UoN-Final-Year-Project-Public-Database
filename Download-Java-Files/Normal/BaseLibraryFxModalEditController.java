package edu.mum.library.view.base;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import edu.mum.library.common.NoAutoSettingGetting;
import edu.mum.library.view.util.UserObjectForView;
import javafx.fxml.FXML;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputControl;

public abstract class BaseLibraryFxModalEditController<T> extends BaseFxModalController {

	protected T entityDto;

	protected Set<Field> ignoreFieldList = new HashSet<>(
			FieldUtils.getFieldsListWithAnnotation(this.getClass(), NoAutoSettingGetting.class));

	protected void fromDtoToView() {

		if (entityDto != null) {
			loopFXMLFieldList(this.entityDto, (obj, name) -> {
				try {
					if (obj instanceof TextInputControl) {
						((TextInputControl) obj).setText(BeanUtils.getProperty(this.entityDto, name));
					} else if (obj instanceof Labeled) {
						((Labeled) obj).setText(BeanUtils.getProperty(this.entityDto, name));
					}
				} catch (Exception e) {
					// throw new RuntimeException("failed to do field setting",e);
					e.printStackTrace();
				}
			});
		}
	}

	protected void fromViewToDto() {
		fromViewToDto(entityDto);
	}

	protected void fromViewToDto(T entityDto) {
		loopFXMLFieldList(entityDto, (obj, name) -> {
			if (obj instanceof TextInputControl) {
				try {
					BeanUtils.setProperty(entityDto, name, ((TextInputControl) obj).getText());
				} catch (Exception e) {
					// throw new RuntimeException("failed to do field setting",e);
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		this.getCurrentStage().close();
	}

	private void loopFXMLFieldList(T entityDto, BiConsumer<Object, String> consumer) {
		if (entityDto != null) {
			try {
				List<Field> fieldList = FieldUtils.getFieldsListWithAnnotation(this.getClass(), FXML.class);
				for (Field field : fieldList) {
					if (shouldSkipe(field)) {
						continue;
					}
					field.setAccessible(true);
					Object obj = field.get(this);
					String name = field.getName();
					if (name.endsWith("Field")) {
						name = name.substring(0, name.length() - "Field".length());
					}
					consumer.accept(obj, name);
				}
			} catch (Exception e) {
				// throw new RuntimeException("failed to do field setting",e);
				e.printStackTrace();
			}
		}
	}

	// FieldUtils
	@Override
	public void postInit() {

		this.entityDto = provideEntity();

		fromDtoToView();

		postInitInChild();

	}

	protected void postInitInChild() {

	}

	@SuppressWarnings("unchecked")
	protected T provideEntity() {
		return (T) UserObjectForView.getParamFromStage(this.getCurrentStage());
	}

	protected boolean shouldSkipe(Field field) {
		return ignoreFieldList.contains(field);
	}
}
