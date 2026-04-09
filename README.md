### docker-compose.yml
```yaml
services:
  command-wrapper:
    image: inxmi/command-wrapper:latest
    restart: unless-stopped
    
    environment:
      TOKEN: <TOKEN> # DISCORD_BOT_TOKEN
      INSTANCES: |- # List of docker compose stacks -> path:channel_id
        <PATH>:<CHANNEL_ID>
        
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock # necessary to execute docker compose command sin container for host machine
      - /data:/data # if you want logs
      - /dir/<PATH>:/instances/<PATH>:ro # mount the docker compose stack directory in the appropriate path defined earlier as read only
```

### testing for me
```yaml
commands:
  start: "docker compose start"
  stop: "docker compose stop"
  restart: "docker compose restart"
  status: "docker compose ps"

instances:
  minecraft/gtnh_multiplayer:
    channel: 93487219472
    commands:
      backup: "some command"
        
  minecraft/new_beginnings:
    channel: 00183718431123

  ark_se/the_island_grind:
    channel: 04820472649050
```