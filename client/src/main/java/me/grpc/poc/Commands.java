package me.grpc.poc;

import me.grpc.poc.AvailableZonesProto.AvailableZonesInput;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class Commands {
    private final AvailableZonesClient client = new AvailableZonesClient("localhost", AvailableZonesServer.PORT);

    @ShellMethod(key = "get", value = "Get available zones")
    public String sendCommand(
            @ShellOption int cpus,
            @ShellOption int ram,
            @ShellOption int disk,
            @ShellOption int vport,
            @ShellOption boolean sriov,
            @ShellOption String location
    ) {
        return client.getAvailableZones(AvailableZonesInput.newBuilder()
                .setCpus(cpus)
                .setRam(ram)
                .setDisk(disk)
                .setVports(vport)
                .setSriov(sriov)
                .setLocation(location)
                .build()).toString();
    }
}
