package me.grpc.poc;

import me.grpc.poc.AvailableZonesProto.AvailableZonesInput;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class Commands {
    private final AvailableZonesClient client = new AvailableZonesClient("localhost", AvailableZonesServer.PORT);

    @ShellMethod(key = "get", value = "Get available zones")
    public String sendCommand(
            int cpus,
            int ram,
            int disk,
            int vports,
            boolean sriov,
            String location
    ) {
        return client.getAvailableZones(AvailableZonesInput.newBuilder()
                .setCpus(cpus)
                .setRam(ram)
                .setDisk(disk)
                .setVports(vports)
                .setSriov(sriov)
                .setLocation(location)
                .build()).toString();
    }
}
