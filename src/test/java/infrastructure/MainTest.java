package infrastructure;

import domain.fee.FeeService;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class MainTest {

    @Test
    public void main_WhenApplicationRuns_CallDemonstrativeMethodsForTheProject() {
        FeeService mockService = mock(FeeService.class);
        FeeService.setInstance(mockService);

        Main.main(new String[]{});

        verify(mockService, times(2)).getNodeTotalFees(any(String[].class));
        verify(mockService).getNodeList();
        verify(mockService).getFeesByLayer(eq(5), eq(true), any(String[].class));
        verify(mockService).getFeesByLayer(eq(3), eq(false), any(String[].class));
    }

}
