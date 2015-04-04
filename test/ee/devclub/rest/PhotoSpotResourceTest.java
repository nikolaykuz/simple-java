package ee.devclub.rest;

public class PhotoSpotResourceTest {
    /*PhotoSpotResource resource = new PhotoSpotResource();

    @Before
    public void initMocks() throws Exception {
        resource.repo = mock(PhotoSpotRepository.class, RETURNS_DEEP_STUBS);
    }

    @Test
    public void resourceLimitedNumberOfSpotsInRepo() throws Exception {
        resource.maxSpots = 10;
        PhotoSpot photoSpot = mock(PhotoSpot.class);
        when(resource.repo.getAllSpots()).thenReturn(nCopies(15, photoSpot));

        assertEquals(10, resource.getAllSpots().size());
    }

    @Test
    public void newPhotoSpotsArePersisted() throws Exception {
        PhotoSpot photoSpot = new PhotoSpot("Aegna island", "WWI defence structures", new Location(59.583771f, 24.749720f));
        resource.newPhotoSpot(photoSpot);

        ArgumentCaptor<PhotoSpot> captor = ArgumentCaptor.forClass(PhotoSpot.class);
        verify(resource.repo).persist(captor.capture());

        PhotoSpot spot = captor.getValue();
        assertThat(spot.getName(), is("Aegna island"));
        assertThat(spot.getDescription(), is("WWI defence structures"));
        assertThat(spot.getLocation(), is(new Location(59.583771f, 24.749720f)));
    }*/
}
