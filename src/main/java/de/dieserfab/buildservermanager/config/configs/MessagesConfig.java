package de.dieserfab.buildservermanager.config.configs;

import de.dieserfab.buildservermanager.annotations.LoadMessage;
import de.dieserfab.buildservermanager.config.Config;
import de.dieserfab.buildservermanager.utilities.Logger;

import java.lang.reflect.Field;
import java.util.List;

public class MessagesConfig extends Config {
    public MessagesConfig() {
        super("messages");
    }

    public MessagesConfig populateMessages(Object object){
        LoadMessage annotation;
        for(Field field : object.getClass().getDeclaredFields()){
            annotation = field.getAnnotation(LoadMessage.class);
            if(annotation == null)
                continue;
            String path = annotation.path();
            field.setAccessible(true);
            Class fieldType = field.getType();
            try {
                if(fieldType == String.class) {
                    field.set(object, getConfig().getString(path));
                }else if(fieldType == List.class){
                    field.set(object,getConfig().getList(path));
                }
            }catch (IllegalAccessException e){
                Logger.l("eError while populating the Messages Class:"+e.getMessage());
            }
        }
        return this;
    }

}
