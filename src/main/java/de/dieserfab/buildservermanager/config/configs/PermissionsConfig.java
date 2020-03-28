package de.dieserfab.buildservermanager.config.configs;

import de.dieserfab.buildservermanager.annotations.LoadMessage;
import de.dieserfab.buildservermanager.annotations.LoadPermission;
import de.dieserfab.buildservermanager.config.Config;
import de.dieserfab.buildservermanager.utilities.Logger;

import java.lang.reflect.Field;

public class PermissionsConfig extends Config {
    public PermissionsConfig() {
        super("permissions");
    }

    public PermissionsConfig populatePermissions(Object object) {
        LoadPermission annotation;
        for (Field field : object.getClass().getDeclaredFields()) {
            annotation = field.getAnnotation(LoadPermission.class);
            if (annotation == null)
                continue;
            String path = annotation.path();
            field.setAccessible(true);
            Class fieldType = field.getType();
            try {
                if (fieldType == String.class) {
                    field.set(object, getConfig().getString(path));
                }
            } catch (IllegalAccessException e) {
                Logger.l("eError while populating the Messages Class:" + e.getMessage());
            }
        }
        return this;
    }
}
