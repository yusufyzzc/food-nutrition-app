package com.example.foodnutritionapp.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for NetworkManager class
 * Tests network connectivity and API initialization
 */
public class NetworkManagerTest {

    @Mock
    private Context mockContext;

    @Mock
    private ConnectivityManager mockConnectivityManager;

    @Mock
    private NetworkInfo mockNetworkInfo;

    private NetworkManager networkManager;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        when(mockContext.getApplicationContext()).thenReturn(mockContext);
        when(mockContext.getSystemService(Context.CONNECTIVITY_SERVICE))
                .thenReturn(mockConnectivityManager);
    }

    @Test
    public void getInstance_ReturnsSingletonInstance() {
        // Act
        NetworkManager instance1 = NetworkManager.getInstance(mockContext);
        NetworkManager instance2 = NetworkManager.getInstance(mockContext);

        // Assert
        assertSame("getInstance should return the same instance", instance1, instance2);
    }

    @Test
    public void isNetworkAvailable_WithConnectedNetwork_ReturnsTrue() {
        // Arrange
        when(mockConnectivityManager.getActiveNetworkInfo()).thenReturn(mockNetworkInfo);
        when(mockNetworkInfo.isConnected()).thenReturn(true);
        networkManager = NetworkManager.getInstance(mockContext);

        // Act
        boolean isAvailable = networkManager.isNetworkAvailable();

        // Assert
        assertTrue("Should return true when network is connected", isAvailable);
    }

    @Test
    public void isNetworkAvailable_WithDisconnectedNetwork_ReturnsFalse() {
        // Arrange
        when(mockConnectivityManager.getActiveNetworkInfo()).thenReturn(mockNetworkInfo);
        when(mockNetworkInfo.isConnected()).thenReturn(false);
        networkManager = NetworkManager.getInstance(mockContext);

        // Act
        boolean isAvailable = networkManager.isNetworkAvailable();

        // Assert
        assertFalse("Should return false when network is disconnected", isAvailable);
    }

    @Test
    public void isNetworkAvailable_WithNullNetworkInfo_ReturnsFalse() {
        // Arrange
        when(mockConnectivityManager.getActiveNetworkInfo()).thenReturn(null);
        networkManager = NetworkManager.getInstance(mockContext);

        // Act
        boolean isAvailable = networkManager.isNetworkAvailable();

        // Assert
        assertFalse("Should return false when NetworkInfo is null", isAvailable);
    }

    @Test
    public void getApi_ReturnsNonNullInstance() {
        // Arrange
        networkManager = NetworkManager.getInstance(mockContext);

        // Act & Assert
        assertNotNull("API instance should not be null", networkManager.getApi());
    }
}