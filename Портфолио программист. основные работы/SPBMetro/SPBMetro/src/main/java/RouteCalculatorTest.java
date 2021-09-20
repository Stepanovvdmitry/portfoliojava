import core.Line;
import core.Station;
import junit.framework.TestCase;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

public class RouteCalculatorTest extends TestCase
{
    StationIndex stationIndex;
    RouteCalculator routeCalculator;
    Line line1;
    Line line2;
    Line line3;
    Station station1;
    Station station2;
    Station station3;
    Station station4;
    Station station5;
    Station station6;
    Station station7;
    Station station8;

   @Before
   @Override
   public void setUp() throws Exception {
       stationIndex = new StationIndex();
       line1 = new Line(1, "первая");
       line2 = new Line(2, "вторая");
       line3 = new Line(3, "третья");
       station1 = new Station("станция первая", line1);
       station2 = new Station("станция вторая", line1);
       station3 = new Station("станция третья", line1);
       station4 = new Station("станция четвёртая", line2);
       station5 = new Station("станция пятая", line2);
       station6 = new Station("станция шестая", line3);
       station7 = new Station("станция седьмая", line3);
       station8 = new Station("станция восьмая", line3);


       stationIndex.addLine(line1);
        stationIndex.addLine(line2);
        stationIndex.addLine(line3);

        stationIndex.addStation(station1);
       line1.addStation(station1);


        stationIndex.addStation(station2);
        line1.addStation(station2);
        stationIndex.addStation(station3);
        line1.addStation(station3);
        stationIndex.addStation(station4);
        line2.addStation(station4);
        stationIndex.addStation(station5);
        line2.addStation(station5);
        stationIndex.addStation(station6);
        line3.addStation(station6);
       stationIndex.addStation(station7);
       line3.addStation(station7);
        stationIndex.addStation(station8);
       line3.addStation(station8);

        stationIndex.addConnection(Arrays.asList(station2,station5));
        stationIndex.addConnection(Arrays.asList(station4,station7));
       routeCalculator = new RouteCalculator(stationIndex);
    }



    public void testGetShortestRoute1()
    {

        List<Station> actual =  routeCalculator.getShortestRoute(stationIndex.getStation("станция первая"),stationIndex.getStation("станция восьмая"));
        List<Station> expected = Arrays.asList(station1,station2,station5,station4,station7,station8);
        assertEquals(expected,actual);
    }
    public void testGetShortestRoute2()
    {

        List<Station> actual =  routeCalculator.getShortestRoute(stationIndex.getStation("станция первая"),stationIndex.getStation("станция пятая"));
        List<Station> expected = Arrays.asList(station1,station2,station5);
        assertEquals(expected,actual);
    }

    public void testGetShortestRoute3()
    {

        List<Station> actual =  routeCalculator.getShortestRoute(stationIndex.getStation("станция первая"),stationIndex.getStation("станция третья"));
        List<Station> expected = Arrays.asList(station1,station2,station3);
        assertEquals(expected,actual);
    }

    public void testCalculateDuration()
    {
        double actual = RouteCalculator.calculateDuration(Arrays.asList(station1,station2,station5,station4,station7,station8));
        double expected = 14.5;
        assertEquals(expected,actual);
    }


}
