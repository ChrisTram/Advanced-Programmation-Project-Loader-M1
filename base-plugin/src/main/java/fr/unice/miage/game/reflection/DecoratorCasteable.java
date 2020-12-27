package fr.unice.miage.game.reflection;

import java.lang.reflect.Field;

public interface DecoratorCasteable {
    default <T extends DecoratorCasteable> T getInstance(Class<T> tClass) {
        if(tClass.isInstance(this))
            return tClass.cast(this);
        Class<?> superClass = this.getClass();
        while (!superClass.equals(Object.class)) {
            for (Field field : superClass.getDeclaredFields())
                if (field.isAnnotationPresent(Decorate.class) && DecoratorCasteable.class.isAssignableFrom(field.getType())) {
                    field.setAccessible(true);
                    try {
                        return ((DecoratorCasteable) field.get(this)).getInstance(tClass);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            superClass = superClass.getSuperclass();
        }
        return null;
    }
}
