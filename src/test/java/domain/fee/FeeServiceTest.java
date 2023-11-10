package domain.fee;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static domain.fee.FeeFactory.mockFeeList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FeeServiceTest {
    private FeeService feeService;
    private FeeReader mockRepository;

    @Before
    public void setUp() {
        mockRepository = mock(FeeReader.class);
        feeService = new FeeService(mockRepository);
    }

    @Test
    public void constructorWithNoParameters_WhenClassIsIstantiated_ReaderRepositoryIsCreated() {
        feeService = new FeeService();
        assertNotNull(feeService.getFeeReaderForTesting());
    }

    @Test
    public void constructorWithNoParameters_WhenClassIsIstantiated_RootNodeIsCreated() {
        FeeNode root = feeService.getRootNodeForTesting();

        assertNotNull(root);
        assertEquals("Total", root.getName());
    }

    @Test
    public void constructorWithParameters_WhenClassIsIstantiated_ReaderRepositoryIsCreated() {
        assertNotNull(feeService.getFeeReaderForTesting());
    }

    @Test
    public void constructorWithParameters_WhenClassIsIstantiated_RootNodeIsCreated() {
        feeService = new FeeService();

        FeeNode root = feeService.getRootNodeForTesting();

        assertNotNull(root);
        assertEquals("Total", root.getName());
        assertFalse(root.hasNoChild());
    }

    @Test
    public void getNodeTotalFees_WhenRequestedWithLayers_ReturnsTotalFeeForSpecificLayers() {
        List<Fee> mockFees = new ArrayList<>(mockFeeList(25));
        mockFees.add(new Fee("Fee1", "Fee1", "", "Test Development", "Coding", "Cat3", "TypeA", 3, 5.00));
        mockFees.add(new Fee("Fee2", "Fee2", "", "Test Development", "Quality Assurance", "Cat2", "TypeC", 2, 2.25));

        when(mockRepository.fetchFeeList()).thenReturn(mockFees);

        feeService = new FeeService(mockRepository);

        double result = feeService.getNodeTotalFees("Test Development", "Coding");
        assertEquals(15.00, result, 0.01);
    }

    @Test
    public void getNodeTotalFees_WhenRequestedWithNoLayers_ReturnsTotalFeeForAllLayers() {
        List<Fee> mockFees = Arrays.asList(
                new Fee("Fee1", "Fee1", "", "Test Development", "Coding", "Cat2", "TypeB", 1, 15.00),
                new Fee("Fee2", "Fee2", "", "Test Operations", "Human Resources", "Cat3", "TypeA", 3, 5.00),
                new Fee("Fee3", "Fee3", "", "Test Operations", "Performance Management", "Cat2", "TypeC", 4, 5.00)

        );

        when(mockRepository.fetchFeeList()).thenReturn(mockFees);

        feeService = new FeeService(mockRepository);

        double result = feeService.getNodeTotalFees();
        assertEquals(50.00, result, 0.01);
    }

    @Test
    public void getNodeTotalFees_WhenRequestedWithInexistentLayers_ReturnsZero() {
        List<Fee> mockFees = mockFeeList(25);

        when(mockRepository.fetchFeeList()).thenReturn(mockFees);

        feeService = new FeeService(mockRepository);

        double result = feeService.getNodeTotalFees("Management", "Project Managers");
        assertEquals(0, result, 0);
    }

    @Test
    public void getNodeList_WhenRequestedWithLayers_ReturnedListMatchesNumberOfDefinedNodes() {
        List<Fee> mockFees = new ArrayList<>(mockFeeList(25));
        mockFees.addAll(Arrays.asList(
                new Fee("Fee1", "Fee1", "", "Test Development", "Coding", "Cat3", "TypeA", 3, 10.00),
                new Fee("Fee2", "Fee2", "", "Test Development", "Quality Assurance", "Cat2", "TypeC", 3, 5.00)
        ));

        when(mockRepository.fetchFeeList()).thenReturn(mockFees);

        feeService = new FeeService(mockRepository);

        List<FeeNode> nodes = feeService.getNodeList("Test Development");

        assertEquals(2, nodes.size());
    }

    @Test
    public void getNodeList_WhenRequestedWithNoLayers_ReturnedListMatchesNumberOfAllExistentDepartments() {
        List<Fee> mockFees = Arrays.asList(
                new Fee("Fee1", "Fee1", "", "Operations", "Coding", "Cat1", "TypeA", 3, 10.00),
                new Fee("Fee2", "Fee2", "", "Marketing", "ABM", "Cat3", "TypeA", 4, 12.00),
                new Fee("Fee3", "Fee3", "", "Sales", "Pre Sales", "Cat3", "TypeC", 7, 6.00),
                new Fee("Fee4", "Fee4", "", "Support", "Tier 1", "Cat2", "TypeC", 2, 7.00)

        );

        when(mockRepository.fetchFeeList()).thenReturn(mockFees);

        feeService = new FeeService(mockRepository);

        List<FeeNode> nodes = feeService.getNodeList();

        assertEquals(4, nodes.size());
    }

    @Test
    public void getNodeList_WhenRequestedWithInexistentLayers_ReturnedListMatchesNumberOfAllExistentDepartments() {
        List<Fee> mockFees = Arrays.asList(
                new Fee("Fee1", "Fee1", "", "Operations", "Coding", "Cat1", "TypeA", 3, 10.00),
                new Fee("Fee2", "Fee2", "", "Marketing", "ABM", "Cat3", "TypeA", 4, 12.00),
                new Fee("Fee3", "Fee3", "", "Sales", "Pre Sales", "Cat3", "TypeC", 7, 6.00),
                new Fee("Fee4", "Fee4", "", "Support", "Tier 1", "Cat2", "TypeC", 2, 7.00)
        );

        when(mockRepository.fetchFeeList()).thenReturn(mockFees);

        feeService = new FeeService(mockRepository);

        List<FeeNode> nodes = feeService.getNodeList("Inexistent", "Layers");

        assertEquals(4, nodes.size());
    }

    @Test
    public void getFeesByLayer_WhenRequestedThe3BiggestFeesForExistentLayer_ListOnlyRequestedLayersFees() {
        List<Fee> expected = Arrays.asList(
                new Fee("Fee1", "Fee1", "", "Test Department", "First Cat", "Cat1", "TypeA", 1, 55.00),
                new Fee("Fee2", "Fee2", "", "Test Department", "First Cat", "Cat3", "TypeA", 1, 35.00),
                new Fee("Fee3", "Fee3", "", "Test Department", "First Cat", "Cat3", "TypeC", 1, 25.00)
        );
        List<Fee> extra = Arrays.asList(
                new Fee("Fee4", "Fee4", "", "Test Department", "First Cat", "Cat3", "TypeC", 1, 15.00),
                new Fee("Fee5", "Fee5", "", "Test Department", "Second Cat", "Cat3", "TypeC", 1, 30.00)
        );
        List<Fee> mockFees = new ArrayList<>(mockFeeList(25));
        mockFees.addAll(expected);
        mockFees.addAll(extra);
        Collections.shuffle(mockFees);

        when(mockRepository.fetchFeeList()).thenReturn(mockFees);

        feeService = new FeeService(mockRepository);

        List<Fee> resultList = feeService.getFeesByLayer(3, true, "Test Department", "First Cat");

        assertEquals(resultList, expected);
    }

    @Test
    public void getFeesByLayer_WhenRequestedThe3BiggestFeesForInexistentLayer_ListFeesFromAllLayers() {
        List<Fee> expected = Arrays.asList(
                new Fee("Fee1", "Fee1", "", "Test Development", "Coding", "Cat1", "TypeA", 1, 55.00),
                new Fee("Fee2", "Fee2", "", "Test Marketing", "ABM", "Cat3", "TypeA", 1, 35.00),
                new Fee("Fee3", "Fee3", "", "Test Operations", "Human Resources", "Cat3", "TypeC", 1, 32.00)

        );
        List<Fee> mockFees = new ArrayList<>(Arrays.asList(
                new Fee("Fee4", "Fee4", "", "Test Sales", "Sales Engineering", "Cat3", "TypeB", 1, 15.00),
                new Fee("Fee5", "Fee5", "", "Test Support", "Tier 1", "Cat3", "TypeA", 1, 30.00),
                new Fee("Fee6", "Fee6", "", "Test Support", "Tier 2", "Cat1", "TypeA", 1, 28.00),
                new Fee("Fee7", "Fee7", "", "Test Sales", "Sales Engineering", "Cat2", "TypeA", 1, 13.00),
                new Fee("Fee8", "Fee8", "", "Test Development", "Quality Assurance", "Cat2", "TypeC", 1, 22.00),
                new Fee("Fee9", "Fee9", "", "Test Support", "Tier 3", "Cat3", "TypeC", 1, 17.00)
        ));
        mockFees.addAll(expected);
        Collections.shuffle(mockFees);

        when(mockRepository.fetchFeeList()).thenReturn(mockFees);

        feeService = new FeeService(mockRepository);

        List<Fee> resultList = feeService.getFeesByLayer(3, true, "Inexistent", "Layer");
        assertEquals(resultList, expected);
    }

    @Test
    public void getFeesByLayer_WhenRequestedThe3BiggestFeesWithNoLayerDefined_ListFeesFromAllLayers() {
        List<Fee> expected = Arrays.asList(
                new Fee("Fee1", "Fee1", "", "Test Development", "Coding", "Cat1", "TypeA", 1, 55.00),
                new Fee("Fee2", "Fee2", "", "Test Marketing", "ABM", "Cat3", "TypeA", 1, 35.00),
                new Fee("Fee3", "Fee3", "", "Test Operations", "Human Resources", "Cat3", "TypeC", 1, 32.00)
        );
        List<Fee> mockFees = new ArrayList<>(Arrays.asList(
                new Fee("Fee4", "Fee4", "", "Test Sales", "Sales Engineering", "Cat3", "TypeC", 1, 15.00),
                new Fee("Fee5", "Fee5", "", "Test Support", "Tier 1", "Cat3", "TypeC", 1, 30.00),
                new Fee("Fee6", "Fee6", "", "Test Support", "Tier 2", "Cat3", "TypeC", 1, 28.00),
                new Fee("Fee6", "Fee6", "", "Test Sales", "Sales Engineering", "Cat3", "TypeC", 1, 13.00),
                new Fee("Fee6", "Fee6", "", "Test Development", "Quality Assurance", "Cat3", "TypeC", 1, 22.00),
                new Fee("Fee6", "Fee6", "", "Test Support", "Tier 3", "Cat3", "TypeC", 1, 17.00)
        ));
        mockFees.addAll(expected);
        Collections.shuffle(mockFees);

        when(mockRepository.fetchFeeList()).thenReturn(mockFees);

        feeService = new FeeService(mockRepository);

        List<Fee> resultList = feeService.getFeesByLayer(3, true);
        assertEquals(resultList, expected);
    }

    @Test
    public void getFeesByLayer_WhenRequestedThe4SmallerFeesForExistentLayer_ListOnlyRequestedLayersFees() {
        List<Fee> expected = Arrays.asList(
                new Fee("Fee1", "Fee1", "", "Test Department", "First Cat", "Cat1", "TypeA", 1, 15.00),
                new Fee("Fee2", "Fee2", "", "Test Department", "First Cat", "Cat3", "TypeA", 1, 25.00),
                new Fee("Fee3", "Fee3", "", "Test Department", "First Cat", "Cat3", "TypeC", 1, 34.00),
                new Fee("Fee6", "Fee6", "", "Test Department", "First Cat", "Cat2", "TypeB", 1, 35.00)
        );
        List<Fee> extra = Arrays.asList(
                new Fee("Fee4", "Fee4", "", "Test Department", "First Cat", "Cat3", "TypeC", 1, 36.00),
                new Fee("Fee5", "Fee5", "", "Test Department", "Second Cat", "Cat3", "TypeC", 1, 14.00)
        );
        List<Fee> mockFees = new ArrayList<>(mockFeeList(25));
        mockFees.addAll(expected);
        mockFees.addAll(extra);
        Collections.shuffle(mockFees);

        when(mockRepository.fetchFeeList()).thenReturn(mockFees);

        feeService = new FeeService(mockRepository);

        List<Fee> resultList = feeService.getFeesByLayer(4, false, "Test Department", "First Cat");

        assertEquals(resultList, expected);
    }

    @Test
    public void getFeesByLayer_WhenRequestedThe4SmallerFeesForInexistentLayer_ListFeesFromAllLayers() {
        List<Fee> expected = Arrays.asList(
                new Fee("Fee1", "Fee1", "", "Test Development", "Coding", "Cat1", "TypeA", 1, 4.50),
                new Fee("Fee2", "Fee2", "", "Test Marketing", "ABM", "Cat3", "TypeA", 1, 7.75),
                new Fee("Fee3", "Fee3", "", "Test Operations", "Human Resources", "Cat3", "TypeC", 1, 10.25)
        );
        List<Fee> mockFees = new ArrayList<>(Arrays.asList(
                new Fee("Fee4", "Fee4", "", "Test Sales", "Sales Engineering", "Cat1", "TypeC", 1, 15.00),
                new Fee("Fee5", "Fee5", "", "Test Support", "Tier 1", "Cat2", "TypeB", 1, 30.00),
                new Fee("Fee6", "Fee6", "", "Test Support", "Tier 2", "Cat3", "TypeA", 1, 28.00),
                new Fee("Fee7", "Fee7", "", "Test Sales", "Sales Engineering", "Cat1", "TypeC", 1, 13.00),
                new Fee("Fee8", "Fee8", "", "Test Development", "Quality Assurance", "Cat2", "TypeB", 1, 22.00),
                new Fee("Fee9", "Fee9", "", "Test Support", "Tier 3", "Cat3", "TypeA", 1, 17.00)
        ));
        mockFees.addAll(expected);
        Collections.shuffle(mockFees);

        when(mockRepository.fetchFeeList()).thenReturn(mockFees);

        feeService = new FeeService(mockRepository);

        List<Fee> resultList = feeService.getFeesByLayer(3, false, "Inexistent", "Layer");
        assertEquals(resultList, expected);
    }

    @Test
    public void getFeesByLayer_WhenRequestedThe4SmallerFeesWithNoLayerDefined_ListFeesFromAllLayers() {
        List<Fee> expected = Arrays.asList(
                new Fee("Fee1", "Fee1", "", "Test Development", "Coding", "Cat1", "TypeA", 1, 4.50),
                new Fee("Fee2", "Fee2", "", "Test Marketing", "ABM", "Cat3", "TypeA", 1, 7.75),
                new Fee("Fee3", "Fee3", "", "Test Operations", "Human Resources", "Cat3", "TypeC", 1, 10.25)
        );
        List<Fee> mockFees = new ArrayList<>(Arrays.asList(
                new Fee("Fee4", "Fee4", "", "Test Sales", "Sales Engineering", "Cat1", "TypeC", 1, 15.00),
                new Fee("Fee5", "Fee5", "", "Test Support", "Tier 1", "Cat2", "TypeB", 1, 30.00),
                new Fee("Fee6", "Fee6", "", "Test Support", "Tier 2", "Cat3", "TypeA", 1, 28.00),
                new Fee("Fee7", "Fee7", "", "Test Sales", "Sales Engineering", "Cat1", "TypeC", 1, 13.00),
                new Fee("Fee8", "Fee8", "", "Test Development", "Quality Assurance", "Cat2", "TypeB", 1, 22.00),
                new Fee("Fee9", "Fee9", "", "Test Support", "Tier 3", "Cat3", "TypeA", 1, 17.00)
        ));
        mockFees.addAll(expected);
        Collections.shuffle(mockFees);

        when(mockRepository.fetchFeeList()).thenReturn(mockFees);

        feeService = new FeeService(mockRepository);

        List<Fee> resultList = feeService.getFeesByLayer(3, false);
        assertEquals(resultList, expected);
    }

    @Test
    public void getFeesByLayer_WhenRequestedThe4SmallerFeesWithNoLayerDefined_LaistFeesFromAllLayers() {
        List<Fee> expected = Arrays.asList(
                new Fee("Fee1", "Fee1", "", "Test Development", "Coding", "Cat1", "TypeA", 1, 4.50),
                new Fee("Fee2", "Fee2", "", "Test Marketing", "ABM", "Cat3", "TypeA", 1, 7.75),
                new Fee("Fee3", "Fee3", "", "Test Operations", "Human Resources", "Cat3", "TypeC", 1, 10.25)
        );
        List<Fee> mockFees = new ArrayList<>(Arrays.asList(
                new Fee("Fee4", "Fee4", "", "Test Sales", "Sales Engineering", "Cat1", "TypeC", 1, 15.00),
                new Fee("Fee5", "Fee5", "", "Test Support", "Tier 1", "Cat2", "TypeB", 1, 30.00),
                new Fee("Fee6", "Fee6", "", "Test Support", "Tier 2", "Cat3", "TypeA", 1, 28.00),
                new Fee("Fee7", "Fee7", "", "Test Sales", "Sales Engineering", "Cat1", "TypeC", 1, 13.00),
                new Fee("Fee8", "Fee8", "", "Test Development", "Quality Assurance", "Cat2", "TypeB", 1, 22.00),
                new Fee("Fee9", "Fee9", "", "Test Support", "Tier 3", "Cat3", "TypeA", 1, 17.00)
        ));
        mockFees.addAll(expected);
        Collections.shuffle(mockFees);

        when(mockRepository.fetchFeeList()).thenReturn(mockFees);

        feeService = new FeeService(mockRepository);

        List<Fee> resultList = feeService.getFeesByLayer(3, false);
        assertEquals(resultList, expected);
    }

    @Test
    public void getInstance_WhenInstanceIsNull_CreatesSingletonInstance() {
        FeeService.cleanInstance();
        feeService = new FeeService();

        boolean isInstanceNull = FeeService.isInstanceNull();

        assertTrue(isInstanceNull);

        FeeService singletonInstance = FeeService.getInstance();
        isInstanceNull = FeeService.isInstanceNull();

        assertNotNull(singletonInstance);
        assertFalse(isInstanceNull);
    }
}
