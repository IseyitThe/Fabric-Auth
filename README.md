# Fabric-Auth
Minecraft Fabric HWID Auth System with Socket Server

Simple HWID Authentication System for Minecraft Fabric mods.

## Usage:
- Change Hwid list in `server/SocketAuthServer` `loadHwidListFromWebsite("https://example.com/HWID.txt");`
- Build Project
- Run `server.jar` with java -jar on machine/server
- Add try method in `onInitializeClient` (with getHWID method) to own mod
- Change `localhost` to ur machine/server ip

## Version
1.20.4 Fabric
## Build
- with JDK 17 `gradle build`
