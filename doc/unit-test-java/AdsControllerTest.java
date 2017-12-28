package controller;

import com.michelin.pmu.domain.bo.central.Ads;
import com.michelin.pmu.domain.bo.service.AdsService;
import com.michelin.pmu.webservices.util.Error;
import com.michelin.pmu.webservices.util.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Unit Tests for Ads Controller
 * Created by fp11597 on 19/05/2017.
 */
@RunWith(PowerMockRunner.class)
public class AdsControllerTest {

    @Mock
    private Ads ads1;

    @Mock
    private Ads ads2;

    @Mock
    private AdsService adsService;

    @Mock
    private HttpServletRequest request;

    @Spy
    private AdsController adsController = new AdsController();

    private List<Ads> ads;

    @Before
    public void init() throws IOException {

        ServiceUtils.defaultMockGetters("adsPid", ads1, ads2);

        when(ads1.getPhysicalid()).thenReturn("adsPid1");
        when(ads2.getPhysicalid()).thenReturn("adsPid2");

        when(ads1.getName()).thenReturn("adsName1");
        when(ads2.getName()).thenReturn("adsName2");

        doReturn(adsService).when(adsController).initService(request, AdsService.class);

        ads = Collections.singletonList(ads1);
    }

    @Test
    public void findADSFromName() throws IOException {

        when(adsService.getAllAdsWhereName("name")).thenReturn(ads);

        Response resp = adsController.findADSFromName(request, "name");

        verify(adsController, times(1)).initService(request, AdsService.class);
        verify(adsService, times(1)).getAllAdsWhereName("name");

        //region assert
        assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());
        assertTrue(resp.getEntity() instanceof String);

        String respAds = (String) resp.getEntity();
        assertEquals("adsPid1", respAds);
    }

    @Test
    public void findADSFromName_ADS_Not_Found() throws IOException {

        when(adsService.getAllAdsWhereName("name")).thenReturn(Collections.emptyList());

        Response resp = adsController.findADSFromName(request, "name");

        verify(adsController, times(1)).initService(request, AdsService.class);
        verify(adsService, times(1)).getAllAdsWhereName("name");

        //region assert
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), resp.getStatus());
        assertTrue(resp.getEntity() instanceof Error);
        Error error = (Error) resp.getEntity();
        assertEquals(Message.ADS_NOT_FOUND.toString(), error.getMessage());
    }

    @Test
    public void getAdsFromSiteAndActivity() throws IOException {

        when(adsService.getRelatedADS("site", "activity")).thenReturn(Optional.of(ads1));

        Response resp = adsController.getAdsFromSiteAndActivity(request, "site", "activity");

        verify(adsController, times(1)).initService(request, AdsService.class);
        verify(adsService, times(1)).getRelatedADS("site", "activity");

        //region assert
        assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());
        assertTrue(resp.getEntity() instanceof String);

        String respAds = (String) resp.getEntity();
        assertEquals("adsName1", respAds);
    }

    @Test
    public void getAdsFromSiteAndActivity_ADS_Not_Found() throws IOException {

        when(adsService.getRelatedADS("site", "activity")).thenReturn(Optional.empty());

        Response resp = adsController.getAdsFromSiteAndActivity(request, "site", "activity");

        verify(adsController, times(1)).initService(request, AdsService.class);
        verify(adsService, times(1)).getRelatedADS("site", "activity");

        //region assert
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), resp.getStatus());
        assertTrue(resp.getEntity() instanceof Error);
        Error error = (Error) resp.getEntity();
        assertEquals(Message.ADS_NOT_FOUND.toString(), error.getMessage());
    }

    @Test
    public void getAdsFromNif() throws IOException {

        when(adsService.getAllAdsWhereNif("nif")).thenReturn(ads);

        Response resp = adsController.getAdsFromNif(request, "nif");

        verify(adsController, times(1)).initService(request, AdsService.class);
        verify(adsService, times(1)).getAllAdsWhereNif("nif");

        //region assert
        assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());
        assertTrue(resp.getEntity() instanceof Map);

        Map respAds = (Map) resp.getEntity();
        assertTrue(respAds.containsKey("physicalid"));
        assertEquals("adsPid1", respAds.get("physicalid"));
    }

    @Test
    public void getAdsFromNif_ADS_Not_Found() throws IOException {

        when(adsService.getAllAdsWhereNif("nif")).thenReturn(Collections.emptyList());

        Response resp = adsController.getAdsFromNif(request, "nif");

        verify(adsController, times(1)).initService(request, AdsService.class);
        verify(adsService, times(1)).getAllAdsWhereNif("nif");

        //region assert
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), resp.getStatus());
        assertTrue(resp.getEntity() instanceof Error);
        Error error = (Error) resp.getEntity();
        assertEquals(Message.ADS_NOT_FOUND.toString(), error.getMessage());
    }
}
