package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ControllerInstanceTest {

    @Test
    void 인터페이스를_생성하려하는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new ControllerInstance(Abstract.class))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("클래스가 아닌 추상클래스, 인터페이스는 생성자를 만들 수 없습니다.");
    }

    @Test
    void 생성자가_접근불가능한_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new ControllerInstance(PrivateAccess.class))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("클래스의 생성자에 접근할 수 없습니다.");
    }

    @Test
    void 생성자에서_예외가_발생하는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new ControllerInstance(Invocation.class))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("생성자에서 예외가 발생했습니다.");
    }

    @Test
    void 지원생성자가_없는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new ControllerInstance(NoSuch.class))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("지원하는 생성자가 존재하지 않습니다.");
    }

    @Test
    void RequestMapping메소드만_추출한다() throws NoSuchMethodException {
        List<Method> actual = new ControllerInstance(SuccessController.class).getRequestMappingMethods();
        assertThat(actual).containsExactly(SuccessController.class.getMethod("success"));
    }

    @Controller
    private static class SuccessController {
        public SuccessController() {
        }

        @RequestMapping
        public void success() {
        }

        public void fail() {
        }
    }

    private static abstract class Abstract {

        public Abstract() {
        }
    }

    private static class PrivateAccess {

        private PrivateAccess() {
        }
    }

    private static class Invocation {

        public Invocation() {
            throw new RuntimeException();
        }
    }

    private static class NoSuch {

        private final int value;

        public NoSuch(int value) {
            this.value = value;
        }
    }
}