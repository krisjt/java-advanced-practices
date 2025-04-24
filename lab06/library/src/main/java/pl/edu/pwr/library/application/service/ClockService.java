package pl.edu.pwr.library.application.service;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class ClockService extends Clock {

    private final Instant startRealTime;
    private final Instant startVirtualTime;
    private final ZoneId zone;
    private final double scaleFactor;

    public ClockService(Instant startVirtualTime, ZoneId zone, double scaleFactor) {
        this.startRealTime = Instant.now();
        this.startVirtualTime = startVirtualTime;
        this.zone = zone;
        this.scaleFactor = scaleFactor;
    }

    @Override
    public ZoneId getZone() {
        return zone;
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return new ClockService(startVirtualTime, zone, scaleFactor);
    }

    @Override
    public Instant instant() {
        long realElapsedMillis = Instant.now().toEpochMilli() - startRealTime.toEpochMilli();
        long virtualElapsedMillis = (long) (realElapsedMillis * scaleFactor);
        return startVirtualTime.plusMillis(virtualElapsedMillis);
    }
}
