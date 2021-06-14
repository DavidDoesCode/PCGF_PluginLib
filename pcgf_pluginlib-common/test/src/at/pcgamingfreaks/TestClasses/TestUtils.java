/*
 * Copyright (C) 2016 MarkusWME
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package at.pcgamingfreaks.TestClasses;

import org.mockito.internal.util.reflection.FieldSetter;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;

public class TestUtils {
    private static Map<String, Object> fields;

    public static void initReflection() {
        fields = new TreeMap<>();
    }

    public static Field setAccessible(Class clazz, Object object, String name, Object value) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            FieldSetter.setField(object, field, value);
            return field;
        } catch (Exception ignored) {
            return null;
        }
    }

    public static void set(Field field, Object object, Object value) {
        if (object == null && field != null) {
            object = field.getClass();
        }
        FieldSetter.setField(object, field, value);
    }

    @SuppressWarnings("SpellCheckingInspection")
    public static void setUnaccessible(Field field, Object object, boolean isFinal) {
        try {
            if (field != null && object != null) {
                FieldSetter.setField(object, field, fields.get(field.getName()));
                fields.remove(field.getName());
                field.setAccessible(false);
            }
        } catch (Exception ignored) { }
    }
}