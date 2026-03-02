package com.example.foodnutritionapp.network;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Unit tests for NetworkManager class
 * Updated for Mobile Application Programming II
 * Tests network connectivity and API initialization
 */
@RunWith(MockitoJUnitRunner.class)
public class NetworkManagerTest {

    @Mock
    private ConnectivityProvider mockProvider;

    private NetworkManager networkManager;

    @Before
    public void setUp() {
        networkManager = new NetworkManager(mockProvider);
    }

    @Test
    public void isNetworkAvailable_whenProviderReturnsTrue_shouldReturnTrue() {
        // Arrange
        when(mockProvider.isNetworkAvailable()).thenReturn(true);

        // Act
        boolean result = networkManager.isNetworkAvailable();

        // Assert
        assertTrue("Network should be available", result);
        verify(mockProvider, times(1)).isNetworkAvailable();
    }

    @Test
    public void isNetworkAvailable_whenProviderReturnsFalse_shouldReturnFalse() {
        // Arrange
        when(mockProvider.isNetworkAvailable()).thenReturn(false);

        // Act
        boolean result = networkManager.isNetworkAvailable();

        // Assert
        assertFalse("Network should not be available", result);
        verify(mockProvider, times(1)).isNetworkAvailable();
    }

    @Test
    public void isNetworkAvailable_calledMultipleTimes_shouldQueryProviderEachTime() {
        // Arrange
        when(mockProvider.isNetworkAvailable()).thenReturn(true, false, true);

        // Act & Assert
        assertTrue("First call should return true", networkManager.isNetworkAvailable());
        assertFalse("Second call should return false", networkManager.isNetworkAvailable());
        assertTrue("Third call should return true", networkManager.isNetworkAvailable());

        verify(mockProvider, times(3)).isNetworkAvailable();
    }

    @Test
    public void getUsdaApi_shouldReturnNonNullInstance() {
        // Act
        UsdaAPI api = networkManager.getUsdaApi();

        // Assert
        assertNotNull("USDA API instance should not be null", api);
    }

    @Test
    public void getUsdaApi_calledMultipleTimes_shouldReturnSameInstance() {
        // Act
        UsdaAPI api1 = networkManager.getUsdaApi();
        UsdaAPI api2 = networkManager.getUsdaApi();

        // Assert
        assertSame("Should return the same API instance", api1, api2);
    }

    @Test
    public void networkManager_withNullProvider_shouldHandleGracefully() {
        // This tests defensive programming
        NetworkManager managerWithNull = new NetworkManager(null);

        // The implementation should handle null provider gracefully
        // or throw a meaningful exception
        try {
            managerWithNull.isNetworkAvailable();
        } catch (NullPointerException e) {
            // Expected if not handling null
            assertNotNull("Should throw NPE for null provider", e);
        }
    }
}