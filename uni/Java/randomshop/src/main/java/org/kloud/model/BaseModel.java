package org.kloud.model;

import javafx.scene.control.Button;
import javafx.util.Pair;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kloud.common.fields.Field;
import org.kloud.daos.BasicDAO;
import org.kloud.module.gui.components.BootstrapColumn;
import org.kloud.module.gui.components.BootstrapRow;
import org.kloud.utils.Logger;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.BooleanSupplier;

import static java.lang.Math.min;

/**
 * vase class for all storable objects
 */
@EqualsAndHashCode(doNotUseGetters = true)
public abstract class BaseModel implements Serializable {

    // Id used in getStoredClasses, a dummy id
    @EqualsAndHashCode.Exclude
    public static final long DUMMY_ID = 333;

    public final long id;

    protected BaseModel(long id) {
        this.id = id;
    }

    protected BaseModel() {
        id = System.nanoTime();
    }

    @NotNull
    public abstract List<Field<?>> getFields();

    public BootstrapRow loadReadonlyGui() {
        return loadEditableGui(null, null, null);
    }

    public <T extends BaseModel> BootstrapRow loadEditableGui(@Nullable Button validateButton,
                                                              @Nullable BasicDAO<T> dao,
                                                              @Nullable Runnable afterSave) {
        BootstrapRow row = new BootstrapRow();
        var fields = getFields().stream().filter(Field::isVisibleInUI).toList();
        List<BooleanSupplier> fxControlHandlers = new ArrayList<>(fields.size());

        boolean readonly = validateButton == null;

        for (var field : fields) {
            var fieldControl = field.getJavaFxControl(readonly, () -> Objects.requireNonNull(validateButton).setDisable(!hasChanges()));
            fxControlHandlers.add(fieldControl.getValue());
            row.addColumn(new BootstrapColumn(fieldControl.getKey()));
        }

        Logger.info("Loaded " + fields.size() + " fields for '" + this + "'");

        if (validateButton == null) {
            return row;
        }
        validateButton.setDisable(!hasChanges());
        validateButton.setOnAction(actionEvent -> {
            boolean isValid = true;
            for (var fxControlHandler : fxControlHandlers) {
                boolean fieldValid = fxControlHandler.getAsBoolean();
                isValid = isValid && fieldValid;
            }
            if (isValid) {
                //noinspection unchecked
                if (Objects.requireNonNull(dao).addOrUpdateObject((T) this)) {
                    if (afterSave != null) {
                        afterSave.run();
                    }
                    validateButton.setDisable(true);
                }
            }
        });

        return row;
    }

    public List<? extends ColumnDescriptor<?>> getColumnDescriptors() {
        return getFields().stream().map(Field::getColumnDescriptor).toList();
    }

    public Map<String, Pair<Object, Class<?>>> getFieldMap() {
        var fields = getFields();
        var mapping = new HashMap<String, Pair<Object, Class<?>>>(fields.size());
        for (var field : fields) {
            mapping.put(field.name, new Pair<>(field.get(), field.klass));
        }
        return mapping;
    }

    public boolean hasChanges() {
        for (var field : getFields()) {
            if (field.hasChanges()) {
                return true;
            }
        }
        return false;
    }

    public void markLatestVersionSaved() {
        for (var field : getFields()) {
            field.markLatestVersionSaved();
        }
    }

    public abstract String isSafeToDelete();

    public void postRead() {
        BaseModel cleanInstance;
        try {
            // NOTE: Calling the constructor with id is CRUCIAL because some
            // validators use the id and if not copied, it would be that of the cleanInstance
            // Another option would be co copy values from the current instance to the clean one and return the clean one
            cleanInstance = getClass().getDeclaredConstructor(long.class).newInstance(id);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        var fields = getFields();
        var cleanFields = cleanInstance.getFields();
        for (int i = 0; i < min(fields.size(), cleanFields.size()); i++) {
            fields.get(i).postRead(cleanFields.get(i));
        }
    }

    @Override
    public String toString() {
        return toStringInternal();
    }

    @NotNull
    protected abstract String toStringInternal();

    // TODO: Delete method which handles all linked values?
}
