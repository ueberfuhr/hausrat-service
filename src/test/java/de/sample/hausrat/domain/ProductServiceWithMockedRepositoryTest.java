package de.sample.hausrat.domain;

import de.sample.hausrat.domain.exceptions.ServiceException;
import de.sample.hausrat.domain.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;
import static pl.rzrz.assertj.reactor.Assertions.assertThat;
import static pl.rzrz.assertj.reactor.Assertions.assertThatThrownBy;

@SpringBootTest
class ProductServiceWithMockedRepositoryTest {

    @MockBean(answer = Answers.RETURNS_MOCKS)
    ProductRepository repo;
    @Autowired
    ProductService service;

    @Nested
    @DisplayName("Synchronous Invocations")
    class Sync {

        @DisplayName("When accessing the database occurs an exception, a ServiceException is thrown.")
        @Test
        void testServiceExceptionWrappingRuntimeException() {
            RuntimeException ex = new RuntimeException();
            when(repo.findAll()).thenThrow(ex);
            assertThatThrownBy(service::findAll)
              .isInstanceOf(ServiceException.class)
              .hasCause(ex);
        }

        @DisplayName("When accessing the database occurs a ServiceException, it is not wrapped.")
        @Test
        void testServiceExceptionNotWrapped() {
            ServiceException ex = new ServiceException();
            when(repo.findAll()).thenThrow(ex);
            assertThatThrownBy(service::findAll)
              .isInstanceOf(ServiceException.class)
              .hasNoCause();
        }

    }

    @Nested
    @DisplayName("Reactive Invocations")
    class Rx {

        @DisplayName("When accessing the database occurs an exception, a ServiceException is thrown.")
        @Test
        void testServiceExceptionWrappingRuntimeException() {
            RuntimeException ex = new RuntimeException();
            when(repo.findAll()).thenReturn(Flux.error(ex));
            assertThat(service.findAll())
              .sendsError(t -> assertThat(t)
                .isInstanceOf(ServiceException.class)
                .hasCause(ex));
        }

        @DisplayName("When accessing the database occurs a ServiceException, it is not wrapped.")
        @Test
        void testServiceExceptionNotWrapped() {
            ServiceException ex = new ServiceException();
            when(repo.findAll()).thenReturn(Flux.error(ex));
            assertThat(service.findAll())
              .sendsError(t -> assertThat(t)
                .isInstanceOf(ServiceException.class)
                .hasNoCause());
        }

    }

}
