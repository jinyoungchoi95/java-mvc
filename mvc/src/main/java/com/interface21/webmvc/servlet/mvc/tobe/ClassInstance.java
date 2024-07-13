package com.interface21.webmvc.servlet.mvc.tobe;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class ClassInstance {

    private final Class<?> clazz;
    private final Object instance;

    public ClassInstance(Class<?> clazz) {
        this.clazz = clazz;
        this.instance = createNoArgInstance(clazz);
    }

    private Object createNoArgInstance(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor()
                    .newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException("클래스가 아닌 추상클래스, 인터페이스는 생성자를 만들 수 없습니다.");
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("클래스의 생성자에 접근할 수 없습니다.");
        } catch (InvocationTargetException e) {
            throw new IllegalStateException("생성자에서 예외가 발생했습니다.", e);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("지원하는 생성자가 존재하지 않습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassInstance that = (ClassInstance) o;
        return Objects.equals(clazz, that.clazz);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(clazz);
    }
}
