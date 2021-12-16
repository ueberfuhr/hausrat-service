package de.sample.hausrat.domain;

import de.sample.hausrat.domain.exceptions.ServiceException;
import de.sample.hausrat.domain.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureTestDatabase
class ProductServiceWithMockedRepositoryTest {

    @MockBean(answer = Answers.CALLS_REAL_METHODS)
    ProductRepository repo;
    @Autowired
    ProductService service;

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
