# How to start
`mvn clean protobuf:compile-custom compile`

## Server
1. Start `AvailableZonesServer` main method from `server` module
2. Enjoy

## Client
Client part using `spring-shell` to interact with user
1. Start `App.java` from `client` module
2. type `help` to get list of commands
3. to get any result you can type: `get --cpus 1 --ram 1 --disk 40 --vport 4 --sriov --location "some location"`
> All arguments are mandatory (--sriov it's boolean so you can omit it to false value)

## Extra information
`AvailableZonesGrpc` and all sub-classes are auto generated from your .proto file

