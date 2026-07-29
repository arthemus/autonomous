package org.autonomous.faces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link RequestMultipartFilter}.
 * <p>
 * Only the non-multipart passthrough and lifecycle methods are exercised. The
 * real {@code ServletFileUpload.isMultipartContent} returns {@code false} for a
 * request whose method is not POST (the mock default), so the filter delegates
 * unchanged. The multipart parsing path requires a real request body and is
 * skipped.
 */
@DisplayName("RequestMultipartFilter")
class RequestMultipartFilterTest {

    @Test
    @DisplayName("init stores the filter config and destroy clears it")
    void initAndDestroyManageFilterConfig() throws Exception {
        // Arrange
        RequestMultipartFilter filter = new RequestMultipartFilter();
        FilterConfig config = mock(FilterConfig.class);

        // Act
        filter.init(config);

        // Assert
        assertThat(filter.filterConfig).isSameAs(config);

        // Act
        filter.destroy();

        // Assert
        assertThat(filter.filterConfig).isNull();
    }

    @Test
    @DisplayName("doFilter delegates to the chain unchanged for a non-HttpServletRequest")
    void doFilterPassesNonHttpRequestThrough() throws Exception {
        // Arrange
        RequestMultipartFilter filter = new RequestMultipartFilter();
        ServletRequest request = mock(ServletRequest.class);
        ServletResponse response = mock(ServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        // Act
        filter.doFilter(request, response, chain);

        // Assert
        verify(chain).doFilter(request, response);
    }

    @Test
    @DisplayName("doFilter delegates unchanged when the request is not multipart")
    void doFilterPassesNonMultipartRequestThrough() throws Exception {
        // Arrange
        RequestMultipartFilter filter = new RequestMultipartFilter();
        HttpServletRequest request = mock(HttpServletRequest.class);
        ServletResponse response = mock(ServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        // Act
        filter.doFilter(request, response, chain);

        // Assert
        verify(chain).doFilter(request, response);
    }
}
